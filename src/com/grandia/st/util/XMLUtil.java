package com.grandia.st.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bocom.jump.bp.core.CoreException;
import com.grandia.st.common.STOCKErrorCode;

public class XMLUtil {

	private static Logger logger = LoggerFactory.getLogger(XMLUtil.class);

	public static String convert(String str) {

		if ("".equals(str)) {

			return null;
		}

		return str;

	}

	@SuppressWarnings("rawtypes")
	public static String mapToXml(HashMap map) {

		logger.debug("GSRA mapToXml().map:" + map);

		String result = null;

		StringBuffer sb = new StringBuffer();

		// 此处调用map转化为XML方法
		MapToXml(map, sb);

		result = sb.toString();

		logger.debug("GSRA mapToXml().xml:" + result);

		return result;
	}

	// 使输入的xml文件内容转化得出map
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static HashMap xmlToMap(String xml) throws CoreException {

		logger.debug("GSRA xmlToMap().xml:" + xml);

		HashMap map = new HashMap();

		try {

			xml = xml.substring(xml.indexOf("<"), xml.lastIndexOf(">") + 1);

			// //jdom解析器 解析XML形式的文本,得到document对象
			Document doc = DocumentHelper.parseText(xml);

			if (null == doc) {

				return map;

			}

			// 获得XML的根元素
			Element root = doc.getRootElement();

			for (Iterator iterator = root.elementIterator(); iterator.hasNext();) {

				List mapList = new ArrayList();

				Element e = (Element) iterator.next();

				List list = e.elements();

				if (list.size() > 0) {

					Map m = XmlFileToMap(e);

					if (map.get(e.getName()) != null) {

						Object obj = map.get(e.getName());

						if (!obj.getClass().getName().equals("java.util.ArrayList")) {

							mapList = new ArrayList();

							mapList.add(obj);

							mapList.add(m);

						}

						if (obj.getClass().getName().equals("java.util.ArrayList")) {

							mapList = (List) obj;

							mapList.add(m);

						}

						map.put(e.getName(), mapList);

					} else {

						map.put(e.getName(), m);

					}

				} else {

					// 如果e.getText为""串，则传化为null
					map.put(e.getName(), convert(e.getText()));

				}

			}

			// 如果count为1，将map转化为list
			if (map.containsKey("Count")) {

				if (null == map.get("Count") || Integer.parseInt((String) map.get("Count")) <= 1) {

					List maptolist = new ArrayList();

					Iterator it = map.keySet().iterator();

					while (it.hasNext()) {

						String key = it.next().toString();

						if (!"Count".equals(key)) {

							// System.out.println("keykeykeykeykeykeykeykeykeykeykeykeykeykeykeykeyk"+key);
							Object obj = map.get(key);

							if (obj == null)
								continue;

							if (obj.getClass().getName().equals("java.util.HashMap")) {

								maptolist = new ArrayList();

								maptolist.add(obj);

								map.put(key, maptolist);

							}

						}
					}
				}
			}

		} catch (Exception e) {

			// logger.debug("error:GSRA xmlToMap().xml:"+xml);

			e.printStackTrace();

			CoreException coreException = new CoreException(STOCKErrorCode.PARSE_XML_ERROR_CODE);

			throw coreException;

		}

		return map;

	}

	// Map转化为xml文件
	@SuppressWarnings("rawtypes")
	public static void MapToXml(HashMap map, StringBuffer sbu) {

		Set set = map.keySet();

		for (Iterator it = set.iterator(); it.hasNext();) {

			String key = (String) it.next();

			Object value = map.get(key);

			if (null == value) {

				value = "";

			}

			if (value.getClass().getName().equals("java.util.ArrayList")) {

				ArrayList list = (ArrayList) map.get(key);

				for (int i = 0; i < list.size(); i++) {

					sbu.append("<" + key + ">");

					if (list.get(i).getClass().getName().equals("java.lang.String")) {

						sbu.append(list.get(i));

					} else {

						HashMap hm = (HashMap) list.get(i);

						MapToXml(hm, sbu);

					}

					sbu.append("</" + key + ">");
				}

			} else {

				if (value instanceof HashMap) {

					sbu.append("<" + key + ">");

					if (value.getClass().getName().equals("java.lang.String")) {

						sbu.append(value);

					} else {

						MapToXml((HashMap) (value), sbu);

					}

					sbu.append("</" + key + ">");

				} else {

					sbu.append("<" + key + ">" + value + "</" + key + ">");

				}
			}
		}

	}

	// 使用递归算法将 目前扫描节点存入map
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map XmlFileToMap(Element e) {

		Map map = new HashMap();

		List list = e.elements();

		if (list.size() > 0) {

			for (int i = 0; i < list.size(); i++) {

				Element iter = (Element) list.get(i);

				List mapList = new ArrayList();

				if (iter.elements().size() > 0) {

					// 递归调用
					Map m = XmlFileToMap(iter);

					// 若已存在键值，说明是list
					if (map.get(iter.getName()) != null) {

						Object obj = map.get(iter.getName());

						if (!obj.getClass().getName().equals("java.util.ArrayList")) {

							mapList = new ArrayList();

							mapList.add(obj);

							mapList.add(m);

						}

						if (obj.getClass().getName().equals("java.util.ArrayList")) {

							mapList = (List) obj;

							mapList.add(m);

						}

						map.put(iter.getName(), mapList);

					} else {

						map.put(iter.getName(), m);

					}

				} else {

					if (map.get(iter.getName()) != null) {

						Object obj = map.get(iter.getName());

						if (!obj.getClass().getName().equals("java.util.ArrayList")) {

							mapList = new ArrayList();

							mapList.add(obj);

							mapList.add(convert(iter.getText()));

						}

						if (obj.getClass().getName().equals("java.util.ArrayList")) {

							mapList = (List) obj;

							mapList.add(convert(iter.getText()));

						}

						map.put(iter.getName(), mapList);

					} else {

						map.put(iter.getName(), convert(iter.getText()));

					}

				}
			}
		} else {

			map.put(e.getName(), convert(e.getText()));

		}

		// 如果count为1，将map转化为list
		if (map.containsKey("Count")) {

			if (null == map.get("Count") || Integer.parseInt((String) map.get("Count")) <= 1) {

				List maptolist = new ArrayList();

				Iterator it = map.keySet().iterator();

				while (it.hasNext()) {

					String key = it.next().toString();

					if (!"Count".equals(key)) {

						Object obj = map.get(key);

						if (obj == null)
							continue;

						if (obj.getClass().getName().equals("java.util.HashMap")) {

							// System.out.println("key==========="+key);
							// System.out.println("Object==========="+obj.toString());

							maptolist = new ArrayList();

							maptolist.add(obj);

							map.put(key, maptolist);

						}

					}
				}
			}
		}

		return map;

	}
}