package com.facebook.react.devsupport;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.text.SpannedString;
import android.text.method.LinkMovementMethod;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.facebook.common.e.a;
import com.facebook.i.a.a;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.devsupport.interfaces.DevSupportManager;
import com.facebook.react.devsupport.interfaces.StackFrame;
import okhttp3.ac;
import okhttp3.ad;
import okhttp3.w;
import okhttp3.y;
import org.json.JSONObject;

class RedBoxDialog extends Dialog implements AdapterView.OnItemClickListener {
  public boolean isReporting;
  
  private Button mCopyToClipboardButton;
  
  public final DevSupportManager mDevSupportManager;
  
  private Button mDismissButton;
  
  private final DoubleTapReloadRecognizer mDoubleTapReloadRecognizer;
  
  public View mLineSeparator;
  
  public ProgressBar mLoadingIndicator;
  
  public final RedBoxHandler mRedBoxHandler;
  
  private Button mReloadJsButton;
  
  public Button mReportButton;
  
  private View.OnClickListener mReportButtonOnClickListener = new View.OnClickListener() {
      public void onClick(View param1View) {
        if (RedBoxDialog.this.mRedBoxHandler != null && RedBoxDialog.this.mRedBoxHandler.isReportEnabled()) {
          if (RedBoxDialog.this.isReporting)
            return; 
          RedBoxDialog redBoxDialog = RedBoxDialog.this;
          redBoxDialog.isReporting = true;
          ((TextView)a.b(redBoxDialog.mReportTextView)).setText("Reporting...");
          ((TextView)a.b(RedBoxDialog.this.mReportTextView)).setVisibility(0);
          ((ProgressBar)a.b(RedBoxDialog.this.mLoadingIndicator)).setVisibility(0);
          ((View)a.b(RedBoxDialog.this.mLineSeparator)).setVisibility(0);
          ((Button)a.b(RedBoxDialog.this.mReportButton)).setEnabled(false);
          String str1 = (String)a.b(RedBoxDialog.this.mDevSupportManager.getLastErrorTitle());
          StackFrame[] arrayOfStackFrame = (StackFrame[])a.b(RedBoxDialog.this.mDevSupportManager.getLastErrorStack());
          String str2 = RedBoxDialog.this.mDevSupportManager.getSourceUrl();
          RedBoxDialog.this.mRedBoxHandler.reportRedbox(param1View.getContext(), str1, arrayOfStackFrame, str2, (RedBoxHandler.ReportCompletedListener)a.b(RedBoxDialog.this.mReportCompletedListener));
        } 
      }
    };
  
  public RedBoxHandler.ReportCompletedListener mReportCompletedListener = new RedBoxHandler.ReportCompletedListener() {
      public void onReportError(SpannedString param1SpannedString) {
        RedBoxDialog redBoxDialog = RedBoxDialog.this;
        redBoxDialog.isReporting = false;
        ((Button)a.b(redBoxDialog.mReportButton)).setEnabled(true);
        ((ProgressBar)a.b(RedBoxDialog.this.mLoadingIndicator)).setVisibility(8);
        ((TextView)a.b(RedBoxDialog.this.mReportTextView)).setText((CharSequence)param1SpannedString);
      }
      
      public void onReportSuccess(SpannedString param1SpannedString) {
        RedBoxDialog redBoxDialog = RedBoxDialog.this;
        redBoxDialog.isReporting = false;
        ((Button)a.b(redBoxDialog.mReportButton)).setEnabled(true);
        ((ProgressBar)a.b(RedBoxDialog.this.mLoadingIndicator)).setVisibility(8);
        ((TextView)a.b(RedBoxDialog.this.mReportTextView)).setText((CharSequence)param1SpannedString);
      }
    };
  
  public TextView mReportTextView;
  
  private ListView mStackView;
  
  protected RedBoxDialog(Context paramContext, DevSupportManager paramDevSupportManager, RedBoxHandler paramRedBoxHandler) {
    super(paramContext, 1980170253);
    requestWindowFeature(1);
    setContentView(1980039172);
    this.mDevSupportManager = paramDevSupportManager;
    this.mDoubleTapReloadRecognizer = new DoubleTapReloadRecognizer();
    this.mRedBoxHandler = paramRedBoxHandler;
    this.mStackView = (ListView)findViewById(1979973669);
    this.mStackView.setOnItemClickListener(this);
    this.mReloadJsButton = (Button)findViewById(1979973666);
    this.mReloadJsButton.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            RedBoxDialog.this.mDevSupportManager.handleReloadJS();
          }
        });
    this.mDismissButton = (Button)findViewById(1979973663);
    this.mDismissButton.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            RedBoxDialog.this.dismiss();
          }
        });
    this.mCopyToClipboardButton = (Button)findViewById(1979973662);
    this.mCopyToClipboardButton.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            String str = RedBoxDialog.this.mDevSupportManager.getLastErrorTitle();
            StackFrame[] arrayOfStackFrame = RedBoxDialog.this.mDevSupportManager.getLastErrorStack();
            a.b(str);
            a.b(arrayOfStackFrame);
            (new RedBoxDialog.CopyToHostClipBoardTask(RedBoxDialog.this.mDevSupportManager)).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Object[])new String[] { StackTraceHelper.formatStackTrace(str, arrayOfStackFrame) });
          }
        });
    RedBoxHandler redBoxHandler = this.mRedBoxHandler;
    if (redBoxHandler != null && redBoxHandler.isReportEnabled()) {
      this.mLoadingIndicator = (ProgressBar)findViewById(1979973665);
      this.mLineSeparator = findViewById(1979973664);
      this.mReportTextView = (TextView)findViewById(1979973668);
      this.mReportTextView.setMovementMethod(LinkMovementMethod.getInstance());
      this.mReportTextView.setHighlightColor(0);
      this.mReportButton = (Button)findViewById(1979973667);
      this.mReportButton.setOnClickListener(this.mReportButtonOnClickListener);
    } 
  }
  
  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong) {
    (new OpenStackFrameTask(this.mDevSupportManager)).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Object[])new StackFrame[] { (StackFrame)this.mStackView.getAdapter().getItem(paramInt) });
  }
  
  public boolean onKeyUp(int paramInt, KeyEvent paramKeyEvent) {
    if (paramInt == 82) {
      this.mDevSupportManager.showDevOptionsDialog();
      return true;
    } 
    if (this.mDoubleTapReloadRecognizer.didDoubleTapR(paramInt, getCurrentFocus()))
      this.mDevSupportManager.handleReloadJS(); 
    return super.onKeyUp(paramInt, paramKeyEvent);
  }
  
  public void resetReporting() {
    RedBoxHandler redBoxHandler = this.mRedBoxHandler;
    if (redBoxHandler != null) {
      if (!redBoxHandler.isReportEnabled())
        return; 
      this.isReporting = false;
      ((TextView)a.b(this.mReportTextView)).setVisibility(8);
      ((ProgressBar)a.b(this.mLoadingIndicator)).setVisibility(8);
      ((View)a.b(this.mLineSeparator)).setVisibility(8);
      ((Button)a.b(this.mReportButton)).setVisibility(0);
      ((Button)a.b(this.mReportButton)).setEnabled(true);
    } 
  }
  
  public void setExceptionDetails(String paramString, StackFrame[] paramArrayOfStackFrame) {
    this.mStackView.setAdapter((ListAdapter)new StackAdapter(paramString, paramArrayOfStackFrame));
  }
  
  static class CopyToHostClipBoardTask extends AsyncTask<String, Void, Void> {
    private final DevSupportManager mDevSupportManager;
    
    private CopyToHostClipBoardTask(DevSupportManager param1DevSupportManager) {
      this.mDevSupportManager = param1DevSupportManager;
    }
    
    protected Void doInBackground(String... param1VarArgs) {
      try {
        String str = Uri.parse(this.mDevSupportManager.getSourceUrl()).buildUpon().path("/copy-to-clipboard").query(null).build().toString();
        int j = param1VarArgs.length;
        for (int i = 0; i < j; i++) {
          String str1 = param1VarArgs[i];
          y y = new y();
          ad ad = ad.create(null, str1);
          y.a((new ac.a()).a(str).a(ad).c()).b();
        } 
      } catch (Exception exception) {
        a.c("ReactNative", "Could not copy to the host clipboard", exception);
      } 
      return null;
    }
  }
  
  static class OpenStackFrameTask extends AsyncTask<StackFrame, Void, Void> {
    private static final w JSON = w.a("application/json; charset=utf-8");
    
    private final DevSupportManager mDevSupportManager;
    
    private OpenStackFrameTask(DevSupportManager param1DevSupportManager) {
      this.mDevSupportManager = param1DevSupportManager;
    }
    
    private static JSONObject stackFrameToJson(StackFrame param1StackFrame) {
      return new JSONObject(MapBuilder.of("file", param1StackFrame.getFile(), "methodName", param1StackFrame.getMethod(), "lineNumber", Integer.valueOf(param1StackFrame.getLine()), "column", Integer.valueOf(param1StackFrame.getColumn())));
    }
    
    protected Void doInBackground(StackFrame... param1VarArgs) {
      try {
        String str = Uri.parse(this.mDevSupportManager.getSourceUrl()).buildUpon().path("/open-stack-frame").query(null).build().toString();
        y y = new y();
        int j = param1VarArgs.length;
        for (int i = 0; i < j; i++) {
          String str1 = stackFrameToJson(param1VarArgs[i]).toString();
          ad ad = ad.create(JSON, str1);
          y.a((new ac.a()).a(str).a(ad).c()).b();
        } 
      } catch (Exception exception) {
        a.c("ReactNative", "Could not open stack frame", exception);
      } 
      return null;
    }
  }
  
  static class StackAdapter extends BaseAdapter {
    private final StackFrame[] mStack;
    
    private final String mTitle;
    
    public StackAdapter(String param1String, StackFrame[] param1ArrayOfStackFrame) {
      this.mTitle = param1String;
      this.mStack = param1ArrayOfStackFrame;
    }
    
    public boolean areAllItemsEnabled() {
      return false;
    }
    
    public int getCount() {
      return this.mStack.length + 1;
    }
    
    public Object getItem(int param1Int) {
      return (param1Int == 0) ? this.mTitle : this.mStack[param1Int - 1];
    }
    
    public long getItemId(int param1Int) {
      return param1Int;
    }
    
    public int getItemViewType(int param1Int) {
      return (param1Int == 0) ? 0 : 1;
    }
    
    public View getView(int param1Int, View param1View, ViewGroup param1ViewGroup) {
      TextView textView1;
      View view;
      if (param1Int == 0) {
        if (param1View != null) {
          textView1 = (TextView)param1View;
        } else {
          textView1 = (TextView)LayoutInflater.from(param1ViewGroup.getContext()).inflate(1980039171, param1ViewGroup, false);
        } 
        textView1.setText(this.mTitle);
        return (View)textView1;
      } 
      TextView textView2 = textView1;
      if (textView1 == null) {
        view = LayoutInflater.from(param1ViewGroup.getContext()).inflate(1980039170, param1ViewGroup, false);
        view.setTag(new FrameViewHolder(view));
      } 
      StackFrame stackFrame = this.mStack[param1Int - 1];
      FrameViewHolder frameViewHolder = (FrameViewHolder)view.getTag();
      frameViewHolder.mMethodView.setText(stackFrame.getMethod());
      frameViewHolder.mFileView.setText(StackTraceHelper.formatFrameSource(stackFrame));
      return view;
    }
    
    public int getViewTypeCount() {
      return 2;
    }
    
    public boolean isEnabled(int param1Int) {
      return (param1Int > 0);
    }
    
    static class FrameViewHolder {
      public final TextView mFileView;
      
      public final TextView mMethodView;
      
      private FrameViewHolder(View param2View) {
        this.mMethodView = (TextView)param2View.findViewById(1979973661);
        this.mFileView = (TextView)param2View.findViewById(1979973660);
      }
    }
  }
  
  static class FrameViewHolder {
    public final TextView mFileView;
    
    public final TextView mMethodView;
    
    private FrameViewHolder(View param1View) {
      this.mMethodView = (TextView)param1View.findViewById(1979973661);
      this.mFileView = (TextView)param1View.findViewById(1979973660);
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\devsupport\RedBoxDialog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */