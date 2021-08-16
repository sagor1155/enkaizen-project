package com.project.ImageUploader.controller;

import com.project.ImageUploader.dto.WsMessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebSocketController {

    @Autowired
    SimpMessagingTemplate brokerMessagingTemplate;

    @PostMapping("/send")
    public ResponseEntity<Void> sendMessage(@RequestBody WsMessageDTO wsMessageDTO) {
        brokerMessagingTemplate.convertAndSend("/topic/message", wsMessageDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @MessageMapping("/sendMessage")
    public void receiveMessage(@Payload WsMessageDTO wsMessageDTO) {
        // receive message from client
    }

    @SendTo("/topic/message")
    public WsMessageDTO broadcastMessage(@Payload WsMessageDTO wsMessageDTO) {
        return wsMessageDTO;
    }
}
