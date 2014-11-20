package sm.mm.nicebody;


	import java.util.Date;
	import java.util.Random;

	public class FreeData {

		private int id;
		private int type;
		private int count;
		private String t;
		
		public FreeData(){}

		
		//이게 데이터 테이블 생성잔데, ID는 어떤식으로 입력받을지 안정했고, 
		//날짜는 DAFAULT로 들어간다고 가정했을때, 
		// 운동 type하고 운동 count(횟수)만 생성한다.
		public FreeData(int type, int count) {
			super();
			this.type = type;
			this.count = count;
		}

		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public int getType() {
			return type;
		}
		public void setType(int type) {
			this.type = type;
		}
		
		public int getCount() {
			return count;
		}
		
		public void setCount(int count) {
			this.count = count;
		}

		public void setDate(String t){
			this.t=t;
		}
		
		public String getDate(){
			return t;
		}



		@Override
		public String toString() {
			return "FreeData [id=" + id+", type=" + type + ", count=" + count+", t="+t.toString()
					+ "]";
		}
	}

