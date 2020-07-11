package com.tt.miniapp.webbridge.sync;

import android.app.Activity;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.WebViewManager;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.option.y.b;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class ShowMultiPickerViewHandler extends BasePickerEventHandler {
  public ShowMultiPickerViewHandler(WebViewManager.IRender paramIRender, String paramString, int paramInt) {
    super(paramIRender, paramString, paramInt);
  }
  
  public String act() {
    // Byte code:
    //   0: new org/json/JSONObject
    //   3: dup
    //   4: aload_0
    //   5: getfield mArgs : Ljava/lang/String;
    //   8: invokespecial <init> : (Ljava/lang/String;)V
    //   11: astore #7
    //   13: aload #7
    //   15: ldc 'array'
    //   17: invokevirtual getJSONArray : (Ljava/lang/String;)Lorg/json/JSONArray;
    //   20: astore #8
    //   22: new java/util/ArrayList
    //   25: dup
    //   26: invokespecial <init> : ()V
    //   29: astore #6
    //   31: iconst_0
    //   32: istore_3
    //   33: aload #8
    //   35: ifnull -> 121
    //   38: aload #8
    //   40: invokevirtual length : ()I
    //   43: istore #4
    //   45: iconst_0
    //   46: istore_1
    //   47: iload_1
    //   48: iload #4
    //   50: if_icmpge -> 121
    //   53: aload #8
    //   55: iload_1
    //   56: invokevirtual getJSONArray : (I)Lorg/json/JSONArray;
    //   59: astore #9
    //   61: aload #9
    //   63: ifnull -> 231
    //   66: new java/util/ArrayList
    //   69: dup
    //   70: invokespecial <init> : ()V
    //   73: astore #10
    //   75: aload #6
    //   77: aload #10
    //   79: invokeinterface add : (Ljava/lang/Object;)Z
    //   84: pop
    //   85: aload #9
    //   87: invokevirtual length : ()I
    //   90: istore #5
    //   92: iconst_0
    //   93: istore_2
    //   94: iload_2
    //   95: iload #5
    //   97: if_icmpge -> 231
    //   100: aload #10
    //   102: aload #9
    //   104: iload_2
    //   105: invokevirtual getString : (I)Ljava/lang/String;
    //   108: invokeinterface add : (Ljava/lang/Object;)Z
    //   113: pop
    //   114: iload_2
    //   115: iconst_1
    //   116: iadd
    //   117: istore_2
    //   118: goto -> 94
    //   121: aload #6
    //   123: invokeinterface size : ()I
    //   128: istore_2
    //   129: iload_2
    //   130: ifne -> 140
    //   133: aload_0
    //   134: ldc 'empty array'
    //   136: invokevirtual makeFailMsg : (Ljava/lang/String;)Ljava/lang/String;
    //   139: areturn
    //   140: aload #7
    //   142: ldc 'current'
    //   144: invokevirtual optJSONArray : (Ljava/lang/String;)Lorg/json/JSONArray;
    //   147: astore #7
    //   149: aload #6
    //   151: invokeinterface size : ()I
    //   156: newarray int
    //   158: astore #8
    //   160: aload #7
    //   162: ifnull -> 189
    //   165: iload_3
    //   166: istore_1
    //   167: iload_1
    //   168: iload_2
    //   169: if_icmpge -> 189
    //   172: aload #8
    //   174: iload_1
    //   175: aload #7
    //   177: iload_1
    //   178: invokevirtual getInt : (I)I
    //   181: iastore
    //   182: iload_1
    //   183: iconst_1
    //   184: iadd
    //   185: istore_1
    //   186: goto -> 167
    //   189: getstatic com/tt/miniapphost/AppbrandContext.mainHandler : Landroid/os/Handler;
    //   192: new com/tt/miniapp/webbridge/sync/ShowMultiPickerViewHandler$1
    //   195: dup
    //   196: aload_0
    //   197: aload #6
    //   199: aload #8
    //   201: invokespecial <init> : (Lcom/tt/miniapp/webbridge/sync/ShowMultiPickerViewHandler;Ljava/util/List;[I)V
    //   204: invokevirtual post : (Ljava/lang/Runnable;)Z
    //   207: pop
    //   208: aconst_null
    //   209: areturn
    //   210: astore #6
    //   212: bipush #6
    //   214: ldc 'tma_ShowMultiPickerViewHandler'
    //   216: aload #6
    //   218: invokevirtual getStackTrace : ()[Ljava/lang/StackTraceElement;
    //   221: invokestatic stacktrace : (ILjava/lang/String;[Ljava/lang/StackTraceElement;)V
    //   224: aload_0
    //   225: aload #6
    //   227: invokevirtual makeFailMsg : (Ljava/lang/Throwable;)Ljava/lang/String;
    //   230: areturn
    //   231: iload_1
    //   232: iconst_1
    //   233: iadd
    //   234: istore_1
    //   235: goto -> 47
    // Exception table:
    //   from	to	target	type
    //   0	31	210	java/lang/Exception
    //   38	45	210	java/lang/Exception
    //   53	61	210	java/lang/Exception
    //   66	92	210	java/lang/Exception
    //   100	114	210	java/lang/Exception
    //   121	129	210	java/lang/Exception
    //   133	140	210	java/lang/Exception
    //   140	160	210	java/lang/Exception
    //   172	182	210	java/lang/Exception
    //   189	208	210	java/lang/Exception
  }
  
  public String getApiName() {
    return "showMultiPickerView";
  }
  
  public void showMultiPickerViewFinal(Activity paramActivity, List<List<String>> paramList, int[] paramArrayOfint) {
    HostDependManager.getInst().showMultiPickerView(paramActivity, this.mArgs, paramList, paramArrayOfint, new b.b() {
          public void onCancel() {
            AppBrandLogger.d("tma_ShowMultiPickerViewHandler", new Object[] { "onWheeled onCancel" });
            ShowMultiPickerViewHandler.this.makeCancelMsg("showMultiPickerView");
          }
          
          public void onConfirm(int[] param1ArrayOfint) {
            int i = 0;
            AppBrandLogger.d("tma_ShowMultiPickerViewHandler", new Object[] { "onWheeled onConfirm" });
            try {
              JSONObject jSONObject = new JSONObject();
              jSONObject.put("errMsg", ShowMultiPickerViewHandler.this.buildErrorMsg("showMultiPickerView", "ok"));
              JSONArray jSONArray = new JSONArray();
              int j = param1ArrayOfint.length;
              while (i < j) {
                jSONArray.put(param1ArrayOfint[i]);
                i++;
              } 
              jSONObject.put("current", jSONArray);
              AppbrandApplicationImpl.getInst().getWebViewManager().invokeHandler(ShowMultiPickerViewHandler.this.mRender.getWebViewId(), ShowMultiPickerViewHandler.this.mCallBackId, jSONObject.toString());
              return;
            } catch (Exception exception) {
              AppBrandLogger.stacktrace(6, "tma_ShowMultiPickerViewHandler", exception.getStackTrace());
              ShowMultiPickerViewHandler showMultiPickerViewHandler = ShowMultiPickerViewHandler.this;
              showMultiPickerViewHandler.invokeHandler(showMultiPickerViewHandler.makeFailMsg(exception));
              return;
            } 
          }
          
          public void onDismiss() {
            AppBrandLogger.d("tma_ShowMultiPickerViewHandler", new Object[] { "onWheeled onDismiss" });
            ShowMultiPickerViewHandler.this.makeCancelMsg("showMultiPickerView");
          }
          
          public void onFailure(String param1String) {
            AppBrandLogger.d("tma_ShowMultiPickerViewHandler", new Object[] { "onWheeled onFailure", param1String });
            ShowMultiPickerViewHandler.this.makeCancelMsg("showMultiPickerView");
          }
          
          public void onWheeled(int param1Int1, int param1Int2, Object param1Object) {
            AppBrandLogger.d("tma_ShowMultiPickerViewHandler", new Object[] { "onWheeled column", Integer.valueOf(param1Int1), " index ", Integer.valueOf(param1Int2), " item ", param1Object });
            try {
              param1Object = new JSONObject();
              param1Object.put("column", param1Int1);
              param1Object.put("current", param1Int2);
              AppbrandApplicationImpl.getInst().getWebViewManager().publish(ShowMultiPickerViewHandler.this.mRender.getWebViewId(), "onMultiPickerViewChange", param1Object.toString());
              return;
            } catch (Exception exception) {
              AppBrandLogger.stacktrace(6, "tma_ShowMultiPickerViewHandler", exception.getStackTrace());
              return;
            } 
          }
        });
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\webbridge\sync\ShowMultiPickerViewHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */