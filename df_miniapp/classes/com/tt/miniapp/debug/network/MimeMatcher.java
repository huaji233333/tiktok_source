package com.tt.miniapp.debug.network;

import java.util.ArrayList;

public class MimeMatcher<T> {
  private final ArrayList<MimeMatcherRule> mRuleMap = new ArrayList<MimeMatcherRule>();
  
  public void addRule(String paramString, T paramT) {
    this.mRuleMap.add(new MimeMatcherRule(paramString, paramT));
  }
  
  public void clear() {
    this.mRuleMap.clear();
  }
  
  public T match(String paramString) {
    int j = this.mRuleMap.size();
    for (int i = 0; i < j; i++) {
      MimeMatcherRule mimeMatcherRule = this.mRuleMap.get(i);
      if (mimeMatcherRule.match(paramString))
        return mimeMatcherRule.getResultIfMatched(); 
    } 
    return null;
  }
  
  class MimeMatcherRule {
    private final boolean mHasWildcard;
    
    private final String mMatchPrefix;
    
    private final T mResultIfMatched;
    
    public MimeMatcherRule(String param1String, T param1T) {
      if (param1String.endsWith("*")) {
        this.mHasWildcard = true;
        this.mMatchPrefix = param1String.substring(0, param1String.length() - 1);
      } else {
        this.mHasWildcard = false;
        this.mMatchPrefix = param1String;
      } 
      if (!this.mMatchPrefix.contains("*")) {
        this.mResultIfMatched = param1T;
        return;
      } 
      StringBuilder stringBuilder = new StringBuilder("Multiple wildcards present in rule expression ");
      stringBuilder.append(param1String);
      throw new IllegalArgumentException(stringBuilder.toString());
    }
    
    public T getResultIfMatched() {
      return this.mResultIfMatched;
    }
    
    public boolean match(String param1String) {
      return !param1String.startsWith(this.mMatchPrefix) ? false : ((this.mHasWildcard || param1String.length() == this.mMatchPrefix.length()));
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\debug\network\MimeMatcher.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */