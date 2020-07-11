package com.tt.miniapp.component.nativeview.picker.wheel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import com.a;
import com.tt.miniapp.component.nativeview.picker.wheel.entity.WheelItem;
import com.tt.miniapphost.util.UIUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.TimerTask;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class WheelView extends View {
  private float centerContentOffset;
  
  private DividerConfig dividerConfig = new DividerConfig();
  
  private int drawCenterContentStart = 0;
  
  private int drawOutContentStart = 0;
  
  private float firstLineY;
  
  private GestureDetector gestureDetector;
  
  private int gravity = 17;
  
  public MessageHandler handler;
  
  public int initPosition = -1;
  
  public boolean isLoop = true;
  
  public float itemHeight;
  
  public List<WheelItem> items = new ArrayList<WheelItem>();
  
  private String label;
  
  private float lineSpaceMultiplier = 3.0F;
  
  private ScheduledFuture<?> mFuture;
  
  private int maxTextHeight;
  
  private int maxTextWidth;
  
  private int measuredHeight;
  
  private int measuredWidth;
  
  private int offset = 0;
  
  public OnItemSelectListener onItemSelectListener;
  
  public OnWheelListener onWheelListener;
  
  private boolean onlyShowCenterLabel = true;
  
  private int outTextSize = 15;
  
  private Paint paintCenterText;
  
  private Paint paintIndicator;
  
  private Paint paintOuterText;
  
  private Paint paintShadow;
  
  private int preCurrentIndex;
  
  private float previousY = 0.0F;
  
  private int radius;
  
  private float secondLineY;
  
  public int selectedIndex;
  
  private long startTime = 0L;
  
  private int textColorCenter = -14540254;
  
  private int textColorOuter = -6710887;
  
  private int textPadding = -1;
  
  private int textSize = 17;
  
  private boolean textSizeAutoFit = true;
  
  private int textSizeAutoScaleMinimum = -1;
  
  private int textSkewXOffset = 0;
  
  public float totalScrollY = 0.0F;
  
  private Typeface typeface = Typeface.DEFAULT;
  
  private boolean useWeight = false;
  
  private int visibleItemCount = 11;
  
  private int widthMeasureSpec;
  
  public WheelView(Context paramContext) {
    this(paramContext, (AttributeSet)null);
  }
  
  public WheelView(Context paramContext, AttributeSet paramAttributeSet) {
    super(paramContext, paramAttributeSet);
    float f = (getResources().getDisplayMetrics()).density;
    if (f < 1.0F) {
      this.centerContentOffset = 2.4F;
    } else if (1.0F <= f && f < 2.0F) {
      this.centerContentOffset = 3.6F;
    } else if (1.0F <= f && f < 2.0F) {
      this.centerContentOffset = 4.5F;
    } else if (2.0F <= f && f < 3.0F) {
      this.centerContentOffset = 6.0F;
    } else if (f >= 3.0F) {
      this.centerContentOffset = f * 2.5F;
    } 
    judgeLineSpace();
    initView(paramContext);
  }
  
  public static float computeTextWidth(String paramString, float paramFloat) {
    Paint paint = new Paint();
    paint.setTextSize(paramFloat);
    return paint.measureText(paramString);
  }
  
  public static String cutShortString(String paramString, int paramInt) {
    // Byte code:
    //   0: iload_1
    //   1: iconst_1
    //   2: if_icmpeq -> 17
    //   5: iload_1
    //   6: iconst_2
    //   7: if_icmpeq -> 58
    //   10: iload_1
    //   11: iconst_3
    //   12: if_icmpeq -> 99
    //   15: aload_0
    //   16: areturn
    //   17: aload_0
    //   18: invokevirtual length : ()I
    //   21: bipush #15
    //   23: if_icmple -> 58
    //   26: new java/lang/StringBuilder
    //   29: dup
    //   30: invokespecial <init> : ()V
    //   33: astore_2
    //   34: aload_2
    //   35: aload_0
    //   36: iconst_0
    //   37: bipush #15
    //   39: invokevirtual substring : (II)Ljava/lang/String;
    //   42: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   45: pop
    //   46: aload_2
    //   47: ldc '...'
    //   49: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   52: pop
    //   53: aload_2
    //   54: invokevirtual toString : ()Ljava/lang/String;
    //   57: areturn
    //   58: aload_0
    //   59: invokevirtual length : ()I
    //   62: bipush #7
    //   64: if_icmple -> 99
    //   67: new java/lang/StringBuilder
    //   70: dup
    //   71: invokespecial <init> : ()V
    //   74: astore_2
    //   75: aload_2
    //   76: aload_0
    //   77: iconst_0
    //   78: bipush #7
    //   80: invokevirtual substring : (II)Ljava/lang/String;
    //   83: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   86: pop
    //   87: aload_2
    //   88: ldc '...'
    //   90: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   93: pop
    //   94: aload_2
    //   95: invokevirtual toString : ()Ljava/lang/String;
    //   98: areturn
    //   99: aload_0
    //   100: astore_2
    //   101: aload_0
    //   102: invokevirtual length : ()I
    //   105: iconst_5
    //   106: if_icmple -> 140
    //   109: new java/lang/StringBuilder
    //   112: dup
    //   113: invokespecial <init> : ()V
    //   116: astore_2
    //   117: aload_2
    //   118: aload_0
    //   119: iconst_0
    //   120: iconst_5
    //   121: invokevirtual substring : (II)Ljava/lang/String;
    //   124: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   127: pop
    //   128: aload_2
    //   129: ldc '...'
    //   131: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   134: pop
    //   135: aload_2
    //   136: invokevirtual toString : ()Ljava/lang/String;
    //   139: astore_2
    //   140: aload_2
    //   141: areturn
  }
  
  private int getLoopMappingIndex(int paramInt) {
    if (paramInt < 0)
      return getLoopMappingIndex(paramInt + this.items.size()); 
    int i = paramInt;
    if (paramInt > this.items.size() - 1)
      i = getLoopMappingIndex(paramInt - this.items.size()); 
    return i;
  }
  
  private void initDataForIDE() {
    if (isInEditMode())
      setItems(new String[] { "李玉江", "男", "贵州", "穿青人" }); 
  }
  
  private void initPaints() {
    this.paintOuterText = new Paint();
    this.paintOuterText.setAntiAlias(true);
    this.paintOuterText.setColor(this.textColorOuter);
    this.paintOuterText.setTypeface(this.typeface);
    this.paintOuterText.setTextSize(this.outTextSize);
    this.paintCenterText = new Paint();
    this.paintCenterText.setAntiAlias(true);
    this.paintCenterText.setColor(this.textColorCenter);
    this.paintCenterText.setTextScaleX(1.0F);
    this.paintCenterText.setTypeface(this.typeface);
    this.paintCenterText.setTextSize(this.textSize);
    this.paintIndicator = new Paint();
    this.paintIndicator.setAntiAlias(true);
    this.paintIndicator.setColor(this.dividerConfig.color);
    this.paintIndicator.setStrokeWidth(this.dividerConfig.thick);
    this.paintIndicator.setAlpha(this.dividerConfig.alpha);
    this.paintShadow = new Paint();
    this.paintShadow.setAntiAlias(true);
    this.paintShadow.setColor(this.dividerConfig.shadowColor);
    this.paintShadow.setAlpha(this.dividerConfig.shadowAlpha);
    setLayerType(1, null);
  }
  
  private void initView(Context paramContext) {
    this.handler = new MessageHandler(this);
    this.gestureDetector = new GestureDetector(paramContext, (GestureDetector.OnGestureListener)new GestureDetector.SimpleOnGestureListener() {
          public final boolean onFling(MotionEvent param1MotionEvent1, MotionEvent param1MotionEvent2, float param1Float1, float param1Float2) {
            WheelView.this.scrollBy(param1Float2);
            return true;
          }
        });
    this.gestureDetector.setIsLongpressEnabled(false);
    initPaints();
    initDataForIDE();
  }
  
  private void judgeLineSpace() {
    float f = this.lineSpaceMultiplier;
    if (f < 1.5F) {
      this.lineSpaceMultiplier = 1.5F;
      return;
    } 
    if (f > 4.0F)
      this.lineSpaceMultiplier = 4.0F; 
  }
  
  private void measureTextWidthHeight() {
    Rect rect = new Rect();
    for (int i = 0; i < this.items.size(); i++) {
      String str = obtainContentText(this.items.get(i));
      this.paintCenterText.getTextBounds(str, 0, str.length(), rect);
      int j = rect.width();
      if (j > this.maxTextWidth)
        this.maxTextWidth = j; 
    } 
    this.paintCenterText.getTextBounds("测试", 0, 2, rect);
    this.maxTextHeight = rect.height() + 2;
    this.itemHeight = (int)UIUtils.dip2Px(getContext(), 48.0F);
  }
  
  private void measuredCenterContentStart(String paramString) {
    Rect rect = new Rect();
    this.paintCenterText.getTextBounds(paramString, 0, paramString.length(), rect);
    int i = this.gravity;
    if (i != 3) {
      if (i != 5) {
        if (i != 17)
          return; 
        double d = (this.measuredWidth - rect.width());
        Double.isNaN(d);
        this.drawCenterContentStart = (int)(d * 0.5D);
        return;
      } 
      this.drawCenterContentStart = this.measuredWidth - rect.width() - (int)this.centerContentOffset;
      return;
    } 
    this.drawCenterContentStart = (int)UIUtils.dip2Px(getContext(), 8.0F);
  }
  
  private void measuredOutContentStart(String paramString) {
    Rect rect = new Rect();
    this.paintOuterText.getTextBounds(paramString, 0, paramString.length(), rect);
    int i = this.gravity;
    if (i != 3) {
      if (i != 5) {
        if (i != 17)
          return; 
        double d = (this.measuredWidth - rect.width());
        Double.isNaN(d);
        this.drawOutContentStart = (int)(d * 0.5D);
        return;
      } 
      this.drawOutContentStart = this.measuredWidth - rect.width() - (int)this.centerContentOffset;
      return;
    } 
    this.drawOutContentStart = (int)UIUtils.dip2Px(getContext(), 8.0F);
  }
  
  private String obtainContentText(Object paramObject) {
    return (paramObject == null) ? "" : ((paramObject instanceof WheelItem) ? ((WheelItem)paramObject).getName() : ((paramObject instanceof Integer) ? a.a(Locale.getDefault(), "%02d", new Object[] { Integer.valueOf(((Integer)paramObject).intValue()) }) : paramObject.toString()));
  }
  
  private int obtainTextWidth(Paint paramPaint, String paramString) {
    boolean bool;
    int i = 0;
    if (paramString != null && paramString.length() > 0) {
      int k = paramString.length();
      float[] arrayOfFloat = new float[k];
      paramPaint.getTextWidths(paramString, arrayOfFloat);
      int j = 0;
      while (true) {
        bool = j;
        if (i < k) {
          j += (int)Math.ceil(arrayOfFloat[i]);
          i++;
          continue;
        } 
        break;
      } 
    } else {
      bool = false;
    } 
    return bool;
  }
  
  private void remeasure() {
    if (this.items == null)
      return; 
    measureTextWidthHeight();
    int i = (int)(this.itemHeight * (this.visibleItemCount - 1));
    double d = (i * 2);
    Double.isNaN(d);
    this.measuredHeight = (int)(d / Math.PI);
    d = i;
    Double.isNaN(d);
    this.radius = (int)(d / Math.PI);
    ViewGroup.LayoutParams layoutParams = getLayoutParams();
    if (this.useWeight) {
      this.measuredWidth = View.MeasureSpec.getSize(this.widthMeasureSpec);
    } else if (layoutParams != null && layoutParams.width > 0) {
      this.measuredWidth = layoutParams.width;
    } else {
      this.measuredWidth = this.maxTextWidth;
      if (this.textPadding < 0)
        this.textPadding = (int)UIUtils.dip2Px(getContext(), 13.0F); 
      this.measuredWidth += this.textPadding * 2;
      if (!TextUtils.isEmpty(this.label))
        this.measuredWidth += obtainTextWidth(this.paintCenterText, this.label); 
    } 
    i = this.measuredHeight;
    float f1 = i;
    float f2 = this.itemHeight;
    this.firstLineY = (f1 - f2) / 2.0F;
    this.secondLineY = (i + f2) / 2.0F;
    if (this.initPosition == -1)
      if (this.isLoop) {
        this.initPosition = (this.items.size() + 1) / 2;
      } else {
        this.initPosition = 0;
      }  
    this.preCurrentIndex = this.initPosition;
    this.textSizeAutoScaleMinimum = UIUtils.sp2px(getContext(), 8.0F);
  }
  
  private boolean remeasureTextSize(String paramString) {
    Rect rect = new Rect();
    this.paintCenterText.getTextBounds(paramString, 0, paramString.length(), rect);
    float f = computeTextWidth(paramString, this.textSize);
    int i = this.textSize;
    if (f > this.measuredWidth) {
      this.paintOuterText.setTextSize(this.outTextSize);
      this.paintCenterText.setTextSize(i);
      this.paintCenterText.getTextBounds(paramString, 0, paramString.length(), rect);
      return false;
    } 
    return true;
  }
  
  public void cancelFuture() {
    ScheduledFuture<?> scheduledFuture = this.mFuture;
    if (scheduledFuture != null && !scheduledFuture.isCancelled()) {
      this.mFuture.cancel(true);
      this.mFuture = null;
    } 
  }
  
  protected int getItemCount() {
    List<WheelItem> list = this.items;
    return (list != null) ? list.size() : 0;
  }
  
  public final int getSelectedIndex() {
    return this.selectedIndex;
  }
  
  public void itemSelectedCallback() {
    if (this.onItemSelectListener == null && this.onWheelListener == null)
      return; 
    postDelayed(new Runnable() {
          public void run() {
            if (WheelView.this.onItemSelectListener != null)
              WheelView.this.onItemSelectListener.onSelected(WheelView.this.selectedIndex); 
            if (WheelView.this.onWheelListener != null)
              WheelView.this.onWheelListener.onSelected(true, WheelView.this.selectedIndex, ((WheelItem)WheelView.this.items.get(WheelView.this.selectedIndex)).getName()); 
          }
        }200L);
  }
  
  protected void onDraw(Canvas paramCanvas) {
    List<WheelItem> list = this.items;
    if (list != null) {
      if (list.size() == 0)
        return; 
      String[] arrayOfString = new String[this.visibleItemCount];
      int i = (int)(this.totalScrollY / this.itemHeight);
      this.preCurrentIndex = this.initPosition + i % this.items.size();
      if (!this.isLoop) {
        if (this.preCurrentIndex < 0)
          this.preCurrentIndex = 0; 
        if (this.preCurrentIndex > this.items.size() - 1)
          this.preCurrentIndex = this.items.size() - 1; 
      } else {
        if (this.preCurrentIndex < 0)
          this.preCurrentIndex = this.items.size() + this.preCurrentIndex; 
        if (this.preCurrentIndex > this.items.size() - 1)
          this.preCurrentIndex -= this.items.size(); 
      } 
      float f = this.totalScrollY % this.itemHeight;
      for (i = 0;; i++) {
        int j = this.visibleItemCount;
        if (i < j) {
          int k = this.preCurrentIndex - j / 2 - i;
          if (this.isLoop) {
            j = getLoopMappingIndex(k);
          } else {
            if (k < 0) {
              arrayOfString[i] = "";
              continue;
            } 
            j = k;
            if (k > this.items.size() - 1) {
              arrayOfString[i] = "";
              continue;
            } 
          } 
          arrayOfString[i] = ((WheelItem)this.items.get(j)).getName();
          continue;
        } 
        if (this.dividerConfig.visible) {
          float f1 = this.dividerConfig.ratio;
          i = this.measuredWidth;
          float f3 = i;
          float f4 = this.firstLineY;
          float f5 = i;
          float f2 = 1.0F - f1;
          paramCanvas.drawLine(f3 * f1, f4, f5 * f2, f4, this.paintIndicator);
          i = this.measuredWidth;
          f3 = i;
          f4 = this.secondLineY;
          paramCanvas.drawLine(f3 * f1, f4, i * f2, f4, this.paintIndicator);
        } 
        if (this.dividerConfig.shadowVisible) {
          this.paintShadow.setColor(this.dividerConfig.shadowColor);
          this.paintShadow.setAlpha(this.dividerConfig.shadowAlpha);
          paramCanvas.drawRect(0.0F, this.firstLineY, this.measuredWidth, this.secondLineY, this.paintShadow);
        } 
        i = 0;
        while (true) {
          i++;
          this.paintOuterText.setTextSize(this.outTextSize);
        } 
        break;
      } 
    } 
  }
  
  protected void onMeasure(int paramInt1, int paramInt2) {
    this.widthMeasureSpec = paramInt1;
    remeasure();
    setMeasuredDimension(this.measuredWidth, this.measuredHeight);
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent) {
    boolean bool = this.gestureDetector.onTouchEvent(paramMotionEvent);
    ViewParent viewParent = getParent();
    int i = paramMotionEvent.getAction();
    if (i != 0) {
      if (i != 2) {
        if (!bool) {
          float f1 = paramMotionEvent.getY();
          i = this.radius;
          double d1 = Math.acos(((i - f1) / i));
          double d2 = this.radius;
          Double.isNaN(d2);
          f1 = this.itemHeight;
          double d3 = (f1 / 2.0F);
          Double.isNaN(d3);
          double d4 = f1;
          Double.isNaN(d4);
          i = (int)((d1 * d2 + d3) / d4);
          float f2 = this.totalScrollY;
          this.offset = (int)((i - this.visibleItemCount / 2) * f1 - (f2 % f1 + f1) % f1);
          if (System.currentTimeMillis() - this.startTime > 120L) {
            smoothScroll(3);
          } else {
            smoothScroll(1);
          } 
        } 
        if (viewParent != null)
          viewParent.requestDisallowInterceptTouchEvent(false); 
      } else {
        float f = this.previousY - paramMotionEvent.getRawY();
        this.previousY = paramMotionEvent.getRawY();
        this.totalScrollY += f;
        if (!this.isLoop) {
          float f3 = -this.initPosition * this.itemHeight;
          float f2 = (this.items.size() - 1 - this.initPosition);
          float f1 = this.itemHeight;
          float f4 = f2 * f1;
          float f5 = this.totalScrollY;
          double d1 = f5;
          double d2 = f1;
          Double.isNaN(d2);
          Double.isNaN(d1);
          if (d1 - d2 * 0.25D < f3) {
            f2 = f5 - f;
            f1 = f4;
          } else {
            d1 = f5;
            d2 = f1;
            Double.isNaN(d2);
            Double.isNaN(d1);
            f1 = f4;
            f2 = f3;
            if (d1 + d2 * 0.25D > f4) {
              f1 = f5 - f;
              f2 = f3;
            } 
          } 
          f3 = this.totalScrollY;
          if (f3 < f2) {
            this.totalScrollY = (int)f2;
          } else if (f3 > f1) {
            this.totalScrollY = (int)f1;
          } 
        } 
      } 
    } else {
      this.startTime = System.currentTimeMillis();
      cancelFuture();
      this.previousY = paramMotionEvent.getRawY();
      if (viewParent != null)
        viewParent.requestDisallowInterceptTouchEvent(true); 
    } 
    invalidate();
    return true;
  }
  
  public void scrollBy(float paramFloat) {
    cancelFuture();
    InertiaTimerTask inertiaTimerTask = new InertiaTimerTask(this, paramFloat);
    this.mFuture = _lancet.com_ss_android_ugc_aweme_lancet_ThreadPoolLancet_newSingleThreadScheduledExecutor().scheduleWithFixedDelay(inertiaTimerTask, 0L, 5L, TimeUnit.MILLISECONDS);
  }
  
  public final void setCycleDisable(boolean paramBoolean) {
    this.isLoop = paramBoolean ^ true;
  }
  
  public void setDividerColor(int paramInt) {
    this.dividerConfig.setColor(paramInt);
    this.paintIndicator.setColor(paramInt);
  }
  
  public void setDividerConfig(DividerConfig paramDividerConfig) {
    if (paramDividerConfig == null) {
      this.dividerConfig.setVisible(false);
      this.dividerConfig.setShadowVisible(false);
      return;
    } 
    this.dividerConfig = paramDividerConfig;
    this.paintIndicator.setColor(paramDividerConfig.color);
    this.paintIndicator.setStrokeWidth(paramDividerConfig.thick);
    this.paintIndicator.setAlpha(paramDividerConfig.alpha);
    this.paintShadow.setColor(paramDividerConfig.shadowColor);
    this.paintShadow.setAlpha(paramDividerConfig.shadowAlpha);
  }
  
  public final void setGravity(int paramInt) {
    this.gravity = paramInt;
  }
  
  public final void setItems(List<?> paramList) {
    this.items.clear();
    Iterator<?> iterator = paramList.iterator();
    while (iterator.hasNext()) {
      Object object = iterator.next();
      if (object instanceof WheelItem) {
        this.items.add((WheelItem)object);
        continue;
      } 
      if (object instanceof CharSequence || object instanceof Number) {
        this.items.add(new StringItem(object.toString()));
        continue;
      } 
      StringBuilder stringBuilder = new StringBuilder("please implements ");
      stringBuilder.append(WheelItem.class.getName());
      throw new IllegalArgumentException(stringBuilder.toString());
    } 
    remeasure();
    invalidate();
  }
  
  public final void setItems(List<?> paramList, int paramInt) {
    setItems(paramList);
    setSelectedIndex(paramInt);
  }
  
  public final void setItems(List<String> paramList, String paramString) {
    int j = paramList.indexOf(paramString);
    int i = j;
    if (j == -1)
      i = 0; 
    setItems(paramList, i);
  }
  
  public final void setItems(String[] paramArrayOfString) {
    setItems(Arrays.asList((Object[])paramArrayOfString));
  }
  
  public final void setItems(String[] paramArrayOfString, int paramInt) {
    setItems(Arrays.asList((Object[])paramArrayOfString), paramInt);
  }
  
  public final void setItems(String[] paramArrayOfString, String paramString) {
    setItems(Arrays.asList(paramArrayOfString), paramString);
  }
  
  public final void setLabel(String paramString) {
    setLabel(paramString, true);
  }
  
  public final void setLabel(String paramString, boolean paramBoolean) {
    this.label = paramString;
    this.onlyShowCenterLabel = paramBoolean;
  }
  
  @Deprecated
  public void setLineConfig(DividerConfig paramDividerConfig) {
    setDividerConfig(paramDividerConfig);
  }
  
  public final void setLineSpaceMultiplier(float paramFloat) {
    this.lineSpaceMultiplier = paramFloat;
    judgeLineSpace();
  }
  
  public final void setOffset(int paramInt) {
    if (paramInt > 0 && paramInt <= 5) {
      int i;
      if (paramInt % 2 == 0) {
        i = paramInt;
      } else {
        i = paramInt - 1;
      } 
      setVisibleItemCount(paramInt * 2 + 1 + i);
      return;
    } 
    throw new IllegalArgumentException("must between 1 and 5");
  }
  
  public final void setOnItemSelectListener(OnItemSelectListener paramOnItemSelectListener) {
    this.onItemSelectListener = paramOnItemSelectListener;
  }
  
  @Deprecated
  public final void setOnWheelListener(OnWheelListener paramOnWheelListener) {
    this.onWheelListener = paramOnWheelListener;
  }
  
  public final void setOutTextSize(float paramFloat) {
    if (paramFloat > 0.0F) {
      this.outTextSize = (int)((getContext().getResources().getDisplayMetrics()).density * paramFloat);
      this.paintOuterText.setTextSize(this.outTextSize);
    } 
  }
  
  @Deprecated
  public void setPadding(int paramInt) {
    setTextPadding(paramInt);
  }
  
  public final void setSelectedIndex(int paramInt) {
    List<WheelItem> list = this.items;
    if (list != null) {
      if (list.isEmpty())
        return; 
      int i = this.items.size();
      if (paramInt == 0 || (paramInt > 0 && paramInt < i && paramInt != this.selectedIndex)) {
        this.initPosition = paramInt;
        this.totalScrollY = 0.0F;
        this.offset = 0;
        invalidate();
      } 
    } 
  }
  
  public void setTextColor(int paramInt) {
    this.textColorOuter = paramInt;
    this.textColorCenter = paramInt;
    this.paintOuterText.setColor(paramInt);
    this.paintCenterText.setColor(paramInt);
  }
  
  public void setTextColor(int paramInt1, int paramInt2) {
    this.textColorOuter = paramInt1;
    this.textColorCenter = paramInt2;
    this.paintOuterText.setColor(paramInt1);
    this.paintCenterText.setColor(paramInt2);
  }
  
  public void setTextPadding(int paramInt) {
    this.textPadding = (int)UIUtils.dip2Px(getContext(), paramInt);
  }
  
  public final void setTextSize(float paramFloat) {
    if (paramFloat > 0.0F) {
      this.textSize = (int)((getContext().getResources().getDisplayMetrics()).density * paramFloat);
      this.paintCenterText.setTextSize(this.textSize);
    } 
  }
  
  public void setTextSizeAutoFit(boolean paramBoolean) {
    this.textSizeAutoFit = paramBoolean;
  }
  
  public void setTextSkewXOffset(int paramInt) {
    this.textSkewXOffset = paramInt;
    if (paramInt != 0)
      this.paintCenterText.setTextScaleX(1.0F); 
  }
  
  public final void setTypeface(Typeface paramTypeface) {
    this.typeface = paramTypeface;
    this.paintOuterText.setTypeface(this.typeface);
    this.paintCenterText.setTypeface(this.typeface);
  }
  
  public void setUseWeight(boolean paramBoolean) {
    this.useWeight = paramBoolean;
  }
  
  public final void setVisibleItemCount(int paramInt) {
    if (paramInt % 2 != 0) {
      if (paramInt != this.visibleItemCount)
        this.visibleItemCount = paramInt; 
      return;
    } 
    throw new IllegalArgumentException("must be odd");
  }
  
  public void smoothScroll(int paramInt) {
    cancelFuture();
    if (paramInt == 2 || paramInt == 3) {
      float f1 = this.totalScrollY;
      float f2 = this.itemHeight;
      this.offset = (int)((f1 % f2 + f2) % f2);
      paramInt = this.offset;
      if (paramInt > f2 / 2.0F) {
        this.offset = (int)(f2 - paramInt);
      } else {
        this.offset = -paramInt;
      } 
    } 
    SmoothScrollTimerTask smoothScrollTimerTask = new SmoothScrollTimerTask(this, this.offset);
    this.mFuture = _lancet.com_ss_android_ugc_aweme_lancet_ThreadPoolLancet_newSingleThreadScheduledExecutor().scheduleWithFixedDelay(smoothScrollTimerTask, 0L, 10L, TimeUnit.MILLISECONDS);
  }
  
  public void stopScroll() {
    cancelFuture();
    OnItemSelectListener onItemSelectListener = this.onItemSelectListener;
    if (onItemSelectListener != null)
      onItemSelectListener.onSelected(this.selectedIndex); 
  }
  
  public static class DividerConfig {
    protected int alpha = 220;
    
    protected int color = -1513240;
    
    protected float ratio;
    
    protected int shadowAlpha = 100;
    
    protected int shadowColor = -6710887;
    
    protected boolean shadowVisible;
    
    protected float thick = 2.0F;
    
    protected boolean visible = true;
    
    public DividerConfig() {}
    
    public DividerConfig(float param1Float) {
      this.ratio = param1Float;
    }
    
    public DividerConfig setAlpha(int param1Int) {
      this.alpha = param1Int;
      return this;
    }
    
    public DividerConfig setColor(int param1Int) {
      this.color = param1Int;
      return this;
    }
    
    public DividerConfig setRatio(float param1Float) {
      this.ratio = param1Float;
      return this;
    }
    
    public DividerConfig setShadowAlpha(int param1Int) {
      this.shadowAlpha = param1Int;
      return this;
    }
    
    public DividerConfig setShadowColor(int param1Int) {
      this.shadowVisible = true;
      this.shadowColor = param1Int;
      return this;
    }
    
    public DividerConfig setShadowVisible(boolean param1Boolean) {
      this.shadowVisible = param1Boolean;
      if (param1Boolean && this.color == -1513240) {
        this.color = this.shadowColor;
        this.alpha = 255;
      } 
      return this;
    }
    
    public DividerConfig setThick(float param1Float) {
      this.thick = param1Float;
      return this;
    }
    
    public DividerConfig setVisible(boolean param1Boolean) {
      this.visible = param1Boolean;
      return this;
    }
    
    public String toString() {
      StringBuilder stringBuilder = new StringBuilder("visible=");
      stringBuilder.append(this.visible);
      stringBuilder.append(",color=");
      stringBuilder.append(this.color);
      stringBuilder.append(",alpha=");
      stringBuilder.append(this.alpha);
      stringBuilder.append(",thick=");
      stringBuilder.append(this.thick);
      return stringBuilder.toString();
    }
  }
  
  static class InertiaTimerTask extends TimerTask {
    float a = 2.14748365E9F;
    
    final float velocityY;
    
    final WheelView view;
    
    InertiaTimerTask(WheelView param1WheelView, float param1Float) {
      this.view = param1WheelView;
      this.velocityY = param1Float;
    }
    
    public final void run() {
      if (this.a == 2.14748365E9F)
        if (Math.abs(this.velocityY) > 2000.0F) {
          if (this.velocityY > 0.0F) {
            this.a = 2000.0F;
          } else {
            this.a = -2000.0F;
          } 
        } else {
          this.a = this.velocityY;
        }  
      if (Math.abs(this.a) >= 0.0F && Math.abs(this.a) <= 20.0F) {
        this.view.cancelFuture();
        this.view.handler.sendEmptyMessage(2000);
        return;
      } 
      int i = (int)(this.a * 10.0F / 1000.0F);
      WheelView wheelView = this.view;
      float f1 = wheelView.totalScrollY;
      float f2 = i;
      wheelView.totalScrollY = f1 - f2;
      if (!this.view.isLoop) {
        float f3;
        f1 = this.view.itemHeight;
        float f4 = -this.view.initPosition * f1;
        float f5 = (this.view.getItemCount() - 1 - this.view.initPosition) * f1;
        double d1 = this.view.totalScrollY;
        double d2 = f1;
        Double.isNaN(d2);
        d2 *= 0.25D;
        Double.isNaN(d1);
        if (d1 - d2 < f4) {
          f1 = this.view.totalScrollY + f2;
          f3 = f5;
        } else {
          d1 = this.view.totalScrollY;
          Double.isNaN(d1);
          f1 = f4;
          f3 = f5;
          if (d1 + d2 > f5) {
            f3 = this.view.totalScrollY + f2;
            f1 = f4;
          } 
        } 
        if (this.view.totalScrollY <= f1) {
          this.a = 40.0F;
          this.view.totalScrollY = (int)f1;
        } else if (this.view.totalScrollY >= f3) {
          this.view.totalScrollY = (int)f3;
          this.a = -40.0F;
        } 
      } 
      f1 = this.a;
      if (f1 < 0.0F) {
        this.a = f1 + 20.0F;
      } else {
        this.a = f1 - 20.0F;
      } 
      this.view.handler.sendEmptyMessage(1000);
    }
  }
  
  @Deprecated
  public static class LineConfig extends DividerConfig {}
  
  static class MessageHandler extends Handler {
    final WheelView view;
    
    MessageHandler(WheelView param1WheelView) {
      this.view = param1WheelView;
    }
    
    public final void handleMessage(Message param1Message) {
      int i = param1Message.what;
      if (i != 1000) {
        if (i != 2000) {
          if (i != 3000)
            return; 
          this.view.itemSelectedCallback();
          return;
        } 
        this.view.smoothScroll(2);
        return;
      } 
      this.view.invalidate();
    }
  }
  
  public static interface OnItemSelectListener {
    void onSelected(int param1Int);
  }
  
  @Deprecated
  public static interface OnWheelListener {
    void onSelected(boolean param1Boolean, int param1Int, String param1String);
  }
  
  @Deprecated
  public static interface OnWheelViewListener extends OnWheelListener {}
  
  static class SmoothScrollTimerTask extends TimerTask {
    int offset;
    
    int realOffset;
    
    int realTotalOffset = Integer.MAX_VALUE;
    
    final WheelView view;
    
    SmoothScrollTimerTask(WheelView param1WheelView, int param1Int) {
      this.view = param1WheelView;
      this.offset = param1Int;
    }
    
    public void run() {
      if (this.realTotalOffset == Integer.MAX_VALUE)
        this.realTotalOffset = this.offset; 
      int i = this.realTotalOffset;
      this.realOffset = (int)(i * 0.1F);
      if (this.realOffset == 0)
        if (i < 0) {
          this.realOffset = -1;
        } else {
          this.realOffset = 1;
        }  
      if (Math.abs(this.realTotalOffset) <= 1) {
        this.view.cancelFuture();
        this.view.handler.sendEmptyMessage(3000);
        return;
      } 
      WheelView wheelView = this.view;
      wheelView.totalScrollY += this.realOffset;
      if (!this.view.isLoop) {
        float f1 = this.view.itemHeight;
        float f2 = -this.view.initPosition;
        float f3 = (this.view.getItemCount() - 1 - this.view.initPosition);
        if (this.view.totalScrollY <= f2 * f1 || this.view.totalScrollY >= f3 * f1) {
          wheelView = this.view;
          wheelView.totalScrollY -= this.realOffset;
          this.view.cancelFuture();
          this.view.handler.sendEmptyMessage(3000);
          return;
        } 
      } 
      this.view.handler.sendEmptyMessage(1000);
      this.realTotalOffset -= this.realOffset;
    }
  }
  
  static class StringItem implements WheelItem {
    private String name;
    
    private StringItem(String param1String) {
      this.name = param1String;
    }
    
    public String getName() {
      return this.name;
    }
  }
  
  class WheelView {}
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\component\nativeview\picker\wheel\WheelView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */