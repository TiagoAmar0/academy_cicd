package com.ctw.workstation.rack.dtos;

public record RackRequest(String serialNumber, String status, Long teamId, String defaultLocation) {
}
