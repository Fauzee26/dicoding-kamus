package fauzi.hilmy.submissionketigaprojectkamus.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;

import fauzi.hilmy.submissionketigaprojectkamus.R;
import fauzi.hilmy.submissionketigaprojectkamus.model.KamusModel;

import static android.provider.BaseColumns._ID;
import static fauzi.hilmy.submissionketigaprojectkamus.helper.KamusContract.EngToIndo.ARTI;
import static fauzi.hilmy.submissionketigaprojectkamus.helper.KamusContract.EngToIndo.KATA;
import static fauzi.hilmy.submissionketigaprojectkamus.helper.KamusContract.TABLE_NAME_ENG;
import static fauzi.hilmy.submissionketigaprojectkamus.helper.KamusContract.TABLE_NAME_IND;

public class KamusHelper {
    private Context context;
    private DatabaseHelper databaseHelper;

    private SQLiteDatabase database;

    public KamusHelper(Context context) {
        this.context = context;
    }

    public KamusHelper open() throws SQLException {
        databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        databaseHelper.close();
    }

    public ArrayList<KamusModel> getDataByName(String search, String selection) {
        Cursor cursor;
        if (selection == "Eng") {
            cursor = database.query(TABLE_NAME_ENG, null, KATA + " LIKE ?", new String[]{search.trim() + "%"}, null, null, _ID + " ASC", null);
        } else {
            cursor = database.query(TABLE_NAME_IND, null, KATA + " LIKE ?", new String[]{search.trim() + "%"}, null, null, _ID + " ASC", null);
        }
        cursor.moveToFirst();

        ArrayList<KamusModel> arrayList = new ArrayList<>();
        KamusModel kamusModel;
        if (cursor.getCount() > 0) {
            do {
                kamusModel = new KamusModel();
                kamusModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                kamusModel.setKata(cursor.getString(cursor.getColumnIndexOrThrow(KATA)));
                kamusModel.setArti(cursor.getString(cursor.getColumnIndexOrThrow(ARTI)));
                arrayList.add(kamusModel);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public ArrayList<KamusModel> getAllData(String selection) {
        Cursor cursor;
        if (selection.equalsIgnoreCase("Eng")) {
            cursor = database.query(TABLE_NAME_ENG, null, null, null, null, null, _ID + " ASC", null);
        } else if (selection.equalsIgnoreCase("Ind")) {
            cursor = database.query(TABLE_NAME_IND, null, null, null, null, null, _ID + " ASC", null);
        } else {
            cursor = null;
        }

        assert cursor != null;
        cursor.moveToFirst();
        ArrayList<KamusModel> arrayList = new ArrayList<>();
        KamusModel kamusModel;
        if (cursor.getCount() > 0) {
            do {
                kamusModel = new KamusModel();
                kamusModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                kamusModel.setKata(cursor.getString(cursor.getColumnIndexOrThrow(KATA)));
                kamusModel.setArti(cursor.getString(cursor.getColumnIndexOrThrow(ARTI)));
                arrayList.add(kamusModel);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public void insertTransactionEng(KamusModel kamusModel) {

        String sql = "INSERT INTO " + TABLE_NAME_ENG + " (" + KATA + ", " + ARTI
                + ") VALUES (?, ?)";
        SQLiteStatement stmt = database.compileStatement(sql);
        stmt.bindString(1, kamusModel.getKata());
        stmt.bindString(2, kamusModel.getArti());
        stmt.execute();
        stmt.clearBindings();
    }

    public void insertTransactionInd(KamusModel kamusModel) {

        String sql = "INSERT INTO " + TABLE_NAME_IND + " (" + KATA + ", " + ARTI
                + ") VALUES (?, ?)";
        SQLiteStatement stmt = database.compileStatement(sql);
        stmt.bindString(1, kamusModel.getKata());
        stmt.bindString(2, kamusModel.getArti());
        stmt.execute();
        stmt.clearBindings();
    }

    public int update(KamusModel kamusModel, String selection) {
        String table = null;
        if (selection == "Eng") {
            table = TABLE_NAME_ENG;
        } else {
            table = TABLE_NAME_IND;
        }

        ContentValues args = new ContentValues();
        args.put(KATA, kamusModel.getKata());
        args.put(ARTI, kamusModel.getArti());
        return database.update(table, args, _ID + "= '" + kamusModel.getId() + "'", null);
    }

    public int delete(int id, String selection) {
        String table = null;
        if (selection == "Eng") {
            table = TABLE_NAME_ENG;
        } else {
            table = TABLE_NAME_IND;
        }

        return database.delete(table, _ID + " = '" + id + "'", null);
    }


    public void beginTransaction() {
        database.beginTransaction();
    }

    public void setTransactionSuccess() {
        database.setTransactionSuccessful();
    }

    public void endTransaction() {
        database.endTransaction();
    }


}
