package com.cafe.storeservice.event;

import com.cafe.storeservice.dto.alarm.AlarmEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AlarmEventPublisher {

    private static final String TOPIC = "alarm-events";

    private final KafkaTemplate<String, AlarmEvent> kafkaTemplate;

    public void publish(AlarmEvent event) {
        kafkaTemplate.send(TOPIC, event.type().name(), event)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        log.error("알람 이벤트 발행 실패: {}", event, ex);
                    }
                });
    }
}
