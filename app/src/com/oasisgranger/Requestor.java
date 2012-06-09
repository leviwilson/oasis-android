package com.oasisgranger;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;


public interface Requestor {
	Response get(String url) throws ClientProtocolException, IOException;
}
