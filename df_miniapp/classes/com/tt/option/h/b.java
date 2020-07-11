package com.tt.option.h;

import android.text.TextUtils;

public final class b {
  public String a;
  
  public int b;
  
  public String c;
  
  public String d;
  
  public String e;
  
  public b(String paramString1, int paramInt, String paramString2, String paramString3, String paramString4) {
    this.a = paramString1;
    this.b = paramInt;
    this.c = paramString2;
    this.e = paramString3;
    paramString1 = paramString4;
    if (TextUtils.isEmpty(paramString4))
      paramString1 = "current"; 
    this.d = paramString1;
  }
  
  public final String toString() {
    StringBuilder stringBuilder = new StringBuilder("FeedBackAppInfo{mpId='");
    stringBuilder.append(this.a);
    stringBuilder.append('\'');
    stringBuilder.append(", mpType=");
    stringBuilder.append(this.b);
    stringBuilder.append(", mpName='");
    stringBuilder.append(this.c);
    stringBuilder.append('\'');
    stringBuilder.append(", mpVersionType='");
    stringBuilder.append(this.d);
    stringBuilder.append('\'');
    stringBuilder.append(", mpVersion='");
    stringBuilder.append(this.e);
    stringBuilder.append('\'');
    stringBuilder.append('}');
    return stringBuilder.toString();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\option\h\b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */