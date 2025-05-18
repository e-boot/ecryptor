# FileEncryptor

A simple Java CLI tool to encrypt and decrypt files using AES encryption and a password-based key.

## 🔐 Features

- Encrypt any file using a password
- Decrypt previously encrypted files
- Interactive terminal input
- Modular code (Main, CommandHandler, FileEncryptor)


## ✅ To-Do

- [x] Encrypt and decrypt files via CLI
- [x] Prompt for password (and confirmation)
- [x] Modularize argument parsing
- [x] Use `java.nio.file.Path` for safety
- [ ] Improve key derivation with PBKDF2 and salt
- [ ] Add ability to encrypt directories (ZIP support)
- [ ] Add unit tests for `CommandHandler` and `ArgumentParser`
- [ ] Improve CLI output with colors