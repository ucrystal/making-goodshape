package sm.mm.nicebody;


	import java.util.Date;
	import java.util.Random;

	public class ProfileData {

		private int id;
		private String name;
		private int height;
		private int weight;

		public ProfileData(){}

		public ProfileData(String name, int height, int weight) {
			super();
			this.name = name;
			this.height = height;
			this.weight = weight;
		}

		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		
		public double getHeight() {
			return height;
		}
		
		public void setHeight(int height) {
			this.height = height;
		}
		
		public double getWeight() {
			return weight;
		}
		
		public void setWeight(int weight) {
			this.weight = weight;
		}

	}

