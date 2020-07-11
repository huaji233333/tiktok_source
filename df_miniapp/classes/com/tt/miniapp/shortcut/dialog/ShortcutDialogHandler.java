package com.tt.miniapp.shortcut.dialog;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.tt.miniapp.view.interpolator.CubicBezierInterpolator;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.util.UIUtils;

public class ShortcutDialogHandler {
  private static void adjustTitleAndContent(final Context context, final View dialogView, final TextView tvTitle, final ScrollView svContentContainer, final LinearLayout llButtonsContainer, final TextView tvContent, boolean paramBoolean1, boolean paramBoolean2) {
    if (!paramBoolean1)
      tvTitle.setVisibility(8); 
    if (!paramBoolean2)
      tvContent.setVisibility(8); 
    tvTitle.post(new Runnable() {
          public final void run() {
            tvContent.post(new Runnable() {
                  public void run() {
                    int i = UIUtils.px2dip(context, dialogView.getMeasuredHeight());
                    int j = UIUtils.px2dip(context, llButtonsContainer.getMeasuredHeight());
                    int k = tvTitle.getLineCount();
                    int m = tvContent.getLineCount();
                    if (k > 0 && m == 0) {
                      svContentContainer.setPadding(svContentContainer.getPaddingLeft(), 0, svContentContainer.getPaddingRight(), svContentContainer.getPaddingBottom());
                      i = (i - j - UIUtils.px2dip(context, (tvTitle.getMeasuredHeight() - tvTitle.getPaddingTop() - tvTitle.getPaddingBottom()))) / 2;
                      TextView textView = tvTitle;
                      j = tvTitle.getPaddingLeft();
                      Context context = context;
                      float f = i;
                      textView.setPadding(j, (int)UIUtils.dip2Px(context, f), tvTitle.getPaddingRight(), (int)UIUtils.dip2Px(context, f));
                      return;
                    } 
                    if (k == 0 && (m == 1 || m == 2)) {
                      svContentContainer.setPadding(svContentContainer.getPaddingLeft(), 0, svContentContainer.getPaddingRight(), svContentContainer.getPaddingBottom());
                      i = (i - j - UIUtils.px2dip(context, (tvContent.getMeasuredHeight() - tvContent.getPaddingTop() - tvContent.getPaddingBottom()))) / 2;
                      TextView textView = tvContent;
                      j = tvContent.getPaddingLeft();
                      Context context = context;
                      float f = i;
                      textView.setPadding(j, (int)UIUtils.dip2Px(context, f), tvContent.getPaddingRight(), (int)UIUtils.dip2Px(context, f));
                    } 
                  }
                });
          }
        });
  }
  
  private static boolean hasContent(DialogConfig paramDialogConfig) {
    if (paramDialogConfig == null)
      return false; 
    MultiSpanView multiSpanView = paramDialogConfig.content;
    return (multiSpanView == null) ? false : ((multiSpanView.getSpans() != null && !multiSpanView.getSpans().isEmpty()));
  }
  
  private static boolean hasTitle(DialogConfig paramDialogConfig) {
    if (paramDialogConfig == null)
      return false; 
    SingleSpanView singleSpanView = paramDialogConfig.title;
    if (singleSpanView == null)
      return false; 
    TextSpan textSpan = singleSpanView.getSpan();
    return (textSpan != null && !TextUtils.isEmpty(textSpan.getText()));
  }
  
  private static void initShortcutAnim(final CallBackListener callBack, final Dialog dialog, View paramView, TextView paramTextView1, TextView paramTextView2) {
    CubicBezierInterpolator cubicBezierInterpolator = new CubicBezierInterpolator(0.14D, 1.0D, 0.34D, 1.0D);
    ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(paramView, "scaleX", new float[] { 0.0F, 1.0F }).setDuration(350L);
    ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(paramView, "scaleY", new float[] { 0.0F, 1.0F }).setDuration(350L);
    ObjectAnimator objectAnimator4 = ObjectAnimator.ofFloat(paramView, "alpha", new float[] { 0.0F, 1.0F }).setDuration(100L);
    final AnimatorSet dialogViewEnterAnimSet = new AnimatorSet();
    animatorSet2.play((Animator)objectAnimator4).with((Animator)objectAnimator2).with((Animator)objectAnimator3);
    animatorSet2.setInterpolator((TimeInterpolator)cubicBezierInterpolator);
    dialog.setOnShowListener(new DialogInterface.OnShowListener() {
          public final void onShow(DialogInterface param1DialogInterface) {
            dialogViewEnterAnimSet.start();
          }
        });
    objectAnimator2 = ObjectAnimator.ofFloat(paramView, "scaleX", new float[] { 1.0F, 0.0F }).setDuration(450L);
    objectAnimator3 = ObjectAnimator.ofFloat(paramView, "scaleY", new float[] { 1.0F, 0.0F }).setDuration(450L);
    ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(paramView, "alpha", new float[] { 1.0F, 0.0F }).setDuration(150L);
    objectAnimator1.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter() {
          public final void onAnimationEnd(Animator param1Animator) {
            super.onAnimationEnd(param1Animator);
            AppbrandContext.mainHandler.post(new Runnable() {
                  public void run() {
                    _lancet.com_ss_android_ugc_aweme_lancet_DebugCheckLancet_dialogDismiss(dialog);
                  }
                  
                  class null {}
                });
          }
        });
    final AnimatorSet dialogViewExitAnimSet = new AnimatorSet();
    animatorSet1.play((Animator)objectAnimator1).with((Animator)objectAnimator2).with((Animator)objectAnimator3);
    animatorSet1.setInterpolator((TimeInterpolator)cubicBezierInterpolator);
    paramTextView2.setOnClickListener(new View.OnClickListener() {
          public final void onClick(View param1View) {
            ShortcutDialogHandler.CallBackListener callBackListener = callBack;
            if (callBackListener != null)
              callBackListener.confirm(); 
            dialogViewExitAnimSet.start();
          }
        });
    paramTextView1.setOnClickListener(new View.OnClickListener() {
          public final void onClick(View param1View) {
            ShortcutDialogHandler.CallBackListener callBackListener = callBack;
            if (callBackListener != null)
              callBackListener.cancel(); 
            dialogViewExitAnimSet.start();
          }
        });
  }
  
  public static Dialog showDialogByConfig(DialogConfig paramDialogConfig, CallBackListener paramCallBackListener) {
    byte b;
    if (paramDialogConfig == null)
      return null; 
    Context context = paramDialogConfig.context;
    if (!(context instanceof Activity))
      return null; 
    if (paramCallBackListener != null)
      paramCallBackListener.mobEvent(); 
    boolean bool1 = hasTitle(paramDialogConfig);
    boolean bool2 = hasContent(paramDialogConfig);
    if (!bool1 && !bool2)
      return null; 
    Dialog dialog = new Dialog(context, 2097807367);
    View view = LayoutInflater.from(context).inflate(2097676349, null);
    dialog.setContentView(view);
    dialog.setCancelable(false);
    dialog.setCanceledOnTouchOutside(false);
    TextView textView1 = (TextView)view.findViewById(2097545428);
    ScrollView scrollView = (ScrollView)view.findViewById(2097545389);
    LinearLayout linearLayout = (LinearLayout)view.findViewById(2097545325);
    TextView textView2 = (TextView)view.findViewById(2097545420);
    TextView textView3 = (TextView)view.findViewById(2097545418);
    TextView textView4 = (TextView)view.findViewById(2097545419);
    SingleSpanView singleSpanView = paramDialogConfig.title;
    paramDialogConfig.applySpan(textView1, singleSpanView.getSpan());
    if (singleSpanView.isVisibility()) {
      b = 0;
    } else {
      b = 8;
    } 
    textView1.setVisibility(b);
    textView1.setGravity(singleSpanView.getAlign());
    MultiSpanView multiSpanView = paramDialogConfig.content;
    paramDialogConfig.applySpans(textView2, multiSpanView.getSpans());
    if (multiSpanView.isVisibility()) {
      b = 0;
    } else {
      b = 8;
    } 
    textView2.setVisibility(b);
    textView2.setGravity(multiSpanView.getAlign());
    adjustTitleAndContent(context, view, textView1, scrollView, linearLayout, textView2, bool1, bool2);
    paramDialogConfig.applySpan(textView4, paramDialogConfig.okBtn.getSpan());
    paramDialogConfig.applySpan(textView3, paramDialogConfig.cancelBtn.getSpan());
    initShortcutAnim(paramCallBackListener, dialog, view, textView3, textView4);
    Window window = dialog.getWindow();
    if (window != null) {
      WindowManager.LayoutParams layoutParams = window.getAttributes();
      if (layoutParams != null)
        layoutParams.gravity = 17; 
    } 
    Activity activity = (Activity)context;
    if (!activity.isFinishing() && !activity.isDestroyed()) {
      dialog.show();
      return dialog;
    } 
    return null;
  }
  
  public static interface CallBackListener {
    void cancel();
    
    void confirm();
    
    void mobEvent();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\shortcut\dialog\ShortcutDialogHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */