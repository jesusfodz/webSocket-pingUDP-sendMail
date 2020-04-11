package co.edu.poligran.model;

public class PingUDP {
	
	private String server;
	private String port;
	
	
	public PingUDP(String server) {
		this.server = server;
	}
	
	public String getServer() {
		return server;
	}
	public void setServer(String server) {
		this.server = server;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	
	

}
