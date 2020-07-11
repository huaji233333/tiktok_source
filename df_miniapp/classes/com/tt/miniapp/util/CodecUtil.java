package com.tt.miniapp.util;

import android.net.Uri;
import android.text.TextUtils;
import com.tt.miniapphost.AppBrandLogger;
import java.util.Iterator;
import org.json.JSONObject;

public class CodecUtil {
  public static JSONObject decodeQueryKeyAndValue(String paramString) {
    JSONObject jSONObject = new JSONObject();
    if (!TextUtils.isEmpty(paramString))
      try {
        JSONObject jSONObject1 = new JSONObject(paramString);
        Iterator<String> iterator = jSONObject1.keys();
        while (iterator.hasNext()) {
          String str1 = iterator.next();
          String str2 = jSONObject1.optString(str1);
          jSONObject.put(Uri.decode(str1), Uri.decode(str2));
        } 
      } catch (Exception exception) {
        AppBrandLogger.e("CodecUtil", new Object[] { exception });
      }  
    return jSONObject;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniap\\util\CodecUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */