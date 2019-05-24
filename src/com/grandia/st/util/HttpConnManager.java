package com.grandia.st.util;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public class HttpConnManager {

	private String httpUrl = "";

	private int connTimeOut = 30000;

	private int readTimeOut = 30000;

	public HttpConnManager(String url) {

		this.httpUrl = url;

	}

	public URLConnection getConnection() throws IOException {

		URL url = new URL(httpUrl);

		URLConnection conn = url.openConnection();

		conn.setConnectTimeout(connTimeOut);

		conn.setReadTimeout(readTimeOut);

		conn.setDoInput(true);

		conn.setDoOutput(true);

		conn.setUseCaches(true);

		conn.setRequestProperty("Keep-Alive", "");

		return conn;

	}

	public String getHttpUrl() {

		return httpUrl;

	}

}
