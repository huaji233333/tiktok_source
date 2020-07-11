package com.tt.miniapp.msg.file.read;

import android.text.TextUtils;
import com.tt.frontendapiinterface.a;
import com.tt.miniapp.msg.ApiParamParser;
import com.tt.miniapp.msg.bean.ApiReadFileParam;
import com.tt.miniapp.msg.file.AbsFileCtrl;
import com.tt.miniapp.streamloader.StreamLoader;
import com.tt.miniapp.util.ToolUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ApiReadFileBufferCtrl extends AbsFileCtrl<ApiReadFileParam.InputParam, ApiReadFileParam.OutputParam> {
  public ApiReadFileBufferCtrl(String paramString) {
    super(paramString);
  }
  
  protected ApiReadFileParam.OutputParam exception(Throwable paramThrowable) {
    String str = a.a(paramThrowable);
    ApiReadFileParam.OutputParam outputParam = new ApiReadFileParam.OutputParam();
    outputParam.errMsg = buildErrMsg(this.mApiName, "fail", str);
    return outputParam;
  }
  
  protected ApiReadFileParam.OutputParam fail() {
    return new ApiReadFileParam.OutputParam(buildErrMsg(this.mApiName, "fail", trim(this.mExtraInfo)), null, null);
  }
  
  public boolean handleInvoke() throws Exception {
    byte[] arrayOfByte1;
    ArrayList<ApiReadFileParam.OutputParam.NativeBufferItem> arrayList;
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
      arrayOfByte1 = ToolUtils.readBytes(file.getAbsolutePath());
      arrayList = new ArrayList();
      arrayList.add(new ApiReadFileParam.OutputParam.NativeBufferItem("data", arrayOfByte1));
      this.mExtraData.put("__nativeBuffers__", arrayList);
      return true;
    } 
    byte[] arrayOfByte2 = StreamLoader.loadByteFromStream((String)arrayList);
    if (arrayOfByte2 != null && arrayOfByte2.length > 0) {
      if (!TextUtils.isEmpty((CharSequence)arrayOfByte1) && !arrayOfByte1.equals("null")) {
        String str = ToolUtils.decodeByteArrayToString(arrayOfByte2, (String)arrayOfByte1);
        this.mExtraData.put("data", str);
        return true;
      } 
      ArrayList<ApiReadFileParam.OutputParam.NativeBufferItem> arrayList1 = new ArrayList();
      arrayList1.add(new ApiReadFileParam.OutputParam.NativeBufferItem("data", arrayOfByte2));
      this.mExtraData.put("__nativeBuffers__", arrayList1);
      return true;
    } 
    return false;
  }
  
  protected void parseParam(ApiReadFileParam.InputParam paramInputParam) throws Exception {
    String str3 = paramInputParam.filePath;
    String str2 = paramInputParam.encoding;
    Map<String, AbsFileCtrl.ValuePair> map2 = this.mArgumentsMap;
    String str1 = str3;
    if (ApiParamParser.isEmptyString(str3))
      str1 = ""; 
    map2.put("filePath", new AbsFileCtrl.ValuePair(this, str1, true));
    Map<String, AbsFileCtrl.ValuePair> map1 = this.mArgumentsMap;
    str1 = str2;
    if (ApiParamParser.isEmptyString(str2))
      str1 = ""; 
    map1.put("encoding", new AbsFileCtrl.ValuePair(this, str1, false));
  }
  
  protected ApiReadFileParam.OutputParam success() {
    String str = (String)this.mExtraData.get("data");
    ArrayList arrayList = (ArrayList)this.mExtraData.get("__nativeBuffers__");
    return new ApiReadFileParam.OutputParam(buildErrMsg(this.mApiName, "ok", null), str, arrayList);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\file\read\ApiReadFileBufferCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */