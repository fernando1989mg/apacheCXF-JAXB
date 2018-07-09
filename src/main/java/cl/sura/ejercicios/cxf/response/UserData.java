package cl.sura.ejercicios.cxf.response;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.eclipse.persistence.oxm.annotations.XmlPath;

@XmlRootElement(name = "respuesta")
public class UserData {
	private Sacu sacu;
	@XmlPath("datos/respuestas/respuesta/datos/cuentas/cuenta")
	List<Account> accounts;

	public Sacu getSacu() {
		return sacu;
	}

	public void setSacu(Sacu sacu) {
		this.sacu = sacu;
	}

	public List<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}

}
