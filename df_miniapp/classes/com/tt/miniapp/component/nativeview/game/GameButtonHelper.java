package com.tt.miniapp.component.nativeview.game;

import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.tt.b.a;
import com.tt.b.c;
import com.tt.miniapp.storage.filestorge.FileManager;
import com.tt.miniapp.streamloader.StreamLoader;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.host.HostDependManager;
import java.io.File;
import java.util.List;

public class GameButtonHelper {
  public static void applyImage(ImageView paramImageView, String paramString, int paramInt1, int paramInt2, final ApplyImageCallback callback) {
    c c;
    if (paramString.startsWith("http")) {
      c = (new c(paramString)).a(2097479705);
    } else {
      c = new c(new File((String)c));
    } 
    final ImageView tmpView = new ImageView(paramImageView.getContext());
    imageView.setLayoutParams((ViewGroup.LayoutParams)new GameAbsoluteLayout.LayoutParams(paramInt1, paramInt2, 0, 0));
    c.a(paramInt1, paramInt2).a(new a() {
          public final void onFail(Exception param1Exception) {
            AppBrandLogger.d("tma_GameButtonHelper", new Object[] { "applyImage failed" });
            GameButtonHelper.ApplyImageCallback applyImageCallback = callback;
            if (applyImageCallback != null)
              applyImageCallback.onLoaded(false, tmpView.getDrawable()); 
          }
          
          public final void onSuccess() {
            AppBrandLogger.d("tma_GameButtonHelper", new Object[] { "applyImage success" });
            GameButtonHelper.ApplyImageCallback applyImageCallback = callback;
            if (applyImageCallback != null)
              applyImageCallback.onLoaded(true, tmpView.getDrawable()); 
          }
        }).a((View)imageView);
    HostDependManager.getInst().loadImage(imageView.getContext(), c);
    if (c.b != 0)
      paramImageView.setImageResource(c.b); 
  }
  
  public static void applyImageStyle(RoundedImageView paramRoundedImageView, GameButtonStyle paramGameButtonStyle) {
    paramRoundedImageView.setBorderWidth(paramGameButtonStyle.borderWidth);
    paramRoundedImageView.setBorderColor(parseColor(paramGameButtonStyle.borderColor, 0));
    paramRoundedImageView.setCornerRadius(paramGameButtonStyle.borderRadius);
  }
  
  public static void applyText(TextView paramTextView, String paramString) {
    paramTextView.setText(paramString);
  }
  
  public static void applyTextStyle(TextView paramTextView, GameButtonStyle paramGameButtonStyle) {
    GradientDrawable gradientDrawable = new GradientDrawable();
    gradientDrawable.setCornerRadius(paramGameButtonStyle.borderRadius);
    gradientDrawable.setStroke(paramGameButtonStyle.borderWidth, parseColor(paramGameButtonStyle.borderColor, 0));
    gradientDrawable.setGradientType(0);
    gradientDrawable.setColor(parseColor(paramGameButtonStyle.backgroundColor, 0));
    paramTextView.setBackground((Drawable)gradientDrawable);
    int i = paramGameButtonStyle.borderWidth;
    paramTextView.setPadding(i, i, i, i);
    paramTextView.setTextColor(parseColor(paramGameButtonStyle.textColor, -16777216));
    paramTextView.setTextSize(paramGameButtonStyle.fontSize);
    paramTextView.setGravity(convertTextLayoutParams(paramGameButtonStyle.textAlign) | 0x10);
    if (Build.VERSION.SDK_INT >= 28) {
      paramTextView.setLineHeight(paramGameButtonStyle.lineHeight);
      return;
    } 
    i = paramTextView.getPaint().getFontMetricsInt(null);
    if (paramGameButtonStyle.lineHeight != i)
      paramTextView.setLineSpacing((paramGameButtonStyle.lineHeight - i), 1.0F); 
  }
  
  private static int convertTextLayoutParams(String paramString) {
    if (TextUtils.equals("left", paramString))
      return 3; 
    if (TextUtils.equals("right", paramString))
      return 5; 
    if (TextUtils.equals("center", paramString));
    return 17;
  }
  
  public static GameButton createImageButton(Context paramContext, final GameButtonStyle style) {
    final RoundedImageView roundedImageView = new RoundedImageView(paramContext);
    ThreadUtil.runOnUIThread(new Runnable() {
          public final void run() {
            GameButtonHelper.applyImageStyle(roundedImageView, style);
            GameButtonHelper.applyImage(roundedImageView, style.content, style.width, style.height, new GameButtonHelper.ApplyImageCallback() {
                  public void onLoaded(boolean param2Boolean, Drawable param2Drawable) {
                    roundedImageView.setImageDrawable(param2Drawable);
                  }
                });
          }
        });
    return new GameBtnImage(roundedImageView, style);
  }
  
  public static GameButton createTextButton(Context paramContext, final GameButtonStyle style) {
    final Button button = new Button(paramContext);
    ThreadUtil.runOnUIThread(new Runnable() {
          public final void run() {
            button.setStateListAnimator(null);
            GameButtonHelper.applyTextStyle((TextView)button, style);
            GameButtonHelper.applyText((TextView)button, style.content);
            button.setAllCaps(false);
          }
        });
    return new GameBtnText((TextView)button, style);
  }
  
  public static String filterImagePath(boolean paramBoolean, String paramString, StringBuilder paramStringBuilder) {
    if (TextUtils.isEmpty(paramString)) {
      if (paramStringBuilder != null)
        paramStringBuilder.append("empty params.image"); 
      return null;
    } 
    FileManager fileManager = FileManager.inst();
    paramString = fileManager.getRealFilePath(paramString);
    if (paramString.startsWith("http")) {
      if (!paramBoolean) {
        if (paramStringBuilder != null)
          paramStringBuilder.append("error params.image: not support web image"); 
        return null;
      } 
      return paramString;
    } 
    File file = new File(paramString);
    if (!fileManager.canRead(file)) {
      if (paramStringBuilder != null)
        paramStringBuilder.append("error params.image: file cannot read"); 
      return null;
    } 
    if (file.exists() && file.isFile())
      return paramString; 
    StreamLoader.extractToFile(paramString, file.getParent(), file.getName());
    if (file.exists() && fileManager.canRead(file)) {
      AppBrandLogger.d("streamload file unzip ok", new Object[0]);
      return paramString;
    } 
    if (paramStringBuilder != null)
      paramStringBuilder.append("error params.image"); 
    return null;
  }
  
  private static int parseColor(String paramString, int paramInt) {
    try {
      return Color.parseColor(paramString);
    } catch (Exception exception) {
      return paramInt;
    } 
  }
  
  public static void preloadImages(List<String> paramList) {
    if (paramList != null) {
      if (paramList.isEmpty())
        return; 
      Application application = AppbrandContext.getInst().getApplicationContext();
      for (String str : paramList) {
        final ImageView imageView = new ImageView((Context)application);
        try {
          c c = (new c(str)).a(new a() {
                public final void onFail(Exception param1Exception) {
                  StringBuilder stringBuilder = new StringBuilder();
                  stringBuilder.append(imageView.hashCode());
                  stringBuilder.append("preload failed.");
                  stringBuilder.append(path);
                  AppBrandLogger.e("tma_GameButtonHelper", new Object[] { stringBuilder.toString() });
                }
                
                public final void onSuccess() {
                  StringBuilder stringBuilder = new StringBuilder();
                  stringBuilder.append(imageView.hashCode());
                  stringBuilder.append("preload succeed.");
                  stringBuilder.append(path);
                  AppBrandLogger.d("tma_GameButtonHelper", new Object[] { stringBuilder.toString() });
                }
              }).a((View)imageView);
          HostDependManager.getInst().loadImage((Context)application, c);
        } catch (RuntimeException runtimeException) {
          AppBrandLogger.eWithThrowable("tma_GameButtonHelper", "preload error", runtimeException);
        } 
      } 
    } 
  }
  
  public static interface ApplyImageCallback {
    void onLoaded(boolean param1Boolean, Drawable param1Drawable);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\component\nativeview\game\GameButtonHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */