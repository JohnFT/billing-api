package com.co.devux.spring.springapi.models.services;

import com.co.devux.spring.springapi.models.dao.IClientDao;
import com.co.devux.spring.springapi.models.entity.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClienService implements  IClientService {

    @Autowired
    private IClientDao clientDao;


    @Override
    @Transactional(readOnly = true)
    public List<Client> findAll() {
        return  (List<Client>) this.clientDao.findAll();
    }
}
