package com.tt.miniapp.guide.reenter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.f;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import com.tt.b.c;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.dialog.NoLeakDialog;
import com.tt.miniapp.event.Event;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapp.view.dialog.AlertDialogHelper;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.entity.AppInfoEntity;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.miniapphost.util.UIUtils;

public class ReenterDialog extends f {
  private static boolean sNotFirstShown;
  
  private TextView mBtn;
  
  private TextView mDesc;
  
  private DismissCallback mDismissCallback;
  
  private ImageView mImage;
  
  public void onActivityCreated(Bundle paramBundle) {
    super.onActivityCreated(paramBundle);
    View view = getView();
    if (view != null)
      AlertDialogHelper.adjustDialogViewSize(view.getContext(), view, true); 
  }
  
  public void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    setStyle(2, 2097807367);
  }
  
  public Dialog onCreateDialog(Bundle paramBundle) {
    NoLeakDialog noLeakDialog = new NoLeakDialog((Context)getActivity(), this.mTheme);
    noLeakDialog.setCancelable(false);
    noLeakDialog.setCanceledOnTouchOutside(true);
    noLeakDialog.setOnKeyListener(new NoBackKeyListener());
    return (Dialog)noLeakDialog;
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
    View view = paramLayoutInflater.inflate(2097676300, paramViewGroup, false);
    this.mImage = (ImageView)view.findViewById(2097545229);
    this.mDesc = (TextView)view.findViewById(2097545477);
    this.mBtn = (TextView)view.findViewById(2097545476);
    this.mBtn.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            AppBrandLogger.d("tma_ReenterDialog", new Object[] { "r64091: click guide dialog btn" });
            ReenterDialog.this.dismissAllowingStateLoss();
          }
        });
    return view;
  }
  
  public void onDestroyView() {
    View view = getView();
    if (view instanceof ViewGroup)
      ((ViewGroup)view).removeAllViews(); 
    super.onDestroyView();
    this.mImage = null;
    this.mBtn = null;
    this.mDesc = null;
    this.mDismissCallback = null;
  }
  
  public void onDialogClose() {
    Event.builder("mp_retain_guide_done").flush();
    if (this.mDismissCallback != null) {
      AppInfoEntity appInfoEntity = AppbrandApplicationImpl.getInst().getAppInfo();
      final DismissCallback callback = this.mDismissCallback;
      if (appInfoEntity != null && appInfoEntity.isGame() && !sNotFirstShown) {
        ThreadUtil.runOnUIThread(new Runnable() {
              public void run() {
                ReenterDialog.DismissCallback dismissCallback = callback;
                if (dismissCallback != null)
                  dismissCallback.onDismiss(); 
              }
            },  220L);
      } else {
        this.mDismissCallback.onDismiss();
      } 
    } 
    sNotFirstShown = true;
  }
  
  public void onDismiss(DialogInterface paramDialogInterface) {
    super.onDismiss(paramDialogInterface);
    onDialogClose();
  }
  
  public void onStart() {
    AppBrandLogger.d("tma_ReenterDialog", new Object[] { "r64091: onStart" });
    if (AppbrandApplicationImpl.getInst().getAppInfo().isGame()) {
      Window window = null;
      Dialog dialog = getDialog();
      if (dialog != null)
        window = dialog.getWindow(); 
      AlertDialogHelper.unfocused(window);
      super.onStart();
      AlertDialogHelper.focusable(window);
      return;
    } 
    super.onStart();
  }
  
  public void onViewCreated(View paramView, Bundle paramBundle) {
    super.onViewCreated(paramView, paramBundle);
    Bundle bundle = getArguments();
    if (bundle == null)
      return; 
    String str3 = bundle.getString("key_text_title", "");
    String str1 = bundle.getString("key_image_uri", "");
    String str2 = bundle.getString("key_btn_color", "");
    String str4 = bundle.getString("key_btn_text", "");
    Resources resources = getResources();
    try {
      this.mDesc.setText(str3);
      float f1 = resources.getDimensionPixelSize(2097414174);
      float f2 = resources.getDimensionPixelSize(2097414173);
      int[] arrayOfInt = AlertDialogHelper.getAdjustWidthAndHeight(this.mImage.getContext(), true);
      f2 /= f1;
      float f3 = UIUtils.dip2Px(this.mImage.getContext(), arrayOfInt[0]);
      HostDependManager.getInst().loadImage(this.mImage.getContext(), (new c(str1)).a((Drawable)new ColorDrawable(0)).a((int)f1, (int)(f2 * f3)).a((View)this.mImage));
      this.mBtn.setTextColor(Color.parseColor(str2));
      this.mBtn.setText(str4);
      return;
    } catch (RuntimeException runtimeException) {
      AppBrandLogger.eWithThrowable("tma_ReenterDialog", "r49403 dialog error", runtimeException);
      return;
    } 
  }
  
  public void setDismissCallback(DismissCallback paramDismissCallback) {
    this.mDismissCallback = paramDismissCallback;
  }
  
  public static interface DismissCallback {
    void onDismiss();
  }
  
  static final class NoBackKeyListener implements DialogInterface.OnKeyListener {
    private NoBackKeyListener() {}
    
    public final boolean onKey(DialogInterface param1DialogInterface, int param1Int, KeyEvent param1KeyEvent) {
      return (4 == param1Int);
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\guide\reenter\ReenterDialog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */