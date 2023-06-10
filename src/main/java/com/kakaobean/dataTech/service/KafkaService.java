package com.kakaobean.dataTech.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakaobean.dataTech.config.KafkaConfig;
import com.kakaobean.dataTech.domain.dto.BasicDto;
import com.kakaobean.dataTech.infrastructure.SubwayUsageQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KafkaService {

    private final KafkaTemplate kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final SubwayUsageQueryRepository queryRepository;

    public void sendData() throws JsonProcessingException {
        List<BasicDto> list = queryRepository.findAll();
        for (BasicDto dto : list) {
            kafkaTemplate.send(KafkaConfig.BASIC_TOPIC_NAME, objectMapper.writeValueAsString(dto));
        }
    }
}
