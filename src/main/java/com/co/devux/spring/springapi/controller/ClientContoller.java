package com.co.devux.spring.springapi.controller;

import com.co.devux.spring.springapi.models.entity.Client;
import com.co.devux.spring.springapi.models.entity.Region;
import com.co.devux.spring.springapi.models.services.IClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
public class ClientContoller {

    @Autowired
    private IClientService service;

    private  final Logger logger = LoggerFactory.getLogger(ClientContoller.class);

    @GetMapping("/clients")
    public List<Client> getAll(){
        return  this.service.findAll();
    }




    @GetMapping("/clients/page/{page}")
    public Page<Client> getAll(@PathVariable Integer page){

        return  this.service.findAll(PageRequest.of(page, 4));
    }



    @GetMapping("/clients/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        return  this.service.findById(id);
    }

    @PostMapping("/clients")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> add(@Valid @RequestBody  Client client, BindingResult result){

        Map<String, Object> response = new HashMap<>();
        if(result.hasErrors()){

            List<String> errors = result.getFieldErrors().stream()
                    .map(fieldError -> "Field '" + fieldError.getField() + "' "+  fieldError.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors", errors);
        return  new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
        return  this.service.add(client);
    }



    @PutMapping("/clients/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> update(@Valid @RequestBody Client client, @PathVariable Long id, BindingResult result){

        Map<String, Object> response = new HashMap<>();
        if(result.hasErrors()){

            List<String> errors = result.getFieldErrors().stream()
                    .map(fieldError -> "Field '" + fieldError.getField() + "' "+  fieldError.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors", errors);
            return  new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }


       return  this.service.update(id, client);
    }


    @DeleteMapping("/clients/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> delete(@PathVariable Long id){
         return  this.service.delete(id);
    }

    @PostMapping("/clients/upload")
    public ResponseEntity<?> upload(@RequestParam("file")MultipartFile file, @RequestParam("id") Long id){
        logger.info("new file: " + file.getName());
        return  this.service.upload(file, id);
    }


@GetMapping("/uploads/img/{avatar}")
    public ResponseEntity<Resource> getImage(@PathVariable  String avatar){
    return  this.service.getImage(avatar);
    }



    @GetMapping("/clients/regions")
    public List<Region> getAllRegions(){
        return  this.service.getAllRegions();
    }


}
