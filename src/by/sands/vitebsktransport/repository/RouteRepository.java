package by.sands.vitebsktransport.repository;

import static by.sands.vitebsktransport.Constants.TAG;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import by.sands.vitebsktransport.domain.Route;

import java.util.List;

public class RouteRepository extends AbstractRepository<Route> {
    private static final String   TABLE = "routes";
    private static final String[] COLUMNS = { "_id", "name", "number", "type" };

    public RouteRepository(Context context) {
        super(context, TABLE);
    }

    public List<Route> getAll(String transportType) {
        Log.i(TAG, "Getting routes for transport type [" + transportType + "]");
        List<Route> result = getAll("type = ?", transportType);
        Log.i(TAG, "Got [" + result.size() + "] routes for transport type [" + transportType + "]");
        return result;
    }

    @Override
    protected Route cursorToDomain(Cursor cursor) {
        Route route = new Route();
        route.setId(cursor.getLong(0));
        route.setName(cursor.getString(1));
        route.setNumber(cursor.getString(2));
        route.setType(cursor.getString(3));
        return route;
    }

    @Override
    protected String[] getAllColumns() {
        return COLUMNS;
    }

    @Override
    protected ContentValues fillValues(Route obj) {
        ContentValues values = new ContentValues();
        values.put("name", obj.getName());
        values.put("number", obj.getNumber());
        values.put("type", obj.getType());
        return values;
    }
}
