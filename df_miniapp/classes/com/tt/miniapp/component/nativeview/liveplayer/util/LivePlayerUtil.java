package com.tt.miniapp.component.nativeview.liveplayer.util;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import com.bytedance.sandboxapp.protocol.service.j.a;
import com.tt.miniapp.liveplayer.ITTLivePlayer;
import d.f.b.l;

public final class LivePlayerUtil {
  public static final LivePlayerUtil INSTANCE = new LivePlayerUtil();
  
  public final a.a convertDegreeToOrientation(int paramInt) {
    return (paramInt != -90) ? ((paramInt != 0) ? ((paramInt != 90) ? a.a.PORTRAIT : a.a.LANDSCAPE) : a.a.PORTRAIT) : a.a.REVERSE_LANDSCAPE;
  }
  
  public final ITTLivePlayer.Orientation convertDisplayOrientation(String paramString) {
    l.b(paramString, "str");
    return TextUtils.equals(paramString, ITTLivePlayer.Orientation.HORIZONTAL.getValue()) ? ITTLivePlayer.Orientation.HORIZONTAL : ITTLivePlayer.Orientation.VERTICAL;
  }
  
  public final ITTLivePlayer.ObjectFit convertObjectFit(String paramString) {
    l.b(paramString, "str");
    return TextUtils.equals(paramString, ITTLivePlayer.ObjectFit.FILLCROP.getValue()) ? ITTLivePlayer.ObjectFit.FILLCROP : ITTLivePlayer.ObjectFit.CONTAIN;
  }
  
  public final boolean isExpectScreenOrientation(Context paramContext, a.a parama) {
    l.b(paramContext, "context");
    l.b(parama, "screenOrientation");
    Resources resources = paramContext.getResources();
    l.a(resources, "context.resources");
    return ((resources.getConfiguration()).orientation == 2) ? ((parama != a.a.LANDSCAPE && parama != a.a.REVERSE_LANDSCAPE) ? ((parama == a.a.SENSOR_LANDSCAPE)) : true) : ((parama != a.a.PORTRAIT && parama != a.a.REVERSE_PORTRAIT) ? ((parama == a.a.SENSOR_PORTRAIT)) : true);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\component\nativeview\liveplaye\\util\LivePlayerUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */