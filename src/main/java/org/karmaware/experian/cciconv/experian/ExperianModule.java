package org.karmaware.experian.cciconv.experian;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.karmaware.experian.cciconv.Field;
import org.karmaware.experian.cciconv.FieldFormat;
import org.karmaware.experian.cciconv.FieldFormat.Segment;
import org.karmaware.experian.cciconv.FieldFormat.Type;
import org.karmaware.experian.cciconv.IConversor;
import org.karmaware.experian.cciconv.IField;
import org.karmaware.experian.cciconv.Register;
import org.karmaware.experian.cciconv.experian.PrimeraFechaConv.Posicion;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Named;

public class ExperianModule extends AbstractModule {
	SimpleDateFormat inDateFormat = new SimpleDateFormat("dd/MM/yy");
	SimpleDateFormat outDateFormat = new SimpleDateFormat("yyyyMMdd");
	
	@Override
	protected void configure() {

	}

	@Provides @Named("header-reg")
	Register providesHeaderRegister(){
		List<IField> fields = new ArrayList<IField>(5);
		fields.add(new Field("tipoRegistro", 
				new FieldFormat(Type.FIX, Segment.Mandatory, 6, "EX2000")));
		
		fields.add(new Field("codigoSuscriptor", 
				new FieldFormat(Type.TEXT, Segment.Mandatory, 5)));
		
		fields.add(new Field("fechaGeneracion", 
				new FieldFormat(Type.DATE, Segment.Mandatory, 8)));
		
		fields.add(new Field("fechaSituacion",
				new FieldFormat(Type.DATE, Segment.Mandatory, 8)));
		
		fields.add(new Field("filler", 
				new FieldFormat(Type.BLANK, null, 573)));
		
		return new Register(fields);
	}
	
	@Provides @Named("control-reg")
	Register providesControlRegister(){
		List<IField> fields = new ArrayList<IField>(5);
		fields.add(new Field("tipoRegistro", 
				new FieldFormat(Type.FIX, Segment.Mandatory, 6, "EX2999")));
		
		fields.add(new Field("codigoSuscriptor", 
				new FieldFormat(Type.TEXT, Segment.Mandatory, 5)));
		
		fields.add(new Field("fechaGeneracion", 
				new FieldFormat(Type.DATE, Segment.Mandatory, 8)));
		
		fields.add(new Field("numOperation", "numReg",
				new FieldFormat(Type.NUMBER, Segment.Mandatory, 9)));
		
		fields.add(new Field("numOperation", "numReg",
				new FieldFormat(Type.NUMBER, Segment.Mandatory, 9)));
		
		fields.add(new Field("numOperation", "numReg",
				new FieldFormat(Type.NUMBER, Segment.Mandatory, 9)));
		
		fields.add(new Field("numOperation", "numTotalReg",
				new FieldFormat(Type.NUMBER, Segment.Mandatory, 9)));
		
		fields.add(new Field("filler", 
				new FieldFormat(Type.BLANK, null, 545)));
		
		return new Register(fields);
	}
	
	@Provides @Named("body-regs")
	Register[] providesBodyRegisters(
			@Named("operacion-reg") Register operacion, 
			@Named("nombre-reg") Register nombre,
			@Named("direccion-reg") Register direccion){
		return new Register[]{operacion, nombre, direccion};
	}
	
	@Provides @Named("operacion-reg")
	Register providesOperacionRegister(){
		List<IField> fields = new ArrayList<IField>(19);
		
		fields.add(new Field("tipoRegistro",
				new FieldFormat(Type.FIX, Segment.Mandatory, 6, "EX2010")));
		
		fields.add(new Field("codigoSuscriptor",
				new FieldFormat(Type.TEXT, Segment.Mandatory, 5)));
		
		fields.add(new Field("numeroCuenta", "refnum",
				new FieldFormat(Type.TEXT, Segment.Mandatory, 29),
				new IConversor() {
					public Object convert(Object val) {
						return ((String)val).replace("-", "");
					}
				}));
		
		fields.add(new Field("filler", 
				new FieldFormat(Type.BLANK, null, 1, " ")));
		
		fields.add(new Field("tipoProductoFinanciero", "",
				new FieldFormat(Type.TEXT, Segment.Mandatory, 2, "01")));
		
		fields.add(new Field("situacionPago", "",
				new FieldFormat(Type.TEXT, Segment.Mandatory, 1, "6")));
		
		fields.add(new Field("fechaInicio", "entrada",
				new FieldFormat(Type.DATE, Segment.Mandatory, 8)));
		
		// TODO: Revisar documento para calcular fecha de fin!!
		fields.add(new Field("fechaFin",
				new FieldFormat(Type.DATE, Segment.Conditional, 8)));
		
		fields.add(new Field("fechaPrimerVencimiento", "mvencidos",
				new FieldFormat(Type.DATE, Segment.Mandatory, 8),
				new PrimeraFechaConv(inDateFormat,Posicion.INICIAL)));
		
		fields.add(new Field("fechaUltimoVencimiento", "mvencidos",
				new FieldFormat(Type.DATE, Segment.Mandatory, 8),
				new PrimeraFechaConv(inDateFormat,Posicion.FINAL)));
		
		fields.add(new Field("numeroCoutas", "cuotas",
				new FieldFormat(Type.NUMBER, Segment.Optional, 4)));
		
		fields.add(new Field("frecuenciaPago",
				new FieldFormat(Type.TEXT, Segment.Optional, 2, "01")));
		
		fields.add(new Field("numeroCoutasImpago", "cuotas",
				new FieldFormat(Type.NUMBER, Segment.Conditional, 4)));
		
		fields.add(new Field("importeFinanciado",
				new FieldFormat(Type.AMOUNT, Segment.Optional, 15)));
		
		// TODO: Este valor depende de dos campos: no está soportado
		fields.add(new Field("importeCuota",
				new FieldFormat(Type.AMOUNT, Segment.Optional, 15)));
		
		fields.add(new Field("importePendienteTotal",
				new FieldFormat(Type.AMOUNT, Segment.Optional, 15)));
		
		fields.add(new Field("saldoActualImpago", "deuda",
				new FieldFormat(Type.AMOUNT, Segment.Mandatory, 15)));
		
		fields.add(new Field("fillerReservado",
				new FieldFormat(Type.TEXT, Segment.Mandatory, 3, " ")));
		
		fields.add(new Field("filler", 
				new FieldFormat(Type.BLANK, null, 451, " ")));
		
		return new Register(fields);
	}
	
	@Provides @Named("nombre-reg")
	Register providesNombreRegister(){
		List<IField> fields = new ArrayList<IField>(20);
		
		fields.add(new Field("tipoRegistro",
				new FieldFormat(Type.FIX, Segment.Mandatory, 6, "EX2020")));
		
		fields.add(new Field("codigoSuscriptor",
				new FieldFormat(Type.TEXT, Segment.Mandatory, 5)));
		
		fields.add(new Field("numeroCuenta", "refnum",
				new FieldFormat(Type.TEXT, Segment.Mandatory, 29),
				new IConversor() {
					public Object convert(Object val) {
						return ((String)val).replace("-", "");
					}
				}));
		
		fields.add(new Field("filler",
				new FieldFormat(Type.BLANK, null, 1, " ")));
		
		fields.add(new Field("numDoc", "ident",
				new FieldFormat(Type.TEXT, Segment.Mandatory, 10, " ")));
		
		fields.add(new Field("filler",
				new FieldFormat(Type.BLANK, null, 10, " ")));
		
		fields.add(new Field("tipoInterviniente",
				new FieldFormat(Type.TEXT, Segment.Mandatory, 1, "1")));

		fields.add(new Field("formatoNombre",
				new FieldFormat(Type.TEXT, Segment.Mandatory, 1, "1")));
		
		fields.add(new Field("nombre", "nsocio",
				new FieldFormat(Type.TEXT, Segment.Mandatory, 110)));

		fields.add(new Field("fechaNacimiento",
				new FieldFormat(Type.DATE, Segment.Optional, 8)));
		
		fields.add(new Field("codigoNacionalidad",
				new FieldFormat(Type.TEXT, Segment.Optional, 3, "724")));
		
		fields.add(new Field("codigoCNO",
				new FieldFormat(Type.TEXT, Segment.Optional, 5)));
		
		fields.add(new Field("codigoCNAE",
				new FieldFormat(Type.TEXT, Segment.Optional, 5)));
		
		fields.add(new Field("porcentajeImputable",
				new FieldFormat(Type.TEXT, Segment.Mandatory, 5, "10000")));
		
		fields.add(new Field("tipoDoc1", "ident",
				new FieldFormat(Type.TEXT, Segment.Conditional, 2), 
				new IConversor() {
					
					public Object convert(Object val) {
						if(val instanceof String){
							String valS = (String)val;
							if(valS.startsWith("X")){
								return "02"; // Foreign
							}else if(valS.length()>9){
								return "04"; // Passport
							}
						} 
						return "01"; // NIF
					}
				}));
		
		
		fields.add(new Field("paisExpedicion",
				new FieldFormat(Type.TEXT, Segment.Conditional, 3, "724")));
		
		fields.add(new Field("tipoDoc2",
				new FieldFormat(Type.TEXT, Segment.Mandatory, 2)));
		
		fields.add(new Field("paisExpedicion",
				new FieldFormat(Type.TEXT, Segment.Conditional, 3)));

		fields.add(new Field("numDoc2",
				new FieldFormat(Type.TEXT, Segment.Conditional, 25)));
		
		fields.add(new Field("filler",
				new FieldFormat(Type.BLANK, null, 366, " ")));
		
		return new Register(fields);
	}
	
	@Provides @Named("direccion-reg")
	Register providesDireccionRegister(){
		List<IField> fields = new ArrayList<IField>(20);
		
		fields.add(new Field("tipoRegistro",
				new FieldFormat(Type.FIX, Segment.Mandatory, 6, "EX2030")));
		
		fields.add(new Field("codigoSuscriptor",
				new FieldFormat(Type.TEXT, Segment.Mandatory, 5)));
		
		fields.add(new Field("numeroCuenta", "refnum",
				new FieldFormat(Type.TEXT, Segment.Mandatory, 29),
				new IConversor() {
					public Object convert(Object val) {
						return ((String)val).replace("-", "");
					}
				}));
		
		fields.add(new Field("filler",
				new FieldFormat(Type.BLANK, null, 1, " ")));
		
		fields.add(new Field("numDoc", "ident",
				new FieldFormat(Type.TEXT, Segment.Mandatory, 10, " ")));
		
		fields.add(new Field("filler",
				new FieldFormat(Type.BLANK, null, 10, " ")));
		
		fields.add(new Field("formatoDireccion",
				new FieldFormat(Type.TEXT, Segment.Mandatory, 1, "1")));
		
		fields.add(new Field("direccion",
				new FieldFormat(Type.TEXT, Segment.Mandatory, 110)));

		fields.add(new Field("codMunicipio",
				new FieldFormat(Type.TEXT, Segment.Conditional, 6)));
		
		fields.add(new Field("localidad",
				new FieldFormat(Type.TEXT, Segment.Conditional, 50)));
		
		fields.add(new Field("codProvincia",
				new FieldFormat(Type.TEXT, Segment.Conditional, 2, "28")));

		fields.add(new Field("codPais",
				new FieldFormat(Type.TEXT, Segment.Conditional, 3, "724")));
		
		fields.add(new Field("codPostal", "codpostal",
				new FieldFormat(Type.TEXT, Segment.Conditional, 5)));
		
		fields.add(new Field("telefono",
				new FieldFormat(Type.TEXT, Segment.Optional, 20)));
		
		fields.add(new Field("dirConfirmada",
				new FieldFormat(Type.TEXT, Segment.Conditional, 1, "1")));
		
		fields.add(new Field("tipoDoc1", "ident",
				new FieldFormat(Type.TEXT, Segment.Conditional, 2), 
				new IConversor() {
					
					public Object convert(Object val) {
						if(val instanceof String){
							String valS = (String)val;
							if(valS.startsWith("X")){
								return "02"; // Foreign
							}else if(valS.length()>9){
								return "04"; // Passport
							}
						} 
						return "01"; // NIF
					}
				}));
		
		fields.add(new Field("paisExpedicion",
				new FieldFormat(Type.TEXT, Segment.Conditional, 3, "724")));
		
		fields.add(new Field("tipoDoc2",
				new FieldFormat(Type.TEXT, Segment.Mandatory, 2)));
		
		fields.add(new Field("paisExpedicion",
				new FieldFormat(Type.TEXT, Segment.Conditional, 3)));

		fields.add(new Field("numDoc2",
				new FieldFormat(Type.TEXT, Segment.Conditional, 25)));
		
		fields.add(new Field("filler",
				new FieldFormat(Type.BLANK, null, 306, " ")));
		
		return new Register(fields);
	}
	
	
	public enum Etiqueta{
		TIPO_REGISTRO("tipoRegistro"),
		FECHA_GENERACION("fechaGeneracion"),
		FECHA_SITUACION("fechaSituacion"),
		FILLER("filler"),
		;
		protected String cadena;
		private Etiqueta(String etiqueta){
			cadena = etiqueta;
		}
		public String getCadena(){
			return cadena;
		}
	}
}
