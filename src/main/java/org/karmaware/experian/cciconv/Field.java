package org.karmaware.experian.cciconv;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class Field implements IField {

	protected String name;
	protected String key;
	protected IFieldFormat format;
	protected IConversor conv;
	
	public Field(String name, IFieldFormat format) {
		this(name, name, format, null);
	}
	
	public Field(String name, String key, IFieldFormat format) {
		this(name, key, format, null);
	}
	
	public Field(String name, String key, IFieldFormat format, IConversor conv) {
		this.name = name;
		this.key = key;
		this.format = format;
		this.conv = conv;
	}
	
	public String get(Map<String,Object> custom) {
			return format.format(custom.containsKey(key)?
				conv!=null? 
						conv.convert(custom.get(key)):
						custom.get(key):
				"");
	}
	
	public void write(Map<String,Object> custom, OutputStream out) throws IOException {
		String val = format.format(custom.containsKey(key)?
				conv!=null? 
						conv.convert(custom.get(key)):
						custom.get(key):
					"");
		out.write(val.toString().getBytes());
	}
}
