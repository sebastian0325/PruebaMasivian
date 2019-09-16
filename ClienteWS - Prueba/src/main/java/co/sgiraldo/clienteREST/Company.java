package co.sgiraldo.clienteREST;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Clase que representa y describe el atributo compañia de un usuario determinado
 * @author sgira
 *
 */
@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class Company {
	
	
	String name;
	String catchPhrase;
	String bs;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCatchPhrase() {
		return catchPhrase;
	}
	public void setCatchPhrase(String catchPhrase) {
		this.catchPhrase = catchPhrase;
	}
	public String getBs() {
		return bs;
	}
	public void setBs(String bs) {
		this.bs = bs;
	}
	
	

}
