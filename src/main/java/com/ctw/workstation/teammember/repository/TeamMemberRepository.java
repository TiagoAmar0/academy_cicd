package com.ctw.workstation.teammember.repository;

import com.ctw.workstation.teammember.entity.TeamMember;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
public class TeamMemberRepository implements PanacheRepository<TeamMember> {
    public Optional<TeamMember> findByCtwId(String ctwId){
        return find("ctwId", ctwId).firstResultOptional();
    }
}
