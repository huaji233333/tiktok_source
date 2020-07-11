package com.tt.option.n;

import android.app.Activity;
import android.content.Intent;
import com.tt.miniapp.impl.HostOptionMediaDependImpl;
import com.tt.miniapphost.entity.ScanResultEntity;
import com.tt.option.a;

public class a extends a<b> implements b {
  public void chooseImage(Activity paramActivity, int paramInt, boolean paramBoolean1, boolean paramBoolean2, b.b paramb, b.a parama) {
    if (inject())
      ((b)this.defaultOptionDepend).chooseImage(paramActivity, paramInt, paramBoolean1, paramBoolean2, paramb, parama); 
  }
  
  public void chooseVideo(Activity paramActivity, int paramInt, boolean paramBoolean1, boolean paramBoolean2, b.c paramc) {
    if (inject())
      ((b)this.defaultOptionDepend).chooseVideo(paramActivity, paramInt, paramBoolean1, paramBoolean2, paramc); 
  }
  
  public c createChooseFileHandler(Activity paramActivity) {
    return inject() ? ((b)this.defaultOptionDepend).createChooseFileHandler(paramActivity) : null;
  }
  
  public ScanResultEntity handleActivityScanResult(int paramInt1, int paramInt2, Intent paramIntent) {
    return inject() ? ((b)this.defaultOptionDepend).handleActivityScanResult(paramInt1, paramInt2, paramIntent) : new ScanResultEntity();
  }
  
  public boolean scanCode(Activity paramActivity, b.d paramd) {
    return inject() ? ((b)this.defaultOptionDepend).scanCode(paramActivity, paramd) : false;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\option\n\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */