package util;

import java.io.File;
import java.io.FileInputStream;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.*;

import java.io.IOException;
import java.io.InputStream; 
import java.util.Enumeration;
import java.util.Hashtable;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.dom4j.DocumentException;

public class Return2D {
	
	private static final Logger LOGGER = Logger.getLogger(Return2D.class);

	int rowIndex = 0, columnIndex = 0;
	Workbook wb;
	Sheet ws;
	Row wr;
	Cell cell;
	String fileName, sheetName;
	FileInputStream fis;
	
	//array of hashtables to hold column name and cell value 
	Hashtable <String, String> [] data = null;	

	  public boolean isSheetExist(Workbook wb, String sheetName){
	        int index = wb.getSheetIndex(sheetName);
	        if(index==-1){
	            index=wb.getSheetIndex(sheetName.toUpperCase());
	                if(index==-1)
	                    return false;
	                else
	                    return true;
	        }
	        else
	            return true;
	    }

	@SuppressWarnings("deprecation")
	public  void open(String fileName, String sheetName) throws IOException  {

		try
		{
			fis = new FileInputStream(new File(fileName));
		}
		
		catch (IOException e){
			System.out.println("Specified sheet does NOT EXIST. Create sheet to fix NPE exception.");
			e.printStackTrace();
			LOGGER.info(e);

		}
		this.fileName = fileName;
		this.sheetName = sheetName;
				
		if (fileName.indexOf("xlsx") < 0) 

		{					
			wb = new HSSFWorkbook(fis);
			if (isSheetExist(wb, sheetName)==false)
			{
				System.out.println("Specified sheet does NOT EXIST. Create sheet to fix NPE exception.");
			}
			else
			ws = wb.getSheet(sheetName);			
		} else {
			wb = new XSSFWorkbook(fileName);
			if (isSheetExist(wb, sheetName)==false)
			{
				System.out.println("Specified sheet does NOT EXIST. Create sheet to fix NPE exception.");
			}
			else
			//ws = wb.getSheet(sheetName);	
			ws = (XSSFSheet) wb.getSheet(sheetName);			
		}			
	}
	public  Sheet getSheet(String fileName, String sheetName) throws IOException  {
		
		Sheet ws=null;
		this.ws=ws;
		if (fileName.indexOf("xlsx") < 0) 

		{					
			wb = new HSSFWorkbook(new FileInputStream(new File(fileName)));
			if (isSheetExist(wb, sheetName)==false)
			{
				System.out.println("Specified sheet does NOT EXIST. Create sheet to fix NPE exception.");
			}
			else
			{
				ws = wb.getSheet(sheetName);	
			}
		} else {
			wb = new XSSFWorkbook(fileName);
			if (isSheetExist(wb, sheetName)==false)
			{
				System.out.println("Specified sheet does NOT EXIST. Create sheet to fix NPE exception.");
			}
			else
			{
				ws = wb.getSheet(sheetName);	
				ws = (XSSFSheet) wb.getSheet(sheetName);
			}				
		}
		return ws;
	}
	private  static String getCellValue(Cell cell) {
	    if (cell == null) {
	        return "";
	    }
	    if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
	        return cell.getStringCellValue();
	    } else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
	        return cell.getNumericCellValue() + "";
	    } else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
	        return cell.getBooleanCellValue() + "";
	    }else if(cell.getCellType() == Cell.CELL_TYPE_BLANK){
	        return cell.getStringCellValue();
	    }else if(cell.getCellType() == Cell.CELL_TYPE_ERROR){
	        return cell.getErrorCellValue() + "";
	    } 
	    else if(cell.getCellType()==Cell.CELL_TYPE_BLANK){
			return ""; 
	    }
	    else {
	        return null;
	    }
	}

	
	public   String getCellValue(Row row,Cell cell) {
		
		
	    if (cell == null) {
	        return "";
	    }
	    if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
	        return cell.getStringCellValue();
	    } else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
	    	//CAST to int for appending to URL
	        return (int)cell.getNumericCellValue() + "";
	    } else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
	        return cell.getBooleanCellValue() + "";
	    
	    }else if(cell.getCellType() == Cell.CELL_TYPE_ERROR){
	        return cell.getErrorCellValue() + "";
	    } 
	    else if(cell.getCellType()==Cell.CELL_TYPE_BLANK){
			return ""; 
	    }
	    else {
	        return "check cell type";
	    }

	}

	@SuppressWarnings("unchecked")
	public Object[][] getData() throws IOException  {	
		data = new Hashtable[ws.getPhysicalNumberOfRows()];     

		//header
		wr = ws.getRow(0);	

		for(rowIndex = 1; rowIndex < ws.getPhysicalNumberOfRows(); rowIndex++) {
		//	System.out.println("Physically def rows "+ ws.getPhysicalNumberOfRows());
			data[rowIndex - 1] = new Hashtable <String, String>();

			for (columnIndex = 0; columnIndex < ws.getRow(rowIndex).getPhysicalNumberOfCells(); columnIndex++) {

				cell =ws.getRow(rowIndex).getCell(columnIndex,Row.CREATE_NULL_AS_BLANK) ;//A new, blank cell is created for missing cells
				String cellVal = Return2D.getCellValue(cell);

				/*to keep track of current values
				System.out.println("cellVal ="+cellVal);
				System.out.println("column index = "+columnIndex+" rownum=" +ws.getRow(rowIndex).getRowNum());
				System.out.println("wr.getCell(columnIndex).toString()=" +wr.getCell(columnIndex).toString());
		        System.out.println("ws.getRow(rowIndex).getCell(columnIndex).toString() ="+ ws.getRow(rowIndex).getCell(columnIndex).toString());
				putting 2 Strings
				*/

				data[rowIndex - 1].put(wr.getCell(columnIndex).toString(), cellVal);
			}		
		}

		//[1] - stands for one array to hold 'n' number of Hashtable<String, String>[] arrays; where n=row numbers in xlsx spreedsheet
		Object[][] obj = new Object[data.length - 1][1];		 
		for(int i = 0; i < data.length - 1; i++) {
			obj[i][0] = data[i];
		}
		
		if (fis!=null){
			fis.close();
		}
		return obj;		
	}

       public  void close() throws IOException  {
            wb = null;
            ws = null;
            wr = null;
            data = null;
	}

	
}

class TestReturn2D{
	public static void main (String args[]) throws IOException
	{
		ReadingProperties rp = ReadingProperties.getInstance();
		String excelPath= rp.readConfigProperties("excel.path");

		Return2D return2d = new Return2D();
		return2d.open(System.getProperty("user.dir")+excelPath, "Test_Address");

		Object[][] data = return2d.getData();
		System.out.println("data.length "+ data.length);
		
		Sheet sheet=return2d.getSheet(System.getProperty("user.dir")+excelPath, "Test_Address");
		System.out.println("sheet name "+ sheet.getSheetName());
		Row rowApache=sheet.getRow(2);
		Cell cellApache= rowApache.getCell(1);
		
	 String cellData= return2d.getCellValue(rowApache, cellApache);
	 System.out.println("cell data=" +cellData);
		


	}
}