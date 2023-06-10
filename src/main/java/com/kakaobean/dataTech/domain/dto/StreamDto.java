package com.kakaobean.dataTech.domain.dto;

import lombok.Getter;

@Getter
public class StreamDto {

    private final Integer onBoard;
    private final String subwayStationName;
    private final String subwayLine;
    private final Double precipitation;
    private final String date;
    private final String weekDay;
    private final String timeStamp;

    public StreamDto(Integer onBoard,
                    String subwayStationName,
                    String subwayLine,
                    Double precipitation,
                    String date,
                    String weekDay,
                    String timeStamp) {
        this.onBoard = onBoard;
        this.subwayStationName = subwayStationName;
        this.subwayLine = subwayLine;
        this.precipitation = precipitation;
        this.date = date;
        this.weekDay = weekDay;
        this.timeStamp = timeStamp;
    }

    public static StreamDto map(BasicDto basicDto) {
        return new StreamDto(
                basicDto.getOnBoard(),
                basicDto.getSubwayStationName(),
                basicDto.getSubwayLine(),
                basicDto.getPrecipitation(),
                basicDto.getDate(),
                basicDto.getWeekDay(),
                basicDto.getTimeStamp()
        );
    }
}
