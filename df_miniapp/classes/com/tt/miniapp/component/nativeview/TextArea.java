package com.tt.miniapp.component.nativeview;

import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Layout;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.IKeyBoardStateChange;
import com.tt.miniapp.WebViewManager;
import com.tt.miniapp.keyboarddetect.KeyboardHeightProvider;
import com.tt.miniapp.util.ConcaveScreenUtils;
import com.tt.miniapp.util.DevicesUtil;
import com.tt.miniapp.view.webcore.AbsoluteLayout;
import com.tt.miniapp.view.webcore.NativeNestWebView;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandApplication;
import com.tt.miniapphost.language.LocaleManager;
import com.tt.miniapphost.util.CharacterUtils;
import com.tt.miniapphost.util.IllegalColorException;
import com.tt.miniapphost.util.JsonBuilder;
import com.tt.miniapphost.util.NativeDimenUtil;
import com.tt.miniapphost.util.UIUtils;
import com.tt.option.e.k;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Stack;
import org.json.JSONException;
import org.json.JSONObject;

public class TextArea extends EditText implements InputComponent, NativeComponent {
  int cursor = -1;
  
  public boolean edFlag;
  
  private boolean isAdjustPosition = true;
  
  private boolean isFixed;
  
  private boolean mAutoBlur = true;
  
  public boolean mConfirm;
  
  private String mDataObject;
  
  boolean mHasFocus = false;
  
  public boolean mHasUpdated;
  
  public boolean mKeyBoardShow = false;
  
  private IKeyBoardStateChange mKeyBoardStateChange;
  
  public int mLineCount = 1;
  
  public TextAreaMode mModel;
  
  private NativeOffset mNaOffset;
  
  public NativeNestWebView mNativeNestWebView;
  
  public ViewTreeObserver.OnGlobalLayoutListener mOnGlobalLayoutListener;
  
  private AbsoluteLayout mParent;
  
  public WebViewManager.IRender mRender;
  
  int mSrceenH = UIUtils.getDeviceHeight(getContext());
  
  int mTextAreaHeight = 0;
  
  public int mViewId;
  
  public int mWebViewId;
  
  private int marginBottom;
  
  int maxLength = -1;
  
  int selectionEnd = -1;
  
  int selectionStart = -1;
  
  public Stack<Integer> stack = new Stack<Integer>();
  
  public TextArea(int paramInt1, AbsoluteLayout paramAbsoluteLayout, WebViewManager.IRender paramIRender, int paramInt2, NativeNestWebView paramNativeNestWebView) {
    super(paramAbsoluteLayout.getContext());
    this.mViewId = paramInt1;
    this.mParent = paramAbsoluteLayout;
    this.mRender = paramIRender;
    this.mWebViewId = paramInt2;
    this.mNativeNestWebView = paramNativeNestWebView;
  }
  
  private int getCurrentCursorLine() {
    int i = Selection.getSelectionStart((CharSequence)getText());
    Layout layout = getLayout();
    return (i != -1) ? (layout.getLineForOffset(i) + 1) : -1;
  }
  
  private float[] getCursorCoordinate() {
    boolean bool2;
    Class<? super EditText> clazz = EditText.class.getSuperclass();
    try {
      Field field1 = clazz.getDeclaredField("mEditor");
      field1.setAccessible(true);
      Object object = field1.get(this);
      Field field2 = Class.forName("android.widget.Editor").getDeclaredField("mCursorDrawable");
      field2.setAccessible(true);
      Drawable[] arrayOfDrawable = (Drawable[])field2.get(object);
      object = clazz.getDeclaredMethod("getVerticalOffset", new Class[] { boolean.class });
      Method method2 = clazz.getDeclaredMethod("getCompoundPaddingLeft", new Class[0]);
      Method method1 = clazz.getDeclaredMethod("getExtendedPaddingTop", new Class[0]);
      object.setAccessible(true);
      method2.setAccessible(true);
      method1.setAccessible(true);
      if (arrayOfDrawable != null) {
        Rect rect = arrayOfDrawable[0].getBounds();
        AppBrandLogger.e("tma_Textarea", new Object[] { rect.toString() });
        int i = ((Integer)method2.invoke(this, new Object[0])).intValue();
        bool2 = rect.left;
        bool2 = i + bool2;
        try {
          i = ((Integer)method1.invoke(this, new Object[0])).intValue();
          int j = ((Integer)object.invoke(this, new Object[] { Boolean.valueOf(false) })).intValue();
          int k = rect.bottom;
          i = i + j + k;
          return new float[] { getX() + bool2, getY() + i };
        } catch (NoSuchMethodException|java.lang.reflect.InvocationTargetException|IllegalAccessException|NoSuchFieldException|ClassNotFoundException noSuchMethodException) {}
      } else {
        boolean bool = false;
        bool2 = false;
        return new float[] { getX() + bool2, getY() + bool };
      } 
    } catch (NoSuchMethodException|java.lang.reflect.InvocationTargetException|IllegalAccessException|NoSuchFieldException|ClassNotFoundException noSuchMethodException) {
      bool2 = false;
    } 
    boolean bool1 = false;
    return new float[] { getX() + bool2, getY() + bool1 };
  }
  
  private void synHeightChangeWhenAddView() {
    post(new Runnable() {
          public void run() {
            TextArea.this.syncHeightChangeToJs();
          }
        });
  }
  
  private String transformColor(String paramString) {
    if (TextUtils.isEmpty(paramString))
      return paramString; 
    String str = paramString;
    if (paramString.charAt(0) == '#') {
      str = paramString;
      if (paramString.length() == 9) {
        StringBuilder stringBuilder = new StringBuilder("#");
        stringBuilder.append(paramString.substring(7));
        stringBuilder.append(paramString.substring(1, 7));
        str = stringBuilder.toString();
      } 
    } 
    return str;
  }
  
  private boolean updateCursor(String paramString) {
    try {
      JSONObject jSONObject = new JSONObject(paramString);
      boolean bool = jSONObject.has("cursor");
      if (bool || jSONObject.has("selectionStart") || jSONObject.has("selectionEnd")) {
        if (jSONObject.has("selectionStart")) {
          this.selectionStart = jSONObject.optInt("selectionStart", -1);
          if (jSONObject.has("selectionEnd")) {
            this.selectionEnd = jSONObject.optInt("selectionEnd", -1);
            if (this.selectionEnd <= this.selectionStart) {
              this.cursor = this.selectionEnd;
              this.selectionStart = -1;
              this.selectionEnd = -1;
            } 
            if (this.maxLength != -1) {
              int i;
              if (this.selectionEnd >= this.maxLength) {
                i = this.selectionEnd - 1;
              } else {
                i = this.selectionEnd;
              } 
              this.selectionEnd = i;
              return true;
            } 
          } 
        } else if (jSONObject.has("cursor")) {
          this.cursor = jSONObject.optInt("cursor", -1);
          if (this.maxLength != -1) {
            int i;
            if (this.cursor >= this.maxLength) {
              i = this.maxLength - 1;
            } else {
              i = this.cursor;
            } 
            this.cursor = i;
          } 
        } 
        return true;
      } 
    } catch (Exception exception) {
      AppBrandLogger.e("tma_Textarea", new Object[] { "", exception });
    } 
    return false;
  }
  
  public void addView(String paramString, k paramk) {
    AppBrandLogger.i("tma_Textarea", new Object[] { "addView : ", paramString });
    TextAreaMode textAreaMode = TextAreaMode.parasTextAreaMode(paramString);
    this.isFixed = textAreaMode.fixed;
    this.isAdjustPosition = textAreaMode.adjustPosition;
    this.mModel = textAreaMode;
    this.mDataObject = textAreaMode.dataObject;
    this.mConfirm = textAreaMode.confirm;
    int n = textAreaMode.top;
    int m = textAreaMode.left;
    AppBrandLogger.i("tma_Textarea", new Object[] { "origin position：Left=", Integer.valueOf(m), ", Top=", Integer.valueOf(n), ", isFixed=", Boolean.valueOf(this.isFixed), ", curScroll： scrollX=", Integer.valueOf(this.mParent.getCurScrollX()), ", scrollY=", Integer.valueOf(this.mParent.getCurScrollY()) });
    int j = n;
    int i = m;
    if (!this.isFixed) {
      j = n - this.mParent.getCurScrollY();
      i = m - this.mParent.getCurScrollX();
      AppBrandLogger.i("tma_Textarea", new Object[] { "not fixed：scrollX=", Integer.valueOf(this.mParent.getCurScrollX()), ", scrollY=", Integer.valueOf(this.mParent.getCurScrollY()), "; TextArea-position: Left=", Integer.valueOf(i), ", Top=", Integer.valueOf(j) });
    } 
    int i1 = textAreaMode.width;
    String str = textAreaMode.placeHolder;
    this.mAutoBlur = textAreaMode.autoBlur;
    n = textAreaMode.minHeight;
    m = n;
    if (isAutoSize()) {
      m = n;
      if (n < textAreaMode.height)
        m = textAreaMode.height; 
    } 
    if (m >= 0)
      setMinHeight(m); 
    m = textAreaMode.maxHeight;
    if (m > 0)
      setMaxHeight(m); 
    this.marginBottom = textAreaMode.marginBottom;
    setPadding(0, 0, 0, 0);
    try {
      setBackgroundColor(Color.parseColor(transformColor(textAreaMode.backgroundColor)));
    } catch (IllegalArgumentException illegalArgumentException) {
      setBackgroundColor(Color.parseColor("#ffffff"));
    } 
    setOnFocusChangeListener(new View.OnFocusChangeListener() {
          public void onFocusChange(View param1View, boolean param1Boolean) {
            TextArea textArea = TextArea.this;
            textArea.mHasFocus = param1Boolean;
            if (textArea.mHasFocus) {
              if ((TextArea.this.isVisibleInScreen() & TextArea.this.mHasFocus) != 0 && !TextArea.this.mKeyBoardShow)
                TextArea.this.postDelayed(new Runnable() {
                      public void run() {
                        if (TextArea.this.mRender != null)
                          TextArea.this.mRender.showKeyboard(TextArea.this.mViewId); 
                      }
                    },  100L); 
              if (TextArea.this.mKeyBoardShow) {
                TextArea.this.sendOnKeyboardShowEvent();
                return;
              } 
            } else {
              JSONObject jSONObject = (new JsonBuilder()).put("value", TextArea.this.getValue()).put("inputId", Integer.valueOf(TextArea.this.mViewId)).put("cursor", Integer.valueOf(TextArea.this.getCursor())).build();
              AppbrandApplicationImpl.getInst().getWebViewManager().publish(TextArea.this.mWebViewId, "onKeyboardComplete", jSONObject.toString());
            } 
          }
        });
    this.maxLength = textAreaMode.maxLength;
    if (this.maxLength > 0)
      setFilters(new InputFilter[] { (InputFilter)new InputFilter.LengthFilter(textAreaMode.maxLength) }); 
    if (LocaleManager.getInst().isRTL()) {
      setTextDirection(4);
      setLayoutDirection(1);
      setGravity(5);
    } else {
      AppBrandLogger.d("tma_Textarea", new Object[] { "NOT RTL!" });
      setGravity(3);
    } 
    setHint(str);
    setText(textAreaMode.value);
    if (textAreaMode.lineSpace > 0)
      setLineSpacing(textAreaMode.lineSpace, 1.0F); 
    setTextSize(textAreaMode.fontSize);
    if (TextUtils.equals(textAreaMode.fontWeight, "bold")) {
      getPaint().setFakeBoldText(true);
    } else {
      getPaint().setFakeBoldText(false);
    } 
    if (textAreaMode.hasPlaceHolderStyle) {
      if (!TextUtils.isEmpty(textAreaMode.placeHolderColor))
        try {
          setHintTextColor(UIUtils.parseColor(textAreaMode.placeHolderColor));
        } catch (IllegalColorException illegalColorException) {
          AppBrandLogger.eWithThrowable("tma_Textarea", "textArea placeHolderColor error", (Throwable)illegalColorException);
        }  
      if (textAreaMode.placeHolderFontSize > 0)
        setTextSize(textAreaMode.placeHolderFontSize); 
      if (TextUtils.equals(textAreaMode.placeHolderFontWeight, "bold")) {
        getPaint().setFakeBoldText(true);
      } else {
        getPaint().setFakeBoldText(false);
      } 
    } 
    try {
      setTextColor(UIUtils.parseColor(textAreaMode.color));
    } catch (IllegalColorException illegalColorException) {
      AppBrandLogger.eWithThrowable("tma_Textarea", "textArea color error", (Throwable)illegalColorException);
    } 
    if (textAreaMode.height > 0 && !textAreaMode.autoSize) {
      m = textAreaMode.height;
    } else {
      m = -2;
    } 
    AbsoluteLayout.LayoutParams layoutParams = new AbsoluteLayout.LayoutParams(i1 + 1, m, i - 1, j);
    if (textAreaMode.hasZIndex)
      layoutParams.zIndex = textAreaMode.zIndex; 
    layoutParams.isFixed = this.isFixed;
    this.mParent.addView((View)this, (ViewGroup.LayoutParams)layoutParams);
    setPadding(1, 0, 0, 0);
    setOnTouchListener(new View.OnTouchListener() {
          public boolean onTouch(View param1View, MotionEvent param1MotionEvent) {
            TextArea textArea = TextArea.this;
            if (textArea.canVerticalScroll(textArea)) {
              param1View.getParent().requestDisallowInterceptTouchEvent(true);
              int i = param1MotionEvent.getAction() & 0xFF;
              if (i != 0) {
                if (i != 1)
                  return false; 
                param1View.getParent().requestDisallowInterceptTouchEvent(false);
              } 
            } 
            return false;
          }
        });
    setEnabled(textAreaMode.disabled ^ true);
    this.mKeyBoardStateChange = new IKeyBoardStateChange() {
        public void onKeyboardHide() {
          TextArea.this.mKeyBoardShow = false;
          if (ConcaveScreenUtils.isVivoConcaveScreen()) {
            if (TextArea.this.mConfirm)
              TextArea.this.mNativeNestWebView.hideConfirmBar(); 
            TextArea.this.clearFocus();
            AppBrandLogger.e("tma_Textarea", new Object[] { "onKeyboardShow onKeyboardHide ", (new JsonBuilder()).put("inputId", Integer.valueOf(this.this$0.mViewId)).put("cursor", Integer.valueOf(this.this$0.getCursor())).put("value", this.this$0.getValue()).build().toString() });
          } 
          while (!TextArea.this.stack.empty())
            TextArea.this.stack.pop(); 
          if (TextArea.this.mConfirm)
            TextArea.this.mNativeNestWebView.hideConfirmBar(); 
          TextArea.this.clearFocus();
        }
        
        public void onKeyboardShow(int param1Int1, int param1Int2) {
          TextArea textArea = TextArea.this;
          textArea.mKeyBoardShow = true;
          if (textArea.mConfirm)
            TextArea.this.mNativeNestWebView.showConfirmBar(); 
          if (TextArea.this.mHasFocus)
            TextArea.this.sendOnKeyboardShowEvent(); 
        }
      };
    if (textAreaMode.confirm)
      this.mConfirm = true; 
    this.mNativeNestWebView.registerKeyBoardStateChange(this.mKeyBoardStateChange);
    if (!TextUtils.isEmpty((CharSequence)getText()))
      setSelection(getText().length()); 
    addTextChangedListener(new TextWatcher() {
          public void afterTextChanged(Editable param1Editable) {
            int i;
            if (TextArea.this.mHasUpdated) {
              TextArea.this.mHasUpdated = false;
            } else {
              TextArea.this.syncChangeToJs();
            } 
            if (TextArea.this.edFlag)
              return; 
            TextArea textArea = TextArea.this;
            textArea.edFlag = true;
            textArea.mTextAreaHeight = (textArea.getCursorRect()).bottom;
            if (!TextArea.this.isAutoSize()) {
              textArea = TextArea.this;
              textArea.mTextAreaHeight = textArea.getMeasuredHeight();
            } 
            StringBuilder stringBuilder = new StringBuilder("mTextAreaHeight = ");
            stringBuilder.append(TextArea.this.mTextAreaHeight);
            AppBrandLogger.e("tma_Textarea", new Object[] { stringBuilder.toString() });
            if (TextArea.this.getText().length() == 0) {
              i = 0;
            } else {
              i = TextArea.this.getLineCount();
            } 
            if (i != TextArea.this.mLineCount) {
              if (TextArea.this.isAutoSize() || (TextArea.this.getLayoutParams()).height == -2) {
                TextArea.this.smoothOffsetTopAndBottom();
                TextArea.this.edFlag = false;
                return;
              } 
              TextArea.this.syncHeightChangeToJs();
              int k = (TextArea.this.getCursorRect()).bottom;
              int j = k;
              if (k > TextArea.this.mTextAreaHeight) {
                j = k;
                if (!TextArea.this.isAutoSize())
                  j = TextArea.this.mTextAreaHeight; 
              } 
              k = KeyboardHeightProvider.getKeyboardHeight();
              int[] arrayOfInt = new int[2];
              TextArea.this.getLocationOnScreen(arrayOfInt);
              int m = arrayOfInt[1];
              if (TextArea.this.needToSmoothOffset() && i > TextArea.this.mLineCount && j + m > TextArea.this.mSrceenH - k) {
                i = TextArea.this.mSrceenH - k - j - m;
                TextArea.this.stack.push(Integer.valueOf(i));
                TextArea.this.mNativeNestWebView.smoothOffsetTopAndBottom(i, true, (View)TextArea.this);
              } else if (!TextArea.this.stack.empty() && i < TextArea.this.mLineCount && j + m < TextArea.this.mSrceenH - k) {
                i = -((Integer)TextArea.this.stack.pop()).intValue();
                TextArea.this.mNativeNestWebView.smoothOffsetTopAndBottom(i, true, (View)TextArea.this);
              } 
            } 
            TextArea.this.edFlag = false;
          }
          
          public void beforeTextChanged(CharSequence param1CharSequence, int param1Int1, int param1Int2, int param1Int3) {
            TextArea textArea = TextArea.this;
            if (textArea.getText().length() == 0) {
              param1Int1 = 0;
            } else {
              param1Int1 = TextArea.this.getLineCount();
            } 
            textArea.mLineCount = param1Int1;
          }
          
          public void onTextChanged(CharSequence param1CharSequence, int param1Int1, int param1Int2, int param1Int3) {}
        });
    synHeightChangeWhenAddView();
  }
  
  public boolean canVerticalScroll(EditText paramEditText) {
    try {
      int i = paramEditText.getScrollY();
      int j = paramEditText.getLayout().getHeight();
      int k = paramEditText.getHeight();
      int m = paramEditText.getCompoundPaddingTop();
      int n = paramEditText.getCompoundPaddingBottom();
      j -= k - m - n;
      return (j == 0) ? false : ((i <= 0) ? ((i < j - 1)) : true);
    } catch (Exception exception) {
      AppBrandLogger.e("tma_Textarea", new Object[] { exception });
      return false;
    } 
  }
  
  public boolean getConfirm() {
    return this.mConfirm;
  }
  
  public int getCursor() {
    return getSelectionStart();
  }
  
  public Rect getCursorRect() {
    Rect rect = new Rect();
    int i = getSelectionStart();
    Layout layout = getLayout();
    layout.getLineBounds(layout.getLineForOffset(i), rect);
    return rect;
  }
  
  public int getInputHeight() {
    int i;
    if (getCurrentCursorLine() * getLineHeight() > getMeasuredHeight()) {
      i = getMeasuredHeight();
    } else {
      i = getCurrentCursorLine() * getLineHeight();
    } 
    int k = this.marginBottom;
    int j = i;
    if (k > 0) {
      if (k >= getBottom() - getTop() - i) {
        j = getBottom() - getTop() - i;
      } else {
        j = this.marginBottom;
      } 
      this.marginBottom = j;
      j = i + this.marginBottom;
    } 
    return j;
  }
  
  public String getType() {
    return "textarea";
  }
  
  public String getValue() {
    return getText().toString();
  }
  
  public boolean hasFocus() {
    return this.mHasFocus;
  }
  
  public boolean isAdjustPosition() {
    return this.isAdjustPosition;
  }
  
  public boolean isAutoBlur() {
    return this.mAutoBlur;
  }
  
  public boolean isAutoSize() {
    TextAreaMode textAreaMode = this.mModel;
    return (textAreaMode != null && textAreaMode.autoSize);
  }
  
  public boolean isFixed() {
    return this.isFixed;
  }
  
  public boolean isVisibleInScreen() {
    return getLocalVisibleRect(new Rect(0, 0, DevicesUtil.getScreenWidth(getContext()), DevicesUtil.getScreenHight(getContext())));
  }
  
  public boolean needToSmoothOffset() {
    int i;
    int k = KeyboardHeightProvider.getKeyboardHeight();
    int j = (getCursorRect()).bottom;
    if (isAutoSize()) {
      if (j < this.mModel.minHeight) {
        i = (getCursorRect()).bottom;
      } else {
        i = j;
        if (j > this.mModel.maxHeight) {
          i = j;
          if (this.mModel.maxHeight > 0)
            i = this.mModel.maxHeight; 
        } 
      } 
    } else {
      i = j;
      if (j > getMeasuredHeight())
        i = getMeasuredHeight(); 
    } 
    j = this.mTextAreaHeight;
    int[] arrayOfInt = new int[2];
    getLocationOnScreen(arrayOfInt);
    int m = arrayOfInt[1];
    int n = this.mSrceenH;
    return (m + j > n - k && i + arrayOfInt[1] > n - k);
  }
  
  public boolean onBackPressed() {
    return false;
  }
  
  public void onDestroy() {}
  
  public void onViewPause() {}
  
  public void onViewResume() {}
  
  public void removeView(int paramInt, k paramk) {
    if (this.mOnGlobalLayoutListener != null) {
      getViewTreeObserver().removeOnGlobalLayoutListener(this.mOnGlobalLayoutListener);
      this.mOnGlobalLayoutListener = null;
    } 
    IKeyBoardStateChange iKeyBoardStateChange = this.mKeyBoardStateChange;
    if (iKeyBoardStateChange != null)
      this.mNativeNestWebView.unregisterKeyBoardStateChange(iKeyBoardStateChange); 
  }
  
  public boolean requestRectangleOnScreen(Rect paramRect, boolean paramBoolean) {
    if (Build.VERSION.SDK_INT >= 17 && Build.VERSION.SDK_INT <= 23)
      paramRect.offset(-getScrollX(), -getScrollY()); 
    return super.requestRectangleOnScreen(paramRect, paramBoolean);
  }
  
  public void sendOnKeyboardShowEvent() {
    JSONObject jSONObject = (new JsonBuilder()).put("inputId", Integer.valueOf(this.mViewId)).put("height", Integer.valueOf(NativeDimenUtil.convertPxToRx(KeyboardHeightProvider.getKeyboardHeight()))).build();
    AppbrandApplicationImpl.getInst().getWebViewManager().publish(this.mWebViewId, "onKeyboardShow", jSONObject.toString());
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
  
  public void setValue(String paramString) {
    setText(paramString);
  }
  
  public void smoothOffsetTopAndBottom() {
    final int lines;
    if (getText().length() == 0) {
      i = 0;
    } else {
      i = getLineCount();
    } 
    if (i != this.mLineCount) {
      syncHeightChangeToJs();
      postDelayed(new Runnable() {
            public void run() {
              if ((TextArea.this.isAutoSize() || (TextArea.this.getLayoutParams()).height == -2) && TextArea.this.mTextAreaHeight > TextArea.this.mModel.maxHeight && TextArea.this.mModel.maxHeight > 0) {
                TextArea textArea = TextArea.this;
                textArea.mTextAreaHeight = textArea.mModel.maxHeight;
              } else if (TextArea.this.isAutoSize() && TextArea.this.mTextAreaHeight < TextArea.this.mModel.minHeight) {
                TextArea textArea = TextArea.this;
                textArea.mTextAreaHeight = textArea.mModel.minHeight;
              } 
              int i = (TextArea.this.getCursorRect()).bottom - TextArea.this.getScrollY();
              int j = KeyboardHeightProvider.getKeyboardHeight();
              int[] arrayOfInt = new int[2];
              TextArea.this.getLocationOnScreen(arrayOfInt);
              int k = arrayOfInt[1];
              StringBuilder stringBuilder = new StringBuilder("当前TextArea底部坐标");
              int m = i + k;
              stringBuilder.append(m);
              AppBrandLogger.e("tma_Textarea", new Object[] { stringBuilder.toString(), "键盘顶部坐标", Integer.valueOf(this.this$0.mSrceenH - j) });
              if (TextArea.this.needToSmoothOffset() && lines > TextArea.this.mLineCount && m > TextArea.this.mSrceenH - j) {
                i = TextArea.this.mSrceenH - j - i - k;
                AppBrandLogger.e("tma_Textarea", new Object[] { "偏移量", Integer.valueOf(i) });
                TextArea.this.stack.push(Integer.valueOf(i));
                TextArea.this.mNativeNestWebView.smoothOffsetTopAndBottom(i, true, (View)TextArea.this);
                return;
              } 
              if (!TextArea.this.stack.empty() && lines < TextArea.this.mLineCount && m < TextArea.this.mSrceenH - j) {
                i = -((Integer)TextArea.this.stack.pop()).intValue();
                TextArea.this.mNativeNestWebView.smoothOffsetTopAndBottom(i, true, (View)TextArea.this);
              } 
            }
          }60L);
    } 
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
      AppBrandLogger.stacktrace(6, "tma_Textarea", jSONException.getStackTrace());
      return;
    } 
  }
  
  public void syncHeightChangeToJs() {
    try {
      int i = (getCursorRect()).bottom;
      if (isAutoSize())
        i = getLayout().getHeight(); 
      StringBuilder stringBuilder = new StringBuilder("文本内容高度: ");
      stringBuilder.append(i);
      String str = stringBuilder.toString();
      boolean bool = false;
      AppBrandLogger.e("tma_Textarea", new Object[] { str });
      JSONObject jSONObject = new JSONObject();
      jSONObject.put("height", NativeDimenUtil.convertPxToRx(i));
      jSONObject.put("inputId", this.mViewId);
      if (getText().length() == 0) {
        i = bool;
      } else {
        i = getLineCount();
      } 
      jSONObject.put("lineCount", i);
      AppbrandApplicationImpl.getInst().getWebViewManager().publish(this.mWebViewId, "onTextAreaHeightChange", jSONObject.toString());
      return;
    } catch (Exception exception) {
      AppBrandLogger.stacktrace(6, "tma_Textarea", exception.getStackTrace());
      return;
    } 
  }
  
  public void updateView(String paramString, k paramk) {
    boolean bool;
    if (updateCursor(paramString)) {
      if (!hasFocus())
        requestFocus(); 
      if (this.mOnGlobalLayoutListener != null) {
        getViewTreeObserver().removeOnGlobalLayoutListener(this.mOnGlobalLayoutListener);
        this.mOnGlobalLayoutListener = null;
      } 
      this.mOnGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
          public void onGlobalLayout() {
            if ((TextArea.this.isVisibleInScreen() & TextArea.this.mHasFocus) != 0 && !TextArea.this.mKeyBoardShow) {
              TextArea.this.postDelayed(new Runnable() {
                    public void run() {
                      if (TextArea.this.mRender != null)
                        TextArea.this.mRender.showKeyboard(TextArea.this.mViewId); 
                    }
                  },  100L);
              TextArea.this.getViewTreeObserver().removeOnGlobalLayoutListener(this);
              TextArea.this.mOnGlobalLayoutListener = null;
            } 
          }
        };
      getViewTreeObserver().addOnGlobalLayoutListener(this.mOnGlobalLayoutListener);
      if (this.selectionStart != -1) {
        if (!hasFocus())
          requestFocus(); 
        int i = this.selectionEnd;
        if (i == -1) {
          setSelection(this.selectionStart, getText().length());
        } else {
          if (i > getText().length())
            this.selectionEnd = getText().length(); 
          setSelection(this.selectionStart, this.selectionEnd);
        } 
      } 
      bool = this.cursor;
      if (bool != -1) {
        if (bool > getText().length())
          this.cursor = getText().length(); 
        setSelection(this.cursor);
      } 
      return;
    } 
    TextAreaMode textAreaMode = TextAreaMode.updateTextAreaMode(this.mModel, paramString);
    if (textAreaMode.hasPlaceHolder)
      setHint(textAreaMode.placeHolder); 
    if (textAreaMode.hasBackgroundColor)
      try {
        setBackgroundColor(Color.parseColor(transformColor(textAreaMode.backgroundColor)));
      } catch (IllegalArgumentException illegalArgumentException) {
        AppBrandLogger.e("tma_Textarea", new Object[] { "TextArea parse backgroundColor failed" });
      }  
    if (textAreaMode.hasPlaceHolderStyle) {
      if (!TextUtils.isEmpty(textAreaMode.placeHolderColor))
        try {
          setHintTextColor(UIUtils.parseColor(textAreaMode.placeHolderColor));
        } catch (IllegalColorException illegalColorException) {
          AppBrandLogger.eWithThrowable("tma_Textarea", "textarea placeHolderColor error", (Throwable)illegalColorException);
        }  
      if (textAreaMode.placeHolderFontSize > 0)
        setTextSize(textAreaMode.placeHolderFontSize); 
    } 
    if (textAreaMode.maxLength > 0)
      setFilters(new InputFilter[] { (InputFilter)new InputFilter.LengthFilter(textAreaMode.maxLength) }); 
    if (textAreaMode.hasValue && !TextUtils.equals(getValue(), textAreaMode.value) && textAreaMode.mValueUpdate) {
      this.mHasUpdated = true;
      setText(textAreaMode.value);
      setSelection(CharacterUtils.length(textAreaMode.value));
    } 
    AbsoluteLayout.LayoutParams layoutParams = (AbsoluteLayout.LayoutParams)getLayoutParams();
    if (textAreaMode.hasStyle) {
      bool = this.mParent.getCurScrollX();
      int i = this.mParent.getCurScrollY();
      if (textAreaMode.fixed) {
        bool = false;
        i = 0;
      } 
      layoutParams.x = textAreaMode.left - bool;
      layoutParams.y = textAreaMode.top - i;
      this.mParent.updateCurScrollXY(this.mViewId);
      layoutParams.width = textAreaMode.width;
      if (textAreaMode.height > 0 && !isAutoSize()) {
        bool = textAreaMode.height;
      } else {
        bool = true;
      } 
      layoutParams.height = bool;
      try {
        setTextColor(UIUtils.parseColor(textAreaMode.color));
      } catch (IllegalColorException illegalColorException) {
        AppBrandLogger.eWithThrowable("tma_Textarea", "textArea color error", (Throwable)illegalColorException);
      } 
      bool = true;
    } else {
      bool = false;
    } 
    if (textAreaMode.hasZIndex) {
      layoutParams.zIndex = textAreaMode.zIndex;
      bool = true;
    } 
    if (textAreaMode.hasFixed)
      layoutParams.isFixed = textAreaMode.fixed; 
    setEnabled(textAreaMode.disabled ^ true);
    if (textAreaMode.hidden) {
      setVisibility(8);
    } else {
      setVisibility(0);
    } 
    this.mAutoBlur = textAreaMode.autoBlur;
    if (bool)
      requestLayout(); 
    textAreaMode.mValueUpdate = false;
  }
  
  public static class TextAreaMode {
    boolean adjustPosition;
    
    boolean autoBlur = true;
    
    boolean autoSize;
    
    String backgroundColor;
    
    String color;
    
    boolean confirm;
    
    String dataObject;
    
    boolean disabled;
    
    boolean fixed;
    
    String fontFamily;
    
    int fontSize;
    
    String fontWeight;
    
    boolean hasAutoSize;
    
    boolean hasBackgroundColor;
    
    boolean hasFixed;
    
    boolean hasPlaceHolder;
    
    boolean hasPlaceHolderStyle;
    
    boolean hasStyle;
    
    boolean hasValue;
    
    boolean hasZIndex;
    
    int height;
    
    boolean hidden;
    
    int left;
    
    int lineSpace;
    
    public boolean mValueUpdate;
    
    int marginBottom;
    
    int maxHeight;
    
    int maxLength;
    
    int minHeight;
    
    int parentId;
    
    String placeHolder;
    
    String placeHolderColor;
    
    int placeHolderFontSize;
    
    String placeHolderFontWeight;
    
    String placeHolderontFaFmily;
    
    String textAlign;
    
    int top;
    
    String value;
    
    int width;
    
    int zIndex;
    
    static TextAreaMode parasTextAreaMode(String param1String) {
      TextAreaMode textAreaMode = new TextAreaMode();
      try {
        JSONObject jSONObject1 = new JSONObject(param1String);
        if (jSONObject1.has("confirm"))
          textAreaMode.confirm = jSONObject1.optBoolean("confirm"); 
        textAreaMode.parentId = jSONObject1.optInt("parentId");
        textAreaMode.dataObject = jSONObject1.optString("data");
        if (jSONObject1.has("fixed")) {
          textAreaMode.hasFixed = true;
          textAreaMode.fixed = jSONObject1.optBoolean("fixed");
        } 
        textAreaMode.adjustPosition = jSONObject1.optBoolean("adjustPosition", true);
        JSONObject jSONObject2 = jSONObject1.optJSONObject("style");
        if (jSONObject2 != null) {
          textAreaMode.hasStyle = true;
          textAreaMode.width = jSONObject2.optInt("width");
          if (textAreaMode.width > 0)
            textAreaMode.width = NativeDimenUtil.convertRxToPx(textAreaMode.width); 
          textAreaMode.left = jSONObject2.optInt("left");
          if (textAreaMode.left != 0)
            textAreaMode.left = NativeDimenUtil.convertRxToPx(textAreaMode.left); 
          textAreaMode.minHeight = jSONObject2.optInt("minHeight");
          if (textAreaMode.minHeight > 0)
            textAreaMode.minHeight = NativeDimenUtil.convertRxToPx(textAreaMode.minHeight); 
          textAreaMode.maxHeight = jSONObject2.optInt("maxHeight");
          if (textAreaMode.maxHeight > 0)
            textAreaMode.maxHeight = NativeDimenUtil.convertRxToPx(textAreaMode.maxHeight); 
          textAreaMode.top = jSONObject2.optInt("top");
          if (textAreaMode.top != 0)
            textAreaMode.top = NativeDimenUtil.convertRxToPx(textAreaMode.top); 
          textAreaMode.fontWeight = jSONObject2.optString("fontWeight");
          textAreaMode.fontFamily = jSONObject2.optString("fontFamily");
          textAreaMode.fontSize = jSONObject2.optInt("fontSize");
          textAreaMode.lineSpace = jSONObject2.optInt("lineSpace");
          if (textAreaMode.lineSpace > 0)
            textAreaMode.lineSpace = NativeDimenUtil.convertRxToPx(textAreaMode.lineSpace); 
          textAreaMode.textAlign = jSONObject2.optString("textAlign");
          textAreaMode.color = UIUtils.rgbaToFullARGBStr(jSONObject2.optString("color"), "#000000");
          textAreaMode.backgroundColor = UIUtils.rgbaToFullARGBStr(jSONObject2.optString("backgroundColor"), "#ffffff");
          textAreaMode.marginBottom = jSONObject2.optInt("marginBottom");
          if (textAreaMode.marginBottom > 0)
            textAreaMode.marginBottom = NativeDimenUtil.convertRxToPx(textAreaMode.marginBottom); 
          textAreaMode.height = jSONObject2.optInt("height");
          if (textAreaMode.height > 0)
            textAreaMode.height = NativeDimenUtil.convertRxToPx(textAreaMode.height); 
        } 
        textAreaMode.maxLength = jSONObject1.optInt("maxLength");
        if (jSONObject1.has("placeholder") && !TextUtils.isEmpty(jSONObject1.optString("placeholder"))) {
          textAreaMode.hasPlaceHolder = true;
          textAreaMode.placeHolder = jSONObject1.optString("placeholder");
        } 
        jSONObject2 = jSONObject1.optJSONObject("placeholderStyle");
        if (jSONObject2 != null) {
          textAreaMode.hasPlaceHolderStyle = true;
          textAreaMode.placeHolderColor = UIUtils.rgbaToFullARGBStr(jSONObject2.optString("color"), "#888888");
          textAreaMode.placeHolderFontSize = jSONObject2.optInt("fontSize");
          textAreaMode.placeHolderFontWeight = jSONObject2.optString("fontWeight");
          textAreaMode.placeHolderontFaFmily = jSONObject2.optString("fontFamily");
        } 
        textAreaMode.disabled = jSONObject1.optBoolean("disabled");
        textAreaMode.hidden = jSONObject1.optBoolean("hidden");
        textAreaMode.autoSize = jSONObject1.optBoolean("autoSize");
        if (jSONObject1.has("value") && !TextUtils.isEmpty(jSONObject1.optString("value"))) {
          textAreaMode.hasValue = true;
          textAreaMode.value = jSONObject1.optString("value");
        } 
        if (jSONObject1.has("autoBlur"))
          textAreaMode.autoBlur = jSONObject1.getBoolean("autoBlur"); 
        if (jSONObject1.has("zIndex")) {
          textAreaMode.hasZIndex = true;
          textAreaMode.zIndex = jSONObject1.optInt("zIndex");
        } 
        return textAreaMode;
      } catch (JSONException jSONException) {
        return textAreaMode;
      } 
    }
    
    static TextAreaMode updateTextAreaMode(TextAreaMode param1TextAreaMode, String param1String) {
      try {
        JSONObject jSONObject1 = new JSONObject(param1String);
        if (jSONObject1.has("confirm"))
          param1TextAreaMode.confirm = jSONObject1.optBoolean("confirm"); 
        if (jSONObject1.has("parentId"))
          param1TextAreaMode.parentId = jSONObject1.optInt("parentId"); 
        if (jSONObject1.has("data"))
          param1TextAreaMode.dataObject = jSONObject1.optString("data"); 
        if (jSONObject1.has("fixed")) {
          param1TextAreaMode.hasFixed = true;
          param1TextAreaMode.fixed = jSONObject1.optBoolean("fixed");
        } else {
          param1TextAreaMode.hasFixed = false;
        } 
        JSONObject jSONObject2 = jSONObject1.optJSONObject("style");
        if (jSONObject2 != null) {
          param1TextAreaMode.hasStyle = true;
          param1TextAreaMode.width = jSONObject2.optInt("width");
          if (param1TextAreaMode.width > 0)
            param1TextAreaMode.width = NativeDimenUtil.convertRxToPx(param1TextAreaMode.width); 
          if (jSONObject2.has("left"))
            param1TextAreaMode.left = NativeDimenUtil.convertRxToPx(jSONObject2.optInt("left")); 
          param1TextAreaMode.minHeight = jSONObject2.optInt("minHeight");
          if (param1TextAreaMode.minHeight > 0)
            param1TextAreaMode.minHeight = NativeDimenUtil.convertRxToPx(param1TextAreaMode.minHeight); 
          param1TextAreaMode.maxHeight = jSONObject2.optInt("maxHeight");
          if (param1TextAreaMode.maxHeight > 0)
            param1TextAreaMode.maxHeight = NativeDimenUtil.convertRxToPx(param1TextAreaMode.maxHeight); 
          if (jSONObject2.has("top"))
            param1TextAreaMode.top = NativeDimenUtil.convertRxToPx(jSONObject2.optInt("top")); 
          param1TextAreaMode.fontWeight = jSONObject2.optString("fontWeight");
          param1TextAreaMode.fontFamily = jSONObject2.optString("fontFamily");
          param1TextAreaMode.fontSize = jSONObject2.optInt("fontSize");
          param1TextAreaMode.lineSpace = jSONObject2.optInt("lineSpace");
          if (param1TextAreaMode.lineSpace > 0)
            param1TextAreaMode.lineSpace = NativeDimenUtil.convertRxToPx(param1TextAreaMode.lineSpace); 
          param1TextAreaMode.textAlign = jSONObject2.optString("textAlign");
          param1TextAreaMode.color = jSONObject2.optString("color");
          if (jSONObject2.has("backgroundColor")) {
            param1TextAreaMode.hasBackgroundColor = true;
            param1TextAreaMode.backgroundColor = jSONObject2.optString("backgroundColor");
          } 
          param1TextAreaMode.marginBottom = jSONObject2.optInt("marginBottom");
          if (param1TextAreaMode.marginBottom > 0)
            param1TextAreaMode.marginBottom = NativeDimenUtil.convertRxToPx(param1TextAreaMode.marginBottom); 
          param1TextAreaMode.height = jSONObject2.optInt("height");
          if (param1TextAreaMode.height > 0)
            param1TextAreaMode.height = NativeDimenUtil.convertRxToPx(param1TextAreaMode.height); 
        } else {
          param1TextAreaMode.hasStyle = false;
        } 
        if (jSONObject1.has("maxLength"))
          param1TextAreaMode.maxLength = jSONObject1.optInt("maxLength"); 
        if (jSONObject1.has("placeholder")) {
          param1TextAreaMode.hasPlaceHolder = true;
          param1TextAreaMode.placeHolder = jSONObject1.optString("placeholder");
        } 
        jSONObject2 = jSONObject1.optJSONObject("placeholderStyle");
        if (jSONObject2 != null) {
          param1TextAreaMode.hasPlaceHolderStyle = true;
          param1TextAreaMode.placeHolderColor = jSONObject2.optString("color");
          param1TextAreaMode.placeHolderFontSize = jSONObject2.optInt("fontSize");
          param1TextAreaMode.placeHolderFontWeight = jSONObject2.optString("fontWeight");
          param1TextAreaMode.placeHolderontFaFmily = jSONObject2.optString("fontFamily");
        } 
        if (jSONObject1.has("disabled"))
          param1TextAreaMode.disabled = jSONObject1.optBoolean("disabled"); 
        if (jSONObject1.has("hidden"))
          param1TextAreaMode.hidden = jSONObject1.optBoolean("hidden"); 
        if (jSONObject1.has("autoSize"))
          param1TextAreaMode.autoSize = jSONObject1.optBoolean("autoSize"); 
        if (jSONObject1.has("value")) {
          param1TextAreaMode.mValueUpdate = true;
          param1TextAreaMode.hasValue = true;
          param1TextAreaMode.value = jSONObject1.optString("value");
        } 
        if (jSONObject1.has("autoBlur"))
          param1TextAreaMode.autoBlur = jSONObject1.getBoolean("autoBlur"); 
        if (jSONObject1.has("zIndex")) {
          param1TextAreaMode.hasZIndex = true;
          param1TextAreaMode.zIndex = jSONObject1.optInt("zIndex");
          return param1TextAreaMode;
        } 
        param1TextAreaMode.hasZIndex = false;
        return param1TextAreaMode;
      } catch (JSONException jSONException) {
        return param1TextAreaMode;
      } 
    }
    
    public String toString() {
      StringBuilder stringBuilder = new StringBuilder("TextAreaMode{parentId=");
      stringBuilder.append(this.parentId);
      stringBuilder.append(", width=");
      stringBuilder.append(this.width);
      stringBuilder.append(", left=");
      stringBuilder.append(this.left);
      stringBuilder.append(", minHeight=");
      stringBuilder.append(this.minHeight);
      stringBuilder.append(", maxHeight=");
      stringBuilder.append(this.maxHeight);
      stringBuilder.append(", top=");
      stringBuilder.append(this.top);
      stringBuilder.append(", hasStyle=");
      stringBuilder.append(this.hasStyle);
      stringBuilder.append(", fontWeight='");
      stringBuilder.append(this.fontWeight);
      stringBuilder.append('\'');
      stringBuilder.append(", fontFamily='");
      stringBuilder.append(this.fontFamily);
      stringBuilder.append('\'');
      stringBuilder.append(", fontSize=");
      stringBuilder.append(this.fontSize);
      stringBuilder.append(", lineSpace=");
      stringBuilder.append(this.lineSpace);
      stringBuilder.append(", textAlign='");
      stringBuilder.append(this.textAlign);
      stringBuilder.append('\'');
      stringBuilder.append(", color='");
      stringBuilder.append(this.color);
      stringBuilder.append('\'');
      stringBuilder.append(", marginBottom=");
      stringBuilder.append(this.marginBottom);
      stringBuilder.append(", height=");
      stringBuilder.append(this.height);
      stringBuilder.append(", maxLength=");
      stringBuilder.append(this.maxLength);
      stringBuilder.append(", hasPlaceHolder=");
      stringBuilder.append(this.hasPlaceHolder);
      stringBuilder.append(", placeHolder='");
      stringBuilder.append(this.placeHolder);
      stringBuilder.append('\'');
      stringBuilder.append(", disabled=");
      stringBuilder.append(this.disabled);
      stringBuilder.append(", hidden=");
      stringBuilder.append(this.hidden);
      stringBuilder.append(", autoSize=");
      stringBuilder.append(this.autoSize);
      stringBuilder.append(", confirm=");
      stringBuilder.append(this.confirm);
      stringBuilder.append(", fixed=");
      stringBuilder.append(this.fixed);
      stringBuilder.append(", value='");
      stringBuilder.append(this.value);
      stringBuilder.append('\'');
      stringBuilder.append(", hasValue=");
      stringBuilder.append(this.hasValue);
      stringBuilder.append(", dataObject='");
      stringBuilder.append(this.dataObject);
      stringBuilder.append('\'');
      stringBuilder.append(", hasPlaceHolderStyle=");
      stringBuilder.append(this.hasPlaceHolderStyle);
      stringBuilder.append(", placeHolderFontSize=");
      stringBuilder.append(this.placeHolderFontSize);
      stringBuilder.append(", placeHolderFontWeight='");
      stringBuilder.append(this.placeHolderFontWeight);
      stringBuilder.append('\'');
      stringBuilder.append(", placeHolderontFaFmily='");
      stringBuilder.append(this.placeHolderontFaFmily);
      stringBuilder.append('\'');
      stringBuilder.append(", placeHolderColor='");
      stringBuilder.append(this.placeHolderColor);
      stringBuilder.append('\'');
      stringBuilder.append('}');
      return stringBuilder.toString();
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\component\nativeview\TextArea.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */