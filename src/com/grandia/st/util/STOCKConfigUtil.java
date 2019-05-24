package com.grandia.st.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

public class STOCKConfigUtil implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private static Properties props = null;
	
	static {
		
		props = new Properties();
		
		InputStream is = null;
		
		try {
			
			is = getURL("config/property/stock-config.properties").openStream();
			
			props.load(is);
			
		} catch (IOException e) {
			
			e.printStackTrace();
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		} finally {
			
			if(is != null) {
				
				try {
					
					is.close();
					
				} catch (IOException e) {
					
					e.printStackTrace();
					
				}
			}
		}
	}
	public static String getProp(String key) {
		
		return props.getProperty(key);
		
	}

	public static URL getURL(String filename) {
		
		URL url = null;
		
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		
		url = cl.getResource(filename);
		
		if (null == url) {
			
			cl = STOCKConfigUtil.class.getClassLoader();
			
			url = cl.getResource(filename);
			
		}
		
		if (null == url) {
			
			url = ClassLoader.getSystemResource(filename);
			
		}
		
		if (null == url) {
			
			try {
				
				File file = new File(filename);
				
				if (file.exists()) {
					
					url = new File(filename).toURI().toURL();
					
				}
				
			} catch (MalformedURLException e) {
				
				e.printStackTrace();
				
			}
		}
		if (null != url) {
			
			//System.out.println(filename + " URL:" + url.toString());
			
		}
		
		return url;
	}
	
}
