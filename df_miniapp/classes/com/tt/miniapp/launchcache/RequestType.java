package com.tt.miniapp.launchcache;

public enum RequestType {
  async, jump_batch, jump_single, normal, prefetch_host, preload, silence;
  
  static {
    RequestType requestType1 = new RequestType("normal", 0);
    normal = requestType1;
    RequestType requestType2 = new RequestType("preload", 1);
    preload = requestType2;
    RequestType requestType3 = new RequestType("async", 2);
    async = requestType3;
    RequestType requestType4 = new RequestType("prefetch_host", 3);
    prefetch_host = requestType4;
    RequestType requestType5 = new RequestType("jump_single", 4);
    jump_single = requestType5;
    RequestType requestType6 = new RequestType("jump_batch", 5);
    jump_batch = requestType6;
    RequestType requestType7 = new RequestType("silence", 6);
    silence = requestType7;
    $VALUES = new RequestType[] { requestType1, requestType2, requestType3, requestType4, requestType5, requestType6, requestType7 };
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\launchcache\RequestType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */