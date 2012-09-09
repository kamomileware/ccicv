package org.karmaware.experian.cciconv;

import java.util.Formatter;

public class FieldFormat implements IFieldFormat {
	
//	static private Formatter formatter = new Formatter();

	private int tam = 0;
	private String defaultval; 
	private Type type;
	private Segment segment;
	private String format;
	
	public FieldFormat(Type type, Segment seg, int tam) {
		this(type, seg, tam, null);
	}
	
	public FieldFormat(Type type,  Segment seg, int tam, String defaultval) {
		this.type=type;
		this.segment=seg;
		this.tam=tam;
		this.defaultval=defaultval;
		makeFormatString();
	}
	
	protected void makeFormatString(){
		switch (type){
		case DATE:
			format = "%1$tY%1$tm%1$td";
			break;
		case NUMBER:
			format = "%0"+tam+"d";
			break;
		case TEXT:
			format = "%-"+tam+"s";
			break;
		case AMOUNT:
			format = "%0"+tam+"d";
			break;
		case FIX:
			format = defaultval!=null?
					(defaultval.length()>tam?
						defaultval.substring(0,tam-1):
						String.format("%"+tam+"s", defaultval)
					):String.format("%-"+tam+"s", "");
			break;
		case BLANK:
			format = String.format("%-"+tam+"s", "");
			break;
		}
	}
	
	public String format(Object val) {
		if(val.toString().isEmpty() && segment != Segment.Mandatory){
			if(defaultval==null){
				return String.format("%"+tam+"s", "").replace(' ', '*');
			}else{
				return String.format("%"+tam+"s", defaultval).replace(' ', '*');
			}
		}
		else {
			if(val.toString().isEmpty() && defaultval!=null){
				return new Formatter().format(format, defaultval)
					.toString().replace(' ', '*');
			}else{
				return new Formatter().format(format,val)
						.toString().replace(' ', '*');
			}
		}
	}
	
	public enum Type {
		DATE, 
		NUMBER,
		TEXT,
		AMOUNT,
		FIX,
		BLANK;
	}
	public enum Dir {
		LEFT, // For DATE, TEXT & FIX
		RIGHT; // For AMOUNT, NUMBER
	}
	public enum Segment {
		Mandatory,
		Conditional,
		Optional;
	}
}
