package com.along.common.export;

import org.apache.ibatis.type.ShortTypeHandler;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description: 复杂表头excel，支持任意复杂的表头
 * @Author along
 * @Date 2019/1/14 17:19
 */
public class CreateMoreMapExcel {

    private static Logger logger = LoggerFactory.getLogger(CreateMoreMapExcel.class);

    /**
     * @param datas       数据
     * @param moreSetters 参数设置
     * @param fps         合并单元格坐标值
     * @param sheetName   单元格名称
     * @return
     */
    public static SXSSFWorkbook create(List<Map<String, Object>> datas,
                                       List<MoreSetter> moreSetters, List<FourPoint> fps, String sheetName) {

        long startTime = System.currentTimeMillis();
        SXSSFWorkbook workbook = new SXSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        workbook.setSheetName(0, sheetName);
        DataFormat format = workbook.createDataFormat();

        // 标题字体定义
        Font titleFont = workbook.createFont();
        titleFont.setFontName("微软雅黑");
        titleFont.setFontHeightInPoints((short) 10); // 字体大小
        titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD); // 加粗

        // 内容字体定义
        Font contentFont = workbook.createFont();
        contentFont.setFontName("微软雅黑");
        contentFont.setFontHeightInPoints((short) 9);

        CellStyle titleStyle = workbook.createCellStyle();
        titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直
        titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平
        titleStyle.setBorderTop(CellStyle.BORDER_THIN); // 上边跨
        titleStyle.setBorderRight(CellStyle.BORDER_THIN); // 右边框
        titleStyle.setBorderBottom(CellStyle.BORDER_THIN); // 下边框
        titleStyle.setBorderLeft(CellStyle.BORDER_THIN); // 左边框
        titleStyle.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);// 填暗红色
        titleStyle.setFillPattern(CellStyle.SOLID_FOREGROUND); // 设置背景色
        titleStyle.setFont(titleFont); // 设置字体
        titleStyle.setWrapText(true);

        CellStyle stringStyle = workbook.createCellStyle();
        stringStyle.setAlignment(CellStyle.ALIGN_LEFT); // 内容据左
        stringStyle.setBorderTop(CellStyle.BORDER_THIN); // 上边跨
        stringStyle.setBorderRight(CellStyle.BORDER_THIN); // 右边框
        stringStyle.setBorderBottom(CellStyle.BORDER_THIN); // 下边框
        stringStyle.setBorderLeft(CellStyle.BORDER_THIN); // 左边框
        stringStyle.setFont(contentFont); // 设置字体
        stringStyle.setWrapText(true);

        CellStyle longStyle = workbook.createCellStyle();
        longStyle.setAlignment(CellStyle.ALIGN_LEFT); // 内容据左
        longStyle.setBorderTop(CellStyle.BORDER_THIN); // 上边跨
        longStyle.setBorderRight(CellStyle.BORDER_THIN); // 右边框
        longStyle.setBorderBottom(CellStyle.BORDER_THIN); // 下边框
        longStyle.setBorderLeft(CellStyle.BORDER_THIN); // 左边框
        longStyle.setFont(contentFont); // 设置字体
        longStyle.setWrapText(true);

        CellStyle doubleStyle = workbook.createCellStyle();
        doubleStyle.setAlignment(CellStyle.ALIGN_LEFT); // 内容据左
        doubleStyle.setBorderTop(CellStyle.BORDER_THIN); // 上边跨
        doubleStyle.setBorderRight(CellStyle.BORDER_THIN); // 右边框
        doubleStyle.setBorderBottom(CellStyle.BORDER_THIN); // 下边框
        doubleStyle.setBorderLeft(CellStyle.BORDER_THIN); // 左边框
        doubleStyle.setFont(contentFont); // 设置字体
        doubleStyle.setWrapText(true);

        CellStyle dateStyle = workbook.createCellStyle();
        dateStyle.setAlignment(CellStyle.ALIGN_LEFT); // 内容据左
        dateStyle.setBorderTop(CellStyle.BORDER_THIN); // 上边跨
        dateStyle.setBorderRight(CellStyle.BORDER_THIN); // 右边框
        dateStyle.setBorderBottom(CellStyle.BORDER_THIN); // 下边框
        dateStyle.setBorderLeft(CellStyle.BORDER_THIN); // 左边框
        dateStyle.setFont(contentFont); // 设置字体
        dateStyle.setWrapText(true);

        CellStyle booleanStyle = workbook.createCellStyle();
        booleanStyle.setAlignment(CellStyle.ALIGN_LEFT); // 内容据左
        booleanStyle.setBorderTop(CellStyle.BORDER_THIN); // 上边跨
        booleanStyle.setBorderRight(CellStyle.BORDER_THIN); // 右边框
        booleanStyle.setBorderBottom(CellStyle.BORDER_THIN); // 下边框
        booleanStyle.setBorderLeft(CellStyle.BORDER_THIN); // 左边框
        booleanStyle.setFont(contentFont); // 设置字体
        booleanStyle.setWrapText(true);

        // 标题的行数
        int lineCount = moreSetters.get(0).getRowTitles().size();

        // 标题设置
        List<Row> rowList = new ArrayList<>();
        for (int i = 0; i < lineCount; i++) {
            Row row = sheet.createRow(i);
            row.setHeight((short) 350); // 设置行高
            rowList.add(row);
        }
        Cell cell;
        for (int i = 0; i < moreSetters.size(); i++) {
            for (int j = 0; j < rowList.size(); j++) {
                cell = rowList.get(j).createCell(i); // 一级标题的设置
                cell.setCellStyle(titleStyle);
                cell.setCellValue(moreSetters.get(i).getRowTitles().get(j));
            }
            sheet.setColumnWidth(i, moreSetters.get(i).getWidth()); // 宽度
        }
        for (FourPoint fp : fps) { // 合并单元格
            sheet.addMergedRegion(new CellRangeAddress(fp.getRowStart(), fp.getRowEnd(),
                    fp.getColStart(), fp.getColEnd()));
        }

        // 内容设置
        Row row;
        if (datas.size() != 0) {
            for (int m = 0; m < datas.size(); m++) {
                row = sheet.createRow(m + lineCount);
                row.setHeight((short) 310);
                for (int i = 0; i < moreSetters.size(); i++) {
                    Cell cont = row.createCell(i);
                    MoreSetter moreSetter = moreSetters.get(i);
                    Object value = datas.get(m).get(moreSetter.getProp());
                    if (value == null) {
                        cont.setCellValue("");
                        cont.setCellStyle(stringStyle);
                        continue;
                    }
                    if (moreSetter.getRender() != null) {
                        cont.setCellType(Cell.CELL_TYPE_STRING);
                        cont.setCellValue(moreSetter.getRender().view(value));
                        cont.setCellStyle(stringStyle);
                        continue;
                    }
                    String type = moreSetter.getType().trim().toLowerCase();
                    if ("string".equals(type) || "char".equals(type)) {
                        cont.setCellType(Cell.CELL_TYPE_STRING);
                        cont.setCellValue(value.toString());
                        stringStyle.setDataFormat(format.getFormat(moreSetter.getFormat()));
                        cont.setCellStyle(stringStyle);
                        continue;
                    }
                    if ("date".equals(type)) {
                        cont.setCellType(Cell.CELL_TYPE_NUMERIC);
                        cont.setCellValue((Date) value);
                        dateStyle.setDataFormat(format.getFormat(moreSetter.getFormat()));
                        cont.setCellStyle(dateStyle);
                        continue;
                    }
                    if ("long".equals(type) || "int".equals(type)
                            || "byte".equals(type) || "short".equals(type)) {
                        cont.setCellType(Cell.CELL_TYPE_NUMERIC);
                        cont.setCellValue(Long.valueOf(value.toString()));
                        longStyle.setDataFormat(format.getFormat(moreSetter.getFormat()));
                        cont.setCellStyle(longStyle);
                        continue;
                    }
                    if ("double".equals(type) || "float".equals(type)) {
                        cont.setCellType(Cell.CELL_TYPE_NUMERIC);
                        cont.setCellValue(Double.parseDouble(value.toString()));
                        doubleStyle.setDataFormat(format.getFormat(moreSetter.getFormat()));
                        cont.setCellStyle(doubleStyle);
                        continue;
                    }
                    if ("boolean".equals(type)) {
                        cont.setCellType(Cell.CELL_TYPE_BOOLEAN);
                        cont.setCellValue((Boolean) value);
                        booleanStyle.setDataFormat(format.getFormat(moreSetter.getFormat()));
                        cont.setCellStyle(booleanStyle);
                    }
                }
            }
        }
        logger.info("导出总计耗时: {}", System.currentTimeMillis() - startTime + "毫秒!");
        return workbook;
    }

}
