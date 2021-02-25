package by.legan.android.firealert.data.dto;

import lombok.Data;
import lombok.ToString;

/**
 * Created by AndreyLS on 20.02.2017.
 */

@Data
@ToString
public class SMSEvent {
    String sms_text;
    String alert_text;
    Alert alert = SMSEvent.Alert.showAlertWindow;
    public enum Alert {
        none,
        showAlertWindow,
        showAlertWindow_SendAlertToServer,
        sendAlertToServer
    }
}
