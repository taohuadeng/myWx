package com.thd.wx.study;

import jxl.Workbook;
import org.apache.commons.io.IOUtils;
import jxl.Cell;
import jxl.Sheet;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Excel生成需要的文件
 */
public class WorkbookTest {
    public static void main(String[] args) throws Exception {
        Workbook workbook = null;
        InputStream inputStream = new FileInputStream("F:/456.xls");
        workbook = Workbook.getWorkbook(inputStream);
        Sheet sheet = workbook.getSheet(0);
        int rsRows = sheet.getRows();
        System.out.println(rsRows);
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < rsRows; i++) {
            Cell cell0 = sheet.getCell(0, i);
            String itemId = cell0.getContents();
            String sql = "selectiveCourseIds.add(\"" + itemId + "\");\n";
            list.add(sql);
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
