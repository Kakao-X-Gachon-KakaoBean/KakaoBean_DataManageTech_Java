package com.kakaobean.dataTech.domain.respositorty;

import com.kakaobean.dataTech.domain.TimeStamp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeStampRepository extends JpaRepository<TimeStamp, Long> {
}
