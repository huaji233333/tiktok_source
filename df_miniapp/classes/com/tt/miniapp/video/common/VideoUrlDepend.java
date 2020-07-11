package com.tt.miniapp.video.common;

import android.text.TextUtils;
import com.a;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;

public class VideoUrlDepend {
  public static String urlWithVideoId(String paramString, Map<String, String> paramMap) {
    null = paramString;
    if (!TextUtils.isEmpty(paramString)) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(paramString);
      if (paramMap != null) {
        Iterator<Map.Entry> iterator = paramMap.entrySet().iterator();
        while (true) {
          if (iterator.hasNext()) {
            Map.Entry entry = iterator.next();
            try {
              stringBuilder.append(a.a("&%s=%s", new Object[] { entry.getKey(), URLEncoder.encode((String)entry.getValue(), "UTF-8") }));
            } catch (UnsupportedEncodingException unsupportedEncodingException) {}
            continue;
          } 
          return stringBuilder.toString();
        } 
      } 
    } else {
      return null;
    } 
    return null.toString();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\video\common\VideoUrlDepend.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */