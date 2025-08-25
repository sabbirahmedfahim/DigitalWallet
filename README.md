# Digital Wallet Application 

A Java MySQL digital wallet system with full banking operations.

## One-Liner Command (Use It)
```
cd /Users/sabbirahmedfahim/Documents/Programming/DigitalWallet && javac -cp ".:lib/mysql-connector-j-9.4.0.jar" src/Main.java src/db/*.java src/models/*.java src/services/*.java && java -cp ".:lib/mysql-connector-j-9.4.0.jar:src" Main
```

## Run Step by Step

### 1. Go to project folder
```bash
cd /Users/sabbirahmedfahim/Documents/Programming/DigitalWallet
```

### 2. Compile everything
```
javac -cp ".:lib/mysql-connector-j-9.4.0.jar" src/Main.java src/db/*.java src/models/*.java src/services/*.java
```

### 3. Run the app
```
java -cp ".:lib/mysql-connector-j-9.4.0.jar:src" Main
```

## Features
- User registration and login

- Money deposit and withdrawal

- Send money to other users

- Check account balance

- View transaction history

## Troubleshooting

#### Start MySQL::

```
sudo /Applications/XAMPP/xamppfiles/xampp startmysql
```
#### Stop MySQL:

```
sudo /Applications/XAMPP/xamppfiles/xampp stopmysql
```
#### Check MySQL status:

```
/Applications/XAMPP/xamppfiles/xampp status
```