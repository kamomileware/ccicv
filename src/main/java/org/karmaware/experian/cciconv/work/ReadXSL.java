package org.karmaware.experian.cciconv.work;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ReadXSL {

	public static void doRead(String filename) throws IOException{
		
		InputStream inp = new FileInputStream(filename);
	    //InputStream inp = new FileInputStream("workbook.xlsx");
		
	    Workbook wb;
		try {
			wb = WorkbookFactory.create(inp);
		    Sheet sheet = wb.getSheetAt(0);
		    int line=1;
		    for (Iterator<Row> rit = sheet.rowIterator(); rit.hasNext(); ) {
		    	System.out.print(""+line+" ");
		        Row row = rit.next();
//		        boolean blank_cell = true;
		        char col = 'A';
		        for (Iterator<Cell> cit = row.cellIterator(); cit.hasNext(); ) {
		          Cell cell = cit.next();
		          
		          String format = cell.getCellStyle().getDataFormatString();
		          switch(cell.getCellType()){
		          case  Cell.CELL_TYPE_STRING:
		        	  System.out.printf("%c:%s [%s] ",col,cell.getStringCellValue(),cell.getCellStyle().getDataFormatString());
		        	  break;
		          case  Cell.CELL_TYPE_NUMERIC:
		        	  if(format.contains("/")){
		        		  System.out.printf("%c:%tF [%s]",col,cell.getDateCellValue(), cell.getCellStyle().getDataFormatString());
		        	  }else if(format.contains("€")){
		        		  System.out.printf("%c:%.0f [%s]",col,cell.getNumericCellValue()*100, cell.getCellStyle().getDataFormatString());
		        	  }else {
		        		  System.out.printf("%c:%.0f [%s]",col,cell.getNumericCellValue(), cell.getCellStyle().getDataFormatString());
		        	  }
		        	  break;
		          case  Cell.CELL_TYPE_BLANK:
		        	  System.out.printf("%c: ",col);
		        	  break;
		          }
		          
		          col+=1;
		        }
		        line++;
		        System.out.println();
		      }
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			inp.close();
		}
		
	}
	
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		ReadXSL.doRead("C:/Users/admin/Desktop/PRUEBA EXPERIAN.xls");
	}

}
