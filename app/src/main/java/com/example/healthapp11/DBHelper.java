package com.example.healthapp11;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "HealthApp.db";
    private static final int DATABASE_VERSION = 4;  // Update version

    private static final String TABLE_USERS = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";

    private static final String TABLE_RECORDS = "records";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_EXERCISE_TYPE = "exercise_type";
    private static final String COLUMN_EXERCISE_NAME = "exercise_name";
    private static final String COLUMN_WEIGHT = "weight";
    private static final String COLUMN_SETS = "sets";
    private static final String COLUMN_REPS = "reps";
    private static final String COLUMN_DURATION = "duration";
    private static final String COLUMN_DISTANCE = "distance";
    private static final String COLUMN_CALORIES = "calories";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_EMAIL + " TEXT UNIQUE,"
                + COLUMN_PASSWORD + " TEXT" + ")";
        db.execSQL(CREATE_USERS_TABLE);

        String CREATE_RECORDS_TABLE = "CREATE TABLE " + TABLE_RECORDS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_DATE + " TEXT,"
                + COLUMN_EXERCISE_TYPE + " TEXT,"
                + COLUMN_EXERCISE_NAME + " TEXT,"
                + COLUMN_WEIGHT + " REAL,"
                + COLUMN_SETS + " INTEGER,"
                + COLUMN_REPS + " INTEGER,"
                + COLUMN_DURATION + " INTEGER,"
                + COLUMN_DISTANCE + " REAL,"
                + COLUMN_CALORIES + " REAL"
                + ")";
        db.execSQL(CREATE_RECORDS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 3) {
            String ALTER_TABLE_ADD_DURATION = "ALTER TABLE " + TABLE_RECORDS + " ADD COLUMN " + COLUMN_DURATION + " INTEGER";
            db.execSQL(ALTER_TABLE_ADD_DURATION);
        }

        if (oldVersion < 4) { // 버전 4로 올리고, distance와 calories 열 추가
            String ALTER_TABLE_ADD_DISTANCE = "ALTER TABLE " + TABLE_RECORDS + " ADD COLUMN " + COLUMN_DISTANCE + " REAL";
            String ALTER_TABLE_ADD_CALORIES = "ALTER TABLE " + TABLE_RECORDS + " ADD COLUMN " + COLUMN_CALORIES + " REAL";
            db.execSQL(ALTER_TABLE_ADD_DISTANCE);
            db.execSQL(ALTER_TABLE_ADD_CALORIES);
        }
    }

    // 사용자 추가 메소드
    public boolean addUser(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        String hashedPassword = hashPassword(password); // 비밀번호 해시화 함수 사용
        ContentValues values = new ContentValues();
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASSWORD, hashedPassword);
        long result = db.insert(TABLE_USERS, null, values);
        return result != -1;
    }

    public boolean checkUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String hashedPassword = hashPassword(password); // 비밀번호 해시화 함수 사용
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE email = ? AND password = ?", new String[]{email, hashedPassword});
        boolean userExists = cursor.getCount() > 0;
        cursor.close();
        return userExists;
    }
    private String hashPassword(String password) {
        return Integer.toString(password.hashCode());
    }
    // 운동 기록 삽입 메소드
    public boolean insertRecord(String date, String exerciseType, String exerciseName, float weight, int sets, int reps, int duration, float distance, float calories) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUMN_DATE, date);
            values.put(COLUMN_EXERCISE_TYPE, exerciseType);
            values.put(COLUMN_EXERCISE_NAME, exerciseName);
            values.put(COLUMN_WEIGHT, weight);
            values.put(COLUMN_SETS, sets);
            values.put(COLUMN_REPS, reps);
            values.put(COLUMN_DURATION, duration);
            values.put(COLUMN_DISTANCE, distance);
            values.put(COLUMN_CALORIES, calories);

            long result = db.insert(TABLE_RECORDS, null, values);
            Log.i("db", result + "");
            return result != -1;
        } catch (Exception e) {
            Log.e("DBHelper", "Error inserting record", e);
            return false;
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }
    // 날짜별 기록 조회 메소드
    @SuppressLint("Range")
    public ArrayList<HashMap<String, String>> getRecordsByDate(String date) {
        ArrayList<HashMap<String, String>> recordsList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            String query = "SELECT * FROM " + TABLE_RECORDS + " WHERE " + COLUMN_DATE + "=?";
            cursor = db.rawQuery(query, new String[]{date});

            if (cursor.moveToFirst()) {
                do {
                    HashMap<String, String> record = new HashMap<>();
                    record.put("exerciseType", cursor.getString(cursor.getColumnIndex(COLUMN_EXERCISE_TYPE)));
                    record.put("exerciseName", cursor.getString(cursor.getColumnIndex(COLUMN_EXERCISE_NAME)));
                    record.put("weight", String.valueOf(cursor.getFloat(cursor.getColumnIndex(COLUMN_WEIGHT))));
                    record.put("sets", String.valueOf(cursor.getInt(cursor.getColumnIndex(COLUMN_SETS))));
                    record.put("reps", String.valueOf(cursor.getInt(cursor.getColumnIndex(COLUMN_REPS))));
                    record.put("duration", String.valueOf(cursor.getInt(cursor.getColumnIndex(COLUMN_DURATION))));
                    record.put("distance", String.valueOf(cursor.getFloat(cursor.getColumnIndex(COLUMN_DISTANCE))));
                    record.put("calories", String.valueOf(cursor.getFloat(cursor.getColumnIndex(COLUMN_CALORIES))));
                    recordsList.add(record);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("DBHelper", "Error retrieving records", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return recordsList;
    }
}