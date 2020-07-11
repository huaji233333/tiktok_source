package com.facebook.react.flat;

import java.lang.reflect.Array;
import java.util.ArrayDeque;
import java.util.ArrayList;

final class ElementsList<E> {
  private Scope mCurrentScope = null;
  
  private final ArrayDeque<E> mElements = new ArrayDeque<E>();
  
  private final E[] mEmptyArray;
  
  private int mScopeIndex = 0;
  
  private final ArrayList<Scope> mScopesStack = new ArrayList<Scope>();
  
  public ElementsList(E[] paramArrayOfE) {
    this.mEmptyArray = paramArrayOfE;
    this.mScopesStack.add(this.mCurrentScope);
  }
  
  private E[] extractElements(int paramInt) {
    if (paramInt == 0)
      return this.mEmptyArray; 
    Object[] arrayOfObject = (Object[])Array.newInstance(this.mEmptyArray.getClass().getComponentType(), paramInt);
    while (--paramInt >= 0) {
      arrayOfObject[paramInt] = this.mElements.pollLast();
      paramInt--;
    } 
    return (E[])arrayOfObject;
  }
  
  private Scope getCurrentScope() {
    return this.mCurrentScope;
  }
  
  private void popScope() {
    this.mScopeIndex--;
    this.mCurrentScope = this.mScopesStack.get(this.mScopeIndex);
  }
  
  private void pushScope() {
    this.mScopeIndex++;
    if (this.mScopeIndex == this.mScopesStack.size()) {
      this.mCurrentScope = new Scope();
      this.mScopesStack.add(this.mCurrentScope);
      return;
    } 
    this.mCurrentScope = this.mScopesStack.get(this.mScopeIndex);
  }
  
  public final void add(E paramE) {
    Scope scope = getCurrentScope();
    if (scope.index < scope.elements.length && scope.elements[scope.index] == paramE) {
      scope.index++;
    } else {
      scope.index = Integer.MAX_VALUE;
    } 
    this.mElements.add(paramE);
  }
  
  public final void clear() {
    if (getCurrentScope() == null) {
      this.mElements.clear();
      return;
    } 
    throw new RuntimeException("Must call finish() for every start() call being made.");
  }
  
  public final E[] finish() {
    E[] arrayOfE;
    Scope scope = getCurrentScope();
    popScope();
    int i = this.mElements.size() - scope.size;
    if (scope.index != scope.elements.length) {
      E[] arrayOfE1 = extractElements(i);
    } else {
      for (int j = 0; j < i; j++)
        this.mElements.pollLast(); 
      arrayOfE = null;
    } 
    scope.elements = null;
    return arrayOfE;
  }
  
  public final void start(Object[] paramArrayOfObject) {
    pushScope();
    Scope scope = getCurrentScope();
    scope.elements = paramArrayOfObject;
    scope.index = 0;
    scope.size = this.mElements.size();
  }
  
  static final class Scope {
    Object[] elements;
    
    int index;
    
    int size;
    
    private Scope() {}
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\flat\ElementsList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */