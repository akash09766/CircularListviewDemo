package skylightdeveloper.com.circularlistviewdemo.listeners;

import skylightdeveloper.com.circularlistviewdemo.customui.CircularListView;

public interface CircularListViewListener {
    void onCircularLayoutFinished(CircularListView circularListView,
                                  int firstVisibleItem,
                                  int visibleItemCount,
                                  int totalItemCount);
}
