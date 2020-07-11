package com.tt.miniapp.permission;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.SparseArray;
import com.tt.miniapp.AppConfig;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.business.frontendapihandle.handler.subscribe.util.SubscribeMsgShowRecordUtil;
import com.tt.miniapp.event.Event;
import com.tt.miniapp.impl.HostOptionPermissionDependImpl;
import com.tt.miniapp.impl.HostOptionUiDependImpl;
import com.tt.miniapp.manager.UserInfoManager;
import com.tt.miniapp.mmkv.KVUtil;
import com.tt.miniapp.secrecy.SecrecyManager;
import com.tt.miniapp.util.BaseNavBarUtils;
import com.tt.miniapp.util.StackUtil;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandApplication;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.MiniappHostBase;
import com.tt.miniapphost.entity.AppInfoEntity;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.miniapphost.monitor.AppBrandMonitor;
import com.tt.miniapphost.permission.IPermissionsRequestCallback;
import com.tt.miniapphost.permission.IPermissionsResultAction;
import com.tt.miniapphost.process.HostProcessBridge;
import com.tt.miniapphost.util.AppbrandUtil;
import com.tt.miniapphost.util.CharacterUtils;
import com.tt.miniapphost.util.DebugUtil;
import com.tt.miniapphost.util.UIUtils;
import com.tt.option.t.c;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONObject;

public final class BrandPermissionUtils {
  static int INNERTYPE = 1;
  
  static String SP_PERMISSION_KEY_PREFIX = "permission";
  
  static String TAG = "BrandPermissionUtils";
  
  public static final Object mAuthLock;
  
  private static final Object mLock;
  
  private static SparseArray<List<IPermissionsResultAction>> mPermissionResultActionArray;
  
  public static SparseArray<List<IPermissionsRequestCallback>> mPermissionResultCallbackArray;
  
  public static List<Integer> permissionTypeList;
  
  static {
    ArrayList<Integer> arrayList = new ArrayList();
    permissionTypeList = arrayList;
    arrayList.add(Integer.valueOf(11));
    permissionTypeList.add(Integer.valueOf(12));
    permissionTypeList.add(Integer.valueOf(13));
    permissionTypeList.add(Integer.valueOf(14));
    permissionTypeList.add(Integer.valueOf(15));
    permissionTypeList.add(Integer.valueOf(16));
    permissionTypeList.add(Integer.valueOf(17));
    permissionTypeList.add(Integer.valueOf(18));
    permissionTypeList.add(Integer.valueOf(19));
    permissionTypeList.add(Integer.valueOf(20));
    mPermissionResultActionArray = new SparseArray();
    mLock = new Object();
    mPermissionResultCallbackArray = new SparseArray();
    mAuthLock = new Object();
  }
  
  public static String appbrandpermissionTypeToEventParamVal(int paramInt) {
    if (paramInt != 20) {
      switch (paramInt) {
        default:
          return null;
        case 17:
          return "photo";
        case 16:
          return "phone_num";
        case 15:
          return "address";
        case 14:
          return "camera";
        case 13:
          return "record";
        case 12:
          return "location";
        case 11:
          break;
      } 
      return "user_info";
    } 
    return "subscribe_assistant";
  }
  
  private static LinkedHashMap<Integer, String> createOkResultMap(Set<BrandPermission> paramSet) {
    LinkedHashMap<Object, Object> linkedHashMap = new LinkedHashMap<Object, Object>();
    Iterator<BrandPermission> iterator = paramSet.iterator();
    while (iterator.hasNext())
      linkedHashMap.put(Integer.valueOf(((BrandPermission)iterator.next()).getPermissionType()), "ok"); 
    return (LinkedHashMap)linkedHashMap;
  }
  
  public static void filterAuthorizedPermissions(Set<BrandPermission> paramSet, LinkedHashMap<Integer, String> paramLinkedHashMap) {
    MiniappHostBase miniappHostBase = AppbrandContext.getInst().getCurrentActivity();
    if (miniappHostBase == null)
      return; 
    Iterator<BrandPermission> iterator = paramSet.iterator();
    while (iterator.hasNext()) {
      BrandPermission brandPermission = iterator.next();
      boolean bool4 = BrandPermission.isStrictPermission(brandPermission);
      boolean bool5 = hasRequestPermission(brandPermission.getPermissionType());
      if (brandPermission.getSysPermissions() == null) {
        paramSet = new HashSet<BrandPermission>();
      } else {
        paramSet = new HashSet<BrandPermission>((Collection)Arrays.asList((Object[])brandPermission.getSysPermissions()));
      } 
      PackageManager packageManager = miniappHostBase.getPackageManager();
      Iterator<BrandPermission> iterator1 = paramSet.iterator();
      boolean bool3 = true;
      boolean bool1 = true;
      while (iterator1.hasNext()) {
        if (-1 == packageManager.checkPermission((String)iterator1.next(), miniappHostBase.getPackageName()))
          bool1 = false; 
      } 
      if (!bool4) {
        boolean bool = bool3;
        if (!bool5) {
          if (isAuthPass(brandPermission)) {
            bool = bool3;
            continue;
          } 
        } else {
          continue;
        } 
      } 
      boolean bool2 = false;
      continue;
      if (SYNTHETIC_LOCAL_VARIABLE_3 != null) {
        if (isGranted(SYNTHETIC_LOCAL_VARIABLE_9.getPermissionType())) {
          if (SYNTHETIC_LOCAL_VARIABLE_2 != null) {
            paramLinkedHashMap.put(Integer.valueOf(SYNTHETIC_LOCAL_VARIABLE_9.getPermissionType()), "ok");
          } else {
            paramLinkedHashMap.put(Integer.valueOf(SYNTHETIC_LOCAL_VARIABLE_9.getPermissionType()), "system auth deny");
          } 
        } else {
          paramLinkedHashMap.put(Integer.valueOf(SYNTHETIC_LOCAL_VARIABLE_9.getPermissionType()), "auth deny");
        } 
        iterator.remove();
      } 
    } 
  }
  
  public static List<Integer> getPermissionTypeList() {
    return permissionTypeList;
  }
  
  private static SharedPreferences getSharedPreference() {
    String str = (AppbrandApplication.getInst().getAppInfo()).appId;
    return getSharedPreference((Context)AppbrandContext.getInst().getApplicationContext(), str);
  }
  
  private static SharedPreferences getSharedPreference(Context paramContext, String paramString) {
    return KVUtil.getSharedPreferences(paramContext, getSpName(paramString));
  }
  
  private static String getSpName(String paramString) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(getSpNamePrefix());
    stringBuilder.append(paramString);
    return stringBuilder.toString();
  }
  
  public static String getSpNamePrefix() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(HostDependManager.getInst().getSpPrefixPath());
    stringBuilder.append("permission_");
    return stringBuilder.toString();
  }
  
  public static boolean hasRequestPermission(int paramInt) {
    SharedPreferences sharedPreferences = getSharedPreference();
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(SP_PERMISSION_KEY_PREFIX);
    stringBuilder.append(paramInt);
    return sharedPreferences.contains(stringBuilder.toString());
  }
  
  public static boolean hasRequestPermission(Context paramContext, String paramString, int paramInt) {
    SharedPreferences sharedPreferences = getSharedPreference(paramContext, paramString);
    StringBuilder stringBuilder = new StringBuilder("permission");
    stringBuilder.append(paramInt);
    return sharedPreferences.contains(stringBuilder.toString());
  }
  
  private static boolean isAuthPass(BrandPermission paramBrandPermission) {
    AppInfoEntity appInfoEntity = AppbrandApplicationImpl.getInst().getAppInfo();
    if (appInfoEntity == null)
      return false; 
    int i = appInfoEntity.authPass;
    return ((paramBrandPermission.authPassId & i) > 0);
  }
  
  public static boolean isGranted(int paramInt) {
    return isGranted(paramInt, false);
  }
  
  public static boolean isGranted(int paramInt, boolean paramBoolean) {
    if (!hasRequestPermission(paramInt) && isAuthPass(BrandPermission.makeFromAppbrandPermissionType(paramInt))) {
      setPermission(paramInt, true);
      return true;
    } 
    SharedPreferences sharedPreferences = getSharedPreference();
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(SP_PERMISSION_KEY_PREFIX);
    stringBuilder.append(paramInt);
    return sharedPreferences.getBoolean(stringBuilder.toString(), paramBoolean);
  }
  
  public static boolean isGranted(Context paramContext, String paramString, int paramInt) {
    return isGranted(paramContext, paramString, paramInt, false);
  }
  
  public static boolean isGranted(Context paramContext, String paramString, int paramInt, boolean paramBoolean) {
    SharedPreferences sharedPreferences = getSharedPreference(paramContext, paramString);
    StringBuilder stringBuilder = new StringBuilder("permission");
    stringBuilder.append(paramInt);
    return sharedPreferences.getBoolean(stringBuilder.toString(), paramBoolean);
  }
  
  public static String makePermissionErrorMsg(String paramString) {
    try {
      JSONObject jSONObject = new JSONObject();
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(paramString);
      stringBuilder.append(":fail auth deny");
      jSONObject.put("errMsg", stringBuilder.toString());
      return jSONObject.toString();
    } catch (Exception exception) {
      AppBrandLogger.stacktrace(6, TAG, exception.getStackTrace());
      return "";
    } 
  }
  
  public static void requestPermissions(Activity paramActivity, String paramString, Set<BrandPermission> paramSet, final LinkedHashMap<Integer, String> authorizeResultMap, IPermissionsRequestCallback paramIPermissionsRequestCallback, final HashMap<String, String> extraData) {
    if (paramActivity == null) {
      AppBrandMonitor.reportError("mp_special_error", "Activity is null", StackUtil.createLog((Object[])(new Throwable()).getStackTrace()));
      null = paramSet.iterator();
      while (null.hasNext())
        authorizeResultMap.put(Integer.valueOf(((BrandPermission)null.next()).permissionType), "system auth deny"); 
      if (paramSet.size() > 1)
        paramIPermissionsRequestCallback.onDenied(authorizeResultMap); 
      return;
    } 
    if (paramSet == null || paramSet.size() <= 0) {
      AppBrandLogger.e(TAG, new Object[] { "showPermissionsDialog", "permissions is empty!" });
      return;
    } 
    final Set<BrandPermission> filterNeedAuthPermissions = HostDependManager.getInst().filterNeedRequestPermission(paramString, paramSet);
    if (set == null || set.isEmpty()) {
      paramIPermissionsRequestCallback.onGranted(createOkResultMap(paramSet));
      return;
    } 
    if (set.contains(BrandPermission.USER_INFO) && HostOptionPermissionDependImpl.isNeedSendPermissionGrantRequest())
      HostDependManager.getInst().savePermissionGrant(11, HostOptionPermissionDependImpl.getSendPermissionGrantState()); 
    filterAuthorizedPermissions(set, authorizeResultMap);
    if (set.size() <= 0) {
      if (authorizeResultMap.keySet().size() > 0) {
        if (isGranted(((Integer)authorizeResultMap.keySet().iterator().next()).intValue())) {
          paramIPermissionsRequestCallback.onGranted(authorizeResultMap);
          return;
        } 
        paramIPermissionsRequestCallback.onDenied(authorizeResultMap);
      } 
      return;
    } 
    if (set.contains(BrandPermission.USER_INFO) && !(new UserInfoManager.UserInfo(HostProcessBridge.getUserInfo())).isLogin) {
      authorizeResultMap.put(Integer.valueOf(11), "platform auth deny");
      paramIPermissionsRequestCallback.onDenied(authorizeResultMap);
      return;
    } 
    synchronized (mAuthLock) {
      int i;
      if (set.size() > 1) {
        i = -1;
      } else if (set.size() == 1) {
        i = ((BrandPermission)set.iterator().next()).permissionType;
      } else {
        AppBrandLogger.e(TAG, new Object[] { "needAuthPermissions is empty" });
        return;
      } 
      List<IPermissionsRequestCallback> list = (List)mPermissionResultCallbackArray.get(i);
      if (list != null && list.size() > 0) {
        list.add(paramIPermissionsRequestCallback);
        return;
      } 
      list = new ArrayList<IPermissionsRequestCallback>();
      list.add(paramIPermissionsRequestCallback);
      mPermissionResultCallbackArray.put(i, list);
      (new Handler(Looper.getMainLooper())).post(new Runnable() {
            public final void run() {
              final int finalPermissionKey;
              String str;
              HashSet<Integer> hashSet = new HashSet();
              Iterator<BrandPermissionUtils.BrandPermission> iterator = filterNeedAuthPermissions.iterator();
              while (iterator.hasNext())
                hashSet.add(Integer.valueOf(((BrandPermissionUtils.BrandPermission)iterator.next()).permissionType)); 
              if (filterNeedAuthPermissions.size() > 1) {
                i = -1;
              } else if (filterNeedAuthPermissions.size() == 1) {
                i = ((BrandPermissionUtils.BrandPermission)filterNeedAuthPermissions.iterator().next()).permissionType;
              } else {
                AppBrandLogger.e(BrandPermissionUtils.TAG, new Object[] { "needAuthPermissions is empty" });
                return;
              } 
              IPermissionsRequestCallback iPermissionsRequestCallback = new IPermissionsRequestCallback() {
                  private void notifyResult(LinkedHashMap<Integer, String> param2LinkedHashMap, boolean param2Boolean, int param2Int) {
                    synchronized (BrandPermissionUtils.mAuthLock) {
                      List list = (List)BrandPermissionUtils.mPermissionResultCallbackArray.get(param2Int);
                      if (param2Boolean) {
                        Iterator<IPermissionsRequestCallback> iterator = list.iterator();
                        while (iterator.hasNext())
                          ((IPermissionsRequestCallback)iterator.next()).onGranted(param2LinkedHashMap); 
                      } else {
                        Iterator<IPermissionsRequestCallback> iterator = list.iterator();
                        while (iterator.hasNext())
                          ((IPermissionsRequestCallback)iterator.next()).onDenied(param2LinkedHashMap); 
                      } 
                      list.clear();
                      return;
                    } 
                  }
                  
                  public void onDenied(LinkedHashMap<Integer, String> param2LinkedHashMap) {
                    BrandPermissionUtils.setPermissions(param2LinkedHashMap);
                    notifyResult(param2LinkedHashMap, false, finalPermissionKey);
                    if (AppbrandContext.getInst().isGame())
                      BaseNavBarUtils.hideNavigation(activity); 
                    HostDependManager.getInst().syncPermissionToService();
                  }
                  
                  public void onGranted(LinkedHashMap<Integer, String> param2LinkedHashMap) {
                    BrandPermissionUtils.setPermissions(param2LinkedHashMap);
                    notifyResult(param2LinkedHashMap, true, finalPermissionKey);
                    if (AppbrandContext.getInst().isGame())
                      BaseNavBarUtils.hideNavigation(activity); 
                    HostDependManager.getInst().syncPermissionToService();
                  }
                };
              if (filterNeedAuthPermissions.contains(BrandPermissionUtils.BrandPermission.SUBSCRIBE_MESSAGE)) {
                SubscribeMsgShowRecordUtil.recordTemplateMsgAuthShow((String)extraData.get("templateId"));
                for (BrandPermissionUtils.BrandPermission brandPermission : filterNeedAuthPermissions) {
                  if (brandPermission.permissionType == 20) {
                    brandPermission.msg = (String)extraData.get("title");
                    brandPermission.subtitle = (String)extraData.get("subtitle");
                    break;
                  } 
                } 
              } 
              Dialog dialog1 = HostDependManager.getInst().showPermissionsDialog(activity, hashSet, authorizeResultMap, iPermissionsRequestCallback, extraData);
              Dialog dialog2 = dialog1;
              if (dialog1 == null)
                dialog2 = (new HostOptionUiDependImpl()).showPermissionsDialog(activity, hashSet, authorizeResultMap, iPermissionsRequestCallback, extraData); 
              dialog1 = null;
              if (filterNeedAuthPermissions.size() > 1) {
                str = "multiple";
              } else if (filterNeedAuthPermissions.iterator().hasNext()) {
                str = BrandPermissionUtils.appbrandpermissionTypeToEventParamVal(((BrandPermissionUtils.BrandPermission)filterNeedAuthPermissions.iterator().next()).permissionType);
              } 
              Event.builder("mp_auth_alert_show").kv("auth_type", str).flush();
              dialog2.show();
            }
          });
      return;
    } 
  }
  
  public static void setPermission(int paramInt, boolean paramBoolean) {
    if (DebugUtil.debug() && HostDependManager.getInst().isEnablePermissionSaveTest()) {
      getSharedPreference().edit().clear().commit();
      return;
    } 
    SharedPreferences.Editor editor = getSharedPreference().edit();
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(SP_PERMISSION_KEY_PREFIX);
    stringBuilder.append(paramInt);
    editor.putBoolean(stringBuilder.toString(), paramBoolean).apply();
    HostDependManager.getInst().setPermissionTime(paramInt);
    SecrecyManager.inst().secrecyPermissionChanged(paramInt, paramBoolean);
  }
  
  public static void setPermissions(LinkedHashMap<Integer, String> paramLinkedHashMap) {
    if (DebugUtil.debug() && HostDependManager.getInst().isEnablePermissionSaveTest()) {
      getSharedPreference().edit().clear().commit();
      return;
    } 
    SharedPreferences.Editor editor = getSharedPreference().edit();
    for (Map.Entry<Integer, String> entry : paramLinkedHashMap.entrySet()) {
      boolean bool = ((String)entry.getValue()).contentEquals("ok");
      int i = ((Integer)entry.getKey()).intValue();
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(SP_PERMISSION_KEY_PREFIX);
      stringBuilder.append(i);
      editor.putBoolean(stringBuilder.toString(), bool);
      HostDependManager.getInst().setPermissionTime(i);
    } 
    editor.apply();
  }
  
  public static String systemPermissionErrorMsg(String paramString) {
    try {
      JSONObject jSONObject = new JSONObject();
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(paramString);
      stringBuilder.append(":fail system auth deny");
      jSONObject.put("errMsg", stringBuilder.toString());
      return jSONObject.toString();
    } catch (Exception exception) {
      AppBrandLogger.stacktrace(6, TAG, exception.getStackTrace());
      return "";
    } 
  }
  
  public static class BrandPermission {
    public static final BrandPermission ADDRESS;
    
    public static final BrandPermission ALBUM;
    
    public static final BrandPermission CAMERA;
    
    public static final BrandPermission FACIAL_VERIFY;
    
    public static final BrandPermission LOCATION = new BrandPermission(32, 12, 2097741924, "scope.userLocation", new String[] { "android.permission.ACCESS_COARSE_LOCATION" });
    
    public static final BrandPermission PHONE_NUMBER;
    
    public static final BrandPermission RECORD_AUDIO = new BrandPermission(8, 13, 2097741831, "scope.record", new String[] { "android.permission.RECORD_AUDIO" });
    
    public static final BrandPermission SCREEN_RECORD;
    
    public static final BrandPermission SUBSCRIBE_MESSAGE;
    
    public static final BrandPermission USER_INFO = new BrandPermission(4, 11, 2097742047, "scope.userInfo", null);
    
    public static List<BrandPermission> sSeparatePermissionList;
    
    public static List<BrandPermission> sStrictPermissionList;
    
    public static List<BrandPermission> sUserDefinablePermissionList;
    
    public final int authPassId;
    
    public String msg;
    
    public int permissionType;
    
    private String scope;
    
    private int stringResid;
    
    public String subtitle;
    
    private String[] sysPermissions;
    
    static {
      CAMERA = new BrandPermission(1, 14, 2097741826, "scope.camera", new String[] { "android.permission.CAMERA" });
      ALBUM = new BrandPermission(2, 17, 2097741855, "scope.album", new String[] { "android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE" });
      ADDRESS = new BrandPermission(16, 15, 2097741854, "scope.address", null);
      PHONE_NUMBER = new BrandPermission(0, 16, 2097741987, null, null);
      SCREEN_RECORD = new BrandPermission(64, 18, 2097742014, "scope.screenRecord", null);
      FACIAL_VERIFY = new BrandPermission(0, 19, 2097741888, null, null);
      SUBSCRIBE_MESSAGE = new BrandPermission(0, 20, 2097742036, null, null);
      sUserDefinablePermissionList = new ArrayList<BrandPermission>();
      sStrictPermissionList = new ArrayList<BrandPermission>();
      sSeparatePermissionList = new ArrayList<BrandPermission>();
      sUserDefinablePermissionList.add(USER_INFO);
      sUserDefinablePermissionList.add(LOCATION);
      sUserDefinablePermissionList.add(CAMERA);
      sUserDefinablePermissionList.add(ALBUM);
      sUserDefinablePermissionList.add(RECORD_AUDIO);
      sUserDefinablePermissionList.add(ADDRESS);
      sStrictPermissionList.add(PHONE_NUMBER);
      sStrictPermissionList.add(FACIAL_VERIFY);
      sStrictPermissionList.add(SUBSCRIBE_MESSAGE);
      sSeparatePermissionList.add(USER_INFO);
      sSeparatePermissionList.add(ADDRESS);
      sSeparatePermissionList.add(PHONE_NUMBER);
      sSeparatePermissionList.add(FACIAL_VERIFY);
      sSeparatePermissionList.add(SUBSCRIBE_MESSAGE);
    }
    
    public BrandPermission(int param1Int1, int param1Int2, int param1Int3, String param1String1, String param1String2, String param1String3, String[] param1ArrayOfString) {
      this.authPassId = param1Int1;
      this.permissionType = param1Int2;
      this.stringResid = param1Int3;
      this.msg = param1String1;
      this.subtitle = param1String2;
      this.scope = param1String3;
      this.sysPermissions = param1ArrayOfString;
      if (TextUtils.isEmpty(param1String1)) {
        StringBuilder stringBuilder;
        switch (param1Int2) {
          case 17:
            stringBuilder = new StringBuilder("- ");
            stringBuilder.append(UIUtils.getString(2097741841));
            this.msg = stringBuilder.toString();
            break;
          case 16:
            stringBuilder = new StringBuilder("- ");
            stringBuilder.append(UIUtils.getString(2097741846));
            this.msg = stringBuilder.toString();
            break;
          case 15:
            stringBuilder = new StringBuilder("- ");
            stringBuilder.append(UIUtils.getString(2097741848));
            this.msg = stringBuilder.toString();
            break;
          case 14:
            stringBuilder = new StringBuilder("- ");
            stringBuilder.append(UIUtils.getString(2097741842));
            this.msg = stringBuilder.toString();
            break;
          case 13:
            stringBuilder = new StringBuilder("- ");
            stringBuilder.append(UIUtils.getString(2097741844));
            this.msg = stringBuilder.toString();
            break;
          case 12:
            stringBuilder = new StringBuilder("- ");
            stringBuilder.append(UIUtils.getString(2097741843));
            this.msg = stringBuilder.toString();
            break;
          case 11:
            stringBuilder = new StringBuilder("- ");
            stringBuilder.append(UIUtils.getString(2097741845));
            this.msg = stringBuilder.toString();
            break;
        } 
      } 
      if (TextUtils.isEmpty(param1String2)) {
        if (param1Int2 != 17) {
          if (param1Int2 != 20) {
            Application application;
            switch (param1Int2) {
              default:
                return;
              case 15:
                application = AppbrandContext.getInst().getApplicationContext();
                if (application != null) {
                  StringBuilder stringBuilder = new StringBuilder();
                  stringBuilder.append(UIUtils.getString(2097741852));
                  stringBuilder.append("“");
                  stringBuilder.append(AppbrandUtil.getApplicationName((Context)application));
                  stringBuilder.append("”");
                  stringBuilder.append(UIUtils.getString(2097741853));
                  this.subtitle = stringBuilder.toString();
                } 
                return;
              case 14:
                this.subtitle = UIUtils.getString(2097741869);
                return;
              case 13:
                this.subtitle = UIUtils.getString(2097741990);
                return;
              case 12:
                break;
            } 
            this.subtitle = UIUtils.getString(2097742048);
            return;
          } 
          this.subtitle = UIUtils.getString(2097742038);
          return;
        } 
        this.subtitle = UIUtils.getString(2097741856);
      } 
    }
    
    public BrandPermission(int param1Int1, int param1Int2, int param1Int3, String param1String1, String param1String2, String[] param1ArrayOfString) {
      this(param1Int1, param1Int2, param1Int3, getMsg(param1Int2), param1String1, param1String2, param1ArrayOfString);
    }
    
    public BrandPermission(int param1Int1, int param1Int2, int param1Int3, String param1String, String[] param1ArrayOfString) {
      this(param1Int1, param1Int2, param1Int3, getSubtitle(param1Int2), param1String, param1ArrayOfString);
    }
    
    public static String getMsg(int param1Int) {
      String str2 = CharacterUtils.empty();
      c c = HostDependManager.getInst().getPermissionCustomDialogMsgEntity();
      String str1 = str2;
      if (c != null) {
        switch (param1Int) {
          default:
            return str2;
          case 19:
            return c.h;
          case 17:
            return c.e;
          case 16:
            return c.g;
          case 15:
            return c.f;
          case 14:
            return c.d;
          case 13:
            return c.c;
          case 12:
            return c.b;
          case 11:
            break;
        } 
        str1 = c.a;
      } 
      return str1;
    }
    
    public static String getSubtitle(int param1Int) {
      String str2 = CharacterUtils.empty();
      AppConfig appConfig = AppbrandApplicationImpl.getInst().getAppConfig();
      String str1 = str2;
      if (appConfig != null) {
        str1 = str2;
        if (appConfig.getAuthorizeDescription() != null) {
          switch (param1Int) {
            default:
              return str2;
            case 17:
              return appConfig.getAuthorizeDescription().getAlbum();
            case 15:
              return appConfig.getAuthorizeDescription().getAddress();
            case 14:
              return appConfig.getAuthorizeDescription().getCamera();
            case 13:
              return appConfig.getAuthorizeDescription().getRecord();
            case 12:
              break;
          } 
          str1 = appConfig.getAuthorizeDescription().getUserLocation();
        } 
      } 
      return str1;
    }
    
    public static List<BrandPermission> getUserDefinablePermissionList() {
      return sUserDefinablePermissionList;
    }
    
    public static boolean isStrictPermission(BrandPermission param1BrandPermission) {
      return sStrictPermissionList.contains(param1BrandPermission);
    }
    
    public static BrandPermission makeFromAppbrandPermissionType(int param1Int) {
      switch (param1Int) {
        default:
          return null;
        case 20:
          return SUBSCRIBE_MESSAGE;
        case 19:
          return FACIAL_VERIFY;
        case 18:
          return SCREEN_RECORD;
        case 17:
          return ALBUM;
        case 16:
          return PHONE_NUMBER;
        case 15:
          return ADDRESS;
        case 14:
          return CAMERA;
        case 13:
          return RECORD_AUDIO;
        case 12:
          return LOCATION;
        case 11:
          break;
      } 
      return USER_INFO;
    }
    
    public static BrandPermission makeFromScope(String param1String) {
      byte b;
      switch (param1String.hashCode()) {
        default:
          b = -1;
          break;
        case 1927763546:
          if (param1String.equals("scope.address")) {
            b = 0;
            break;
          } 
        case 1244699221:
          if (param1String.equals("scope.album")) {
            b = 5;
            break;
          } 
        case 986629481:
          if (param1String.equals("scope.writePhotosAlbum")) {
            b = 6;
            break;
          } 
        case 786754871:
          if (param1String.equals("scope.screenRecord")) {
            b = 7;
            break;
          } 
        case 583039347:
          if (param1String.equals("scope.userInfo")) {
            b = 1;
            break;
          } 
        case 411225387:
          if (param1String.equals("scope.record")) {
            b = 3;
            break;
          } 
        case -21617665:
          if (param1String.equals("scope.camera")) {
            b = 4;
            break;
          } 
        case -653473286:
          if (param1String.equals("scope.userLocation")) {
            b = 2;
            break;
          } 
      } 
      switch (b) {
        default:
          return null;
        case 7:
          return SCREEN_RECORD;
        case 5:
        case 6:
          return ALBUM;
        case 4:
          return CAMERA;
        case 3:
          return RECORD_AUDIO;
        case 2:
          return LOCATION;
        case 1:
          return USER_INFO;
        case 0:
          break;
      } 
      return ADDRESS;
    }
    
    public String getMsg() {
      return this.msg;
    }
    
    public String getName() {
      return UIUtils.getString(this.stringResid);
    }
    
    public int getPermissionType() {
      return this.permissionType;
    }
    
    public String getScope() {
      return this.scope;
    }
    
    public String getSubtitle() {
      return this.subtitle;
    }
    
    public String[] getSysPermissions() {
      return this.sysPermissions;
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\permission\BrandPermissionUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */