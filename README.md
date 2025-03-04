Java File Explorer
Description
This is a JFrame-based Desktop File Explorer built in Java, allowing users to navigate directories, view file properties, and perform essential file operations. It supports the following features:

✅ Directory Navigation (Displays directory listing at each level clicked)
✅ View File Properties (Size, type, last modified date, etc.)
✅ Open Files (Double-click to open files in their respective editors)
✅ Rename Files/Folders
✅ Delete Files/Folders (Supports recursive deletion)
✅ Move Files/Folders
✅ Object-Oriented Design (Encapsulation, Abstraction, Inheritance, Polymorphism)
✅ Google Java Style Guide Compliance

Technologies Used
Java (JDK 8+)
Swing (JFrame, JTree, JTable, JFileChooser, etc.)
Java IO (File handling)
Object-Oriented Programming (OOP)

Installation & Usage
1️⃣ Clone the repository

sh
Copy
Edit
git clone https://github.com/your-username/Java-File-Explorer.git
cd Java-File-Explorer
2️⃣ Compile the code

sh
Copy
Edit
javac -d bin -cp src src/com/yourpackage/Main.java
3️⃣ Run the application

sh
Copy
Edit
java -cp bin com.yourpackage.Main
Project Structure
bash
Copy
Edit
📂 Java-File-Explorer  
│── 📂 src                # Source code folder  
│   │── 📂 com/yourpackage  
│   │   │── FileExplorer.java  
│   │   │── Main.java  
│── 📂 bin                # Compiled class files  
│── 📜 README.md          # Project documentation  
│── 📜 .gitignore         # Git ignore file  
│── 📜 LICENSE            # Optional (Add if needed)  
