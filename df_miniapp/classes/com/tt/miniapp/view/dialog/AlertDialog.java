package com.tt.miniapp.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Message;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.tt.miniapphost.AppBrandLogger;

public class AlertDialog extends Dialog implements DialogInterface {
  public AlertController mAlert = new AlertController(getContext(), this, getWindow());
  
  protected AlertDialog(Context paramContext) {
    this(paramContext, 0);
  }
  
  protected AlertDialog(Context paramContext, int paramInt) {
    super(paramContext, 2132607944);
  }
  
  public Button getButton(int paramInt) {
    return this.mAlert.getButton(paramInt);
  }
  
  public ListView getListView() {
    return this.mAlert.getListView();
  }
  
  protected void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    this.mAlert.installContent();
    Window window = getWindow();
    if (window != null)
      window.setBackgroundDrawable((Drawable)new ColorDrawable(Color.parseColor("#00000000"))); 
  }
  
  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent) {
    return this.mAlert.onKeyDown(paramInt, paramKeyEvent) ? true : super.onKeyDown(paramInt, paramKeyEvent);
  }
  
  public boolean onKeyUp(int paramInt, KeyEvent paramKeyEvent) {
    return this.mAlert.onKeyUp(paramInt, paramKeyEvent) ? true : super.onKeyUp(paramInt, paramKeyEvent);
  }
  
  public void setButton(int paramInt, CharSequence paramCharSequence, DialogInterface.OnClickListener paramOnClickListener) {
    this.mAlert.setButton(paramInt, paramCharSequence, paramOnClickListener, null);
  }
  
  public void setButton(int paramInt, CharSequence paramCharSequence, Message paramMessage) {
    this.mAlert.setButton(paramInt, paramCharSequence, null, paramMessage);
  }
  
  @Deprecated
  public void setButton(CharSequence paramCharSequence, DialogInterface.OnClickListener paramOnClickListener) {
    setButton(-1, paramCharSequence, paramOnClickListener);
  }
  
  @Deprecated
  public void setButton(CharSequence paramCharSequence, Message paramMessage) {
    setButton(-1, paramCharSequence, paramMessage);
  }
  
  @Deprecated
  public void setButton2(CharSequence paramCharSequence, DialogInterface.OnClickListener paramOnClickListener) {
    setButton(-2, paramCharSequence, paramOnClickListener);
  }
  
  @Deprecated
  public void setButton2(CharSequence paramCharSequence, Message paramMessage) {
    setButton(-2, paramCharSequence, paramMessage);
  }
  
  @Deprecated
  public void setButton3(CharSequence paramCharSequence, DialogInterface.OnClickListener paramOnClickListener) {
    setButton(-3, paramCharSequence, paramOnClickListener);
  }
  
  @Deprecated
  public void setButton3(CharSequence paramCharSequence, Message paramMessage) {
    setButton(-3, paramCharSequence, paramMessage);
  }
  
  public void setCustomTitle(View paramView) {
    this.mAlert.setCustomTitle(paramView);
  }
  
  public void setIcon(int paramInt) {
    this.mAlert.setIcon(paramInt);
  }
  
  public void setIcon(Drawable paramDrawable) {
    this.mAlert.setIcon(paramDrawable);
  }
  
  public void setIconAttribute(int paramInt) {
    TypedValue typedValue = new TypedValue();
    getContext().getTheme().resolveAttribute(paramInt, typedValue, true);
    this.mAlert.setIcon(typedValue.resourceId);
  }
  
  public void setInverseBackgroundForced(boolean paramBoolean) {
    this.mAlert.setInverseBackgroundForced(paramBoolean);
  }
  
  public void setMessage(CharSequence paramCharSequence) {
    this.mAlert.setMessage(paramCharSequence);
  }
  
  public void setTitle(CharSequence paramCharSequence) {
    super.setTitle(paramCharSequence);
    this.mAlert.setTitle(paramCharSequence);
  }
  
  public void setView(View paramView) {
    this.mAlert.setView(paramView);
  }
  
  public void setView(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    this.mAlert.setView(paramView, paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  public static class Builder {
    private final AlertController.AlertParams P;
    
    private int mTheme;
    
    public Builder(Context param1Context) {
      this(param1Context, 2097807367);
    }
    
    public Builder(Context param1Context, int param1Int) {
      this.P = new AlertController.AlertParams((Context)new ContextThemeWrapper(param1Context, param1Int));
      this.mTheme = param1Int;
    }
    
    public AlertDialog create() {
      AlertDialog alertDialog = new AlertDialog(this.P.mContext);
      this.P.apply(alertDialog.mAlert);
      alertDialog.setCancelable(this.P.mCancelable);
      if (this.P.mCancelable)
        alertDialog.setCanceledOnTouchOutside(true); 
      alertDialog.setOnCancelListener(this.P.mOnCancelListener);
      alertDialog.setOnDismissListener(this.P.mOnDismissListener);
      if (this.P.mOnKeyListener != null)
        alertDialog.setOnKeyListener(this.P.mOnKeyListener); 
      return alertDialog;
    }
    
    public Context getContext() {
      return this.P.mContext;
    }
    
    public Builder setAdapter(ListAdapter param1ListAdapter, DialogInterface.OnClickListener param1OnClickListener) {
      AlertController.AlertParams alertParams = this.P;
      alertParams.mAdapter = param1ListAdapter;
      alertParams.mOnClickListener = param1OnClickListener;
      return this;
    }
    
    public Builder setCancelable(boolean param1Boolean) {
      this.P.mCancelable = param1Boolean;
      return this;
    }
    
    public Builder setCursor(Cursor param1Cursor, DialogInterface.OnClickListener param1OnClickListener, String param1String) {
      AlertController.AlertParams alertParams = this.P;
      alertParams.mCursor = param1Cursor;
      alertParams.mLabelColumn = param1String;
      alertParams.mOnClickListener = param1OnClickListener;
      return this;
    }
    
    public Builder setCustomTitle(View param1View) {
      this.P.mCustomTitleView = param1View;
      return this;
    }
    
    public Builder setIcon(int param1Int) {
      this.P.mIconId = param1Int;
      return this;
    }
    
    public Builder setIcon(Drawable param1Drawable) {
      this.P.mIcon = param1Drawable;
      return this;
    }
    
    public Builder setIconAttribute(int param1Int) {
      TypedValue typedValue = new TypedValue();
      this.P.mContext.getTheme().resolveAttribute(param1Int, typedValue, true);
      this.P.mIconId = typedValue.resourceId;
      return this;
    }
    
    public Builder setInverseBackgroundForced(boolean param1Boolean) {
      this.P.mForceInverseBackground = param1Boolean;
      return this;
    }
    
    public Builder setItems(int param1Int, DialogInterface.OnClickListener param1OnClickListener) {
      AlertController.AlertParams alertParams = this.P;
      alertParams.mItems = alertParams.mContext.getResources().getTextArray(param1Int);
      this.P.mOnClickListener = param1OnClickListener;
      return this;
    }
    
    public Builder setItems(CharSequence[] param1ArrayOfCharSequence, DialogInterface.OnClickListener param1OnClickListener) {
      AlertController.AlertParams alertParams = this.P;
      alertParams.mItems = param1ArrayOfCharSequence;
      alertParams.mOnClickListener = param1OnClickListener;
      return this;
    }
    
    public Builder setMessage(int param1Int) {
      AlertController.AlertParams alertParams = this.P;
      alertParams.mMessage = alertParams.mContext.getText(param1Int);
      return this;
    }
    
    public Builder setMessage(CharSequence param1CharSequence) {
      this.P.mMessage = param1CharSequence;
      return this;
    }
    
    public Builder setMultiChoiceItems(int param1Int, boolean[] param1ArrayOfboolean, DialogInterface.OnMultiChoiceClickListener param1OnMultiChoiceClickListener) {
      AlertController.AlertParams alertParams = this.P;
      alertParams.mItems = alertParams.mContext.getResources().getTextArray(param1Int);
      alertParams = this.P;
      alertParams.mOnCheckboxClickListener = param1OnMultiChoiceClickListener;
      alertParams.mCheckedItems = param1ArrayOfboolean;
      alertParams.mIsMultiChoice = true;
      return this;
    }
    
    public Builder setMultiChoiceItems(Cursor param1Cursor, String param1String1, String param1String2, DialogInterface.OnMultiChoiceClickListener param1OnMultiChoiceClickListener) {
      AlertController.AlertParams alertParams = this.P;
      alertParams.mCursor = param1Cursor;
      alertParams.mOnCheckboxClickListener = param1OnMultiChoiceClickListener;
      alertParams.mIsCheckedColumn = param1String1;
      alertParams.mLabelColumn = param1String2;
      alertParams.mIsMultiChoice = true;
      return this;
    }
    
    public Builder setMultiChoiceItems(CharSequence[] param1ArrayOfCharSequence, boolean[] param1ArrayOfboolean, DialogInterface.OnMultiChoiceClickListener param1OnMultiChoiceClickListener) {
      AlertController.AlertParams alertParams = this.P;
      alertParams.mItems = param1ArrayOfCharSequence;
      alertParams.mOnCheckboxClickListener = param1OnMultiChoiceClickListener;
      alertParams.mCheckedItems = param1ArrayOfboolean;
      alertParams.mIsMultiChoice = true;
      return this;
    }
    
    public Builder setNegativeButton(int param1Int, DialogInterface.OnClickListener param1OnClickListener) {
      AlertController.AlertParams alertParams = this.P;
      alertParams.mNegativeButtonText = alertParams.mContext.getText(param1Int);
      this.P.mNegativeButtonListener = param1OnClickListener;
      return this;
    }
    
    public Builder setNegativeButton(CharSequence param1CharSequence, DialogInterface.OnClickListener param1OnClickListener) {
      AlertController.AlertParams alertParams = this.P;
      alertParams.mNegativeButtonText = param1CharSequence;
      alertParams.mNegativeButtonListener = param1OnClickListener;
      return this;
    }
    
    public Builder setNeutralButton(int param1Int, DialogInterface.OnClickListener param1OnClickListener) {
      AlertController.AlertParams alertParams = this.P;
      alertParams.mNeutralButtonText = alertParams.mContext.getText(param1Int);
      this.P.mNeutralButtonListener = param1OnClickListener;
      return this;
    }
    
    public Builder setNeutralButton(CharSequence param1CharSequence, DialogInterface.OnClickListener param1OnClickListener) {
      AlertController.AlertParams alertParams = this.P;
      alertParams.mNeutralButtonText = param1CharSequence;
      alertParams.mNeutralButtonListener = param1OnClickListener;
      return this;
    }
    
    public Builder setOnCancelListener(DialogInterface.OnCancelListener param1OnCancelListener) {
      this.P.mOnCancelListener = param1OnCancelListener;
      return this;
    }
    
    public Builder setOnDismissListener(DialogInterface.OnDismissListener param1OnDismissListener) {
      this.P.mOnDismissListener = param1OnDismissListener;
      return this;
    }
    
    public Builder setOnItemSelectedListener(AdapterView.OnItemSelectedListener param1OnItemSelectedListener) {
      this.P.mOnItemSelectedListener = param1OnItemSelectedListener;
      return this;
    }
    
    public Builder setOnKeyListener(DialogInterface.OnKeyListener param1OnKeyListener) {
      this.P.mOnKeyListener = param1OnKeyListener;
      return this;
    }
    
    public Builder setPositiveButton(int param1Int, DialogInterface.OnClickListener param1OnClickListener) {
      AlertController.AlertParams alertParams = this.P;
      alertParams.mPositiveButtonText = alertParams.mContext.getText(param1Int);
      this.P.mPositiveButtonListener = param1OnClickListener;
      return this;
    }
    
    public Builder setPositiveButton(CharSequence param1CharSequence, DialogInterface.OnClickListener param1OnClickListener) {
      AlertController.AlertParams alertParams = this.P;
      alertParams.mPositiveButtonText = param1CharSequence;
      alertParams.mPositiveButtonListener = param1OnClickListener;
      return this;
    }
    
    public Builder setRecycleOnMeasureEnabled(boolean param1Boolean) {
      this.P.mRecycleOnMeasure = param1Boolean;
      return this;
    }
    
    public Builder setSingleChoiceItems(int param1Int1, int param1Int2, DialogInterface.OnClickListener param1OnClickListener) {
      AlertController.AlertParams alertParams = this.P;
      alertParams.mItems = alertParams.mContext.getResources().getTextArray(param1Int1);
      alertParams = this.P;
      alertParams.mOnClickListener = param1OnClickListener;
      alertParams.mCheckedItem = param1Int2;
      alertParams.mIsSingleChoice = true;
      return this;
    }
    
    public Builder setSingleChoiceItems(Cursor param1Cursor, int param1Int, String param1String, DialogInterface.OnClickListener param1OnClickListener) {
      AlertController.AlertParams alertParams = this.P;
      alertParams.mCursor = param1Cursor;
      alertParams.mOnClickListener = param1OnClickListener;
      alertParams.mCheckedItem = param1Int;
      alertParams.mLabelColumn = param1String;
      alertParams.mIsSingleChoice = true;
      return this;
    }
    
    public Builder setSingleChoiceItems(ListAdapter param1ListAdapter, int param1Int, DialogInterface.OnClickListener param1OnClickListener) {
      AlertController.AlertParams alertParams = this.P;
      alertParams.mAdapter = param1ListAdapter;
      alertParams.mOnClickListener = param1OnClickListener;
      alertParams.mCheckedItem = param1Int;
      alertParams.mIsSingleChoice = true;
      return this;
    }
    
    public Builder setSingleChoiceItems(CharSequence[] param1ArrayOfCharSequence, int param1Int, DialogInterface.OnClickListener param1OnClickListener) {
      AlertController.AlertParams alertParams = this.P;
      alertParams.mItems = param1ArrayOfCharSequence;
      alertParams.mOnClickListener = param1OnClickListener;
      alertParams.mCheckedItem = param1Int;
      alertParams.mIsSingleChoice = true;
      return this;
    }
    
    public Builder setTitle(int param1Int) {
      AlertController.AlertParams alertParams = this.P;
      alertParams.mTitle = alertParams.mContext.getText(param1Int);
      return this;
    }
    
    public Builder setTitle(CharSequence param1CharSequence) {
      this.P.mTitle = param1CharSequence;
      return this;
    }
    
    public Builder setView(int param1Int) {
      AlertController.AlertParams alertParams = this.P;
      alertParams.mView = null;
      alertParams.mViewLayoutResId = param1Int;
      alertParams.mViewSpacingSpecified = false;
      return this;
    }
    
    public Builder setView(View param1View) {
      AlertController.AlertParams alertParams = this.P;
      alertParams.mView = param1View;
      alertParams.mViewLayoutResId = 0;
      alertParams.mViewSpacingSpecified = false;
      return this;
    }
    
    public Builder setView(View param1View, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
      AlertController.AlertParams alertParams = this.P;
      alertParams.mView = param1View;
      alertParams.mViewLayoutResId = 0;
      alertParams.mViewSpacingSpecified = true;
      alertParams.mViewSpacingLeft = param1Int1;
      alertParams.mViewSpacingTop = param1Int2;
      alertParams.mViewSpacingRight = param1Int3;
      alertParams.mViewSpacingBottom = param1Int4;
      return this;
    }
    
    public AlertDialog show() {
      AlertDialog alertDialog = create();
      try {
        alertDialog.show();
        return alertDialog;
      } catch (Exception exception) {
        AppBrandLogger.stacktrace(6, "AlertDialog", exception.getStackTrace());
        return alertDialog;
      } 
    }
  }
  
  public static interface NightMode {
    boolean isToggled();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\view\dialog\AlertDialog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */