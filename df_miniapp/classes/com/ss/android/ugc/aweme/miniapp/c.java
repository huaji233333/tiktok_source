package com.ss.android.ugc.aweme.miniapp;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public final class c implements g {
  private String a;
  
  public final boolean a(Context paramContext, String paramString) {
    MiniAppService.inst().getRouterDepend().b(paramContext, this.a);
    return true;
  }
  
  public final boolean a(String paramString) {
    if (TextUtils.isEmpty(paramString))
      return false; 
    try {
      String str = URLDecoder.decode(paramString, "UTF-8");
      paramString = str;
    } catch (UnsupportedEncodingException unsupportedEncodingException) {}
    this.a = Uri.parse(paramString).getQueryParameter("other_open");
    return !TextUtils.isEmpty(this.a);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\ss\androi\\ugc\aweme\miniapp\c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */