package com.co.devux.spring.springapi.models.dao;

import com.co.devux.spring.springapi.models.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IClientDao extends JpaRepository<Client, Long> {}
