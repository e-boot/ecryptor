# 🔐 File Encryptor CLI

A simple yet secure command-line tool to **encrypt** and **decrypt** files using AES encryption. Built in Java with a focus on clarity, modularity, and ease of use.

---

## 📦 Features

- 🔐 AES-256 file encryption and decryption
- 🔑 Secure password input (your password is hidden)
- 💬 Easy-to-use command-line interface
- 🧩 Modular structure for maintainability

---

## 🚀 How to Use

### 🧪 Encrypt a file

```bash
java -jar file-encryptor.jar encrypt -in <path/to/yourfile.txt>
````
### Decrypt a file 

```bash
java -jar file-encryptor.jar decrypt -in <path/to/file>
```

### Project Structure

```bash
src/
├── cli/               # Handles CLI interaction
│   ├── Main.java
│   ├── ArgumentParser.java
│   └── PasswordReader.java
│
├── core/              # Core logic for command execution and encryption
│   ├── CommandHandler.java
│   └── FileEncryptor.java
│
└── utils/             # Utility helpers
    ├── CryptoHelper.java
    ├── FileHelper.java
    ├── ExtensionHelper.java
    └── PayloadBuilder.java
```

###  Technical Notes
    - AES encryption with CBC mode and PKCS5 padding
    - Password-based key derivation using PBKDF2 and random salt
    - Random IV generated per encryption
    - The original file extension is stored inside the encrypted payload