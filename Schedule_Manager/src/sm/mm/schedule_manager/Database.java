package sm.mm.schedule_manager;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.LinkedList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Database extends SQLiteOpenHelper {
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "MeetDB";

	  public Database(Context context) {
          super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }


	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_PROFILE_TABLE = "CREATE TABLE IF NOT EXISTS profiles (id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(10), univ VARCHAR(10), phone VARCHAR(10), photo BLOB)";
		String CREATE_PROMISE_TABLE = "CREATE TABLE IF NOT EXISTS promises (id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(10), p_day VARCHAR(10), p_time VARCHAR(10))";
		db.execSQL(CREATE_PROFILE_TABLE);
		db.execSQL(CREATE_PROMISE_TABLE);
	}

	// 테이블 존재 여부 확인
	public int checkTable() {
		int cec = 0;

		SQLiteDatabase db = this.getWritableDatabase();

		String query = "SELECT * FROM " + TABLE_PROFILES;
			Cursor cursor = db.rawQuery(query, null);

			if (cursor.moveToFirst()) {
				do {
					if (cursor.getString(1) == null) {
						cec = 0;
						return cec;
					} else if (cursor.getString(1) != null) {
						cec = 1;
						return cec;
					}
				} while (cursor.moveToNext());

			}

		return cec;
	}


	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS profiles");
		// create fresh books table
		this.onCreate(db);
	}

	public void dropProfileTable() {
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("DROP TABLE IF EXISTS profiles");
		db.close();
	}
	
	public void dropPromiseTable() {
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("DROP TABLE IF EXISTS promises");
		db.close();
	}


	public void createIndex() {
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("CREATE INDEX titleIndex ON profiles (title ASC);");
		db.close();
	}

	public void createProfileTable() {
		SQLiteDatabase db = this.getWritableDatabase();
		String CREATE_PROFILE_TABLE = "CREATE TABLE profiles (id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(10), univ VARCHAR(10), phone VARCHAR(10), photo BLOB)";
		db.execSQL(CREATE_PROFILE_TABLE);
		db.close();
	}
	
	public void createPromiseTable() {
		SQLiteDatabase db = this.getWritableDatabase();
		String CREATE_PROFILE_TABLE = "CREATE TABLE promises (id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(10), p_day VARCHAR(3), p_time VARCHAR(3))";
		db.execSQL(CREATE_PROFILE_TABLE);
		db.close();
	}

	/*
	 * 프로필 함수 생성
	 */

	private static final String TABLE_PROFILES = "profiles";
	private static final String KEY_ID = "id";
	private static final String KEY_NAME = "name";
	private static final String KEY_UNIV = "univ";
	private static final String KEY_PHONE = "phone";
	private static final String KEY_PHOTO = "photo";

	// private static final String[] P_COLUMNS = { KEY_ID,
	// KEY_TYPE, KEY_COUNT, KEY_DATE };

	public void addProfileData(ProfileData profileData) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_NAME, profileData.getName());
		values.put(KEY_UNIV, profileData.getUniv());
		values.put(KEY_PHONE, profileData.getPhone());
		values.put(KEY_PHOTO, profileData.getPhoto());
		db.insert(TABLE_PROFILES, null, values);
		db.close();
	}

	public ProfileData getProfileData() {

		String query = "SELECT  * FROM " + TABLE_PROFILES;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		ProfileData profileData = null;
		if (cursor.moveToFirst()) {
			profileData = new ProfileData();
			profileData.setName(cursor.getString(1));
			profileData.setUniv(cursor.getString(2));
			profileData.setPhone(cursor.getString(3));

			//int drawalbeColumnId = cursor.getColumnIndex(cursor.getString(4));
			byte[] drawableIconByteArray = cursor.getBlob(4);

			profileData.setPhoto(drawableIconByteArray);
		}
		return profileData;
	}
	
	public List<ProfileData> getAllProfileDatas() {
		List<ProfileData> ProfileDatas = new LinkedList<ProfileData>();
		String query = "SELECT  * FROM " + TABLE_PROFILES;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		ProfileData ProfileData = null;
		if (cursor.moveToFirst()) {
			do {
				ProfileData= new ProfileData();
				ProfileData.setName(cursor.getString(1));
				ProfileData.setUniv(cursor.getString(2));
				ProfileData.setPhone(cursor.getString(3));
				ProfileData.setPhoto(cursor.getBlob(4));
	
				ProfileDatas.add(ProfileData);
			} while (cursor.moveToNext());
		}
		return ProfileDatas;
	}
	
	////약속 함수 생성
	
	private static final String TABLE_PROMISES = "promises";
	private static final String KEY_DAY = "p_day";
	private static final String KEY_TIME = "p_time";

	// private static final String[] P_COLUMNS = { KEY_ID,
	// KEY_TYPE, KEY_COUNT, KEY_DATE };

	public void addPromiseData(PromiseData promiseData) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_NAME, promiseData.getName());
		values.put(KEY_DAY, promiseData.getDay());
		values.put(KEY_TIME, promiseData.getTime());
		db.insert(TABLE_PROMISES, null, values);
		db.close();
	}
	
	public List<PromiseData> getAllPromiseDatas() {
		List<PromiseData> PromiseDatas = new LinkedList<PromiseData>();
		String query = "SELECT  * FROM " + TABLE_PROMISES;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		PromiseData PromiseData = null;
		if (cursor.moveToFirst()) {
			do {
				PromiseData= new PromiseData();
				PromiseData.setId(Integer.parseInt(cursor.getString(0)));
				PromiseData.setName(cursor.getString(1));
				PromiseData.setDay(cursor.getString(2));
				PromiseData.setTime(cursor.getString(3));
	
				PromiseDatas.add(PromiseData);
			} while (cursor.moveToNext());
		}
		return PromiseDatas;
	}

	public void deletePromise(String id) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		db.delete(TABLE_PROMISES,
				KEY_ID+" = ?",
				new String[] { id });
		db.close();
	}



}