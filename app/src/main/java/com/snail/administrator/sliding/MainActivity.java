package com.snail.administrator.sliding;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MyListView.MyCallBack {

    MyListView myListView;
    List<String> datas = new ArrayList<>();//初始化数据
    MyAdapter myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myListView = (MyListView) findViewById(R.id.list_view);
        initData();
        setAdapter();
    }

    /**
     * 设置适配器
     */

    private void setAdapter() {
        myAdapter = new MyAdapter(MainActivity.this) ;
        myListView.setAdapter(myAdapter);
    }

    /**
     * 自定义适配器
     */
    private class MyAdapter extends BaseAdapter {

        Context context;
        LayoutInflater inflater;
        public MyAdapter(Context context) {
            this.context = context;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public Object getItem(int position) {
            return datas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView mTextView;
            convertView =inflater.inflate(R.layout.activity_second,null);
            mTextView = (TextView) convertView.findViewById(R.id.text_view);
            mTextView.setText(datas.get(position));
            mTextView.setTag(position);
            myListView.setTouchView(convertView);
            convertView.setLayoutParams(new LinearLayout
                    .LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,150));
            return convertView;
        }
    }

    private void initData() {
        for (int i = 0; i < 30; i++) {
            datas.add("内容"+i);
        }

    }

    /**
     * 删除的方法
     */
    @Override
    public void deleteData(int position) {
        datas.remove(position);
        myAdapter.notifyDataSetChanged();
    }
}
