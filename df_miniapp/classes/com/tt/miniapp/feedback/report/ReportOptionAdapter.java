package com.tt.miniapp.feedback.report;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.tt.miniapphost.util.UIUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class ReportOptionAdapter extends RecyclerView.a<ReportOptionAdapter.ReportOptionViewHolder> {
  public List<ItemVO> mData;
  
  public OnOptionClickListener mListener;
  
  ReportOptionAdapter(OnOptionClickListener paramOnOptionClickListener) {
    this.mListener = paramOnOptionClickListener;
  }
  
  public int getItemCount() {
    List<ItemVO> list = this.mData;
    return (list == null) ? 0 : list.size();
  }
  
  ItemVO getSelectItem() {
    List<ItemVO> list = this.mData;
    if (list == null || list.isEmpty())
      return new ItemVO(-1, ""); 
    for (ItemVO itemVO : this.mData) {
      if (itemVO.selected)
        return itemVO; 
    } 
    return new ItemVO(-1, "");
  }
  
  public void onBindViewHolder(ReportOptionViewHolder paramReportOptionViewHolder, int paramInt) {
    ItemVO itemVO = this.mData.get(paramInt);
    paramReportOptionViewHolder.textView.setText(itemVO.text);
    if (ReportHelper.getInfringementType() == itemVO.type) {
      paramReportOptionViewHolder.imageView.setImageResource(2097479700);
      ((LinearLayout.LayoutParams)paramReportOptionViewHolder.imageView.getLayoutParams()).topMargin = (int)UIUtils.dip2Px(paramReportOptionViewHolder.imageView.getContext(), 2.0F);
      return;
    } 
    ImageView imageView = paramReportOptionViewHolder.imageView;
    if (itemVO.selected) {
      paramInt = 2097479701;
    } else {
      paramInt = 2097479702;
    } 
    imageView.setImageResource(paramInt);
    ((LinearLayout.LayoutParams)paramReportOptionViewHolder.imageView.getLayoutParams()).topMargin = 0;
  }
  
  public ReportOptionViewHolder onCreateViewHolder(ViewGroup paramViewGroup, int paramInt) {
    return new ReportOptionViewHolder(LayoutInflater.from(paramViewGroup.getContext()).inflate(2097676312, paramViewGroup, false));
  }
  
  void updateData(JSONArray paramJSONArray) {
    if (paramJSONArray != null) {
      if (paramJSONArray.length() < 0)
        return; 
      this.mData = new ArrayList<ItemVO>();
      for (int i = 0; i < paramJSONArray.length(); i++) {
        JSONObject jSONObject = paramJSONArray.optJSONObject(i);
        this.mData.add(new ItemVO(jSONObject.optInt("type"), jSONObject.optString("text")));
      } 
      notifyDataSetChanged();
    } 
  }
  
  static class ItemVO {
    boolean selected;
    
    String text;
    
    int type;
    
    ItemVO(int param1Int, String param1String) {
      this.type = param1Int;
      this.text = param1String;
    }
  }
  
  public static interface OnOptionClickListener {
    void onClick(ReportOptionAdapter.ItemVO param1ItemVO);
  }
  
  class ReportOptionViewHolder extends RecyclerView.v {
    ImageView imageView;
    
    TextView textView;
    
    ReportOptionViewHolder(View param1View) {
      super(param1View);
      param1View.setOnClickListener(new View.OnClickListener() {
            public void onClick(View param2View) {
              int i = ReportOptionAdapter.ReportOptionViewHolder.this.getLayoutPosition();
              ReportOptionAdapter.ItemVO itemVO = ReportOptionAdapter.this.mData.get(i);
              if (itemVO.selected || ReportHelper.getInfringementType() == itemVO.type) {
                if (ReportOptionAdapter.this.mListener != null)
                  ReportOptionAdapter.this.mListener.onClick(itemVO); 
                return;
              } 
              Iterator<ReportOptionAdapter.ItemVO> iterator = ReportOptionAdapter.this.mData.iterator();
              while (iterator.hasNext())
                ((ReportOptionAdapter.ItemVO)iterator.next()).selected = false; 
              itemVO.selected = true;
              if (ReportOptionAdapter.this.mListener != null)
                ReportOptionAdapter.this.mListener.onClick(itemVO); 
              ReportOptionAdapter.this.notifyDataSetChanged();
            }
          });
      this.imageView = (ImageView)param1View.findViewById(2097545316);
      this.textView = (TextView)param1View.findViewById(2097545424);
    }
  }
  
  class null implements View.OnClickListener {
    public void onClick(View param1View) {
      int i = this.this$1.getLayoutPosition();
      ReportOptionAdapter.ItemVO itemVO = ReportOptionAdapter.this.mData.get(i);
      if (itemVO.selected || ReportHelper.getInfringementType() == itemVO.type) {
        if (ReportOptionAdapter.this.mListener != null)
          ReportOptionAdapter.this.mListener.onClick(itemVO); 
        return;
      } 
      Iterator<ReportOptionAdapter.ItemVO> iterator = ReportOptionAdapter.this.mData.iterator();
      while (iterator.hasNext())
        ((ReportOptionAdapter.ItemVO)iterator.next()).selected = false; 
      itemVO.selected = true;
      if (ReportOptionAdapter.this.mListener != null)
        ReportOptionAdapter.this.mListener.onClick(itemVO); 
      ReportOptionAdapter.this.notifyDataSetChanged();
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\feedback\report\ReportOptionAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */