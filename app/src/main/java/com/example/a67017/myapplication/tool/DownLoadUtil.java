package com.example.a67017.myapplication.tool;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DownLoadUtil {
    private static DownLoadUtil instance;
    private OkHttpClient client;

    public DownLoadUtil() {
        client = new OkHttpClient();
    }

    public static DownLoadUtil getInstance() {
        if (instance == null) {
            synchronized (DownLoadUtil.class) {
                if (instance == null) {
                    instance = new DownLoadUtil();
                }
            }
        }
        return instance;
    }

    public void downLoad(Context context, final String url, final String saveDir, final DownloadListener downloadListener) {
        Request request = new Request
                .Builder()
                .url(url)
//                .header("RANGE", "bytes=" + startPoints + "-")//断点续传要用到的，指示下载的区间
//        Call<ResponseBody> downloadFile(@Url String fileUrl,@Header("Range") String range);
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                onFailed(downloadListener);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = null;
                FileOutputStream os = null;
                byte[] buffer = new byte[2048];
                int len;
                File dir = new File(saveDir);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                try {
                    is = response.body().byteStream();
                    long totalLen = response.body().contentLength();
                    File file = new File(dir, getNameFromUrl(url));
                    if (file.exists()) {
                        file.delete();
                    }
                    os = new FileOutputStream(file);
                    long current = 0;
                    while ((len = is.read(buffer)) != -1) {
                        os.write(buffer, 0, len);
                        current += len;
                        int progress = (int) (current * 1f / totalLen * 100);
                        onLoading(progress, downloadListener);
                        Log.d("LK", progress + "");
                    }
                    os.flush();
                    onSuccess(downloadListener);
                } catch (Exception e) {
                    e.printStackTrace();
                    onFailed(downloadListener);
                } finally {
                    if (is != null) {
                        is.close();
                    }
                    if (os != null) {
                        os.close();
                    }
                }

            }
        });
    }

    public interface DownloadListener {
        /**
         * 下载成功
         */
        void onDownloadSuccess();

        /**
         * @param progress 下载进度
         */
        void onDownloading(int progress);

        /**
         * 下载失败
         */
        void onDownloadFailed();
    }

    public String getNameFromUrl(String url) {
        return url.substring(url.lastIndexOf(File.separator) + 1);
    }

    private void onFailed(final DownloadListener downloadListener) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                downloadListener.onDownloadFailed();
            }
        });
    }

    private void onLoading(final int progress, final DownloadListener downloadListener) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                downloadListener.onDownloading(progress);
            }
        });
    }

    private void onSuccess(final DownloadListener downloadListener) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                downloadListener.onDownloadSuccess();
            }
        });
    }
}
