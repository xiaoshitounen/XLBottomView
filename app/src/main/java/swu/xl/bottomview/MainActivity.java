package swu.xl.bottomview;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final XLBottomView bottomView = findViewById(R.id.bottom_view);

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
    }
}
