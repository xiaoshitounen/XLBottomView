package swu.xl.bottomview;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.Gravity;

/**
 * 自定义View：子项
 */

public class MyItem extends androidx.appcompat.widget.AppCompatTextView {
    //图片
    private int icon_id;
    //文本
    private String title;
    //索引
    private int item_index;
    //正常状态的颜色
    private int normal_color;
    //选中状态的颜色
    private int select_color;

    //正常状态
    public static final int STATUS_NORMAL = 0;
    //选中状态
    public static final int STATUS_SELECT = 1;

    /**
     * 构造方法：Java代码初始化
     * @param context
     */
    public MyItem(Context context, int icon_id, String title, int item_index, int normal_color, int select_color) {
        super(context);

        //保存数据
        this.icon_id = icon_id;
        this.title = title;
        this.item_index = item_index;
        this.normal_color = normal_color;
        this.select_color = select_color;
        setBackground(null);

        //初始化操作
        init(STATUS_NORMAL);
    }

    /**
     * 初始化操作
     */
    private void init(int status) {
        //setBackgroundColor(Color.MAGENTA);
        //1.设置图标

        //1.1获取图片，便于设置颜色滤镜
        Drawable drawable = getResources().getDrawable(icon_id).mutate();
        //1.2设置图片的尺寸
        drawable.setBounds(0,PxUtil.dpToPx(5,getContext()),PxUtil.dpToPx(25,getContext()),PxUtil.dpToPx(25,getContext())+PxUtil.dpToPx(5,getContext()));
        setCompoundDrawables(null,drawable,null,null);
        setCompoundDrawablePadding(PxUtil.dpToPx(8,getContext()));

        //2.设置文本
        setText(title);
        setTextSize(PxUtil.spToPx(5,getContext()));
        setGravity(Gravity.CENTER);
        setTextColor(normal_color);
    }

    /**
     * 改变状态
     */
    public void changeStatus(int status){
        if (status == STATUS_SELECT){
            //改变图片的颜色
            getCompoundDrawables()[1].mutate().setColorFilter(select_color, PorterDuff.Mode.SRC_ATOP);

            //改变文本的颜色
            setTextColor(select_color);
        }else if (status == STATUS_NORMAL){
            //改变图片的颜色
            getCompoundDrawables()[1].mutate().setColorFilter(normal_color, PorterDuff.Mode.SRC_ATOP);

            //改变文本的颜色
            setTextColor(normal_color);
        }

    }

    //get方法
    public int getItem_index() {
        return item_index;
    }
}
