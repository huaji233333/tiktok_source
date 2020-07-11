package com.tt.miniapp.msg.file.read;

import android.text.TextUtils;
import com.tt.miniapp.msg.file.AbsFileCtrl;
import com.tt.miniapp.msg.file.AbsStringParamCtrl;
import com.tt.miniapp.streamloader.StreamLoader;
import com.tt.miniapp.util.ToolUtils;
import g.i;
import java.io.File;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONObject;

public class ApiReadFileCtrl extends AbsStringParamCtrl {
  public ApiReadFileCtrl(String paramString) {
    super(paramString);
  }
  
  public boolean handleInvoke() throws Exception {
    JSONObject jSONObject;
    JSONArray jSONArray;
    String str2 = optString("filePath");
    String str1 = optString("encoding");
    File file = new File(getRealFilePath(str2));
    if (TextUtils.isEmpty(str2)) {
      this.mExtraInfo = getPermissionDenyStr(new String[] { this.mApiName, str2 });
      return false;
    } 
    if (!canRead(file)) {
      this.mExtraInfo = getPermissionDenyStr(new String[] { this.mApiName, str2 });
      return false;
    } 
    if (!file.exists() && StreamLoader.loadByteFromStream(str2) == null) {
      this.mExtraInfo = getNoSuchFileStr(new String[] { this.mApiName, str2 });
      return false;
    } 
    this.mExtraData = new HashMap<Object, Object>();
    if (file.exists()) {
      if (!TextUtils.isEmpty(str1) && !"null".equals(str1)) {
        str1 = ToolUtils.readString(file.getAbsolutePath(), str1);
        this.mExtraData.put("data", str1);
        return true;
      } 
      byte[] arrayOfByte1 = ToolUtils.readBytes(file.getAbsolutePath());
      str2 = i.of(arrayOfByte1, 0, arrayOfByte1.length).base64();
      jSONObject = new JSONObject();
      jSONObject.put("key", "data");
      jSONObject.put("base64", str2);
      jSONArray = new JSONArray();
      jSONArray.put(jSONObject);
      this.mExtraData.put("__nativeBuffers__", jSONArray);
      return true;
    } 
    byte[] arrayOfByte = StreamLoader.loadByteFromStream((String)jSONArray);
    if (arrayOfByte != null && arrayOfByte.length > 0) {
      if (!TextUtils.isEmpty((CharSequence)jSONObject) && !jSONObject.equals("null")) {
        String str3 = ToolUtils.decodeByteArrayToString(arrayOfByte, (String)jSONObject);
        this.mExtraData.put("data", str3);
        return true;
      } 
      String str = i.of(arrayOfByte, 0, arrayOfByte.length).base64();
      jSONObject = new JSONObject();
      jSONObject.put("key", "data");
      jSONObject.put("base64", str);
      JSONArray jSONArray1 = new JSONArray();
      jSONArray1.put(jSONObject);
      this.mExtraData.put("__nativeBuffers__", jSONArray1);
      return true;
    } 
    return false;
  }
  
  protected void parseParam(String paramString) throws Exception {
    JSONObject jSONObject = new JSONObject(paramString);
    this.mArgumentsMap.put("filePath", new AbsFileCtrl.ValuePair((AbsFileCtrl)this, jSONObject.optString("filePath"), true));
    this.mArgumentsMap.put("encoding", new AbsFileCtrl.ValuePair((AbsFileCtrl)this, jSONObject.optString("encoding"), false));
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\file\read\ApiReadFileCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */