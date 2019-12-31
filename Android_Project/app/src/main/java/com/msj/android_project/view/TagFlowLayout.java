package com.msj.android_project.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.msj.android_project.adapter.TagAdapter;

import java.util.ArrayList;
import java.util.List;

public class TagFlowLayout extends FlowLayout implements TagAdapter.OnDataSetChangedListener {

    private TagAdapter mAdapter;

    private int mMaxSelectCount;

    public void setmMaxSelectCount(int mMaxSelectCount) {
        this.mMaxSelectCount = mMaxSelectCount;
    }

    public TagFlowLayout(Context context) {
        super(context);
    }

    public TagFlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setAdapter(TagAdapter tagAdapter) {
        mAdapter = tagAdapter;
        mAdapter.setOnDataSetChangedListener(this);
        onDataChanged();
    }

    @Override
    public void onDataChanged() {
        removeAllViews();
        TagAdapter adapter = mAdapter;
        for (int i = 0; i < adapter.getItemCount(); i++) {
            View view = adapter.createView(LayoutInflater.from(getContext()), this, i);
            adapter.bindView(view, i);
            addView(view);
            if (view.isSelected()) {
                adapter.onItemSelected(view, i);
            } else {
                adapter.onItemUnSelected(view, i);
            }
            bindViewMethod(i, view);
        }
    }

    /**
     * 单选: 可以直接选择一个,当选择下一个的时候,上一个选择效果自动取消
     * 多选: 可以需要手动反选
     *
     * @param position
     * @param view
     */
    private void bindViewMethod(final int position, View view) {
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mAdapter.onItemViewClick(view, position);

                if (mMaxSelectCount <= 0) {
                    return;
                }
                // todo 特殊情况
                if (!view.isSelected()) {
                    if (getSelectViewCount() >= mMaxSelectCount) {
                        // todo 单选情况
                        if (getSelectViewCount() == 1) {
                            View selectView = getSelectView();
                            if (selectView != null) {
                                selectView.setSelected(false);
                                mAdapter.onItemUnSelected(selectView, getPositionForChild(selectView));
                            }
                        } else {
                            mAdapter.tipForSelectMax(view, mMaxSelectCount);
                            return;
                        }
                    }
                }

                if (view.isSelected()) {
                    view.setSelected(false);
                    mAdapter.onItemUnSelected(view, position);
                } else {
                    view.setSelected(true);
                    mAdapter.onItemSelected(view, position);
                }
            }
        });
    }

    private int getPositionForChild(View selectView) {
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if (view == selectView) {
                return i;
            }
        }
        return 0;
    }

    private View getSelectView() {
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if (view.isSelected()) {
                return view;
            }
        }
        return null;
    }

    private int getSelectViewCount() {

        int result = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if (view.isSelected()) {
                result++;
            }
        }
        return result;
    }


    public List<Integer> getSelectedItemPositionList(){
        List<Integer> selectList = new ArrayList<>();
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if (view.isSelected()) {
                selectList.add(i);
            }
        }
        return selectList;
    }
}
