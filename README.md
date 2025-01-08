# MUTU

1. Install java jdk 17
   1. set `JAVA_HOME` to install directory
2. Install apache-tomcat-10
3. Install mySQL-8.4
4. clone this repo (`git clone https://github.com/lostbody/mutu.git`)
5. build war file using maven (`mvn clean package`)
6. copy war file to tomcat webapps directory
   1. set `CATALINA_OPTS` to `-Dconfig=prod`
7. start tomcat
8. start mysql server
9. Create schema "mutudb"
10. run HibernateSeed to populate the database with test data
11. visit the tomcat url from a browser (http://localhost:8080/mutu-1.0-SNAPSHOT)
12. login using one of the test accounts (e.g. username: "gina@gina.com" password: "gina")