package com.example.treestructuredemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.treestructuredemo.treebean.FileBean;
import com.example.treestructuredemo.treebean.InfoBean;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<FileBean> array=new ArrayList<>();
    private ListView listView;
    private FileListAdapter  adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView=(ListView)findViewById(R.id.listView);
        initData();
    }
    public void initData(){
        array.add(new FileBean("1","0",new InfoBean("文件夹","1","0","0")));
        array.add(new FileBean("2","0",new InfoBean("文件夹1","2","0","0")));
        array.add(new FileBean("3","1",new InfoBean("文件夹2","3","1","0")));
        array.add(new FileBean("4","3",new InfoBean("文件夹3","4","3","0")));
        array.add(new FileBean("5","3",new InfoBean("文件","5","3","1")));
        array.add(new FileBean("6","4",new InfoBean("文件夹","6","4","0")));
        array.add(new FileBean("7","4",new InfoBean("文件夹8","7","4","0")));
        array.add(new FileBean("8","6",new InfoBean("文件夹9","8","6","0")));
        array.add(new FileBean("9","8",new InfoBean("文件.png","9","8","1")));

        try {
            adapter=new FileListAdapter(listView,this,array,1);
            listView.setAdapter(adapter);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }
}
