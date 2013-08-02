package by.sands.vitebsktransport.repository;

import static by.sands.vitebsktransport.Constants.TAG;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import by.sands.vitebsktransport.domain.Direction;

import java.util.List;

public class DirectionRepository extends AbstractRepository<Direction> {
    private static final String   TABLE = "directions";
    private static final String[] COLUMNS = { "_id", "name", "route_id" };

    public DirectionRepository(Context context) {
        super(context, TABLE);
    }

    public List<Direction> getAll(long routeId) {
        Log.i(TAG, "Getting all directions for route [" + routeId + "]");
        List<Direction> result = getAll("route_id = ?", Long.toString(routeId));
        Log.i(TAG, "Got [" + result.size() + "] directions for route [" + routeId + "]");
        return result;
    }

    @Override
    protected Direction cursorToDomain(Cursor cursor) {
        Direction d = new Direction();
        d.setId(cursor.getLong(0));
        d.setName(cursor.getString(1));
        d.setRouteId(cursor.getLong(2));
        return d;
    }

    @Override
    protected String[] getAllColumns() {
        return COLUMNS;
    }

    @Override
    protected ContentValues fillValues(Direction obj) {
        ContentValues values = new ContentValues();
        values.put("name", obj.getName());
        values.put("route_id", obj.getRouteId());
        return values;
    }
}
