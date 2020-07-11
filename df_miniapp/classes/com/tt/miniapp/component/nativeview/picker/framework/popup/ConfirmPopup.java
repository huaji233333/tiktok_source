package com.tt.miniapp.component.nativeview.picker.framework.popup;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.tt.miniapphost.util.UIUtils;

public abstract class ConfirmPopup<V extends View> extends BasicPopup<View> {
  protected int backgroundColor = -1;
  
  protected TextView cancelButton;
  
  protected CharSequence cancelText = "";
  
  protected int cancelTextColor = -13987625;
  
  protected int cancelTextSize = 17;
  
  protected boolean cancelVisible = true;
  
  protected View centerView;
  
  protected int contentLeftAndRightPadding;
  
  protected int contentTopAndBottomPadding;
  
  protected View footerView;
  
  protected View headerView;
  
  private OnCancelListener onCancelListener;
  
  protected int pressedTextColor = -13987625;
  
  protected TextView submitButton;
  
  protected CharSequence submitText = "";
  
  protected int submitTextColor = -13987625;
  
  protected int submitTextSize = 17;
  
  protected CharSequence titleText = "";
  
  protected int titleTextColor = -13987625;
  
  protected int titleTextSize;
  
  protected View titleView;
  
  protected int topBackgroundColor = -1;
  
  protected int topHeight = 40;
  
  protected int topLineColor = -1513240;
  
  protected int topLineHeightPixels = 1;
  
  protected boolean topLineVisible = true;
  
  protected int topPadding = 15;
  
  public ConfirmPopup(Activity paramActivity) {
    super(paramActivity);
    this.cancelText = paramActivity.getString(2097741975);
    this.submitText = paramActivity.getString(2097741884);
  }
  
  public TextView getCancelButton() {
    TextView textView = this.cancelButton;
    if (textView != null)
      return textView; 
    throw new NullPointerException("please call show at first");
  }
  
  public TextView getSubmitButton() {
    TextView textView = this.submitButton;
    if (textView != null)
      return textView; 
    throw new NullPointerException("please call show at first");
  }
  
  public View getTitleView() {
    View view = this.titleView;
    if (view != null)
      return view; 
    throw new NullPointerException("please call show at first");
  }
  
  protected abstract V makeCenterView();
  
  protected final View makeContentView() {
    boolean bool1;
    boolean bool2;
    LinearLayout linearLayout = new LinearLayout((Context)this.activity);
    linearLayout.setLayoutParams((ViewGroup.LayoutParams)new LinearLayout.LayoutParams(-1, -1));
    linearLayout.setBackgroundColor(this.backgroundColor);
    linearLayout.setOrientation(1);
    linearLayout.setGravity(17);
    linearLayout.setPadding(0, 0, 0, 0);
    linearLayout.setClipToPadding(false);
    View view2 = makeHeaderView();
    if (view2 != null)
      linearLayout.addView(view2); 
    if (this.topLineVisible) {
      view2 = new View((Context)this.activity);
      view2.setLayoutParams((ViewGroup.LayoutParams)new LinearLayout.LayoutParams(-1, this.topLineHeightPixels));
      view2.setBackgroundColor(this.topLineColor);
      linearLayout.addView(view2);
    } 
    if (this.centerView == null)
      this.centerView = (View)makeCenterView(); 
    if (this.contentLeftAndRightPadding > 0) {
      bool1 = (int)UIUtils.dip2Px((Context)this.activity, this.contentLeftAndRightPadding);
    } else {
      bool1 = false;
    } 
    if (this.contentTopAndBottomPadding > 0) {
      bool2 = (int)UIUtils.dip2Px((Context)this.activity, this.contentTopAndBottomPadding);
    } else {
      bool2 = false;
    } 
    this.centerView.setPadding(bool1, bool2, bool1, bool2);
    ViewGroup viewGroup = (ViewGroup)this.centerView.getParent();
    if (viewGroup != null)
      viewGroup.removeView(this.centerView); 
    linearLayout.addView(this.centerView, (ViewGroup.LayoutParams)new LinearLayout.LayoutParams(-1, 0, 1.0F));
    View view1 = makeFooterView();
    if (view1 != null)
      linearLayout.addView(view1); 
    return (View)linearLayout;
  }
  
  protected View makeFooterView() {
    View view = this.footerView;
    return (view != null) ? view : null;
  }
  
  protected View makeHeaderView() {
    View view = this.headerView;
    if (view != null)
      return view; 
    RelativeLayout relativeLayout = new RelativeLayout((Context)this.activity);
    relativeLayout.setLayoutParams((ViewGroup.LayoutParams)new RelativeLayout.LayoutParams(-1, (int)UIUtils.dip2Px((Context)this.activity, this.topHeight)));
    relativeLayout.setBackgroundColor(this.topBackgroundColor);
    relativeLayout.setGravity(16);
    this.cancelButton = new TextView((Context)this.activity);
    TextView textView = this.cancelButton;
    if (this.cancelVisible) {
      i = 0;
    } else {
      i = 8;
    } 
    textView.setVisibility(i);
    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -1);
    layoutParams.addRule(9, -1);
    layoutParams.addRule(15, -1);
    this.cancelButton.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
    this.cancelButton.setBackgroundColor(0);
    this.cancelButton.setGravity(17);
    int i = (int)UIUtils.dip2Px((Context)this.activity, this.topPadding);
    this.cancelButton.setPadding(i, 0, i, 0);
    if (!TextUtils.isEmpty(this.cancelText))
      this.cancelButton.setText(this.cancelText); 
    this.cancelButton.setTextColor(UIUtils.toColorStateList(this.cancelTextColor, this.pressedTextColor));
    int j = this.cancelTextSize;
    if (j != 0)
      this.cancelButton.setTextSize(j); 
    this.cancelButton.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            ConfirmPopup.this.dismiss();
            ConfirmPopup.this.onCancel();
          }
        });
    relativeLayout.addView((View)this.cancelButton);
    if (this.titleView == null) {
      TextView textView1 = new TextView((Context)this.activity);
      RelativeLayout.LayoutParams layoutParams1 = new RelativeLayout.LayoutParams(-2, -2);
      j = (int)UIUtils.dip2Px((Context)this.activity, this.topPadding);
      layoutParams1.leftMargin = j;
      layoutParams1.rightMargin = j;
      layoutParams1.addRule(14, -1);
      layoutParams1.addRule(15, -1);
      textView1.setLayoutParams((ViewGroup.LayoutParams)layoutParams1);
      textView1.setGravity(17);
      if (!TextUtils.isEmpty(this.titleText))
        textView1.setText(this.titleText); 
      textView1.setTextColor(this.titleTextColor);
      j = this.titleTextSize;
      if (j != 0)
        textView1.setTextSize(j); 
      this.titleView = (View)textView1;
    } 
    relativeLayout.addView(this.titleView);
    this.submitButton = new TextView((Context)this.activity);
    layoutParams = new RelativeLayout.LayoutParams(-2, -1);
    layoutParams.addRule(11, -1);
    layoutParams.addRule(15, -1);
    this.submitButton.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
    this.submitButton.setBackgroundColor(0);
    this.submitButton.setGravity(17);
    this.submitButton.setPadding(i, 0, i, 0);
    if (!TextUtils.isEmpty(this.submitText))
      this.submitButton.setText(this.submitText); 
    this.submitButton.setTextColor(UIUtils.toColorStateList(this.submitTextColor, this.pressedTextColor));
    i = this.submitTextSize;
    if (i != 0)
      this.submitButton.setTextSize(i); 
    this.submitButton.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            ConfirmPopup.this.dismiss();
            ConfirmPopup.this.onSubmit();
          }
        });
    relativeLayout.addView((View)this.submitButton);
    return (View)relativeLayout;
  }
  
  protected void onCancel() {
    OnCancelListener onCancelListener = this.onCancelListener;
    if (onCancelListener != null)
      onCancelListener.onCancel(); 
  }
  
  protected void onSubmit() {}
  
  public void setBackgroundColor(int paramInt) {
    this.backgroundColor = paramInt;
  }
  
  public void setCancelText(int paramInt) {
    setCancelText(this.activity.getString(paramInt));
  }
  
  public void setCancelText(CharSequence paramCharSequence) {
    TextView textView = this.cancelButton;
    if (textView != null) {
      textView.setText(paramCharSequence);
      return;
    } 
    this.cancelText = paramCharSequence;
  }
  
  public void setCancelTextColor(int paramInt) {
    TextView textView = this.cancelButton;
    if (textView != null) {
      textView.setTextColor(paramInt);
      return;
    } 
    this.cancelTextColor = paramInt;
  }
  
  public void setCancelTextSize(int paramInt) {
    this.cancelTextSize = paramInt;
  }
  
  public void setCancelVisible(boolean paramBoolean) {
    TextView textView = this.cancelButton;
    if (textView != null) {
      byte b;
      if (paramBoolean) {
        b = 0;
      } else {
        b = 8;
      } 
      textView.setVisibility(b);
      return;
    } 
    this.cancelVisible = paramBoolean;
  }
  
  public void setContentPadding(int paramInt1, int paramInt2) {
    this.contentLeftAndRightPadding = paramInt1;
    this.contentTopAndBottomPadding = paramInt2;
  }
  
  public void setFooterView(View paramView) {
    this.footerView = paramView;
  }
  
  public void setHeaderView(View paramView) {
    this.headerView = paramView;
  }
  
  public void setOnCancelListener(OnCancelListener paramOnCancelListener) {
    this.onCancelListener = paramOnCancelListener;
  }
  
  public void setPressedTextColor(int paramInt) {
    this.pressedTextColor = paramInt;
  }
  
  public void setSubmitText(int paramInt) {
    setSubmitText(this.activity.getString(paramInt));
  }
  
  public void setSubmitText(CharSequence paramCharSequence) {
    TextView textView = this.submitButton;
    if (textView != null) {
      textView.setText(paramCharSequence);
      return;
    } 
    this.submitText = paramCharSequence;
  }
  
  public void setSubmitTextColor(int paramInt) {
    TextView textView = this.submitButton;
    if (textView != null) {
      textView.setTextColor(paramInt);
      return;
    } 
    this.submitTextColor = paramInt;
  }
  
  public void setSubmitTextSize(int paramInt) {
    this.submitTextSize = paramInt;
  }
  
  public void setTitleText(int paramInt) {
    setTitleText(this.activity.getString(paramInt));
  }
  
  public void setTitleText(CharSequence paramCharSequence) {
    View view = this.titleView;
    if (view != null && view instanceof TextView) {
      ((TextView)view).setText(paramCharSequence);
      return;
    } 
    this.titleText = paramCharSequence;
  }
  
  public void setTitleTextColor(int paramInt) {
    View view = this.titleView;
    if (view != null && view instanceof TextView) {
      ((TextView)view).setTextColor(paramInt);
      return;
    } 
    this.titleTextColor = paramInt;
  }
  
  public void setTitleTextSize(int paramInt) {
    this.titleTextSize = paramInt;
  }
  
  public void setTitleView(View paramView) {
    this.titleView = paramView;
  }
  
  public void setTopBackgroundColor(int paramInt) {
    this.topBackgroundColor = paramInt;
  }
  
  public void setTopHeight(int paramInt) {
    this.topHeight = paramInt;
  }
  
  public void setTopLineColor(int paramInt) {
    this.topLineColor = paramInt;
  }
  
  public void setTopLineHeight(int paramInt) {
    this.topLineHeightPixels = paramInt;
  }
  
  public void setTopLineVisible(boolean paramBoolean) {
    this.topLineVisible = paramBoolean;
  }
  
  public void setTopPadding(int paramInt) {
    this.topPadding = paramInt;
  }
  
  public static interface OnCancelListener {
    void onCancel();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\component\nativeview\picker\framework\popup\ConfirmPopup.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */