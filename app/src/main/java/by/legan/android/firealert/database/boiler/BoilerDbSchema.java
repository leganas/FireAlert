package by.legan.android.firealert.database.boiler;

/**
 * Класс описывающие базу данных для хранения списка котельных
 */
public class BoilerDbSchema {
    /**Внутренний класс с названием таблицы
     * и именами её полей*/
    public static final class BoilerTable {
        public static final String NAME = "boiler";

        public static final class Cols {
            public static final String _ID = "uuid";
            public static final String NAME = "name";
            public static final String PHONE_NUMBER_STATIONARY = "phone";
            public static final String PHONE_NUMBER_MOBILE = "phone_m";
            public static final String ALERT_NUMBER = "alert_number";
            public static final String ADDRESS = "address";
            public static final String JSON_ITEM = "json_item";
        }
    }


}
