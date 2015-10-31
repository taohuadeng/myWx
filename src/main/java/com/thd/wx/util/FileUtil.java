package com.thd.wx.util;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class FileUtil {
    private static final Log LOG = LogFactory.getLog(FileUtil.class);
    private String contextUploadFilePath = "D:/mvcLoadFile";

    /**
     * yyyyMMdd 20121225
     */
    public static String DATE_PATTERN_yyyyMMdd = "yyyyMMdd";

    /**
     * yyyy-MM-dd 2012-12-25
     */
    public static String DATE_PATTERN_yyyy_MM_dd = "yyyy-MM-dd";

    /**
     * yyyy-MM-dd HH:mm:ss 2012-12-25 20:20:20
     */
    public static String DATE_PATTERN_yyyy_MM_dd_HH_MM_ss = "yyyy-MM-dd HH:mm:ss";

    /**
     * yyyy-MM-dd_HHmmss_SSS 2012-12-25_20-20-20_123
     */
    public static String DATE_PATTERN_yyyy_MM_dd_HHmmss_SSS = "yyyy-MM-dd_HHmmss_SSS";

    /**
     * 一天的开始时间点   00:00:00
     */
    public static String START_TIME = " 00:00:00";

    /**
     * 一天的结束时间点   23:59:59
     */
    public static String END_TIME = " 23:59:59";

    public FileUtil() {
        super();
    }

    /**
     * 上传文件
     */
    public String uploadFile(MultipartFile file) {
        String uploadPath = null;
        if (file != null && !file.isEmpty()) {
            try {
                uploadPath = outputFile(file);
            } catch (IOException e) {
                LOG.error(e.getMessage(), e);
                uploadPath = null;
            }
        }

        return uploadPath;
    }

    /**
     * 删除文件
     */
    public Boolean deleteFile(String uploadPath) {
        File file = new File(contextUploadFilePath + "/" + uploadPath);
        return !file.exists() || file.delete();
    }

    /**
     * 下载文件
     *
     * @param fileName         服务器端保存的文件名称
     * @param originalFileName 上传是文件的原名
     */
    public void downloadFile(String fileName, String originalFileName, HttpServletRequest request,
                             HttpServletResponse response) {

        BufferedInputStream bis = null;
        //BufferedOutputStream bos = null;
        try {
            String downloadPath = contextUploadFilePath + "/" + fileName;
            LOG.debug("downloadPath====================" + downloadPath);

            long fileLength = new File(downloadPath).length();

            response.setContentType("application/octet-stream; charset=GBK");
            response.setHeader("Accept-Ranges", "bytes");
            response.addHeader("Content-Disposition", "attachment; filename="
                    + new String(originalFileName.getBytes("GB2312"), "ISO-8859-1"));
            response.setHeader("Content-Length", String.valueOf(fileLength));

            bis = new BufferedInputStream(new FileInputStream(downloadPath));
            IOUtils.copy(bis, response.getOutputStream());
            response.flushBuffer();
        } catch (IOException e) {
            //do nothing
        } finally {
            IOUtils.closeQuietly(bis);
        }
    }

    /**
     * 输出文件
     *
     * @param multipartFile
     * @return fileName
     */
    private String outputFile(MultipartFile multipartFile) throws IOException {
        String filePath;
        String timeDir = new SimpleDateFormat(DATE_PATTERN_yyyyMMdd).format(new Date());
        File dirPath = new File(contextUploadFilePath + "/" + timeDir);
        if (!dirPath.exists()) {
            dirPath.mkdir();
        }

        filePath = timeDir + "/" + new SimpleDateFormat(DATE_PATTERN_yyyy_MM_dd_HHmmss_SSS).format(new Date()) +
                "-" + multipartFile.getOriginalFilename();
        //filePath = timeDir + "/" + multipartFile.getOriginalFilename();
        File file = new File(contextUploadFilePath + "/" + filePath);
        file.setWritable(true);
        FileCopyUtils.copy(multipartFile.getBytes(), file);

        return filePath;
    }

}
