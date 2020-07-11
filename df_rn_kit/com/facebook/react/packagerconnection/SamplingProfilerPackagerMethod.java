package com.facebook.react.packagerconnection;

import android.os.Looper;
import com.facebook.jni.HybridData;

public class SamplingProfilerPackagerMethod extends RequestOnlyHandler {
  private SamplingProfilerJniMethod mJniMethod;
  
  static {
    _lancet.com_ss_android_ugc_aweme_lancet_launch_LoadSoLancet_loadLibrary("packagerconnectionjnifb");
  }
  
  public SamplingProfilerPackagerMethod(long paramLong) {
    this.mJniMethod = new SamplingProfilerJniMethod(paramLong);
  }
  
  public void onRequest(Object paramObject, Responder paramResponder) {
    this.mJniMethod.poke(paramResponder);
  }
  
  static final class SamplingProfilerJniMethod {
    private final HybridData mHybridData;
    
    public SamplingProfilerJniMethod(long param1Long) {
      if (Looper.myLooper() == null)
        Looper.prepare(); 
      this.mHybridData = initHybrid(param1Long);
    }
    
    private static native HybridData initHybrid(long param1Long);
    
    public final native void poke(Responder param1Responder);
  }
  
  class SamplingProfilerPackagerMethod {}
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\packagerconnection\SamplingProfilerPackagerMethod.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */