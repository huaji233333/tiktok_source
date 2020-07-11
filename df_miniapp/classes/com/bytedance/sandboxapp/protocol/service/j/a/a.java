package com.bytedance.sandboxapp.protocol.service.j.a;

import d.f.b.g;

public final class a {
  public static final a e = new a(null);
  
  public final b a;
  
  public final f b;
  
  public final e c;
  
  public final c d;
  
  public a(b paramb, f paramf, e parame, c paramc) {
    this.a = paramb;
    this.b = paramf;
    this.c = parame;
    this.d = paramc;
  }
  
  public static final class a {
    private a() {}
  }
  
  public enum b {
    FOCUS_LOSS_TRANSIENT_CAN_DUCK, FOCUS_NONE, GAIN, GAIN_TRANSIENT, GAIN_TRANSIENT_EXCLUSIVE, GAIN_TRANSIENT_MAY_DUCK, LOSS, LOSS_TRANSIENT;
    
    static {
      b b1 = new b("GAIN", 0);
      GAIN = b1;
      b b2 = new b("GAIN_TRANSIENT", 1);
      GAIN_TRANSIENT = b2;
      b b3 = new b("GAIN_TRANSIENT_MAY_DUCK", 2);
      GAIN_TRANSIENT_MAY_DUCK = b3;
      b b4 = new b("GAIN_TRANSIENT_EXCLUSIVE", 3);
      GAIN_TRANSIENT_EXCLUSIVE = b4;
      b b5 = new b("LOSS", 4);
      LOSS = b5;
      b b6 = new b("LOSS_TRANSIENT", 5);
      LOSS_TRANSIENT = b6;
      b b7 = new b("FOCUS_LOSS_TRANSIENT_CAN_DUCK", 6);
      FOCUS_LOSS_TRANSIENT_CAN_DUCK = b7;
      b b8 = new b("FOCUS_NONE", 7);
      FOCUS_NONE = b8;
      a = new b[] { b1, b2, b3, b4, b5, b6, b7, b8 };
    }
  }
  
  public static interface c {
    void onAudioFocusChanged(a.b param1b);
  }
  
  public enum d {
    FOCUS_REQUEST_DELAYED, FOCUS_REQUEST_FAILED, FOCUS_REQUEST_GRANTED;
    
    static {
      d d1 = new d("FOCUS_REQUEST_FAILED", 0);
      FOCUS_REQUEST_FAILED = d1;
      d d2 = new d("FOCUS_REQUEST_GRANTED", 1);
      FOCUS_REQUEST_GRANTED = d2;
      d d3 = new d("FOCUS_REQUEST_DELAYED", 2);
      FOCUS_REQUEST_DELAYED = d3;
      a = new d[] { d1, d2, d3 };
    }
  }
  
  public enum e {
    SELF_ONLY, SHARE;
    
    static {
      e e1 = new e("SHARE", 0);
      SHARE = e1;
      e e2 = new e("SELF_ONLY", 1);
      SELF_ONLY = e2;
      a = new e[] { e1, e2 };
    }
  }
  
  public enum f {
    USAGE_ALARM, USAGE_MEDIA;
    
    static {
      f f1 = new f("USAGE_MEDIA", 0);
      USAGE_MEDIA = f1;
      f f2 = new f("USAGE_ALARM", 1);
      USAGE_ALARM = f2;
      a = new f[] { f1, f2 };
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\bytedance\sandboxapp\protocol\service\j\a\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */