package com.co.devux.spring.springapi.models.services;

import com.co.devux.spring.springapi.models.entity.Client;
import com.co.devux.spring.springapi.models.entity.Region;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IClientService {

    public List<Client> findAll();

    public Page<Client> findAll(Pageable pageable);

    public ResponseEntity<?> findById(Long id);

    public ResponseEntity<?>  add(Client client);

    public ResponseEntity<?>  delete(Long id);

    public  ResponseEntity<?>   update(Long id, Client client);

    public ResponseEntity<?> upload(MultipartFile file, Long id);

    public ResponseEntity<Resource> getImage(String avatar);

    public List<Region> getAllRegions ();
}
