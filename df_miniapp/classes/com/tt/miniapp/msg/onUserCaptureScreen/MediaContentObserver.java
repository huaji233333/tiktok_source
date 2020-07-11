package com.tt.miniapp.msg.onUserCaptureScreen;

import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import com.tt.miniapp.util.DevicesUtil;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;

public class MediaContentObserver extends ContentObserver {
  private Uri mContentUri;
  
  private TakeScreenshotManager.TakeScreenshotObserver mObserver;
  
  public MediaContentObserver(Uri paramUri, TakeScreenshotManager.TakeScreenshotObserver paramTakeScreenshotObserver) {
    super(null);
    this.mContentUri = paramUri;
    this.mObserver = paramTakeScreenshotObserver;
  }
  
  private boolean checkScreenShot(String paramString, long paramLong) {
    paramString = paramString.toLowerCase();
    String[] arrayOfString = TakeScreenshotManager.KEYWORDS;
    int j = arrayOfString.length;
    int i;
    for (i = 0; i < j; i++) {
      if (paramString.contains(arrayOfString[i]))
        return true; 
    } 
    if (DevicesUtil.isHuawei() && paramString.contains("/dcim/camera/")) {
      arrayOfString = TakeScreenshotManager.CAMERA_SNAPSHOT_KEYWORDS;
      j = arrayOfString.length;
      for (i = 0; i < j; i++) {
        if (paramString.contains(arrayOfString[i]))
          return true; 
      } 
    } 
    return false;
  }
  
  private void handleMediaContentChange(Uri paramUri) {
    Cursor cursor2 = null;
    Cursor cursor1 = null;
    try {
      Cursor cursor = AppbrandContext.getInst().getApplicationContext().getContentResolver().query(paramUri, TakeScreenshotManager.MEDIA_PROJECTIONS, null, null, "date_added desc limit 1");
      if (cursor == null) {
        cursor1 = cursor;
        cursor2 = cursor;
        AppBrandLogger.i("MediaContentObserver", new Object[] { "handleMediaContentChange cursor == null" });
        if (cursor != null && !cursor.isClosed())
          cursor.close(); 
        return;
      } 
      cursor1 = cursor;
      cursor2 = cursor;
      if (!cursor.moveToFirst()) {
        cursor1 = cursor;
        cursor2 = cursor;
        AppBrandLogger.i("MediaContentObserver", new Object[] { "handleMediaContentChange !cursor.moveToFirst()" });
        if (cursor != null && !cursor.isClosed())
          cursor.close(); 
        return;
      } 
      cursor1 = cursor;
      cursor2 = cursor;
      int i = cursor.getColumnIndex("_data");
      cursor1 = cursor;
      cursor2 = cursor;
      int j = cursor.getColumnIndex("datetaken");
      cursor1 = cursor;
      cursor2 = cursor;
      handleMediaRowData(cursor.getString(i), cursor.getLong(j));
      if (cursor != null && !cursor.isClosed()) {
        cursor.close();
        return;
      } 
    } catch (Exception exception) {
      cursor1 = cursor2;
      AppBrandLogger.e("MediaContentObserver", new Object[] { "handleMediaContentChange fail", exception });
      if (cursor2 != null && !cursor2.isClosed())
        cursor2.close(); 
    } finally {}
  }
  
  private void handleMediaRowData(String paramString, long paramLong) {
    if (checkScreenShot(paramString, paramLong)) {
      TakeScreenshotManager.TakeScreenshotObserver takeScreenshotObserver = this.mObserver;
      if (takeScreenshotObserver != null) {
        takeScreenshotObserver.onUserCaptureScreen(paramString, paramLong);
        AppBrandLogger.d("MediaContentObserver", new Object[] { "handleMediaRowData data:", paramString, "dateTaken:", Long.valueOf(paramLong) });
        return;
      } 
    } else {
      AppBrandLogger.d("MediaContentObserver", new Object[] { "Not screenshot event data:", paramString });
    } 
  }
  
  public void onChange(boolean paramBoolean) {
    super.onChange(paramBoolean);
    AppBrandLogger.d("MediaContentObserver", new Object[] { "onChange mContentUri:", this.mContentUri, "selfChange:", Boolean.valueOf(paramBoolean) });
    handleMediaContentChange(this.mContentUri);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\onUserCaptureScreen\MediaContentObserver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */