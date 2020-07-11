package com.tt.miniapp.msg.file;

import android.text.TextUtils;
import com.tt.frontendapiinterface.f;
import com.tt.frontendapiinterface.g;
import com.tt.miniapp.msg.file.read.ApiReadFileBufferCtrl;
import com.tt.miniapp.msg.file.write.ApiWriteFileBufferCtrl;
import com.tt.miniapp.msg.sync.SyncMsgCtrlV2;

public class FileSystemManagerSyncV2 extends SyncMsgCtrlV2 {
  private String mFunctionName;
  
  public FileSystemManagerSyncV2(f paramf, String paramString) {
    super(paramf);
    this.mFunctionName = paramString;
  }
  
  public g act() {
    return TextUtils.equals(this.mFunctionName, "writeFileSync") ? (g)(new ApiWriteFileBufferCtrl(this.mFunctionName)).invoke(this.mParams) : (TextUtils.equals(this.mFunctionName, "readFileSync") ? (g)(new ApiReadFileBufferCtrl(this.mFunctionName)).invoke(this.mParams) : null);
  }
  
  public String getName() {
    return this.mFunctionName;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\file\FileSystemManagerSyncV2.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */