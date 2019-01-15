package test1.example.administrator.myswipelayout;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by tianlin on 2017/1/17.
 * Tel : 15071485690
 * QQ 953108373
 * Function :
 */

public class TlRecyclerAdapter extends RecyclerView.Adapter<TlRecyclerAdapter.MyViewHolder>

{
    /**
     * 当前打开的菜单项
     */
    TLSlideMenu currentMenu;

    List<String> list;

    Context context;

    int currentPosition = -1;

    public TlRecyclerAdapter(Context context, List<String> list)
    {
        this.context = context;
        this.list = list;
    }

    public void deleteData(int position)
    {
        list.remove(position);
        notifyItemRemoved(position);
    }

    interface OnItemOperateListener
    {
        /**
         * 点击某项item
         *
         * @param position
         */
        void onItemClick(int position);

        /**
         * 点击删除菜单
         * @param position
         */
        void onDelete(int position);

        /**
         * 关闭菜单
         * @param position
         */
        void closeMenu(int position);

        /**
         * 打开菜单
         * @param position
         */
        void openMenu(int position);


    }

    OnItemOperateListener onItemOperateListener;

    public void setOnItemOperateListener(OnItemOperateListener onItemOperateListener)
    {
        this.onItemOperateListener = onItemOperateListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        /**
         * view是一个一个往parent上加的，每加一个就会调用该方法一次,ViewGroup总共只有一部分孩子
         */
        Log.d("my", "count " + parent.getChildCount());

        View view = LayoutInflater.from(context).inflate(R.layout.item_view, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        /**
         * 用于自动的关闭已经打开的item
         */
        TLSlideMenu tlSlideMenu = (TLSlideMenu) view;
        tlSlideMenu.setOnStatusChangedListener(myViewHolder);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {
        /**
         * 这个与ListView的最大的区别是，在ListView的该函数中（getView），能够得到item的View，但在这里不能得到
         * 在ListView中可以很轻易的对整个Item的View进行操作，但在RecyclerView的该函数里只能对item的孩子进行操作
         *
         */
        holder.tvContent.setText(list.get(position));

        /**
         * 屏幕滑动时要想关闭之前显示的，只能通过回调让RecycleView来得到孩子关闭，因为我们的案例是通过对整个
         * item进行操作实现菜单的打开和关闭
         */
        if(onItemOperateListener != null)
        {
            if (position == currentPosition)
            {
                onItemOperateListener.openMenu(position);
            }
            else
            {
                onItemOperateListener.closeMenu(position);
            }
        }

    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder implements TLSlideMenu.OnStatusChangedListener
    {
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.rl_content)
        RelativeLayout rlContent;
        @BindView(R.id.tv_menu_delete)
        TextView tvMenuDelete;

        @Override
        public void onOpen(TLSlideMenu openMenu)
        {
            currentMenu = openMenu;

            /**
             * 获取当前行的位置，这个位置信息被保存在ViewHolder里，每次bind时，该位置信息都会得到更新
             * 他跟当前的item是绑定的，并且ViewHolder与item的数量是相同的，点击不同的item，响应的ViewHolder会得到不同的
             * 位置信息
             */
            currentPosition = getLayoutPosition();
        }

        @Override
        public void onClose(TLSlideMenu closeMenu)
        {
            if(closeMenu == currentMenu)
            {
                currentMenu = null;

                /**
                 * 还原当前行的位置
                 */
                currentPosition = -1;
            }
        }

        @Override
        public void onDown(TLSlideMenu openedMenu)
        {
            if(currentMenu != null && openedMenu != currentMenu)
            {
                currentMenu.closeMenu();
            }
        }

        public MyViewHolder(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }



        @OnClick({R.id.rl_content, R.id.tv_menu_delete})
        public void onClick(View view)
        {
            switch (view.getId())
            {
                case R.id.rl_content:
                    if(onItemOperateListener != null)
                        onItemOperateListener.onItemClick(getLayoutPosition());
                    break;
                case R.id.tv_menu_delete:
                    if(onItemOperateListener != null)
                        onItemOperateListener.onDelete(getLayoutPosition());
                    break;
            }
        }
    }
}
