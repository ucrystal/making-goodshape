package sm.mm.nicebody;

public class Calorie{

	private static double calCalorie;

	public static double cal_fushUp(int exerciseNum) {

		calCalorie = Double.parseDouble(Profile_modify.Height) / 100
				* Double.parseDouble(Profile_modify.Weight) * (0.0494) * 2
				* exerciseNum;
		return calCalorie;
	}
	
	public static double cal_lunge(int exerciseNum) {

		calCalorie = Double.parseDouble(Profile_modify.Height) / 100
				* Double.parseDouble(Profile_modify.Weight) * (0.07639) * 2
				* exerciseNum;
		return calCalorie;
	}

	public static double cal_legRaise(int exerciseNum) {

		calCalorie = Double.parseDouble(Profile_modify.Height) / 100
				* Double.parseDouble(Profile_modify.Weight) * (0.0211) * 2
				* exerciseNum;
		return calCalorie;
	}
}
