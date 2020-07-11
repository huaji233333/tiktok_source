package com.tt.option.c;

import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import org.json.JSONObject;

public abstract class b {
  public String a;
  
  public a b;
  
  public View c;
  
  public static JSONObject a(String paramString) {
    if (TextUtils.isEmpty(paramString))
      return null; 
    try {
      return new JSONObject(Uri.parse(paramString).getQueryParameter("bottom_bar"));
    } catch (Exception exception) {
      return null;
    } 
  }
  
  public abstract int a();
  
  public final boolean a(ViewGroup paramViewGroup) {
    this.c = b(paramViewGroup);
    View view = this.c;
    if (view != null) {
      ViewParent viewParent = view.getParent();
      if (viewParent instanceof ViewGroup)
        ((ViewGroup)viewParent).removeView(this.c); 
      paramViewGroup.addView(this.c, -1, -1);
    } 
    return (this.c != null);
  }
  
  protected abstract View b(ViewGroup paramViewGroup);
  
  public final void b() {
    View view = this.c;
    if (view != null) {
      ViewParent viewParent = view.getParent();
      if (viewParent instanceof ViewGroup)
        ((ViewGroup)viewParent).removeView(this.c); 
      this.c = null;
    } 
    this.b = null;
  }
  
  public static interface a {}
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\option\c\b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */