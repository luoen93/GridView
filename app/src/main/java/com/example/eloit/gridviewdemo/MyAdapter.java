package com.example.eloit.gridviewdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.List;
import java.util.Map;

/**
 * Created by Eloit on 16/8/16.
 */
public class MyAdapter extends BaseAdapter {


    private Context context;
    private List<Map<String, String>> images;
    private LayoutInflater layoutInflater;

    public MyAdapter(Context context, List<Map<String, String>> images) {
        this.context = context;
        this.images = images;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public final class GalleryItem {
        private ImageView imgs;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object getItem(int position) {
        return images.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        GalleryItem gallery = null;
        if (convertView == null) {
            gallery = new GalleryItem();
            //获取item地址
            convertView = layoutInflater.inflate(R.layout.activity_gallery_item, null);
            gallery.imgs = (ImageView) convertView.findViewById(R.id.image_gallery_title);

            convertView.setTag(gallery);
        } else {
            gallery = (GalleryItem) convertView.getTag();
        }
        //获取图片信息,list<Bitmap>
//        gallery.imgs.setImageBitmap((Bitmap) images.get(position).get("p_origin_image"));

//        gallery.imgs.setBackgroundResource(R.drawable.natoli);

        String img_url = (String) images.get(position).get("path");

        try {
            FileInputStream fis = new FileInputStream(img_url);
            BufferedInputStream bis = new BufferedInputStream(fis);
            Bitmap bitmap = BitmapFactory.decodeStream(bis);
//            Bitmap newBit = Bitmap.createScaledBitmap(bitmap, 85, 85, true);
            gallery.imgs.setImageBitmap(bitmap);


            bis.close();
            fis.close();
        } catch (Exception e) {
            Log.e("ERROR", e.toString());
            gallery.imgs.setImageURI(Uri.parse(img_url));
        }
        return convertView;
    }
}
