package com.redhat.ea.fair.service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/auth")
public class AuthService {
	
	private static Set<String> _activeTokens;
	
	public AuthService(){
		_activeTokens = Collections.synchronizedSet(new HashSet<String>());
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/validate/{key}")
	public boolean validateToken(@PathParam("key") String key){
		return _activeTokens.contains(key);
	}
	
	@DELETE
	@Path("/validate/{key}")
	public void deactivateToken(@PathParam("key") String key){
		if(_activeTokens.contains(key)){
			_activeTokens.remove(key);
		}
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/initialize")
	public String initialize(){
		String token = ((Double)Math.random()).toString();
		_activeTokens.add(token);
		return token;
	}

}
