package fauzi.hilmy.submissionketigaprojectkamus.helper;

import android.provider.BaseColumns;

public class KamusContract {
    public static String TABLE_NAME_ENG = "table_eng_to_indo";
    public static String TABLE_NAME_IND = "table_ind_to_eng";

//    public static Boolean selLanguage = true;

    public static final class EngToIndo implements BaseColumns {

        public static String KATA = "kata";

        public static String ARTI = "arti";
    }
}
