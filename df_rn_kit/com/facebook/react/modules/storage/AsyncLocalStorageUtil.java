package com.facebook.react.modules.storage;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import com.facebook.react.bridge.ReadableArray;
import java.util.Arrays;
import java.util.Iterator;
import org.json.JSONException;
import org.json.JSONObject;

public class AsyncLocalStorageUtil {
  static String buildKeySelection(int paramInt) {
    String[] arrayOfString = new String[paramInt];
    Arrays.fill((Object[])arrayOfString, "?");
    StringBuilder stringBuilder = new StringBuilder("key IN (");
    stringBuilder.append(TextUtils.join(", ", (Object[])arrayOfString));
    stringBuilder.append(")");
    return stringBuilder.toString();
  }
  
  static String[] buildKeySelectionArgs(ReadableArray paramReadableArray, int paramInt1, int paramInt2) {
    String[] arrayOfString = new String[paramInt2];
    for (int i = 0; i < paramInt2; i++)
      arrayOfString[i] = paramReadableArray.getString(paramInt1 + i); 
    return arrayOfString;
  }
  
  private static void deepMergeInto(JSONObject paramJSONObject1, JSONObject paramJSONObject2) throws JSONException {
    Iterator<String> iterator = paramJSONObject2.keys();
    while (iterator.hasNext()) {
      String str = iterator.next();
      JSONObject jSONObject1 = paramJSONObject2.optJSONObject(str);
      JSONObject jSONObject2 = paramJSONObject1.optJSONObject(str);
      if (jSONObject1 != null && jSONObject2 != null) {
        deepMergeInto(jSONObject2, jSONObject1);
        paramJSONObject1.put(str, jSONObject2);
        continue;
      } 
      paramJSONObject1.put(str, paramJSONObject2.get(str));
    } 
  }
  
  public static String getItemImpl(SQLiteDatabase paramSQLiteDatabase, String paramString) {
    Cursor cursor = paramSQLiteDatabase.query("catalystLocalStorage", new String[] { "value" }, "key=?", new String[] { paramString }, null, null, null);
    try {
      boolean bool = cursor.moveToFirst();
      if (!bool)
        return null; 
      paramString = cursor.getString(0);
      return paramString;
    } finally {
      cursor.close();
    } 
  }
  
  static boolean mergeImpl(SQLiteDatabase paramSQLiteDatabase, String paramString1, String paramString2) throws JSONException {
    String str = getItemImpl(paramSQLiteDatabase, paramString1);
    if (str != null) {
      JSONObject jSONObject = new JSONObject(str);
      deepMergeInto(jSONObject, new JSONObject(paramString2));
      paramString2 = jSONObject.toString();
    } 
    return setItemImpl(paramSQLiteDatabase, paramString1, paramString2);
  }
  
  static boolean setItemImpl(SQLiteDatabase paramSQLiteDatabase, String paramString1, String paramString2) {
    ContentValues contentValues = new ContentValues();
    contentValues.put("key", paramString1);
    contentValues.put("value", paramString2);
    return (-1L != paramSQLiteDatabase.insertWithOnConflict("catalystLocalStorage", null, contentValues, 5));
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\modules\storage\AsyncLocalStorageUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */