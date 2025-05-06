د
# TCP_Chat_App

A simple Java-based chat application that uses TCP sockets to allow multiple clients to connect and communicate with a server. The app includes GUI interfaces for both server and clients, with basic status tracking and message handling.

---

## 📁 Project Structure

- `Net1_Part2.java`  
  Main class to launch the server window and three client windows for testing.

- `ServerFrame.java`  
  The server-side GUI. Manages connected clients, displays user statuses, and receives messages.

- `Client.java`  
  The client-side GUI. Allows login and sending/receiving messages.

- `receive.java`  
  A thread class for receiving messages on the client side.

- `User.java`  
  Represents a user with activity tracking logic.

- `*.form` files  
  GUI layout files (created using IntelliJ IDEA or NetBeans).

---

## ✅ Features

- Peer-to-peer chat over TCP (via centralized server)
- GUI for both server and clients (Java Swing)
- Real-time message display
- User status tracking (Active/Away)
- Accounts stored and verified using a local file

---

## 🛠️ Requirements

- Java 8 or later
- IntelliJ IDEA or any Java IDE

---

## ▶️ How to Run

1. Open the project in your Java IDE.
2. Run `Net1_Part2.java` to open the server and 3 client windows.
3. Use one of the test accounts to log in and start chatting.

> Make sure to have an `accounts.txt` file in place with valid user credentials.

---
## 📜 License

This project is open-source and available under the **MIT License**.  
See the [LICENSE](LICENSE) file for details.
