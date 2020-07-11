package com.tt.miniapp.view.dialog;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.tt.miniapp.util.DevicesUtil;
import com.tt.miniapp.view.interpolator.CubicBezierInterpolator;
import com.tt.miniapphost.entity.NativeUIParamsEntity;
import com.tt.miniapphost.util.CharacterUtils;
import com.tt.miniapphost.util.UIUtils;

public class ModalDialog extends Dialog {
  private AnimatorSet enAnimSet;
  
  public AnimatorSet exitAnimSet;
  
  public OnNegativeBtnClickListener mOnNegativeBtnClickListener;
  
  public OnPositiveBtnClickListener mOnPositiveBtnClickListener;
  
  private ModalDialog(Builder paramBuilder) {
    super((Context)paramBuilder.mActivity, 2097807367);
    setCancelable(false);
    setCanceledOnTouchOutside(false);
    initView(paramBuilder);
  }
  
  private void initAnimations(View paramView) {
    this.enAnimSet = new AnimatorSet();
    this.enAnimSet.play((Animator)ObjectAnimator.ofFloat(paramView, "alpha", new float[] { 0.0F, 1.0F }).setDuration(100L)).with((Animator)ObjectAnimator.ofFloat(paramView, "scaleX", new float[] { 0.0F, 1.0F }).setDuration(350L)).with((Animator)ObjectAnimator.ofFloat(paramView, "scaleY", new float[] { 0.0F, 1.0F }).setDuration(350L));
    this.enAnimSet.setInterpolator((TimeInterpolator)new CubicBezierInterpolator(0.14D, 1.0D, 0.34D, 1.0D));
    this.exitAnimSet = new AnimatorSet();
    ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(paramView, "alpha", new float[] { 1.0F, 0.0F }).setDuration(150L);
    this.exitAnimSet.play((Animator)objectAnimator).with((Animator)ObjectAnimator.ofFloat(paramView, "scaleX", new float[] { 1.0F, 0.0F }).setDuration(450L)).with((Animator)ObjectAnimator.ofFloat(paramView, "scaleY", new float[] { 1.0F, 0.0F }).setDuration(450L));
    this.exitAnimSet.setInterpolator((TimeInterpolator)new CubicBezierInterpolator(0.14D, 1.0D, 0.34D, 1.0D));
    objectAnimator.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter() {
          public void onAnimationEnd(Animator param1Animator) {
            super.onAnimationEnd(param1Animator);
            ModalDialog.this.dismiss();
          }
        });
  }
  
  private void initButtons(View paramView, Builder paramBuilder) {
    this.mOnNegativeBtnClickListener = paramBuilder.mOnNegativeBtnClickListener;
    this.mOnPositiveBtnClickListener = paramBuilder.mOnPositiveBtnClickListener;
    TextView textView1 = (TextView)paramView.findViewById(2097545418);
    TextView textView2 = (TextView)paramView.findViewById(2097545419);
    View view = paramView.findViewById(2097545267);
    String str2 = CharacterUtils.trimString(paramBuilder.mPositiveBtnText, 8, false, null);
    String str1 = str2;
    if (TextUtils.isEmpty(str2))
      str1 = getContext().getText(2097741877).toString(); 
    textView2.setTextColor(Color.parseColor(NativeUIParamsEntity.getInst().getPositiveTextColor()));
    textView2.setText(str1);
    textView2.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            if (ModalDialog.this.mOnPositiveBtnClickListener != null)
              ModalDialog.this.mOnPositiveBtnClickListener.onClick(); 
            ModalDialog.this.exitAnimSet.start();
          }
        });
    str1 = CharacterUtils.trimString(paramBuilder.mNegativeBtnText, 8, false, null);
    if (TextUtils.isEmpty(str1)) {
      textView1.setVisibility(8);
      view.setVisibility(8);
    } 
    textView1.setTextColor(Color.parseColor(NativeUIParamsEntity.getInst().getNegativeTextColor()));
    textView1.setText(str1);
    textView1.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            if (ModalDialog.this.mOnNegativeBtnClickListener != null)
              ModalDialog.this.mOnNegativeBtnClickListener.onClick(); 
            ModalDialog.this.exitAnimSet.start();
          }
        });
  }
  
  private void initContent(View paramView, Builder paramBuilder) {
    float f;
    int i = TextUtils.isEmpty(paramBuilder.mTitle) ^ true;
    if (i != 0) {
      f = UIUtils.dip2Px((Context)paramBuilder.mActivity, 8.0F);
    } else {
      f = UIUtils.dip2Px((Context)paramBuilder.mActivity, 24.0F);
    } 
    int j = (int)f;
    ((LinearLayout.LayoutParams)paramView.findViewById(2097545389).getLayoutParams()).topMargin = j;
    TextView textView = (TextView)paramView.findViewById(2097545428);
    if (i != 0) {
      i = 0;
    } else {
      i = 8;
    } 
    textView.setVisibility(i);
    textView.setText(paramBuilder.mTitle);
    ((TextView)paramView.findViewById(2097545420)).setText(paramBuilder.mContent);
  }
  
  private void initView(Builder paramBuilder) {
    View view = View.inflate(getContext(), 2097676352, null);
    setContentView(view);
    initAnimations(view);
    initContent(view, paramBuilder);
    initButtons(view, paramBuilder);
    setRootViewSize(view, paramBuilder);
  }
  
  private void setRootViewSize(View paramView, Builder paramBuilder) {
    double d;
    Context context = getContext();
    if ((context.getResources().getConfiguration()).orientation == 2) {
      i = 1;
    } else {
      i = 0;
    } 
    float f2 = DevicesUtil.getScreenWidth(context) / context.getResources().getDimension(2097414168);
    ViewGroup.LayoutParams layoutParams = paramView.getLayoutParams();
    float f1 = context.getResources().getDimension(2097414169);
    if (!i)
      f1 *= f2; 
    int k = (int)f1;
    layoutParams.width = k;
    f1 = context.getResources().getDimension(2097414166);
    if (i) {
      d = DevicesUtil.getScreenHight(context);
      Double.isNaN(d);
      d *= 0.7D;
    } else {
      d = (f1 * f2);
    } 
    int j = (int)d;
    paramView.measure(View.MeasureSpec.makeMeasureSpec(k, -2147483648), View.MeasureSpec.makeMeasureSpec(0, 0));
    k = paramView.getMeasuredHeight();
    f1 = context.getResources().getDimension(2097414167);
    if (!i)
      f1 *= f2; 
    int i = (int)f1;
    if (k < i) {
      layoutParams.height = i;
      if (TextUtils.isEmpty(paramBuilder.mTitle)) {
        TextView textView = (TextView)paramView.findViewById(2097545420);
        ((LinearLayout.LayoutParams)findViewById(2097545389).getLayoutParams()).topMargin = 0;
        textView.setPadding(textView.getPaddingLeft(), textView.getPaddingTop(), textView.getPaddingRight(), 0);
        ((FrameLayout.LayoutParams)textView.getLayoutParams()).gravity = 17;
        return;
      } 
    } else {
      if (k > j) {
        layoutParams.height = j;
        return;
      } 
      layoutParams.height = -2;
    } 
  }
  
  public void show() {
    super.show();
    this.enAnimSet.start();
  }
  
  public static class Builder {
    public Activity mActivity;
    
    public String mContent;
    
    public String mNegativeBtnText;
    
    public ModalDialog.OnNegativeBtnClickListener mOnNegativeBtnClickListener;
    
    public ModalDialog.OnPositiveBtnClickListener mOnPositiveBtnClickListener;
    
    public String mPositiveBtnText;
    
    public String mTitle;
    
    private Builder(Activity param1Activity) {
      this.mActivity = param1Activity;
    }
    
    public static Builder builder(Activity param1Activity) {
      return new Builder(param1Activity);
    }
    
    public ModalDialog build() {
      return new ModalDialog(this);
    }
    
    public Builder content(String param1String) {
      this.mContent = param1String;
      return this;
    }
    
    public Builder negativeBtnText(String param1String) {
      this.mNegativeBtnText = param1String;
      return this;
    }
    
    public Builder onNegativeBtnClickListener(ModalDialog.OnNegativeBtnClickListener param1OnNegativeBtnClickListener) {
      this.mOnNegativeBtnClickListener = param1OnNegativeBtnClickListener;
      return this;
    }
    
    public Builder onPositiveBtnClickListener(ModalDialog.OnPositiveBtnClickListener param1OnPositiveBtnClickListener) {
      this.mOnPositiveBtnClickListener = param1OnPositiveBtnClickListener;
      return this;
    }
    
    public Builder positiveBtnText(String param1String) {
      this.mPositiveBtnText = param1String;
      return this;
    }
    
    public Builder title(String param1String) {
      this.mTitle = param1String;
      return this;
    }
  }
  
  public static interface OnNegativeBtnClickListener {
    void onClick();
  }
  
  public static interface OnPositiveBtnClickListener {
    void onClick();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\view\dialog\ModalDialog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */