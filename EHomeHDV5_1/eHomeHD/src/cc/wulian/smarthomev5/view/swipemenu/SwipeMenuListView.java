package cc.wulian.smarthomev5.view.swipemenu;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.ListView;

public class SwipeMenuListView extends ListView {

	private static final int TOUCH_STATE_NONE = 0;
	private static final int TOUCH_STATE_X = 1;
	private static final int TOUCH_STATE_Y = 2;

	private int MAX_Y = 5;
	private int MAX_X = 3;
	private float mDownX;
	private float mDownY;
	private int mTouchState;
	private int mTouchPosition;
	private SwipeMenuLayout mTouchView;

	private Interpolator mCloseInterpolator;
	private Interpolator mOpenInterpolator;
	private OpenOrCloseListener onOpenOrCloseListener;
	private boolean isFristOpen;

	public SwipeMenuListView(Context context) {
		super(context);
		init();
	}

	public SwipeMenuListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public SwipeMenuListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		MAX_X = dp2px(MAX_X);
		MAX_Y = dp2px(MAX_Y);
		mTouchState = TOUCH_STATE_NONE;
	}

	// @Override
	// /**
	// * 重写该方法，达到使ListView适应ScrollView的效果
	// */
	// protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
	// int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
	// MeasureSpec.AT_MOST);
	// super.onMeasure(widthMeasureSpec, expandSpec);
	// }

	/*
	 * if (mTouchView != null) { mTouchView.smoothCloseMenu(); }
	 */
	public void setCloseInterpolator(Interpolator interpolator) {
		mCloseInterpolator = interpolator;
	}

	public void setOpenInterpolator(Interpolator interpolator) {
		mOpenInterpolator = interpolator;
	}

	public Interpolator getOpenInterpolator() {
		return mOpenInterpolator;
	}

	public Interpolator getCloseInterpolator() {
		return mCloseInterpolator;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		return super.onInterceptTouchEvent(ev);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		int action = MotionEventCompat.getActionMasked(ev);
		action = ev.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			if (mTouchView != null)
				isFristOpen = mTouchView.isOpen();
			if (onOpenOrCloseListener != null) {
				onOpenOrCloseListener.isOpen(isFristOpen);
			}
			mDownX = ev.getX();
			mDownY = ev.getY();
			mTouchState = TOUCH_STATE_NONE;
			if (mTouchView != null && mTouchView.isOpen()) {
				mTouchView.smoothCloseMenu();
				return false;
			}
			mTouchPosition = pointToPosition((int) ev.getX(), (int) ev.getY());
			View view = getChildAt(mTouchPosition - getFirstVisiblePosition());
			if (view instanceof SwipeMenuLayout) {
				mTouchView = (SwipeMenuLayout) view;
			}
			if (mTouchView != null) {
				mTouchView.onSwipe(ev);
			}
			break;
		case MotionEvent.ACTION_MOVE:
			float dy = Math.abs((ev.getY() - mDownY));
			float dx = Math.abs((ev.getX() - mDownX));
			if (mTouchState == TOUCH_STATE_X) {
				// 滑动
				if (mTouchView != null) {
					mTouchView.onSwipe(ev);
				}
				getSelector().setState(new int[] { 0 });
				ev.setAction(MotionEvent.ACTION_CANCEL);
				super.onTouchEvent(ev);
				return true;
			} else {
				if (mTouchState != TOUCH_STATE_X && Math.abs(dy) > MAX_Y) {
					mTouchState = TOUCH_STATE_Y;
				}
				if (mTouchState != TOUCH_STATE_Y && dx > MAX_X) {
					mTouchState = TOUCH_STATE_X;
				}
			}
			break;
		case MotionEvent.ACTION_UP:
			if (mTouchState == TOUCH_STATE_X) {
				if (mTouchView != null) {
					mTouchView.onSwipe(ev);
				}
				ev.setAction(MotionEvent.ACTION_CANCEL);
				super.onTouchEvent(ev);
				return true;
			}

			break;
		}
		return super.onTouchEvent(ev);
	}

	private int dp2px(int dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getContext().getResources()
				.getDisplayMetrics());
	}

	public void setOnOpenOrCloseListener(OpenOrCloseListener onOpenOrCloseListener) {
		this.onOpenOrCloseListener = onOpenOrCloseListener;
	}

	public static interface OpenOrCloseListener {
		void isOpen(boolean isOpen);
	}
}
