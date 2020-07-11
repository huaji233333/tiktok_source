package com.tt.miniapp.route;

import android.support.v4.app.r;

public interface RouteTransaction {
  void doWithOutCommitByAnimationEnd(r paramr);
  
  void executeHideWithOutCommit(r paramr);
  
  void executeRemoveWithOutCommit(r paramr);
  
  void executeShowWithOutCommit(r paramr);
  
  void offerHideToQueue();
  
  void offerRemoveToQueue();
  
  void offerShowToQueue();
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\route\RouteTransaction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */