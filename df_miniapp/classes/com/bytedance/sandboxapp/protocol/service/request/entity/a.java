package com.bytedance.sandboxapp.protocol.service.request.entity;

import org.json.JSONObject;

public final class a {
  public static interface a {
    void a(a.b param1b);
    
    void a(a.c param1c);
  }
  
  public static final class b {
    public boolean a;
    
    public int b;
    
    public int c;
    
    public String d;
    
    public String e;
    
    public String f;
    
    public Throwable g;
    
    public final String toString() {
      StringBuilder stringBuilder = new StringBuilder("{success: ");
      stringBuilder.append(this.a);
      stringBuilder.append(", downloadTaskId: ");
      stringBuilder.append(this.b);
      stringBuilder.append(", statusCode: ");
      stringBuilder.append(this.c);
      stringBuilder.append(", filePath: ");
      stringBuilder.append(this.d);
      stringBuilder.append(", tempFilePath: ");
      stringBuilder.append(this.e);
      stringBuilder.append(", message: ");
      stringBuilder.append(this.f);
      stringBuilder.append(", failThrowable: ");
      stringBuilder.append(this.g);
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
      StringBuilder stringBuilder = new StringBuilder("{downloadTaskId: ");
      stringBuilder.append(this.a);
      stringBuilder.append(", progress: ");
      stringBuilder.append(this.b);
      stringBuilder.append(", totalBytesWritten: ");
      stringBuilder.append(this.c);
      stringBuilder.append(", totalBytesExpectedToWrite: ");
      stringBuilder.append(this.d);
      stringBuilder.append('}');
      return stringBuilder.toString();
    }
  }
  
  public static final class d {
    public final int a;
    
    public final String b;
    
    public final JSONObject c;
    
    public final String d;
    
    public final boolean e;
    
    public d(int param1Int, String param1String1, JSONObject param1JSONObject, String param1String2, boolean param1Boolean) {
      this.a = param1Int;
      this.b = param1String1;
      this.c = param1JSONObject;
      this.d = param1String2;
      this.e = param1Boolean;
    }
    
    public final String toString() {
      StringBuilder stringBuilder = new StringBuilder("{downloadTaskId: ");
      stringBuilder.append(this.a);
      stringBuilder.append(", url: ");
      stringBuilder.append(this.b);
      stringBuilder.append(", header: ");
      stringBuilder.append(this.c);
      stringBuilder.append(", filePath: ");
      stringBuilder.append(this.d);
      stringBuilder.append('}');
      return stringBuilder.toString();
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\bytedance\sandboxapp\protocol\service\request\entity\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */