package com.ctw.workstation.team.dtos;

import java.sql.Timestamp;

public record TeamResponseDTO(Long id, String name, String product, String defaultLocation, Timestamp createdAt,
                              Timestamp modifiedAt) {
}
