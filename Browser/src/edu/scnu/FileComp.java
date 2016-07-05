/**
 * 
 */
package edu.scnu;

import java.io.File;
import java.util.Comparator;

/**
 * This class is a comparator to sort the filenames and dirs
 */
class FileComp implements Comparator {

	int mode;
	int sign;

	FileComp() {
		this.mode = 1;
		this.sign = 1;
	}

	/**
	 * @param mode sort by 1=Filename, 2=Size, 3=Date, 4=Type
	 * The default sorting method is by Name
	 * Negative mode means descending sort
	 */
	FileComp(int mode) {
		if (mode < 0) {
			this.mode = -mode;
			sign = -1;
		}
		else {
			this.mode = mode;
			this.sign = 1;
		}
	}

	public int compare(Object o1, Object o2) {
		File f1 = (File) o1;
		File f2 = (File) o2;
		if (f1.isDirectory()) {
			if (f2.isDirectory()) {
				switch (mode) {
				//Filename or Type
				case 1:
				case 4:
					return sign
							* f1.getAbsolutePath().toUpperCase().compareTo(
									f2.getAbsolutePath().toUpperCase());
				//Filesize
				case 2:
					return sign * (new Long(f1.length()).compareTo(new Long(f2.length())));
				//Date
				case 3:
					return sign
							* (new Long(f1.lastModified())
									.compareTo(new Long(f2.lastModified())));
				default:
					return 1;
				}
			}
			else return -1;
		}
		else if (f2.isDirectory()) return 1;
		else {
			switch (mode) {
			case 1:
				return sign
						* f1.getAbsolutePath().toUpperCase().compareTo(
								f2.getAbsolutePath().toUpperCase());
			case 2:
				return sign * (new Long(f1.length()).compareTo(new Long(f2.length())));
			case 3:
				return sign
						* (new Long(f1.lastModified()).compareTo(new Long(f2.lastModified())));
			case 4: { // Sort by extension
				int tempIndexf1 = f1.getAbsolutePath().lastIndexOf('.');
				int tempIndexf2 = f2.getAbsolutePath().lastIndexOf('.');
				if ((tempIndexf1 == -1) && (tempIndexf2 == -1)) { // Neither have an extension
					return sign
							* f1.getAbsolutePath().toUpperCase().compareTo(
									f2.getAbsolutePath().toUpperCase());
				}
				// f1 has no extension
				else if (tempIndexf1 == -1) return -sign;
				// f2 has no extension
				else if (tempIndexf2 == -1) return sign;
				// Both have an extension
				else {
					String tempEndf1 = f1.getAbsolutePath().toUpperCase()
							.substring(tempIndexf1);
					String tempEndf2 = f2.getAbsolutePath().toUpperCase()
							.substring(tempIndexf2);
					return sign * tempEndf1.compareTo(tempEndf2);
				}
			}
			default:
				return 1;
			}
		}
	}
}
