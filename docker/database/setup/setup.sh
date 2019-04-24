#!/bin/bash

echo "alter system disable restricted session;" | sqlplus -s SYSTEM/Pwd
echo "create tablespace test datafile 'test.dat' size 512M online;" | sqlplus -s SYS/Pwd as SYSDBA
echo "alter profile default limit password_verify_function null;" | sqlplus -s SYS/Pwd as SYSDBA
echo "alter session set \"_ORACLE_SCRIPT\"=true;" | sqlplus -s SYS/Pwd as SYSDBA
echo "create user test identified by test default tablespace test;" | sqlplus -s SYS/Pwd as SYSDBA
echo "alter user test quota unlimited on test;" | sqlplus -s SYS/Pwd as SYSDBA
echo "grant create session to test;" | sqlplus -s SYS/Pwd as SYSDBA
echo "grant create table to test;" | sqlplus -s SYS/Pwd as SYSDBA
echo "grant create view to test;" | sqlplus -s SYS/Pwd as SYSDBA
echo "grant create any trigger to test;" | sqlplus -s SYS/Pwd as SYSDBA
echo "grant create any procedure to test;" | sqlplus -s SYS/Pwd as SYSDBA
echo "grant create any sequence to test;" | sqlplus -s SYS/Pwd as SYSDBA
echo "grant create any synonym to test;" | sqlplus -s SYS/Pwd as SYSDBA
echo "grant select on sys.dba_pending_transactions to test;" | sqlplus -s SYS/Pwd as SYSDBA
echo "grant select on sys.pending_trans$ to test;" | sqlplus -s SYS/Pwd as SYSDBA
echo "grant select on sys.dba_2pc_pending to test;" | sqlplus -s SYS/Pwd as SYSDBA
echo "grant execute on sys.dbms_xa to test;" | sqlplus -s SYS/Pwd as SYSDBA
echo "grant force any transaction to test;" | sqlplus -s SYS/Pwd as SYSDBA

for script in `ls /setup/*.sql | sort -V`; do echo "sqlplus test/test@SID @$script"; done;

touch /.initialized

