package com.tt.miniapp.feedback.entrance;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.k;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import com.tt.miniapp.ImmersedStatusBarHelper;
import com.tt.miniapp.base.activity.IActivityResultHandler;
import com.tt.miniapp.feedback.entrance.vo.FAQItemVO;
import com.tt.miniapp.feedback.entrance.vo.FeedbackParam;
import com.tt.miniapphost.entity.AppInfoEntity;
import com.tt.miniapphost.util.UIUtils;
import java.util.List;

public abstract class BaseFAQFragment extends Fragment {
  public Activity mActivity;
  
  protected AppInfoEntity mAppInfoEntity;
  
  public FeedbackParam mFeedbackParam;
  
  public FAQPresenter mPresenter;
  
  protected View mRoot;
  
  public View mTitleBar;
  
  protected abstract int getLayoutId();
  
  public void initFragment() {
    this.mFeedbackParam = this.mPresenter.getFeedbackParam();
    this.mAppInfoEntity = this.mPresenter.getAppInfoEntity();
  }
  
  public void initTitleBar() {
    Window window = this.mActivity.getWindow();
    window.clearFlags(67108864);
    window.addFlags(-2147483648);
    if (Build.VERSION.SDK_INT >= 21)
      window.setStatusBarColor(getResources().getColor(2097348663)); 
    this.mTitleBar = this.mRoot.findViewById(2097545400);
    ImmersedStatusBarHelper immersedStatusBarHelper = new ImmersedStatusBarHelper(this.mActivity, (new ImmersedStatusBarHelper.ImmersedStatusBarConfig()).setFitsSystemWindows(true));
    immersedStatusBarHelper.setup(true);
    immersedStatusBarHelper.setUseLightStatusBarInternal(true);
    this.mRoot.findViewById(2097545354).setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            BaseFAQFragment.this.mActivity.onBackPressed();
          }
        });
    Activity activity = this.mActivity;
    if (activity instanceof FAQActivity && ((FAQActivity)activity).getSupportFragmentManager().f().size() > 1) {
      ImageButton imageButton = (ImageButton)this.mRoot.findViewById(2097545355);
      UIUtils.setViewVisibility((View)imageButton, 0);
      imageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View param1View) {
              BaseFAQFragment.this.mActivity.finish();
            }
          });
    } 
  }
  
  protected void initView() {}
  
  public void onAttach(Context paramContext) {
    super.onAttach(paramContext);
    if (paramContext instanceof FAQPresenter)
      this.mPresenter = (FAQPresenter)paramContext; 
    if (paramContext instanceof Activity)
      this.mActivity = (Activity)paramContext; 
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
    if (this.mRoot == null)
      this.mRoot = paramLayoutInflater.inflate(getLayoutId(), paramViewGroup, false); 
    initFragment();
    initTitleBar();
    initView();
    return this.mRoot;
  }
  
  public void onDetach() {
    super.onDetach();
    this.mPresenter = null;
  }
  
  public static interface FAQPresenter {
    k getActivitySupportFragmentManager();
    
    AppInfoEntity getAppInfoEntity();
    
    FeedbackParam getFeedbackParam();
    
    String getOpenIdSync();
    
    void onAddFragment(Fragment param1Fragment1, Fragment param1Fragment2);
    
    void requestItemList(BaseFAQFragment.OnRequestDataCallback param1OnRequestDataCallback);
    
    void setActivityResultHandler(IActivityResultHandler param1IActivityResultHandler);
  }
  
  public static interface OnRequestDataCallback {
    void callbackItemList(List<FAQItemVO> param1List);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\feedback\entrance\BaseFAQFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */