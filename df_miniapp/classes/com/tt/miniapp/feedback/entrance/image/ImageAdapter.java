package com.tt.miniapp.feedback.entrance.image;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import com.tt.miniapphost.util.UIUtils;
import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter {
  private int addPicRes;
  
  private Context context;
  
  private int delPicRes;
  
  private int iconHeight;
  
  public ImageClickListener imageClickListener;
  
  public ArrayList<ImageModel> imageList;
  
  private ImageLoaderInterface imageLoader;
  
  private boolean isShowDel;
  
  public int mMaxNum = 9;
  
  public ImageAdapter(Context paramContext, ArrayList<ImageModel> paramArrayList) {
    this.context = paramContext;
    this.imageList = paramArrayList;
  }
  
  private ImageModel getImageModelByIndex(int paramInt) {
    return (paramInt < 0 || paramInt >= this.imageList.size()) ? null : this.imageList.get(paramInt);
  }
  
  public int getAddPicRes() {
    return this.addPicRes;
  }
  
  public int getCount() {
    int i = this.imageList.size();
    int j = this.mMaxNum;
    return (i >= j) ? j : (this.imageList.size() + 1);
  }
  
  public int getDelPicRes() {
    return this.delPicRes;
  }
  
  public int getIconHeight() {
    return this.iconHeight;
  }
  
  public ImageClickListener getImageClickListener() {
    return this.imageClickListener;
  }
  
  public ImageLoaderInterface getImageLoader() {
    return this.imageLoader;
  }
  
  public Object getItem(int paramInt) {
    return null;
  }
  
  public long getItemId(int paramInt) {
    return paramInt;
  }
  
  public View getView(final int i, View paramView, ViewGroup paramViewGroup) {
    FrameLayout frameLayout = new FrameLayout(this.context);
    frameLayout.setLayoutParams((ViewGroup.LayoutParams)new FrameLayout.LayoutParams(-2, -2));
    ViewHoler viewHoler = new ViewHoler();
    viewHoler.iv_pic = new ImageView(this.context);
    viewHoler.iv_del = new ImageView(this.context);
    viewHoler.iv_mask = new ImageView(this.context);
    viewHoler.iv_loading = new ProgressBar(this.context, null, 16842873);
    frameLayout.addView((View)viewHoler.iv_pic);
    frameLayout.addView((View)viewHoler.iv_mask);
    frameLayout.addView((View)viewHoler.iv_del);
    frameLayout.addView((View)viewHoler.iv_loading);
    frameLayout.setTag(viewHoler);
    int i = this.iconHeight;
    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(i, i);
    viewHoler.iv_pic.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
    layoutParams = new FrameLayout.LayoutParams(-2, -2);
    layoutParams.gravity = 8388661;
    i = (int)UIUtils.dip2Px(this.context, 4.0F);
    layoutParams.setMargins(i, i, i, i);
    viewHoler.iv_del.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
    viewHoler.iv_del.setImageResource(this.delPicRes);
    layoutParams = new FrameLayout.LayoutParams(this.iconHeight, (int)UIUtils.dip2Px(this.context, 20.0F));
    layoutParams.gravity = 48;
    GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[] { -2030043136, 0 });
    viewHoler.iv_mask.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
    viewHoler.iv_mask.setBackground((Drawable)gradientDrawable);
    layoutParams = new FrameLayout.LayoutParams((int)UIUtils.dip2Px(this.context, 26.0F), (int)UIUtils.dip2Px(this.context, 26.0F));
    layoutParams.gravity = 17;
    viewHoler.iv_loading.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
    viewHoler.iv_loading.setIndeterminateDrawable(this.context.getDrawable(2097479754));
    viewHoler.iv_loading.setIndeterminate(true);
    ImageModel imageModel = getImageModelByIndex(i);
    if (imageModel != null && imageModel.getStatus() == 1) {
      viewHoler.iv_loading.setVisibility(0);
      viewHoler.iv_mask.setVisibility(0);
      viewHoler.iv_del.setVisibility(8);
    } else if (imageModel != null && imageModel.getStatus() == 2) {
      viewHoler.iv_loading.setVisibility(8);
      viewHoler.iv_mask.setVisibility(0);
      if (this.isShowDel && i != this.imageList.size()) {
        viewHoler.iv_del.setVisibility(0);
      } else {
        viewHoler.iv_del.setVisibility(8);
      } 
    } else {
      viewHoler.iv_mask.setVisibility(8);
      viewHoler.iv_del.setVisibility(8);
      viewHoler.iv_loading.setVisibility(8);
    } 
    if (this.imageLoader != null)
      if (imageModel != null && imageModel.getStatus() == 2) {
        this.imageLoader.displayImage(this.context, ((ImageModel)this.imageList.get(i)).getImg(), viewHoler.iv_pic);
      } else if (imageModel != null && imageModel.getStatus() == 1) {
        this.imageLoader.displayImage(this.context, Integer.valueOf(2097479720), viewHoler.iv_pic);
      } else if (i == this.imageList.size()) {
        this.imageLoader.displayImage(this.context, Integer.valueOf(this.addPicRes), viewHoler.iv_pic);
      }  
    viewHoler.iv_pic.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            if (ImageAdapter.this.imageClickListener != null) {
              if (ImageAdapter.this.imageList.size() < ImageAdapter.this.mMaxNum && i == ImageAdapter.this.imageList.size()) {
                ImageAdapter.this.imageClickListener.addImageClickListener();
                return;
              } 
              ImageAdapter.this.imageClickListener.showImageClickListener(ImageAdapter.this.imageList, i);
            } 
          }
        });
    viewHoler.iv_del.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            if (ImageAdapter.this.imageClickListener != null)
              ImageAdapter.this.imageClickListener.delImageClickListener(i); 
          }
        });
    return (View)frameLayout;
  }
  
  public int getmMaxNum() {
    return this.mMaxNum;
  }
  
  public boolean isShowDel() {
    return this.isShowDel;
  }
  
  public void setAddPicRes(int paramInt) {
    this.addPicRes = paramInt;
  }
  
  public void setDelPicRes(int paramInt) {
    this.delPicRes = paramInt;
  }
  
  public void setIconHeight(int paramInt) {
    this.iconHeight = paramInt;
  }
  
  public void setImageClickListener(ImageClickListener paramImageClickListener) {
    this.imageClickListener = paramImageClickListener;
  }
  
  public void setImageLoader(ImageLoaderInterface paramImageLoaderInterface) {
    this.imageLoader = paramImageLoaderInterface;
  }
  
  public void setShowDel(boolean paramBoolean) {
    this.isShowDel = paramBoolean;
  }
  
  public void setmMaxNum(int paramInt) {
    this.mMaxNum = paramInt;
  }
  
  public static class ViewHoler {
    public ImageView iv_del;
    
    public ProgressBar iv_loading;
    
    public ImageView iv_mask;
    
    public ImageView iv_pic;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\feedback\entrance\image\ImageAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */