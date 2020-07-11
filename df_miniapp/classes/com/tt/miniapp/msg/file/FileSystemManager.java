package com.tt.miniapp.msg.file;

import com.a;
import com.tt.frontendapiinterface.ApiCallResult;
import com.tt.frontendapiinterface.b;
import com.tt.miniapp.msg.file.read.ApiReadFileCtrl;
import com.tt.miniapp.msg.file.write.ApiWriteFileCtrl;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.option.e.e;

public class FileSystemManager extends b {
  private String functionName;
  
  public FileSystemManager(String paramString1, String paramString2, int paramInt, e parame) {
    super(paramString2, paramInt, parame);
    this.functionName = paramString1;
  }
  
  public void act() {
    byte b1;
    ApiUnzipCtrl apiUnzipCtrl;
    ApiUnlinkCtrl apiUnlinkCtrl;
    ApiStatCtrl apiStatCtrl;
    ApiReadDirCtrl apiReadDirCtrl;
    ApiRmDirCtrl apiRmDirCtrl;
    ApiRenameCtrl apiRenameCtrl;
    ApiReadFileCtrl apiReadFileCtrl;
    ApiMkDirCtrl apiMkDirCtrl;
    ApiCopyFileCtrl apiCopyFileCtrl;
    ApiAccessCtrl apiAccessCtrl;
    ApiWriteFileCtrl<String, ApiCallResult> apiWriteFileCtrl;
    ApiCallResult apiCallResult;
    String str = this.functionName;
    switch (str.hashCode()) {
      default:
        b1 = -1;
        break;
      case 1080408887:
        if (str.equals("readdir")) {
          b1 = 7;
          break;
        } 
      case 111449576:
        if (str.equals("unzip")) {
          b1 = 10;
          break;
        } 
      case 108628082:
        if (str.equals("rmdir")) {
          b1 = 6;
          break;
        } 
      case 103950895:
        if (str.equals("mkdir")) {
          b1 = 3;
          break;
        } 
      case 3540564:
        if (str.equals("stat")) {
          b1 = 8;
          break;
        } 
      case -506374511:
        if (str.equals("copyFile")) {
          b1 = 2;
          break;
        } 
      case -840447469:
        if (str.equals("unlink")) {
          b1 = 9;
          break;
        } 
      case -867956686:
        if (str.equals("readFile")) {
          b1 = 4;
          break;
        } 
      case -934594754:
        if (str.equals("rename")) {
          b1 = 5;
          break;
        } 
      case -1406748165:
        if (str.equals("writeFile")) {
          b1 = 0;
          break;
        } 
      case -1423461020:
        if (str.equals("access")) {
          b1 = 1;
          break;
        } 
    } 
    switch (b1) {
      default:
        str = null;
        break;
      case 10:
        apiUnzipCtrl = new ApiUnzipCtrl(this.functionName);
        break;
      case 9:
        apiUnlinkCtrl = new ApiUnlinkCtrl(this.functionName);
        break;
      case 8:
        apiStatCtrl = new ApiStatCtrl(this.functionName);
        break;
      case 7:
        apiReadDirCtrl = new ApiReadDirCtrl(this.functionName);
        break;
      case 6:
        apiRmDirCtrl = new ApiRmDirCtrl(this.functionName);
        break;
      case 5:
        apiRenameCtrl = new ApiRenameCtrl(this.functionName);
        break;
      case 4:
        apiReadFileCtrl = new ApiReadFileCtrl(this.functionName);
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
        apiWriteFileCtrl = new ApiWriteFileCtrl(this.functionName);
        break;
    } 
    if (apiWriteFileCtrl != null) {
      apiCallResult = apiWriteFileCtrl.invoke(this.mArgs);
    } else {
      String str1 = a.a("api is not supported in app: %s", new Object[] { this.functionName });
      AppBrandLogger.e("FileSystemManager", new Object[] { str1 });
      apiCallResult = ApiCallResult.a.b(this.functionName).d(str1).a();
    } 
    callbackApiHandleResult(apiCallResult);
  }
  
  public String getActionName() {
    return this.functionName;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\file\FileSystemManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */