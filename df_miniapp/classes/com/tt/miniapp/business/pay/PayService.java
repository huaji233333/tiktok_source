package com.tt.miniapp.business.pay;

import android.app.Activity;
import android.widget.FrameLayout;
import com.bytedance.sandboxapp.b.a;
import com.bytedance.sandboxapp.protocol.service.b.a;
import com.bytedance.sandboxapp.protocol.service.h.c;
import com.bytedance.sandboxapp.protocol.service.k.a;
import com.bytedance.sandboxapp.protocol.service.request.a;
import com.bytedance.sandboxapp.protocol.service.request.entity.HttpRequest;
import com.tt.miniapp.AppbrandConstant;
import com.tt.miniapp.base.MiniAppContext;
import com.tt.miniapp.base.activity.ActivityServiceInterface;
import com.tt.miniapp.event.InnerEventHelper;
import com.tt.miniapp.manager.WxPayManager;
import com.tt.miniapp.notification.MiniAppNotificationManager;
import com.tt.miniapphost.AppbrandContext;
import java.util.HashMap;

public class PayService implements a {
  private final MiniAppContext mMiniAppContext;
  
  public PayService(MiniAppContext paramMiniAppContext) {
    this.mMiniAppContext = paramMiniAppContext;
  }
  
  public a.c createPayNotification() {
    return new a.c(MiniAppNotificationManager.createPayNotification());
  }
  
  public MiniAppContext getContext() {
    return this.mMiniAppContext;
  }
  
  public void onDestroy() {}
  
  public void payOnH5(String paramString1, String paramString2, a.b paramb, final a.a payListener) {
    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-2, -2);
    layoutParams.leftMargin = paramb.a;
    layoutParams.topMargin = paramb.b;
    layoutParams.width = paramb.c;
    layoutParams.height = paramb.d;
    WxPayManager.payOnH5((Activity)AppbrandContext.getInst().getCurrentActivity(), paramString1, paramString2, layoutParams, new WxPayManager.WxPayListener() {
          public void onNotInstalled() {
            payListener.a();
          }
          
          public void onPayFail(String param1String) {
            String str = param1String;
            if (param1String == null)
              str = ""; 
            payListener.a(str);
          }
          
          public void onTriggerWxClientPay() {
            payListener.b();
            final ActivityServiceInterface activityServiceInterface = (ActivityServiceInterface)PayService.this.getContext().getService(ActivityServiceInterface.class);
            activityServiceInterface.registerActivityLifecycleCallbacks((ActivityServiceInterface.ActivityLifecycleCallbacks)new ActivityServiceInterface.AbsActivityLifecycleCallbacks() {
                  public void onActivityResumed(Activity param2Activity) {
                    payListener.c();
                    activityServiceInterface.unregisterActivityLifecycleCallbacks((ActivityServiceInterface.ActivityLifecycleCallbacks)this);
                    InnerEventHelper.mpTechnologyMsg("微信 H5 支付回调成功");
                  }
                });
          }
        });
  }
  
  public void reportPayInformation() {
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
    c c = (c)this.mMiniAppContext.getService(c.class);
    hashMap.put("aid", (c.getHostAppInfo()).a);
    hashMap.put("uid", (c.getHostAppUserInfo()).a);
    hashMap.put("sec_user_id", (c.getHostAppUserInfo()).b);
    hashMap.put("app_id", ((a)this.mMiniAppContext.getService(a.class)).getAppId());
    hashMap.put("order_no", String.valueOf(System.currentTimeMillis() / 1000L));
    a a1 = (a)this.mMiniAppContext.getService(a.class);
    HttpRequest.RequestTask.a a2 = HttpRequest.RequestTask.a.a.a(AppbrandConstant.OpenApi.getInst().getUploadOrderInfoUrl(), "POST").a(true);
    a2.c = hashMap;
    a1.syncHttpRequest(a2.a());
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\business\pay\PayService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */