package com.tt.miniapp.msg.sync;

import android.text.TextUtils;
import android.view.View;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.WebViewManager;
import com.tt.miniapp.component.nativeview.Input;
import com.tt.miniapp.component.nativeview.NativeViewManager;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import org.json.JSONObject;

public class SetKeyboardValueSync extends SyncMsgCtrl {
  public SetKeyboardValueSync(String paramString) {
    super(paramString);
  }
  
  public String act() {
    try {
      JSONObject jSONObject = new JSONObject(this.mParams);
      int i = jSONObject.getInt("inputId");
      final int cursor = jSONObject.getInt("cursor");
      null = jSONObject.getString("value");
      WebViewManager webViewManager = AppbrandApplicationImpl.getInst().getWebViewManager();
      if (webViewManager == null)
        return makeFailMsg("WebViewManager is null"); 
      WebViewManager.IRender iRender = webViewManager.getCurrentIRender();
      if (iRender == null)
        return makeFailMsg("current render is null"); 
      NativeViewManager nativeViewManager = iRender.getNativeViewManager();
      if (nativeViewManager == null)
        return makeFailMsg("native view manager is null"); 
      View view = nativeViewManager.getView(i);
      if (view instanceof Input) {
        final Input input = (Input)view;
        if (!TextUtils.equals(input.getValue(), null))
          AppbrandContext.mainHandler.post(new Runnable() {
                public void run() {
                  input.setValue(value);
                  int i = cursor;
                  if (i != -1) {
                    input.setSelection(i);
                    return;
                  } 
                  Input input = input;
                  input.setSelection(input.getText().length());
                }
              }); 
        return makeOkMsg();
      } 
      return makeFailMsg("input id error");
    } catch (Exception exception) {
      AppBrandLogger.e("tma_ApiRequestCtrl", new Object[] { exception });
      return makeFailMsg(exception);
    } 
  }
  
  public String getName() {
    return "setKeyboardValue";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\sync\SetKeyboardValueSync.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */