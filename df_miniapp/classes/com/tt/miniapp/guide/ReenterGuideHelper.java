package com.tt.miniapp.guide;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.k;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.storage.async.Action;
import com.tt.b.a;
import com.tt.b.c;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.guide.reenter.FileConfig;
import com.tt.miniapp.guide.reenter.ReenterDialog;
import com.tt.miniapp.guide.reenter.ReenterSetting;
import com.tt.miniapp.settings.data.SettingsDAO;
import com.tt.miniapp.settings.keys.Settings;
import com.tt.miniapp.thread.ThreadPools;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapp.view.dialog.AlertDialogHelper;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.entity.AppInfoEntity;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.miniapphost.util.UIUtils;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ReenterGuideHelper {
  public static String sPreloadImage;
  
  public static long sShowTimestamp;
  
  public static void callback(Runnable paramRunnable) {
    if (paramRunnable == null)
      return; 
    paramRunnable.run();
  }
  
  private static ReenterSetting checkNull4Stop(Context paramContext, AppInfoEntity paramAppInfoEntity) {
    StringBuilder stringBuilder1;
    boolean bool = true;
    if (paramContext == null || paramAppInfoEntity == null) {
      boolean bool1;
      StringBuilder stringBuilder = new StringBuilder("r64091: checkNull4Stop: ctx null?");
      if (paramContext == null) {
        bool1 = true;
      } else {
        bool1 = false;
      } 
      stringBuilder.append(bool1);
      stringBuilder.append(", info null?");
      if (paramAppInfoEntity == null) {
        bool1 = bool;
      } else {
        bool1 = false;
      } 
      stringBuilder.append(bool1);
      AppBrandLogger.d("tma_ReenterGuideHelper", new Object[] { stringBuilder.toString() });
      return null;
    } 
    ReenterSetting reenterSetting = getSetting(paramContext, paramAppInfoEntity.isGame());
    if (TextUtils.isEmpty(reenterSetting.image) || reenterSetting.count <= 0 || TextUtils.isEmpty(reenterSetting.title) || TextUtils.isEmpty(reenterSetting.buttonColor) || TextUtils.isEmpty(reenterSetting.buttonText)) {
      stringBuilder1 = new StringBuilder("r64091: checkNull4Stop: ");
      stringBuilder1.append(reenterSetting);
      AppBrandLogger.d("tma_ReenterGuideHelper", new Object[] { stringBuilder1.toString() });
      return null;
    } 
    if (!reenterSetting.blackList.isEmpty() && reenterSetting.blackList.contains(paramAppInfoEntity.appId)) {
      AppBrandLogger.d("tma_ReenterGuideHelper", new Object[] { "r64091: checkNull4Stop: app is in blacklist" });
      return null;
    } 
    FileConfig fileConfig = getFileConfig((Context)stringBuilder1);
    int i = Integer.MIN_VALUE;
    if (fileConfig != null) {
      if (paramAppInfoEntity.isGame()) {
        i = fileConfig.gameCnt;
      } else {
        i = fileConfig.appCnt;
      } 
      if (i >= 0) {
        reenterSetting.nowCnt = i;
        long l = System.currentTimeMillis();
        if (i >= reenterSetting.count || l <= fileConfig.timestamp + 86400000L) {
          StringBuilder stringBuilder = new StringBuilder("r64091: checkNull4Stop: need't dialog(cnt=");
          stringBuilder.append(i);
          stringBuilder.append(",max=");
          stringBuilder.append(reenterSetting.count);
          stringBuilder.append(";tg:");
          stringBuilder.append(l - fileConfig.timestamp);
          stringBuilder.append(" )");
          AppBrandLogger.d("tma_ReenterGuideHelper", new Object[] { stringBuilder.toString() });
          return null;
        } 
        return reenterSetting;
      } 
    } 
    StringBuilder stringBuilder2 = new StringBuilder("r64091: checkNull4Stop: can't dialog(cnt=");
    stringBuilder2.append(i);
    stringBuilder2.append(",cgf=");
    stringBuilder2.append(fileConfig);
    stringBuilder2.append(")");
    AppBrandLogger.d("tma_ReenterGuideHelper", new Object[] { stringBuilder2.toString() });
    return null;
  }
  
  public static void checkReenterGuideTip(Activity paramActivity, Runnable paramRunnable) {
    AppBrandLogger.d("tma_ReenterGuideHelper", new Object[] { "r64091: checkReenterGuideTip" });
    if (!(paramActivity instanceof FragmentActivity) || paramActivity.isFinishing() || paramActivity.isDestroyed()) {
      callback(paramRunnable);
      return;
    } 
    AppInfoEntity appInfoEntity = AppbrandApplicationImpl.getInst().getAppInfo();
    ReenterSetting reenterSetting = checkNull4Stop((Context)paramActivity, appInfoEntity);
    if (reenterSetting == null) {
      callback(paramRunnable);
      return;
    } 
    if (!TextUtils.equals(sPreloadImage, reenterSetting.image)) {
      AppBrandLogger.d("tma_ReenterGuideHelper", new Object[] { "r64091: checkReenterGuideTip: can't dialog(!preload)" });
      callback(paramRunnable);
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder("r64091: checkReenterGuideTip: dialog(cnt=");
    stringBuilder.append(reenterSetting.nowCnt);
    stringBuilder.append(",max=");
    stringBuilder.append(reenterSetting.count);
    stringBuilder.append(")");
    AppBrandLogger.d("tma_ReenterGuideHelper", new Object[] { stringBuilder.toString() });
    showGuideDialog((FragmentActivity)paramActivity, paramRunnable, reenterSetting, appInfoEntity.isGame());
  }
  
  public static FileConfig getFileConfig(final Context context) {
    FutureTask<FileConfig> futureTask = new FutureTask(new Callable<FileConfig>() {
          public final FileConfig call() {
            return ReenterGuideHelper.readFromFile(new File(context.getFilesDir(), ".reuse_cnt"));
          }
        });
    ThreadPools.defaults().execute(futureTask);
    try {
      return futureTask.get(1800L, TimeUnit.MILLISECONDS);
    } catch (Exception exception) {
      AppBrandLogger.e("tma_ReenterGuideHelper", new Object[] { exception });
      return null;
    } 
  }
  
  private static ReenterSetting getSetting(Context paramContext, boolean paramBoolean) {
    String str;
    ReenterSetting reenterSetting = new ReenterSetting();
    if (paramBoolean) {
      str = SettingsDAO.getJSONObject(paramContext, new Enum[] { (Enum)Settings.BDP_REENTER_TIPS, (Enum)Settings.BdpReenterTips.TMG }).toString();
    } else {
      str = SettingsDAO.getJSONObject((Context)str, new Enum[] { (Enum)Settings.BDP_REENTER_TIPS, (Enum)Settings.BdpReenterTips.TMA }).toString();
    } 
    StringBuilder stringBuilder = new StringBuilder("r64091: setting json: ");
    stringBuilder.append(str);
    AppBrandLogger.d("tma_ReenterGuideHelper", new Object[] { stringBuilder.toString() });
    if (!TextUtils.isEmpty(str))
      try {
        JSONObject jSONObject = new JSONObject(str);
        reenterSetting.image = jSONObject.optString("image", reenterSetting.image);
        reenterSetting.buttonColor = jSONObject.optString("buttonColor", reenterSetting.buttonColor);
        reenterSetting.buttonText = jSONObject.optString("buttonText", reenterSetting.buttonText);
        reenterSetting.title = jSONObject.optString("title", reenterSetting.title);
        reenterSetting.count = jSONObject.optInt("count", 0);
        JSONArray jSONArray = jSONObject.optJSONArray("blackList");
        if (jSONArray != null && jSONArray.length() > 0)
          for (int i = 0; i < jSONArray.length(); i++) {
            String str1 = jSONArray.optString(i);
            if (!TextUtils.isEmpty(str1))
              reenterSetting.blackList.add(str1); 
          }  
      } catch (JSONException jSONException) {
        AppBrandLogger.e("tma_ReenterGuideHelper", new Object[] { jSONException });
      }  
    return reenterSetting;
  }
  
  public static void preload(Context paramContext) {
    AppBrandLogger.d("tma_ReenterGuideHelper", new Object[] { "r64091: preload" });
    sPreloadImage = null;
    final ReenterSetting setting = checkNull4Stop(paramContext, AppbrandApplicationImpl.getInst().getAppInfo());
    if (reenterSetting == null) {
      AppBrandLogger.d("tma_ReenterGuideHelper", new Object[] { "r64091: need't preload" });
      return;
    } 
    Resources resources = paramContext.getResources();
    float f1 = resources.getDimensionPixelSize(2097414174);
    float f2 = resources.getDimensionPixelSize(2097414173);
    int[] arrayOfInt = AlertDialogHelper.getAdjustWidthAndHeight(paramContext, true);
    f2 /= f1;
    float f3 = UIUtils.dip2Px(paramContext, arrayOfInt[0]);
    final ImageView view = new ImageView(paramContext);
    int i = (int)f1;
    int j = (int)(f2 * f3);
    imageView.setLayoutParams((ViewGroup.LayoutParams)new FrameLayout.LayoutParams(i, j));
    AppBrandLogger.d("tma_ReenterGuideHelper", new Object[] { "r64091: preload start." });
    try {
      c c = (new c(reenterSetting.image)).a((Drawable)new ColorDrawable(0)).a(i, j).a(new a() {
            public final void onFail(Exception param1Exception) {
              ReenterGuideHelper.sPreloadImage = null;
              StringBuilder stringBuilder = new StringBuilder("r64091: preload: failed.");
              stringBuilder.append(view.hashCode());
              AppBrandLogger.d("tma_ReenterGuideHelper", new Object[] { stringBuilder.toString() });
            }
            
            public final void onSuccess() {
              ReenterGuideHelper.sPreloadImage = setting.image;
              StringBuilder stringBuilder = new StringBuilder("r64091: preload: succeed.");
              stringBuilder.append(view.hashCode());
              AppBrandLogger.d("tma_ReenterGuideHelper", new Object[] { stringBuilder.toString() });
            }
          }).a((View)imageView);
      HostDependManager.getInst().loadImage(paramContext, c);
      return;
    } catch (RuntimeException runtimeException) {
      AppBrandLogger.eWithThrowable("tma_ReenterGuideHelper", "r64091: preload error", runtimeException);
      return;
    } 
  }
  
  private static boolean prepareFileFailed(File paramFile, boolean paramBoolean) {
    if (paramFile == null)
      return true; 
    if (paramFile.exists())
      return false; 
    try {
      if (paramFile.createNewFile() && paramBoolean)
        writeToFile(paramFile, new FileConfig()); 
      return false;
    } catch (IOException iOException) {
      AppBrandLogger.e("tma_ReenterGuideHelper", new Object[] { iOException });
      return true;
    } 
  }
  
  public static FileConfig readFromFile(File paramFile) {
    // Byte code:
    //   0: invokestatic newAndStart : ()Lcom/tt/miniapphost/util/TimeMeter;
    //   3: astore #10
    //   5: aload_0
    //   6: iconst_1
    //   7: invokestatic prepareFileFailed : (Ljava/io/File;Z)Z
    //   10: istore_1
    //   11: aconst_null
    //   12: astore #6
    //   14: aconst_null
    //   15: astore #5
    //   17: aconst_null
    //   18: astore #4
    //   20: aconst_null
    //   21: astore #8
    //   23: iload_1
    //   24: ifeq -> 29
    //   27: aconst_null
    //   28: areturn
    //   29: new com/tt/miniapp/guide/reenter/FileConfig
    //   32: dup
    //   33: invokespecial <init> : ()V
    //   36: astore #9
    //   38: new java/io/RandomAccessFile
    //   41: dup
    //   42: aload_0
    //   43: ldc_w 'rw'
    //   46: invokespecial <init> : (Ljava/io/File;Ljava/lang/String;)V
    //   49: astore #7
    //   51: aload #8
    //   53: astore #6
    //   55: aload #5
    //   57: astore #4
    //   59: aload #7
    //   61: astore #5
    //   63: aload #7
    //   65: invokevirtual getChannel : ()Ljava/nio/channels/FileChannel;
    //   68: invokevirtual lock : ()Ljava/nio/channels/FileLock;
    //   71: astore #8
    //   73: aload #8
    //   75: astore #6
    //   77: aload #8
    //   79: astore #4
    //   81: aload #7
    //   83: astore #5
    //   85: aload #9
    //   87: aload_0
    //   88: invokevirtual file2Cfg : (Ljava/io/File;)V
    //   91: aload #8
    //   93: ifnull -> 118
    //   96: aload #8
    //   98: invokevirtual release : ()V
    //   101: goto -> 118
    //   104: astore_0
    //   105: ldc 'tma_ReenterGuideHelper'
    //   107: iconst_1
    //   108: anewarray java/lang/Object
    //   111: dup
    //   112: iconst_0
    //   113: aload_0
    //   114: aastore
    //   115: invokestatic e : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   118: aload #7
    //   120: invokevirtual close : ()V
    //   123: goto -> 241
    //   126: astore_0
    //   127: ldc 'tma_ReenterGuideHelper'
    //   129: iconst_1
    //   130: anewarray java/lang/Object
    //   133: dup
    //   134: iconst_0
    //   135: aload_0
    //   136: aastore
    //   137: invokestatic e : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   140: goto -> 241
    //   143: astore #4
    //   145: aload #7
    //   147: astore_0
    //   148: aload #4
    //   150: astore #7
    //   152: goto -> 166
    //   155: astore_0
    //   156: aconst_null
    //   157: astore #5
    //   159: goto -> 351
    //   162: astore #7
    //   164: aconst_null
    //   165: astore_0
    //   166: aload #6
    //   168: astore #4
    //   170: aload_0
    //   171: astore #5
    //   173: ldc 'tma_ReenterGuideHelper'
    //   175: iconst_1
    //   176: anewarray java/lang/Object
    //   179: dup
    //   180: iconst_0
    //   181: aload #7
    //   183: aastore
    //   184: invokestatic e : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   187: aload #6
    //   189: ifnull -> 216
    //   192: aload #6
    //   194: invokevirtual release : ()V
    //   197: goto -> 216
    //   200: astore #4
    //   202: ldc 'tma_ReenterGuideHelper'
    //   204: iconst_1
    //   205: anewarray java/lang/Object
    //   208: dup
    //   209: iconst_0
    //   210: aload #4
    //   212: aastore
    //   213: invokestatic e : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   216: aload_0
    //   217: ifnull -> 241
    //   220: aload_0
    //   221: invokevirtual close : ()V
    //   224: goto -> 241
    //   227: astore_0
    //   228: ldc 'tma_ReenterGuideHelper'
    //   230: iconst_1
    //   231: anewarray java/lang/Object
    //   234: dup
    //   235: iconst_0
    //   236: aload_0
    //   237: aastore
    //   238: invokestatic e : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   241: aload #10
    //   243: invokestatic stop : (Lcom/tt/miniapphost/util/TimeMeter;)J
    //   246: lstore_2
    //   247: new java/lang/StringBuilder
    //   250: dup
    //   251: ldc_w 'r64091: readFromFile: '
    //   254: invokespecial <init> : (Ljava/lang/String;)V
    //   257: astore_0
    //   258: aload_0
    //   259: aload #9
    //   261: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   264: pop
    //   265: aload_0
    //   266: ldc_w ', ms: '
    //   269: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   272: pop
    //   273: aload_0
    //   274: lload_2
    //   275: invokevirtual append : (J)Ljava/lang/StringBuilder;
    //   278: pop
    //   279: ldc 'tma_ReenterGuideHelper'
    //   281: iconst_1
    //   282: anewarray java/lang/Object
    //   285: dup
    //   286: iconst_0
    //   287: aload_0
    //   288: invokevirtual toString : ()Ljava/lang/String;
    //   291: aastore
    //   292: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   295: lload_2
    //   296: ldc2_w 1800
    //   299: lcmp
    //   300: ifle -> 347
    //   303: new org/json/JSONObject
    //   306: dup
    //   307: invokespecial <init> : ()V
    //   310: astore_0
    //   311: aload_0
    //   312: ldc_w 'duration'
    //   315: lload_2
    //   316: invokevirtual put : (Ljava/lang/String;J)Lorg/json/JSONObject;
    //   319: pop
    //   320: goto -> 339
    //   323: astore #4
    //   325: ldc 'tma_ReenterGuideHelper'
    //   327: iconst_1
    //   328: anewarray java/lang/Object
    //   331: dup
    //   332: iconst_0
    //   333: aload #4
    //   335: aastore
    //   336: invokestatic e : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   339: ldc_w 'mp_renter_io_time_out'
    //   342: iconst_0
    //   343: aload_0
    //   344: invokestatic statusRate : (Ljava/lang/String;ILorg/json/JSONObject;)V
    //   347: aload #9
    //   349: areturn
    //   350: astore_0
    //   351: aload #4
    //   353: ifnull -> 380
    //   356: aload #4
    //   358: invokevirtual release : ()V
    //   361: goto -> 380
    //   364: astore #4
    //   366: ldc 'tma_ReenterGuideHelper'
    //   368: iconst_1
    //   369: anewarray java/lang/Object
    //   372: dup
    //   373: iconst_0
    //   374: aload #4
    //   376: aastore
    //   377: invokestatic e : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   380: aload #5
    //   382: ifnull -> 409
    //   385: aload #5
    //   387: invokevirtual close : ()V
    //   390: goto -> 409
    //   393: astore #4
    //   395: ldc 'tma_ReenterGuideHelper'
    //   397: iconst_1
    //   398: anewarray java/lang/Object
    //   401: dup
    //   402: iconst_0
    //   403: aload #4
    //   405: aastore
    //   406: invokestatic e : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   409: aload_0
    //   410: athrow
    // Exception table:
    //   from	to	target	type
    //   38	51	162	java/io/IOException
    //   38	51	155	finally
    //   63	73	143	java/io/IOException
    //   63	73	350	finally
    //   85	91	143	java/io/IOException
    //   85	91	350	finally
    //   96	101	104	java/io/IOException
    //   118	123	126	java/io/IOException
    //   173	187	350	finally
    //   192	197	200	java/io/IOException
    //   220	224	227	java/io/IOException
    //   311	320	323	org/json/JSONException
    //   356	361	364	java/io/IOException
    //   385	390	393	java/io/IOException
  }
  
  private static void showGuideDialog(final FragmentActivity activity, final Runnable callbackRun, ReenterSetting paramReenterSetting, final boolean isGame) {
    Bundle bundle = new Bundle();
    bundle.putString("key_image_uri", paramReenterSetting.image);
    bundle.putString("key_text_title", paramReenterSetting.title);
    bundle.putString("key_btn_color", paramReenterSetting.buttonColor);
    bundle.putString("key_btn_text", paramReenterSetting.buttonText);
    ReenterDialog reenterDialog = new ReenterDialog();
    reenterDialog.setArguments(bundle);
    sShowTimestamp = System.currentTimeMillis();
    reenterDialog.setDismissCallback(new ReenterDialog.DismissCallback() {
          public final void onDismiss() {
            ReenterGuideHelper.callback(callbackRun);
            ReenterGuideHelper.updateFileConfig((Context)activity, isGame);
          }
        });
    k k = activity.getSupportFragmentManager();
    if (!activity.isFinishing() && !activity.isDestroyed() && k != null && !k.h())
      try {
        reenterDialog.show(k, "");
        return;
      } catch (IllegalStateException illegalStateException) {
        AppBrandLogger.eWithThrowable("tma_ReenterGuideHelper", "show reenter dialog exp", illegalStateException);
      }  
    callback(callbackRun);
  }
  
  public static void updateFileConfig(final Context context, final boolean isGame) {
    ThreadUtil.runOnWorkThread(new Action() {
          public final void act() {
            AppBrandLogger.d("tma_ReenterGuideHelper", new Object[] { "r64091: updateFileConfig" });
            File file = new File(context.getFilesDir(), ".reuse_cnt");
            FileConfig fileConfig = ReenterGuideHelper.getFileConfig(context);
            if (fileConfig == null)
              return; 
            if (isGame) {
              fileConfig.gameCnt++;
            } else {
              fileConfig.appCnt++;
            } 
            fileConfig.timestamp = ReenterGuideHelper.sShowTimestamp;
            ReenterGuideHelper.writeToFile(file, fileConfig);
          }
        }ThreadPools.longIO());
  }
  
  public static void writeToFile(File paramFile, FileConfig paramFileConfig) {
    // Byte code:
    //   0: invokestatic newAndStart : ()Lcom/tt/miniapphost/util/TimeMeter;
    //   3: astore #7
    //   5: aload_0
    //   6: iconst_0
    //   7: invokestatic prepareFileFailed : (Ljava/io/File;Z)Z
    //   10: ifeq -> 14
    //   13: return
    //   14: aconst_null
    //   15: astore #4
    //   17: aconst_null
    //   18: astore_3
    //   19: aconst_null
    //   20: astore_2
    //   21: aconst_null
    //   22: astore #6
    //   24: new java/io/RandomAccessFile
    //   27: dup
    //   28: aload_0
    //   29: ldc_w 'rw'
    //   32: invokespecial <init> : (Ljava/io/File;Ljava/lang/String;)V
    //   35: astore #5
    //   37: aload #6
    //   39: astore #4
    //   41: aload_3
    //   42: astore_2
    //   43: aload #5
    //   45: astore_3
    //   46: aload #5
    //   48: invokevirtual getChannel : ()Ljava/nio/channels/FileChannel;
    //   51: invokevirtual lock : ()Ljava/nio/channels/FileLock;
    //   54: astore #6
    //   56: aload #6
    //   58: astore #4
    //   60: aload #6
    //   62: astore_2
    //   63: aload #5
    //   65: astore_3
    //   66: aload_1
    //   67: aload_0
    //   68: invokevirtual cfg2File : (Ljava/io/File;)V
    //   71: aload #6
    //   73: ifnull -> 98
    //   76: aload #6
    //   78: invokevirtual release : ()V
    //   81: goto -> 98
    //   84: astore_0
    //   85: ldc 'tma_ReenterGuideHelper'
    //   87: iconst_1
    //   88: anewarray java/lang/Object
    //   91: dup
    //   92: iconst_0
    //   93: aload_0
    //   94: aastore
    //   95: invokestatic e : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   98: aload #5
    //   100: invokevirtual close : ()V
    //   103: goto -> 214
    //   106: astore_0
    //   107: ldc 'tma_ReenterGuideHelper'
    //   109: iconst_1
    //   110: anewarray java/lang/Object
    //   113: dup
    //   114: iconst_0
    //   115: aload_0
    //   116: aastore
    //   117: invokestatic e : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   120: goto -> 214
    //   123: astore_2
    //   124: aload #5
    //   126: astore_0
    //   127: aload_2
    //   128: astore #5
    //   130: goto -> 143
    //   133: astore_0
    //   134: aconst_null
    //   135: astore_3
    //   136: goto -> 267
    //   139: astore #5
    //   141: aconst_null
    //   142: astore_0
    //   143: aload #4
    //   145: astore_2
    //   146: aload_0
    //   147: astore_3
    //   148: ldc 'tma_ReenterGuideHelper'
    //   150: iconst_1
    //   151: anewarray java/lang/Object
    //   154: dup
    //   155: iconst_0
    //   156: aload #5
    //   158: aastore
    //   159: invokestatic e : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   162: aload #4
    //   164: ifnull -> 189
    //   167: aload #4
    //   169: invokevirtual release : ()V
    //   172: goto -> 189
    //   175: astore_2
    //   176: ldc 'tma_ReenterGuideHelper'
    //   178: iconst_1
    //   179: anewarray java/lang/Object
    //   182: dup
    //   183: iconst_0
    //   184: aload_2
    //   185: aastore
    //   186: invokestatic e : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   189: aload_0
    //   190: ifnull -> 214
    //   193: aload_0
    //   194: invokevirtual close : ()V
    //   197: goto -> 214
    //   200: astore_0
    //   201: ldc 'tma_ReenterGuideHelper'
    //   203: iconst_1
    //   204: anewarray java/lang/Object
    //   207: dup
    //   208: iconst_0
    //   209: aload_0
    //   210: aastore
    //   211: invokestatic e : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   214: new java/lang/StringBuilder
    //   217: dup
    //   218: ldc_w 'r64091: writeT2File: '
    //   221: invokespecial <init> : (Ljava/lang/String;)V
    //   224: astore_0
    //   225: aload_0
    //   226: aload_1
    //   227: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   230: pop
    //   231: aload_0
    //   232: ldc_w ', ms: '
    //   235: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   238: pop
    //   239: aload_0
    //   240: aload #7
    //   242: invokestatic stop : (Lcom/tt/miniapphost/util/TimeMeter;)J
    //   245: invokevirtual append : (J)Ljava/lang/StringBuilder;
    //   248: pop
    //   249: ldc 'tma_ReenterGuideHelper'
    //   251: iconst_1
    //   252: anewarray java/lang/Object
    //   255: dup
    //   256: iconst_0
    //   257: aload_0
    //   258: invokevirtual toString : ()Ljava/lang/String;
    //   261: aastore
    //   262: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   265: return
    //   266: astore_0
    //   267: aload_2
    //   268: ifnull -> 292
    //   271: aload_2
    //   272: invokevirtual release : ()V
    //   275: goto -> 292
    //   278: astore_1
    //   279: ldc 'tma_ReenterGuideHelper'
    //   281: iconst_1
    //   282: anewarray java/lang/Object
    //   285: dup
    //   286: iconst_0
    //   287: aload_1
    //   288: aastore
    //   289: invokestatic e : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   292: aload_3
    //   293: ifnull -> 317
    //   296: aload_3
    //   297: invokevirtual close : ()V
    //   300: goto -> 317
    //   303: astore_1
    //   304: ldc 'tma_ReenterGuideHelper'
    //   306: iconst_1
    //   307: anewarray java/lang/Object
    //   310: dup
    //   311: iconst_0
    //   312: aload_1
    //   313: aastore
    //   314: invokestatic e : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   317: aload_0
    //   318: athrow
    // Exception table:
    //   from	to	target	type
    //   24	37	139	java/io/IOException
    //   24	37	133	finally
    //   46	56	123	java/io/IOException
    //   46	56	266	finally
    //   66	71	123	java/io/IOException
    //   66	71	266	finally
    //   76	81	84	java/io/IOException
    //   98	103	106	java/io/IOException
    //   148	162	266	finally
    //   167	172	175	java/io/IOException
    //   193	197	200	java/io/IOException
    //   271	275	278	java/io/IOException
    //   296	300	303	java/io/IOException
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\guide\ReenterGuideHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */