SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';


-- -----------------------------------------------------
-- Schema madang
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `madang` ;

-- -----------------------------------------------------
-- Schema madang
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `madang` DEFAULT CHARACTER SET utf8 ;
USE `madang` ;

-- -----------------------------------------------------
-- Table `madang`.`Doctors`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `madang`.`Doctors` ;

CREATE TABLE IF NOT EXISTS `madang`.`Doctors` (
	doc_id  integer not null,
	major_treat varchar(25) not null,
	doc_name varchar(20) not null,
	doc_gen char(1) not null,
    doc_phone varchar(15) null,
    doc_email varchar(50) unique,
    doc_position varchar(20) not null
);
alter table Doctors
	ADD constraint doc_id_pk primary key (doc_id);

-- -----------------------------------------------------
-- Table `madang`.`Nurse`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `madang`.`Nurses` ;

CREATE TABLE IF NOT EXISTS `madang`.`Nurses` (
	nur_id  integer not null,
	major_job varchar(25) not null,
	nur_name varchar(20) not null,
	nur_gen char(1) not null,
    nur_phone varchar(15) null,
    nur_email varchar(50) unique,
    nur_position varchar(20) not null
);
alter table Nurses
	ADD constraint nur_id_pk primary key (nur_id);

-- -----------------------------------------------------
-- Table `madang`.`Patients`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `madang`.`Patients` ;

CREATE TABLE IF NOT EXISTS `madang`.`Patients` (
	pat_id  integer not null,
    nur_id  integer not null,
    doc_id  integer not null,
	pat_name varchar(20) not null,
	pat_gen char(1) not null,
    pat_jumin varchar(14) not null,
    pat_addr varchar(100) not null,
    pat_phone varchar(15) null,
    pat_email varchar(50) unique,
    pat_position varchar(20) not null
);
alter table Patients
	ADD constraint pat_id_pk primary key (pat_id);
    
alter table Patients
	ADD (constraint R_2 foreign key(doc_id) references Doctors (doc_id));

alter table patients
	ADD (constraint R_3 foreign key(nur_id) references nurses(nur_id));
    
-- -----------------------------------------------------
-- Table `madang`.`Treatments`
-- -----------------------------------------------------    
DROP TABLE IF EXISTS `madang`.`Treatments` ;

CREATE TABLE IF NOT EXISTS `madang`.`Treatments` (

	treat_id integer not null,
	pat_id  integer not null,
    doc_id  integer not null,
	treat_contents varchar(1000) not null,
    treat_date DATE not null
);
alter table Treatments
	ADD constraint treat_pat_doc_id_pk primary key (treat_id,pat_id,doc_id);
    
alter table Treatments
	ADD (constraint R_5 foreign key(pat_id) references Patients (pat_id));

alter table Treatments
	ADD (constraint R_6 foreign key(doc_id) references Doctors (doc_id));
    
-- -----------------------------------------------------
-- Table `madang`.`Charts`
-- -----------------------------------------------------    
DROP TABLE IF EXISTS `madang`.`Charts` ;

CREATE TABLE IF NOT EXISTS `madang`.`Charts` (

	charts_id integer not null,
	treat_id integer not null,
    doc_id  integer not null,
	pat_id  integer not null,
    nur_id  integer not null,
	charts_contents varchar(1000) not null
);
alter table charts
	ADD constraint chart_treat_doc_pat_id_pk primary key (charts_id,treat_id,pat_id,doc_id);
    
alter table charts
	ADD (constraint R_4 foreign key(nur_id) references Nurses (nur_id));

alter table charts
	ADD (constraint R_7 foreign key(treat_id,pat_id,doc_id) references Treatments (treat_id,pat_id,doc_id));

-- insert code--

-- doctors -- 
insert into Doctors values(980312,'소아과','이태정','M','010-333-1340','ltj@hanbh.com','과장');
insert into Doctors values(000601,'내과','안성기','M','011-222-0987','ask@hanbh.com','과장');
insert into Doctors values(001208,'외과','김민종','M','010-333-8743','kmj@hanbh.com','과장');
insert into Doctors values(020403,'피부과','이태서','M','019-777-3764','lts@hanbh.com','과장');
insert into Doctors values(050900,'소아과','김연아','F','010-555-3746','kya@hanbh.com','전문의');
insert into Doctors values(050101,'내과','차태현','M','011-222-7643','cth@hanbh.com','전문의');
insert into Doctors values(062019,'소아과','전지현','F','010-999-1265','jjh@hanbh.com','전문의');
insert into Doctors values(070576,'피부과','홍길동','M','016-333-7263','hgd@hanbh.com','전문의');
insert into Doctors values(080543,'방사선과','유재석','M','010-222-1263','yjs@hanbh.com','과장');
insert into Doctors values(091001,'외과','김병만','M','010-555-3542','kbm@hanbh.com','전문의');

-- nurses --
insert into Nurses values(050302,'소아과','김은영','F','010-555-8751','key@hanbh.com','수간호사');
insert into Nurses values(050021,'내과','윤성애','F','016-333-8745','ysa@hanbh.com','수간호사');
insert into Nurses values(040089,'피부과','신지원','M','010-666-7646','sjw@hanbh.com','주임');
insert into Nurses values(070605,'방사선과','유정화','F','010-333-4588','yjh@hanbh.com','주임');
insert into Nurses values(070804,'내과','라하나','F','010-222-1340','nhn@hanbh.com','주임');
insert into Nurses values(071018,'소아과','김화경','F','019-888-4116','khk@hanbh.com','주임');
insert into Nurses values(100356,'소아과','이선용','M','010-777-1234','lsy@hanbh.com','간호사');
insert into Nurses values(104145,'외과','김현','M','010-999-8520','kh@hanbh.com','간호사');
insert into Nurses values(120309,'피부과','박성완','M','010-777-4996','psw@hanbh.com','간호사');
insert into Nurses values(130211,'외과','이서연','F','010-222-3214','lsy2@hanbh.com','간호사');

-- patients --
insert into Patients values(2345,050302,980312,'안상건','M','232345','서울','010-555-7845','ask@ab.com','회사원');
insert into Patients values(3545,040089,020403,'김성룡','M','543545','서울','010-333-7812','ksr@bb.com','자영업');
insert into Patients values(3424,070605,080543,'이종진','M','433424','부산','019-888-4859','ljjk@ab.com','회사원');
insert into Patients values(7675,100356,050900,'최강석','M','677675','당진','010-222-4847','cks@cc.com','회사원');
insert into Patients values(4533,070804,000601,'정한경','M','744533','강릉','010-777-9630','jhk@ab.com','교수');
insert into Patients values(5546,120309,070576,'유원현','M','765546','대구','016-777-0214','ywh@cc.com','자영업');
insert into Patients values(4543,070804,050101,'최재정','M','454543','부산','010-555-4187','cjj@bb.com','회사원');
insert into Patients values(9768,130211,091001,'이진희','F','119768','서울','010-888-3675','ljh@ab.com','교수');
insert into Patients values(4234,130211,091001,'오나미','F','234234','속초','010-999-6541','onm@cc.com','학생');
insert into Patients values(7643,071018,062019,'송성묵','M','987643','서울','010-222-5874','ssm@bb.com','학생');

-- treatments --
insert into Treatments values(130516023,2345,980312,'감기, 몸살','2013-05-16');
insert into Treatments values(130628100,3545,020403,'피부 트러블 치료','2013-06-28');
insert into Treatments values(131205056,3424,080543,'목 디스크로 MRI 촬영','2013-12-05');
insert into Treatments values(131218024,7675,050900,'중이염','2013-12-18');
insert into Treatments values(131224012,4533,000601,'장염','2013-12-24');
insert into Treatments values(140103001,5546,070576,'여드름 치료','2014-01-03');
insert into Treatments values(140109026,4543,050101,'위염','2014-01-09');
insert into Treatments values(140226102,9768,091001,'화상치료','2014-02-26');
insert into Treatments values(140303003,4234,091001,'교통사고 외상치료','2014-03-03');
insert into Treatments values(140308087,7643,062019,'장염','2014-03-08');

-- charts --
insert into Charts values(1,130516023,980312,2345,050302,'해열제 처방');
insert into Charts values(2,130628100,020403,3545,040089,'박피');
insert into Charts values(3,131205056,080543,3424,070605,'MRI 상태 확인');
insert into Charts values(4,131218024,050900,7675,070804,'중이염약 처방');
insert into Charts values(5,131224012,000601,4533,070804,'장염약 처방');
insert into Charts values(6,140103001,070576,5546,040089,'여드름 레이저 치료');
insert into Charts values(7,140109026,050101,4543,050021,'위염약 처방');
insert into Charts values(8,140226102,091001,9768,040089,'부분마취와 피부이식');
insert into Charts values(9,140303003,091001,4234,104145,'3번 척추 수술');
insert into Charts values(10,140308087,062019,7643,070804,'장염약 처방');

insert into Charts values(11,130516023,980312,2345,050302,'타이레놀 추가 처방');
insert into Charts values(12,130628100,020403,3545,040089,'트러블 완화제 추가 처방');
insert into Charts values(13,131205056,080543,3424,070605,'MRI 결과 : 폐암 2단계');
insert into Charts values(14,140103001,070576,5546,040089,'여드름 크림 처방');
insert into Charts values(15,140303003,091001,4234,104145,'수술결과 확인 및 무통주사 처방');


commit;
    