package com.tt.miniapp.msg.file.write;

import android.text.TextUtils;
import com.tt.frontendapiinterface.a;
import com.tt.miniapp.msg.ApiParamParser;
import com.tt.miniapp.msg.bean.ApiWriteFileParam;
import com.tt.miniapp.msg.file.AbsFileCtrl;
import com.tt.miniapp.storage.filestorge.FileManager;
import com.tt.miniapp.util.ToolUtils;
import com.tt.miniapphost.AppBrandLogger;
import java.io.File;
import java.nio.ByteBuffer;
import java.util.Map;

public class ApiWriteFileBufferCtrl extends AbsFileCtrl<ApiWriteFileParam.InputParam, ApiWriteFileParam.OutPutParam> {
  private ByteBuffer mBufferData;
  
  public ApiWriteFileBufferCtrl(String paramString) {
    super(paramString);
  }
  
  protected ApiWriteFileParam.OutPutParam exception(Throwable paramThrowable) {
    String str = a.a(paramThrowable);
    ApiWriteFileParam.OutPutParam outPutParam = new ApiWriteFileParam.OutPutParam();
    outPutParam.errMsg = buildErrMsg(this.mApiName, "fail", str);
    return outPutParam;
  }
  
  protected ApiWriteFileParam.OutPutParam fail() {
    ApiWriteFileParam.OutPutParam outPutParam = new ApiWriteFileParam.OutPutParam();
    outPutParam.errMsg = buildErrMsg(this.mApiName, "fail", trim(this.mExtraInfo));
    return outPutParam;
  }
  
  public boolean handleInvoke() throws Exception {
    String str3 = optString("filePath");
    String str1 = optString("data");
    String str2 = optString("encoding");
    File file = new File(getRealFilePath(str3));
    AppBrandLogger.d("ApiWriteFileBufferCtrl", new Object[] { "filePath ", str3, " \n data ", str1, " \n encoding ", str2 });
    if (!canWrite(file)) {
      this.mExtraInfo = getPermissionDenyStr(new String[] { this.mApiName, str3 });
      return false;
    } 
    if (!file.getParentFile().exists()) {
      this.mExtraInfo = getNoSuchFileStr(new String[] { this.mApiName, str3 });
      return false;
    } 
    ByteBuffer byteBuffer = this.mBufferData;
    if (byteBuffer != null) {
      long l = ToolUtils.getByteBufferSize(byteBuffer);
      if (FileManager.inst().isUserDirOverLimit(l)) {
        this.mExtraInfo = "user dir saved file size limit exceeded";
        return false;
      } 
      ToolUtils.writeByteBufferToFile(file.getAbsolutePath(), this.mBufferData);
      return true;
    } 
    if (!TextUtils.isEmpty(str1) && str1.getBytes() != null) {
      int i = (str1.getBytes()).length;
      if (FileManager.inst().isUserDirOverLimit(i)) {
        this.mExtraInfo = "user dir saved file size limit exceeded";
        return false;
      } 
      ToolUtils.writeStringToFile(file.getAbsolutePath(), str1, str2);
    } 
    return true;
  }
  
  protected void parseParam(ApiWriteFileParam.InputParam paramInputParam) throws Exception {
    String str2 = paramInputParam.filePath;
    Map<String, AbsFileCtrl.ValuePair> map = this.mArgumentsMap;
    String str1 = str2;
    if (ApiParamParser.isEmptyString(str2))
      str1 = ""; 
    map.put("filePath", new AbsFileCtrl.ValuePair(this, str1, true));
    str2 = paramInputParam.data;
    map = this.mArgumentsMap;
    str1 = str2;
    if (ApiParamParser.isEmptyString(str2))
      str1 = ""; 
    map.put("data", new AbsFileCtrl.ValuePair(this, str1, false));
    str2 = paramInputParam.encoding;
    map = this.mArgumentsMap;
    str1 = str2;
    if (ApiParamParser.isEmptyString(str2))
      str1 = "utf-8"; 
    map.put("encoding", new AbsFileCtrl.ValuePair(this, str1, false));
    this.mBufferData = paramInputParam.byteBuffer;
  }
  
  protected ApiWriteFileParam.OutPutParam success() {
    ApiWriteFileParam.OutPutParam outPutParam = new ApiWriteFileParam.OutPutParam();
    outPutParam.errMsg = buildErrMsg(this.mApiName, "ok", null);
    return outPutParam;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\file\write\ApiWriteFileBufferCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */