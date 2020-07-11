package com.tt.option.e;

import android.content.Context;
import android.view.View;
import com.tt.frontendapiinterface.i;
import com.tt.miniapp.component.nativeview.NativeComponent;
import com.tt.miniapp.liveplayer.ITTLivePlayer;
import com.tt.miniapp.liveplayer.dns.ILivePlayerDns;
import com.tt.miniapp.view.webcore.AbsoluteLayout;
import com.tt.miniapp.view.webcore.NativeNestWebView;
import com.tt.option.a;

public class b extends a<g> implements g {
  public ITTLivePlayer createLivePlayer(Context paramContext) {
    return null;
  }
  
  public boolean enableTTRender(View paramView) {
    return true;
  }
  
  public boolean enableTTRender(String paramString) {
    return true;
  }
  
  public ILivePlayerDns getLivePlayerDnsOptimizer(Context paramContext) {
    return null;
  }
  
  public NativeComponent getNativeComponentView(String paramString, int paramInt1, int paramInt2, AbsoluteLayout paramAbsoluteLayout, NativeNestWebView paramNativeNestWebView, i parami) {
    return null;
  }
  
  public boolean isSupportNativeLivePlayer() {
    return false;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\option\e\b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */