package org.karmaware.cciconv;

import java.util.regex.Pattern;

public class ConvTipoDocumento implements IConversor {
	static Pattern nifpattern = Pattern.compile("(\\d{8}[ \\-]?\\w)");
	static Pattern passpattern = Pattern.compile("(\\d{6,8} )");
	static Pattern emppattern = Pattern.compile("(?i)[A-Z&&[^X]] ?\\d{5,8}");
	
	public Object convert(Object val) {
		if (val instanceof Integer) {
			val = ((Integer) val).toString();
		}
		if (val instanceof String) {
			String valStr = (String) val;
			if (valStr.startsWith("X")) {
				return "02"; // NIE
			} else if (nifpattern.matcher(valStr).find()) {
				return "01"; // NIF
			} else if (emppattern.matcher(valStr).find()) {
				return "03"; // CIF
			} else if (passpattern.matcher(valStr).find()) {
				return "04"; // Passport
			}
		}
		return "";
	}

	public Object convert(Object[] val) {
		return convert(val[0]);
	}
}
