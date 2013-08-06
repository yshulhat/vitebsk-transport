package com.spax.vitebsktransport.repository;

import static com.spax.vitebsktransport.Constants.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.spax.vitebsktransport.domain.Departure;

import java.util.ArrayList;
import java.util.List;

public class DepartureRepository extends AbstractRepository<Departure> {
    private static final String   TABLE = "departures";
    private static final String[] COLUMNS = { "_id", "day", "time", "from_stop_id", "to_stop_id", "direction_id" };

    public DepartureRepository(Context context) {
        super(context, TABLE);
    }

    public List<Departure> getAll(long directionId, String day) {
        Log.i(TAG, "Getting all departures for direction [" + directionId + "] and day [" + day + "]");
        List<Departure> result = new ArrayList<Departure>();
        Cursor cursor = getDb().query(true, TABLE, getAllColumns(), "direction_id = ? and day = ?",
                new String[] {Long.toString(directionId), day}, null, null, "`time`", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Departure obj = cursorToDomain(cursor);
            result.add(obj);
            cursor.moveToNext();
        }
        cursor.close();
        Log.i(TAG, "Got [" + result.size() + "] departures for direction [" + directionId + "] and day [" + day + "]");
        return result;
    }

    public List<String> getDaysForDirection(long directionId) {
        Log.i(TAG, "Getting days for direction [" + directionId + "]");
        List<String> result = new ArrayList<String>();
        Cursor cursor = getDb().query(true, TABLE, new String[] {"day"}, "direction_id = ?",
                new String[] {Long.toString(directionId)}, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            result.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        Log.i(TAG, "Got [" + result.size() + "] day(s) for direction [" + directionId + "]");
        return result;
    }

    @Override
    protected Departure cursorToDomain(Cursor cursor) {
        Departure d = new Departure();
        d.setId(cursor.getLong(0));
        d.setDay(cursor.getString(1));
        d.setTime(cursor.getString(2));
        d.setFromStopId(cursor.getLong(3));
        d.setToStopId(cursor.getLong(4));
        d.setDirectionId(cursor.getLong(5));
        return d;
    }

    @Override
    protected String[] getAllColumns() {
        return COLUMNS;
    }

    @Override
    protected ContentValues fillValues(Departure obj) {
        ContentValues values = new ContentValues();
        values.put("day", obj.getDay());
        values.put("time", obj.getTime());
        values.put("from_stop_id", obj.getFromStopId());
        values.put("to_stop_id", obj.getToStopId());
        values.put("direction_id", obj.getDirectionId());
        return values;
    }
}
