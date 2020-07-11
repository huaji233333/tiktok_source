package com.bytedance.sandboxapp.protocol.service.c;

import android.app.Activity;
import com.bytedance.sandboxapp.b.b;

public interface a extends b {
  boolean canCheckFollowAwemeState();
  
  void checkFollowAwemeState(String paramString1, String paramString2, c paramc);
  
  void getAwemeUidFromSuffixMeta(b paramb);
  
  boolean hasAwemeDepend();
  
  boolean hasLogin();
  
  void openAwemeUserProfile(Activity paramActivity, String paramString1, String paramString2, boolean paramBoolean, c paramc);
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\bytedance\sandboxapp\protocol\service\c\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */