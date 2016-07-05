package models.scnu;

public class UplInfo {

	public long totalSize;
	public long currSize;
	public long starttime;
	public boolean aborted;

	public UplInfo() {
		totalSize = 0l;
		currSize = 0l;
		starttime = System.currentTimeMillis();
		aborted = false;
	}

	public UplInfo(int size) {
		totalSize = size;
		currSize = 0;
		starttime = System.currentTimeMillis();
		aborted = false;
	}

	public String getUprate() {
		long time = System.currentTimeMillis() - starttime;
		if (time != 0) {
			long uprate = currSize * 1000 / time;
			return convertFileSize(uprate) + "/s";
		}
		else return "n/a";
	}

	public int getPercent() {
		if (totalSize == 0) return 0;
		else return (int) (currSize * 100 / totalSize);
	}

	public String getTimeElapsed() {
		long time = (System.currentTimeMillis() - starttime) / 1000l;
		if (time - 60l >= 0){
			if (time % 60 >=10) return time / 60 + ":" + (time % 60) + "m";
			else return time / 60 + ":0" + (time % 60) + "m";
		}
		else return time<10 ? "0" + time + "s": time + "s";
	}

	public String getTimeEstimated() {
		if (currSize == 0) return "n/a";
		long time = System.currentTimeMillis() - starttime;
		time = totalSize * time / currSize;
		time /= 1000l;
		if (time - 60l >= 0){
			if (time % 60 >=10) return time / 60 + ":" + (time % 60) + "m";
			else return time / 60 + ":0" + (time % 60) + "m";
		}
		else return time<10 ? "0" + time + "s": time + "s";
	}
	
	/**
	 * This Method converts a byte size in a kbytes or Mbytes size, depending on the size
	 *     @param size The size in bytes
	 *     @return String with size and unit
	 */
	public static String convertFileSize(long size) {
		int divisor = 1;
		String unit = "bytes";
		if (size >= 1024 * 1024) {
			divisor = 1024 * 1024;
			unit = "MB";
		}
		else if (size >= 1024) {
			divisor = 1024;
			unit = "KB";
		}
		if (divisor == 1) return size / divisor + " " + unit;
		String aftercomma = "" + 100 * (size % divisor) / divisor;
		if (aftercomma.length() == 1) aftercomma = "0" + aftercomma;
		return size / divisor + "." + aftercomma + " " + unit;
	}


}