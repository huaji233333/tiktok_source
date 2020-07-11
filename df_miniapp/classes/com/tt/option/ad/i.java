package com.tt.option.ad;

import android.os.Bundle;
import com.tt.option.ad.a.a;

public interface i {
  a createAdSiteDxppManager();
  
  e createAdViewManager(e.a parama);
  
  g createGameAdManager(g.a parama);
  
  j createVideoPatchAdManager(j.a parama);
  
  Bundle getAdConfig();
  
  void initAdDepend();
  
  boolean isSupportAd(c paramc);
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\option\ad\i.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */