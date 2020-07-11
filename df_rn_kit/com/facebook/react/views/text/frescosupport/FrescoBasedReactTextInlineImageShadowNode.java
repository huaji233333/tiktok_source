package com.facebook.react.views.text.frescosupport;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import com.facebook.drawee.c.b;
import com.facebook.react.bridge.Dynamic;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.uimanager.LayoutShadowNode;
import com.facebook.react.uimanager.ReactShadowNode;
import com.facebook.react.uimanager.ReactShadowNodeImpl;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.views.text.ReactTextInlineImageShadowNode;
import com.facebook.react.views.text.TextInlineImageSpan;
import java.util.Locale;

public class FrescoBasedReactTextInlineImageShadowNode extends ReactTextInlineImageShadowNode {
  private final Object mCallerContext;
  
  private final b mDraweeControllerBuilder;
  
  private ReadableMap mHeaders;
  
  private float mHeight = 1.0E21F;
  
  private Uri mUri;
  
  private float mWidth = 1.0E21F;
  
  public FrescoBasedReactTextInlineImageShadowNode(b paramb, Object paramObject) {
    this.mDraweeControllerBuilder = paramb;
    this.mCallerContext = paramObject;
  }
  
  private FrescoBasedReactTextInlineImageShadowNode(FrescoBasedReactTextInlineImageShadowNode paramFrescoBasedReactTextInlineImageShadowNode) {
    super(paramFrescoBasedReactTextInlineImageShadowNode);
    this.mHeaders = paramFrescoBasedReactTextInlineImageShadowNode.mHeaders;
    this.mWidth = paramFrescoBasedReactTextInlineImageShadowNode.mWidth;
    this.mHeight = paramFrescoBasedReactTextInlineImageShadowNode.mHeight;
    this.mDraweeControllerBuilder = paramFrescoBasedReactTextInlineImageShadowNode.mDraweeControllerBuilder;
    this.mCallerContext = paramFrescoBasedReactTextInlineImageShadowNode.mCallerContext;
    this.mUri = paramFrescoBasedReactTextInlineImageShadowNode.mUri;
  }
  
  private static Uri getResourceDrawableUri(Context paramContext, String paramString) {
    if (paramString == null || paramString.isEmpty())
      return null; 
    paramString = paramString.toLowerCase(Locale.getDefault()).replace("-", "_");
    int i = paramContext.getResources().getIdentifier(paramString, "drawable", paramContext.getPackageName());
    return (new Uri.Builder()).scheme("res").path(String.valueOf(i)).build();
  }
  
  public TextInlineImageSpan buildInlineImageSpan() {
    Resources resources = getThemedContext().getResources();
    int i = (int)Math.ceil(this.mWidth);
    return new FrescoBasedReactTextInlineImageSpan(resources, (int)Math.ceil(this.mHeight), i, getUri(), getHeaders(), getDraweeControllerBuilder(), getCallerContext());
  }
  
  public Object getCallerContext() {
    return this.mCallerContext;
  }
  
  public b getDraweeControllerBuilder() {
    return this.mDraweeControllerBuilder;
  }
  
  public ReadableMap getHeaders() {
    return this.mHeaders;
  }
  
  public Uri getUri() {
    return this.mUri;
  }
  
  public boolean isVirtual() {
    return true;
  }
  
  public FrescoBasedReactTextInlineImageShadowNode mutableCopy() {
    return new FrescoBasedReactTextInlineImageShadowNode(this);
  }
  
  @ReactProp(name = "headers")
  public void setHeaders(ReadableMap paramReadableMap) {
    this.mHeaders = paramReadableMap;
  }
  
  public void setHeight(Dynamic paramDynamic) {
    if (paramDynamic.getType() == ReadableType.Number) {
      this.mHeight = (float)paramDynamic.asDouble();
      return;
    } 
    throw new JSApplicationIllegalArgumentException("Inline images must not have percentage based height");
  }
  
  @ReactProp(name = "src")
  public void setSource(ReadableArray paramReadableArray) {
    // Byte code:
    //   0: aconst_null
    //   1: astore #4
    //   3: aconst_null
    //   4: astore_2
    //   5: aload_1
    //   6: ifnull -> 39
    //   9: aload_1
    //   10: invokeinterface size : ()I
    //   15: ifne -> 21
    //   18: goto -> 39
    //   21: aload_1
    //   22: iconst_0
    //   23: invokeinterface getMap : (I)Lcom/facebook/react/bridge/ReadableMap;
    //   28: ldc 'uri'
    //   30: invokeinterface getString : (Ljava/lang/String;)Ljava/lang/String;
    //   35: astore_3
    //   36: goto -> 41
    //   39: aconst_null
    //   40: astore_3
    //   41: aload #4
    //   43: astore_1
    //   44: aload_3
    //   45: ifnull -> 87
    //   48: aload_3
    //   49: invokestatic parse : (Ljava/lang/String;)Landroid/net/Uri;
    //   52: astore_1
    //   53: aload_1
    //   54: invokevirtual getScheme : ()Ljava/lang/String;
    //   57: astore #4
    //   59: aload #4
    //   61: ifnonnull -> 67
    //   64: goto -> 72
    //   67: aload_1
    //   68: astore_2
    //   69: goto -> 72
    //   72: aload_2
    //   73: astore_1
    //   74: aload_2
    //   75: ifnonnull -> 87
    //   78: aload_0
    //   79: invokevirtual getThemedContext : ()Lcom/facebook/react/uimanager/ThemedReactContext;
    //   82: aload_3
    //   83: invokestatic getResourceDrawableUri : (Landroid/content/Context;Ljava/lang/String;)Landroid/net/Uri;
    //   86: astore_1
    //   87: aload_1
    //   88: aload_0
    //   89: getfield mUri : Landroid/net/Uri;
    //   92: if_acmpeq -> 99
    //   95: aload_0
    //   96: invokevirtual markUpdated : ()V
    //   99: aload_0
    //   100: aload_1
    //   101: putfield mUri : Landroid/net/Uri;
    //   104: return
    //   105: astore_1
    //   106: goto -> 72
    //   109: astore_2
    //   110: goto -> 67
    // Exception table:
    //   from	to	target	type
    //   48	53	105	java/lang/Exception
    //   53	59	109	java/lang/Exception
  }
  
  public void setWidth(Dynamic paramDynamic) {
    if (paramDynamic.getType() == ReadableType.Number) {
      this.mWidth = (float)paramDynamic.asDouble();
      return;
    } 
    throw new JSApplicationIllegalArgumentException("Inline images must not have percentage based width");
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\text\frescosupport\FrescoBasedReactTextInlineImageShadowNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */