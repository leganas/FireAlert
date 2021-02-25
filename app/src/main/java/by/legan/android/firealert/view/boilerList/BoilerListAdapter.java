package by.legan.android.firealert.view.boilerList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;

import by.legan.android.firealert.R;
import by.legan.android.firealert.data.model.Boiler;
import by.legan.android.firealert.databinding.ListItemBinding;
import by.legan.android.firealert.utils.SupportRecyclerView.BaseListAdapter;
import by.legan.android.firealert.utils.SupportRecyclerView.BaseListHolder;
import by.legan.android.firealert.suport.LongResultCallback;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BoilerListAdapter extends BaseListAdapter<Boiler, BaseListHolder<Boiler>> {
    private LifecycleOwner lifecycleOwner;
    private LongResultCallback callbackAction;


    @Override
    public BaseListHolder<Boiler> getViewDataBinding(LayoutInflater inflater, ViewGroup parent) {
        ListItemBinding eventBinding = DataBindingUtil.inflate(inflater, R.layout.list_item, parent,false);
        return new BoilerListHolder(parent.getContext(),eventBinding);
    }

    public class BoilerListHolder extends BaseListHolder<Boiler> {

        public BoilerListHolder(Context context, ViewDataBinding binding) {
            super(context, binding);
        }

        @Override
        public void holderInitialisation() {
            ListItemBinding binding = (ListItemBinding) getBinding();
            binding.setHolder(this);
            binding.setBoiler(getItem());

        }

        public void onClickControl(View view){
            if (callbackAction != null) callbackAction.run(getItem().getId());
        }
    }


}
