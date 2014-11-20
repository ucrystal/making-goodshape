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
		String CREATE_FREE_TABLE = "CREATE TABLE IF NOT EXISTS frees ( id INTEGER PRIMARY KEY AUTOINCREMENT, type INTEGER, count INTEGER, t DATETIME DEFAULT CURRENT_TIMESTAMP)";
		String CREATE_PROFILE_TABLE = "CREATE TABLE IF NOT EXISTS profiles (id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(10), height VARCHAR(10), weight VARCHAR(10))";
		db.execSQL(CREATE_FREE_TABLE);
		db.execSQL(CREATE_PROFILE_TABLE);		
	}
	
	//���̺� ���� ���� Ȯ�� 
	public int checkTable(){
		int cec = 0;
		
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor c =db.rawQuery("SELECT COUNT() FROM sqlite_master WHERE name ='profiles';",null);
        if (c != null ) {
            if  (c.moveToFirst()) {
                  do {
                	  cec = Integer.parseInt(c.getString(0));
                	  return cec;
                  }while (c.moveToNext());
            }
       }else if(c == null){
    	   return cec;
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
		String CREATE_PROFILE_TABLE = "CREATE TABLE profiles (id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(10), height INTEGER, weight INTEGER)";
		db.execSQL(CREATE_PROFILE_TABLE);
		db.close();
	}

	/*
	 * ����� �Լ� ����
	 */

	// ������ �� �ϴ��� ���� �𸣰ڴٸ�... �϶�ϱ� �غ���..
	private static final String TABLE_FREES = "frees";
	private static final String KEY_ID = "id";
	private static final String KEY_TYPE = "type";
	private static final String KEY_COUNT = "count";
	private static final String KEY_DATE = "t";
	
	Calendar calendar = Calendar.getInstance();
	Date today = calendar.getTime();


	private static final String[] COLUMNS = { KEY_ID, KEY_TYPE, KEY_COUNT,
			KEY_DATE};

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
	 * ������ �Լ� ����
	 */

	private static final String TABLE_PROFILES = "profiles";
	private static final String KEY_NAME = "name";
	private static final String KEY_HEIGHT = "height";
	private static final String KEY_WEIGHT = "weight";

	// private static final String[] P_COLUMNS = { KEY_ID,
	// KEY_TYPE, KEY_COUNT, KEY_DATE };

	public void addProfileData(ProfileData profileData) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_NAME, profileData.getName());
		values.put(KEY_HEIGHT, profileData.getHeight());
		values.put(KEY_WEIGHT, profileData.getWeight());
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
		}
		return profileData;
	}

	/*
	 * public List<Book> getAllBooksOrderByTitle() { List<Book> books = new
	 * LinkedList<Book>(); String query = "SELECT  * FROM " + TABLE_BOOKS +
	 * " order by title"; SQLiteDatabase db = this.getWritableDatabase(); Cursor
	 * cursor = db.rawQuery(query, null); Book book = null; if
	 * (cursor.moveToFirst()) { do { book = new Book();
	 * book.setId(Integer.parseInt(cursor.getString(0)));
	 * book.setTitle(cursor.getString(1)); book.setAuthor(cursor.getString(2));
	 * try { book.setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
	 * .parse(cursor.getString(3))); } catch (Exception e) { Log.v("test",
	 * "error parsing <" + cursor.getString(3) + ">"); } books.add(book); }
	 * while (cursor.moveToNext()); } return books; }
	 * 
	 * public int updateBook(Book book) { SQLiteDatabase db =
	 * this.getWritableDatabase(); ContentValues values = new ContentValues();
	 * values.put("title", book.getTitle()); // get title values.put("author",
	 * book.getAuthor()); // get author values.put(KEY_DATE, getDateTime()); int
	 * i = db.update(TABLE_BOOKS, // table values, // column/value KEY_ID +
	 * " = ?", // selections new String[] { String.valueOf(book.getId()) }); //
	 * selection // args db.close(); return i; }
	 * 
	 * public void deleteBook(Book book) { SQLiteDatabase db =
	 * this.getWritableDatabase(); db.delete(TABLE_BOOKS, KEY_ID + " = ?", new
	 * String[] { String.valueOf(book.getId()) }); db.close(); }
	 */

	/*
	 * public Book getBookWithFirstCharInTitle(char c) { SQLiteDatabase db =
	 * this.getReadableDatabase(); Cursor cursor = db.query(TABLE_BOOKS, // a.
	 * table COLUMNS, // b. column names "title like '" + c + "%'", // c.
	 * selections null, // e. group by null, // f. having null, // g. order by
	 * null); // h. limit if (cursor != null) cursor.moveToFirst(); Book book =
	 * new Book(); book.setId(Integer.parseInt(cursor.getString(0)));
	 * book.setTitle(cursor.getString(1)); book.setAuthor(cursor.getString(2));
	 * try { book.setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
	 * .parse(cursor.getString(3))); } catch (Exception e) { Log.v("test",
	 * "error parsing <" + cursor.getString(3) + ">"); } return book; }
	 */
}