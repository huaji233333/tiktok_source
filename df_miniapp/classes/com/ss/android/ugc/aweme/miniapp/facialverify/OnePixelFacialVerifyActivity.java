package com.ss.android.ugc.aweme.miniapp.facialverify;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.b;
import android.view.Window;
import android.view.WindowManager;
import com.bytedance.apm.agent.v2.instrumentation.ActivityAgent;
import com.ss.android.ugc.aweme.miniapp.BaseActivity;
import com.ss.android.ugc.aweme.miniapp.MiniAppService;
import com.tt.miniapphost.AppBrandLogger;

public class OnePixelFacialVerifyActivity extends BaseActivity {
  public boolean a;
  
  private void a(int paramInt, String paramString1, String paramString2) {
    Intent intent = new Intent();
    intent.putExtra("req_order_no", paramString2);
    intent.putExtra("err_code", paramInt);
    intent.putExtra("err_msg", paramString1);
    setResult(-1, intent);
  }
  
  protected void onCreate(Bundle paramBundle) {
    ActivityAgent.onTrace("com.ss.android.ugc.aweme.miniapp.facialverify.OnePixelFacialVerifyActivity", "onCreate", true);
    super.onCreate(paramBundle);
    Window window = getWindow();
    window.setGravity(8388659);
    WindowManager.LayoutParams layoutParams = window.getAttributes();
    layoutParams.x = 0;
    layoutParams.y = 0;
    layoutParams.height = 1;
    layoutParams.width = 1;
    window.setAttributes(layoutParams);
    if (!(getIntent().getSerializableExtra("dataMap") instanceof java.util.HashMap)) {
      AppBrandLogger.e("OnePixelFacialVerifyActivity", new Object[] { "dataMap is null or invalid" });
      a(9999, "internal error", "");
      finish();
    } else if (MiniAppService.inst().getFacialVerifyDepend() != null) {
      new Object(this) {
        
        };
    } else {
      AppBrandLogger.e("OnePixelFacialVerifyActivity", new Object[] { "facialVerify depend not ready" });
      a(9999, "internal error", "");
      finish();
    } 
    ActivityAgent.onTrace("com.ss.android.ugc.aweme.miniapp.facialverify.OnePixelFacialVerifyActivity", "onCreate", false);
  }
  
  protected void onRestart() {
    super.onRestart();
    if (!this.a) {
      if (b.a((Context)this, "android.permission.CAMERA") != 0) {
        a(3000, "no camera permission", "");
      } else {
        a(4998, "user cancel", "");
      } 
      finish();
    } 
  }
  
  public void onResume() {
    ActivityAgent.onTrace("com.ss.android.ugc.aweme.miniapp.facialverify.OnePixelFacialVerifyActivity", "onResume", true);
    super.onResume();
    ActivityAgent.onTrace("com.ss.android.ugc.aweme.miniapp.facialverify.OnePixelFacialVerifyActivity", "onResume", false);
  }
  
  public void onWindowFocusChanged(boolean paramBoolean) {
    ActivityAgent.onTrace("com.ss.android.ugc.aweme.miniapp.facialverify.OnePixelFacialVerifyActivity", "onWindowFocusChanged", true);
    super.onWindowFocusChanged(paramBoolean);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\ss\androi\\ugc\aweme\miniapp\facialverify\OnePixelFacialVerifyActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */