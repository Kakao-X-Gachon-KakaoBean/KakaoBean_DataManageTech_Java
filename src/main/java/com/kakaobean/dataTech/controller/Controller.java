package com.kakaobean.dataTech.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kakaobean.dataTech.service.KafkaService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class Controller {

    private final KafkaService kafkaService;

    @GetMapping
    public void send() throws JsonProcessingException {
        kafkaService.sendData();
    }
}
