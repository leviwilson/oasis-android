package com.oasisgranger.di;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.oasisgranger.Requestor;
import com.oasisgranger.Response;

public class HttpRequestor implements Requestor {

	public Response get(String url) throws ClientProtocolException, IOException {
		return new Response(new DefaultHttpClient().execute(new HttpGet(url)));
	}

}
