package com.tt.option.q;

public final class h {
  public final String a;
  
  public String b;
  
  public h(String paramString1, String paramString2) {
    this.a = paramString1;
    this.b = paramString2;
  }
  
  public final boolean equals(Object paramObject) {
    if (this == paramObject)
      return true; 
    if (paramObject != null) {
      if (getClass() != paramObject.getClass())
        return false; 
      paramObject = paramObject;
      String str = this.a;
      if (str != null) {
        if (!str.equals(((h)paramObject).a))
          return false; 
      } else if (((h)paramObject).a != null) {
        return false;
      } 
      str = this.b;
      paramObject = ((h)paramObject).b;
      if (str != null) {
        if (!str.equals(paramObject))
          return false; 
      } else if (paramObject != null) {
        return false;
      } 
      return true;
    } 
    return false;
  }
  
  public final int hashCode() {
    byte b;
    String str = this.a;
    int i = 0;
    if (str != null) {
      b = str.hashCode();
    } else {
      b = 0;
    } 
    str = this.b;
    if (str != null)
      i = str.hashCode(); 
    return b * 31 + i;
  }
  
  public final String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    String str = this.a;
    if (str == null)
      str = ""; 
    stringBuilder.append(str);
    stringBuilder.append(": ");
    str = this.b;
    if (str == null)
      str = ""; 
    stringBuilder.append(str);
    return stringBuilder.toString();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\option\q\h.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */