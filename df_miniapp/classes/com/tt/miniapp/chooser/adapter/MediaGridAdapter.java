package com.tt.miniapp.chooser.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Parcelable;
import android.support.v4.content.FileProvider;
import android.support.v4.content.c;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.tt.miniapp.chooser.PickerConfig;
import com.tt.miniapp.permission.PermissionsManager;
import com.tt.miniapp.permission.PermissionsResultAction;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.entity.MediaEntity;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.miniapphost.util.IOUtils;
import com.tt.miniapphost.util.UIUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;

public class MediaGridAdapter extends RecyclerView.a<RecyclerView.v> {
  Context context;
  
  int mCamerType = 0;
  
  public OnRecyclerViewItemClickListener mOnItemClickListener = null;
  
  long maxSelect;
  
  long maxSize;
  
  ArrayList<MediaEntity> medias;
  
  ArrayList<MediaEntity> selectMedias = new ArrayList<MediaEntity>();
  
  private VideoThumbLoader thumbLoader;
  
  public MediaGridAdapter(int paramInt1, ArrayList<MediaEntity> paramArrayList1, Context paramContext, ArrayList<MediaEntity> paramArrayList2, int paramInt2, long paramLong) {
    if (paramArrayList2 != null)
      this.selectMedias = paramArrayList2; 
    this.maxSelect = paramInt2;
    this.maxSize = paramLong;
    this.medias = paramArrayList1;
    this.context = paramContext;
    this.mCamerType = paramInt1;
    this.thumbLoader = new VideoThumbLoader();
  }
  
  public static void openCamera(final Activity activity, final int requestCode) {
    HashSet<String> hashSet = new HashSet();
    hashSet.add("android.permission.CAMERA");
    hashSet.add("android.permission.WRITE_EXTERNAL_STORAGE");
    PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(activity, hashSet, new PermissionsResultAction() {
          public final void onDenied(String param1String) {}
          
          public final void onGranted() {
            File file = new File(activity.getExternalCacheDir(), "image");
            if (file.exists() || file.mkdirs()) {
              Uri uri;
              StringBuilder stringBuilder = new StringBuilder();
              stringBuilder.append(System.currentTimeMillis());
              stringBuilder.append("-tmp.jpg");
              file = new File(file, stringBuilder.toString());
              PickerConfig.currentCameraPhotoPath = file.getAbsolutePath();
              Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
              if (Build.VERSION.SDK_INT >= 24) {
                String str = AppbrandContext.getInst().getInitParams().getHostStr(1007, "com.ss.android.uri.fileprovider");
                uri = FileProvider.getUriForFile((Context)activity, str, file);
              } else {
                uri = Uri.fromFile((File)uri);
              } 
              intent.putExtra("output", (Parcelable)uri);
              activity.startActivityForResult(intent, requestCode);
            } 
          }
        });
  }
  
  public static void openVideoCap(final Activity activity, final int requestCode) {
    HashSet<String> hashSet = new HashSet();
    hashSet.add("android.permission.CAMERA");
    hashSet.add("android.permission.WRITE_EXTERNAL_STORAGE");
    PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(activity, hashSet, new PermissionsResultAction() {
          public final void onDenied(String param1String) {}
          
          public final void onGranted() {
            Uri uri;
            Intent intent = new Intent();
            intent.setAction("android.media.action.VIDEO_CAPTURE");
            intent.addCategory("android.intent.category.DEFAULT");
            File file = activity.getExternalCacheDir();
            StringBuilder stringBuilder = new StringBuilder("video/");
            stringBuilder.append(System.currentTimeMillis());
            stringBuilder.append(".3pg");
            file = new File(file, stringBuilder.toString());
            if (!file.getParentFile().exists())
              file.getParentFile().mkdirs(); 
            PickerConfig.currentCameraVideoPath = file.getAbsolutePath();
            if (Build.VERSION.SDK_INT >= 24) {
              String str = AppbrandContext.getInst().getInitParams().getHostStr(1007, "com.ss.android.uri.fileprovider");
              uri = FileProvider.getUriForFile((Context)activity, str, file);
            } else {
              uri = Uri.fromFile((File)uri);
            } 
            intent.putExtra("output", (Parcelable)uri);
            activity.startActivityForResult(intent, requestCode);
          }
        });
  }
  
  public int getItemCount() {
    int i = this.mCamerType;
    return (i == 1 || i == 2) ? (this.medias.size() + 1) : this.medias.size();
  }
  
  public int getItemViewType(int paramInt) {
    int i = this.mCamerType;
    return (i == 2 || i == 1) ? ((paramInt == 0) ? 0 : 1) : 1;
  }
  
  int getItemWidth() {
    return UIUtils.getScreenWidth(this.context) / PickerConfig.GridSpanCount - PickerConfig.GridSpanCount;
  }
  
  public ArrayList<MediaEntity> getSelectMedias() {
    return this.selectMedias;
  }
  
  public int isSelect(MediaEntity paramMediaEntity) {
    if (this.selectMedias.size() <= 0)
      return -1; 
    for (int i = 0; i < this.selectMedias.size(); i++) {
      if (((MediaEntity)this.selectMedias.get(i)).path.equals(paramMediaEntity.path))
        return i; 
    } 
    return -1;
  }
  
  public void onBindViewHolder(RecyclerView.v paramv, int paramInt) {
    // Byte code:
    //   0: aload_1
    //   1: instanceof com/tt/miniapp/chooser/adapter/MediaGridAdapter$CamerHolder
    //   4: ifeq -> 61
    //   7: aload_1
    //   8: checkcast com/tt/miniapp/chooser/adapter/MediaGridAdapter$CamerHolder
    //   11: astore_1
    //   12: aload_0
    //   13: getfield mCamerType : I
    //   16: istore_2
    //   17: iload_2
    //   18: iconst_1
    //   19: if_icmpne -> 39
    //   22: aload_1
    //   23: getfield came_plan : Landroid/widget/TextView;
    //   26: aload_0
    //   27: getfield context : Landroid/content/Context;
    //   30: ldc 2097742039
    //   32: invokevirtual getString : (I)Ljava/lang/String;
    //   35: invokevirtual setText : (Ljava/lang/CharSequence;)V
    //   38: return
    //   39: iload_2
    //   40: iconst_2
    //   41: if_icmpne -> 60
    //   44: aload_1
    //   45: getfield came_plan : Landroid/widget/TextView;
    //   48: aload_0
    //   49: getfield context : Landroid/content/Context;
    //   52: ldc 2097742040
    //   54: invokevirtual getString : (I)Ljava/lang/String;
    //   57: invokevirtual setText : (Ljava/lang/CharSequence;)V
    //   60: return
    //   61: aload_0
    //   62: getfield mCamerType : I
    //   65: istore #4
    //   67: iload #4
    //   69: iconst_1
    //   70: if_icmpeq -> 81
    //   73: iload_2
    //   74: istore_3
    //   75: iload #4
    //   77: iconst_2
    //   78: if_icmpne -> 85
    //   81: iload_2
    //   82: iconst_1
    //   83: isub
    //   84: istore_3
    //   85: aload_1
    //   86: instanceof com/tt/miniapp/chooser/adapter/MediaGridAdapter$MyViewHolder
    //   89: ifeq -> 414
    //   92: aload_1
    //   93: checkcast com/tt/miniapp/chooser/adapter/MediaGridAdapter$MyViewHolder
    //   96: astore #5
    //   98: aload_0
    //   99: getfield medias : Ljava/util/ArrayList;
    //   102: iload_3
    //   103: invokevirtual get : (I)Ljava/lang/Object;
    //   106: checkcast com/tt/miniapphost/entity/MediaEntity
    //   109: astore #6
    //   111: aload #6
    //   113: getfield mediaType : I
    //   116: iconst_3
    //   117: if_icmpne -> 223
    //   120: aload_0
    //   121: getfield context : Landroid/content/Context;
    //   124: aload #6
    //   126: getfield id : I
    //   129: invokestatic getVideoThumbPath : (Landroid/content/Context;I)Ljava/lang/String;
    //   132: astore_1
    //   133: aload_1
    //   134: ifnull -> 203
    //   137: new java/io/File
    //   140: dup
    //   141: aload_1
    //   142: invokespecial <init> : (Ljava/lang/String;)V
    //   145: invokevirtual exists : ()Z
    //   148: ifeq -> 203
    //   151: new com/tt/b/c
    //   154: dup
    //   155: new java/io/File
    //   158: dup
    //   159: aload_1
    //   160: invokespecial <init> : (Ljava/lang/String;)V
    //   163: invokespecial <init> : (Ljava/io/File;)V
    //   166: invokevirtual a : ()Lcom/tt/b/c;
    //   169: aload_0
    //   170: invokevirtual getItemWidth : ()I
    //   173: aload_0
    //   174: invokevirtual getItemWidth : ()I
    //   177: invokevirtual a : (II)Lcom/tt/b/c;
    //   180: aload #5
    //   182: getfield media_image : Landroid/widget/ImageView;
    //   185: invokevirtual a : (Landroid/view/View;)Lcom/tt/b/c;
    //   188: astore_1
    //   189: invokestatic getInst : ()Lcom/tt/miniapphost/host/HostDependManager;
    //   192: aload_0
    //   193: getfield context : Landroid/content/Context;
    //   196: aload_1
    //   197: invokevirtual loadImage : (Landroid/content/Context;Lcom/tt/b/c;)V
    //   200: goto -> 276
    //   203: aload_0
    //   204: getfield thumbLoader : Lcom/tt/miniapp/chooser/adapter/VideoThumbLoader;
    //   207: aload #6
    //   209: getfield path : Ljava/lang/String;
    //   212: aload #5
    //   214: getfield media_image : Landroid/widget/ImageView;
    //   217: invokevirtual showThumb : (Ljava/lang/String;Landroid/widget/ImageView;)V
    //   220: goto -> 276
    //   223: new com/tt/b/c
    //   226: dup
    //   227: new java/io/File
    //   230: dup
    //   231: aload #6
    //   233: getfield path : Ljava/lang/String;
    //   236: invokespecial <init> : (Ljava/lang/String;)V
    //   239: invokespecial <init> : (Ljava/io/File;)V
    //   242: invokevirtual a : ()Lcom/tt/b/c;
    //   245: aload_0
    //   246: invokevirtual getItemWidth : ()I
    //   249: aload_0
    //   250: invokevirtual getItemWidth : ()I
    //   253: invokevirtual a : (II)Lcom/tt/b/c;
    //   256: aload #5
    //   258: getfield media_image : Landroid/widget/ImageView;
    //   261: invokevirtual a : (Landroid/view/View;)Lcom/tt/b/c;
    //   264: astore_1
    //   265: invokestatic getInst : ()Lcom/tt/miniapphost/host/HostDependManager;
    //   268: aload_0
    //   269: getfield context : Landroid/content/Context;
    //   272: aload_1
    //   273: invokevirtual loadImage : (Landroid/content/Context;Lcom/tt/b/c;)V
    //   276: aload #6
    //   278: getfield mediaType : I
    //   281: istore_3
    //   282: iconst_0
    //   283: istore_2
    //   284: iload_3
    //   285: iconst_3
    //   286: if_icmpne -> 318
    //   289: aload #5
    //   291: getfield video_info : Landroid/widget/RelativeLayout;
    //   294: iconst_0
    //   295: invokevirtual setVisibility : (I)V
    //   298: aload #5
    //   300: getfield textView_size : Landroid/widget/TextView;
    //   303: aload #6
    //   305: getfield size : J
    //   308: l2d
    //   309: invokestatic getSizeByUnit : (D)Ljava/lang/String;
    //   312: invokevirtual setText : (Ljava/lang/CharSequence;)V
    //   315: goto -> 327
    //   318: aload #5
    //   320: getfield video_info : Landroid/widget/RelativeLayout;
    //   323: iconst_4
    //   324: invokevirtual setVisibility : (I)V
    //   327: aload_0
    //   328: aload #6
    //   330: invokevirtual isSelect : (Lcom/tt/miniapphost/entity/MediaEntity;)I
    //   333: istore_3
    //   334: aload #5
    //   336: getfield mask_view : Landroid/view/View;
    //   339: astore_1
    //   340: iload_3
    //   341: iflt -> 347
    //   344: goto -> 349
    //   347: iconst_4
    //   348: istore_2
    //   349: aload_1
    //   350: iload_2
    //   351: invokevirtual setVisibility : (I)V
    //   354: aload #5
    //   356: getfield check_image : Landroid/widget/ImageView;
    //   359: astore #7
    //   361: iload_3
    //   362: iflt -> 376
    //   365: aload_0
    //   366: getfield context : Landroid/content/Context;
    //   369: astore_1
    //   370: ldc 2097479695
    //   372: istore_2
    //   373: goto -> 384
    //   376: aload_0
    //   377: getfield context : Landroid/content/Context;
    //   380: astore_1
    //   381: ldc 2097479696
    //   383: istore_2
    //   384: aload #7
    //   386: aload_1
    //   387: iload_2
    //   388: invokestatic a : (Landroid/content/Context;I)Landroid/graphics/drawable/Drawable;
    //   391: invokevirtual setImageDrawable : (Landroid/graphics/drawable/Drawable;)V
    //   394: aload #5
    //   396: getfield media_image : Landroid/widget/ImageView;
    //   399: new com/tt/miniapp/chooser/adapter/MediaGridAdapter$3
    //   402: dup
    //   403: aload_0
    //   404: aload #6
    //   406: aload #5
    //   408: invokespecial <init> : (Lcom/tt/miniapp/chooser/adapter/MediaGridAdapter;Lcom/tt/miniapphost/entity/MediaEntity;Lcom/tt/miniapp/chooser/adapter/MediaGridAdapter$MyViewHolder;)V
    //   411: invokevirtual setOnClickListener : (Landroid/view/View$OnClickListener;)V
    //   414: return
  }
  
  public RecyclerView.v onCreateViewHolder(ViewGroup paramViewGroup, int paramInt) {
    return (RecyclerView.v)((paramInt == 0) ? new CamerHolder(LayoutInflater.from(paramViewGroup.getContext()).inflate(2097676299, paramViewGroup, false)) : ((paramInt == 1) ? new MyViewHolder(LayoutInflater.from(paramViewGroup.getContext()).inflate(2097676330, paramViewGroup, false)) : null));
  }
  
  public void setOnItemClickListener(OnRecyclerViewItemClickListener paramOnRecyclerViewItemClickListener) {
    this.mOnItemClickListener = paramOnRecyclerViewItemClickListener;
  }
  
  public void setSelectMedias(MediaEntity paramMediaEntity) {
    int i = isSelect(paramMediaEntity);
    if (i == -1) {
      this.selectMedias.add(paramMediaEntity);
      return;
    } 
    this.selectMedias.remove(i);
  }
  
  public void updateAdapter(ArrayList<MediaEntity> paramArrayList) {
    this.medias = paramArrayList;
    notifyDataSetChanged();
  }
  
  public void updateSelectAdapter(ArrayList<MediaEntity> paramArrayList) {
    if (paramArrayList != null)
      this.selectMedias = paramArrayList; 
    notifyDataSetChanged();
  }
  
  public class CamerHolder extends RecyclerView.v {
    public TextView came_plan;
    
    public CamerHolder(View param1View) {
      super(param1View);
      this.came_plan = (TextView)param1View.findViewById(2097545347);
      this.itemView.setLayoutParams((ViewGroup.LayoutParams)new AbsListView.LayoutParams(-1, MediaGridAdapter.this.getItemWidth()));
      param1View.setOnClickListener(new View.OnClickListener() {
            public void onClick(View param2View) {
              if (MediaGridAdapter.this.mCamerType == 1) {
                MediaGridAdapter.openCamera((Activity)MediaGridAdapter.this.context, 10);
                return;
              } 
              if (MediaGridAdapter.this.mCamerType == 2)
                MediaGridAdapter.openVideoCap((Activity)MediaGridAdapter.this.context, 9); 
            }
          });
    }
  }
  
  class null implements View.OnClickListener {
    public void onClick(View param1View) {
      if (MediaGridAdapter.this.mCamerType == 1) {
        MediaGridAdapter.openCamera((Activity)MediaGridAdapter.this.context, 10);
        return;
      } 
      if (MediaGridAdapter.this.mCamerType == 2)
        MediaGridAdapter.openVideoCap((Activity)MediaGridAdapter.this.context, 9); 
    }
  }
  
  public class MyViewHolder extends RecyclerView.v {
    public ImageView check_image;
    
    public View mask_view;
    
    public ImageView media_image;
    
    public TextView textView_size;
    
    public RelativeLayout video_info;
    
    public MyViewHolder(View param1View) {
      super(param1View);
      this.media_image = (ImageView)param1View.findViewById(2097545348);
      this.check_image = (ImageView)param1View.findViewById(2097545257);
      this.mask_view = param1View.findViewById(2097545346);
      this.video_info = (RelativeLayout)param1View.findViewById(2097545438);
      this.textView_size = (TextView)param1View.findViewById(2097545398);
      this.itemView.setLayoutParams((ViewGroup.LayoutParams)new AbsListView.LayoutParams(-1, MediaGridAdapter.this.getItemWidth()));
    }
  }
  
  public static interface OnRecyclerViewItemClickListener {
    void onItemClick(View param1View, MediaEntity param1MediaEntity, ArrayList<MediaEntity> param1ArrayList);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\chooser\adapter\MediaGridAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */