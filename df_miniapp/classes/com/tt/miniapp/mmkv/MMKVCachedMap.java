package com.tt.miniapp.mmkv;

import com.tencent.appbrand.mmkv.MMKV;
import java.util.LinkedHashMap;
import java.util.Map;

public class MMKVCachedMap {
  public LinkedHashMap<String, MMKV> cacheMap;
  
  MMKVCachedMap(final int cacheSize) {
    this.cacheMap = new LinkedHashMap<String, MMKV>(cacheSize, 0.75F, true) {
        protected boolean removeEldestEntry(Map.Entry param1Entry) {
          if (MMKVCachedMap.this.cacheMap.size() > cacheSize) {
            if (param1Entry.getValue() != null)
              ((MMKV)param1Entry.getValue()).closeMMKV(); 
            return true;
          } 
          return false;
        }
      };
  }
  
  public boolean containsKey(String paramString) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield cacheMap : Ljava/util/LinkedHashMap;
    //   6: aload_1
    //   7: invokevirtual containsKey : (Ljava/lang/Object;)Z
    //   10: istore_2
    //   11: aload_0
    //   12: monitorexit
    //   13: iload_2
    //   14: ireturn
    //   15: astore_1
    //   16: aload_0
    //   17: monitorexit
    //   18: aload_1
    //   19: athrow
    // Exception table:
    //   from	to	target	type
    //   2	11	15	finally
  }
  
  public MMKV get(String paramString) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield cacheMap : Ljava/util/LinkedHashMap;
    //   6: aload_1
    //   7: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   10: checkcast com/tencent/appbrand/mmkv/MMKV
    //   13: astore_1
    //   14: aload_0
    //   15: monitorexit
    //   16: aload_1
    //   17: areturn
    //   18: astore_1
    //   19: aload_0
    //   20: monitorexit
    //   21: aload_1
    //   22: athrow
    // Exception table:
    //   from	to	target	type
    //   2	14	18	finally
  }
  
  public void put(String paramString, MMKV paramMMKV) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield cacheMap : Ljava/util/LinkedHashMap;
    //   6: aload_1
    //   7: aload_2
    //   8: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   11: pop
    //   12: aload_0
    //   13: monitorexit
    //   14: return
    //   15: astore_1
    //   16: aload_0
    //   17: monitorexit
    //   18: aload_1
    //   19: athrow
    // Exception table:
    //   from	to	target	type
    //   2	12	15	finally
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\mmkv\MMKVCachedMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */