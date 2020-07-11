package com.tt.option.t;

import android.content.Context;
import com.tt.miniapp.jsbridge.ApiPermissionManager;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.util.AppbrandUtil;
import com.tt.miniapphost.util.UIUtils;

public final class c {
  public String a;
  
  public String b;
  
  public String c;
  
  public String d;
  
  public String e;
  
  public String f;
  
  public String g;
  
  public String h;
  
  public String i;
  
  private c(a parama) {
    this.a = parama.a;
    this.b = parama.b;
    this.c = parama.c;
    this.d = parama.d;
    this.e = parama.e;
    this.f = parama.f;
    if (ApiPermissionManager.isAdSiteMiniApp()) {
      this.g = UIUtils.getString(2097741847);
    } else {
      this.g = parama.g;
    } 
    this.h = parama.h;
    this.i = parama.i;
  }
  
  public static final class a {
    public String a = UIUtils.getString(2097741845);
    
    public String b = UIUtils.getString(2097741843);
    
    public String c = UIUtils.getString(2097741844);
    
    public String d = UIUtils.getString(2097741842);
    
    public String e = UIUtils.getString(2097741841);
    
    public String f = UIUtils.getString(2097741848);
    
    public String g;
    
    public String h;
    
    public String i;
    
    public a() {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(AppbrandUtil.getApplicationName((Context)AppbrandContext.getInst().getApplicationContext()));
      stringBuilder.append(UIUtils.getString(2097741846));
      this.g = stringBuilder.toString();
      this.h = UIUtils.getString(2097741888);
      this.i = UIUtils.getString(2097742037);
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\option\t\c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */