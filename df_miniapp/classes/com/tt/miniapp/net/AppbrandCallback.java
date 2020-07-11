package com.tt.miniapp.net;

import java.io.IOException;
import okhttp3.ae;
import okhttp3.e;
import okhttp3.f;

public abstract class AppbrandCallback implements f {
  public abstract String callbackFrom();
  
  public void onResponse(e parame, ae paramae) throws IOException {
    onSuccess(parame, paramae);
  }
  
  public abstract void onSuccess(e parame, ae paramae) throws IOException;
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\net\AppbrandCallback.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */