package org.karmaware.experian.cciconv.experian;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

import org.karmaware.experian.cciconv.IConversor;

public class PrimeraFechaConv implements IConversor {

	DateFormat dateFormat;
	Posicion pos;
	
	public PrimeraFechaConv(DateFormat dateFormat, Posicion pos) {
		this.dateFormat = dateFormat;
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
				fechas = fechas[index].split("Y");
				index = pos == Posicion.INICIAL? 0:fechas.length-1;
			}
			
			if(fechas[index].contains("AL")){
				fechas = fechas[index].split("AL");
				index = pos == Posicion.INICIAL? 0:fechas.length-1;
			}
			
			Date date;
			try {
				date = dateFormat.parse(fechas[index].trim());
				return date;
			} catch (ParseException e) {
				throw new RuntimeException(e);
			}
		}else{
			return val;
		}
	}

	public enum Posicion {
		INICIAL,FINAL;
	}
}
