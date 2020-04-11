package co.edu.poligran.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import co.edu.poligran.model.Greeting;
import co.edu.poligran.model.Message;
import co.edu.poligran.model.PingUDP;
import co.edu.poligran.service.PingClient;

@Controller
public class PingUDPController {
	
	@Autowired
	private PingClient pingClient;
	
	@MessageMapping("/hello")
	@SendTo("/topic/greetings")
	public Greeting greeting(Message message) throws Exception {
		Thread.sleep(1000); // simulated delay
		String time = new SimpleDateFormat("HH:mm").format(new Date());

		return new Greeting(message.getFrom() + ": " + message.getText() + " (" + time + ")");
	}	
	
	@GetMapping("/greeting")
	public ModelAndView irPingUDP() {
		InetAddress ipServer = null;
		try {
			ipServer = InetAddress.getLocalHost();
			System.out.println(ipServer.getHostName());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return new ModelAndView("greeting", "pingUDP", new PingUDP(ipServer.getHostName()));
	}
	
	@PostMapping("/enviarPingUDP")
	public String guardar(@ModelAttribute PingUDP pingUDP, Model model) {
		String error=null;
		List<String> respuestaConsole=new ArrayList<String>();
		System.out.println(pingUDP.getServer() + " : " + pingUDP.getPort());
		try {
		//	pingClient = new PingClientImpl();
			respuestaConsole = pingClient.response(pingUDP);
		} catch (Exception e) {
			e.printStackTrace();
			error=e.getMessage();
		}
		model.addAttribute("respuestaConsole", respuestaConsole);
		model.addAttribute("errorConsole", error);
		return "greeting";
	}

}
