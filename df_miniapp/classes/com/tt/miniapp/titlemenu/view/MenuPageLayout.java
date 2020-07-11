package com.tt.miniapp.titlemenu.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import com.tt.miniapphost.util.UIUtils;
import java.util.Iterator;
import java.util.List;

public class MenuPageLayout extends RelativeLayout {
  private int mColCount;
  
  private boolean mIsDoubleLine;
  
  private List<MenuItemView> mMenuItemList;
  
  public MenuPageLayout(Context paramContext, List<MenuItemView> paramList, int paramInt) {
    this(paramContext, paramList, paramInt, bool);
  }
  
  public MenuPageLayout(Context paramContext, List<MenuItemView> paramList, int paramInt, boolean paramBoolean) {
    super(paramContext);
    this.mMenuItemList = paramList;
    this.mColCount = paramInt;
    this.mIsDoubleLine = paramBoolean;
    Iterator<MenuItemView> iterator = paramList.iterator();
    while (iterator.hasNext())
      addView((View)iterator.next()); 
  }
  
  protected void onMeasure(int paramInt1, int paramInt2) {
    List<MenuItemView> list = this.mMenuItemList;
    int i = 0;
    int j = ((MenuItemView)list.get(0)).getItemWidth();
    int k = ((MenuItemView)this.mMenuItemList.get(0)).getItemHeight();
    int m = getMeasuredWidth();
    int n = this.mColCount;
    m = (m - j * n) / (n + 1);
    n = (int)UIUtils.dip2Px(getContext(), 26.0F);
    int i1 = (int)UIUtils.dip2Px(getContext(), 36.0F);
    while (i < this.mColCount && i < this.mMenuItemList.size()) {
      MenuItemView menuItemView = (MenuItemView)getChildAt(i);
      RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
      layoutParams.topMargin = n;
      layoutParams.setMarginStart((m + j) * i + m);
      menuItemView.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
      i++;
    } 
    if (this.mIsDoubleLine)
      for (i = this.mColCount; i < this.mColCount * 2 && i < this.mMenuItemList.size(); i++) {
        MenuItemView menuItemView = this.mMenuItemList.get(i);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.topMargin = n + k + i1;
        layoutParams.setMarginStart((m + j) * (i - this.mColCount) + m);
        menuItemView.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
      }  
    super.onMeasure(paramInt1, paramInt2);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\titlemenu\view\MenuPageLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */