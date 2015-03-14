package sm.mm.nicebody;

import java.util.Date;
import java.util.Random;

public class RecordData {

	private int id;
	private int checkInt;
	private String recordDate;

	public RecordData() {
	}

	public RecordData(int checkInt, String recordDate) {
		super();
		this.checkInt = checkInt;
		this.recordDate = recordDate;

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCheckInt() {
		return checkInt;
	}

	public void setCheckInt(int checkInt) {
		this.checkInt = checkInt;
	}

	public String getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(String recordDate) {
		this.recordDate = recordDate;
	}

}
