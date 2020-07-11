package com.bytedance.sandboxapp.protocol.service.request.entity;

import org.json.JSONObject;

public final class b {
  public static interface a {
    void a(b.b param1b);
    
    void a(b.c param1c);
  }
  
  public static final class b {
    public boolean a;
    
    public int b;
    
    public int c;
    
    public String d;
    
    public String e;
    
    public Throwable f;
    
    public final String toString() {
      StringBuilder stringBuilder = new StringBuilder("{success: ");
      stringBuilder.append(this.a);
      stringBuilder.append(", uploadTaskId: ");
      stringBuilder.append(this.b);
      stringBuilder.append(", statusCode: ");
      stringBuilder.append(this.c);
      stringBuilder.append(", data: ");
      stringBuilder.append(this.d);
      stringBuilder.append(", message: ");
      stringBuilder.append(this.e);
      stringBuilder.append(", failThrowable: ");
      stringBuilder.append(this.f);
      stringBuilder.append('}');
      return stringBuilder.toString();
    }
  }
  
  public static final class c {
    public int a;
    
    public int b;
    
    public long c;
    
    public long d;
    
    public final String toString() {
      StringBuilder stringBuilder = new StringBuilder("{uploadTaskId: ");
      stringBuilder.append(this.a);
      stringBuilder.append(", progress: ");
      stringBuilder.append(this.b);
      stringBuilder.append(", totalBytesSent: ");
      stringBuilder.append(this.c);
      stringBuilder.append(", totalBytesExpectedToSend: ");
      stringBuilder.append(this.d);
      stringBuilder.append('}');
      return stringBuilder.toString();
    }
  }
  
  public static final class d {
    public int a;
    
    public String b;
    
    public JSONObject c;
    
    public final String d;
    
    public final String e;
    
    public final JSONObject f;
    
    public final boolean g;
    
    public d(int param1Int, String param1String1, JSONObject param1JSONObject1, String param1String2, String param1String3, JSONObject param1JSONObject2, boolean param1Boolean) {
      this.a = param1Int;
      this.b = param1String1;
      this.c = param1JSONObject1;
      this.d = param1String2;
      this.e = param1String3;
      this.f = param1JSONObject2;
      this.g = param1Boolean;
    }
    
    public final String toString() {
      StringBuilder stringBuilder = new StringBuilder("{uploadTaskId: ");
      stringBuilder.append(this.a);
      stringBuilder.append(", url: ");
      stringBuilder.append(this.b);
      stringBuilder.append(", header: ");
      stringBuilder.append(this.c);
      stringBuilder.append(", filePath: ");
      stringBuilder.append(this.d);
      stringBuilder.append(", name: ");
      stringBuilder.append(this.e);
      stringBuilder.append(", formData: ");
      stringBuilder.append(this.f);
      stringBuilder.append('}');
      return stringBuilder.toString();
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\bytedance\sandboxapp\protocol\service\request\entity\b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */