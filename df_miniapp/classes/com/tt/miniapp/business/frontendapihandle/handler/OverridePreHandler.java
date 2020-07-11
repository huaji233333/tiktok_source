package com.tt.miniapp.business.frontendapihandle.handler;

import com.bytedance.sandboxapp.c.a.a.a;
import com.bytedance.sandboxapp.c.a.a.b;
import com.bytedance.sandboxapp.protocol.service.api.a.a;
import com.bytedance.sandboxapp.protocol.service.api.entity.a;
import com.bytedance.sandboxapp.protocol.service.api.entity.b;
import com.tt.miniapphost.AppbrandContext;
import com.tt.option.e.f;

public class OverridePreHandler extends b {
  public OverridePreHandler(a parama) {
    super(parama);
  }
  
  public b preHandleApi(a parama, a parama1) {
    f.a a1 = AppbrandContext.getInst().getExtensionApiCreator();
    if (a1 != null) {
      if (a1.a(parama1.apiName, null) != null)
        return b.c; 
      if (a1.a(parama1.apiName, null, 0, null) != null)
        return b.c; 
    } 
    return null;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\business\frontendapihandle\handler\OverridePreHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */