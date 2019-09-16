package co.sgiraldo.clienteREST;

import javax.xml.bind.annotation.XmlRootElement;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Clase que representa y describe los atributos de geoposici�n asociados a 
 * una direcci�n
 * @author sgira
 *
 */
@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class Geo {

	String lat;
	String lng;
	
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getLng() {
		return lng;
	}
	public void setLng(String lng) {
		this.lng = lng;
	}
	
	
}
