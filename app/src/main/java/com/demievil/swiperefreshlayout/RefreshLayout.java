package com.demievil.swiperefreshlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.HeaderViewListAdapter;
import android.widget.ListView;

public class RefreshLayout extends SwipeRefreshLayout {

    private int mTouchSlop;
    private ListView mListView;
    private OnRefreshLoadMoreListener mOnRefreshLoadMoreListener;
    private View mListViewFooter;

    private int mYDown;
    private int mLastY;

    private boolean isLoading = false;


    public RefreshLayout(Context context) {
        this(context, null);
    }

    public RefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //set the footer of the ListView with a ProgressBar in it
    public void setFooterView(int layoutId) {
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        mListViewFooter = LayoutInflater.from(getContext()).inflate(layoutId, null,false);
        ensureListView();
        mListView.addFooterView(mListViewFooter);
        mListView.setFooterDividersEnabled(false);
    }

    @Override
    public void setRefreshing(boolean refreshing) {
        super.setRefreshing(refreshing);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        ensureListView();
    }

    private void ensureListView() {
        if (mListView == null){
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                if (child instanceof ListView) {
                    mListView = (ListView) child;
                    break;
                }
            }
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mYDown = (int) event.getRawY();
                break;

            case MotionEvent.ACTION_MOVE:
                mLastY = (int) event.getRawY();
                if (mLastY - mYDown > 200) {
//                    playStartSound();
                }
                if (isPullingUp()) {
                    //you can add view or hint here when pulling up the ListView
                }

                break;

            case MotionEvent.ACTION_UP:
                if (canLoad()) {
                    loadMore();
                }
                break;
            default:
                break;
        }

        return super.dispatchTouchEvent(event);
    }

    private boolean canLoad() {
        return isBottom() && !isLoading && isPullingUp();
    }

    private boolean isBottom() {
        if (mListView.getCount() > 0) {
            if (mListView.getLastVisiblePosition() == mListView.getAdapter().getCount() - 1 &&
                    mListView.getChildAt(mListView.getChildCount() - 1).getBottom() <= mListView.getHeight()) {
                return true;
            }
        }
        return false;
    }

    private boolean isPullingUp() {
        return (mYDown - mLastY) >= mTouchSlop;
    }

    private void loadMore() {
        if (mOnRefreshLoadMoreListener != null) {
            setLoading(true);
            mOnRefreshLoadMoreListener.onLoadMore();
        }
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
        if (isLoading) {
            if (isRefreshing()) setRefreshing(false);
            if (mListView.getFooterViewsCount() == 0) {
                mListView.addFooterView(mListViewFooter);
                mListView.setSelection(mListView.getAdapter().getCount() - 1);
            } else {
                mListViewFooter.setVisibility(VISIBLE);
                //mListView.addFooterView(mListViewFooter);
            }
        } else {
            if (mListView.getAdapter() instanceof HeaderViewListAdapter) {
                mListView.removeFooterView(mListViewFooter);
            } else {
                mListViewFooter.setVisibility(View.GONE);
            }
            mYDown = 0;
            mLastY = 0;
        }
    }

    public static interface OnRefreshLoadMoreListener {
        public void onRefresh();

        public void onLoadMore();
    }

    public void setOnRefreshLoadMoreListener(OnRefreshLoadMoreListener listener) {
        this.mOnRefreshLoadMoreListener = listener;
        this.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (mOnRefreshLoadMoreListener != null) {
                    mOnRefreshLoadMoreListener.onRefresh();
                }
            }
        });
    }
}