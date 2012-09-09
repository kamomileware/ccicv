package org.karmaware.cciconv;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public interface IField {
	public String get(Map<String,Object> val);
	public void write(Map<String,Object> val, OutputStream out) throws IOException;
}
