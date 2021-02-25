package by.legan.android.firealert.data.model;

import androidx.databinding.BaseObservable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Элемент истории
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class HistoryItem extends BaseObservable {

    @PrimaryKey(autoGenerate = true)
    private Long Id;
    private String date_alert;
    private String alertName;
    private String alertMessage;
}
