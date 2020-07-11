package com.tt.miniapp.secrecy.ui;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.tt.miniapp.secrecy.SecrecyChangedListener;
import com.tt.miniapp.secrecy.SecrecyEntity;
import com.tt.miniapp.secrecy.SecrecyManager;
import com.tt.miniapphost.AppBrandLogger;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class SecrecyUIHelper implements SecrecyChangedListener {
  private MenuBtnHelper mBtnHelper = new MenuBtnHelper(this);
  
  private MenuItemHelper mItemHelper = new MenuItemHelper(this);
  
  private SecrecyEntity mShownEntity;
  
  private List<SecrecyEntity> mStartedEntityList = new LinkedList<SecrecyEntity>();
  
  private SecrecyUIHelper() {
    SecrecyManager.inst().registerListener(-1, this);
  }
  
  private SecrecyEntity getNextEntity() {
    int i = this.mStartedEntityList.size() - 1;
    if (i < 0)
      return null; 
    SecrecyEntity secrecyEntity = this.mShownEntity;
    if (secrecyEntity != null) {
      int j = this.mStartedEntityList.indexOf(secrecyEntity);
      if (j >= 0)
        return this.mStartedEntityList.get((j + 1) % (i + 1)); 
    } 
    return this.mStartedEntityList.get(i);
  }
  
  public static SecrecyUIHelper inst() {
    return Holder.sInstance;
  }
  
  public static void preload(Context paramContext) {
    inst();
  }
  
  private void updateShownEntity(SecrecyEntity paramSecrecyEntity, int paramInt) {
    this.mShownEntity = paramSecrecyEntity;
    this.mBtnHelper.updateState(paramInt);
  }
  
  public int addSecrecyTip(RelativeLayout paramRelativeLayout, View.OnClickListener paramOnClickListener, int paramInt) {
    return this.mItemHelper.addSecrecyTip(paramRelativeLayout, paramOnClickListener, paramInt);
  }
  
  public void addSecrecyTip(LinearLayout paramLinearLayout, View.OnClickListener paramOnClickListener) {
    this.mItemHelper.addSecrecyTip(paramLinearLayout, paramOnClickListener);
  }
  
  public boolean callOnMainThread() {
    return true;
  }
  
  public SecrecyEntity getShownEntity() {
    return this.mShownEntity;
  }
  
  boolean isSecrecyNotStarted(SecrecyEntity paramSecrecyEntity) {
    List<SecrecyEntity> list = this.mStartedEntityList;
    return (list == null) ? true : (!list.contains(paramSecrecyEntity));
  }
  
  boolean needAnim2Hide() {
    return (this.mStartedEntityList.size() != 1) ? true : (!Objects.equals(this.mShownEntity, this.mStartedEntityList.get(0)));
  }
  
  public void onStart(int paramInt) {
    SecrecyEntity secrecyEntity = new SecrecyEntity(paramInt);
    if (!this.mStartedEntityList.contains(secrecyEntity))
      this.mStartedEntityList.add(secrecyEntity); 
    if (Objects.equals(this.mShownEntity, secrecyEntity)) {
      AppBrandLogger.d("SecrecyUIHelper", new Object[] { "onStart, failed, showing" });
      return;
    } 
    updateShownEntity(secrecyEntity, 1);
    StringBuilder stringBuilder = new StringBuilder("onStart, type=");
    stringBuilder.append(paramInt);
    AppBrandLogger.d("SecrecyUIHelper", new Object[] { stringBuilder.toString() });
  }
  
  public void onStop(int paramInt) {
    SecrecyEntity secrecyEntity = new SecrecyEntity(paramInt);
    boolean bool = this.mStartedEntityList.remove(secrecyEntity);
    StringBuilder stringBuilder = new StringBuilder("onStop, res=");
    stringBuilder.append(bool);
    AppBrandLogger.d("SecrecyUIHelper", new Object[] { stringBuilder.toString() });
  }
  
  public void registerView(ImageView paramImageView) {
    this.mBtnHelper.registerView(paramImageView);
  }
  
  void switch2NextSecrecy() {
    updateShownEntity(getNextEntity(), 2);
  }
  
  static class Holder {
    public static final SecrecyUIHelper sInstance = new SecrecyUIHelper();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\secrec\\ui\SecrecyUIHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */