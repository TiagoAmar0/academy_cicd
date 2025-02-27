package com.ctw.workstation.rack.repository;

import com.ctw.workstation.rack.entity.Rack;
import com.ctw.workstation.rack.enums.RackStatus;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class RackRepository implements PanacheRepository<Rack> {
    public List<Rack> findByStatus(RackStatus status) {
        return find("status", status).list();
    }
}
