package com.graphic.RNCanvas;

import java.util.Stack;

public class CanvasDrawingStateManager {
  private final Stack<CanvasDrawingState> states = new Stack<CanvasDrawingState>();
  
  private void setUpNewState() {
    this.states.push(new CanvasDrawingState());
  }
  
  private void setUpNewState(CanvasDrawingState paramCanvasDrawingState) {
    this.states.push(new CanvasDrawingState(paramCanvasDrawingState));
  }
  
  public CanvasDrawingState getCurrentState() {
    return this.states.peek();
  }
  
  public void reset() {
    this.states.clear();
    setUpNewState();
  }
  
  public void restore() {
    this.states.pop();
  }
  
  public void save() {
    setUpNewState(getCurrentState());
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\graphic\RNCanvas\CanvasDrawingStateManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */