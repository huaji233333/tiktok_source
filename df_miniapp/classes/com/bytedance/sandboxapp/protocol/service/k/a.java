package com.bytedance.sandboxapp.protocol.service.k;

import com.bytedance.sandboxapp.b.b;
import com.tt.miniapp.notification.MiniAppNotificationManager;

public interface a extends b {
  c createPayNotification();
  
  void payOnH5(String paramString1, String paramString2, b paramb, a parama);
  
  void reportPayInformation();
  
  public static interface a {
    void a();
    
    void a(String param1String);
    
    void b();
    
    void c();
  }
  
  public static final class b {
    public final int a;
    
    public final int b;
    
    public final int c;
    
    public final int d;
    
    public b(int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
      this.a = param1Int1;
      this.b = param1Int2;
      this.c = param1Int3;
      this.d = param1Int4;
    }
  }
  
  public static final class c {
    private final MiniAppNotificationManager.NotificationEntity a;
    
    public c(MiniAppNotificationManager.NotificationEntity param1NotificationEntity) {
      this.a = param1NotificationEntity;
    }
    
    public final void a() {
      MiniAppNotificationManager.cancelPayNotification(this.a);
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\bytedance\sandboxapp\protocol\service\k\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */