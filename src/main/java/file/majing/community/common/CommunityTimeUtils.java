package file.majing.community.common;

/**
 * 日期工具栏
 * Created by hechuan on 2019/9/4;
 */
public class CommunityTimeUtils {
	private static long nd = 1000 * 24 * 3600;
	private static long nh = 1000 * 3600;
	private static long nm = 1000 * 60;

	/**
	 * 计算时间戳的时间差
	 *
	 * @Author: hechuan on 2019/9/4 16:55
	 * @param: [oldTime, nowTime]
	 * @return: java.lang.String
	 */
	public static String getDatePoor(long oldTime, long nowTime) {
		long diff = nowTime - oldTime;
		long year = 0;
		long day = diff / nd;//计算相差多少天
		if (day > 365) {
			year = day / 365;
			day = day % 365;
		}
		long hour = diff % nd / nh;//计算差多少小时
		long min = diff % nd % nh / nm;//计算相差多少分钟
		return year > 0 ?
				year + "年" + day + "天" + hour + "小时" + min + "分钟" :
				day > 0 ? day + "天" + hour + "小时" + min + "分钟" : hour > 0 ? hour + "小时" + min + "分钟" : min + "分钟";
	}

}
