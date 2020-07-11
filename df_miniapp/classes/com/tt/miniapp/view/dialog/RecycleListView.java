package com.tt.miniapp.view.dialog;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class RecycleListView extends ListView {
  boolean mRecycleOnMeasure = true;
  
  public RecycleListView(Context paramContext) {
    super(paramContext);
  }
  
  public RecycleListView(Context paramContext, AttributeSet paramAttributeSet) {
    super(paramContext, paramAttributeSet);
  }
  
  public RecycleListView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  protected boolean recycleOnMeasure() {
    return this.mRecycleOnMeasure;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\view\dialog\RecycleListView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */