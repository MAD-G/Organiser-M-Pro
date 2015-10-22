package com.foound.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

public class AmazingListView
  extends ListView
  implements AmazingAdapter.HasMorePagesListener
{
  public static final String TAG = AmazingListView.class.getSimpleName();
  private AmazingAdapter adapter;
  boolean footerViewAttached = false;
  View listFooter;
  private View mHeaderView;
  private int mHeaderViewHeight;
  private boolean mHeaderViewVisible;
  private int mHeaderViewWidth;
  
  public AmazingListView(Context paramContext)
  {
    super(paramContext);
  }
  
  public AmazingListView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public AmazingListView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  public void configureHeaderView(int paramInt)
  {
    if (this.mHeaderView == null) {}
    View localView;
    do
    {
      return;
      switch (this.adapter.getPinnedHeaderState(paramInt))
      {
      default: 
        return;
      case 0: 
        this.mHeaderViewVisible = false;
        return;
      case 1: 
        this.adapter.configurePinnedHeader(this.mHeaderView, paramInt, 255);
        if (this.mHeaderView.getTop() != 0) {
          this.mHeaderView.layout(0, 0, this.mHeaderViewWidth, this.mHeaderViewHeight);
        }
        this.mHeaderViewVisible = true;
        return;
      }
      localView = getChildAt(0);
    } while (localView == null);
    int j = localView.getBottom();
    int i = this.mHeaderView.getHeight();
    if (j < i) {
      j -= i;
    }
    for (i = (i + j) * 255 / i;; i = 255)
    {
      this.adapter.configurePinnedHeader(this.mHeaderView, paramInt, i);
      if (this.mHeaderView.getTop() != j) {
        this.mHeaderView.layout(0, j, this.mHeaderViewWidth, this.mHeaderViewHeight + j);
      }
      this.mHeaderViewVisible = true;
      return;
      j = 0;
    }
  }
  
  protected void dispatchDraw(Canvas paramCanvas)
  {
    super.dispatchDraw(paramCanvas);
    if (this.mHeaderViewVisible) {
      drawChild(paramCanvas, this.mHeaderView, getDrawingTime());
    }
  }
  
  public AmazingAdapter getAdapter()
  {
    return this.adapter;
  }
  
  public View getLoadingView()
  {
    return this.listFooter;
  }
  
  public boolean isLoadingViewVisible()
  {
    return this.footerViewAttached;
  }
  
  public void mayHaveMorePages()
  {
    if ((!this.footerViewAttached) && (this.listFooter != null))
    {
      addFooterView(this.listFooter);
      this.footerViewAttached = true;
    }
  }
  
  public void noMorePages()
  {
    if (this.listFooter != null) {
      removeFooterView(this.listFooter);
    }
    this.footerViewAttached = false;
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    if (this.mHeaderView != null)
    {
      this.mHeaderView.layout(0, 0, this.mHeaderViewWidth, this.mHeaderViewHeight);
      configureHeaderView(getFirstVisiblePosition());
    }
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    super.onMeasure(paramInt1, paramInt2);
    if (this.mHeaderView != null)
    {
      measureChild(this.mHeaderView, paramInt1, paramInt2);
      this.mHeaderViewWidth = this.mHeaderView.getMeasuredWidth();
      this.mHeaderViewHeight = this.mHeaderView.getMeasuredHeight();
    }
  }
  
  public void setAdapter(ListAdapter paramListAdapter)
  {
    if (!(paramListAdapter instanceof AmazingAdapter)) {
      throw new IllegalArgumentException(AmazingListView.class.getSimpleName() + " must use adapter of type " + AmazingAdapter.class.getSimpleName());
    }
    if (this.adapter != null)
    {
      this.adapter.setHasMorePagesListener(null);
      setOnScrollListener(null);
    }
    this.adapter = ((AmazingAdapter)paramListAdapter);
    ((AmazingAdapter)paramListAdapter).setHasMorePagesListener(this);
    setOnScrollListener((AmazingAdapter)paramListAdapter);
    View localView = new View(getContext());
    super.addFooterView(localView);
    super.setAdapter(paramListAdapter);
    super.removeFooterView(localView);
  }
  
  public void setLoadingView(View paramView)
  {
    this.listFooter = paramView;
  }
  
  public void setPinnedHeaderView(View paramView)
  {
    this.mHeaderView = paramView;
    if (this.mHeaderView != null) {
      setFadingEdgeLength(0);
    }
    requestLayout();
  }
}


/* Location:              C:\Users\Madhav\Downloads\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\com\foound\widget\AmazingListView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1-SNAPSHOT-20140817
 */