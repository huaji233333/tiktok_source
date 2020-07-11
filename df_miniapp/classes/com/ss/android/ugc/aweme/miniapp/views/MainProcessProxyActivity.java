package com.ss.android.ugc.aweme.miniapp.views;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.b;
import android.text.TextUtils;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import com.bytedance.apm.agent.v2.instrumentation.ActivityAgent;
import com.ss.android.ugc.aweme.miniapp.MiniAppService;
import com.ss.android.ugc.aweme.miniapp.utils.f;
import com.ss.android.ugc.aweme.miniapp_api.a.c;
import com.ss.android.ugc.aweme.miniapp_api.a.l;
import com.ss.android.ugc.aweme.miniapp_api.b.a;
import com.ss.android.ugc.aweme.miniapp_api.b.c;
import com.ss.android.ugc.aweme.miniapp_api.b.e;
import com.ss.android.ugc.aweme.miniapp_api.b.h;
import com.ss.android.ugc.aweme.miniapp_api.c;
import com.ss.android.ugc.aweme.miniapp_api.d;
import com.ss.android.ugc.aweme.miniapp_api.model.a.a;
import com.tt.miniapp.util.ActivityUtil;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.process.data.CrossProcessDataEntity;
import com.tt.miniapphost.process.helper.AsyncIpcHandler;
import com.tt.miniapphost.util.ProcessUtil;
import java.io.Serializable;

public class MainProcessProxyActivity extends Activity {
  public static final String a = MainProcessProxyActivity.class.getSimpleName();
  
  public AsyncIpcHandler b;
  
  public boolean c;
  
  private int d;
  
  private boolean e = true;
  
  private Dialog f;
  
  public final void a(DialogInterface paramDialogInterface, boolean paramBoolean) {
    if (this.b == null)
      this.b = ProcessUtil.generateAsyncIpcHandlerFromIntent(getIntent()); 
    AsyncIpcHandler asyncIpcHandler = this.b;
    if (asyncIpcHandler != null)
      asyncIpcHandler.callback(CrossProcessDataEntity.Builder.create().put("proxy_result", Boolean.valueOf(paramBoolean)).build()); 
    if (paramDialogInterface != null)
      paramDialogInterface.dismiss(); 
    finish();
  }
  
  public void finish() {
    super.finish();
    AppBrandLogger.i(a, new Object[] { Log.getStackTraceString(new Throwable()) });
  }
  
  public void onBackPressed() {
    super.onBackPressed();
    AppBrandLogger.i(a, new Object[] { Log.getStackTraceString(new Throwable()) });
  }
  
  protected void onCreate(Bundle paramBundle) {
    ActivityAgent.onTrace("com.ss.android.ugc.aweme.miniapp.views.MainProcessProxyActivity", "onCreate", true);
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
    c c = MiniAppService.inst().getBaseLibDepend();
    if (c != null) {
      String str;
      c.a("video_publish", new a(this) {
            public final void onEvent(a param1a) {
              if (this.a.b != null) {
                this.a.b.callback(CrossProcessDataEntity.Builder.create().put("proxy_result", Boolean.valueOf(param1a.isSuccess())).build());
                this.a.c = param1a.isSuccess();
                String str = MainProcessProxyActivity.a;
                StringBuilder stringBuilder = new StringBuilder("onEvent_publishResult: ");
                stringBuilder.append(param1a.isSuccess());
                AppBrandLogger.i(str, new Object[] { stringBuilder.toString() });
              } 
              AppBrandLogger.i(MainProcessProxyActivity.a, new Object[] { "onEvent_finish" });
              this.a.finish();
            }
          });
      this.d = getIntent().getIntExtra("proxy_type", 0);
      int i = this.d;
      if (i == 0) {
        String str2 = getIntent().getStringExtra("micro_app_schema");
        String str1 = getIntent().getStringExtra("enter_from");
        if (TextUtils.isEmpty(str2)) {
          finish();
        } else {
          c c1 = MiniAppService.inst().getBaseLibDepend();
          if (str1 == null) {
            str = "";
          } else {
            str = str1;
          } 
          c1.a(this, str, "click_mp", new h(this, str2, str1) {
                public final void a() {
                  f.a(this.a, this.b, "mp_login_success");
                  if (f.a((Context)this.c, this.a))
                    ActivityUtil.moveMiniAppActivityToFront(this.c, d.a(this.a)); 
                  this.c.setResult(-1);
                  this.c.finish();
                }
                
                public final void a(Bundle param1Bundle) {
                  f.a(this.a, this.b, "mp_login_close");
                  this.c.setResult(0);
                  this.c.finish();
                }
              });
        } 
      } else if (i == 1) {
        this.b = ProcessUtil.generateAsyncIpcHandlerFromIntent(getIntent());
        if (str.c()) {
          str.a((Context)this, getString(2097741825));
          finish();
        } else {
          MiniAppService.inst().getBaseLibDepend().c(this, getIntent());
        } 
      } else if (i == 2) {
        this.b = ProcessUtil.generateAsyncIpcHandlerFromIntent(getIntent());
        if (str.c()) {
          str.a((Context)this, getString(2097741825));
          finish();
        } else {
          MiniAppService.inst().getBaseLibDepend().a(this, getIntent());
        } 
      } else if (i == 3) {
        str = getIntent().getStringExtra("micro_app_schema");
        String str1 = getIntent().getStringExtra("enter_from");
        Bundle bundle = new Bundle();
        bundle.putParcelable("share_package", getIntent().getParcelableExtra("share_package"));
        this.b = ProcessUtil.generateAsyncIpcHandlerFromIntent(getIntent());
        MiniAppService.inst().getBaseLibDepend().a((Context)this, bundle, new e(this, str, str1) {
              public final void a(a param1a) {
                if ("doLogin".equals(param1a.getType())) {
                  f.a(this.a, this.b, "mp_login_success");
                  if (f.a((Context)this.c, this.a))
                    ActivityUtil.moveMiniAppActivityToFront(this.c, d.a(this.a)); 
                } 
                if (this.c.b != null)
                  this.c.b.callback(CrossProcessDataEntity.Builder.create().put("proxy_result", Boolean.valueOf(param1a.isSuccess())).build()); 
                this.c.finish();
              }
            });
      } else if (i == 4) {
        str = getIntent().getStringExtra("game_pay_url");
        l l = MiniAppService.inst().getRouterDepend();
        (c.a()).f = new c.a(this, l, str) {
          
          };
      } else if (i == 5) {
        this.b = ProcessUtil.generateAsyncIpcHandlerFromIntent(getIntent());
        MiniAppService.inst().getBaseLibDepend().a(this, "applet_authorize", null, new c(this) {
              public final void a(int param1Int1, int param1Int2, Object param1Object) {
                if (this.a.b == null)
                  return; 
                MiniAppService.inst().getConstantDepend();
                if (param1Int1 == 7) {
                  MiniAppService.inst().getConstantDepend();
                  if (param1Int2 == 1) {
                    this.a.b.callback(CrossProcessDataEntity.Builder.create().put("bindPhoneNumberResult", Boolean.valueOf(true)).build());
                    return;
                  } 
                  this.a.b.callback(CrossProcessDataEntity.Builder.create().put("bindPhoneNumberResult", Boolean.valueOf(false)).build());
                } 
              }
            });
      } else if (i == 6) {
        if (b.a((Context)this, "android.permission.READ_CONTACTS") == 0) {
          a((DialogInterface)null, true);
        } else {
          this.f = str.a((Context)this, new DialogInterface.OnClickListener(this) {
                public final void onClick(DialogInterface param1DialogInterface, int param1Int) {
                  if (-1 == param1Int) {
                    b.a(this.a, new String[] { "android.permission.READ_CONTACTS" }, 1);
                    param1DialogInterface.dismiss();
                    return;
                  } 
                  this.a.a(param1DialogInterface, false);
                }
              });
          Dialog dialog = this.f;
          if (dialog != null) {
            dialog.setOnCancelListener(new DialogInterface.OnCancelListener(this) {
                  public final void onCancel(DialogInterface param1DialogInterface) {
                    this.a.a(param1DialogInterface, false);
                  }
                });
            this.f.show();
          } else {
            a((DialogInterface)null, false);
          } 
        } 
      } 
    } 
    ActivityAgent.onTrace("com.ss.android.ugc.aweme.miniapp.views.MainProcessProxyActivity", "onCreate", false);
  }
  
  protected void onDestroy() {
    super.onDestroy();
    if (!this.c && this.b != null) {
      int i = this.d;
      if (i == 2 || i == 1) {
        this.b.callback(CrossProcessDataEntity.Builder.create().put("proxy_result", Boolean.valueOf(false)).build());
        AppBrandLogger.i(a, new Object[] { "onDestroy_publishResult: false" });
      } 
    } 
    if (MiniAppService.inst().getBaseLibDepend() != null) {
      MiniAppService.inst().getBaseLibDepend().a("video_publish");
      AppBrandLogger.i(a, new Object[] { "onDestroy_unregister" });
    } 
  }
  
  public void onRequestPermissionsResult(int paramInt, String[] paramArrayOfString, int[] paramArrayOfint) {
    boolean bool = true;
    if (1 == paramInt && paramArrayOfString.length > 0 && TextUtils.equals("android.permission.READ_CONTACTS", paramArrayOfString[0])) {
      if (paramArrayOfint[0] != 0)
        bool = false; 
      a((DialogInterface)null, bool);
      return;
    } 
    super.onRequestPermissionsResult(paramInt, paramArrayOfString, paramArrayOfint);
  }
  
  protected void onResume() {
    ActivityAgent.onTrace("com.ss.android.ugc.aweme.miniapp.views.MainProcessProxyActivity", "onResume", true);
    super.onResume();
    if (this.e) {
      this.e = false;
    } else {
      this.e = true;
      if (!isFinishing()) {
        if (this.d == 3)
          finish(); 
        if (this.d == 4) {
          c c = c.a();
          if (c.g != null) {
            Intent intent = new Intent();
            intent.putExtra("pay_key_result_params", (Serializable)c.g);
            setResult(-1, intent);
          } 
          finish();
        } 
        if (this.d == 5)
          finish(); 
        if (this.d == 0)
          finish(); 
        if (this.d == 1) {
          AppBrandLogger.i(a, new Object[] { "onResume_videoRecord_finish" });
          finish();
        } 
        if (this.d == 2) {
          AppBrandLogger.i(a, new Object[] { "onResume_videoCut_finish" });
          finish();
        } 
        if (this.d == 6) {
          a((DialogInterface)this.f, false);
          finish();
        } 
      } 
    } 
    ActivityAgent.onTrace("com.ss.android.ugc.aweme.miniapp.views.MainProcessProxyActivity", "onResume", false);
  }
  
  public void onWindowFocusChanged(boolean paramBoolean) {
    ActivityAgent.onTrace("com.ss.android.ugc.aweme.miniapp.views.MainProcessProxyActivity", "onWindowFocusChanged", true);
    super.onWindowFocusChanged(paramBoolean);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\ss\androi\\ugc\aweme\miniapp\views\MainProcessProxyActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */