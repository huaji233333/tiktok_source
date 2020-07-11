package com.ss.android.ugc.aweme.miniapp.g;

import android.content.Context;
import com.tt.miniapp.liveplayer.ITTLivePlayer;
import com.tt.miniapphost.liveplayer.BDPLivePlayerFactory;
import com.tt.option.e.b;
import d.f.b.l;

public final class e extends b {
  public final ITTLivePlayer createLivePlayer(Context paramContext) {
    if (paramContext != null)
      return BDPLivePlayerFactory.Companion.createDefault(paramContext); 
    ITTLivePlayer iTTLivePlayer = super.createLivePlayer(paramContext);
    l.a(iTTLivePlayer, "super.createLivePlayer(applicationContext)");
    return iTTLivePlayer;
  }
  
  public final boolean isSupportNativeLivePlayer() {
    return true;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\ss\androi\\ugc\aweme\miniapp\g\e.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */