package org.karmaware.experian.cciconv.experian;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.karmaware.experian.cciconv.IField;
import org.karmaware.experian.cciconv.Register;
import org.karmaware.experian.cciconv.Structure;
import org.karmaware.experian.cciconv.reader.xsl.XslRowIterator;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class Conversor {
	
	static public void prepare(String orig, String dest) throws InvalidFormatException, IOException{
		OutputStream out = dest!=null? 
				new FileOutputStream(dest):
				System.out;
		InputStream in = new FileInputStream(orig);
		Date lastModified = new Date(new File(orig).lastModified());
		
		Injector injector = Guice.createInjector(new ExperianModule());
		Structure struct = injector.getInstance(Structure.class);
		
		convert(out, in, lastModified, struct);
		out.close();
		in.close();
	}

	private static void convert(OutputStream out, InputStream in,
			Date lastModified, Structure struct) throws IOException,
			InvalidFormatException {
		XslRowIterator rit = new XslRowIterator(in, 1);
		
		Map<String, Object> map = 
				new HashMap<String, Object>();
		map.put("codigoSuscriptor", new String("07421"));
		map.put("fechaGeneracion", new Date());
		map.put("fechaSituacion", lastModified);
		
		// Header register
		for(IField field : struct.getHeader().getFields()){
			field.write(map, out);
		}
		out.write(System.lineSeparator().getBytes());
		
		// Customer register
		int numCustom = 0;
		for(Map<String,Object> custom : rit){
			custom.putAll(map);
			for(Register reg : struct.getBody()){
				for(IField field : reg.getFields()){
					if(!rit.debug){
						field.write(custom, out);
					}
				}
				out.write(System.lineSeparator().getBytes());
			}
			numCustom++;
		}
		
		map.put("numReg", new Integer(numCustom));
		map.put("numTotalReg", 
				new Integer((numCustom*struct.getBody().length)+2));
		
		// Header register
		for(IField field : struct.getControl().getFields()){
			field.write(map, out);
		}
		out.write(System.lineSeparator().getBytes());
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			prepare(args[0],args.length>1?args[1]:null);
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
