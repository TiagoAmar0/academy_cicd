package com.ctw.workstation.booking.service;

import com.ctw.workstation.booking.dtos.BookingRequestDTO;
import com.ctw.workstation.booking.dtos.BookingResponseDTO;
import com.ctw.workstation.booking.entity.Booking;
import com.ctw.workstation.booking.repository.BookingRepository;
import com.ctw.workstation.rack.entity.Rack;
import com.ctw.workstation.rack.repository.RackRepository;
import com.ctw.workstation.teammember.entity.TeamMember;
import com.ctw.workstation.teammember.repository.TeamMemberRepository;
import io.quarkus.logging.Log;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;
import org.jboss.logging.MDC;

import java.sql.Timestamp;
import java.time.ZoneOffset;
import java.util.List;

@Singleton
public class BookingService {

    @Inject
    BookingRepository bookingRepository;

    @Inject
    RackRepository rackRepository;

    @Inject
    TeamMemberRepository teamMemberRepository;


    @Transactional
    public BookingResponseDTO store(BookingRequestDTO request){
        TeamMember requester = teamMemberRepository.findById(request.requesterId());
        Rack rack = rackRepository.findById(request.rackId());

        Booking booking = new Booking(rack, requester, Timestamp.from(request.bookFrom().toInstant(ZoneOffset.MIN)), Timestamp.from(request.bookTo().toInstant(ZoneOffset.MIN)));
        bookingRepository.persistAndFlush(booking);

        return buildBookingResponseDTO(booking);
    }

    public BookingResponseDTO buildBookingResponseDTO(Booking booking){
        return new BookingResponseDTO(booking.getId(), booking.getRack().getSerialNumber(), booking.getRequester().getName(), booking.getBookFrom(), booking.getBookTo(), booking.getCreatedAt(), booking.getModifiedAt());
    }

    public List<BookingResponseDTO> index() {
        Log.infov("%s SERVICE index();", MDC.get("request.id"));

        return bookingRepository.findAll()
                .stream()
                .map(this::buildBookingResponseDTO)
                .toList();
    }

    @Transactional
    public BookingResponseDTO update(Long id, BookingRequestDTO request) {
        Booking booking = bookingRepository.findById(id);
        TeamMember requester = teamMemberRepository.findById(request.requesterId());
        Rack rack = rackRepository.findById(request.rackId());

        booking.setRack(rack);
        booking.setRequester(requester);
        booking.setBookTo(Timestamp.from(request.bookTo().toInstant(ZoneOffset.MIN)));
        booking.setBookFrom(Timestamp.from(request.bookFrom().toInstant(ZoneOffset.MIN)));

        bookingRepository.persistAndFlush(booking);

        return buildBookingResponseDTO(booking);
    }

    @Transactional
    public void delete(Long id){
        bookingRepository.deleteById(id);
    }

    public BookingResponseDTO show(Long id){
        Booking booking = bookingRepository.findById(id);

        return buildBookingResponseDTO(booking);
    }
}
