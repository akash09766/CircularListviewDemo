package skylightdeveloper.com.circularlistviewdemo.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import skylightdeveloper.com.circularlistviewdemo.customui.CircularListView;
import skylightdeveloper.com.circularlistviewdemo.constants.CircularListViewContentAlignment;
import skylightdeveloper.com.circularlistviewdemo.R;
import skylightdeveloper.com.circularlistviewdemo.listeners.CircularListViewListener;
import skylightdeveloper.com.circularlistviewdemo.listeners.OnScrollStopped;

public class MainActivity extends AppCompatActivity implements OnScrollStopped, CircularListViewListener {

    private CircularListView mCircularListView;
    private boolean mIsAdapterDirty = true;
    private String TAG = MainActivity.class.getSimpleName();
    private Display display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCircularListView = (CircularListView) findViewById(R.id.circularListView);

        setAdapterToListView();
        setListenerToListView();
    }

    private void setListenerToListView() {
        mCircularListView.setOnScrollStoppedListener(this);
        mCircularListView.setCircularListViewListener(this);
    }

    private void setAdapterToListView() {
        ArrayAdapter<String> listAdapter = new
                ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        for (int i = 0; i < 10; i++) {
            if(i == 0){
                listAdapter.add("");
            }else{
                listAdapter.add(String.format("Item %02d", i-1));
            }
        }

        display = getWindowManager().getDefaultDisplay();
        mCircularListView.setRadius(Math.min(400, display.getWidth() / 2));
        mCircularListView.setAdapter(listAdapter);
        mCircularListView.setOnScrollStoppedListener(this);
    }

    void refreshCircular() {
        if (mIsAdapterDirty) {
//            mCircularListView.scrollFirstItemToCenter();
            mIsAdapterDirty = false;
        }

        TextView centerView = (TextView) mCircularListView.getCentralChild();

        if (centerView != null) {
            centerView.setTextColor(Color.WHITE);
        }
        for (int i = 0; i < mCircularListView.getChildCount(); i++) {
            TextView view = (TextView) mCircularListView.getChildAt(i);
            if (view != null && view != centerView) {
                view.setTextColor(Color.WHITE);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.align_left:
                mCircularListView.setCircularListViewContentAlignment(CircularListViewContentAlignment.Left);
                return true;
            case R.id.align_right:
                mCircularListView.setCircularListViewContentAlignment(CircularListViewContentAlignment.Right);
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onFingurePulled(int position) {

        Log.d(TAG, "onFingurePulled: pos = "+(position-1));
        mCircularListView.setSelection((position-1));
//        mCircularListView.smoothScrollToPosition(position,1000);
/*
        TextView centerView = (TextView) mCircularListView.getCentralChild();
        for (int i = 0; i < mCircularListView.getCount(); i++) {
            TextView view = (TextView) mCircularListView.getChildAt(i);
            if (view != null && view == centerView) {
        mCircularListView.setSelection(i);
            }
        }*/
    }

    @Override
    public void onCircularLayoutFinished(CircularListView circularListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        refreshCircular();
    }
}
