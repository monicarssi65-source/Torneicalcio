package com.torneicalcio.app;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.UUID;

public class DatabaseBridge {

    private final Context context;
    private final DatabaseHelper dbHelper;
    private final WebView webView;

    public DatabaseBridge(Context context, DatabaseHelper dbHelper, WebView webView) {
        this.context = context;
        this.dbHelper = dbHelper;
        this.webView = webView;
    }

    @JavascriptInterface
    public String query(String sql, String paramsJson) {
        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String[] params = parseParams(paramsJson);
            Cursor cursor = db.rawQuery(sql, params);
            JSONArray result = cursorToJson(cursor);
            cursor.close();
            return result.toString();
        } catch (Exception e) {
            Log.e("DB", "Query error: " + e.getMessage() + " | SQL: " + sql);
            return "[]";
        }
    }

    @JavascriptInterface
    public String execute(String sql, String paramsJson) {
        try {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            String[] params = parseParams(paramsJson);
            db.execSQL(sql, params);
            return "{\"ok\":true}";
        } catch (Exception e) {
            Log.e("DB", "Execute error: " + e.getMessage() + " | SQL: " + sql);
            return "{\"ok\":false,\"error\":\"" + e.getMessage().replace("\"", "'") + "\"}";
        }
    }

    @JavascriptInterface
    public String generateId() {
        return UUID.randomUUID().toString();
    }

    private JSONArray cursorToJson(Cursor cursor) throws Exception {
        JSONArray array = new JSONArray();
        String[] columns = cursor.getColumnNames();
        while (cursor.moveToNext()) {
            JSONObject row = new JSONObject();
            for (int i = 0; i < columns.length; i++) {
                int type = cursor.getType(i);
                switch (type) {
                    case Cursor.FIELD_TYPE_INTEGER:
                        row.put(columns[i], cursor.getLong(i));
                        break;
                    case Cursor.FIELD_TYPE_FLOAT:
                        row.put(columns[i], cursor.getDouble(i));
                        break;
                    case Cursor.FIELD_TYPE_NULL:
                        row.put(columns[i], JSONObject.NULL);
                        break;
                    default:
                        row.put(columns[i], cursor.getString(i));
                }
            }
            array.put(row);
        }
        return array;
    }

    private String[] parseParams(String paramsJson) {
        if (paramsJson == null || paramsJson.equals("[]") || paramsJson.isEmpty()) {
            return new String[0];
        }
        try {
            JSONArray arr = new JSONArray(paramsJson);
            String[] result = new String[arr.length()];
            for (int i = 0; i < arr.length(); i++) {
                Object val = arr.get(i);
                result[i] = val == JSONObject.NULL ? null : val.toString();
            }
            return result;
        } catch (Exception e) {
            return new String[0];
        }
    }
}
