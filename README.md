# Mutu - A Dating Web Application

## Project Overview
Mutu is a dating web application built with Java, JSP, Hibernate, and MySQL. The application allows users to:
- Create and manage their profiles
- Upload and manage profile pictures
- View potential matches
- Swipe left/right on other users
- Chat with their matches

## Prerequisites
- Java Development Kit (JDK) 17 or higher
- Apache Maven 3.8 or higher
- MySQL Server 8.0 or higher
- Apache Tomcat 10.0 or higher
- Git (for cloning the repository)

## Setup Steps

### 1. Clone the Repository
```bash
git clone https://github.com/lostbody/mutu.git
cd mutu
```

### 2. Initialize Database
1. Install MySQL if you haven't already
2. Run the sql script at `src/main/resources/db_init.sql` to create the database and user.
3. Run the HibernateSeed class (located in gr.aueb.cf.mutu.utils package) to populate the database with sample data.

### 3. Build the Project
Build the project using Maven:
```bash
mvn clean package
```

### 5. Configure Tomcat
1. Before deploying, configure Tomcat to run the application in production mode:

#### Windows:
1. Navigate to your Tomcat installation directory
2. Open or create the file `bin/setenv.bat`
3. Add the following line:
```batch
set "CATALINA_OPTS=%CATALINA_OPTS% -Dconfig=prod"
```

#### Linux/Mac:
1. Navigate to your Tomcat installation directory
2. Open or create the file `bin/setenv.sh`
3. Add the following line:
```bash
export CATALINA_OPTS="$CATALINA_OPTS -Dconfig=prod"
```
4. Make the file executable:
```bash
chmod +x bin/setenv.sh
```

### 6. Deploy the Application
1. Copy the generated WAR file to Tomcat's webapps directory:
```bash
cp target/mutu-1.0-SNAPSHOT.war [tomcat-installation-path]/webapps/mutu.war
```

2. Start Tomcat:
- On Windows: `[tomcat-installation-path]/bin/startup.bat`
- On Unix-based systems: `[tomcat-installation-path]/bin/startup.sh`

3. Access the application at:
```
http://localhost:8080/mutu
```
4. Log-in using one of the test accounts:
- Email: gina@gina.com / Password: gina
- Email: dora@dora.com / Password: dora
- Email: rodia@rodia.com / Password: rodia
- Email: andreas@andreas.com / Password: andreas
- Email: anna@anna.com / Password: anna
- Email: alex@alex.com / Password: alex

## Project Structure
- `src/main/java/gr/aueb/cf/mutu/` - Main source code
    - `dao/` - Data Access Objects interfaces
    - `dto/` - Data Transfer Objects
    - `endpoints/` - Servlet controllers
    - `models_prod/` - Entity models and DAO implementations
    - `service/` - Service layer classes
    - `utils/` - Utility classes
- `src/main/webapp/` - Web resources
    - `*.jsp` - JSP pages
    - `static/` - Static resources (CSS, images)

## Technologies Used
- Java 17
- Jakarta EE 10
- Hibernate 6.5
- MySQL 8
- JSP for server-side rendering
- Maven for dependency management