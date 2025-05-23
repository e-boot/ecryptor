# 🔐 File Encryptor CLI
A simple yet secure command-line tool to encrypt and decrypt files using AES-256 encryption in Java.


---


## 🚀 How to Use

### Commands Usage
```bash
java -jar file.encryptor.jar help
```

 how to encrypt a file:

```bash
java -jar file-encryptor.jar lock <path/to/yourfile.txt>
````
 How to decrypt a file:

```bash
java -jar file-encryptor.jar unlock <path/to/file.enc>
```
how to use:
```
java -jar file-encryptor.jar help
```


## Tips & Recommendations

   - Always use strong, unique passwords

   - Keep your encrypted files and passwords safe and separate

   - Backup important data before encryption


###  Technical Notes
    - AES encryption with CBC mode and PKCS5 padding
    - Password-based key derivation using PBKDF2 and random salt
    - Random IV generated per encryption
    - The original file extension is stored inside the encrypted payload
    - Can encrypt/decrypt files: .txt, .pdf, .png, etc). Images and text files work fine. Bigger files and directories yet to be implemented.
