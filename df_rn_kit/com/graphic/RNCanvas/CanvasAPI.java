package com.graphic.RNCanvas;

import android.graphics.Paint;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.WritableMap;
import java.util.ArrayList;
import java.util.HashMap;

public class CanvasAPI extends ReactContextBaseJavaModule {
  private static final Paint paint = new Paint();
  
  public CanvasAPI(ReactApplicationContext paramReactApplicationContext) {
    super(paramReactApplicationContext);
  }
  
  @ReactMethod(isBlockingSynchronousMethod = true)
  public Integer drawSync(String paramString, ReadableArray paramReadableArray) {
    if (paramReadableArray.size() == 0)
      return Integer.valueOf(0); 
    ArrayList<HashMap> arrayList = CanvasConvert.convertActions(paramReadableArray);
    CanvasTextureView canvasTextureView = CanvasViewManager.getCanvasView(paramString);
    if (canvasTextureView != null) {
      canvasTextureView.setActions(arrayList);
      canvasTextureView.drawOutput();
    } 
    return Integer.valueOf(1);
  }
  
  public String getName() {
    return "CanvasAPI";
  }
  
  @ReactMethod(isBlockingSynchronousMethod = true)
  public WritableMap measureText(String paramString, double paramDouble) {
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
    paint.setTextSize((float)paramDouble);
    hashMap.put("width", Float.valueOf(paint.measureText(paramString)));
    return (WritableMap)Arguments.makeNativeMap(hashMap);
  }
  
  @ReactMethod
  public void release(String paramString) {
    CanvasViewManager.removeCanvasView(paramString);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\graphic\RNCanvas\CanvasAPI.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */