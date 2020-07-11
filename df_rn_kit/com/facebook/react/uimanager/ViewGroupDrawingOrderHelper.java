package com.facebook.react.uimanager;

import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ViewGroupDrawingOrderHelper {
  private int[] mDrawingOrderIndices;
  
  private int mNumberOfChildrenWithZIndex;
  
  private final ViewGroup mViewGroup;
  
  public ViewGroupDrawingOrderHelper(ViewGroup paramViewGroup) {
    this.mViewGroup = paramViewGroup;
  }
  
  public int getChildDrawingOrder(int paramInt1, int paramInt2) {
    if (this.mDrawingOrderIndices == null) {
      ArrayList<View> arrayList = new ArrayList();
      boolean bool = false;
      int i;
      for (i = 0; i < paramInt1; i++)
        arrayList.add(this.mViewGroup.getChildAt(i)); 
      Collections.sort(arrayList, new Comparator<View>() {
            public int compare(View param1View1, View param1View2) {
              Integer integer4 = ViewGroupManager.getViewZIndex(param1View1);
              Integer integer1 = Integer.valueOf(0);
              Integer integer3 = integer4;
              if (integer4 == null)
                integer3 = integer1; 
              integer4 = ViewGroupManager.getViewZIndex(param1View2);
              Integer integer2 = integer4;
              if (integer4 == null)
                integer2 = integer1; 
              return integer3.intValue() - integer2.intValue();
            }
          });
      this.mDrawingOrderIndices = new int[paramInt1];
      for (i = bool; i < paramInt1; i++) {
        View view = arrayList.get(i);
        this.mDrawingOrderIndices[i] = this.mViewGroup.indexOfChild(view);
      } 
    } 
    return this.mDrawingOrderIndices[paramInt2];
  }
  
  public void handleAddView(View paramView) {
    if (ViewGroupManager.getViewZIndex(paramView) != null)
      this.mNumberOfChildrenWithZIndex++; 
    this.mDrawingOrderIndices = null;
  }
  
  public void handleRemoveView(View paramView) {
    if (ViewGroupManager.getViewZIndex(paramView) != null)
      this.mNumberOfChildrenWithZIndex--; 
    this.mDrawingOrderIndices = null;
  }
  
  public boolean shouldEnableCustomDrawingOrder() {
    return (this.mNumberOfChildrenWithZIndex > 0);
  }
  
  public void update() {
    int i = 0;
    this.mNumberOfChildrenWithZIndex = 0;
    while (i < this.mViewGroup.getChildCount()) {
      if (ViewGroupManager.getViewZIndex(this.mViewGroup.getChildAt(i)) != null)
        this.mNumberOfChildrenWithZIndex++; 
      i++;
    } 
    this.mDrawingOrderIndices = null;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\reac\\uimanager\ViewGroupDrawingOrderHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */