package org.karmaware.experian.cciconv;

import java.util.List;

public class Register {

	List<IField> fields;
	
	public Register(List<IField> fields) {
		this.fields = fields;
	}

	public List<IField> getFields() {
		return fields;
	}

	public void setFields(List<IField> fields) {
		this.fields = fields;
	}
	
	
}
