package lyon.browser.mutilewebview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity {

    String TAG = MainActivity.class.getSimpleName();
    ArrayList<WebviewModel> webviewModelArrayList = new ArrayList<>();
    String defUrl = "https://www.google.com.tw";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        // 使用SpaceItemDecoration，第二個參數是在dimens.xml中定義的間隔值
        recyclerView.addItemDecoration(new SpaceItemDecoration(this, R.dimen.item_space));
        WebAdapter adapter = new WebAdapter(this);
        recyclerView.setAdapter(adapter);
    }
}