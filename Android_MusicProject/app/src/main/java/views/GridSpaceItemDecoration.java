package views;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

public class GridSpaceItemDecoration extends RecyclerView.ItemDecoration {
    private  int space;
    public GridSpaceItemDecoration(int mSpace, RecyclerView parent) {
        space = mSpace;
        getRecycleViewOffsets(parent);
    }


    /**
     * @param outRect Item的矩形边界
     * @param view ItemView
     * @param parent RecyclerView
     * @param state RecyclerView的状态
     */
    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.left = space;
        // 判断item是否为每行首个item
//        if(parent.getChildAdapterPosition(view) % 3 == 0){
//            outRect.left = 0;
//        }
    }

    private void getRecycleViewOffsets(RecyclerView parent){
        // margin 为正 则view 距离边界产生一个距离
        // margin 为负 则view 超出边界一个距离

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) parent.getLayoutParams();
        params.leftMargin = -space;
        parent.setLayoutParams(params);
    }
}
