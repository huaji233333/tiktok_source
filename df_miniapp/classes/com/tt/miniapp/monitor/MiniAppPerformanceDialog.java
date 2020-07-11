package com.tt.miniapp.monitor;

import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Debug;
import android.os.Process;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.debug.SwitchManager;
import com.tt.miniapp.launchcache.pkg.PkgService;
import com.tt.miniapp.storage.Storage;
import com.tt.miniapp.util.DevicesUtil;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.util.TimeMeter;
import com.tt.miniapphost.util.UIUtils;

public class MiniAppPerformanceDialog {
  private static Dialog mDialog;
  
  private static long sDownloadTime = -1L;
  
  private static long sLaunchTime = -1L;
  
  private static long sReRenderTime = -1L;
  
  private static long sRenderTime = -1L;
  
  private static void addDivider(ViewGroup paramViewGroup, Context paramContext) {
    View view = new View(paramContext);
    view.setBackgroundColor(paramContext.getResources().getColor(2097348629));
    view.setLayoutParams((ViewGroup.LayoutParams)new LinearLayout.LayoutParams(-1, (int)UIUtils.dip2Px(paramContext, 0.5F)));
    paramViewGroup.addView(view);
  }
  
  public static void dismissDialog() {
    Dialog dialog = mDialog;
    if (dialog != null && dialog.isShowing())
      _lancet.com_ss_android_ugc_aweme_lancet_DebugCheckLancet_dialogDismiss(mDialog); 
    ((SwitchManager)AppbrandApplicationImpl.getInst().getService(SwitchManager.class)).setPerformanceSwithOn(false);
  }
  
  private static void formatTextView(TextView paramTextView) {
    paramTextView.setTextSize(14.0F);
    paramTextView.setGravity(19);
    paramTextView.setTextColor(paramTextView.getContext().getResources().getColor(2097348658));
  }
  
  public static void saveLaunchTime(long paramLong) {
    sLaunchTime = paramLong;
  }
  
  public static void saveReRenderTime(long paramLong) {
    sReRenderTime = paramLong;
  }
  
  public static void saveRenderTime(long paramLong) {
    sRenderTime = paramLong;
  }
  
  public static void showPerformanceDialog(Context paramContext, final IDismissCallback callback) {
    StringBuffer stringBuffer2;
    View view = View.inflate(paramContext, 2097676351, null);
    LinearLayout linearLayout = (LinearLayout)view.findViewById(2097545349);
    linearLayout.setShowDividers(0);
    mDialog = new Dialog(paramContext, 2132607953);
    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, (int)UIUtils.dip2Px(paramContext, 38.0F));
    layoutParams.leftMargin = (int)UIUtils.dip2Px(paramContext, 14.0F);
    TextView textView2 = new TextView(paramContext);
    formatTextView(textView2);
    textView2.setTextSize(16.0F);
    textView2.setText(paramContext.getText(2097741972));
    linearLayout.addView((View)textView2, (ViewGroup.LayoutParams)layoutParams);
    addDivider((ViewGroup)linearLayout, paramContext);
    int i = MonitorInfoPackTask.getCpuRate();
    textView2 = new TextView(paramContext);
    StringBuffer stringBuffer5 = new StringBuffer(paramContext.getString(2097741964));
    if (i > 0) {
      stringBuffer5.append(i);
      stringBuffer5.append(" %");
    } else {
      stringBuffer5.append(" --");
    } 
    formatTextView(textView2);
    textView2.setText(stringBuffer5);
    linearLayout.addView((View)textView2, (ViewGroup.LayoutParams)layoutParams);
    ActivityManager activityManager = (ActivityManager)paramContext.getSystemService("activity");
    if (activityManager != null) {
      textView2 = new TextView(paramContext);
      Debug.MemoryInfo[] arrayOfMemoryInfo = activityManager.getProcessMemoryInfo(new int[] { Process.myPid() });
      StringBuffer stringBuffer = new StringBuffer(paramContext.getString(2097741973));
      stringBuffer.append((arrayOfMemoryInfo[0]).dalvikPss / 1024.0F);
      stringBuffer.append(" MB");
      textView2.setText(stringBuffer);
      formatTextView(textView2);
      linearLayout.addView((View)textView2, (ViewGroup.LayoutParams)layoutParams);
    } 
    textView2 = new TextView(paramContext);
    StringBuffer stringBuffer4 = new StringBuffer(paramContext.getString(2097741966));
    stringBuffer4.append(sLaunchTime);
    stringBuffer4.append(" ms");
    formatTextView(textView2);
    textView2.setText(stringBuffer4);
    linearLayout.addView((View)textView2, (ViewGroup.LayoutParams)layoutParams);
    textView2 = new TextView(paramContext);
    stringBuffer4 = new StringBuffer(paramContext.getString(2097741965));
    long l = TimeMeter.nowAfterStart(((PkgService)AppbrandApplicationImpl.getInst().getService(PkgService.class)).getDownloadTime());
    sDownloadTime = l;
    if (l > 0L) {
      stringBuffer4.append(sDownloadTime);
      stringBuffer4.append(" ms");
    } else {
      stringBuffer4.append(" --");
    } 
    formatTextView(textView2);
    textView2.setText(stringBuffer4);
    linearLayout.addView((View)textView2, (ViewGroup.LayoutParams)layoutParams);
    TextView textView3 = new TextView(paramContext);
    l = RouterMonitorTask.optLastRouterTime();
    if (l >= 0L) {
      stringBuffer2 = new StringBuffer(paramContext.getString(2097741970));
      stringBuffer2.append(l);
      stringBuffer2.append(" ms");
    } else {
      stringBuffer2 = new StringBuffer(paramContext.getString(2097741970));
      stringBuffer2.append("--");
    } 
    formatTextView(textView3);
    textView3.setText(stringBuffer2);
    linearLayout.addView((View)textView3, (ViewGroup.LayoutParams)layoutParams);
    TextView textView1 = new TextView(paramContext);
    StringBuffer stringBuffer3 = new StringBuffer(paramContext.getString(2097741967));
    stringBuffer3.append(MonitorInfoPackTask.getLastFps());
    stringBuffer3.append(" fps");
    formatTextView(textView1);
    textView1.setText(stringBuffer3);
    linearLayout.addView((View)textView1, (ViewGroup.LayoutParams)layoutParams);
    textView1 = new TextView(paramContext);
    stringBuffer3 = new StringBuffer(paramContext.getString(2097741969));
    l = sRenderTime;
    if (l >= 0L) {
      stringBuffer3.append(l);
      stringBuffer3.append(" ms");
    } else {
      stringBuffer3.append(" --");
    } 
    formatTextView(textView1);
    textView1.setText(stringBuffer3);
    linearLayout.addView((View)textView1, (ViewGroup.LayoutParams)layoutParams);
    textView1 = new TextView(paramContext);
    stringBuffer3 = new StringBuffer(paramContext.getString(2097741968));
    l = sReRenderTime;
    if (l >= 0L) {
      stringBuffer3.append(l);
      stringBuffer3.append(" ms");
    } else {
      stringBuffer3.append(" --");
    } 
    formatTextView(textView1);
    textView1.setText(stringBuffer3);
    linearLayout.addView((View)textView1, (ViewGroup.LayoutParams)layoutParams);
    textView1 = new TextView(paramContext);
    StringBuffer stringBuffer1 = new StringBuffer(paramContext.getString(2097741971));
    stringBuffer1.append(Storage.getStorageSize());
    stringBuffer1.append(" B");
    formatTextView(textView1);
    textView1.setText(stringBuffer1);
    linearLayout.addView((View)textView1, (ViewGroup.LayoutParams)layoutParams);
    mDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
          public final void onDismiss(DialogInterface param1DialogInterface) {
            callback.onDismiss();
          }
        });
    mDialog.setContentView(view);
    Window window = mDialog.getWindow();
    if (window != null) {
      if (TextUtils.equals(DevicesUtil.getBrand().toLowerCase(), "huawei") && AppbrandContext.getInst().isGame()) {
        window.setFlags(1024, 1024);
        window.getDecorView().setSystemUiVisibility(2822);
      } 
      WindowManager.LayoutParams layoutParams1 = window.getAttributes();
      layoutParams1.flags = 8;
      window.setAttributes(layoutParams1);
      window.setLayout(-1, -2);
      window.setGravity(80);
      window.setWindowAnimations(2132607943);
      window.getDecorView().setSystemUiVisibility(2304);
    } 
    mDialog.show();
    ((SwitchManager)AppbrandApplicationImpl.getInst().getService(SwitchManager.class)).setPerformanceSwithOn(true);
  }
  
  public static interface IDismissCallback {
    void onDismiss();
  }
  
  class MiniAppPerformanceDialog {}
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\monitor\MiniAppPerformanceDialog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */