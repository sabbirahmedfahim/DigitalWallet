# Digital Wallet Application 

A Java MySQL digital wallet system with full banking operations.

## Prerequisites

Before running this project, make sure you have the following installed:

	•	Java JDK 8+ (check with java -version in your terminal)
	•	MySQL (ensure MySQL server is installed and running)
	•	MySQL Connector/J (mysql-connector-j-8.0.33.jar) in the lib/ folder
	•	Git (if you want to clone the project)

## How to Run

### 1. Clone or download the project
Download the project from your repository or clone it:
```bash
git clone <https://github.com/sabbirahmedfahim/DigitalWallet/tree/main>
```

### 2. Open a terminal and navigate to the project folder: 
(Replace /path/to/DigitalWallet with your actual path)
```bash
cd /path/to/DigitalWallet
```

### 3. Compile all Java files:

#### macOS / Linux:
```bash
mkdir -p bin
javac -d bin -cp "lib/mysql-connector-j-8.0.33.jar" src/*.java src/db/*.java src/services/*.java
```

#### Windows (PowerShell / CMD):
```cmd
mkdir bin
javac -d bin -cp "lib\mysql-connector-j-8.0.33.jar" src\Main.java src\db\*.java src\services\*.java
```

### 4. Run the application

#### macOS / Linux:
```bash
java -cp "bin:lib/mysql-connector-j-8.0.33.jar" Main
```
#### Windows:
```cmd
java -cp "bin;lib\mysql-connector-j-8.0.33.jar" Main
```



## Features
- User registration and login

- Money deposit and withdrawal

- Send money to other users

- Check account balance

- View transaction history

<br> </br>


# MySQL Server


Make sure MySQL server is running. You can start/stop it using your system's service manager or MySQL commands:


#### macOS (Homebrew):
```bash
brew services start mysql
brew services stop mysql
brew services list
```

#### Linux (systemd):
```bash
sudo systemctl start mysql
sudo systemctl stop mysql
sudo systemctl status mysql
```

#### Windows:
Start MySQL using the Services panel or with CMD commands:

```cmd
net start mysql
net stop mysql
```

# Quick Compile & Run for Mac-OS
```bash
cd "/Users/sabbirahmedfahim/Documents/Programming/DigitalWallet"
mkdir -p bin
javac -d bin -cp "lib/mysql-connector-j-8.0.33.jar" src/*.java src/db/*.java src/services/*.java
java -cp "bin:lib/mysql-connector-j-8.0.33.jar" Main
```