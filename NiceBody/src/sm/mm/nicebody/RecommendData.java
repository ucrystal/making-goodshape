package sm.mm.nicebody;


	import java.util.Date;
	import java.util.Random;

	public class RecommendData {

		private int id;
		private int s_check;
		private String t;
		
		public RecommendData(){}

		
		//이게 데이터 테이블 생성잔데, ID는 어떤식으로 입력받을지 안정했고, 
		//날짜는 DAFAULT로 들어간다고 가정했을때, 
		// 운동 type하고 운동 count(횟수)만 생성한다.
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

		public void setDate(String t){
			this.t=t;
		}
		
		public String getDate(){
			return t;
		}



		@Override
		public String toString() {
			return "FreeData [id=" + id+", check=" + s_check + ", t="+t.toString()
					+ "]";
		}
	}

