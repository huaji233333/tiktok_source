package com.facebook.react.modules.blob;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import com.facebook.react.ReactApplication;
import java.io.FileNotFoundException;
import java.io.IOException;

public final class BlobProvider extends ContentProvider {
  public final int delete(Uri paramUri, String paramString, String[] paramArrayOfString) {
    return 0;
  }
  
  public final String getType(Uri paramUri) {
    return null;
  }
  
  public final Uri insert(Uri paramUri, ContentValues paramContentValues) {
    return null;
  }
  
  public final boolean onCreate() {
    return true;
  }
  
  public final ParcelFileDescriptor openFile(Uri paramUri, String paramString) throws FileNotFoundException {
    StringBuilder stringBuilder1;
    if (paramString.equals("r")) {
      Context context = getContext().getApplicationContext();
      if (context instanceof ReactApplication) {
        BlobModule blobModule = (BlobModule)((ReactApplication)context).getReactNativeHost().getReactInstanceManager().getCurrentReactContext().getNativeModule(BlobModule.class);
      } else {
        context = null;
      } 
      if (context != null) {
        byte[] arrayOfByte = context.resolve(paramUri);
        if (arrayOfByte != null)
          try {
            ParcelFileDescriptor[] arrayOfParcelFileDescriptor = ParcelFileDescriptor.createPipe();
            ParcelFileDescriptor parcelFileDescriptor = arrayOfParcelFileDescriptor[0];
            ParcelFileDescriptor.AutoCloseOutputStream autoCloseOutputStream = new ParcelFileDescriptor.AutoCloseOutputStream(arrayOfParcelFileDescriptor[1]);
            autoCloseOutputStream.write(arrayOfByte);
            autoCloseOutputStream.close();
            return parcelFileDescriptor;
          } catch (IOException iOException) {
            return null;
          }  
        stringBuilder1 = new StringBuilder("Cannot open ");
        stringBuilder1.append(iOException.toString());
        stringBuilder1.append(", blob not found.");
        throw new FileNotFoundException(stringBuilder1.toString());
      } 
      throw new RuntimeException("No blob module associated with BlobProvider");
    } 
    StringBuilder stringBuilder2 = new StringBuilder("Cannot open ");
    stringBuilder2.append(iOException.toString());
    stringBuilder2.append(" in mode '");
    stringBuilder2.append((String)stringBuilder1);
    stringBuilder2.append("'");
    throw new FileNotFoundException(stringBuilder2.toString());
  }
  
  public final Cursor query(Uri paramUri, String[] paramArrayOfString1, String paramString1, String[] paramArrayOfString2, String paramString2) {
    return null;
  }
  
  public final int update(Uri paramUri, ContentValues paramContentValues, String paramString, String[] paramArrayOfString) {
    return 0;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\modules\blob\BlobProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */