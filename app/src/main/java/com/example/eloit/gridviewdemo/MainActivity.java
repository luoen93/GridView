package com.example.eloit.gridviewdemo;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    // 创建LinearLayout对象
    LinearLayout mLinearLayout = new LinearLayout(this);

    private GridView MyGridView;

    private MyAdapter imageAdapter;
    //    private Cursor cr;
    private ContentResolver cr;
    private int[] thumbIds;
    private Context context;
    private ContentResolver ctr;

    List<Map<String, String>> mlist = new ArrayList<Map<String, String>>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // 建立布局样式宽和高，对应xml布局中：
        // android:layout_width="fill_parent"
        // android:layout_height="fill_parent"
        mLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));

        // 设置方向，对应xml布局中：
        // android:orientation="vertical"
        mLinearLayout.setOrientation(LinearLayout.VERTICAL);

        // setContentView(R.layout.activity_main);
        setContentView(mLinearLayout);

        context = this;
        init();
        mlist = showThumbnails();
        imageAdapter = new MyAdapter(context, mlist);
        MyGridView.setAdapter(imageAdapter);
        ClickListener();
    }

    private void ClickListener() {
        MyGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Toast.makeText(getApplicationContext(), "点击了" + position + (String) mlist.get(position).get("pname"), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void init() {

        TextView MytextView = new TextView(this);
        LinearLayout.LayoutParams Text_layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);


//        MyGridView = (GridView) findViewById(R.id.gridview_1);
        MyGridView = new GridView(this);

        //android:layout_width="wrap_content"
        //android:layout_height="wrap_content"
        LinearLayout.LayoutParams Match_layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        mLinearLayout.addView(MytextView, Text_layoutParams);
        //添加进入父类布局,并加入自身样式
        mLinearLayout.addView(MyGridView, Match_layoutParams);


    }

    private List<Map<String, String>> showThumbnails() {

        List<Map<String, String>> aibumList = new ArrayList<Map<String, String>>();

        cr = getContentResolver();
        String[] imagedata = {MediaStore.Images.Thumbnails._ID, MediaStore.Images.Thumbnails.IMAGE_ID, MediaStore.Images.Thumbnails.DATA};//找到image
        Cursor cursor = cr.query(MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI, imagedata, null, null, null);


        if (cursor.moveToFirst()) {
            int _id;
            int image_id;
            String image_path;
            int _idColumn = cursor.getColumnIndex(MediaStore.Images.Thumbnails._ID);
            int image_idColumn = cursor.getColumnIndex(MediaStore.Images.Thumbnails.IMAGE_ID);
            int dataColumn = cursor.getColumnIndex(MediaStore.Images.Thumbnails.DATA);

            Log.i("========", String.valueOf(_idColumn));

            do {
                //Get the field values
                _id = cursor.getInt(_idColumn);
                image_id = cursor.getInt(image_idColumn);
                image_path = cursor.getString(dataColumn);


                //Do something with the values.
                //Log.i(TAG, _id + " image_id:" + image_id + " path:"
                //+ image_path + "---");
                HashMap<String, String> hash = new HashMap<String, String>();
                hash.put("image_id", image_id + "");
                hash.put("path", image_path);
                aibumList.add(hash);


            }
            while (cursor.moveToNext());


        }


        return aibumList;
    }
}
