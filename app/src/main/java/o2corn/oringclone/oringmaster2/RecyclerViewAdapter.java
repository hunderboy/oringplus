package o2corn.oringclone.oringmaster2;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.oringclone.oringmaster2.R;

import o2corn.oringclone.oringmaster2.model.Oring;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;

import o2corn.oringclone.oringmaster2.DimensionSearchFragement.OringFragment;

import static o2corn.oringclone.oringmaster2.MainActivity.oring_list;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.OringViewHolder> implements Filterable {
    /**
     * 일반적인 Adapter와 다른 점은 리사이클러뷰의 내용물에 해당하는 List가 3가지가 추가된다는 점입니다.
     *
     * 1. 필터링 되지 않은 리스트
     * 2. 필터링 중인 리스트
     * 3. 필터링 된 리스트
     */
    Context context;
    ArrayList<Oring> unFilteredlist;   // oring 을 포함하고 있는 필터안된 리스트
    ArrayList<Oring> filteredList;     // oring 을 포함하고 있는 필터된 리스트



    double ViewHolder_ID_value;
    double ViewHolder_CS_value;
    double ViewHolder_ID_Tolernce_value;
    double ViewHolder_CS_Tolernce_value;

     BigDecimal ViewHolder_result_A1;  // 해당 행의 ID + 오차범위
     BigDecimal ViewHolder_result_A2;  // 해당 행의 ID - 오차범위
     BigDecimal ViewHolder_result_B1;  // 해당 행의 CS + 오차범위
     BigDecimal ViewHolder_result_B2;  // 해당 행의 CS - 오차범위

     // 비교 결과 값 = int
     int ViewHolder_compare_result_A1_to_ID;
     int ViewHolder_compare_result_A2_to_ID;
     int ViewHolder_compare_result_B1_to_CS;
     int ViewHolder_compare_result_B2_to_CS;


    public RecyclerViewAdapter(Context context, ArrayList<Oring> unFilteredlist , ArrayList<Oring> filteredList) {
        super();
        this.context = context;
        this.unFilteredlist = unFilteredlist;
        this.filteredList = filteredList;
    }

    @Override
    public OringViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.oring_list_item, parent, false);
        return new OringViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OringViewHolder holder, int position) {

        if( filteredList.size()!=0){
            // 홀수에 따라 row 전체 색 변환
            if((position+1)%2==0) {
                // Log.e("짝수줄  = ", String.valueOf(position+1));
                holder.oringitem_background.setBackgroundColor(Color.parseColor("#FAFABE"));
            }else {
                // Log.e("홀수줄 = ", String.valueOf(position+1));
                holder.oringitem_background.setBackgroundColor(Color.parseColor("#ffffff"));
            }

            /**
             * 각 행의 ID, CS 데이터 들 결과 추출 해야 함.
             * 1. ID ± ID_Tolernce : A1 = + 결과, A2 = - 결과
             * 2. CS ± CS_Tolernce : B1 = + 결과, B2 = - 결과
             *
             * 3. A1 과 현재 ID_current_value 비교
             * 4. A2 과 현재 CS_current_value 비교
             * 5. B1 과 현재 ID_current_value 비교
             * 6. B2 과 현재 CS_current_value 비교
             */

            ViewHolder_ID_value = filteredList.get(position).getID();
            ViewHolder_CS_value = filteredList.get(position).getCS();
            ViewHolder_ID_Tolernce_value = filteredList.get(position).getTolernce_id();
            ViewHolder_CS_Tolernce_value = filteredList.get(position).getTolernce_cs();

//            Log.e("CS_current_value = ",CS_current_value);
//            Log.e("ID_current_value = ",ID_current_value);
            if(OringFragment.ID_current_value.isEmpty() || OringFragment.CS_current_value.isEmpty()){ // 현재 ID,CS 값이 둘중에 하나라도 존재하지 않으면 Do nothing
                // Do nothing
                holder.IV_match_type_right.setImageResource(R.drawable.red_circle); // [TODO] 붉은 원 설정
                // ID
                holder.TV_id_num.setTextColor( Color.parseColor("#8a000000") );
                holder.TV_id_tolerance.setTextColor(Color.parseColor("#8a000000"));
                // CS
                holder.TV_cs_num.setTextColor(Color.parseColor("#8a000000"));
                holder.TV_cs_tolerance.setTextColor(Color.parseColor("#8a000000") );
                // 스탠다드, 링 넘버
                holder.TV_column_standard.setTextColor(Color.parseColor("#8a000000"));
                holder.TV_column_no.setTextColor(Color.parseColor("#8a000000"));
            }
            else { // 현재 값. ID,CS가 값이 존재 하면 -  현재 ID,CS 값이 둘중에 하나라도 있으면
                // 오차범위 +-
                ViewHolder_result_A1 = BigDecimal.valueOf(ViewHolder_ID_value).add(BigDecimal.valueOf(ViewHolder_ID_Tolernce_value));       // 해당 행의 ID + 오차범위
                ViewHolder_result_A2 = BigDecimal.valueOf(ViewHolder_ID_value).subtract(BigDecimal.valueOf(ViewHolder_ID_Tolernce_value));  // 해당 행의 ID - 오차범위
                ViewHolder_result_B1 = BigDecimal.valueOf(ViewHolder_CS_value).add(BigDecimal.valueOf(ViewHolder_CS_Tolernce_value));       // 해당 행의 CS + 오차범위
                ViewHolder_result_B2 = BigDecimal.valueOf(ViewHolder_CS_value).subtract(BigDecimal.valueOf(ViewHolder_CS_Tolernce_value));  // 해당 행의 CS - 오차범위
                // 비교 결과 값 = int
                ViewHolder_compare_result_A1_to_ID = Double.compare(ViewHolder_result_A1.doubleValue(), Double.parseDouble(OringFragment.ID_current_value));
                ViewHolder_compare_result_A2_to_ID = Double.compare(ViewHolder_result_A2.doubleValue(), Double.parseDouble(OringFragment.ID_current_value));
                ViewHolder_compare_result_B1_to_CS = Double.compare(ViewHolder_result_B1.doubleValue(), Double.parseDouble(OringFragment.CS_current_value));
                ViewHolder_compare_result_B2_to_CS = Double.compare(ViewHolder_result_B2.doubleValue(), Double.parseDouble(OringFragment.CS_current_value));

                // [TODO] 현재 입력 ID,CS 가 검색된 행의 ID,CS 값과 완전 일치하면
                if ( Double.compare( filteredList.get(position).getID() , Double.parseDouble(OringFragment.ID_current_value) ) == 0 &&
                        Double.compare( filteredList.get(position).getCS() , Double.parseDouble(OringFragment.CS_current_value) ) == 0 ) {
                    holder.IV_match_type_right.setImageResource(R.drawable.green_circle); // [TODO] 초록색 원 설정
                    // ID
                    holder.TV_id_num.setTextColor(Color.parseColor("#FFD228"));
                    holder.TV_id_tolerance.setTextColor(Color.parseColor("#FFD228"));
                    // CS
                    holder.TV_cs_num.setTextColor(Color.parseColor("#FFD228"));
                    holder.TV_cs_tolerance.setTextColor(Color.parseColor("#FFD228") );
                    // 스탠다드, 링 넘버
                    holder.TV_column_standard.setTextColor(Color.parseColor("#FFD228"));
                    holder.TV_column_no.setTextColor(Color.parseColor("#FFD228"));
                }
                // [TODO] ID,CS 값이 해당 row 오차범위 내에 포함 될 경우
                else if ( (ViewHolder_compare_result_A1_to_ID==0 || ViewHolder_compare_result_A1_to_ID==1) && (ViewHolder_compare_result_A2_to_ID==0 || ViewHolder_compare_result_A2_to_ID==-1) ){ // ID값 오차 범위 내 대조
    //                Log.e("compare_A1_to_ID = ", String.valueOf(compare_result_A1_to_ID));
    //                Log.e("compare_A2_to_ID = ", String.valueOf(compare_result_A2_to_ID));
                    if( (ViewHolder_compare_result_B1_to_CS==0 || ViewHolder_compare_result_B1_to_CS ==1) && (ViewHolder_compare_result_B2_to_CS==0 || ViewHolder_compare_result_B2_to_CS==-1) ){   // CS값 오차 범위 내 대조
    //                    Log.e("compare_B1_to_CS = ", String.valueOf(compare_result_B1_to_CS));
    //                    Log.e("compare_B2_to_CS = ", String.valueOf(compare_result_B2_to_CS));
                        holder.IV_match_type_right.setImageResource(R.drawable.blue_circle); // [TODO] 파란 원 설정
                        // ID
                        holder.TV_id_num.setTextColor( Color.parseColor("#8a000000") );
                        holder.TV_id_tolerance.setTextColor(Color.parseColor("#8a000000"));
                        // CS
                        holder.TV_cs_num.setTextColor(Color.parseColor("#8a000000"));
                        holder.TV_cs_tolerance.setTextColor(Color.parseColor("#8a000000") );
                        // 스탠다드, 링 넘버
                        holder.TV_column_standard.setTextColor(Color.parseColor("#8a000000"));
                        holder.TV_column_no.setTextColor(Color.parseColor("#8a000000"));
                    }
                }
            }
            // 데이터 설정 ----------------------------------------------------------------------------------------------------
            // ID
            holder.TV_id_num.setText(String.valueOf(filteredList.get(position).getID()));
            holder.TV_id_tolerance.setText(String.valueOf(filteredList.get(position).getTolernce_id()) );
            // CS
            holder.TV_cs_num.setText(String.valueOf(filteredList.get(position).getCS()) );
            holder.TV_cs_tolerance.setText(String.valueOf(filteredList.get(position).getTolernce_cs()) );
            // 스탠다드, 링 넘버
            holder.TV_column_standard.setText(filteredList.get(position).getSTANDARD());
            holder.TV_column_no.setText(filteredList.get(position).getNo());
        }


    }// onBindViewHolder 끝

    /**
     * 오링 뷰홀더
     */
    public class OringViewHolder extends RecyclerView.ViewHolder {
        LinearLayout oringitem_background;
        ImageView IV_match_type_right;

        TextView TV_id_num;
        TextView TV_id_tolerance;
        TextView TV_cs_num;
        TextView TV_cs_tolerance;
        TextView TV_column_standard;
        TextView TV_column_no;

        public OringViewHolder(View itemView) {
            super(itemView);

            IV_match_type_right = itemView.findViewById(R.id.IV_match_type_right);
            TV_id_num = itemView.findViewById(R.id.TV_id_num);
            TV_id_tolerance = itemView.findViewById(R.id.TV_id_tolerance);
            TV_cs_num = itemView.findViewById(R.id.TV_cs_num);
            TV_cs_tolerance = itemView.findViewById(R.id.TV_cs_tolerance);
            TV_column_standard = itemView.findViewById(R.id.TV_column_standard);
            TV_column_no = itemView.findViewById(R.id.TV_column_no);
            oringitem_background = itemView.findViewById(R.id.oringitem_background); // 백그라운드 레이아웃
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) { // CharSequence constraint 입력된 String
                String type = "no_type";    // 입력한 텍스트 타입
                String msg = "no_msg";      // 입력한 ID 값(String)
                String jsonString = constraint.toString();  // 전달 받은 jsonString 1.type 2.msg

                /***
                 * 1. 전달 받은 json String 값 분리
                 * type = 타입
                 * msg = 메세지
                 * */
                try {
                    JSONObject resultObj = new JSONObject(jsonString);

                    type = resultObj.getString("type");
                    msg = resultObj.getString("msg");

                    Log.e("type = ",type);
                    Log.e("msg = ",msg);

                } catch (JSONException e) {
                    e.printStackTrace();
                }



                /*** 2. Json 데이터 빈 값일 경우 처리*/
                if(jsonString.isEmpty()) {  // 비어있을때
                    // do nothing
                } else {
                    /*** 3. Type 에 다른 메세지 처리*/
                    ArrayList<Oring> filteringList = new ArrayList<>(); // 필터링 중인 리스트 생성

                    if (type.equals("init_List")){ // 리스트 초기 상태로 원상복귀 --------------------------------------------------------------------------------------------------------------------------------------

                        for(int a=0; a < 6; a++){ // 0 1 2 3 4 5
                            filteringList.add(oring_list.get(a));
                        }
//                        filteringList = reseted_oring_List; // 리스트 초기 상태화
                    }
                    else if(type.equals("Type_ID")){ // ID 에서 넘어온 데이터 -------------------------------------------------------------------------------------------------------------------------------------------

                        if(OringFragment.CS_current_value.isEmpty()){ // 먼저 입력된 CS 값이 존재하지 않는다면    ||ID_current_value.equals()

                            /**
                             * 전달 받은 데이터의
                             * type = Type_ID
                             * msg = "???" 이다.
                             * 만약에 msg = "" 일때 어떻게 처리 할 것인가??
                             * msg = "" 에서 리스트 오작동 되는 상황
                             * 1. 이 경우는 TextWatcher가 입력 텍스트를 모두 지웠을때 발생함. = ID,CS EditText 두개 모두를 지웠을때는 발생하지 않는다. 1개만 지웠을때만 발생한다.
                             * 2. Fragment 화면으로 다시 돌아 왔을 경우 onCreateView 만 시작되는 경우
                             */

                            if(msg.isEmpty()){ // [TODO] 이렇게 되면 ID,CS 값 모두 비어있다는 이야기가 된다.
                                for(int a=0; a < 6; a++){ // 0 1 2 3 4 5
                                    filteringList.add(oring_list.get(a));
                                }
                            }else { // [TODO] 입력된 ID  = O 존재 , CS =  X 없음 (""상태)
                                for(Oring oring : unFilteredlist) {  // A : B => B 에서 차례대로 객체를 꺼내서 A 에다가 넣겠다. String tmp = Double.toString(1.24657);
                                    /**
                                     * 준비물
                                     * 1. 입력한 ID
                                     * 2. 각 row 의 ID 값
                                     * 3. 각 row 의 오차범위
                                     * 4. 계산된 Id + (오차범위)
                                     * 5. 계산된 Id - (오차범위)
                                     * 입력한 아이디를 계산된 오차범위 내에 해당 되는지 확인한다.
                                     * 확인하고 난뒤에 필터링 리스트가 사이즈가 6을 초과여부를 확인한다. 6개 이상 화면에 표시하지 않는다.
                                     */
//                                    Log.e("filteringList 사이즈 => ", String.valueOf(filteringList.size()));
                                    if(filteringList.size() <= 5){ // 필터링 리스트 사이즈 5 으로 제한(0~5 =6)

                                        BigDecimal result_A1 = BigDecimal.valueOf(oring.getID()).add(BigDecimal.valueOf(oring.getTolernce_id()));       // 해당 행의 ID + 오차범위
                                        BigDecimal result_A2 = BigDecimal.valueOf(oring.getID()).subtract(BigDecimal.valueOf(oring.getTolernce_id()));  // 해당 행의 ID - 오차범위
                                        int compare_result_A1_to_ID = Double.compare(result_A1.doubleValue(), Double.parseDouble(msg)); // ID+오차범위 , msg_ID 값 비교
                                        int compare_result_A2_to_ID = Double.compare(result_A2.doubleValue(), Double.parseDouble(msg)); // ID-오차범위 , msg_ID 값 비교

                                        if ( (compare_result_A1_to_ID==0 || compare_result_A1_to_ID==1) && (compare_result_A2_to_ID==0 || compare_result_A2_to_ID==-1) ) { // ID값 오차 범위 내 대조 = 허용 오차 범위 인 경우
                                            filteringList.add(oring);    // 필터링 리스트에 해당 oring row 추가
                                        }
                                    } else {    // filteringList 사이즈 => 6 포함 이상인 경우
                                        break;
                                    }

                                }// 반복문 끝
                            }
                        }// CS 값 존재 안함 조건문 끝
                        else { // [TODO] 입력된 ID  = O 존재 , CS = O 존재
                            Log.e("CS 값이 존재함??? 확인해보자 => ", OringFragment.CS_current_value);

                            if(msg.isEmpty()){ // CS 값은 존재 하는데 ,ID 값이 비어있다 , CS 값으로만 데이터 필터링 작업을 진행 해야 한다.
                                for(Oring oring : unFilteredlist) {  // A : B => B 에서 차례대로 객체를 꺼내서 A 에다가 넣겠다.

                                    if (filteringList.size() <= 5) { // 필터링 리스트 사이즈 5 으로 제한(0~5 =6)

                                        BigDecimal result_B1 = BigDecimal.valueOf(oring.getCS()).add(BigDecimal.valueOf(oring.getTolernce_cs()));       // 해당 행의 CS + 오차범위
                                        BigDecimal result_B2 = BigDecimal.valueOf(oring.getCS()).subtract(BigDecimal.valueOf(oring.getTolernce_cs()));  // 해당 행의 CS - 오차범위
                                        int compare_result_B1_to_ID = Double.compare(result_B1.doubleValue(), Double.parseDouble(OringFragment.CS_current_value)); // CS+오차범위 , 현재 CS로 값 비교
                                        int compare_result_B2_to_ID = Double.compare(result_B2.doubleValue(), Double.parseDouble(OringFragment.CS_current_value)); // CS-오차범위 , 현재 CS로 값 비교

                                        if ((compare_result_B1_to_ID == 0 || compare_result_B1_to_ID == 1) && (compare_result_B2_to_ID == 0 || compare_result_B2_to_ID == -1)) { // ID값 오차 범위 내 대조 = 허용 오차 범위 인 경우
                                            filteringList.add(oring);    // 필터링 리스트에 해당 oring row 추가
                                        }
                                    } else {    // filteringList 사이즈 => 6 포함 이상인 경우
                                        break;
                                    }
                                }// 반복문 끝
                            }
                            else { // [TODO] ID,CS 값 모두 가지고 필터링 진행
                                for(Oring oring : unFilteredlist) {  // A : B => B 에서 차례대로 객체를 꺼내서 A 에다가 넣겠다. String tmp = Double.toString(1.24657);

                                    if(filteringList.size() <= 5){ // 필터링 리스트 사이즈 5 으로 제한(0~5 =6)

                                        // 입력한 ID 로 비교
                                        BigDecimal result_A1 = BigDecimal.valueOf(oring.getID()).add(BigDecimal.valueOf(oring.getTolernce_id()));       // 해당 행의 ID + 오차범위
                                        BigDecimal result_A2 = BigDecimal.valueOf(oring.getID()).subtract(BigDecimal.valueOf(oring.getTolernce_id()));  // 해당 행의 ID - 오차범위
                                        int compare_result_A1_to_ID = Double.compare(result_A1.doubleValue(), Double.parseDouble(msg)); // ID+오차범위 , msg_ID 값 비교
                                        int compare_result_A2_to_ID = Double.compare(result_A2.doubleValue(), Double.parseDouble(msg)); // ID-오차범위 , msg_ID 값 비교

                                        // 현재값 CS 로 비교
                                        BigDecimal result_B1 = BigDecimal.valueOf(oring.getCS()).add(BigDecimal.valueOf(oring.getTolernce_cs()));       // 해당 행의 CS + 오차범위
                                        BigDecimal result_B2 = BigDecimal.valueOf(oring.getCS()).subtract(BigDecimal.valueOf(oring.getTolernce_cs()));  // 해당 행의 CS - 오차범위
                                        int compare_result_B1_to_ID = Double.compare(result_B1.doubleValue(), Double.parseDouble(OringFragment.CS_current_value)); // CS+오차범위 , 현재 CS로 값 비교
                                        int compare_result_B2_to_ID = Double.compare(result_B2.doubleValue(), Double.parseDouble(OringFragment.CS_current_value)); // CS-오차범위 , 현재 CS로 값 비교

                                        // [TODO] ID입력값,CS 현재값으로 모두 필터링 , 범위 안에 모두 포함 되어야 함.
                                        if( ( (compare_result_B1_to_ID == 0 || compare_result_B1_to_ID == 1) && (compare_result_B2_to_ID == 0 || compare_result_B2_to_ID == -1) ) &&
                                                ( (compare_result_A1_to_ID==0 || compare_result_A1_to_ID==1) && (compare_result_A2_to_ID==0 || compare_result_A2_to_ID==-1) ) ) {


                                            filteringList.add(oring);    // 필터링 리스트에 데이터 추가
                                        }



                                    } else {    // filteringList 사이즈 => 6 포함 이상인 경우
                                        break;
                                    }

                                }
                            }

                        }// CS 값 존재 할때 ID 값 처리 끝
                    }// type ID 끝 ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                    else if(type.equals("Type_CS")){
                        if(OringFragment.ID_current_value.isEmpty()){ // 먼저 입력된 ID 값이 존재하지 않는다면

                            if(msg.isEmpty()){ // [TODO] 이렇게 되면 ID,CS 값 모두 비어있다는 이야기가 된다.
                                for(int a=0; a < 6; a++){ // 0 1 2 3 4 5
                                    filteringList.add(oring_list.get(a));
                                }
                            } else { // [TODO] 입력된 CS  = 0 존재 , ID = X 없음 (""상태)
                                for(Oring oring : unFilteredlist) {  // A : B => B 에서 차례대로 객체를 꺼내서 A 에다가 넣겠다. String tmp = Double.toString(1.24657);

//                                    Log.e("filteringList 사이즈 => ", String.valueOf(filteringList.size()));
                                    if(filteringList.size() <= 5){ // 필터링 리스트 사이즈 5 으로 제한(0~5 =6)

                                        BigDecimal result_B1 = BigDecimal.valueOf(oring.getCS()).add(BigDecimal.valueOf(oring.getTolernce_cs()));       // 해당 행의 ID + 오차범위
                                        BigDecimal result_B2 = BigDecimal.valueOf(oring.getCS()).subtract(BigDecimal.valueOf(oring.getTolernce_cs()));  // 해당 행의 ID - 오차범위
                                        int compare_result_B1_to_ID = Double.compare(result_B1.doubleValue(), Double.parseDouble(msg)); // ID+오차범위 , msg_ID 값 비교
                                        int compare_result_B2_to_ID = Double.compare(result_B2.doubleValue(), Double.parseDouble(msg)); // ID-오차범위 , msg_ID 값 비교

                                        if ( (compare_result_B1_to_ID==0 || compare_result_B1_to_ID==1) && (compare_result_B2_to_ID==0 || compare_result_B2_to_ID==-1) ) { // ID값 오차 범위 내 대조 = 허용 오차 범위 인 경우
                                            filteringList.add(oring);    // 필터링 리스트에 해당 oring row 추가
                                        }
                                    } else {    // filteringList 사이즈 => 6 포함 이상인 경우
                                        break;
                                    }
                                }
                            }
                        }// ID 값 존재 안함 조건문 끝
                        else { // 먼저 입력된 ID 값이 존재한다면
                            Log.e("ID 값이 존재함??? 확인해보자 => ", OringFragment.ID_current_value);

                            /**
                             * 오케이 ID값이 존재 한다는건 알겠다
                             * 그런데 Cs값이 "" 일수도 있다. => 이에 대한 처리를 해줘야 한다.
                             */

                            if(msg.isEmpty()){ // ID 값은 존재 하는데 ,CS 값이 비어있다
                                // ID 값으로만 데이터 필터링 작업을 진행 해야 한다.
                                for(Oring oring : unFilteredlist) {  // A : B => B 에서 차례대로 객체를 꺼내서 A 에다가 넣겠다.

                                    if (filteringList.size() <= 5) { // 필터링 리스트 사이즈 5 으로 제한(0~5 =6)

                                        BigDecimal result_A1 = BigDecimal.valueOf(oring.getID()).add(BigDecimal.valueOf(oring.getTolernce_id()));       // 해당 행의 ID + 오차범위
                                        BigDecimal result_A2 = BigDecimal.valueOf(oring.getID()).subtract(BigDecimal.valueOf(oring.getTolernce_id()));  // 해당 행의 ID - 오차범위
                                        int compare_result_A1_to_ID = Double.compare(result_A1.doubleValue(), Double.parseDouble(OringFragment.ID_current_value)); // ID+오차범위 , 현재 입력 ID로 값 비교
                                        int compare_result_A2_to_ID = Double.compare(result_A2.doubleValue(), Double.parseDouble(OringFragment.ID_current_value)); // ID-오차범위 , 현재 입력 ID로 값 비교

                                        if ((compare_result_A1_to_ID == 0 || compare_result_A1_to_ID == 1) && (compare_result_A2_to_ID == 0 || compare_result_A2_to_ID == -1)) { // ID값 오차 범위 내 대조 = 허용 오차 범위 인 경우
                                            filteringList.add(oring);    // 필터링 리스트에 해당 oring row 추가
                                        }

                                    } else {    // filteringList 사이즈 => 6 포함 이상인 경우
                                        break;
                                    }
                                }
                            }
                            else { //  ID,CS 값 모두 가지고 필터링 진행
                                for(Oring oring : unFilteredlist) {  // A : B => B 에서 차례대로 객체를 꺼내서 A 에다가 넣겠다.

                                    if(filteringList.size() <= 5){ // 필터링 리스트 사이즈 5 으로 제한(0~5 =6)

                                        // 현재값 ID 로 비교
                                        BigDecimal result_A1 = BigDecimal.valueOf(oring.getID()).add(BigDecimal.valueOf(oring.getTolernce_id()));       // 해당 행의 ID + 오차범위
                                        BigDecimal result_A2 = BigDecimal.valueOf(oring.getID()).subtract(BigDecimal.valueOf(oring.getTolernce_id()));  // 해당 행의 ID - 오차범위
                                        int compare_result_A1_to_ID = Double.compare(result_A1.doubleValue(), Double.parseDouble(OringFragment.ID_current_value)); // ID+오차범위 , 현재 ID 으로 값 비교
                                        int compare_result_A2_to_ID = Double.compare(result_A2.doubleValue(), Double.parseDouble(OringFragment.ID_current_value)); // ID-오차범위 , 현재 ID 으로 값 비교

                                        // 입력값 CS 로 비교
                                        BigDecimal result_B1 = BigDecimal.valueOf(oring.getCS()).add(BigDecimal.valueOf(oring.getTolernce_cs()));       // 해당 행의 CS + 오차범위
                                        BigDecimal result_B2 = BigDecimal.valueOf(oring.getCS()).subtract(BigDecimal.valueOf(oring.getTolernce_cs()));  // 해당 행의 CS - 오차범위
                                        int compare_result_B1_to_ID = Double.compare(result_B1.doubleValue(), Double.parseDouble(msg)); // CS+오차범위 , 입력값 CS로 값 비교
                                        int compare_result_B2_to_ID = Double.compare(result_B2.doubleValue(), Double.parseDouble(msg)); // CS-오차범위 , 입력값 CS로 값 비교

                                        // [TODO] ID입력값,CS 현재값으로 모두 필터링 , 범위 안에 모두 포함 되어야 함.
                                        if( ( (compare_result_B1_to_ID == 0 || compare_result_B1_to_ID == 1) && (compare_result_B2_to_ID == 0 || compare_result_B2_to_ID == -1) ) &&
                                                ( (compare_result_A1_to_ID==0 || compare_result_A1_to_ID==1) && (compare_result_A2_to_ID==0 || compare_result_A2_to_ID==-1) ) ) {

                                            filteringList.add(oring);    // 리스트에 데이터 추가
                                        }

                                    } else {    // filteringList 사이즈 => 6 포함 이상인 경우
                                        break;
                                    }
                                }// 반복문 종료

                            }
                        }
                    }// type CS 끝 ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                    else {// no type 인 경우
                        Toast.makeText(context, "입력된 ET의 type 이 설정되지 않음 = no type", Toast.LENGTH_LONG).show();
                    }


                    filteredList = filteringList;   // 최종 필터링된 리스트
                    }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;
            }// performFiltering 끝
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredList = (ArrayList<Oring>)results.values;

//                getRecycledViewPool().clear();
                notifyDataSetChanged();
            }
        };
    }


    @Override
    public int getItemCount() {
        return filteredList.size();
    }


}
