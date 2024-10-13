import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class TestRunner {

    private JTree folderTree;
    private DefaultMutableTreeNode rootNode;
    private JTextField textField;
    private String defaultPath;
    private String[] fileTypes;
    private JTextArea scenarioContentArea;
    private String mvnPackageCommand;


    public static void main(String[] args) {
        SwingUtilities.invokeLater(TestRunner::new);
    }

    public TestRunner() {

        // Load properties
        loadProperties();

        // Create a new JFrame instance
        JFrame frame = new JFrame("File Explorer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        // Create the root node
        rootNode = new DefaultMutableTreeNode(new FileWrapper(new File("Computer"))); // Placeholder for root
        folderTree = new JTree(rootNode);
        loadFiles(rootNode, File.listRoots()); // Load the root files

        // Explicitly update the root node display
        updateRootNodeDisplay();

        // Create an editable text field
        textField = new JTextField();
        textField.setEditable(false);

        // Create a text area for scenario content
        scenarioContentArea = new JTextArea();
        scenarioContentArea.setEditable(false);

        // Add a selection listener to the tree
        folderTree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) folderTree.getLastSelectedPathComponent();
                if (selectedNode != null) {
                    Object userObject = selectedNode.getUserObject();
                    if (userObject instanceof FileWrapper) {
                        FileWrapper fileWrapper = (FileWrapper) userObject;
                        textField.setText(fileWrapper.file.getAbsolutePath());

                        if (fileWrapper.file.isFile() && fileWrapper.file.getName().endsWith(".feature")) {
                            loadScenarios(selectedNode, fileWrapper.file);
                        } else if (fileWrapper.file.isDirectory()) {
                            loadFiles(selectedNode, fileWrapper.file.listFiles());
                        }
                    }
                }
            }
        });

        // Add the new mouse listener
        addTreeMouseListener();

        // Create the button to display the mvn.package command
        JButton displayMvnButton = new JButton("Display MVN Package Command");
        displayMvnButton.addActionListener(event -> {
            // Set the text in the text field
            textField.setText(mvnPackageCommand);

            // Open a terminal and echo the mvn.package property
            try {
                String command;
                if (System.getProperty("os.name").toLowerCase().contains("win")) {
                    // For Windows
                    command = "cmd.exe /c echo " + mvnPackageCommand;
                } else {
                    // For Unix-based systems (Linux, macOS)
//                    command = "bash -c 'echo " + mvnPackageCommand + "'";

                    command = "echo " + mvnPackageCommand;
                    String[] cmd = { "xfce4-terminal", "-e", "bash -c '" + command + "; exec bash'" };


                }

                // Execute the command in a new terminal window
                ProcessBuilder processBuilder = new ProcessBuilder(command);
                processBuilder.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


        // Layout the components
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                new JScrollPane(folderTree),
                new JScrollPane(scenarioContentArea));
        splitPane.setResizeWeight(0.7);

        JPanel panel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(displayMvnButton);
        panel.add(buttonPanel, BorderLayout.NORTH); // Add button panel
        panel.add(splitPane, BorderLayout.CENTER);
        panel.add(textField, BorderLayout.SOUTH);

        frame.add(panel, BorderLayout.CENTER);

        // Load the default path into the folder tree
        File initialDirectory = new File(defaultPath);
        if (initialDirectory.exists()) {
            loadFiles(rootNode, initialDirectory.listFiles());
        } else {
            JOptionPane.showMessageDialog(frame, "The specified path does not exist: " + defaultPath, "Error", JOptionPane.ERROR_MESSAGE);
        }

        // Set the window to be visible
        frame.setVisible(true);
    }


    private void updateRootNodeDisplay() {
        // This method updates the root node display based on current counts
        if (rootNode.getUserObject() instanceof FileWrapper) {
            FileWrapper rootWrapper = (FileWrapper) rootNode.getUserObject();
            rootWrapper.setFeatureCount(rootWrapper.featureCount); // Resetting the count
            rootWrapper.setScenarioCount(rootWrapper.scenarioCount); // Resetting the count
        }
        ((DefaultTreeModel) folderTree.getModel()).reload(rootNode);
    }

    private void loadProperties() {
        Properties properties = new Properties();
        try {
            FileInputStream input = new FileInputStream("config.properties");
            properties.load(input);
            // Get the relative path and resolve it to an absolute path
            String relativePath = properties.getProperty("default.path", "");
            defaultPath = Paths.get(relativePath).toAbsolutePath().toString();
            String types = properties.getProperty("file.types", "");
            fileTypes = types.split(",");
            mvnPackageCommand = properties.getProperty("mvn.package", "");

        } catch (IOException e) {
            e.printStackTrace();
            // Default to user home if there's an issue
            defaultPath = System.getProperty("user.home");
        }
    }

    private void loadFiles(DefaultMutableTreeNode parent, File[] files) {
        // Clear existing nodes
        parent.removeAllChildren();
        int featureCount = 0;
        int scenarioCount = 0;

        if (files != null) {
            for (File file : files) {
                if (file.isDirectory() || matchesFileTypes(file)) {
                    DefaultMutableTreeNode node = new DefaultMutableTreeNode(new FileWrapper(file));
                    parent.add(node);

                    if (file.isDirectory()) {
                        node.add(new DefaultMutableTreeNode("Loading...")); // Placeholder
                    }

                    // If it's a feature file, count it and its scenarios
                    if (file.isFile() && file.getName().endsWith(".feature")) {
                        featureCount++;
                        List<String> scenarios = extractScenarios(file);
                        scenarioCount += scenarios.size();
                    }
                }
            }
        }

        // Update the counts in the parent directory's FileWrapper
        if (parent.getUserObject() instanceof FileWrapper) {
            FileWrapper parentFileWrapper = (FileWrapper) parent.getUserObject();
            parentFileWrapper.setFeatureCount(featureCount);
            parentFileWrapper.setScenarioCount(scenarioCount);
        }

        // If parent is root, update the root node counts
        if (parent == rootNode) {
            // Create a new FileWrapper for root if it doesn't exist
            if (rootNode.getUserObject() == null) {
                rootNode.setUserObject(new FileWrapper(new File("Computer"))); // or another placeholder
            }
            FileWrapper rootWrapper = (FileWrapper) rootNode.getUserObject();
            rootWrapper.setFeatureCount(featureCount);
            rootWrapper.setScenarioCount(scenarioCount);

            // Log the current counts
            System.out.println("Updating Root Node Display:");
            System.out.println("Total Features: " + rootWrapper.featureCount);
            System.out.println("Total Scenarios: " + rootWrapper.scenarioCount);

        }

        ((DefaultTreeModel) folderTree.getModel()).reload(parent);
    }

    private void loadScenarios(DefaultMutableTreeNode parent, File featureFile) {
        parent.removeAllChildren(); // Clear existing children (scenarios)
        List<String> scenarios = extractScenarios(featureFile);
        // Update the scenario count in the FileWrapper
        if (parent.getUserObject() instanceof FileWrapper) {
            FileWrapper fileWrapper = (FileWrapper) parent.getUserObject();
            fileWrapper.setScenarioCount(scenarios.size());
        }
        for (String scenario : scenarios) {
            parent.add(new DefaultMutableTreeNode(scenario));
        }
        ((DefaultTreeModel) folderTree.getModel()).reload(parent);
    }


    private List<String> extractScenarios(File featureFile) {
        List<String> scenarios = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(featureFile)))) {
            String line;
            int lineNumber = 0;
            while ((line = br.readLine()) != null) {
                lineNumber++;
                // Look for lines starting with "Scenario:"
                if (line.trim().startsWith("Scenario:")) {
                    String scenarioName = line.trim().substring("Scenario:".length()).trim();
                    scenarios.add(lineNumber + ": " + scenarioName); // Add line number with scenario name
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return scenarios;
    }

    private boolean matchesFileTypes(File file) {
        for (String type : fileTypes) {
            String trimmedType = type.trim().replace("*", "");
            if (file.getName().endsWith(trimmedType)) {
                return true;
            }
        }
        return false;
    }

    private void displayScenarioContent(String scenarioLine, DefaultMutableTreeNode parentNode) {
        if (parentNode != null && parentNode.getUserObject() instanceof FileWrapper) {
            FileWrapper fileWrapper = (FileWrapper) parentNode.getUserObject();
            File featureFile = fileWrapper.file;

            try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(featureFile)))) {
                StringBuilder content = new StringBuilder();
                int targetLine = Integer.parseInt(scenarioLine.split(":")[0]); // Extract line number
                String line;
                int currentLine = 0;
                boolean inScenario = false;

                while ((line = br.readLine()) != null) {
                    currentLine++;
                    if (currentLine == targetLine) {
                        inScenario = true; // Start capturing the scenario content
                    }
                    if (inScenario) {
                        content.append(line).append("\n");
                        String nextLine = br.readLine(); // Read next line
                        if (nextLine == null ||
                                nextLine.trim().startsWith("Scenario:") ||
                                nextLine.trim().startsWith("Feature:") ||
                                nextLine.trim().startsWith("Background:")) {
                            break; // Stop when reaching the next scenario or certain keywords
                        }
                        content.append(nextLine).append("\n");
                    }
                }
                scenarioContentArea.setText(content.toString().trim());
            } catch (IOException e) {
                e.printStackTrace();
                scenarioContentArea.setText("Error reading scenario content.");
            }
        }
    }


    private void addTreeMouseListener() {
        folderTree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    handleDoubleClick(e);
                } else if (SwingUtilities.isRightMouseButton(e)) {
                    handleRightClick(e);
                }
            }
        });
    }


    private void handleDoubleClick(MouseEvent e) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) folderTree.getLastSelectedPathComponent();
        if (node != null) {
            Object userObject = node.getUserObject();

            // Check if the clicked node is a directory
            if (userObject instanceof FileWrapper) {
                FileWrapper fileWrapper = (FileWrapper) userObject;

                if (fileWrapper.file.isDirectory()) {
                    // Load the files in the directory
                    loadFiles(node, fileWrapper.file.listFiles());
                }
            } else if (userObject instanceof String && ((String) userObject).contains(":")) {
                // If it's a scenario, display its content
                displayScenarioContent((String) userObject, (DefaultMutableTreeNode) node.getParent());
            }
        }
    }


    private void handleRightClick(MouseEvent e) {
        int row = folderTree.getRowForLocation(e.getX(), e.getY());
        folderTree.setSelectionRow(row);
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                folderTree.getLastSelectedPathComponent();

        if (node != null) {
            if (node == rootNode) {
                showRootContextMenu(e);
            } else if (node.getUserObject() instanceof FileWrapper) {
                FileWrapper fileWrapper = (FileWrapper) node.getUserObject();
                if (fileWrapper.file.getName().endsWith(".feature")) {
                    showFeatureFileContextMenu(e, fileWrapper.file);
                }
            } else if (node.getUserObject() instanceof String && ((String) node.getUserObject()).contains(":")) {
                showScenarioContextMenu(e, (String) node.getUserObject());
            }
        }
    }


    private void showFeatureFileContextMenu(MouseEvent e, File featureFile) {
        JPopupMenu contextMenu = new JPopupMenu();
        JMenuItem viewItem = new JMenuItem("View");
        JMenuItem runItem = new JMenuItem("Run");
        JMenuItem refreshItem = new JMenuItem("Refresh");

        viewItem.addActionListener(event -> viewFeatureFile(featureFile));
        runItem.addActionListener(event -> runFeatureFile(featureFile));
        refreshItem.addActionListener(event -> refreshFeatureFile(featureFile));

        contextMenu.add(viewItem);
        contextMenu.add(runItem);
        contextMenu.add(refreshItem);
        contextMenu.show(e.getComponent(), e.getX(), e.getY());
    }

    private void showScenarioContextMenu(MouseEvent e, String scenarioName) {
        JPopupMenu contextMenu = new JPopupMenu();
        JMenuItem runItem = new JMenuItem("Run");
        JMenuItem refreshItem = new JMenuItem("Refresh");

        runItem.addActionListener(event -> runScenario(scenarioName));
        refreshItem.addActionListener(event -> refreshScenario(scenarioName));

        contextMenu.add(runItem);
        contextMenu.add(refreshItem);
        contextMenu.show(e.getComponent(), e.getX(), e.getY());
    }

    private void showRootContextMenu(MouseEvent e) {
        JPopupMenu contextMenu = new JPopupMenu();
        JMenuItem statsItem = new JMenuItem("Stats");
        JMenuItem refreshItem = new JMenuItem("Refresh");

        statsItem.addActionListener(event -> showStats());
        refreshItem.addActionListener(event -> refreshRootNode());

        contextMenu.add(statsItem);
        contextMenu.add(refreshItem);
        contextMenu.show(e.getComponent(), e.getX(), e.getY());
    }

    private void showStats() {
        FileWrapper rootWrapper = (FileWrapper) rootNode.getUserObject();
        String message = String.format("Total Features: %d\nTotal Scenarios: %d",
                rootWrapper.featureCount, rootWrapper.scenarioCount);
        JOptionPane.showMessageDialog(folderTree, message, "Statistics", JOptionPane.INFORMATION_MESSAGE);
    }

    private void refreshRootNode() {
        loadFiles(rootNode, File.listRoots());
        updateRootNodeDisplay(); // Ensure the root node displays updated counts
    }


    private void viewFeatureFile(File featureFile) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(featureFile)))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            scenarioContentArea.setText("Error reading feature file.");
            return;
        }
        // Set the text area content to the feature file content
        scenarioContentArea.setText(content.toString().trim());
        System.out.println("Viewing feature file: " + featureFile.getName());
    }


    private void runFeatureFile(File featureFile) {
        // Implement running logic here
        System.out.println("Running feature file: " + featureFile.getName());
    }

    private void refreshFeatureFile(File featureFile) {
        // Implement refresh logic here
        System.out.println("Refreshing feature file: " + featureFile.getName());
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) folderTree.getLastSelectedPathComponent();
        loadScenarios(node, featureFile);
    }

    private void runScenario(String scenarioName) {
        // Implement running logic here
        System.out.println("Running scenario: " + scenarioName);
    }

    private void refreshScenario(String scenarioName) {
        // Implement refresh logic here
        System.out.println("Refreshing scenario: " + scenarioName);
        DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode)
                ((DefaultMutableTreeNode) folderTree.getLastSelectedPathComponent()).getParent();
        if (parentNode != null && parentNode.getUserObject() instanceof FileWrapper) {
            FileWrapper fileWrapper = (FileWrapper) parentNode.getUserObject();
            loadScenarios(parentNode, fileWrapper.file);
        }
    }

    private static class FileWrapper {
        File file;
        int scenarioCount;
        int featureCount;

        FileWrapper(File file) {
            this.file = file;
            this.scenarioCount = 0;
            this.featureCount = 0;
        }

        void setScenarioCount(int count) {
            this.scenarioCount = count;
        }

        void setFeatureCount(int count) {
            this.featureCount = count;
        }

        @Override
        public String toString() {
            if (file.isFile()) {
                return file.getName() + " (" + scenarioCount + " scenarios)";
            } else {
                return file.getName() + " (S:" + scenarioCount + "|F:" + featureCount + ")";
            }
        }
    }

}