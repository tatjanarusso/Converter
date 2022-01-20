package com.example.converter;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleSetProperty;
import javafx.beans.property.SimpleStringProperty;

import java.nio.file.Files;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.FileTime;

    public class ConverterItem {

        public ConverterItem() {
            this("", "");
        }

        public ConverterItem(String path, String modificationdate){
            setPathname(path);
            setModificationdate(modificationdate);
        }

        public String getPathname() {
            return pathname.get();
        }

        public SimpleStringProperty pathnameProperty() {
            return pathname;
        }

        public void setPathname(String pathname) {
            this.pathname.set(pathname);
        }

        public boolean isSelected() {
            return selected.get();
        }

        public SimpleBooleanProperty selectedProperty() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected.set(selected);
        }

        private SimpleStringProperty pathname = new SimpleStringProperty("");
        private SimpleBooleanProperty selected = new SimpleBooleanProperty(false);

        public String getModificationdate() {
            return modificationdate.get();
        }

        public SimpleStringProperty modificationdateProperty() {
            return modificationdate;
        }

        public void setModificationdate(String modificationdate) {
            this.modificationdate.set(modificationdate);
        }

        private SimpleStringProperty modificationdate = new SimpleStringProperty("");


}
