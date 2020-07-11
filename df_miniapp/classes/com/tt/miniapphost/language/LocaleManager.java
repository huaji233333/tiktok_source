package com.tt.miniapphost.language;

import android.content.Context;
import android.os.Build;
import android.os.LocaleList;
import android.text.TextUtils;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class LocaleManager {
  private static volatile LocaleManager sInstance;
  
  private Locale currentHostSetLocale;
  
  private volatile boolean isRTL;
  
  private List<WeakReference<LanguageChangeListener>> listeners = new LinkedList<WeakReference<LanguageChangeListener>>();
  
  private boolean contains(LanguageChangeListener paramLanguageChangeListener) {
    Iterator<WeakReference<LanguageChangeListener>> iterator = this.listeners.iterator();
    while (iterator.hasNext()) {
      LanguageChangeListener languageChangeListener = ((WeakReference<LanguageChangeListener>)iterator.next()).get();
      if (languageChangeListener == null) {
        iterator.remove();
        AppBrandLogger.d("LocaleManager", new Object[] { "recycle refer" });
      } 
      if (languageChangeListener == paramLanguageChangeListener)
        return true; 
    } 
    return false;
  }
  
  public static LocaleManager getInst() {
    // Byte code:
    //   0: getstatic com/tt/miniapphost/language/LocaleManager.sInstance : Lcom/tt/miniapphost/language/LocaleManager;
    //   3: ifnonnull -> 37
    //   6: ldc com/tt/miniapphost/language/LocaleManager
    //   8: monitorenter
    //   9: getstatic com/tt/miniapphost/language/LocaleManager.sInstance : Lcom/tt/miniapphost/language/LocaleManager;
    //   12: ifnonnull -> 25
    //   15: new com/tt/miniapphost/language/LocaleManager
    //   18: dup
    //   19: invokespecial <init> : ()V
    //   22: putstatic com/tt/miniapphost/language/LocaleManager.sInstance : Lcom/tt/miniapphost/language/LocaleManager;
    //   25: ldc com/tt/miniapphost/language/LocaleManager
    //   27: monitorexit
    //   28: goto -> 37
    //   31: astore_0
    //   32: ldc com/tt/miniapphost/language/LocaleManager
    //   34: monitorexit
    //   35: aload_0
    //   36: athrow
    //   37: getstatic com/tt/miniapphost/language/LocaleManager.sInstance : Lcom/tt/miniapphost/language/LocaleManager;
    //   40: areturn
    // Exception table:
    //   from	to	target	type
    //   9	25	31	finally
    //   25	28	31	finally
    //   32	35	31	finally
  }
  
  public static Locale getSysLocale(Context paramContext) {
    if (Build.VERSION.SDK_INT >= 24) {
      LocaleList localeList = LocaleList.getDefault();
      if (localeList != null && !localeList.isEmpty())
        return localeList.get(0); 
    } 
    return Locale.getDefault();
  }
  
  public static boolean isRTL(Context paramContext) {
    return (paramContext != null && Build.VERSION.SDK_INT >= 17 && paramContext.getResources().getConfiguration().getLayoutDirection() == 1);
  }
  
  public Locale getCurrentHostSetLocale() {
    return this.currentHostSetLocale;
  }
  
  public String getCurrentLang() {
    Locale locale = getCurrentLocale();
    return (locale == null) ? null : locale.getLanguage();
  }
  
  public Locale getCurrentLocale() {
    Locale locale = this.currentHostSetLocale;
    return (locale != null) ? locale : getSysLocale((Context)AppbrandContext.getInst().getApplicationContext());
  }
  
  public boolean isInCNLang() {
    try {
      return TextUtils.equals(Locale.CHINESE.getLanguage(), getCurrentLocale().getLanguage());
    } finally {
      Exception exception = null;
      AppBrandLogger.eWithThrowable("LocaleManager", "isInCNLang", exception);
    } 
  }
  
  public boolean isRTL() {
    return this.isRTL;
  }
  
  public void notifyLangChange(Locale paramLocale) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: aload_1
    //   4: iconst_0
    //   5: invokevirtual notifyLangChange : (Ljava/util/Locale;Z)V
    //   8: aload_0
    //   9: monitorexit
    //   10: return
    //   11: astore_1
    //   12: aload_0
    //   13: monitorexit
    //   14: aload_1
    //   15: athrow
    // Exception table:
    //   from	to	target	type
    //   2	8	11	finally
  }
  
  public void notifyLangChange(Locale paramLocale, boolean paramBoolean) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: iload_2
    //   3: ifne -> 35
    //   6: aload_0
    //   7: getfield currentHostSetLocale : Ljava/util/Locale;
    //   10: ifnull -> 35
    //   13: aload_1
    //   14: invokevirtual toString : ()Ljava/lang/String;
    //   17: aload_0
    //   18: getfield currentHostSetLocale : Ljava/util/Locale;
    //   21: invokevirtual toString : ()Ljava/lang/String;
    //   24: invokestatic equals : (Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Z
    //   27: istore_2
    //   28: iload_2
    //   29: ifeq -> 35
    //   32: aload_0
    //   33: monitorexit
    //   34: return
    //   35: aload_0
    //   36: aload_1
    //   37: putfield currentHostSetLocale : Ljava/util/Locale;
    //   40: new java/lang/StringBuilder
    //   43: dup
    //   44: ldc 'notifyLangChange:'
    //   46: invokespecial <init> : (Ljava/lang/String;)V
    //   49: astore_3
    //   50: aload_3
    //   51: aload_1
    //   52: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   55: pop
    //   56: ldc 'LocaleManager'
    //   58: iconst_1
    //   59: anewarray java/lang/Object
    //   62: dup
    //   63: iconst_0
    //   64: aload_3
    //   65: invokevirtual toString : ()Ljava/lang/String;
    //   68: aastore
    //   69: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   72: invokestatic getInst : ()Lcom/tt/miniapphost/AppbrandContext;
    //   75: invokevirtual getApplicationContext : ()Landroid/app/Application;
    //   78: astore_1
    //   79: aload_1
    //   80: invokestatic updateResourceLocale : (Landroid/content/Context;)V
    //   83: new java/lang/StringBuilder
    //   86: dup
    //   87: ldc 'isRTL:'
    //   89: invokespecial <init> : (Ljava/lang/String;)V
    //   92: astore_3
    //   93: aload_3
    //   94: aload_0
    //   95: getfield isRTL : Z
    //   98: invokevirtual append : (Z)Ljava/lang/StringBuilder;
    //   101: pop
    //   102: ldc 'LocaleManager'
    //   104: iconst_1
    //   105: anewarray java/lang/Object
    //   108: dup
    //   109: iconst_0
    //   110: aload_3
    //   111: invokevirtual toString : ()Ljava/lang/String;
    //   114: aastore
    //   115: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   118: aload_0
    //   119: aload_1
    //   120: invokestatic isRTL : (Landroid/content/Context;)Z
    //   123: putfield isRTL : Z
    //   126: aload_0
    //   127: getfield listeners : Ljava/util/List;
    //   130: invokeinterface iterator : ()Ljava/util/Iterator;
    //   135: astore_1
    //   136: aload_1
    //   137: invokeinterface hasNext : ()Z
    //   142: ifeq -> 238
    //   145: aload_1
    //   146: invokeinterface next : ()Ljava/lang/Object;
    //   151: checkcast java/lang/ref/WeakReference
    //   154: invokevirtual get : ()Ljava/lang/Object;
    //   157: checkcast com/tt/miniapphost/language/LanguageChangeListener
    //   160: astore_3
    //   161: aload_3
    //   162: ifnonnull -> 188
    //   165: aload_1
    //   166: invokeinterface remove : ()V
    //   171: ldc 'LocaleManager'
    //   173: iconst_1
    //   174: anewarray java/lang/Object
    //   177: dup
    //   178: iconst_0
    //   179: ldc 'recycle refer'
    //   181: aastore
    //   182: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   185: goto -> 136
    //   188: new java/lang/StringBuilder
    //   191: dup
    //   192: ldc 'listener:'
    //   194: invokespecial <init> : (Ljava/lang/String;)V
    //   197: astore #4
    //   199: aload #4
    //   201: aload_3
    //   202: invokevirtual getClass : ()Ljava/lang/Class;
    //   205: invokevirtual getSimpleName : ()Ljava/lang/String;
    //   208: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   211: pop
    //   212: ldc 'LocaleManager'
    //   214: iconst_1
    //   215: anewarray java/lang/Object
    //   218: dup
    //   219: iconst_0
    //   220: aload #4
    //   222: invokevirtual toString : ()Ljava/lang/String;
    //   225: aastore
    //   226: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   229: aload_3
    //   230: invokeinterface onLanguageChange : ()V
    //   235: goto -> 136
    //   238: aload_0
    //   239: monitorexit
    //   240: return
    //   241: astore_1
    //   242: aload_0
    //   243: monitorexit
    //   244: goto -> 249
    //   247: aload_1
    //   248: athrow
    //   249: goto -> 247
    // Exception table:
    //   from	to	target	type
    //   6	28	241	finally
    //   35	136	241	finally
    //   136	161	241	finally
    //   165	185	241	finally
    //   188	235	241	finally
  }
  
  public void registerLangChangeListener(LanguageChangeListener paramLanguageChangeListener) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: aload_1
    //   4: iconst_0
    //   5: invokevirtual registerLangChangeListener : (Lcom/tt/miniapphost/language/LanguageChangeListener;Z)V
    //   8: aload_0
    //   9: monitorexit
    //   10: return
    //   11: astore_1
    //   12: aload_0
    //   13: monitorexit
    //   14: aload_1
    //   15: athrow
    // Exception table:
    //   from	to	target	type
    //   2	8	11	finally
  }
  
  public void registerLangChangeListener(LanguageChangeListener paramLanguageChangeListener, boolean paramBoolean) {
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
    //   11: invokespecial contains : (Lcom/tt/miniapphost/language/LanguageChangeListener;)Z
    //   14: istore_3
    //   15: iload_3
    //   16: ifeq -> 22
    //   19: aload_0
    //   20: monitorexit
    //   21: return
    //   22: new java/lang/StringBuilder
    //   25: dup
    //   26: ldc 'registerLangChangeListener:'
    //   28: invokespecial <init> : (Ljava/lang/String;)V
    //   31: astore #4
    //   33: aload #4
    //   35: aload_1
    //   36: invokevirtual getClass : ()Ljava/lang/Class;
    //   39: invokevirtual getSimpleName : ()Ljava/lang/String;
    //   42: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   45: pop
    //   46: ldc 'LocaleManager'
    //   48: iconst_1
    //   49: anewarray java/lang/Object
    //   52: dup
    //   53: iconst_0
    //   54: aload #4
    //   56: invokevirtual toString : ()Ljava/lang/String;
    //   59: aastore
    //   60: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   63: new java/lang/ref/WeakReference
    //   66: dup
    //   67: aload_1
    //   68: invokespecial <init> : (Ljava/lang/Object;)V
    //   71: astore_1
    //   72: iload_2
    //   73: ifeq -> 90
    //   76: aload_0
    //   77: getfield listeners : Ljava/util/List;
    //   80: iconst_0
    //   81: aload_1
    //   82: invokeinterface add : (ILjava/lang/Object;)V
    //   87: aload_0
    //   88: monitorexit
    //   89: return
    //   90: aload_0
    //   91: getfield listeners : Ljava/util/List;
    //   94: aload_1
    //   95: invokeinterface add : (Ljava/lang/Object;)Z
    //   100: pop
    //   101: aload_0
    //   102: monitorexit
    //   103: return
    //   104: astore_1
    //   105: aload_0
    //   106: monitorexit
    //   107: aload_1
    //   108: athrow
    // Exception table:
    //   from	to	target	type
    //   9	15	104	finally
    //   22	72	104	finally
    //   76	87	104	finally
    //   90	101	104	finally
  }
  
  public void unreigsterLangChangeListener(LanguageChangeListener paramLanguageChangeListener) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_1
    //   3: ifnonnull -> 9
    //   6: aload_0
    //   7: monitorexit
    //   8: return
    //   9: aload_0
    //   10: getfield listeners : Ljava/util/List;
    //   13: invokeinterface iterator : ()Ljava/util/Iterator;
    //   18: astore_2
    //   19: aload_2
    //   20: invokeinterface hasNext : ()Z
    //   25: ifeq -> 85
    //   28: aload_2
    //   29: invokeinterface next : ()Ljava/lang/Object;
    //   34: checkcast java/lang/ref/WeakReference
    //   37: invokevirtual get : ()Ljava/lang/Object;
    //   40: checkcast com/tt/miniapphost/language/LanguageChangeListener
    //   43: astore_3
    //   44: aload_3
    //   45: ifnonnull -> 71
    //   48: aload_2
    //   49: invokeinterface remove : ()V
    //   54: ldc 'LocaleManager'
    //   56: iconst_1
    //   57: anewarray java/lang/Object
    //   60: dup
    //   61: iconst_0
    //   62: ldc 'recycle refer'
    //   64: aastore
    //   65: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   68: goto -> 19
    //   71: aload_3
    //   72: aload_1
    //   73: if_acmpne -> 19
    //   76: aload_2
    //   77: invokeinterface remove : ()V
    //   82: goto -> 19
    //   85: aload_0
    //   86: monitorexit
    //   87: return
    //   88: astore_1
    //   89: aload_0
    //   90: monitorexit
    //   91: goto -> 96
    //   94: aload_1
    //   95: athrow
    //   96: goto -> 94
    // Exception table:
    //   from	to	target	type
    //   9	19	88	finally
    //   19	44	88	finally
    //   48	68	88	finally
    //   76	82	88	finally
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapphost\language\LocaleManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */