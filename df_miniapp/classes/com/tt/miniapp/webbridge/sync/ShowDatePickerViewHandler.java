package com.tt.miniapp.webbridge.sync;

import android.app.Activity;
import android.text.TextUtils;
import android.text.format.Time;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.WebViewManager;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.miniapphost.util.CharacterUtils;
import com.tt.option.y.b;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.json.JSONObject;

public class ShowDatePickerViewHandler extends BasePickerEventHandler {
  public ShowDatePickerViewHandler(WebViewManager.IRender paramIRender, String paramString, int paramInt) {
    super(paramIRender, paramString, paramInt);
  }
  
  private String showTimePicker(JSONObject paramJSONObject) throws Exception {
    JSONObject jSONObject = paramJSONObject.optJSONObject("range");
    String str1 = jSONObject.getString("start");
    String str3 = jSONObject.getString("end");
    String str2 = paramJSONObject.optString("current");
    int[] arrayOfInt2 = parseTime(str1);
    int[] arrayOfInt1 = arrayOfInt2;
    if (arrayOfInt2 == null)
      arrayOfInt1 = parseTime("00:00"); 
    final int range_start_hour = arrayOfInt1[0];
    final int range_start_minute = arrayOfInt1[1];
    arrayOfInt2 = parseTime(str3);
    arrayOfInt1 = arrayOfInt2;
    if (arrayOfInt2 == null)
      arrayOfInt1 = parseTime("23:59"); 
    final int range_end_hour = arrayOfInt1[0];
    final int range_end_minute = arrayOfInt1[1];
    arrayOfInt2 = parseTime(str2);
    arrayOfInt1 = arrayOfInt2;
    if (arrayOfInt2 == null) {
      Time time = new Time("GMT+8");
      time.setToNow();
      arrayOfInt1 = new int[] { time.hour, time.minute };
    } 
    final int range_current_hour = arrayOfInt1[0];
    final int range_current_minute = arrayOfInt1[1];
    AppbrandContext.mainHandler.post(new Runnable() {
          public void run() {
            ShowDatePickerViewHandler showDatePickerViewHandler;
            if (ShowDatePickerViewHandler.this.mRender == null) {
              AppBrandLogger.e("tma_ShowDatePickerViewHandler", new Object[] { "render is null" });
              return;
            } 
            Activity activity = ShowDatePickerViewHandler.this.mRender.getCurrentActivity();
            if (activity == null) {
              showDatePickerViewHandler = ShowDatePickerViewHandler.this;
              showDatePickerViewHandler.invokeHandler(showDatePickerViewHandler.makeFailMsg("activity is null"));
              return;
            } 
            if (showDatePickerViewHandler.isFinishing()) {
              showDatePickerViewHandler = ShowDatePickerViewHandler.this;
              showDatePickerViewHandler.invokeHandler(showDatePickerViewHandler.makeFailMsg("activity is finishing"));
              return;
            } 
            ShowDatePickerViewHandler.this.showTimePickerFinal((Activity)showDatePickerViewHandler, range_start_hour, range_start_minute, range_end_hour, range_end_minute, range_current_hour, range_current_minute);
          }
        });
    return CharacterUtils.empty();
  }
  
  public String act() {
    try {
      JSONObject jSONObject = new JSONObject(this.mArgs);
      String str = jSONObject.optString("mode");
      return TextUtils.equals(str, "time") ? showTimePicker(jSONObject) : (TextUtils.equals(str, "date") ? showDatePicker(jSONObject) : makeFailMsg("unsupported mode"));
    } catch (Exception exception) {
      AppBrandLogger.e("tma_ShowDatePickerViewHandler", new Object[] { "", exception });
      return null;
    } 
  }
  
  public String getApiName() {
    return "showDatePickerView";
  }
  
  int[] parseDate(String paramString1, String paramString2) {
    String[] arrayOfString = paramString1.split("-");
    if (TextUtils.equals(paramString2, "day")) {
      if (arrayOfString.length == 3)
        return new int[] { Integer.parseInt(arrayOfString[0]), Integer.parseInt(arrayOfString[1]), Integer.parseInt(arrayOfString[2]) }; 
      throw new IllegalArgumentException("should have year month day");
    } 
    if (TextUtils.equals(paramString2, "month")) {
      if (arrayOfString.length >= 2)
        return new int[] { Integer.parseInt(arrayOfString[0]), Integer.parseInt(arrayOfString[1]) }; 
      throw new IllegalArgumentException("should have year month at least");
    } 
    if (TextUtils.equals(paramString2, "year")) {
      if (arrayOfString.length > 0)
        return new int[] { Integer.parseInt(arrayOfString[0]) }; 
      throw new IllegalArgumentException("should have year at least");
    } 
    return null;
  }
  
  int[] parseTime(String paramString) {
    String[] arrayOfString = paramString.split(":");
    return (arrayOfString.length == 2) ? new int[] { Integer.parseInt(arrayOfString[0]), Integer.parseInt(arrayOfString[1]) } : null;
  }
  
  String showDatePicker(JSONObject paramJSONObject) throws Exception {
    final String fields;
    JSONObject jSONObject = paramJSONObject.optJSONObject("range");
    String str3 = jSONObject.getString("start").replace("/", "-");
    String str4 = jSONObject.getString("end").replace("/", "-");
    String str5 = paramJSONObject.optString("current").replace("/", "-");
    if (TextUtils.isEmpty(paramJSONObject.optString("fields"))) {
      str1 = "day";
    } else {
      str1 = str1.optString("fields");
    } 
    String str2 = str3;
    if (TextUtils.isEmpty(str3))
      str2 = "2010-1-1"; 
    str3 = str4;
    if (TextUtils.isEmpty(str4))
      str3 = "2020-12-31"; 
    str4 = str5;
    if (TextUtils.isEmpty(str5))
      str4 = (new SimpleDateFormat("yyyy-MM-dd")).format(new Date(System.currentTimeMillis())); 
    final int[] range_start = parseDate(str2, str1);
    final int[] range_end = parseDate(str3, str1);
    final int[] range_current = parseDate(str4, str1);
    AppbrandContext.mainHandler.post(new Runnable() {
          public void run() {
            ShowDatePickerViewHandler showDatePickerViewHandler;
            if (ShowDatePickerViewHandler.this.mRender == null) {
              AppBrandLogger.e("tma_ShowDatePickerViewHandler", new Object[] { "render is null" });
              return;
            } 
            Activity activity = ShowDatePickerViewHandler.this.mRender.getCurrentActivity();
            if (activity == null) {
              showDatePickerViewHandler = ShowDatePickerViewHandler.this;
              showDatePickerViewHandler.invokeHandler(showDatePickerViewHandler.makeFailMsg("activity is null"));
              return;
            } 
            if (showDatePickerViewHandler.isFinishing()) {
              showDatePickerViewHandler = ShowDatePickerViewHandler.this;
              showDatePickerViewHandler.invokeHandler(showDatePickerViewHandler.makeFailMsg("activity is finishing"));
              return;
            } 
            ShowDatePickerViewHandler.this.showDatePickerFinal((Activity)showDatePickerViewHandler, fields, range_start, range_end, range_current);
          }
        });
    return CharacterUtils.empty();
  }
  
  public void showDatePickerFinal(Activity paramActivity, String paramString, int[] paramArrayOfint1, int[] paramArrayOfint2, int[] paramArrayOfint3) {
    byte b1;
    byte b2;
    byte b3;
    byte b4;
    byte b5;
    byte b6;
    byte b7;
    byte b8;
    byte b9;
    if (TextUtils.equals(paramString, "year")) {
      b1 = paramArrayOfint1[0];
      b4 = paramArrayOfint2[0];
      b7 = paramArrayOfint3[0];
      b2 = -1;
      b3 = -1;
      b5 = -1;
      b6 = -1;
      b8 = -1;
      b9 = -1;
    } else if (TextUtils.equals(paramString, "month")) {
      b1 = paramArrayOfint1[0];
      b2 = paramArrayOfint1[1];
      b4 = paramArrayOfint2[0];
      b5 = paramArrayOfint2[1];
      b7 = paramArrayOfint3[0];
      b8 = paramArrayOfint3[1];
      b6 = -1;
      b9 = -1;
      b3 = -1;
    } else if (TextUtils.equals(paramString, "day")) {
      b1 = paramArrayOfint1[0];
      b2 = paramArrayOfint1[1];
      b3 = paramArrayOfint1[2];
      b4 = paramArrayOfint2[0];
      b5 = paramArrayOfint2[1];
      b6 = paramArrayOfint2[2];
      b7 = paramArrayOfint3[0];
      b8 = paramArrayOfint3[1];
      b9 = paramArrayOfint3[2];
    } else {
      b1 = -1;
      b2 = -1;
      b3 = -1;
      b4 = -1;
      b5 = -1;
      b6 = -1;
      b7 = -1;
      b8 = -1;
      b9 = -1;
    } 
    HostDependManager.getInst().showDatePickerView(paramActivity, this.mArgs, paramString, b1, b2, b3, b4, b5, b6, b7, b8, b9, new b.a<String>() {
          public void onCancel() {
            ShowDatePickerViewHandler.this.makeCancelMsg("showDatePickerView");
          }
          
          public void onDatePicked(String param1String1, String param1String2, String param1String3) {
            boolean bool = TextUtils.isEmpty(param1String1);
            String str = "";
            if (!bool) {
              StringBuilder stringBuilder = new StringBuilder();
              stringBuilder.append("");
              stringBuilder.append(param1String1);
              param1String1 = stringBuilder.toString();
              String str1 = param1String1;
              if (!TextUtils.isEmpty(param1String2)) {
                StringBuilder stringBuilder1 = new StringBuilder();
                stringBuilder1.append(param1String1);
                stringBuilder1.append("-");
                stringBuilder1.append(param1String2);
                param1String1 = stringBuilder1.toString();
                str = param1String1;
                if (!TextUtils.isEmpty(param1String3)) {
                  StringBuilder stringBuilder2 = new StringBuilder();
                  stringBuilder2.append(param1String1);
                  stringBuilder2.append("-");
                  stringBuilder2.append(param1String3);
                  str = stringBuilder2.toString();
                } 
              } 
            } 
            AppBrandLogger.d("tma_ShowDatePickerViewHandler", new Object[] { "datePicker onDatePicked result ", str });
            try {
              JSONObject jSONObject = new JSONObject();
              jSONObject.put("errMsg", ShowDatePickerViewHandler.this.buildErrorMsg("showDatePickerView", "ok"));
              jSONObject.put("value", str);
              AppbrandApplicationImpl.getInst().getWebViewManager().invokeHandler(ShowDatePickerViewHandler.this.mRender.getWebViewId(), ShowDatePickerViewHandler.this.mCallBackId, jSONObject.toString());
              return;
            } catch (Exception exception) {
              AppBrandLogger.stacktrace(6, "tma_ShowDatePickerViewHandler", exception.getStackTrace());
              return;
            } 
          }
          
          public void onDismiss() {
            ShowDatePickerViewHandler.this.makeCancelMsg("showDatePickerView");
          }
          
          public void onFailure(String param1String) {
            ShowDatePickerViewHandler.this.makeCancelMsg("showDatePickerView");
          }
        });
  }
  
  public void showTimePickerFinal(Activity paramActivity, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
    HostDependManager.getInst().showTimePickerView(paramActivity, this.mArgs, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, new b.f<String>() {
          public void onCancel() {
            ShowDatePickerViewHandler.this.makeCancelMsg("showDatePickerView");
          }
          
          public void onDismiss() {
            ShowDatePickerViewHandler.this.makeCancelMsg("showDatePickerView");
          }
          
          public void onFailure(String param1String) {
            ShowDatePickerViewHandler.this.makeCancelMsg("showDatePickerView");
          }
          
          public void onTimePicked(String param1String1, String param1String2) {
            AppBrandLogger.d("tma_ShowDatePickerViewHandler", new Object[] { "timePicker hour ", param1String1, " minute ", param1String2 });
            try {
              JSONObject jSONObject = new JSONObject();
              jSONObject.put("errMsg", ShowDatePickerViewHandler.this.buildErrorMsg("showDatePickerView", "ok"));
              StringBuilder stringBuilder = new StringBuilder();
              stringBuilder.append(param1String1);
              stringBuilder.append(":");
              stringBuilder.append(param1String2);
              jSONObject.put("value", stringBuilder.toString());
              AppbrandApplicationImpl.getInst().getWebViewManager().invokeHandler(ShowDatePickerViewHandler.this.mRender.getWebViewId(), ShowDatePickerViewHandler.this.mCallBackId, jSONObject.toString());
              return;
            } catch (Exception exception) {
              AppBrandLogger.stacktrace(6, "tma_ShowDatePickerViewHandler", exception.getStackTrace());
              return;
            } 
          }
        });
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\webbridge\sync\ShowDatePickerViewHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */