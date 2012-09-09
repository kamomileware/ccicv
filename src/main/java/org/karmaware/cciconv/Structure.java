package org.karmaware.cciconv;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

@Singleton
public class Structure {

	protected Register header;
	protected Register[] body;
	protected Register control;
	
	public Register getHeader() {
		return header;
	}
	
	@Inject 
	public void setHeader(@Named("header-reg") Register header) {
		this.header = header;
	}
	
	public Register[] getBody() {
		return body;
	}
	
	@Inject 
	public void setBody(@Named("body-regs") Register[] body) {
		this.body = body;
	}
	
	public Register getControl() {
		return control;
	}
	
	@Inject 
	public void setControl(@Named("control-reg")Register control) {
		this.control = control;
	}
	
//	@Inject 
//	public Structure(@Named("header-reg") Register header,
//			@Named("body-regs") Register[] body,
//			@Named("control-reg")) {
//		// TODO Auto-generated constructor stub
//	}
	
	
}
