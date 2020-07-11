package com.bytedance.ies.bullet.kit.rn.pkg.fastimage;

import android.support.v4.f.a;
import com.facebook.imagepipeline.k.c;
import com.facebook.imagepipeline.o.b;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public final class c implements c {
  private final Map<String, List<String>> a = (Map<String, List<String>>)new a();
  
  private int b;
  
  private long c = -1L;
  
  public final void a(com.facebook.imagepipeline.o.c paramc) {
    this.b++;
    paramc.a(this);
  }
  
  public final void onProducerEvent(String paramString1, String paramString2, String paramString3) {}
  
  public final void onProducerFinishWithCancellation(String paramString1, String paramString2, Map<String, String> paramMap) {}
  
  public final void onProducerFinishWithFailure(String paramString1, String paramString2, Throwable paramThrowable, Map<String, String> paramMap) {}
  
  public final void onProducerFinishWithSuccess(String paramString1, String paramString2, Map<String, String> paramMap) {
    List<String> list = this.a.get(paramString1);
    if (list != null)
      list.add(paramString2); 
  }
  
  public final void onProducerStart(String paramString1, String paramString2) {}
  
  public final void onRequestCancellation(String paramString) {
    this.a.remove(paramString);
  }
  
  public final void onRequestFailure(b paramb, String paramString, Throwable paramThrowable, boolean paramBoolean) {
    this.a.remove(paramString);
  }
  
  public final void onRequestStart(b paramb, Object paramObject, String paramString, boolean paramBoolean) {
    this.a.put(paramString, new LinkedList<String>());
    if (this.c == -1L)
      this.c = System.currentTimeMillis(); 
  }
  
  public final void onRequestSuccess(b paramb, String paramString, boolean paramBoolean) {
    List list = this.a.remove(paramString);
    if (list != null) {
      list.contains("NetworkFetchProducer");
      if (this.c > 0L) {
        System.currentTimeMillis();
        this.c = -1L;
      } 
    } 
  }
  
  public final void onUltimateProducerReached(String paramString1, String paramString2, boolean paramBoolean) {}
  
  public final boolean requiresExtraMap(String paramString) {
    return false;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\bytedance\ies\bullet\kit\rn\pkg\fastimage\c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */