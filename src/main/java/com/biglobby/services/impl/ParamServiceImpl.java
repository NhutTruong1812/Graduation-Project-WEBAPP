package com.biglobby.services.impl;

import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ParamServiceImpl {
	@Autowired
	HttpServletRequest request;
	@Autowired
	ServletContext app;

	/**
	 * Get parameter's value
	 * 
	 * @param name
	 * @param defaultValue
	 * @return value or defaultValue
	 */
	public String getString(String name, String defaultValue) {
		String param = request.getParameter(name);
		return param == null ? defaultValue : param;
	}

	/**
	 * Get value of Integer parameter
	 * 
	 * @param name
	 * @param defaultValue
	 * @return value or defaultValue
	 */
	public Integer getInt(String name, Integer defaultValue) {
		String stringParam = request.getParameter(name);
		try {
			return Integer.parseInt(stringParam);
		} catch (Exception e) {
			e.printStackTrace();
			return defaultValue;
		}
	}

	/**
	 * Get value of double parameter
	 * 
	 * @param name
	 * @param defaultValue
	 * @return value or defaultValue
	 */
	public Double getDouble(String name, Double defaultValue) {
		String stringParam = request.getParameter(name);
		try {
			return Double.parseDouble(stringParam);
		} catch (Exception e) {
			e.printStackTrace();
			return defaultValue;
		}
	}

	/**
	 * Get value of Boolean parameter
	 * 
	 * @param name
	 * @param defaultValue
	 * @return value or defaultValue
	 */
	public Boolean getBoolean(String name, Boolean defaultValue) {
		String stringParam = request.getParameter(name);
		try {
			return Boolean.parseBoolean(stringParam);
		} catch (Exception e) {
			e.printStackTrace();
			return defaultValue;
		}
	}

	/**
	 * Get value of Date parameter
	 * 
	 * @param name
	 * @param pattern
	 * @return value or null
	 * @throws RuntimeException
	 */
	public Date getDate(String name, String pattern) throws RuntimeException {
		SimpleDateFormat sdf = new SimpleDateFormat();
		String stringParam = request.getParameter(name);
		sdf.applyPattern(pattern);
		try {
			Date date = sdf.parse(stringParam);
			return date == null ? null : date;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Save file to path
	 * 
	 * @param file
	 * @param path
	 * @return File was save
	 */

	public File save(MultipartFile file, String folder) throws RuntimeException {
		File dir = new File(app.getRealPath(folder));
		if (!dir.exists()) {
			dir.mkdirs();
		}
		try {
			String fn = System.currentTimeMillis() + file.getOriginalFilename();
			String filename = Integer.toHexString(fn.hashCode()) + fn.substring(fn.lastIndexOf("."));
			File saveFile = new File(dir, filename);
			file.transferTo(saveFile);

			InputStream rs = app.getResourceAsStream(folder + "/" + saveFile.getName());
			while (rs == null) {
				rs = app.getResourceAsStream(folder + "/" + saveFile.getName());
			}
			return saveFile;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
