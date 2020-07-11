package com.facebook.react.views.imagehelper;

import com.facebook.imagepipeline.e.h;
import com.facebook.imagepipeline.e.k;
import java.util.Iterator;
import java.util.List;

public class MultiSourceHelper {
  public static MultiSourceResult getBestSourceForSize(int paramInt1, int paramInt2, List<ImageSource> paramList) {
    return getBestSourceForSize(paramInt1, paramInt2, paramList, 1.0D);
  }
  
  public static MultiSourceResult getBestSourceForSize(int paramInt1, int paramInt2, List<ImageSource> paramList, double paramDouble) {
    Object object1;
    Object object2;
    Object object3;
    if (paramList.isEmpty())
      return new MultiSourceResult(null, null); 
    if (paramList.size() == 1)
      return new MultiSourceResult(paramList.get(0), null); 
    if (paramInt1 <= 0 || paramInt2 <= 0)
      return new MultiSourceResult(null, null); 
    h h = k.a().e();
    double d3 = (paramInt1 * paramInt2);
    Double.isNaN(d3);
    Iterator<ImageSource> iterator = paramList.iterator();
    double d2 = Double.MAX_VALUE;
    double d1 = d2;
    paramList = null;
    Object object4;
    for (object4 = paramList; iterator.hasNext(); object4 = SYNTHETIC_LOCAL_VARIABLE_17) {
      double d6;
      ImageSource imageSource = iterator.next();
      double d5 = Math.abs(1.0D - imageSource.getSize() / d3 * paramDouble);
      Object object6 = object3;
      Object object7 = object4;
      if (d5 < object3) {
        object7 = imageSource;
        d6 = d5;
      } 
      double d4 = d6;
      object4 = object7;
      if (d5 < object2) {
        if (!h.c(imageSource.getUri())) {
          d4 = d6;
          object4 = object7;
          if (h.d(imageSource.getUri()))
            continue; 
          continue;
        } 
        continue;
      } 
      continue;
      object1 = SYNTHETIC_LOCAL_VARIABLE_16;
      object2 = SYNTHETIC_LOCAL_VARIABLE_9;
      object3 = SYNTHETIC_LOCAL_VARIABLE_11;
    } 
    Object object5 = object1;
    if (object1 != null) {
      object5 = object1;
      if (object4 != null) {
        object5 = object1;
        if (object1.getSource().equals(object4.getSource()))
          object5 = null; 
      } 
    } 
    return new MultiSourceResult((ImageSource)object4, (ImageSource)object5);
  }
  
  public static class MultiSourceResult {
    private final ImageSource bestResult;
    
    private final ImageSource bestResultInCache;
    
    private MultiSourceResult(ImageSource param1ImageSource1, ImageSource param1ImageSource2) {
      this.bestResult = param1ImageSource1;
      this.bestResultInCache = param1ImageSource2;
    }
    
    public ImageSource getBestResult() {
      return this.bestResult;
    }
    
    public ImageSource getBestResultInCache() {
      return this.bestResultInCache;
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\imagehelper\MultiSourceHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */