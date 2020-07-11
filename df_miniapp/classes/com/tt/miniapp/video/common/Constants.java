package com.tt.miniapp.video.common;

public class Constants {
  public static final String BYTEDANCE_GET_PLAY_URL_V2 = i("/video/play/");
  
  public static String i(String paramString) {
    StringBuilder stringBuilder = new StringBuilder("http://ib.snssdk.com");
    stringBuilder.append(paramString);
    return stringBuilder.toString();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\video\common\Constants.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */