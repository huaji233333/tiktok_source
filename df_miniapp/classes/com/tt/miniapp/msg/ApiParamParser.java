package com.tt.miniapp.msg;

import android.text.TextUtils;
import com.he.jsbinding.JsObject;
import com.tt.frontendapiinterface.f;
import com.tt.frontendapiinterface.g;
import com.tt.miniapp.business.frontendapihandle.entity.CommonApiOutputParam;
import com.tt.miniapp.business.frontendapihandle.handler.net.ApiCreateSocketTaskParam;
import com.tt.miniapp.business.frontendapihandle.handler.net.ApiOperateSocketTaskParam;
import com.tt.miniapp.msg.bean.ApiReadFileParam;
import com.tt.miniapp.msg.bean.ApiWriteFileParam;
import com.tt.miniapp.msg.bean.NativeBufferItem;
import com.tt.miniapp.util.ToolUtils;
import com.tt.miniapphost.AppBrandLogger;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ApiParamParser {
  public static JSONObject convertArrayBufferToJSONObject(String paramString, ApiJsExecuteContext paramApiJsExecuteContext) {
    int i;
    if (paramString.hashCode() == 1907068440 && paramString.equals("createRequestTask")) {
      i = 0;
    } else {
      i = -1;
    } 
    if (i == 0) {
      String str5 = paramApiJsExecuteContext.getString("url");
      String str2 = paramApiJsExecuteContext.getString("method");
      paramString = str2;
      if (TextUtils.equals(str2, "undefined"))
        paramString = "GET"; 
      String str6 = paramApiJsExecuteContext.getString("data");
      boolean bool = paramApiJsExecuteContext.getBoolean("usePrefetchCache");
      paramApiJsExecuteContext.getString("header");
      JsObject jsObject1 = paramApiJsExecuteContext.getObject("header2");
      JSONObject jSONObject = new JSONObject();
      if (jsObject1 != null) {
        i = jsObject1.arrayGetLength();
        for (int j = 0;; j++) {
          if (j < i) {
            JsObject jsObject = paramApiJsExecuteContext.<JsObject>getParamInJsArray(jsObject1, j, JsObject.class);
            if (jsObject != null) {
              try {
                String str = jsObject.getString("0");
                try {
                  jSONObject.put(str, jsObject.getString("1"));
                } catch (Exception null) {}
              } catch (Exception exception) {}
              AppBrandLogger.e("ApiParamParser", new Object[] { exception });
            } 
          } else {
            break;
          } 
        } 
      } 
      String str3 = paramApiJsExecuteContext.getString("dataType");
      String str1 = str3;
      if (isEmptyString(str3))
        str1 = null; 
      String str4 = paramApiJsExecuteContext.getString("responseType");
      str3 = str4;
      if (isEmptyString(str4))
        str3 = null; 
      JsObject jsObject2 = paramApiJsExecuteContext.getObject("__nativeBuffers__");
      JSONArray jSONArray = new JSONArray();
      if (jsObject2 != null) {
        jsObject2 = paramApiJsExecuteContext.<JsObject>getParamInJsArray(jsObject2, 0, JsObject.class);
        if (jsObject2 != null) {
          JsObject jsObject = paramApiJsExecuteContext.<JsObject>getParamInJsObject(jsObject2, "value", JsObject.class);
          if (jsObject != null) {
            ByteBuffer byteBuffer = jsObject.asArrayBuffer();
            byte[] arrayOfByte = new byte[byteBuffer.limit() - byteBuffer.position()];
            byteBuffer.get(arrayOfByte);
            try {
              JSONObject jSONObject1 = new JSONObject();
              jSONObject1.put("value", arrayOfByte);
              jSONArray.put(0, jSONObject1);
            } catch (JSONException jSONException) {}
          } 
        } 
      } 
      try {
        JSONObject jSONObject1 = new JSONObject();
        jSONObject1.put("url", str5);
        jSONObject1.put("method", paramString);
        jSONObject1.put("data", str6);
        jSONObject1.put("usePrefetchCache", bool);
        jSONObject1.put("header", jSONObject);
        jSONObject1.put("dataType", str1);
        jSONObject1.put("responseType", str3);
        jSONObject1.put("__nativeBuffers__", jSONArray);
        return jSONObject1;
      } catch (JSONException jSONException) {}
    } 
    return null;
  }
  
  public static JsObject createResponseParam(String paramString, g paramg, ApiJsExecuteContext paramApiJsExecuteContext) {
    JsObject jsObject1;
    ApiReadFileParam.OutputParam outputParam1;
    ApiOperateSocketTaskParam.OutputParam outputParam;
    JsObject jsObject2 = null;
    if (paramg == null)
      return null; 
    boolean bool = TextUtils.equals("readFile", paramString);
    int i = 0;
    if (bool || TextUtils.equals("readFileSync", paramString)) {
      jsObject1 = paramApiJsExecuteContext.createObject();
      outputParam1 = (ApiReadFileParam.OutputParam)paramg;
      List list = outputParam1.__nativeBuffers__;
      if (list != null && !list.isEmpty()) {
        jsObject2 = paramApiJsExecuteContext.createJsArray(list.size());
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
          byte[] arrayOfByte = ((ApiReadFileParam.OutputParam.NativeBufferItem)iterator.next()).value;
          JsObject jsObject3 = paramApiJsExecuteContext.createObject();
          JsObject jsObject4 = paramApiJsExecuteContext.createArrayBuffer(arrayOfByte.length);
          jsObject4.asArrayBuffer().put(arrayOfByte);
          jsObject3.set("key", "data");
          jsObject3.set("value", jsObject4);
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append(i);
          jsObject2.set(stringBuilder.toString(), jsObject3);
          i++;
        } 
        jsObject1.set("__nativeBuffers__", jsObject2);
      } 
      jsObject1.set("errMsg", outputParam1.errMsg);
      jsObject2 = jsObject1;
      if (!TextUtils.isEmpty(outputParam1.data)) {
        jsObject1.set("data", outputParam1.data);
        jsObject2 = jsObject1;
      } 
      return jsObject2;
    } 
    if (TextUtils.equals("writeFile", (CharSequence)jsObject1) || TextUtils.equals("writeFileSync", (CharSequence)jsObject1)) {
      jsObject1 = paramApiJsExecuteContext.createObject();
      jsObject1.set("errMsg", ((ApiWriteFileParam.OutPutParam)outputParam1).errMsg);
      return jsObject1;
    } 
    if (isRequestTaskApi((String)jsObject1))
      return createResponseParamFromCommon((String)jsObject1, (CommonApiOutputParam)outputParam1, paramApiJsExecuteContext); 
    if (TextUtils.equals("operateSocketTask", (CharSequence)jsObject1)) {
      outputParam = (ApiOperateSocketTaskParam.OutputParam)outputParam1;
      jsObject1 = paramApiJsExecuteContext.createObject();
      jsObject1.set("errMsg", outputParam.errMsg);
      jsObject2 = jsObject1;
      if (!TextUtils.isEmpty(outputParam.errStack)) {
        jsObject1.set("errStack", outputParam.errStack);
        return jsObject1;
      } 
    } else {
      JsObject jsObject3;
      JsObject jsObject4;
      if (TextUtils.equals("onSocketTaskStateChange", (CharSequence)jsObject1)) {
        outputParam = outputParam;
        jsObject1 = paramApiJsExecuteContext.createObject();
        if (!TextUtils.isEmpty(outputParam.errMsg))
          jsObject1.set("errMsg", outputParam.errMsg); 
        if (!TextUtils.isEmpty(outputParam.data))
          jsObject1.set("data", outputParam.data); 
        if (!TextUtils.isEmpty(outputParam.state))
          jsObject1.set("state", outputParam.state); 
        if (!TextUtils.isEmpty(outputParam.header))
          jsObject1.set("header", outputParam.header); 
        if (outputParam.socketTaskId > 0)
          jsObject1.set("socketTaskId", outputParam.socketTaskId); 
        jsObject1.set("socketType", outputParam.socketType);
        jsObject1.set("protocolType", outputParam.protocolType);
        jsObject2 = jsObject1;
        if (outputParam.__nativeBuffers__ != null) {
          NativeBufferItem nativeBufferItem = outputParam.__nativeBuffers__.get(0);
          jsObject2 = jsObject1;
          if (nativeBufferItem != null) {
            jsObject2 = jsObject1;
            if (nativeBufferItem.value != null) {
              jsObject2 = paramApiJsExecuteContext.createArrayBuffer((int)ToolUtils.getByteBufferSize(nativeBufferItem.value));
              jsObject2.asArrayBuffer().put(nativeBufferItem.value);
              jsObject3 = paramApiJsExecuteContext.createObject();
              jsObject3.set("key", "data");
              jsObject3.set("value", jsObject2);
              jsObject4 = paramApiJsExecuteContext.createJsArray(1);
              jsObject4.set("0", jsObject3);
              jsObject1.set("__nativeBuffers__", jsObject4);
              return jsObject1;
            } 
          } 
        } 
      } else if (TextUtils.equals("createSocketTask", (CharSequence)jsObject1)) {
        ApiCreateSocketTaskParam.OutputParam outputParam2 = (ApiCreateSocketTaskParam.OutputParam)jsObject3;
        jsObject3 = jsObject4.createObject();
        jsObject3.set("errMsg", outputParam2.errMsg);
        jsObject3.set("socketTaskId", outputParam2.socketTaskId);
        jsObject3.set("socketType", outputParam2.socketType);
        return jsObject3;
      } 
    } 
    return jsObject2;
  }
  
  public static JsObject createResponseParamFromCommon(String paramString, CommonApiOutputParam paramCommonApiOutputParam, ApiJsExecuteContext paramApiJsExecuteContext) {
    JsObject jsObject = paramApiJsExecuteContext.createObject();
    Iterator<String> iterator = paramCommonApiOutputParam.keys();
    while (iterator.hasNext()) {
      String str = iterator.next();
      Object object = paramCommonApiOutputParam.getData(str);
      if (object != null && !insertSpecialParam(paramString, jsObject, paramApiJsExecuteContext, str, object)) {
        if (object instanceof Boolean) {
          jsObject.set(str, ((Boolean)object).booleanValue());
          continue;
        } 
        if (object instanceof Integer) {
          jsObject.set(str, ((Integer)object).intValue());
          continue;
        } 
        if (object instanceof Double) {
          jsObject.set(str, ((Double)object).doubleValue());
          continue;
        } 
        if (object instanceof String) {
          jsObject.set(str, (String)object);
          continue;
        } 
        if (object instanceof JsObject)
          jsObject.set(str, (JsObject)object); 
      } 
    } 
    return jsObject;
  }
  
  private static boolean insertSpecialParam(String paramString1, JsObject paramJsObject, ApiJsExecuteContext paramApiJsExecuteContext, String paramString2, Object paramObject) {
    if (isRequestTaskApi(paramString1)) {
      byte b = -1;
      int i = paramString2.hashCode();
      if (i != -1908903652) {
        if (i == -1221270899 && paramString2.equals("header"))
          b = 0; 
      } else if (paramString2.equals("__nativeBuffers__")) {
        b = 1;
      } 
      if (b != 0) {
        if (b != 1)
          return false; 
        JSONObject jSONObject = ((JSONArray)paramObject).optJSONObject(0);
        if (jSONObject != null) {
          byte[] arrayOfByte = (byte[])jSONObject.opt("value");
          if (arrayOfByte != null) {
            JsObject jsObject1 = paramApiJsExecuteContext.createArrayBuffer(arrayOfByte.length);
            jsObject1.asArrayBuffer().put(arrayOfByte);
            JsObject jsObject2 = paramApiJsExecuteContext.createObject();
            jsObject2.set("key", "data");
            jsObject2.set("value", jsObject1);
            jsObject1 = paramApiJsExecuteContext.createJsArray(1);
            jsObject1.set("0", jsObject2);
            paramJsObject.set("__nativeBuffers__", jsObject1);
            return true;
          } 
        } 
      } else {
        JsObject jsObject = paramApiJsExecuteContext.createObject();
        JSONObject jSONObject = (JSONObject)paramObject;
        Iterator<String> iterator = jSONObject.keys();
        while (iterator.hasNext()) {
          try {
            paramObject = iterator.next();
            jsObject.set((String)paramObject, jSONObject.getString((String)paramObject));
          } catch (JSONException jSONException) {
            AppBrandLogger.e("ApiParamParser", new Object[] { "insertSpecialParam", jSONException });
          } 
        } 
        paramJsObject.set("header", jsObject);
        return true;
      } 
    } 
    return false;
  }
  
  public static boolean isEmptyString(String paramString) {
    return (TextUtils.isEmpty(paramString) || paramString.toLowerCase().equals("undefined") || paramString.toLowerCase().equals("null"));
  }
  
  private static boolean isRequestTaskApi(String paramString) {
    byte b;
    switch (paramString.hashCode()) {
      default:
        b = -1;
        break;
      case 1907068440:
        if (paramString.equals("createRequestTask")) {
          b = 0;
          break;
        } 
      case 1846050572:
        if (paramString.equals("onRequestTaskStateChange")) {
          b = 1;
          break;
        } 
      case 309666532:
        if (paramString.equals("onInnerRequestTaskStateChange")) {
          b = 3;
          break;
        } 
      case 38556058:
        if (paramString.equals("createInnerRequestTask")) {
          b = 2;
          break;
        } 
    } 
    return !(b != 0 && b != 1 && b != 2 && b != 3);
  }
  
  public static f parse(String paramString, ApiJsExecuteContext paramApiJsExecuteContext) {
    ByteBuffer byteBuffer;
    String str1;
    JsObject jsObject;
    ArrayList<NativeBufferItem> arrayList;
    boolean bool = TextUtils.equals("writeFile", paramString);
    String str4 = null;
    String str2 = null;
    String str3 = null;
    int i = 0;
    if (bool || TextUtils.equals("writeFileSync", paramString)) {
      str3 = paramApiJsExecuteContext.getString("filePath");
      str4 = paramApiJsExecuteContext.getString("data");
      String str = paramApiJsExecuteContext.getString("encoding");
      JsObject jsObject1 = paramApiJsExecuteContext.getObject("__nativeBuffers__");
      paramString = str2;
      if (jsObject1 != null) {
        jsObject = paramApiJsExecuteContext.<JsObject>getParamInJsObject(paramApiJsExecuteContext.<JsObject>getParamInJsArray(jsObject1, 0, JsObject.class), "value", JsObject.class);
        paramString = str2;
        if (jsObject != null)
          byteBuffer = jsObject.asArrayBuffer(); 
      } 
      return (f)new ApiWriteFileParam.InputParam(str3, str, str4, byteBuffer);
    } 
    if (TextUtils.equals("readFile", (CharSequence)byteBuffer) || TextUtils.equals("readFileSync", (CharSequence)byteBuffer))
      return (f)new ApiReadFileParam.InputParam(jsObject.getString("filePath"), jsObject.getString("encoding")); 
    if (TextUtils.equals("createSocketTask", (CharSequence)byteBuffer)) {
      str3 = jsObject.getString("url");
      str4 = jsObject.getString("method");
      bool = jsObject.getBoolean("__skipDomainCheck__");
      JsObject jsObject1 = jsObject.getObject("header2");
      str2 = jsObject.getString("socketType");
      str1 = str2;
      if (TextUtils.isEmpty(str2))
        str1 = "tradition"; 
      JSONObject jSONObject = new JSONObject();
      if (jsObject1 != null) {
        int k = jsObject1.arrayGetLength();
        for (int j = 0; j < k; j++) {
          JsObject jsObject2 = jsObject.<JsObject>getParamInJsArray(jsObject1, j, JsObject.class);
          if (jsObject2 != null) {
            try {
              jSONObject.put(jsObject2.getString("0"), jsObject2.getString("1"));
            } catch (Exception exception) {
              AppBrandLogger.e("ApiParamParser", new Object[] { exception });
            } 
            jsObject2.release();
          } 
        } 
      } 
      jsObject1 = jsObject.getObject("protocols");
      JSONArray jSONArray = new JSONArray();
      if (jsObject1 != null) {
        int k = jsObject1.arrayGetLength();
        for (int j = i; j < k; j++)
          jSONArray.put(jsObject.<String>getParamInJsArray(jsObject1, j, String.class)); 
      } 
      return (f)new ApiCreateSocketTaskParam.InputParam(str1, str3, str4, jSONObject, jSONArray, bool);
    } 
    str2 = str4;
    if (TextUtils.equals("operateSocketTask", str1)) {
      ArrayList<NativeBufferItem> arrayList1;
      str4 = jsObject.getString("operationType");
      String str5 = jsObject.getString("data");
      int j = jsObject.getInt("socketTaskId");
      String str6 = jsObject.getString("reason");
      i = jsObject.getInt("code");
      JsObject jsObject1 = jsObject.getObject("__nativeBuffers__");
      str1 = str3;
      if (jsObject1 != null) {
        arrayList = new ArrayList();
        JsObject jsObject2 = jsObject.<JsObject>getParamInJsArray(jsObject1, 0, JsObject.class);
        arrayList1 = arrayList;
        if (jsObject2 != null) {
          jsObject = jsObject.<JsObject>getParamInJsObject(jsObject2, "value", JsObject.class);
          arrayList1 = arrayList;
          if (jsObject != null) {
            arrayList.add(new NativeBufferItem("data", jsObject.asArrayBuffer()));
            arrayList1 = arrayList;
          } 
        } 
      } 
      return (f)new ApiOperateSocketTaskParam.InputParam(str4, j, str5, arrayList1, i, str6);
    } 
    return (f)arrayList;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ApiParamParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */