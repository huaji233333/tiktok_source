package com.tt.miniapp.database.recentapps;

import android.database.sqlite.SQLiteOpenHelper;
import com.tt.miniapp.database.base.BaseDao;
import com.tt.miniapphost.entity.AppLaunchInfo;
import java.util.List;

public class RecentAppsDao extends BaseDao {
  public RecentAppsDao(SQLiteOpenHelper paramSQLiteOpenHelper) {
    super(paramSQLiteOpenHelper);
  }
  
  public void addAllData(List<AppLaunchInfo> paramList) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_1
    //   3: ifnull -> 278
    //   6: aload_1
    //   7: invokeinterface size : ()I
    //   12: istore_2
    //   13: iload_2
    //   14: ifne -> 20
    //   17: goto -> 278
    //   20: aload_0
    //   21: invokevirtual beginTrans : ()V
    //   24: aload_0
    //   25: getfield db : Landroid/database/sqlite/SQLiteDatabase;
    //   28: ldc 'replace into TB_RECENT_APPS(appID,appName,ttid,icon,type,orientation,mark,minJssdk,schema,state,summary,timestamp)values(?,?,?,?,?,?,?,?,?,?,?,?)'
    //   30: invokevirtual compileStatement : (Ljava/lang/String;)Landroid/database/sqlite/SQLiteStatement;
    //   33: astore_3
    //   34: aload_1
    //   35: invokeinterface iterator : ()Ljava/util/Iterator;
    //   40: astore #4
    //   42: aload #4
    //   44: invokeinterface hasNext : ()Z
    //   49: ifeq -> 203
    //   52: aload #4
    //   54: invokeinterface next : ()Ljava/lang/Object;
    //   59: checkcast com/tt/miniapphost/entity/AppLaunchInfo
    //   62: astore #5
    //   64: aload_3
    //   65: iconst_1
    //   66: aload #5
    //   68: getfield appId : Ljava/lang/String;
    //   71: invokevirtual bindString : (ILjava/lang/String;)V
    //   74: aload_3
    //   75: iconst_2
    //   76: aload #5
    //   78: getfield appName : Ljava/lang/String;
    //   81: invokevirtual bindString : (ILjava/lang/String;)V
    //   84: aload_3
    //   85: iconst_3
    //   86: aload #5
    //   88: getfield ttid : Ljava/lang/String;
    //   91: invokevirtual bindString : (ILjava/lang/String;)V
    //   94: aload_3
    //   95: iconst_4
    //   96: aload #5
    //   98: getfield icon : Ljava/lang/String;
    //   101: invokevirtual bindString : (ILjava/lang/String;)V
    //   104: aload_3
    //   105: iconst_5
    //   106: aload #5
    //   108: getfield type : I
    //   111: i2l
    //   112: invokevirtual bindLong : (IJ)V
    //   115: aload_3
    //   116: bipush #6
    //   118: aload #5
    //   120: getfield orientation : I
    //   123: i2l
    //   124: invokevirtual bindLong : (IJ)V
    //   127: aload_3
    //   128: bipush #7
    //   130: aload #5
    //   132: getfield mark : I
    //   135: i2l
    //   136: invokevirtual bindLong : (IJ)V
    //   139: aload_3
    //   140: bipush #8
    //   142: aload #5
    //   144: getfield minJssdk : Ljava/lang/String;
    //   147: invokevirtual bindString : (ILjava/lang/String;)V
    //   150: aload_3
    //   151: bipush #9
    //   153: aload #5
    //   155: getfield schema : Ljava/lang/String;
    //   158: invokevirtual bindString : (ILjava/lang/String;)V
    //   161: aload_3
    //   162: bipush #10
    //   164: aload #5
    //   166: getfield state : I
    //   169: i2l
    //   170: invokevirtual bindLong : (IJ)V
    //   173: aload_3
    //   174: bipush #11
    //   176: aload #5
    //   178: getfield summary : Ljava/lang/String;
    //   181: invokevirtual bindString : (ILjava/lang/String;)V
    //   184: aload_3
    //   185: bipush #12
    //   187: aload #5
    //   189: getfield timestamp : J
    //   192: invokevirtual bindLong : (IJ)V
    //   195: aload_3
    //   196: invokevirtual executeInsert : ()J
    //   199: pop2
    //   200: goto -> 42
    //   203: new java/lang/StringBuilder
    //   206: dup
    //   207: ldc 'data size is '
    //   209: invokespecial <init> : (Ljava/lang/String;)V
    //   212: astore_3
    //   213: aload_3
    //   214: aload_1
    //   215: invokeinterface size : ()I
    //   220: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   223: pop
    //   224: ldc 'RecentAppsDao'
    //   226: iconst_1
    //   227: anewarray java/lang/Object
    //   230: dup
    //   231: iconst_0
    //   232: aload_3
    //   233: invokevirtual toString : ()Ljava/lang/String;
    //   236: aastore
    //   237: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   240: aload_0
    //   241: invokevirtual commit : ()V
    //   244: aload_0
    //   245: monitorexit
    //   246: return
    //   247: astore_1
    //   248: goto -> 272
    //   251: astore_1
    //   252: ldc 'RecentAppsDao'
    //   254: iconst_1
    //   255: anewarray java/lang/Object
    //   258: dup
    //   259: iconst_0
    //   260: aload_1
    //   261: aastore
    //   262: invokestatic e : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   265: aload_0
    //   266: invokevirtual commit : ()V
    //   269: aload_0
    //   270: monitorexit
    //   271: return
    //   272: aload_0
    //   273: invokevirtual commit : ()V
    //   276: aload_1
    //   277: athrow
    //   278: ldc 'RecentAppsDao'
    //   280: iconst_1
    //   281: anewarray java/lang/Object
    //   284: dup
    //   285: iconst_0
    //   286: ldc 'no data to add'
    //   288: aastore
    //   289: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   292: aload_0
    //   293: monitorexit
    //   294: return
    //   295: astore_1
    //   296: aload_0
    //   297: monitorexit
    //   298: goto -> 303
    //   301: aload_1
    //   302: athrow
    //   303: goto -> 301
    // Exception table:
    //   from	to	target	type
    //   6	13	295	finally
    //   20	42	251	java/lang/Exception
    //   20	42	247	finally
    //   42	200	251	java/lang/Exception
    //   42	200	247	finally
    //   203	240	251	java/lang/Exception
    //   203	240	247	finally
    //   240	244	295	finally
    //   252	265	247	finally
    //   265	269	295	finally
    //   272	278	295	finally
    //   278	292	295	finally
  }
  
  public void addRecentApp(AppLaunchInfo paramAppLaunchInfo) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: invokevirtual beginTrans : ()V
    //   6: new android/content/ContentValues
    //   9: dup
    //   10: invokespecial <init> : ()V
    //   13: astore_2
    //   14: aload_1
    //   15: ifnull -> 186
    //   18: aload_2
    //   19: ldc 'appID'
    //   21: aload_1
    //   22: getfield appId : Ljava/lang/String;
    //   25: invokevirtual put : (Ljava/lang/String;Ljava/lang/String;)V
    //   28: aload_2
    //   29: ldc 'appName'
    //   31: aload_1
    //   32: getfield appName : Ljava/lang/String;
    //   35: invokevirtual put : (Ljava/lang/String;Ljava/lang/String;)V
    //   38: aload_2
    //   39: ldc 'ttid'
    //   41: aload_1
    //   42: getfield ttid : Ljava/lang/String;
    //   45: invokevirtual put : (Ljava/lang/String;Ljava/lang/String;)V
    //   48: aload_2
    //   49: ldc 'icon'
    //   51: aload_1
    //   52: getfield icon : Ljava/lang/String;
    //   55: invokevirtual put : (Ljava/lang/String;Ljava/lang/String;)V
    //   58: aload_2
    //   59: ldc 'type'
    //   61: aload_1
    //   62: getfield type : I
    //   65: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   68: invokevirtual put : (Ljava/lang/String;Ljava/lang/Integer;)V
    //   71: aload_2
    //   72: ldc 'orientation'
    //   74: aload_1
    //   75: getfield orientation : I
    //   78: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   81: invokevirtual put : (Ljava/lang/String;Ljava/lang/Integer;)V
    //   84: aload_2
    //   85: ldc 'mark'
    //   87: aload_1
    //   88: getfield mark : I
    //   91: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   94: invokevirtual put : (Ljava/lang/String;Ljava/lang/Integer;)V
    //   97: aload_2
    //   98: ldc 'minJssdk'
    //   100: aload_1
    //   101: getfield minJssdk : Ljava/lang/String;
    //   104: invokevirtual put : (Ljava/lang/String;Ljava/lang/String;)V
    //   107: aload_2
    //   108: ldc 'schema'
    //   110: aload_1
    //   111: getfield schema : Ljava/lang/String;
    //   114: invokevirtual put : (Ljava/lang/String;Ljava/lang/String;)V
    //   117: aload_2
    //   118: ldc 'state'
    //   120: aload_1
    //   121: getfield state : I
    //   124: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   127: invokevirtual put : (Ljava/lang/String;Ljava/lang/Integer;)V
    //   130: aload_2
    //   131: ldc 'summary'
    //   133: aload_1
    //   134: getfield summary : Ljava/lang/String;
    //   137: invokevirtual put : (Ljava/lang/String;Ljava/lang/String;)V
    //   140: aload_2
    //   141: ldc 'timestamp'
    //   143: aload_1
    //   144: getfield timestamp : J
    //   147: invokestatic valueOf : (J)Ljava/lang/Long;
    //   150: invokevirtual put : (Ljava/lang/String;Ljava/lang/Long;)V
    //   153: ldc 'RecentAppsDao'
    //   155: iconst_4
    //   156: anewarray java/lang/Object
    //   159: dup
    //   160: iconst_0
    //   161: ldc 'appId:'
    //   163: aastore
    //   164: dup
    //   165: iconst_1
    //   166: aload_1
    //   167: getfield appId : Ljava/lang/String;
    //   170: aastore
    //   171: dup
    //   172: iconst_2
    //   173: ldc 'appName:'
    //   175: aastore
    //   176: dup
    //   177: iconst_3
    //   178: aload_1
    //   179: getfield appName : Ljava/lang/String;
    //   182: aastore
    //   183: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   186: aload_0
    //   187: getfield db : Landroid/database/sqlite/SQLiteDatabase;
    //   190: ldc 'TB_RECENT_APPS'
    //   192: aconst_null
    //   193: aload_2
    //   194: invokevirtual replace : (Ljava/lang/String;Ljava/lang/String;Landroid/content/ContentValues;)J
    //   197: pop2
    //   198: aload_0
    //   199: invokevirtual commit : ()V
    //   202: aload_0
    //   203: monitorexit
    //   204: return
    //   205: astore_1
    //   206: aload_0
    //   207: monitorexit
    //   208: aload_1
    //   209: athrow
    // Exception table:
    //   from	to	target	type
    //   2	14	205	finally
    //   18	186	205	finally
    //   186	202	205	finally
  }
  
  public void clearRecentApp() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: invokevirtual beginTrans : ()V
    //   6: ldc 'delete from %s'
    //   8: iconst_1
    //   9: anewarray java/lang/Object
    //   12: dup
    //   13: iconst_0
    //   14: ldc 'TB_RECENT_APPS'
    //   16: aastore
    //   17: invokestatic a : (Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   20: astore_1
    //   21: aload_0
    //   22: getfield db : Landroid/database/sqlite/SQLiteDatabase;
    //   25: aload_1
    //   26: invokevirtual execSQL : (Ljava/lang/String;)V
    //   29: aload_0
    //   30: invokevirtual commit : ()V
    //   33: aload_0
    //   34: monitorexit
    //   35: return
    //   36: astore_1
    //   37: goto -> 61
    //   40: astore_1
    //   41: ldc 'RecentAppsDao'
    //   43: iconst_1
    //   44: anewarray java/lang/Object
    //   47: dup
    //   48: iconst_0
    //   49: aload_1
    //   50: aastore
    //   51: invokestatic e : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   54: aload_0
    //   55: invokevirtual commit : ()V
    //   58: aload_0
    //   59: monitorexit
    //   60: return
    //   61: aload_0
    //   62: invokevirtual commit : ()V
    //   65: aload_1
    //   66: athrow
    //   67: astore_1
    //   68: aload_0
    //   69: monitorexit
    //   70: aload_1
    //   71: athrow
    // Exception table:
    //   from	to	target	type
    //   2	29	40	java/lang/Exception
    //   2	29	36	finally
    //   29	33	67	finally
    //   41	54	36	finally
    //   54	58	67	finally
    //   61	67	67	finally
  }
  
  public List<AppLaunchInfo> getRecentApps() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: new java/util/ArrayList
    //   5: dup
    //   6: invokespecial <init> : ()V
    //   9: astore_3
    //   10: aload_0
    //   11: aload_0
    //   12: getfield helper : Landroid/database/sqlite/SQLiteOpenHelper;
    //   15: invokevirtual getReadableDatabase : ()Landroid/database/sqlite/SQLiteDatabase;
    //   18: putfield db : Landroid/database/sqlite/SQLiteDatabase;
    //   21: ldc 'select * from %s ORDER BY %s DESC'
    //   23: iconst_2
    //   24: anewarray java/lang/Object
    //   27: dup
    //   28: iconst_0
    //   29: ldc 'TB_RECENT_APPS'
    //   31: aastore
    //   32: dup
    //   33: iconst_1
    //   34: ldc 'timestamp'
    //   36: aastore
    //   37: invokestatic a : (Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   40: astore_2
    //   41: aload_0
    //   42: aload_0
    //   43: getfield db : Landroid/database/sqlite/SQLiteDatabase;
    //   46: aload_2
    //   47: aconst_null
    //   48: invokevirtual rawQuery : (Ljava/lang/String;[Ljava/lang/String;)Landroid/database/Cursor;
    //   51: putfield cursor : Landroid/database/Cursor;
    //   54: aload_0
    //   55: getfield cursor : Landroid/database/Cursor;
    //   58: invokeinterface moveToNext : ()Z
    //   63: ifeq -> 391
    //   66: new com/tt/miniapphost/entity/AppLaunchInfo
    //   69: dup
    //   70: invokespecial <init> : ()V
    //   73: astore_2
    //   74: aload_2
    //   75: aload_0
    //   76: getfield cursor : Landroid/database/Cursor;
    //   79: aload_0
    //   80: getfield cursor : Landroid/database/Cursor;
    //   83: ldc 'appID'
    //   85: invokeinterface getColumnIndex : (Ljava/lang/String;)I
    //   90: invokeinterface getString : (I)Ljava/lang/String;
    //   95: putfield appId : Ljava/lang/String;
    //   98: aload_2
    //   99: aload_0
    //   100: getfield cursor : Landroid/database/Cursor;
    //   103: aload_0
    //   104: getfield cursor : Landroid/database/Cursor;
    //   107: ldc 'appName'
    //   109: invokeinterface getColumnIndex : (Ljava/lang/String;)I
    //   114: invokeinterface getString : (I)Ljava/lang/String;
    //   119: putfield appName : Ljava/lang/String;
    //   122: aload_2
    //   123: aload_0
    //   124: getfield cursor : Landroid/database/Cursor;
    //   127: aload_0
    //   128: getfield cursor : Landroid/database/Cursor;
    //   131: ldc 'ttid'
    //   133: invokeinterface getColumnIndex : (Ljava/lang/String;)I
    //   138: invokeinterface getString : (I)Ljava/lang/String;
    //   143: putfield ttid : Ljava/lang/String;
    //   146: aload_2
    //   147: aload_0
    //   148: getfield cursor : Landroid/database/Cursor;
    //   151: aload_0
    //   152: getfield cursor : Landroid/database/Cursor;
    //   155: ldc 'icon'
    //   157: invokeinterface getColumnIndex : (Ljava/lang/String;)I
    //   162: invokeinterface getString : (I)Ljava/lang/String;
    //   167: putfield icon : Ljava/lang/String;
    //   170: aload_2
    //   171: aload_0
    //   172: getfield cursor : Landroid/database/Cursor;
    //   175: aload_0
    //   176: getfield cursor : Landroid/database/Cursor;
    //   179: ldc 'type'
    //   181: invokeinterface getColumnIndex : (Ljava/lang/String;)I
    //   186: invokeinterface getInt : (I)I
    //   191: putfield type : I
    //   194: aload_2
    //   195: aload_0
    //   196: getfield cursor : Landroid/database/Cursor;
    //   199: aload_0
    //   200: getfield cursor : Landroid/database/Cursor;
    //   203: ldc 'orientation'
    //   205: invokeinterface getColumnIndex : (Ljava/lang/String;)I
    //   210: invokeinterface getInt : (I)I
    //   215: putfield orientation : I
    //   218: aload_2
    //   219: aload_0
    //   220: getfield cursor : Landroid/database/Cursor;
    //   223: aload_0
    //   224: getfield cursor : Landroid/database/Cursor;
    //   227: ldc 'mark'
    //   229: invokeinterface getColumnIndex : (Ljava/lang/String;)I
    //   234: invokeinterface getInt : (I)I
    //   239: putfield mark : I
    //   242: aload_2
    //   243: aload_0
    //   244: getfield cursor : Landroid/database/Cursor;
    //   247: aload_0
    //   248: getfield cursor : Landroid/database/Cursor;
    //   251: ldc 'minJssdk'
    //   253: invokeinterface getColumnIndex : (Ljava/lang/String;)I
    //   258: invokeinterface getString : (I)Ljava/lang/String;
    //   263: putfield minJssdk : Ljava/lang/String;
    //   266: aload_2
    //   267: aload_0
    //   268: getfield cursor : Landroid/database/Cursor;
    //   271: aload_0
    //   272: getfield cursor : Landroid/database/Cursor;
    //   275: ldc 'schema'
    //   277: invokeinterface getColumnIndex : (Ljava/lang/String;)I
    //   282: invokeinterface getString : (I)Ljava/lang/String;
    //   287: putfield schema : Ljava/lang/String;
    //   290: aload_2
    //   291: aload_0
    //   292: getfield cursor : Landroid/database/Cursor;
    //   295: aload_0
    //   296: getfield cursor : Landroid/database/Cursor;
    //   299: ldc 'state'
    //   301: invokeinterface getColumnIndex : (Ljava/lang/String;)I
    //   306: invokeinterface getInt : (I)I
    //   311: putfield state : I
    //   314: aload_2
    //   315: aload_0
    //   316: getfield cursor : Landroid/database/Cursor;
    //   319: aload_0
    //   320: getfield cursor : Landroid/database/Cursor;
    //   323: ldc 'summary'
    //   325: invokeinterface getColumnIndex : (Ljava/lang/String;)I
    //   330: invokeinterface getString : (I)Ljava/lang/String;
    //   335: putfield summary : Ljava/lang/String;
    //   338: aload_2
    //   339: aload_0
    //   340: getfield cursor : Landroid/database/Cursor;
    //   343: aload_0
    //   344: getfield cursor : Landroid/database/Cursor;
    //   347: ldc 'timestamp'
    //   349: invokeinterface getColumnIndex : (Ljava/lang/String;)I
    //   354: invokeinterface getLong : (I)J
    //   359: putfield timestamp : J
    //   362: aload_2
    //   363: getfield type : I
    //   366: iconst_2
    //   367: if_icmpne -> 465
    //   370: iconst_1
    //   371: istore_1
    //   372: goto -> 375
    //   375: aload_2
    //   376: iload_1
    //   377: putfield isGame : Z
    //   380: aload_3
    //   381: aload_2
    //   382: invokeinterface add : (Ljava/lang/Object;)Z
    //   387: pop
    //   388: goto -> 54
    //   391: aload_0
    //   392: invokevirtual closeCursor : ()V
    //   395: aload_0
    //   396: getfield db : Landroid/database/sqlite/SQLiteDatabase;
    //   399: astore_2
    //   400: aload_2
    //   401: invokevirtual close : ()V
    //   404: goto -> 437
    //   407: astore_2
    //   408: goto -> 441
    //   411: astore_2
    //   412: ldc 'RecentAppsDao'
    //   414: iconst_1
    //   415: anewarray java/lang/Object
    //   418: dup
    //   419: iconst_0
    //   420: aload_2
    //   421: aastore
    //   422: invokestatic e : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   425: aload_0
    //   426: invokevirtual closeCursor : ()V
    //   429: aload_0
    //   430: getfield db : Landroid/database/sqlite/SQLiteDatabase;
    //   433: astore_2
    //   434: goto -> 400
    //   437: aload_0
    //   438: monitorexit
    //   439: aload_3
    //   440: areturn
    //   441: aload_0
    //   442: invokevirtual closeCursor : ()V
    //   445: aload_0
    //   446: getfield db : Landroid/database/sqlite/SQLiteDatabase;
    //   449: invokevirtual close : ()V
    //   452: aload_2
    //   453: athrow
    //   454: astore_2
    //   455: aload_0
    //   456: monitorexit
    //   457: goto -> 462
    //   460: aload_2
    //   461: athrow
    //   462: goto -> 460
    //   465: iconst_0
    //   466: istore_1
    //   467: goto -> 375
    // Exception table:
    //   from	to	target	type
    //   2	10	454	finally
    //   10	54	411	java/lang/Exception
    //   10	54	407	finally
    //   54	370	411	java/lang/Exception
    //   54	370	407	finally
    //   375	388	411	java/lang/Exception
    //   375	388	407	finally
    //   391	400	454	finally
    //   400	404	454	finally
    //   412	425	407	finally
    //   425	434	454	finally
    //   441	454	454	finally
  }
  
  public void removeRecentApp(String paramString) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: invokevirtual beginTrans : ()V
    //   6: ldc_w 'delete from %s where appID = '%s''
    //   9: iconst_2
    //   10: anewarray java/lang/Object
    //   13: dup
    //   14: iconst_0
    //   15: ldc 'TB_RECENT_APPS'
    //   17: aastore
    //   18: dup
    //   19: iconst_1
    //   20: aload_1
    //   21: aastore
    //   22: invokestatic a : (Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   25: astore_1
    //   26: aload_0
    //   27: getfield db : Landroid/database/sqlite/SQLiteDatabase;
    //   30: aload_1
    //   31: invokevirtual execSQL : (Ljava/lang/String;)V
    //   34: aload_0
    //   35: invokevirtual commit : ()V
    //   38: aload_0
    //   39: monitorexit
    //   40: return
    //   41: astore_1
    //   42: goto -> 66
    //   45: astore_1
    //   46: ldc 'RecentAppsDao'
    //   48: iconst_1
    //   49: anewarray java/lang/Object
    //   52: dup
    //   53: iconst_0
    //   54: aload_1
    //   55: aastore
    //   56: invokestatic e : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   59: aload_0
    //   60: invokevirtual commit : ()V
    //   63: aload_0
    //   64: monitorexit
    //   65: return
    //   66: aload_0
    //   67: invokevirtual commit : ()V
    //   70: aload_1
    //   71: athrow
    //   72: astore_1
    //   73: aload_0
    //   74: monitorexit
    //   75: aload_1
    //   76: athrow
    // Exception table:
    //   from	to	target	type
    //   2	34	45	java/lang/Exception
    //   2	34	41	finally
    //   34	38	72	finally
    //   46	59	41	finally
    //   59	63	72	finally
    //   66	72	72	finally
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\database\recentapps\RecentAppsDao.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */