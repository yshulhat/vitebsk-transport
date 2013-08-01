package by.sands.vitebsktransport.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import by.sands.vitebsktransport.model.Route;

public class RouteRepository extends AbstractRepository<Route> {
    private static final String   TABLE = "routes";
    private static final String[] COLUMNS = { "_id", "name", "number" };

    public RouteRepository(Context context) {
        super(context, TABLE);
    }

    @Override
    protected Route cursorToDomain(Cursor cursor) {
        Route route = new Route();
        route.setId(cursor.getLong(0));
        route.setName(cursor.getString(1));
        route.setNumber(cursor.getString(2));
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
        return values;
    }
}
