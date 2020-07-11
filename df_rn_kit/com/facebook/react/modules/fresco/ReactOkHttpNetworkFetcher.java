package com.facebook.react.modules.fresco;

import android.net.Uri;
import android.os.SystemClock;
import com.facebook.imagepipeline.b.a.b;
import com.facebook.imagepipeline.n.ai;
import com.facebook.imagepipeline.n.t;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import okhttp3.ac;
import okhttp3.d;
import okhttp3.s;
import okhttp3.y;

class ReactOkHttpNetworkFetcher extends b {
  private final Executor mCancellationExecutor;
  
  private final y mOkHttpClient;
  
  public ReactOkHttpNetworkFetcher(y paramy) {
    super(paramy);
    this.mOkHttpClient = paramy;
    this.mCancellationExecutor = paramy.c.a();
  }
  
  private Map<String, String> getHeaders(ReadableMap paramReadableMap) {
    if (paramReadableMap == null)
      return null; 
    ReadableMapKeySetIterator readableMapKeySetIterator = paramReadableMap.keySetIterator();
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
    while (readableMapKeySetIterator.hasNextKey()) {
      String str = readableMapKeySetIterator.nextKey();
      hashMap.put(str, paramReadableMap.getString(str));
    } 
    return (Map)hashMap;
  }
  
  public void fetch(b.a parama, ai.a parama1) {
    Map<?, ?> map1;
    parama.a = SystemClock.elapsedRealtime();
    Uri uri = parama.c();
    if (((t)parama).e.a() instanceof ReactNetworkImageRequest) {
      map1 = getHeaders(((ReactNetworkImageRequest)((t)parama).e.a()).getHeaders());
    } else {
      map1 = null;
    } 
    Map<?, ?> map2 = map1;
    if (map1 == null)
      map2 = Collections.emptyMap(); 
    fetchWithRequest(parama, parama1, (new ac.a()).a((new d.a()).a().b()).a(uri.toString()).a(s.a(map2)).a().c());
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\modules\fresco\ReactOkHttpNetworkFetcher.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */