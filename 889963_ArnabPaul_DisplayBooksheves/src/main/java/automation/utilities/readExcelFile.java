package automation.utilities;

import org.apache.poi.hssf.usermodel.HSSFCell;
//import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DateUtil;
//import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;

import java.io.File;
import java.io.FileInputStream;
//import java.io.FileOutputStream;
import java.io.IOException;
 
public class readExcelFile {
   private static HSSFWorkbook workbook;
    private static HSSFSheet sheet;
    //private static HSSFRow row;
    private static HSSFCell cell;
   
 
   public void setExcelFile(String excelFile,String sheetName) throws IOException {
       //Create an object of File class to open xls file
       File file =    new File(System.getProperty("user.dir")+"\\test_data\\"+excelFile);
       
       //Create an object of FileInputStream class to read excel file
       FileInputStream inputStream = new FileInputStream(file);
       
       //creating workbook instance that refers to .xls file
       workbook=new HSSFWorkbook(inputStream);
       
       //creating a Sheet object
        sheet=workbook.getSheet(sheetName);
 
   }
 
    public String getCellData(int rowNumber,int cellNumber){
       //getting the cell value from rowNumber and cell Number
        cell =sheet.getRow(rowNumber).getCell(cellNumber);
        
        DataFormatter formatter = new DataFormatter();
       
        
        String cellValue = null;

        switch (cell.getCellType()) {
        case STRING:
        	//cellValue = cell.getStringCellValue();
        	 cellValue = formatter.formatCellValue(cell);
        	break;
        	
        case FORMULA:
        	cellValue = cell.getCellFormula();
        	break;

        case NUMERIC:
        	if (DateUtil.isCellDateFormatted(cell)) {
        		cellValue = formatter.formatCellValue(cell);
        		//cellValue = cell.getDateCellValue().toString();
        	} else {
        		//cellValue = Double.toString(cell.getNumericCellValue());
        		cellValue = formatter.formatCellValue(cell);
        	}
        	break;

        case BLANK:
        	cellValue = "";
        	break;
        	
        case BOOLEAN:
        	cellValue = Boolean.toString(cell.getBooleanCellValue());
        	break;
		default:
			break;

        }
      //returning the cell value as string
        return cellValue;
    }
 
    public int getRowCountInSheet(){
       int rowcount = sheet.getLastRowNum()-sheet.getFirstRowNum();
       return rowcount;
    }

}