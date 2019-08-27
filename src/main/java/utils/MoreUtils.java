package utils;


import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.LoggerFactory;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.apache.poi.ss.usermodel.CellType.STRING;

public class MoreUtils {

    private static final String ExceptionToLog = "Exception";

    /**
     *
     * @Title: getJSSBSJ_FSDYZBInfo
     * @Description: 将附属单元子表.xls写入到JSSBSJ_FSDYZBEntity实体类中
     * @param @param path
     * @param @return    设定文件
     * @return List<RegisterEntity>    返回类型
     * @throws
     */
    public static List<RegisterEntity> getExcel(String path) {
        XSSFWorkbook xssfWorkbook = null;
        InputStream is = null;
        RegisterEntity Entity = null;
        List<RegisterEntity> EntityList = new ArrayList<RegisterEntity>();
        EntityList.clear();
        try {
            is = new FileInputStream(path);
            xssfWorkbook = new XSSFWorkbook(is);
            if (xssfWorkbook != null) {
                XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
                for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
                    XSSFRow xssfRow = xssfSheet.getRow(rowNum);
                    if (xssfRow != null) {
                        Entity = new RegisterEntity();
                        XSSFCell name = xssfRow.getCell(0);
                        XSSFCell phone = xssfRow.getCell(1);

                        Entity.setUsername(getValue(name));
                        Entity.setUserPhone(getValue(phone));
                        EntityList.add(Entity);
                    }
                }
            }
        } catch (Exception e) {
            LoggerFactory.getLogger(ExceptionToLog).warn(e.toString());
        } finally{
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    LoggerFactory.getLogger(ExceptionToLog).warn(e.toString());
                } finally{
                }
            }
        }
        return EntityList;
    }
    /**
     *
     * @Title: getValue
     * @Description: 判断后缀为xls的excel文件的数据类型
     * @param @param hssfCell
     * @param @return    设定文件
     * @return String    返回类型
     * @throws
     */
    private static String getValue(XSSFCell sfhssfCellCell) {

        sfhssfCellCell.setCellType(STRING);

        String Value = sfhssfCellCell.getStringCellValue();
        return String.valueOf(Value);
    }

    /**
     *
     * @Title: isEmpty
     * @Description: TODO判断值是否为空，如果为空返回0;
     * @return long    返回类型
     */
    private long isEmptyForLong(String value) {
        long result = 0;
        value = value.trim();
        if (value != null && !"".equals(value)) {
            result = Long.valueOf(value);
        }
        return result;
    }

    /**
     *
     * @Title: isEmptyForFloat
     * @Description: TODO判断值是否为空，如果为空返回0;
     * @return float    返回类型
     */
    private float isEmptyForFloat(String value) {
        float result = 0;
        value = value.trim();
        if (value != null && !"".equals(value)) {
            result = Float.valueOf((value));
        }
        return result;
    }
    private int isEmptyForInt(String value) {
        int result = 0;
        value = value.trim();
        if (value != null && !"".equals(value)) {
            result = Integer.valueOf((value));
        }
        return result;
    }






}
