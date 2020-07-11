package com.tt.miniapp.view.keyboard;

import android.content.Context;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;

public class KeyboardInputView extends RelativeLayout {
  private TextView confirmTextView;
  
  public EditText keyboardEt;
  
  private TextWatcher mTextSwitcher;
  
  public KeyboardInputView(Context paramContext) {
    super(paramContext);
    initView(paramContext);
  }
  
  public KeyboardInputView(Context paramContext, AttributeSet paramAttributeSet) {
    super(paramContext, paramAttributeSet);
    initView(paramContext);
  }
  
  public KeyboardInputView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
    initView(paramContext);
  }
  
  public KeyboardInputView(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2) {
    super(paramContext, paramAttributeSet, paramInt1, paramInt2);
    initView(paramContext);
  }
  
  private void initView(Context paramContext) {
    try {
      View view = inflate((Context)AppbrandContext.getInst().getApplicationContext(), 2097676319, (ViewGroup)this);
      return;
    } finally {
      paramContext = null;
      AppBrandLogger.e("KeyboardInputView", new Object[] { paramContext });
    } 
  }
  
  public void clearKeyboardEdFocus() {
    this.keyboardEt.clearFocus();
  }
  
  public TextView getConfirmTextView() {
    return this.confirmTextView;
  }
  
  public EditText getEditText() {
    return this.keyboardEt;
  }
  
  public void keyboardEdFocus() {
    if (this.keyboardEt.hasFocus())
      return; 
    this.keyboardEt.requestFocus();
  }
  
  public void setTextChangedListener(TextWatcher paramTextWatcher) {
    TextWatcher textWatcher = this.mTextSwitcher;
    if (textWatcher != null)
      this.keyboardEt.removeTextChangedListener(textWatcher); 
    this.mTextSwitcher = paramTextWatcher;
    if (paramTextWatcher != null)
      this.keyboardEt.addTextChangedListener(this.mTextSwitcher); 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\view\keyboard\KeyboardInputView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */