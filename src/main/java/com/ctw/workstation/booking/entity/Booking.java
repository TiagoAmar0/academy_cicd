package com.ctw.workstation.booking.entity;

import com.ctw.workstation.rack.entity.Rack;
import com.ctw.workstation.teammember.entity.TeamMember;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Entity
@Table(name = "T_BOOKING")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bookingIdGenerator")
    @SequenceGenerator(name = "bookingIdGenerator", sequenceName = "SEQ_BOOKING_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rack_id")
    private Rack rack;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requester_id")
    private TeamMember requester;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "book_from")
    private Timestamp bookFrom;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "book_to")
    private Timestamp bookTo;


    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @Column(name = "created_at")
    private Timestamp createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified_at")
    private Timestamp modifiedAt;

    public Booking() {
    }

    public Booking(Rack rack, TeamMember requester, Timestamp bookFrom, Timestamp bookTo) {
        this.rack = rack;
        this.requester = requester;
        this.bookFrom = bookFrom;
        this.bookTo = bookTo;
    }

    public Long getId() {
        return id;
    }

    public Rack getRack() {
        return rack;
    }

    public void setRack(Rack rack) {
        this.rack = rack;
    }

    public void setRequester(TeamMember requester) {
        this.requester = requester;
    }

    public void setBookFrom(Timestamp bookFrom) {
        this.bookFrom = bookFrom;
    }

    public void setBookTo(Timestamp bookTo) {
        this.bookTo = bookTo;
    }

    public TeamMember getRequester() {
        return requester;
    }

    public Timestamp getBookFrom() {
        return bookFrom;
    }

    public Timestamp getBookTo() {
        return bookTo;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public Timestamp getModifiedAt() {
        return modifiedAt;
    }
}
