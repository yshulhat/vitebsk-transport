package by.sands.vitebsktransport.repository;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import by.sands.vitebsktransport.model.Direction;

public class DirectionRepository extends AbstractRepository<Direction> {
    private static final String   TABLE = "directions";
    private static final String[] COLUMNS = { "_id", "name", "route_id" };

    public DirectionRepository(Context context) {
        super(context, TABLE);
    }

    public List<Direction> getAll(long routeId) {
        Log.i(null, "Getting all directions for route [" + routeId + "]");
        List<Direction> result = new ArrayList<Direction>();
        Cursor cursor = getDb().query(TABLE, getAllColumns(), "route_id = ?", new String[] {Long.toString(routeId)},
                null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Direction obj = cursorToDomain(cursor);
            result.add(obj);
            cursor.moveToNext();
        }
        cursor.close();
        Log.i(null, "Got [" + result.size() + "] directions for route [" + routeId + "]");
        return result;
    }

    @Override
    protected Direction cursorToDomain(Cursor cursor) {
        Direction route = new Direction();
        route.setId(cursor.getLong(0));
        route.setName(cursor.getString(1));
        route.setRouteId(cursor.getLong(2));
        return route;
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
