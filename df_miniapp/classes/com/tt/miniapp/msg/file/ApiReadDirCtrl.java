package com.tt.miniapp.msg.file;

import org.json.JSONObject;

public class ApiReadDirCtrl extends AbsStringParamCtrl {
  public ApiReadDirCtrl(String paramString) {
    super(paramString);
  }
  
  protected boolean handleInvoke() {
    // Byte code:
    //   0: aload_0
    //   1: ldc 'dirPath'
    //   3: invokevirtual optString : (Ljava/lang/String;)Ljava/lang/String;
    //   6: astore #4
    //   8: new java/io/File
    //   11: dup
    //   12: aload_0
    //   13: aload #4
    //   15: invokevirtual getRealFilePath : (Ljava/lang/String;)Ljava/lang/String;
    //   18: invokespecial <init> : (Ljava/lang/String;)V
    //   21: astore #5
    //   23: aload_0
    //   24: aload #5
    //   26: invokevirtual canRead : (Ljava/io/File;)Z
    //   29: ifne -> 58
    //   32: aload_0
    //   33: aload_0
    //   34: iconst_2
    //   35: anewarray java/lang/String
    //   38: dup
    //   39: iconst_0
    //   40: aload_0
    //   41: getfield mApiName : Ljava/lang/String;
    //   44: aastore
    //   45: dup
    //   46: iconst_1
    //   47: aload #4
    //   49: aastore
    //   50: invokevirtual getPermissionDenyStr : ([Ljava/lang/String;)Ljava/lang/String;
    //   53: putfield mExtraInfo : Ljava/lang/String;
    //   56: iconst_0
    //   57: ireturn
    //   58: aload #5
    //   60: invokevirtual exists : ()Z
    //   63: ifne -> 103
    //   66: invokestatic inst : ()Lcom/tt/miniapp/storage/filestorge/FileManager;
    //   69: aload #5
    //   71: invokevirtual isChildOfInstallDir : (Ljava/io/File;)Z
    //   74: ifne -> 103
    //   77: aload_0
    //   78: aload_0
    //   79: iconst_2
    //   80: anewarray java/lang/String
    //   83: dup
    //   84: iconst_0
    //   85: aload_0
    //   86: getfield mApiName : Ljava/lang/String;
    //   89: aastore
    //   90: dup
    //   91: iconst_1
    //   92: aload #4
    //   94: aastore
    //   95: invokevirtual getNoSuchFileStr : ([Ljava/lang/String;)Ljava/lang/String;
    //   98: putfield mExtraInfo : Ljava/lang/String;
    //   101: iconst_0
    //   102: ireturn
    //   103: aload #5
    //   105: invokevirtual exists : ()Z
    //   108: ifeq -> 202
    //   111: aload #5
    //   113: invokevirtual isDirectory : ()Z
    //   116: ifeq -> 202
    //   119: aload #5
    //   121: invokevirtual list : ()[Ljava/lang/String;
    //   124: astore #6
    //   126: new org/json/JSONArray
    //   129: dup
    //   130: invokespecial <init> : ()V
    //   133: astore #7
    //   135: aload #6
    //   137: ifnull -> 174
    //   140: aload #6
    //   142: arraylength
    //   143: ifle -> 174
    //   146: aload #6
    //   148: arraylength
    //   149: istore_2
    //   150: iconst_0
    //   151: istore_1
    //   152: iload_1
    //   153: iload_2
    //   154: if_icmpge -> 174
    //   157: aload #7
    //   159: aload #6
    //   161: iload_1
    //   162: aaload
    //   163: invokevirtual put : (Ljava/lang/Object;)Lorg/json/JSONArray;
    //   166: pop
    //   167: iload_1
    //   168: iconst_1
    //   169: iadd
    //   170: istore_1
    //   171: goto -> 152
    //   174: aload_0
    //   175: new java/util/HashMap
    //   178: dup
    //   179: invokespecial <init> : ()V
    //   182: putfield mExtraData : Ljava/util/HashMap;
    //   185: aload_0
    //   186: getfield mExtraData : Ljava/util/HashMap;
    //   189: ldc 'files'
    //   191: aload #7
    //   193: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   196: pop
    //   197: iconst_1
    //   198: istore_3
    //   199: goto -> 339
    //   202: invokestatic inst : ()Lcom/tt/miniapp/storage/filestorge/FileManager;
    //   205: aload #5
    //   207: invokevirtual isChildOfInstallDir : (Ljava/io/File;)Z
    //   210: ifeq -> 337
    //   213: invokestatic getLoadTask : ()Lcom/tt/miniapp/streamloader/LoadTask;
    //   216: ifnull -> 337
    //   219: aload_0
    //   220: aload #4
    //   222: invokevirtual getRealFilePath : (Ljava/lang/String;)Ljava/lang/String;
    //   225: invokestatic listTTAPKG : (Ljava/lang/String;)Ljava/util/Set;
    //   228: astore #7
    //   230: aload #7
    //   232: ifnull -> 337
    //   235: aload #7
    //   237: invokeinterface isEmpty : ()Z
    //   242: ifne -> 337
    //   245: new org/json/JSONArray
    //   248: dup
    //   249: invokespecial <init> : ()V
    //   252: astore #6
    //   254: aload #7
    //   256: invokeinterface size : ()I
    //   261: ifle -> 311
    //   264: aload #7
    //   266: invokeinterface iterator : ()Ljava/util/Iterator;
    //   271: astore #7
    //   273: aload #7
    //   275: invokeinterface hasNext : ()Z
    //   280: ifeq -> 311
    //   283: aload #7
    //   285: invokeinterface next : ()Ljava/lang/Object;
    //   290: checkcast java/lang/String
    //   293: astore #8
    //   295: aload #8
    //   297: ifnull -> 273
    //   300: aload #6
    //   302: aload #8
    //   304: invokevirtual put : (Ljava/lang/Object;)Lorg/json/JSONArray;
    //   307: pop
    //   308: goto -> 273
    //   311: aload_0
    //   312: new java/util/HashMap
    //   315: dup
    //   316: invokespecial <init> : ()V
    //   319: putfield mExtraData : Ljava/util/HashMap;
    //   322: aload_0
    //   323: getfield mExtraData : Ljava/util/HashMap;
    //   326: ldc 'files'
    //   328: aload #6
    //   330: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   333: pop
    //   334: goto -> 197
    //   337: iconst_0
    //   338: istore_3
    //   339: iload_3
    //   340: ifne -> 404
    //   343: aload #5
    //   345: invokevirtual exists : ()Z
    //   348: ifne -> 377
    //   351: aload_0
    //   352: aload_0
    //   353: iconst_2
    //   354: anewarray java/lang/String
    //   357: dup
    //   358: iconst_0
    //   359: aload_0
    //   360: getfield mApiName : Ljava/lang/String;
    //   363: aastore
    //   364: dup
    //   365: iconst_1
    //   366: aload #4
    //   368: aastore
    //   369: invokevirtual getNoSuchFileStr : ([Ljava/lang/String;)Ljava/lang/String;
    //   372: putfield mExtraInfo : Ljava/lang/String;
    //   375: iload_3
    //   376: ireturn
    //   377: aload #5
    //   379: invokevirtual isDirectory : ()Z
    //   382: ifne -> 404
    //   385: aload_0
    //   386: aload_0
    //   387: ldc 'not a directory %s'
    //   389: iconst_1
    //   390: anewarray java/lang/Object
    //   393: dup
    //   394: iconst_0
    //   395: aload #4
    //   397: aastore
    //   398: invokevirtual getFormatExtraInfo : (Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   401: putfield mExtraInfo : Ljava/lang/String;
    //   404: iload_3
    //   405: ireturn
  }
  
  protected void parseParam(String paramString) throws Exception {
    JSONObject jSONObject = new JSONObject(paramString);
    this.mArgumentsMap.put("dirPath", new AbsFileCtrl.ValuePair<String>(this, jSONObject.optString("dirPath"), true));
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\file\ApiReadDirCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */