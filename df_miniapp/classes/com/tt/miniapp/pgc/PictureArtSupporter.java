package com.tt.miniapp.pgc;

import android.net.Uri;
import android.text.TextUtils;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.storage.filestorge.FileManager;
import com.tt.miniapp.streamloader.StreamLoader;
import com.tt.miniapp.thread.ThreadPools;
import com.tt.miniapp.util.PageUtil;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.util.CharacterUtils;
import com.tt.miniapphost.util.IOUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PictureArtSupporter {
  private PictureArtSupporter() {}
  
  private void callback(OnFinishedListener paramOnFinishedListener, int paramInt, List<OperateImageResult> paramList) {
    StringBuilder stringBuilder = new StringBuilder("r67533_callback: ");
    stringBuilder.append(paramInt);
    AppBrandLogger.d("PictureArtSupporter", new Object[] { stringBuilder.toString() });
    if (paramOnFinishedListener == null)
      return; 
    paramOnFinishedListener.onFinish(paramInt, paramList);
  }
  
  public static boolean fileIsNotImage(int paramInt) {
    return (paramInt != 2 && paramInt != 3 && paramInt != 1 && paramInt != 4);
  }
  
  public static boolean fileIsNotImage(File paramFile) {
    return fileIsNotImage(getImageType(paramFile));
  }
  
  public static String getImageFileSuffix(int paramInt) {
    return (paramInt != 1) ? ((paramInt != 2) ? ((paramInt != 3) ? ((paramInt != 4) ? "" : ".bmp") : ".gif") : ".png") : ".jpg";
  }
  
  private static int getImageType(File paramFile) {
    if (paramFile != null && paramFile.exists()) {
      if (!paramFile.isFile())
        return -1; 
      File file2 = null;
      IOException iOException = null;
      byte[] arrayOfByte = new byte[10];
      try {
        FileInputStream fileInputStream = new FileInputStream(paramFile);
        try {
          return (i != 10) ? -1 : getImageType(arrayOfByte);
        } catch (Exception null) {
        
        } finally {
          byte[] arrayOfByte1;
          arrayOfByte = null;
          iOException = iOException1;
        } 
      } catch (Exception exception) {
        paramFile = file2;
      } finally {}
      File file1 = paramFile;
      AppBrandLogger.eWithThrowable("PictureArtSupporter", "rr67533_read image file exp", exception);
      if (paramFile != null)
        try {
          paramFile.close();
          return -1;
        } catch (IOException iOException1) {
          AppBrandLogger.eWithThrowable("PictureArtSupporter", "rr67533_close image file exp", iOException1);
        }  
      return -1;
    } 
    return -1;
  }
  
  public static int getImageType(byte[] paramArrayOfbyte) {
    return (paramArrayOfbyte == null || paramArrayOfbyte.length < 10) ? -1 : (((paramArrayOfbyte[0] & 0xFF) == 137 && (paramArrayOfbyte[1] & 0xFF) == 80) ? 2 : (((paramArrayOfbyte[0] & 0xFF) == 71 && (paramArrayOfbyte[1] & 0xFF) == 73) ? 3 : (((paramArrayOfbyte[0] & 0xFF) == 255 && (paramArrayOfbyte[1] & 0xFF) == 216) ? 1 : (((paramArrayOfbyte[0] & 0xFF) == 66 && (paramArrayOfbyte[1] & 0xFF) == 77) ? 4 : 0))));
  }
  
  public static File getTempPicDir(String paramString) {
    return new File(FileManager.inst().getTempDir(paramString), "pgc_pic");
  }
  
  public static PictureArtSupporter inst() {
    return Holder.sInst;
  }
  
  public String checkAndGetQuery(String paramString) {
    StringBuilder stringBuilder1 = new StringBuilder("r67533_checkAndGetQuery: ");
    stringBuilder1.append(String.valueOf(paramString));
    AppBrandLogger.d("PictureArtSupporter", new Object[] { stringBuilder1.toString() });
    if (TextUtils.isEmpty(paramString))
      return null; 
    if (paramString.startsWith("[")) {
      try {
        JSONArray jSONArray = new JSONArray(paramString);
      } catch (JSONException jSONException) {
        AppBrandLogger.w("PictureArtSupporter", new Object[] { "try json array err", jSONException });
        jSONException = null;
      } 
      if (jSONException != null)
        return Uri.encode(jSONException.toString()); 
    } else {
      try {
        JSONObject jSONObject1 = new JSONObject(paramString);
      } catch (JSONException jSONException) {
        AppBrandLogger.w("PictureArtSupporter", new Object[] { "try json object err", jSONException });
        jSONException = null;
      } 
      if (jSONException != null)
        return Uri.encode(jSONException.toString()); 
    } 
    if (!paramString.contains("="))
      return Uri.encode(paramString); 
    StringBuilder stringBuilder2 = new StringBuilder("http://");
    String str = "?";
    if (paramString.startsWith("?"))
      str = ""; 
    stringBuilder2.append(str);
    stringBuilder2.append(paramString);
    JSONObject jSONObject = PageUtil.getQueryObject(Uri.parse(stringBuilder2.toString()));
    return (jSONObject == null) ? null : Uri.encode(jSONObject.toString());
  }
  
  public String checkAndGetStartPage(String paramString) {
    StringBuilder stringBuilder = new StringBuilder("r67533_checkAndGetStartPage: ");
    stringBuilder.append(String.valueOf(paramString));
    AppBrandLogger.d("PictureArtSupporter", new Object[] { stringBuilder.toString() });
    return TextUtils.isEmpty(paramString) ? null : (PageUtil.isPageValidate(paramString) ? Uri.encode(paramString) : null);
  }
  
  public void delTempFileWithResults(List<OperateImageResult> paramList) {
    // Byte code:
    //   0: aload_1
    //   1: ifnull -> 323
    //   4: aload_1
    //   5: invokeinterface isEmpty : ()Z
    //   10: ifeq -> 16
    //   13: goto -> 323
    //   16: invokestatic getInst : ()Lcom/tt/miniapp/AppbrandApplicationImpl;
    //   19: invokevirtual getAppInfo : ()Lcom/tt/miniapphost/entity/AppInfoEntity;
    //   22: getfield appId : Ljava/lang/String;
    //   25: astore #4
    //   27: aload #4
    //   29: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   32: ifeq -> 50
    //   35: ldc 'PictureArtSupporter'
    //   37: iconst_1
    //   38: anewarray java/lang/Object
    //   41: dup
    //   42: iconst_0
    //   43: ldc 'r67533_delTempFileWithResults: empty mpId'
    //   45: aastore
    //   46: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   49: return
    //   50: new java/lang/StringBuilder
    //   53: dup
    //   54: ldc 'r67533_delTempFileWithResults: '
    //   56: invokespecial <init> : (Ljava/lang/String;)V
    //   59: astore #5
    //   61: aload #5
    //   63: aload #4
    //   65: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   68: pop
    //   69: ldc 'PictureArtSupporter'
    //   71: iconst_1
    //   72: anewarray java/lang/Object
    //   75: dup
    //   76: iconst_0
    //   77: aload #5
    //   79: invokevirtual toString : ()Ljava/lang/String;
    //   82: aastore
    //   83: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   86: aload #4
    //   88: invokestatic getTempPicDir : (Ljava/lang/String;)Ljava/io/File;
    //   91: invokevirtual getCanonicalPath : ()Ljava/lang/String;
    //   94: astore #4
    //   96: aload_1
    //   97: invokeinterface iterator : ()Ljava/util/Iterator;
    //   102: astore_1
    //   103: iconst_0
    //   104: istore_2
    //   105: iload_2
    //   106: istore_3
    //   107: aload_1
    //   108: invokeinterface hasNext : ()Z
    //   113: ifeq -> 192
    //   116: iload_2
    //   117: istore_3
    //   118: aload_1
    //   119: invokeinterface next : ()Ljava/lang/Object;
    //   124: checkcast com/tt/miniapp/pgc/OperateImageResult
    //   127: astore #5
    //   129: iload_2
    //   130: istore_3
    //   131: aload #5
    //   133: getfield fileType : I
    //   136: iconst_1
    //   137: if_icmpne -> 105
    //   140: iload_2
    //   141: istore_3
    //   142: aload #5
    //   144: getfield outputFilePath : Ljava/lang/String;
    //   147: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   150: ifne -> 105
    //   153: iload_2
    //   154: istore_3
    //   155: aload #5
    //   157: getfield outputFilePath : Ljava/lang/String;
    //   160: aload #4
    //   162: invokevirtual startsWith : (Ljava/lang/String;)Z
    //   165: ifeq -> 105
    //   168: iload_2
    //   169: istore_3
    //   170: new java/io/File
    //   173: dup
    //   174: aload #5
    //   176: getfield outputFilePath : Ljava/lang/String;
    //   179: invokespecial <init> : (Ljava/lang/String;)V
    //   182: invokevirtual deleteOnExit : ()V
    //   185: iload_2
    //   186: iconst_1
    //   187: iadd
    //   188: istore_2
    //   189: goto -> 105
    //   192: new java/lang/StringBuilder
    //   195: dup
    //   196: ldc_w 'r67533_clear cnt: '
    //   199: invokespecial <init> : (Ljava/lang/String;)V
    //   202: astore_1
    //   203: aload_1
    //   204: iload_2
    //   205: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   208: pop
    //   209: ldc 'PictureArtSupporter'
    //   211: iconst_1
    //   212: anewarray java/lang/Object
    //   215: dup
    //   216: iconst_0
    //   217: aload_1
    //   218: invokevirtual toString : ()Ljava/lang/String;
    //   221: aastore
    //   222: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   225: return
    //   226: astore_1
    //   227: goto -> 239
    //   230: astore_1
    //   231: iconst_0
    //   232: istore_3
    //   233: goto -> 285
    //   236: astore_1
    //   237: iconst_0
    //   238: istore_2
    //   239: iload_2
    //   240: istore_3
    //   241: ldc 'PictureArtSupporter'
    //   243: ldc_w 'r67533_clear files exp!'
    //   246: aload_1
    //   247: invokestatic eWithThrowable : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   250: new java/lang/StringBuilder
    //   253: dup
    //   254: ldc_w 'r67533_clear cnt: '
    //   257: invokespecial <init> : (Ljava/lang/String;)V
    //   260: astore_1
    //   261: aload_1
    //   262: iload_2
    //   263: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   266: pop
    //   267: ldc 'PictureArtSupporter'
    //   269: iconst_1
    //   270: anewarray java/lang/Object
    //   273: dup
    //   274: iconst_0
    //   275: aload_1
    //   276: invokevirtual toString : ()Ljava/lang/String;
    //   279: aastore
    //   280: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   283: return
    //   284: astore_1
    //   285: new java/lang/StringBuilder
    //   288: dup
    //   289: ldc_w 'r67533_clear cnt: '
    //   292: invokespecial <init> : (Ljava/lang/String;)V
    //   295: astore #4
    //   297: aload #4
    //   299: iload_3
    //   300: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   303: pop
    //   304: ldc 'PictureArtSupporter'
    //   306: iconst_1
    //   307: anewarray java/lang/Object
    //   310: dup
    //   311: iconst_0
    //   312: aload #4
    //   314: invokevirtual toString : ()Ljava/lang/String;
    //   317: aastore
    //   318: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   321: aload_1
    //   322: athrow
    //   323: ldc 'PictureArtSupporter'
    //   325: iconst_1
    //   326: anewarray java/lang/Object
    //   329: dup
    //   330: iconst_0
    //   331: ldc_w 'r67533_delTempFileWithResults: empty input list'
    //   334: aastore
    //   335: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   338: return
    // Exception table:
    //   from	to	target	type
    //   86	103	236	java/io/IOException
    //   86	103	230	finally
    //   107	116	226	java/io/IOException
    //   107	116	284	finally
    //   118	129	226	java/io/IOException
    //   118	129	284	finally
    //   131	140	226	java/io/IOException
    //   131	140	284	finally
    //   142	153	226	java/io/IOException
    //   142	153	284	finally
    //   155	168	226	java/io/IOException
    //   155	168	284	finally
    //   170	185	226	java/io/IOException
    //   170	185	284	finally
    //   241	250	284	finally
  }
  
  public void getOrCreateImageFile(final List<String> inputFilePathList, final OnFinishedListener listener) {
    if (inputFilePathList == null || inputFilePathList.isEmpty()) {
      AppBrandLogger.d("PictureArtSupporter", new Object[] { "r67533_getOrCreateImageFile: empty input list" });
      callback(listener, 102, Collections.emptyList());
      return;
    } 
    final String mpId = (AppbrandApplicationImpl.getInst().getAppInfo()).appId;
    if (TextUtils.isEmpty(str)) {
      AppBrandLogger.d("PictureArtSupporter", new Object[] { "r67533_getOrCreateImageFile: empty mpId" });
      callback(listener, 101, Collections.emptyList());
      return;
    } 
    AppBrandLogger.d("PictureArtSupporter", new Object[] { "r67533_getOrCreateImageFile: handles..." });
    ThreadPools.longIO().execute(new Runnable() {
          public void run() {
            PictureArtSupporter.this.handle(mpId, inputFilePathList, listener);
          }
        });
  }
  
  public void handle(String paramString, List<String> paramList, OnFinishedListener paramOnFinishedListener) {
    ArrayList<OperateImageResult> arrayList = new ArrayList(paramList.size());
    Iterator<String> iterator = paramList.iterator();
    while (iterator.hasNext())
      arrayList.add((new FileHandlerCall(paramString, iterator.next())).call()); 
    StringBuilder stringBuilder = new StringBuilder("r67533_getOrCreateImageFile: handle finish.");
    stringBuilder.append(paramList.size());
    AppBrandLogger.d("PictureArtSupporter", new Object[] { stringBuilder.toString() });
    callback(paramOnFinishedListener, 0, arrayList);
  }
  
  static class FileHandlerCall implements Callable<OperateImageResult> {
    private String mInputFilePath;
    
    private String mMpId;
    
    private OperateImageResult mResult;
    
    private FileHandlerCall(String param1String1, String param1String2) {
      this.mMpId = param1String1;
      this.mInputFilePath = param1String2;
      this.mResult = new OperateImageResult(this.mInputFilePath);
    }
    
    public OperateImageResult call() {
      if (TextUtils.isEmpty(this.mInputFilePath)) {
        OperateImageResult operateImageResult = this.mResult;
        operateImageResult.fileType = -1;
        operateImageResult.outputFilePath = CharacterUtils.empty();
        operateImageResult = this.mResult;
        operateImageResult.errCode = 101;
        return operateImageResult;
      } 
      if (this.mInputFilePath.startsWith("ttfile")) {
        OperateImageResult operateImageResult = this.mResult;
        operateImageResult.fileType = 0;
        operateImageResult.outputFilePath = FileManager.inst().getRealFilePath(this.mInputFilePath);
        File file = new File(this.mResult.outputFilePath);
        if (!file.exists()) {
          this.mResult.errCode = 101;
        } else if (PictureArtSupporter.fileIsNotImage(file)) {
          this.mResult.errCode = 201;
        } else {
          this.mResult.errCode = 0;
        } 
      } else {
        OperateImageResult operateImageResult;
        this.mResult.fileType = 1;
        byte[] arrayOfByte = StreamLoader.loadByteFromStream(this.mInputFilePath);
        int i = PictureArtSupporter.getImageType(arrayOfByte);
        if (PictureArtSupporter.fileIsNotImage(i)) {
          this.mResult.outputFilePath = CharacterUtils.empty();
          operateImageResult = this.mResult;
          operateImageResult.errCode = 201;
          return operateImageResult;
        } 
        File file = PictureArtSupporter.getTempPicDir(this.mMpId);
        if (!file.exists())
          file.mkdirs(); 
        try {
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append(CharacterUtils.md5Hex((byte[])operateImageResult));
          stringBuilder.append(PictureArtSupporter.getImageFileSuffix(i));
          file = new File(file, stringBuilder.toString());
          if (!file.exists())
            file.createNewFile(); 
          this.mResult.outputFilePath = file.getCanonicalPath();
          IOUtils.writeBytes(this.mResult.outputFilePath, (byte[])operateImageResult);
          this.mResult.errCode = 0;
        } catch (IOException iOException) {
          AppBrandLogger.eWithThrowable("PictureArtSupporter", "r67533_write pkg file err", iOException);
          OperateImageResult operateImageResult1 = this.mResult;
          operateImageResult1.errCode = 102;
          operateImageResult1.outputFilePath = CharacterUtils.empty();
        } 
      } 
      return this.mResult;
    }
  }
  
  static class Holder {
    public static final PictureArtSupporter sInst = new PictureArtSupporter();
  }
  
  public static interface OnFinishedListener {
    void onFinish(int param1Int, List<OperateImageResult> param1List);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\pgc\PictureArtSupporter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */