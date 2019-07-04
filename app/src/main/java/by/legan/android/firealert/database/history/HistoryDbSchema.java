package by.legan.android.firealert.database.history;

/**
 * Created by AndreyLS on 21.02.2017.
 */

public class HistoryDbSchema {
    /**Внутренний класс с названием таблицы
     * и именами её полей*/
    public static final class HistoryTable {
        public static final String NAME = "history";

        public static final class Cols {
            public static final String _ID = "uuid";
            public static final String id_boiler = "id_boiler";
            public static final String data = "data";
            public static final String alert_name = "alert_name";
            public static final String alert_msg = "alert_msg";
        }
    }
}
