package cl.sura.ejercicios.cxf.response;

import javax.xml.bind.annotation.XmlElement;

import org.eclipse.persistence.oxm.annotations.XmlPath;

public class Sacu {
	private String token;
	
	private String passwordType;

	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@XmlElement(name="tipoClave")
	public String getPasswordType() {
		return passwordType;
	}

	
	public void setPasswordType(String passwordType) {
		this.passwordType = passwordType;
	}

}
