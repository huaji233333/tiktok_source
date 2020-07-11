package com.tt.miniapp.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import com.tt.miniapp.ImmersedStatusBarHelper;
import com.tt.miniapp.manager.SnapshotManager;
import com.tt.miniapp.process.AppProcessManager;
import com.tt.miniapp.process.bridge.InnerMiniAppProcessBridge;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapp.util.ActivityUtil;
import com.tt.miniapp.util.BaseNavBarUtils;
import com.tt.miniapp.util.DevicesUtil;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.miniapphost.process.callback.IpcCallback;
import com.tt.miniapphost.process.data.CrossProcessDataEntity;
import com.tt.miniapphost.process.helper.AsyncIpcHandler;
import com.tt.miniapphost.util.DebugUtil;
import com.tt.miniapphost.util.ProcessUtil;
import com.tt.miniapphost.util.UIUtils;
import com.tt.miniapphost.view.BaseActivity;

public class OpenSchemaMiddleActivity extends BaseActivity {
  @Deprecated
  public static final String PARAMS_CLASS_NAME = "class_name";
  
  private boolean mAddSecureFlag = false;
  
  private Runnable mAutoShowMiniAppRunnable = new Runnable() {
      public void run() {
        OpenSchemaMiddleActivity.this.tryShowMiniAppActivity();
      }
    };
  
  public boolean mFirstResume = true;
  
  public String mFromAppId;
  
  private boolean mFromAppInHostStack;
  
  private boolean mFromMiniGame = false;
  
  public IpcCallback mGetSnapshotIpcCallback;
  
  public String mLaunchFlag;
  
  public final Object mMeasureLock = new Object();
  
  public boolean mMovingMiniAppActivity;
  
  public boolean mTriggerResumeWhenNewShow;
  
  public Runnable mUpdateSnapshotRunnable;
  
  private void configWindow() {
    if (this.mFromMiniGame) {
      requestWindowFeature(1);
      getWindow().setFlags(1024, 1024);
      getWindow().setSoftInputMode(16);
      if (DevicesUtil.hasNotchInScreen((Context)this))
        DevicesUtil.setFullScreenWindowLayoutInDisplayCutout(getWindow()); 
      if (Build.VERSION.SDK_INT >= 28) {
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.layoutInDisplayCutoutMode = 1;
        getWindow().setAttributes(layoutParams);
      } 
      BaseNavBarUtils.hideNavigation((Activity)this);
      ActivityUtil.setFullScreen((Activity)this);
      return;
    } 
    ImmersedStatusBarHelper immersedStatusBarHelper = new ImmersedStatusBarHelper((Activity)this, new ImmersedStatusBarHelper.ImmersedStatusBarConfig());
    immersedStatusBarHelper.setup(true);
    immersedStatusBarHelper.setUseLightStatusBarInternal(true);
  }
  
  private void generateSnapShotView(String paramString) {
    final View contentView = new View((Context)this);
    view.setBackgroundColor(-1);
    setContentView(view);
    view.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            if (OpenSchemaMiddleActivity.this.mTriggerResumeWhenNewShow) {
              AppBrandLogger.i("OpenSchemaMiddleActivity", new Object[] { "点击快照视图跳回小程序页面 mFromAppId:", this.this$0.mFromAppId });
              OpenSchemaMiddleActivity.this.showMiniAppActivityOnFront();
            } 
          }
        });
    AppProcessManager.ProcessInfo processInfo = AppProcessManager.getProcessInfoByAppId(paramString);
    if (processInfo == null) {
      AppBrandLogger.e("OpenSchemaMiddleActivity", new Object[] { "获取触发 openSchema 的小程序进程信息异常" });
      return;
    } 
    this.mGetSnapshotIpcCallback = new IpcCallback() {
        public void onIpcCallback(CrossProcessDataEntity param1CrossProcessDataEntity) {
          AppBrandLogger.i("OpenSchemaMiddleActivity", new Object[] { "收到小程序进程快照回调" });
          finishListenIpcCallback();
          if (param1CrossProcessDataEntity != null) {
            String str = param1CrossProcessDataEntity.getString("snapshot");
          } else {
            param1CrossProcessDataEntity = null;
          } 
          if (TextUtils.isEmpty((CharSequence)param1CrossProcessDataEntity)) {
            AppBrandLogger.e("OpenSchemaMiddleActivity", new Object[] { "小程序进程快照回调中不包含快照信息" });
            return;
          } 
          try {
            if (contentView.getHeight() == 0)
              synchronized (OpenSchemaMiddleActivity.this.mMeasureLock) {
                OpenSchemaMiddleActivity.this.forceMeasureContentView(contentView);
                OpenSchemaMiddleActivity.this.mMeasureLock.wait();
              }  
            int i = contentView.getWidth();
            int j = contentView.getHeight();
            final BitmapDrawable snapshotDrawable = SnapshotManager.getSnapshotDrawableFromFile(OpenSchemaMiddleActivity.this.getResources(), (String)param1CrossProcessDataEntity, i, j);
            AppBrandLogger.i("OpenSchemaMiddleActivity", new Object[] { "生成快照视图" });
            OpenSchemaMiddleActivity.this.mUpdateSnapshotRunnable = new Runnable() {
                public void run() {
                  AppBrandLogger.i("OpenSchemaMiddleActivity", new Object[] { "设置页面快照" });
                  contentView.setBackground((Drawable)snapshotDrawable);
                }
              };
            ThreadUtil.runOnUIThread(OpenSchemaMiddleActivity.this.mUpdateSnapshotRunnable);
          } catch (Exception exception) {
            AppBrandLogger.eWithThrowable("OpenSchemaMiddleActivity", "setSnapshotAsBackground", exception);
          } 
          OpenSchemaMiddleActivity.this.mGetSnapshotIpcCallback = null;
        }
        
        public void onIpcConnectError() {
          OpenSchemaMiddleActivity.this.mGetSnapshotIpcCallback = null;
        }
      };
    InnerMiniAppProcessBridge.getSnapshot(processInfo.mProcessIdentity, this.mGetSnapshotIpcCallback);
  }
  
  private boolean isOpenSchemaInCurrentStack() {
    return "currentTask".equalsIgnoreCase(this.mLaunchFlag);
  }
  
  private boolean isSchemaPageInHostStack() {
    return "newTask".equalsIgnoreCase(this.mLaunchFlag);
  }
  
  private boolean openSchema() {
    boolean bool1;
    boolean bool2;
    String str1 = getIntent().getStringExtra("schema");
    String str2 = getIntent().getStringExtra("args");
    AsyncIpcHandler asyncIpcHandler = ProcessUtil.generateAsyncIpcHandlerFromUri(Uri.parse(str1));
    if (HostDependManager.getInst().openSchema((Context)this, str1) || HostDependManager.getInst().openSchema((Context)this, str1, str2)) {
      if (HostDependManager.getInst().isEnableOpenSchemaAnimation())
        overridePendingTransition(UIUtils.getSlideInAnimation(), 2131034242); 
      bool2 = true;
      bool1 = false;
    } else {
      bool2 = false;
      bool1 = true;
    } 
    if (asyncIpcHandler != null) {
      asyncIpcHandler.callback(CrossProcessDataEntity.Builder.create().put("openSchemaResult", Boolean.valueOf(bool2)).put("openSchemaFailType", Integer.valueOf(bool1)).build());
    } else {
      DebugUtil.outputError("OpenSchemaMiddleActivity", new Object[] { "asyncIpcHandler ==null" });
    } 
    AppBrandLogger.i("OpenSchemaMiddleActivity", new Object[] { "openSchema schema:", str1, "openSchemaSuccess:", Boolean.valueOf(bool2) });
    return bool2;
  }
  
  public void finishCurrentActivity() {
    if (isFinishing())
      return; 
    AppBrandLogger.i("OpenSchemaMiddleActivity", new Object[] { "finishCurrentActivity mFromAppId:", this.mFromAppId });
    if (ActivityUtil.isTaskSingleActivity((Activity)this)) {
      finishAndRemoveTask();
      return;
    } 
    finish();
  }
  
  public void forceMeasureContentView(final View contentView) {
    AppBrandLogger.i("OpenSchemaMiddleActivity", new Object[] { "forceMeasureContentView" });
    ThreadUtil.runOnUIThread(new Runnable() {
          public void run() {
            FrameLayout frameLayout = (FrameLayout)OpenSchemaMiddleActivity.this.getWindow().getDecorView();
            contentView.measure(View.MeasureSpec.makeMeasureSpec(frameLayout.getMeasuredWidth(), -2147483648), View.MeasureSpec.makeMeasureSpec(frameLayout.getMeasuredHeight(), -2147483648));
            synchronized (OpenSchemaMiddleActivity.this.mMeasureLock) {
              OpenSchemaMiddleActivity.this.mMeasureLock.notifyAll();
              return;
            } 
          }
        });
  }
  
  public void onBackPressed() {
    AppBrandLogger.i("OpenSchemaMiddleActivity", new Object[] { "onBackPressed" });
    tryShowMiniAppActivity();
  }
  
  public void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    AppBrandLogger.i("OpenSchemaMiddleActivity", new Object[] { "onCreate" });
    this.mLaunchFlag = getIntent().getStringExtra("launch_flag");
    this.mFromAppId = getIntent().getStringExtra("from_app_id");
    this.mFromAppInHostStack = getIntent().getBooleanExtra("is_from_app_in_host_stack", this.mFromAppInHostStack);
    this.mFromMiniGame = getIntent().getBooleanExtra("is_game", false);
    configWindow();
    if (!openSchema()) {
      tryShowMiniAppActivity();
    } else {
      ThreadUtil.runOnUIThread(this.mAutoShowMiniAppRunnable, 5000L);
    } 
    generateSnapShotView(this.mFromAppId);
  }
  
  public void onDestroy() {
    super.onDestroy();
    AppBrandLogger.i("OpenSchemaMiddleActivity", new Object[] { "onDestroy" });
    if (this.mAddSecureFlag)
      getWindow().clearFlags(8192); 
    ThreadUtil.cancelUIRunnable(this.mUpdateSnapshotRunnable);
    ThreadUtil.cancelUIRunnable(this.mAutoShowMiniAppRunnable);
    IpcCallback ipcCallback = this.mGetSnapshotIpcCallback;
    if (ipcCallback != null) {
      ipcCallback.finishListenIpcCallback();
      this.mGetSnapshotIpcCallback = null;
    } 
  }
  
  public void onEnterAnimationComplete() {
    super.onEnterAnimationComplete();
    if (this.mTriggerResumeWhenNewShow) {
      AppBrandLogger.i("OpenSchemaMiddleActivity", new Object[] { "onEnterAnimationComplete tryShowMiniAppActivity mFromAppId:", this.mFromAppId });
      tryShowMiniAppActivity();
    } 
  }
  
  public void onPause() {
    super.onPause();
    AppBrandLogger.i("OpenSchemaMiddleActivity", new Object[] { "onPause" });
    ThreadUtil.cancelUIRunnable(this.mAutoShowMiniAppRunnable);
    if (isSchemaPageInHostStack())
      ThreadUtil.runOnUIThread(new Runnable() {
            public void run() {
              OpenSchemaMiddleActivity.this.finishCurrentActivity();
            }
          },  300L); 
  }
  
  public void onResume() {
    super.onResume();
    AppBrandLogger.i("OpenSchemaMiddleActivity", new Object[] { "onResume" });
    if (this.mFromMiniGame)
      ActivityUtil.setFullScreen((Activity)this); 
    if (this.mFirstResume) {
      this.mFirstResume = false;
      return;
    } 
    this.mTriggerResumeWhenNewShow = true;
    getWindow().addFlags(8192);
    this.mAddSecureFlag = true;
    ThreadUtil.runOnUIThread(new Runnable() {
          public void run() {
            AppBrandLogger.i("OpenSchemaMiddleActivity", new Object[] { "onResume tryShowMiniAppActivity mFromAppId:", this.this$0.mFromAppId });
            OpenSchemaMiddleActivity.this.tryShowMiniAppActivity();
          }
        }500L);
  }
  
  public void onStop() {
    super.onStop();
    AppBrandLogger.i("OpenSchemaMiddleActivity", new Object[] { "onStop" });
  }
  
  public void showMiniAppActivityOnFront() {
    AppBrandLogger.d("OpenSchemaMiddleActivity", new Object[] { "showMiniAppActivityOnFront mFromAppId:", this.mFromAppId });
    if (!isOpenSchemaInCurrentStack() && !isSchemaPageInHostStack() && !this.mFromAppInHostStack) {
      this.mMovingMiniAppActivity = ActivityUtil.moveMiniAppActivityToFront((Activity)this, this.mFromAppId);
      AppBrandLogger.i("OpenSchemaMiddleActivity", new Object[] { "moveMiniAppActivityToFront mFromAppId:", this.mFromAppId });
    } 
    finishCurrentActivity();
    if (!this.mMovingMiniAppActivity)
      ActivityUtil.changeToSilentHideActivityAnimation((Activity)this); 
  }
  
  public void tryShowMiniAppActivity() {
    if (this.mMovingMiniAppActivity || isFinishing()) {
      AppBrandLogger.i("OpenSchemaMiddleActivity", new Object[] { "tryShowMiniAppActivity mMovingMiniAppActivity || isFinishing()" });
      return;
    } 
    long l = System.currentTimeMillis();
    if (ActivityUtil.isActivityAtHostStackTop((Activity)this))
      showMiniAppActivityOnFront(); 
    AppBrandLogger.i("OpenSchemaMiddleActivity", new Object[] { "tryShowMiniAppActivity duration:", Long.valueOf(System.currentTimeMillis() - l) });
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\activity\OpenSchemaMiddleActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */