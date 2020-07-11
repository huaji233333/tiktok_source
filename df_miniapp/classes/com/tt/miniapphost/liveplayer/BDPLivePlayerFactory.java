package com.tt.miniapphost.liveplayer;

import android.content.Context;
import com.ss.c.a.a.b.e;
import com.ss.f.a.c;
import com.tt.miniapp.liveplayer.ITTLivePlayer;
import com.tt.miniapp.liveplayer.dns.ILivePlayerDns;
import com.tt.miniapphost.liveplayer.network.LivePlayerNetworkClient;
import d.f.b.g;
import d.f.b.l;
import d.u;

public final class BDPLivePlayerFactory {
  public static final Companion Companion = new Companion(null);
  
  public static final class Companion {
    private Companion() {}
    
    public final ITTLivePlayer createDefault(Context param1Context) {
      l.b(param1Context, "applicationContext");
      return createDefault(param1Context, null);
    }
    
    public final ITTLivePlayer createDefault(Context param1Context, ILivePlayerDns param1ILivePlayerDns) {
      l.b(param1Context, "applicationContext");
      BDPLivePlayerBuilder bDPLivePlayerBuilder = (new BDPLivePlayerBuilder(param1Context)).setNetworkClient((c)new LivePlayerNetworkClient()).setIntOption(18, 1).setIntOption(4, 1).setIntOption(11, 30).setIntOption(10, 5000).setIntOption(37, 5).setIntOption(38, 1).setIntOption(48, 1);
      if (param1ILivePlayerDns != null) {
        if (param1ILivePlayerDns.getOptimizerInstance() instanceof e) {
          Object object = param1ILivePlayerDns.getOptimizerInstance();
          if (object != null) {
            bDPLivePlayerBuilder.setDns((e)object);
          } else {
            throw new u("null cannot be cast to non-null type com.ss.optimizer.live.sdk.dns.IDns");
          } 
        } 
        param1ILivePlayerDns.startOptimize();
      } 
      return bDPLivePlayerBuilder.build();
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapphost\liveplayer\BDPLivePlayerFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */