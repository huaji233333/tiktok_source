package com.tt.miniapp.favorite;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.tt.b.c;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.entity.NativeUIParamsEntity;
import com.tt.miniapphost.host.HostDependManager;

public class FavoriteGuideBarView extends FavoriteGuideView {
  public FavoriteGuideBarView(FavoriteGuideModel paramFavoriteGuideModel, FavoriteGuideView.Callback paramCallback) {
    super(paramFavoriteGuideModel, paramCallback);
  }
  
  private int getBarBottomMargin() {
    int i;
    if ("overtab".equals(this.mModel.position)) {
      i = 2097414156;
    } else {
      i = 2097414155;
    } 
    return this.mCallback.getActivity().getResources().getDimensionPixelSize(i);
  }
  
  private void loadAppIcon(ImageView paramImageView) {
    try {
      c c2 = new c(Uri.parse((AppbrandApplicationImpl.getInst().getAppInfo()).icon));
      c2.b = 2097479705;
      c c1 = c2.a(2097479705).a((View)paramImageView);
      HostDependManager.getInst().loadImage((Context)this.mCallback.getActivity(), c1);
      return;
    } catch (Exception exception) {
      AppBrandLogger.eWithThrowable("FavoriteGuideBarView", "loadAppIcon", exception);
      return;
    } 
  }
  
  private void setAddButtonBackground(TextView paramTextView) {
    try {
      GradientDrawable gradientDrawable = new GradientDrawable();
      gradientDrawable.setCornerRadius(this.mCallback.getActivity().getResources().getDimensionPixelSize(2097414157));
      gradientDrawable.setColor(Color.parseColor(NativeUIParamsEntity.getInst().getPositiveColor()));
      paramTextView.setBackground((Drawable)gradientDrawable);
      return;
    } catch (android.content.res.Resources.NotFoundException notFoundException) {
      AppBrandLogger.eWithThrowable("FavoriteGuideBarView", "setAddButtonBackground", (Throwable)notFoundException);
      return;
    } 
  }
  
  protected int getLayoutId() {
    return 2097676302;
  }
  
  protected int getShowGravity() {
    return 81;
  }
  
  protected int getShowX() {
    return 0;
  }
  
  protected int getShowY() {
    return getBarBottomMargin();
  }
  
  public boolean isTip() {
    return false;
  }
  
  public void show() {
    super.show();
    ImageView imageView = (ImageView)this.mContentView.findViewById(2097545217);
    TextView textView = (TextView)this.mContentView.findViewById(2097545216);
    textView.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            FavoriteGuideBarView.this.dismiss(false);
            FavoriteEvent.onGuideClick(FavoriteGuideBarView.this.getTotalShowDuration());
            FavoriteGuideBarView.this.mCallback.onClickAddButton();
          }
        });
    loadAppIcon(imageView);
    setAddButtonBackground(textView);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\favorite\FavoriteGuideBarView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */