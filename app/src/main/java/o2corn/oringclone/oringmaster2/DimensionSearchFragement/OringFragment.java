package o2corn.oringclone.oringmaster2.DimensionSearchFragement;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.oringclone.oringmaster2.R;
import o2corn.oringclone.oringmaster2.RecyclerViewAdapter;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;

import org.json.JSONException;
import org.json.JSONObject;

import static o2corn.oringclone.oringmaster2.MainActivity.filtered_oring_List;
import static o2corn.oringclone.oringmaster2.MainActivity.oring_list;

public class OringFragment extends Fragment  {

    View view;

    public static OringFragment newInstance() {
        OringFragment tab1 = new OringFragment();
        return tab1;
    }

    @Override
    public void onAttach(Activity activity) {
        Log.e(this.getClass().getSimpleName(), "onAttach()"); super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.e(this.getClass().getSimpleName(), "onCreate()");
        super.onCreate(savedInstanceState);
    }



    // Mode 버튼 그룹 객체
    MaterialButtonToggleGroup oringModeButtonToggleGroup;   // 상단 버튼 그룹 객체
    MaterialButton top_toggle_button_1; // 상단 토글 버튼 1 = 맛집
    MaterialButton top_toggle_button_2; // 상단 토글 버튼 2 = 뷰티
    // Unit 버튼 그룹 객체
    MaterialButtonToggleGroup oringUnitButtonToggleGroup;    // 하단 버튼 그룹 객체
    MaterialButton bottom_toggle_button_1; // 하단 토글 버튼 1
    MaterialButton bottom_toggle_button_2; // 하단 토글 버튼 2
    // View
    ImageView oring_imageView;
    EditText ID_editText;
    EditText CS_editText;
    // RecyclerView
    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;
    // Type edittext
    String ET_Type_ID = "Type_ID";
    String ET_Type_CS = "Type_CS";
    // 현재 입력된 값
    public static String ID_current_value = "";
    public static String CS_current_value = "";



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_oring, container, false);
        Log.e(this.getClass().getSimpleName(), "onCreateView()");

        oring_imageView = view.findViewById(R.id.imageView);
        oring_imageView.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("이미지 버튼 클릭됨 = ", "ET 1 and 2  Clear");

                // 1. 먼저 현재 입력된 값을 저장하는 변수를 먼저 초기화 하고 = ID EditText 부터 초기화 하는데 초기화 하자마 TextWatcher 가 동작하여 오작동하는 것을 방지
                ID_current_value = "";
                CS_current_value = "";
                // 2. 그다음에 EditText 초기화
                ID_editText.getText().clear();
                CS_editText.getText().clear();
            }
        }) ;






        // Mode 버튼 그룹
        oringModeButtonToggleGroup = view.findViewById(R.id.select_toggle_mode_type);  // mode 그룹
        top_toggle_button_1 = view.findViewById(R.id.top_toggle_button_1); // Table type view 토글 버튼
        top_toggle_button_2 = view.findViewById(R.id.top_toggle_button_2); // Table type view 토글 버튼
        // Unit 버튼 그룹
        oringUnitButtonToggleGroup = view.findViewById(R.id.select_toggle_unit_type);  // unit 그룹
        bottom_toggle_button_1 = view.findViewById(R.id.bottom_toggle_button_1); // Table type view 토글 버튼
        bottom_toggle_button_2 = view.findViewById(R.id.bottom_toggle_button_2); // Table type view 토글 버튼

        // 상단 버튼 체크 리스너
        oringModeButtonToggleGroup.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {

                MaterialButton button = group.findViewById(checkedId);  // 머티리얼 버튼 객체 가져오기 (상단)
                String button_Tag = button.getTag().toString();   // 상단 버튼 TEXT 가져옴

                if (isChecked){
                    Log.e("선택된 상단 Mode = ", button_Tag+"----------------------------------------------------------------------------------------------");
                    Log.e("checkedId = ", String.valueOf(checkedId));   // 체크된 버튼의 ID 확인
                    Log.e("isChecked = ", String.valueOf(isChecked));   // 체크된 버튼의 boolean 상태 확인


                    if(button_Tag.equals(getString(R.string.mode_viewtype_1))){    // table_viewtype 클릭
                        top_toggle_button_1.setCheckable(false);   // "table" 버튼 '사용금지' 설정
                        top_toggle_button_2.setCheckable(true);    // [TODO] "scroll" 버튼 '사용' 설정
                    }
                    else if(button_Tag.equals(getString(R.string.mode_viewtype_2))){   // scroll_viewtype 클릭
                        top_toggle_button_2.setCheckable(false);   // "scroll" 버튼 '사용금지' 설정
                        top_toggle_button_1.setCheckable(true);    // [TODO] "table" 버튼 '사용' 설정
                    }


                } else{ // 체크가 해제 되는 경우
                    Log.e("버튼 체크 해제 = ", button_Tag);
                    Log.e("checkedId = ", String.valueOf(checkedId));   // UN 체크된 버튼의 ID 확인
                    Log.e("isChecked = ", String.valueOf(isChecked));   // 체크된 버튼의 boolean 상태 확인
                }

            }
        }); // 상단 버튼 체크 리스너 끝
        // 하단 버튼 체크 리스너
        oringUnitButtonToggleGroup.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {

                MaterialButton button = group.findViewById(checkedId);  // 머티리얼 버튼 객체 가져오기 (상단)
                String button_Tag = button.getTag().toString();   // 상단 버튼 TEXT 가져옴

                if (isChecked){
                    Log.e("선택된 상단 Unit = ", button_Tag+"----------------------------------------------------------------------------------------------");
                    Log.e("checkedId = ", String.valueOf(checkedId));   // 체크된 버튼의 ID 확인
                    Log.e("isChecked = ", String.valueOf(isChecked));   // 체크된 버튼의 boolean 상태 확인


                    if(button_Tag.equals(getString(R.string.str_mm))){    // table_viewtype 클릭
                        bottom_toggle_button_1.setCheckable(false);   // "table" 버튼 '사용금지' 설정
                        bottom_toggle_button_2.setCheckable(true);    // [TODO] "scroll" 버튼 '사용' 설정
                    }
                    else if(button_Tag.equals(getString(R.string.str_inch))){   // scroll_viewtype 클릭
                        bottom_toggle_button_2.setCheckable(false);   // "scroll" 버튼 '사용금지' 설정
                        bottom_toggle_button_1.setCheckable(true);    // [TODO] "table" 버튼 '사용' 설정
                    }


                } else{ // 체크가 해제 되는 경우
                    Log.e("버튼 체크 해제 = ", button_Tag);
                    Log.e("checkedId = ", String.valueOf(checkedId));   // UN 체크된 버튼의 ID 확인
                    Log.e("isChecked = ", String.valueOf(isChecked));   // 체크된 버튼의 boolean 상태 확인
                }

            }
        }); // 하단 버튼 체크 리스너 끝




        // 리사이클러뷰
        recyclerView = view.findViewById(R.id.oring_recyclerview);
        adapter = new RecyclerViewAdapter(getContext(), oring_list, filtered_oring_List); // 필터안된 리스트 , 필터된 리스트
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
//        recyclerView.setItemAnimator(null); // 애니메이션 제거
        recyclerView.setAdapter(adapter);


        // 객체 생성
        ID_editText = (EditText) view.findViewById(R.id.ET_id_noun);
        CS_editText = (EditText) view.findViewById(R.id.ET_cs_noun);

        // 텍스트 변경 감지 리스너
        ID_editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) { // ID_editText 변경 감지
                recyclerView.getRecycledViewPool().clear();
                adapter.notifyDataSetChanged();

//                Log.e("ID 입력값 확인 = ",charSequence.toString());
//                Log.e("현재 CS 값 확인 = ",CS_current_value);

                if(charSequence.toString().isEmpty() && CS_current_value.isEmpty()){
                    Log.e("onTextChanged  = ","ID , CS_current_value: X 데이터 없음 ");
                    // 다시 초기화 => 입력 후 다시 입력값 '없음'으로 제대로 초기화 해야함
                    ID_current_value = "";
                    CS_current_value = "";
                    onTextChanged_Reset_OringList(); // 리스트 초기화 메소드
                } else {
                    ID_current_value = charSequence.toString();
                    try{
                        JSONObject resultObj = new JSONObject();

                        resultObj.put("type",ET_Type_ID);
                        resultObj.put("msg",charSequence.toString());

//                    if (BuildConfig.DEBUG) {
//                        Log.e("JsonToString = ", String.valueOf(resultObj));
//                    }

                        adapter.getFilter().filter(String.valueOf(resultObj));

                    }catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void afterTextChanged(Editable arg0) { }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
        });
        CS_editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) { // CS_editText 변경 감지
                recyclerView.getRecycledViewPool().clear();
                adapter.notifyDataSetChanged();

//                Log.e("CS 입력값 확인 = ",charSequence.toString());
//                Log.e("현재 ID 값 확인 = ",ID_current_value);

                if(charSequence.toString().isEmpty() && ID_current_value.isEmpty()){
                    Log.e("onTextChanged  = ","CS , ID_current_value: X 데이터 없음 ");
                    ID_current_value = "";
                    CS_current_value = "";
                    onTextChanged_Reset_OringList(); // 리스트 초기화 메소드
                } else {
                    CS_current_value = charSequence.toString();
                    try{
                        JSONObject resultObj = new JSONObject();

                        resultObj.put("type",ET_Type_CS);
                        resultObj.put("msg",charSequence.toString());

//                    if (BuildConfig.DEBUG) {
//                        Log.e("JsonToString = ", String.valueOf(resultObj));
//                    }

                        adapter.getFilter().filter(String.valueOf(resultObj));

                    }catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


            }
            @Override
            public void afterTextChanged(Editable arg0) { }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
        });


        initOringListData(); // 리스트 초기화 메소드


        return view;
    }// onCreateView 끝




    // 리스트 초기화 메소드 - OringFragment 에서
    private void initOringListData() {
        Log.e("initOringListData() = ","리스트 초기화 메소드 들어옴");

        // 현재 입력값 초기화
        ID_current_value = "";
        CS_current_value = "";
        // 버튼 초기화
        top_toggle_button_1.setChecked(true);
        bottom_toggle_button_1.setChecked(true);

        recyclerView.getRecycledViewPool().clear();
        adapter.notifyDataSetChanged();

//        filtered_oring_List.clear();
        for(int a=0; a < 6; a++){ // 0 1 2 3 4 5
            filtered_oring_List.add(oring_list.get(a));
        }


    }


    // 리스트 초기화 메소드 - 리사이클러뷰 Adapter 에서
    private void onTextChanged_Reset_OringList() {
        Log.e("initOringListData_2 = " ,"ID,CS onTextChanged 에서 리스트 초기화 실행됨");

        try{
            JSONObject resultObj = new JSONObject();
            resultObj.put("type","init_List");
            resultObj.put("msg","init_List_msg");
            adapter.getFilter().filter(String.valueOf(resultObj)); // 어댑터에 초기화 필터기능 작동
        }catch (JSONException e) {
            e.printStackTrace();
        }
    }




//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        Log.e(this.getClass().getSimpleName(), "onActivityCreated()");
//        super.onActivityCreated(savedInstanceState);
//    }

//    @Override
//    public void onPause() {
//        Log.e(this.getClass().getSimpleName(), "onPause()");
//        super.onPause();
//    }
//
//    @Override
//    public void onStop() {
//        Log.e(this.getClass().getSimpleName(), "onStop()");
//        super.onStop();
//    }

    @Override
    public void onDestroyView() {
        Log.e(this.getClass().getSimpleName(), "onDestroyView()");
        super.onDestroyView();
    }



}
