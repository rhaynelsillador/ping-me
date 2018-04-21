package com.me.ping.socket.server;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;
import com.me.ping.services.RoomServives;


@Service
public class InitServer {

	@Value("${socket.port}")
	private int port;
	@Value("${socket.address}")
	private String address;
	private SocketIOServer server;
	@Autowired
	private RoomServives roomServives;
	
	public InitServer() {
		Thread ts = new Thread(new Runnable() {
			public void run() {
				try {
					server();
					System.err.println("SERVER RUNNING");
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		});
		ts.start();
	}
	
	public void server() throws InterruptedException, UnsupportedEncodingException {
		Configuration config = new Configuration();
		config.setHostname(address);
		config.setPort(port);
		server = new SocketIOServer(config);
//		serverEndpointsServices.room(server);
		server.addEventListener("on-connect", String.class, new DataListener<String>() {
			public void onData(SocketIOClient client, String data, AckRequest ackRequest) {
				System.err.println("SOCKET CLIENT CONNECTED " + data);
			}
		});
		
		server.addEventListener("join-room", String.class, new DataListener<String>() {
			public void onData(SocketIOClient client, String data, AckRequest ackRequest) {
				client.joinRoom(data);
				System.err.println("joined room "+data);
				client.sendEvent("join-room", data);
			}
		});
		
		server.addEventListener("rooms", String.class, new DataListener<String>() {
			public void onData(SocketIOClient client, String data, AckRequest ackRequest) {
				client.sendEvent("room-check", roomServives.getRooms(data));			
			}
		});
		server.start();
		
	}

	public SocketIOServer getServer() {
		return server;
	}	
}
