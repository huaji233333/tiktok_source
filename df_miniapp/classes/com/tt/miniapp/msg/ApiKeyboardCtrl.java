package com.tt.miniapp.msg;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.tt.frontendapiinterface.b;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.WebViewManager;
import com.tt.miniapp.component.nativeview.NativeViewManager;
import com.tt.miniapp.view.keyboard.KeyboardInputView;
import com.tt.miniapp.view.keyboard.KeyboardLayout;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandApplication;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.MiniappHostBase;
import com.tt.miniapphost.entity.AppInfoEntity;
import com.tt.miniapphost.util.UIUtils;
import com.tt.option.e.e;
import org.json.JSONException;
import org.json.JSONObject;

public class ApiKeyboardCtrl extends b {
  private String functionName;
  
  private TextView mConfirmTextView;
  
  public ApiKeyboardCtrl(String paramString1, String paramString2, int paramInt, e parame) {
    super(paramString2, paramInt, parame);
    this.functionName = paramString1;
  }
  
  private KeyboardInputView findKeyboardInputView(Activity paramActivity) {
    View view = getContentView(paramActivity);
    return (view instanceof ViewGroup) ? (KeyboardInputView)findViewByClass(view, KeyboardInputView.class) : null;
  }
  
  private View findViewByClass(View paramView, Class paramClass) {
    if (paramView != null) {
      if (TextUtils.equals(paramView.getClass().getName(), paramClass.getName()))
        return paramView; 
      if (paramView instanceof ViewGroup) {
        ViewGroup viewGroup = (ViewGroup)paramView;
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
          View view = findViewByClass(viewGroup.getChildAt(i), paramClass);
          if (view != null)
            return view; 
        } 
      } 
    } 
    return null;
  }
  
  private int getConfirmAction(String paramString) {
    byte b1;
    switch (paramString.hashCode()) {
      default:
        b1 = -1;
        break;
      case 3526536:
        if (paramString.equals("send")) {
          b1 = 4;
          break;
        } 
      case 3377907:
        if (paramString.equals("next")) {
          b1 = 1;
          break;
        } 
      case 3089282:
        if (paramString.equals("done")) {
          b1 = 0;
          break;
        } 
      case 3304:
        if (paramString.equals("go")) {
          b1 = 3;
          break;
        } 
      case -906336856:
        if (paramString.equals("search")) {
          b1 = 2;
          break;
        } 
    } 
    return (b1 != 0) ? ((b1 != 1) ? ((b1 != 2) ? ((b1 != 3) ? ((b1 != 4) ? 6 : 4) : 2) : 3) : 5) : 6;
  }
  
  private int getConfirmTxt(String paramString) {
    byte b1;
    switch (paramString.hashCode()) {
      default:
        b1 = -1;
        break;
      case 3526536:
        if (paramString.equals("send")) {
          b1 = 4;
          break;
        } 
      case 3377907:
        if (paramString.equals("next")) {
          b1 = 1;
          break;
        } 
      case 3089282:
        if (paramString.equals("done")) {
          b1 = 0;
          break;
        } 
      case 3304:
        if (paramString.equals("go")) {
          b1 = 3;
          break;
        } 
      case -906336856:
        if (paramString.equals("search")) {
          b1 = 2;
          break;
        } 
    } 
    return (b1 != 0) ? ((b1 != 1) ? ((b1 != 2) ? ((b1 != 3) ? ((b1 != 4) ? 2097741928 : 2097741932) : 2097741929) : 2097741931) : 2097741930) : 2097741928;
  }
  
  private View getContentView(Activity paramActivity) {
    return paramActivity.findViewById(16908290);
  }
  
  private TextView.OnEditorActionListener getInputViewEditorActionListener(final boolean confirmHold, final boolean singleLine, final EditText finalEditText) {
    return new TextView.OnEditorActionListener() {
        public boolean onEditorAction(TextView param1TextView, int param1Int, KeyEvent param1KeyEvent) {
          if (singleLine) {
            if (param1Int == 6 || param1Int == 5 || param1Int == 3 || param1Int == 2 || param1Int == 4)
              ApiKeyboardCtrl.this.onKeyboardConfirm(finalEditText); 
            return confirmHold;
          } 
          return false;
        }
      };
  }
  
  private View.OnClickListener getOkBtnClickListener(final EditText finalEditText, final boolean confirmHold, final Activity activity) {
    return new View.OnClickListener() {
        public void onClick(View param1View) {
          AppBrandLogger.i("tma_ApiKeyboardCtrl", new Object[] { "键盘确认按钮点击" });
          ApiKeyboardCtrl.this.onKeyboardConfirm(finalEditText);
          if (!confirmHold)
            ApiKeyboardCtrl.this.closeKeyboard((View)finalEditText, activity); 
        }
      };
  }
  
  private ResultReceiver getShowKeyboardReceiver() {
    return new ResultReceiver(null) {
        protected void onReceiveResult(int param1Int, Bundle param1Bundle) {
          super.onReceiveResult(param1Int, param1Bundle);
          if (param1Int == 2 || param1Int == 0) {
            param1Int = 1;
          } else {
            param1Int = 0;
          } 
          if (param1Int != 0) {
            ApiKeyboardCtrl.this.callbackOk();
            return;
          } 
          ApiKeyboardCtrl.this.callbackFail("show keyboard fail");
        }
      };
  }
  
  private void handleKeyboardForApp(Activity paramActivity) {
    if (TextUtils.equals(this.functionName, "hideKeyboard")) {
      WebViewManager.IRender iRender = AppbrandApplicationImpl.getInst().getWebViewManager().getCurrentIRender();
      if (iRender != null) {
        NativeViewManager nativeViewManager = iRender.getNativeViewManager();
        if (nativeViewManager != null) {
          View view = nativeViewManager.getFocusedInput();
          if (view != null) {
            if (closeKeyboard(view, paramActivity)) {
              callbackOk();
              return;
            } 
            callbackFail("close keyboard fail");
            return;
          } 
          callbackFail("focused input is null");
          return;
        } 
        callbackFail("native view manager is null");
        return;
      } 
      callbackFail("current render is null");
    } 
  }
  
  private void handleKeyboardForGame(Activity paramActivity) {
    if (TextUtils.equals(this.functionName, "hideKeyboard")) {
      hideKeyboard(paramActivity);
      return;
    } 
    if (TextUtils.equals(this.functionName, "showKeyboard")) {
      showKeyboard(paramActivity);
      return;
    } 
    if (TextUtils.equals(this.functionName, "updateKeyboard"))
      updateKeyboard(paramActivity); 
  }
  
  private void hideKeyboard(Activity paramActivity) {
    EditText editText;
    KeyboardInputView keyboardInputView = findKeyboardInputView(paramActivity);
    if (keyboardInputView == null) {
      keyboardInputView = null;
    } else {
      editText = keyboardInputView.getEditText();
    } 
    if (closeKeyboard((View)editText, paramActivity)) {
      callbackOk();
      return;
    } 
    callbackFail("close keyboard fail");
  }
  
  private boolean isKeyBoardClosed(Activity paramActivity) {
    View view = findViewByClass(getContentView(paramActivity), KeyboardLayout.class);
    return (view != null && view instanceof KeyboardLayout && !((KeyboardLayout)view).isKeyboardActive());
  }
  
  private void showKeyboard(final Activity activity) {
    AppbrandContext.mainHandler.post(new Runnable() {
          public void run() {
            try {
              ApiKeyboardCtrl.this.initInputViewOkTextView(activity);
              ApiKeyboardCtrl.this.initInputViewEditText(activity);
              ApiKeyboardCtrl.this.openKeyboard(activity);
              return;
            } catch (Exception exception) {
              ApiKeyboardCtrl.this.callbackFail(exception);
              AppBrandLogger.stacktrace(6, "tma_ApiKeyboardCtrl", exception.getStackTrace());
              return;
            } 
          }
        });
  }
  
  private void updateInputViewEditTextWidth(Activity paramActivity, EditText paramEditText) {
    if (paramEditText != null && this.mConfirmTextView != null) {
      RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)paramEditText.getLayoutParams();
      layoutParams.width = UIUtils.getScreenWidth((Context)paramActivity) - this.mConfirmTextView.getMeasuredWidth() - this.mConfirmTextView.getPaddingStart();
      paramEditText.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
    } 
  }
  
  private void updateKeyboard(Activity paramActivity) {
    KeyboardInputView keyboardInputView = findKeyboardInputView(paramActivity);
    if (keyboardInputView != null)
      try {
        final String value = (new JSONObject(this.mArgs)).optString("value");
        final EditText finalEditText = keyboardInputView.getEditText();
        AppbrandContext.mainHandler.post(new Runnable() {
              public void run() {
                finalEditText.setText(value);
                EditText editText = finalEditText;
                editText.setSelection(editText.getText().length());
                ApiKeyboardCtrl.this.callbackOk();
              }
            });
        return;
      } catch (JSONException jSONException) {
        AppBrandLogger.e("tma_ApiKeyboardCtrl", new Object[] { jSONException });
        callbackFail((Throwable)jSONException);
        return;
      }  
    callbackFail("keyboard layout is null");
  }
  
  public void act() {
    AppBrandLogger.i("tma_ApiKeyboardCtrl", new Object[] { "ApiKeyboardCtrl ", this.functionName, ",", this.mArgs });
    MiniappHostBase miniappHostBase = AppbrandContext.getInst().getCurrentActivity();
    if (miniappHostBase == null) {
      callbackFail("activity is null");
      return;
    } 
    AppInfoEntity appInfoEntity = AppbrandApplicationImpl.getInst().getAppInfo();
    if (appInfoEntity == null) {
      callbackFail("app info is null");
      return;
    } 
    try {
      return;
    } finally {
      miniappHostBase = null;
      callbackFail((Throwable)miniappHostBase);
    } 
  }
  
  public boolean closeKeyboard(View paramView, Activity paramActivity) {
    if (isKeyBoardClosed(paramActivity))
      return true; 
    InputMethodManager inputMethodManager = (InputMethodManager)paramActivity.getSystemService("input_method");
    return (paramView != null && inputMethodManager != null) ? inputMethodManager.hideSoftInputFromWindow(paramView.getWindowToken(), 0) : false;
  }
  
  public String getActionName() {
    return this.functionName;
  }
  
  public void initInputViewEditText(Activity paramActivity) throws JSONException {
    EditText editText;
    JSONObject jSONObject = new JSONObject(this.mArgs);
    String str1 = jSONObject.optString("defaultValue");
    long l = jSONObject.optLong("maxLength", 2147483647L);
    boolean bool1 = jSONObject.optBoolean("multiple");
    boolean bool2 = jSONObject.optBoolean("confirmHold");
    String str2 = jSONObject.optString("confirmType");
    KeyboardInputView keyboardInputView = findKeyboardInputView(paramActivity);
    if (keyboardInputView == null) {
      keyboardInputView = null;
    } else {
      editText = keyboardInputView.getEditText();
    } 
    if (editText != null) {
      AppBrandLogger.i("tma_ApiKeyboardCtrl", new Object[] { str1 });
      editText.setImeOptions(getConfirmAction(str2) | 0x10000000);
      editText.setSingleLine(bool1 ^ true);
      editText.setFilters(new InputFilter[] { (InputFilter)new InputFilter.LengthFilter((int)l) });
      editText.setText(str1);
      editText.setSelection(editText.getText().length());
      editText.setMaxHeight((int)UIUtils.dip2Px((Context)paramActivity, 55.0F));
      editText.setOnEditorActionListener(getInputViewEditorActionListener(bool2, bool1 ^ true, editText));
      updateInputViewEditTextWidth(paramActivity, editText);
    } 
  }
  
  public void initInputViewOkTextView(Activity paramActivity) throws JSONException {
    JSONObject jSONObject = new JSONObject(this.mArgs);
    boolean bool = jSONObject.optBoolean("confirmHold");
    String str = jSONObject.optString("confirmType");
    KeyboardInputView keyboardInputView = findKeyboardInputView(paramActivity);
    if (keyboardInputView == null) {
      jSONObject = null;
    } else {
      textView = keyboardInputView.getConfirmTextView();
    } 
    this.mConfirmTextView = textView;
    TextView textView = this.mConfirmTextView;
    if (textView != null) {
      textView.setText(getConfirmTxt(str));
      this.mConfirmTextView.setOnClickListener(getOkBtnClickListener(keyboardInputView.getEditText(), bool, paramActivity));
      this.mConfirmTextView.measure(0, 0);
    } 
  }
  
  public void onKeyboardConfirm(EditText paramEditText) {
    JSONObject jSONObject = new JSONObject();
    try {
      jSONObject.put("value", paramEditText.getText().toString());
    } catch (JSONException jSONException) {
      AppBrandLogger.stacktrace(6, "tma_ApiKeyboardCtrl", jSONException.getStackTrace());
    } 
    AppbrandApplication.getInst().getJsBridge().sendMsgToJsCore("onKeyboardConfirm", jSONObject.toString());
  }
  
  public boolean openKeyboard(Activity paramActivity) {
    KeyboardInputView keyboardInputView = findKeyboardInputView(paramActivity);
    if (keyboardInputView != null && keyboardInputView.keyboardEt != null) {
      keyboardInputView.keyboardEt.requestFocus();
      InputMethodManager inputMethodManager = (InputMethodManager)paramActivity.getSystemService("input_method");
      if (inputMethodManager != null)
        return inputMethodManager.showSoftInput((View)keyboardInputView.keyboardEt, 2, getShowKeyboardReceiver()); 
    } 
    return false;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ApiKeyboardCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */