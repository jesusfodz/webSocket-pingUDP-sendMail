package co.edu.poligran.service;

import java.util.List;

import co.edu.poligran.model.PingUDP;

public interface PingClient {
	
	public List<String> response(PingUDP ping) throws Exception;

}
