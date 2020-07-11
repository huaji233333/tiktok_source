package com.facebook.react.modules.clipboard;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Build;
import com.facebook.react.bridge.ContextBaseJavaModule;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.module.annotations.ReactModule;

@ReactModule(name = "Clipboard")
public class ClipboardModule extends ContextBaseJavaModule {
  public ClipboardModule(Context paramContext) {
    super(paramContext);
  }
  
  private ClipboardManager getClipboardService() {
    Context context = getContext();
    getContext();
    return (ClipboardManager)context.getSystemService("clipboard");
  }
  
  public String getName() {
    return "Clipboard";
  }
  
  @ReactMethod
  public void getString(Promise paramPromise) {
    try {
      ClipboardManager clipboardManager = getClipboardService();
      ClipData clipData = clipboardManager.getPrimaryClip();
      if (clipData == null) {
        paramPromise.resolve("");
        return;
      } 
      if (clipData.getItemCount() > 0) {
        ClipData.Item item = clipboardManager.getPrimaryClip().getItemAt(0);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(item.getText());
        paramPromise.resolve(stringBuilder.toString());
        return;
      } 
      paramPromise.resolve("");
      return;
    } catch (Exception exception) {
      paramPromise.reject(exception);
      return;
    } 
  }
  
  @ReactMethod
  public void setString(String paramString) {
    ClipData clipData;
    if (Build.VERSION.SDK_INT >= 11) {
      clipData = ClipData.newPlainText(null, paramString);
      getClipboardService().setPrimaryClip(clipData);
      return;
    } 
    getClipboardService().setText((CharSequence)clipData);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\modules\clipboard\ClipboardModule.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */