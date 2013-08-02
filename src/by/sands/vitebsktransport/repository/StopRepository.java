package by.sands.vitebsktransport.repository;

import static by.sands.vitebsktransport.Constants.TAG;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import by.sands.vitebsktransport.domain.Stop;

import java.util.ArrayList;
import java.util.List;

public class StopRepository extends AbstractRepository<Stop> {
    private static final String   TABLE = "stops";
    private static final String[] COLUMNS = { "_id", "name", "coords" };

    public StopRepository(Context context) {
        super(context, TABLE);
    }

    public List<Stop> getForDirection(long directionId) {
        Log.i(TAG, "Getting stops for direction [" + directionId + "]");
        List<Stop> result = new ArrayList<Stop>();
        Cursor cursor = getDb().query("move_times t inner join stops s on t.from_stop_id = s._id",
                new String[] {"s._id", "s.name", "s.coords"}, "t.direction_id = ?",
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
        s.setCoords(cursor.getString(2));
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
        values.put("coords", obj.getCoords());
        return values;
    }
}
