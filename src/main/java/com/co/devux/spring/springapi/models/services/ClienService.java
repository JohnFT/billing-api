package com.co.devux.spring.springapi.models.services;

import com.co.devux.spring.springapi.models.dao.IClientDao;
import com.co.devux.spring.springapi.models.entity.Client;
import com.co.devux.spring.springapi.models.entity.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class ClienService implements  IClientService {

    @Autowired
    private IClientDao clientDao;


    @Override
    @Transactional(readOnly = true)
    public List<Client> findAll() {
        return  (List<Client>) this.clientDao.findAll();
    }



    @Override
    @Transactional(readOnly = true)
    public Page<Client> findAll(Pageable pageable) {
        return  (Page<Client>) this.clientDao.findAll(pageable);
    }



    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<?> findById(Long id) {

        Client client =  null;
        Map<String, Object> response = new HashMap<>();
        try{
            client = this.clientDao.findById(id).orElse(null);
            if(client == null){
                response.put("message", "The client id: ".concat(id.toString()).concat(" no exist"));
                return  new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
            }

            return  new ResponseEntity<Client>(client, HttpStatus.OK);

        }catch (DataAccessException ex){
            response.put("message", "The client id: ".concat(id.toString()).concat(" no exist"));
            response.put("error", ex.getMessage().concat(" : ").concat(ex.getMostSpecificCause().getMessage()));

            return  new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }






    @Override
    @Transactional
    public  ResponseEntity<?>   add(Client client) {

        Client cli = null;
        Map<String, Object> response = new HashMap<>();

        try{
            cli = this.clientDao.save(client);
            return new ResponseEntity<Client>(cli, HttpStatus.CREATED);

        }catch (DataAccessException ex ){
            response.put("message", "Error on insert data base");
            response.put("error", ex.getMessage().concat(" : ").concat(ex.getMostSpecificCause().getMessage()));

            return  new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    @Transactional
    public ResponseEntity<?> delete(Long id) {
        Map<String, Object> response = new HashMap<>();

        try {


            Client cli  = this.clientDao.findById(id).orElse(null);
            if(cli == null){
                response.put("message", "The client id: ".concat(id.toString()).concat(" no exist"));
                return  new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
            }


            String lastFileName = cli.getAvatar();
            if(lastFileName != null && lastFileName.length() > 0 ){

                Path route = Paths.get("uploads").resolve(lastFileName).toAbsolutePath();
                File lastFile = route.toFile();

                if(lastFile.exists() && lastFile.canRead()){
                    lastFile.delete();
                }
            }


            this.clientDao.deleteById(id);

        }catch (DataAccessException ex){
            response.put("message", "Error on delete the client on data base");
            response.put("error", ex.getMessage().concat(" : ").concat(ex.getMostSpecificCause().getMessage()));

            return  new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "The client deleted successs");

        return  new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> update(Long id, Client client) {
        Client cli = null;
        Map<String, Object> response = new HashMap<>();

        try{

            cli = this.clientDao.findById(id).orElse(null);
            if(client == null){
                response.put("message", "The client id: ".concat(id.toString()).concat(" no exist"));
                return  new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
            }

            cli.setCreateAt(client.getCreateAt());
            cli.setEmail(client.getEmail());
            cli.setFirstName(client.getFirstName());
            cli.setLastName(client.getLastName());
            cli.setRegion(client.getRegion());
            this.clientDao.save(cli);

            return new ResponseEntity<Client>(cli, HttpStatus.CREATED);

        }catch (DataAccessException ex){
            response.put("message", "Error on update on data base");
            response.put("error", ex.getMessage().concat(" : ").concat(ex.getMostSpecificCause().getMessage()));

            return  new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @Override
    @Transactional
    public ResponseEntity<?> upload(MultipartFile file, Long id) {


        Client cli = null;
        Map<String, Object> response = new HashMap<>();

        try{

            cli = this.clientDao.findById(id).orElse(null);
            if(cli == null){
                response.put("message", "The client id: ".concat(id.toString()).concat(" no exist"));
                return  new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
            }

            String lastFileName = cli.getAvatar();
           if(lastFileName != null && lastFileName.length() > 0 ){

               Path route = Paths.get("uploads").resolve(lastFileName).toAbsolutePath();
               File lastFile = route.toFile();

               if(lastFile.exists() && lastFile.canRead()){
                   lastFile.delete();
               }
           }

            if(!file.isEmpty()){

               String nameFile = UUID.randomUUID().toString() + "_" +file.getOriginalFilename().replace(" ", "");
               Path route = Paths.get("uploads").resolve(nameFile).toAbsolutePath();
                Files.copy(file.getInputStream(), route);

                cli.setAvatar(nameFile);

                this.clientDao.save(cli);

                return  new ResponseEntity<Client>(cli  , HttpStatus.CREATED);
            }


        }catch (DataAccessException ex){
            response.put("message", "Error on upload register avatar");
            response.put("error", ex.getMessage().concat(" : ").concat(ex.getMostSpecificCause().getMessage()));

            return  new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IOException e) {
            e.printStackTrace();

            response.put("message", "Error on upload image file");
            response.put("error", e.getMessage());
        }

        response.put("message", "The client deleted successs");

        return  new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

    public ResponseEntity<Resource> getImage(String avatar){
        Path route = Paths.get("uploads").resolve(avatar).toAbsolutePath();
        Resource resource = null;

        try{
            resource = new UrlResource(route.toUri());

            if(!resource.exists() && !resource.isReadable()){
                throw new RuntimeException("No existe la imagen " + avatar);


            }


        }catch (MalformedURLException e){
            e.printStackTrace();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"");



        return new ResponseEntity<Resource>(resource,headers, HttpStatus.OK);

    }


    @Override
    @Transactional(readOnly = true)
    public List<Region> getAllRegions() {
        return this.clientDao.getAllRegions();
    }
}
