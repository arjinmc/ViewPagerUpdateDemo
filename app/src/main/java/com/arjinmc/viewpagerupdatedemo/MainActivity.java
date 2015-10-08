package com.arjinmc.viewpagerupdatedemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private List<DataFragment> fragmentList;
    private ViewPageAdapter adapter;

    private Button btnAdd;
    private Button btnDel;
    private ViewPager viewPager;
    private TextView tvCurrentPage;
    private TextView tvTotalPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = (Button) findViewById(R.id.btn_add);
        btnDel = (Button) findViewById(R.id.btn_delete);
        viewPager = (ViewPager) findViewById(R.id.vp);
        tvCurrentPage = (TextView) findViewById(R.id.tv_current_page);
        tvTotalPage = (TextView) findViewById(R.id.tv_total_page);

        btnAdd.setOnClickListener(this);
        btnDel.setOnClickListener(this);

        initData();
    }

    private void initData(){
        if(DataUtil.data ==null){
            DataUtil.data = new ArrayList<>();
        }
        for(int i=0;i<DataUtil.DATA_SIZE;i++){
            DataUtil.data.add("data:"+i);
        }
        fragmentList = new ArrayList<>();
        adapter = new ViewPageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPageListener());

        updateData();
        tvCurrentPage.setText("Page\t1");
        tvTotalPage.setText("total:\t2");

    }

    /**
     * to show data
     */
    public void updateData(){
        fragmentList.clear();
        int pageSize = DataUtil.data.size()/DataUtil.PER_SIZE;
        int pageRest = DataUtil.data.size()%DataUtil.PER_SIZE;
        if(pageRest!=0){
            pageSize++;
        }
        tvTotalPage.setText("total:\t"+pageSize);

        for (int i=0;i<pageSize;i++){
            fragmentList.add(DataFragment.newInstance(i));
        }

        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_add:
                DataUtil.data.add("add data");
                updateData();
                break;
            case R.id.btn_delete:
                if(adapter.getCount()>0){
                    DataUtil.data.remove(DataUtil.data.size()-1);
                    updateData();
                }else {
                    Toast.makeText(this,"no more data",Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }

    private class ViewPageAdapter extends FragmentPagerAdapter {

        public ViewPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

    }

    private class ViewPageListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            tvCurrentPage.setText("Page\t"+(position+1));
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
