package com.spax.vitebsktransport.repository;

import static com.spax.vitebsktransport.Constants.TAG;

import com.spax.vitebsktransport.domain.Stop;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class StopRepository extends AbstractRepository<Stop> {
    private static final String   TABLE = "stops";
    private static final String[] COLUMNS = { "_id", "name", "latitudeE6", "longitudeE6" };

    public StopRepository(Context context) {
        super(context, TABLE);
    }

    public List<Stop> getForDirection(long directionId) {
        Log.i(TAG, "Getting stops for direction [" + directionId + "]");
        List<Stop> result = new ArrayList<Stop>();
        Cursor cursor = getDb().query("move_times t inner join stops s on t.from_stop_id = s._id",
                new String[] {"s._id", "s.name", "s.latitudeE6", "s.longitudeE6"}, "t.direction_id = ?",
                new String[] {Long.toString(directionId)}, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Stop obj = cursorToDomain(cursor);
            result.add(obj);
            cursor.moveToNext();
        }
        cursor.close();
        Log.i(TAG, "Got [" + result.size() + "] stops for direction [" + directionId + "]");
        return result;
    }

    @Override
    protected Stop cursorToDomain(Cursor cursor) {
        Stop s = new Stop();
        s.setId(cursor.getLong(0));
        s.setName(cursor.getString(1));
        s.setLatitudeE6(cursor.getInt(2));
        s.setLongitudeE6(cursor.getInt(3));
        return s;
    }

    @Override
    protected String[] getAllColumns() {
        return COLUMNS;
    }

    @Override
    protected ContentValues fillValues(Stop obj) {
        ContentValues values = new ContentValues();
        values.put("name", obj.getName());
        values.put("latitudeE6", obj.getLatitudeE6());
        values.put("longitudeE6", obj.getLongitudeE6());
        return values;
    }
}
