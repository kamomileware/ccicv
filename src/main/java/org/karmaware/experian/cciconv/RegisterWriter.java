package org.karmaware.experian.cciconv;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class RegisterWriter {

	OutputStream out;
	boolean closed;
	
	public RegisterWriter(OutputStream out) {
		this.out = out;
	}
	
	public void write(Register reg, Map<String,Object> values) throws IOException{
		for(IField field : reg.getFields()){
			field.write(values, out);
		}
		out.write(System.lineSeparator().getBytes());
	}	
}
