package com.ss.android.ugc.aweme.miniapp.h;

import android.text.TextUtils;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.NativeModule;
import java.io.File;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public final class b extends NativeModule {
  public b(AppbrandContext paramAppbrandContext) {
    super(paramAppbrandContext);
  }
  
  public final String getName() {
    return "previewImage";
  }
  
  public final String invoke(String paramString, NativeModule.NativeModuleCallback paramNativeModuleCallback) throws Exception {
    JSONObject jSONObject = new JSONObject(paramString);
    String str2 = jSONObject.optString("current");
    ArrayList<String> arrayList = new ArrayList();
    JSONArray jSONArray = jSONObject.optJSONArray("urls");
    byte b1 = 0;
    if (jSONArray != null) {
      int m = jSONArray.length();
      for (int k = 0; k < m; k++) {
        String str = jSONArray.getString(k);
        if (str.contains("file://")) {
          if ((new File(str.substring(7))).exists())
            arrayList.add(jSONArray.getString(k)); 
        } else if (str.contains("http://")) {
          arrayList.add(str);
        } 
      } 
    } 
    if (arrayList.size() <= 0) {
      if (paramNativeModuleCallback != null)
        paramNativeModuleCallback.onNativeModuleCall(Boolean.valueOf(false)); 
      return null;
    } 
    String str1 = str2;
    if (!arrayList.contains(str2))
      str1 = arrayList.get(0); 
    int j = arrayList.size();
    for (int i = b1; i < j && !TextUtils.equals(str1, arrayList.get(i)); i++);
    new ArrayList();
    if (paramNativeModuleCallback != null)
      paramNativeModuleCallback.onNativeModuleCall(Boolean.valueOf(true)); 
    return null;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\ss\androi\\ugc\aweme\miniapp\h\b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */