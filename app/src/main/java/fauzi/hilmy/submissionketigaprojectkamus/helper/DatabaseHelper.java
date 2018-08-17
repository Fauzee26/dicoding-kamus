package fauzi.hilmy.submissionketigaprojectkamus.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static fauzi.hilmy.submissionketigaprojectkamus.helper.KamusContract.EngToIndo.ARTI;
import static fauzi.hilmy.submissionketigaprojectkamus.helper.KamusContract.EngToIndo.KATA;
import static fauzi.hilmy.submissionketigaprojectkamus.helper.KamusContract.TABLE_NAME_ENG;
import static fauzi.hilmy.submissionketigaprojectkamus.helper.KamusContract.TABLE_NAME_IND;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static String DATABASE_NAME = "dbkamus";
    private static final int DATABASE_VERSION = 1;

    public static String CREATE_TABLE_ENG_TO_INDO = "create table " + TABLE_NAME_ENG +
            " (" + _ID + " integer primary key autoincrement, "
            + KATA + " text not null, "
            + ARTI + " text not null);";

    public static String CREATE_TABLE_INDO_TO_ENG = "create table " + TABLE_NAME_IND +
            " (" + _ID + " integer primary key autoincrement, "
            + KATA + " text not null, "
            + ARTI + " text not null);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_ENG_TO_INDO);
        db.execSQL(CREATE_TABLE_INDO_TO_ENG);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_ENG);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_IND);
        onCreate(db);
    }
}
