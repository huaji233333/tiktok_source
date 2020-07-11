package com.tt.miniapp.debug.storage;

import com.tt.miniapp.storage.Storage;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class StorageReporter {
  private JSONObject storageId;
  
  public String clearStorage(int paramInt, boolean paramBoolean) throws JSONException {
    JSONObject jSONObject = new JSONObject();
    if (paramInt != 0)
      jSONObject.put("id", paramInt); 
    if (paramBoolean) {
      jSONObject.put("method", "DOMStorage.domStorageItemsCleared");
      JSONObject jSONObject1 = new JSONObject();
      jSONObject1.put("storageId", this.storageId);
      jSONObject.put("params", jSONObject1);
    } else {
      jSONObject.put("result", "{}");
    } 
    return jSONObject.toString();
  }
  
  public String getDOMStorageItems(int paramInt, JSONArray paramJSONArray) throws JSONException {
    JSONArray jSONArray1 = paramJSONArray;
    if (paramJSONArray == null)
      jSONArray1 = new JSONArray(); 
    JSONObject jSONObject1 = new JSONObject();
    if (paramInt != 0)
      jSONObject1.put("id", paramInt); 
    JSONArray jSONArray2 = new JSONArray();
    for (paramInt = 0; paramInt < jSONArray1.length(); paramInt++) {
      String str = (String)jSONArray1.get(paramInt);
      JSONArray jSONArray = new JSONArray();
      jSONArray.put(str);
      jSONArray.put(Storage.getValue(str));
      jSONArray2.put(jSONArray);
    } 
    JSONObject jSONObject2 = new JSONObject();
    jSONObject2.put("entries", jSONArray2);
    jSONObject1.put("result", jSONObject2);
    return jSONObject1.toString();
  }
  
  public String removeDOMStorageItem(int paramInt, boolean paramBoolean, String paramString) throws JSONException {
    JSONObject jSONObject = new JSONObject();
    if (paramInt != 0)
      jSONObject.put("id", paramInt); 
    if (paramBoolean) {
      jSONObject.put("method", "DOMStorage.domStorageItemRemoved");
      JSONObject jSONObject1 = new JSONObject();
      jSONObject1.put("storageId", this.storageId);
      jSONObject1.put("key", paramString);
      jSONObject.put("params", jSONObject1);
    } else {
      jSONObject.put("result", "{}");
    } 
    return jSONObject.toString();
  }
  
  public String setDOMStorageItem(int paramInt, boolean paramBoolean, String paramString1, String paramString2, String paramString3) throws JSONException {
    JSONObject jSONObject = new JSONObject();
    if (paramInt != 0)
      jSONObject.put("id", paramInt); 
    if (paramBoolean) {
      JSONObject jSONObject1;
      if (paramString2 == null) {
        jSONObject.put("method", "DOMStorage.domStorageItemAdded");
        jSONObject1 = new JSONObject();
        jSONObject1.put("storageId", this.storageId);
        jSONObject1.put("key", paramString1);
        jSONObject1.put("newValue", paramString3);
        jSONObject.put("params", jSONObject1);
      } else {
        jSONObject.put("method", "DOMStorage.domStorageItemUpdated");
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put("storageId", this.storageId);
        jSONObject2.put("key", paramString1);
        jSONObject2.put("oldValue", jSONObject1);
        jSONObject2.put("newValue", paramString3);
        jSONObject.put("params", jSONObject2);
      } 
    } else {
      jSONObject.put("result", "{}");
    } 
    return jSONObject.toString();
  }
  
  public void setStorageId(JSONObject paramJSONObject) {
    this.storageId = paramJSONObject;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\debug\storage\StorageReporter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */