package com.facebook.react.uimanager;

import java.util.Comparator;

public class ViewAtIndex {
  public static Comparator<ViewAtIndex> COMPARATOR = new Comparator<ViewAtIndex>() {
      public final int compare(ViewAtIndex param1ViewAtIndex1, ViewAtIndex param1ViewAtIndex2) {
        return param1ViewAtIndex1.mIndex - param1ViewAtIndex2.mIndex;
      }
    };
  
  public final int mIndex;
  
  public final int mTag;
  
  public ViewAtIndex(int paramInt1, int paramInt2) {
    this.mTag = paramInt1;
    this.mIndex = paramInt2;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\reac\\uimanager\ViewAtIndex.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */