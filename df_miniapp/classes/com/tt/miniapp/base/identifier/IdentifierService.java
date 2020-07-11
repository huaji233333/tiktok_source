package com.tt.miniapp.base.identifier;

import com.bytedance.sandboxapp.b.a;
import com.bytedance.sandboxapp.protocol.service.i.a;
import com.tt.miniapp.net.RequestIDCreator;

public final class IdentifierService implements a {
  private final a context;
  
  public IdentifierService(a parama) {
    this.context = parama;
  }
  
  public final a getContext() {
    return this.context;
  }
  
  public final int getRequestIdentifyId() {
    return RequestIDCreator.create();
  }
  
  public final void onDestroy() {}
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\base\identifier\IdentifierService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */