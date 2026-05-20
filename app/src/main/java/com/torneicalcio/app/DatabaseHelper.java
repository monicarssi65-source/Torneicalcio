package com.torneicalcio.app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "tornei_calcio.db";
    private static final int DB_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS app_config (" +
            "id INTEGER PRIMARY KEY," +
            "admin_password TEXT DEFAULT 'torneo2025'" +
            ");");
        db.execSQL("INSERT OR IGNORE INTO app_config (id, admin_password) VALUES (1, 'torneo2025');");

        db.execSQL("CREATE TABLE IF NOT EXISTS tornei (" +
            "id TEXT PRIMARY KEY," +
            "nome TEXT NOT NULL," +
            "societa TEXT NOT NULL," +
            "num_gironi INTEGER DEFAULT 3," +
            "num_squadre_girone INTEGER DEFAULT 3," +
            "squadre_qualificate INTEGER DEFAULT 1," +
            "token_pubblico TEXT UNIQUE," +
            "created_at TEXT DEFAULT (datetime('now'))" +
            ");");

        db.execSQL("CREATE TABLE IF NOT EXISTS gironi (" +
            "id TEXT PRIMARY KEY," +
            "torneo_id TEXT NOT NULL," +
            "nome TEXT NOT NULL," +
            "created_at TEXT DEFAULT (datetime('now'))" +
            ");");

        db.execSQL("CREATE TABLE IF NOT EXISTS squadre (" +
            "id TEXT PRIMARY KEY," +
            "torneo_id TEXT NOT NULL," +
            "nome TEXT NOT NULL," +
            "colore TEXT DEFAULT '#C62828'" +
            ");");

        db.execSQL("CREATE TABLE IF NOT EXISTS campi (" +
            "id TEXT PRIMARY KEY," +
            "torneo_id TEXT NOT NULL," +
            "nome TEXT NOT NULL" +
            ");");

        db.execSQL("CREATE TABLE IF NOT EXISTS girone_squadre (" +
            "girone_id TEXT NOT NULL," +
            "squadra_id TEXT NOT NULL," +
            "PRIMARY KEY(girone_id, squadra_id)" +
            ");");

        db.execSQL("CREATE TABLE IF NOT EXISTS partite (" +
            "id TEXT PRIMARY KEY," +
            "girone_id TEXT NOT NULL," +
            "squadra_casa_id TEXT NOT NULL," +
            "squadra_ospite_id TEXT NOT NULL," +
            "gol_casa INTEGER," +
            "gol_ospite INTEGER," +
            "orario TEXT DEFAULT '09:00'," +
            "campo TEXT," +
            "created_at TEXT DEFAULT (datetime('now'))" +
            ");");

        db.execSQL("CREATE TABLE IF NOT EXISTS fasi_finali (" +
            "id TEXT PRIMARY KEY," +
            "torneo_id TEXT NOT NULL," +
            "tipo TEXT NOT NULL," +
            "ordine INTEGER DEFAULT 1" +
            ");");

        db.execSQL("CREATE TABLE IF NOT EXISTS partite_finali (" +
            "id TEXT PRIMARY KEY," +
            "fase_id TEXT NOT NULL," +
            "squadra_casa_id TEXT NOT NULL," +
            "squadra_ospite_id TEXT NOT NULL," +
            "gol_casa INTEGER," +
            "gol_ospite INTEGER," +
            "orario TEXT DEFAULT '15:00'," +
            "campo TEXT" +
            ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            db.execSQL("PRAGMA foreign_keys = ON;");
        }
    }
}
