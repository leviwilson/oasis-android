package com.oasisgranger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;

public class Response {
	
	private final HttpResponse response;

	public Response(HttpResponse response) {
		this.response = response;
		
	}
	
	public String getMessage() throws IllegalStateException, IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent())); 
		
		StringBuilder message = new StringBuilder();
		String line;
		
		while( null != (line = reader.readLine())) {
			message.append(line);
		}
		
		return message.toString();
	}
}
