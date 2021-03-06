package com.baidu.android.trail.function;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baidu.android.trail.R;
import com.baidu.android.trail.activity.DetailActivity;
import com.baidu.android.trail.bean.Subject;
import com.baidu.android.trail.db.SubjectEntity;

import java.util.List;


public class CollectionAdapter extends RecyclerView.Adapter<CollectionAdapter.ItemViewHolder> {

  private List<Subject> questions;

  public void setQuestions(List<Subject> questions) {
    this.questions = questions;
    notifyDataSetChanged();
  }

  @Override

  public CollectionAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.item_question_simple, parent, false);
    return new ItemViewHolder(view);
  }

  @Override
  public void onBindViewHolder(ItemViewHolder holder, final int position) {
    holder.titleView.setText(questions.get(position).getQuestion());
    holder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        DetailActivity.launch(v.getContext(), questions.get(position));
      }
    });
  }

  @Override
  public int getItemCount() {
    return questions == null ? 0 : questions.size();
  }

  public static class ItemViewHolder extends RecyclerView.ViewHolder {
    private TextView titleView;

    public ItemViewHolder(View itemView) {
      super(itemView);
      titleView = itemView.findViewById(R.id.question_text);
    }
  }


}
