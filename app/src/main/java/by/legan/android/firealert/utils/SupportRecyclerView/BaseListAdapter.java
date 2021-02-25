package by.legan.android.firealert.utils.SupportRecyclerView;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class BaseListAdapter<L, H extends BaseListHolder<L>> extends RecyclerView.Adapter<H>{
    private List<L> items;

    @NonNull
    @Override
    public H onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return getViewDataBinding(LayoutInflater.from(parent.getContext()),parent);
    }

    public abstract H getViewDataBinding(LayoutInflater inflater, ViewGroup parent);

    @Override
    public void onBindViewHolder(@NonNull H holder, int position) {
        holder.setItem(items.get(position), position);
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }


}
