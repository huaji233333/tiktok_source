package com.bytedance.sandboxapp.protocol.service.h;

import android.net.Uri;
import com.bytedance.sandboxapp.b.b;

public interface c extends b {
  a getHostAppInfo();
  
  b getHostAppUserInfo();
  
  void loginHostApp(a parama);
  
  void openMiniApp(b paramb);
  
  void openSchema(c paramc, d paramd);
  
  public static interface a {
    void a();
    
    void a(String param1String);
    
    void b();
    
    void c();
  }
  
  public static final class b {
    public final String a;
    
    public final boolean b;
    
    public final boolean c;
    
    public final boolean d;
    
    public final int e;
    
    public b(String param1String, boolean param1Boolean1, boolean param1Boolean2, boolean param1Boolean3, int param1Int) {
      this.a = param1String;
      this.b = param1Boolean1;
      this.c = param1Boolean2;
      this.d = param1Boolean3;
      this.e = param1Int;
    }
  }
  
  public static final class c {
    public final Uri a;
    
    public final String b;
    
    public c(Uri param1Uri, String param1String) {
      this.a = param1Uri;
      this.b = param1String;
    }
  }
  
  public static interface d {
    void a();
    
    void a(String param1String);
    
    void b();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\bytedance\sandboxapp\protocol\service\h\c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */