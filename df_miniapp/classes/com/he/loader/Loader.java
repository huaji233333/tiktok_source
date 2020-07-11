package com.he.loader;

import android.net.Uri;

public interface Loader {
  void load(String paramString, Resolver paramResolver);
  
  Uri loadMedia(String paramString);
  
  byte[] loadSync(String paramString);
  
  void loadUrl(String paramString, Resolver paramResolver);
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\he\loader\Loader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */