package com.kakaobean.dataTech.scheduler;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakaobean.dataTech.config.KafkaConfig;
import com.kakaobean.dataTech.domain.dto.BasicDto;
import com.kakaobean.dataTech.domain.dto.StreamDto;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
@RequiredArgsConstructor
public class StreamsScheduler {

    public static final String STREAMS_APPLICATION_NAME = "STREAMS_APPLICATION";

    private final ObjectMapper objectMapper;

    @Scheduled(fixedRate = 10000) //15초마다 실행
    public void runTask() {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, STREAMS_APPLICATION_NAME);
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConfig.BOOTSTRAP_SERVERS);
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        StreamsBuilder builder = new StreamsBuilder();
        KStream<String, String> basicStream = builder.stream(KafkaConfig.BASIC_TOPIC_NAME);

        //카프카 스트림즈를 본격적으로 적용
        //주말은 필터링한다.
        //출근 시간만 뽑는다.
        //하차 인원도 거른다.
        KStream<String, String> filteredStream = basicStream
                .filter((key, value) -> filterConditions(value))
                .mapValues(this::mapStreamDto);

        filteredStream.to(KafkaConfig.STREAMS_TOPIC_NAME);
        KafkaStreams streams = new KafkaStreams(builder.build(), props);
        streams.start();
    }

    private boolean filterConditions(String value) {
        try {
            BasicDto basicDto = objectMapper.readValue(value, BasicDto.class);
            if(timeToGoWork(basicDto) & dayToGoWork(basicDto)) {
                return true;
            }
        } catch (JsonProcessingException e) {}
        return false;
    }

    private String mapStreamDto(String value) {
        String returnValue = "";
        try {
            BasicDto basicDto = objectMapper.readValue(value, BasicDto.class);
            StreamDto streamDto = StreamDto.map(basicDto);
            returnValue = objectMapper.writeValueAsString(streamDto);
        } catch (JsonProcessingException e) {}
        return returnValue;
    }

    private boolean dayToGoWork(BasicDto basicDto) {
        return !basicDto.getWeekDay().equals("SUNDAY") && !basicDto.getWeekDay().equals("SATURDAY");
    }

    private boolean timeToGoWork(BasicDto basicDto) {
        return basicDto.getTimeStamp().equals("7:00") |
                basicDto.getTimeStamp().equals("800") |
                basicDto.getTimeStamp().equals("9:00");
    }
}





