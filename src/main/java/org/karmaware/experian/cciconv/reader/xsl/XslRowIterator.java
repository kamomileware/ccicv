package org.karmaware.experian.cciconv.reader.xsl;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class XslRowIterator implements Iterator<Map<String, Object>>, Iterable<Map<String, Object>>{

	static protected final String[] etiquetas = {"delegacion","entidad", "refnum", "nsocio", 
		"entrada", "mvencidos", "deuda", "ident", "direccion", "codpostal", "localidad", 
		"provincia", "cuotas"};
	
	public boolean debug = false;
	protected Workbook wb;
	protected int sheetNum = 0;
	protected Iterator<Row> rit;
	protected InputStream in;
	
	public XslRowIterator(InputStream in, int start) throws IOException, InvalidFormatException {
		wb = WorkbookFactory.create(in);
	    Sheet sheet = wb.getSheetAt(0);
	    rit = sheet.rowIterator();
	    // Initialize
	    for(int i=0; i<start && rit.hasNext();i++) 
	    		rit.next();
	}
	
	public Iterator<Map<String, Object>> iterator() {
		return this;
	}
	
	public boolean hasNext() {
		return rit.hasNext();
	}
	
	public Map<String, Object> next() {
		Row row = rit.next();
		return readRow(row);
	}


	public void remove() {
		rit.remove();
	}
	
	public void close() throws IOException{
		in.close();
	}
	
	private Map<String, Object> readRow(Row row) {
		Map<String,Object> rowMap = new HashMap<String,Object>();
		if(debug){
			debugRow(row);
			System.out.println();
		}
		else{
			int i=0;
			Cell cell = null;
			Iterator<Cell> cit = row.cellIterator();
			for (;cit.hasNext() && i < etiquetas.length;) 
			{
				cell = cit.next();
				Object value = readCellValue(cell);
				rowMap.put(etiquetas[i],value);
				i++;
			}
		}
		return rowMap;
	}
	
	private Object readCellValue(Cell cell) {
		String format = cell.getCellStyle().getDataFormatString();
		Object result;
        switch(cell.getCellType()){
        case  Cell.CELL_TYPE_STRING:
      	  result = cell.getStringCellValue();
      	  break;
        case  Cell.CELL_TYPE_NUMERIC:
      	  if(format.contains("/")){
      		  result = cell.getDateCellValue();
      	  }else if(format.contains("€")){
      		  result = new Integer(new Double(cell.getNumericCellValue()*100).intValue());
      	  }else {
      		  result = new Integer(new Double(cell.getNumericCellValue()).intValue());
      	  }
      	  break;
        default:
      	  result = "";
      	  break;
        }
		return result;
	}

	protected void debugRow(Row row){
		char col = 'A';
		for (Iterator<Cell> cit = row.cellIterator(); cit.hasNext(); ) {
	          Cell cell = cit.next();
	          String format = cell.getCellStyle().getDataFormatString();
	          switch(cell.getCellType()){
	          case  Cell.CELL_TYPE_STRING:
	        	  System.out.printf("%c:%s ",col,cell.getStringCellValue(),cell.getCellStyle().getDataFormatString());
	        	  break;
	          case  Cell.CELL_TYPE_NUMERIC:
	        	  if(format.contains("/")){
	        		  System.out.printf("%c:%tF ",col,cell.getDateCellValue(), cell.getCellStyle().getDataFormatString());
	        	  }else if(format.contains("€")){
	        		  System.out.printf("%c:%.0f ",col,cell.getNumericCellValue()*100, cell.getCellStyle().getDataFormatString());
	        	  }else {
	        		  System.out.printf("%c:%.0f ",col,cell.getNumericCellValue(), cell.getCellStyle().getDataFormatString());
	        	  }
	        	  break;
	          case  Cell.CELL_TYPE_BLANK:
	        	  System.out.printf("%c: ",col);
	        	  break;
	          }
	          col++;
		}
	}

	
}
