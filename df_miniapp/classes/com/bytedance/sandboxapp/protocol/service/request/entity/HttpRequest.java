package com.bytedance.sandboxapp.protocol.service.request.entity;

import android.os.Parcel;
import android.os.Parcelable;
import d.f.b.g;
import d.f.b.l;
import java.util.Map;
import org.json.JSONObject;

public final class HttpRequest {
  public static final class RequestResult implements Parcelable {
    public static final a CREATOR = new a(null);
    
    public int a;
    
    public boolean b;
    
    public int c;
    
    public String d;
    
    public JSONObject e;
    
    public String f;
    
    public byte[] g;
    
    public String h = "";
    
    public Throwable i;
    
    public int j = -1;
    
    public RequestResult() {}
    
    public RequestResult(int param1Int) {
      this(param1Int, false, 0, null, null, null, 62, null);
    }
    
    public RequestResult(int param1Int1, boolean param1Boolean, int param1Int2, String param1String1, JSONObject param1JSONObject, String param1String2) {
      this();
      this.a = param1Int1;
      this.b = param1Boolean;
      this.c = param1Int2;
      this.d = param1String1;
      this.e = param1JSONObject;
      this.f = param1String2;
    }
    
    public RequestResult(int param1Int1, boolean param1Boolean, int param1Int2, byte[] param1ArrayOfbyte, JSONObject param1JSONObject, String param1String) {
      this();
      this.a = param1Int1;
      this.b = param1Boolean;
      this.c = param1Int2;
      this.g = param1ArrayOfbyte;
      this.e = param1JSONObject;
      this.f = param1String;
    }
    
    public RequestResult(Parcel param1Parcel) {
      this();
      boolean bool;
      this.a = param1Parcel.readInt();
      if (param1Parcel.readByte() != 0) {
        bool = true;
      } else {
        bool = false;
      } 
      this.b = bool;
      this.c = param1Parcel.readInt();
      this.d = param1Parcel.readString();
      this.f = param1Parcel.readString();
      this.g = param1Parcel.createByteArray();
      this.h = param1Parcel.readString();
      this.j = param1Parcel.readInt();
    }
    
    public final int describeContents() {
      return 0;
    }
    
    public final String toString() {
      StringBuilder stringBuilder = new StringBuilder("{success: ");
      stringBuilder.append(this.b);
      stringBuilder.append(", requestId: ");
      stringBuilder.append(this.a);
      stringBuilder.append(", statusCode: ");
      stringBuilder.append(this.c);
      stringBuilder.append(", data: ");
      stringBuilder.append(this.d);
      stringBuilder.append(", header: ");
      stringBuilder.append(this.e);
      stringBuilder.append(", responseType: ");
      stringBuilder.append(this.f);
      stringBuilder.append(", message: ");
      stringBuilder.append(this.h);
      stringBuilder.append(", failThrowable: ");
      stringBuilder.append(this.i);
      stringBuilder.append(", prefetchStatus: ");
      stringBuilder.append(this.j);
      stringBuilder.append('}');
      return stringBuilder.toString();
    }
    
    public final void writeToParcel(Parcel param1Parcel, int param1Int) {
      throw new RuntimeException("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.provideAs(TypeTransformer.java:780)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.e1expr(TypeTransformer.java:496)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:713)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.enexpr(TypeTransformer.java:698)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.s1stmt(TypeTransformer.java:810)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.sxStmt(TypeTransformer.java:840)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:206)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
    }
    
    public static final class a implements Parcelable.Creator<RequestResult> {
      private a() {}
    }
  }
  
  public static final class a implements Parcelable.Creator<RequestResult> {
    private a() {}
  }
  
  public static final class RequestTask implements Parcelable {
    public static final b CREATOR = new b(null);
    
    public final int a;
    
    public final String b;
    
    public final String c;
    
    public final boolean d;
    
    public final String e;
    
    public final byte[] f;
    
    public final JSONObject g;
    
    public final String h;
    
    public final boolean i;
    
    public final Map<String, Object> j;
    
    public final boolean k;
    
    public final boolean l;
    
    public RequestTask(int param1Int, String param1String1, String param1String2, boolean param1Boolean1, String param1String3, byte[] param1ArrayOfbyte, JSONObject param1JSONObject, String param1String4, boolean param1Boolean2, Map<String, ? extends Object> param1Map, boolean param1Boolean3, boolean param1Boolean4) {
      this.a = param1Int;
      this.b = param1String1;
      this.c = param1String2;
      this.d = param1Boolean1;
      this.e = param1String3;
      this.f = param1ArrayOfbyte;
      this.g = param1JSONObject;
      this.h = param1String4;
      this.i = param1Boolean2;
      this.j = (Map)param1Map;
      this.k = param1Boolean3;
      this.l = param1Boolean4;
    }
    
    public RequestTask(Parcel param1Parcel) {
      this(i, str1, str2, bool1, str3, arrayOfByte, jSONObject, str4, bool2, null, bool3, bool4);
    }
    
    public final int describeContents() {
      return 0;
    }
    
    public final String toString() {
      StringBuilder stringBuilder = new StringBuilder("{requestId: ");
      stringBuilder.append(this.a);
      stringBuilder.append(", url: ");
      stringBuilder.append(this.b);
      stringBuilder.append(", method: ");
      stringBuilder.append(this.c);
      stringBuilder.append(", usePrefetchCache: ");
      stringBuilder.append(this.d);
      stringBuilder.append(", data: ");
      stringBuilder.append(this.e);
      stringBuilder.append(", header: ");
      stringBuilder.append(this.g);
      stringBuilder.append(", responseType: ");
      stringBuilder.append(this.h);
      stringBuilder.append(", isSDKRequest: ");
      stringBuilder.append(this.i);
      stringBuilder.append('}');
      return stringBuilder.toString();
    }
    
    public final void writeToParcel(Parcel param1Parcel, int param1Int) {
      throw new RuntimeException("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.provideAs(TypeTransformer.java:780)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.e1expr(TypeTransformer.java:496)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:713)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.enexpr(TypeTransformer.java:698)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.s1stmt(TypeTransformer.java:810)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.sxStmt(TypeTransformer.java:840)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:206)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
    }
    
    public static final class a {
      public static final a h = new a(null);
      
      public boolean a;
      
      public int b;
      
      public Map<String, ? extends Object> c;
      
      public boolean d;
      
      public boolean e;
      
      public final String f;
      
      public final String g;
      
      private boolean i;
      
      private String j;
      
      private String k;
      
      private byte[] l;
      
      private JSONObject m;
      
      public a(String param2String1, String param2String2) {
        this.f = param2String1;
        this.g = param2String2;
      }
      
      public final a a(String param2String) {
        this.j = param2String;
        return this;
      }
      
      public final a a(JSONObject param2JSONObject) {
        this.m = param2JSONObject;
        return this;
      }
      
      public final a a(boolean param2Boolean) {
        this.i = param2Boolean;
        return this;
      }
      
      public final a a(byte[] param2ArrayOfbyte) {
        this.l = param2ArrayOfbyte;
        return this;
      }
      
      public final HttpRequest.RequestTask a() {
        return new HttpRequest.RequestTask(this.b, this.f, this.g, this.a, this.k, this.l, this.m, this.j, this.i, this.c, this.d, this.e);
      }
      
      public final a b(String param2String) {
        this.k = param2String;
        return this;
      }
      
      public static final class a {
        private a() {}
        
        public static HttpRequest.RequestTask.a a(String param3String1, String param3String2) {
          l.b(param3String1, "url");
          l.b(param3String2, "method");
          return new HttpRequest.RequestTask.a(param3String1, param3String2);
        }
      }
    }
    
    public static final class a {
      private a() {}
      
      public static HttpRequest.RequestTask.a a(String param2String1, String param2String2) {
        l.b(param2String1, "url");
        l.b(param2String2, "method");
        return new HttpRequest.RequestTask.a(param2String1, param2String2);
      }
    }
    
    public static final class b implements Parcelable.Creator<RequestTask> {
      private b() {}
    }
  }
  
  public static final class a {
    public static final a h = new a(null);
    
    public boolean a;
    
    public int b;
    
    public Map<String, ? extends Object> c;
    
    public boolean d;
    
    public boolean e;
    
    public final String f;
    
    public final String g;
    
    private boolean i;
    
    private String j;
    
    private String k;
    
    private byte[] l;
    
    private JSONObject m;
    
    public a(String param1String1, String param1String2) {
      this.f = param1String1;
      this.g = param1String2;
    }
    
    public final a a(String param1String) {
      this.j = param1String;
      return this;
    }
    
    public final a a(JSONObject param1JSONObject) {
      this.m = param1JSONObject;
      return this;
    }
    
    public final a a(boolean param1Boolean) {
      this.i = param1Boolean;
      return this;
    }
    
    public final a a(byte[] param1ArrayOfbyte) {
      this.l = param1ArrayOfbyte;
      return this;
    }
    
    public final HttpRequest.RequestTask a() {
      return new HttpRequest.RequestTask(this.b, this.f, this.g, this.a, this.k, this.l, this.m, this.j, this.i, this.c, this.d, this.e);
    }
    
    public final a b(String param1String) {
      this.k = param1String;
      return this;
    }
    
    public static final class a {
      private a() {}
      
      public static HttpRequest.RequestTask.a a(String param3String1, String param3String2) {
        l.b(param3String1, "url");
        l.b(param3String2, "method");
        return new HttpRequest.RequestTask.a(param3String1, param3String2);
      }
    }
  }
  
  public static final class a {
    private a() {}
    
    public static HttpRequest.RequestTask.a a(String param1String1, String param1String2) {
      l.b(param1String1, "url");
      l.b(param1String2, "method");
      return new HttpRequest.RequestTask.a(param1String1, param1String2);
    }
  }
  
  public static final class b implements Parcelable.Creator<RequestTask> {
    private b() {}
  }
  
  public static interface a {
    void onRequestAbort(HttpRequest.RequestTask param1RequestTask);
    
    void onRequestFinish(HttpRequest.RequestResult param1RequestResult);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\bytedance\sandboxapp\protocol\service\request\entity\HttpRequest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */