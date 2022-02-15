package com.rirc.OSGI01;

import java.io.Serializable;

public class ConnPrms implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String server;
	private String source;
	private String user;
	private String pass;

	private boolean isAdmin;
	
    protected ConnPrms() {
    }
    
    public ConnPrms(String _server, String _source) {
	    server = _server;
	    source = _source;
    }
    
    public ConnPrms(String _server, String _source, String _user, String _pass) {
	    server = _server;
	    source = _source;
	    user = (_user==null)? null :_user.trim().toUpperCase();
	    pass = _pass;
	    
	    isAdmin= "SYSDBA".equalsIgnoreCase(user);
    }

    public String getServer() {
    	return server;
    }
    public String getSource() {
    	return source;
    }
    public String getUser() {
    	return user;
    }
    public String getPass() {
    	return pass;
    }
    public String getServer_source() {
    	return "server_"+source;
    }
    
    public boolean isAdmin() {
    	return isAdmin;
    }

    public void setUser(String _user) {
    	user= _user;

    	isAdmin= "SYSDBA".equalsIgnoreCase(user);
    }
    public void setPass(String _pass) {
    	pass= _pass;
    }
    
	@Override
	public boolean equals(Object obj) {
		if (obj==null) return false;
		ConnPrms el= (ConnPrms)obj;
		return server.equals(el.server) && source.equals(el.source) && user.equals(el.user) && pass.equals(el.pass);
	}	
	@Override
	public int hashCode() {
		return server.hashCode()+source.hashCode()+user.hashCode()+pass.hashCode();
	}
    
	@Override
	public String toString() {
		return server+','+source+','+user+','+pass;
	}
}
