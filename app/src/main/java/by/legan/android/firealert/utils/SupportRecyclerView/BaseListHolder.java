package by.legan.android.firealert.utils.SupportRecyclerView;

import android.content.Context;

import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseListHolder<T> extends RecyclerView.ViewHolder {
    private T item;
    private Context context;
    private Integer pos;
    public ViewDataBinding binding;

    public BaseListHolder(Context context, ViewDataBinding binding) {
        super(binding.getRoot());
        this.context = context;
        this.binding = binding;
    }

    public void setItem(T item) {
        setItem(item,-1);
    }

    public void setItem(T item, Integer position) {
        this.item = item;
        this.pos = position;
        holderInitialisation();
    }

    public abstract void holderInitialisation();
}
