package com.ss.android.ugc.aweme.miniapp.g;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.ss.android.ugc.aweme.miniapp.MiniAppService;
import com.ss.android.ugc.aweme.miniapp.a;
import com.ss.android.ugc.aweme.miniapp.c;
import com.ss.android.ugc.aweme.miniapp.f;
import com.ss.android.ugc.aweme.miniapp.g;
import com.ss.android.ugc.aweme.miniapp_api.a.l;
import com.tt.miniapphost.process.HostProcessBridge;
import com.tt.option.u.a;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

public class w extends a {
  private List<g> a = new ArrayList<g>();
  
  public w() {
    this.a.add(new a());
    this.a.add(new c());
  }
  
  public boolean handleAppbrandDisablePage(Context paramContext, String paramString) {
    for (g g : this.a) {
      if (g.a(paramString))
        return g.a(paramContext, paramString); 
    } 
    return false;
  }
  
  public void jumpToWebView(Context paramContext, String paramString1, String paramString2, boolean paramBoolean) {
    Integer integer;
    StringBuilder stringBuilder = new StringBuilder("sslocal://webview?url=");
    stringBuilder.append(Uri.encode(paramString1));
    stringBuilder.append("&title=");
    stringBuilder.append(Uri.encode(paramString2));
    stringBuilder.append("&hide_bar=");
    if (paramBoolean) {
      paramString1 = "1";
    } else {
      integer = Integer.valueOf(0);
    } 
    stringBuilder.append(integer);
    String str = stringBuilder.toString();
    if (paramContext != null) {
      l l = MiniAppService.inst().getRouterDepend();
      if (l != null)
        l.a(paramContext, str); 
    } 
  }
  
  public boolean navigateToVideoView(Activity paramActivity, String paramString) {
    Intent intent = new Intent();
    intent.putExtra("id", paramString);
    MiniAppService.inst().getRouterDepend().a(paramActivity, intent);
    return true;
  }
  
  public boolean openCustomerService(Context paramContext, String paramString) {
    if (paramContext != null) {
      jumpToWebView(paramContext, paramString, "", false);
      return true;
    } 
    return false;
  }
  
  public boolean openProfile(Activity paramActivity, String paramString) {
    HostProcessBridge.logEvent("enter_personal_detail", new JSONObject((f.a().a("to_user_id", paramString).a("enter_from", "mp_ranklist")).a));
    l l = MiniAppService.inst().getRouterDepend();
    return (l != null) ? l.a((Context)paramActivity, paramString, "", "mp_ranklist", "follow_button") : false;
  }
  
  public boolean openSchema(Context paramContext, String paramString) {
    // Byte code:
    //   0: aload_1
    //   1: instanceof android/app/Activity
    //   4: ifeq -> 30
    //   7: aload_1
    //   8: checkcast android/app/Activity
    //   11: invokevirtual getIntent : ()Landroid/content/Intent;
    //   14: astore_3
    //   15: aload_3
    //   16: ifnull -> 30
    //   19: aload_3
    //   20: ldc 'class_name'
    //   22: invokevirtual getStringExtra : (Ljava/lang/String;)Ljava/lang/String;
    //   25: astore #4
    //   27: goto -> 33
    //   30: aconst_null
    //   31: astore #4
    //   33: aload_2
    //   34: astore_3
    //   35: aload_2
    //   36: ifnull -> 57
    //   39: aload_2
    //   40: invokestatic parse : (Ljava/lang/String;)Landroid/net/Uri;
    //   43: invokevirtual buildUpon : ()Landroid/net/Uri$Builder;
    //   46: ldc 'from'
    //   48: aload #4
    //   50: invokevirtual appendQueryParameter : (Ljava/lang/String;Ljava/lang/String;)Landroid/net/Uri$Builder;
    //   53: invokevirtual toString : ()Ljava/lang/String;
    //   56: astore_3
    //   57: invokestatic inst : ()Lcom/ss/android/ugc/aweme/miniapp/MiniAppService;
    //   60: invokevirtual getRouterDepend : ()Lcom/ss/android/ugc/aweme/miniapp_api/a/l;
    //   63: astore_2
    //   64: aload_2
    //   65: ifnull -> 78
    //   68: aload_2
    //   69: aload_1
    //   70: aload_3
    //   71: iconst_0
    //   72: invokeinterface a : (Landroid/content/Context;Ljava/lang/String;Z)Z
    //   77: ireturn
    //   78: iconst_0
    //   79: ireturn
  }
  
  public boolean supportCustomerService() {
    return true;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\ss\androi\\ugc\aweme\miniapp\g\w.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */