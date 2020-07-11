package com.tt.miniapp.address;

import android.content.Context;
import android.os.AsyncTask;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.util.AppbrandUtil;
import com.tt.miniapphost.util.StorageUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoadAddressTask extends AsyncTask<String, Void, List<AddressInfo>> {
  private static AtomicBoolean isLoadFinished;
  
  private static Map<String, List<AddressInfo>> mCache = new ConcurrentHashMap<String, List<AddressInfo>>();
  
  private static Map<String, Integer> mDepthCache = new ConcurrentHashMap<String, Integer>();
  
  private String DEFAULT_DISTRICT_CHINA;
  
  private LoadCallBack mCallBack;
  
  private int mDepth = 2;
  
  private String mKey = "province";
  
  private WeakReference<Context> mWeakContext;
  
  static {
    isLoadFinished = new AtomicBoolean(false);
  }
  
  public LoadAddressTask(Context paramContext, LoadCallBack paramLoadCallBack) {
    this.mCallBack = paramLoadCallBack;
    this.mWeakContext = new WeakReference<Context>(paramContext);
    this.DEFAULT_DISTRICT_CHINA = paramContext.getString(2097741871);
  }
  
  public static InputStream initializeDivisionFile(Context paramContext) throws Exception {
    File file2 = new File(new File(AppbrandUtil.getOfflineZipDir(paramContext), "address"), "address.json");
    if (file2.exists() && file2.length() > 1L)
      return new FileInputStream(file2); 
    StringBuffer stringBuffer = new StringBuffer(StorageUtil.getExternalFilesDir(paramContext).getPath());
    stringBuffer.append("/address.json");
    File file1 = new File(stringBuffer.toString());
    return (file1.exists() && file1.length() > 1L) ? new FileInputStream(file1) : null;
  }
  
  private void loadCityFromProvince(JSONObject paramJSONObject) throws JSONException {
    if (paramJSONObject == null)
      return; 
    JSONArray jSONArray = paramJSONObject.getJSONArray("regionEntitys");
    for (int i = 0; i < jSONArray.length(); i++) {
      ArrayList<AddressInfo> arrayList = new ArrayList();
      for (int j = 0; j < jSONArray.length(); j++)
        arrayList.add(new AddressInfo(jSONArray.getJSONObject(j).getString("region"), jSONArray.getJSONObject(j).getString("code"), "city")); 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(paramJSONObject.getString("region"));
      stringBuilder.append(paramJSONObject.getString("code"));
      stringBuilder.append("province");
      String str = stringBuilder.toString();
      AppBrandLogger.d("LoadAddressTask", new Object[] { "city name ", str });
      mCache.put(str, arrayList);
      mDepthCache.put(str, Integer.valueOf(1));
      loadDistrictFromCity(jSONArray.getJSONObject(i));
    } 
  }
  
  private void loadDistrictFromCity(JSONObject paramJSONObject) throws JSONException {
    if (paramJSONObject == null)
      return; 
    ArrayList<AddressInfo> arrayList = new ArrayList();
    JSONArray jSONArray = paramJSONObject.getJSONArray("regionEntitys");
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramJSONObject.getString("region"));
    stringBuilder.append(paramJSONObject.getString("code"));
    stringBuilder.append("city");
    String str = stringBuilder.toString();
    for (int i = 0; i < jSONArray.length(); i++)
      arrayList.add(new AddressInfo(jSONArray.getJSONObject(i).getString("region"), jSONArray.getJSONObject(i).getString("code"), "district")); 
    if (arrayList.size() <= 0) {
      arrayList.add(new AddressInfo(this.DEFAULT_DISTRICT_CHINA, "", "district"));
      mCache.put(str, arrayList);
      mDepthCache.put(str, Integer.valueOf(2));
      return;
    } 
    mCache.put(str, arrayList);
    mDepthCache.put(str, Integer.valueOf(2));
  }
  
  private List<AddressInfo> loadProvince(JSONArray paramJSONArray) throws JSONException {
    ArrayList<AddressInfo> arrayList = new ArrayList();
    for (int i = 0; i < paramJSONArray.length(); i++) {
      JSONObject jSONObject = paramJSONArray.getJSONObject(i);
      String str1 = jSONObject.getString("region");
      String str2 = jSONObject.getString("code");
      loadCityFromProvince(jSONObject);
      if (!arrayList.contains(str1))
        arrayList.add(new AddressInfo(str1, str2, "province")); 
    } 
    mCache.put("province", arrayList);
    mDepthCache.put("province", Integer.valueOf(0));
    return arrayList;
  }
  
  protected List<AddressInfo> doInBackground(String... paramVarArgs) {
    AppBrandLogger.d("LoadAddressTask", new Object[] { "start load :", paramVarArgs[0] });
    try {
      return loadDivisionFromJson(paramVarArgs[0]);
    } catch (JSONException jSONException) {
      AppBrandLogger.e("LoadAddressTask", new Object[] { jSONException });
      return null;
    } 
  }
  
  public int getLastDepth() {
    return this.mDepth;
  }
  
  public List<AddressInfo> loadDivisionFromCache(String paramString) {
    AppBrandLogger.e("LoadAddressTask", new Object[] { "queryKey ", paramString });
    if (mCache.containsKey(paramString)) {
      this.mDepth = ((Integer)mDepthCache.get(paramString)).intValue();
      return mCache.get(paramString);
    } 
    return null;
  }
  
  public List<AddressInfo> loadDivisionFromJson(String paramString) throws JSONException {
    List<AddressInfo> list = loadDivisionFromCache(paramString);
    if (list != null)
      return list; 
    try {
      InputStream inputStream = initializeDivisionFile(this.mWeakContext.get());
      if (inputStream == null) {
        AppBrandLogger.e("LoadAddressTask", new Object[] { "address.json does not exist!" });
        try {
          isLoadFinished.set(true);
          (new Object[2])[0] = "load asset file finished:";
          throw new NullPointerException();
        } catch (Exception exception) {
          AppBrandLogger.stacktrace(6, "LoadAddressTask", exception.getStackTrace());
          return null;
        } 
      } 
      byte[] arrayOfByte = new byte[inputStream.available()];
      inputStream.read(arrayOfByte);
      inputStream.close();
      JSONArray jSONArray = new JSONArray(new String(arrayOfByte, "UTF-8"));
      try {
        isLoadFinished.set(true);
        AppBrandLogger.d("LoadAddressTask", new Object[] { "load asset file finished:", Integer.valueOf(jSONArray.length()) });
      } catch (Exception exception1) {
        AppBrandLogger.stacktrace(6, "LoadAddressTask", exception1.getStackTrace());
      } 
    } catch (Exception exception1) {
      AppBrandLogger.e("LoadAddressTask", new Object[] { "load error:", exception1 });
      try {
        isLoadFinished.set(true);
        (new Object[2])[0] = "load asset file finished:";
        throw new NullPointerException();
      } catch (Exception exception2) {
        AppBrandLogger.stacktrace(6, "LoadAddressTask", exception2.getStackTrace());
        exception2 = null;
      } 
    } finally {}
    if (list == null || list.length() <= 0) {
      AppBrandLogger.e("LoadAddressTask", new Object[] { "empty list found!" });
      return null;
    } 
    if (mCache.size() <= 1) {
      AppBrandLogger.e("LoadAddressTask", new Object[] { "Json parse exception ", exception });
      loadProvince((JSONArray)list);
    } 
    this.mDepth = ((Integer)mDepthCache.get(exception)).intValue();
    return mCache.get(exception);
  }
  
  protected void onPostExecute(List<AddressInfo> paramList) {
    if (paramList == null)
      return; 
    AppBrandLogger.d("LoadAddressTask", new Object[] { "load finished:", Integer.valueOf(paramList.size()), ",depth:", Integer.valueOf(this.mDepth) });
    LoadCallBack loadCallBack = this.mCallBack;
    if (loadCallBack != null)
      loadCallBack.onLoaded(this.mKey, paramList, this.mDepth); 
  }
  
  public static interface LoadCallBack {
    void onLoaded(String param1String, List<AddressInfo> param1List, int param1Int);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\address\LoadAddressTask.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */