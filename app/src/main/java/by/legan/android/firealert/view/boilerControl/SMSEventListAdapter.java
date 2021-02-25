package by.legan.android.firealert.view.boilerControl;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import by.legan.android.firealert.R;
import by.legan.android.firealert.data.dto.SMSEvent;
import by.legan.android.firealert.databinding.SmsEventBinding;
import by.legan.android.firealert.suport.IntegerResultCallback;
import by.legan.android.firealert.utils.SupportRecyclerView.BaseListAdapter;
import by.legan.android.firealert.utils.SupportRecyclerView.BaseListHolder;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SMSEventListAdapter extends BaseListAdapter<MutableLiveDataSMSEvent, BaseListHolder<MutableLiveDataSMSEvent>> {
    private LifecycleOwner lifecycleOwner;
    private IntegerResultCallback callback;
    @Override
    public BaseListHolder<MutableLiveDataSMSEvent> getViewDataBinding(LayoutInflater inflater, ViewGroup parent) {
        SmsEventBinding eventBinding = DataBindingUtil.inflate(inflater, R.layout.sms_event, parent,false);
        return new SMSEventListHolder(parent.getContext(),eventBinding);
    }

    public class SMSEventListHolder extends BaseListHolder<MutableLiveDataSMSEvent> {

        public SMSEventListHolder(Context context, ViewDataBinding binding) {
            super(context, binding);
        }

        @Override
        public void holderInitialisation() {
            SmsEventBinding binding = (SmsEventBinding) getBinding();
            binding.setHolder(this);
            binding.setModel(getItem());
        }

        public void onClickDelSMSEvent(View view){
            if (callback!= null) callback.run(getPos());
        }
    }

}
