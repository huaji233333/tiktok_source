package com.tt.miniapp.chooser;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListPopupWindow;
import android.widget.TextView;
import com.tt.miniapp.chooser.adapter.FolderAdapter;
import com.tt.miniapp.chooser.adapter.MediaGridAdapter;
import com.tt.miniapp.chooser.adapter.SpacingDecoration;
import com.tt.miniapp.chooser.data.DataCallback;
import com.tt.miniapp.chooser.data.ImageLoader;
import com.tt.miniapp.chooser.data.MediaLoader;
import com.tt.miniapp.chooser.data.VideoLoader;
import com.tt.miniapp.entity.Folder;
import com.tt.miniapp.permission.PermissionsManager;
import com.tt.miniapp.permission.PermissionsResultAction;
import com.tt.miniapp.view.swipeback.SwipeBackActivity;
import com.tt.miniapphost.entity.MediaEntity;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.miniapphost.language.LanguageChangeListener;
import com.tt.miniapphost.util.UIUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;

public class PickerActivity extends SwipeBackActivity implements View.OnClickListener, DataCallback, LanguageChangeListener {
  Intent argsIntent;
  
  Button category_btn;
  
  Button done;
  
  MediaGridAdapter gridAdapter;
  
  public FolderAdapter mFolderAdapter;
  
  ListPopupWindow mFolderPopupWindow;
  
  Button preview;
  
  RecyclerView recyclerView;
  
  public void cancel() {
    Intent intent = new Intent();
    intent.putParcelableArrayListExtra("select_result", null);
    setResult(19901026, intent);
    finish();
  }
  
  void createAdapter() {
    GridLayoutManager gridLayoutManager = new GridLayoutManager((Context)this, PickerConfig.GridSpanCount);
    this.recyclerView.setLayoutManager((RecyclerView.i)gridLayoutManager);
    this.recyclerView.a((RecyclerView.h)new SpacingDecoration(PickerConfig.GridSpanCount, PickerConfig.GridSpace));
    this.recyclerView.setHasFixedSize(true);
    ArrayList arrayList1 = new ArrayList();
    ArrayList arrayList2 = this.argsIntent.getParcelableArrayListExtra("default_list");
    int i = this.argsIntent.getIntExtra("max_select_count", 40);
    long l = this.argsIntent.getLongExtra("max_select_size", 188743680L);
    this.gridAdapter = new MediaGridAdapter(this.argsIntent.getIntExtra("camerType", 0), arrayList1, (Context)this, arrayList2, i, l);
    this.recyclerView.setAdapter((RecyclerView.a)this.gridAdapter);
  }
  
  void createFolderAdapter() {
    this.mFolderAdapter = new FolderAdapter(new ArrayList(), (Context)this);
    this.mFolderPopupWindow = new ListPopupWindow((Context)this);
    this.mFolderPopupWindow.setBackgroundDrawable((Drawable)new ColorDrawable(-1));
    this.mFolderPopupWindow.setAdapter((ListAdapter)this.mFolderAdapter);
    ListPopupWindow listPopupWindow = this.mFolderPopupWindow;
    double d = UIUtils.getScreenHeight((Context)this);
    Double.isNaN(d);
    listPopupWindow.setHeight((int)(d * 0.6D));
    this.mFolderPopupWindow.setAnchorView(findViewById(2097545302));
    this.mFolderPopupWindow.setModal(true);
    this.mFolderPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          public void onItemClick(AdapterView<?> param1AdapterView, View param1View, int param1Int, long param1Long) {
            PickerActivity.this.mFolderAdapter.setSelectIndex(param1Int);
            PickerActivity.this.category_btn.setText((PickerActivity.this.mFolderAdapter.getItem(param1Int)).name);
            PickerActivity.this.gridAdapter.updateAdapter(PickerActivity.this.mFolderAdapter.getSelectMedias());
            PickerActivity.this.mFolderPopupWindow.dismiss();
          }
        });
  }
  
  public void done(ArrayList<MediaEntity> paramArrayList) {
    Intent intent = new Intent();
    intent.putParcelableArrayListExtra("select_result", paramArrayList);
    setResult(19901026, intent);
    finish();
  }
  
  void getMediaData() {
    HashSet<String> hashSet = new HashSet();
    hashSet.add("android.permission.READ_EXTERNAL_STORAGE");
    PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult((Activity)this, hashSet, new PermissionsResultAction() {
          public void onDenied(String param1String) {}
          
          public void onGranted() {
            int i = PickerActivity.this.argsIntent.getIntExtra("select_mode", 101);
            if (i == 101) {
              LoaderManager loaderManager = PickerActivity.this.getLoaderManager();
              PickerActivity pickerActivity = PickerActivity.this;
              loaderManager.initLoader(i, null, (LoaderManager.LoaderCallbacks)new MediaLoader((Context)pickerActivity, pickerActivity));
              return;
            } 
            if (i == 100) {
              LoaderManager loaderManager = PickerActivity.this.getLoaderManager();
              PickerActivity pickerActivity = PickerActivity.this;
              loaderManager.initLoader(i, null, (LoaderManager.LoaderCallbacks)new ImageLoader((Context)pickerActivity, pickerActivity));
              return;
            } 
            if (i == 102) {
              LoaderManager loaderManager = PickerActivity.this.getLoaderManager();
              PickerActivity pickerActivity = PickerActivity.this;
              loaderManager.initLoader(i, null, (LoaderManager.LoaderCallbacks)new VideoLoader((Context)pickerActivity, pickerActivity));
            } 
          }
        });
  }
  
  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent) {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    if (paramInt1 == 8) {
      if (paramIntent == null)
        return; 
      ArrayList<MediaEntity> arrayList = paramIntent.getParcelableArrayListExtra("select_result");
      if (paramInt2 == 1990) {
        this.gridAdapter.updateSelectAdapter(arrayList);
        setButtonText();
        return;
      } 
      if (paramInt2 == 19901026)
        done(arrayList); 
      return;
    } 
    if (paramInt1 == 9 || paramInt1 == 10) {
      String str;
      if (paramInt1 == 9) {
        str = PickerConfig.currentCameraVideoPath;
      } else {
        str = PickerConfig.currentCameraPhotoPath;
      } 
      if (paramInt2 == -1 && !TextUtils.isEmpty(str)) {
        File file = new File(str);
        if (file.exists()) {
          ArrayList<MediaEntity> arrayList = new ArrayList();
          arrayList.add(new MediaEntity(str, file.getName(), 0L, 0, file.length(), 0, ""));
          done(arrayList);
          return;
        } 
      } 
      cancel();
    } 
  }
  
  public void onBackPressed() {
    cancel();
  }
  
  public void onClick(View paramView) {
    int i = paramView.getId();
    if (i == 2097545250) {
      cancel();
      return;
    } 
    if (i == 2097545255) {
      if (this.mFolderPopupWindow.isShowing()) {
        this.mFolderPopupWindow.dismiss();
        return;
      } 
      this.mFolderPopupWindow.show();
      return;
    } 
    if (i == 2097545269) {
      done(this.gridAdapter.getSelectMedias());
      return;
    } 
    if (i == 2097545369) {
      if (this.gridAdapter.getSelectMedias().size() <= 0) {
        HostDependManager.getInst().showToast((Context)this, null, getString(2097742021), 0L, null);
        return;
      } 
      Intent intent = new Intent((Context)this, PreviewActivity.class);
      intent.putExtra("max_select_count", this.argsIntent.getIntExtra("max_select_count", 40));
      intent.putExtra("pre_raw_List", this.gridAdapter.getSelectMedias());
      startActivityForResult(intent, 8);
    } 
  }
  
  public void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    this.argsIntent = getIntent();
    setContentView(2097676327);
    this.recyclerView = (RecyclerView)findViewById(2097545370);
    this.recyclerView.setRecyclerListener(new RecyclerView.p() {
          public void onViewRecycled(RecyclerView.v param1v) {
            if (param1v instanceof MediaGridAdapter.MyViewHolder)
              ((MediaGridAdapter.MyViewHolder)param1v).media_image.setTag(2097545452, null); 
          }
        });
    findViewById(2097545250).setOnClickListener(this);
    setTitleBar();
    this.done = (Button)findViewById(2097545269);
    this.category_btn = (Button)findViewById(2097545255);
    this.preview = (Button)findViewById(2097545369);
    this.done.setOnClickListener(this);
    this.category_btn.setOnClickListener(this);
    this.preview.setOnClickListener(this);
    createAdapter();
    createFolderAdapter();
    getMediaData();
  }
  
  public void onData(ArrayList<Folder> paramArrayList) {
    setView(paramArrayList);
    this.category_btn.setText(((Folder)paramArrayList.get(0)).name);
    this.mFolderAdapter.updateAdapter(paramArrayList);
  }
  
  public void onLanguageChange() {
    setTitleBar();
  }
  
  public void onRequestPermissionsResult(int paramInt, String[] paramArrayOfString, int[] paramArrayOfint) {
    super.onRequestPermissionsResult(paramInt, paramArrayOfString, paramArrayOfint);
    if ((paramInt >> 16 & 0xFFFF) == 0)
      PermissionsManager.getInstance().notifyPermissionsChange((Activity)this, paramArrayOfString, paramArrayOfint); 
  }
  
  void setButtonText() {
    int i = this.argsIntent.getIntExtra("max_select_count", 40);
    Button button = this.done;
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(getString(2097741885));
    stringBuilder.append("(");
    stringBuilder.append(this.gridAdapter.getSelectMedias().size());
    stringBuilder.append("/");
    stringBuilder.append(i);
    stringBuilder.append(")");
    button.setText(stringBuilder.toString());
    button = this.preview;
    stringBuilder = new StringBuilder();
    stringBuilder.append(getString(2097741988));
    stringBuilder.append("(");
    stringBuilder.append(this.gridAdapter.getSelectMedias().size());
    stringBuilder.append(")");
    button.setText(stringBuilder.toString());
  }
  
  public void setTitleBar() {
    int i = this.argsIntent.getIntExtra("select_mode", 101);
    if (i == 101) {
      ((TextView)findViewById(2097545248)).setText(getString(2097742022));
      return;
    } 
    if (i == 100) {
      ((TextView)findViewById(2097545248)).setText(getString(2097742020));
      return;
    } 
    if (i == 102)
      ((TextView)findViewById(2097545248)).setText(getString(2097742023)); 
  }
  
  void setView(ArrayList<Folder> paramArrayList) {
    this.gridAdapter.updateAdapter(((Folder)paramArrayList.get(0)).getMedias());
    setButtonText();
    this.gridAdapter.setOnItemClickListener(new MediaGridAdapter.OnRecyclerViewItemClickListener() {
          public void onItemClick(View param1View, MediaEntity param1MediaEntity, ArrayList<MediaEntity> param1ArrayList) {
            PickerActivity.this.setButtonText();
          }
        });
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\chooser\PickerActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */