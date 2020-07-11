package com.ss.android.ugc.aweme.miniapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v4.f.a;
import android.text.TextUtils;
import com.ss.android.ugc.aweme.miniapp.utils.c;
import com.ss.android.ugc.aweme.miniapp.views.MainProcessProxyActivity;
import com.tt.miniapphost.process.HostProcessBridge;
import com.tt.miniapphost.process.callback.IpcCallback;
import com.tt.miniapphost.process.data.CrossProcessDataEntity;
import com.tt.miniapphost.util.ProcessUtil;
import com.tt.option.w.g;
import java.io.IOException;
import java.util.UUID;
import org.json.JSONObject;

public final class h {
  public static void a(Activity paramActivity, com.tt.option.w.h paramh, g paramg) {
    if (TextUtils.equals("video", paramh.channel)) {
      Intent intent = new Intent((Context)paramActivity, MainProcessProxyActivity.class);
      a<String, String> a = new a();
      a.put("schema", paramh.schema);
      a.put("appId", paramh.appInfo.appId);
      a.put("appTitle", paramh.title);
      a.put("appUrl", paramh.queryString);
      a.put("cardImage", paramh.imageUrl);
      intent.putExtra("micro_app_class", paramActivity.getClass());
      a(paramh, intent, a, 2);
      intent.putExtra("micro_app_info", c.a(a));
      intent.putExtra("translation_type", 3);
      String str = UUID.randomUUID().toString();
      intent.putExtra("creation_id", str);
      intent.putExtra("shoot_way", "mp_record");
      a(paramg, intent);
      intent.putExtra("proxy_type", 1);
      paramActivity.startActivity(intent);
      HostProcessBridge.logEvent("shoot", new JSONObject((f.a().a("shoot_way", "mp_record").a("creation_id", str)).a));
    } 
  }
  
  static void a(g paramg, Intent paramIntent) {
    ProcessUtil.fillCrossProcessCallbackIntent(paramIntent, new IpcCallback(paramg) {
          public final void onIpcCallback(CrossProcessDataEntity param1CrossProcessDataEntity) {
            g g1 = this.a;
            if (g1 != null) {
              if (param1CrossProcessDataEntity == null) {
                g1.onFail(null);
                return;
              } 
              if (param1CrossProcessDataEntity.getBoolean("proxy_result")) {
                this.a.onSuccess(null);
                return;
              } 
              this.a.onCancel(null);
            } 
          }
          
          public final void onIpcConnectError() {
            this.a.onFail("ipc fail");
          }
        });
  }
  
  static void a(com.tt.option.w.h paramh, Intent paramIntent, a<String, String> parama, int paramInt) {
    // Byte code:
    //   0: aload_0
    //   1: ifnonnull -> 15
    //   4: new org/json/JSONObject
    //   7: dup
    //   8: invokespecial <init> : ()V
    //   11: astore_0
    //   12: goto -> 48
    //   15: aload_0
    //   16: getfield extra : Ljava/lang/String;
    //   19: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   22: ifeq -> 36
    //   25: new org/json/JSONObject
    //   28: dup
    //   29: invokespecial <init> : ()V
    //   32: astore_0
    //   33: goto -> 48
    //   36: new org/json/JSONObject
    //   39: dup
    //   40: aload_0
    //   41: getfield extra : Ljava/lang/String;
    //   44: invokespecial <init> : (Ljava/lang/String;)V
    //   47: astore_0
    //   48: aload_0
    //   49: ldc 'sticker_id'
    //   51: invokevirtual optString : (Ljava/lang/String;)Ljava/lang/String;
    //   54: astore #4
    //   56: aload #4
    //   58: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   61: ifne -> 73
    //   64: aload_1
    //   65: ldc 'sticker_id'
    //   67: aload #4
    //   69: invokevirtual putExtra : (Ljava/lang/String;Ljava/lang/String;)Landroid/content/Intent;
    //   72: pop
    //   73: aload_0
    //   74: ldc 'timor_video_source'
    //   76: iload_3
    //   77: invokevirtual put : (Ljava/lang/String;I)Lorg/json/JSONObject;
    //   80: pop
    //   81: aload_2
    //   82: ldc 'extra'
    //   84: aload_0
    //   85: invokevirtual toString : ()Ljava/lang/String;
    //   88: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   91: pop
    //   92: return
    //   93: astore_0
    //   94: return
    // Exception table:
    //   from	to	target	type
    //   4	12	93	org/json/JSONException
    //   15	33	93	org/json/JSONException
    //   36	48	93	org/json/JSONException
    //   48	73	93	org/json/JSONException
    //   73	92	93	org/json/JSONException
  }
  
  static boolean a(Activity paramActivity, String paramString) {
    MediaPlayer mediaPlayer = new MediaPlayer();
    try {
      mediaPlayer.setDataSource(paramString);
      mediaPlayer.prepare();
      if (mediaPlayer.getDuration() < 3000L) {
        MiniAppService.inst().getPopToastDepend().a(paramActivity, paramActivity.getString(2097742069));
        return true;
      } 
    } catch (IOException iOException) {}
    return false;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\ss\androi\\ugc\aweme\miniapp\h.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */