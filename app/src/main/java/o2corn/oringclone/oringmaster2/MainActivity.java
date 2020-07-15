package o2corn.oringclone.oringmaster2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import o2corn.oringclone.oringmaster2.BottomNavigationFragment.AboutUsFragment;
import o2corn.oringclone.oringmaster2.BottomNavigationFragment.DimensionSearchFragment;
import o2corn.oringclone.oringmaster2.BottomNavigationFragment.OringHousingFragment;

import com.oringclone.oringmaster2.R;

import o2corn.oringclone.oringmaster2.model.Oring;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // 미리 메인화면에서 리스트 데이터 세팅
    public static ArrayList<Oring> oring_list = new ArrayList<>();          // 원본 오링 리스트 3316 개
    public static ArrayList<Oring> filtered_oring_List = new ArrayList<>(); // 필터된 오링 리스트
    public static ArrayList<Oring> reseted_oring_List = new ArrayList<>();    // 오링 리스트 초기 상태 [고정 리스트 0~5 index]


    // FrameLayout에 각 메뉴의 Fragment를 바꿔 줌
    private FragmentManager fragmentManager = getSupportFragmentManager();
    // 4개의 메뉴에 들어갈 Fragment들
    private DimensionSearchFragment dimensionSearchFragment = new DimensionSearchFragment();
    private OringHousingFragment oringHousingFragment = new OringHousingFragment();
    private AboutUsFragment aboutUsFragment = new AboutUsFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // [TODO] 데이터 초기화
        OringjsonParsing(getOringJsonString());
        resetOringList();



        // 바텀 네비게이션 객체 설정
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);
//        // 첫 화면 지정
//        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        transaction.replace(R.id.frame_layout, oringHousingFragment).commitAllowingStateLoss(); // 기존 첫화면 수정 = dimensionSearchFragment

        // bottomNavigationView의 아이템이 선택될 때 호출될 리스너 등록
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                switch (item.getItemId()) {
                    case R.id.navigation_menu1: {
                        transaction.replace(R.id.frame_layout, dimensionSearchFragment).commitAllowingStateLoss();
                        break;
                    }
                    case R.id.navigation_menu2: {
                        transaction.replace(R.id.frame_layout, oringHousingFragment).commitAllowingStateLoss();
                        break;
                    }
                    case R.id.navigation_menu3: {
                        transaction.replace(R.id.frame_layout, aboutUsFragment).commitAllowingStateLoss();
                        break;
                    }
                }

                return true;
            }
        });

        bottomNavigationView.setSelectedItemId(R.id.navigation_menu1); // 초기화면 2번째 바텀 아이템으로 설정
    }// onCreate 끝


    private void resetOringList() {
        for(int a=0; a < 6; a++){ // 0 1 2 3 4 5
            reseted_oring_List.add(oring_list.get(a));
        }
    }


    // Json 데이터 추출
    private String getOringJsonString() {
        String json = "";
        try {
            InputStream is = getAssets().open("oringtable_mm.json");
            int fileSize = is.available();

            byte[] buffer = new byte[fileSize];
            is.read(buffer);
            is.close();

            json = new String(buffer, "UTF-8");
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        return json;
    }

    // 데이터 파싱(전체 원본 리스트)
    private void OringjsonParsing(String json) {
        try{
            JSONObject jsonObject = new JSONObject(json);

            JSONArray oringArray = jsonObject.getJSONArray("oringtable_mm");
            Gson gson = new Gson();

            for(int i=0; i<oringArray.length(); i++) {
                Oring oring = gson.fromJson(oringArray.get(i).toString(), Oring.class);
                oring_list.add(oring);


//                JSONObject movieObject = movieArray.getJSONObject(i);
//
//                Movie movie = new Movie();
//
//                movie.setTitle(movieObject.getString("title"));
//                movie.setGrade(movieObject.getString("grade"));
//                movie.setCategory(movieObject.getString("category"));
//
//                movieList.add(movie);
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }
    }



}
