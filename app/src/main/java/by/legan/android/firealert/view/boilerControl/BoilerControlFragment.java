package by.legan.android.firealert.view.boilerControl;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.enums.EPickType;
import com.vansuita.pickimage.listeners.IPickClick;

import java.io.FileNotFoundException;
import java.util.List;

import by.legan.android.firealert.R;
import by.legan.android.firealert.data.dto.SMSEvent;
import by.legan.android.firealert.data.model.Boiler;
import by.legan.android.firealert.databinding.FrBoilerControlBinding;
import by.legan.android.firealert.utils.AndroidImageUtils;
import java9.util.stream.Collectors;
import java9.util.stream.StreamSupport;

import static android.app.Activity.RESULT_OK;
import static by.legan.android.firealert.utils.AndroidImageUtils.getImageFromGallery;

/**
 * Created by AndreyLS on 19.02.2017.
 */

public class BoilerControlFragment extends Fragment {
    static final int IMAGE_REQUEST = 1;
    static final int IMAGE_REQUEST_GALLERY = 2;

    FrBoilerControlBinding binding;
    BoilerControlFragmentModel model;
    BoilerControlActivityModel model_activity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FrBoilerControlBinding.inflate(inflater, container, false);
        model_activity = new ViewModelProvider(requireActivity()).get(BoilerControlActivityModel.class);
        model = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication())).get(BoilerControlFragmentModel.class);
        binding.setModel(model);
        binding.setView(this);

        binding.tabHost.setup();
        TabHost.TabSpec tabSpec = binding.tabHost.newTabSpec("tag1");
        tabSpec.setContent(R.id.tab1);
        tabSpec.setIndicator("Параметры");
        binding.tabHost.addTab(tabSpec);
        tabSpec = binding.tabHost.newTabSpec("tag2");
        tabSpec.setContent(R.id.tab2);
        tabSpec.setIndicator("Оповещения");
        binding.tabHost.addTab(tabSpec);
        // это нужно будет менять в зависимости от того для чего мы вызвали активность
        binding.tabHost.setCurrentTab(0);

        binding.alertRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        model.setListAdapter(new SMSEventListAdapter(getViewLifecycleOwner(), value -> {
            List<SMSEvent> list =model.getAdapterData().getValue();
            list.remove((int)value);
            model.getAdapterData().setValue(list);
        }));
        binding.alertRecyclerView.setAdapter(model.getListAdapter());

        model_activity.getBoilerId().observe(getViewLifecycleOwner(), boilerId ->
                model.loadingBoiler(boilerId).observe(getViewLifecycleOwner(),
                        new Observer<Boiler>() {
                            @Override
                            public void onChanged(Boiler boiler) {
                                model.getAdapterData().setValue(boiler.getSmsEvents());
                            }
                        }));
        model.getAdapterData().observe(getViewLifecycleOwner(), new Observer<List<SMSEvent>>() {
            @Override
            public void onChanged(List<SMSEvent> smsEvents) {
                if (smsEvents != null) {
                    List<MutableLiveDataSMSEvent> result = StreamSupport.stream(smsEvents).map(event -> {
                        MutableLiveDataSMSEvent ldSMSEvent = new MutableLiveDataSMSEvent();
                        ldSMSEvent.setValue(event);
                        return ldSMSEvent;
                    }).collect(Collectors.toList());
                    updateSMSEventAdapterData(result);
                }
            }
        });
        return binding.getRoot();
    }

    public void onClickCreateSMSEvent(View view){
        model.addNewSMSEvent();
    }

    public void onClickSave(View view) {
        Log.d("SAVE", "SAVE");
        Boiler boiler = model.getBoiler().getValue();
        if (boiler != null) {
           boiler.setSmsEvents(model.getAdapterData().getValue());
           model.getService().saveBoiler(boiler);
        }
        getActivity().finish();
    }

    public void onClickCancel(View view){
        getActivity().finish();
    }

    private void updateSMSEventAdapterData(List<MutableLiveDataSMSEvent> liveSMSEvent){
        model.getListAdapter().setItems(liveSMSEvent);
        model.getListAdapter().notifyDataSetChanged();
        binding.invalidateAll();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_control, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_del_boiler:
                delBoiler();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void delBoiler() {
        model.getService().removeBoiler(model.getBoiler().getValue());
        getActivity().finish();
    }

    public void setImage(View view){
        @SuppressLint("WrongConstant") PickSetup setup = new PickSetup()
                .setTitle(getString(R.string.image_source))
                .setCancelText(getString(R.string.cancel))
                .setFlip(true)
                .setMaxSize(500)
                .setPickTypes(EPickType.GALLERY, EPickType.CAMERA)
                .setButtonOrientation(LinearLayoutCompat.HORIZONTAL)
                .setCameraButtonText(getString(R.string.camera_name_button))
                .setGalleryButtonText(getString(R.string.gallery_name_button))
                .setIconGravity(Gravity.LEFT)
                .setSystemDialog(false);
        PickImageDialog dialog = PickImageDialog.build(setup);
        dialog.setOnClick(new IPickClick() {
            @Override
            public void onGalleryClick() {
                dialog.dismiss();
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(photoPickerIntent, IMAGE_REQUEST_GALLERY);
            }

            @Override
            public void onCameraClick() {
                dialog.dismiss();
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
                    requestPermissions(new String[] {Manifest.permission.CAMERA}, IMAGE_REQUEST);
                } else getImageFromCamera();
            }
        }).show(getActivity());

    }

    private void getImageFromCamera(){
        Intent photoPickerIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(photoPickerIntent, IMAGE_REQUEST);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case IMAGE_REQUEST: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getImageFromCamera();
                } else {}
                return;
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case IMAGE_REQUEST_GALLERY:
                if(resultCode == RESULT_OK && null != data){
                    try {
                        byte[] img = getImageFromGallery(requireActivity(), data);
                        model.getBoiler().getValue().setImg(img);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case IMAGE_REQUEST:
                if(resultCode == RESULT_OK && null != data){
                    try {
                        Bitmap bmp = (Bitmap) data.getExtras().get("data");
                        byte[] img = AndroidImageUtils.compress(bmp);
                        bmp.recycle();
                        model.getBoiler().getValue().setImg(img);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
        model.getBoiler().setValue(model.getBoiler().getValue());
        binding.invalidateAll();
    }


    @Override
    public void onResume() {
        super.onResume();
//        model.loadingBoiler(model_activity.getBoilerId().getValue());
    }
}
