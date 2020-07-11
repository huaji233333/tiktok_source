package com.tt.miniapp.util;

import android.webkit.MimeTypeMap;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

public class MimeTypeUtil {
  private static Map<String, String> mimeTypeMap = new HashMap<String, String>() {
    
    };
  
  public static String getMimeType(String paramString) {
    try {
      return URLConnection.getFileNameMap().getContentTypeFor(paramString);
    } finally {
      String str1;
      Exception exception2 = null;
      exception2 = null;
      String str3 = MimeTypeMap.getFileExtensionFromUrl(paramString);
      Exception exception1 = exception2;
      if (str3 != null) {
        String str = MimeTypeMap.getSingleton().getMimeTypeFromExtension(str3.toLowerCase());
        str1 = str;
        if (str == null)
          str1 = mimeTypeMap.get(str3.toLowerCase()); 
      } 
      String str2 = str1;
      if (str1 == null)
        str2 = "image/*"; 
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniap\\util\MimeTypeUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */