package com.tt.miniapp.webbridge.sync;

import com.tt.miniapp.WebViewManager;
import com.tt.miniapp.component.nativeview.picker.wheel.MultiPicker;
import com.tt.miniapp.component.nativeview.picker.wheel.entity.MultiPickerManager;
import com.tt.miniapp.webbridge.WebEventHandler;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class UpdateMultiPickerViewHandler extends WebEventHandler {
  public UpdateMultiPickerViewHandler(WebViewManager.IRender paramIRender, String paramString, int paramInt) {
    super(paramIRender, paramString, paramInt);
  }
  
  public String act() {
    try {
      JSONObject jSONObject2 = new JSONObject(this.mArgs);
      final int column = jSONObject2.optInt("column");
      final int current = jSONObject2.optInt("current");
      final ArrayList<String> items = new ArrayList();
      JSONArray jSONArray = jSONObject2.optJSONArray("array");
      if (jSONArray != null) {
        int m = jSONArray.length();
        for (int k = 0; k < m; k++)
          arrayList.add(jSONArray.getString(k)); 
      } 
      AppbrandContext.mainHandler.post(new Runnable() {
            public void run() {
              MultiPicker multiPicker = MultiPickerManager.getInst().getMultiPicker();
              if (multiPicker != null) {
                multiPicker.updateMultiPickerView(column, items, current);
                return;
              } 
              UpdateMultiPickerViewHandler updateMultiPickerViewHandler = UpdateMultiPickerViewHandler.this;
              updateMultiPickerViewHandler.invokeHandler(updateMultiPickerViewHandler.makeFailMsg("multi picker is null"));
            }
          });
      JSONObject jSONObject1 = new JSONObject();
      jSONObject1.put("errMsg", buildErrorMsg("updateMultiPickerView", "ok"));
      return jSONObject1.toString();
    } catch (Exception exception) {
      AppBrandLogger.e("UpdateMultiPickerViewHandler", new Object[] { exception });
      return makeFailMsg(exception);
    } 
  }
  
  public String getApiName() {
    return "updateMultiPickerView";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\webbridge\sync\UpdateMultiPickerViewHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */