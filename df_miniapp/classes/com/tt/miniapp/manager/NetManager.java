package com.tt.miniapp.manager;

import android.text.TextUtils;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.option.q.c;
import com.tt.option.q.f;
import com.tt.option.q.g;
import com.tt.option.q.i;
import com.tt.option.q.j;

public class NetManager {
  private NetManager() {}
  
  public static NetManager getInst() {
    return Holder.instance;
  }
  
  public g downloadFile(f paramf, c.a parama) {
    return HostDependManager.getInst().downloadFile(paramf, parama);
  }
  
  public j request(i parami) {
    return TextUtils.equals(parami.c, "GET") ? HostDependManager.getInst().doGet(parami) : HostDependManager.getInst().doPostBody(parami);
  }
  
  public j request(String paramString) {
    return request(new i(paramString, "GET"));
  }
  
  public j requestRaw(i parami) {
    return HostDependManager.getInst().doRequest(parami);
  }
  
  static class Holder {
    static final NetManager instance = new NetManager();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\manager\NetManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */