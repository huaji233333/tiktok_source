package com.tt.miniapp.facialverify;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import com.tt.miniapp.ImmersedStatusBarHelper;
import com.tt.miniapp.view.swipeback.SwipeBackActivity;
import com.tt.miniapphost.util.UIUtils;

public class FacialVerifyProtocolActivity extends SwipeBackActivity {
  private void initProtocol() {
    _lancet.com_ss_android_ugc_aweme_lancet_H5UrlCheckLancet_loadUrl((WebView)findViewById(2097545502), "https://developer.toutiao.com/facial_recognition_protocol");
  }
  
  private void initTitle() {
    ((ImageView)findViewById(2097545355)).setImageResource(2097479751);
    UIUtils.configTitleBarWithHeight((Context)this, findViewById(2097545400));
    findViewById(2097545404).setVisibility(8);
    findViewById(2097545400).setBackgroundColor(-1);
    findViewById(2097545355).setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            FacialVerifyProtocolActivity.this.finish();
          }
        });
    UIUtils.setViewVisibility(findViewById(2097545413), 8);
    ((TextView)findViewById(2097545358)).setText(getString(2097741989));
  }
  
  public static void startFacialVerifyProtocol(Context paramContext) {
    paramContext.startActivity(new Intent(paramContext, FacialVerifyProtocolActivity.class));
  }
  
  public void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    setContentView(2097676291);
    Window window = getWindow();
    window.clearFlags(67108864);
    window.addFlags(-2147483648);
    if (Build.VERSION.SDK_INT >= 21)
      window.setStatusBarColor(getResources().getColor(2097348663)); 
    initTitle();
    ImmersedStatusBarHelper immersedStatusBarHelper = new ImmersedStatusBarHelper((Activity)this, (new ImmersedStatusBarHelper.ImmersedStatusBarConfig()).setFitsSystemWindows(true));
    immersedStatusBarHelper.setup(true);
    immersedStatusBarHelper.setUseLightStatusBarInternal(true);
    initProtocol();
  }
  
  class FacialVerifyProtocolActivity {}
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\facialverify\FacialVerifyProtocolActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */