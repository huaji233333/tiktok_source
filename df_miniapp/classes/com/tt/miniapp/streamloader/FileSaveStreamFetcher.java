package com.tt.miniapp.streamloader;

import com.tt.miniapp.ttapkgdecoder.downloader.IStreamFetcher;
import com.tt.miniapp.ttapkgdecoder.utils.DecodeException;
import com.tt.miniapphost.AppBrandLogger;
import g.g;
import g.q;
import g.z;
import java.io.File;
import java.io.IOException;
import okhttp3.ae;
import okhttp3.af;

public class FileSaveStreamFetcher implements IStreamFetcher {
  private af body;
  
  private ReplicatingSource mMirroredSource;
  
  private String mPkgCompressType;
  
  private final File mSaveFile;
  
  private ae response;
  
  public FileSaveStreamFetcher(File paramFile) {
    this(paramFile, "");
  }
  
  public FileSaveStreamFetcher(File paramFile, String paramString) {
    if (paramFile.exists())
      paramFile.delete(); 
    this.mSaveFile = paramFile;
    this.mPkgCompressType = paramString;
  }
  
  public void close() {
    ReplicatingSource replicatingSource = this.mMirroredSource;
    if (replicatingSource != null)
      try {
        replicatingSource.close();
      } finally {} 
    ae ae1 = this.response;
    if (ae1 != null)
      try {
        ae1.close();
        return;
      } catch (Exception exception) {
        return;
      }  
  }
  
  public long contentLength() {
    af af1 = this.body;
    return (af1 != null) ? af1.contentLength() : 0L;
  }
  
  public z getDownloadInputStream(String paramString) throws IOException {
    byte b;
    this.response = StreamLoaderUtils.getResponse(paramString, this.mPkgCompressType);
    ae ae2 = this.response;
    if (ae2 != null) {
      af af1 = ae2.g;
    } else {
      ae2 = null;
    } 
    this.body = (af)ae2;
    ae2 = this.response;
    if (ae2 != null && ae2.a()) {
      af af1 = this.body;
      if (af1 != null) {
        this.mMirroredSource = new ReplicatingSource((z)af1.source());
        return (z)this.mMirroredSource;
      } 
    } 
    File file = this.mSaveFile;
    if (file != null && file.exists())
      this.mSaveFile.delete(); 
    ae ae1 = this.response;
    if (ae1 != null) {
      b = ae1.c;
    } else {
      b = -2;
    } 
    throw new DecodeException(b);
  }
  
  public boolean isAlive() {
    ae ae1 = this.response;
    return (ae1 != null && ae1.a());
  }
  
  public void onReadFinished() {
    File file = this.mSaveFile;
    if (file != null) {
      file = file.getParentFile();
      if (!file.exists() && !file.mkdirs())
        return; 
      File file1 = null;
      g g3 = null;
      g g2 = null;
      file = file1;
      g g1 = g3;
      try {
        if (this.mSaveFile.createNewFile()) {
          file = file1;
          g1 = g3;
          g2 = q.a(q.a(this.mSaveFile));
          g g = g2;
          g1 = g2;
          g2.a(this.mMirroredSource.getReplBuffer(), (this.mMirroredSource.getReplBuffer()).b);
        } 
        if (g2 != null)
          try {
            g2.close();
            return;
          } catch (IOException iOException) {
            return;
          }  
      } catch (IOException iOException) {
        g g = g1;
        AppBrandLogger.e("FileSaveStreamFetcher", new Object[] { "save file failed!", iOException });
        g = g1;
        if (this.mSaveFile.exists()) {
          g = g1;
          this.mSaveFile.delete();
        } 
        if (g1 != null)
          try {
            g1.close();
            return;
          } catch (IOException iOException1) {
            return;
          }  
      } finally {}
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\streamloader\FileSaveStreamFetcher.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */