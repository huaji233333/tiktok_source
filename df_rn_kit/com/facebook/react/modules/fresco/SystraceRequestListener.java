package com.facebook.react.modules.fresco;

import android.util.Pair;
import com.facebook.imagepipeline.k.a;
import com.facebook.imagepipeline.o.b;
import java.util.HashMap;
import java.util.Map;

public class SystraceRequestListener extends a {
  int mCurrentID;
  
  Map<String, Pair<Integer, String>> mProducerID = new HashMap<String, Pair<Integer, String>>();
  
  Map<String, Pair<Integer, String>> mRequestsID = new HashMap<String, Pair<Integer, String>>();
  
  public void onProducerEvent(String paramString1, String paramString2, String paramString3) {}
  
  public void onProducerFinishWithCancellation(String paramString1, String paramString2, Map<String, String> paramMap) {}
  
  public void onProducerFinishWithFailure(String paramString1, String paramString2, Throwable paramThrowable, Map<String, String> paramMap) {}
  
  public void onProducerFinishWithSuccess(String paramString1, String paramString2, Map<String, String> paramMap) {}
  
  public void onProducerStart(String paramString1, String paramString2) {}
  
  public void onRequestCancellation(String paramString) {}
  
  public void onRequestFailure(b paramb, String paramString, Throwable paramThrowable, boolean paramBoolean) {}
  
  public void onRequestStart(b paramb, Object paramObject, String paramString, boolean paramBoolean) {}
  
  public void onRequestSuccess(b paramb, String paramString, boolean paramBoolean) {}
  
  public boolean requiresExtraMap(String paramString) {
    return false;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\modules\fresco\SystraceRequestListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */