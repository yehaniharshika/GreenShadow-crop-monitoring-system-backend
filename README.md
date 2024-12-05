# Green Shadow - crop monitoring System 🌱🌿

Green Shadow is a robust crop management system designed to streamline agricultural operations by providing tools for managing fields, crops, equipment, staff, and vehicles while monitoring logs effectively. With role-based access control and an intuitive interface, it enhances decision-making and farm productivity. Features like field mapping, crop lifecycle tracking, equipment maintenance logs, and data-driven dashboards ensure seamless management.making it an essential tool for modern agriculture.

## Tech Stack

### **Frontend**
- **HTML5, CSS3, JavaScript**
- **Frameworks**: Bootstrap for responsive web design
- **AJAX & Fetch API**: For seamless API integration

### **Backend**
- **Spring Boot**: REST API development with Hibernate for ORM
- **MySQL**: Database management
- **Gradle**: (Build automation)
- **Lombok**: (Boilerplate reduction)
- **JWT (JSON Web Token)**: (Authentication and Authorization)
- **Logback & SLF4J**: (Logging)
- **Postman**: (API testing and documentation)

## Features

- **Field Management**: Add, update, and monitor field details with field images.
- **Crop Management**: Track crop types, growth stages, and their association with fields.
- **Staff Management**: Add,update,delete staff information.
- **Equipment Management**: Add and allocate farm equipment to fields or staff.Track equipment status and usage.
- **Vehicle Management**: Maintain records of vehicles assigned for agricultural operations.
- **Monitoring Logs**: Record observations and updates related to fields, crops, and staff.Add comments and upload images for detailed logs.
- **Role-Based Access Control**: Permissions tailored to roles such as Manager, Administrator, and Scientist.

## Installation Guide

### Prerequisites

1. **Java 21** - installed on your system.
2. **Gradle** - installed or use the Gradle wrapper included in the project.
3. **MySQL** - server running with a configured database.
4. **Postman** - optional, for API end points testing.

### Steps to Setup
1. Clone the repository:
   ```bash
   git clone https://github.com/yehaniharshika/GreenShadow-crop-monitoring-system-backend.git
   cd green-shadow
   ```
2. Configure the database in the `application.properties` file:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/green_shadow
   spring.datasource.username=your-username
   spring.datasource.password=your-password
   ```

## API Endpoints

You can view the detailed API documentation for the Green shadow-crop management system, which includes comprehensive example requests and responses, by visiting this link.<br>
https://documenter.getpostman.com/view/36189376/2sAYBbcoao

<br>
The frontend code for the crop Monitoring System is available in a separate repository. You can find it by this link<br>
https://github.com/yehaniharshika/GreenShadow-crop-monitoring-system-frontend.git


## License
© 2024 All Right Reserved Created By Yehani Harshika
<br/>
This project is licensed under the [MIT](License.txt) license
