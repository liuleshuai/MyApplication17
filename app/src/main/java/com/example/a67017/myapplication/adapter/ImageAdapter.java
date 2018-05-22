package com.example.a67017.myapplication.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.support.v4.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.a67017.myapplication.R;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by LiuKuo at 2018/4/4
 */

public class ImageAdapter extends ArrayAdapter<String> {

    /**
     * 图片缓存技术的核心类，用于缓存所有下载好的图片，在程序内存达到设定值时会将最少最近使用的图片移除掉。
     */
    private LruCache<String, BitmapDrawable> mMemoryCache;
    private String[] urls;

    public ImageAdapter(Context context, int resource, String[] objects) {
        super(context, resource, objects);
        // 获取应用程序最大可用内存
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory / 8;
        mMemoryCache = new LruCache<String, BitmapDrawable>(cacheSize) {
            @Override
            protected int sizeOf(String key, BitmapDrawable drawable) {
                return drawable.getBitmap().getByteCount();
            }
        };
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String url = getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity17_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        BitmapDrawable drawable = getBitmapFromMemoryCache(url);
        if (drawable != null) {
            viewHolder.iv.setImageDrawable(drawable);
        } else {
            viewHolder.iv.setTag(url);
            BitmapWorkerTask task = new BitmapWorkerTask(viewHolder.iv);
            task.execute(url);
        }
        return convertView;
    }

    private static class ViewHolder {
        private ImageView iv;

        public ViewHolder(View view) {
            this.iv = view.findViewById(R.id.iv);
        }
    }

    /**
     * 将一张图片存储到LruCache中。
     *
     * @param key      LruCache的键，这里传入图片的URL地址。
     * @param drawable LruCache的值，这里传入从网络上下载的BitmapDrawable对象。
     */

    public void addBitmapToMemoryCache(String key, BitmapDrawable drawable) {
        if (getBitmapFromMemoryCache(key) == null) {
            mMemoryCache.put(key, drawable);
        }
    }

    /**
     * 从LruCache中获取一张图片，如果不存在就返回null。
     *
     * @param key LruCache的键，这里传入图片的URL地址。
     * @return 对应传入键的BitmapDrawable对象，或者null。
     */
    public BitmapDrawable getBitmapFromMemoryCache(String key) {
        return mMemoryCache.get(key);
    }

    /**
     * 异步下载图片的任务。
     *
     * @author guolin
     */
    class BitmapWorkerTask extends AsyncTask<String, Void, BitmapDrawable> {

        private ImageView mImageView;
        private String imageUrl;

        public BitmapWorkerTask(ImageView imageView) {
            mImageView = imageView;
        }

        @Override
        protected BitmapDrawable doInBackground(String... params) {
            imageUrl = params[0];
            // 在后台开始下载图片
            Bitmap bitmap = downloadBitmap(imageUrl);
            BitmapDrawable drawable = new BitmapDrawable(getContext().getResources(), bitmap);
            addBitmapToMemoryCache(imageUrl, drawable);
            return drawable;
        }

        @Override
        protected void onPostExecute(BitmapDrawable drawable) {
            if (mImageView != null && drawable != null
                    && mImageView.getTag() != null && mImageView.getTag().equals(imageUrl)) {
                mImageView.setImageDrawable(drawable);
            }
        }

        /**
         * 建立HTTP请求，并获取Bitmap对象。
         *
         * @param imageUrl 图片的URL地址
         * @return 解析后的Bitmap对象
         */
        private Bitmap downloadBitmap(String imageUrl) {
            Bitmap bitmap = null;
            HttpURLConnection con = null;
            try {
                URL url = new URL(imageUrl);
                con = (HttpURLConnection) url.openConnection();
                con.setConnectTimeout(5 * 1000);
                con.setReadTimeout(10 * 1000);
                bitmap = BitmapFactory.decodeStream(con.getInputStream());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (con != null) {
                    con.disconnect();
                }
            }
            return bitmap;
        }

    }

}
