package com.thd.wx.study;

import com.thd.wx.util.DateUtil;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

/**
 * Excel生成需要的文件
 */
public class AttendanceLaterMac {

    public static final String OVERTIME_COUNT = "overtimeCount";
    public static final String LATER_COUNT = "laterCount";
    public static final String OVERTIME_TOTAL_TIMES = "overtimeTotalTimes";
    public static final String LATER_TOTAL_TIMES = "laterTotalTimes";

    public static void main(String[] args) throws Exception {
        dealDate();
    }

    private static void dealDate() throws IOException, BiffException {
        Workbook workbook = null;
        InputStream inputStream = new FileInputStream("/Users/taofadeng/Documents/日管会/考勤/青谷考勤-201605.xls");
        workbook = Workbook.getWorkbook(inputStream);
        Sheet sheet = workbook.getSheet(0);
        int rsRows = sheet.getRows();
        Map<String, Map<String, Object>> mapMap = new HashMap<String, Map<String, Object>>();
        appendData(sheet, rsRows, mapMap);

        List<String> list = new ArrayList<String>();
        for (String keyName : mapMap.keySet()) {
            Map<String, Object> map = mapMap.get(keyName);
            //int count = (Integer) map.get(OVERTIME_COUNT);
            //String value = keyName + "\t" + count + "\t" + (count * 12) + "\t" + map.get(OVERTIME_TOTAL_TIMES);

            int count = (Integer) map.get(LATER_COUNT);
            if (count > 0) {
                String value = keyName + "\t" + count + "\t" + map.get(LATER_TOTAL_TIMES);
                System.out.println(value);
                list.add(value);
            }
        }

        File file = new File("/Users/taofadeng/work_work/result.txt");
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

    private static void appendData(Sheet sheet, int rsRows, Map<String, Map<String, Object>> mapMap) {
        Date laterTimeDate = DateUtil.getStringToDate("2016-01-01 08:30:00");//开始算上班时间
        Date overtimeDate = DateUtil.getStringToDate("2016-01-01 20:30:00");//开始算加班的时间点
        Date standardDate = DateUtil.getStringToDate("2016-01-01 18:00:00");//正常下班时间
        Date standardDate2 = DateUtil.getStringToDate("2016-01-01 19:00:00");//正常下班时间
        for (int i = 1; i < rsRows; i++) {
            int columns = sheet.getColumns();
            Cell cell0 = sheet.getCell(3, i);//姓名
            Cell cell1 = sheet.getCell(9, i);//签到时间
            Cell cell2 = sheet.getCell(10, i);//签退时间
            String name = cell0.getContents();
            Map<String, Object> map = mapMap.get(name);
            if (MapUtils.isEmpty(map)) {
                map = new HashMap<String, Object>();
            }

            //迟到总次数
            int laterCount = map.get(LATER_COUNT) == null ? 0 : (Integer) map.get(LATER_COUNT);
            //加班总次数
            int overtimeCount = map.get(OVERTIME_COUNT) == null ? 0 : (Integer) map.get(OVERTIME_COUNT);
            //迟到总时长（分钟）
            double laterTotalTimes = map.get(LATER_TOTAL_TIMES) == null ? 0 : (Double) map.get(LATER_TOTAL_TIMES);
            //加班总时长（分钟）
            double overtimeTotalTimes = map.get(OVERTIME_TOTAL_TIMES) == null ? 0 : (Double) map.get(OVERTIME_TOTAL_TIMES);
            String timeText = cell2.getContents();
            if (StringUtils.isNotBlank(timeText)) {
                timeText = "2016-01-01 " + timeText + ":00";
                Date toDate = DateUtil.getStringToDate(timeText);
                long overMinutes = (toDate.getTime() - overtimeDate.getTime());
                double minutes = (toDate.getTime() - standardDate.getTime()) / (1000 * 60 * 60 * 1.0);//小时
                overtimeTotalTimes = overtimeTotalTimes + minutes;
                if (overMinutes > 0) {
                    overtimeCount++;
                }

                map.put(OVERTIME_COUNT, overtimeCount);
                map.put(OVERTIME_TOTAL_TIMES, round(overtimeTotalTimes, 2));
                mapMap.put(name, map);
            }

            String laterTimeText = cell1.getContents();
            if (StringUtils.isNotBlank(laterTimeText)) {
                laterTimeText = "2016-01-01 " + laterTimeText + ":00";
                Date toDate = DateUtil.getStringToDate(laterTimeText);
                long laterMinutes = (toDate.getTime() - laterTimeDate.getTime());
                double minutes = laterMinutes / (1000 * 60 * 1.0);//分钟
                if (minutes > 0) {
                    laterTotalTimes = laterTotalTimes + minutes;
                    laterCount++;
                }

                map.put(LATER_COUNT, laterCount);
                map.put(LATER_TOTAL_TIMES, round(laterTotalTimes, 2));
                mapMap.put(name, map);
            }
        }
    }

    /**
     * 提供精确的小数位四舍五入处理
     *
     * @param v
     * @param scale 小数点后保留几位
     * @return
     */
    private static double round(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }

        if (Double.isNaN(v)) {
            throw new IllegalArgumentException("v is NaN!");
        }

        if (Double.isInfinite(v)) {
            throw new IllegalArgumentException("v is Infinite!");
        }

        BigDecimal b = BigDecimal.valueOf(v);
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
