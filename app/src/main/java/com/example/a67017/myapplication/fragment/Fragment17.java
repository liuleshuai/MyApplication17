package com.example.a67017.myapplication.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.a67017.myapplication.R;
import com.example.a67017.myapplication.bean.MovieEntity;
import com.example.a67017.myapplication.bean.TestBean;
import com.example.a67017.myapplication.bean.WeatherEntity;
import com.example.a67017.myapplication.retrofit.RetrofitLoader;
import com.example.a67017.myapplication.retrofit.RetrofitUrl;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment17#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment17 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.tv)
    TextView tv;
    Unbinder unbinder;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Disposable mDisposable;


    public Fragment17() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment17.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment17 newInstance(String param1, String param2) {
        Fragment17 fragment = new Fragment17();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment17, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        getMovie();
//        getWeather();
//        getInfor();
    }

    @Override
    public void onStart() {
        super.onStart();
        getInfor();
    }

    private void getMovie() {
        RetrofitLoader movieLoader = new RetrofitLoader(RetrofitUrl.BASE_URL);
        movieLoader.getMovie(0, 10).subscribe(new Consumer<List<MovieEntity.SubjectsBean>>() {
            @Override
            public void accept(@NonNull List<MovieEntity.SubjectsBean> subjectsBeen) throws Exception {
                String s = JSON.toJSONString(subjectsBeen);
                tv.setText(s);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                Log.e("TAG", "error message:" + throwable.getMessage());
            }
        }, new Action() {
            @Override
            public void run() throws Exception {

            }
        });
    }

    public void getWeather() {
        RetrofitLoader weatherLoader = new RetrofitLoader(RetrofitUrl.BASE_WEATHER_URL);
        Disposable disposable = weatherLoader.getHeWeather("beijing", "ec56e0cdd08843adacaef248e196855a").subscribe(
                new Consumer<WeatherEntity>() {
                    @Override
                    public void accept(@NonNull WeatherEntity weatherEntity) throws Exception {
                        String s = JSON.toJSONString(weatherEntity);
                        tv.setText(s);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        Log.e("TAG", "error message:" + throwable.getMessage());
                    }
                });
    }

    public void getInfor() {
        RetrofitLoader inforLoader = new RetrofitLoader(RetrofitUrl.BASE_TEST);
        inforLoader.getInfor().subscribe(new Observer<TestBean>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(TestBean testBean) {
                String s = JSON.toJSONString(testBean);
                tv.setText(s);
            }

            @Override
            public void onError(Throwable e) {
                Log.e("TAG", "error message:" + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.e("TAG", "complete!");
            }
        });
/*        new Consumer<TestBean>() {
            @Override
            public void accept(@NonNull TestBean testBean) throws Exception {
                String s = JSON.toJSONString(testBean);
                tv.setText(s);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                Log.e("TAG", "error message:" + throwable.getMessage());
            }
        });*/

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }
}
