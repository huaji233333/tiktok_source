package com.tt.miniapphost.liveplayer;

import android.content.Context;
import android.util.SparseArray;
import android.util.SparseIntArray;
import com.ss.c.a.a.b.e;
import com.ss.f.a.a;
import com.ss.f.a.c;
import com.ss.f.a.e;
import d.f.b.l;

public final class BDPLivePlayerBuilder {
  private final Context applicationContext;
  
  private e dns;
  
  private boolean enableAutoResolutionDegrad;
  
  private boolean enableForceHttpDns;
  
  private boolean enableForceTTNetHttpDns;
  
  private boolean enableMultiProcess;
  
  private final SparseArray<Float> floatOption;
  
  private final SparseIntArray intOption;
  
  private c networkClient;
  
  private int retryTimeout;
  
  private final SparseArray<String> stringOption;
  
  public BDPLivePlayerBuilder(Context paramContext) {
    this.applicationContext = paramContext;
    this.retryTimeout = 300000;
    this.enableAutoResolutionDegrad = true;
    this.intOption = new SparseIntArray();
    this.stringOption = new SparseArray();
    this.floatOption = new SparseArray();
  }
  
  public final BDPLivePlayer build() {
    return new BDPLivePlayer(this);
  }
  
  public final e getDns() {
    return this.dns;
  }
  
  public final e getRealPlayer(a parama) {
    l.b(parama, "listener");
    e.a a1 = e.a(this.applicationContext).a("appbrand-live-player").a(this.enableForceHttpDns).b(this.retryTimeout).b(this.enableForceTTNetHttpDns).a(parama);
    if (this.enableMultiProcess) {
      i = 2;
    } else {
      i = 1;
    } 
    a1 = a1.a(i);
    c c1 = this.networkClient;
    if (c1 == null)
      l.a(); 
    e e1 = a1.a(c1).c(this.enableAutoResolutionDegrad).a();
    e e2 = this.dns;
    if (e2 != null)
      e1.a(e2); 
    int j = this.intOption.size();
    boolean bool = false;
    int i;
    for (i = 0; i < j; i++)
      e1.a(this.intOption.keyAt(i), this.intOption.valueAt(i)); 
    j = this.floatOption.size();
    for (i = 0; i < j; i++) {
      int k = this.floatOption.keyAt(i);
      Object object = this.floatOption.valueAt(i);
      l.a(object, "floatOption.valueAt(index)");
      e1.a(k, ((Number)object).floatValue());
    } 
    j = this.stringOption.size();
    for (i = bool; i < j; i++)
      e1.a(this.stringOption.keyAt(i), (String)this.stringOption.valueAt(i)); 
    l.a(e1, "VideoLiveManager.newBuil…      }\n                }");
    return e1;
  }
  
  public final BDPLivePlayerBuilder setDns(e parame) {
    l.b(parame, "dns");
    this.dns = parame;
    return this;
  }
  
  public final void setDns(e parame) {
    this.dns = parame;
  }
  
  public final BDPLivePlayerBuilder setEnableAutoResolutionDegrad(boolean paramBoolean) {
    this.enableAutoResolutionDegrad = paramBoolean;
    return this;
  }
  
  public final BDPLivePlayerBuilder setEnableForceHttpDns(boolean paramBoolean) {
    this.enableForceHttpDns = paramBoolean;
    return this;
  }
  
  public final BDPLivePlayerBuilder setEnableForceTTNetHttpDns(boolean paramBoolean) {
    this.enableForceTTNetHttpDns = paramBoolean;
    return this;
  }
  
  public final BDPLivePlayerBuilder setEnableMultiProcess(boolean paramBoolean) {
    this.enableMultiProcess = paramBoolean;
    return this;
  }
  
  public final BDPLivePlayerBuilder setFloatOption(int paramInt, float paramFloat) {
    this.floatOption.append(paramInt, Float.valueOf(paramFloat));
    return this;
  }
  
  public final BDPLivePlayerBuilder setIntOption(int paramInt1, int paramInt2) {
    this.intOption.append(paramInt1, paramInt2);
    return this;
  }
  
  public final BDPLivePlayerBuilder setNetworkClient(c paramc) {
    l.b(paramc, "networkClient");
    this.networkClient = paramc;
    return this;
  }
  
  public final BDPLivePlayerBuilder setRetryTimeout(int paramInt) {
    this.retryTimeout = paramInt;
    return this;
  }
  
  public final BDPLivePlayerBuilder setStringOption(int paramInt, String paramString) {
    l.b(paramString, "value");
    this.stringOption.append(paramInt, paramString);
    return this;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapphost\liveplayer\BDPLivePlayerBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */