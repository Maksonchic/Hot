package ru.probe.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;

import ru.probe.convertors.DateConvertUtils;

/**
 * My custom logger
 * @author maksim
 *
 */
public class Logger {

	@Value("${logger.path}")
	private String logPath;
	
	@Value("${logger.debugmode}")
	private boolean isDebug;
	
	/**
	 * available log record types
	 * @author maksim
	 *
	 */
	public enum logTypes {
		ERROR, WARNING, INFO
	}
	
	/**
	 * 
	 * @param message
	 * @param fileName	= deflog
	 * @param type		= INFO
	 */
	public void log(
			String message
		) {
		this.log(
				message,
				"deflog",
				logTypes.INFO
			);
	}
	
	/**
	 * 
	 * @param message
	 * @param fileName
	 * @param type		= INFO
	 */
	public void log(
			String message,
			String fileName
		) {
		this.log(
				message,
				fileName,
				logTypes.INFO
			);
	}
	
	/**
	 * 
	 * @param message
	 * @param fileName	= deflog
	 * @param type
	 */
	public void log(
			String message,
			logTypes type
		) {
		this.log(
				message,
				"deflog",
				type
			);
	}
	
	/**
	 * 
	 * @param message
	 * @param fileName
	 * @param type
	 */
	public void log(
				String message,
				String fileName,
				logTypes type
			) {
		
		if (this.isDebug) { System.out.println("Logger say: " + message); }
		
		try(FileOutputStream fos = new FileOutputStream(this.checkPath(fileName), true)) {
			
			fos.write(String.format("[%s]\t%s\t%s\r\n",
					DateConvertUtils.getRus(),
					type.name(),
					message
					).getBytes());
			
			fos.flush();
			
		} catch (IOException e) {
			if (this.isDebug) { System.err.println("Logger say: " + e.getMessage()); }
			e.printStackTrace();
		}
	}
	
	/**
	 * create if need full path and return a file
	 * @param fileName
	 * @return File target log
	 */
	private File checkPath(String fileName) {
		logPath = Strings.isBlank(logPath) ? "./" : logPath;
		logPath = logPath.substring(logPath.length() - 1).equals("/") ? logPath : (logPath + "/");
		
		File file = new File(String.format("%s%s.%s", logPath, fileName, "log"));
		file.getParentFile().mkdirs();
		
		return file;
	}
	
}
