package org.karmaware.cciconv.work;

public class RegistroCtc {
	
	protected String delegacion;
	protected String entidad;
	protected String refnum;
	protected String nsocio;
	protected String entrada;
	protected String mvencidos;
	protected String deuda;
	protected String ident;
	protected String direccion;
	protected String codpostal;
	protected String localidad;
	protected String provincia;
	protected String cuotas;
	
	
	
	public RegistroCtc(String delegacion,
			String entidad,
			String refnum,
			String nsocio,
			String entrada,
			String mvencidos,
			String deuda,
			String ident,
			String direccion,
			String codpostal,
			String localidad,
			String provincia,
			String cuotas) {
		this.delegacion=delegacion;
		this.entidad=entidad;
		this.refnum=refnum;
		this.nsocio=nsocio;
		this.entrada=entrada;
		this.mvencidos=mvencidos;
		this.deuda=deuda;
		this.ident=ident;
		this.direccion=direccion;
		this.codpostal=codpostal;
		this.localidad=localidad;
		this.provincia=provincia;
		this.cuotas=cuotas;
	}
	
	public String getDelegacion() {
		return delegacion;
	}
	public void setDelegacion(String delegacion) {
		this.delegacion = delegacion;
	}
	public String getEntidad() {
		return entidad;
	}
	public void setEntidad(String entidad) {
		this.entidad = entidad;
	}
	public String getRefnum() {
		return refnum;
	}
	public void setRefnum(String refnum) {
		this.refnum = refnum;
	}
	public String getNsocio() {
		return nsocio;
	}
	public void setNsocio(String nsocio) {
		this.nsocio = nsocio;
	}
	public String getEntrada() {
		return entrada;
	}
	public void setEntrada(String entrada) {
		this.entrada = entrada;
	}
	public String getMvencidos() {
		return mvencidos;
	}
	public void setMvencidos(String mvencidos) {
		this.mvencidos = mvencidos;
	}
	public String getDeuda() {
		return deuda;
	}
	public void setDeuda(String deuda) {
		this.deuda = deuda;
	}
	public String getIdent() {
		return ident;
	}
	public void setIdent(String ident) {
		this.ident = ident;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getCodpostal() {
		return codpostal;
	}
	public void setCodpostal(String codpostal) {
		this.codpostal = codpostal;
	}
	public String getLocalidad() {
		return localidad;
	}
	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	public String getCuotas() {
		return cuotas;
	}
	public void setCuotas(String cuotas) {
		this.cuotas = cuotas;
	}
}
