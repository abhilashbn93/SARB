package sarb.core.utils;

public class TimeLoggingUtil {
	
	TimeLoggingUtil() {
    }

    public static long logMethodTime(long lStartTime) {
        return (System.nanoTime() - lStartTime)/1000000;
    }

    public static long getStartTime() {
        return System.nanoTime();
    }
}
