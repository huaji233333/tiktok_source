package com.tt.miniapp.streamloader;

import java.net.URI;

public class PathUtils {
  public static String relativize(String paramString1, String paramString2) {
    paramString1 = URI.create(paramString2).relativize(URI.create(paramString1)).getPath();
    return paramString1.substring(paramString1.indexOf('/') + 1);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\streamloader\PathUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */