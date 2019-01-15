package test1.example.administrator.androidandh5;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity
{

    @BindView(R.id.btn_java_and_js)
    Button btnJavaAndJs;
    @BindView(R.id.btn_js_call_java)
    Button btnJsCallJava;
    @BindView(R.id.btn_js_call_phone)
    Button btnJsCallPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_java_and_js, R.id.btn_js_call_java, R.id.btn_js_call_phone})
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.btn_java_and_js:
            {
                Intent intent = new Intent(this, JavaCallJsActivity.class);
                startActivity(intent);
            }
                break;
            case R.id.btn_js_call_java:
            {
                Intent intent = new Intent(this, JavaScriptCallAndroidActivityVideo.class);
                startActivity(intent);
            }
                break;
            case R.id.btn_js_call_phone:
                break;
        }
    }
}
