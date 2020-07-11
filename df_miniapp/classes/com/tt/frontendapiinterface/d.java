package com.tt.frontendapiinterface;

import android.text.TextUtils;

public final class d {
  public final boolean a;
  
  public final String b;
  
  private d(boolean paramBoolean, String paramString) {
    this.a = paramBoolean;
    String str = paramString;
    if (TextUtils.isEmpty(paramString))
      str = ""; 
    this.b = str;
  }
  
  public static d a() {
    return new d(true, "");
  }
  
  public static d a(String paramString) {
    return new d(false, paramString);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\frontendapiinterface\d.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */