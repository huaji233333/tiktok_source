package com.facebook.react.common.futures;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class SimpleSettableFuture<T> implements Future<T> {
  private Exception mException;
  
  private final CountDownLatch mReadyLatch = new CountDownLatch(1);
  
  private T mResult;
  
  private void checkNotSet() {
    if (this.mReadyLatch.getCount() != 0L)
      return; 
    throw new RuntimeException("Result has already been set!");
  }
  
  public boolean cancel(boolean paramBoolean) {
    throw new UnsupportedOperationException();
  }
  
  public T get() throws InterruptedException, ExecutionException {
    this.mReadyLatch.await();
    Exception exception = this.mException;
    if (exception == null)
      return this.mResult; 
    throw new ExecutionException(exception);
  }
  
  public T get(long paramLong, TimeUnit paramTimeUnit) throws InterruptedException, ExecutionException, TimeoutException {
    if (this.mReadyLatch.await(paramLong, paramTimeUnit)) {
      Exception exception = this.mException;
      if (exception == null)
        return this.mResult; 
      throw new ExecutionException(exception);
    } 
    throw new TimeoutException("Timed out waiting for result");
  }
  
  public T getOrThrow() {
    try {
      return get();
    } catch (InterruptedException interruptedException) {
    
    } catch (ExecutionException executionException) {}
    throw new RuntimeException(executionException);
  }
  
  public T getOrThrow(long paramLong, TimeUnit paramTimeUnit) {
    try {
      return get(paramLong, paramTimeUnit);
    } catch (InterruptedException interruptedException) {
    
    } catch (ExecutionException executionException) {
    
    } catch (TimeoutException timeoutException) {}
    throw new RuntimeException(timeoutException);
  }
  
  public boolean isCancelled() {
    return false;
  }
  
  public boolean isDone() {
    return (this.mReadyLatch.getCount() == 0L);
  }
  
  public void set(T paramT) {
    checkNotSet();
    this.mResult = paramT;
    this.mReadyLatch.countDown();
  }
  
  public void setException(Exception paramException) {
    checkNotSet();
    this.mException = paramException;
    this.mReadyLatch.countDown();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\common\futures\SimpleSettableFuture.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */