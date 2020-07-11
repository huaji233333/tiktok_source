package com.facebook.react.views.textinput;

import android.view.KeyEvent;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.events.EventDispatcher;

class ReactEditTextInputConnectionWrapper extends InputConnectionWrapper {
  private ReactEditText mEditText;
  
  private EventDispatcher mEventDispatcher;
  
  private boolean mIsBatchEdit;
  
  private String mKey;
  
  public ReactEditTextInputConnectionWrapper(InputConnection paramInputConnection, ReactContext paramReactContext, ReactEditText paramReactEditText) {
    super(paramInputConnection, false);
    this.mEventDispatcher = ((UIManagerModule)paramReactContext.getNativeModule(UIManagerModule.class)).getEventDispatcher();
    this.mEditText = paramReactEditText;
  }
  
  private void dispatchKeyEvent(String paramString) {
    String str = paramString;
    if (paramString.equals("\n"))
      str = "Enter"; 
    this.mEventDispatcher.dispatchEvent(new ReactTextInputKeyPressEvent(this.mEditText.getId(), str));
  }
  
  private void dispatchKeyEventOrEnqueue(String paramString) {
    if (this.mIsBatchEdit) {
      this.mKey = paramString;
      return;
    } 
    dispatchKeyEvent(paramString);
  }
  
  public boolean beginBatchEdit() {
    this.mIsBatchEdit = true;
    return super.beginBatchEdit();
  }
  
  public boolean commitText(CharSequence paramCharSequence, int paramInt) {
    String str = paramCharSequence.toString();
    if (str.length() <= 1) {
      String str1 = str;
      if (str.equals(""))
        str1 = "Backspace"; 
      dispatchKeyEventOrEnqueue(str1);
    } 
    return super.commitText(paramCharSequence, paramInt);
  }
  
  public boolean deleteSurroundingText(int paramInt1, int paramInt2) {
    dispatchKeyEvent("Backspace");
    return super.deleteSurroundingText(paramInt1, paramInt2);
  }
  
  public boolean endBatchEdit() {
    this.mIsBatchEdit = false;
    String str = this.mKey;
    if (str != null) {
      dispatchKeyEvent(str);
      this.mKey = null;
    } 
    return super.endBatchEdit();
  }
  
  public boolean sendKeyEvent(KeyEvent paramKeyEvent) {
    if (paramKeyEvent.getAction() == 0)
      if (paramKeyEvent.getKeyCode() == 67) {
        dispatchKeyEvent("Backspace");
      } else if (paramKeyEvent.getKeyCode() == 66) {
        dispatchKeyEvent("Enter");
      }  
    return super.sendKeyEvent(paramKeyEvent);
  }
  
  public boolean setComposingText(CharSequence paramCharSequence, int paramInt) {
    int k = this.mEditText.getSelectionStart();
    int i = this.mEditText.getSelectionEnd();
    boolean bool1 = super.setComposingText(paramCharSequence, paramInt);
    int j = this.mEditText.getSelectionStart();
    boolean bool = false;
    if (k == i) {
      paramInt = 1;
    } else {
      paramInt = 0;
    } 
    if (j == k) {
      i = 1;
    } else {
      i = 0;
    } 
    if (j < k || j <= 0)
      bool = true; 
    if (bool || (paramInt == 0 && i != 0)) {
      paramCharSequence = "Backspace";
      dispatchKeyEventOrEnqueue((String)paramCharSequence);
      return bool1;
    } 
    paramCharSequence = String.valueOf(this.mEditText.getText().charAt(j - 1));
    dispatchKeyEventOrEnqueue((String)paramCharSequence);
    return bool1;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\textinput\ReactEditTextInputConnectionWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */