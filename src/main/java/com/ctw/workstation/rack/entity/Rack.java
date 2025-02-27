package com.ctw.workstation.rack.entity;

import com.ctw.workstation.booking.entity.Booking;
import com.ctw.workstation.rack.enums.RackStatus;
import com.ctw.workstation.rackasset.entity.RackAsset;
import com.ctw.workstation.team.entity.Team;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.jetbrains.annotations.NotNull;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "T_RACK")
public class Rack {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rackIdGenerator")
    @SequenceGenerator(name = "rackIdGenerator", sequenceName = "SEQ_RACK_ID")
    private Long id;

    @Column(name = "serial_number", length = 20, nullable = false, unique = true)
    private String serialNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private RackStatus status;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @Column(name = "default_location", nullable = false, length = 10)
    private String defaultLocation;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @Column(name = "created_at")
    private Timestamp createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified_at")
    private Timestamp modifiedAt;

    @OneToMany(mappedBy = "rack", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<RackAsset> rackAssets;

    @OneToMany(mappedBy = "rack", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Booking> bookings;

    public Rack(String serialNumber, RackStatus status, Team team, String defaultLocation) {
        this.serialNumber = serialNumber;
        this.status = status;
        this.team = team;
        this.defaultLocation = defaultLocation;
        this.rackAssets = new ArrayList<>();
        this.bookings = new ArrayList<>();
    }

    public Rack() {}

    public List<RackAsset> getRackAssets() {
        return new ArrayList<>(this.rackAssets);
    }

    public Long getId() {
        return id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public RackStatus getStatus() {
        return status;
    }

    public Team getTeam() {
        return team;
    }

    public String getDefaultLocation() {
        return defaultLocation;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public void setStatus(RackStatus status) {
        this.status = status;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public void setDefaultLocation(String defaultLocation) {
        this.defaultLocation = defaultLocation;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public Timestamp getModifiedAt() {
        return modifiedAt;
    }
}
