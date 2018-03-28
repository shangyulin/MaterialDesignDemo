package com.example.cardview;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "SHANGYULIN";

    String[] array = new String[]{"item-1", "item-2", "item-3", "item-4", "item-5", "item-6", "item-7", "item-8", "item-9"};
    private LinearLayout ll;
    private ImageView imageView;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        imageView = findViewById(R.id.image);
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
                /* 改变背景、字体颜色 */
                View snackbarView = snackbar.getView();
                snackbarView.setBackgroundColor(Color.BLUE);
                TextView textView = snackbarView.findViewById(R.id.snackbar_text);
                textView.setTextColor(Color.WHITE);
                /* 添加图片 */
                Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbarView;
                View add = View.inflate(MainActivity.this, R.layout.snackbar_addview, null);

                LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                p.gravity = Gravity.CENTER_VERTICAL;
                layout.addView(add);

                snackbar.setAction("点我啊", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(MainActivity.this, "你点击了action", Toast.LENGTH_SHORT).show();
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
                        Log.d(TAG, "发送1");
                        e.onNext(2);
                        Log.d(TAG, "发送2");
                        e.onNext(3);
                        Log.d(TAG, "发送3");
                        e.onNext(4);
                        Log.d(TAG, "发送4");
                        e.onNext(5);
                        Log.d(TAG, "发送5");
                        e.onComplete();
                    }
                }).map(new Function<Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer) throws Exception {
                        //System.out.println(integer);
                        if (integer > 3) {
                            return integer;
                        } else {
                            return 9;
                        }
                    }
                }).subscribe(new Observer<Integer>() {

                    private Disposable mDisposable;

                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe");
                        mDisposable = d;// 用于解除订阅
                    }

                    @Override
                    public void onNext(Integer value) {
                        if (value == 4) {
                            mDisposable.dispose();
                            Log.d(TAG, "中断");
                        } else {
                            Log.d(TAG, value + "");
                        }

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                    }
                });
            }
        });

        findViewById(R.id.okhttp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OkHttpClient client = new OkHttpClient();
                RequestBody body = new FormBody.Builder().add("name", "liming").build();
                Request request = new Request.Builder().url("http://www.baidu.com").post(body).build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.d(TAG, response.body().string());
                    }
                });
            }
        });

        findViewById(R.id.camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, 0);
                } else {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// 启动系统相机
                    startActivityForResult(intent, 100);
                }
            }
        });
        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bundle != null){
                    Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                    intent.putExtra("image_bundle", bundle);
                    startActivity(intent);
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 100:
                Bundle extras = data.getExtras();
                if (extras != null) {
                    imageView.setVisibility(View.VISIBLE);
                    this.bundle = extras;
                    Bitmap bm = (Bitmap) extras.get("data");
                    if (bm != null) {
                        imageView.setImageBitmap(bm);
                    }
                }
                break;
        }
    }

    /**
     * 请求权限回调
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 0: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// 启动系统相机
                    startActivityForResult(intent, 100);
                } else {
                    Toast.makeText(MainActivity.this, "你拒绝了", Toast.LENGTH_LONG).show();
                }
                break;
            }
        }
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
            if (convertView == null) {
                view = View.inflate(MainActivity.this, R.layout.item_list, null);
            } else {
                view = convertView;
            }
            TextView content = view.findViewById(R.id.content);
            content.setText(array[i]);
            return view;
        }
    }
}
