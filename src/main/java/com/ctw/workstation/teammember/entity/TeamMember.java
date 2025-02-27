package com.ctw.workstation.teammember.entity;

import com.ctw.workstation.team.entity.Team;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Entity
@Table(name = "T_TEAM_MEMBER")
public class TeamMember {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "teamMemberIdGenerator")
    @SequenceGenerator(name = "teamMemberIdGenerator", sequenceName = "SEQ_TEAM_MEMBER_ID")
    private Long id;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "ctw_id", length = 10, nullable = false, unique = true)
    private String ctwId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified_at")
    private Timestamp modifiedAt;

    public TeamMember(String ctwId, Team team, String name) {
        this.ctwId = ctwId;
        this.team = team;
        this.name = name;
    }

    public TeamMember() {}

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCtwId() {
        return ctwId;
    }

    public Team getTeam() {
        return team;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public Timestamp getModifiedAt() {
        return modifiedAt;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCtwId(String ctwId) {
        this.ctwId = ctwId;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
