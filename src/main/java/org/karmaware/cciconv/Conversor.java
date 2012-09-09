package org.karmaware.cciconv;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import com.google.inject.Guice;
import com.google.inject.Injector;


/**
 * TODO:
 * 	- Check character encoding writing
 *  
 * @author admin
 *
 */
public class Conversor {
	
	public static void prepare(String orig, String dest, boolean debug) 
			throws InvalidFormatException, IOException{
		OutputStream out = dest!=null? 
				new FileOutputStream(dest):
				System.out;
		InputStream in = new FileInputStream(orig);
		Date lastModified = new Date(new File(orig).lastModified());
		
		Structure struct = buildStructure();
		
		convert(out, in, lastModified, struct, debug);
		out.close();
		in.close();
	}

	public static Structure buildStructure() {
		Injector injector = Guice.createInjector(new ExperianModule());
		Structure struct = injector.getInstance(Structure.class);
		return struct;
	}

	public static void convert(OutputStream out, InputStream in,
			Date lastModified, Structure struct, boolean debug) throws IOException,
			InvalidFormatException {
		FieldFormat.setDebug(debug);
		XslRowIterator rit = new XslRowIterator(in, 1);
		
		Map<String, Object> map = 
				new HashMap<String, Object>();
		map.put("codigoSuscriptor", Config.codigoSuscriptor);
		map.put("fechaGeneracion", new Date());
		map.put("fechaSituacion", lastModified);
		
		// Header register
		for(IField field : struct.getHeader().getFields()){
			field.write(map, out);
		}
		out.write(Config.lineSeparator.getBytes());
		
		// Customer register
		int numCustom = 0;
		for(Map<String,Object> custom : rit){
			custom.putAll(map);
			for(Register reg : struct.getBody()){
				for(IField field : reg.getFields()){
					try{
						field.write(custom, out);
					}catch (Exception e) {
						throw new IOException("Reg. number: "+(numCustom+2)+ ". " + e.getLocalizedMessage(), e);
					}
				}
				out.write(Config.lineSeparator.getBytes());
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
		out.write(Config.lineSeparator.getBytes());
	}
	
	/**
	 * @param args
	 * @throws UnsupportedEncodingException 
	 */
	public static void main(String[] args) throws UnsupportedEncodingException {
		boolean debug = false;
		if(args.length==0){
			String path = Conversor.class.getProtectionDomain().getCodeSource().getLocation().getPath();
			String decodedPath = URLDecoder.decode(path, "UTF-8");
			decodedPath = decodedPath.substring(decodedPath.lastIndexOf('/'));
			System.out.printf("Usage: java -jar %s input.xls [output.txt [debug]] ", decodedPath);
			System.exit(0);
		}
		try {
			if(args.length>2 && args[2].equals("debug")){
				debug = true;
			}else if(args.length>1 && args[1].equals("debug")){
				debug = true;
				args[1]=null;
			}
			prepare(args[0],args.length>1?args[1]:null, debug);
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
