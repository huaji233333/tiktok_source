package com.tt.miniapp.component.nativeview;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.text.Editable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.IKeyBoardStateChange;
import com.tt.miniapp.WebViewManager;
import com.tt.miniapp.keyboarddetect.KeyboardHeightProvider;
import com.tt.miniapp.util.InputMethodUtil;
import com.tt.miniapp.view.TextChangedAdapter;
import com.tt.miniapp.view.webcore.AbsoluteLayout;
import com.tt.miniapp.view.webcore.NativeNestWebView;
import com.tt.miniapp.view.webcore.scroller.WebViewScroller;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandApplication;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.util.JsonBuilder;
import com.tt.miniapphost.util.NativeDimenUtil;
import com.tt.option.e.k;
import org.json.JSONException;
import org.json.JSONObject;

public class Input extends EditText implements View.OnFocusChangeListener, InputComponent, NativeComponent {
  private static Input sLastCreatedInput;
  
  private boolean isAdjustPosition = true;
  
  private boolean isFixed = false;
  
  private boolean mAutoBlur = true;
  
  private String mDataObject;
  
  public boolean mHasFocus = false;
  
  private boolean mHasSentKeyboardShow = false;
  
  public boolean mHintUseBoldText = false;
  
  public boolean mInputUseBoldText = false;
  
  private IKeyBoardStateChange mKeyBoardStateChange;
  
  public NativeNestWebView mNativeNestWebView;
  
  private AbsoluteLayout mParent;
  
  private WebViewManager.IRender mRender;
  
  private String mType;
  
  private int mViewId;
  
  private int mWebViewId;
  
  public int marginBottom;
  
  public boolean syncNextTextChangeToJs = true;
  
  private Input(int paramInt1, AbsoluteLayout paramAbsoluteLayout, WebViewManager.IRender paramIRender, int paramInt2, String paramString, NativeNestWebView paramNativeNestWebView) {
    super(paramAbsoluteLayout.getContext());
    this.mViewId = paramInt1;
    this.mParent = paramAbsoluteLayout;
    this.mRender = paramIRender;
    this.mWebViewId = paramInt2;
    this.mType = paramString;
    this.mNativeNestWebView = paramNativeNestWebView;
    init();
  }
  
  private void adjustOffsetIfNeed() {
    if (!this.mHasFocus)
      return; 
    post(new Runnable() {
          public void run() {
            WebViewScroller webViewScroller = Input.this.mNativeNestWebView.getScroller();
            if (webViewScroller != null) {
              Rect rect = new Rect();
              Input.this.mNativeNestWebView.getGlobalVisibleRect(rect);
              int i = webViewScroller.computeOffsetWhenKeyboardShow((View)Input.this, rect, 1);
              if (i != 0)
                Input.this.mNativeNestWebView.smoothOffsetTopAndBottom(i, (View)Input.this); 
            } 
          }
        });
  }
  
  public static Input getInputView(int paramInt1, AbsoluteLayout paramAbsoluteLayout, WebViewManager.IRender paramIRender, int paramInt2, String paramString, NativeNestWebView paramNativeNestWebView) {
    Input input2 = sLastCreatedInput;
    if (input2 != null) {
      WebViewManager.IRender iRender = input2.mRender;
      if (iRender != null && iRender.getNativeViewManager().hasView(sLastCreatedInput.mViewId))
        sLastCreatedInput.mRender.getNativeViewManager().removeView(sLastCreatedInput.mViewId, null); 
    } 
    Input input1 = new Input(paramInt1, paramAbsoluteLayout, paramIRender, paramInt2, paramString, paramNativeNestWebView);
    sLastCreatedInput = input1;
    return input1;
  }
  
  private int getPxDimension(double paramDouble) {
    return (int)Math.round(NativeDimenUtil.convertRxToPx(paramDouble));
  }
  
  private void init() {
    setSingleLine(true);
    setImeOptions(6);
    setBackgroundColor(0);
    setPadding(0, 0, 0, 0);
    setOnEditorActionListener(new TextView.OnEditorActionListener() {
          public boolean onEditorAction(TextView param1TextView, int param1Int, KeyEvent param1KeyEvent) {
            if (param1Int != 2 && param1Int != 3 && param1Int != 4 && param1Int != 5 && param1Int != 6)
              return false; 
            Input.this.publishKeyboardConfirmEvent();
            InputMethodUtil.hideSoftKeyboard(Input.this, param1TextView.getContext().getApplicationContext());
            return true;
          }
        });
    setOnFocusChangeListener(this);
    addTextChangedListener((TextWatcher)new TextChangedAdapter() {
          public void afterTextChanged(Editable param1Editable) {
            boolean bool;
            if (Input.this.syncNextTextChangeToJs) {
              Input.this.syncChangeToJs();
            } else {
              Input.this.syncNextTextChangeToJs = true;
            } 
            TextPaint textPaint = Input.this.getPaint();
            if (TextUtils.isEmpty((CharSequence)param1Editable)) {
              bool = Input.this.mHintUseBoldText;
            } else {
              bool = Input.this.mInputUseBoldText;
            } 
            textPaint.setFakeBoldText(bool);
          }
        });
    this.mKeyBoardStateChange = new IKeyBoardStateChange() {
        public void onKeyboardHide() {}
        
        public void onKeyboardShow(int param1Int1, int param1Int2) {
          if (Input.this.mHasFocus)
            Input.this.publishOnKeyboardShowEvent(); 
        }
      };
    this.mNativeNestWebView.registerKeyBoardStateChange(this.mKeyBoardStateChange);
  }
  
  private void publishKeyboardCompleteEvent() {
    JSONObject jSONObject = (new JsonBuilder()).put("value", getValue()).put("inputId", Integer.valueOf(this.mViewId)).put("cursor", Integer.valueOf(getCursor())).build();
    AppbrandApplicationImpl.getInst().getWebViewManager().publish(this.mWebViewId, "onKeyboardComplete", jSONObject.toString());
  }
  
  public void addView(String paramString, k paramk) {
    updateView(paramString, false);
    this.mParent.addView((View)this, getLayoutParams());
    requestFocus();
    postDelayed(new Runnable() {
          public void run() {
            InputMethodUtil.showSoftKeyboard((View)Input.this, (Context)AppbrandContext.getInst().getApplicationContext());
          }
        }0L);
  }
  
  public boolean getConfirm() {
    return false;
  }
  
  public int getCursor() {
    return getSelectionStart();
  }
  
  public int getInputHeight() {
    return (this.marginBottom > 0) ? (getMeasuredHeight() + this.marginBottom) : getMeasuredHeight();
  }
  
  public String getType() {
    return "input";
  }
  
  public String getValue() {
    return getText().toString();
  }
  
  public boolean hasFocus() {
    return super.hasFocus();
  }
  
  public boolean isAdjustPosition() {
    return this.isAdjustPosition;
  }
  
  public boolean isAutoBlur() {
    return this.mAutoBlur;
  }
  
  public boolean isFixed() {
    return this.isFixed;
  }
  
  public boolean onBackPressed() {
    return false;
  }
  
  public void onDestroy() {}
  
  public void onFocusChange(View paramView, boolean paramBoolean) {
    this.mHasFocus = paramBoolean;
    if (!paramBoolean) {
      if (KeyboardHeightProvider.getKeyboardHeight() != 0)
        InputMethodUtil.hideSoftKeyboard(this, (Context)AppbrandContext.getInst().getApplicationContext()); 
      WebViewManager.IRender iRender = this.mRender;
      if (iRender != null && iRender.getNativeViewManager().hasView(this.mViewId))
        this.mRender.getNativeViewManager().removeView(this.mViewId, null); 
    } 
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent) {
    boolean bool = super.onTouchEvent(paramMotionEvent);
    if (paramMotionEvent.getAction() == 3)
      clearFocus(); 
    return bool;
  }
  
  public void onViewPause() {}
  
  public void onViewResume() {}
  
  public void publishKeyboardConfirmEvent() {
    JSONObject jSONObject = (new JsonBuilder()).put("value", getValue()).put("inputId", Integer.valueOf(this.mViewId)).put("cursor", Integer.valueOf(getCursor())).build();
    AppbrandApplicationImpl.getInst().getWebViewManager().publish(this.mWebViewId, "onKeyboardConfirm", jSONObject.toString());
  }
  
  public void publishOnKeyboardShowEvent() {
    JSONObject jSONObject = (new JsonBuilder()).put("inputId", Integer.valueOf(this.mViewId)).put("height", Integer.valueOf(NativeDimenUtil.convertPxToRx(KeyboardHeightProvider.getKeyboardHeight()))).build();
    AppbrandApplicationImpl.getInst().getWebViewManager().publish(this.mWebViewId, "onKeyboardShow", jSONObject.toString());
    this.mHasSentKeyboardShow = true;
  }
  
  public void removeView(int paramInt, k paramk) {
    IKeyBoardStateChange iKeyBoardStateChange = this.mKeyBoardStateChange;
    if (iKeyBoardStateChange != null)
      this.mNativeNestWebView.unregisterKeyBoardStateChange(iKeyBoardStateChange); 
    if (!this.mHasSentKeyboardShow)
      publishOnKeyboardShowEvent(); 
    publishKeyboardCompleteEvent();
    if (sLastCreatedInput == this)
      sLastCreatedInput = null; 
  }
  
  public boolean requestRectangleOnScreen(Rect paramRect, boolean paramBoolean) {
    if (Build.VERSION.SDK_INT >= 17 && Build.VERSION.SDK_INT <= 23)
      paramRect.offset(-getScrollX(), -getScrollY()); 
    return super.requestRectangleOnScreen(paramRect, paramBoolean);
  }
  
  public void setSelection(int paramInt) {
    if (paramInt <= getText().length())
      super.setSelection(paramInt); 
  }
  
  public void setSelection(int paramInt1, int paramInt2) {
    int i = getText().length();
    if (paramInt2 <= i && paramInt1 < i) {
      super.setSelection(paramInt1, paramInt2);
      return;
    } 
    if (i < paramInt2 && i > paramInt1)
      super.setSelection(paramInt1, i); 
  }
  
  public void setText(String paramString, boolean paramBoolean) {
    this.syncNextTextChangeToJs = paramBoolean;
    setText(paramString);
  }
  
  public void setValue(String paramString) {
    setText(paramString, false);
  }
  
  public void syncChangeToJs() {
    try {
      JSONObject jSONObject = new JSONObject();
      jSONObject.put("value", getValue());
      jSONObject.put("inputId", this.mViewId);
      jSONObject.put("cursor", getCursor());
      jSONObject.put("data", this.mDataObject);
      AppbrandApplication.getInst().getJsBridge().sendMsgToJsCore("onKeyboardValueChange", jSONObject.toString(), this.mWebViewId);
      return;
    } catch (JSONException jSONException) {
      AppBrandLogger.stacktrace(6, "tma_Input", jSONException.getStackTrace());
      return;
    } 
  }
  
  public void updateSelectionOrCursor(String paramString) {
    try {
      JSONObject jSONObject = new JSONObject(paramString);
      int j = jSONObject.optInt("cursor", -1);
      int n = jSONObject.optInt("selectionStart", -1);
      int m = jSONObject.optInt("selectionEnd", -1);
      int k = m;
      if (n != -1)
        if (m == -1) {
          setSelection(n, getText().length());
          k = m;
        } else {
          int i1 = m;
          if (m > getText().length())
            i1 = getText().length(); 
          setSelection(n, i1);
          k = i1;
        }  
      int i = j;
      if (j != -1) {
        i = j;
        if (j > getText().length())
          i = getText().length(); 
        setSelection(i);
      } 
      if (i == -1 && n == -1 && k == -1)
        setSelection(getText().length()); 
      return;
    } catch (JSONException jSONException) {
      AppBrandLogger.e("tma_Input", new Object[] { "update selection or cursor", jSONException });
      return;
    } 
  }
  
  public void updateView(String paramString, k paramk) {
    updateView(paramString, false);
  }
  
  public void updateView(String paramString, boolean paramBoolean) {
    // Byte code:
    //   0: new org/json/JSONObject
    //   3: dup
    //   4: aload_1
    //   5: invokespecial <init> : (Ljava/lang/String;)V
    //   8: astore #12
    //   10: aload #12
    //   12: ldc_w 'fixed'
    //   15: invokevirtual has : (Ljava/lang/String;)Z
    //   18: ifeq -> 37
    //   21: aload_0
    //   22: aload #12
    //   24: ldc_w 'fixed'
    //   27: aload_0
    //   28: getfield isFixed : Z
    //   31: invokevirtual optBoolean : (Ljava/lang/String;Z)Z
    //   34: putfield isFixed : Z
    //   37: aload #12
    //   39: ldc_w 'style'
    //   42: invokevirtual optJSONObject : (Ljava/lang/String;)Lorg/json/JSONObject;
    //   45: astore #13
    //   47: bipush #8
    //   49: istore #5
    //   51: aload #13
    //   53: ifnull -> 1294
    //   56: aload_0
    //   57: aload_0
    //   58: aload #13
    //   60: ldc_w 'marginBottom'
    //   63: invokevirtual optInt : (Ljava/lang/String;)I
    //   66: i2d
    //   67: invokespecial getPxDimension : (D)I
    //   70: putfield marginBottom : I
    //   73: aload_0
    //   74: aload #13
    //   76: ldc_w 'width'
    //   79: invokevirtual optDouble : (Ljava/lang/String;)D
    //   82: invokespecial getPxDimension : (D)I
    //   85: istore #8
    //   87: aload_0
    //   88: aload #13
    //   90: ldc_w 'height'
    //   93: invokevirtual optDouble : (Ljava/lang/String;)D
    //   96: invokespecial getPxDimension : (D)I
    //   99: istore #9
    //   101: aload_0
    //   102: aload #13
    //   104: ldc_w 'left'
    //   107: invokevirtual optDouble : (Ljava/lang/String;)D
    //   110: invokespecial getPxDimension : (D)I
    //   113: istore #7
    //   115: aload_0
    //   116: aload #13
    //   118: ldc_w 'top'
    //   121: invokevirtual optDouble : (Ljava/lang/String;)D
    //   124: invokespecial getPxDimension : (D)I
    //   127: istore #6
    //   129: iload #7
    //   131: istore #4
    //   133: iload #6
    //   135: istore_3
    //   136: aload_0
    //   137: getfield isFixed : Z
    //   140: ifne -> 166
    //   143: iload #7
    //   145: aload_0
    //   146: getfield mParent : Lcom/tt/miniapp/view/webcore/AbsoluteLayout;
    //   149: invokevirtual getCurScrollX : ()I
    //   152: isub
    //   153: istore #4
    //   155: iload #6
    //   157: aload_0
    //   158: getfield mParent : Lcom/tt/miniapp/view/webcore/AbsoluteLayout;
    //   161: invokevirtual getCurScrollY : ()I
    //   164: isub
    //   165: istore_3
    //   166: ldc_w 'tma_Input'
    //   169: bipush #8
    //   171: anewarray java/lang/Object
    //   174: dup
    //   175: iconst_0
    //   176: ldc_w 'width '
    //   179: aastore
    //   180: dup
    //   181: iconst_1
    //   182: iload #8
    //   184: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   187: aastore
    //   188: dup
    //   189: iconst_2
    //   190: ldc_w ' height '
    //   193: aastore
    //   194: dup
    //   195: iconst_3
    //   196: iload #9
    //   198: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   201: aastore
    //   202: dup
    //   203: iconst_4
    //   204: ldc_w ' x '
    //   207: aastore
    //   208: dup
    //   209: iconst_5
    //   210: iload #4
    //   212: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   215: aastore
    //   216: dup
    //   217: bipush #6
    //   219: ldc_w ' y '
    //   222: aastore
    //   223: dup
    //   224: bipush #7
    //   226: iload_3
    //   227: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   230: aastore
    //   231: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   234: aload_0
    //   235: invokevirtual getLayoutParams : ()Landroid/view/ViewGroup$LayoutParams;
    //   238: checkcast com/tt/miniapp/view/webcore/AbsoluteLayout$LayoutParams
    //   241: astore #11
    //   243: aload #11
    //   245: ifnonnull -> 273
    //   248: new com/tt/miniapp/view/webcore/AbsoluteLayout$LayoutParams
    //   251: dup
    //   252: iload #8
    //   254: iconst_1
    //   255: iadd
    //   256: iload #9
    //   258: iconst_1
    //   259: iadd
    //   260: iload #4
    //   262: iconst_1
    //   263: isub
    //   264: iload_3
    //   265: invokespecial <init> : (IIII)V
    //   268: astore #11
    //   270: goto -> 306
    //   273: aload #11
    //   275: iload #8
    //   277: iconst_1
    //   278: iadd
    //   279: putfield width : I
    //   282: aload #11
    //   284: iload #9
    //   286: iconst_1
    //   287: iadd
    //   288: putfield height : I
    //   291: aload #11
    //   293: iload #4
    //   295: iconst_1
    //   296: isub
    //   297: putfield x : I
    //   300: aload #11
    //   302: iload_3
    //   303: putfield y : I
    //   306: aload_0
    //   307: aload #11
    //   309: invokevirtual setLayoutParams : (Landroid/view/ViewGroup$LayoutParams;)V
    //   312: aload #13
    //   314: ldc_w 'backgroundColor'
    //   317: invokevirtual has : (Ljava/lang/String;)Z
    //   320: ifeq -> 376
    //   323: aload #13
    //   325: ldc_w 'backgroundColor'
    //   328: invokevirtual optString : (Ljava/lang/String;)Ljava/lang/String;
    //   331: ldc_w '#00000000'
    //   334: invokestatic rgbaToFullARGBStr : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   337: astore #11
    //   339: aload #11
    //   341: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   344: istore #10
    //   346: iload #10
    //   348: ifne -> 376
    //   351: aload_0
    //   352: aload #11
    //   354: invokestatic parseColor : (Ljava/lang/String;)I
    //   357: invokevirtual setBackgroundColor : (I)V
    //   360: goto -> 376
    //   363: astore #11
    //   365: ldc_w 'tma_Input'
    //   368: ldc_w 'input backgroundColor error'
    //   371: aload #11
    //   373: invokestatic eWithThrowable : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   376: aload #13
    //   378: ldc_w 'color'
    //   381: invokevirtual has : (Ljava/lang/String;)Z
    //   384: ifeq -> 440
    //   387: aload #13
    //   389: ldc_w 'color'
    //   392: invokevirtual optString : (Ljava/lang/String;)Ljava/lang/String;
    //   395: ldc_w '#000000'
    //   398: invokestatic rgbaToFullARGBStr : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   401: astore #11
    //   403: aload #11
    //   405: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   408: istore #10
    //   410: iload #10
    //   412: ifne -> 440
    //   415: aload_0
    //   416: aload #11
    //   418: invokestatic parseColor : (Ljava/lang/String;)I
    //   421: invokevirtual setTextColor : (I)V
    //   424: goto -> 440
    //   427: astore #11
    //   429: ldc_w 'tma_Input'
    //   432: ldc_w 'input textColor color'
    //   435: aload #11
    //   437: invokestatic eWithThrowable : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   440: aload #13
    //   442: ldc_w 'fontSize'
    //   445: invokevirtual optInt : (Ljava/lang/String;)I
    //   448: istore_3
    //   449: iload_3
    //   450: ifle -> 459
    //   453: aload_0
    //   454: iload_3
    //   455: i2f
    //   456: invokevirtual setTextSize : (F)V
    //   459: aload #13
    //   461: ldc_w 'fontWeight'
    //   464: invokevirtual optString : (Ljava/lang/String;)Ljava/lang/String;
    //   467: astore #11
    //   469: aload #11
    //   471: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   474: ifne -> 489
    //   477: aload_0
    //   478: aload #11
    //   480: ldc_w 'bold'
    //   483: invokestatic equals : (Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Z
    //   486: putfield mInputUseBoldText : Z
    //   489: aload #13
    //   491: ldc_w 'fontFamily'
    //   494: invokevirtual optString : (Ljava/lang/String;)Ljava/lang/String;
    //   497: pop
    //   498: aload #13
    //   500: ldc_w 'textAlign'
    //   503: invokevirtual optString : (Ljava/lang/String;)Ljava/lang/String;
    //   506: astore #11
    //   508: aload #11
    //   510: ldc_w 'center'
    //   513: invokestatic equals : (Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Z
    //   516: ifeq -> 528
    //   519: aload_0
    //   520: bipush #17
    //   522: invokevirtual setGravity : (I)V
    //   525: goto -> 596
    //   528: aload #11
    //   530: ldc_w 'left'
    //   533: invokestatic equals : (Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Z
    //   536: ifne -> 587
    //   539: aload #11
    //   541: ldc_w 'right'
    //   544: invokestatic equals : (Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Z
    //   547: ifeq -> 559
    //   550: aload_0
    //   551: bipush #21
    //   553: invokevirtual setGravity : (I)V
    //   556: goto -> 596
    //   559: invokestatic getInst : ()Lcom/tt/miniapphost/language/LocaleManager;
    //   562: invokevirtual isRTL : ()Z
    //   565: ifeq -> 587
    //   568: aload_0
    //   569: iconst_4
    //   570: invokevirtual setTextDirection : (I)V
    //   573: aload_0
    //   574: iconst_1
    //   575: invokevirtual setLayoutDirection : (I)V
    //   578: aload_0
    //   579: bipush #21
    //   581: invokevirtual setGravity : (I)V
    //   584: goto -> 596
    //   587: aload_0
    //   588: bipush #19
    //   590: invokevirtual setGravity : (I)V
    //   593: goto -> 596
    //   596: aload #12
    //   598: ldc_w 'placeholderStyle'
    //   601: invokevirtual optJSONObject : (Ljava/lang/String;)Lorg/json/JSONObject;
    //   604: astore #11
    //   606: aload #11
    //   608: ifnull -> 724
    //   611: aload #11
    //   613: ldc_w 'color'
    //   616: invokevirtual has : (Ljava/lang/String;)Z
    //   619: ifeq -> 675
    //   622: aload #11
    //   624: ldc_w 'color'
    //   627: invokevirtual optString : (Ljava/lang/String;)Ljava/lang/String;
    //   630: ldc_w '#888888'
    //   633: invokestatic rgbaToFullARGBStr : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   636: astore #13
    //   638: aload #13
    //   640: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   643: istore #10
    //   645: iload #10
    //   647: ifne -> 675
    //   650: aload_0
    //   651: aload #13
    //   653: invokestatic parseColor : (Ljava/lang/String;)I
    //   656: invokevirtual setHintTextColor : (I)V
    //   659: goto -> 675
    //   662: astore #13
    //   664: ldc_w 'tma_Input'
    //   667: ldc_w 'input placeHolderColor error'
    //   670: aload #13
    //   672: invokestatic eWithThrowable : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   675: aload #11
    //   677: ldc_w 'fontSize'
    //   680: invokevirtual optInt : (Ljava/lang/String;)I
    //   683: istore_3
    //   684: iload_3
    //   685: ifle -> 694
    //   688: aload_0
    //   689: iload_3
    //   690: i2f
    //   691: invokevirtual setTextSize : (F)V
    //   694: aload #11
    //   696: ldc_w 'fontWeight'
    //   699: invokevirtual optString : (Ljava/lang/String;)Ljava/lang/String;
    //   702: astore #11
    //   704: aload #11
    //   706: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   709: ifne -> 724
    //   712: aload_0
    //   713: aload #11
    //   715: ldc_w 'bold'
    //   718: invokestatic equals : (Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Z
    //   721: putfield mHintUseBoldText : Z
    //   724: aload_0
    //   725: invokevirtual getLayoutParams : ()Landroid/view/ViewGroup$LayoutParams;
    //   728: checkcast com/tt/miniapp/view/webcore/AbsoluteLayout$LayoutParams
    //   731: astore #11
    //   733: aload #12
    //   735: ldc_w 'zIndex'
    //   738: invokevirtual has : (Ljava/lang/String;)Z
    //   741: ifeq -> 762
    //   744: aload #11
    //   746: aload #12
    //   748: ldc_w 'zIndex'
    //   751: aload #11
    //   753: getfield zIndex : I
    //   756: invokevirtual optInt : (Ljava/lang/String;I)I
    //   759: putfield zIndex : I
    //   762: aload #11
    //   764: aload_0
    //   765: getfield isFixed : Z
    //   768: putfield isFixed : Z
    //   771: aload_0
    //   772: aload #11
    //   774: invokevirtual setLayoutParams : (Landroid/view/ViewGroup$LayoutParams;)V
    //   777: aload_0
    //   778: getfield mParent : Lcom/tt/miniapp/view/webcore/AbsoluteLayout;
    //   781: aload_0
    //   782: getfield mViewId : I
    //   785: invokevirtual updateCurScrollXY : (I)V
    //   788: aload_0
    //   789: aload #12
    //   791: ldc_w 'adjustPosition'
    //   794: aload_0
    //   795: getfield isAdjustPosition : Z
    //   798: invokevirtual optBoolean : (Ljava/lang/String;Z)Z
    //   801: putfield isAdjustPosition : Z
    //   804: aload_0
    //   805: aload #12
    //   807: ldc_w 'data'
    //   810: aload_0
    //   811: getfield mDataObject : Ljava/lang/String;
    //   814: invokevirtual optString : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   817: putfield mDataObject : Ljava/lang/String;
    //   820: aload_0
    //   821: aload #12
    //   823: ldc_w 'autoBlur'
    //   826: aload_0
    //   827: getfield mAutoBlur : Z
    //   830: invokevirtual optBoolean : (Ljava/lang/String;Z)Z
    //   833: putfield mAutoBlur : Z
    //   836: aload #12
    //   838: ldc_w 'maxLength'
    //   841: invokevirtual optInt : (Ljava/lang/String;)I
    //   844: istore_3
    //   845: iload_3
    //   846: ifle -> 868
    //   849: aload_0
    //   850: iconst_1
    //   851: anewarray android/text/InputFilter
    //   854: dup
    //   855: iconst_0
    //   856: new android/text/InputFilter$LengthFilter
    //   859: dup
    //   860: iload_3
    //   861: invokespecial <init> : (I)V
    //   864: aastore
    //   865: invokevirtual setFilters : ([Landroid/text/InputFilter;)V
    //   868: aload #12
    //   870: ldc_w 'placeholder'
    //   873: invokevirtual has : (Ljava/lang/String;)Z
    //   876: ifeq -> 891
    //   879: aload_0
    //   880: aload #12
    //   882: ldc_w 'placeholder'
    //   885: invokevirtual optString : (Ljava/lang/String;)Ljava/lang/String;
    //   888: invokevirtual setHint : (Ljava/lang/CharSequence;)V
    //   891: aload #12
    //   893: ldc 'value'
    //   895: invokevirtual has : (Ljava/lang/String;)Z
    //   898: ifeq -> 913
    //   901: aload_0
    //   902: aload #12
    //   904: ldc 'value'
    //   906: invokevirtual optString : (Ljava/lang/String;)Ljava/lang/String;
    //   909: iload_2
    //   910: invokevirtual setText : (Ljava/lang/String;Z)V
    //   913: aload #12
    //   915: ldc_w 'password'
    //   918: invokevirtual has : (Ljava/lang/String;)Z
    //   921: ifeq -> 954
    //   924: aload #12
    //   926: ldc_w 'password'
    //   929: invokevirtual optBoolean : (Ljava/lang/String;)Z
    //   932: ifeq -> 943
    //   935: invokestatic getInstance : ()Landroid/text/method/PasswordTransformationMethod;
    //   938: astore #11
    //   940: goto -> 948
    //   943: invokestatic getInstance : ()Landroid/text/method/SingleLineTransformationMethod;
    //   946: astore #11
    //   948: aload_0
    //   949: aload #11
    //   951: invokevirtual setTransformationMethod : (Landroid/text/method/TransformationMethod;)V
    //   954: aload #12
    //   956: ldc_w 'hidden'
    //   959: invokevirtual has : (Ljava/lang/String;)Z
    //   962: ifeq -> 988
    //   965: aload #12
    //   967: ldc_w 'hidden'
    //   970: iconst_1
    //   971: invokevirtual optBoolean : (Ljava/lang/String;Z)Z
    //   974: ifeq -> 1297
    //   977: iload #5
    //   979: istore_3
    //   980: goto -> 983
    //   983: aload_0
    //   984: iload_3
    //   985: invokevirtual setVisibility : (I)V
    //   988: aload #12
    //   990: ldc_w 'disabled'
    //   993: invokevirtual has : (Ljava/lang/String;)Z
    //   996: ifeq -> 1021
    //   999: aload #12
    //   1001: ldc_w 'disabled'
    //   1004: iconst_0
    //   1005: invokevirtual optBoolean : (Ljava/lang/String;Z)Z
    //   1008: ifne -> 1302
    //   1011: iconst_1
    //   1012: istore_2
    //   1013: goto -> 1016
    //   1016: aload_0
    //   1017: iload_2
    //   1018: invokevirtual setEnabled : (Z)V
    //   1021: aload #12
    //   1023: ldc_w 'type'
    //   1026: invokevirtual optString : (Ljava/lang/String;)Ljava/lang/String;
    //   1029: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   1032: ifne -> 1048
    //   1035: aload #12
    //   1037: ldc_w 'type'
    //   1040: invokevirtual optString : (Ljava/lang/String;)Ljava/lang/String;
    //   1043: astore #11
    //   1045: goto -> 1054
    //   1048: aload_0
    //   1049: getfield mType : Ljava/lang/String;
    //   1052: astore #11
    //   1054: aload_0
    //   1055: aload #11
    //   1057: putfield mType : Ljava/lang/String;
    //   1060: ldc_w 'text'
    //   1063: aload_0
    //   1064: getfield mType : Ljava/lang/String;
    //   1067: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1070: ifeq -> 1081
    //   1073: aload_0
    //   1074: iconst_1
    //   1075: invokevirtual setInputType : (I)V
    //   1078: goto -> 1134
    //   1081: ldc_w 'digit'
    //   1084: aload_0
    //   1085: getfield mType : Ljava/lang/String;
    //   1088: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1091: istore_2
    //   1092: iload_2
    //   1093: ifeq -> 1114
    //   1096: aload_0
    //   1097: iconst_2
    //   1098: invokevirtual setInputType : (I)V
    //   1101: aload_0
    //   1102: ldc_w '0123456789.'
    //   1105: invokestatic getInstance : (Ljava/lang/String;)Landroid/text/method/DigitsKeyListener;
    //   1108: invokevirtual setKeyListener : (Landroid/text/method/KeyListener;)V
    //   1111: goto -> 1134
    //   1114: ldc_w 'number'
    //   1117: aload_0
    //   1118: getfield mType : Ljava/lang/String;
    //   1121: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1124: istore_2
    //   1125: iload_2
    //   1126: ifeq -> 1134
    //   1129: aload_0
    //   1130: iconst_2
    //   1131: invokevirtual setInputType : (I)V
    //   1134: aload #12
    //   1136: ldc_w 'confirmType'
    //   1139: invokevirtual optString : (Ljava/lang/String;)Ljava/lang/String;
    //   1142: astore #11
    //   1144: ldc_w 'done'
    //   1147: aload #11
    //   1149: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1152: ifeq -> 1164
    //   1155: aload_0
    //   1156: bipush #6
    //   1158: invokevirtual setImeOptions : (I)V
    //   1161: goto -> 1239
    //   1164: ldc_w 'go'
    //   1167: aload #11
    //   1169: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1172: istore_2
    //   1173: iload_2
    //   1174: ifeq -> 1185
    //   1177: aload_0
    //   1178: iconst_2
    //   1179: invokevirtual setImeOptions : (I)V
    //   1182: goto -> 1239
    //   1185: ldc_w 'next'
    //   1188: aload #11
    //   1190: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1193: ifeq -> 1204
    //   1196: aload_0
    //   1197: iconst_5
    //   1198: invokevirtual setImeOptions : (I)V
    //   1201: goto -> 1239
    //   1204: ldc_w 'search'
    //   1207: aload #11
    //   1209: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1212: ifeq -> 1223
    //   1215: aload_0
    //   1216: iconst_3
    //   1217: invokevirtual setImeOptions : (I)V
    //   1220: goto -> 1239
    //   1223: ldc_w 'send'
    //   1226: aload #11
    //   1228: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1231: ifeq -> 1239
    //   1234: aload_0
    //   1235: iconst_4
    //   1236: invokevirtual setImeOptions : (I)V
    //   1239: aload_0
    //   1240: aload_1
    //   1241: invokevirtual updateSelectionOrCursor : (Ljava/lang/String;)V
    //   1244: aload_0
    //   1245: invokespecial adjustOffsetIfNeed : ()V
    //   1248: goto -> 1272
    //   1251: astore_1
    //   1252: ldc_w 'tma_Input'
    //   1255: iconst_2
    //   1256: anewarray java/lang/Object
    //   1259: dup
    //   1260: iconst_0
    //   1261: ldc_w 'updateView'
    //   1264: aastore
    //   1265: dup
    //   1266: iconst_1
    //   1267: aload_1
    //   1268: aastore
    //   1269: invokestatic e : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   1272: aload_0
    //   1273: getfield mNativeNestWebView : Lcom/tt/miniapp/view/webcore/NativeNestWebView;
    //   1276: astore_1
    //   1277: aload_1
    //   1278: ifnull -> 1289
    //   1281: aload_1
    //   1282: iconst_0
    //   1283: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   1286: invokevirtual setShowConfirmBar : (Ljava/lang/Boolean;)V
    //   1289: return
    //   1290: astore_1
    //   1291: goto -> 1252
    //   1294: goto -> 596
    //   1297: iconst_0
    //   1298: istore_3
    //   1299: goto -> 983
    //   1302: iconst_0
    //   1303: istore_2
    //   1304: goto -> 1016
    //   1307: astore_1
    //   1308: goto -> 1252
    // Exception table:
    //   from	to	target	type
    //   0	37	1251	org/json/JSONException
    //   37	47	1251	org/json/JSONException
    //   56	129	1251	org/json/JSONException
    //   136	166	1251	org/json/JSONException
    //   166	243	1251	org/json/JSONException
    //   248	270	1251	org/json/JSONException
    //   273	306	1251	org/json/JSONException
    //   306	346	1251	org/json/JSONException
    //   351	360	363	com/tt/miniapphost/util/IllegalColorException
    //   351	360	1251	org/json/JSONException
    //   365	376	1251	org/json/JSONException
    //   376	410	1251	org/json/JSONException
    //   415	424	427	com/tt/miniapphost/util/IllegalColorException
    //   415	424	1251	org/json/JSONException
    //   429	440	1251	org/json/JSONException
    //   440	449	1251	org/json/JSONException
    //   453	459	1251	org/json/JSONException
    //   459	489	1251	org/json/JSONException
    //   489	525	1251	org/json/JSONException
    //   528	556	1251	org/json/JSONException
    //   559	584	1251	org/json/JSONException
    //   587	593	1251	org/json/JSONException
    //   596	606	1251	org/json/JSONException
    //   611	645	1251	org/json/JSONException
    //   650	659	662	com/tt/miniapphost/util/IllegalColorException
    //   650	659	1251	org/json/JSONException
    //   664	675	1251	org/json/JSONException
    //   675	684	1251	org/json/JSONException
    //   688	694	1251	org/json/JSONException
    //   694	724	1251	org/json/JSONException
    //   724	762	1251	org/json/JSONException
    //   762	845	1251	org/json/JSONException
    //   849	868	1251	org/json/JSONException
    //   868	891	1251	org/json/JSONException
    //   891	913	1251	org/json/JSONException
    //   913	940	1251	org/json/JSONException
    //   943	948	1251	org/json/JSONException
    //   948	954	1251	org/json/JSONException
    //   954	977	1251	org/json/JSONException
    //   983	988	1251	org/json/JSONException
    //   988	1011	1251	org/json/JSONException
    //   1016	1021	1251	org/json/JSONException
    //   1021	1045	1251	org/json/JSONException
    //   1048	1054	1251	org/json/JSONException
    //   1054	1078	1251	org/json/JSONException
    //   1081	1092	1251	org/json/JSONException
    //   1096	1101	1307	org/json/JSONException
    //   1101	1111	1251	org/json/JSONException
    //   1114	1125	1251	org/json/JSONException
    //   1129	1134	1307	org/json/JSONException
    //   1134	1161	1251	org/json/JSONException
    //   1164	1173	1251	org/json/JSONException
    //   1177	1182	1307	org/json/JSONException
    //   1185	1201	1251	org/json/JSONException
    //   1204	1220	1251	org/json/JSONException
    //   1223	1239	1251	org/json/JSONException
    //   1239	1248	1251	org/json/JSONException
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\component\nativeview\Input.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */