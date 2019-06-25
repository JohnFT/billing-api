package com.co.devux.spring.springapi.controller;

import com.co.devux.spring.springapi.models.entity.Client;
import com.co.devux.spring.springapi.models.services.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ClientContoller {

    @Autowired
    private IClientService service;


    @GetMapping("/clients")
    public List<Client> getAll(){
        return  this.service.findAll();
    }

}
