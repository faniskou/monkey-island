package com.main.pirateisland;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

public class logindatabaseadapter {
	static final String DATABASE_NAME = "islanddata.db";
	static final int DATABASE_VERSION = 1;
	public static final int NAME_COLUMN = 1;
	// TODO: Create public field for each column in your table.
	// SQL Statement to create a new database.
	static final String DATABASE_USERS_CREATE = "create table "
			+ "USERS"
			+ "( "
			+ "ID"
			+ " integer primary key autoincrement,"
			+ "USERNAME  text,AGE text,"
			+ "FAILSLEVEL1 SMALLINT,FAILSLEVEL2 SMALLINT,FAILSLEVEL3 SMALLINT,"
			+ "FAILSLEVEL4 SMALLINT,FAILSLEVEL5 SMALLINT,FAILSLEVEL6 SMALLINT,"
			+ "USERNEGATIVESCORE BIGINT, MAXLEVEL SMALLINT , CURRENTLEVEL SMALLINT , DIFFICULTY SMALLINT );";
	static final String DATABASE_HIST_CREATE = "create table "
			+ "HIST"
			+ "( "
			+ "ID"
			+ " integer primary key autoincrement,"
			+ "USERNAME  text, DIFFICULTY SMALLINT,"
			+ "FAILSLEVEL1 SMALLINT,FAILSLEVEL2 SMALLINT,FAILSLEVEL3 SMALLINT,"
			+ "FAILSLEVEL4 SMALLINT,FAILSLEVEL5 SMALLINT,FAILSLEVEL6 SMALLINT  );";
	// Variable to hold the database instance
	public SQLiteDatabase db;
	// Context of the application using the database.
	private final Context context;
	// Database open/upgrade helper
	private databasehelper dbHelper;

	public logindatabaseadapter(Context _context) {
		context = _context;
		dbHelper = new databasehelper(context, DATABASE_NAME, null,
				DATABASE_VERSION);
	}

	public logindatabaseadapter open() throws SQLException {
		db = dbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		db.close();
	}

	public SQLiteDatabase getDatabaseInstance() {
		return db;
	}

	public void insertEntry(String userName, String password) {
		ContentValues newValues = new ContentValues();
		// Assign values for each row.
		
		
		newValues.put("USERNAME", userName);
		newValues.put("AGE", password);
		newValues.put("FAILSLEVEL1", 3);
		newValues.put("FAILSLEVEL2", 3);
		newValues.put("FAILSLEVEL3", 3);
		newValues.put("FAILSLEVEL4", 3);
		newValues.put("FAILSLEVEL5", 3);
		newValues.put("FAILSLEVEL6", 3);
		newValues.put("USERNEGATIVESCORE", 0);
		newValues.put("MAXLEVEL", 1);
		newValues.put("CURRENTLEVEL", 1);
		newValues.put("DIFFICULTY", 1);
		// Insert the row into your table
		db.insert("USERS", null, newValues);
		// /Toast.makeText(context, "Reminder Is Successfully Saved",
		// Toast.LENGTH_LONG).show();
	}

	public int deleteEntry(String UserName) {
		// String id=String.valueOf(ID);
		String where = "USERNAME=?";
		int numberOFEntriesDeleted = db.delete("USERS", where,
				new String[] { UserName });
		// Toast.makeText(context,
		// "Number fo Entry Deleted Successfully : "+numberOFEntriesDeleted,
		// Toast.LENGTH_LONG).show();
		return numberOFEntriesDeleted;
	}

	public String getSinlgeEntry(String userName) {
		Cursor cursor = db.query("USERS", null, " USERNAME=?",
				new String[] { userName }, null, null, null);
		if (cursor.getCount() < 1) // UserName Not Exist
		{
			cursor.close();
			return "NOT EXIST";
		}
		cursor.moveToFirst();
		String password = cursor.getString(cursor.getColumnIndex("AGE"));
		cursor.close();
		return password;
	}

	public void updateEntry(String userName, String password) {
		// Define the updated row content.
		ContentValues updatedValues = new ContentValues();
		// Assign values for each row.
		updatedValues.put("USERNAME", userName);
		updatedValues.put("AGE", password);

		String where = "USERNAME = ?";
		db.update("USERS", updatedValues, where, new String[] { userName });
	}

	public void updateAll(User curuser) {
		// Define the updated row content.
		ContentValues updatedValues = new ContentValues();
		// Assign values for each row.
		updatedValues.put("USERNAME", curuser._USERNAME);
		updatedValues.put("AGE", curuser._AGE);
		updatedValues.put("FAILSLEVEL1", curuser._FAILSLEVEL1);
		updatedValues.put("FAILSLEVEL2", curuser._FAILSLEVEL2);
		updatedValues.put("FAILSLEVEL3", curuser._FAILSLEVEL3);
		updatedValues.put("FAILSLEVEL4", curuser._FAILSLEVEL4);
		updatedValues.put("FAILSLEVEL5", curuser._FAILSLEVEL5);
		updatedValues.put("FAILSLEVEL6", curuser._FAILSLEVEL6);
		updatedValues.put("USERNEGATIVESCORE", curuser._USERNEGATIVESCORE);
		updatedValues.put("MAXLEVEL", curuser._MAXLEVEL);
		updatedValues.put("CURRENTLEVEL", curuser._CURRENTLEVEL);
		updatedValues.put("DIFFICULTY", curuser._DIFFICULTY);
		String where = "USERNAME = ? and AGE = ?";
		db.update("USERS", updatedValues, where, new String[] {
				curuser._USERNAME, curuser._AGE });
	}

	public User getUser(String userName, String age) {
		// Define the updated row content.
		User theuser = new User();
		Cursor cursor = db.query("USERS", null, " USERNAME=? and AGE=?",
				new String[] { userName, age }, null, null, null);
		if (cursor.getCount() < 1) // UserName Not Exist
		{
			cursor.close();

			theuser._USERNAME = "NOT EXIST";
			theuser._AGE = "0";
			return theuser;
		}
		cursor.moveToFirst();
		theuser = new User(cursor.getString(cursor.getColumnIndex("USERNAME")),
				cursor.getString(cursor.getColumnIndex("AGE")),
				cursor.getInt(cursor.getColumnIndex("FAILSLEVEL1")),
				cursor.getInt(cursor.getColumnIndex("FAILSLEVEL2")),
				cursor.getInt(cursor.getColumnIndex("FAILSLEVEL3")),
				cursor.getInt(cursor.getColumnIndex("FAILSLEVEL4")),
				cursor.getInt(cursor.getColumnIndex("FAILSLEVEL5")),
				cursor.getInt(cursor.getColumnIndex("FAILSLEVEL6")),
				cursor.getInt(cursor.getColumnIndex("USERNEGATIVESCORE")),
				cursor.getInt(cursor.getColumnIndex("MAXLEVEL")),
				cursor.getInt(cursor.getColumnIndex("CURRENTLEVEL")),
				cursor.getInt(cursor.getColumnIndex("DIFFICULTY")));
		cursor.close();
		return theuser;

	}
	public void updateEX(int errorflag) {
		// Define the updated row content.
		ContentValues updatedValues = new ContentValues();
		// Assign values for each row.
		updatedValues.put("FAILSLEVEL1", errorflag);

		String where = "USERNAME = ?";
		// String[] whereArgs = new int[errorflag];
		db.update("USERS", updatedValues, where, null);
	}
	
	public void showErrors(String userName, String age) {
		
		Cursor cursor = db.query("USERS", null, " USERNAME=? and AGE=?",
				new String[] { userName, age }, null, null, null);
		
		if (cursor.getCount() < 1) // UserName Not Exist
		{
			cursor.close();
            Toast.makeText(context, "kane tin askisi 1", Toast.LENGTH_SHORT).show();
		}
		cursor.moveToFirst();
	    //aaa = cursor.getInt(cursor.getColumnIndex("FAILSLEVEL1"));
	    }

}
