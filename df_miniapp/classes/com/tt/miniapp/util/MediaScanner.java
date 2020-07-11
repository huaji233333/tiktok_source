package com.tt.miniapp.util;

import android.content.Context;
import android.media.MediaScannerConnection;
import android.net.Uri;
import java.io.File;

public class MediaScanner {
  private SannerClient mClient;
  
  public MediaScannerConnection mConn;
  
  public File mFile;
  
  public String mMimeType;
  
  public MediaScanner(Context paramContext) {
    if (this.mClient == null)
      this.mClient = new SannerClient(); 
    if (this.mConn == null)
      this.mConn = new MediaScannerConnection(paramContext, this.mClient); 
  }
  
  public void scanFile(File paramFile, String paramString) {
    this.mFile = paramFile;
    this.mMimeType = paramString;
    this.mConn.connect();
  }
  
  class SannerClient implements MediaScannerConnection.MediaScannerConnectionClient {
    private void scan(File param1File, String param1String) {
      if (param1File.isFile()) {
        MediaScanner.this.mConn.scanFile(param1File.getAbsolutePath(), null);
        return;
      } 
      if (param1File.listFiles() == null)
        return; 
      File[] arrayOfFile = param1File.listFiles();
      int j = arrayOfFile.length;
      for (int i = 0; i < j; i++)
        scan(arrayOfFile[i], param1String); 
    }
    
    public void onMediaScannerConnected() {
      if (MediaScanner.this.mFile == null)
        return; 
      scan(MediaScanner.this.mFile, MediaScanner.this.mMimeType);
    }
    
    public void onScanCompleted(String param1String, Uri param1Uri) {
      MediaScanner.this.mConn.disconnect();
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniap\\util\MediaScanner.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */