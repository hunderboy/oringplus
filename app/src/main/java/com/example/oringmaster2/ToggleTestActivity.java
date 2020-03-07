package com.example.oringmaster2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;

public class ToggleTestActivity extends AppCompatActivity {


    // MaterialButtonToggleGroup 버튼 그룹 객체
    MaterialButtonToggleGroup storeClassTypeSelector;    // 상단 버튼 그룹 객체
    MaterialButtonToggleGroup storeFoodTypeSelector;    // 하단 버튼 그룹 객체


    // 버튼 상태 변수
    MaterialButton top_toggle_button_1; // 상단 토글 버튼 1 = 맛집
    MaterialButton top_toggle_button_2; // 상단 토글 버튼 2 = 뷰티

    MaterialButton bottom_toggle_button_1; // 하단 토글 버튼 1
    MaterialButton bottom_toggle_button_2; // 하단 토글 버튼 2
    MaterialButton bottom_toggle_button_3; // 하단 토글 버튼 3
    MaterialButton bottom_toggle_button_4; // 하단 토글 버튼 4
    MaterialButton bottom_toggle_button_5; // 하단 토글 버튼 5
    MaterialButton bottom_toggle_button_6; // 하단 토글 버튼 6
    MaterialButton bottom_toggle_button_7; // 하단 토글 버튼 7


    int resetButtonBottomNumbers = 0; // 초기화 넘버 변수
    // 하단 버튼 그룹 초기화 Boolean 변수
    Boolean bottom_button_group_variable = false; // 하단 버튼 그룹 초기화 할때만 true



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toggle_test);


        // 상단 버튼 객체 선언
        top_toggle_button_1 = findViewById(R.id.top_toggle_button_1); // 상단 토글 버튼 1 = 맛집
        top_toggle_button_2 = findViewById(R.id.top_toggle_button_2); // 상단 토글 버튼 2 = 뷰티

        // 하단 버튼 객체 선언
        bottom_toggle_button_1 = findViewById(R.id.bottom_toggle_button_1); // 하단 토글 버튼 1
        bottom_toggle_button_2 = findViewById(R.id.bottom_toggle_button_2); // 하단 토글 버튼 2
        bottom_toggle_button_3 = findViewById(R.id.bottom_toggle_button_3); // 하단 토글 버튼 3
        bottom_toggle_button_4 = findViewById(R.id.bottom_toggle_button_4); // 하단 토글 버튼 4
        bottom_toggle_button_5 = findViewById(R.id.bottom_toggle_button_5); // 하단 토글 버튼 5
        bottom_toggle_button_6 = findViewById(R.id.bottom_toggle_button_6); // 하단 토글 버튼 6
        bottom_toggle_button_7 = findViewById(R.id.bottom_toggle_button_7); // 하단 토글 버튼 7


        // 0.01 분류 및 2차 분류 키워드
        storeClassTypeSelector = findViewById(R.id.select_toggle_store_class_type);// 상단 버튼 그룹
        storeFoodTypeSelector = findViewById(R.id.select_toggle_store_food_type);  // 하단 버튼 그룹

        // 상단 버튼 체크 리스너
        storeClassTypeSelector.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                MaterialButton button = group.findViewById(checkedId); // 머티리얼 버튼 객체 가져오기 (상단)
                String Top_button_text = button.getText().toString();

                if (isChecked){
                    Log.e("선택된 상단 텍스트 = ", Top_button_text+"----------------------------------------------------------------------------------------------");   // 체크된 버튼의 ID 확인!! 확인 중요!!!
                    Log.e("checkedId = ", String.valueOf(checkedId));   // 체크된 버튼의 ID 확인
                    Log.e("isChecked = ", String.valueOf(isChecked));   // 체크된 버튼의 boolean 상태 확인
//            Toast.makeText(mActivity, "체크", Toast.LENGTH_SHORT).show();


                    // 1. 상단 버튼을 클릭시 하단 버튼에 나오는 텍스트가 다르게 해야됨.
                    if(Top_button_text.equals("맛집")){
                        top_toggle_button_2.setCheckable(true);    // 뷰티 버튼 '사용' 설정
                        top_toggle_button_1.setCheckable(false);   // 맛집 버튼 '사용금지' 설정

                        bottom_button_group_variable = true; // 하단 버튼 초기화 변수 true 설정
                        storeFoodTypeSelector.clearChecked(); // 하단 버튼 초기화

                        // Text 설정
                        bottom_toggle_button_1.setText(R.string.food_type_1);
                        bottom_toggle_button_2.setText(R.string.food_type_2);
                        bottom_toggle_button_3.setText(R.string.food_type_3);
                        bottom_toggle_button_4.setText(R.string.food_type_4);
                        bottom_toggle_button_5.setText(R.string.food_type_5);
                        bottom_toggle_button_6.setText(R.string.food_type_6);
                        bottom_toggle_button_7.setText(R.string.food_type_7_2);
                        // Tag 설정
                        bottom_toggle_button_1.setTag("한식");
                        bottom_toggle_button_2.setTag("중식");
                        bottom_toggle_button_3.setTag("일식");
                        bottom_toggle_button_4.setTag("BBQ");
                        bottom_toggle_button_5.setTag("양식");
                        bottom_toggle_button_6.setTag("아시아");
                        bottom_toggle_button_7.setTag("회식sub");




//                /** 2. 하단 버튼 초기화 변수 설정 */
//                bottom_button_group_variable = true;
//                /** 3. 하단 버튼 초기화 하기 */
//                /** 4. 맛집 키워드 추가 */
//                keywordsViewModel.setKeywordByTag((String) button.getTag());

                    }
                    else if(Top_button_text.equals("뷰티")){
                        // 상단 버튼 뷰티 체크 설정
                        top_toggle_button_1.setCheckable(true);    // 맛집 버튼 '사용' 설정
                        top_toggle_button_2.setCheckable(false);   // 뷰티 버튼 '사용금지' 설정

                        bottom_button_group_variable = true; // 하단 버튼 초기화 변수 true 설정
                        storeFoodTypeSelector.clearChecked(); // 버튼 초기화

                        // Text 설정
                        bottom_toggle_button_1.setText(R.string.beauty_type_1);
                        bottom_toggle_button_2.setText(R.string.beauty_type_2);
                        bottom_toggle_button_3.setText(R.string.beauty_type_3);
                        bottom_toggle_button_4.setText(R.string.beauty_type_4);
                        bottom_toggle_button_5.setText(R.string.beauty_type_5);
                        bottom_toggle_button_6.setText(R.string.beauty_type_6);
                        bottom_toggle_button_7.setText(R.string.beauty_type_7);
                        // Tag 설정
                        bottom_toggle_button_1.setTag("헤어");
                        bottom_toggle_button_2.setTag("네일");
                        bottom_toggle_button_3.setTag("메이크업");
                        bottom_toggle_button_4.setTag("스파");
                        bottom_toggle_button_5.setTag("마사지");
                        bottom_toggle_button_6.setTag("웨딩");
                        bottom_toggle_button_7.setTag("패키지");


//                /** 1. 키워드 초기화 하기 */
//                keywordsViewModel.clearKeyword();   // 키워드 초기화
//                /** 2. 하단 버튼 초기화 변수 설정 */
//                bottom_button_group_variable = true;
//                /** 3. 하단 버튼 초기화 하기 */

//                /** 4. 뷰티 키워드 추가 */
//                keywordsViewModel.setKeywordByTag((String) button.getTag());


                    }

                } else{ // 체크가 해제 되는 경우
                    Log.e("버튼 체크 해제 = ", Top_button_text);   // UN 체크된 버튼의 ID 확인
                    Log.e("checkedId = ", String.valueOf(checkedId));   // UN 체크된 버튼의 ID 확인
                    Log.e("isChecked = ", String.valueOf(isChecked));   // 체크된 버튼의 boolean 상태 확인
//            Toast.makeText(mActivity, "un 체크", Toast.LENGTH_SHORT).show();
                }

            }
        });


        // 하단 버튼 체크 리스너
        storeFoodTypeSelector.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                Log.e("초기화 Boolean 변수 = ", String.valueOf(bottom_button_group_variable));

                if(bottom_button_group_variable){    // 하단버튼 초기화 변수가 true 경우 = 초기화에 실행되는 버튼 해제 작업 실행됨
                    resetButtonBottomNumbers++; // 숫자 증가
                    Log.e("초기화 넘버 = ", String.valueOf(resetButtonBottomNumbers));

                    if(resetButtonBottomNumbers >= 8){  // 초기화 넘버 변수가 8보다 같거나 크면
                        Log.e("초기화 넘버 변수 조건문 들어옴 = ", String.valueOf(resetButtonBottomNumbers));
                        resetButtonBottomNumbers = 0;   // 0 으로 초기화 한다. (원상 복귀)
                        bottom_button_group_variable = false; // 초기화 Boolean 변수 = false (원상 복귀)

                        Log.e("초기화 Boolean 변수 = ", String.valueOf(bottom_button_group_variable));

                        return; // 메소드 빠져 나옴
                    }
                }

                MaterialButton button = group.findViewById(checkedId); // 머티리얼 버튼 객체 가져오기 (하단)

                String Bottom_button_text = button.getText().toString();
                Log.e("Bottom_button_text = ", Bottom_button_text);   // UN 체크된 버튼의 ID 확인


                if (isChecked){
                    Log.e("선택된 하단 버튼 텍스트 = ", Bottom_button_text);   // 체크된 버튼의 ID 확인!! 확인 중요!!!
                } else{ // 체크가 해제 되는 경우
                    Log.e("체크 해제 된 하단 버튼 텍스트 = ", Bottom_button_text);   // 체크된 버튼의 ID 확인!! 확인 중요!!!
                }

            }
        });





    }




    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

}
