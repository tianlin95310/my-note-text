package test1.example.administrator.androidandh5;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

/**
 * Created by tianlin on 2017/2/12.
 * Tel : 15071485690
 * QQ 953108373
 * Function :
 */
public class JavaScriptCallAndroidActivityVideo extends AppCompatActivity
{

    WebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_js_call_java_video);

        webView = (WebView) findViewById(R.id.webview);
        initWebView();

    }

    private void initWebView()
    {
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
        webView.addJavascriptInterface(new JavaScriptCallAndroidActivityVideo.MyJsInterface(), "android");

        // 加载本地资源
//        webView.loadUrl("file:///android_asset/JavaAndJavaScriptCall.html");

        // 加载网络资源
        webView.loadUrl("http://10.208.60.38:8080/007_adnroidandh5/RealNetJSCallJavaActivity.htm");
    }

    // js调用android的接口
    class MyJsInterface
    {
        @JavascriptInterface
        public void playVideo(int id, String url, String title)
        {
//            // 测试发现对于网络路径，uri和url是一样的
            Toast.makeText(JavaScriptCallAndroidActivityVideo.this, "url" + url, Toast.LENGTH_SHORT).show();
            Toast.makeText(JavaScriptCallAndroidActivityVideo.this, "uri" + Uri.parse(url).toString(), Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent();
//            intent.setDataAndType(Uri.parse(url), "video/*");
//
//            PackageManager pm = getPackageManager();
//            List<ResolveInfo> list = pm.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
//
//            Toast.makeText(JavaScriptCallAndroidActivityVideo.this, "list" + list.get(0).activityInfo.packageName, Toast.LENGTH_SHORT).show();
//
//            Log.d("my", "list" + list.get(0).activityInfo.packageName);
//            startActivity(intent);


            Intent intent = new Intent();

            intent.setAction(Intent.ACTION_CALL);

            intent.setData(Uri.parse(url));

            startActivity(intent);
        }
    }


}
