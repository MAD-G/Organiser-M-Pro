package com.foound.widget;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;

public abstract class AmazingAdapter
  extends BaseAdapter
  implements SectionIndexer, AbsListView.OnScrollListener
{
  public static final int PINNED_HEADER_GONE = 0;
  public static final int PINNED_HEADER_PUSHED_UP = 2;
  public static final int PINNED_HEADER_VISIBLE = 1;
  public static final String TAG = AmazingAdapter.class.getSimpleName();
  boolean automaticNextPageLoading = false;
  HasMorePagesListener hasMorePagesListener;
  int initialPage = 1;
  int page = 1;
  
  protected abstract void bindSectionHeader(View paramView, int paramInt, boolean paramBoolean);
  
  public abstract void configurePinnedHeader(View paramView, int paramInt1, int paramInt2);
  
  public abstract View getAmazingView(int paramInt, View paramView, ViewGroup paramViewGroup);
  
  public int getPinnedHeaderState(int paramInt)
  {
    if ((paramInt < 0) || (getCount() == 0)) {
      return 0;
    }
    int i = getPositionForSection(getSectionForPosition(paramInt) + 1);
    if ((i != -1) && (paramInt == i - 1)) {
      return 2;
    }
    return 1;
  }
  
  public abstract int getPositionForSection(int paramInt);
  
  public abstract int getSectionForPosition(int paramInt);
  
  public abstract Object[] getSections();
  
  public final View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    paramView = getAmazingView(paramInt, paramView, paramViewGroup);
    if ((paramInt == getCount() - 1) && (this.automaticNextPageLoading)) {
      onNextPageRequested(this.page + 1);
    }
    if (getPositionForSection(getSectionForPosition(paramInt)) == paramInt) {}
    for (boolean bool = true;; bool = false)
    {
      bindSectionHeader(paramView, paramInt, bool);
      return paramView;
    }
  }
  
  public void nextPage()
  {
    this.page += 1;
  }
  
  public void notifyMayHaveMorePages()
  {
    this.automaticNextPageLoading = true;
    if (this.hasMorePagesListener != null) {
      this.hasMorePagesListener.mayHaveMorePages();
    }
  }
  
  public void notifyNoMorePages()
  {
    this.automaticNextPageLoading = false;
    if (this.hasMorePagesListener != null) {
      this.hasMorePagesListener.noMorePages();
    }
  }
  
  protected abstract void onNextPageRequested(int paramInt);
  
  public void onScroll(AbsListView paramAbsListView, int paramInt1, int paramInt2, int paramInt3)
  {
    if ((paramAbsListView instanceof AmazingListView)) {
      ((AmazingListView)paramAbsListView).configureHeaderView(paramInt1);
    }
  }
  
  public void onScrollStateChanged(AbsListView paramAbsListView, int paramInt) {}
  
  public void resetPage()
  {
    this.page = this.initialPage;
  }
  
  void setHasMorePagesListener(HasMorePagesListener paramHasMorePagesListener)
  {
    this.hasMorePagesListener = paramHasMorePagesListener;
  }
  
  public void setInitialPage(int paramInt)
  {
    this.initialPage = paramInt;
  }
  
  public static abstract interface HasMorePagesListener
  {
    public abstract void mayHaveMorePages();
    
    public abstract void noMorePages();
  }
}


/* Location:              C:\Users\Madhav\Downloads\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\com\foound\widget\AmazingAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1-SNAPSHOT-20140817
 */