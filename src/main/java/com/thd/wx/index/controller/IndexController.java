package com.thd.wx.index.controller;

import com.thd.wx.util.CoreService;
import com.thd.wx.util.SignUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 微信入口Controller
 *
 * @author Fish
 * @since 2015年10月24日15:38:27
 */
@Controller
@RequestMapping("/index/*")
public class IndexController {
    private static final long serialVersionUID = 1L;
    public static final String GET = "GET";
    public static final String POST = "POST";
    private static Log LOG = LogFactory.getLog(IndexController.class);

    @RequestMapping("/index")
    public String index(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String method = request.getMethod();
        if (GET.equals(method)) {
            // 微信加密签名
            String signature = request.getParameter("signature");
            // 随机字符串
            String echostr = request.getParameter("echostr");
            // 时间戳
            String timestamp = request.getParameter("timestamp");
            // 随机数
            String nonce = request.getParameter("nonce");

            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
            if (StringUtils.isBlank(signature)) {
                out.print("WeChat param not found");
            } else if (SignUtil.checkSignature(signature, timestamp, nonce)) {
                out.print(echostr);
            }

            out.close();
            out = null;
        } else if (POST.equals(method)) {
            // 将请求、响应的编码均设置为UTF-8（防止中文乱码）
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            // 调用核心业务类接收消息、处理消息
            String respMessage = CoreService.processRequest(request, response);
            // 响应消息
            PrintWriter out = response.getWriter();
            out.print(respMessage);
            out.close();
        }

        return null;
    }
}
