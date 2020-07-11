package com.facebook.react.modules.fresco;

import com.facebook.imagepipeline.o.b;
import com.facebook.imagepipeline.o.c;
import com.facebook.react.bridge.ReadableMap;

public class ReactNetworkImageRequest extends b {
  private final ReadableMap mHeaders;
  
  protected ReactNetworkImageRequest(c paramc, ReadableMap paramReadableMap) {
    super(paramc);
    this.mHeaders = paramReadableMap;
  }
  
  public static ReactNetworkImageRequest fromBuilderWithHeaders(c paramc, ReadableMap paramReadableMap) {
    return new ReactNetworkImageRequest(paramc, paramReadableMap);
  }
  
  public ReadableMap getHeaders() {
    return this.mHeaders;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\modules\fresco\ReactNetworkImageRequest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */