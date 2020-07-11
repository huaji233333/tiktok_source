package com.tt.miniapp.ttapkgdecoder.downloader;

import g.z;
import java.io.IOException;

public interface IStreamFetcher {
  void close();
  
  long contentLength();
  
  z getDownloadInputStream(String paramString) throws IOException;
  
  boolean isAlive();
  
  void onReadFinished();
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\ttapkgdecoder\downloader\IStreamFetcher.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */