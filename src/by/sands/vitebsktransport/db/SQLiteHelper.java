package by.sands.vitebsktransport.db;

import static by.sands.vitebsktransport.Constants.TAG;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    public static final String DB_NAME = "time_table.db";

    private Context ctx;

    public SQLiteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        ctx = context;
        String packageName = context.getPackageName();
        String dbPath = String.format("//data//data//%s//databases//", packageName);
        openDataBase(dbPath);
    }

    private void openDataBase(String dbPath) throws SQLException {
        String path = dbPath + DB_NAME;
        createDataBase(dbPath);
        SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public void createDataBase(String dbPath) {
        boolean dbExist = checkDataBase(dbPath);
        if (!dbExist) {
            getReadableDatabase();
            try {
                copyDataBase(dbPath);
            } catch (IOException e) {
                Log.e(TAG, "Copying error");
                throw new Error("Error copying database!");
            }
        } else {
            Log.i(TAG, "Database already exists");
        }
    }

    private boolean checkDataBase(String dbPath) {
        SQLiteDatabase checkDb = null;
        try {
            String path = dbPath + DB_NAME;
            checkDb = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLException e) {
            Log.e(TAG, "Error while checking db");
        }
        if (checkDb != null) {
            checkDb.close();
        }
        return checkDb != null;
    }

    private void copyDataBase(String dbPath) throws IOException {
        InputStream externalDbStream = ctx.getAssets().open(DB_NAME);
        String outFileName = dbPath + DB_NAME;
        OutputStream localDbStream = new FileOutputStream(outFileName);

        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = externalDbStream.read(buffer)) > 0) {
            localDbStream.write(buffer, 0, bytesRead);
        }

        localDbStream.close();
        externalDbStream.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    // TODO: move here copying BD file if new version comes
    @Override
    public void onUpgrade(SQLiteDatabase db, int v1, int v2) {
    }

}
