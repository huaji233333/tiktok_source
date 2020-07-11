package com.tt.option.e;

import android.content.Context;
import android.view.View;
import com.tt.frontendapiinterface.i;
import com.tt.miniapp.component.nativeview.NativeComponent;
import com.tt.miniapp.liveplayer.ITTLivePlayer;
import com.tt.miniapp.liveplayer.dns.ILivePlayerDns;
import com.tt.miniapp.view.webcore.AbsoluteLayout;
import com.tt.miniapp.view.webcore.NativeNestWebView;

public interface g {
  ITTLivePlayer createLivePlayer(Context paramContext);
  
  boolean enableTTRender(View paramView);
  
  boolean enableTTRender(String paramString);
  
  ILivePlayerDns getLivePlayerDnsOptimizer(Context paramContext);
  
  NativeComponent getNativeComponentView(String paramString, int paramInt1, int paramInt2, AbsoluteLayout paramAbsoluteLayout, NativeNestWebView paramNativeNestWebView, i parami);
  
  boolean isSupportNativeLivePlayer();
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\option\e\g.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */