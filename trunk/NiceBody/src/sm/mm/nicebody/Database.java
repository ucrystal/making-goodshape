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

public class Database extends SQLiteOpenHelper {
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "NiceDB";

	  public Database(Context context) {
          super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }


	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_FREE_TABLE = "CREATE TABLE IF NOT EXISTS frees ( id INTEGER PRIMARY KEY AUTOINCREMENT, type INTEGER, count INTEGER, t DATETIME DEFAULT CURRENT_TIMESTAMP, photo BLOB)";
		String CREATE_PROFILE_TABLE = "CREATE TABLE IF NOT EXISTS profiles (id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(10), height INTEGER, weight INTEGER)";
		String CREATE_RECOMMEND_TABLE = "CREATE TABLE IF NOT EXISTS recommends (id INTEGER PRIMARY KEY AUTOINCREMENT, s_check INTEGER, t DATETIME DEFAULT CURRENT_TIMESTAMP)";
		String CREATE_RECORD_TABLE = "CREATE TABLE IF NOT EXISTS records (id INTEGER PRIMARY KEY AUTOINCREMENT, checkInt INTEGER, recordDate VARCHAR(10))";
		db.execSQL(CREATE_FREE_TABLE);
		db.execSQL(CREATE_PROFILE_TABLE);
		db.execSQL(CREATE_RECOMMEND_TABLE);
		db.execSQL(CREATE_RECORD_TABLE);
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
	
	public void dropRecordTable() {
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("DROP TABLE IF EXISTS records");
		db.close();
	}

	public void dropProfileTable() {
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("DROP TABLE IF EXISTS profiles");
		db.close();
	}
	
	public void dropRecommendTable() {
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("DROP TABLE IF EXISTS recommends");
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
	
	public void createRecordTable() {
		SQLiteDatabase db = this.getWritableDatabase();
		String CREATE_RECORD_TABLE = "CREATE TABLE records (id INTEGER PRIMARY KEY AUTOINCREMENT, checkInt INTEGER, recordDate VARCHAR(10))";
		db.execSQL(CREATE_RECORD_TABLE);
		db.close();
	}

	public void createProfileTable() {
		SQLiteDatabase db = this.getWritableDatabase();
		String CREATE_PROFILE_TABLE = "CREATE TABLE profiles (id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(10), height INTEGER, weight INTEGER, photo BLOB)";
		db.execSQL(CREATE_PROFILE_TABLE);
		db.close();
	}
	
	public void createRecommendTable() {
		SQLiteDatabase db = this.getWritableDatabase();
		String CREATE_RECOMMEND_TABLE = "CREATE TABLE recommends (id INTEGER PRIMARY KEY AUTOINCREMENT, s_check INTEGER, t DATETIME DEFAULT CURRENT_TIMESTAMP)";
		db.execSQL(CREATE_RECOMMEND_TABLE);
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

		List<ProfileData> profileDatas = new LinkedList<ProfileData>();
		String query = "SELECT  * FROM " + TABLE_PROFILES;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		ProfileData profileData = null;
		if (cursor.moveToFirst()) {
			do {
				profileData= new ProfileData();
				profileData.setName(cursor.getString(1));
				profileData.setHeight(Integer.parseInt(cursor.getString(2)));
				profileData.setWeight(Integer.parseInt(cursor.getString(3)));
				profileData.setPhoto(cursor.getBlob(4));
	
				profileDatas.add(profileData);
			} while (cursor.moveToNext());
		}
		
		ProfileData n_profileData = null;
		int r_size = profileDatas.size() -1;
		Log.v("test", String.valueOf(r_size));

		n_profileData = profileDatas.get(r_size);
		return n_profileData;
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
	
	/*
	 * 추천운동함수생성
	 */

	private static final String TABLE_RECOMMENDS = "recommends";
	private static final String KEY_SCHECK = "s_check";

	// private static final String[] P_COLUMNS = { KEY_ID,
	// KEY_TYPE, KEY_COUNT, KEY_DATE };

	public void openNext (RecommendData recommendData) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_SCHECK, recommendData.getCheck());
		values.put(KEY_DATE, getDateTime());
		
		db.insert(TABLE_RECOMMENDS, null, values);
		db.close();
		
	}
	
	public RecommendData getRecommendData(int id) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_RECOMMENDS, // a. table
				COLUMNS, // b. column names
				"id = ?", // c. selections
				new String[] { Integer.toString(id) }, // d. selections args
				null, // e. group by
				null, // f. having
				null, // g. order by
				null); // h. limit
		if (cursor != null)
			cursor.moveToFirst();
		RecommendData recommendData = new RecommendData();
		recommendData.setId(Integer.parseInt(cursor.getString(0)));
		recommendData.setCheck(Integer.parseInt(cursor.getString(1)));
		recommendData.setDate(cursor.getString(2));

		return recommendData;
	}

	public List<RecommendData> getAllRecommendDatas() {
		List<RecommendData> recommendDatas = new LinkedList<RecommendData>();
		String query = "SELECT  * FROM " + TABLE_RECOMMENDS;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		RecommendData recommendData = null;
		if (cursor.moveToFirst()) {
			do {
				recommendData= new RecommendData();
				recommendData.setId(Integer.parseInt(cursor.getString(1)));
				recommendData.setCheck(Integer.parseInt(cursor.getString(2)));
				recommendData.setDate(cursor.getString(2));
	
				recommendDatas.add(recommendData);
			} while (cursor.moveToNext());
		}
		return recommendDatas;
	}
	
	public List<RecommendData> getRecommendDatasByDate(String t) {
		List<RecommendData> recommendDatas = new LinkedList<RecommendData>();
		String query = "SELECT  * FROM " + TABLE_RECOMMENDS + " WHERE t =" + t;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		RecommendData recommendData = null;
		if (cursor.moveToFirst()) {
			do {
				recommendData = new RecommendData();
				recommendData.setId(Integer.parseInt(cursor.getString(0)));
				recommendData.setCheck(Integer.parseInt(cursor.getString(1)));
				recommendData.setDate(cursor.getString(2));

				recommendDatas.add(recommendData);
			} while (cursor.moveToNext());
		}
		return recommendDatas;
	}

	
	public List<RecommendData> getRecommendDatasById(int i) {
		List<RecommendData> recommendDatas = new LinkedList<RecommendData>();
		String query = "SELECT  * FROM " + TABLE_RECOMMENDS + " WHERE id =" + i;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		RecommendData recommendData = null;
		if (cursor.moveToFirst()) {
			do {
				recommendData = new RecommendData();
				recommendData.setId(Integer.parseInt(cursor.getString(0)));
				recommendData.setCheck(Integer.parseInt(cursor.getString(1)));
				recommendData.setDate(cursor.getString(2));

				recommendDatas.add(recommendData);
			} while (cursor.moveToNext());
		}
		return recommendDatas;
	}
	
	/*
	 * 추천운동함수생성
	 */

	private static final String TABLE_RECORDS = "records";
	private static final String KEY_CHECKINT = "checkInt";
	private static final String KEY_RECORDDATE = "recordDate";

	// private static final String[] P_COLUMNS = { KEY_ID,
	// KEY_TYPE, KEY_COUNT, KEY_DATE };
	
	public void addRecordData(RecordData recordData) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_CHECKINT, recordData.getCheckInt());
		values.put(KEY_RECORDDATE, recordData.getRecordDate());
		db.insert(TABLE_RECORDS, null, values);
		db.close();
	}

	public void updateRecordData(int input, String date) {
		
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("UPDATE " + TABLE_RECORDS +" SET checkInt = "+input+" WHERE id = 1;");  
		db.execSQL("UPDATE " + TABLE_RECORDS +" SET recordDate = "+date+" WHERE id = 1;");  
		db.close();
		
	}

	public RecordData getRecordData() {
		
		List<RecordData> recordDatas = new LinkedList<RecordData>();
		String query = "SELECT  * FROM " + TABLE_RECORDS;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		RecordData recordData = null;
		if (cursor.moveToFirst()) {
			do {
				recordData = new RecordData();
				recordData.setId(Integer.parseInt(cursor.getString(0)));
				recordData.setCheckInt(Integer.parseInt(cursor.getString(1)));
				recordData.setRecordDate(cursor.getString(2));

				recordDatas.add(recordData);
			} while (cursor.moveToNext());
		}
		
		RecordData n_recordData = null;
		int r_size = recordDatas.size() -1;

		n_recordData = recordDatas.get(r_size);
		return n_recordData;
	}

}