package com.facebook.react.views.textinput;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.KeyListener;
import android.text.method.QwertyKeyListener;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import com.facebook.i.a.a;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.uimanager.PixelUtil;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.views.text.CustomStyleSpan;
import com.facebook.react.views.text.ReactTagSpan;
import com.facebook.react.views.text.ReactTextUpdate;
import com.facebook.react.views.text.TextInlineImageSpan;
import com.facebook.react.views.view.ReactViewBackgroundManager;
import java.util.ArrayList;
import java.util.Iterator;

public class ReactEditText extends EditText {
  public static final KeyListener sKeyListener = (KeyListener)QwertyKeyListener.getInstanceForFullKeyboard();
  
  private Boolean mBlurOnSubmit;
  
  private boolean mContainsImages;
  
  private ContentSizeWatcher mContentSizeWatcher;
  
  private int mDefaultGravityHorizontal;
  
  private int mDefaultGravityVertical;
  
  private boolean mDetectScrollMovement;
  
  private boolean mDisableFullscreen;
  
  private final InputMethodManager mInputMethodManager;
  
  private boolean mIsJSSettingFocus;
  
  public boolean mIsSettingTextFromJS;
  
  private final InternalKeyListener mKeyListener;
  
  private float mLetterSpacingPt;
  
  public ArrayList<TextWatcher> mListeners;
  
  private int mMostRecentEventCount;
  
  private int mNativeEventCount;
  
  private ReactViewBackgroundManager mReactBackgroundManager;
  
  private String mReturnKeyType;
  
  private ScrollWatcher mScrollWatcher;
  
  private SelectionWatcher mSelectionWatcher;
  
  private int mStagedInputType;
  
  private TextWatcherDelegator mTextWatcherDelegator;
  
  public ReactEditText(Context paramContext) {
    super(paramContext);
    setFocusableInTouchMode(false);
    this.mReactBackgroundManager = new ReactViewBackgroundManager((View)this);
    this.mInputMethodManager = (InputMethodManager)a.b(getContext().getSystemService("input_method"));
    this.mDefaultGravityHorizontal = getGravity() & 0x800007;
    this.mDefaultGravityVertical = getGravity() & 0x70;
    this.mNativeEventCount = 0;
    this.mMostRecentEventCount = 0;
    this.mIsSettingTextFromJS = false;
    this.mIsJSSettingFocus = false;
    this.mBlurOnSubmit = null;
    this.mDisableFullscreen = false;
    this.mListeners = null;
    this.mTextWatcherDelegator = null;
    this.mStagedInputType = getInputType();
    this.mKeyListener = new InternalKeyListener();
    this.mScrollWatcher = null;
  }
  
  private TextWatcherDelegator getTextWatcherDelegator() {
    if (this.mTextWatcherDelegator == null)
      this.mTextWatcherDelegator = new TextWatcherDelegator(); 
    return this.mTextWatcherDelegator;
  }
  
  private void hideSoftKeyboard() {
    this.mInputMethodManager.hideSoftInputFromWindow(getWindowToken(), 0);
  }
  
  private boolean isMultiline() {
    return ((getInputType() & 0x20000) != 0);
  }
  
  private boolean isSecureText() {
    return ((getInputType() & 0x90) != 0);
  }
  
  private void manageSpans(SpannableStringBuilder paramSpannableStringBuilder) {
    Editable editable = getText();
    int j = length();
    int i = 0;
    Object[] arrayOfObject = editable.getSpans(0, j, Object.class);
    while (i < arrayOfObject.length) {
      if (ForegroundColorSpan.class.isInstance(arrayOfObject[i]) || BackgroundColorSpan.class.isInstance(arrayOfObject[i]) || AbsoluteSizeSpan.class.isInstance(arrayOfObject[i]) || CustomStyleSpan.class.isInstance(arrayOfObject[i]) || ReactTagSpan.class.isInstance(arrayOfObject[i]))
        getText().removeSpan(arrayOfObject[i]); 
      if ((getText().getSpanFlags(arrayOfObject[i]) & 0x21) == 33) {
        Object object = arrayOfObject[i];
        j = getText().getSpanStart(arrayOfObject[i]);
        int k = getText().getSpanEnd(arrayOfObject[i]);
        int m = getText().getSpanFlags(arrayOfObject[i]);
        getText().removeSpan(arrayOfObject[i]);
        if (sameTextForSpan(getText(), paramSpannableStringBuilder, j, k))
          paramSpannableStringBuilder.setSpan(object, j, k, m); 
      } 
      i++;
    } 
  }
  
  private static boolean sameTextForSpan(Editable paramEditable, SpannableStringBuilder paramSpannableStringBuilder, int paramInt1, int paramInt2) {
    if (paramInt1 <= paramSpannableStringBuilder.length()) {
      if (paramInt2 > paramSpannableStringBuilder.length())
        return false; 
      while (paramInt1 < paramInt2) {
        if (paramEditable.charAt(paramInt1) != paramSpannableStringBuilder.charAt(paramInt1))
          return false; 
        paramInt1++;
      } 
      return true;
    } 
    return false;
  }
  
  private void setIntrinsicContentSize() {
    UIManagerModule uIManagerModule = (UIManagerModule)((ReactContext)getContext()).getNativeModule(UIManagerModule.class);
    ReactTextInputLocalData reactTextInputLocalData = new ReactTextInputLocalData(this);
    uIManagerModule.setViewLocalData(getId(), reactTextInputLocalData);
  }
  
  private boolean showSoftKeyboard() {
    return this.mInputMethodManager.showSoftInput((View)this, 0);
  }
  
  private void updateImeOptions() {
    String str = this.mReturnKeyType;
    byte b = 4;
    if (str != null) {
      byte b1;
      switch (str.hashCode()) {
        default:
          b1 = -1;
          break;
        case 3526536:
          if (str.equals("send")) {
            b1 = 5;
            break;
          } 
        case 3387192:
          if (str.equals("none")) {
            b1 = 2;
            break;
          } 
        case 3377907:
          if (str.equals("next")) {
            b1 = 1;
            break;
          } 
        case 3089282:
          if (str.equals("done")) {
            b1 = 6;
            break;
          } 
        case 3304:
          if (str.equals("go")) {
            b1 = 0;
            break;
          } 
        case -906336856:
          if (str.equals("search")) {
            b1 = 4;
            break;
          } 
        case -1273775369:
          if (str.equals("previous")) {
            b1 = 3;
            break;
          } 
      } 
      switch (b1) {
        default:
          b = 6;
          break;
        case 4:
          b = 3;
          break;
        case 3:
          b = 7;
          break;
        case 2:
          b = 1;
          break;
        case 1:
          b = 5;
          break;
        case 0:
          b = 2;
          break;
        case 6:
          b = 6;
          break;
        case 5:
          break;
      } 
      if (this.mDisableFullscreen) {
        setImeOptions(0x2000000 | b);
        return;
      } 
      setImeOptions(b);
      return;
    } 
  }
  
  public void addTextChangedListener(TextWatcher paramTextWatcher) {
    if (this.mListeners == null) {
      this.mListeners = new ArrayList<TextWatcher>();
      super.addTextChangedListener(getTextWatcherDelegator());
    } 
    this.mListeners.add(paramTextWatcher);
  }
  
  public void clearFocus() {
    setFocusableInTouchMode(false);
    super.clearFocus();
    hideSoftKeyboard();
  }
  
  void clearFocusFromJS() {
    clearFocus();
  }
  
  void commitStagedInputType() {
    if (getInputType() != this.mStagedInputType) {
      int i = getSelectionStart();
      int j = getSelectionEnd();
      setInputType(this.mStagedInputType);
      setSelection(i, j);
    } 
  }
  
  public boolean getBlurOnSubmit() {
    Boolean bool = this.mBlurOnSubmit;
    return (bool == null) ? (!isMultiline()) : bool.booleanValue();
  }
  
  public boolean getDisableFullscreenUI() {
    return this.mDisableFullscreen;
  }
  
  public String getReturnKeyType() {
    return this.mReturnKeyType;
  }
  
  int getStagedInputType() {
    return this.mStagedInputType;
  }
  
  public int incrementAndGetEventCounter() {
    int i = this.mNativeEventCount + 1;
    this.mNativeEventCount = i;
    return i;
  }
  
  public void invalidateDrawable(Drawable paramDrawable) {
    if (this.mContainsImages) {
      Editable editable = getText();
      int j = editable.length();
      int i = 0;
      TextInlineImageSpan[] arrayOfTextInlineImageSpan = (TextInlineImageSpan[])editable.getSpans(0, j, TextInlineImageSpan.class);
      j = arrayOfTextInlineImageSpan.length;
      while (i < j) {
        if (arrayOfTextInlineImageSpan[i].getDrawable() == paramDrawable)
          invalidate(); 
        i++;
      } 
    } 
    super.invalidateDrawable(paramDrawable);
  }
  
  public boolean isLayoutRequested() {
    return false;
  }
  
  public void maybeSetText(ReactTextUpdate paramReactTextUpdate) {
    if (isSecureText() && TextUtils.equals((CharSequence)getText(), (CharSequence)paramReactTextUpdate.getText()))
      return; 
    this.mMostRecentEventCount = paramReactTextUpdate.getJsEventCounter();
    if (this.mMostRecentEventCount < this.mNativeEventCount)
      return; 
    SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder((CharSequence)paramReactTextUpdate.getText());
    manageSpans(spannableStringBuilder);
    this.mContainsImages = paramReactTextUpdate.containsImages();
    this.mIsSettingTextFromJS = true;
    getText().replace(0, length(), (CharSequence)spannableStringBuilder);
    this.mIsSettingTextFromJS = false;
    if (Build.VERSION.SDK_INT >= 23 && getBreakStrategy() != paramReactTextUpdate.getTextBreakStrategy())
      setBreakStrategy(paramReactTextUpdate.getTextBreakStrategy()); 
  }
  
  public void onAttachedToWindow() {
    super.onAttachedToWindow();
    if (this.mContainsImages) {
      Editable editable = getText();
      int j = editable.length();
      int i = 0;
      TextInlineImageSpan[] arrayOfTextInlineImageSpan = (TextInlineImageSpan[])editable.getSpans(0, j, TextInlineImageSpan.class);
      j = arrayOfTextInlineImageSpan.length;
      while (i < j) {
        arrayOfTextInlineImageSpan[i].onAttachedToWindow();
        i++;
      } 
    } 
  }
  
  public void onContentSizeChange() {
    ContentSizeWatcher contentSizeWatcher = this.mContentSizeWatcher;
    if (contentSizeWatcher != null)
      contentSizeWatcher.onLayout(); 
    setIntrinsicContentSize();
  }
  
  public InputConnection onCreateInputConnection(EditorInfo paramEditorInfo) {
    ReactEditTextInputConnectionWrapper reactEditTextInputConnectionWrapper;
    ReactContext reactContext = (ReactContext)getContext();
    InputConnection inputConnection2 = super.onCreateInputConnection(paramEditorInfo);
    InputConnection inputConnection1 = inputConnection2;
    if (inputConnection2 != null)
      reactEditTextInputConnectionWrapper = new ReactEditTextInputConnectionWrapper(inputConnection2, reactContext, this); 
    if (isMultiline() && getBlurOnSubmit())
      paramEditorInfo.imeOptions &= 0xBFFFFFFF; 
    return (InputConnection)reactEditTextInputConnectionWrapper;
  }
  
  public void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    if (this.mContainsImages) {
      Editable editable = getText();
      int j = editable.length();
      int i = 0;
      TextInlineImageSpan[] arrayOfTextInlineImageSpan = (TextInlineImageSpan[])editable.getSpans(0, j, TextInlineImageSpan.class);
      j = arrayOfTextInlineImageSpan.length;
      while (i < j) {
        arrayOfTextInlineImageSpan[i].onDetachedFromWindow();
        i++;
      } 
    } 
  }
  
  public void onFinishTemporaryDetach() {
    super.onFinishTemporaryDetach();
    if (this.mContainsImages) {
      Editable editable = getText();
      int j = editable.length();
      int i = 0;
      TextInlineImageSpan[] arrayOfTextInlineImageSpan = (TextInlineImageSpan[])editable.getSpans(0, j, TextInlineImageSpan.class);
      j = arrayOfTextInlineImageSpan.length;
      while (i < j) {
        arrayOfTextInlineImageSpan[i].onFinishTemporaryDetach();
        i++;
      } 
    } 
  }
  
  protected void onFocusChanged(boolean paramBoolean, int paramInt, Rect paramRect) {
    super.onFocusChanged(paramBoolean, paramInt, paramRect);
    if (paramBoolean) {
      SelectionWatcher selectionWatcher = this.mSelectionWatcher;
      if (selectionWatcher != null)
        selectionWatcher.onSelectionChanged(getSelectionStart(), getSelectionEnd()); 
    } 
  }
  
  public boolean onKeyUp(int paramInt, KeyEvent paramKeyEvent) {
    if (paramInt == 66 && !isMultiline()) {
      hideSoftKeyboard();
      return true;
    } 
    return super.onKeyUp(paramInt, paramKeyEvent);
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    onContentSizeChange();
  }
  
  protected void onScrollChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    super.onScrollChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    ScrollWatcher scrollWatcher = this.mScrollWatcher;
    if (scrollWatcher != null)
      scrollWatcher.onScrollChanged(paramInt1, paramInt2, paramInt3, paramInt4); 
  }
  
  protected void onSelectionChanged(int paramInt1, int paramInt2) {
    super.onSelectionChanged(paramInt1, paramInt2);
    if (this.mSelectionWatcher != null && hasFocus())
      this.mSelectionWatcher.onSelectionChanged(paramInt1, paramInt2); 
  }
  
  public void onStartTemporaryDetach() {
    super.onStartTemporaryDetach();
    if (this.mContainsImages) {
      Editable editable = getText();
      int j = editable.length();
      int i = 0;
      TextInlineImageSpan[] arrayOfTextInlineImageSpan = (TextInlineImageSpan[])editable.getSpans(0, j, TextInlineImageSpan.class);
      j = arrayOfTextInlineImageSpan.length;
      while (i < j) {
        arrayOfTextInlineImageSpan[i].onStartTemporaryDetach();
        i++;
      } 
    } 
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent) {
    int i = paramMotionEvent.getAction();
    if (i != 0) {
      if (i == 2 && this.mDetectScrollMovement) {
        if (!canScrollVertically(-1) && !canScrollVertically(1) && !canScrollHorizontally(-1) && !canScrollHorizontally(1))
          getParent().requestDisallowInterceptTouchEvent(false); 
        this.mDetectScrollMovement = false;
      } 
    } else {
      this.mDetectScrollMovement = true;
      getParent().requestDisallowInterceptTouchEvent(true);
    } 
    return super.onTouchEvent(paramMotionEvent);
  }
  
  public void removeTextChangedListener(TextWatcher paramTextWatcher) {
    ArrayList<TextWatcher> arrayList = this.mListeners;
    if (arrayList != null) {
      arrayList.remove(paramTextWatcher);
      if (this.mListeners.isEmpty()) {
        this.mListeners = null;
        super.removeTextChangedListener(getTextWatcherDelegator());
      } 
    } 
  }
  
  public boolean requestFocus(int paramInt, Rect paramRect) {
    if (isFocused())
      return true; 
    if (!this.mIsJSSettingFocus)
      return false; 
    setFocusableInTouchMode(true);
    boolean bool = super.requestFocus(paramInt, paramRect);
    showSoftKeyboard();
    return bool;
  }
  
  public void requestFocusFromJS() {
    this.mIsJSSettingFocus = true;
    requestFocus();
    this.mIsJSSettingFocus = false;
  }
  
  public void setBackgroundColor(int paramInt) {
    this.mReactBackgroundManager.setBackgroundColor(paramInt);
  }
  
  public void setBlurOnSubmit(Boolean paramBoolean) {
    this.mBlurOnSubmit = paramBoolean;
  }
  
  public void setBorderColor(int paramInt, float paramFloat1, float paramFloat2) {
    this.mReactBackgroundManager.setBorderColor(paramInt, paramFloat1, paramFloat2);
  }
  
  public void setBorderRadius(float paramFloat) {
    this.mReactBackgroundManager.setBorderRadius(paramFloat);
  }
  
  public void setBorderRadius(float paramFloat, int paramInt) {
    this.mReactBackgroundManager.setBorderRadius(paramFloat, paramInt);
  }
  
  public void setBorderStyle(String paramString) {
    this.mReactBackgroundManager.setBorderStyle(paramString);
  }
  
  public void setBorderWidth(int paramInt, float paramFloat) {
    this.mReactBackgroundManager.setBorderWidth(paramInt, paramFloat);
  }
  
  public void setContentSizeWatcher(ContentSizeWatcher paramContentSizeWatcher) {
    this.mContentSizeWatcher = paramContentSizeWatcher;
  }
  
  public void setDisableFullscreenUI(boolean paramBoolean) {
    this.mDisableFullscreen = paramBoolean;
    updateImeOptions();
  }
  
  void setGravityHorizontal(int paramInt) {
    int i = paramInt;
    if (paramInt == 0)
      i = this.mDefaultGravityHorizontal; 
    setGravity(i | getGravity() & 0xFFFFFFF8 & 0xFF7FFFF8);
  }
  
  void setGravityVertical(int paramInt) {
    int i = paramInt;
    if (paramInt == 0)
      i = this.mDefaultGravityVertical; 
    setGravity(i | getGravity() & 0xFFFFFF8F);
  }
  
  public void setInputType(int paramInt) {
    Typeface typeface = getTypeface();
    super.setInputType(paramInt);
    this.mStagedInputType = paramInt;
    setTypeface(typeface);
    this.mKeyListener.setInputType(paramInt);
    setKeyListener(this.mKeyListener);
  }
  
  public void setLetterSpacingPt(float paramFloat) {
    this.mLetterSpacingPt = paramFloat;
    updateLetterSpacing();
  }
  
  public void setReturnKeyType(String paramString) {
    this.mReturnKeyType = paramString;
    updateImeOptions();
  }
  
  public void setScrollWatcher(ScrollWatcher paramScrollWatcher) {
    this.mScrollWatcher = paramScrollWatcher;
  }
  
  public void setSelection(int paramInt1, int paramInt2) {
    if (this.mMostRecentEventCount < this.mNativeEventCount)
      return; 
    super.setSelection(paramInt1, paramInt2);
  }
  
  public void setSelectionWatcher(SelectionWatcher paramSelectionWatcher) {
    this.mSelectionWatcher = paramSelectionWatcher;
  }
  
  void setStagedInputType(int paramInt) {
    this.mStagedInputType = paramInt;
  }
  
  public void setTextSize(float paramFloat) {
    super.setTextSize(paramFloat);
    updateLetterSpacing();
  }
  
  public void setTextSize(int paramInt, float paramFloat) {
    super.setTextSize(paramInt, paramFloat);
    updateLetterSpacing();
  }
  
  protected void updateLetterSpacing() {
    if (Build.VERSION.SDK_INT >= 21)
      setLetterSpacing(PixelUtil.toPixelFromSP(this.mLetterSpacingPt) / getTextSize()); 
  }
  
  protected boolean verifyDrawable(Drawable paramDrawable) {
    if (this.mContainsImages) {
      Editable editable = getText();
      int j = editable.length();
      int i = 0;
      TextInlineImageSpan[] arrayOfTextInlineImageSpan = (TextInlineImageSpan[])editable.getSpans(0, j, TextInlineImageSpan.class);
      j = arrayOfTextInlineImageSpan.length;
      while (i < j) {
        if (arrayOfTextInlineImageSpan[i].getDrawable() == paramDrawable)
          return true; 
        i++;
      } 
    } 
    return super.verifyDrawable(paramDrawable);
  }
  
  static class InternalKeyListener implements KeyListener {
    private int mInputType;
    
    public void clearMetaKeyState(View param1View, Editable param1Editable, int param1Int) {
      ReactEditText.sKeyListener.clearMetaKeyState(param1View, param1Editable, param1Int);
    }
    
    public int getInputType() {
      return this.mInputType;
    }
    
    public boolean onKeyDown(View param1View, Editable param1Editable, int param1Int, KeyEvent param1KeyEvent) {
      return ReactEditText.sKeyListener.onKeyDown(param1View, param1Editable, param1Int, param1KeyEvent);
    }
    
    public boolean onKeyOther(View param1View, Editable param1Editable, KeyEvent param1KeyEvent) {
      return ReactEditText.sKeyListener.onKeyOther(param1View, param1Editable, param1KeyEvent);
    }
    
    public boolean onKeyUp(View param1View, Editable param1Editable, int param1Int, KeyEvent param1KeyEvent) {
      return ReactEditText.sKeyListener.onKeyUp(param1View, param1Editable, param1Int, param1KeyEvent);
    }
    
    public void setInputType(int param1Int) {
      this.mInputType = param1Int;
    }
  }
  
  class TextWatcherDelegator implements TextWatcher {
    private TextWatcherDelegator() {}
    
    public void afterTextChanged(Editable param1Editable) {
      if (!ReactEditText.this.mIsSettingTextFromJS && ReactEditText.this.mListeners != null) {
        Iterator<TextWatcher> iterator = ReactEditText.this.mListeners.iterator();
        while (iterator.hasNext())
          ((TextWatcher)iterator.next()).afterTextChanged(param1Editable); 
      } 
    }
    
    public void beforeTextChanged(CharSequence param1CharSequence, int param1Int1, int param1Int2, int param1Int3) {
      if (!ReactEditText.this.mIsSettingTextFromJS && ReactEditText.this.mListeners != null) {
        Iterator<TextWatcher> iterator = ReactEditText.this.mListeners.iterator();
        while (iterator.hasNext())
          ((TextWatcher)iterator.next()).beforeTextChanged(param1CharSequence, param1Int1, param1Int2, param1Int3); 
      } 
    }
    
    public void onTextChanged(CharSequence param1CharSequence, int param1Int1, int param1Int2, int param1Int3) {
      if (!ReactEditText.this.mIsSettingTextFromJS && ReactEditText.this.mListeners != null) {
        Iterator<TextWatcher> iterator = ReactEditText.this.mListeners.iterator();
        while (iterator.hasNext())
          ((TextWatcher)iterator.next()).onTextChanged(param1CharSequence, param1Int1, param1Int2, param1Int3); 
      } 
      ReactEditText.this.onContentSizeChange();
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\textinput\ReactEditText.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */