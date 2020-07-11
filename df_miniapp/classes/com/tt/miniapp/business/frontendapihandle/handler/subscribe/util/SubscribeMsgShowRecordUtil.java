package com.tt.miniapp.business.frontendapihandle.handler.subscribe.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.storage.async.Action;
import com.tt.miniapp.business.frontendapihandle.handler.subscribe.data.TemplateMsgLimitInfo;
import com.tt.miniapp.mmkv.KVUtil;
import com.tt.miniapp.thread.ThreadPools;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandApplication;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.util.CharacterUtils;
import com.tt.miniapphost.util.JsonBuilder;
import java.util.TimeZone;
import org.json.JSONException;
import org.json.JSONObject;

public class SubscribeMsgShowRecordUtil {
  private static volatile String sUserId;
  
  public static SharedPreferences getSharedPreference() {
    return KVUtil.getSharedPreferences((Context)AppbrandContext.getInst().getApplicationContext(), "Subscribe_Message");
  }
  
  public static TemplateMsgAuthShowRecord getTemplateMsgAuthShowRecord(String paramString) {
    String str1 = (AppbrandApplication.getInst().getAppInfo()).appId;
    String str2 = getUserIdentifier();
    String str3 = getSharedPreference().getString(str1, "");
    if (TextUtils.isEmpty(str3))
      return null; 
    StringBuilder stringBuilder = new StringBuilder("appRecord = ");
    stringBuilder.append(str3);
    AppBrandLogger.d("SubscribeMsgShowRecordUtil", new Object[] { stringBuilder.toString() });
    JSONObject jSONObject = (new JsonBuilder(str3)).build().optJSONObject(str2);
    if (jSONObject == null)
      return null; 
    int i = jSONObject.optInt("lastShowTotalCount");
    long l1 = jSONObject.optLong("lastShowTime");
    jSONObject = jSONObject.optJSONObject(paramString);
    if (jSONObject == null)
      return new TemplateMsgAuthShowRecord(str1, str2, i, l1, paramString, 0, System.currentTimeMillis()); 
    jSONObject = jSONObject.optJSONObject("lastTplShowInfo");
    if (jSONObject == null)
      return new TemplateMsgAuthShowRecord(str1, str2, i, l1, paramString, 0, System.currentTimeMillis()); 
    long l2 = jSONObject.optLong("lastTplShowTime");
    return new TemplateMsgAuthShowRecord(str1, str2, i, l1, paramString, jSONObject.optInt("lastTplShowCount"), l2);
  }
  
  public static String getUserIdentifier() {
    // Byte code:
    //   0: getstatic com/tt/miniapp/business/frontendapihandle/handler/subscribe/util/SubscribeMsgShowRecordUtil.sUserId : Ljava/lang/String;
    //   3: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   6: ifeq -> 63
    //   9: ldc com/tt/miniapp/business/frontendapihandle/handler/subscribe/util/SubscribeMsgShowRecordUtil
    //   11: monitorenter
    //   12: getstatic com/tt/miniapp/business/frontendapihandle/handler/subscribe/util/SubscribeMsgShowRecordUtil.sUserId : Ljava/lang/String;
    //   15: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   18: ifeq -> 51
    //   21: invokestatic getHostClientUserInfo : ()Lcom/tt/miniapp/manager/UserInfoManager$UserInfo;
    //   24: astore_0
    //   25: aload_0
    //   26: ifnull -> 67
    //   29: aload_0
    //   30: getfield isLogin : Z
    //   33: ifeq -> 67
    //   36: aload_0
    //   37: getfield userId : Ljava/lang/String;
    //   40: astore_0
    //   41: goto -> 44
    //   44: aload_0
    //   45: invokestatic md5Hex : (Ljava/lang/String;)Ljava/lang/String;
    //   48: putstatic com/tt/miniapp/business/frontendapihandle/handler/subscribe/util/SubscribeMsgShowRecordUtil.sUserId : Ljava/lang/String;
    //   51: ldc com/tt/miniapp/business/frontendapihandle/handler/subscribe/util/SubscribeMsgShowRecordUtil
    //   53: monitorexit
    //   54: goto -> 63
    //   57: astore_0
    //   58: ldc com/tt/miniapp/business/frontendapihandle/handler/subscribe/util/SubscribeMsgShowRecordUtil
    //   60: monitorexit
    //   61: aload_0
    //   62: athrow
    //   63: getstatic com/tt/miniapp/business/frontendapihandle/handler/subscribe/util/SubscribeMsgShowRecordUtil.sUserId : Ljava/lang/String;
    //   66: areturn
    //   67: ldc 'AnonymousUser'
    //   69: astore_0
    //   70: goto -> 44
    // Exception table:
    //   from	to	target	type
    //   12	25	57	finally
    //   29	41	57	finally
    //   44	51	57	finally
    //   51	54	57	finally
    //   58	61	57	finally
  }
  
  public static boolean hasReachLimitCount(String paramString, TemplateMsgLimitInfo paramTemplateMsgLimitInfo1, TemplateMsgLimitInfo paramTemplateMsgLimitInfo2) {
    if (paramTemplateMsgLimitInfo1 != null) {
      StringBuilder stringBuilder;
      if (paramTemplateMsgLimitInfo2 == null)
        return true; 
      TemplateMsgAuthShowRecord templateMsgAuthShowRecord = getTemplateMsgAuthShowRecord(paramString);
      if (templateMsgAuthShowRecord == null)
        return false; 
      long l1 = System.currentTimeMillis();
      int i = templateMsgAuthShowRecord.lastTotalShowCount;
      long l2 = templateMsgAuthShowRecord.lastShowTime;
      if (l1 < l2) {
        templateMsgAuthShowRecord.resetLastShowTime();
        templateMsgAuthShowRecord.save();
        AppBrandLogger.d("SubscribeMsgShowRecordUtil", new Object[] { "lastShowTime is ahead of currentTime" });
        return true;
      } 
      boolean bool1 = needResetRecord(l2, l1, paramTemplateMsgLimitInfo1.getTimeUnit() * 1000);
      if (!bool1 && i >= paramTemplateMsgLimitInfo1.getMaxCount()) {
        stringBuilder = new StringBuilder("reach total limit, lastTotalCount = ");
        stringBuilder.append(i);
        stringBuilder.append(",  limit total count = ");
        stringBuilder.append(paramTemplateMsgLimitInfo1.getMaxCount());
        AppBrandLogger.d("SubscribeMsgShowRecordUtil", new Object[] { stringBuilder.toString() });
        return true;
      } 
      if (bool1)
        stringBuilder.resetLastTotalShowCount(); 
      l2 = ((TemplateMsgAuthShowRecord)stringBuilder).lastTplShowTime;
      i = ((TemplateMsgAuthShowRecord)stringBuilder).lastTplShowCount;
      if (l1 < l2) {
        stringBuilder.resetLastTplShowTime();
        stringBuilder.save();
        AppBrandLogger.d("SubscribeMsgShowRecordUtil", new Object[] { "lastTplShowTime is ahead of currentTime" });
        return true;
      } 
      boolean bool2 = needResetRecord(l2, l1, paramTemplateMsgLimitInfo2.getTimeUnit() * 1000);
      if (!bool2 && i >= paramTemplateMsgLimitInfo2.getMaxCount()) {
        stringBuilder = new StringBuilder("reach template msg limit, lastTplShowCount = ");
        stringBuilder.append(i);
        stringBuilder.append(", limit count = ");
        stringBuilder.append(paramTemplateMsgLimitInfo2.getMaxCount());
        AppBrandLogger.d("SubscribeMsgShowRecordUtil", new Object[] { stringBuilder.toString() });
        return true;
      } 
      if (bool2)
        stringBuilder.resetLastTplShowCount(); 
      if (bool1 || bool2)
        stringBuilder.save(); 
      return false;
    } 
    return true;
  }
  
  private static boolean isSameTimeUnit(long paramLong1, long paramLong2, int paramInt1, int paramInt2) {
    paramLong1 = translateLocalLocale(paramLong1);
    long l1 = paramInt2;
    paramLong2 = translateLocalLocale(paramLong2);
    long l2 = paramInt1;
    return ((paramLong1 - l1) / l2 == (paramLong2 - l1) / l2);
  }
  
  private static boolean needResetRecord(long paramLong1, long paramLong2, int paramInt) {
    if (paramInt != 86400000) {
      if (paramInt != 604800000) {
        boolean bool2 = isSameTimeUnit(paramLong1, paramLong2, paramInt, 0);
        return bool2 ^ true;
      } 
      boolean bool1 = isSameTimeUnit(paramLong1, paramLong2, paramInt, 363600000);
      return bool1 ^ true;
    } 
    boolean bool = isSameTimeUnit(paramLong1, paramLong2, paramInt, 18000000);
    return bool ^ true;
  }
  
  public static void recordTemplateMsgAuthShow(final String templateId) {
    ThreadUtil.runOnWorkThread(new Action() {
          public final void act() {
            if (TextUtils.isEmpty(templateId))
              return; 
            SubscribeMsgShowRecordUtil.TemplateMsgAuthShowRecord templateMsgAuthShowRecord2 = SubscribeMsgShowRecordUtil.getTemplateMsgAuthShowRecord(templateId);
            SubscribeMsgShowRecordUtil.TemplateMsgAuthShowRecord templateMsgAuthShowRecord1 = templateMsgAuthShowRecord2;
            if (templateMsgAuthShowRecord2 == null)
              templateMsgAuthShowRecord1 = new SubscribeMsgShowRecordUtil.TemplateMsgAuthShowRecord(templateId); 
            templateMsgAuthShowRecord1.lastTotalShowCount++;
            templateMsgAuthShowRecord1.lastShowTime = System.currentTimeMillis();
            templateMsgAuthShowRecord1.lastTplShowCount++;
            templateMsgAuthShowRecord1.lastTplShowTime = System.currentTimeMillis();
            templateMsgAuthShowRecord1.save();
            StringBuilder stringBuilder = new StringBuilder("record TemplateMsgInfo = ");
            stringBuilder.append(templateMsgAuthShowRecord1.toString());
            AppBrandLogger.d("SubscribeMsgShowRecordUtil", new Object[] { stringBuilder.toString() });
          }
        }ThreadPools.defaults());
  }
  
  private static long translateLocalLocale(long paramLong) {
    return paramLong + TimeZone.getDefault().getRawOffset();
  }
  
  public static void updateUser() {
    sUserId = CharacterUtils.empty();
    getUserIdentifier();
  }
  
  public static class TemplateMsgAuthShowRecord {
    public String appId = (AppbrandApplication.getInst().getAppInfo()).appId;
    
    public long lastShowTime;
    
    public int lastTotalShowCount;
    
    public int lastTplShowCount;
    
    public long lastTplShowTime;
    
    public String templateId;
    
    public String userId = SubscribeMsgShowRecordUtil.getUserIdentifier();
    
    public TemplateMsgAuthShowRecord(String param1String) {
      this.templateId = param1String;
    }
    
    public TemplateMsgAuthShowRecord(String param1String1, String param1String2, int param1Int1, long param1Long1, String param1String3, int param1Int2, long param1Long2) {
      this.lastTotalShowCount = param1Int1;
      this.lastShowTime = param1Long1;
      this.templateId = param1String3;
      this.lastTplShowCount = param1Int2;
      this.lastTplShowTime = param1Long2;
    }
    
    public void resetLastShowTime() {
      this.lastShowTime = System.currentTimeMillis();
    }
    
    public void resetLastTotalShowCount() {
      this.lastTotalShowCount = 0;
    }
    
    public void resetLastTplShowCount() {
      this.lastTplShowCount = 0;
    }
    
    public void resetLastTplShowTime() {
      this.lastTplShowTime = System.currentTimeMillis();
    }
    
    public void save() {
      JSONObject jSONObject5 = (new JsonBuilder(SubscribeMsgShowRecordUtil.getSharedPreference().getString(this.appId, ""))).build();
      JSONObject jSONObject2 = jSONObject5.optJSONObject(this.userId);
      JSONObject jSONObject1 = jSONObject2;
      if (jSONObject2 == null)
        jSONObject1 = new JSONObject(); 
      JSONObject jSONObject3 = jSONObject1.optJSONObject(this.templateId);
      jSONObject2 = jSONObject3;
      if (jSONObject3 == null)
        jSONObject2 = new JSONObject(); 
      JSONObject jSONObject4 = jSONObject2.optJSONObject("lastTplShowInfo");
      jSONObject3 = jSONObject4;
      if (jSONObject4 == null)
        jSONObject3 = new JSONObject(); 
      try {
        jSONObject3.put("lastTplShowTime", this.lastTplShowTime);
        jSONObject3.put("lastTplShowCount", this.lastTplShowCount);
        jSONObject2.put("lastTplShowInfo", jSONObject3);
        jSONObject1.put(this.templateId, jSONObject2);
        jSONObject1.put("lastShowTotalCount", this.lastTotalShowCount);
        jSONObject1.put("lastShowTime", this.lastShowTime);
        jSONObject5.put(this.userId, jSONObject1);
        SubscribeMsgShowRecordUtil.getSharedPreference().edit().putString(this.appId, jSONObject5.toString()).apply();
        return;
      } catch (JSONException jSONException) {
        AppBrandLogger.e("SubscribeMsgShowRecordUtil", new Object[] { "", jSONException });
        return;
      } 
    }
    
    public String toString() {
      StringBuilder stringBuilder = new StringBuilder("TemplateMsgAuthShowRecord{appId='");
      stringBuilder.append(this.appId);
      stringBuilder.append('\'');
      stringBuilder.append(", userId='");
      stringBuilder.append(this.userId);
      stringBuilder.append('\'');
      stringBuilder.append(", lastTotalShowCount=");
      stringBuilder.append(this.lastTotalShowCount);
      stringBuilder.append(", lastShowTime=");
      stringBuilder.append(this.lastShowTime);
      stringBuilder.append(", templateId='");
      stringBuilder.append(this.templateId);
      stringBuilder.append('\'');
      stringBuilder.append(", lastTplShowCount=");
      stringBuilder.append(this.lastTplShowCount);
      stringBuilder.append(", lastTplShowTime=");
      stringBuilder.append(this.lastTplShowTime);
      stringBuilder.append('}');
      return stringBuilder.toString();
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\business\frontendapihandle\handler\subscrib\\util\SubscribeMsgShowRecordUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */