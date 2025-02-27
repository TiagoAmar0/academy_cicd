package com.ctw.workstation.booking.dtos;

import java.sql.Timestamp;

public record BookingResponseDTO(Long id, String serialNumber, String requesterName, Timestamp bookFrom, Timestamp bookTo, Timestamp createdAt, Timestamp modifiedAt) {
}