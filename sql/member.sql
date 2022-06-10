DROP TABLE MEMBER_LOG;
DROP TABLE MEMBER;

CREATE TABLE MEMBER
(
    MEMBER_NO NUMBER NOT NULL,
    ID VARCHAR2(32 BYTE) NOT NULL UNIQUE,s
    PW VARCHAR2(64 BYTE) NOT NULL,   -- 암호화된 비번
    NAME VARCHAR2(100 BYTE),
    EMAIL VARCHAR2(200 BYTE) NOT NULL UNIQUE,   -- 이메일인증을 위한 유니크 처리
    AGREE_STATE NUMBER, -- 동의 여부(1: 필수만 2: 필수, 위치 3 : 필수, 프로모션 4: 필수, 위치, 프로모션)
    SIGN_IN DATE,   --DEFAULT로 처리해놓으면 편함
    PW_MODIFIED DATE,
    INFO_MODIFIED DATE, -- 회원정보 수정일
    SESSION_ID VARCHAR2(50 BYTE),   -- 세선아이디(자동로그인 관련)
    SESSION_LIMIT DATE          -- 세션 만료일(자동로그인)
);

CREATE TABLE MEMBER_LOG
(
    MEMBER_LOG_NO NUMBER NOT NULL,
    ID VARCHAR2(32 BYTE) NOT NULL,      -- 1대 多관계(여러사람이 로그인 하므로)
    SIGN_UP DATE    -- 로그인 시간
);    


ALTER TABLE MEMBER ADD CONSTRAINT MEMBER_PK PRIMARY KEY(MEMBER_NO);
ALTER TABLE MEMBER_LOG ADD CONSTRAINT MEMBER_LOG_PK PRIMARY KEY(MEMBER_LOG_NO);

ALTER TABLE MEMBER_LOG ADD CONSTRAINT MEMBER_LOG_MEMBER_FK FOREIGN KEY(ID) REFERENCES MEMBER(ID) ON DELETE CASCADE;

DROP SEQUENCE MEMBER_SEQ;
DROP SEQUENCE MEMBER_LOG_SEQ;
CREATE SEQUENCE MEMBER_SEQ NOCACHE;
CREATE SEQUENCE MEMBER_LOG_SEQ NOCACHE;

-- 트리거 구성
-- 탈퇴자 테이블
DROP TABLE SIGN_OUT_MEMBER;
CREATE TABLE SIGN_OUT_MEMBER
(
    SIGN_OUT_NO NUMBER NOT NULL,
    MEMBER_NO NUMBER,
    ID VARCHAR2(32 BYTE) UNIQUE,
    NAME VARCHAR2(100 BYTE),
    EMAIL VARCHAR2(200 BYTE),
    AGREE_STATE NUMBER,
    SIGN_OUT DATE DEFAULT SYSDATE  
);

ALTER TABLE SIGN_OUT_MEMBER ADD CONSTRAINT SIGN_OUT_MEMBER_PK PRIMARY KEY(SIGN_OUT_NO);

-- 시퀀스
DROP SEQUENCE SIGN_OUT_MEMBER_SEQ;
CREATE SEQUENCE SIGN_OUT_MEMBER_SEQ NOCACHE;

-- MEMBER TABLE의 정보가 삭제되면 SIGN_OUT_MEMBER 테이블로 해당정보가 저장되는 트리거
CREATE OR REPLACE TRIGGER SIGN_OUT_TRIGGER
    AFTER DELETE ON MEMBER FOR EACH ROW
BEGIN INSERT INTO SIGN_OUT_MEMBER 
    (SIGN_OUT_NO, MEMBER_NO, ID, NAME, EMAIL, AGREE_STATE)
   VALUES
    (SIGN_OUT_MEMBER_SEQ.NEXTVAL, :OLD.MEMBER_NO, :OLD.ID,:OLD.NAME, :OLD.EMAIL, :OLD.AGREE_STATE);
END SIGN_OUT_TRIGGER;
