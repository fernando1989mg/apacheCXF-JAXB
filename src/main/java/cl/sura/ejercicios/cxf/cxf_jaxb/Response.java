package cl.sura.ejercicios.cxf.cxf_jaxb;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="details")
public class Response {
	private String bezeichnung;
	private String bic;
	private String ort;
	private String plz;

	public String getBezeichnung() {
		return bezeichnung;
	}

	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}

	public String getBic() {
		return bic;
	}

	public void setBic(String bic) {
		this.bic = bic;
	}

	public String getOrt() {
		return ort;
	}

	public void setOrt(String ort) {
		this.ort = ort;
	}

	public String getPlz() {
		return plz;
	}

	public void setPlz(String plz) {
		this.plz = plz;
	}

}
