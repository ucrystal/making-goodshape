package sm.mm.nicebody;

public class Calorie {

	/*
	 * 
	- 닐링레그리프트
	  0.420442092*kg*m
	- 푸시업
	  4.238*kg*m
	- 레그레이즈
	  0.6250*kg*m - 0.0210013*kg

	 */
	private static double calCalorie;
	static ProfileData cal_pd = new ProfileData();

	public static double cal_pushUp(int exerciseNum) {

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
		calCalorie = ((cal_pd.getHeight() / 100 * (0.6250)) - ((0.23)*(0.09131))) * cal_pd.getWeight() * exerciseNum;
		return calCalorie;
	}
}
