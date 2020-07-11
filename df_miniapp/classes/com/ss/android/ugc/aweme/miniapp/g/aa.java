package com.ss.android.ugc.aweme.miniapp.g;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.c;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.bytedance.common.utility.p;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapp.util.DevicesUtil;
import com.tt.miniapp.util.ToolUtils;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.entity.FeignHostConfig;
import com.tt.option.y.a;

public class aa extends a {
  public void initFeignHostConfig(Context paramContext) {
    FeignHostConfig feignHostConfig = FeignHostConfig.inst();
    feignHostConfig.setAppCapsuleDrawable((Drawable)new ColorDrawable(0)).setAppCapsuleBlackDrawable((Drawable)new ColorDrawable(0)).setAppLoadingCapsuleDrawable((Drawable)new ColorDrawable(0)).setAppCloseIconDrawable(c.a(paramContext, 2097479821)).setAppMoreMenuDrawable(c.a(paramContext, 2097479823)).setAppCloseIconBlackDrawable(c.a(paramContext, 2097479820)).setAppMoreMenuBlackDrawable(c.a(paramContext, 2097479822)).setAppLoadingCloseIconDrawable(c.a(paramContext, 2097479821)).setAppLoadingMoreMenuDrawable(c.a(paramContext, 2097479823));
    FrameLayout frameLayout = new FrameLayout(paramContext);
    View view = LayoutInflater.from(paramContext).inflate(2097676361, null);
    view.findViewById(2097545236).setVisibility(0);
    Handler handler = new Handler(Looper.getMainLooper());
    feignHostConfig.setAppLaunchLoadingView((View)frameLayout).setAppLaunchLoadingListener(new FeignHostConfig.LaunchLoadingListener(this, frameLayout, view, handler, paramContext) {
          public final void onLoadingEnd() {
            if (Looper.getMainLooper() == Looper.myLooper()) {
              this.a.removeView(this.b);
              return;
            } 
            this.c.post(new Runnable(this) {
                  public final void run() {
                    this.a.a.removeView(this.a.b);
                  }
                });
          }
          
          public final void onLoadingFail(String param1String) {
            ThreadUtil.runOnUIThread(new Runnable(this, param1String) {
                  public final void run() {
                    this.b.a.removeView(this.b.b);
                    aa aa = this.b.e;
                    Context context = this.b.d;
                    FrameLayout frameLayout = this.b.a;
                    String str = this.a;
                    if (context != null && frameLayout != null) {
                      if (TextUtils.isEmpty(str))
                        return; 
                      View view = LayoutInflater.from(context).inflate(2097676363, null);
                      FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -2);
                      layoutParams.gravity = 16;
                      view.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
                      frameLayout.addView(view);
                      TextView textView = (TextView)frameLayout.findViewById(2097545497);
                      float f1 = DevicesUtil.getScreenWidth(context) - p.b(context, 66.0F) * 2.0F;
                      TextPaint textPaint = textView.getPaint();
                      String str1 = context.getString(2097742068);
                      float f2 = textPaint.measureText("  ");
                      float f3 = textPaint.measureText(str);
                      if (textPaint.measureText(str1) < f1 - f3 % f1 - f2) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(str);
                        stringBuilder.append("  ");
                        stringBuilder.append(str1);
                        str = stringBuilder.toString();
                      } else {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(str);
                        stringBuilder.append("\n");
                        stringBuilder.append(str1);
                        str = stringBuilder.toString();
                      } 
                      SpannableString spannableString = new SpannableString(str);
                      spannableString.setSpan(new ClickableSpan(aa, context) {
                            public final void onClick(View param1View) {
                              ToolUtils.onActivityExit((Activity)AppbrandContext.getInst().getCurrentActivity(), 13);
                            }
                            
                            public final void updateDrawState(TextPaint param1TextPaint) {
                              param1TextPaint.setColor(c.c(this.a, 2097348697));
                              param1TextPaint.setUnderlineText(false);
                            }
                          }str.length() - str1.length(), str.length(), 33);
                      textView.setHighlightColor(0);
                      textView.setText((CharSequence)spannableString);
                      textView.setMovementMethod(LinkMovementMethod.getInstance());
                    } 
                  }
                });
          }
          
          public final void onLoadingStart() {
            if (Looper.getMainLooper() == Looper.myLooper()) {
              this.a.addView(this.b);
              return;
            } 
            this.c.post(new Runnable(this) {
                  public final void run() {
                    this.a.a.addView(this.a.b);
                  }
                });
          }
        });
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\ss\androi\\ugc\aweme\miniapp\g\aa.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */