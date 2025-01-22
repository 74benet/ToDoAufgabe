package com.example.todoa9.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.todoa9.model.ToDo
import java.io.FileOutputStream

/**
 * Datenbank-Helper-Klasse für die Verwaltung der ToDo-Datenbank.
 * Verantwortlich für CRUD-Operationen und die Datenbankinitialisierung.
 *
 * @param context Der Kontext der aufrufenden Anwendung.
 */
class ToDoDatabaseHelper(val context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "dba9.db"
        private const val DATABASE_VERSION = 1

        private const val TABLE_TODO = "ToDo"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_PRIORITY = "priority"
        private const val COLUMN_END_TIME = "endTime"
        private const val COLUMN_DESCRIPTION = "description"
        private const val COLUMN_STATUS = "status"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        // Leere Implementierung, da die Datenbank aus den Assets geladen wird.
    }

    /**
     * Führt ein Upgrade der Datenbank durch.
     * Löscht die bestehende Datenbank und kopiert eine neue Version aus den Assets.
     *
     * @param db Die Datenbankinstanz.
     * @param oldVersion Die alte Version der Datenbank.
     * @param newVersion Die neue Version der Datenbank.
     */
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        context.deleteDatabase(DATABASE_NAME)
        copyDatabaseFromAssets()
    }

    override fun getReadableDatabase(): SQLiteDatabase {
        copyDatabaseFromAssets()
        return super.getReadableDatabase()
    }

    override fun getWritableDatabase(): SQLiteDatabase {
        copyDatabaseFromAssets()
        return super.getWritableDatabase()
    }

    /**
     * Kopiert die Datenbank aus den Assets, falls sie nicht existiert.
     */
    private fun copyDatabaseFromAssets() {
        val dbPath = context.getDatabasePath(DATABASE_NAME)
        try {
            if (!dbPath.exists()) {
                // Überprüfen, ob die Datei in den Assets existiert
                try {
                    context.assets.open(DATABASE_NAME).use { inputStream ->
                        FileOutputStream(dbPath).use { outputStream ->
                            inputStream.copyTo(outputStream)
                        }
                    }
                    android.util.Log.d("ToDoDatabaseHelper", "Database successfully copied to: ${dbPath.absolutePath}")
                } catch (e: Exception) {
                    android.util.Log.e("ToDoDatabaseHelper", "Database file not found in assets: $DATABASE_NAME", e)
                    throw IllegalStateException("Die Datenbank konnte nicht aus den Assets geladen werden. Überprüfen Sie, ob '$DATABASE_NAME' in den Assets existiert.")
                }
            } else {
                android.util.Log.d("ToDoDatabaseHelper", "Database already exists at: ${dbPath.absolutePath}")
            }

            // Prüfen, ob die Datei nach dem Kopieren tatsächlich existiert
            if (dbPath.exists()) {
                android.util.Log.d("ToDoDatabaseHelper", "Database size: ${dbPath.length()} bytes")
            } else {
                android.util.Log.e("ToDoDatabaseHelper", "Database file not found after copy")
                throw IllegalStateException("Die Datenbankdatei wurde nicht erstellt. Überprüfen Sie die Schreibrechte des Geräts.")
            }
        } catch (e: Exception) {
            android.util.Log.e("ToDoDatabaseHelper", "Error copying database", e)
            throw IllegalStateException("Ein Fehler ist beim Kopieren der Datenbank aufgetreten: ${e.message}")
        }
    }

    /**
     * Markiert ein ToDo-Element als erledigt.
     *
     * @param id Die ID des zu markierenden ToDos.
     * @return Die Anzahl der aktualisierten Zeilen.
     */
    fun markToDoAsDone(id: Int): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_STATUS, 1)
        }
        return db.update(TABLE_TODO, values, "$COLUMN_ID = ?", arrayOf(id.toString())).also {
            db.close()
        }
    }

    /**
     * Fügt ein neues ToDo-Element in die Datenbank ein.
     *
     * @param name Der Name des ToDos.
     * @param priority Die Priorität des ToDos (1, 2, 3).
     * @param endTime Die Deadline des ToDos in Millisekunden.
     * @param description Die Beschreibung des ToDos.
     * @param status Der Status des ToDos (true = erledigt, false = offen).
     * @return Die ID des eingefügten ToDos.
     */
    fun addToDo(name: String, priority: Int, endTime: Long, description: String, status: Boolean): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, name)
            put(COLUMN_PRIORITY, priority)
            put(COLUMN_END_TIME, endTime)
            put(COLUMN_DESCRIPTION, description)
            put(COLUMN_STATUS, if (status) 1 else 0)
        }
        return db.insert(TABLE_TODO, null, values).also {
            db.close()
        }
    }

    /**
     * Ruft alle ToDo-Elemente aus der Datenbank ab.
     *
     * @return Eine Liste von ToDo-Objekten.
     */
    fun getAllToDos(): List<ToDo> {
        val todoList = mutableListOf<ToDo>()
        val query = "SELECT * FROM $TABLE_TODO"
        val db = readableDatabase

        var cursor: Cursor? = null
        try {
            // Überprüfen, ob die Tabelle existiert
            if (!isTableExists(db, TABLE_TODO)) {
                android.util.Log.e("ToDoDatabaseHelper", "Die Tabelle '$TABLE_TODO' existiert nicht in der Datenbank.")
                return emptyList() // Gibt eine leere Liste zurück, wenn die Tabelle fehlt
            }

            cursor = db.rawQuery(query, null)
            if (cursor.moveToFirst()) {
                do {
                    val todo = ToDo(
                        id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                        name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)),
                        priority = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PRIORITY)),
                        endTime = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_END_TIME)),
                        description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION)),
                        status = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_STATUS)) == 1
                    )
                    todoList.add(todo)
                } while (cursor.moveToNext())
            }
        } catch (e: Exception) {
            android.util.Log.e("ToDoDatabaseHelper", "Fehler beim Abrufen der Daten: ${e.message}", e)
        } finally {
            cursor?.close()
            db.close()
        }
        return todoList
    }

    /**
     * Überprüft, ob eine Tabelle in der Datenbank existiert.
     *
     * @param db Die Datenbankinstanz.
     * @param tableName Der Name der zu überprüfenden Tabelle.
     * @return `true`, wenn die Tabelle existiert, sonst `false`.
     */
    private fun isTableExists(db: SQLiteDatabase, tableName: String): Boolean {
        val query = "SELECT name FROM sqlite_master WHERE type='table' AND name=?"
        val cursor = db.rawQuery(query, arrayOf(tableName))
        return try {
            cursor.count > 0
        } finally {
            cursor.close()
        }
    }

    /**
     * Aktualisiert ein bestehendes ToDo-Element in der Datenbank.
     *
     * @param todo Das zu aktualisierende ToDo-Objekt.
     * @return Die Anzahl der aktualisierten Zeilen.
     */
    fun updateToDo(todo: ToDo): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, todo.name)
            put(COLUMN_PRIORITY, todo.priority)
            put(COLUMN_END_TIME, todo.endTime)
            put(COLUMN_DESCRIPTION, todo.description)
            put(COLUMN_STATUS, if (todo.status) 1 else 0)
        }
        return db.update(TABLE_TODO, values, "$COLUMN_ID = ?", arrayOf(todo.id.toString())).also {
            db.close()
        }
    }

    /**
     * Löscht ein ToDo-Element aus der Datenbank.
     *
     * @param id Die ID des zu löschenden ToDos.
     * @return Die Anzahl der gelöschten Zeilen.
     */
    fun deleteToDo(id: Int): Int {
        val db = writableDatabase
        return db.delete(TABLE_TODO, "$COLUMN_ID = ?", arrayOf(id.toString())).also {
            db.close()
        }
    }
}
