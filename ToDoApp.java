import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class ToDoApp extends Application {

    private MaxHeap heap = new MaxHeap(50);
    private ListView<Task> listView = new ListView<>();

    // Time dropdowns
    private ComboBox<Integer> hourBox = new ComboBox<>();
    private ComboBox<Integer> minuteBox = new ComboBox<>();
    private ComboBox<String> ampmBox = new ComboBox<>();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Priority To-Do List");

        Label descLabel = new Label("Task Description:");
        TextField descField = new TextField();

        Label priorityLabel = new Label("Priority (1–10):");
        ComboBox<Integer> priorityBox = new ComboBox<>();
        for (int i = 1; i <= 10; i++) priorityBox.getItems().add(i);
        priorityBox.setPromptText("Select");

        Label timeLabel = new Label("Choose Time:");
        for (int i = 1; i <= 12; i++) hourBox.getItems().add(i);
        for (int i = 0; i < 60; i++) minuteBox.getItems().add(i);
        ampmBox.getItems().addAll("AM", "PM");
        hourBox.setPromptText("Hour");
        minuteBox.setPromptText("Min");
        ampmBox.setPromptText("AM/PM");

        HBox timeRow = new HBox(10, hourBox, minuteBox, ampmBox);

        Button addButton = new Button("Add Task");
        Button viewButton = new Button("View Highest Priority");
        Button completeButton = new Button("Complete Highest Priority");

        Label outputLabel = new Label("");

        addButton.setOnAction(e -> {
            try {
                String desc = descField.getText().trim();
                Integer priority = priorityBox.getValue();
                Integer hour = hourBox.getValue();
                Integer minute = minuteBox.getValue();
                String ampm = ampmBox.getValue();

                if (desc.isEmpty() || priority == null || hour == null || minute == null || ampm == null) {
                    outputLabel.setText("Error: Fill all fields.");
                    return;
                }

                String formattedTime = String.format("%d:%02d %s", hour, minute, ampm);

                heap.insert(new Task(desc, priority, formattedTime));
                updateList();

                descField.clear();
                priorityBox.setValue(null);
                hourBox.setValue(null);
                minuteBox.setValue(null);
                ampmBox.setValue(null);

                outputLabel.setText("Task added!");
            } catch (Exception ex) {
                outputLabel.setText("Error: Invalid input.");
            }
        });

        viewButton.setOnAction(e -> {
            Task top = heap.peek();
            if (top == null) outputLabel.setText("Heap is empty.");
            else outputLabel.setText("Highest: " + top);
        });

        completeButton.setOnAction(e -> {
            Task removed = heap.extractMax();
            if (removed == null) outputLabel.setText("No tasks to complete.");
            else outputLabel.setText("Completed: " + removed);
            updateList();
        });

        // ---------- CUSTOM CELL WITH DELETE BUTTON ----------
        listView.setCellFactory(lv -> new ListCell<Task>() {

            private final Button deleteButton = new Button("X");
            private final HBox row = new HBox(10);

            {
                deleteButton.setOnAction(e -> {
                    Task task = getItem();
                    if (task != null) {
                        heap.remove(task);
                        getListView().getItems().remove(task);
                    }
                });
                row.getChildren().add(deleteButton);
            }

            @Override
            protected void updateItem(Task task, boolean empty) {
                super.updateItem(task, empty);

                if (empty || task == null) {
                    setText(null);
                    setGraphic(null);
                    setStyle("");
                    return;
                }

                setText(task.toString());
                setGraphic(row);

                int priority = task.getPriority();

                if (priority <= 3) setStyle("-fx-background-color: #b6fcb6;");
                else if (priority <= 6) setStyle("-fx-background-color: #fff6a3;");
                else if (priority <= 8) setStyle("-fx-background-color: #ffcc85;");
                else setStyle("-fx-background-color: #ff8a8a;");
            }
        });

        VBox layout = new VBox(10,
            descLabel, descField,
            priorityLabel, priorityBox,
            timeLabel, timeRow,
            addButton, viewButton, completeButton,
            outputLabel,
            new Label("Current Tasks:"),
            listView
        );
        layout.setPadding(new Insets(15));

        Scene scene = new Scene(layout, 420, 550);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void updateList() {
        listView.getItems().clear();
        for (Task t : heap.getHeapArray()) {
            listView.getItems().add(t);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
