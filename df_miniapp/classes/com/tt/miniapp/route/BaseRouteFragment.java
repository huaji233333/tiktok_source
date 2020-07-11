package com.tt.miniapp.route;

import android.support.v4.app.Fragment;
import android.support.v4.app.r;
import java.util.LinkedList;
import java.util.Queue;

public abstract class BaseRouteFragment extends Fragment implements RouteTransaction {
  private Queue<Integer> transactionList = new LinkedList<Integer>();
  
  public void doWithOutCommitByAnimationEnd(r paramr) {
    if (paramr == null) {
      this.transactionList.clear();
      return;
    } 
    while (this.transactionList.peek() != null) {
      Integer integer = this.transactionList.poll();
      if (integer != null) {
        if (integer.intValue() == 1) {
          paramr.c(this);
          continue;
        } 
        if (integer.intValue() == 2) {
          paramr.b(this);
          continue;
        } 
        paramr.a(this);
      } 
    } 
  }
  
  public void executeHideWithOutCommit(r paramr) {
    if (this.transactionList.isEmpty()) {
      paramr.b(this);
      return;
    } 
    this.transactionList.offer(Integer.valueOf(2));
  }
  
  public void executeRemoveWithOutCommit(r paramr) {
    if (this.transactionList.isEmpty()) {
      paramr.a(this);
      return;
    } 
    this.transactionList.offer(Integer.valueOf(3));
  }
  
  public void executeShowWithOutCommit(r paramr) {
    if (this.transactionList.isEmpty()) {
      paramr.c(this);
      return;
    } 
    this.transactionList.offer(Integer.valueOf(1));
  }
  
  public void offerHideToQueue() {
    this.transactionList.offer(Integer.valueOf(2));
  }
  
  public void offerRemoveToQueue() {
    this.transactionList.offer(Integer.valueOf(3));
  }
  
  public void offerShowToQueue() {
    this.transactionList.offer(Integer.valueOf(1));
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\route\BaseRouteFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */