package com.ibicd.common.poi;

import com.ibicd.domain.poi.ExcelAttribute;
import lombok.Getter;
import lombok.Setter;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName ExcelExportUtils
 * @Description EXcel 导出工具类
 * @Author Julie
 * @Date 2019/10/13 16:05
 * @Version 1.0
 */

@Setter
@Getter
public class ExcelExportUtils<T> {


    private int rowIndex;

    private int styleIndex;

    private String templatePath;

    private Class clazz;

    private Field fields[];

    public ExcelExportUtils(int rowIndex, int styleIndex, Class clazz) {
        this.rowIndex = rowIndex;
        this.styleIndex = styleIndex;
        this.clazz = clazz;
        this.fields = clazz.getDeclaredFields();
    }


    /**
     * 导出
     *
     * @param response 响应
     * @param fis      文件模板流
     * @param objs     导出的数据
     * @param fileName 文件名称
     * @throws Exception
     */
    public void export(HttpServletResponse response, InputStream fis, List<T> objs, String fileName) throws Exception {
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet sheet = workbook.createSheet();

        CellStyle[] templateStyles = getTemplateStyles(sheet.getRow(styleIndex));
        AtomicInteger datasAi = new AtomicInteger(rowIndex);

        for (T obj : objs) {

            XSSFRow row = sheet.getRow(datasAi.getAndIncrement());
            for (int i = 0; i < templateStyles.length; i++) {
                XSSFCell cell = row.createCell(i);
                cell.setCellStyle(templateStyles[i]);
                for (Field field : fields) {
                    if (field.isAnnotationPresent(ExcelAttribute.class)) {

                        field.setAccessible(true);

                        ExcelAttribute annotation = field.getAnnotation(ExcelAttribute.class);
                        if (i == annotation.sort()) {
                            cell.setCellValue(field.get(obj).toString());

                        }
                    }
                }
            }
        }

        fileName = URLEncoder.encode(fileName, "UTF-8");
        response.setContentType("application/octet-stream");
        response.setHeader("content-disposition", "attachment;filename=" + new String(fileName.getBytes("ISO8859-1")));
        response.setHeader("fileName", fileName);
        workbook.write(response.getOutputStream());
    }

    /**
     * 获取模板中的样式
     *
     * @param row
     * @return
     */
    private CellStyle[] getTemplateStyles(Row row) {
        CellStyle[] styles = new CellStyle[row.getLastCellNum()];
        for (int i = 0; i <= row.getLastCellNum(); i++) {

            styles[i] = row.getCell(i).getCellStyle();
        }

        return styles;
    }


}
