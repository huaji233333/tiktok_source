package com.tt.miniapp.base.netrequest;

import android.util.SparseArray;
import com.bytedance.sandboxapp.b.a.b.b;
import d.f.b.g;

public final class AbortRequestHolder {
  public static final Companion Companion = new Companion(null);
  
  private final SparseArray<Boolean> mAbortRequests = new SparseArray();
  
  public final void cancelRequest(int paramInt) {
    if ((Boolean)this.mAbortRequests.get(paramInt) != null) {
      b.b.e("AbortRequestHolder", new Object[] { "multi cancel" });
      return;
    } 
    this.mAbortRequests.put(paramInt, Boolean.valueOf(true));
  }
  
  public final boolean isRequestCancel(int paramInt) {
    Boolean bool = (Boolean)this.mAbortRequests.get(paramInt);
    return (bool != null && bool.booleanValue());
  }
  
  public static final class Companion {
    private Companion() {}
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\base\netrequest\AbortRequestHolder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */