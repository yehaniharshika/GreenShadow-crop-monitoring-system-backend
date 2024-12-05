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
- **JWT**: Secure authentication 
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


## API Endpoints

### 01. Staff Management

- **POST** `/staff`: create a new staff.
- **GET** `/staff`: Get all staff details.
- **GET** `/staff/{staffId}`: Get a staff by using staff ID.
- **GET** `/staff/generate-next-staff-id`: To get next staff ID.
- **PUT** `/staff/{staffId}`: Update a staff by using staff ID.
- **DELETE** `/staff/{staffId}`: Delete a staff by using staff ID.


### 02. Item Management
- **POST** `/items`: Add a new item.
- **GET** `/items`: Get all items.
- **GET** `/items/{itemCode}`: Get a item by using item Code.
- **GET** `/items/generate-next-item-code`: To get next item code.
- **PUT** `/items/{itemCode}`: Update a item by using item code.
- **DELETE** `/items/{itemCode}`: Delete a item by using item code.

### 03. Orders Management
- **POST** `/orders`: Place a new order.
- **GET** `/orders`: Get all orders.
- **GET** `/orders/{orderId}`: Get a order by using order ID.
- **GET** `/orders/generate-next-order-id`: To get next order Id.
- **PUT** `/orders/{ordeId}`: Update a order by using order ID.
- **DELETE** `/orders/{orderId}`: Delete a order by using order ID.

You can view the detailed API documentation for the Green shadow-crop management system, which includes comprehensive example requests and responses, by visiting this link.<br>


<br>
The frontend code for the crop Monitoring System is available in a separate repository. You can find it by this link<br>


## License
© 2024 All Right Reserved Created By Yehani Harshika
<br/>
This project is licensed under the [MIT](License.txt) license
