package org.karmaware.cciconv;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class Field implements IField {

	protected String name;
	protected String[] keys;
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
		this.keys = new String[1];
		this.keys[0] = key;
		this.format = format;
		this.conv = conv;
	}
	
	public Field(String name, String[] keys, IFieldFormat format) {
		this(name, keys, format, null);
	}
	
	public Field(String name, String[] keys, IFieldFormat format, IConversor conv) {
		this.name = name;
		this.keys = new String[1];
		this.keys = keys;
		this.format = format;
		this.conv = conv;
	}
	
	public String get(Map<String,Object> custom) {
			return format.format(custom.containsKey(keys[0])?
				conv!=null? 
						conv.convert(custom.get(keys[0])):
						custom.get(keys[0]):
				"");
	}
	
	public void write(Map<String,Object> custom, OutputStream out) throws IOException {
		String val;
		if(keys.length==1){
			val = format.format(custom.containsKey(keys[0])?
					conv!=null? 
							conv.convert(custom.get(keys[0])):
							custom.get(keys[0]):
						"");
		}else{
			Object[] vals = new Object[keys.length];
			int i=0;
			for(String key : keys){
				vals[i++]=custom.get(key);
			}
			val = format.format(conv!=null? 
					conv.convert((Object[])vals):
					vals[0]);
		}
		out.write(val.toString().getBytes("iso-8859-1"));
	}
}
