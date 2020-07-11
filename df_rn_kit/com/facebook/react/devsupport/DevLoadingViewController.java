package com.facebook.react.devsupport;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.a;
import com.facebook.common.e.a;
import com.facebook.react.bridge.UiThreadUtil;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

public class DevLoadingViewController {
  private static final int COLOR_DARK_GREEN = Color.parseColor("#035900");
  
  private static boolean sEnabled = true;
  
  private final Context mContext;
  
  private PopupWindow mDevLoadingPopup;
  
  public final TextView mDevLoadingView;
  
  private final ReactInstanceManagerDevHelper mReactInstanceManagerHelper;
  
  public DevLoadingViewController(Context paramContext, ReactInstanceManagerDevHelper paramReactInstanceManagerDevHelper) {
    this.mContext = paramContext;
    this.mReactInstanceManagerHelper = paramReactInstanceManagerDevHelper;
    this.mDevLoadingView = (TextView)((LayoutInflater)this.mContext.getSystemService("layout_inflater")).inflate(1980039168, null);
  }
  
  public static void setDevLoadingEnabled(boolean paramBoolean) {
    sEnabled = paramBoolean;
  }
  
  public void hide() {
    if (!sEnabled)
      return; 
    UiThreadUtil.runOnUiThread(new Runnable() {
          public void run() {
            DevLoadingViewController.this.hideInternal();
          }
        });
  }
  
  public void hideInternal() {
    PopupWindow popupWindow = this.mDevLoadingPopup;
    if (popupWindow != null && popupWindow.isShowing()) {
      _lancet.com_ss_android_ugc_aweme_lancet_DebugCheckLancet_popupWindowDismiss(this.mDevLoadingPopup);
      this.mDevLoadingPopup = null;
    } 
  }
  
  public void show() {
    if (!sEnabled)
      return; 
    UiThreadUtil.runOnUiThread(new Runnable() {
          public void run() {
            DevLoadingViewController.this.showInternal();
          }
        });
  }
  
  public void showForRemoteJSEnabled() {
    showMessage(this.mContext.getString(1980104725), -1, COLOR_DARK_GREEN);
  }
  
  public void showForUrl(String paramString) {
    try {
      URL uRL = new URL(paramString);
      Context context = this.mContext;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(uRL.getHost());
      stringBuilder.append(":");
      stringBuilder.append(uRL.getPort());
      showMessage(context.getString(1980104718, new Object[] { stringBuilder.toString() }), -1, COLOR_DARK_GREEN);
      return;
    } catch (MalformedURLException malformedURLException) {
      StringBuilder stringBuilder = new StringBuilder("Bundle url format is invalid. \n\n");
      stringBuilder.append(malformedURLException.toString());
      a.c("ReactNative", stringBuilder.toString());
      return;
    } 
  }
  
  public void showInternal() {
    boolean bool;
    PopupWindow popupWindow = this.mDevLoadingPopup;
    if (popupWindow != null && popupWindow.isShowing())
      return; 
    Activity activity = this.mReactInstanceManagerHelper.getCurrentActivity();
    if (activity == null) {
      a.c("ReactNative", "Unable to display loading message because react activity isn't available");
      return;
    } 
    if (Build.VERSION.SDK_INT <= 19) {
      Rect rect = new Rect();
      activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
      bool = rect.top;
    } else {
      bool = false;
    } 
    this.mDevLoadingPopup = new PopupWindow((View)this.mDevLoadingView, -1, -2);
    this.mDevLoadingPopup.setTouchable(false);
    this.mDevLoadingPopup.showAtLocation(activity.getWindow().getDecorView(), 0, 0, bool);
  }
  
  public void showMessage(final String message, final int color, final int backgroundColor) {
    if (!sEnabled)
      return; 
    UiThreadUtil.runOnUiThread(new Runnable() {
          public void run() {
            DevLoadingViewController.this.mDevLoadingView.setBackgroundColor(backgroundColor);
            DevLoadingViewController.this.mDevLoadingView.setText(message);
            DevLoadingViewController.this.mDevLoadingView.setTextColor(color);
            DevLoadingViewController.this.showInternal();
          }
        });
  }
  
  public void updateProgress(final String status, final Integer done, final Integer total) {
    if (!sEnabled)
      return; 
    UiThreadUtil.runOnUiThread(new Runnable() {
          public void run() {
            StringBuilder stringBuilder = new StringBuilder();
            String str = status;
            if (str == null)
              str = "Loading"; 
            stringBuilder.append(str);
            if (done != null) {
              Integer integer = total;
              if (integer != null && integer.intValue() > 0)
                stringBuilder.append(a.a(Locale.getDefault(), " %.1f%% (%d/%d)", new Object[] { Float.valueOf(this.val$done.intValue() / this.val$total.intValue() * 100.0F), this.val$done, this.val$total })); 
            } 
            stringBuilder.append("…");
            DevLoadingViewController.this.mDevLoadingView.setText(stringBuilder);
          }
        });
  }
  
  class DevLoadingViewController {}
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\devsupport\DevLoadingViewController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */