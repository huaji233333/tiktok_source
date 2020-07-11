package com.facebook.react.uimanager;

import android.view.View;
import android.view.ViewGroup;
import com.facebook.react.bridge.UiThreadUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ViewHierarchyDumper {
  public static JSONObject toJSON(View paramView) throws JSONException {
    UiThreadUtil.assertOnUiThread();
    JSONObject jSONObject = new JSONObject();
    jSONObject.put("n", paramView.getClass().getName());
    jSONObject.put("i", System.identityHashCode(paramView));
    Object object = paramView.getTag();
    if (object != null && object instanceof String)
      jSONObject.put("t", object); 
    if (paramView instanceof ViewGroup) {
      ViewGroup viewGroup = (ViewGroup)paramView;
      if (viewGroup.getChildCount() > 0) {
        object = new JSONArray();
        for (int i = 0; i < viewGroup.getChildCount(); i++)
          object.put(i, toJSON(viewGroup.getChildAt(i))); 
        jSONObject.put("c", object);
      } 
    } 
    return jSONObject;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\reac\\uimanager\ViewHierarchyDumper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */