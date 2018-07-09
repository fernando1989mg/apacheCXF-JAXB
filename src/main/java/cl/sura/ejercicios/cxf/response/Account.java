package cl.sura.ejercicios.cxf.response;

import javax.xml.bind.annotation.XmlElement;

public class Account {
	
	private String accountCode;

	@XmlElement(name="codigo")
	public String getAccountCode() {
		return accountCode;
	}

	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}
}
