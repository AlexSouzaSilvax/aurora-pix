package com.aurora.pix.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webhook")
public class WebHookController {

    @PostMapping
    public void receiveEvent(@RequestBody Map<String, Object> payload) {

        System.out.println(payload);

        // Acessa o "event"
        Map<String, Object> event = (Map<String, Object>) payload.get("event");

        String id = (String) event.get("id");
        Boolean isDelivered = (Boolean) event.get("isDelivered");

        // Acessa "log" dentro de "event"
        Map<String, Object> log = (Map<String, Object>) event.get("log");
        String type = (String) log.get("type");

        // Acessa "boleto" dentro de "log"
        Map<String, Object> boleto = (Map<String, Object>) log.get("boleto");
        String taxId = (String) boleto.get("taxId");

        System.out.println("ID do evento: " + id);
        System.out.println("Entregue? " + isDelivered);
        System.out.println("Tipo do log: " + type);
        System.out.println("CNPJ do boleto: " + taxId);
        System.out.println("Status: " + boleto.get("status"));
    }
}