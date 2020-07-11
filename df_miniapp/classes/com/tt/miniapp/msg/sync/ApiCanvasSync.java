package com.tt.miniapp.msg.sync;

import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.TextUtils;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.util.CharacterUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ApiCanvasSync extends SyncMsgCtrl {
  private String functionName;
  
  private Paint mPaint;
  
  public ApiCanvasSync(String paramString1, String paramString2) {
    super(paramString2);
    this.functionName = paramString1;
  }
  
  private Paint getPaint() {
    if (this.mPaint == null)
      this.mPaint = new Paint(); 
    return this.mPaint;
  }
  
  private float measureWidth(String paramString, float paramFloat1, String[] paramArrayOfString, float paramFloat2) {
    Paint paint = getPaint();
    paint.setTextSize(paramFloat1);
    paint.setLinearText(true);
    paint.setTypeface(Typeface.DEFAULT);
    int k = paramArrayOfString.length;
    byte b = 0;
    int j;
    for (j = 0; j < k; j++) {
      String str = paramArrayOfString[j].toLowerCase();
      byte b1 = -1;
      int m = str.hashCode();
      if (m != -1536685117) {
        if (m != -1431958525) {
          if (m == 109326717 && str.equals("serif"))
            b1 = 1; 
        } else if (str.equals("monospace")) {
          b1 = 2;
        } 
      } else if (str.equals("sans-serif")) {
        b1 = 0;
      } 
      if (b1 != 0) {
        if (b1 != 1) {
          if (b1 == 2)
            paint.setTypeface(Typeface.MONOSPACE); 
        } else {
          paint.setTypeface(Typeface.SERIF);
        } 
      } else {
        paint.setTypeface(Typeface.SANS_SERIF);
      } 
    } 
    Rect rect = new Rect();
    paint.getTextBounds(paramString, 0, paramString.length(), rect);
    float[] arrayOfFloat = new float[paramString.length()];
    this.mPaint.getTextWidths(paramString, arrayOfFloat);
    j = arrayOfFloat.length;
    paramFloat1 = 0.0F;
    int i;
    for (i = b; i < j; i++)
      paramFloat1 += arrayOfFloat[i]; 
    return (paramFloat2 <= 0.0F) ? paramFloat1 : (paramFloat1 * paramFloat2);
  }
  
  private float measureWidthWithNative(String paramString, float paramFloat, String[] paramArrayOfString) {
    boolean bool = TextUtils.isEmpty(paramString);
    float f = 0.0F;
    if (bool)
      return 0.0F; 
    String[] arrayOfString = paramString.split("\n");
    int j = arrayOfString.length;
    int i = 0;
    while (i < j) {
      float f2 = measureWidthWithNativeSingleLine(arrayOfString[i], paramFloat, paramArrayOfString);
      float f1 = f;
      if (f2 > f)
        f1 = f2; 
      i++;
      f = f1;
    } 
    return f;
  }
  
  private float measureWidthWithNativeSingleLine(String paramString, float paramFloat, String[] paramArrayOfString) {
    return TextUtils.isEmpty(paramString) ? 0.0F : measureWidth(paramString, paramFloat, paramArrayOfString, 0.0F);
  }
  
  public String act() {
    if (this.functionName.equals("measureText"))
      try {
        JSONObject jSONObject3 = new JSONObject(this.mParams);
        int j = jSONObject3.optInt("fontSize");
        JSONArray jSONArray = jSONObject3.optJSONArray("font");
        int i = 0;
        String[] arrayOfString2 = new String[0];
        String[] arrayOfString1 = arrayOfString2;
        if (jSONArray != null) {
          arrayOfString1 = arrayOfString2;
          if (jSONArray.length() > 0) {
            arrayOfString2 = new String[jSONArray.length()];
            while (true) {
              arrayOfString1 = arrayOfString2;
              if (i < jSONArray.length()) {
                arrayOfString2[i] = jSONArray.optString(i);
                i++;
                continue;
              } 
              break;
            } 
          } 
        } 
        float f = measureWidthWithNative(jSONObject3.optString("text"), j, arrayOfString1);
        JSONObject jSONObject1 = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put("width", f);
        jSONObject1.put("data", jSONObject2);
        return jSONObject1.toString();
      } catch (JSONException jSONException) {
        AppBrandLogger.stacktrace(6, "SyncMsgCtrl", jSONException.getStackTrace());
      }  
    return CharacterUtils.empty();
  }
  
  public String getName() {
    return this.functionName;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\sync\ApiCanvasSync.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */