package com.example.a67017.myapplication.fragment;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a67017.myapplication.R;
import com.example.a67017.myapplication.tool.FileUtil;
import com.example.a67017.myapplication.tool.FileUtils;
import com.example.a67017.myapplication.tool.MyThreadPool;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment6 extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 1;
    @BindView(R.id.old_button)
    AppCompatButton oldButton;
    @BindView(R.id.old_image)
    ImageView oldImage;
    @BindView(R.id.text_old)
    TextView textOld;
    @BindView(R.id.new_button)
    AppCompatButton newButton;
    @BindView(R.id.new_image)
    ImageView newImage;
    @BindView(R.id.text_new)
    TextView textNew;
    Unbinder unbinder;
    private File file;
    private String path;

    public Fragment6() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment6, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.old_button, R.id.new_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.old_button:
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
                break;
            case R.id.new_button:
                MyThreadPool.getInstance().execute(new Runnable() {
                    @Override
                    public void run() {
                        // 压缩图片
                        propress();
                    }
                });

                break;
            default:
                break;
        }
    }

    private void propress() {
        Bitmap bitmap1;
        Luban.with(getContext())
                .load(file)
                .ignoreBy(30)
                .setTargetDir(getPath())
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                        Log.d("LK", "start!!!!!!!!!");
                    }

                    @Override
                    public void onSuccess(File file) {
                        Toast.makeText(getContext(), "onSuccess!!!!!!!!!", Toast.LENGTH_SHORT).show();
                        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                        newImage.setImageBitmap(bitmap);
                        textNew.setText(String.format("Size : %s", getReadableFileSize(file.length())));
                        FileUtils.deleteDir(path);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("LK", "onError!!!!!!!!!");
                    }
                }).launch();
    }

    private String getPath() {
        path = Environment.getExternalStorageDirectory() + "/aaaa/";
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return path;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
            if (data != null) {
                try {
                    file = FileUtil.getTempFile(getContext(), data.getData());
                    oldImage.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
                    textOld.setText(String.format("Size : %s", getReadableFileSize(file.length())));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String getReadableFileSize(long size) {
        if (size <= 0) {
            return "0";
        }
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }
}
