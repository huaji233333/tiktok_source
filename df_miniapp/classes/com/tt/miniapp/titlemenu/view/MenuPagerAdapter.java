package com.tt.miniapp.titlemenu.view;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import com.tt.miniapp.titlemenu.item.IMenuItem;
import java.util.ArrayList;
import java.util.List;

public class MenuPagerAdapter extends PagerAdapter {
  private boolean isDoubleLine;
  
  private final int mColCount;
  
  private Context mContext;
  
  private final List<IMenuItem> mMenuListVO;
  
  private final int mPageMaxCount;
  
  public MenuPagerAdapter(Context paramContext, List<IMenuItem> paramList) {
    boolean bool;
    this.mContext = paramContext;
    this.mMenuListVO = paramList;
    this.mPageMaxCount = 10;
    this.mColCount = this.mPageMaxCount / 2;
    if (this.mMenuListVO.size() > this.mColCount) {
      bool = true;
    } else {
      bool = false;
    } 
    this.isDoubleLine = bool;
  }
  
  public void destroyItem(ViewGroup paramViewGroup, int paramInt, Object paramObject) {}
  
  public int getCount() {
    List<IMenuItem> list = this.mMenuListVO;
    return (list != null) ? (int)Math.ceil((list.size() / this.mPageMaxCount)) : 0;
  }
  
  public Object instantiateItem(ViewGroup paramViewGroup, int paramInt) {
    if (paramViewGroup.getChildAt(paramInt) == null) {
      ArrayList<MenuItemView> arrayList = new ArrayList();
      int j = this.mPageMaxCount;
      for (int i = paramInt * j; i < this.mMenuListVO.size() && i < (paramInt + 1) * j; i++) {
        MenuItemView menuItemView = ((IMenuItem)this.mMenuListVO.get(i)).getView();
        if (menuItemView.getParent() != null)
          ((ViewGroup)menuItemView.getParent()).removeView((View)menuItemView); 
        arrayList.add(menuItemView);
      } 
      paramViewGroup.addView((View)new MenuPageLayout(this.mContext, arrayList, this.mColCount));
    } 
    return paramViewGroup.getChildAt(paramInt);
  }
  
  public boolean isDoubleLine() {
    return this.isDoubleLine;
  }
  
  public boolean isViewFromObject(View paramView, Object paramObject) {
    return (paramView == paramObject);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\titlemenu\view\MenuPagerAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */