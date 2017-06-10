package com.weinxin.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import com.weinxin.model02.Article;
import com.weinxin.model02.NewsMessage;
import com.weinxin.model02.TextMessage;


//����ĳЩ����ʱ�ľ�����Ϣ(��ǿ������ת����ʱ����������������)
@SuppressWarnings("unchecked")
public class MessageUtil {
	
	public static final String MESSAGE_TEXT = "text";
	public static final String MESSAGE_NEWS = "news";
	public static final String MESSAGE_IMADE = "image";
	public static final String MESSAGE_VOICE = "voice";
	public static final String MESSAGE_VIDEO = "video";
	public static final String MESSAGE_LINK = "link";
	public static final String MESSAGE_LOCATIONG = "location";
	public static final String MESSAGE_EVENT = "event";
	public static final String MESSAGE_SUBSCRIBE = "subscribe";
	public static final String MESSAGE_UNSUBSCRIBE = "unsubscribe";
	public static final String MESSAGE_CLICK = "click";
	public static final String MESSAGE_VIEW = "view";
	/**
	 * ����΢�ŷ���������XML��
	 *
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static Map<String,String> xmlToMap(HttpServletRequest request)
			throws IOException,DocumentException{
		// ����������洢��HashMap��
        Map<String,String> map = new HashMap<String,String>();

        // ��request��ȡ��������
        InputStream inputStream = request.getInputStream();
        // ��ȡ������
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        // �õ�xml��Ԫ��
        Element root = document.getRootElement();
        // �õ���Ԫ�ص������ӽڵ�
        List<Element> elementList = root.elements();

        // ���������ӽڵ�
        for (Element e : elementList){
            map.put(e.getName(),e.getText());
        }

        // �ͷ���Դ
        inputStream.close();
        inputStream = null;

        return map;
	}
	
	/**
     * �ı���Ϣ����ת����xml
     *
     * @param textMessage �ı���Ϣ����
     * @return xml
     */
	public static String textMessageToXml(TextMessage textMessage){
		XStream xstream = new XStream();
		xstream.alias("xml", textMessage.getClass());
		return xstream.toXML(textMessage);
	}
	
	public static String initText(String toUserName,String fromUserName,String content){
		TextMessage text = new TextMessage();
		text.setFromUserName(toUserName);
		text.setToUserName(fromUserName);
		text.setMsgType(MessageUtil.MESSAGE_TEXT);
		text.setCreateTime(new Date().getTime());
		text.setContent(content);
		return textMessageToXml(text);
	}
	
	/**
	 * ���˵�
	 */
	public static String menuText(){
		StringBuilder sb = new StringBuilder();
		sb.append("��ӭ���Ĺ�ע���밴�ղ˵���ʾ���в�����\n\n");
		sb.append("1���γ̽���\n");
		sb.append("2��Ľ��������\n\n");
		sb.append("�ظ����������˵���");
		return sb.toString();
	}
	
	public static String firstMenu(){
		StringBuilder sb = new StringBuilder();
		sb.append("���׿γ̽���΢�Ź��˺ſ�������Ҫ�漰���ںŽ��ܣ��༭ģʽ���ܣ�����ģʽ���ܵ�");
		return sb.toString();
	}
	
	public static String secondMenu(){
		StringBuilder sb = new StringBuilder();
		sb.append("Ľ�����Ǵ�ֱ�Ļ�����IT�������ѧϰ��վ���Զ�����Ƶ�̡̳����߱�̹��ߡ�ѧϰ�ƻ����ʴ�����Ϊ������ɫ��"
				+ "�����������ҵ���õĻ���������ţ�ˣ�Ҳ����ͨ����ѵ����߹�����Ƶ�γ�ѧϰ�������ȵĻ�����IT������");
		sb.append("Ľ�����γ̺���ǰ�˿�����PHP��Html5��Android��iOS��Swift��ITǰ�ؼ������ԣ����������γ̡�"
				+ "ʵ�ð������߼������������ͣ��ʺϲ�ͬ�׶ε�ѧϰ��Ⱥ���Դ��ɻ�������Ƶ����ʽΪƽ̨�ص㣬Ϊ��Уѧ����"
				+ "ְ�������ṩ��һ��Ѹ���������ܡ���ͬ���������ѧϰƽ̨��");
		return sb.toString();
	}
	/**
	 * ͼ����Ϣת��ΪXML
	 * @param newsMessage
	 * @return
	 */
	public static String newsMessageToXml(NewsMessage newsMessage){
		XStream xstream = new XStream();
		xstream.alias("xml", newsMessage.getClass());
		xstream.alias("item", new Article().getClass());
		return xstream.toXML(newsMessage);
	}
	/**
	 * ͼ����Ϣ��װ
	 * @param toUserName
	 * @param fromUserName
	 * @return
	 */
	public static String initNewsMessage(String toUserName,String fromUserName){
		String message = null;
		List<Article> list = new ArrayList<Article>();
		NewsMessage newsMessage = new NewsMessage();
		
		Article article = new Article();
		article.setTitle("Ľ��������");
		article.setDescription("Ľ�����Ǵ�ֱ�Ļ�����IT�������ѧϰ��վ���Զ�����Ƶ�̡̳����߱�̹��ߡ�ѧϰ�ƻ����ʴ�����Ϊ������ɫ��"
								+ "�����������ҵ���õĻ���������ţ�ˣ�Ҳ����ͨ����ѵ����߹�����Ƶ�γ�ѧϰ�������ȵĻ�����IT������");
		article.setPicUrl("http://weixincheng.ngrok.cc/images/001.png");
		article.setUrl("www.imooc.com");
		
		Article article2 = new Article();
		article2.setTitle("Spring����");
		article2.setDescription("Spring��һ����Դ��ܣ�Spring����2003 �������һ����������Java ������ܣ���Rod Johnson������"
								+ "����˵��Spring��һ���ֲ��JavaSE/EEfull-stack(һվʽ) ��������Դ��ܡ�");
		article2.setPicUrl("http://weixincheng.ngrok.cc/images/002.jpg");
		article2.setUrl("http://baike.baidu.com/link?url=X51mgA7ymKn_6durdToriZe-jhYjPwaKdb6b4iCHt677zpTddvgXYxaYxTh9oDxgciYr9fb5eFQkZizaldlqJa");
		
		list.add(article);
		list.add(article2);
		
		newsMessage.setToUserName(fromUserName); 
		newsMessage.setFromUserName(toUserName);
		newsMessage.setCreateTime(new Date().getTime());
		newsMessage.setMsgType(MESSAGE_NEWS);
		newsMessage.setArticles(list);
		newsMessage.setArticleCount(list.size());
		
		message = newsMessageToXml(newsMessage);
		return message;
	}
	
	/**
	 * �ж��Ƿ���QQ����
	 *
	 * @param content
	 * @return
	 */
	public static boolean isQqFace(String content) {
	        boolean result = false;

	        // �ж�QQ�����������ʽ
	        String qqfaceRegex = "/::\\)|/::~|/::B|/::\\||/:8-\\)|/::<|/::$|/::X|/::Z|/::'\\(|/::-\\||/::@|/::P|/::D|/::O|/::\\(|/::\\+|/:--b|/::Q|/::T|/:,@P|/:,@-D|/::d|/:,@o|/::g|/:\\|-\\)|/::!|/::L|/::>|/::,@|/:,@f|/::-S|/:\\?|/:,@x|/:,@@|/::8|/:,@!|/:!!!|/:xx|/:bye|/:wipe|/:dig|/:handclap|/:&-\\(|/:B-\\)|/:<@|/:@>|/::-O|/:>-\\||/:P-\\(|/::'\\||/:X-\\)|/::\\*|/:@x|/:8\\*|/:pd|/:<W>|/:beer|/:basketb|/:oo|/:coffee|/:eat|/:pig|/:rose|/:fade|/:showlove|/:heart|/:break|/:cake|/:li|/:bome|/:kn|/:footb|/:ladybug|/:shit|/:moon|/:sun|/:gift|/:hug|/:strong|/:weak|/:share|/:v|/:@\\)|/:jj|/:@@|/:bad|/:lvu|/:no|/:ok|/:love|/:<L>|/:jump|/:shake|/:<O>|/:circle|/:kotow|/:turn|/:skip|/:oY|/:#-0|/:hiphot|/:kiss|/:<&|/:&>";
	        Pattern p = Pattern.compile(qqfaceRegex);
	        Matcher m = p.matcher(content);
	        if (m.matches()) {
	        result = true;
	        }
	        return result;
	        }
	/**
	 * �ظ�QQ����
	 * @param toUserName
	 * @param fromUserName
	 * @param content
	 * @return
	 */
	public static String initQqFace(String toUserName,String fromUserName,String content){
		TextMessage text = new TextMessage();
		text.setFromUserName(toUserName);
		text.setToUserName(fromUserName);
		text.setMsgType(MessageUtil.MESSAGE_TEXT);
		text.setCreateTime(new Date().getTime());
		text.setContent(content);
		return textMessageToXml(text);
	}
	
	/**
     * ��չxstream��ʹ��֧��CDATA��
     *
     * @date 2013-05-19
     */
    private static XStream xstream = new XStream(new XppDriver() {
        public HierarchicalStreamWriter createWriter(Writer out) {
            return new PrettyPrintWriter(out) {
                // ������xml�ڵ��ת��������CDATA���
                boolean cdata = true;

                public void startNode(String name, Class clazz) {
                    super.startNode(name, clazz);
                }

                protected void writeText(QuickWriter writer, String text) {
                    if (cdata) {
                        writer.write("<![CDATA[");
                        writer.write(text);
                        writer.write("]]>");
                    } else {
                        writer.write(text);
                    }
                }
            };
        }
    });

}
