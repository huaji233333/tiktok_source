package com.tt.miniapp.business.device;

import android.text.TextUtils;
import com.bytedance.sandboxapp.b.a;
import com.bytedance.sandboxapp.protocol.service.e.a;
import com.bytedance.sandboxapp.protocol.service.e.b;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.entity.InitParamsEntity;
import com.tt.option.q.d;
import d.f.b.l;

public final class DeviceService implements b {
  private final a context;
  
  private a mCacheDeviceInfo;
  
  public DeviceService(a parama) {
    this.context = parama;
  }
  
  public final a getContext() {
    return this.context;
  }
  
  public final a getDeviceInfo() {
    a a2 = this.mCacheDeviceInfo;
    if (a2 != null)
      return a2; 
    AppbrandContext appbrandContext = AppbrandContext.getInst();
    l.a(appbrandContext, "AppbrandContext.getInst()");
    InitParamsEntity initParamsEntity = appbrandContext.getInitParams();
    String str = d.a();
    if (TextUtils.isEmpty(str)) {
      l.a(initParamsEntity, "initParams");
      return new a(str, initParamsEntity.getOsVersion(), initParamsEntity.getDevicePlatform());
    } 
    l.a(initParamsEntity, "initParams");
    a a1 = new a(str, initParamsEntity.getOsVersion(), initParamsEntity.getDevicePlatform());
    this.mCacheDeviceInfo = a1;
    return a1;
  }
  
  public final void onDestroy() {}
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\business\device\DeviceService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */