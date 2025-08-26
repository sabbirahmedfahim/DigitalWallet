# Digital Wallet Application 

A Java MySQL digital wallet system with full banking operations.

## Prerequisites

Before running this project, make sure you have the following installed:

	•	Java JDK 8+ (check with java -version in your terminal)
	•	MySQL (XAMPP recommended for cross-platform simplicity)
	•	MySQL Connector/J (mysql-connector-j-9.4.0.jar) in the lib/ folder
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
javac -cp ".:lib/mysql-connector-j-9.4.0.jar" $(find src -name "*.java")
```

#### Windows (PowerShell / CMD):
```cmd
javac -cp ".;lib\mysql-connector-j-9.4.0.jar" src\Main.java src\db\*.java src\models\*.java src\services\*.java
```

### 4. Run the application

#### macOS / Linux:
```bash
java -cp ".:lib/mysql-connector-j-9.4.0.jar:src" Main
```
#### Windows:
```cmd
java -cp ".;lib\mysql-connector-j-9.4.0.jar;src" Main
```



## Features
- User registration and login

- Money deposit and withdrawal

- Send money to other users

- Check account balance

- View transaction history

<br> </br>

# MySQL Setup (cross-platform)

These commands assume you are using XAMPP on macOS. Adjust paths for other systems if necessary.

## macOS (XAMPP)
#### Start MySQL::

```bash
sudo /Applications/XAMPP/xamppfiles/xampp startmysql
```
#### Stop MySQL:

```bash
sudo /Applications/XAMPP/xamppfiles/xampp stopmysql
```
#### Check MySQL status:

```bash
/Applications/XAMPP/xamppfiles/xampp status
```

<br>

## Windows (XAMPP)
#### Start MySQL::

```cmd
<xampp_install_path>\xampp_start.exe
```
#### Stop MySQL:

```cmd
<xampp_install_path>\xampp_stop.exe
```
#### Check MySQL status:

```cmd
<xampp_install_path>\xampp_status.exe
```

<br>

## Linux (XAMPP)
#### Start MySQL::

```bash
sudo /opt/lampp/lampp startmysql
```
#### Stop MySQL:

```bash
sudo /opt/lampp/lampp stopmysql
```
#### Check MySQL status:

```bash
sudo /opt/lampp/lampp status
```

