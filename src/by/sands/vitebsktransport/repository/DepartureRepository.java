package by.sands.vitebsktransport.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import by.sands.vitebsktransport.domain.Departure;

import java.util.List;

public class DepartureRepository extends AbstractRepository<Departure> {
    private static final String   TABLE = "departures";
    private static final String[] COLUMNS = { "_id", "day", "time", "from_stop_id", "to_stop_id", "direction_id" };

    public DepartureRepository(Context context) {
        super(context, TABLE);
    }

    public List<Departure> getAll(long directionId) {
        Log.i(null, "Getting all departures for direction [" + directionId + "]");
        List<Departure> result = getAll("direction_id = ?", Long.toString(directionId));
        Log.i(null, "Got [" + result.size() + "] departures for direction [" + directionId + "]");
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
