package sm.mm.nicebody;

import java.util.Date;
import java.util.Random;

public class RecommendData {

	private int id;
	private int s_check;
	private String t;

	public RecommendData() {
	}

	public RecommendData(int check) {
		super();
		this.s_check = check;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCheck() {
		return s_check;
	}

	public void setCheck(int s_check) {
		this.s_check = s_check;
	}

	public void setDate(String t) {
		this.t = t;
	}

	public String getDate() {
		return t;
	}

	@Override
	public String toString() {
		return "FreeData [id=" + id + ", check=" + s_check + ", t="
				+ t.toString() + "]";
	}
}
