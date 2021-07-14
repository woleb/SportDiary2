package com.example.sportdiary2

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.example.sportdiary2.Entries as Entries1

val DATABASENAME = "SPORTDIARYDATABASE"
val DATABASEVERSION = 1
val TABLENAME = "entries"
public val COL_ID = "id"
public val COL_DATE = "date"
val COL_SPORT = "sport"
val COL_FEELING = "feeling"
val COL_NOTES = "notes"

class DataBaseHandler(var context: Context) : SQLiteOpenHelper(context, DATABASENAME, null,
    DATABASEVERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        //val createTable = "CREATE TABLE " + TABLENAME + " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COL_NAME + " VARCHAR(256)," + COL_AGE + " INTEGER)"
        //val CREATE_TABLE = "CREATE TABLE $TABLE_NAME " + "($ID Integer PRIMARY KEY, $FIRST_NAME TEXT, $LAST_NAME TEXT)"
        val createTable = "CREATE TABLE $TABLENAME " + "($COL_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                                                        " $COL_DATE VARCHAR(256), " +
                                                        " $COL_SPORT VARCHAR(256), " +
                                                        " $COL_FEELING VARCHAR(256), " +
                                                        " $COL_NOTES VARCHAR(1000))"
        db?.execSQL(createTable)
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //onCreate(db);
    }
    fun insertData(entries: Entries1) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        //contentValues.put(COL_ID, entries.id)
        contentValues.put(COL_DATE, entries.date)
        contentValues.put(COL_SPORT, entries.sport)
        contentValues.put(COL_FEELING, entries.feeling)
        contentValues.put(COL_NOTES, entries.notes)
        val result = database.insert(TABLENAME, null, contentValues)
        if (result == (0).toLong()) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        }
        else {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }
    }

    fun readData(): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLENAME", null)
    }


    /*
    fun readData(): MutableList<Entries1> {
        val db = this.readableDatabase
        val query = "Select * from $TABLENAME"
        val result = db.rawQuery(query, null)
        //var idnum: Int
        var date: String
        var sport: String
        var feeling: String
        var notes: String
        if (result.moveToFirst()) {
            do {
                //idnum = result.getString(result.getColumnIndex(COL_ID)).toInt()
                result.getColumnIndex(COL_DATE).toString().also { date = it }
                sport = result.getString(result.getColumnIndex(COL_SPORT))
                feeling = result.getString(result.getColumnIndex(COL_FEELING))
                notes = result.getString(result.getColumnIndex(COL_NOTES))
                Log.d("PRINT", date)
                var entries = Entries1(date.toString(), sport.toString(), feeling.toString(), notes.toString())
                list.add(entries)
            }
            while (result.moveToNext())
        }
        return list
    }
 */



}