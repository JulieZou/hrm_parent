package com.ibicd.common.poi;

import com.ibicd.domain.poi.ExcelAttribute;
import lombok.Getter;
import lombok.Setter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName ExcelImportUtils
 * @Description Excel导入工具类
 * @Author Julie
 * @Date 2019/10/13 16:25
 * @Version 1.0
 */
@Setter
@Getter
public class ExcelImportUtils<T> {

    private Class clazz;

    private Field fields[];

    public ExcelImportUtils(Class clazz) {
        this.clazz = clazz;
        fields = clazz.getDeclaredFields();
    }

    /**
     * 解析Excel
     *
     * @param fis       文件输入流
     * @param rowIndex  数据的开始行
     * @param cellIndex 数据的开始列
     * @return
     */
    public List<T> parseExcel(InputStream fis, int rowIndex, int cellIndex) throws Exception {

        List<T> list = new ArrayList<>();
        T entity = null;

        try {
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            XSSFSheet sheet = workbook.getSheetAt(0);
            int lastRowNum = sheet.getLastRowNum();
            System.out.println("数据最后一行： " + lastRowNum);
            for (int i = rowIndex; i <= lastRowNum; i++) {
                XSSFRow row = sheet.getRow(i);
                entity = (T) clazz.newInstance();
                for (int j = cellIndex; j <= row.getLastCellNum(); j++) {

                    Cell cell = row.getCell(j);
                    for (Field field : fields) {
                        if (field.isAnnotationPresent(ExcelAttribute.class)) {
                            field.setAccessible(true);

                            ExcelAttribute annotation = field.getAnnotation(ExcelAttribute.class);
                            if (j == annotation.sort()) {
                                field.set(entity, convertAttr(field, cell));
                            }
                        }
                    }
                }
                list.add(entity);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    /**
     * 转换field对应的值
     *
     * @param field
     * @param cell
     * @return
     * @throws ParseException
     */
    private Object convertAttr(Field field, Cell cell) throws ParseException {
        String simpleName = field.getType().getSimpleName();
        switch (simpleName) {
            case "String":
                return getValue(cell);
            case "Date":
                return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(getValue(cell));
            case "int":
            case "Inteer":
                return Integer.parseInt(getValue(cell));
            case "double":
            case "Double":
                return Double.parseDouble(getValue(cell));
            default:
                return null;
        }

    }

    /**
     * 获取单元格的值
     *
     * @param cell
     * @return
     */
    public String getValue(Cell cell) {
        if (cell == null)
            return "";

        switch (cell.getCellType()) {
            case STRING:
                return cell.getRichStringCellValue().getString().trim();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    Date date = DateUtil.getJavaDate(cell.getNumericCellValue());
                    return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
                } else {
                    String strCell = "";
                    Double numericCellValue = cell.getNumericCellValue();
                    BigDecimal num = new BigDecimal(numericCellValue.toString());
                    if (num != null)
                        strCell = num.toPlainString();
                    if (strCell.endsWith(".0"))
                        strCell = strCell.substring(0, strCell.indexOf("."));
                    return strCell;
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            default:
                return "";
        }
    }
}
