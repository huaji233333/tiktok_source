package com.tt.miniapp.base.file;

import com.bytedance.sandboxapp.b.a;
import com.bytedance.sandboxapp.protocol.service.f.a;
import com.tt.miniapp.storage.filestorge.FileManager;
import d.f.b.l;
import java.io.File;

public final class FileService implements a {
  private final a context;
  
  public FileService(a parama) {
    this.context = parama;
  }
  
  public final boolean canWrite(String paramString) {
    l.b(paramString, "filePath");
    File file = new File(FileManager.inst().getRealFilePath(paramString));
    return FileManager.inst().canWrite(file);
  }
  
  public final a getContext() {
    return this.context;
  }
  
  public final boolean isParentDirExists(String paramString) {
    l.b(paramString, "filePath");
    File file = (new File(FileManager.inst().getRealFilePath(paramString))).getParentFile();
    return (file == null) ? false : file.exists();
  }
  
  public final void onDestroy() {}
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\base\file\FileService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */