package com.co.devux.spring.springapi.models.dao;

import com.co.devux.spring.springapi.models.entity.Client;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface IClientDao extends CrudRepository<Client, Long> {}
