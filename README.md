# StoreLedger

The Kirana Store Transaction Management Service is a robust backend solution designed to empower Kirana stores in efficiently managing their transaction registers. The service provides a seamless platform for daily tracking of both credit and debit transactions, offering a comprehensive solution for effective financial record-keeping.

[Api Documentation Link](https://docs.google.com/document/d/1jkmr91iip5XNuJNFlLuw8ABkGjUvfG1JkXryx8Yz--s/edit?usp=sharing)


## Key Features

### Authentication

Implemented a robust authentication system that restricts access to specific endpoints based on the user's role type. This ensures enhanced security by preventing users from accessing endpoints intended for other role types.

Additionally, a specialized service allows STORE USERS to access transactions related to their specific store only. This restriction ensures the application operates on a global level, maintaining data separation between different stores. The authentication mechanism is centered around the user's login mobile number.

#### User Login

Users are required to log in using their mobile numbers to access the system. Upon successful login, a secure authentication token is generated, facilitating further interactions securely.

#### Admin Access

Administrators enjoy elevated privileges, allowing them to add new Kirana stores. Admins are uniquely identified by their mobile numbers, and only individuals with admin credentials can log in with admin access.

### Admin Control

#### Add Kirana Store

Admins can seamlessly add new Kirana stores to the system, ensuring that each store is registered and can access the system exclusively with its designated mobile number.

### Transaction Management

#### Record Transaction

Kirana store owners can efficiently record transactions, providing details such as amount, type (credit/debit), and description.

#### Get All Transactions (Pagination)

Retrieve a paginated list of all transactions for a specific Kirana store, facilitating efficient viewing of transaction history.

#### Update Transaction

Edit and update existing transactions with new details.

#### Delete Transaction

Remove a specific transaction from the records.

## Download Transaction Report

Generate and download a comprehensive CSV report of transactions, including additional metrics if needed.Before downloading reports a call is being made fxRates to get the current USD to INR value, So that we can use INR for base amount. Total credit and debit along with net is being calculated and recorded in downloaded report.

Please find the sample CSV file 1_transactions.csv in the repo.
## Setup
### MYSQL Setup:

This project uses Mysql as its Database. Follow these steps to set up the required Mysql database:
- Login to your mysql server and add a database named `store_ledger`
- Tables will be automatically created my hibernate as we run in `update` mode.

### Spring Boot Application Repository Setup:
This project is a Spring Boot Application. Follow these steps to setup and application repository.
- Open a terminal session in a directory of your choice, run the following to clone the repository:
  ```bash
     git clone https://github.com/pavanbsp/StoreLedger.git
  ```
### Import Project into IntelliJ IDEA

- Open IntelliJ IDEA.
- Click on `File` -> `Open...` and select the project directory (`StoreLedger`).
- IntelliJ IDEA will automatically recognize it as a Maven project and import the necessary dependencies.

### Configure Application

- Open `src/main/resources/application.properties` for application-specific configurations.
  - Modify the configurations as needed.
    ```properties
            server.port=9000
    
            jdbc.driverClassName=com.mysql.jdbc.Driver
            jdbc.url=jdbc:mysql://localhost/store_ledger
            jdbc.username=root
            jdbc.password=
    
            hibernate.hbm2ddl.auto=update
            hibernate.dialect=org.hibernate.dialect.MySQL5InnoDBDialect
            hibernate.show_sql=true
            hibernate.jdbc.batch_size=20
    
            app.admin.mobile=9346912858
    ```
### Run the Application
- Open the `StoreLedgerApplication` class located in `src/main/java/com/jar/storeLedger`.
- Right-click on the file and select `Run 'Application'`.
- Alternatively, you can run the application from the command line (make sure in the project directory):
    ```bash
    ./mvnw clean spring-boot:run
    ```
- The application will be accessible at `http://localhost:9000`.

### Sample Images

Please find the sample images in the [imgs](https://github.com/pavanbsp/StoreLedger/tree/main/imgs) folder of this repository