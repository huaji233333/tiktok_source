package com.facebook.react.views.art;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextUtils;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.ReactShadowNode;
import com.facebook.react.uimanager.ReactShadowNodeImpl;
import com.facebook.react.uimanager.annotations.ReactProp;

public class ARTTextShadowNode extends ARTShapeShadowNode {
  private ReadableMap mFrame;
  
  private int mTextAlignment;
  
  public ARTTextShadowNode() {}
  
  public ARTTextShadowNode(ARTTextShadowNode paramARTTextShadowNode) {
    super(paramARTTextShadowNode);
    this.mTextAlignment = paramARTTextShadowNode.mTextAlignment;
    this.mFrame = paramARTTextShadowNode.mFrame;
  }
  
  private void applyTextPropertiesToPaint(Paint paramPaint) {
    int i = this.mTextAlignment;
    byte b = 2;
    if (i != 0) {
      if (i != 1) {
        if (i == 2)
          paramPaint.setTextAlign(Paint.Align.CENTER); 
      } else {
        paramPaint.setTextAlign(Paint.Align.RIGHT);
      } 
    } else {
      paramPaint.setTextAlign(Paint.Align.LEFT);
    } 
    ReadableMap readableMap = this.mFrame;
    if (readableMap != null && readableMap.hasKey("font")) {
      readableMap = this.mFrame.getMap("font");
      if (readableMap != null) {
        boolean bool;
        float f = 12.0F;
        if (readableMap.hasKey("fontSize"))
          f = (float)readableMap.getDouble("fontSize"); 
        paramPaint.setTextSize(f * this.mScale);
        if (readableMap.hasKey("fontWeight") && "bold".equals(readableMap.getString("fontWeight"))) {
          i = 1;
        } else {
          i = 0;
        } 
        if (readableMap.hasKey("fontStyle") && "italic".equals(readableMap.getString("fontStyle"))) {
          bool = true;
        } else {
          bool = false;
        } 
        if (i != 0 && bool) {
          i = 3;
        } else if (i != 0) {
          i = 1;
        } else if (bool) {
          i = b;
        } else {
          i = 0;
        } 
        paramPaint.setTypeface(Typeface.create(readableMap.getString("fontFamily"), i));
      } 
    } 
  }
  
  public void draw(Canvas paramCanvas, Paint paramPaint, float paramFloat) {
    if (this.mFrame == null)
      return; 
    paramFloat *= this.mOpacity;
    if (paramFloat <= 0.01F)
      return; 
    if (!this.mFrame.hasKey("lines"))
      return; 
    ReadableArray readableArray = this.mFrame.getArray("lines");
    if (readableArray != null) {
      if (readableArray.size() == 0)
        return; 
      saveAndSetupCanvas(paramCanvas);
      String[] arrayOfString = new String[readableArray.size()];
      int i;
      for (i = 0; i < arrayOfString.length; i++)
        arrayOfString[i] = readableArray.getString(i); 
      String str = TextUtils.join("\n", (Object[])arrayOfString);
      if (setupStrokePaint(paramPaint, paramFloat)) {
        applyTextPropertiesToPaint(paramPaint);
        if (this.mPath == null) {
          paramCanvas.drawText(str, 0.0F, -paramPaint.ascent(), paramPaint);
        } else {
          paramCanvas.drawTextOnPath(str, this.mPath, 0.0F, 0.0F, paramPaint);
        } 
      } 
      if (setupFillPaint(paramPaint, paramFloat)) {
        applyTextPropertiesToPaint(paramPaint);
        if (this.mPath == null) {
          paramCanvas.drawText(str, 0.0F, -paramPaint.ascent(), paramPaint);
        } else {
          paramCanvas.drawTextOnPath(str, this.mPath, 0.0F, 0.0F, paramPaint);
        } 
      } 
      restoreCanvas(paramCanvas);
      markUpdateSeen();
    } 
  }
  
  public ARTShapeShadowNode mutableCopy() {
    return new ARTTextShadowNode(this);
  }
  
  @ReactProp(defaultInt = 0, name = "alignment")
  public void setAlignment(int paramInt) {
    this.mTextAlignment = paramInt;
  }
  
  @ReactProp(name = "frame")
  public void setFrame(ReadableMap paramReadableMap) {
    this.mFrame = paramReadableMap;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\art\ARTTextShadowNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */