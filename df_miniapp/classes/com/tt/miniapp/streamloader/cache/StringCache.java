package com.tt.miniapp.streamloader.cache;

import java.util.concurrent.ConcurrentHashMap;

public class StringCache {
  private ConcurrentHashMap<String, StringObject> map = new ConcurrentHashMap<String, StringObject>();
  
  public String convertString(String paramString, byte[] paramArrayOfbyte) {
    StringObject stringObject2 = this.map.get(paramString);
    StringObject stringObject1 = stringObject2;
    if (stringObject2 == null) {
      stringObject1 = new StringObject();
      stringObject1.bytes = paramArrayOfbyte;
      stringObject1.convertedString = new String(paramArrayOfbyte);
      this.map.put(paramString, stringObject1);
    } 
    return stringObject1.convertedString;
  }
  
  public void release() {}
  
  class StringObject {
    byte[] bytes;
    
    String convertedString;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\streamloader\cache\StringCache.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */