package sm.mm.schedule_manager;


	import java.util.Date;
	import java.util.Random;

	public class ProfileData {

		private int id;
		private String name;
		private String univ;
		private String phone;
		private byte[] photo;

		public ProfileData(){}

		public ProfileData(String name, String univ, String phone, byte[] blob) {
			super();
			this.name = name;
			this.univ = univ;
			this.phone = phone;
			this.photo = blob;
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
		
		public String getUniv() {
			return univ;
		}
		
		public void setUniv(String univ) {
			this.univ = univ;
		}
		
		public String getPhone() {
			return phone;
		}
		
		public void setPhone(String phone) {
			this.phone = phone;
		}
		
		public byte[] getPhoto() {
			return photo;
		}
		
		public void setPhoto(byte[] blob) {
			this.photo = blob;
		}

	}

