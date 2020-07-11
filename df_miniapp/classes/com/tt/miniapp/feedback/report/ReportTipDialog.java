package com.tt.miniapp.feedback.report;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.tt.b.a;
import com.tt.b.c;
import com.tt.miniapp.game.health.dialog.AbsDialog;
import com.tt.miniapphost.entity.NativeUIParamsEntity;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.miniapphost.util.UIUtils;

public class ReportTipDialog extends AbsDialog {
  public int mCodeFrom;
  
  private ImageView mImageView;
  
  public String mImgUrl;
  
  private CharSequence mTitle;
  
  public static ReportTipDialog newInst(CharSequence paramCharSequence, String paramString, int paramInt) {
    ReportTipDialog reportTipDialog = new ReportTipDialog();
    Bundle bundle = new Bundle();
    bundle.putCharSequence("key_title", paramCharSequence);
    bundle.putByte("key_close_setting", (byte)3);
    bundle.putString("key_img_url", paramString);
    bundle.putInt("key_code_from", paramInt);
    reportTipDialog.setArguments(bundle);
    return reportTipDialog;
  }
  
  public void onActivityCreated(Bundle paramBundle) {
    super.onActivityCreated(paramBundle);
    View view = getView();
    if (view == null)
      return; 
    (view.getLayoutParams()).width = (int)UIUtils.dip2Px(view.getContext(), 290.0F);
  }
  
  public void onCreate(Bundle paramBundle) {
    this.mStyleResId = 2097807368;
    super.onCreate(paramBundle);
    paramBundle = getArguments();
    if (paramBundle == null)
      return; 
    this.mTitle = paramBundle.getCharSequence("key_title", "");
    this.mImgUrl = paramBundle.getString("key_img_url", "");
    this.mCodeFrom = paramBundle.getInt("key_code_from", -1);
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
    return paramLayoutInflater.inflate(2097676301, paramViewGroup, false);
  }
  
  public void onViewCreated(View paramView, Bundle paramBundle) {
    super.onViewCreated(paramView, paramBundle);
    ((TextView)paramView.findViewById(2097545493)).setText(this.mTitle);
    this.mImageView = (ImageView)paramView.findViewById(2097545229);
    HostDependManager.getInst().loadImage(this.mImageView.getContext(), (new c(this.mImgUrl)).a(new a() {
            public void onFail(Exception param1Exception) {
              String str1;
              int i = ReportTipDialog.this.mCodeFrom;
              String str2 = ReportTipDialog.this.mImgUrl;
              if (param1Exception == null) {
                str1 = "null";
              } else {
                str1 = str1.getMessage();
              } 
              ReportHelper.monitor(i, str2, str1);
            }
            
            public void onSuccess() {}
          }).a((View)this.mImageView));
    TextView textView = (TextView)paramView.findViewById(2097545476);
    textView.setTextColor(Color.parseColor(NativeUIParamsEntity.getInst().getPositiveTextColor()));
    textView.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            ReportTipDialog.this.dismissAllowingStateLoss();
          }
        });
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\feedback\report\ReportTipDialog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */