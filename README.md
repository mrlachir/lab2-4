# Lab 4 — Hibernate, DAO and MySQL

Requires Java 11+ and Maven. Hibernate 5.6.15.Final, MySQL Connector/J 8.0.33
and JUnit 5.9.2. This is a standalone application.

## Database and run commands

MySQL mode uses the database **db_tp_hibernate**. Start Docker Desktop and
run `docker start jee-mysql` if you use the existing lab container. Execute
[database.sql](database.sql) in your MySQL console:

```sql
CREATE DATABASE IF NOT EXISTS db_tp_hibernate CHARACTER SET utf8mb4;
```

The XML defaults to your local Docker example (127.0.0.1:3306, root/password).
Override with DB_URL, DB_USER and DB_PASSWORD if needed. Hibernate creates
or updates salles and machines automatically.

```powershell
cd C:\Users\PC\Documents\study_5\JEE\labs2\lab4
mvn compile exec:java
mvn test
```

To run the demo without MySQL, use `mvn compile exec:java "-Dlab.db=h2"`.
H2 data is temporary and separate from MySQL. The four tests always use
isolated H2 databases. In IntelliJ import pom.xml and run test.MainTest
(VM option -Dlab.db=h2 for H2) or services.SalleServiceTest.

## Behavior

The generic DAO centralizes sessions and transactions. Writes roll back before
session closure on failure and return false with a logged error. Salle owns the
inverse lazy collection; Machine stores salle_id. Use SalleService.getWithMachines
to access the collection after closing the session. Removing a room cascades to
its machines. MachineService.findByMarque uses a bound HQL parameter.
The demo retains an S1 room and a Dell M-101 machine for inspection. Repeated
MySQL runs add more demo rows, so the Dell result count can increase.

## Four screenshots

1. IntelliJ project tree with entities, DAOs, services and hibernate.cfg.xml (hide credentials).
2. Demo console showing DONNEES INSEREES, MACHINE, HQL Dell and CRUD OK.
3. MySQL Database panel: db_tp_hibernate, salles and machines, with machines.salle_id
   and the stored M-101 row. Refresh the schema after running the MySQL demo.
4. JUnit or Maven summary showing four tests passed.

Use Win + Shift + S and save captures in screenshots/.
