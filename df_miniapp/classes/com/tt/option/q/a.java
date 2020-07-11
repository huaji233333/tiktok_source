package com.tt.option.q;

import com.tt.miniapp.impl.HostOptionNetDependImpl;
import com.tt.miniapp.settings.net.RequestService;
import com.tt.miniapp.settings.net.RequestServiceImpl;
import com.tt.option.a;

public class a extends a<c> implements c {
  public i convertMetaRequest(i parami) {
    i i1 = parami;
    if (inject())
      i1 = ((c)this.defaultOptionDepend).convertMetaRequest(parami); 
    return i1;
  }
  
  public RequestService createSettingsResponseService() {
    return (RequestService)(inject() ? ((c)this.defaultOptionDepend).createSettingsResponseService() : new RequestServiceImpl());
  }
  
  public k createWsClient(k.a parama) {
    return null;
  }
  
  public j doGet(i parami) throws Exception {
    return inject() ? ((c)this.defaultOptionDepend).doGet(parami) : null;
  }
  
  public j doPostBody(i parami) throws Exception {
    return inject() ? ((c)this.defaultOptionDepend).doPostBody(parami) : null;
  }
  
  public j doPostUrlEncoded(i parami) throws Exception {
    return inject() ? ((c)this.defaultOptionDepend).doPostUrlEncoded(parami) : null;
  }
  
  public j doRequest(i parami) throws Exception {
    return inject() ? ((c)this.defaultOptionDepend).doRequest(parami) : null;
  }
  
  public g downloadFile(f paramf, c.a parama) throws Exception {
    return inject() ? ((c)this.defaultOptionDepend).downloadFile(paramf, parama) : null;
  }
  
  public j postMultiPart(i parami) throws Exception {
    return inject() ? ((c)this.defaultOptionDepend).postMultiPart(parami) : null;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\option\q\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */