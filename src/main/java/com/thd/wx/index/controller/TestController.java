package com.thd.wx.index.controller;

import com.thd.wx.util.FileUtil;
import com.thd.wx.util.FishUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;
import java.util.Map;

/**
 * 微信入口Controller
 *
 * @author Fish
 * @since 2015年10月24日15:38:27
 */
@Controller
@RequestMapping("/test/*")
public class TestController {
    private static final Log LOG = LogFactory.getLog(TestController.class);
    private FileUtil fileUtil = new FileUtil();

    @RequestMapping("/picture")
    public String picture() {
        return "picture";
    }

    @RequestMapping("/getSecCode")
    public void getSecCode(HttpServletResponse response) {
        FishUtil.generateSecCode(FishUtil.getConfirmCode(4), 100, 40, response);
    }

    /**
     * 上传附件
     */
    @RequestMapping("/uploadFile")
//    @ResponseBody
    public String uploadFile(HttpServletRequest request, HttpServletResponse response) {
        MultipartHttpServletRequest multipartServletRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> files = multipartServletRequest.getFileMap();
        Iterator<String> fileNames = multipartServletRequest.getFileNames();
        while (fileNames.hasNext()) {
            String fileName = fileNames.next();
            MultipartFile multipartFile = files.get(fileName);
            String attachmentPath = fileUtil.uploadFile(multipartFile);
            if (attachmentPath != null && !"".equals(attachmentPath)) {
                String originalFilename = multipartFile.getOriginalFilename();
                LOG.error(originalFilename);
                LOG.error(attachmentPath);
            }
        }

        return "picture";
    }

    @RequestMapping("/study")
    public String study() {
        return "study/study";
    }

    @RequestMapping("/angular")
    public String angular() {
        return "study/angular";
    }
}
