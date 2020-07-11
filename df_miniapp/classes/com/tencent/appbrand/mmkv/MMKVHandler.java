package com.tencent.appbrand.mmkv;

public interface MMKVHandler {
  void mmkvLog(c paramc, String paramString1, int paramInt, String paramString2, String paramString3);
  
  d onMMKVCRCCheckFail(String paramString);
  
  d onMMKVFileLengthError(String paramString);
  
  boolean wantLogRedirecting();
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tencent\appbrand\mmkv\MMKVHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */