package com.tt.miniapp.msg.file;

import android.text.TextUtils;
import com.tt.frontendapiinterface.f;
import com.tt.frontendapiinterface.g;
import com.tt.frontendapiinterface.j;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.msg.ApiHandlerV2;
import com.tt.miniapp.msg.file.read.ApiReadFileBufferCtrl;
import com.tt.miniapp.msg.file.write.ApiWriteFileBufferCtrl;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.option.e.e;

public class FileSystemManagerV2 extends ApiHandlerV2 {
  private String mFunctionName;
  
  public FileSystemManagerV2(String paramString, f paramf, int paramInt, e parame) {
    super(paramf, paramInt, parame);
    this.mFunctionName = paramString;
  }
  
  public void act() {
    g g;
    if (TextUtils.equals("readFile", this.mFunctionName)) {
      g = (g)(new ApiReadFileBufferCtrl(this.mFunctionName)).invoke(this.mApiParams);
    } else if (TextUtils.equals("writeFile", this.mFunctionName)) {
      g = (g)(new ApiWriteFileBufferCtrl(this.mFunctionName)).invoke(this.mApiParams);
    } else {
      AppBrandLogger.e("ApiHandler", new Object[] { "api no implement", this.mFunctionName });
      g = null;
    } 
    if (g != null) {
      j j = AppbrandApplicationImpl.getInst().getJsBridge();
      if (j != null)
        j.invokeApi(getActionName(), g, this.mCallBackId); 
    } 
  }
  
  public String getActionName() {
    return this.mFunctionName;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\file\FileSystemManagerV2.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */