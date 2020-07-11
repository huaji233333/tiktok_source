package com.ss.android.ugc.aweme.miniapp.k;

import android.text.TextUtils;
import com.ss.android.ugc.aweme.miniapp_api.b.i;
import com.ss.android.ugc.aweme.miniapp_api.model.k;
import com.storage.async.Function;
import com.storage.async.Observable;
import com.storage.async.Schedulers;
import com.storage.async.Subscriber;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.option.q.i;

public final class a {
  public static void a(String paramString, i parami) {
    if (TextUtils.isEmpty(paramString))
      return; 
    StringBuilder stringBuilder = new StringBuilder("https://developer.toutiao.com/api/apps/share/decode_token?token=");
    stringBuilder.append(paramString);
    Observable.create(new Function<k>(stringBuilder.toString()) {
        
        }).schudleOn(Schedulers.longIO()).subscribe((Subscriber)new Subscriber.ResultableSubscriber<k>(parami) {
          public final void onError(Throwable param1Throwable) {}
        });
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\ss\androi\\ugc\aweme\miniapp\k\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */