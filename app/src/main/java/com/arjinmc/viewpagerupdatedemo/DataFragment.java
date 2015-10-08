package com.arjinmc.viewpagerupdatedemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * fragment for show data
 * Created by Eminem Lu on 8/10/15.
 * Email arjinmc@hotmail.com
 */
public class DataFragment extends Fragment {

    private View vRoot;

    private ListView listView;
    private ListViewAdapter adapter;

    private List<String> data;
    private int page = 0;

    public DataFragment(){}

    public static DataFragment newInstance(int page){
        DataFragment dataFragment = new DataFragment();
        Bundle args = new Bundle();
        args.putInt("page", page);
        dataFragment.setArguments(args);
        return dataFragment;
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        page = args.getInt("page", 0);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(vRoot==null)
            vRoot = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_data,null);
        return vRoot;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init(){
        listView = (ListView) vRoot.findViewById(R.id.lv_data);
        data = new ArrayList<>();
        //count the real pageSize
        int pageSize = DataUtil.data.size()/DataUtil.PER_SIZE;
        int pageRest = DataUtil.data.size()%DataUtil.PER_SIZE;
        if(pageRest!=0){
            pageSize++;
        }
        if(page==pageSize-1){
            data.addAll(DataUtil.data.subList(DataUtil.PER_SIZE * page, DataUtil.data.size()));
        }else{
            data.addAll(DataUtil.data.subList(DataUtil.PER_SIZE * page, DataUtil.PER_SIZE * (page + 1)));
        }
        adapter = new ListViewAdapter();
        listView.setAdapter(adapter);
    }

    private class ListViewAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder = null;
            if(convertView==null){
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.item_data,null);
                viewHolder = new ViewHolder();
                viewHolder.tv = (TextView) convertView.findViewById(R.id.tv_data);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.tv.setText(data.get(position));

            return convertView;
        }

        private class ViewHolder{
            TextView tv;
        }
    }
}
