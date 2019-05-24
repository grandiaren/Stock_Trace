package com.grandia.st.frame;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import com.grandia.st.util.STOCKFactoryCache;

public class STOCKBabySitter extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void init() throws ServletException {

		super.init();

		STOCKFactoryCache.init();

	}

	public void destroy() {

		STOCKFactoryCache.destroy();

		super.destroy();

	}

}
