package com.cafe.storeservice.dto.alarm;

import java.time.LocalDateTime;
import java.util.Map;

public record AlarmEvent(
        AlarmType type,
        LocalDateTime occurredAt,
        Map<String, Object> payload
) {
}
