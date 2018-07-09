package cl.sura.ejercicios.cxf.callService;

import java.io.StringReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;

import com.thomas_bayer.blz.BLZService;
import com.thomas_bayer.blz.BLZServicePortType;

import cl.sura.ejercicios.cxf.cxf_jaxb.Response;

public class ProcessSoapResponse {

	
	@SuppressWarnings("unchecked")
	public <T> T stringResponseToClass(Class<T> responseType, String rsString){
		try{
			Constructor<?> ctor = responseType.getConstructor();
			T clazzResponse = (T) ctor.newInstance();

		
			if(clazzResponse.getClass().isAnnotationPresent(XmlRootElement.class) == false){
				throw new IllegalArgumentException("La clase de respuesta ("+clazzResponse.getClass().getName()+") debe tener la anotación @XmlRootElement"); 
			}else if(clazzResponse.getClass().getAnnotation(XmlRootElement.class).name().equals("##default")){
				throw new IllegalArgumentException("Es necesario definir el atributo 'name' en la anotación @XmlRootElement de la clase response ("+clazzResponse.getClass().getName()+")");
			}
		
		    JAXBContext jc = JAXBContext.newInstance(clazzResponse.getClass());
	        Unmarshaller unmarshaller = jc.createUnmarshaller();
	        
	        XmlRootElement rootElement = clazzResponse.getClass().getAnnotation(XmlRootElement.class);
	         
	        rsString = rsString.replaceAll("([<^][\\/]*)[\\w]+\\:", "$1").trim();
	        rsString = rsString.replaceAll("[ ]*xmlns.[^\"].*[\"]", "").trim(); //eliminamos los namespace declarados en los tags ej: <Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"> quedaría <Envelope>
	       
	        try {
	        	rsString = rsString.substring(rsString.indexOf("<"+rootElement.name()+">"), rsString.lastIndexOf("</"+rootElement.name()));
	        }catch(ArrayIndexOutOfBoundsException e) {
	        	throw new IllegalArgumentException("TECNICO: El rootElement proporcionado ("+rootElement.name()+") en el response no existe");
	        }catch(StringIndexOutOfBoundsException e){
	            try{
	                rsString = rsString.replaceAll("[ ]*xmlns.*[\"]>", ">").trim();
	                rsString = rsString.substring(rsString.indexOf("<"+rootElement.name()), rsString.indexOf("</"+rootElement.name()));
	            }catch(StringIndexOutOfBoundsException e1){
	                throw new IllegalArgumentException("TECNICO: El rootElement proporcionado ("+rootElement.name()+") en el response no existe");
	            }
	        }
	        
	        rsString = rsString+"</"+rootElement.name()+">";
	        rsString = rsString.replaceAll("<\\w+:{0,1}\\w+[ ]*\\/>|<\\w+[ ]*\\/>", ""); //eliminamos etiquetas vacias, ej: <value />
	        
	        StringReader reader = new StringReader(rsString);
	        
	        try {
		        return (T)unmarshaller.unmarshal(reader);
            }catch(JAXBException e){
	    		throw new RuntimeException(e);
	    	}
		} catch (SecurityException | IllegalArgumentException 
				| IllegalAccessException | StringIndexOutOfBoundsException | InstantiationException | InvocationTargetException | NoSuchMethodException | JAXBException e) {
			throw new RuntimeException("TECNICO: Error inesperado al intentar crear el objeto de respuesta",e);
		}
	}
}
