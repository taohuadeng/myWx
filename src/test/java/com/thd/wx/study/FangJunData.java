package com.thd.wx.study;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Excel生成需要的文件
 */
public class FangJunData {
    public static void main(String[] args) throws Exception {
        Workbook workbook = null;
        InputStream inputStream = new FileInputStream("F:/aaaaa.xls");
        workbook = Workbook.getWorkbook(inputStream);
        Sheet sheet = workbook.getSheet(0);
        int rsRows = sheet.getRows();
        System.out.println(rsRows);
        List<String> list = new ArrayList<String>();
        String id = "";
        for (int i = 0; i < rsRows; i++) {
            Cell cell0 = sheet.getCell(0, i);
            Cell cell1 = sheet.getCell(1, i);
            String itemId0 = cell0.getContents();
            String itemId1 = cell1.getContents();
//            if (StringUtils.isEmpty(id)) {
//                id = "'" + itemId + "'";
//            } else {
//                id = id + ",'" + itemId + "'";
//            }

            String sql = "tempIdMap.put(\"" + itemId0 + "\",\"" + itemId1 + "\");";
            list.add(sql);
        }

        //安排课程数
        //String sql = "SELECT corp_code,\"count\"(*) FROM t_els_study_plan WHERE corp_code IN(" + id + ") AND plan_status='LANCH' GROUP BY corp_code;";
        //上传课程数
        //String sql = "SELECT corp_code,\"count\"(*) FROM t_els_course_info WHERE corp_code IN(" + id + ") AND create_corp_code IN (" + id + ") GROUP BY corp_code;";
        //资料中心内容上传数量
        //String sql = "SELECT corp_code,COUNT (*) FROM t_km_knowledge WHERE opt_status= 'ENABLE' AND corp_code IN (" + id + ") GROUP BY corp_code;";
        //资讯数量
        //String sql = "SELECT corp_code,\"count\"(*) from t_cms_info_content where corp_code IN (" + id + ") AND status = 'enable' and author <> '时代光华' and tbc_id is null GROUP BY corp_code;";
        //问答  是否修改 首页公告
//        String sql = "SELECT corp_code from t_qa_system_config where ((announcement_content <> '（1）请先检索问题，勿重复提问；\n" +
//                "（2）问题描述采用“标题+问题描述+解决目的”格式，并选对话题；\n" +
//                "（3）务必遵守系统相关保密规定。' and announcement_content <> 'v4.js.announcementContentA\n" +
//                "v4.js.announcementContentB\n" +
//                "v4.js.announcementContentC') or announcement_content is null or announcement_content = '') AND corp_code IN (" + id + ");";

        //String sql = "SELECT tp.corp_code,\"count\"(*) from t_qa_topic tp INNER JOIN t_qa_org_relating rel ON rel.object_id = tp.topic_id WHERE tp.corp_code IN (" + id + ") GROUP BY tp.corp_code;";
        //有个性化登录的公司
        //String sql = "SELECT max(oc.corp_code) FROM t_oms_login_page olp INNER JOIN t_oms_corp oc ON olp.corp_id = oc.corp_id WHERE oc.corp_code IN (" + id + ") GROUP BY olp.corp_id;";
        //是否添加培训管理员
        //String sql = "SELECT corp_code FROM t_uc_user_role_rel WHERE corp_code IN (" + id + ") AND role_id = '402880593604ea49013605e3208602f2' GROUP BY corp_code HAVING COUNT(user_id)>0 ;";
        //是否添过角色
//        String sql = "SELECT corp_code,\"count\"(*) from t_oms_role where corp_code IN (" + id + ") AND  override_id is null and override_type is null GROUP BY corp_code ;";
//        list.add(sql);

        File file = new File("F:/select.sql");
        FileWriter fw = null;
        BufferedWriter writer = null;
        Iterator<String> iterator = list.iterator();
        try {
            fw = new FileWriter(file);
            writer = new BufferedWriter(fw);
            while (iterator.hasNext()) {
                writer.write(iterator.next());
                writer.newLine();//换行
            }
            writer.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(writer);
            IOUtils.closeQuietly(fw);
        }
    }
}
