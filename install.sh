#!/bin/bash

set -e  # Exit on error

APP_NAME="ecryptor"
INSTALL_DIR="/usr/local/lib/$APP_NAME"
BIN_PATH="/usr/local/bin/$APP_NAME"
JAR_SOURCE="./target/file-encryptor-1.0.jar"  

echo "Installing $APP_NAME..."

# Ensure JAR exists
if [ ! -f "$JAR_SOURCE" ]; then
  echo "❌ JAR file not found at $JAR_SOURCE"
  exit 1
fi

# Create installation directory
sudo mkdir -p "$INSTALL_DIR"

# Copy JAR to install dir
sudo cp "$JAR_SOURCE" "$INSTALL_DIR/file-encryptor.jar"

# Create wrapper script using heredoc
sudo tee "$BIN_PATH" > /dev/null << EOF
#!/bin/bash
java -jar "$INSTALL_DIR/file-encryptor.jar" "\$@"
EOF

# Make wrapper executable
sudo chmod +x "$BIN_PATH"

echo "$APP_NAME installed successfully!"
echo "Run it like this: $APP_NAME lock/unlock yourfile.txt"

