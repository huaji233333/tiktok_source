package com.tt.miniapp.permission;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import com.tt.miniapp.util.DevicesUtil;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.NativeModule;
import com.tt.miniapphost.language.LanguageChangeListener;
import com.tt.miniapphost.language.LocaleManager;
import com.tt.miniapphost.util.AppbrandUtil;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PermissionsManager implements LanguageChangeListener {
  private static DialogBuilderProvider mDialogBuilderProvider;
  
  private static PermissionsManager mInstance;
  
  private final List<SoftReference<PermissionsResultAction>> mPendingActions = new ArrayList<SoftReference<PermissionsResultAction>>(1);
  
  private final List<PermissionsResultAction> mPendingActionsForGc = new ArrayList<PermissionsResultAction>(1);
  
  private final Set<String> mPendingRequests = new HashSet<String>(1);
  
  private final Set<String> mPermissions = new HashSet<String>(1);
  
  private final List<SoftReference<RequestPermissionResultListener>> mRequestPermissionResultListeners = new ArrayList<SoftReference<RequestPermissionResultListener>>();
  
  private Map<String, Integer> sDescriptMap = new HashMap<String, Integer>();
  
  private PermissionsManager() {
    initializePermissionsMap();
    initPermissionDescript();
    LocaleManager.getInst().registerLangChangeListener(this);
  }
  
  private void addPendingAction(Set<String> paramSet, PermissionsResultAction paramPermissionsResultAction) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_2
    //   3: ifnonnull -> 9
    //   6: aload_0
    //   7: monitorexit
    //   8: return
    //   9: aload_2
    //   10: aload_1
    //   11: invokevirtual registerPermissions : (Ljava/util/Set;)V
    //   14: aload_0
    //   15: getfield mPendingActionsForGc : Ljava/util/List;
    //   18: aload_2
    //   19: invokeinterface add : (Ljava/lang/Object;)Z
    //   24: pop
    //   25: aload_0
    //   26: getfield mPendingActions : Ljava/util/List;
    //   29: new java/lang/ref/SoftReference
    //   32: dup
    //   33: aload_2
    //   34: invokespecial <init> : (Ljava/lang/Object;)V
    //   37: invokeinterface add : (Ljava/lang/Object;)Z
    //   42: pop
    //   43: aload_0
    //   44: monitorexit
    //   45: return
    //   46: astore_1
    //   47: aload_0
    //   48: monitorexit
    //   49: aload_1
    //   50: athrow
    // Exception table:
    //   from	to	target	type
    //   9	43	46	finally
  }
  
  private void doPermissionWorkBeforeAndroidM(Activity paramActivity, Set<String> paramSet, PermissionsResultAction paramPermissionsResultAction) {
    Iterator<String> iterator = paramSet.iterator();
    while (true) {
      if (iterator.hasNext()) {
        String str = iterator.next();
        if (paramPermissionsResultAction != null)
          try {
            boolean bool;
            if (!this.mPermissions.contains(str)) {
              bool = paramPermissionsResultAction.onResult(str, Permissions.NOT_FOUND);
            } else if (PermissionActivityCompat.checkSelfPermission((Context)paramActivity, str) != 0) {
              bool = paramPermissionsResultAction.onResult(str, Permissions.DENIED);
            } else {
              bool = paramPermissionsResultAction.onResult(str, Permissions.GRANTED);
            } 
            if (bool) {
              removePendingAction(paramPermissionsResultAction);
              return;
            } 
          } finally {} 
        continue;
      } 
      removePendingAction(paramPermissionsResultAction);
      return;
    } 
  }
  
  public static PermissionsManager getInstance() {
    if (mInstance == null)
      mInstance = new PermissionsManager(); 
    return mInstance;
  }
  
  private String[] getManifestPermissions(Activity paramActivity) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aconst_null
    //   3: astore #4
    //   5: new java/util/ArrayList
    //   8: dup
    //   9: iconst_1
    //   10: invokespecial <init> : (I)V
    //   13: astore #5
    //   15: aload_1
    //   16: invokevirtual getPackageManager : ()Landroid/content/pm/PackageManager;
    //   19: aload_1
    //   20: invokevirtual getPackageName : ()Ljava/lang/String;
    //   23: sipush #4096
    //   26: invokevirtual getPackageInfo : (Ljava/lang/String;I)Landroid/content/pm/PackageInfo;
    //   29: astore_1
    //   30: goto -> 33
    //   33: aload_1
    //   34: ifnull -> 74
    //   37: aload_1
    //   38: getfield requestedPermissions : [Ljava/lang/String;
    //   41: astore_1
    //   42: aload_1
    //   43: ifnull -> 74
    //   46: aload_1
    //   47: arraylength
    //   48: istore_3
    //   49: iconst_0
    //   50: istore_2
    //   51: iload_2
    //   52: iload_3
    //   53: if_icmpge -> 74
    //   56: aload #5
    //   58: aload_1
    //   59: iload_2
    //   60: aaload
    //   61: invokeinterface add : (Ljava/lang/Object;)Z
    //   66: pop
    //   67: iload_2
    //   68: iconst_1
    //   69: iadd
    //   70: istore_2
    //   71: goto -> 51
    //   74: aload #5
    //   76: aload #5
    //   78: invokeinterface size : ()I
    //   83: anewarray java/lang/String
    //   86: invokeinterface toArray : ([Ljava/lang/Object;)[Ljava/lang/Object;
    //   91: checkcast [Ljava/lang/String;
    //   94: astore_1
    //   95: aload_0
    //   96: monitorexit
    //   97: aload_1
    //   98: areturn
    //   99: astore_1
    //   100: aload_0
    //   101: monitorexit
    //   102: goto -> 107
    //   105: aload_1
    //   106: athrow
    //   107: goto -> 105
    //   110: astore_1
    //   111: aload #4
    //   113: astore_1
    //   114: goto -> 33
    // Exception table:
    //   from	to	target	type
    //   5	15	99	finally
    //   15	30	110	android/content/pm/PackageManager$NameNotFoundException
    //   15	30	99	finally
    //   37	42	99	finally
    //   46	49	99	finally
    //   56	67	99	finally
    //   74	95	99	finally
  }
  
  private String getPermissionTip(Context paramContext, List<String> paramList) {
    if (paramList.isEmpty())
      return ""; 
    if (paramList.contains("android.permission.ACCESS_COARSE_LOCATION") && paramList.contains("android.permission.ACCESS_FINE_LOCATION"))
      paramList.remove("android.permission.ACCESS_FINE_LOCATION"); 
    int j = paramList.size();
    int i = 2;
    if (j > 1) {
      StringBuilder stringBuilder = new StringBuilder();
      for (String str1 : paramList) {
        i = ((Integer)this.sDescriptMap.get(str1)).intValue();
        if (i > 0) {
          stringBuilder.append(paramContext.getString(i));
          stringBuilder.append("、");
        } 
      } 
      i = stringBuilder.length() - 1;
      if (i >= 0)
        stringBuilder.deleteCharAt(i); 
      return paramContext.getString(2097741981, new Object[] { AppbrandContext.getInst().getInitParams().getHostStr(101, AppbrandUtil.getApplicationName((Context)AppbrandContext.getInst().getApplicationContext())), stringBuilder.toString() });
    } 
    String str = paramList.get(0);
    try {
      switch (str.hashCode()) {
        case 1977429404:
          if (str.equals("android.permission.READ_CONTACTS")) {
            i = 3;
            break;
          } 
        case 1831139720:
          if (str.equals("android.permission.RECORD_AUDIO")) {
            i = 5;
            break;
          } 
        case 1365911975:
          if (str.equals("android.permission.WRITE_EXTERNAL_STORAGE")) {
            i = 6;
            break;
          } 
        case 463403621:
          if (str.equals("android.permission.CAMERA")) {
            i = 4;
            break;
          } 
        case -5573545:
          if (str.equals("android.permission.READ_PHONE_STATE")) {
            i = 0;
            break;
          } 
        case -63024214:
          if (str.equals("android.permission.ACCESS_COARSE_LOCATION")) {
            i = 1;
            break;
          } 
        case -406040016:
          if (str.equals("android.permission.READ_EXTERNAL_STORAGE")) {
            i = 7;
            break;
          } 
        case -1888586689:
          if (str.equals("android.permission.ACCESS_FINE_LOCATION"))
            break; 
        default:
          i = -1;
          break;
      } 
    } catch (Exception exception) {
      AppBrandLogger.stacktrace(6, "PermissionsManager", exception.getStackTrace());
      return "";
    } 
    switch (i) {
      case 6:
      case 7:
        return exception.getString(2097741985, new Object[] { AppbrandContext.getInst().getInitParams().getHostStr(101, AppbrandUtil.getApplicationName((Context)AppbrandContext.getInst().getApplicationContext())) });
      case 5:
        return exception.getString(2097741980, new Object[] { AppbrandContext.getInst().getInitParams().getHostStr(101, AppbrandUtil.getApplicationName((Context)AppbrandContext.getInst().getApplicationContext())) });
      case 4:
        return exception.getString(2097741974, new Object[] { AppbrandContext.getInst().getInitParams().getHostStr(101, AppbrandUtil.getApplicationName((Context)AppbrandContext.getInst().getApplicationContext())) });
      case 3:
        return exception.getString(2097741976, new Object[] { AppbrandContext.getInst().getInitParams().getHostStr(101, AppbrandUtil.getApplicationName((Context)AppbrandContext.getInst().getApplicationContext())) });
      case 1:
      case 2:
        return exception.getString(2097741979, new Object[] { AppbrandContext.getInst().getInitParams().getHostStr(101, AppbrandUtil.getApplicationName((Context)AppbrandContext.getInst().getApplicationContext())) });
      case 0:
        return exception.getString(2097741977, new Object[] { AppbrandContext.getInst().getInitParams().getHostStr(101, AppbrandUtil.getApplicationName((Context)AppbrandContext.getInst().getApplicationContext())) });
    } 
    return "";
  }
  
  private List<String> getPermissionsListToRequest(Activity paramActivity, Set<String> paramSet, PermissionsResultAction paramPermissionsResultAction) {
    ArrayList<String> arrayList = new ArrayList(paramSet.size());
    Iterator<String> iterator = paramSet.iterator();
    boolean bool = false;
    while (iterator.hasNext()) {
      String str = iterator.next();
      if (!this.mPermissions.contains(str)) {
        if (paramPermissionsResultAction != null)
          paramPermissionsResultAction.onResult(str, Permissions.NOT_FOUND); 
        continue;
      } 
      if (!hasPermission((Context)paramActivity, str)) {
        if (!this.mPendingRequests.contains(str)) {
          arrayList.add(str);
          continue;
        } 
        bool = true;
        continue;
      } 
      if (paramPermissionsResultAction != null)
        paramPermissionsResultAction.onResult(str, Permissions.GRANTED); 
    } 
    if (arrayList.isEmpty() && !bool)
      removePendingAction(paramPermissionsResultAction); 
    return arrayList;
  }
  
  private void handleNeverShowPermissionDialog(Activity paramActivity, String[] paramArrayOfString1, String[] paramArrayOfString2, int[] paramArrayOfint, String paramString) {
    if (paramActivity != null && paramArrayOfString1 != null) {
      if (paramArrayOfString1.length <= 0)
        return; 
      try {
        return;
      } finally {
        paramActivity = null;
      } 
    } 
  }
  
  private void initPermissionDescript() {
    Map<String, Integer> map = this.sDescriptMap;
    Integer integer = Integer.valueOf(2097741829);
    map.put("android.permission.ACCESS_COARSE_LOCATION", integer);
    this.sDescriptMap.put("android.permission.ACCESS_FINE_LOCATION", integer);
    this.sDescriptMap.put("android.permission.READ_CONTACTS", Integer.valueOf(2097741827));
    this.sDescriptMap.put("android.permission.CAMERA", Integer.valueOf(2097741826));
    this.sDescriptMap.put("android.permission.RECORD_AUDIO", Integer.valueOf(2097741831));
    this.sDescriptMap.put("android.permission.READ_PHONE_STATE", Integer.valueOf(2097741830));
    map = this.sDescriptMap;
    integer = Integer.valueOf(2097741828);
    map.put("android.permission.WRITE_EXTERNAL_STORAGE", integer);
    if (Build.VERSION.SDK_INT >= 16)
      this.sDescriptMap.put("android.permission.READ_EXTERNAL_STORAGE", integer); 
  }
  
  private void initializePermissionsMap() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: ldc_w android/Manifest$permission
    //   5: invokevirtual getFields : ()[Ljava/lang/reflect/Field;
    //   8: astore #4
    //   10: aload #4
    //   12: arraylength
    //   13: istore_2
    //   14: iconst_0
    //   15: istore_1
    //   16: iload_1
    //   17: iload_2
    //   18: if_icmpge -> 59
    //   21: aload #4
    //   23: iload_1
    //   24: aaload
    //   25: astore_3
    //   26: aload_3
    //   27: ldc ''
    //   29: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   32: checkcast java/lang/String
    //   35: astore_3
    //   36: goto -> 41
    //   39: aconst_null
    //   40: astore_3
    //   41: aload_0
    //   42: getfield mPermissions : Ljava/util/Set;
    //   45: aload_3
    //   46: invokeinterface add : (Ljava/lang/Object;)Z
    //   51: pop
    //   52: iload_1
    //   53: iconst_1
    //   54: iadd
    //   55: istore_1
    //   56: goto -> 16
    //   59: aload_0
    //   60: monitorexit
    //   61: return
    //   62: astore_3
    //   63: aload_0
    //   64: monitorexit
    //   65: goto -> 70
    //   68: aload_3
    //   69: athrow
    //   70: goto -> 68
    //   73: astore_3
    //   74: goto -> 39
    // Exception table:
    //   from	to	target	type
    //   2	14	62	finally
    //   26	36	73	java/lang/IllegalAccessException
    //   26	36	62	finally
    //   41	52	62	finally
  }
  
  private void removePendingAction(PermissionsResultAction paramPermissionsResultAction) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield mPendingActions : Ljava/util/List;
    //   6: invokeinterface iterator : ()Ljava/util/Iterator;
    //   11: astore_2
    //   12: aload_2
    //   13: invokeinterface hasNext : ()Z
    //   18: ifeq -> 55
    //   21: aload_2
    //   22: invokeinterface next : ()Ljava/lang/Object;
    //   27: checkcast java/lang/ref/SoftReference
    //   30: astore_3
    //   31: aload_3
    //   32: invokevirtual get : ()Ljava/lang/Object;
    //   35: aload_1
    //   36: if_acmpeq -> 46
    //   39: aload_3
    //   40: invokevirtual get : ()Ljava/lang/Object;
    //   43: ifnonnull -> 12
    //   46: aload_2
    //   47: invokeinterface remove : ()V
    //   52: goto -> 12
    //   55: aload_0
    //   56: getfield mPendingActionsForGc : Ljava/util/List;
    //   59: invokeinterface iterator : ()Ljava/util/Iterator;
    //   64: astore_2
    //   65: aload_2
    //   66: invokeinterface hasNext : ()Z
    //   71: ifeq -> 96
    //   74: aload_2
    //   75: invokeinterface next : ()Ljava/lang/Object;
    //   80: checkcast com/tt/miniapp/permission/PermissionsResultAction
    //   83: aload_1
    //   84: if_acmpne -> 65
    //   87: aload_2
    //   88: invokeinterface remove : ()V
    //   93: goto -> 65
    //   96: aload_0
    //   97: monitorexit
    //   98: return
    //   99: astore_1
    //   100: aload_0
    //   101: monitorexit
    //   102: goto -> 107
    //   105: aload_1
    //   106: athrow
    //   107: goto -> 105
    // Exception table:
    //   from	to	target	type
    //   2	12	99	finally
    //   12	46	99	finally
    //   46	52	99	finally
    //   55	65	99	finally
    //   65	93	99	finally
  }
  
  public static void setDialogBuilderProvider(DialogBuilderProvider paramDialogBuilderProvider) {
    mDialogBuilderProvider = paramDialogBuilderProvider;
  }
  
  public void addReauestPermissionResultListener(SoftReference<RequestPermissionResultListener> paramSoftReference) {
    this.mRequestPermissionResultListeners.add(paramSoftReference);
  }
  
  public void forceFlushPendingRequest(Activity paramActivity, boolean paramBoolean) {
    if (paramBoolean) {
      Set<String> set = this.mPendingRequests;
      if (set != null && !set.isEmpty()) {
        set = this.mPendingRequests;
        String[] arrayOfString = set.<String>toArray(new String[set.size()]);
        if (paramActivity != null)
          PermissionActivityCompat.requestPermissions(paramActivity, arrayOfString, 1); 
        return;
      } 
    } else {
      List<SoftReference<PermissionsResultAction>> list1 = this.mPendingActions;
      if (list1 != null)
        list1.clear(); 
      Set<String> set = this.mPendingRequests;
      if (set != null)
        set.clear(); 
      List<PermissionsResultAction> list = this.mPendingActionsForGc;
      if (list != null)
        list.clear(); 
    } 
  }
  
  public boolean hasAllPermissions(Context paramContext, String[] paramArrayOfString) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: iconst_0
    //   3: istore_3
    //   4: aload_1
    //   5: ifnonnull -> 12
    //   8: aload_0
    //   9: monitorexit
    //   10: iconst_0
    //   11: ireturn
    //   12: aload_2
    //   13: arraylength
    //   14: istore #4
    //   16: iconst_1
    //   17: istore #5
    //   19: iload_3
    //   20: iload #4
    //   22: if_icmpge -> 49
    //   25: aload_0
    //   26: aload_1
    //   27: aload_2
    //   28: iload_3
    //   29: aaload
    //   30: invokevirtual hasPermission : (Landroid/content/Context;Ljava/lang/String;)Z
    //   33: istore #6
    //   35: iload #5
    //   37: iload #6
    //   39: iand
    //   40: istore #5
    //   42: iload_3
    //   43: iconst_1
    //   44: iadd
    //   45: istore_3
    //   46: goto -> 19
    //   49: aload_0
    //   50: monitorexit
    //   51: iload #5
    //   53: ireturn
    //   54: astore_1
    //   55: aload_0
    //   56: monitorexit
    //   57: goto -> 62
    //   60: aload_1
    //   61: athrow
    //   62: goto -> 60
    // Exception table:
    //   from	to	target	type
    //   12	16	54	finally
    //   25	35	54	finally
  }
  
  public boolean hasPermission(Context paramContext, String paramString) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_1
    //   3: ifnonnull -> 10
    //   6: aload_0
    //   7: monitorexit
    //   8: iconst_0
    //   9: ireturn
    //   10: invokestatic isMiui : ()Z
    //   13: ifeq -> 55
    //   16: aload_1
    //   17: aload_2
    //   18: invokestatic checkPermission : (Landroid/content/Context;Ljava/lang/String;)Z
    //   21: ifeq -> 51
    //   24: aload_1
    //   25: aload_2
    //   26: invokestatic checkSelfPermission : (Landroid/content/Context;Ljava/lang/String;)I
    //   29: ifeq -> 47
    //   32: aload_0
    //   33: getfield mPermissions : Ljava/util/Set;
    //   36: aload_2
    //   37: invokeinterface contains : (Ljava/lang/Object;)Z
    //   42: istore_3
    //   43: iload_3
    //   44: ifne -> 51
    //   47: aload_0
    //   48: monitorexit
    //   49: iconst_1
    //   50: ireturn
    //   51: aload_0
    //   52: monitorexit
    //   53: iconst_0
    //   54: ireturn
    //   55: aload_1
    //   56: aload_2
    //   57: invokestatic checkSelfPermission : (Landroid/content/Context;Ljava/lang/String;)I
    //   60: ifeq -> 85
    //   63: aload_0
    //   64: getfield mPermissions : Ljava/util/Set;
    //   67: aload_2
    //   68: invokeinterface contains : (Ljava/lang/Object;)Z
    //   73: istore_3
    //   74: iload_3
    //   75: ifne -> 81
    //   78: goto -> 85
    //   81: aload_0
    //   82: monitorexit
    //   83: iconst_0
    //   84: ireturn
    //   85: aload_0
    //   86: monitorexit
    //   87: iconst_1
    //   88: ireturn
    //   89: astore_1
    //   90: aload_0
    //   91: monitorexit
    //   92: aload_1
    //   93: athrow
    // Exception table:
    //   from	to	target	type
    //   10	43	89	finally
    //   55	74	89	finally
  }
  
  public void invokeAndClearPermissionResult(String[] paramArrayOfString1, int[] paramArrayOfint, String[] paramArrayOfString2) {
    try {
      int j = paramArrayOfString1.length;
      int i = j;
      if (paramArrayOfint.length < j)
        i = paramArrayOfint.length; 
      Iterator<SoftReference<PermissionsResultAction>> iterator = this.mPendingActions.iterator();
      while (true) {
        boolean bool = iterator.hasNext();
        boolean bool2 = false;
        boolean bool1 = false;
        if (bool) {
          PermissionsResultAction permissionsResultAction = ((SoftReference<PermissionsResultAction>)iterator.next()).get();
          j = bool1;
          if (paramArrayOfString2 != null) {
            j = bool1;
            if (paramArrayOfString2.length > 0) {
              j = bool1;
              if (permissionsResultAction instanceof CustomPermissionsResultAction) {
                ((CustomPermissionsResultAction)permissionsResultAction).onCustomAction(paramArrayOfString2);
                continue;
              } 
            } 
          } 
          while (j < i) {
            if (permissionsResultAction == null || permissionsResultAction.onResult(paramArrayOfString1[j], paramArrayOfint[j])) {
              iterator.remove();
              break;
            } 
            j++;
          } 
          continue;
        } 
        Iterator<PermissionsResultAction> iterator1 = this.mPendingActionsForGc.iterator();
        while (true) {
          j = bool2;
          if (iterator1.hasNext()) {
            iterator1.next();
            iterator1.remove();
            continue;
          } 
          break;
        } 
        return;
      } 
    } finally {
      paramArrayOfString1 = null;
    } 
  }
  
  public void notifyPermissionsChange(Activity paramActivity, String[] paramArrayOfString, int[] paramArrayOfint) {
    /* monitor enter ThisExpression{ObjectType{com/tt/miniapp/permission/PermissionsManager}} */
    try {
      ArrayList<String> arrayList = new ArrayList(3);
      int i = 0;
      int j = paramArrayOfString.length;
      while (true) {
        if (i < j) {
          String str = paramArrayOfString[i];
          if (paramArrayOfint[i] == -1 || (DevicesUtil.isMiui() && !MIUIPermissionUtils.checkPermission((Context)paramActivity, str))) {
            if (paramArrayOfint[i] != -1)
              paramArrayOfint[i] = -1; 
            if (!PermissionActivityCompat.shouldShowRequestPermissionRationale(paramActivity, str) && this.sDescriptMap.containsKey(str))
              arrayList.add(str); 
          } 
        } else {
          if (!arrayList.isEmpty()) {
            String str = getPermissionTip((Context)paramActivity, arrayList);
            if (!TextUtils.isEmpty(str))
              return; 
          } 
          return;
        } 
        i++;
      } 
    } finally {
      paramActivity = null;
      /* monitor exit ThisExpression{ObjectType{com/tt/miniapp/permission/PermissionsManager}} */
    } 
  }
  
  void notifyRequestPermissionResultListener(String paramString, int paramInt) {
    List<SoftReference<RequestPermissionResultListener>> list = this.mRequestPermissionResultListeners;
    if (list != null && !list.isEmpty()) {
      Iterator<SoftReference<RequestPermissionResultListener>> iterator = this.mRequestPermissionResultListeners.iterator();
      while (iterator.hasNext()) {
        RequestPermissionResultListener requestPermissionResultListener = ((SoftReference<RequestPermissionResultListener>)iterator.next()).get();
        if (requestPermissionResultListener != null)
          requestPermissionResultListener.onPermissionResult(paramString, paramInt); 
      } 
    } 
  }
  
  public void onLanguageChange() {
    initializePermissionsMap();
  }
  
  public void removeRequestPermissionResultListener(SoftReference<RequestPermissionResultListener> paramSoftReference) {
    this.mRequestPermissionResultListeners.remove(paramSoftReference);
  }
  
  public void requestAllManifestPermissionsIfNecessary(Activity paramActivity, PermissionsResultAction paramPermissionsResultAction) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_1
    //   3: ifnonnull -> 9
    //   6: aload_0
    //   7: monitorexit
    //   8: return
    //   9: aload_0
    //   10: aload_1
    //   11: invokespecial getManifestPermissions : (Landroid/app/Activity;)[Ljava/lang/String;
    //   14: astore_3
    //   15: new java/util/HashSet
    //   18: dup
    //   19: invokespecial <init> : ()V
    //   22: astore #4
    //   24: aload #4
    //   26: aload_3
    //   27: invokestatic addAll : (Ljava/util/Collection;[Ljava/lang/Object;)Z
    //   30: pop
    //   31: aload_0
    //   32: aload_1
    //   33: aload #4
    //   35: aload_2
    //   36: invokevirtual requestPermissionsIfNecessaryForResult : (Landroid/app/Activity;Ljava/util/Set;Lcom/tt/miniapp/permission/PermissionsResultAction;)V
    //   39: aload_0
    //   40: monitorexit
    //   41: return
    //   42: astore_1
    //   43: aload_0
    //   44: monitorexit
    //   45: aload_1
    //   46: athrow
    // Exception table:
    //   from	to	target	type
    //   9	39	42	finally
  }
  
  public void requestPermissionsForMIUI7(Activity paramActivity, Set<String> paramSet, PermissionsResultAction paramPermissionsResultAction) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_1
    //   3: ifnonnull -> 9
    //   6: aload_0
    //   7: monitorexit
    //   8: return
    //   9: aload_0
    //   10: aload_2
    //   11: aload_3
    //   12: invokespecial addPendingAction : (Ljava/util/Set;Lcom/tt/miniapp/permission/PermissionsResultAction;)V
    //   15: getstatic android/os/Build$VERSION.SDK_INT : I
    //   18: bipush #23
    //   20: if_icmpge -> 33
    //   23: aload_0
    //   24: aload_1
    //   25: aload_2
    //   26: aload_3
    //   27: invokespecial doPermissionWorkBeforeAndroidM : (Landroid/app/Activity;Ljava/util/Set;Lcom/tt/miniapp/permission/PermissionsResultAction;)V
    //   30: goto -> 180
    //   33: aload_0
    //   34: aload_1
    //   35: aload_2
    //   36: aload_3
    //   37: invokespecial getPermissionsListToRequest : (Landroid/app/Activity;Ljava/util/Set;Lcom/tt/miniapp/permission/PermissionsResultAction;)Ljava/util/List;
    //   40: astore #6
    //   42: aload #6
    //   44: invokeinterface isEmpty : ()Z
    //   49: ifne -> 177
    //   52: aload #6
    //   54: aload #6
    //   56: invokeinterface size : ()I
    //   61: anewarray java/lang/String
    //   64: invokeinterface toArray : ([Ljava/lang/Object;)[Ljava/lang/Object;
    //   69: checkcast [Ljava/lang/String;
    //   72: astore_3
    //   73: aload_0
    //   74: getfield mPendingRequests : Ljava/util/Set;
    //   77: aload #6
    //   79: invokeinterface addAll : (Ljava/util/Collection;)Z
    //   84: pop
    //   85: aload_2
    //   86: invokeinterface size : ()I
    //   91: newarray int
    //   93: astore #5
    //   95: iconst_0
    //   96: istore #4
    //   98: iload #4
    //   100: aload #5
    //   102: arraylength
    //   103: if_icmpge -> 121
    //   106: aload #5
    //   108: iload #4
    //   110: iconst_m1
    //   111: iastore
    //   112: iload #4
    //   114: iconst_1
    //   115: iadd
    //   116: istore #4
    //   118: goto -> 98
    //   121: aload_0
    //   122: aload_1
    //   123: aload #6
    //   125: invokespecial getPermissionTip : (Landroid/content/Context;Ljava/util/List;)Ljava/lang/String;
    //   128: astore #6
    //   130: aload #6
    //   132: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   135: ifne -> 152
    //   138: aload_0
    //   139: aload_1
    //   140: aload_3
    //   141: aload_3
    //   142: aload #5
    //   144: aload #6
    //   146: invokespecial handleNeverShowPermissionDialog : (Landroid/app/Activity;[Ljava/lang/String;[Ljava/lang/String;[ILjava/lang/String;)V
    //   149: goto -> 180
    //   152: aload_0
    //   153: aload_2
    //   154: aload_2
    //   155: invokeinterface size : ()I
    //   160: anewarray java/lang/String
    //   163: invokeinterface toArray : ([Ljava/lang/Object;)[Ljava/lang/Object;
    //   168: checkcast [Ljava/lang/String;
    //   171: aload #5
    //   173: aconst_null
    //   174: invokevirtual invokeAndClearPermissionResult : ([Ljava/lang/String;[I[Ljava/lang/String;)V
    //   177: aload_0
    //   178: monitorexit
    //   179: return
    //   180: aload_0
    //   181: monitorexit
    //   182: return
    //   183: astore_1
    //   184: goto -> 180
    // Exception table:
    //   from	to	target	type
    //   9	30	183	finally
    //   33	95	183	finally
    //   98	106	183	finally
    //   121	149	183	finally
    //   152	177	183	finally
  }
  
  public void requestPermissionsIfNecessaryForResult(Activity paramActivity, Set<String> paramSet, PermissionsResultAction paramPermissionsResultAction) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_1
    //   3: ifnonnull -> 9
    //   6: aload_0
    //   7: monitorexit
    //   8: return
    //   9: aload_0
    //   10: aload_2
    //   11: aload_3
    //   12: invokespecial addPendingAction : (Ljava/util/Set;Lcom/tt/miniapp/permission/PermissionsResultAction;)V
    //   15: getstatic android/os/Build$VERSION.SDK_INT : I
    //   18: bipush #23
    //   20: if_icmpge -> 33
    //   23: aload_0
    //   24: aload_1
    //   25: aload_2
    //   26: aload_3
    //   27: invokespecial doPermissionWorkBeforeAndroidM : (Landroid/app/Activity;Ljava/util/Set;Lcom/tt/miniapp/permission/PermissionsResultAction;)V
    //   30: goto -> 113
    //   33: aload_0
    //   34: aload_1
    //   35: aload_2
    //   36: aload_3
    //   37: invokespecial getPermissionsListToRequest : (Landroid/app/Activity;Ljava/util/Set;Lcom/tt/miniapp/permission/PermissionsResultAction;)Ljava/util/List;
    //   40: astore_2
    //   41: aload_2
    //   42: invokeinterface isEmpty : ()Z
    //   47: ifne -> 86
    //   50: aload_2
    //   51: aload_2
    //   52: invokeinterface size : ()I
    //   57: anewarray java/lang/String
    //   60: invokeinterface toArray : ([Ljava/lang/Object;)[Ljava/lang/Object;
    //   65: checkcast [Ljava/lang/String;
    //   68: astore_3
    //   69: aload_0
    //   70: getfield mPendingRequests : Ljava/util/Set;
    //   73: aload_2
    //   74: invokeinterface addAll : (Ljava/util/Collection;)Z
    //   79: pop
    //   80: aload_1
    //   81: aload_3
    //   82: iconst_1
    //   83: invokestatic requestPermissions : (Landroid/app/Activity;[Ljava/lang/String;I)V
    //   86: aload_0
    //   87: monitorexit
    //   88: return
    //   89: astore_1
    //   90: ldc_w 'PermissionsManager'
    //   93: iconst_2
    //   94: anewarray java/lang/Object
    //   97: dup
    //   98: iconst_0
    //   99: ldc_w 'requestPermissionsIfNecessaryForResult'
    //   102: aastore
    //   103: dup
    //   104: iconst_1
    //   105: aload_1
    //   106: invokevirtual getMessage : ()Ljava/lang/String;
    //   109: aastore
    //   110: invokestatic e : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   113: aload_0
    //   114: monitorexit
    //   115: return
    //   116: astore_1
    //   117: aload_0
    //   118: monitorexit
    //   119: aload_1
    //   120: athrow
    // Exception table:
    //   from	to	target	type
    //   9	30	89	finally
    //   33	86	89	finally
    //   90	113	116	finally
  }
  
  public void requestPermissionsIfNecessaryForResult(Activity paramActivity, String[] paramArrayOfString, PermissionsResultAction paramPermissionsResultAction) {
    requestPermissionsIfNecessaryForResult(paramActivity, new HashSet<String>(Arrays.asList(paramArrayOfString)), paramPermissionsResultAction);
  }
  
  public void requestPermissionsIfNecessaryForResult(Fragment paramFragment, Set<String> paramSet, PermissionsResultAction paramPermissionsResultAction) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_1
    //   3: invokevirtual getActivity : ()Landroid/support/v4/app/FragmentActivity;
    //   6: astore #4
    //   8: aload #4
    //   10: ifnonnull -> 16
    //   13: aload_0
    //   14: monitorexit
    //   15: return
    //   16: aload_0
    //   17: aload_2
    //   18: aload_3
    //   19: invokespecial addPendingAction : (Ljava/util/Set;Lcom/tt/miniapp/permission/PermissionsResultAction;)V
    //   22: getstatic android/os/Build$VERSION.SDK_INT : I
    //   25: bipush #23
    //   27: if_icmpge -> 41
    //   30: aload_0
    //   31: aload #4
    //   33: aload_2
    //   34: aload_3
    //   35: invokespecial doPermissionWorkBeforeAndroidM : (Landroid/app/Activity;Ljava/util/Set;Lcom/tt/miniapp/permission/PermissionsResultAction;)V
    //   38: goto -> 98
    //   41: aload_0
    //   42: aload #4
    //   44: aload_2
    //   45: aload_3
    //   46: invokespecial getPermissionsListToRequest : (Landroid/app/Activity;Ljava/util/Set;Lcom/tt/miniapp/permission/PermissionsResultAction;)Ljava/util/List;
    //   49: astore_2
    //   50: aload_2
    //   51: invokeinterface isEmpty : ()Z
    //   56: ifne -> 95
    //   59: aload_2
    //   60: aload_2
    //   61: invokeinterface size : ()I
    //   66: anewarray java/lang/String
    //   69: invokeinterface toArray : ([Ljava/lang/Object;)[Ljava/lang/Object;
    //   74: checkcast [Ljava/lang/String;
    //   77: astore_3
    //   78: aload_0
    //   79: getfield mPendingRequests : Ljava/util/Set;
    //   82: aload_2
    //   83: invokeinterface addAll : (Ljava/util/Collection;)Z
    //   88: pop
    //   89: aload_1
    //   90: aload_3
    //   91: iconst_1
    //   92: invokevirtual requestPermissions : ([Ljava/lang/String;I)V
    //   95: aload_0
    //   96: monitorexit
    //   97: return
    //   98: aload_0
    //   99: monitorexit
    //   100: return
    //   101: astore_1
    //   102: aload_0
    //   103: monitorexit
    //   104: aload_1
    //   105: athrow
    //   106: astore_1
    //   107: goto -> 98
    // Exception table:
    //   from	to	target	type
    //   2	8	101	finally
    //   16	38	106	finally
    //   41	95	106	finally
  }
  
  public void requestPermissionsIfNecessaryForResult(Fragment paramFragment, String[] paramArrayOfString, PermissionsResultAction paramPermissionsResultAction) {
    requestPermissionsIfNecessaryForResult(paramFragment, new HashSet<String>(Arrays.asList(paramArrayOfString)), paramPermissionsResultAction);
  }
  
  public static class DefaultDialogBuilder extends DialogBuilder {
    private AlertDialog.Builder builder;
    
    public DefaultDialogBuilder(Context param1Context) {
      this.builder = new AlertDialog.Builder(param1Context, 16843529);
    }
    
    public Dialog create() {
      return (Dialog)this.builder.create();
    }
    
    public PermissionsManager.DialogBuilder setMessage(CharSequence param1CharSequence) {
      this.builder.setMessage(param1CharSequence);
      return this;
    }
    
    public PermissionsManager.DialogBuilder setNegativeButton(int param1Int, DialogInterface.OnClickListener param1OnClickListener) {
      this.builder.setNegativeButton(param1Int, param1OnClickListener);
      return this;
    }
    
    public PermissionsManager.DialogBuilder setPositiveButton(int param1Int, DialogInterface.OnClickListener param1OnClickListener) {
      this.builder.setPositiveButton(param1Int, param1OnClickListener);
      return this;
    }
    
    public PermissionsManager.DialogBuilder setTitle(int param1Int) {
      this.builder.setTitle(param1Int);
      return this;
    }
    
    public PermissionsManager.DialogBuilder setTitle(CharSequence param1CharSequence) {
      this.builder.setTitle(param1CharSequence);
      return this;
    }
  }
  
  public static abstract class DialogBuilder {
    public abstract Dialog create();
    
    public abstract DialogBuilder setMessage(CharSequence param1CharSequence);
    
    public abstract DialogBuilder setNegativeButton(int param1Int, DialogInterface.OnClickListener param1OnClickListener);
    
    public abstract DialogBuilder setPositiveButton(int param1Int, DialogInterface.OnClickListener param1OnClickListener);
    
    public abstract DialogBuilder setTitle(int param1Int);
    
    public abstract DialogBuilder setTitle(CharSequence param1CharSequence);
  }
  
  public static interface DialogBuilderProvider {
    PermissionsManager.DialogBuilder createBuilder(Context param1Context);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\permission\PermissionsManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */