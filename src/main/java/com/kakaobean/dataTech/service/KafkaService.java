package com.kakaobean.dataTech.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakaobean.dataTech.config.KafkaConfig;
import com.kakaobean.dataTech.domain.SubwayUsage;
import com.kakaobean.dataTech.domain.respositorty.SubwayUsageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KafkaService {

    private final KafkaTemplate kafkaTemplate;
    private final SubwayUsageRepository subwayUsageRepository;
    private final ObjectMapper objectMapper;

    public void sendData() throws JsonProcessingException {
        List<SubwayUsage> list = subwayUsageRepository.findAll();
        for (SubwayUsage subwayUsage : list) {
            kafkaTemplate.send(KafkaConfig.TOPIC_NAME, objectMapper.writeValueAsString(subwayUsage));
        }
    }
}
