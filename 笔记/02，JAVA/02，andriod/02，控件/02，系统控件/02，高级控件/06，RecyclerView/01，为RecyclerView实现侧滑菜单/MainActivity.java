package test1.example.administrator.myswipelayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        TlRecyclerAdapter.OnItemOperateListener
{
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.rv);
        initView();
    }

    @Override
    public void onItemClick(int position)
    {
        Log.d("my", "onItemClick position" + position);
    }

    @Override
    public void onDelete(int position)
    {
        Log.d("my", "onDelete position" + position);
        TlRecyclerAdapter adapter = (TlRecyclerAdapter) recyclerView.getAdapter();
        adapter.deleteData(position);

        /**
         * 获取当前的item,因为RecyclerView的孩子总个数只有一屏幕能容纳的数量，往后的都是复用的，所以只能
         * 取模运算
         */
        if(recyclerView.getChildCount() != 0)
        {
            TLSlideMenu menu = (TLSlideMenu) recyclerView.getChildAt(position % recyclerView.getChildCount());
            if (menu != null)
                menu.closeMenu();
        }
    }

    @Override
    public void closeMenu(int position)
    {
        if(recyclerView.getChildCount() != 0)
        {
            int childPosition = position % recyclerView.getChildCount();

            TLSlideMenu tlSlideMenu = (TLSlideMenu) recyclerView.getChildAt(childPosition);

            if(tlSlideMenu != null)
                tlSlideMenu.closeMenu();
        }
    }

    @Override
    public void openMenu(int position)
    {
        if(recyclerView.getChildCount() != 0)
        {
            int childPosition = position % recyclerView.getChildCount();

            TLSlideMenu tlSlideMenu = (TLSlideMenu) recyclerView.getChildAt(childPosition);

            if(tlSlideMenu != null)
                tlSlideMenu.openMenu();
        }
    }

    private void initView()
    {
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        List<String> list = new ArrayList<>();
        for(int i = 0; i < 50; i++)
        {
            list.add("content " + i);
        }

        TlRecyclerAdapter adapter = new TlRecyclerAdapter(this, list);
        /**
         * 设置点击监听
         */
        adapter.setOnItemOperateListener(this);

        recyclerView.setAdapter(adapter);


    }
}
