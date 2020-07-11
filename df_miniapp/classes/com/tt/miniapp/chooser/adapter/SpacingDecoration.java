package com.tt.miniapp.chooser.adapter;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class SpacingDecoration extends RecyclerView.h {
  private int space;
  
  private int spanCount;
  
  public SpacingDecoration(int paramInt1, int paramInt2) {
    this.spanCount = paramInt1;
    this.space = paramInt2;
  }
  
  public void getItemOffsets(Rect paramRect, View paramView, RecyclerView paramRecyclerView, RecyclerView.s params) {
    int i = this.space;
    paramRect.left = i;
    paramRect.bottom = i;
    if (RecyclerView.g(paramView) % this.spanCount == 0)
      paramRect.left = 0; 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\chooser\adapter\SpacingDecoration.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */