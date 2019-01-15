package test1.example.administrator.myswipelayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Scroller;

/**
 * Created by tianlin on 2017/1/17.
 * Tel : 15071485690
 * QQ 953108373
 * Function :
 */

public class TLSlideMenu extends FrameLayout
{
    /**
     * item内容
     */
    View m_content;
    /**
     * 菜单
     */
    View m_menu;

    /**
     * 内容的宽度
     */
    int m_content_width;
    /**
     * 菜单的宽度
     */
    int m_menu_width;
    /**
     * item的高度
     */
    int m_item_height;

    /**
     * 系统自动滑动的工具
     */
    Scroller scroller;

    /**
     * 水平滑动的起始位置
     */
    float startX;

    /**
     * 用于判断是水平还是竖直滑动
     */
    float flagX;
    float flagY;

    public TLSlideMenu(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        scroller = new Scroller(context);
    }

    @Override
    protected void onFinishInflate()
    {
        /**
         * 得到内容和菜单的View，他们可以是ViewGroup，但是只能是两个
         */
        int childCount = getChildCount();
        if(childCount != 2)
        {
            throw new RuntimeException("孩子个数不支持！");
        }
        m_content = getChildAt(0);
        m_menu = getChildAt(1);

        super.onFinishInflate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        /**
         * 保存自己的测量值和测量孩子
         */
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        /**
         * item, content, menu的高度一致
         */
        m_content_width = m_content.getMeasuredWidth();
        m_menu_width = m_menu.getMeasuredWidth();
        m_item_height = getMeasuredHeight();

    }

    /**
     * 菜单打开或关闭时的回调
     */
    interface OnStatusChangedListener
    {
        /**
         * 菜单打开时
         * @param openMenu 当前打开的item菜单
         */
        void onOpen(TLSlideMenu openMenu);

        /**
         * 菜单关闭时
         * @param closeMenu 当前关闭的item菜单
         */
        void onClose(TLSlideMenu closeMenu);

        /**
         * 当item菜单打开后，点击其他的item时调用
         * @param openedMenu 当前点击的item菜单
         */
        void onDown(TLSlideMenu openedMenu);
    }

    /**
     * 监听器,可选项
     */
    OnStatusChangedListener onStatusChangedListener;

    public void setOnStatusChangedListener(OnStatusChangedListener onStatusChangedListener)
    {
        this.onStatusChangedListener = onStatusChangedListener;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom)
    {
        /**
         * 指定孩子的位置
         */
        super.onLayout(changed, left, top, right, bottom);

        /**
         * 指定菜单的布局，让他在内容的右边，如果不指定的话，Fragment默认是重叠的
         */
        m_menu.layout(m_content_width, 0, m_content_width + m_menu_width, m_item_height);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev)
    {
        boolean isIntercept = false;

        switch (ev.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                /**
                 *正常情况下孩子会优先处理事件，所以本类的onTouchEvent不会接收到DOWN事件
                 * 即使后来拦截了，也只有部分MOVE事件和UP事件，但是事件的分发肯定会经过onInterceptTouchEvent
                 * 方法，所以startX最好的获取位置就是在这里，为了与后面的MOVE相对接
                 */
                startX = ev.getX();

                /**
                 * 只要手指点击的不是当前打开的item，我们就把他关闭，如果点击的是当前的，我们就不关闭
                 * 因为onTouchEvent里面，不能接受到DOWN事件，所以我们需要写在这里
                 */
                if(onStatusChangedListener != null)
                {
                    onStatusChangedListener.onDown(this);
                }

                break;

            case MotionEvent.ACTION_MOVE:
                /**
                 * 水平滑动的距离大于5，我们就认为他item发生了左移，就拦截事件
                 */
                if(Math.abs(ev.getX() - startX) > 5)
                {
                    isIntercept = true;
                }
                break;
        }

        return isIntercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {

        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                startX = flagX = event.getX();
                flagY = event.getY();
                break;

            case MotionEvent.ACTION_MOVE:

                /**
                 * 获取MOVE中的偏移量
                 */
                float dx = event.getX() - startX;

                /**
                 * 获取水平和竖直的位移量
                 */
                float d_horizontal = event.getX() - flagX;
                float d_vertical = event.getY() - flagY;

                /**
                 * 如果某次滑动过程中，水平距离大于竖直距离，此时我们应该让RecyclerView的滑动失效
                 * 否则，可能会造成水平与竖直都有一定的偏移量，并且划出的部分不好控制。应为他丢失了一部分事件
                 * 给了RecyclerView列表（列表的特性是竖直只要有移动，他就会拦截事件）
                 * 导致UP事件的处理不会执行，所以无法回弹
                 */
                if(Math.abs(d_horizontal) > Math.abs(d_vertical) && Math.abs(d_horizontal) > 5)
                    getParent().requestDisallowInterceptTouchEvent(true);
                /**
                 * 获取内部内容的平移量，getScrollX是当前的布局的内容item的偏移量
                 * 左滑为正，右滑为负
                 */
                int aimX = (int) (getScrollX() - dx);

                /**
                 * 边界控制
                 */
                if(aimX < 0)
                    aimX = 0;
                if(aimX > m_menu_width)
                    aimX = m_menu_width;

                /**
                 * 竖直方向，我们不移动内容
                 */
                scrollTo(aimX, 0);

                /**
                 * 更新起点位置
                 */
                startX = event.getX();
                break;

            case MotionEvent.ACTION_UP:

                if(getScrollX() > m_menu_width / 2)
                {
                    openMenu();
                }
                else
                {
                    closeMenu();
                }
                break;
        }
        return true;
    }

    /**
     * 关闭菜单
     */
    public void closeMenu()
    {
        /**
         * 向左滑动的距离为0，就是默认的位置
         */
        int dx = 0 - getScrollX();
        scroller.startScroll(getScrollX(), getScrollY(), dx, 0);
        invalidate();

        if(onStatusChangedListener != null)
            onStatusChangedListener.onClose(this);

    }

    /**
     * 打开菜单
     */
    public void openMenu()
    {
        /**
         * 向左滑动的距离为menu的宽度
         */
        int dx = m_menu_width - getScrollX();
        scroller.startScroll(getScrollX(), getScrollY(), dx, 0);
        invalidate();

        if(onStatusChangedListener != null)
            onStatusChangedListener.onOpen(this);
    }

    /**
     * 自动移动
     */
    @Override
    public void computeScroll()
    {
        if(scroller.computeScrollOffset())
        {
            scrollTo(scroller.getCurrX(), 0);
            invalidate();
        }
        super.computeScroll();
    }
}
