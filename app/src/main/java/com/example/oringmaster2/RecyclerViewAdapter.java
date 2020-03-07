package com.example.oringmaster2;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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

import com.example.oringmaster2.model.Oring;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;

import static com.example.oringmaster2.DimensionSearchFragement.OringFragment.CS_current_value;
import static com.example.oringmaster2.DimensionSearchFragement.OringFragment.ID_current_value;

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
    double ViewHolder_ID_Tolernce_value; // ± 텍스트
    double ViewHolder_CS_Tolernce_value;

    BigDecimal result_A1;
    BigDecimal result_A2;
    BigDecimal result_B1;
    BigDecimal result_B2;

    int compare_result_A1_to_ID;
    int compare_result_A2_to_ID;
    int compare_result_B1_to_CS;
    int compare_result_B2_to_CS;


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


        if(ID_current_value.isEmpty()||CS_current_value.isEmpty()){ // 현재 값. ID,CS가 값이 존재 안하면
           // Do nothing
        }else {

            // 현재 값. ID,CS가 값이 존재 하면
            result_A1 = BigDecimal.valueOf(ViewHolder_ID_value).add(BigDecimal.valueOf(ViewHolder_ID_Tolernce_value));       // 해당 행의 ID + 오차범위
            result_A2 = BigDecimal.valueOf(ViewHolder_ID_value).subtract(BigDecimal.valueOf(ViewHolder_ID_Tolernce_value));  // 해당 행의 ID - 오차범위
            result_B1 = BigDecimal.valueOf(ViewHolder_CS_value).add(BigDecimal.valueOf(ViewHolder_CS_Tolernce_value));       // 해당 행의 CS + 오차범위
            result_B2 = BigDecimal.valueOf(ViewHolder_CS_value).subtract(BigDecimal.valueOf(ViewHolder_CS_Tolernce_value));  // 해당 행의 CS - 오차범위

            Log.e("result_A1 = ", String.valueOf(result_A1));
            Log.e("result_A2 = ", String.valueOf(result_A2));
            Log.e("result_B1 = ", String.valueOf(result_B1));
            Log.e("result_B2 = ", String.valueOf(result_B2));


            compare_result_A1_to_ID = Double.compare(result_A1.doubleValue(), ViewHolder_ID_value);
            compare_result_A2_to_ID = Double.compare(result_A2.doubleValue(), ViewHolder_ID_value);
            compare_result_B1_to_CS = Double.compare(result_B1.doubleValue(), ViewHolder_CS_value);
            compare_result_B2_to_CS = Double.compare(result_B2.doubleValue(), ViewHolder_CS_value);

//            // 결과값 확인
//            Log.e("compare_A1_to_ID = ", String.valueOf(compare_result_A1_to_ID));
//            Log.e("compare_A2_to_ID = ", String.valueOf(compare_result_A2_to_ID));
//            Log.e("compare_B1_to_CS = ", String.valueOf(compare_result_B1_to_CS));
//            Log.e("compare_B2_to_CS = ", String.valueOf(compare_result_B2_to_CS));


            // 필터된 행의 ID,CS = 현재 입력값 ID,CS (String equals 비교) 경우
            if( Double.toString(filteredList.get(position).getID()).equals(ID_current_value) &&
                    Double.toString(filteredList.get(position).getCS()).equals(CS_current_value) ) {

                holder.IV_match_type_right.setImageResource(R.drawable.green_circle); // 초록색 원 설정
                // ID
//                holder.TV_id_num.setTextColor(Color.YELLOW);
                holder.TV_id_num.setTextColor(Color.parseColor("#FFD228"));
                holder.TV_id_tolerance.setTextColor(Color.parseColor("#FFD228"));
                // CS
                holder.TV_cs_num.setTextColor(Color.parseColor("#FFD228"));
                holder.TV_cs_tolerance.setTextColor(Color.parseColor("#FFD228") );
                // 스탠다드, 링 넘버
                holder.TV_column_standard.setTextColor(Color.parseColor("#FFD228"));
                holder.TV_column_no.setTextColor(Color.parseColor("#FFD228"));
            }
            else if ( (compare_result_A1_to_ID==0 || compare_result_A1_to_ID==1) && (compare_result_A2_to_ID==0 || compare_result_A2_to_ID==-1) ){ // ID값 오차 범위 내 대조

                Log.e("compare_A1_to_ID = ", String.valueOf(compare_result_A1_to_ID)); //
                Log.e("compare_A2_to_ID = ", String.valueOf(compare_result_A2_to_ID));

                if( (compare_result_B1_to_CS==0 || compare_result_B1_to_CS == -1)
                        && (compare_result_B2_to_CS==0 || compare_result_B2_to_CS==-1) ){   // CS값 오차 범위 내 대조
                    Log.e("compare_B1_to_CS = ", String.valueOf(compare_result_B1_to_CS));
                    Log.e("compare0_B1_to_CS = ", String.valueOf(compare_result_B1_to_CS));

                    holder.IV_match_type_right.setImageResource(R.drawable.blue_circle); // 초록색 원 설정
                }

            }

        }



//        /**
//         * double 데이터 비교 값
//         *  a.compareTo(b) = 출력값
//         *  1   = a 가 더 큰경우
//         *  0   = 동일한 경우
//         *  -1  = b 가 더 큰경우
//         */
//        else if(   && ) { // 두가지 경우 모두 오차범위내에 있어야 한다.
//
//        }

        // 써클 이미지 설정
//        holder.IV_match_type_right.setImageResource(R.drawable.red_circle); // 기본 빨강색


        // ID
        holder.TV_id_num.setText(String.valueOf(filteredList.get(position).getID()));
        holder.TV_id_tolerance.setText(String.valueOf(filteredList.get(position).getTolernce_id()) );
        // CS
        holder.TV_cs_num.setText(String.valueOf(filteredList.get(position).getCS()) );
        holder.TV_cs_tolerance.setText(String.valueOf(filteredList.get(position).getTolernce_cs()) );
        // 스탠다드, 링 넘버
        holder.TV_column_standard.setText(filteredList.get(position).getSTANDARD());
        holder.TV_column_no.setText(filteredList.get(position).getNo());
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

//                double EditText_ID_value;   // 입력한 ID 값(double)

                String jsonString = constraint.toString();  // 전달 받은 jsonString 1.type 2.msg


                try {
                    JSONObject resultObj = new JSONObject(jsonString);

                    type = resultObj.getString("type");
                    msg = resultObj.getString("msg");

                    Log.e("type = ",type);
                    Log.e("msg = ",msg);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                /*** 아래 부분은 필터 기능 처리*/
                if(jsonString.isEmpty()) {  // 비어있을때
//                    filteredList = unFilteredlist;
                } else {
                    ArrayList<Oring> filteringList = new ArrayList<>(); // 필터링 중인 리스트

                    if(type.equals("Type_ID")){ // ID 에서 넘어온 데이터
//                        EditText_ID_value = Double.parseDouble(msg); // 입력값 double 화

                        if(CS_current_value.isEmpty()){ // 현재 CS 값이 존재하지 않는다면    ||ID_current_value.equals()

                            for(Oring oring : unFilteredlist) {  // A : B => B 에서 차례대로 객체를 꺼내서 A 에다가 넣겠다. String tmp = Double.toString(1.24657);

                                if(Double.toString(oring.getID()).toLowerCase().contains(msg.toLowerCase())) {  // [TODO] 먼저 입력 ID 값을 포함하고 있는 데이터를 먼저 필터링 하여 넣는다.

                                    if(msg.toLowerCase().isEmpty()){
                                        Log.e("ID 필터링 접근 isEmpty() ","메소드 거치는중");
                                    }

                                    filteringList.add(oring);    // 필터링 추가`
                                }
                            }
                        }
                        else { // 현재 CS 값이 존재한다면

                            for(Oring oring : unFilteredlist) {  // A : B => B 에서 차례대로 객체를 꺼내서 A 에다가 넣겠다. String tmp = Double.toString(1.24657);

                                if(Double.toString(oring.getID()).toLowerCase().contains(msg.toLowerCase()) &&  // A and B
                                        Double.toString(oring.getCS()).toLowerCase().contains(CS_current_value)) {  // [TODO] ID,CS 모두 필터링

                                    if(msg.toLowerCase().isEmpty()){
                                        Log.e("Type_ID 더블 필터링 ","메소드 거치는중");
                                    }

                                    filteringList.add(oring);    // 필터링 추가`
                                }
                            }
                        }

                    }// type ID 끝
                    else if(type.equals("Type_CS")){ // CS 에서 넘어온 데이터

                        if(ID_current_value.isEmpty()){ // 현재 ID 값이 존재하지 않는다면

                            for(Oring oring : unFilteredlist) {  // A : B => B 에서 차례대로 객체를 꺼내서 A 에다가 넣겠다. String tmp = Double.toString(1.24657);

                                if(Double.toString(oring.getCS()).toLowerCase().contains(msg.toLowerCase())) {  // [TODO] 먼저 입력 CS 값을 포함하고 있는 데이터를 먼저 필터링 하여 넣는다.

                                    if(msg.toLowerCase().isEmpty()){
                                        Log.e("CS 필터링 접근 isEmpty()  ","!!");
                                    }

                                    filteringList.add(oring);    // 필터링 추가
                                }
                            }
                        }
                        else { // 현재 ID 값이 존재한다면

                            for(Oring oring : unFilteredlist) {  // A : B => B 에서 차례대로 객체를 꺼내서 A 에다가 넣겠다.

                                if(Double.toString(oring.getCS()).toLowerCase().contains(msg.toLowerCase()) &&  // A and B
                                        Double.toString(oring.getID()).toLowerCase().contains(ID_current_value)) {  // [TODO] ID,CS 모두 필터링
                                    filteringList.add(oring);    // 필터링 추가`
                                }
                            }
                        }
                    }// type CS 끝
                    else {// no type 인 경우
                        Toast.makeText(context, "입력된 ET의 type 이 설정되지 않음 = no type", Toast.LENGTH_LONG).show();
                    }


                    filteredList = filteringList;   // 최종 필터링된 리스트
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredList = (ArrayList<Oring>)results.values;
                notifyDataSetChanged();
            }
        };
    }


    @Override
    public int getItemCount() {
        return filteredList.size();
    }


}
