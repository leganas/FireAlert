package by.legan.android.firealert.data.model;

import androidx.databinding.BaseObservable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.List;

import by.legan.android.firealert.data.converter.JSONConverter;
import by.legan.android.firealert.data.dto.SMSEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Класс описывающий котельную
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Boiler extends BaseObservable {
    @PrimaryKey(autoGenerate = true)
    private Long Id;
    private String name;
    private String phone_number_stationary;
    private String phone_number_mobile;
    private String alert_number;
    private String address;
    private String master_name;
    @TypeConverters({JSONConverter.class})
    private List<SMSEvent>smsEvents;
    private byte[] img; // Картинка
}
