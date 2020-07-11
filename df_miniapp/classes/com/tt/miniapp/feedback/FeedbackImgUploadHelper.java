package com.tt.miniapp.feedback;

import android.graphics.Bitmap;
import com.storage.async.Function;
import com.storage.async.Observable;
import com.storage.async.Subscriber;
import com.tt.miniapp.AppbrandConstant;
import com.tt.miniapp.feedback.entrance.vo.FeedbackParam;
import com.tt.miniapp.storage.filestorge.FileManager;
import com.tt.miniapp.thread.ThreadPools;
import com.tt.miniapp.util.ImageUtil;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.entity.MediaEntity;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.miniapphost.util.JsonBuilder;
import com.tt.option.q.i;
import com.tt.option.q.j;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import org.json.JSONObject;

public class FeedbackImgUploadHelper {
  public static void uploadImage(final FeedbackParam param, final MediaEntity entity, final ImgUploadCallback cb) {
    Observable.create(new Function<String>() {
          public final String fun() {
            StringBuilder stringBuilder1 = new StringBuilder();
            stringBuilder1.append(AppbrandConstant.SnssdkAPI.getInst().getFeedbackImageUpload());
            FeedbackParam feedbackParam = param;
            stringBuilder1.append(feedbackParam.toParamString(feedbackParam.getHostAppKey(), param.getHostAid(), param.getHostAppName()));
            i i = new i(stringBuilder1.toString(), "POST", false);
            i.k = 30000L;
            i.l = 30000L;
            File file2 = new File(entity.path);
            File file1 = FileManager.inst().getTempDir(param.getAid());
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append((new Random()).nextInt());
            stringBuilder2.append(".png");
            file1 = new File(file1, stringBuilder2.toString());
            try {
              file1 = ImageUtil.compressImage(file2, 640, 480, Bitmap.CompressFormat.PNG, 75, file1.toString());
            } catch (IOException iOException) {
              iOException = null;
            } 
            if (iOException != null) {
              i.a("image", (File)iOException, "image/jpeg");
            } else {
              i.a("image", file2, "image/jpeg");
            } 
            String str = "";
            try {
              j j = HostDependManager.getInst().postMultiPart(i);
              if (j == null)
                return ""; 
              String str1 = j.a();
              str = str1;
            } catch (Exception exception) {
              AppBrandLogger.e("FeedbackImgUploadHelper", new Object[] { exception });
            } 
            if (iOException != null && iOException.exists())
              iOException.delete(); 
            return str;
          }
        }).schudleOn(ThreadPools.longIO()).subscribe((Subscriber)new Subscriber.ResultableSubscriber<String>() {
          public final void onError(Throwable param1Throwable) {
            FeedbackImgUploadHelper.ImgUploadCallback imgUploadCallback = cb;
            if (imgUploadCallback == null)
              return; 
            imgUploadCallback.onError(param1Throwable);
          }
          
          public final void onSuccess(String param1String) {
            FeedbackImgUploadHelper.ImgUploadCallback imgUploadCallback = cb;
            if (imgUploadCallback == null)
              return; 
            imgUploadCallback.onSuccess((new JsonBuilder(param1String)).build());
          }
        });
  }
  
  public static interface ImgUploadCallback {
    void onError(Throwable param1Throwable);
    
    void onSuccess(JSONObject param1JSONObject);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\feedback\FeedbackImgUploadHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */