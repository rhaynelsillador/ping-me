package com.me.ping.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.me.ping.model.Account;
import com.me.ping.model.Room;

public interface RoomRepository extends JpaRepository<Room, Long>{
	
	List<Room> findByAccounts(Account account);

}
