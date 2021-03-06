DROP TABLE NOTICE;
CREATE TABLE NOTICE
(
    NOTICE_NO NUMBER NOT NULL PRIMARY KEY,
    TITLE VARCHAR2(100 BYTE) NOT NULL,
    CONTENT VARCHAR2(1000 BYTE),
    HIT NUMBER,
    CREATED TIMESTAMP,
    LASTMODIFIED TIMESTAMP
);

DROP SEQUENCE NOTICE_SEQ;
CREATE SEQUENCE NOTICE_SEQ NOCACHE;

INSERT INTO NOTICE VALUES(1, '공지사항1', '내용1', 0, SYSTIMESTAMP, SYSTIMESTAMP);
INSERT INTO NOTICE VALUES(2, '공지사항2', '내용2', 0, SYSTIMESTAMP, SYSTIMESTAMP);
INSERT INTO NOTICE VALUES(3, '공지사항3', '내용3', 0, SYSTIMESTAMP, SYSTIMESTAMP);
COMMIT;



