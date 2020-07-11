package com.tt.miniapp.msg.file;

import com.a;
import com.tt.frontendapiinterface.ApiCallResult;
import com.tt.miniapp.msg.file.read.ApiReadFileCtrl;
import com.tt.miniapp.msg.file.write.ApiWriteFileCtrl;
import com.tt.miniapp.msg.sync.SyncMsgCtrl;
import com.tt.miniapphost.AppBrandLogger;
import org.json.JSONObject;

public class FileSystemManagerSync extends SyncMsgCtrl {
  private String functionName;
  
  public FileSystemManagerSync(String paramString1, String paramString2) {
    super(paramString2);
    this.functionName = paramString1;
  }
  
  public String act() {
    byte b;
    ApiUnlinkCtrl apiUnlinkCtrl;
    ApiSaveFileCtrl apiSaveFileCtrl;
    ApiStatCtrl apiStatCtrl;
    ApiRmDirCtrl apiRmDirCtrl;
    ApiRenameCtrl apiRenameCtrl;
    ApiReadDirCtrl apiReadDirCtrl;
    ApiMkDirCtrl apiMkDirCtrl;
    ApiCopyFileCtrl apiCopyFileCtrl;
    ApiAccessCtrl apiAccessCtrl;
    ApiReadFileCtrl<String, ApiCallResult> apiReadFileCtrl;
    ApiCallResult apiCallResult;
    if (this.functionName.equals("protocolPathToAbsPath"))
      try {
        null = (new JSONObject(this.mParams)).optString("protocolPath");
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("absPath", null);
        return makeOkMsg(jSONObject);
      } catch (Exception exception) {
        AppBrandLogger.e("FileSystemManagerSync", new Object[] { exception });
        return makeFailMsg(exception);
      }  
    ApiWriteFileCtrl apiWriteFileCtrl = null;
    String str = this.functionName;
    switch (str.hashCode()) {
      default:
        b = -1;
        break;
      case 2112368109:
        if (str.equals("readFileSync")) {
          b = 0;
          break;
        } 
      case 1713034038:
        if (str.equals("writeFileSync")) {
          b = 10;
          break;
        } 
      case 1431909580:
        if (str.equals("copyFileSync")) {
          b = 2;
          break;
        } 
      case 1317686031:
        if (str.equals("statSync")) {
          b = 7;
          break;
        } 
      case -271906454:
        if (str.equals("mkdirSync")) {
          b = 3;
          break;
        } 
      case -734079374:
        if (str.equals("readdirSync")) {
          b = 4;
          break;
        } 
      case -799949100:
        if (str.equals("saveFileSync")) {
          b = 8;
          break;
        } 
      case -1142033889:
        if (str.equals("accessSync")) {
          b = 1;
          break;
        } 
      case -1251412231:
        if (str.equals("renameSync")) {
          b = 5;
          break;
        } 
      case -1528590803:
        if (str.equals("rmdirSync")) {
          b = 6;
          break;
        } 
      case -1574561970:
        if (str.equals("unlinkSync")) {
          b = 9;
          break;
        } 
    } 
    switch (b) {
      case 10:
        apiWriteFileCtrl = new ApiWriteFileCtrl(this.functionName);
        break;
      case 9:
        apiUnlinkCtrl = new ApiUnlinkCtrl(this.functionName);
        break;
      case 8:
        apiSaveFileCtrl = new ApiSaveFileCtrl(this.functionName);
        break;
      case 7:
        apiStatCtrl = new ApiStatCtrl(this.functionName);
        break;
      case 6:
        apiRmDirCtrl = new ApiRmDirCtrl(this.functionName);
        break;
      case 5:
        apiRenameCtrl = new ApiRenameCtrl(this.functionName);
        break;
      case 4:
        apiReadDirCtrl = new ApiReadDirCtrl(this.functionName);
        break;
      case 3:
        apiMkDirCtrl = new ApiMkDirCtrl(this.functionName);
        break;
      case 2:
        apiCopyFileCtrl = new ApiCopyFileCtrl(this.functionName);
        break;
      case 1:
        apiAccessCtrl = new ApiAccessCtrl(this.functionName);
        break;
      case 0:
        apiReadFileCtrl = new ApiReadFileCtrl(this.functionName);
        break;
    } 
    if (apiReadFileCtrl != null) {
      apiCallResult = apiReadFileCtrl.invoke(this.mParams);
    } else {
      String str1 = a.a("api is not supported in app: %s", new Object[] { this.functionName });
      AppBrandLogger.e("FileSystemManagerSync", new Object[] { str1 });
      apiCallResult = ApiCallResult.a.b(this.functionName).d(str1).a();
    } 
    return apiCallResult.toString();
  }
  
  public String getName() {
    return this.functionName;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\file\FileSystemManagerSync.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */