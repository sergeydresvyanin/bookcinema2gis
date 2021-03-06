--CREATE DATABASE bookcinema2gis;

CREATE TABLE CINEMA(ID INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                    NAME VARCHAR(200)
);
CREATE TABLE HALL(ID INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                  REFCINEMA INTEGER references CINEMA ON DELETE CASCADE ON UPDATE CASCADE,
                  NAME VARCHAR(200)
);
CREATE TABLE FILMSESSION(ID bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                         REFHALL INTEGER references HALL ON DELETE CASCADE ON UPDATE CASCADE,
                         NAME VARCHAR(200),
                         START TIMESTAMP,
                         DATEEND TIMESTAMP
);

CREATE TABLE PERSONS(ID bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                     PHONE VARCHAR(100),
                     CONSTRAINT uniq_person UNIQUE (PHONE)
);

CREATE TABLE PLACES(ID bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                    REFFILMSESSION bigint references FILMSESSION,
                    REFPERSONS INTEGER references PERSONS ON DELETE CASCADE ON UPDATE CASCADE,
                    ROW INTEGER,
                    PLACENUM INTEGER,
                    VERSION INTEGER DEFAULT 1 NOT NULL,
                    CHANGE_TIME TIMESTAMP DEFAULT  CURRENT_TIMESTAMP NOT NULL
);

CREATE TABLE PLACESOPERATION(
                                ID INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                                REFPERSONS BIGINT,
                                REFPLACES BIGINT,
                                DATEOPERATION TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE FUNCTION PLACES_LOG() RETURNS trigger AS $PLACES_LOG$
BEGIN
    IF NEW.REFPERSONS IS NOT NULL THEN
        INSERT INTO placesoperation(REFPERSONS, REFPLACES) VALUES(NEW.REFPERSONS, NEW.ID);
    END IF;
    RETURN NEW;
END;
$PLACES_LOG$ LANGUAGE plpgsql;


CREATE TRIGGER PLACES_LOG AFTER UPDATE ON PLACES
    FOR EACH ROW EXECUTE FUNCTION PLACES_LOG();


TRUNCATE TABLE PLACES RESTART IDENTITY CASCADE;
TRUNCATE TABLE FILMSESSION RESTART IDENTITY CASCADE;
TRUNCATE TABLE HALL RESTART IDENTITY CASCADE;
TRUNCATE TABLE CINEMA RESTART IDENTITY CASCADE;
TRUNCATE TABLE PERSONS RESTART IDENTITY CASCADE;
TRUNCATE TABLE placesoperation RESTART IDENTITY CASCADE;

INSERT INTO CINEMA(NAME) VALUES('??????????1');
INSERT INTO CINEMA(NAME) VALUES('??????');
INSERT INTO CINEMA(NAME) VALUES('??????????2');
COMMIT;
INSERT INTO HALL(REFCINEMA, NAME) VALUES(1,'1');
INSERT INTO HALL(REFCINEMA, NAME) VALUES(1,'2');
INSERT INTO HALL(REFCINEMA, NAME) VALUES(1,'3');
INSERT INTO HALL(REFCINEMA, NAME) VALUES(2,'1');
INSERT INTO HALL(REFCINEMA, NAME) VALUES(2,'2');
INSERT INTO HALL(REFCINEMA, NAME) VALUES(2,'3');
INSERT INTO HALL(REFCINEMA, NAME) VALUES(3,'1');
INSERT INTO HALL(REFCINEMA, NAME) VALUES(3,'2');
COMMIT;
INSERT INTO FILMSESSION(REFHALL, NAME, START, DATEEND) VALUES(1, '?????????? 1', CURRENT_TIMESTAMP + interval '1h' * 2, CURRENT_TIMESTAMP + interval '1h' * 3);
INSERT INTO FILMSESSION(REFHALL, NAME, START, DATEEND) VALUES(1, '?????????? 2', CURRENT_TIMESTAMP + interval '1h' * 2, CURRENT_TIMESTAMP + interval '1h' * 3);
INSERT INTO FILMSESSION(REFHALL, NAME, START, DATEEND) VALUES(1, '?????????? 3', CURRENT_TIMESTAMP + interval '1h' * 2, CURRENT_TIMESTAMP + interval '1h' * 3);
INSERT INTO FILMSESSION(REFHALL, NAME, START, DATEEND) VALUES(2,'?????????? 4', CURRENT_TIMESTAMP + interval '1h' * 2, CURRENT_TIMESTAMP + interval '1h' * 3);
INSERT INTO FILMSESSION(REFHALL, NAME, START, DATEEND) VALUES(2,'?????????? 5', CURRENT_TIMESTAMP + interval '1h' * 1, CURRENT_TIMESTAMP + interval '1h' * 2);
INSERT INTO FILMSESSION(REFHALL, NAME, START, DATEEND) VALUES(2,'?????????? 6',  CURRENT_TIMESTAMP + interval '1h' * 1, CURRENT_TIMESTAMP + interval '1h' * 2);
INSERT INTO FILMSESSION(REFHALL, NAME, START, DATEEND) VALUES(5,'?????????? 7', CURRENT_TIMESTAMP + interval '1h' * 2, CURRENT_TIMESTAMP + interval '1h' * 3);
INSERT INTO FILMSESSION(REFHALL, NAME, START, DATEEND) VALUES(5,'?????????? 8',  CURRENT_TIMESTAMP + interval '1h' * -2, CURRENT_TIMESTAMP + interval '1h' * -1);
COMMIT;
INSERT INTO PLACES(reffilmsession, row, placenum) VALUES(1, 1, 1);
INSERT INTO PLACES(reffilmsession, row, placenum) VALUES(1, 1, 2);
INSERT INTO PLACES(reffilmsession, row, placenum) VALUES(1, 1, 3);
INSERT INTO PLACES(reffilmsession, row, placenum) VALUES(1, 2, 1);
INSERT INTO PLACES(reffilmsession, row, placenum) VALUES(1, 2, 2);
INSERT INTO PLACES(reffilmsession, row, placenum) VALUES(1, 2, 3);
INSERT INTO PLACES(reffilmsession, row, placenum) VALUES(2, 1, 1);
INSERT INTO PLACES(reffilmsession, row, placenum) VALUES(2, 1, 2);
INSERT INTO PLACES(reffilmsession, row, placenum) VALUES(2, 1, 3);
INSERT INTO PLACES(reffilmsession, row, placenum) VALUES(2, 2, 1);
INSERT INTO PLACES(reffilmsession, row, placenum) VALUES(2, 2, 2);
INSERT INTO PLACES(reffilmsession, row, placenum) VALUES(2, 2, 3);
INSERT INTO PLACES(reffilmsession, row, placenum) VALUES(3, 1, 1);
INSERT INTO PLACES(reffilmsession, row, placenum) VALUES(3, 1, 2);
INSERT INTO PLACES(reffilmsession, row, placenum) VALUES(3, 1, 3);
INSERT INTO PLACES(reffilmsession, row, placenum) VALUES(3, 2, 1);
INSERT INTO PLACES(reffilmsession, row, placenum) VALUES(3, 2, 2);
INSERT INTO PLACES(reffilmsession, row, placenum) VALUES(3, 2, 3);
INSERT INTO PLACES(reffilmsession, row, placenum) VALUES(4, 1, 1);
INSERT INTO PLACES(reffilmsession, row, placenum) VALUES(4, 1, 2);
INSERT INTO PLACES(reffilmsession, row, placenum) VALUES(4, 1, 3);
INSERT INTO PLACES(reffilmsession, row, placenum) VALUES(4, 2, 1);
INSERT INTO PLACES(reffilmsession, row, placenum) VALUES(4, 2, 2);
INSERT INTO PLACES(reffilmsession, row, placenum) VALUES(4, 2, 3);
INSERT INTO PLACES(reffilmsession, row, placenum) VALUES(5, 1, 1);
INSERT INTO PLACES(reffilmsession, row, placenum) VALUES(5, 1, 2);
INSERT INTO PLACES(reffilmsession, row, placenum) VALUES(5, 1, 3);
INSERT INTO PLACES(reffilmsession, row, placenum) VALUES(5, 2, 1);
INSERT INTO PLACES(reffilmsession, row, placenum) VALUES(5, 2, 2);
INSERT INTO PLACES(reffilmsession, row, placenum) VALUES(5, 2, 3);
INSERT INTO PLACES(reffilmsession, row, placenum) VALUES(6, 1, 1);
INSERT INTO PLACES(reffilmsession, row, placenum) VALUES(6, 1, 2);
INSERT INTO PLACES(reffilmsession, row, placenum) VALUES(6, 1, 3);
INSERT INTO PLACES(reffilmsession, row, placenum) VALUES(6, 2, 1);
INSERT INTO PLACES(reffilmsession, row, placenum) VALUES(6, 2, 2);
INSERT INTO PLACES(reffilmsession, row, placenum) VALUES(6, 2, 3);
INSERT INTO PLACES(reffilmsession, row, placenum) VALUES(7, 1, 1);
INSERT INTO PLACES(reffilmsession, row, placenum) VALUES(7, 1, 2);
INSERT INTO PLACES(reffilmsession, row, placenum) VALUES(7, 1, 3);
INSERT INTO PLACES(reffilmsession, row, placenum) VALUES(7, 2, 1);
INSERT INTO PLACES(reffilmsession, row, placenum) VALUES(7, 2, 2);
INSERT INTO PLACES(reffilmsession, row, placenum) VALUES(7, 2, 3);
INSERT INTO PLACES(reffilmsession, row, placenum) VALUES(8, 1, 1);
INSERT INTO PLACES(reffilmsession, row, placenum) VALUES(8, 1, 2);
INSERT INTO PLACES(reffilmsession, row, placenum) VALUES(8, 1, 3);
INSERT INTO PLACES(reffilmsession, row, placenum) VALUES(8, 2, 1);
INSERT INTO PLACES(reffilmsession, row, placenum) VALUES(8, 2, 2);
INSERT INTO PLACES(reffilmsession, row, placenum) VALUES(8, 2, 3);
COMMIT;