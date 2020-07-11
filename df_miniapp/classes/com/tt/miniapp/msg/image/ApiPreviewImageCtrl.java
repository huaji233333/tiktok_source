package com.tt.miniapp.msg.image;

import com.tt.frontendapiinterface.b;
import com.tt.option.e.e;

public class ApiPreviewImageCtrl extends b {
  String TAG = "ApiPreviewImageCtrl";
  
  public ApiPreviewImageCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public void act() {
    // Byte code:
    //   0: new org/json/JSONObject
    //   3: dup
    //   4: aload_0
    //   5: getfield mArgs : Ljava/lang/String;
    //   8: invokespecial <init> : (Ljava/lang/String;)V
    //   11: astore #7
    //   13: aload_0
    //   14: getfield TAG : Ljava/lang/String;
    //   17: iconst_2
    //   18: anewarray java/lang/Object
    //   21: dup
    //   22: iconst_0
    //   23: ldc 'doAct mApi '
    //   25: aastore
    //   26: dup
    //   27: iconst_1
    //   28: aload_0
    //   29: getfield mArgs : Ljava/lang/String;
    //   32: aastore
    //   33: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   36: aload #7
    //   38: ldc 'current'
    //   40: invokevirtual optString : (Ljava/lang/String;)Ljava/lang/String;
    //   43: astore #5
    //   45: aload #5
    //   47: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   50: istore_3
    //   51: aload #5
    //   53: astore #4
    //   55: iload_3
    //   56: ifne -> 128
    //   59: aload #5
    //   61: astore #4
    //   63: aload #5
    //   65: ldc 'ttfile://'
    //   67: invokevirtual startsWith : (Ljava/lang/String;)Z
    //   70: ifeq -> 128
    //   73: invokestatic inst : ()Lcom/tt/miniapp/storage/filestorge/FileManager;
    //   76: aload #5
    //   78: invokevirtual getRealFilePath : (Ljava/lang/String;)Ljava/lang/String;
    //   81: astore #6
    //   83: aload #5
    //   85: astore #4
    //   87: new java/io/File
    //   90: dup
    //   91: aload #6
    //   93: invokespecial <init> : (Ljava/lang/String;)V
    //   96: invokevirtual exists : ()Z
    //   99: ifeq -> 128
    //   102: new java/lang/StringBuilder
    //   105: dup
    //   106: ldc 'file://'
    //   108: invokespecial <init> : (Ljava/lang/String;)V
    //   111: astore #4
    //   113: aload #4
    //   115: aload #6
    //   117: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   120: pop
    //   121: aload #4
    //   123: invokevirtual toString : ()Ljava/lang/String;
    //   126: astore #4
    //   128: new java/util/ArrayList
    //   131: dup
    //   132: invokespecial <init> : ()V
    //   135: astore #6
    //   137: aload #7
    //   139: ldc 'urls'
    //   141: invokevirtual optJSONArray : (Ljava/lang/String;)Lorg/json/JSONArray;
    //   144: astore #5
    //   146: aload #5
    //   148: ifnull -> 346
    //   151: aload #5
    //   153: invokevirtual length : ()I
    //   156: istore_2
    //   157: iconst_0
    //   158: istore_1
    //   159: iload_1
    //   160: iload_2
    //   161: if_icmpge -> 346
    //   164: aload #5
    //   166: iload_1
    //   167: invokevirtual getString : (I)Ljava/lang/String;
    //   170: astore #7
    //   172: aload #7
    //   174: ldc 'file://'
    //   176: invokevirtual startsWith : (Ljava/lang/String;)Z
    //   179: ifeq -> 219
    //   182: new java/io/File
    //   185: dup
    //   186: aload #7
    //   188: bipush #7
    //   190: invokevirtual substring : (I)Ljava/lang/String;
    //   193: invokespecial <init> : (Ljava/lang/String;)V
    //   196: invokevirtual exists : ()Z
    //   199: ifeq -> 545
    //   202: aload #6
    //   204: aload #5
    //   206: iload_1
    //   207: invokevirtual getString : (I)Ljava/lang/String;
    //   210: invokeinterface add : (Ljava/lang/Object;)Z
    //   215: pop
    //   216: goto -> 545
    //   219: aload #7
    //   221: ldc 'http://'
    //   223: invokevirtual startsWith : (Ljava/lang/String;)Z
    //   226: ifne -> 333
    //   229: aload #7
    //   231: ldc 'https://'
    //   233: invokevirtual startsWith : (Ljava/lang/String;)Z
    //   236: ifeq -> 242
    //   239: goto -> 333
    //   242: aload #7
    //   244: ldc 'ttfile://'
    //   246: invokevirtual startsWith : (Ljava/lang/String;)Z
    //   249: ifeq -> 312
    //   252: invokestatic inst : ()Lcom/tt/miniapp/storage/filestorge/FileManager;
    //   255: aload #7
    //   257: invokevirtual getRealFilePath : (Ljava/lang/String;)Ljava/lang/String;
    //   260: astore #7
    //   262: new java/io/File
    //   265: dup
    //   266: aload #7
    //   268: invokespecial <init> : (Ljava/lang/String;)V
    //   271: invokevirtual exists : ()Z
    //   274: ifeq -> 545
    //   277: new java/lang/StringBuilder
    //   280: dup
    //   281: ldc 'file://'
    //   283: invokespecial <init> : (Ljava/lang/String;)V
    //   286: astore #8
    //   288: aload #8
    //   290: aload #7
    //   292: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   295: pop
    //   296: aload #6
    //   298: aload #8
    //   300: invokevirtual toString : ()Ljava/lang/String;
    //   303: invokeinterface add : (Ljava/lang/Object;)Z
    //   308: pop
    //   309: goto -> 545
    //   312: aload #7
    //   314: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   317: ifne -> 545
    //   320: aload #6
    //   322: aload #7
    //   324: invokeinterface add : (Ljava/lang/Object;)Z
    //   329: pop
    //   330: goto -> 545
    //   333: aload #6
    //   335: aload #7
    //   337: invokeinterface add : (Ljava/lang/Object;)Z
    //   342: pop
    //   343: goto -> 545
    //   346: aload #6
    //   348: invokeinterface size : ()I
    //   353: ifgt -> 366
    //   356: aload_0
    //   357: ldc 'urls'
    //   359: invokestatic b : (Ljava/lang/String;)Ljava/lang/String;
    //   362: invokevirtual callbackFail : (Ljava/lang/String;)V
    //   365: return
    //   366: aload #4
    //   368: astore #5
    //   370: aload #6
    //   372: aload #4
    //   374: invokeinterface contains : (Ljava/lang/Object;)Z
    //   379: ifne -> 395
    //   382: aload #6
    //   384: iconst_0
    //   385: invokeinterface get : (I)Ljava/lang/Object;
    //   390: checkcast java/lang/String
    //   393: astore #5
    //   395: aload #6
    //   397: invokeinterface size : ()I
    //   402: istore_2
    //   403: iconst_0
    //   404: istore_1
    //   405: iload_1
    //   406: iload_2
    //   407: if_icmpge -> 559
    //   410: aload #5
    //   412: aload #6
    //   414: iload_1
    //   415: invokeinterface get : (I)Ljava/lang/Object;
    //   420: checkcast java/lang/CharSequence
    //   423: invokestatic equals : (Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Z
    //   426: ifeq -> 552
    //   429: goto -> 432
    //   432: invokestatic getInst : ()Lcom/tt/miniapphost/AppbrandContext;
    //   435: invokevirtual getCurrentActivity : ()Lcom/tt/miniapphost/MiniappHostBase;
    //   438: astore #4
    //   440: aload #4
    //   442: ifnonnull -> 452
    //   445: aload_0
    //   446: ldc 'activity is null'
    //   448: invokevirtual callbackFail : (Ljava/lang/String;)V
    //   451: return
    //   452: aload #4
    //   454: invokevirtual isFinishing : ()Z
    //   457: ifeq -> 467
    //   460: aload_0
    //   461: ldc 'activity is finishing'
    //   463: invokevirtual callbackFail : (Ljava/lang/String;)V
    //   466: return
    //   467: aload_0
    //   468: getfield TAG : Ljava/lang/String;
    //   471: iconst_2
    //   472: anewarray java/lang/Object
    //   475: dup
    //   476: iconst_0
    //   477: ldc 'doAct mApi  index is :'
    //   479: aastore
    //   480: dup
    //   481: iconst_1
    //   482: iload_1
    //   483: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   486: aastore
    //   487: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   490: invokestatic getInst : ()Lcom/tt/miniapphost/host/HostDependManager;
    //   493: aload #4
    //   495: aload_0
    //   496: getfield mArgs : Ljava/lang/String;
    //   499: aload #6
    //   501: iload_1
    //   502: invokevirtual startImagePreviewActivity : (Landroid/app/Activity;Ljava/lang/String;Ljava/util/List;I)Z
    //   505: ifeq -> 513
    //   508: aload_0
    //   509: invokevirtual callbackOk : ()V
    //   512: return
    //   513: aload_0
    //   514: ldc 'start image preview activity fail'
    //   516: invokevirtual callbackFail : (Ljava/lang/String;)V
    //   519: return
    //   520: astore #4
    //   522: aload_0
    //   523: getfield TAG : Ljava/lang/String;
    //   526: iconst_1
    //   527: anewarray java/lang/Object
    //   530: dup
    //   531: iconst_0
    //   532: aload #4
    //   534: aastore
    //   535: invokestatic e : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   538: aload_0
    //   539: aload #4
    //   541: invokevirtual callbackFail : (Ljava/lang/Throwable;)V
    //   544: return
    //   545: iload_1
    //   546: iconst_1
    //   547: iadd
    //   548: istore_1
    //   549: goto -> 159
    //   552: iload_1
    //   553: iconst_1
    //   554: iadd
    //   555: istore_1
    //   556: goto -> 405
    //   559: iconst_0
    //   560: istore_1
    //   561: goto -> 432
    // Exception table:
    //   from	to	target	type
    //   0	51	520	java/lang/Exception
    //   63	83	520	java/lang/Exception
    //   87	128	520	java/lang/Exception
    //   128	146	520	java/lang/Exception
    //   151	157	520	java/lang/Exception
    //   164	216	520	java/lang/Exception
    //   219	239	520	java/lang/Exception
    //   242	309	520	java/lang/Exception
    //   312	330	520	java/lang/Exception
    //   333	343	520	java/lang/Exception
    //   346	365	520	java/lang/Exception
    //   370	395	520	java/lang/Exception
    //   395	403	520	java/lang/Exception
    //   410	429	520	java/lang/Exception
    //   432	440	520	java/lang/Exception
    //   445	451	520	java/lang/Exception
    //   452	466	520	java/lang/Exception
    //   467	512	520	java/lang/Exception
    //   513	519	520	java/lang/Exception
  }
  
  public String getActionName() {
    return "previewImage";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\image\ApiPreviewImageCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */