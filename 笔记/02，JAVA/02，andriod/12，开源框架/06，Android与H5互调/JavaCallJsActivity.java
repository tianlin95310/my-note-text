package test1.example.administrator.androidandh5;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by tianlin on 2017/2/11.
 * Tel : 15071485690
 * QQ 953108373
 * Function :
 */
public class JavaCallJsActivity extends AppCompatActivity
{

    WebView webView;
    @BindView(R.id.et_number)
    EditText etNumber;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java_and_js);

        ButterKnife.bind(this);

        initWebView();
    }

    private void initWebView()
    {
        webView = new WebView(this);

        // 获取网页信息
        WebSettings setting = webView.getSettings();

        // 启用JS
        setting.setJavaScriptEnabled(true);
        // 双击放大
        setting.setUseWideViewPort(true);
        // 缩放控件
        setting.setBuiltInZoomControls(true);

        // 设置自己的客户端
        webView.setWebViewClient(new WebViewClient());

        // 设置android调用js的接口
        webView.addJavascriptInterface(new MyJsInterface(), "login");

        // 加载本地资源
//        webView.loadUrl("file:///android_asset/JavaAndJavaScriptCall.html");

        // 加载网络资源
        webView.loadUrl("http://10.208.60.38:8080/007_adnroidandh5/JavaAndJavaScriptCall.html");
    }

    // js调用android的接口
    class MyJsInterface
    {
        @JavascriptInterface
        public void callAndroid()
        {
            Toast.makeText(JavaCallJsActivity.this, "android 被 js 调用", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.btn_login)
    public void onClick()
    {
        String number = etNumber.getText().toString();
        String password = etPassword.getText().toString();

        if(number != null && number.equals(password))
        {
            login(number);
        }
        else
            Toast.makeText(this, "错误", Toast.LENGTH_SHORT).show();

    }

    private void login(String number)
    {
        // android调用js，相当于javascript:javaCallJs('adsasd')
        // 这个是一个url，在jsp里经常出现
        webView.loadUrl("javascript:javaCallJs(" + "'" + number + "'" + ")");

        // 设置内容
        setContentView(webView);
    }
}
