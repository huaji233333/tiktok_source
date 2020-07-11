package com.tt.miniapp.debug.network;

import java.io.IOException;

public interface ResponseHandler {
  void onEOF();
  
  void onError(IOException paramIOException);
  
  void onRead(int paramInt);
  
  void onReadDecoded(int paramInt);
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\debug\network\ResponseHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */