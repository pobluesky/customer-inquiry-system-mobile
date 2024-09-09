package com.example.customer_inquiry_system_mobile.domain.inquiry.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HeaderPagerAdapter extends RecyclerView.Adapter<HeaderPagerAdapter.HeaderViewHolder> {

    private final List<Integer> headerLayouts;

    public HeaderPagerAdapter(List<Integer> headerLayouts) {
        this.headerLayouts = headerLayouts;
    }

    @NonNull
    @Override
    public HeaderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(headerLayouts.get(viewType), parent, false);
        return new HeaderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HeaderViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return headerLayouts.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    static class HeaderViewHolder extends RecyclerView.ViewHolder {
        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}


