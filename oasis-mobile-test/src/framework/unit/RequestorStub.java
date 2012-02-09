package framework.unit;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import com.oasisgranger.Requestor;
import com.oasisgranger.Response;

public class RequestorStub implements Requestor {

	private Response response;

	@Override
	public Response get(String url) throws ClientProtocolException, IOException {
		return response;
	}
	
	public void setResponse(Response response) {
		this.response = response;
	}

}
