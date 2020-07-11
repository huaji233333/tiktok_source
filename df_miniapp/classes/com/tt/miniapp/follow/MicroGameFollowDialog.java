package com.tt.miniapp.follow;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.tt.b.c;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.host.HostDependManager;
import org.json.JSONException;
import org.json.JSONObject;

public class MicroGameFollowDialog extends Dialog {
  public static String TAG = "MicroGameFollowDialog";
  
  private Handler handler;
  
  public boolean hasFollowed;
  
  private boolean isFirstTimeShow = true;
  
  private ImageView mAuthImage;
  
  public IOnClickListener mOnClickListener;
  
  private ImageView mUserAvatarImage;
  
  private TextView mUserDescTv;
  
  private FollowUserInfo mUserInfo;
  
  private TextView mUserNameTv;
  
  private TextView tvConfirm;
  
  public MicroGameFollowDialog(Context paramContext, FollowUserInfo paramFollowUserInfo, boolean paramBoolean, IOnClickListener paramIOnClickListener) {
    super(paramContext, 2097807365);
    this.mUserInfo = paramFollowUserInfo;
    this.mOnClickListener = paramIOnClickListener;
    this.handler = new Handler(Looper.getMainLooper());
    this.hasFollowed = paramBoolean;
    setContentView(2097676321);
    setCancelable(false);
    initViews();
    initListener();
  }
  
  private void bindConfirmEvent() {
    this.tvConfirm.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            MicroGameFollowDialog.this.dismiss();
            if (MicroGameFollowDialog.this.mOnClickListener != null)
              MicroGameFollowDialog.this.mOnClickListener.onConfirm(); 
          }
        });
    this.tvConfirm.setClickable(true);
  }
  
  private void bindData(Context paramContext) {
    FollowUserInfo followUserInfo = this.mUserInfo;
    if (followUserInfo != null) {
      this.mUserNameTv.setText(followUserInfo.name);
      this.mUserDescTv.setText(this.mUserInfo.description);
      c c = (new c(this.mUserInfo.avatarUrl)).b().a(this.mUserAvatarImage.getWidth(), this.mUserAvatarImage.getHeight());
      c.l = 25.0F;
      c = c.a((View)this.mUserAvatarImage);
      HostDependManager.getInst().loadImage(getContext(), c);
      loadAuthTypeImg();
    } 
  }
  
  private void clearConfirmEvent() {
    this.tvConfirm.setOnClickListener(null);
    this.tvConfirm.setClickable(false);
  }
  
  private String getAuthType(String paramString) {
    if (TextUtils.isEmpty(paramString))
      return ""; 
    try {
      return (new JSONObject(paramString)).optString("auth_type");
    } catch (JSONException jSONException) {
      AppBrandLogger.eWithThrowable(TAG, "auth info error", (Throwable)jSONException);
      return "";
    } 
  }
  
  private String getAuthTypeIconUrl(int paramInt) {
    return (paramInt != 0) ? ((paramInt != 1) ? ((paramInt != 2) ? "" : "http://p3.pstatp.com/origin/2f100002ba0f3f2a1a79") : "http://s3.pstatp.com/toutiao/static_images/authinfo/webp/2.webp") : "http://p3.pstatp.com/origin/3ea40000a242b0178dd2";
  }
  
  private void initListener() {
    findViewById(2097545326).setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            if (MicroGameFollowDialog.this.isShowing()) {
              MicroGameFollowDialog.this.dismiss();
              if (MicroGameFollowDialog.this.mOnClickListener != null)
                MicroGameFollowDialog.this.mOnClickListener.onClose(); 
            } 
          }
        });
    findViewById(2097545419).setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            if (MicroGameFollowDialog.this.isShowing()) {
              MicroGameFollowDialog.this.dismiss();
              if (MicroGameFollowDialog.this.mOnClickListener != null)
                MicroGameFollowDialog.this.mOnClickListener.onConfirm(); 
            } 
          }
        });
  }
  
  private void initViews() {
    this.mUserAvatarImage = (ImageView)findViewById(2097545317);
    this.mUserNameTv = (TextView)findViewById(2097545429);
    this.mUserDescTv = (TextView)findViewById(2097545421);
    this.tvConfirm = (TextView)findViewById(2097545419);
    this.mAuthImage = (ImageView)findViewById(2097545318);
  }
  
  private void loadAuthTypeImg() {
    String str1;
    FollowUserInfo followUserInfo = this.mUserInfo;
    if (followUserInfo == null)
      return; 
    if (TextUtils.isEmpty(followUserInfo.authType))
      return; 
    followUserInfo = null;
    try {
      String str = getAuthTypeIconUrl(Integer.valueOf(this.mUserInfo.authType).intValue());
    } finally {
      Exception exception = null;
    } 
    if (TextUtils.isEmpty(str1))
      return; 
    if (this.mAuthImage == null)
      return; 
    String str2 = TAG;
    StringBuilder stringBuilder = new StringBuilder("loadAuthTypeImg:");
    stringBuilder.append(this.mAuthImage);
    AppBrandLogger.d(str2, new Object[] { stringBuilder.toString() });
    c c = (new c(str1)).b().a(this.mAuthImage.getWidth(), this.mAuthImage.getHeight()).a((View)this.mAuthImage);
    HostDependManager.getInst().loadImage(getContext(), c);
  }
  
  protected void onStart() {
    super.onStart();
    AppBrandLogger.d(TAG, new Object[] { "onstart" });
    bindData(getContext());
    syncFollowState();
    if (this.isFirstTimeShow)
      this.isFirstTimeShow = false; 
  }
  
  protected void onStop() {
    super.onStop();
  }
  
  public void onWindowFocusChanged(boolean paramBoolean) {
    super.onWindowFocusChanged(paramBoolean);
    if (paramBoolean)
      requestAndSyncFollowState(); 
  }
  
  public void requestAndSyncFollowState() {
    CheckFollowMethodImpl.requestFollowState(new CheckFollowMethodImpl.FollowResultCallback() {
          public void failed(Throwable param1Throwable) {
            AppBrandLogger.d(MicroGameFollowDialog.TAG, new Object[] { "requestAndSyncFollowState sync failed" });
          }
          
          public void success(boolean param1Boolean) {
            MicroGameFollowDialog microGameFollowDialog = MicroGameFollowDialog.this;
            microGameFollowDialog.hasFollowed = param1Boolean;
            microGameFollowDialog.syncFollowState();
          }
        });
  }
  
  public void showHasfollowedState() {
    ((GradientDrawable)this.tvConfirm.getBackground()).setAlpha(102);
    this.tvConfirm.setText(getContext().getString(2097742034));
    clearConfirmEvent();
  }
  
  public void showUnfollowedState() {
    ((GradientDrawable)this.tvConfirm.getBackground()).setAlpha(255);
    this.tvConfirm.setText(getContext().getString(2097741921));
    bindConfirmEvent();
  }
  
  public void syncFollowState() {
    AppBrandLogger.d(TAG, new Object[] { "syncFollowState:" });
    this.handler.post(new Runnable() {
          public void run() {
            if (MicroGameFollowDialog.this.hasFollowed) {
              MicroGameFollowDialog.this.showHasfollowedState();
              return;
            } 
            MicroGameFollowDialog.this.showUnfollowedState();
          }
        });
  }
  
  public static interface IOnClickListener {
    void onClose();
    
    void onConfirm();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\follow\MicroGameFollowDialog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */