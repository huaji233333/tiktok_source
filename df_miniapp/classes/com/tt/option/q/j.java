package com.tt.option.q;

import android.util.Log;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.monitor.AppBrandMonitor;
import java.util.ArrayList;
import org.json.JSONObject;

public class j {
  private ArrayList<h> a;
  
  public int b = -1;
  
  public String c;
  
  public String d;
  
  public byte[] e;
  
  public Throwable f;
  
  public JSONObject g;
  
  private static String a(byte[] paramArrayOfbyte) {
    try {
      return new String(paramArrayOfbyte);
    } finally {
      paramArrayOfbyte = null;
      AppBrandLogger.e("TmaResponse", new Object[] { paramArrayOfbyte });
      AppBrandMonitor.reportError("TmaResponse_convert_string", Log.getStackTraceString((Throwable)paramArrayOfbyte), "");
    } 
  }
  
  public final String a() {
    String str = this.d;
    if (str != null)
      return str; 
    byte[] arrayOfByte = this.e;
    return (arrayOfByte == null || arrayOfByte.length == 0) ? "" : a(arrayOfByte);
  }
  
  public final ArrayList<h> b() {
    if (this.a == null)
      this.a = new ArrayList<h>(); 
    return this.a;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\option\q\j.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */