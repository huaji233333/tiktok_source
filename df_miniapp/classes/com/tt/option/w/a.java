package com.tt.option.w;

import com.tt.miniapp.impl.HostOptionShareDependImpl;
import com.tt.option.a;

public final class a extends a<b> implements b {
  public final void getShareBaseInfo(String paramString, d paramd) {
    if (inject())
      ((b)this.defaultOptionDepend).getShareBaseInfo(paramString, paramd); 
  }
  
  public final void getShareToken(h paramh, e parame) {
    if (inject())
      ((b)this.defaultOptionDepend).getShareToken(paramh, parame); 
  }
  
  public final boolean isBlockChanelDefault(String paramString, boolean paramBoolean) {
    return inject() ? ((b)this.defaultOptionDepend).isBlockChanelDefault(paramString, paramBoolean) : false;
  }
  
  public final h obtainShareInfo() {
    return inject() ? ((b)this.defaultOptionDepend).obtainShareInfo() : null;
  }
  
  public final b.a obtainShareInfoCallback() {
    return inject() ? ((b)this.defaultOptionDepend).obtainShareInfoCallback() : null;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\option\w\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */