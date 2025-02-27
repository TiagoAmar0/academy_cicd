package com.ctw.workstation.booking.dtos;
import java.time.LocalDateTime;

public record BookingRequestDTO(Long rackId, Long requesterId, LocalDateTime bookFrom, LocalDateTime bookTo) {
}
