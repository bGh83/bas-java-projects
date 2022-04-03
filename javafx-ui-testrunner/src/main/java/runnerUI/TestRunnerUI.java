package runnerUI;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class TestRunnerUI extends Application {

	List<String> listOfFeatureFiles;
	private static Logger log = LogManager.getLogger(TestRunnerUI.class.getName());

	public void genListOfFeatureFiles(String pAbsolutePath) {
		try {
			File dir = new File(pAbsolutePath);
			String[] extensions = new String[] { "feature" };
			System.out.println(pAbsolutePath+"|"+ dir.getCanonicalPath()
					+ " including those in subdirectories");
			
			listOfFeatureFiles = new ArrayList();
			List<File> files = (List<File>) FileUtils.listFiles(dir, extensions, true);
			for (File file : files) {
				listOfFeatureFiles.add(file.getCanonicalPath());
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("Test Runner");
		TabPane tabPane = new TabPane();
		ListView listView = new ListView();
		listView.getItems().add("DEFAULT");
		Button btnLoadTests = new Button("Load Tests");
		Button btnRunSelectedTest = new Button("Run Selected Test");
		Button btnRunAllTests = new Button("Run All Tests");
		
		DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File("src"));

        btnLoadTests.setOnAction(e -> {
            File selectedDirectory = directoryChooser.showDialog(stage);
            genListOfFeatureFiles(selectedDirectory.getAbsolutePath());
            listView.getItems().clear();
            listView.getItems().addAll(listOfFeatureFiles);
        });
        
        btnRunSelectedTest.setOnAction(e -> {
        	
        	String selected = listView.getSelectionModel().getSelectedItem().toString();
            try {
            	String [] command = {"/bin/bash", "-c", "./run "+selected};    
             	Process proc = new ProcessBuilder(command).start();
             	proc.waitFor();
			} catch (Exception e1) {
				e1.printStackTrace();
			} 
        });
        
		HBox hBox = new HBox (10, btnLoadTests, btnRunSelectedTest, btnRunAllTests);		
		VBox vBox = new VBox(10, listView, hBox);
		Tab tabRun = new Tab("Run", vBox);
		tabPane.getTabs().add(tabRun);
		Scene scene = new Scene(tabPane, 640, 480);
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		Application.launch(args);
	}

}

