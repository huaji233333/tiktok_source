package com.tt.miniapp.feedback.entrance.image;

import android.content.Context;
import android.widget.ImageView;
import com.tt.miniapp.util.ImageUtil;
import com.tt.miniapphost.AppBrandLogger;
import java.io.File;
import java.io.IOException;

public class ImageLoaderUtil implements ImageLoaderInterface {
  public void displayImage(Context paramContext, Integer paramInteger, ImageView paramImageView) {
    paramImageView.setImageDrawable(paramContext.getDrawable(paramInteger.intValue()));
  }
  
  public void displayImage(Context paramContext, String paramString, ImageView paramImageView) {
    try {
      paramImageView.setImageBitmap(ImageUtil.cropCenterBitmap(ImageUtil.decodeSampledBitmapFromFile(new File(paramString), ImageUploadView.dp2px(paramContext, 84.0F), ImageUploadView.dp2px(paramContext, 84.0F))));
      return;
    } catch (IOException iOException) {
      AppBrandLogger.stacktrace(6, "tma_ImageLoaderUtil", iOException.getStackTrace());
      return;
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\feedback\entrance\image\ImageLoaderUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */