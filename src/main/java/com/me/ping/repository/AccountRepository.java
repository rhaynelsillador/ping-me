package com.me.ping.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.me.ping.model.Account;

public interface AccountRepository extends JpaRepository<Account, Long>{

	Account findByUsername(String username);

}
