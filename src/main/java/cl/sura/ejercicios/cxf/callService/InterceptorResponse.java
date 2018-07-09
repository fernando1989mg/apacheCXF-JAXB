package cl.sura.ejercicios.cxf.callService;

import java.io.IOException;
import java.io.InputStream;

import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.io.CachedOutputStream;
import org.apache.cxf.io.CachedOutputStreamCallback;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.Phase;

public class InterceptorResponse extends LoggingInInterceptor {
	public String response = "";

	public InterceptorResponse() {
		super(Phase.RECEIVE);
	}

	@Override
	public void handleMessage(Message message) throws Fault {

		try {
			InputStream is = message.getContent(InputStream.class);
			CachedOutputStream bos = new CachedOutputStream();
			IOUtils.copy(is, bos);
			bos.flush();
			message.setContent(InputStream.class, bos.getInputStream());
			is.close();
			
			LoggingCallback callback = new LoggingCallback();
			bos.registerCallback(callback);
			bos.close();
			
			setResponse(callback.getResponse());
		} catch (IOException e) {
			System.out.println("ResponseInterceptor: handleMessage(): " + e.getMessage());
		}
	}

	private class LoggingCallback implements CachedOutputStreamCallback {
		private String response;
		
		public void onFlush(CachedOutputStream cos) {
		}

		public void onClose(CachedOutputStream cos) {
			try {
				StringBuilder builder = new StringBuilder();
				cos.writeCacheTo(builder, limit);
				setResponse(builder.toString());
			} catch (Exception e) {
				System.out.println(e);
			}
		}

		public String getResponse() {
			return response;
		}

		public void setResponse(String response) {
			this.response = response;
		}
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}
	
	
}
