package com.tools;

import java.util.*;
import java.net.*;
import java.text.*;
import java.util.zip.*;

import models.scnu.UplInfo;

import java.io.*;


public class Helper {
	//FEATURES
    public static final boolean NATIVE_COMMANDS = false;
	/**
	*If true, all operations (besides upload and native commands) 
	*which change something on the file system are permitted
	*/
	public static final boolean READ_ONLY = false;
	//If true, uploads are allowed even if READ_ONLY = true
	public static final boolean ALLOW_UPLOAD = true;

    //Allow browsing and file manipulation only in certain directories
	public static final boolean RESTRICT_BROWSING = true;
    //If true, the user is allowed to browse only in RESTRICT_PATH,
    //if false, the user is allowed to browse all directories besides RESTRICT_PATH
    public static final boolean RESTRICT_WHITELIST = true;
    //Paths, sperated by semicolon
    //public static final String RESTRICT_PATH = "C:\\CODE;E:\\"; //Win32: Case important!!
    public static final String BASE_PATH="/root/gc/docs";
	public static final String RESTRICT_PATH = BASE_PATH;
    
    

    //The refresh time in seconds of the upload monitor window
	public static final int UPLOAD_MONITOR_REFRESH = 2;
	//The number of colums for the edit field
	public static final int EDITFIELD_COLS = 85;
	//The number of rows for the edit field
	public static final int EDITFIELD_ROWS = 30;
	//Open a new window to view a file
	public static final boolean USE_POPUP = false;
	/**
	 * If USE_DIR_PREVIEW = true, then for every directory a tooltip will be
	 * created (hold the mouse over the link) with the first DIR_PREVIEW_NUMBER entries.
	 * This can yield to performance issues. Turn it off, if the directory loads to slow.
	 */
	public static final boolean USE_DIR_PREVIEW = false;
	public static final int DIR_PREVIEW_NUMBER = 10;
	/**
	 * The name of an optional CSS Stylesheet file
	 */
	public static final String CSS_NAME = "Browser.css";
	/**
	 * The compression level for zip file creation (0-9)
	 * 0 = No compression
	 * 1 = Standard compression (Very fast)
	 * ...
	 * 9 = Best compression (Very slow)
	 */
	public static final int COMPRESSION_LEVEL = 1;
	/**
	 * The FORBIDDEN_DRIVES are not displayed on the list. This can be usefull, if the
	 * server runs on a windows platform, to avoid a message box, if you try to access
	 * an empty removable drive (See KNOWN BUGS in Readme.txt).
	 */
	public static final String[] FORBIDDEN_DRIVES = {"a:\\"};

	/**
	 * Command of the shell interpreter and the parameter to run a programm
	 */
	public static final String[] COMMAND_INTERPRETER = {"cmd", "/C"}; // Dos,Windows
	//public static final String[] COMMAND_INTERPRETER = {"/bin/sh","-c"}; 	// Unix

	/**
	 * Max time in ms a process is allowed to run, before it will be terminated
	 */
	public static final long MAX_PROCESS_RUNNING_TIME = 30 * 1000; //30 seconds

	//Button names
	public static final String SAVE_AS_ZIP = "Download selected files as (z)ip";
	public static final String RENAME_FILE = "(R)ename File";
	public static final String DELETE_FILES = "(Del)ete selected files";
	public static final String CREATE_DIR = "Create (D)ir";
	public static final String CREATE_FILE = "(C)reate File";
	public static final String MOVE_FILES = "(M)ove Files";
	public static final String COPY_FILES = "Cop(y) Files";
	public static final String LAUNCH_COMMAND = "(L)aunch external program";
	public static final String UPLOAD_FILES = "Upload";

	//Normally you should not change anything after this line
	//----------------------------------------------------------------------------------
	//Change this to locate the tempfile directory for upload (not longer needed)
	public static String tempdir = ".";
	public static String VERSION_NR = "1.2";
	public static DateFormat dateFormat = DateFormat.getDateTimeInstance();
	
	public static class UploadMonitor {

		public static Hashtable uploadTable = new Hashtable();

		public static void set(String fName, UplInfo info) {
			uploadTable.put(fName, info);
		}

		public static void remove(String fName) {
			uploadTable.remove(fName);
		}

		public static UplInfo getInfo(String fName) {
			UplInfo info = (UplInfo) uploadTable.get(fName);
			return info;
		}
	}
	
	
	public static Vector expandFileList(String[] files, boolean inclDirs) {
		Vector v = new Vector();
		if (files == null) return v;
		for (int i = 0; i < files.length; i++)
			v.add(new File(URLDecoder.decode(files[i])));
		for (int i = 0; i < v.size(); i++) {
			File f = (File) v.get(i);
			if (f.isDirectory()) {
				File[] fs = f.listFiles();
				for (int n = 0; n < fs.length; n++)
					v.add(fs[n]);
				if (!inclDirs) {
					v.remove(i);
					i--;
				}
			}
		}
		return v;
	}

	/**
	 * Method to build an absolute path
	 * @param dir the root dir
	 * @param name the name of the new directory
	 * @return if name is an absolute directory, returns name, else returns dir+name
	 */
	public static String getDir(String dir, String name) {
		if (!dir.endsWith(File.separator)) dir = dir + File.separator;
		File mv = new File(name);
		String new_dir = null;
		if (!mv.isAbsolute()) {
			new_dir = dir + name;
		}
		else new_dir = name;
		return new_dir;
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

	/**
	 * Copies all data from in to out
	 * 	@param in the input stream
	 *	@param out the output stream
	 *	@param buffer copy buffer
	 */
	public static void copyStreams(InputStream in, OutputStream out, byte[] buffer) throws IOException {
		copyStreamsWithoutClose(in, out, buffer);
		in.close();
		out.close();
	}

	/**
	 * Copies all data from in to out
	 * 	@param in the input stream
	 *	@param out the output stream
	 *	@param buffer copy buffer
	 */
	public static void copyStreamsWithoutClose(InputStream in, OutputStream out, byte[] buffer)
			throws IOException {
		int b;
		while ((b = in.read(buffer)) != -1)
			out.write(buffer, 0, b);
	}

	/**
	 * Returns the Mime Type of the file, depending on the extension of the filename
	 */
	public static String getMimeType(String fName) {
		fName = fName.toLowerCase();
		if (fName.endsWith(".jpg") || fName.endsWith(".jpeg") || fName.endsWith(".jpe")) return "image/jpeg";
		else if (fName.endsWith(".gif")) return "image/gif";
		else if (fName.endsWith(".pdf")) return "application/pdf";
		else if (fName.endsWith(".htm") || fName.endsWith(".html") || fName.endsWith(".shtml")) return "text/html";
		else if (fName.endsWith(".avi")) return "video/x-msvideo";
		else if (fName.endsWith(".mov") || fName.endsWith(".qt")) return "video/quicktime";
		else if (fName.endsWith(".mpg") || fName.endsWith(".mpeg") || fName.endsWith(".mpe")) return "video/mpeg";
		else if (fName.endsWith(".zip")) return "application/zip";
		else if (fName.endsWith(".tiff") || fName.endsWith(".tif")) return "image/tiff";
		else if (fName.endsWith(".rtf")) return "application/rtf";
		else if (fName.endsWith(".mid") || fName.endsWith(".midi")) return "audio/x-midi";
		else if (fName.endsWith(".xl") || fName.endsWith(".xls") || fName.endsWith(".xlv")
				|| fName.endsWith(".xla") || fName.endsWith(".xlb") || fName.endsWith(".xlt")
				|| fName.endsWith(".xlm") || fName.endsWith(".xlk")) return "application/excel";
		else if (fName.endsWith(".doc") || fName.endsWith(".dot")) return "application/msword";
		else if (fName.endsWith(".png")) return "image/png";
		else if (fName.endsWith(".xml")) return "text/xml";
		else if (fName.endsWith(".svg")) return "image/svg+xml";
		else if (fName.endsWith(".mp3")) return "audio/mp3";
		else if (fName.endsWith(".ogg")) return "audio/ogg";
		else return "text/plain";
	}

	/**
	 * Converts some important chars (int) to the corresponding html string
	 */
	public static String conv2Html(int i) {
		if (i == '&') return "&amp;";
		else if (i == '<') return "&lt;";
		else if (i == '>') return "&gt;";
		else if (i == '"') return "&quot;";
		else return "" + (char) i;
	}

	/**
	 * Converts a normal string to a html conform string
	 */
	public static String conv2Html(String st) {
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < st.length(); i++) {
			buf.append(conv2Html(st.charAt(i)));
		}
		return buf.toString();
	}

	/**
	 * Starts a native process on the server
	 * 	@param command the command to start the process
	 *	@param dir the dir in which the process starts
	 */
	public static String startProcess(String command, String dir) throws IOException {
		StringBuffer ret = new StringBuffer();
		String[] comm = new String[3];
		comm[0] = COMMAND_INTERPRETER[0];
		comm[1] = COMMAND_INTERPRETER[1];
		comm[2] = command;
		long start = System.currentTimeMillis();
		try {
			//Start process
			Process ls_proc = Runtime.getRuntime().exec(comm, null, new File(dir));
			//Get input and error streams
			BufferedInputStream ls_in = new BufferedInputStream(ls_proc.getInputStream());
			BufferedInputStream ls_err = new BufferedInputStream(ls_proc.getErrorStream());
			boolean end = false;
			while (!end) {
				int c = 0;
				while ((ls_err.available() > 0) && (++c <= 1000)) {
					ret.append(conv2Html(ls_err.read()));
				}
				c = 0;
				while ((ls_in.available() > 0) && (++c <= 1000)) {
					ret.append(conv2Html(ls_in.read()));
				}
				try {
					ls_proc.exitValue();
					//if the process has not finished, an exception is thrown
					//else
					while (ls_err.available() > 0)
						ret.append(conv2Html(ls_err.read()));
					while (ls_in.available() > 0)
						ret.append(conv2Html(ls_in.read()));
					end = true;
				}
				catch (IllegalThreadStateException ex) {
					//Process is running
				}
				//The process is not allowed to run longer than given time.
				if (System.currentTimeMillis() - start > MAX_PROCESS_RUNNING_TIME) {
					ls_proc.destroy();
					end = true;
					ret.append("!!!! Process has timed out, destroyed !!!!!");
				}
				try {
					Thread.sleep(50);
				}
				catch (InterruptedException ie) {}
			}
		}
		catch (IOException e) {
			ret.append("Error: " + e);
		}
		return ret.toString();
	}

	/**
	 * Converts a dir string to a linked dir string
	 * 	@param dir the directory string (e.g. /usr/local/httpd)
	 *	@param browserLink web-path to Browser.jsp
	 */
	public static String dir2linkdir(String dir, String browserLink, int sortMode) {
		File f = new File(dir);
		StringBuffer buf = new StringBuffer();
		while (f.getParentFile() != null) {
			if (f.canRead()) {
				try{
					String encPath = URLEncoder.encode(f.getAbsolutePath(),"UTF-8");
					buf.insert(0, "<a href=\"" + browserLink + "?sort=" + sortMode + "&amp;dir="
					+ encPath + "\">" + conv2Html(f.getName()) + File.separator + "</a>");
				}catch(Exception e)
				{}
			}
			else buf.insert(0, conv2Html(f.getName()) + File.separator);
			f = f.getParentFile();
		}
		if (f.canRead()) {
			try{
				String encPath = URLEncoder.encode(f.getAbsolutePath(),"UTF-8");
				buf.insert(0, "<a href=\"" + browserLink + "?sort=" + sortMode + "&amp;dir=" + encPath
				+ "\">" + conv2Html(f.getAbsolutePath()) + "</a>");
			}catch(Exception e)
			{
			
			}

		}
		else buf.insert(0, f.getAbsolutePath());
		return buf.toString();
	}

	/**
	 *	Returns true if the given filename tends towards a packed file
	 */
	public static boolean isPacked(String name, boolean gz) {
		return (name.toLowerCase().endsWith(".zip") || name.toLowerCase().endsWith(".jar")
				|| (gz && name.toLowerCase().endsWith(".gz")) || name.toLowerCase()
				.endsWith(".war"));
	}

	/**
	 *	If RESTRICT_BROWSING = true this method checks, whether the path is allowed or not
	 */
	public static boolean isAllowed(File path, boolean write) throws IOException{
		if (READ_ONLY && write) return false;
		if (RESTRICT_BROWSING) {
            StringTokenizer stk = new StringTokenizer(RESTRICT_PATH, ";");
            while (stk.hasMoreTokens()){
			    if (path!=null && path.getCanonicalPath().startsWith(stk.nextToken()))
                    return RESTRICT_WHITELIST;
            }
            return !RESTRICT_WHITELIST;
		}
		else return true;
	}
	

}
