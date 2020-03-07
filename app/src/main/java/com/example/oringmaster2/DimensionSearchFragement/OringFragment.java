package com.example.oringmaster2.DimensionSearchFragement;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oringmaster2.BuildConfig;
import com.example.oringmaster2.R;
import com.example.oringmaster2.RecyclerViewAdapter;
import com.example.oringmaster2.model.Oring;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.oringmaster2.MainActivity.filtered_oring_List;
import static com.example.oringmaster2.MainActivity.oring_list;

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

    // MaterialButtonToggleGroup 버튼 그룹 객체
    MaterialButtonToggleGroup storeClassTypeSelector;   // 상단 버튼 그룹 객체
    MaterialButtonToggleGroup storeFoodTypeSelector;    // 하단 버튼 그룹 객체

    MaterialButton top_toggle_button_1; // 상단 토글 버튼 1 = 맛집
    MaterialButton top_toggle_button_2; // 상단 토글 버튼 2 = 뷰티

    MaterialButton bottom_toggle_button_1; // 하단 토글 버튼 1
    MaterialButton bottom_toggle_button_2; // 하단 토글 버튼 2

    EditText ID_editText;
    EditText CS_editText;


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
        view = inflater.inflate(R.layout.fragment_home, container, false);
        Log.e(this.getClass().getSimpleName(), "onCreateView()");

        // 객체 생성
        ID_editText = (EditText) view.findViewById(R.id.ET_id_noun);
        CS_editText = (EditText) view.findViewById(R.id.ET_cs_noun);

        // 리사이클러뷰
        recyclerView = view.findViewById(R.id.oring_recyclerview);
        adapter = new RecyclerViewAdapter(getContext(), oring_list, filtered_oring_List); // 필터안된 리스트 , 필터된 리스트
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        // 머티리얼 버튼 그룹
        MaterialButtonToggleGroup storeClassTypeSelector = view.findViewById(R.id.select_toggle_store_class_type);  // 상단 버튼 그룹
        // 최근 검색 데이터

        top_toggle_button_1 = view.findViewById(R.id.top_toggle_button_1); // 상단 토글 버튼 1 = 맛집
        top_toggle_button_2 = view.findViewById(R.id.top_toggle_button_2); // 상단 토글 버튼 2 = 뷰티


        // 텍스트 변경 감지 리스너
        ID_editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) { // ID_editText 변경 감지
//                Log.e("Input_Type = ",Input_Type);
//                Log.e("onTextChanged = ",charSequence.toString());

                if(charSequence.toString().isEmpty() && CS_current_value.isEmpty()){
                    Log.e("ID 감지 리스너 = ","초기화");
                    // 다시 초기화 => 입력 후 다시 입력값 '없음'으로 제대로 초기화 해야함
                    ID_current_value = "";
                    CS_current_value = "";
                    initOringListData_2(); // 리스트 초기화 메소드
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

                Log.e("CS 입력값 확인 = ",charSequence.toString());
                Log.e("현재 ID 값 확인 = ",ID_current_value);

                if(charSequence.toString().isEmpty() && ID_current_value.isEmpty()){
                    Log.e("CS 감지 리스너 = ","초기화");
                    ID_current_value = "";
                    CS_current_value = "";
                    initOringListData_2(); // 리스트 초기화 메소드
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


//        // 리사이클러뷰
//        recyclerView = view.findViewById(R.id.oring_recyclerview);
//        adapter = new RecyclerViewAdapter(getContext(), oring_list, filtered_oring_List); // 필터안된 리스트 , 필터된 리스트
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
//        recyclerView.setAdapter(adapter);
//
//        // 머티리얼 버튼 그룹
//        MaterialButtonToggleGroup storeClassTypeSelector = view.findViewById(R.id.select_toggle_store_class_type);  // 상단 버튼 그룹
//        // 최근 검색 데이터
//
//        top_toggle_button_1 = view.findViewById(R.id.top_toggle_button_1); // 상단 토글 버튼 1 = 맛집
//        top_toggle_button_2 = view.findViewById(R.id.top_toggle_button_2); // 상단 토글 버튼 2 = 뷰티


        return view;
    }// onCreateView 끝


    // 리스트 초기화 메소드
    private void initOringListData() {
        filtered_oring_List.clear();
        Log.e("initOringListData() = ","들어옴");

        for(int a=0; a < 6; a++){ // 0 1 2 3 4 5
            filtered_oring_List.add(oring_list.get(a));
        }
//        adapter.notifyDataSetChanged();
    }

    // 리스트 초기화 메소드
    private void initOringListData_2() {
        filtered_oring_List.clear();
        Log.e("리스트초기화_2 = " ,"들어옴");

        for(int a=0; a < 6; a++){ // 0 1 2 3 4 5
            filtered_oring_List.add(oring_list.get(a));
        }
        adapter.notifyDataSetChanged();
    }


//    @Override
//    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
//    @Override
//    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//        Log.e("Input_Type = ",Input_Type);
//        Log.e("onTextChanged = ",charSequence.toString());
//
//        /**
//         * ID 와 CS 입력값 타입을 다르게 특정지어야 한다.
//         *
//         *
//         */
////        adapter.getFilter().filter(charSequence);
//    }
//    @Override
//    public void afterTextChanged(Editable editable) { }

}
