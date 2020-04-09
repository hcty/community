package file.majing.community;

import org.junit.Test;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Created by hechuan on 2020/4/9;
 */
public class TimeJunitTest {
	@Test
	public void testTime(){
		long ins=1586422978945L;
		Instant timestamp = Instant.ofEpochMilli(ins);
		ZonedDateTime losAngelesTime = timestamp.atZone(ZoneId.of("Asia/Shanghai"));
		System.out.println(losAngelesTime.toLocalDateTime());
	}
}
