package com.kakaobean.dataTech.domain.respositorty;

import com.kakaobean.dataTech.domain.Date;
import com.kakaobean.dataTech.domain.TimeStamp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TimeStampRepository extends JpaRepository<TimeStamp, Long> {

    @Query("select t from TimeStamp t where t.time_stamp = :time")
    Optional<TimeStamp> findByTimeStamp(@Param("time") String time);
}
