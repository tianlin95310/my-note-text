public class MainActivity extends Activity {

    private Button bt_get;
    private Button bt_post;
    private Button bt_clear;

    private TextView tv_result;
    // 1 创建okhttpClicent对象
    OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        initData();
    }

    private void initData() {

        // get请求点击事件
        bt_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDataFromGet();
            }
        });

        // post请求点击事件
        bt_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDataFromPost();
            }
        });

        bt_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_result.setText("");
            }
        });
    }

    private void initView() {
        bt_get = (Button)findViewById(R.id.bt_get);
        bt_post = (Button)findViewById(R.id.bt_post);
        bt_clear = (Button)findViewById(R.id.bt_clear);

        tv_result = (TextView)findViewById(R.id.tv_result);
    }

    /**
     * 使用get请求网络数据
     */
    private void getDataFromGet() {
        new Thread() {
            @Override
            public void run() {
                super.run();

                try {
                    // 请求网络获取数据
                    final String result = get("http://api.m.mtime.cn/PageSubArea/TrailerList.api");

                    // 显示结果
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv_result.setText(result);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private String get(String url) throws IOException {

        Request request = new Request.Builder()
                .url(url)
                .build();

        // 测试添加截获器
//        test_addInterceptor();

        Response response = client.newCall(request).execute();

        return response.body().string();
    }

    // 测试添加截获器
    private void test_addInterceptor() {
        client.newBuilder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder().addHeader("test", "testvalue").build();
                return chain.proceed(request);
            }
        });
    }

    /**
     * 使用post请求网络数据
     */
    private void getDataFromPost() {
        new Thread() {
            @Override
            public void run() {
                super.run();

                try {
                    final String result = post("http://api.m.mtime.cn/PageSubArea/TrailerList.api", "");

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv_result.setText(result);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }


    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    // post请求
    private String post(String url, String json) throws IOException {

        RequestBody body = RequestBody.create(JSON, json);

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Response response = client.newCall(request).execute();

        return response.body().string();
    }

}