import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

public class FileExplorer extends JFrame {
    
    private JTree directoryTree;
    private DefaultTreeModel treeModel;
    private JTextArea fileInfoArea;
    private FileOperations fileOperations;

    public FileExplorer() {
        setTitle("Desktop File Explorer");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        fileOperations = new FileOperations();
        initializeUI();
    }

    private void initializeUI() {
        // Panel to hold directory tree and file info
        JSplitPane splitPane = new JSplitPane();

        // Directory tree starting from D:\
        File rootDirectory = new File("D:\\");  // Start from D drive
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(rootDirectory);
        treeModel = new DefaultTreeModel(root);
        directoryTree = new JTree(treeModel);
        directoryTree.setShowsRootHandles(true);
        directoryTree.addTreeSelectionListener(new DirectorySelectionListener());
        populateTree(root, rootDirectory);

        // Add a listener to detect double clicks and open files
        directoryTree.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                if (me.getClickCount() == 2) {
                    DefaultMutableTreeNode node = (DefaultMutableTreeNode) directoryTree.getLastSelectedPathComponent();
                    if (node != null) {
                        File selectedFile = (File) node.getUserObject();
                        if (selectedFile.isFile()) {
                            try {
                                Desktop.getDesktop().open(selectedFile); // Open the file with default application
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        });

        JScrollPane treeScrollPane = new JScrollPane(directoryTree);
        splitPane.setLeftComponent(treeScrollPane);

        // File Information panel
        fileInfoArea = new JTextArea();
        fileInfoArea.setEditable(false);
        JScrollPane infoScrollPane = new JScrollPane(fileInfoArea);
        splitPane.setRightComponent(infoScrollPane);

        // Adding operation buttons
        JPanel panel = new JPanel();
        JButton renameButton = new JButton("Rename");
        JButton deleteButton = new JButton("Delete");
        JButton moveButton = new JButton("Move");

        renameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                renameFile();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteFile();
            }
        });

        moveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                moveFile();
            }
        });

        panel.add(renameButton);
        panel.add(deleteButton);
        panel.add(moveButton);

        add(splitPane, BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);
    }

    private void renameFile() {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) directoryTree.getLastSelectedPathComponent();
        if (node != null) {
            File selectedFile = (File) node.getUserObject();
            String newName = JOptionPane.showInputDialog(this, "Enter new name:");
            if (newName != null) {
                fileOperations.renameFile(selectedFile, newName);
                refreshTree();
            }
        }
    }

    private void deleteFile() {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) directoryTree.getLastSelectedPathComponent();
        if (node != null) {
            File selectedFile = (File) node.getUserObject();
            int confirmation = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this file?", "Delete", JOptionPane.YES_NO_OPTION);
            if (confirmation == JOptionPane.YES_OPTION) {
                fileOperations.deleteFile(selectedFile);
                refreshTree();
            }
        }
    }

    private void moveFile() {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) directoryTree.getLastSelectedPathComponent();
        if (node != null) {
            File selectedFile = (File) node.getUserObject();
            String destinationPath = JOptionPane.showInputDialog(this, "Enter destination path:");

            if (destinationPath != null) {
                File destinationDirectory = new File(destinationPath);

                // Check if the destination is valid and is a directory
                if (!destinationDirectory.exists() || !destinationDirectory.isDirectory()) {
                    JOptionPane.showMessageDialog(this, "The destination path is invalid or does not exist!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;  // Stop the operation if the destination is invalid
                }

                // Perform the move operation if the destination is valid
                fileOperations.moveFile(selectedFile, destinationDirectory);
                refreshTree();
            }
        }
    }



    // Refresh the tree view after operations
    private void refreshTree() {
        File rootDirectory = new File("D:\\");  // Refresh from D drive
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(rootDirectory);
        treeModel.setRoot(root);
        populateTree(root, rootDirectory);
    }

    // Populate the tree with files and directories recursively
    private void populateTree(DefaultMutableTreeNode node, File file) {
        File[] files = file.listFiles();
        if (files != null) {
            for (File childFile : files) {
                DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(childFile);
                node.add(childNode);
                if (childFile.isDirectory()) {
                    populateTree(childNode, childFile);
                }
            }
        }
    }

    private class DirectorySelectionListener implements TreeSelectionListener {
        public void valueChanged(TreeSelectionEvent e) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) directoryTree.getLastSelectedPathComponent();
            if (node != null) {
                File selectedFile = (File) node.getUserObject();
                displayFileInfo(selectedFile);
            }
        }
    }

    private void displayFileInfo(File file) {
        StringBuilder info = new StringBuilder();

        if (file.isDirectory()) {
            info.append("Properties of the Directory: ").append(file.getName()).append("\n\n");
        } else {
            info.append("Properties of the File: ").append(file.getName()).append("\n\n");
        }

        info.append("Path: ").append(file.getAbsolutePath()).append("\n");
        info.append("Size: ").append(file.length()).append(" bytes\n");
        info.append("Last Modified: ").append(file.lastModified()).append("\n");
        info.append("Is Directory: ").append(file.isDirectory()).append("\n");
        info.append("Readable: ").append(file.canRead()).append("\n");
        info.append("Writable: ").append(file.canWrite()).append("\n");
        info.append("Executable: ").append(file.canExecute()).append("\n");

        // Set the text in the text area
        fileInfoArea.setText(info.toString());
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new FileExplorer().setVisible(true);
            }
        });
    }
}
