# 🔐 File Encryptor CLI
A simple yet secure command-line tool to encrypt and decrypt files using AES-256 encryption in Java. Designed with clarity, modularity, and ease of use in mind.

---

## 📦 Features
🔐 AES-256 encryption with CBC mode and PKCS5 padding

- Secure password input (hidden while typing)

- Automatic handling of file extensions during encryption and decryption

- Password-based key derivation using PBKDF2 with a random salt

- Random IV generated for each encryption to ensure security

- Intuitive command-line interface with clear commands and flags

- Modular code structure for easy maintenance and extension

---

## 🚀 How to Use

### Commands Usage
```bash
java -jar file.encryptor.jar help
```

.
### 🧪 Encrypt a file

```bash
java -jar file-encryptor.jar lock <path/to/yourfile.txt>
````
### Decrypt a file 

```bash
java -jar file-encryptor.jar unlock <path/to/file.enc>
```

Encryption: Uses AES-256 in CBC mode with PKCS5 padding

Key derivation: Password-based key generated with PBKDF2 + random salt

Payload: Stores original file extension and metadata alongside encrypted data

Security: Unique random IV for each encryption session to enhance security


## Tips & Recommendations

   - Always use strong, unique passwords

   - Keep your encrypted files and passwords safe and separate

   - Backup important data before encryption


###  Technical Notes
    - AES encryption with CBC mode and PKCS5 padding
    - Password-based key derivation using PBKDF2 and random salt
    - Random IV generated per encryption
    - The original file extension is stored inside the encrypted payload