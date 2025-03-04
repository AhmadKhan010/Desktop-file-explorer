import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileOperations {

    // Rename the file or directory
    public void renameFile(File file, String newName) {
        File newFile = new File(file.getParentFile(), newName);
        if (file.renameTo(newFile)) {
            System.out.println("Renamed successfully.");
        } else {
            System.out.println("Failed to rename.");
        }
    }

    // Delete the file or directory
    public void deleteFile(File file) {
        if (file.isDirectory()) {
            deleteDirectory(file);
        } else {
            if (file.delete()) {
                System.out.println("File deleted successfully.");
            } else {
                System.out.println("Failed to delete file.");
            }
        }
    }

    // Recursively delete a directory
    private void deleteDirectory(File dir) {
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                deleteFile(file); // Recursive deletion
            }
        }
        dir.delete();
    }

 // Move a file to a new directory
    public void moveFile(File sourceFile, File destDirectory) {
        // Ensure destDirectory is a valid directory
        if (destDirectory.isDirectory()) {
            File destination = new File(destDirectory, sourceFile.getName()); // Append file name to directory
            try {
                Files.move(sourceFile.toPath(), destination.toPath());
                System.out.println("Moved successfully.");
            } catch (IOException e) {
                System.out.println("Failed to move file: " + e.getMessage());
            }
        } else {
            System.out.println("Destination is not a valid directory.");
        }
    }

}
