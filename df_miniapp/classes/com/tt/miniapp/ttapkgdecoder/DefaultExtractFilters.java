package com.tt.miniapp.ttapkgdecoder;

import android.text.TextUtils;
import com.tt.miniapp.ttapkgdecoder.utils.MediaUtils;
import java.util.Locale;

public class DefaultExtractFilters {
  public static ExtractFilter all() {
    return new ExtractFilter() {
        public final boolean filter(String param1String) {
          return true;
        }
      };
  }
  
  public static ExtractFilter audio() {
    return new ExtractFilter() {
        public final boolean filter(String param1String) {
          if (!TextUtils.isEmpty(param1String)) {
            MediaUtils.MediaFileType mediaFileType = MediaUtils.getFileType(param1String);
            if (mediaFileType != null && MediaUtils.isAudioFileType(mediaFileType.fileType))
              return true; 
          } 
          return false;
        }
      };
  }
  
  public static ExtractFilter media() {
    return new ExtractFilter() {
        public final boolean filter(String param1String) {
          if (!TextUtils.isEmpty(param1String)) {
            MediaUtils.MediaFileType mediaFileType = MediaUtils.getFileType(param1String);
            if (mediaFileType != null && (MediaUtils.isVideoFileType(mediaFileType.fileType) || MediaUtils.isAudioFileType(mediaFileType.fileType)))
              return true; 
          } 
          return false;
        }
      };
  }
  
  public static ExtractFilter merge(ExtractFilter... extractFilters) {
    return new ExtractFilter() {
        public final boolean filter(String param1String) {
          for (ExtractFilter extractFilter : extractFilters) {
            if (extractFilter != null && extractFilter.filter(param1String))
              return true; 
          } 
          return false;
        }
      };
  }
  
  public static ExtractFilter none() {
    return new ExtractFilter() {
        public final boolean filter(String param1String) {
          return false;
        }
      };
  }
  
  public static ExtractFilter zip() {
    return new ExtractFilter() {
        public final boolean filter(String param1String) {
          if (!TextUtils.isEmpty(param1String)) {
            int i = param1String.lastIndexOf('.');
            return (i < 0) ? false : "ZIP".equals(param1String.substring(i + 1).toUpperCase(Locale.ROOT));
          } 
          return false;
        }
      };
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\ttapkgdecoder\DefaultExtractFilters.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */