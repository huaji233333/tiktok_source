package com.tt.miniapp.chooser.data;

import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import com.tt.miniapp.entity.Folder;
import com.tt.miniapphost.entity.MediaEntity;
import java.io.File;
import java.util.ArrayList;

public class ImageLoader extends LoaderM implements LoaderManager.LoaderCallbacks {
  String[] IMAGE_PROJECTION = new String[] { "_data", "_display_name", "date_added", "mime_type", "_size", "_id" };
  
  Context mContext;
  
  DataCallback mLoader;
  
  public ImageLoader(Context paramContext, DataCallback paramDataCallback) {
    this.mContext = paramContext;
    this.mLoader = paramDataCallback;
  }
  
  public Loader onCreateLoader(int paramInt, Bundle paramBundle) {
    Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
    return (Loader)new CursorLoader(this.mContext, uri, this.IMAGE_PROJECTION, null, null, "date_added DESC");
  }
  
  public void onLoadFinished(Loader paramLoader, Object paramObject) {
    ArrayList<Folder> arrayList = new ArrayList();
    Folder folder = new Folder(this.mContext.getResources().getString(2097741858));
    arrayList.add(folder);
    paramObject = paramObject;
    if (paramObject != null) {
      while (paramObject.moveToNext()) {
        String str1 = paramObject.getString(paramObject.getColumnIndexOrThrow("_data"));
        String str2 = paramObject.getString(paramObject.getColumnIndexOrThrow("_display_name"));
        long l1 = paramObject.getLong(paramObject.getColumnIndexOrThrow("date_added"));
        int i = paramObject.getInt(paramObject.getColumnIndexOrThrow("mime_type"));
        long l2 = paramObject.getLong(paramObject.getColumnIndexOrThrow("_size"));
        int j = paramObject.getInt(paramObject.getColumnIndexOrThrow("_id"));
        if (l2 >= 1L && (new File(str1)).exists()) {
          String str = getParent(str1);
          MediaEntity mediaEntity = new MediaEntity(str1, str2, l1, i, l2, j, str);
          folder.addMedias(mediaEntity);
          i = hasDir(arrayList, str);
          if (i != -1) {
            ((Folder)arrayList.get(i)).addMedias(mediaEntity);
            continue;
          } 
          Folder folder1 = new Folder(str);
          folder1.addMedias(mediaEntity);
          arrayList.add(folder1);
        } 
      } 
      paramObject.close();
    } 
    this.mLoader.onData(arrayList);
  }
  
  public void onLoaderReset(Loader paramLoader) {}
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\chooser\data\ImageLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */