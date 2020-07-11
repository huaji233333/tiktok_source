package com.bytedance.sandboxapp.d;

import android.text.TextUtils;
import android.util.Base64;
import d.f.b.l;
import d.m.d;
import d.u;
import g.i;
import java.nio.charset.Charset;
import java.util.Arrays;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class a {
  public static final a a = new a();
  
  public static final JSONArray a(byte[] paramArrayOfbyte, boolean paramBoolean) {
    if (paramArrayOfbyte == null)
      return null; 
    try {
      JSONObject jSONObject = new JSONObject();
      jSONObject.put("key", "data");
      if (paramBoolean) {
        jSONObject.put("value", paramArrayOfbyte);
      } else {
        jSONObject.put("base64", i.of(Arrays.copyOf(paramArrayOfbyte, paramArrayOfbyte.length)).base64());
      } 
      JSONArray jSONArray = new JSONArray();
      jSONArray.put(jSONObject);
      return jSONArray;
    } catch (JSONException jSONException) {
      return null;
    } 
  }
  
  public static final byte[] a(JSONArray paramJSONArray, boolean paramBoolean) {
    if (paramJSONArray != null) {
      JSONObject jSONObject = paramJSONArray.optJSONObject(0);
    } else {
      paramJSONArray = null;
    } 
    if (paramJSONArray != null) {
      if (paramBoolean)
        return (byte[])paramJSONArray.opt("value"); 
      String str2 = paramJSONArray.optString("key");
      String str1 = paramJSONArray.optString("base64");
      if (!TextUtils.isEmpty(str2) && !TextUtils.isEmpty(str1)) {
        l.a(str1, "base64Data");
        Charset charset = d.a;
        if (str1 != null) {
          byte[] arrayOfByte = str1.getBytes(charset);
          l.a(arrayOfByte, "(this as java.lang.String).getBytes(charset)");
          return Base64.decode(arrayOfByte, 0);
        } 
        throw new u("null cannot be cast to non-null type java.lang.String");
      } 
    } 
    return null;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\bytedance\sandboxapp\d\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */