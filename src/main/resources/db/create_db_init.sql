DROP TABLE IF EXISTS t_rack_asset;
CREATE TABLE t_rack_asset(
    id BIGINT PRIMARY KEY  DEFAULT nextval('seq_rack_asset_id'),
    asset_tag varchar(10) NOT NULL,
    rack_id BIGINT NOT NULL
);

DROP TABLE IF EXISTS t_rack CASCADE;
CREATE TABLE t_rack(
    id BIGINT PRIMARY KEY  DEFAULT nextval('seq_rack_id'),
    serial_number varchar(20) NOT NULL,
    status varchar(20) CHECK ( status IN ('AVAILABLE', 'BOOKED', 'UNAVAILABLE') ),
    team_id BIGINT NOT NULL,
    default_location varchar(10),
    created_at timestamp NOT NULL DEFAULT now(),
    modified_at timestamp NOT NULL default now()
);

ALTER TABLE t_rack
    ADD CONSTRAINT t_rack_serial_number_unique UNIQUE(serial_number);

ALTER TABLE t_rack_asset
    ADD CONSTRAINT FK_T_RACK_ASSET_RACK_ID
        FOREIGN KEY (rack_id) REFERENCES t_rack(id);

DROP TABLE IF EXISTS t_team CASCADE;
CREATE TABLE t_team(
   id BIGINT PRIMARY KEY  DEFAULT nextval('SEQ_TEAM_ID'),
   name varchar(50) NOT NULL,
   product varchar(50) NOT NULL,
   created_at timestamp NOT NULL DEFAULT now(),
   modified_at timestamp NOT NULL default now(),
   default_location varchar(10)
);

ALTER TABLE t_rack
    ADD CONSTRAINT FK_T_RACK_TEAM_ID
        FOREIGN KEY (team_id) REFERENCES t_team(id);

DROP TABLE IF EXISTS t_team_member CASCADE ;
CREATE TABLE t_team_member(
  id BIGINT PRIMARY KEY  DEFAULT nextval('SEQ_TEAM_MEMBER_ID'),
  team_id BIGINT NOT NULL,
  ctw_id varchar(10) NOT NULL,
  name varchar(50) NOT NULL,
  created_at timestamp NOT NULL DEFAULT now(),
  modified_at timestamp NOT NULL default now()
);



ALTER TABLE t_team_member
    ADD CONSTRAINT FK_T_TEAM_MEMBER_TEAM_ID
        FOREIGN KEY (team_id) REFERENCES t_team(id);

DROP TABLE IF EXISTS t_booking;
CREATE TABLE t_booking(
      id BIGINT PRIMARY KEY  DEFAULT nextval('seq_booking_id'),
      rack_id BIGINT NOT NULL,
      requester_id BIGINT NOT NULL,
      book_from timestamp NOT NULL,
      book_to timestamp NOT NULL,
      created_at timestamp NOT NULL DEFAULT now(),
      modified_at timestamp NOT NULL default now()
);

ALTER TABLE t_booking
    ADD CONSTRAINT FK_T_BOOKING_RACK_ID
        FOREIGN KEY (rack_id) REFERENCES t_rack(id);

ALTER TABLE t_booking
    ADD CONSTRAINT FK_T_BOOKING_REQUESTER_ID
        FOREIGN KEY (requester_id) REFERENCES t_team_member(id);
