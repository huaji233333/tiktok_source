package com.tt.miniapp.titlemenu.item;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.adsite.AdSiteManager;
import com.tt.miniapp.msg.ApiShareMessageDirectlyNewCtrl;
import com.tt.miniapp.titlemenu.MenuDialog;
import com.tt.miniapp.titlemenu.view.MenuItemView;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandApplication;
import com.tt.miniapphost.util.JsonBuilder;
import com.tt.miniapphost.util.UIUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class ShareMenuItem extends MenuItemAdapter {
  private MenuItemView mItemView;
  
  public ShareMenuItem(final Activity activity) {
    this.mItemView = new MenuItemView((Context)activity);
    this.mItemView.setIcon(activity.getDrawable(2097479741));
    this.mItemView.setLabel(UIUtils.getString(2097742026));
    this.mItemView.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            MenuDialog.inst(activity).dismiss();
            ApiShareMessageDirectlyNewCtrl.setClickPosition(1);
            String str = AppbrandApplicationImpl.getInst().getCurrentPageUrl();
            JSONObject jSONObject = (new JsonBuilder()).put("path", str).build();
            if (!TextUtils.isEmpty(AppbrandApplicationImpl.getInst().getCurrentWebViewUrl()))
              try {
                jSONObject.put("webViewUrl", AppbrandApplicationImpl.getInst().getCurrentWebViewUrl());
              } catch (JSONException jSONException) {
                AppBrandLogger.e("ShareMenuItem", new Object[] { "share menu webview url json error", jSONException });
              }  
            AppbrandApplication.getInst().getJsBridge().sendMsgToJsCore("onShareAppMessage", jSONObject.toString());
          }
        });
    if (AdSiteManager.getInstance().isAdSiteBrowser()) {
      this.mItemView.setVisibility(8);
      return;
    } 
    this.mItemView.setVisibility(0);
  }
  
  public IMenuItem.ItemCategory getCategory() {
    return IMenuItem.ItemCategory.SHARE;
  }
  
  public final String getId() {
    return "share";
  }
  
  public MenuItemView getView() {
    return this.mItemView;
  }
  
  public void setOnClickListener(final OnClickListener onClickListener) {
    this.mItemView.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            onClickListener.onClick();
          }
        });
  }
  
  public static interface OnClickListener {
    void onClick();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\titlemenu\item\ShareMenuItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */