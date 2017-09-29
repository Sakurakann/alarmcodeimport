create SEQUENCE SEQ_S_ALARM_CODEPARAM_ID increment by 1 start with 1 nocache nomaxvalue;
create or replace trigger INSERT_S_ALARM_CODEPARAM before insert on s_Alarm_Codeparam for each row
begin 
  if :new.id is null or :new.id=0
    then 
      select SEQ_S_ALARM_CODEPARAM_ID.Nextval into :new.id from dual;
      end if;
      end INSERT_S_ALARM_CODEPARAM;
      
select * from s_alarm_codeparam

create TABLE S_ALARM_CODEPARAM(
"ID" number not null primary key,
TESTCODE number not null,
TESTPARAM varchar2(100),
PARAMVALUE number default 0,
PARAMNAME varchar2(100),
REMARK varchar2(100),
ISTDATE date default sysdate,
UPTDATE date default sysdate)
comment column s_alarm_codeparam.id '';

update s_alarm_codeparam set istdate=sysdate,uptdate=sysdate;
truncate table S_ALARM_CODEPARAM
commit

SELECT TESTCODE FROM S_ALARM_CODEPARAM GROUP BY TESTCODE

