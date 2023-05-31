package com.kakaobean.dataTech.dataInjection;

import com.kakaobean.dataTech.domain.*;
import com.kakaobean.dataTech.domain.respositorty.DateRepository;
import com.kakaobean.dataTech.domain.respositorty.DayOfWeekRepository;
import com.kakaobean.dataTech.domain.respositorty.PrecipitationRepository;
import com.kakaobean.dataTech.domain.respositorty.TimeStampRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CsvService {

    private final DayOfWeekRepository dayOfWeekRepository;
    private final TimeStampRepository timeStampRepository;
    private final DateRepository dateRepository;
    private final PrecipitationRepository precipitationRepository;

    // 요일 생성
    @Transactional
    public void registerDay() {
        dayOfWeekRepository.save(new DayOfWeek(Week.MONDAY.toString()));
        dayOfWeekRepository.save(new DayOfWeek(Week.TUESDAY.toString()));
        dayOfWeekRepository.save(new DayOfWeek(Week.WEDNESDAY.toString()));
        dayOfWeekRepository.save(new DayOfWeek(Week.THURSDAY.toString()));
        dayOfWeekRepository.save(new DayOfWeek(Week.FRIDAY.toString()));
        dayOfWeekRepository.save(new DayOfWeek(Week.SATURDAY.toString()));
        dayOfWeekRepository.save(new DayOfWeek(Week.SUNDAY.toString()));
    }

    // 시간 생성
    @Transactional
    public void registerTime() {
        for (int i = 0; i < 24; i++) {
            timeStampRepository.save(new TimeStamp(i + ":00"));
        }
    }

    // 날짜 생성
    @Transactional
    public void registerDate() {
        LocalDate startDate = LocalDate.of(2020, 1, 1);
        LocalDate endDate = LocalDate.of(2022, 12, 31);

        List<Date> dateList = new ArrayList<>();

        for (LocalDate date = startDate; date.isBefore(endDate.plusDays(1)); date = date.plusDays(1)) {

            // date 포멧 바꾸기
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy.M.d");
            LocalDate inputDate = LocalDate.parse(date.toString(), inputFormatter);
            String outputDate = inputDate.format(outputFormatter);

            // 요일 찾기
            String dayOfWeek = date.getDayOfWeek().toString();
            DayOfWeek findDayOfWeek = dayOfWeekRepository.findByDayOfWeek(dayOfWeek)
                    .orElseThrow(() -> new RuntimeException("요일 못 찾겠다"));

            Date dateEntity = new Date(outputDate);
            dateEntity.addWeekDay(findDayOfWeek);
            dateList.add(dateEntity);
        }

        dateRepository.saveAll(dateList);
    }

    // 강수량 생성
    @Transactional
    public void registerPrecipitation() throws CsvValidationException, IOException {
        readWeatherCSVFile("src/main/resources/dataset/weather20.csv", 2020);
        readWeatherCSVFile("src/main/resources/dataset/weather21.csv", 2021);
        readWeatherCSVFile("src/main/resources/dataset/weather22.csv", 2022);
    }

    // weather CSV 파일 읽기
    public void readWeatherCSVFile(String fileName, int year) throws IOException, CsvValidationException {

        CSVReader csvReader = new CSVReader(new FileReader(fileName));
        String[] line;

        csvReader.readNext(); // 첫 번째 라인 건너뛰기

        //--------------------------------------------------------------------------------
        LocalDate startDate = LocalDate.of(year, 1, 1);
        LocalDate lastDate = LocalDate.of(year,12,31);
        int startTime = 0;
        //--------------------------------------------------------------------------------

        while ((line = csvReader.readNext()) != null) {

            // 강수량이 있는 데이터
            String date = line[2].split(" ")[0]; // 2020.1.1 ~2020.12.31
            String time = line[2].split(" ")[1];  // 0:00 ~ 23:00
            double precipitation = Double.parseDouble(line[3]); // 1.7 , 0.2 등등

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.M.d");
            LocalDate endDate = LocalDate.parse(date, formatter); // 다시 2020-01-01로 바꿔줌

            for (LocalDate loopDate = startDate; loopDate.isBefore(endDate.plusDays(1)); loopDate = loopDate.plusDays(1)) {

                // loopDate를 string 포멧으로 변환
                DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy.M.d");
                LocalDate inputDate = LocalDate.parse(loopDate.toString(), inputFormatter);
                String formattedLoopDate = inputDate.format(outputFormatter);

                // 다르면
                if (!loopDate.equals(endDate)) {
                    for (int i = startTime; i < 24; i++) {
                        Date new_0_date = dateRepository.findByDate(formattedLoopDate)
                                .orElseThrow(() -> new RuntimeException("날짜..."));
                        TimeStamp new_0_time = timeStampRepository.findByTimeStamp(i + ":00")
                                .orElseThrow(() -> new RuntimeException("...시간"));

                        Precipitation new_0_precipitation = new Precipitation(0);
                        new_0_precipitation.addDate(new_0_date);
                        new_0_precipitation.addTimeStamp(new_0_time);

                        precipitationRepository.save(new_0_precipitation);

                        startTime = 0;
                    }
                } else {
                    // 강수량 0으로 채우기
                    for (int i = startTime; i < Integer.parseInt(time.split(":")[0]); i++) {
                        Date new_0_date = dateRepository.findByDate(formattedLoopDate)
                                .orElseThrow(() -> new RuntimeException("날짜..."));
                        TimeStamp new_0_time = timeStampRepository.findByTimeStamp(i + ":00")
                                .orElseThrow(() -> new RuntimeException("...시간"));

                        Precipitation new_0_precipitation = new Precipitation(0);
                        new_0_precipitation.addDate(new_0_date);
                        new_0_precipitation.addTimeStamp(new_0_time);

                        precipitationRepository.save(new_0_precipitation);
                    }

                    // 강수량이 있는 데이터 저장
                    // DB에 저장될 강수량에 대한 날짜와 시간 조회하기
                    Date findDate = dateRepository.findByDate(date)
                            .orElseThrow(() -> new RuntimeException("날짜 못 찾겠다"));
                    TimeStamp findTimeStamp = timeStampRepository.findByTimeStamp(time)
                            .orElseThrow(() -> new RuntimeException("시간 못 찾겠다"));

                    // 강수량 엔티티 생성 및 저장
                    Precipitation precipitationEntity = new Precipitation(precipitation);
                    precipitationEntity.addDate(findDate);
                    precipitationEntity.addTimeStamp(findTimeStamp);

                    precipitationRepository.save(precipitationEntity);

                    // start 날짜와 시간 초기화
                    startDate = loopDate;
                    startTime = Integer.parseInt(time.split(":")[0]) + 1;

                    break;
                }
            }

        }

        // 나머지 12월 31일 24시까지 강수량 0 데이터 밀어넣기
        for (LocalDate loopDate = startDate; loopDate.isBefore(lastDate.plusDays(1)); loopDate = loopDate.plusDays(1)) {

            // loopDate를 string 포멧으로 변환
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy.M.d");
            LocalDate inputDate = LocalDate.parse(loopDate.toString(), inputFormatter);
            String formattedLoopDate = inputDate.format(outputFormatter);

            if (!loopDate.equals(lastDate)){
                for (int i = startTime; i < 24; i++) {

                    Date new_0_date = dateRepository.findByDate(formattedLoopDate)
                            .orElseThrow(() -> new RuntimeException("날짜..."));
                    TimeStamp new_0_time = timeStampRepository.findByTimeStamp(i + ":00")
                            .orElseThrow(() -> new RuntimeException("...시간"));

                    Precipitation new_0_precipitation = new Precipitation(0);
                    new_0_precipitation.addDate(new_0_date);
                    new_0_precipitation.addTimeStamp(new_0_time);

                    precipitationRepository.save(new_0_precipitation);

                    startTime = 0;
                }
            }else if(loopDate.equals(lastDate)){
                for (int i = startTime; i < 24; i++) {

                    Date new_0_date = dateRepository.findByDate(formattedLoopDate)
                            .orElseThrow(() -> new RuntimeException("날짜..."));
                    TimeStamp new_0_time = timeStampRepository.findByTimeStamp(i + ":00")
                            .orElseThrow(() -> new RuntimeException("...시간"));

                    Precipitation new_0_precipitation = new Precipitation(0);
                    new_0_precipitation.addDate(new_0_date);
                    new_0_precipitation.addTimeStamp(new_0_time);

                    precipitationRepository.save(new_0_precipitation);

                    startTime = 0;
                }

                break;
            }


        }

    }

}



