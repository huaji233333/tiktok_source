package com.tt.miniapp.business.component.video;

import android.app.Activity;
import android.view.View;
import android.view.Window;
import com.bytedance.sandboxapp.b.a;
import com.bytedance.sandboxapp.protocol.service.j.a;
import com.bytedance.sandboxapp.protocol.service.j.a.a;
import com.tt.miniapp.base.MiniAppContext;
import com.tt.miniapp.business.component.video.audiofocus.BDPAudioFocusManager;
import com.tt.miniapp.business.component.video.fullscreen.AnchorTransaction;
import com.tt.miniapp.business.component.video.fullscreen.BottomBarTransaction;
import com.tt.miniapp.business.component.video.fullscreen.FullScreenTransaction;
import com.tt.miniapp.business.component.video.fullscreen.NavigationBarTransaction;
import com.tt.miniapp.business.component.video.fullscreen.PullRefreshTransaction;
import com.tt.miniapp.business.component.video.fullscreen.SwipeBackTransaction;
import com.tt.miniapp.business.component.video.fullscreen.TabHostTransaction;
import com.tt.miniapp.util.VideoUtils;
import com.tt.miniapphost.util.UIUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MediaService implements a {
  private final MiniAppContext mContext;
  
  private List<FullScreenTransaction> mFullScreenTransactions;
  
  public MediaService(MiniAppContext paramMiniAppContext) {
    this.mContext = paramMiniAppContext;
  }
  
  private void setScreenOrientation(a.a parama) {
    Activity activity = this.mContext.getCurrentActivity();
    if (activity == null)
      return; 
    int i = null.$SwitchMap$com$bytedance$sandboxapp$protocol$service$media$MediaServiceInterface$ScreenOrientation[parama.ordinal()];
    byte b = 1;
    if (i != 1) {
      if (i != 2) {
        if (i != 3) {
          if (i != 4) {
            if (i == 5)
              b = 6; 
          } else {
            b = 7;
          } 
        } else {
          b = 8;
        } 
      } else {
        b = 0;
      } 
    } else {
      b = 9;
    } 
    UIUtils.setActivityOrientation(activity, b);
  }
  
  private void setWindowFullScreen(boolean paramBoolean) {
    Activity activity = this.mContext.getCurrentActivity();
    if (activity == null)
      return; 
    Window window = activity.getWindow();
    if (window == null)
      return; 
    if (paramBoolean) {
      window.setFlags(1024, 1024);
      return;
    } 
    window.clearFlags(1024);
  }
  
  public void abandonAudioFocus(a parama) {
    if (parama == null)
      return; 
    BDPAudioFocusManager.Companion.getInstance().abandonAudioFocus(this.mContext, parama);
  }
  
  public a.d acquireAudioFocus(a parama) {
    return (parama == null) ? a.d.FOCUS_REQUEST_FAILED : BDPAudioFocusManager.Companion.getInstance().requestAudioFocus(this.mContext, parama);
  }
  
  public void enterFullScreen(View paramView) {
    enterFullScreen(paramView, a.a.LANDSCAPE);
  }
  
  public void enterFullScreen(View paramView, a.a parama) {
    if (this.mFullScreenTransactions == null) {
      this.mFullScreenTransactions = new ArrayList<FullScreenTransaction>();
      this.mFullScreenTransactions.add(new BottomBarTransaction(this.mContext));
      this.mFullScreenTransactions.add(new NavigationBarTransaction(this.mContext));
      this.mFullScreenTransactions.add(new SwipeBackTransaction(this.mContext));
      this.mFullScreenTransactions.add(new AnchorTransaction(this.mContext));
      this.mFullScreenTransactions.add(new TabHostTransaction(this.mContext));
      this.mFullScreenTransactions.add(new PullRefreshTransaction(this.mContext));
    } 
    Iterator<FullScreenTransaction> iterator = this.mFullScreenTransactions.iterator();
    while (iterator.hasNext())
      ((FullScreenTransaction)iterator.next()).enterFullScreen(); 
    if (paramView != null)
      VideoUtils.showOrHideNaviBar(paramView, false); 
    setScreenOrientation(parama);
    setWindowFullScreen(true);
  }
  
  public void exitFullScreen(View paramView) {
    List<FullScreenTransaction> list = this.mFullScreenTransactions;
    if (list != null) {
      Iterator<FullScreenTransaction> iterator = list.iterator();
      while (iterator.hasNext())
        ((FullScreenTransaction)iterator.next()).exitFullScreen(); 
    } 
    if (paramView != null)
      VideoUtils.showOrHideNaviBar(paramView, true); 
    setScreenOrientation(a.a.PORTRAIT);
    setWindowFullScreen(false);
  }
  
  public MiniAppContext getContext() {
    return this.mContext;
  }
  
  public void onDestroy() {
    List<FullScreenTransaction> list = this.mFullScreenTransactions;
    if (list != null) {
      list.clear();
      this.mFullScreenTransactions = null;
    } 
    BDPAudioFocusManager.Companion.getInstance().release();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\business\component\video\MediaService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */