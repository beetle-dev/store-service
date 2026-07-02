package com.cafe.storeservice.event;

import com.cafe.storeservice.dto.alarm.AlarmEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AlarmRaisedEventListener {

    private final AlarmEventPublisher alarmEventPublisher;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onAlarmRaised(AlarmEvent event) {
        alarmEventPublisher.publish(event);
    }
}
