package sm.mm.nicebody;


	import java.util.Date;
	import java.util.Random;

	public class RecordData {

		private int id;
		private int checkInt;


		public RecordData(){}

		public RecordData(int checkInt) {
			super();
			this.checkInt = checkInt;

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
		
	}

