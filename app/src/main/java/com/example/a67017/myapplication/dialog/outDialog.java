package com.example.a67017.myapplication.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a67017.myapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by LiuKuo at 2018/6/8
 *
 * @author liukuo
 */

public class OutDialog extends Dialog {
    @BindView(R.id.out_cancel)
    TextView outCancel;
    @BindView(R.id.out_sure)
    TextView outSure;

    public OutDialog(Context context) {
        this(context, 0);
    }

    public OutDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.out_dialog);
        ButterKnife.bind(this);
//        WindowManager.LayoutParams lp = getWindow().getAttributes();
//        // 没必要这么写，Dialog弹出会获取焦点，我发进行其它操作。
//        // 显示的是整个布局，包括背景填充部分
//        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
//        lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
//        getWindow().setAttributes(lp);
//        getWindow().setGravity(Gravity.CENTER);
    }

    @OnClick({R.id.out_cancel, R.id.out_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.out_cancel:
                dismiss();
                break;
            case R.id.out_sure:
                Toast.makeText(this.getContext(),"退出啦！",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}
