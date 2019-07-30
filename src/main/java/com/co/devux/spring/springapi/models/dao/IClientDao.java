package com.co.devux.spring.springapi.models.dao;

import com.co.devux.spring.springapi.models.entity.Client;
import com.co.devux.spring.springapi.models.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IClientDao extends JpaRepository<Client, Long> {

    @Query("from  Region")
    public List<Region> getAllRegions ();
}
