package com.facebook.react.views.textinput;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.content.c;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import com.facebook.i.a.a;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.uimanager.BaseViewManager;
import com.facebook.react.uimanager.LayoutShadowNode;
import com.facebook.react.uimanager.PixelUtil;
import com.facebook.react.uimanager.ReactShadowNode;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.annotations.ReactPropGroup;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.EventDispatcher;
import com.facebook.react.views.imagehelper.ResourceDrawableIdHelper;
import com.facebook.react.views.scroll.ScrollEvent;
import com.facebook.react.views.scroll.ScrollEventType;
import com.facebook.react.views.text.DefaultStyleValuesUtil;
import com.facebook.react.views.text.ReactFontManager;
import com.facebook.react.views.text.ReactTextUpdate;
import com.facebook.react.views.text.TextInlineImageSpan;
import com.facebook.yoga.a;
import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.Map;

@ReactModule(name = "AndroidTextInput")
public class ReactTextInputManager extends BaseViewManager<ReactEditText, LayoutShadowNode> {
  private static final InputFilter[] EMPTY_FILTERS;
  
  private static final int[] SPACING_TYPES = new int[] { 8, 0, 2, 1, 3 };
  
  static {
    EMPTY_FILTERS = new InputFilter[0];
  }
  
  private static void checkPasswordType(ReactEditText paramReactEditText) {
    if ((paramReactEditText.getStagedInputType() & 0x3002) != 0 && (paramReactEditText.getStagedInputType() & 0x80) != 0)
      updateStagedInputTypeFlag(paramReactEditText, 128, 16); 
  }
  
  private static int parseNumericFontWeight(String paramString) {
    return (paramString.length() == 3 && paramString.endsWith("00") && paramString.charAt(0) <= '9' && paramString.charAt(0) >= '1') ? ((paramString.charAt(0) - 48) * 100) : -1;
  }
  
  private void setCursorColor(ReactEditText paramReactEditText, Integer paramInteger) {
    try {
      Field field2 = TextView.class.getDeclaredField("mCursorDrawableRes");
      field2.setAccessible(true);
      int i = field2.getInt(paramReactEditText);
      if (i == 0)
        return; 
      Drawable drawable = c.a(paramReactEditText.getContext(), i);
      if (paramInteger != null)
        drawable.setColorFilter(paramInteger.intValue(), PorterDuff.Mode.SRC_IN); 
      Field field1 = TextView.class.getDeclaredField("mEditor");
      field1.setAccessible(true);
      Object object = field1.get(paramReactEditText);
      field1 = object.getClass().getDeclaredField("mCursorDrawable");
      field1.setAccessible(true);
      field1.set(object, new Drawable[] { drawable, drawable });
      return;
    } catch (NoSuchFieldException|IllegalAccessException noSuchFieldException) {
      return;
    } 
  }
  
  private static void updateStagedInputTypeFlag(ReactEditText paramReactEditText, int paramInt1, int paramInt2) {
    paramReactEditText.setStagedInputType((paramInt1 ^ 0xFFFFFFFF) & paramReactEditText.getStagedInputType() | paramInt2);
  }
  
  protected void addEventEmitters(final ThemedReactContext reactContext, final ReactEditText editText) {
    editText.addTextChangedListener(new ReactTextInputTextWatcher((ReactContext)reactContext, editText));
    editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
          public void onFocusChange(View param1View, boolean param1Boolean) {
            EventDispatcher eventDispatcher = ((UIManagerModule)reactContext.getNativeModule(UIManagerModule.class)).getEventDispatcher();
            if (param1Boolean) {
              eventDispatcher.dispatchEvent(new ReactTextInputFocusEvent(editText.getId()));
              return;
            } 
            eventDispatcher.dispatchEvent(new ReactTextInputBlurEvent(editText.getId()));
            eventDispatcher.dispatchEvent(new ReactTextInputEndEditingEvent(editText.getId(), editText.getText().toString()));
          }
        });
    editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
          public boolean onEditorAction(TextView param1TextView, int param1Int, KeyEvent param1KeyEvent) {
            if ((param1Int & 0xFF) > 0 || param1Int == 0) {
              boolean bool = editText.getBlurOnSubmit();
              if ((editText.getInputType() & 0x20000) != 0) {
                param1Int = 1;
              } else {
                param1Int = 0;
              } 
              ((UIManagerModule)reactContext.getNativeModule(UIManagerModule.class)).getEventDispatcher().dispatchEvent(new ReactTextInputSubmitEditingEvent(editText.getId(), editText.getText().toString()));
              if (bool)
                editText.clearFocus(); 
              return !bool ? ((param1Int == 0)) : true;
            } 
            return true;
          }
        });
  }
  
  public LayoutShadowNode createShadowNodeInstance() {
    return (LayoutShadowNode)new ReactTextInputShadowNode();
  }
  
  public ReactEditText createViewInstance(ThemedReactContext paramThemedReactContext) {
    ReactEditText reactEditText = new ReactEditText((Context)paramThemedReactContext);
    reactEditText.setInputType(reactEditText.getInputType() & 0xFFFDFFFF);
    reactEditText.setReturnKeyType("done");
    reactEditText.setTextSize(0, (int)Math.ceil(PixelUtil.toPixelFromSP(14.0F)));
    return reactEditText;
  }
  
  public Map<String, Integer> getCommandsMap() {
    return MapBuilder.of("focusTextInput", Integer.valueOf(1), "blurTextInput", Integer.valueOf(2));
  }
  
  public Map<String, Object> getExportedCustomBubblingEventTypeConstants() {
    return MapBuilder.builder().put("topSubmitEditing", MapBuilder.of("phasedRegistrationNames", MapBuilder.of("bubbled", "onSubmitEditing", "captured", "onSubmitEditingCapture"))).put("topEndEditing", MapBuilder.of("phasedRegistrationNames", MapBuilder.of("bubbled", "onEndEditing", "captured", "onEndEditingCapture"))).put("topTextInput", MapBuilder.of("phasedRegistrationNames", MapBuilder.of("bubbled", "onTextInput", "captured", "onTextInputCapture"))).put("topFocus", MapBuilder.of("phasedRegistrationNames", MapBuilder.of("bubbled", "onFocus", "captured", "onFocusCapture"))).put("topBlur", MapBuilder.of("phasedRegistrationNames", MapBuilder.of("bubbled", "onBlur", "captured", "onBlurCapture"))).put("topKeyPress", MapBuilder.of("phasedRegistrationNames", MapBuilder.of("bubbled", "onKeyPress", "captured", "onKeyPressCapture"))).build();
  }
  
  public Map<String, Object> getExportedCustomDirectEventTypeConstants() {
    return MapBuilder.builder().put(ScrollEventType.SCROLL.getJSEventName(), MapBuilder.of("registrationName", "onScroll")).build();
  }
  
  public Map getExportedViewConstants() {
    return MapBuilder.of("AutoCapitalizationType", MapBuilder.of("none", Integer.valueOf(0), "characters", Integer.valueOf(4096), "words", Integer.valueOf(8192), "sentences", Integer.valueOf(16384)));
  }
  
  public String getName() {
    return "AndroidTextInput";
  }
  
  public Class<? extends LayoutShadowNode> getShadowNodeClass() {
    return (Class)ReactTextInputShadowNode.class;
  }
  
  protected void onAfterUpdateTransaction(ReactEditText paramReactEditText) {
    super.onAfterUpdateTransaction((View)paramReactEditText);
    paramReactEditText.commitStagedInputType();
  }
  
  public void receiveCommand(ReactEditText paramReactEditText, int paramInt, ReadableArray paramReadableArray) {
    if (paramInt != 1) {
      if (paramInt != 2)
        return; 
      paramReactEditText.clearFocusFromJS();
      return;
    } 
    paramReactEditText.requestFocusFromJS();
  }
  
  @ReactProp(name = "autoCapitalize")
  public void setAutoCapitalize(ReactEditText paramReactEditText, int paramInt) {
    updateStagedInputTypeFlag(paramReactEditText, 28672, paramInt);
  }
  
  @ReactProp(name = "autoCorrect")
  public void setAutoCorrect(ReactEditText paramReactEditText, Boolean paramBoolean) {
    boolean bool;
    if (paramBoolean != null) {
      if (paramBoolean.booleanValue()) {
        bool = true;
      } else {
        bool = true;
      } 
    } else {
      bool = false;
    } 
    updateStagedInputTypeFlag(paramReactEditText, 557056, bool);
  }
  
  @ReactProp(name = "blurOnSubmit")
  public void setBlurOnSubmit(ReactEditText paramReactEditText, Boolean paramBoolean) {
    paramReactEditText.setBlurOnSubmit(paramBoolean);
  }
  
  @ReactPropGroup(customType = "Color", names = {"borderColor", "borderLeftColor", "borderRightColor", "borderTopColor", "borderBottomColor"})
  public void setBorderColor(ReactEditText paramReactEditText, int paramInt, Integer paramInteger) {
    float f1;
    float f2 = 1.0E21F;
    if (paramInteger == null) {
      f1 = 1.0E21F;
    } else {
      f1 = (paramInteger.intValue() & 0xFFFFFF);
    } 
    if (paramInteger != null)
      f2 = (paramInteger.intValue() >>> 24); 
    paramReactEditText.setBorderColor(SPACING_TYPES[paramInt], f1, f2);
  }
  
  @ReactPropGroup(defaultFloat = 1.0E21F, names = {"borderRadius", "borderTopLeftRadius", "borderTopRightRadius", "borderBottomRightRadius", "borderBottomLeftRadius"})
  public void setBorderRadius(ReactEditText paramReactEditText, int paramInt, float paramFloat) {
    float f = paramFloat;
    if (!a.a(paramFloat))
      f = PixelUtil.toPixelFromDIP(paramFloat); 
    if (paramInt == 0) {
      paramReactEditText.setBorderRadius(f);
      return;
    } 
    paramReactEditText.setBorderRadius(f, paramInt - 1);
  }
  
  @ReactProp(name = "borderStyle")
  public void setBorderStyle(ReactEditText paramReactEditText, String paramString) {
    paramReactEditText.setBorderStyle(paramString);
  }
  
  @ReactPropGroup(defaultFloat = 1.0E21F, names = {"borderWidth", "borderLeftWidth", "borderRightWidth", "borderTopWidth", "borderBottomWidth"})
  public void setBorderWidth(ReactEditText paramReactEditText, int paramInt, float paramFloat) {
    float f = paramFloat;
    if (!a.a(paramFloat))
      f = PixelUtil.toPixelFromDIP(paramFloat); 
    paramReactEditText.setBorderWidth(SPACING_TYPES[paramInt], f);
  }
  
  @ReactProp(defaultBoolean = false, name = "caretHidden")
  public void setCaretHidden(ReactEditText paramReactEditText, boolean paramBoolean) {
    paramReactEditText.setCursorVisible(paramBoolean ^ true);
  }
  
  @ReactProp(customType = "Color", name = "color")
  public void setColor(ReactEditText paramReactEditText, Integer paramInteger) {
    if (paramInteger == null) {
      paramReactEditText.setTextColor(DefaultStyleValuesUtil.getDefaultTextColor(paramReactEditText.getContext()));
      return;
    } 
    paramReactEditText.setTextColor(paramInteger.intValue());
  }
  
  @ReactProp(defaultBoolean = false, name = "contextMenuHidden")
  public void setContextMenuHidden(ReactEditText paramReactEditText, final boolean _contextMenuHidden) {
    paramReactEditText.setOnLongClickListener(new View.OnLongClickListener() {
          public boolean onLongClick(View param1View) {
            return _contextMenuHidden;
          }
        });
  }
  
  @ReactProp(defaultBoolean = false, name = "disableFullscreenUI")
  public void setDisableFullscreenUI(ReactEditText paramReactEditText, boolean paramBoolean) {
    paramReactEditText.setDisableFullscreenUI(paramBoolean);
  }
  
  @ReactProp(defaultBoolean = true, name = "editable")
  public void setEditable(ReactEditText paramReactEditText, boolean paramBoolean) {
    paramReactEditText.setEnabled(paramBoolean);
  }
  
  @ReactProp(name = "fontFamily")
  public void setFontFamily(ReactEditText paramReactEditText, String paramString) {
    boolean bool;
    if (paramReactEditText.getTypeface() != null) {
      bool = paramReactEditText.getTypeface().getStyle();
    } else {
      bool = false;
    } 
    paramReactEditText.setTypeface(ReactFontManager.getInstance().getTypeface(paramString, bool, paramReactEditText.getContext().getAssets()));
  }
  
  @ReactProp(defaultFloat = 14.0F, name = "fontSize")
  public void setFontSize(ReactEditText paramReactEditText, float paramFloat) {
    paramReactEditText.setTextSize(0, (int)Math.ceil(PixelUtil.toPixelFromSP(paramFloat)));
  }
  
  @ReactProp(name = "fontStyle")
  public void setFontStyle(ReactEditText paramReactEditText, String paramString) {
    byte b;
    if ("italic".equals(paramString)) {
      b = 2;
    } else if ("normal".equals(paramString)) {
      b = 0;
    } else {
      b = -1;
    } 
    Typeface typeface2 = paramReactEditText.getTypeface();
    Typeface typeface1 = typeface2;
    if (typeface2 == null)
      typeface1 = Typeface.DEFAULT; 
    if (b != typeface1.getStyle())
      paramReactEditText.setTypeface(typeface1, b); 
  }
  
  @ReactProp(name = "fontWeight")
  public void setFontWeight(ReactEditText paramReactEditText, String paramString) {
    // Byte code:
    //   0: iconst_m1
    //   1: istore #5
    //   3: aload_2
    //   4: ifnull -> 16
    //   7: aload_2
    //   8: invokestatic parseNumericFontWeight : (Ljava/lang/String;)I
    //   11: istore #4
    //   13: goto -> 19
    //   16: iconst_m1
    //   17: istore #4
    //   19: iload #4
    //   21: sipush #500
    //   24: if_icmpge -> 75
    //   27: ldc_w 'bold'
    //   30: aload_2
    //   31: invokevirtual equals : (Ljava/lang/Object;)Z
    //   34: ifeq -> 40
    //   37: goto -> 75
    //   40: ldc_w 'normal'
    //   43: aload_2
    //   44: invokevirtual equals : (Ljava/lang/Object;)Z
    //   47: ifne -> 70
    //   50: iload #5
    //   52: istore_3
    //   53: iload #4
    //   55: iconst_m1
    //   56: if_icmpeq -> 77
    //   59: iload #5
    //   61: istore_3
    //   62: iload #4
    //   64: sipush #500
    //   67: if_icmpge -> 77
    //   70: iconst_0
    //   71: istore_3
    //   72: goto -> 77
    //   75: iconst_1
    //   76: istore_3
    //   77: aload_1
    //   78: invokevirtual getTypeface : ()Landroid/graphics/Typeface;
    //   81: astore #6
    //   83: aload #6
    //   85: astore_2
    //   86: aload #6
    //   88: ifnonnull -> 95
    //   91: getstatic android/graphics/Typeface.DEFAULT : Landroid/graphics/Typeface;
    //   94: astore_2
    //   95: iload_3
    //   96: aload_2
    //   97: invokevirtual getStyle : ()I
    //   100: if_icmpeq -> 109
    //   103: aload_1
    //   104: aload_2
    //   105: iload_3
    //   106: invokevirtual setTypeface : (Landroid/graphics/Typeface;I)V
    //   109: return
  }
  
  @ReactProp(name = "inlineImageLeft")
  public void setInlineImageLeft(ReactEditText paramReactEditText, String paramString) {
    paramReactEditText.setCompoundDrawablesWithIntrinsicBounds(ResourceDrawableIdHelper.getInstance().getResourceDrawableId(paramReactEditText.getContext(), paramString), 0, 0, 0);
  }
  
  @ReactProp(name = "inlineImagePadding")
  public void setInlineImagePadding(ReactEditText paramReactEditText, int paramInt) {
    paramReactEditText.setCompoundDrawablePadding(paramInt);
  }
  
  @ReactProp(name = "keyboardType")
  public void setKeyboardType(ReactEditText paramReactEditText, String paramString) {
    boolean bool;
    if ("numeric".equalsIgnoreCase(paramString)) {
      bool = true;
    } else if ("email-address".equalsIgnoreCase(paramString)) {
      bool = true;
    } else if ("phone-pad".equalsIgnoreCase(paramString)) {
      bool = true;
    } else if ("visible-password".equalsIgnoreCase(paramString)) {
      bool = true;
    } else {
      bool = true;
    } 
    updateStagedInputTypeFlag(paramReactEditText, 12339, bool);
    checkPasswordType(paramReactEditText);
  }
  
  @ReactProp(defaultFloat = 0.0F, name = "letterSpacing")
  public void setLetterSpacing(ReactEditText paramReactEditText, float paramFloat) {
    paramReactEditText.setLetterSpacingPt(paramFloat);
  }
  
  @ReactProp(name = "maxLength")
  public void setMaxLength(ReactEditText paramReactEditText, Integer paramInteger) {
    InputFilter[] arrayOfInputFilter1;
    InputFilter[] arrayOfInputFilter2 = paramReactEditText.getFilters();
    InputFilter[] arrayOfInputFilter3 = EMPTY_FILTERS;
    int i = 0;
    if (paramInteger == null) {
      arrayOfInputFilter1 = arrayOfInputFilter3;
      if (arrayOfInputFilter2.length > 0) {
        LinkedList<InputFilter> linkedList = new LinkedList();
        while (i < arrayOfInputFilter2.length) {
          if (!(arrayOfInputFilter2[i] instanceof InputFilter.LengthFilter))
            linkedList.add(arrayOfInputFilter2[i]); 
          i++;
        } 
        arrayOfInputFilter1 = arrayOfInputFilter3;
        if (!linkedList.isEmpty())
          arrayOfInputFilter1 = linkedList.<InputFilter>toArray(new InputFilter[linkedList.size()]); 
      } 
    } else if (arrayOfInputFilter2.length > 0) {
      i = 0;
      boolean bool = false;
      while (i < arrayOfInputFilter2.length) {
        if (arrayOfInputFilter2[i] instanceof InputFilter.LengthFilter) {
          arrayOfInputFilter2[i] = (InputFilter)new InputFilter.LengthFilter(arrayOfInputFilter1.intValue());
          bool = true;
        } 
        i++;
      } 
      if (!bool) {
        arrayOfInputFilter3 = new InputFilter[arrayOfInputFilter2.length + 1];
        System.arraycopy(arrayOfInputFilter2, 0, arrayOfInputFilter3, 0, arrayOfInputFilter2.length);
        arrayOfInputFilter2[arrayOfInputFilter2.length] = (InputFilter)new InputFilter.LengthFilter(arrayOfInputFilter1.intValue());
        arrayOfInputFilter1 = arrayOfInputFilter3;
      } else {
        arrayOfInputFilter1 = arrayOfInputFilter2;
      } 
    } else {
      arrayOfInputFilter2 = new InputFilter[1];
      arrayOfInputFilter2[0] = (InputFilter)new InputFilter.LengthFilter(arrayOfInputFilter1.intValue());
      arrayOfInputFilter1 = arrayOfInputFilter2;
    } 
    paramReactEditText.setFilters(arrayOfInputFilter1);
  }
  
  @ReactProp(defaultBoolean = false, name = "multiline")
  public void setMultiline(ReactEditText paramReactEditText, boolean paramBoolean) {
    int i;
    int j = 0;
    if (paramBoolean) {
      i = 0;
    } else {
      i = 131072;
    } 
    if (paramBoolean)
      j = 131072; 
    updateStagedInputTypeFlag(paramReactEditText, i, j);
  }
  
  @ReactProp(defaultInt = 1, name = "numberOfLines")
  public void setNumLines(ReactEditText paramReactEditText, int paramInt) {
    paramReactEditText.setLines(paramInt);
  }
  
  @ReactProp(defaultBoolean = false, name = "onContentSizeChange")
  public void setOnContentSizeChange(ReactEditText paramReactEditText, boolean paramBoolean) {
    if (paramBoolean) {
      paramReactEditText.setContentSizeWatcher(new ReactContentSizeWatcher(paramReactEditText));
      return;
    } 
    paramReactEditText.setContentSizeWatcher((ContentSizeWatcher)null);
  }
  
  @ReactProp(defaultBoolean = false, name = "onScroll")
  public void setOnScroll(ReactEditText paramReactEditText, boolean paramBoolean) {
    if (paramBoolean) {
      paramReactEditText.setScrollWatcher(new ReactScrollWatcher(paramReactEditText));
      return;
    } 
    paramReactEditText.setScrollWatcher((ScrollWatcher)null);
  }
  
  @ReactProp(defaultBoolean = false, name = "onSelectionChange")
  public void setOnSelectionChange(ReactEditText paramReactEditText, boolean paramBoolean) {
    if (paramBoolean) {
      paramReactEditText.setSelectionWatcher(new ReactSelectionWatcher(paramReactEditText));
      return;
    } 
    paramReactEditText.setSelectionWatcher((SelectionWatcher)null);
  }
  
  @ReactProp(name = "placeholder")
  public void setPlaceholder(ReactEditText paramReactEditText, String paramString) {
    paramReactEditText.setHint(paramString);
  }
  
  @ReactProp(customType = "Color", name = "placeholderTextColor")
  public void setPlaceholderTextColor(ReactEditText paramReactEditText, Integer paramInteger) {
    if (paramInteger == null) {
      paramReactEditText.setHintTextColor(DefaultStyleValuesUtil.getDefaultTextColorHint(paramReactEditText.getContext()));
      return;
    } 
    paramReactEditText.setHintTextColor(paramInteger.intValue());
  }
  
  @ReactProp(name = "returnKeyLabel")
  public void setReturnKeyLabel(ReactEditText paramReactEditText, String paramString) {
    paramReactEditText.setImeActionLabel(paramString, 1648);
  }
  
  @ReactProp(name = "returnKeyType")
  public void setReturnKeyType(ReactEditText paramReactEditText, String paramString) {
    paramReactEditText.setReturnKeyType(paramString);
  }
  
  @ReactProp(defaultBoolean = false, name = "secureTextEntry")
  public void setSecureTextEntry(ReactEditText paramReactEditText, boolean paramBoolean) {
    char c1;
    char c2 = Character.MIN_VALUE;
    if (paramBoolean) {
      c1 = Character.MIN_VALUE;
    } else {
      c1 = '';
    } 
    if (paramBoolean)
      c2 = ''; 
    updateStagedInputTypeFlag(paramReactEditText, c1, c2);
    checkPasswordType(paramReactEditText);
  }
  
  @ReactProp(defaultBoolean = false, name = "selectTextOnFocus")
  public void setSelectTextOnFocus(ReactEditText paramReactEditText, boolean paramBoolean) {
    paramReactEditText.setSelectAllOnFocus(paramBoolean);
  }
  
  @ReactProp(name = "selection")
  public void setSelection(ReactEditText paramReactEditText, ReadableMap paramReadableMap) {
    if (paramReadableMap == null)
      return; 
    if (paramReadableMap.hasKey("start") && paramReadableMap.hasKey("end"))
      paramReactEditText.setSelection(paramReadableMap.getInt("start"), paramReadableMap.getInt("end")); 
  }
  
  @ReactProp(customType = "Color", name = "selectionColor")
  public void setSelectionColor(ReactEditText paramReactEditText, Integer paramInteger) {
    if (paramInteger == null) {
      paramReactEditText.setHighlightColor(DefaultStyleValuesUtil.getDefaultTextColorHighlight(paramReactEditText.getContext()));
    } else {
      paramReactEditText.setHighlightColor(paramInteger.intValue());
    } 
    setCursorColor(paramReactEditText, paramInteger);
  }
  
  @ReactProp(name = "textAlign")
  public void setTextAlign(ReactEditText paramReactEditText, String paramString) {
    if (paramString == null || "auto".equals(paramString)) {
      paramReactEditText.setGravityHorizontal(0);
      return;
    } 
    if ("left".equals(paramString)) {
      paramReactEditText.setGravityHorizontal(3);
      return;
    } 
    if ("right".equals(paramString)) {
      paramReactEditText.setGravityHorizontal(5);
      return;
    } 
    if ("center".equals(paramString)) {
      paramReactEditText.setGravityHorizontal(1);
      return;
    } 
    if ("justify".equals(paramString)) {
      paramReactEditText.setGravityHorizontal(3);
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder("Invalid textAlign: ");
    stringBuilder.append(paramString);
    throw new JSApplicationIllegalArgumentException(stringBuilder.toString());
  }
  
  @ReactProp(name = "textAlignVertical")
  public void setTextAlignVertical(ReactEditText paramReactEditText, String paramString) {
    if (paramString == null || "auto".equals(paramString)) {
      paramReactEditText.setGravityVertical(0);
      return;
    } 
    if ("top".equals(paramString)) {
      paramReactEditText.setGravityVertical(48);
      return;
    } 
    if ("bottom".equals(paramString)) {
      paramReactEditText.setGravityVertical(80);
      return;
    } 
    if ("center".equals(paramString)) {
      paramReactEditText.setGravityVertical(16);
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder("Invalid textAlignVertical: ");
    stringBuilder.append(paramString);
    throw new JSApplicationIllegalArgumentException(stringBuilder.toString());
  }
  
  @ReactProp(customType = "Color", name = "underlineColorAndroid")
  public void setUnderlineColor(ReactEditText paramReactEditText, Integer paramInteger) {
    Drawable drawable2 = paramReactEditText.getBackground();
    Drawable drawable1 = drawable2;
    if (drawable2.getConstantState() != null)
      drawable1 = drawable2.mutate(); 
    if (paramInteger == null) {
      drawable1.clearColorFilter();
      return;
    } 
    drawable1.setColorFilter(paramInteger.intValue(), PorterDuff.Mode.SRC_IN);
  }
  
  public void updateExtraData(ReactEditText paramReactEditText, Object paramObject) {
    if (paramObject instanceof ReactTextUpdate) {
      paramObject = paramObject;
      paramReactEditText.setPadding((int)paramObject.getPaddingLeft(), (int)paramObject.getPaddingTop(), (int)paramObject.getPaddingRight(), (int)paramObject.getPaddingBottom());
      if (paramObject.containsImages())
        TextInlineImageSpan.possiblyUpdateInlineImageSpans(paramObject.getText(), (TextView)paramReactEditText); 
      paramReactEditText.maybeSetText((ReactTextUpdate)paramObject);
    } 
  }
  
  class ReactContentSizeWatcher implements ContentSizeWatcher {
    private ReactEditText mEditText;
    
    private EventDispatcher mEventDispatcher;
    
    private int mPreviousContentHeight;
    
    private int mPreviousContentWidth;
    
    public ReactContentSizeWatcher(ReactEditText param1ReactEditText) {
      this.mEditText = param1ReactEditText;
      this.mEventDispatcher = ((UIManagerModule)((ReactContext)param1ReactEditText.getContext()).getNativeModule(UIManagerModule.class)).getEventDispatcher();
    }
    
    public void onLayout() {
      int i = this.mEditText.getWidth();
      int j = this.mEditText.getHeight();
      if (this.mEditText.getLayout() != null) {
        i = this.mEditText.getCompoundPaddingLeft() + this.mEditText.getLayout().getWidth() + this.mEditText.getCompoundPaddingRight();
        j = this.mEditText.getCompoundPaddingTop() + this.mEditText.getLayout().getHeight() + this.mEditText.getCompoundPaddingBottom();
      } 
      if (i != this.mPreviousContentWidth || j != this.mPreviousContentHeight) {
        this.mPreviousContentHeight = j;
        this.mPreviousContentWidth = i;
        this.mEventDispatcher.dispatchEvent(new ReactContentSizeChangedEvent(this.mEditText.getId(), PixelUtil.toDIPFromPixel(i), PixelUtil.toDIPFromPixel(j)));
      } 
    }
  }
  
  class ReactScrollWatcher implements ScrollWatcher {
    private EventDispatcher mEventDispatcher;
    
    private int mPreviousHoriz;
    
    private int mPreviousVert;
    
    private ReactEditText mReactEditText;
    
    public ReactScrollWatcher(ReactEditText param1ReactEditText) {
      this.mReactEditText = param1ReactEditText;
      this.mEventDispatcher = ((UIManagerModule)((ReactContext)param1ReactEditText.getContext()).getNativeModule(UIManagerModule.class)).getEventDispatcher();
    }
    
    public void onScrollChanged(int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
      if (this.mPreviousHoriz != param1Int1 || this.mPreviousVert != param1Int2) {
        ScrollEvent scrollEvent = ScrollEvent.obtain(this.mReactEditText.getId(), ScrollEventType.SCROLL, param1Int1, param1Int2, 0.0F, 0.0F, 0, 0, this.mReactEditText.getWidth(), this.mReactEditText.getHeight());
        this.mEventDispatcher.dispatchEvent((Event)scrollEvent);
        this.mPreviousHoriz = param1Int1;
        this.mPreviousVert = param1Int2;
      } 
    }
  }
  
  class ReactSelectionWatcher implements SelectionWatcher {
    private EventDispatcher mEventDispatcher;
    
    private int mPreviousSelectionEnd;
    
    private int mPreviousSelectionStart;
    
    private ReactEditText mReactEditText;
    
    public ReactSelectionWatcher(ReactEditText param1ReactEditText) {
      this.mReactEditText = param1ReactEditText;
      this.mEventDispatcher = ((UIManagerModule)((ReactContext)param1ReactEditText.getContext()).getNativeModule(UIManagerModule.class)).getEventDispatcher();
    }
    
    public void onSelectionChanged(int param1Int1, int param1Int2) {
      if (this.mPreviousSelectionStart != param1Int1 || this.mPreviousSelectionEnd != param1Int2) {
        this.mEventDispatcher.dispatchEvent(new ReactTextInputSelectionEvent(this.mReactEditText.getId(), param1Int1, param1Int2));
        this.mPreviousSelectionStart = param1Int1;
        this.mPreviousSelectionEnd = param1Int2;
      } 
    }
  }
  
  class ReactTextInputTextWatcher implements TextWatcher {
    private ReactEditText mEditText;
    
    private EventDispatcher mEventDispatcher;
    
    private String mPreviousText;
    
    public ReactTextInputTextWatcher(ReactContext param1ReactContext, ReactEditText param1ReactEditText) {
      this.mEventDispatcher = ((UIManagerModule)param1ReactContext.getNativeModule(UIManagerModule.class)).getEventDispatcher();
      this.mEditText = param1ReactEditText;
      this.mPreviousText = null;
    }
    
    public void afterTextChanged(Editable param1Editable) {}
    
    public void beforeTextChanged(CharSequence param1CharSequence, int param1Int1, int param1Int2, int param1Int3) {
      this.mPreviousText = param1CharSequence.toString();
    }
    
    public void onTextChanged(CharSequence param1CharSequence, int param1Int1, int param1Int2, int param1Int3) {
      if (param1Int3 == 0 && param1Int2 == 0)
        return; 
      a.b(this.mPreviousText);
      String str1 = param1CharSequence.toString().substring(param1Int1, param1Int1 + param1Int3);
      String str2 = this.mPreviousText;
      int i = param1Int1 + param1Int2;
      str2 = str2.substring(param1Int1, i);
      if (param1Int3 == param1Int2 && str1.equals(str2))
        return; 
      this.mEventDispatcher.dispatchEvent(new ReactTextChangedEvent(this.mEditText.getId(), param1CharSequence.toString(), this.mEditText.incrementAndGetEventCounter()));
      this.mEventDispatcher.dispatchEvent(new ReactTextInputEvent(this.mEditText.getId(), str1, str2, param1Int1, i));
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\textinput\ReactTextInputManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */