package com.st.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.DocumentException;

import com.st.po.TextMessage;
import com.st.util.CheakUtil;
import com.st.util.MessageUtil;

/**
 * Servlet implementation class weixinServlet
 */
@WebServlet("/weixinServlet")
public class weixinServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public weixinServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echostr = request.getParameter("echostr");
		
		PrintWriter pw = response.getWriter();
		if(CheakUtil.cheakSignature(signature, timestamp, nonce)){
			pw.write(echostr);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		
			PrintWriter pw = response.getWriter();
			
		try {
			Map<String,String> map = MessageUtil.xmlToMap(request);
			String toUserName = map.get("ToUserName");
			String fromUserName = map.get("FromUserName");
//			String createTime = map.get("CreateTime");
			String msgType = map.get("MsgType");
			String content = map.get("Content");
//			String msgId = map.get("MsgId");
			
			String mymessage = null;
			String finalInfo = null;
			if("text".equals(msgType)){
				if(content.equals("1")){
					mymessage = PrintInfo(toUserName, fromUserName, MessageUtil.returnOneMessage());
				}else if(content.equals("2")){
					mymessage = PrintInfo(toUserName, fromUserName, MessageUtil.returnTowMessage());
				}else{
					mymessage = PrintInfo(toUserName, fromUserName, MessageUtil.returnOtherMessage(content));
				}
			}
			pw.write(mymessage);
			
		} catch (DocumentException e) {
			e.printStackTrace();
		} finally {
			pw.close();
		}
		
		
	}

	//将回复方法提取出来
	private String PrintInfo(String toUserName, String fromUserName,
			String content) {
		String mymessage;
		TextMessage tm = new TextMessage();
		tm.setToUserName(fromUserName);
		tm.setFromUserName(toUserName);
		tm.setMsgType("text");
		tm.setCreateTime(new Date().getTime());
		tm.setContent(content);
		mymessage = MessageUtil.textMessageToXml(tm);
		
		System.out.println(mymessage);
		return mymessage;
	}

}
