package com.co.devux.spring.springapi.models.services;

import com.co.devux.spring.springapi.models.entity.Client;

import java.util.List;

public interface IClientService {

    public List<Client> findAll();
}
