package swu.xl.bottomview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.List;

public class XLBottomView extends RelativeLayout {
    //日志
    public static final String TAG = "XLBottomView";
    //正常状态的颜色
    private int normal_color = Color.BLACK;
    //选中状态的颜色
    private int select_color = Color.MAGENTA;
    //两端是否给间距
    private boolean hasLeftOrRightSize = true;
    //item的大小
    private int item_size = 50;
    //item的布局
    private int item_layout;
    //按钮选中状态被点击是否响应事件
    private boolean isSelectClick = false;

    //上一个被选中的
    private MyItem lastItem;

    //监听者
    private XLBottomViewItemListener listener;

    //数据源
    private List<BottomViewItem> items;

    /**
     * 构造方法 Java代码初始化
     * @param context
     */
    public XLBottomView(Context context, int normal_color, int select_color, boolean hasLeftOrRightSize, int item_size, int item_layout) {
        super(context);

        this.normal_color = normal_color;
        this.select_color = select_color;
        this.hasLeftOrRightSize = hasLeftOrRightSize;
        this.item_size = item_size;
        this.item_layout = item_layout;

        //初始化操作
        init();
    }

    /**
     * 构造方法 Xml代码初始化
     * @param context
     * @param attrs
     */
    public XLBottomView(Context context, AttributeSet attrs) {
        super(context, attrs);

        //解析
        if (attrs != null){
            //1.获得所有属性值的集合
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.XLBottomView);
            //2.解析属性
            normal_color = typedArray.getColor(R.styleable.XLBottomView_normal_color,normal_color);
            select_color = typedArray.getColor(R.styleable.XLBottomView_select_color,select_color);
            hasLeftOrRightSize = typedArray.getBoolean(R.styleable.XLBottomView_hasLeftOrRightSize,hasLeftOrRightSize);
            item_size = typedArray.getInteger(R.styleable.XLBottomView_item_size,item_size);
            isSelectClick = typedArray.getBoolean(R.styleable.XLBottomView_isSelectClick,isSelectClick);
            //3.释放资源
            typedArray.recycle();
        }

        //初始化操作
        init();
    }

    /**
     * 初始化操作
     */
    private void init() {}

    /**
     * 子View布局
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //布局是否发生变化
        if (changed){
            //判断是否有数据
            if (items.size() != 0) {
                Log.d(TAG,"数据来了");

                //1.计算间距
                int space_num = hasLeftOrRightSize ? items.size()+1 : items.size()-1;
                int horizon_size = (getWidth() - items.size() * PxUtil.dpToPx(item_size,getContext())) / space_num;
                int vertical_size = (getHeight()- PxUtil.dpToPx(item_size,getContext())) / 2;

                //2.根据数据源依次创建视图
                for (int i = 0; i < items.size(); i++) {
                    //2.1 获取对应的模型数据
                    BottomViewItem item = items.get(i);

                    //2.2 创建视图
                    @SuppressLint("DrawAllocation") MyItem item_view = new MyItem(
                            getContext(),
                            item.icon_id,
                            item.title,
                            i,
                            normal_color,
                            select_color
                    );

                    //2.3 设置参数
                    int left = hasLeftOrRightSize ?
                            horizon_size + (horizon_size + PxUtil.dpToPx(item_size,getContext())) * i
                            :
                            (horizon_size + PxUtil.dpToPx(item_size,getContext())) * i;
                    if (!hasLeftOrRightSize){
                        //左右两端不设置间距也留一点间距
                        if (i == 0) left += PxUtil.dpToPx(20,getContext());
                        if (i == items.size()-1) left -= PxUtil.dpToPx(20,getContext());
                    }
                    int top = vertical_size;
                    int right = left + PxUtil.dpToPx(item_size,getContext());
                    int bottom = top + PxUtil.dpToPx(item_size,getContext());
                    item_view.layout(left,top,right,bottom);
                    Log.d(TAG,"left:"+left+" top:"+top);

                    //2.4 添加视图
                    addView(item_view);

                    //2.5 默认选中第一个
                    if (i == 0) {
                        item_view.changeStatus(MyItem.STATUS_SELECT);

                        lastItem = item_view;
                    }else {
                        item_view.changeStatus(MyItem.STATUS_NORMAL);
                    }

                    //2.6 添加点击事件
                    item_view.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            MyItem selectItem = (MyItem) v;

                            if (!lastItem.equals(selectItem)) {
                                //1.上一个还原
                                lastItem.changeStatus(MyItem.STATUS_NORMAL);
                                //2.当前点选中
                                selectItem.changeStatus(MyItem.STATUS_SELECT);
                                //3.记录当前的按钮
                                lastItem = selectItem;
                                //4.回调
                                if (listener != null){
                                    listener.itemStatusDidChange(selectItem.getItem_index());
                                }
                            }else {
                                if (isSelectClick) {
                                    //需要回调
                                    if (listener != null){
                                        listener.itemStatusDidChange(selectItem.getItem_index());
                                    }
                                }
                            }
                        }
                    });
                }
            }
        }
    }

    /**
     * 内部类：管理数据模型
     * 注意：外部操作内部类，内部类必须用static修饰
     */
    public static class BottomViewItem {
        //图片资源ID
        private int icon_id;
        //名称
        private String title;

        /**
         * 构造方法
         * @param icon_id
         * @param title
         */
        public BottomViewItem(int icon_id, String title) {
            this.icon_id = icon_id;
            this.title = title;
        }
    }

    /**
     * 定义回调接口
     */
    public interface XLBottomViewItemListener{
        void itemStatusDidChange(int index);
    }

    //setter方法
    public void setItems(List<BottomViewItem> items) {
        this.items = items;
    }

    public void setXLBottomViewItemListener(XLBottomViewItemListener listener) {
        this.listener = listener;
    }

    public void setSelectClick(boolean select) {
        isSelectClick = select;
    }
}
