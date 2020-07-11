package com.ss.android.ugc.aweme.miniapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.f.a;
import android.text.TextUtils;
import android.util.SparseArray;
import com.ss.android.ugc.aweme.miniapp.h.a;
import com.ss.android.ugc.aweme.miniapp.utils.b;
import com.ss.android.ugc.aweme.miniapp.utils.c;
import com.ss.android.ugc.aweme.miniapp.views.MainProcessProxyActivity;
import com.ss.android.ugc.aweme.miniapp_api.a.e;
import com.ss.android.ugc.aweme.miniapp_api.a.l;
import com.ss.android.ugc.aweme.miniapp_api.b.a.a;
import com.ss.android.ugc.aweme.miniapp_api.b.a.b;
import com.ss.android.ugc.aweme.miniapp_api.b.a.c;
import com.ss.android.ugc.aweme.miniapp_api.b.f;
import com.tt.b.b;
import com.tt.b.c;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapphost.entity.InitParamsEntity;
import com.tt.miniapphost.process.HostProcessBridge;
import com.tt.miniapphost.process.callback.IpcCallback;
import com.tt.miniapphost.process.data.CrossProcessDataEntity;
import com.tt.miniapphost.util.ProcessUtil;
import com.tt.option.w.f;
import com.tt.option.w.g;
import com.tt.option.w.h;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import org.json.JSONObject;

public class d implements b {
  public InitParamsEntity createInitParams() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("com.ss.android.ugc.trill");
    stringBuilder.append(".fileprovider");
    String str = stringBuilder.toString();
    SparseArray sparseArray = new SparseArray();
    sparseArray.put(1006, str);
    sparseArray.put(1007, str);
    sparseArray.put(3000, "true");
    return (new InitParamsEntity.Builder()).setAppId(MiniAppService.inst().getAid()).setChannel(MiniAppService.inst().getChannel()).setVersionCode(MiniAppService.inst().getVersionCode()).setPluginVersion(MiniAppService.inst().getPluginVersionCode()).setAppName(MiniAppService.inst().getAppName()).setUaName("").setInitLocale(new Locale("en", "")).setEnableAppbundle(true).setFeedbackAppKey("tiktok-android").setStrMap(sparseArray).build();
  }
  
  public boolean handleActivityLoginResult(int paramInt1, int paramInt2, Intent paramIntent) {
    return (paramInt1 == 0);
  }
  
  public boolean handleActivityShareResult(int paramInt1, int paramInt2, Intent paramIntent) {
    return (paramInt1 == 1);
  }
  
  public void loadImage(Context paramContext, c paramc) {
    a.a(paramContext, paramc);
  }
  
  public boolean openLoginActivity(Activity paramActivity, HashMap<String, Object> paramHashMap) {
    Intent intent = new Intent((Context)paramActivity, MainProcessProxyActivity.class);
    intent.putExtra("proxy_type", 0);
    intent.putExtra("micro_app_schema", AppbrandApplicationImpl.getInst().getSchema());
    paramActivity.startActivityForResult(intent, 0);
    return true;
  }
  
  public boolean share(Activity paramActivity, h paramh, g paramg) {
    if (paramh != null) {
      String str;
      if (paramh.appInfo == null)
        return false; 
      if (TextUtils.isEmpty(paramh.desc))
        paramh.desc = ""; 
      if (TextUtils.equals("video", paramh.channel)) {
        if (!TextUtils.isEmpty(paramh.extra)) {
          if (!TextUtils.isEmpty((String)((a)c.a(paramh.extra, a.class)).get("videoPath"))) {
            if (paramh == null)
              return false; 
            String str1 = (String)((a)c.a(paramh.extra, a.class)).get("videoPath");
            if (h.a(paramActivity, str1))
              return false; 
            e e = MiniAppService.inst().getConstantDepend();
            Intent intent = new Intent((Context)paramActivity, MainProcessProxyActivity.class);
            intent.putExtra(e.a(), str1);
            intent.putExtra("micro_app_id", paramh.appInfo.appId);
            a<String, String> a = new a();
            a.put("schema", paramh.schema);
            a.put("appId", paramh.appInfo.appId);
            a.put("appTitle", paramh.title);
            a.put("appUrl", paramh.queryString);
            a.put("cardImage", paramh.imageUrl);
            h.a(paramg, intent);
            h.a(paramh, intent, a, 1);
            intent.putExtra("micro_app_info", c.a(a));
            intent.putExtra("micro_app_class", paramActivity.getClass());
            str = UUID.randomUUID().toString();
            intent.putExtra("creation_id", str);
            intent.putExtra("shoot_way", "record_screen");
            intent.putExtra("extra_cross_process", true);
            intent.putExtra("proxy_type", 2);
            paramActivity.startActivity(intent);
            HostProcessBridge.logEvent("shoot", new JSONObject((f.a().a("shoot_way", "record_screen").a("creation_id", str).a("enter_from", "mp")).a));
            return false;
          } 
          h.a(paramActivity, (h)str, paramg);
          return false;
        } 
        h.a(paramActivity, (h)str, paramg);
        return false;
      } 
      if (TextUtils.equals("fancyCodeShare", ((h)str).channel)) {
        MiniAppService.inst().getBaseLibDepend().a(0L, new a(this) {
              public final Object a(c param1c) {
                return null;
              }
            },  new b(this, paramActivity, (h)str, paramg) {
              public final Object a(c param1c) {
                MiniAppService.inst().getRouterDepend();
                b.a(this.b);
                new f(this) {
                    public final void a(String param2String) {
                      this.a.c.onSuccess(null);
                    }
                    
                    public final void a(String param2String, boolean param2Boolean) {}
                    
                    public final void b(String param2String) {
                      this.a.c.onFail(param2String);
                    }
                  };
                return null;
              }
            }true);
        return false;
      } 
      if (TextUtils.equals("aweme_friend", ((h)str).channel)) {
        if (MiniAppService.inst().getRouterDepend() != null) {
          b.a((h)str);
          return false;
        } 
      } else {
        f f = new f(this, paramg) {
            public final void a(String param1String) {
              this.a.onSuccess(null);
            }
            
            public final void a(String param1String, boolean param1Boolean) {}
            
            public final void b(String param1String) {
              this.a.onCancel(param1String);
            }
          };
        if (str != null) {
          l l = MiniAppService.inst().getRouterDepend();
          if (!TextUtils.equals(((h)str).shareType, "chat_mergeIM") && TextUtils.equals(((h)str).shareType, "chat_merge")) {
            Intent intent = new Intent((Context)paramActivity, MainProcessProxyActivity.class);
            intent.putExtra("proxy_type", 3);
            intent.putExtra("micro_app_schema", AppbrandApplicationImpl.getInst().getSchema());
            ProcessUtil.fillCrossProcessCallbackIntent(intent, new IpcCallback(this, paramg) {
                  public final void onIpcCallback(CrossProcessDataEntity param1CrossProcessDataEntity) {
                    if (param1CrossProcessDataEntity == null) {
                      this.a.onFail(null);
                      return;
                    } 
                    if (param1CrossProcessDataEntity.getBoolean("proxy_result")) {
                      this.a.onSuccess(null);
                      return;
                    } 
                    this.a.onCancel(null);
                  }
                });
            l.a(paramActivity, intent, b.a((h)str), f);
            return false;
          } 
          l.a(paramActivity, b.a((h)str), f);
        } 
      } 
    } 
    return false;
  }
  
  public void showShareDialog(Activity paramActivity, f paramf) {
    MiniAppService.inst().getBaseLibDepend().a(0L, new a(this) {
          public final Object a(c param1c) {
            return null;
          }
        },  new b(this, paramActivity, paramf) {
          public final Object a(c param1c) {
            MiniAppService.inst().getRouterDepend().a(this.a, new f(this) {
                  public final void a(String param2String) {}
                  
                  public final void a(String param2String, boolean param2Boolean) {
                    this.a.b.onItemClick(param2String, true);
                  }
                  
                  public final void b(String param2String) {
                    this.a.b.onCancel();
                  }
                });
            return null;
          }
        }true);
  }
  
  public boolean startImagePreviewActivity(Activity paramActivity, String paramString, List<String> paramList, int paramInt) {
    MiniAppService.inst().getRouterDepend().a(paramActivity, paramString, paramList, paramInt);
    return true;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\ss\androi\\ugc\aweme\miniapp\d.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */