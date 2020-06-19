# XLBottomView 

新版本的XLBottomView在：[XLNewBottomView](https://github.com/xiaoshitounen/XLNewBottomView)，在这里面解决了一些自定义View之前不明白的问题。

详细内容博客地址:[自定义View-XLBottomView](https://xuxiaoshi.gitee.io/%E8%87%AA%E5%AE%9A%E4%B9%89View-XLBottomView/#more)

简介：

模拟BottomNavigationView，其中的item子项本来是想使用XlItem的，但是由于自定义某一个方面出了问题，所以用了一个简陋的MyItem。

app模块是使用例子，其运行效果：

![](https://android-1300729795.cos.ap-chengdu.myqcloud.com/project/Self_View/XLBottomView/XLBottomView.gif)


### 1. 添加依赖

Step 1. Add the JitPack repository to your build file

Add it in your root build.gradle at the end of repositories:
~~~
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
~~~

Step 2. Add the dependency
~~~
dependencies {
    implementation 'com.github.xiaoshitounen:XLBottomView:1.0.3'
}
~~~

### 2. Xml文件中静态添加使用

~~~xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <swu.xl.bottomview.XLBottomView
        android:id="@+id/bottom_view"
        android:layout_width="match_parent"
        android:layout_height="60dp"
        android:background="#999999"
        app:normal_color="#000000"
        app:select_color="#ff9900"
        app:hasLeftOrRightSize="false"
        app:item_size="50"
        android:layout_alignParentBottom="true"
        />

</RelativeLayout>
~~~

数据源目前只支持Java代码添加
~~~java
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        XLBottomView bottomView = findViewById(R.id.bottom_view);

        List<XLBottomView.BottomViewItem> items = new ArrayList<>();
        XLBottomView.BottomViewItem item1 = new XLBottomView.BottomViewItem(
                R.drawable.contact,
                "用户1"
        );
        XLBottomView.BottomViewItem item2 = new XLBottomView.BottomViewItem(
                R.drawable.contact,
                "用户2"
        );
        XLBottomView.BottomViewItem item3 = new XLBottomView.BottomViewItem(
                R.drawable.contact,
                "用户3"
        );
        XLBottomView.BottomViewItem item4 = new XLBottomView.BottomViewItem(
                R.drawable.contact,
                "用户4"
        );

        items.add(item1);
        items.add(item2);
        items.add(item3);
        items.add(item4);

        bottomView.setItems(items);
        
        bottomView.setXLBottomViewItemListener(new XLBottomView.XLBottomViewItemListener() {
            @Override
            public void itemStatusDidChange(int index) {
                Toast toast = Toast.makeText(MainActivity.this, "第" + (index + 1) + "个按钮被点击", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER,0,0);
                toast.show();
            }
        });
    }
}
~~~

#### ① 属性

- normal_color：item正常状态下的颜色
- select_color：item选中状态的颜色
- hasLeftOrRightSize：两端是否留有间距
- item_size：item的大小
- isSelectClick：选中的item被点击是否响应回调事件

#### ② 回调当前被点击的item

~~~java
//实现回调接口
public interface XLBottomViewItemListener{
    void itemStatusDidChange(int index);
}
~~~

~~~java
bottomView.setXLBottomViewItemListener(new XLBottomView.XLBottomViewItemListener() {
    @Override
    public void itemStatusDidChange(int index) {
        Toast toast = Toast.makeText(MainActivity.this, "第" + (index + 1) + "个按钮被点击", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }
});
~~~


### 3. Java代码中动态添加

~~~java
//item_layout暂时没有用，没有的话可以传0
public XLBottomView(Context context, int normal_color, int select_color, boolean hasLeftOrRightSize, int item_size, int item_layout);
~~~


***
更新：添加选中

~~~java
bottomView.post(new Runnable() {
    @Override
    public void run() {
        bottomView.getLastItem().changeStatus(MyItem.STATUS_NORMAL);
        for (MyItem item_view : bottomView.getItem_views()) {
            if (item_view.getItem_index() == 1){
                item_view.changeStatus(MyItem.STATUS_SELECT);

                bottomView.setLastItem(item_view);

                break;
            }
        }
    }
});
~~~




