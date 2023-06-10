package com.kakaobean.dataTech.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakaobean.dataTech.config.KafkaConfig;
import com.kakaobean.dataTech.domain.SubwayUsage;
import com.kakaobean.dataTech.domain.dto.Dto;
import com.kakaobean.dataTech.domain.respositorty.SubwayUsageRepository;
import com.kakaobean.dataTech.infrastructure.SubwayUsageQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KafkaService {

    private final KafkaTemplate kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final SubwayUsageQueryRepository queryRepository;

    public void sendData() throws JsonProcessingException {
        List<Dto> list = queryRepository.findAll();
        for (Dto dto : list) {
            kafkaTemplate.send(KafkaConfig.TOPIC_NAME, objectMapper.writeValueAsString(dto));
        }
    }
}
