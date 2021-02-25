package by.legan.android.firealert.view.historyList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import by.legan.android.firealert.R;
import by.legan.android.firealert.data.model.Boiler;
import by.legan.android.firealert.data.model.HistoryItem;
import by.legan.android.firealert.databinding.HistoryItemBinding;
import by.legan.android.firealert.databinding.ListItemBinding;
import by.legan.android.firealert.utils.SupportRecyclerView.BaseListAdapter;
import by.legan.android.firealert.utils.SupportRecyclerView.BaseListHolder;

public class HistoryListAdapter extends BaseListAdapter<HistoryItem, BaseListHolder<HistoryItem>> {
    @Override
    public BaseListHolder<HistoryItem> getViewDataBinding(LayoutInflater inflater, ViewGroup parent) {
        HistoryItemBinding eventBinding = DataBindingUtil.inflate(inflater, R.layout.history_item, parent,false);
        return new HistoryListHolder(parent.getContext(),eventBinding);
    }

    public class HistoryListHolder extends BaseListHolder<HistoryItem> {

        public HistoryListHolder(Context context, ViewDataBinding binding) {
            super(context, binding);
        }

        @Override
        public void holderInitialisation() {
            HistoryItemBinding binding = (HistoryItemBinding) getBinding();
            binding.setHolder(this);
            binding.setModel(getItem());
        }
    }

}
