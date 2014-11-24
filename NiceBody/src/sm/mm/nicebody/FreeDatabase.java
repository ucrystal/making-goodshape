package sm.mm.nicebody;

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

public class FreeDatabase extends SQLiteOpenHelper {
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "NiceDB";

	  public FreeDatabase(Context context) {
          super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }


	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_FREE_TABLE = "CREATE TABLE IF NOT EXISTS frees ( id INTEGER PRIMARY KEY AUTOINCREMENT, type INTEGER, count INTEGER, t DATETIME DEFAULT CURRENT_TIMESTAMP, photo BLOB)";
		String CREATE_PROFILE_TABLE = "CREATE TABLE IF NOT EXISTS profiles (id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(10), height INTEGER, weight INTEGER)";
		db.execSQL(CREATE_FREE_TABLE);
		db.execSQL(CREATE_PROFILE_TABLE);
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
		db.execSQL("DROP TABLE IF EXISTS frees");
		// create fresh books table
		this.onCreate(db);
	}

	public void dropFreeTable() {
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("DROP TABLE IF EXISTS frees");
		db.close();
	}

	public void dropProfileTable() {
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("DROP TABLE IF EXISTS profiles");
		db.close();
	}

	public void createIndex() {
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("CREATE INDEX titleIndex ON frees (title ASC);");
		db.close();
	}

	public void createFreeTable() {
		SQLiteDatabase db = this.getWritableDatabase();
		String CREATE_FREE_TABLE = "CREATE TABLE frees (id INTEGER PRIMARY KEY AUTOINCREMENT, type INTEGER, count INTEGER, t VARCHAR(10))";
		db.execSQL(CREATE_FREE_TABLE);
		db.close();
	}

	public void createProfileTable() {
		SQLiteDatabase db = this.getWritableDatabase();
		String CREATE_PROFILE_TABLE = "CREATE TABLE profiles (id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(10), height INTEGER, weight INTEGER, photo BLOB)";
		db.execSQL(CREATE_PROFILE_TABLE);
		db.close();
	}

	/*
	 * 자유운동 함수 생성
	 */

	// 이짓을 왜 하는지 나도 모르겠다만... 하라니까 해본다..
	private static final String TABLE_FREES = "frees";
	private static final String KEY_ID = "id";
	private static final String KEY_TYPE = "type";
	private static final String KEY_COUNT = "count";
	private static final String KEY_DATE = "t";

	Calendar calendar = Calendar.getInstance();
	Date today = calendar.getTime();

	private static final String[] COLUMNS = { KEY_ID, KEY_TYPE, KEY_COUNT,
			KEY_DATE };

	public void addFreeData(FreeData freeData) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_TYPE, freeData.getType()); // get title
		values.put(KEY_COUNT, freeData.getCount()); // get author
		values.put(KEY_DATE, getDateTime());
		db.insert(TABLE_FREES, null, values);
		db.close();
	}

	public FreeData getFreeData(int type) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_FREES, // a. table
				COLUMNS, // b. column names
				"type = ?", // c. selections
				new String[] { Integer.toString(type) }, // d. selections args
				null, // e. group by
				null, // f. having
				null, // g. order by
				null); // h. limit
		if (cursor != null)
			cursor.moveToFirst();
		FreeData freeData = new FreeData();
		freeData.setId(Integer.parseInt(cursor.getString(0)));
		freeData.setType(Integer.parseInt(cursor.getString(1)));
		freeData.setCount(Integer.parseInt(cursor.getString(2)));
		try {
			freeData.setDate(cursor.getString(3));
		} catch (Exception e) {
			Log.v("test", "error parsing <" + cursor.getString(3) + ">");
		}
		return freeData;
	}

	public List<FreeData> getFreeDatasByDate(String t) {
		List<FreeData> freeDatas = new LinkedList<FreeData>();
		String query = "SELECT  * FROM " + TABLE_FREES + " WHERE t =" + t;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		FreeData freeData = null;
		if (cursor.moveToFirst()) {
			do {
				freeData = new FreeData();
				freeData.setId(Integer.parseInt(cursor.getString(0)));
				freeData.setType(Integer.parseInt(cursor.getString(1)));
				freeData.setCount(Integer.parseInt(cursor.getString(2)));
				try {
					freeData.setDate(cursor.getString(3));
				} catch (Exception e) {
					Log.v("test", "error parsing <" + cursor.getString(3) + ">");
				}
				freeDatas.add(freeData);
			} while (cursor.moveToNext());
		}
		return freeDatas;
	}

	public List<FreeData> getAllFreeDatas() {
		List<FreeData> freeDatas = new LinkedList<FreeData>();
		String query = "SELECT  * FROM " + TABLE_FREES;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		FreeData freeData = null;
		if (cursor.moveToFirst()) {
			do {
				freeData = new FreeData();
				freeData.setId(Integer.parseInt(cursor.getString(0)));
				freeData.setType(Integer.parseInt(cursor.getString(1)));
				freeData.setCount(Integer.parseInt(cursor.getString(2)));
				try {
					freeData.setDate(cursor.getString(3));
				} catch (Exception e) {
					Log.v("test", "error parsing <" + cursor.getString(3) + ">");
				}
				freeDatas.add(freeData);
			} while (cursor.moveToNext());
		}
		return freeDatas;
	}

	private String getDateTime() {

		String instance_t = "";

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.KOREAN);
		instance_t = sdf.format(today);

		return instance_t;
	}

	/*
	 * 프로필 함수 생성
	 */

	private static final String TABLE_PROFILES = "profiles";
	private static final String KEY_NAME = "name";
	private static final String KEY_HEIGHT = "height";
	private static final String KEY_WEIGHT = "weight";
	private static final String KEY_PHOTO = "photo";

	// private static final String[] P_COLUMNS = { KEY_ID,
	// KEY_TYPE, KEY_COUNT, KEY_DATE };

	public void addProfileData(ProfileData profileData) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_NAME, profileData.getName());
		values.put(KEY_HEIGHT, profileData.getHeight());
		values.put(KEY_WEIGHT, profileData.getWeight());
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
			profileData.setHeight(Integer.parseInt(cursor.getString(2)));
			profileData.setWeight(Integer.parseInt(cursor.getString(3)));

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
				ProfileData.setHeight(Integer.parseInt(cursor.getString(2)));
				ProfileData.setWeight(Integer.parseInt(cursor.getString(3)));
				ProfileData.setPhoto(cursor.getBlob(4));
	
				ProfileDatas.add(ProfileData);
			} while (cursor.moveToNext());
		}
		return ProfileDatas;
	}

}