package com.kakaobean.dataTech.dataInjection;

import com.kakaobean.dataTech.domain.Date;
import com.kakaobean.dataTech.domain.DayOfWeek;
import com.kakaobean.dataTech.domain.TimeStamp;
import com.kakaobean.dataTech.domain.Week;
import com.kakaobean.dataTech.domain.respositorty.DateRepository;
import com.kakaobean.dataTech.domain.respositorty.DayOfWeekRepository;
import com.kakaobean.dataTech.domain.respositorty.TimeStampRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CsvService {

    private final DayOfWeekRepository dayOfWeekRepository;
    private final TimeStampRepository timeStampRepository;
    private final DateRepository dateRepository;

    // 요일 생성
    @Transactional
    public void registerDay(){
        dayOfWeekRepository.save(new DayOfWeek(Week.MONDAY.toString()));
        dayOfWeekRepository.save(new DayOfWeek(Week.TUESDAY.toString()));
        dayOfWeekRepository.save(new DayOfWeek(Week.WEDNESDAY.toString()));
        dayOfWeekRepository.save(new DayOfWeek(Week.THURSDAY.toString()));
        dayOfWeekRepository.save(new DayOfWeek(Week.FRIDAY.toString()));
        dayOfWeekRepository.save(new DayOfWeek(Week.SATURDAY.toString()));
        dayOfWeekRepository.save(new DayOfWeek(Week.SUNDAY.toString()));
    }

    @Transactional
    public void registerTime(){
        for (int i = 0; i < 24; i++) {
            timeStampRepository.save(new TimeStamp(i+""));
        }
    }

    @Transactional
    public void registerDate(){
        LocalDate startDate = LocalDate.of(2020,1,1);
        LocalDate endDate = LocalDate.of(2022,12,31);

        List<Date> dateList = new ArrayList<>();

        for (LocalDate date = startDate; date.isBefore(endDate.plusDays(1)); date = date.plusDays(1)) {

            String dayOfWeek = date.getDayOfWeek().toString();
            DayOfWeek findDayOfWeek = dayOfWeekRepository.findByDayOfWeek(dayOfWeek)
                    .orElseThrow(() -> new RuntimeException("Invalid day of week"));

            Date dateEntity = new Date(date);
            dateEntity.addWeekDay(findDayOfWeek);
            dateList.add(dateEntity);
        }

        dateRepository.saveAll(dateList);
    }

}
