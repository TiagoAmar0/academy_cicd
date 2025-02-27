package com.ctw.workstation.teammember.dtos;

import java.sql.Timestamp;

public record TeamMemberResponseDTO(Long id, String name, String ctwId, Timestamp createdAt, Timestamp modifiedAt,
                                    String team) {
}
