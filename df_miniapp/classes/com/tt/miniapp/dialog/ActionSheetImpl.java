package com.tt.miniapp.dialog;

import android.app.Activity;
import android.content.Context;
import com.tt.miniapp.view.dialog.AlertDialogHelper;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.NativeModule;
import org.json.JSONArray;
import org.json.JSONObject;

public class ActionSheetImpl extends NativeModule {
  public ActionSheetImpl(AppbrandContext paramAppbrandContext) {
    super(paramAppbrandContext);
  }
  
  public String getName() {
    return "showActionSheet";
  }
  
  public String invoke(String paramString, final NativeModule.NativeModuleCallback nativeModuleCallback) throws Exception {
    JSONArray jSONArray = (new JSONObject(paramString)).optJSONArray("itemList");
    if (jSONArray != null) {
      int j = jSONArray.length();
      final String[] itemList = new String[j];
      for (int i = 0; i < j; i++)
        arrayOfString[i] = jSONArray.getString(i); 
      AppbrandContext.mainHandler.post(new Runnable() {
            public void run() {
              ActionSheetImpl.this.openActionSheet(itemList, nativeModuleCallback);
            }
          });
    } 
    return null;
  }
  
  void openActionSheet(String[] paramArrayOfString, final NativeModule.NativeModuleCallback nativeModuleCallback) {
    Activity activity = getCurrentActivity();
    if (activity != null) {
      if (paramArrayOfString == null)
        return; 
      AlertDialogHelper.showActionSheet((Context)activity, paramArrayOfString, new AlertDialogHelper.ActionSheetClickListener() {
            public void onActionSheetClick(int param1Int) {
              NativeModule.NativeModuleCallback nativeModuleCallback = nativeModuleCallback;
              if (nativeModuleCallback != null)
                nativeModuleCallback.onNativeModuleCall(String.valueOf(param1Int)); 
            }
            
            public void onCancel() {
              NativeModule.NativeModuleCallback nativeModuleCallback = nativeModuleCallback;
              if (nativeModuleCallback != null)
                nativeModuleCallback.onNativeModuleCall("-1"); 
            }
          });
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\dialog\ActionSheetImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */