package com.tt.miniapp.feedback.entrance.image;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ImageUploadView extends LinearLayout {
  private ImageAdapter adapter;
  
  private Context context;
  
  private GridView gridView;
  
  private ImageClickListener imageClickListener;
  
  private ArrayList<ImageModel> imageList;
  
  private ImageLoaderInterface imageLoaderInterface;
  
  private boolean isShowDel;
  
  private int mAddLabel;
  
  private int mDelLabel;
  
  private int mPicSize;
  
  private int maxNum;
  
  private int oneLineShowNum;
  
  public ImageUploadView(Context paramContext) {
    this(paramContext, null);
  }
  
  public ImageUploadView(Context paramContext, AttributeSet paramAttributeSet) {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public ImageUploadView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
    this.context = paramContext;
    init(paramContext, paramAttributeSet);
  }
  
  public static int dp2px(Context paramContext, float paramFloat) {
    return (int)(paramFloat * (paramContext.getResources().getDisplayMetrics()).density + 0.5F);
  }
  
  private void init(Context paramContext, AttributeSet paramAttributeSet) {
    GridView gridView;
    TypedArray typedArray = paramContext.obtainStyledAttributes(paramAttributeSet, new int[] { 2097283072, 2097283077, 2097283078, 2097283079, 2097283081, 2097283082, 2097283083, 2097283122 });
    this.mPicSize = typedArray.getDimensionPixelSize(5, dp2px(paramContext, 80.0F));
    this.isShowDel = typedArray.getBoolean(2, true);
    this.mAddLabel = typedArray.getResourceId(0, 2097479686);
    this.mDelLabel = typedArray.getResourceId(1, 2097479706);
    this.oneLineShowNum = typedArray.getInt(4, 4);
    this.maxNum = typedArray.getInt(3, 9);
    boolean bool = typedArray.getBoolean(6, false);
    int i = typedArray.getDimensionPixelSize(7, 0);
    typedArray.recycle();
    this.imageList = new ArrayList<ImageModel>();
    if (bool) {
      gridView = new GridViewWithoutScroll(paramContext);
    } else {
      gridView = new GridView(paramContext);
    } 
    this.gridView = gridView;
    this.gridView.setNumColumns(this.oneLineShowNum);
    this.gridView.setVerticalSpacing(i);
    this.adapter = new ImageAdapter(paramContext, this.imageList);
    this.adapter.setIconHeight(this.mPicSize);
    this.adapter.setAddPicRes(this.mAddLabel);
    this.adapter.setDelPicRes(this.mDelLabel);
    this.gridView.setAdapter((ListAdapter)this.adapter);
    addView((View)this.gridView);
  }
  
  public void addImage(ArrayList<ImageModel> paramArrayList) {
    if (paramArrayList == null)
      return; 
    this.imageList.addAll(paramArrayList);
    ImageAdapter imageAdapter = this.adapter;
    if (imageAdapter != null)
      imageAdapter.notifyDataSetChanged(); 
  }
  
  public void clearImage() {
    this.imageList.clear();
    ImageAdapter imageAdapter = this.adapter;
    if (imageAdapter != null)
      imageAdapter.notifyDataSetChanged(); 
  }
  
  public void delImage(int paramInt) {
    this.imageList.remove(paramInt);
    ImageAdapter imageAdapter = this.adapter;
    if (imageAdapter != null)
      imageAdapter.notifyDataSetChanged(); 
  }
  
  public ImageClickListener getImageClickListener() {
    return this.imageClickListener;
  }
  
  public ArrayList<ImageModel> getImageList() {
    return this.imageList;
  }
  
  public ImageLoaderInterface getImageLoaderInterface() {
    return this.imageLoaderInterface;
  }
  
  public int getMaxNum() {
    return this.maxNum;
  }
  
  public int getOneLineShowNum() {
    return this.oneLineShowNum;
  }
  
  public int getmAddLabel() {
    return this.mAddLabel;
  }
  
  public int getmDelLabel() {
    return this.mDelLabel;
  }
  
  public int getmPicSize() {
    return this.mPicSize;
  }
  
  public boolean isShowDel() {
    return this.isShowDel;
  }
  
  public void loadImageDone(List<ImageModel> paramList, boolean paramBoolean) {
    if (paramList == null)
      return; 
    for (ImageModel imageModel : paramList) {
      Iterator<ImageModel> iterator = getImageList().iterator();
      while (iterator.hasNext()) {
        ImageModel imageModel1 = iterator.next();
        if (imageModel.getId() == imageModel1.getId()) {
          if (paramBoolean) {
            imageModel1.setStatus(2);
            continue;
          } 
          iterator.remove();
        } 
      } 
    } 
    ImageAdapter imageAdapter = this.adapter;
    if (imageAdapter != null)
      imageAdapter.notifyDataSetChanged(); 
  }
  
  public void notifyImage() {
    ImageAdapter imageAdapter = this.adapter;
    if (imageAdapter != null)
      imageAdapter.notifyDataSetChanged(); 
  }
  
  public ImageUploadView setImageClickListener(ImageClickListener paramImageClickListener) {
    this.imageClickListener = paramImageClickListener;
    this.adapter.setImageClickListener(this.imageClickListener);
    return this;
  }
  
  public void setImageList(ArrayList<ImageModel> paramArrayList) {
    this.imageList = paramArrayList;
  }
  
  public ImageUploadView setImageLoaderInterface(ImageLoaderInterface paramImageLoaderInterface) {
    this.imageLoaderInterface = paramImageLoaderInterface;
    this.adapter.setImageLoader(this.imageLoaderInterface);
    return this;
  }
  
  public ImageUploadView setMaxNum(int paramInt) {
    this.maxNum = paramInt;
    this.adapter.setmMaxNum(this.maxNum);
    return this;
  }
  
  public ImageUploadView setOneLineShowNum(int paramInt) {
    this.oneLineShowNum = paramInt;
    this.gridView.setNumColumns(paramInt);
    return this;
  }
  
  public ImageUploadView setShowDel(boolean paramBoolean) {
    this.isShowDel = paramBoolean;
    this.adapter.setShowDel(this.isShowDel);
    return this;
  }
  
  public ImageUploadView setmAddLabel(int paramInt) {
    this.mAddLabel = paramInt;
    this.adapter.setAddPicRes(this.mAddLabel);
    return this;
  }
  
  public ImageUploadView setmDelLabel(int paramInt) {
    this.mDelLabel = paramInt;
    this.adapter.setDelPicRes(this.mDelLabel);
    return this;
  }
  
  public ImageUploadView setmPicSize(int paramInt) {
    this.mPicSize = paramInt;
    this.adapter.setIconHeight(this.mPicSize);
    return this;
  }
  
  public void startLoadImage(ImageModel paramImageModel) {
    if (paramImageModel == null)
      return; 
    this.imageList.add(paramImageModel);
    ImageAdapter imageAdapter = this.adapter;
    if (imageAdapter != null)
      imageAdapter.notifyDataSetChanged(); 
  }
  
  class GridViewWithoutScroll extends GridView {
    public GridViewWithoutScroll(Context param1Context) {
      super(param1Context);
    }
    
    public GridViewWithoutScroll(Context param1Context, AttributeSet param1AttributeSet) {
      super(param1Context, param1AttributeSet);
    }
    
    public GridViewWithoutScroll(Context param1Context, AttributeSet param1AttributeSet, int param1Int) {
      super(param1Context, param1AttributeSet, param1Int);
    }
    
    public void onMeasure(int param1Int1, int param1Int2) {
      super.onMeasure(param1Int1, View.MeasureSpec.makeMeasureSpec(536870911, -2147483648));
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\feedback\entrance\image\ImageUploadView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */