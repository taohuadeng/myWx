package com.thd.wx.study;

import jxl.Workbook;
import org.apache.commons.io.IOUtils;
import jxl.Cell;
import jxl.Sheet;
import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.text.DateFormatSymbols;
import java.util.*;

/**
 * Excel生成需要的文件
 */
public class WorkbookTest {
    public static void main(String[] args) throws Exception {
        dealDate();
    }

    private static void dealDate() {
        List<String> list = new ArrayList<String>();
        String id = "";

        Calendar cBegin = new GregorianCalendar();
        Calendar cEnd = new GregorianCalendar();
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] weeks = dfs.getWeekdays();

        cBegin.set(2000, 0, 1); //Calendar的月从0-11，所以1月是0.
        cEnd.set(2050, 11, 31); //Calendar的月从0-11，所以12月是11.

        int count = 1;
        cEnd.add(Calendar.DAY_OF_YEAR, 1);  //结束日期下滚一天是为了包含最后一天

        while (cBegin.before(cEnd)) {
            String uuid = UUID.randomUUID().toString().replace("-", "");
            String startTime = new java.sql.Date(cBegin.getTime().getTime()) + " 00:00:00";
            String endTime = new java.sql.Date(cBegin.getTime().getTime()) + " 23:59:59";
            String sql = "INSERT INTO \"public\".\"t_cal_calendar\" " +
                    "(\"calendar_id\", \"start_time\", \"end_time\", \"corp_code\", \"create_time\", " +
                    "\"create_by\", \"last_modify_time\", \"last_modify_by\", \"opt_time\") " +
                    "VALUES " +
                    "('" + uuid + "', '" + startTime + "', '" + endTime + "', 'default', '" + new Date() + "'," +
                    " 'admin', '" + new Date() + "', 'admin', '88888888');";
            list.add(sql);

            System.out.println("第" + count + "周  日期：" + new java.sql.Date(cBegin.getTime().getTime()) + ", " + weeks[cBegin.get(Calendar.DAY_OF_WEEK)]);
            if (cBegin.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                count++;
            }

            cBegin.add(Calendar.DAY_OF_YEAR, 1);
        }

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
