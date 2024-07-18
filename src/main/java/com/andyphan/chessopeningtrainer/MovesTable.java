package com.andyphan.chessopeningtrainer;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

public class MovesTable extends VBox {
    private TableView<MovePair> tableView;

    public MovesTable() {
        tableView = new TableView<>();

        TableColumn<MovePair, String> moveNumberColumn = new TableColumn<>("#");
        moveNumberColumn.setCellValueFactory(cellData -> new SimpleStringProperty(""));
        moveNumberColumn.setCellFactory(column -> {
            return new TableCell<MovePair, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                    } else {
                        setText(String.valueOf(getIndex() + 1));
                    }
                }
            };
        });
        moveNumberColumn.setResizable(false);
        moveNumberColumn.setSortable(false);
        moveNumberColumn.setPrefWidth(30);
        TableColumn<MovePair, String> whiteColumn = new TableColumn<>("White Move");
        whiteColumn.setCellValueFactory(new PropertyValueFactory<>("whiteMove"));
        whiteColumn.prefWidthProperty().bind(tableView.widthProperty().divide(2).subtract(15));
        whiteColumn.setResizable(false);
        whiteColumn.setSortable(false);
        TableColumn<MovePair, String> blackColumn = new TableColumn<>("Black Move");
        blackColumn.setCellValueFactory(new PropertyValueFactory<>("blackMove"));
        blackColumn.prefWidthProperty().bind(tableView.widthProperty().divide(2).subtract(15));
        blackColumn.setResizable(false);
        blackColumn.setSortable(false);

        tableView.setPrefWidth(200);
        tableView.setMaxWidth(200);
        tableView.setPrefHeight(800);

        tableView.getColumns().addAll(moveNumberColumn, whiteColumn, blackColumn);
        getChildren().add(tableView);
    }

    public TableView<MovePair> getTableView() {
        return this.tableView;
    }

}
