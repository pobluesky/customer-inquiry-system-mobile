package com.example.customer_inquiry_system_mobile;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import com.example.customer_inquiry_system_mobile.databinding.ItemRecyclerviewBinding;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.CustomViewHolder> {

    private ArrayList<Person> list;

    public RecyclerViewAdapter(ArrayList<Person> list) {
        this.list = list;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        private ItemRecyclerviewBinding binding;

        public CustomViewHolder(ItemRecyclerviewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Person item) {
            binding.ivProfile.setImageResource(item.getImg());
            binding.tvName.setText(item.getName());
            binding.tvPhoneNumber.setText(item.getPhoneNumber());
        }
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemRecyclerviewBinding binding = ItemRecyclerviewBinding.inflate(inflater, parent, false);
        return new CustomViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}