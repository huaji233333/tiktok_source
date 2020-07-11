package com.tt.miniapp.chooser.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v4.content.c;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.tt.miniapp.entity.Folder;
import com.tt.miniapphost.entity.MediaEntity;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.miniapphost.util.UIUtils;
import java.text.MessageFormat;
import java.util.ArrayList;

public class FolderAdapter extends BaseAdapter {
  ArrayList<Folder> folders;
  
  int lastSelected;
  
  private Context mContext;
  
  private LayoutInflater mInflater;
  
  public FolderAdapter(ArrayList<Folder> paramArrayList, Context paramContext) {
    this.mInflater = (LayoutInflater)paramContext.getSystemService("layout_inflater");
    this.folders = paramArrayList;
    this.mContext = paramContext;
  }
  
  public int getCount() {
    return this.folders.size();
  }
  
  public Folder getItem(int paramInt) {
    return this.folders.get(paramInt);
  }
  
  public long getItemId(int paramInt) {
    return 0L;
  }
  
  public ArrayList<MediaEntity> getSelectMedias() {
    return ((Folder)this.folders.get(this.lastSelected)).getMedias();
  }
  
  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
    ViewHolder viewHolder;
    boolean bool = false;
    if (paramView == null) {
      paramView = this.mInflater.inflate(2097676306, paramViewGroup, false);
      viewHolder = new ViewHolder(paramView);
    } else {
      viewHolder = (ViewHolder)paramView.getTag();
    } 
    Folder folder = getItem(paramInt);
    if (folder.getMedias().size() > 0) {
      MediaEntity mediaEntity = folder.getMedias().get(0);
      HostDependManager hostDependManager = HostDependManager.getInst();
      Context context = this.mContext;
      ImageView imageView1 = viewHolder.cover;
      StringBuilder stringBuilder = new StringBuilder("file://");
      stringBuilder.append(mediaEntity.path);
      hostDependManager.loadImage(context, imageView1, Uri.parse(stringBuilder.toString()));
    } else {
      viewHolder.cover.setImageDrawable(c.a(this.mContext, 2097479705));
    } 
    viewHolder.name.setText(folder.name);
    viewHolder.size.setText(MessageFormat.format("{0}{1}", new Object[] { Integer.valueOf(folder.getMedias().size()), UIUtils.getString(2097741879) }));
    ImageView imageView = viewHolder.indicator;
    if (this.lastSelected == paramInt) {
      paramInt = bool;
    } else {
      paramInt = 4;
    } 
    imageView.setVisibility(paramInt);
    return paramView;
  }
  
  public void setSelectIndex(int paramInt) {
    if (this.lastSelected == paramInt)
      return; 
    this.lastSelected = paramInt;
    notifyDataSetChanged();
  }
  
  public void updateAdapter(ArrayList<Folder> paramArrayList) {
    this.folders = paramArrayList;
    notifyDataSetChanged();
  }
  
  class ViewHolder {
    ImageView cover;
    
    ImageView indicator;
    
    TextView name;
    
    TextView path;
    
    TextView size;
    
    ViewHolder(View param1View) {
      this.cover = (ImageView)param1View.findViewById(2097545262);
      this.name = (TextView)param1View.findViewById(2097545351);
      this.path = (TextView)param1View.findViewById(2097545361);
      this.size = (TextView)param1View.findViewById(2097545387);
      this.indicator = (ImageView)param1View.findViewById(2097545312);
      param1View.setTag(this);
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\chooser\adapter\FolderAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */