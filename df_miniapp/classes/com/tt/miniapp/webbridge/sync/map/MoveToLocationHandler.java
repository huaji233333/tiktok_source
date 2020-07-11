package com.tt.miniapp.webbridge.sync.map;

import com.tt.miniapp.WebViewManager;
import com.tt.miniapp.webbridge.WebEventHandler;

public class MoveToLocationHandler extends WebEventHandler {
  public MoveToLocationHandler(WebViewManager.IRender paramIRender, String paramString, int paramInt) {
    super(paramIRender, paramString, paramInt);
  }
  
  public String act() {
    // Byte code:
    //   0: aload_0
    //   1: getfield mRender : Lcom/tt/miniapp/WebViewManager$IRender;
    //   4: ifnonnull -> 14
    //   7: aload_0
    //   8: ldc 'render is null'
    //   10: invokevirtual makeFailMsg : (Ljava/lang/String;)Ljava/lang/String;
    //   13: areturn
    //   14: new org/json/JSONObject
    //   17: dup
    //   18: aload_0
    //   19: getfield mArgs : Ljava/lang/String;
    //   22: invokespecial <init> : (Ljava/lang/String;)V
    //   25: astore #7
    //   27: aload #7
    //   29: ldc 'mapId'
    //   31: invokevirtual optInt : (Ljava/lang/String;)I
    //   34: istore_3
    //   35: aload_0
    //   36: getfield mRender : Lcom/tt/miniapp/WebViewManager$IRender;
    //   39: invokeinterface getNativeViewManager : ()Lcom/tt/miniapp/component/nativeview/NativeViewManager;
    //   44: iload_3
    //   45: invokevirtual getView : (I)Landroid/view/View;
    //   48: astore #5
    //   50: aload #5
    //   52: instanceof com/tt/miniapp/component/nativeview/map/Map
    //   55: ifne -> 76
    //   58: aload_0
    //   59: invokevirtual getApiName : ()Ljava/lang/String;
    //   62: ldc 'invalid map id'
    //   64: bipush #103
    //   66: invokestatic a : (Ljava/lang/String;Ljava/lang/String;I)Lcom/tt/frontendapiinterface/ApiCallResult$a;
    //   69: invokevirtual a : ()Lcom/tt/frontendapiinterface/ApiCallResult;
    //   72: invokevirtual toString : ()Ljava/lang/String;
    //   75: areturn
    //   76: aload #5
    //   78: checkcast com/tt/miniapp/component/nativeview/map/Map
    //   81: astore #5
    //   83: new com/tt/option/m/c
    //   86: dup
    //   87: invokespecial <init> : ()V
    //   90: astore #6
    //   92: aload #7
    //   94: ldc 'latitude'
    //   96: invokevirtual has : (Ljava/lang/String;)Z
    //   99: istore #4
    //   101: iload #4
    //   103: ifne -> 232
    //   106: aload #7
    //   108: ldc 'longitude'
    //   110: invokevirtual has : (Ljava/lang/String;)Z
    //   113: ifne -> 232
    //   116: getstatic com/tt/miniapp/permission/BrandPermissionUtils$BrandPermission.LOCATION : Lcom/tt/miniapp/permission/BrandPermissionUtils$BrandPermission;
    //   119: invokevirtual getPermissionType : ()I
    //   122: invokestatic isGranted : (I)Z
    //   125: ifne -> 146
    //   128: aload_0
    //   129: invokevirtual getApiName : ()Ljava/lang/String;
    //   132: ldc 'no location permission'
    //   134: bipush #109
    //   136: invokestatic a : (Ljava/lang/String;Ljava/lang/String;I)Lcom/tt/frontendapiinterface/ApiCallResult$a;
    //   139: invokevirtual a : ()Lcom/tt/frontendapiinterface/ApiCallResult;
    //   142: invokevirtual toString : ()Ljava/lang/String;
    //   145: areturn
    //   146: aload #5
    //   148: invokevirtual getMapContext : ()Lcom/tt/option/m/a;
    //   151: invokeinterface a : ()Z
    //   156: ifne -> 178
    //   159: aload_0
    //   160: invokevirtual getApiName : ()Ljava/lang/String;
    //   163: ldc 'show-location is false'
    //   165: sipush #209
    //   168: invokestatic a : (Ljava/lang/String;Ljava/lang/String;I)Lcom/tt/frontendapiinterface/ApiCallResult$a;
    //   171: invokevirtual a : ()Lcom/tt/frontendapiinterface/ApiCallResult;
    //   174: invokevirtual toString : ()Ljava/lang/String;
    //   177: areturn
    //   178: aload #5
    //   180: invokevirtual getMyLocation : ()Landroid/location/Location;
    //   183: astore #7
    //   185: aload #7
    //   187: ifnonnull -> 209
    //   190: aload_0
    //   191: invokevirtual getApiName : ()Ljava/lang/String;
    //   194: ldc 'obtain location fail'
    //   196: sipush #208
    //   199: invokestatic a : (Ljava/lang/String;Ljava/lang/String;I)Lcom/tt/frontendapiinterface/ApiCallResult$a;
    //   202: invokevirtual a : ()Lcom/tt/frontendapiinterface/ApiCallResult;
    //   205: invokevirtual toString : ()Ljava/lang/String;
    //   208: areturn
    //   209: aload #6
    //   211: aload #7
    //   213: invokevirtual getLatitude : ()D
    //   216: putfield a : D
    //   219: aload #6
    //   221: aload #7
    //   223: invokevirtual getLongitude : ()D
    //   226: putfield b : D
    //   229: goto -> 342
    //   232: aload #7
    //   234: ldc 'latitude'
    //   236: aconst_null
    //   237: invokevirtual optString : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   240: astore #8
    //   242: aload #7
    //   244: ldc 'longitude'
    //   246: aconst_null
    //   247: invokevirtual optString : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   250: astore #7
    //   252: aload #8
    //   254: ifnonnull -> 266
    //   257: aload #6
    //   259: dconst_0
    //   260: putfield a : D
    //   263: goto -> 297
    //   266: aload #8
    //   268: invokestatic parseDouble : (Ljava/lang/String;)D
    //   271: dstore_1
    //   272: dload_1
    //   273: invokestatic isValidLat : (D)Z
    //   276: ifne -> 291
    //   279: aload_0
    //   280: invokevirtual getApiName : ()Ljava/lang/String;
    //   283: ldc 'invalid latitude'
    //   285: bipush #104
    //   287: invokestatic makeFailMsg : (Ljava/lang/String;Ljava/lang/String;I)Ljava/lang/String;
    //   290: areturn
    //   291: aload #6
    //   293: dload_1
    //   294: putfield a : D
    //   297: aload #7
    //   299: ifnonnull -> 311
    //   302: aload #6
    //   304: dconst_0
    //   305: putfield b : D
    //   308: goto -> 342
    //   311: aload #7
    //   313: invokestatic parseDouble : (Ljava/lang/String;)D
    //   316: dstore_1
    //   317: dload_1
    //   318: invokestatic isValidLng : (D)Z
    //   321: ifne -> 336
    //   324: aload_0
    //   325: invokevirtual getApiName : ()Ljava/lang/String;
    //   328: ldc 'invalid longitude'
    //   330: bipush #105
    //   332: invokestatic makeFailMsg : (Ljava/lang/String;Ljava/lang/String;I)Ljava/lang/String;
    //   335: areturn
    //   336: aload #6
    //   338: dload_1
    //   339: putfield b : D
    //   342: aload #5
    //   344: invokevirtual getMapContext : ()Lcom/tt/option/m/a;
    //   347: pop
    //   348: aload_0
    //   349: invokevirtual makeOkMsg : ()Ljava/lang/String;
    //   352: areturn
    //   353: aload_0
    //   354: invokevirtual getApiName : ()Ljava/lang/String;
    //   357: ldc 'invalid longitude'
    //   359: bipush #105
    //   361: invokestatic makeFailMsg : (Ljava/lang/String;Ljava/lang/String;I)Ljava/lang/String;
    //   364: areturn
    //   365: aload_0
    //   366: invokevirtual getApiName : ()Ljava/lang/String;
    //   369: ldc 'invalid latitude'
    //   371: bipush #104
    //   373: invokestatic makeFailMsg : (Ljava/lang/String;Ljava/lang/String;I)Ljava/lang/String;
    //   376: astore #5
    //   378: aload #5
    //   380: areturn
    //   381: astore #5
    //   383: ldc 'tma_MoveToLocationHandler'
    //   385: iconst_1
    //   386: anewarray java/lang/Object
    //   389: dup
    //   390: iconst_0
    //   391: aload #5
    //   393: aastore
    //   394: invokestatic e : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   397: aload_0
    //   398: invokevirtual getApiName : ()Ljava/lang/String;
    //   401: aload #5
    //   403: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/String;
    //   406: sipush #207
    //   409: invokestatic a : (Ljava/lang/String;Ljava/lang/String;I)Lcom/tt/frontendapiinterface/ApiCallResult$a;
    //   412: invokevirtual a : ()Lcom/tt/frontendapiinterface/ApiCallResult;
    //   415: invokevirtual toString : ()Ljava/lang/String;
    //   418: areturn
    //   419: astore #5
    //   421: goto -> 365
    //   424: astore #5
    //   426: goto -> 353
    // Exception table:
    //   from	to	target	type
    //   14	76	381	finally
    //   76	101	381	finally
    //   106	146	381	finally
    //   146	178	381	finally
    //   178	185	381	finally
    //   190	209	381	finally
    //   209	229	381	finally
    //   232	252	381	finally
    //   257	263	381	finally
    //   266	291	419	java/lang/NumberFormatException
    //   266	291	381	finally
    //   291	297	419	java/lang/NumberFormatException
    //   291	297	381	finally
    //   302	308	381	finally
    //   311	336	424	java/lang/NumberFormatException
    //   311	336	381	finally
    //   336	342	424	java/lang/NumberFormatException
    //   336	342	381	finally
    //   342	353	381	finally
    //   353	365	381	finally
    //   365	378	381	finally
  }
  
  public String getApiName() {
    return "moveToLocation";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\webbridge\sync\map\MoveToLocationHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */