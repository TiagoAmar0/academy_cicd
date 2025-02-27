DROP SEQUENCE IF EXISTS SEQ_RACK_ID;
CREATE SEQUENCE SEQ_RACK_ID
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

DROP SEQUENCE IF EXISTS SEQ_RACK_ASSET_ID;
CREATE SEQUENCE SEQ_RACK_ASSET_ID
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

DROP SEQUENCE IF EXISTS SEQ_BOOKING_ID;
CREATE SEQUENCE SEQ_BOOKING_ID
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

DROP SEQUENCE IF EXISTS SEQ_TEAM_ID;
CREATE SEQUENCE SEQ_TEAM_ID
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

DROP SEQUENCE IF EXISTS SEQ_TEAM_MEMBER_ID;
CREATE SEQUENCE SEQ_TEAM_MEMBER_ID
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;