package com.grandia.st.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import com.bocom.jump.bp.channel.http.support.JsonUtils;
import com.bocom.jump.bp.core.CoreException;
import com.grandia.st.common.STOCKConstant;
import com.grandia.st.common.STOCKErrorCode;

public class STOCKTcpJson {

	@SuppressWarnings("rawtypes")
	public static HashMap sendMsg(HashMap reqMap) throws CoreException {

		HashMap rspMap = new HashMap();

		Socket socket = new Socket();

		String reqMsg = null;

		try {

			reqMsg = new String(JsonUtils.jsonFromObject(reqMap, STOCKConstant.DATA_CHANNSET),
					STOCKConstant.DATA_CHANNSET_STANDARD);

		} catch (UnsupportedEncodingException e1) {
			// TODO 自动生成 catch 块
			e1.printStackTrace();

		}

		try {

			socket.connect(
					new InetSocketAddress(STOCKConfigUtil.getProp("APP_IP"),
							Integer.parseInt(STOCKConfigUtil.getProp("APP_PORT"))),
					Integer.parseInt(STOCKConfigUtil.getProp("TIMEOUT")));

			DataOutputStream oos = new DataOutputStream(socket.getOutputStream());

			int length = reqMsg.getBytes(STOCKConstant.DATA_CHANNSET).length;

			String len = convertLength(length);

			byte[] bytes = (len + reqMsg).getBytes(STOCKConstant.DATA_CHANNSET_STANDARD);

			oos.write(bytes);

			oos.flush();

			DataInputStream ois = new DataInputStream(socket.getInputStream());

			byte[] lengthBytes = new byte[8];

			ois.readFully(lengthBytes);

			int lenb = Integer.parseInt(new String(lengthBytes, STOCKConstant.DATA_CHANNSET_STANDARD));

			byte[] leftBytes = new byte[lenb];

			ois.readFully(leftBytes);

			rspMap = JsonUtils.objectFromJson(new String(leftBytes, STOCKConstant.DATA_CHANNSET_STANDARD),
					HashMap.class);

		} catch (Exception e) {

			CoreException exception = new CoreException(STOCKErrorCode.ROUTE_CONN_ERROR_CODE, e);

			throw exception;

		} finally {

			try {

				if (socket != null)

					socket.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}

		}

		return rspMap;

	}

	private static String convertLength(int length) {

		StringBuffer str = new StringBuffer(STOCKConstant.EMPTY_VALUE + length);

		while (str.length() < 8) {

			str.insert(0, '0');

		}

		return str.toString();
	}

	public static void main(String[] args) {

		try {
			InetAddress addr = InetAddress.getLocalHost();

			System.out.println(addr.getHostAddress());
			;
		} catch (UnknownHostException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}

	}

}
