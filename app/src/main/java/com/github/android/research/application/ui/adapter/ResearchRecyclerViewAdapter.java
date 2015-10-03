package com.github.android.research.application.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.android.research.R;
import com.github.android.research.domain.model.Research;

import java.util.List;


public class ResearchRecyclerViewAdapter extends RecyclerView.Adapter<ResearchRecyclerViewAdapter.ViewHolder> {

    TypedValue typedValue = new TypedValue();

    Context context;
    List<Research> researches;

    OnItemClickListener onItemClickListener;

    public ResearchRecyclerViewAdapter(Context context, List<Research> researches) {
        this.context = context;
        this.researches = researches;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_2, parent, false);
        view.setBackgroundResource(typedValue.resourceId);
        return new ViewHolder(view);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {


        final Research research = researches.get(position);

        holder.textViewName.setText(research.getName());
        holder.textViewDescription.setText(research.getDescription());
        holder.view.setBackgroundResource(R.drawable.btn_flat_selector);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(research);
            }
        });
    }

    @Override
    public int getItemCount() {
        return researches.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View view;
        public final TextView textViewName;
        public final TextView textViewDescription;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            this.textViewName = (TextView) view.findViewById(android.R.id.text1);
            this.textViewDescription = (TextView) view.findViewById(android.R.id.text2);
            this.textViewDescription.setTextColor(view.getResources().getColor(R.color.secondary_text));
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Research research);
    }
}
