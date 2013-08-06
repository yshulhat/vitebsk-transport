package com.spax.vitebsktransport.repository;

import static com.spax.vitebsktransport.Constants.TAG;

import com.spax.vitebsktransport.domain.MoveTime;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MoveTimeRepository extends AbstractRepository<MoveTime> {
    private static final String   TABLE = "move_times";
    private static final String[] COLUMNS = { "_id", "time", "from_stop_id", "to_stop_id", "direction_id" };

    public MoveTimeRepository(Context context) {
        super(context, TABLE);
    }

    public List<MoveTime> getAll(long directionId) {
        Log.i(TAG, "Getting all move times for direction [" + directionId + "]");
        List<MoveTime> result = new ArrayList<MoveTime>();
        Cursor cursor = getDb().query(true, TABLE, getAllColumns(), "direction_id = ?",
                new String[] {Long.toString(directionId)}, null, null, "`order`", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            MoveTime obj = cursorToDomain(cursor);
            result.add(obj);
            cursor.moveToNext();
        }
        cursor.close();
        Log.i(TAG, "Got [" + result.size() + "] move times for direction [" + directionId + "]");
        return result;
    }

    @Override
    protected MoveTime cursorToDomain(Cursor cursor) {
        MoveTime d = new MoveTime();
        d.setId(cursor.getLong(0));
        d.setTime(cursor.getInt(1));
        d.setFromStopId(cursor.getLong(2));
        d.setToStopId(cursor.getLong(3));
        d.setDirectionId(cursor.getLong(4));
        return d;
    }

    @Override
    protected String[] getAllColumns() {
        return COLUMNS;
    }

    @Override
    protected ContentValues fillValues(MoveTime obj) {
        ContentValues values = new ContentValues();
        values.put("time", obj.getTime());
        values.put("from_stop_id", obj.getFromStopId());
        values.put("to_stop_id", obj.getToStopId());
        values.put("direction_id", obj.getDirectionId());
        return values;
    }
}
