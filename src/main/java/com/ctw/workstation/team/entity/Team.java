package com.ctw.workstation.team.entity;

import com.ctw.workstation.rack.entity.Rack;
import com.ctw.workstation.teammember.entity.TeamMember;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.jetbrains.annotations.NotNull;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "T_TEAM")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "teamIdGenerator")
    @SequenceGenerator(name = "teamIdGenerator", sequenceName = "SEQ_TEAM_ID", allocationSize = 1)
    private Long id;

    @Column(name = "name", length = 50, nullable = false, unique = true)
    @NotNull
    private String name;

    @Column(name = "product", length = 50, nullable = false)
    @NotNull
    private String product;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "modified_at")
    private Timestamp modifiedAt;

    @Column(name = "default_location", length = 10, nullable = false)
    @NotNull
    private String defaultLocation;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TeamMember> teamMembers = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rack> racks = new ArrayList<>();

    protected Team() { }

    public Team(@NotNull String name, @NotNull String product, @NotNull String defaultLocation) {
        this.name = name;
        this.product = product;
        this.defaultLocation = defaultLocation;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getProduct() { return product; }
    public void setProduct(String product) { this.product = product; }
    public Timestamp getCreatedAt() { return createdAt; }
    public Timestamp getModifiedAt() { return modifiedAt; }
    public String getDefaultLocation() { return defaultLocation; }
    public void setDefaultLocation(String defaultLocation) { this.defaultLocation = defaultLocation; }
    public List<TeamMember> getTeamMembers() { return new ArrayList<>(teamMembers); }
}
