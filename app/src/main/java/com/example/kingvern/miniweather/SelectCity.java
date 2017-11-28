package com.example.kingvern.miniweather;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.edu.pku.kingvern.app.MyApplication;
import cn.edu.pku.kingvern.bean.City;

/**
 * Created by kingvern on 10/18/17.
 */

public class SelectCity extends Activity implements View.OnClickListener{

    private ImageView mBackBtn,mSearchBtn;
    private ListView mlist;
    private List<City> cityList;
    private ArrayList<City> filterDataList;
    private ClearEditText mClearEditText;
    SimpleAdapter simplead;
    List<Map<String, String>> listems;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.select_city);

        mBackBtn=(ImageView)findViewById(R.id.title_back);
        mBackBtn.setOnClickListener(this);

        mClearEditText = (ClearEditText)findViewById(R.id.title_search);

        mClearEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                filterData(s.toString());
//                mlist.setAdapter(simplead);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



//        mSearchBtn = (ImageView)findViewById(R.id.title_search_btn);
//        mSearchBtn.setOnClickListener(this);

        mlist = (ListView) findViewById(R.id.city_list);
        MyApplication myApplication = (MyApplication) getApplication();
        cityList = myApplication.getCityList();

        listems = new ArrayList<Map<String, String>>();

        for(City city : cityList){
            Map<String, String> listem = new HashMap<String, String>();
            listem.put("city",city.getCity());
            listem.put("number",city.getNumber());
            listems.add(listem);
        }
        simplead = new SimpleAdapter(this, listems,
                R.layout.item, new String[] { "city", "number"},
                new int[] {R.id.list_city,R.id.list_number,});
        mlist.setAdapter(simplead);
        mlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                Map<String, String> map = listems.get(pos);
                Intent i = new Intent();
                i.putExtra("cityCode",map.get("number"));
                setResult(RESULT_OK,i);
                finish();
            }
        });

    }


    private void filterData(String fileterStr){
        filterDataList = new ArrayList<City>();
        listems.clear();
        Log.d("filterData: ", "filterData: "+fileterStr);
        if(TextUtils.isEmpty(fileterStr) || fileterStr.equals("")){
            for(City city : cityList){
//                filterDataList.add(city); 大兴
                Map<String, String> listem = new HashMap<String, String>();
                listem.put("city",city.getCity());
                listem.put("number",city.getNumber());
                listems.add(listem);
            }
        }else{
            for(City city : cityList){
                Map<String, String> listem = new HashMap<String, String>();
                if(city.getCity().indexOf(fileterStr.toString())!=-1){
                listem.put("city",city.getCity());
                listem.put("number",city.getNumber());
                listems.add(listem);
                }
            }
            simplead.notifyDataSetChanged();

            //            simplead.

//            filterDataList.clear();
//            for(City city : cityList){
//                if(city.getCity().indexOf(fileterStr.toString())!=-1)
//                filterDataList.add(city);
//            }
        }


//        myadapt.updateListView(filterDataList);



    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_back:
                Intent i=new Intent();
                i.putExtra("cityCode","101160101");
                setResult(RESULT_OK,i);
                finish();
                break;
//            case R.id.title_search_btn:
//
//                break;
            default:
                break;
        }
    }
}
