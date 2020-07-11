package com.tt.miniapp.chooser.data;

import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import com.tt.miniapp.entity.Folder;
import com.tt.miniapphost.entity.MediaEntity;
import java.io.File;
import java.util.ArrayList;

public class MediaLoader extends LoaderM implements LoaderManager.LoaderCallbacks {
  String[] MEDIA_PROJECTION = new String[] { "_data", "_display_name", "date_added", "media_type", "_size", "_id", "parent" };
  
  Context mContext;
  
  DataCallback mLoader;
  
  public MediaLoader(Context paramContext, DataCallback paramDataCallback) {
    this.mContext = paramContext;
    this.mLoader = paramDataCallback;
  }
  
  public Loader onCreateLoader(int paramInt, Bundle paramBundle) {
    Uri uri = MediaStore.Files.getContentUri("external");
    return (Loader)new CursorLoader(this.mContext, uri, this.MEDIA_PROJECTION, "media_type=1 OR media_type=3", null, "date_added DESC");
  }
  
  public void onLoadFinished(Loader paramLoader, Object paramObject) {
    ArrayList<Folder> arrayList = new ArrayList();
    Folder folder1 = new Folder(this.mContext.getResources().getString(2097741857));
    arrayList.add(folder1);
    Folder folder2 = new Folder(this.mContext.getResources().getString(2097742052));
    arrayList.add(folder2);
    Cursor cursor = (Cursor)paramObject;
    if (cursor != null) {
      while (cursor.moveToNext()) {
        String str1 = cursor.getString(cursor.getColumnIndexOrThrow("_data"));
        String str2 = cursor.getString(cursor.getColumnIndexOrThrow("_display_name"));
        long l1 = cursor.getLong(cursor.getColumnIndexOrThrow("date_added"));
        int i = cursor.getInt(cursor.getColumnIndexOrThrow("media_type"));
        long l2 = cursor.getLong(cursor.getColumnIndexOrThrow("_size"));
        int j = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
        if (l2 >= 1L && (new File(str1)).exists()) {
          paramObject = getParent(str1);
          MediaEntity mediaEntity = new MediaEntity(str1, str2, l1, i, l2, j, (String)paramObject);
          folder1.addMedias(mediaEntity);
          if (i == 3)
            folder2.addMedias(mediaEntity); 
          i = hasDir(arrayList, (String)paramObject);
          if (i != -1) {
            ((Folder)arrayList.get(i)).addMedias(mediaEntity);
            continue;
          } 
          paramObject = new Folder((String)paramObject);
          paramObject.addMedias(mediaEntity);
          arrayList.add(paramObject);
        } 
      } 
      cursor.close();
    } 
    this.mLoader.onData(arrayList);
  }
  
  public void onLoaderReset(Loader paramLoader) {}
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\chooser\data\MediaLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */