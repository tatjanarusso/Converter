package com.example.converter;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;

import static java.util.Collections.reverse;
import static java.util.Collections.reverseOrder;

public class ConverterController {

    @FXML private ComboBox<String> pathfield;
    @FXML private Button searchButton;
    @FXML private Button clearButton;
    @FXML private Button selectAllButton;
    @FXML private Button deselectAllButton;
    @FXML private TableView tableView;
    @FXML private RadioButton rbPDF;
    @FXML private RadioButton rbWord;
    @FXML private RadioButton rbZIP;
    @FXML private RadioButton rbEndsWith;
    @FXML private RadioButton rbRegularExpression;
    @FXML
    private TableColumn selected;
    @FXML
    private TableColumn path;


    @FXML
    /**
     * Description: When the search Button is clicked. It'll clear the table and send the values of the path and filter field
     * to the searchDirectories method and the getStragety() & ConverterList. When the method was used it'll fill the selected column
     * with checkboxes.
     */
    protected void onSearchClick() throws IOException {
        updateList();
        searchButton.setDisable(true);
        ObservableList<ConverterItem> ConverterList = tableView.getItems();
        ConverterList.clear();
        String path = pathfield.getValue();
        searchDirectories(path, getStrategy(), tableView.getItems());
        selected.setCellFactory(CheckBoxTableCell.forTableColumn(selected));
        searchButton.setDisable(false);
    }

    @FXML
    /**
     * Description: When the clear button is clicked. It'll clear the table.
     */
    protected void onClearClick(){
        clearButton.setDisable(true);
        ObservableList<ConverterItem> ConverterItems = tableView.getItems();
        ConverterItems.clear();
        clearButton.setDisable(false);
    }

    @FXML
    /**
     * Description: When the select all button is clicked. It'll set all the checkboxes to true.
     */
    protected void onSelectAllClick(){
        selectAllButton.setDisable(true);
        ObservableList<ConverterItem> ConverterItems = tableView.getItems();
        for(ConverterItem item: ConverterItems){
            item.setSelected(true);
        }

        selectAllButton.setDisable(false);
    }

    @FXML
    /**
     * Description: When the deselect all button is clicked. It'll set all the checkboxes to false.
     */
    protected void onDeselectAllClick(){
        deselectAllButton.setDisable(true);
        ObservableList<ConverterItem> ConverterItems = tableView.getItems();
        for(ConverterItem item: ConverterItems){
            item.setSelected(false);
        }

        deselectAllButton.setDisable(false);
    }




    /**
     * Description: When the delte button is clicked. An alert will be created where you can either confirm or cancel your deletion.
     * If you click on delete, the choosen directories will be deleted it'll send the new path/paths to the searchDirectories method
     * and recursively get all subdirectories and save them in a list.
     */
    @FXML
    protected void onConverterClick(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete?", ButtonType.OK, ButtonType.CANCEL);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.OK) {
            ObservableList<ConverterItem> ConverterItems = tableView.getItems();
            for(ConverterItem item: ConverterItems){
                if (item.isSelected()){
                    System.out.println(item.getPathname());
                    File Converter = new File(item.getPathname());
                    deleteDirectory(Converter);

                }
            }
            ObservableList<ConverterItem> ConverterList = tableView.getItems();
            ConverterList.clear();
            String path = pathfield.getValue();
            searchDirectories(path, getStrategy(), tableView.getItems());
        }
        if (alert.getResult() == ButtonType.CANCEL){
            System.out.println("Close");
        }
    }

    /**
     * Description: gets content of the list and deletes the directories.
     * @param directoryToBeDeleted: Path of the directory from the onConverterClick method.
     * @return
     */
    boolean deleteDirectory(File directoryToBeDeleted) {
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file);
                directoryToBeDeleted.delete();
            }
        }
        return directoryToBeDeleted.delete();
    }
    //RBS

    /**
     * Description: checks which radiobutton is used and sets the new  value for strategy.
     * @param name: name of the RB
     * @param strategy: used in searchDirectories as filter
     * @return
     */
    private boolean matchConverter(String name, EnumConverterStrategy strategy){
        if(strategy == EnumConverterStrategy.PDF) return true;
        if(strategy == EnumConverterStrategy.WORD) return true;
        if(strategy == EnumConverterStrategy.ZIP) return true;
        if(strategy == EnumConverterStrategy.ENDS_WITH) return true;
        return strategy == EnumConverterStrategy.REGULAR_EXPRESSION;
    }

    /**
     * Description: checks which rb is currently selected and gets the strategy
     * @return
     */
    private EnumConverterStrategy getStrategy(){
        if(rbPDF.isSelected()) return EnumConverterStrategy.PDF;
        if(rbWord.isSelected()) return EnumConverterStrategy.WORD;
        if(rbZIP.isSelected()) return EnumConverterStrategy.ZIP;
        if(rbEndsWith.isSelected()) return EnumConverterStrategy.ENDS_WITH;
        if(rbRegularExpression.isSelected()) return EnumConverterStrategy.REGULAR_EXPRESSION;
        return EnumConverterStrategy.UNKNOWN;
    }

    //Searching for Directories.

    /**
     * Description: searches directories recursively. It gets the variables from the onSearchClick method and it puts
     * all directories with the specific requirements into the list.
     * If the filter is = "" It'll just search all the directories and subdirectories from the given root path.
     * Else it'll search it with the given filter and method and search them. If the method finds directories it'll
     * get the FileCreationTime and create a new ConverterItem.
     * @param path: rootpath
     * @param method: method/strategy
     * @param ConverterList: list where all the dirs are saved.
     */
    private void searchDirectories(String path, EnumConverterStrategy method, ObservableList<ConverterItem> ConverterList){
        File currentFile = new File(path);
        try{
            File[] files = currentFile.listFiles();
            if (files == null){
                return;
            }
            for (File file: files){
                if(file.isFile()){
                    String name = file.getName();
                        if (matchConverter(name, method)) {
                            FileTime creationTime = (FileTime) Files.getAttribute(Path.of(file.getAbsolutePath()), "creationTime");
                            ConverterList.add(new ConverterItem(file.getAbsolutePath(), creationTime.toString()));

                        } else {
                            searchDirectories(file.getAbsolutePath(), method, ConverterList);
                        }
                    }
                    else {
                        FileTime creationTime = (FileTime) Files.getAttribute(Path.of(file.getAbsolutePath()), "creationTime");
                        ConverterList.add(new ConverterItem(file.getAbsolutePath(), creationTime.toString()));
                        searchDirectories(file.getAbsolutePath(), method, ConverterList);
                    }
                }
        } catch (SecurityException e){
            System.out.println("Verzeichnis ist schreibgeschützt" + currentFile.getAbsolutePath());
        } catch (IOException ex){

        }
    }

    @FXML
    /**
     * Description: puts all the contents of the file Converter.txt which is found in your home dir and will show the
     * contents in the combobox/pathfield
     */
    public void initialize() throws IOException {
        pathfield.getItems().clear();
        //reads txt file
        String homeDir = System.getProperty("user.home");
        System.out.println(homeDir);
        File yourFile = new File(homeDir + "\\Converter.txt");
        yourFile.createNewFile(); // if file already exists will do nothing
        BufferedReader br = new BufferedReader(new FileReader(homeDir + "\\Converter.txt"));
        try {
            reverse(pathfield.getItems());
            //StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                //add item
                pathfield.getItems().add(line);
                //sb.append(line);
                line = br.readLine();
            }

        } finally {
            br.close();
        }

        //Default Selection first item
        //pathfield.getSelectionModel().select(0);
    }


    /**
     * Description: Updates the combobox/the Converter.txt file. It'll save up to 10 values in the list and after that
     * delete the last used and set the newest as the first value. If the value is already in the list, it'll delete the position
     * it has and put it first.
     * @throws IOException
     */
    @FXML
    //Updating the list (dropdown-menue)
    public void updateList() throws IOException {
        String homeDir = System.getProperty("user.home");
        FileWriter writer = new FileWriter(homeDir + "\\Converter.txt");
        System.out.println(pathfield.getItems());
        String newLine = pathfield.getValue();
        if (pathfield.getItems().contains(newLine)){
            pathfield.getItems().remove(newLine);
            pathfield.getItems().add(0, newLine);
            pathfield.getSelectionModel().select(0);
            for (String str : pathfield.getItems()) {
                writer.write(str + "\n");
            }
            return;
        }
        else {
            if (pathfield.getItems().size() < 10) {
                pathfield.getItems().add(0, newLine);

                System.out.println(pathfield.getItems());
                for (String str : pathfield.getItems()) {
                    writer.write(str + "\n");
                }
                writer.close();
            }
            else {
                pathfield.getItems().remove(9);
                pathfield.getItems().add(0,newLine);
                pathfield.getSelectionModel().select(0);
                for (String str : pathfield.getItems()) {

                    writer.write(str + "\n");
                }
                writer.close();

            }

        }
    }

}