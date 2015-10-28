package com.thd.wx.util;


import com.thd.wx.index.message.resp.NewsMessage;
import com.thd.wx.index.message.resp.TextMessage;
import com.thd.wx.index.message.resp.pojo.Article;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 核心服务类，处理微信发来的请求
 */
public class CoreService {
    /**
     * 处理微信发来的请求
     */
    public static String processRequest(HttpServletRequest request, HttpServletResponse response) {
        String respMessage = null;
        try {
            // 默认返回的文本消息内容
            StringBuilder respContent = new StringBuilder();
            // xml请求解析
            Map<String, String> requestMap = MessageUtil.parseXml(request);
            // 发送方帐号（open_id）
            String fromUserName = requestMap.get("FromUserName");
            // 公众帐号
            String toUserName = requestMap.get("ToUserName");
            // 消息类型
            String msgType = requestMap.get("MsgType");
            String content = requestMap.get("Content");
            String eventKey = requestMap.get("EventKey");
            // 回复文本消息
            TextMessage textMessage = new TextMessage();
            textMessage.setToUserName(fromUserName);
            textMessage.setFromUserName(toUserName);
            textMessage.setCreateTime(new Date().getTime());
            textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
            textMessage.setFuncFlag(0);
            // 文本消息
            if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
                if (content.equalsIgnoreCase("授权")
                        || content.equalsIgnoreCase("登录")
                        || content.equalsIgnoreCase("登陆")
                        || content.equalsIgnoreCase("\"登录\"")
                        || content.equalsIgnoreCase("dl")) {
                    respContent.append(loginMsg(request, fromUserName));
                } else {
                    respContent.append("您发送的是文本消息！");
                }
            } else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) { // 图片消息
                respContent.append("您发送的是图片消息！");
            } else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {// 地理位置消息
                respContent.append("您发送的是地理位置消息！");
            } else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {// 链接消息
                respContent.append("您发送的是链接消息！");
            } else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {// 音频消息
                respContent.append("您发送的是音频消息！");
            } else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
                // 事件推送 开始
                String eventType = requestMap.get("Event");  // 事件类型
                if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {// 订阅
                    respContent.append(subscribeMsg(request, fromUserName));
                } else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) { //取消订阅
                    respContent.append("取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息，取消授权关系！");
                    //取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息，取消授权关系
                } else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {  // 自定义菜单点击事件
                    if (eventKey.equalsIgnoreCase("LOGIN")) {
                        respContent.append(loginMsg(request, fromUserName));
                    } else if (eventKey.equalsIgnoreCase("send_news")) {
                        NewsMessage newsMessage = new NewsMessage();
                        newsMessage.setToUserName(fromUserName);
                        newsMessage.setFromUserName(toUserName);
                        newsMessage.setCreateTime(new Date().getTime());
                        newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
                        newsMessage.setArticleCount(2);
                        List<Article> articles = new ArrayList<Article>();
                        Article article = new Article();
                        article.setTitle("文章1");
                        article.setDescription("挺好的一篇文章");
                        article.setPicUrl("http://yufa.21tb.com/wx/images/course/els_course_cover_default.png");
                        article.setUrl("");
                        articles.add(article);
                        Article article1 = new Article();
                        article1.setTitle("文章2");
                        article1.setDescription("挺好的一篇文章");
                        article1.setPicUrl("http://yufa.21tb.com/wx/images/course/els_course_cover_default.png");
                        article1.setUrl("");
                        articles.add(article1);
                        newsMessage.setArticles(articles);
                        respMessage = MessageUtil.newsMessageToXml(newsMessage);
                        return respMessage;
                    }
                }
                // 事件推送 结束
            } else if (msgType.equals(MessageUtil.RESP_MESSAGE_TYPE_SHORT_VIDEO)) {
                respContent.append("您发送的是小视屏！");
            } else {
                respContent.append("请求处理异常，请稍候尝试！");
            }

            textMessage.setContent(respContent.toString());
            respMessage = MessageUtil.textMessageToXml(textMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return respMessage;
    }

    /**
     * 登录信息
     */
    private static String loginMsg(HttpServletRequest request, String wechatId) {
        StringBuilder msg = new StringBuilder();
        msg.append("您好！我是liuyue，欢迎来到liuyue微信公众平台，请先进行身份验证，即可享微课堂的服务");
        return msg.toString();
    }


    /**
     * 登录连接修改，兼容最新登录
     */
    private static String newLoginSrc(HttpServletRequest request, String weChatId, String corpCode) {
        StringBuilder loginSrc = new StringBuilder();
        loginSrc.append("http://").append(request.getServerName()).append(request.getContextPath())
                .append("/loginOut.do?functionName=checkLogin").append("&weChatId=" + weChatId);
        if (StringUtils.isNotBlank(corpCode)) {
            loginSrc.append("&corpCode=" + corpCode);
        }

        return loginSrc.toString();
    }

    /**
     * 订阅时推送的信息
     *
     * @param request
     * @param wechatId
     * @return
     */
    private static String subscribeMsg(HttpServletRequest request, String wechatId) {
        StringBuilder msg = new StringBuilder();
        msg.append("欢迎来到时代光华微信公众平台，赶紧点击菜单，享受微课堂服务吧。");
        return msg.toString();
    }

    /**
     * 登录加密链接
     *
     * @param request
     * @param weChatId
     * @return
     */
    protected static String loginSrc(HttpServletRequest request, String weChatId) {
        return "qwe";
    }


}
