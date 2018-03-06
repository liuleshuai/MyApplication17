package com.example.a67017.myapplication.tool;

import android.os.AsyncTask;
import android.support.annotation.Nullable;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by LiuKuo at 2018/3/1
 */

public class HttpClientHelper {
    private static Map<String, Object> mData;
    private static HttpResult mCallBack;
    private static boolean httpMode = false;

    /**
     * @param url
     * @param mode     true: post;  false:get; 默认get
     * @param data
     * @param callBack
     */
    public static synchronized void execute(String url, @Nullable boolean mode, @Nullable Map<String, Object> data, HttpResult callBack) {
        if (data == null) {
            mData = new HashMap<>();
        } else {
            mData = data;
        }
        httpMode = mode;
        mCallBack = callBack;
        new MyAsyncTask().execute(url);
    }

    static class MyAsyncTask extends AsyncTask<String, Integer, String> {


        @Override
        protected String doInBackground(String... params) {
            String bodyContent = null;
            try {
                URL url = new URL(params[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                // 设置字符集
                conn.setRequestProperty("Charset", "UTF-8");
                //禁用网络缓存
                conn.setUseCaches(false);
                // 设置文件类型(一定要设置 Content-Type 要不然服务端接收不到参数)
                conn.setRequestProperty("Content-Type", "text/xml; charset=UTF-8");
                if (!httpMode) {
                    getConfig(conn);
                } else {
                    postConfig(conn);
                }


                if (conn.getResponseCode() == 200) {
                    //调用getInputStream方法后，服务端才会收到请求，并阻塞式地接收服务端返回的数据
                    InputStream is = conn.getInputStream();
                    //将InputStream转换成byte数组,getBytesByInputStream会关闭输入流
                    byte[] receive = getBytesByInputStream(is);
                    bodyContent = new String(receive, "UTF-8");
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bodyContent;
        }

        @Override
        protected void onPostExecute(String data) {
            super.onPostExecute(data);
            if (data != null) {
                mCallBack.success(data);
            } else {
                mCallBack.failed();
            }
        }
    }

    private static void getConfig(HttpURLConnection conn) throws ProtocolException {
        // 设置请求方法，默认是GET
        conn.setRequestMethod("GET");
    }

    private static void postConfig(HttpURLConnection conn) throws IOException {
        conn.setRequestMethod("POST");
        OutputStream os = conn.getOutputStream();
        StringBuffer requestContent = new StringBuffer();
        for (Map.Entry<String, Object> entry : mData.entrySet()) {
            requestContent.append(entry.getKey() + "=" + entry.getValue() + "&");
        }
        os.write(requestContent.toString().getBytes("UTF-8"));
        os.close();
    }

    /**
     * 从InputStream中读取数据，转换成byte数组，最后关闭InputStream
     */
    private static byte[] getBytesByInputStream(InputStream is) {
        byte[] bytes = null;
        BufferedInputStream bis = new BufferedInputStream(is);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BufferedOutputStream bos = new BufferedOutputStream(baos);
        byte[] buffer = new byte[1024 * 8];
        int length = 0;
        try {
            while ((length = bis.read(buffer)) != -1) {
                bos.write(buffer, 0, length);
            }
            bos.flush();
            bytes = baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                bis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return bytes;
    }

    public interface HttpResult {
        void success(String data);

        void failed();
    }
}
