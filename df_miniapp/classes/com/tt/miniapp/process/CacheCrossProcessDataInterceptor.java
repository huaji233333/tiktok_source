package com.tt.miniapp.process;

import com.tt.miniapp.process.interceptor.ISyncCallInterceptor;
import com.tt.miniapphost.process.data.CrossProcessDataEntity;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class CacheCrossProcessDataInterceptor implements ISyncCallInterceptor {
  private static final List<String> ENABLE_CACHE_HOST_PROCESS_CALL_TYPE_LIST = Arrays.asList(new String[] { "getUserInfo", "getLoginCookie", "getPlatformSession", "getNetCommonParams" });
  
  private HashMap<String, CrossProcessDataEntity> mCacheProcessDataMap = new HashMap<String, CrossProcessDataEntity>();
  
  private boolean isEnableCacheCrossProcessData(String paramString) {
    return ENABLE_CACHE_HOST_PROCESS_CALL_TYPE_LIST.contains(paramString);
  }
  
  public CrossProcessDataEntity interceptSyncCallResult(String paramString, CrossProcessDataEntity paramCrossProcessDataEntity, boolean paramBoolean) {
    // Byte code:
    //   0: aload_0
    //   1: aload_1
    //   2: invokespecial isEnableCacheCrossProcessData : (Ljava/lang/String;)Z
    //   5: ifeq -> 79
    //   8: ldc 'CacheCrossProcessDataInterceptor'
    //   10: iconst_4
    //   11: anewarray java/lang/Object
    //   14: dup
    //   15: iconst_0
    //   16: ldc 'isEnableCacheCrossProcessData callType:'
    //   18: aastore
    //   19: dup
    //   20: iconst_1
    //   21: aload_1
    //   22: aastore
    //   23: dup
    //   24: iconst_2
    //   25: ldc 'crossProcessCallSuccess:'
    //   27: aastore
    //   28: dup
    //   29: iconst_3
    //   30: iload_3
    //   31: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   34: aastore
    //   35: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   38: aload_0
    //   39: monitorenter
    //   40: iload_3
    //   41: ifeq -> 58
    //   44: aload_0
    //   45: getfield mCacheProcessDataMap : Ljava/util/HashMap;
    //   48: aload_1
    //   49: aload_2
    //   50: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   53: pop
    //   54: aload_0
    //   55: monitorexit
    //   56: aload_2
    //   57: areturn
    //   58: aload_0
    //   59: getfield mCacheProcessDataMap : Ljava/util/HashMap;
    //   62: aload_1
    //   63: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   66: checkcast com/tt/miniapphost/process/data/CrossProcessDataEntity
    //   69: astore_1
    //   70: aload_0
    //   71: monitorexit
    //   72: aload_1
    //   73: areturn
    //   74: astore_1
    //   75: aload_0
    //   76: monitorexit
    //   77: aload_1
    //   78: athrow
    //   79: aload_2
    //   80: areturn
    // Exception table:
    //   from	to	target	type
    //   44	56	74	finally
    //   58	72	74	finally
    //   75	77	74	finally
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\process\CacheCrossProcessDataInterceptor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */