package com.me.ping.socket.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;
import com.me.ping.services.RoomServives;

@Service
public class ServerEndpointsServices {

	@Autowired
	private RoomServives roomServives;
	
	public void room(SocketIOServer server) {
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
	}
}
