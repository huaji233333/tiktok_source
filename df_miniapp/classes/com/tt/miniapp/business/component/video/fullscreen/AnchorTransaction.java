package com.tt.miniapp.business.component.video.fullscreen;

import android.app.Activity;
import android.view.View;
import com.tt.miniapp.base.MiniAppContext;

public class AnchorTransaction extends FullScreenTransaction {
  private boolean mAnchorButtonHidden;
  
  public AnchorTransaction(MiniAppContext paramMiniAppContext) {
    super(paramMiniAppContext);
  }
  
  private View getAnchorButton() {
    Activity activity = getCurrentActivity();
    return (activity == null) ? null : activity.findViewById(2097545246);
  }
  
  public void enterFullScreen() {
    View view = getAnchorButton();
    if (view == null)
      return; 
    if (view.getVisibility() == 0) {
      view.setVisibility(4);
      this.mAnchorButtonHidden = true;
    } 
  }
  
  public void exitFullScreen() {
    if (!this.mAnchorButtonHidden)
      return; 
    View view = getAnchorButton();
    if (view == null)
      return; 
    view.setVisibility(0);
    this.mAnchorButtonHidden = false;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\business\component\video\fullscreen\AnchorTransaction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */