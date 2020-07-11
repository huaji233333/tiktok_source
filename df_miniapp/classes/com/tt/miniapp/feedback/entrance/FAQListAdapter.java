package com.tt.miniapp.feedback.entrance;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.tt.miniapp.feedback.entrance.vo.FAQItemVO;
import java.util.ArrayList;
import java.util.List;

public class FAQListAdapter extends RecyclerView.a<FAQListAdapter.ViewHolder> {
  private Context mContext;
  
  private List<FAQItemVO> mFaqItemVOList;
  
  private int mNameTextColor;
  
  public OnItemClickListener mOnItemClickListener;
  
  public FAQListAdapter(Context paramContext) {
    this.mContext = paramContext;
    this.mFaqItemVOList = new ArrayList<FAQItemVO>();
    this.mNameTextColor = this.mContext.getResources().getColor(2097348667);
  }
  
  public int getItemCount() {
    return this.mFaqItemVOList.size();
  }
  
  public void onBindViewHolder(ViewHolder paramViewHolder, int paramInt) {
    FAQItemVO fAQItemVO = this.mFaqItemVOList.get(paramInt);
    if (fAQItemVO == null)
      return; 
    paramViewHolder.name.setText(fAQItemVO.getName());
    paramViewHolder.name.setTextColor(this.mNameTextColor);
    if (paramInt == this.mFaqItemVOList.size() - 1) {
      paramViewHolder.split.setVisibility(4);
    } else {
      paramViewHolder.split.setVisibility(0);
    } 
    paramViewHolder.itemView.setTag(fAQItemVO);
    paramViewHolder.itemView.setTag(2097545313, Integer.valueOf(paramInt));
    paramViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            if (FAQListAdapter.this.mOnItemClickListener != null)
              FAQListAdapter.this.mOnItemClickListener.onItemClick(param1View, ((Integer)param1View.getTag(2097545313)).intValue()); 
          }
        });
  }
  
  public ViewHolder onCreateViewHolder(ViewGroup paramViewGroup, int paramInt) {
    return new ViewHolder(LayoutInflater.from(this.mContext).inflate(2097676304, paramViewGroup, false));
  }
  
  public void setData(List<FAQItemVO> paramList) {
    if (paramList != null) {
      this.mFaqItemVOList.clear();
      this.mFaqItemVOList.addAll(paramList);
    } 
  }
  
  public void setNameTextColor(int paramInt) {
    this.mNameTextColor = this.mContext.getResources().getColor(paramInt);
  }
  
  public void setOnItemClickListener(OnItemClickListener paramOnItemClickListener) {
    this.mOnItemClickListener = paramOnItemClickListener;
  }
  
  public static interface OnItemClickListener {
    void onItemClick(View param1View, int param1Int);
  }
  
  static class ViewHolder extends RecyclerView.v {
    public TextView name;
    
    public View split;
    
    public ViewHolder(View param1View) {
      super(param1View);
      this.name = (TextView)param1View.findViewById(2097545479);
      this.split = param1View.findViewById(2097545465);
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\feedback\entrance\FAQListAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */