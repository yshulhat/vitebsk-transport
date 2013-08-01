package by.sands.vitebsktransport.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import by.sands.vitebsktransport.db.SQLiteHelper;
import by.sands.vitebsktransport.domain.DBObject;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractRepository<T extends DBObject> {
    private SQLiteDatabase database;
    private SQLiteHelper dbHelper;
    private String tableName;

    public AbstractRepository(Context context, String tableName) {
        dbHelper = new SQLiteHelper(context);
        this.tableName = tableName;
    }

    public SQLiteDatabase getDb() {
        return database;
    }

    public void open() throws SQLException {
        Log.i(null, "Opening DB helper");
        database = dbHelper.getWritableDatabase();
        Log.i(null, "Opened DB helper");
    }

    public void close() {
        Log.i(null, "Closing DB helper");
        dbHelper.close();
        Log.i(null, "Closed DB helper");
    }

    public T create(T obj) {
        ContentValues values = fillValues(obj);
        Log.i(null, "Creating in [" + tableName + "] object: " + obj);
        long id = database.insert(tableName, null, values);
        obj.setId(id);
        Log.i(null, "Created in [" + tableName + "] object: " + obj);
        return obj;
    }

    public void delete(long id) {
        Log.i(null, "Deleting from [" + tableName + "] with id: " + id);
        database.delete(tableName, "_id = " + id, null);
        Log.i(null, "Deleted from [" + tableName + "] with id: " + id);
    }

    public List<T> getAll() {
        Log.i(null, "Getting all from [" + tableName + "]");
        List<T> result = new ArrayList<T>();
        Cursor cursor = database.query(tableName, getAllColumns(), null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            T obj = cursorToDomain(cursor);
            result.add(obj);
            cursor.moveToNext();
        }
        cursor.close();
        Log.i(null, "Got [" + result.size() + "] items from [" + tableName + "]");
        return result;
    }

    public List<T> getAll(String selection, String... selectionArgs) {
        Log.i(null, "Getting from [" + tableName + "] with selection = [" + selection +
                "] and args = " + selectionArgs);
        List<T> result = new ArrayList<T>();
        Cursor cursor = database.query(tableName, getAllColumns(), selection, selectionArgs, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            T obj = cursorToDomain(cursor);
            result.add(obj);
            cursor.moveToNext();
        }
        cursor.close();
        Log.i(null, "Got [" + result.size() + "] items from [" + tableName + "]");
        return result;
    }

    protected abstract T cursorToDomain(Cursor cursor);

    protected abstract String[] getAllColumns();

    protected abstract ContentValues fillValues(T obj);
}
