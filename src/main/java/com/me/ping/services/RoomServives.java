package com.me.ping.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.me.ping.model.Account;
import com.me.ping.model.Room;
import com.me.ping.repository.AccountRepository;
import com.me.ping.repository.RoomRepository;

@Service
public class RoomServives {
	
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private RoomRepository roomRepository;
	@Autowired
	private AccountRepository accountRepository;
	
	public ArrayNode getRooms(String username) {
		ArrayNode data = objectMapper.createArrayNode();
		Account account = accountRepository.findByUsername(username);
		List<Room> rooms = roomRepository.findByAccounts(account);
		for (Room room : rooms) {
			ObjectNode node = objectMapper.createObjectNode();
			System.err.println(room);
//			node.put("id", room.getId());
//			node.put("name", room.getName());
			data.add(node);
		}
		return data;
	}
	
	
	
}
