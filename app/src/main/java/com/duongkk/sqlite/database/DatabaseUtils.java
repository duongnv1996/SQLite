package com.duongkk.sqlite.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.duongkk.sqlite.models.Student;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DuongKK on 10/5/2017.
 */

public class DatabaseUtils extends SQLiteOpenHelper {
    public final static String DB_NAME = "student.db";
    public final static int DB_VERSION = 1;
    public final static String TABLE_NAME = "t_student";
    public final static String TEXT = "text";
    public final static String COL_ID = "id";
    private SQLiteDatabase db;

    public DatabaseUtils(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

    }
    public boolean add(String text)
    {
        db=this.getWritableDatabase();
        boolean res=true;
        ContentValues cv=new ContentValues();
        cv.put(TEXT,text);
        long r= db.insert(TABLE_NAME,null,cv);
        if(r==-1)res=false;
        db.close();
        return res;
    }
    public void delete(int id){
        SQLiteDatabase db =this.getWritableDatabase();
        db.delete(TABLE_NAME, COL_ID + " = ?",
                new String[] { String.valueOf(id) });
        db.close();
    }

    public int updateContact(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TEXT, student.getText());
        return db.update(TABLE_NAME, values, COL_ID + " = ?",
                new String[] { String.valueOf(student.getId()) });
    }

    public List<Student> getAllContacts() {
        List<Student> contactList = new ArrayList<Student>();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Student contact = new Student();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setText(cursor.getString(1));
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
        return contactList;
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + TEXT + " TEXT " + ")");
        db = sqLiteDatabase;
        Log.d("DBBBBBB","DB created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        this.onCreate(db);
    }
}
