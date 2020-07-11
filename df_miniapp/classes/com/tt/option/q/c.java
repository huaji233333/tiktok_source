package com.tt.option.q;

import com.tt.miniapp.settings.net.RequestService;
import okhttp3.ae;

public interface c {
  i convertMetaRequest(i parami);
  
  RequestService createSettingsResponseService();
  
  k createWsClient(k.a parama);
  
  j doGet(i parami) throws Exception;
  
  j doPostBody(i parami) throws Exception;
  
  j doPostUrlEncoded(i parami) throws Exception;
  
  j doRequest(i parami) throws Exception;
  
  g downloadFile(f paramf, a parama) throws Exception;
  
  j postMultiPart(i parami) throws Exception;
  
  public static interface a {
    void downloadFailed(String param1String, Throwable param1Throwable);
    
    void downloadSuccess(ae param1ae);
    
    void updateProgress(int param1Int, long param1Long1, long param1Long2);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\option\q\c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */