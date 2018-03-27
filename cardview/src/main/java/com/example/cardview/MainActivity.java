package com.example.cardview;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class MainActivity extends AppCompatActivity {

    String[] array = new String[]{"item-1","item-2","item-3","item-4","item-5","item-6","item-7","item-8","item-9"};
    private LinearLayout ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        // 检查运行时权限
//        if(ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
//            // 若没有权限，则动态申请
//            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
//        }

        ll = findViewById(R.id.ll);
        findViewById(R.id.show).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetDialog dialog = new BottomSheetDialog(MainActivity.this);

                View v = View.inflate(MainActivity.this, R.layout.listview, null);
                ListView listView = v.findViewById(R.id.lv);
                listView.setAdapter(new MyAdapter());
                dialog.setContentView(v);
                dialog.show();
            }
        });
        findViewById(getResources().getIdentifier("snackbar", "id", getPackageName())).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar snackbar = Snackbar.make(ll, "hello world", Snackbar.LENGTH_LONG);

                View snackbarView = snackbar.getView();
                snackbarView.setBackgroundColor(Color.BLUE);
                TextView textView = snackbarView.findViewById(R.id.snackbar_text);
                textView.setTextColor(Color.WHITE);

                Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbarView;
                View add = View.inflate(MainActivity.this, R.layout.snackbar_addview, null);

                LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                p.gravity= Gravity.CENTER_VERTICAL;//设置新建布局在Snackbar内垂直居中显示
                layout.addView(add);

                snackbar.setAction("点我啊", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(MainActivity.this,"你点击了action",Toast.LENGTH_SHORT).show();
                    }
                }).show();
            }
        });
        findViewById(R.id.rxjava).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Observable.create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                        e.onNext(1);
                        System.out.println("发送1");
                        e.onNext(2);
                        System.out.println("发送2");
                        e.onNext(3);
                        System.out.println("发送3");
                        e.onNext(4);
                        System.out.println("发送4");
                        e.onNext(5);
                        System.out.println("发送5");
                        e.onComplete();
                    }
                }).map(new Function<Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer) throws Exception {
                        //System.out.println(integer);
                        if(integer > 3){
                            return integer;
                        }else{
                            return 9;
                        }
                    }
                }).subscribe(new Observer<Integer>() {

                    private Disposable mDisposable;

                    @Override
                    public void onSubscribe(Disposable d) {
                        System.out.println("onSubscribe");
                        mDisposable = d;// 用于解除订阅
                    }

                    @Override
                    public void onNext(Integer value) {
                        System.out.println("=====" + value);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        System.out.println("onComplete");
                    }
                });
            }
        });
    }

    /**
     * 请求权限回调
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return array.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {
            View view;
            if(convertView == null){
                view = View.inflate(MainActivity.this, R.layout.item_list, null);
            }else{
                view = convertView;
            }
            TextView content = view.findViewById(R.id.content);
            content.setText(array[i]);
            return view;
        }
    }
}
