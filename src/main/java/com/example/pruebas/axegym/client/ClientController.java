package com.example.pruebas.axegym.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    ClientService clientService;

    @PostMapping
    public ResponseEntity<Client> newClient(@RequestBody ClientDto clientDto){
            return ResponseEntity.ok(clientService.registerClient(clientDto));

    }

    @GetMapping
    public ResponseEntity<Page<ResponseClient>> allClients(@RequestParam(defaultValue = "0")int page,@RequestParam(defaultValue = "10")int size){
        return ResponseEntity.ok(clientService.getClients(page, size));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteClient(@PathVariable Long id){
        try {
                clientService.deleteClientByid(id);
                return ResponseEntity.ok("client delete");
        }catch (RuntimeException e){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable Long id,@RequestBody RequestUpdateClient request){
        Client updatedClient = clientService.updateClient(id,request);
        return ResponseEntity.ok(updatedClient);
    }

    @GetMapping("/search")
    public ResponseEntity<Client> searchClient(@RequestParam String identification){
        Client client = clientService.getClientByIdentification(identification);
        return ResponseEntity.ok(client);
    }
}
