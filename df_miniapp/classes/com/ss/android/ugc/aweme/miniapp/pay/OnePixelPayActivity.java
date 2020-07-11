package com.ss.android.ugc.aweme.miniapp.pay;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import com.bytedance.apm.agent.v2.instrumentation.ActivityAgent;
import com.ss.android.ugc.aweme.miniapp.BaseActivity;
import com.ss.android.ugc.aweme.miniapp.MiniAppService;
import com.ss.android.ugc.aweme.miniapp_api.a.c;
import com.ss.android.ugc.aweme.miniapp_api.b.a;
import com.ss.android.ugc.aweme.miniapp_api.model.a.a;
import com.ss.android.ugc.aweme.miniapp_api.model.b.c;
import com.tt.frontendapiinterface.ApiCallResult;
import com.tt.frontendapiinterface.a;
import com.tt.miniapp.util.ActivityUtil;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.IDCreator;
import com.tt.miniapphost.process.data.CrossProcessDataEntity;
import com.tt.miniapphost.process.helper.AsyncIpcHandler;
import com.tt.miniapphost.util.DebugUtil;
import com.tt.miniapphost.util.ProcessUtil;
import org.json.JSONException;
import org.json.JSONObject;

public class OnePixelPayActivity extends BaseActivity {
  public AsyncIpcHandler a;
  
  public String b;
  
  public boolean c;
  
  public int d;
  
  private boolean e = true;
  
  private String f;
  
  private String g;
  
  private int h;
  
  public static ApiCallResult a(String paramString) {
    return ApiCallResult.a.b("requestPayment").d(paramString).a();
  }
  
  public static void a(AsyncIpcHandler paramAsyncIpcHandler, ApiCallResult paramApiCallResult) {
    if (paramAsyncIpcHandler == null) {
      DebugUtil.outputError("OnePixelPayActivity", new Object[] { "asyncIpcHandler ==null" });
      return;
    } 
    AppBrandLogger.i("OnePixelPayActivity", new Object[] { "apiCallResult:", paramApiCallResult });
    paramAsyncIpcHandler.callback(CrossProcessDataEntity.Builder.create().putParcelable("payResult", (Parcelable)paramApiCallResult).build());
  }
  
  public static ApiCallResult b(String paramString) {
    return ApiCallResult.a.b("requestWXPayment").d(paramString).a();
  }
  
  public boolean dispatchTouchEvent(MotionEvent paramMotionEvent) {
    return true;
  }
  
  public void finish() {
    super.finish();
    overridePendingTransition(0, 0);
  }
  
  protected void onCreate(Bundle paramBundle) {
    c.a a;
    ActivityAgent.onTrace("com.ss.android.ugc.aweme.miniapp.pay.OnePixelPayActivity", "onCreate", true);
    super.onCreate(paramBundle);
    overridePendingTransition(0, 0);
    Window window = getWindow();
    window.setGravity(8388659);
    WindowManager.LayoutParams layoutParams = window.getAttributes();
    layoutParams.x = 0;
    layoutParams.y = 0;
    layoutParams.height = 1;
    layoutParams.width = 1;
    window.setAttributes(layoutParams);
    this.d = IDCreator.create();
    Intent intent = getIntent();
    String str = intent.getStringExtra("pay_str");
    this.a = ProcessUtil.generateAsyncIpcHandlerFromIntent(intent);
    this.c = intent.getBooleanExtra("is_wx_mini_pay", false);
    this.f = intent.getStringExtra("user_name");
    this.h = intent.getIntExtra("mini_program_type", 0);
    this.b = intent.getStringExtra("app_id");
    this.g = intent.getStringExtra("path");
    c c = MiniAppService.inst().getBaseLibDepend();
    if (c == null) {
      finish();
      ActivityAgent.onTrace("com.ss.android.ugc.aweme.miniapp.pay.OnePixelPayActivity", "onCreate", false);
      return;
    } 
    c.a("wx_pay", new a(this) {
          public final void onEvent(a param1a) {
            if (this.a.d > 0 && param1a != null && this.a.c) {
              StringBuilder stringBuilder = new StringBuilder("mWxMiniProgramCallbackId ");
              stringBuilder.append(this.a.d);
              AppBrandLogger.d("OnePixelPayActivity", new Object[] { stringBuilder.toString() });
              OnePixelPayActivity onePixelPayActivity2 = this.a;
              ActivityUtil.moveMiniAppActivityToFront((Activity)onePixelPayActivity2, onePixelPayActivity2.b);
              String str = param1a.getMessage();
              onePixelPayActivity2 = null;
              if (str != null) {
                String str1;
                try {
                  str1 = (new JSONObject(param1a.getMessage())).optString("errMsg");
                } catch (JSONException jSONException) {
                  AppBrandLogger.e("OnePixelPayActivity", new Object[] { "onWXMiniProgramResp", jSONException });
                  str1 = "";
                } 
                int i = true ^ TextUtils.isEmpty(str1);
                if (i != 0 && str1.startsWith("requestPayment:ok")) {
                  OnePixelPayActivity.a(this.a.a, ApiCallResult.a.a("requestWXPayment").a());
                } else {
                  String str2;
                  if (i != 0)
                    str2 = str1.replace("requestPayment:fail", "").trim(); 
                  OnePixelPayActivity.a(this.a.a, OnePixelPayActivity.b(str2));
                } 
              } else {
                OnePixelPayActivity.a(this.a.a, OnePixelPayActivity.a((String)null));
              } 
              OnePixelPayActivity onePixelPayActivity1 = this.a;
              onePixelPayActivity1.d = 0;
              if (!onePixelPayActivity1.isFinishing())
                this.a.finish(); 
            } 
          }
        });
    MiniAppService.inst().getPayDepend();
    if (this.c) {
      a = new c.a();
      a.b = this.g;
      a.a = this.f;
      a.d = this.h;
      a.e = true;
      c c1 = new c();
      c1.c = a.c;
      c1.b = a.b;
      c1.e = a.e;
      c1.d = a.d;
      c1.a = a.a;
    } else if (TextUtils.isEmpty((CharSequence)a)) {
      if (!isFinishing())
        finish(); 
    } else {
      try {
        new JSONObject((String)a);
        new Object(this) {
          
          };
      } finally {
        a = null;
        AppBrandLogger.e("OnePixelPayActivity", new Object[] { "pay exception", a });
        a(this.a, a(a.a((Throwable)a)));
      } 
    } 
    ActivityAgent.onTrace("com.ss.android.ugc.aweme.miniapp.pay.OnePixelPayActivity", "onCreate", false);
  }
  
  protected void onDestroy() {
    super.onDestroy();
    if (AppBrandLogger.debug())
      AppBrandLogger.d("OnePixelPayActivity", new Object[] { "onDestroy" }); 
    c c = MiniAppService.inst().getBaseLibDepend();
    if (c != null)
      c.a("wx_pay"); 
  }
  
  protected void onPause() {
    super.onPause();
    if (AppBrandLogger.debug())
      AppBrandLogger.d("OnePixelPayActivity", new Object[] { "onPause" }); 
  }
  
  protected void onResume() {
    ActivityAgent.onTrace("com.ss.android.ugc.aweme.miniapp.pay.OnePixelPayActivity", "onResume", true);
    super.onResume();
    AppBrandLogger.d("OnePixelPayActivity", new Object[] { "onResume " });
    if (this.e) {
      this.e = false;
    } else {
      if (this.d > 0 && this.c) {
        StringBuilder stringBuilder = new StringBuilder("mWxMiniProgramCallbackId ");
        stringBuilder.append(this.d);
        AppBrandLogger.d("OnePixelPayActivity", new Object[] { stringBuilder.toString() });
        a(this.a, b("cancel"));
        this.d = 0;
        String str = getIntent().getStringExtra("app_id");
        AppBrandLogger.i("OnePixelPayActivity", new Object[] { "moveMiniAppActivityToFront appId:", str });
        ActivityUtil.moveMiniAppActivityToFront((Activity)this, str);
        if (!isFinishing())
          finish(); 
      } 
      if (!isFinishing())
        finish(); 
    } 
    ActivityAgent.onTrace("com.ss.android.ugc.aweme.miniapp.pay.OnePixelPayActivity", "onResume", false);
  }
  
  public void onWindowFocusChanged(boolean paramBoolean) {
    ActivityAgent.onTrace("com.ss.android.ugc.aweme.miniapp.pay.OnePixelPayActivity", "onWindowFocusChanged", true);
    super.onWindowFocusChanged(paramBoolean);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\ss\androi\\ugc\aweme\miniapp\pay\OnePixelPayActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */