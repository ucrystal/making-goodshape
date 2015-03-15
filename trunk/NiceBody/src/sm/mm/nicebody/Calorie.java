package sm.mm.nicebody;

public class Calorie {

	private static double calCalorie;
	static ProfileData cal_pd = new ProfileData();

	public static double cal_fushUp(int exerciseNum) {

		cal_pd = Profile.db.getProfileData();
		calCalorie = cal_pd.getHeight() / 100 * cal_pd.getWeight() * (4.238) * exerciseNum;
		return calCalorie;
	}

	public static double cal_neeling(int exerciseNum) {

		cal_pd = Profile.db.getProfileData();
		calCalorie = cal_pd.getHeight() / 100 * cal_pd.getWeight()
				* (0.03503) * 4.2 * 2 * exerciseNum;
		return calCalorie;
	}

	public static double cal_legRaise(int exerciseNum) {

		cal_pd = Profile.db.getProfileData();
		// 0.6250*kg*m - 0.0210013*kg
		calCalorie = (cal_pd.getHeight() / 100 * cal_pd.getWeight() * (0.6250)) - ((0.0210013) *cal_pd.getWeight()) * exerciseNum;
		return calCalorie;
	}
}
