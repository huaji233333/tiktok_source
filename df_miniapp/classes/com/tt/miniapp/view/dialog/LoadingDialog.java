package com.tt.miniapp.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class LoadingDialog extends Dialog {
  private ImageView mIvIcon;
  
  private ProgressBar mProgressBar;
  
  private TextView mTextView;
  
  private String text;
  
  public LoadingDialog(Context paramContext, String paramString) {
    super(paramContext);
    this.text = paramString;
  }
  
  protected void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    getWindow().getDecorView().setPadding(0, 0, 0, 0);
    getWindow().getDecorView().setBackground(null);
    setContentView(2097676324);
    this.mIvIcon = (ImageView)findViewById(2097545309);
    this.mProgressBar = (ProgressBar)findViewById(2097545329);
    this.mTextView = (TextView)findViewById(2097545396);
    this.mProgressBar.setVisibility(0);
    this.mTextView.setText(this.text);
  }
  
  public void show() {
    super.show();
    Window window = getWindow();
    if (window != null) {
      WindowManager.LayoutParams layoutParams = window.getAttributes();
      layoutParams.gravity = 17;
      layoutParams.width = -2;
      layoutParams.height = -2;
      window.setAttributes(layoutParams);
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\view\dialog\LoadingDialog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */