package com.facebook.react.views.imagehelper;

import android.content.Context;
import android.net.Uri;
import com.facebook.i.a.a;

public class ImageSource {
  private boolean isResource;
  
  private double mSize;
  
  private String mSource;
  
  private Uri mUri;
  
  public ImageSource(Context paramContext, String paramString) {
    this(paramContext, paramString, 0.0D, 0.0D);
  }
  
  public ImageSource(Context paramContext, String paramString, double paramDouble1, double paramDouble2) {
    this.mSource = paramString;
    this.mSize = paramDouble1 * paramDouble2;
    this.mUri = computeUri(paramContext);
  }
  
  private Uri computeLocalUri(Context paramContext) {
    this.isResource = true;
    return ResourceDrawableIdHelper.getInstance().getResourceDrawableUri(paramContext, this.mSource);
  }
  
  private Uri computeUri(Context paramContext) {
    try {
      Uri uri = Uri.parse(this.mSource);
      return (uri.getScheme() == null) ? computeLocalUri(paramContext) : uri;
    } catch (Exception exception) {
      return computeLocalUri(paramContext);
    } 
  }
  
  public double getSize() {
    return this.mSize;
  }
  
  public String getSource() {
    return this.mSource;
  }
  
  public Uri getUri() {
    return (Uri)a.b(this.mUri);
  }
  
  public boolean isResource() {
    return this.isResource;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\imagehelper\ImageSource.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */