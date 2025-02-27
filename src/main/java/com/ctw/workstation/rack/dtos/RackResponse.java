package com.ctw.workstation.rack.dtos;

import java.sql.Timestamp;

public record RackResponse(Long id, String serialNumber, String status, String team, Timestamp createdAt, Timestamp modifiedAt, String defaultLocation) {
}
