package cn.gov.gxxc.widget;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AbsListView;
import android.widget.ListView;

import cn.gov.gxxc.R;

/**
 * Created by chuyao on 16-1-18.
 */
public class XSwipeRefreshLayout extends SwipeRefreshLayout implements AbsListView.OnScrollListener, SwipeRefreshLayout.OnRefreshListener {

    private boolean isLoading;
    private int mTouchSlop;
    private int mLastY;
    private int mDownY;
    private ListView mListView;
    private View mFooterView;
    private OnLoadListener mOnLoadListener;
    public Status status;

    public enum Status {
        REFRESH, LOAD
    }

    public interface OnLoadListener {
        void onLoad();

        void onRefresh();
    }

    public XSwipeRefreshLayout(Context context) {
        super(context, null);
    }

    public XSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mFooterView = LayoutInflater.from(context).inflate(R.layout.layout_refresh_footer, null, false);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        status = Status.REFRESH;
        if (mOnLoadListener != null)
            mOnLoadListener.onRefresh();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (mListView == null) {
            getListView();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownY = (int) ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                mLastY = (int) ev.getRawY();
                break;
            case MotionEvent.ACTION_UP:
                if (canLoad()) {
                    loadData();
                }
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    public void setOnLoadListener(OnLoadListener listener) {
        this.mOnLoadListener = listener;
    }

    private void loadData() {
        status = Status.LOAD;
        if (mOnLoadListener != null) {
            mOnLoadListener.onLoad();
            setLoading(true);
        }
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
        if (isLoading) {
            mListView.addFooterView(mFooterView);
        } else {
            mListView.removeFooterView(mFooterView);
            mDownY = 0;
            mLastY = 0;
        }
    }

    private boolean canLoad() {
        return isBottom() && !isLoading && isPullUp();
    }

    private boolean isBottom() {
        if (mListView != null && mListView.getAdapter() != null)
            return mListView.getLastVisiblePosition() == (mListView.getAdapter().getCount() - 1);
        return false;
    }

    private boolean isPullUp() {
        return (mDownY - mLastY) >= mTouchSlop;
    }

    private void getListView() {
        int childCount = getChildCount();
        if (childCount > 0) {
            View childView = getChildAt(0);
            if (childView instanceof ListView) {
                mListView = (ListView) childView;
                mListView.setOnScrollListener(this);
            }
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {

    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i1, int i2) {
        if (canLoad())
            loadData();
    }
}
