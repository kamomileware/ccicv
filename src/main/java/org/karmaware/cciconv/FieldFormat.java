package org.karmaware.cciconv;


public class FieldFormat implements IFieldFormat {
	
//	static private Formatter formatter = new Formatter();
	static protected boolean debug = false;
	static public void setDebug(boolean debug){	FieldFormat.debug = debug;}
	
	private int tam = 0;
	private String defaultval;
	private Type type;
	private Segment segment;
	private String formatTpl;
	
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
			formatTpl = "%1$tY%1$tm%1$td";
			break;
		case NUMBER:
			formatTpl = "%0"+tam+"d";
			break;
		case TEXT:
			formatTpl = "%-"+tam+"s";
			break;
		case AMOUNT:
			formatTpl = "%0"+tam+"d";
			break;
		case FIX:
			formatTpl = defaultval!=null?
					(defaultval.length()>tam?
						defaultval.substring(0,tam-1):
						String.format("%"+tam+"s", defaultval)
					):String.format("%-"+tam+"s", "");
			break;
		case BLANK:
			formatTpl = String.format("%-"+tam+"s", "");
			break;
		}
	}
	
	public String format(Object val) {
		String result;
		if(val.toString().isEmpty() && segment != Segment.Mandatory){
			if(defaultval==null){
				result = String.format("%"+tam+"s", "");
			}else{
				result = String.format("%"+tam+"s", 
						defaultval.length()>tam? 
								defaultval.substring(0, tam-1)
								:defaultval);
			}
		}
		else {
			if(val.toString().isEmpty() && defaultval!=null){
				result = String.format(formatTpl, 
						defaultval.length()>tam? 
							defaultval.substring(0, tam-1)
							:defaultval);
			}else{
				result = String.format(formatTpl,
						val instanceof String && ((String)val).length()>tam? 
								((String)val).substring(0, tam-1)
								:val);
			}
		}
		if(debug){
			result = result.replace(' ', '*').concat("|");
		}
		return result;
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
