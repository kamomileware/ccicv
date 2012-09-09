package org.karmaware.cciconv;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ConvFechaField implements IConversor {
	static Pattern pattern = Pattern.compile("(\\d{1,2}/\\d{1,2}/)(\\d{2}$)");
	
	DateFormat dateFormat;
	DateFormat altDateFormat;
	Posicion pos;
	
	public ConvFechaField(DateFormat dateFormat, Posicion pos) {
		this.dateFormat = dateFormat;
		this.pos = pos;
	}
	
	public ConvFechaField(DateFormat dateFormat, DateFormat altDateFormat, Posicion pos) {
		this.dateFormat = dateFormat;
		this.altDateFormat = altDateFormat;
		this.pos = pos;
	}
	
	public Object convert(Object val) {
		if(val instanceof String){
			String valS = (String)val;
			String[] s = {valS};
			String[] fechas = valS.contains("-")? 
					valS.split("-"):
					s;
			int index = pos == Posicion.INICIAL? 0:fechas.length-1;
			
			if(fechas[index].contains("Y")){
				fechas = fechas[index].toUpperCase().split("Y");
				index = pos == Posicion.INICIAL? 0:fechas.length-1;
			}
			
			if(fechas[index].contains("AL")){
				fechas = fechas[index].toUpperCase().split("AL");
				index = pos == Posicion.INICIAL? 0:fechas.length-1;
			}
			
			Date date;
			try {
				String dateStr = fechas[index].trim();
				Matcher matcher = pattern.matcher(dateStr);
				if(matcher.find()){
					dateStr = matcher.group(1)
						.concat("20")
						.concat(matcher.group(2));
				}
				date = dateFormat.parse(dateStr);
				return date;
			} catch (ParseException e) {
				if(altDateFormat!=null){
					try {
						date = dateFormat.parse(fechas[index].trim());
					} catch (ParseException e1) {
						// TODO: log this error
						throw new RuntimeException(e);
					}
					return date;
				}
				// TODO: log this error
				throw new RuntimeException(e);
			}
		}else{
			return val;
		}
	}
	
	public Object convert(Object[] vals) {
		return convert(vals[0]);
	}
	
	public enum Posicion {
		INICIAL,FINAL;
	}
}
