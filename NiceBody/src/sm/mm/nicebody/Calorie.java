package sm.mm.nicebody;

public class Calorie{

	private static double calCalorie;
	static ProfileData cal_pd = new ProfileData();
	
	public static double cal_fushUp(int exerciseNum) {

		cal_pd = Profile.db.getProfileData(); 
		calCalorie = cal_pd.getHeight() / 100
				* cal_pd.getWeight() * (0.0494) * 2
				* exerciseNum;
		return calCalorie;
	}
	
	public static double cal_lunge(int exerciseNum) {

		cal_pd = Profile.db.getProfileData(); 
		calCalorie = cal_pd.getHeight() / 100
				* cal_pd.getWeight() * (0.07639) * 2
				* exerciseNum;
		return calCalorie;
	}

	public static double cal_legRaise(int exerciseNum) {

		cal_pd = Profile.db.getProfileData(); 
		calCalorie = cal_pd.getHeight() / 100
				* cal_pd.getWeight() * (0.0211) * 2
				* exerciseNum;
		return calCalorie;
	}
}
