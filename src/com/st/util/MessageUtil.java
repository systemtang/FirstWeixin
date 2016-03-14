package com.st.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.st.po.NewsMessage;
import com.st.po.TextMessage;
import com.thoughtworks.xstream.XStream;

public class MessageUtil {

	//用于转化的功能
	
	/**
	 * xml转化为map集合
	 * @param req
	 * @return
	 * @throws IOException
	 * @throws DocumentException
	 */
	public static Map<String,String> xmlToMap(HttpServletRequest req) throws IOException, DocumentException{
		Map<String,String> map = new HashMap<String,String>();  
		SAXReader reader = new SAXReader();
		InputStream is = req.getInputStream();
		Document doc = reader.read(is);
		Element root = doc.getRootElement();
		List<Element> list = root.elements();
		for (Element element : list) {
			map.put(element.getName(), element.getText());			
		}
		is.close();
		return map;
	}
	
	/**
	 * 将消息转化为xml
	 * @param message
	 * @return
	 */
	public static String textMessageToXml(TextMessage message){
		XStream stream = new XStream();
		stream.alias("xml", message.getClass());
		return stream.toXML(message);
	}
	
	
	public static String textNewsToXml(NewsMessage message){
		XStream stream = new XStream();
		stream.alias("xml", message.getClass());
		return stream.toXML(message);
	}
	
	//用于回复的模板
	
	/**
	 * 用户输入"1"时回复
	 */
	public static String returnOneMessage(){
		StringBuffer sb = new StringBuffer();
		sb.append("明天的天气是多云");
		return sb.toString();
	}
	
	/**
	 * 用户输入"2"时回复
	 */
	public static String returnTowMessage(){
		StringBuffer sb = new StringBuffer();
		sb.append("现在是【北京时间】："+new Date().toGMTString());
		return sb.toString();
	}
	
	public static String returnOtherMessage(String content){
		StringBuffer sb = new StringBuffer();
		sb.append("你输入的消息是：");
		sb.append(content);
		return sb.toString();
	}
}
