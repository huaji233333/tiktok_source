package com.tt.miniapp.impl;

import android.text.TextUtils;
import com.tt.miniapp.process.bridge.InnerHostProcessBridge;
import com.tt.option.z.b;

public class HostCheckSessionDependImpl implements b {
  public void checkSession(String paramString, b.a parama) {
    paramString = InnerHostProcessBridge.getPlatformSession(paramString);
    parama.onSessionChecked(TextUtils.isEmpty(paramString) ^ true, paramString);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\impl\HostCheckSessionDependImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */