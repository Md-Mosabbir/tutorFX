package tutor.dsafx.controllers;

import javafx.animation.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;
import tutor.dsafx.util.SceneSwitcher;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Arrays;

public class SortingController extends BaseController {



    @FXML
    private TextField inputField;

    @FXML
    private ComboBox<String> algorithmComboBox;

    @FXML
    private Label unsortedDisplay;

    @FXML
    private ListView<String> stepsListView;

    @FXML
    private HBox arrayPane;

    private int[] numbers;

    private static final Duration STEP_DURATION = Duration.millis(800);  // Increased duration
    private static final Duration SWAP_DURATION = Duration.millis(700);  // Increased duration
    private static final Interpolator SMOOTH_INTERPOLATOR = Interpolator.SPLINE(0.25, 0.1, 0.25, 1);

    private ObservableList<String> steps = FXCollections.observableArrayList();

    private VBox[] barContainers;

    @FXML
    public void initialize() {
        algorithmComboBox.setItems(FXCollections.observableArrayList(
                "Bubble Sort", "Selection Sort", "Quick Sort"
        ));
        algorithmComboBox.getSelectionModel().selectFirst();
        stepsListView.setItems(steps);
    }

    @FXML
    protected void onSort(ActionEvent event) {
        String input = inputField.getText();
        if (input == null || input.trim().isEmpty()) {
            return;
        }

        try {
            numbers = Arrays.stream(input.split(","))
                    .map(String::trim)
                    .mapToInt(Integer::parseInt)
                    .toArray();

            unsortedDisplay.setText("Unsorted: " + Arrays.toString(numbers));
            steps.clear();

            String algorithm = algorithmComboBox.getSelectionModel().getSelectedItem();

            setupArrayBars(numbers);

            int[] arrToSort = numbers.clone();

            switch (algorithm) {
                case "Bubble Sort":
                    animateBubbleSort(arrToSort);
                    break;
                case "Selection Sort":
                    animateSelectionSort(arrToSort);
                    break;
                case "Quick Sort":
                    animateQuickSort(arrToSort);
                    break;
            }
        } catch (NumberFormatException e) {
            unsortedDisplay.setText("Invalid input! Please enter numbers separated by commas.");
            steps.clear();
        }
    }

    private void setupArrayBars(int[] arr) {
        arrayPane.getChildren().clear();
        barContainers = new VBox[arr.length];

        double maxVal = Arrays.stream(arr).max().orElse(1);
        double maxHeight = 150;

        for (int i = 0; i < arr.length; i++) {
            Rectangle rect = new Rectangle(30, (arr[i] / maxVal) * maxHeight);
            rect.setFill(Color.CORNFLOWERBLUE);
            rect.setStroke(Color.BLACK);

            Text text = new Text(String.valueOf(arr[i]));

            VBox vbox = new VBox(5, rect, text);
            vbox.setMinWidth(30);
            vbox.setMaxWidth(30);
            vbox.setStyle("-fx-alignment: bottom-center;");  // Changed from center to bottom-center

            // Set VBox to fill available height and align children at bottom
            VBox.setVgrow(rect, Priority.ALWAYS);
            vbox.setFillWidth(true);

            barContainers[i] = vbox;
            arrayPane.getChildren().add(vbox);
        }

        // Ensure the HBox aligns its children at the bottom
        arrayPane.setAlignment(Pos.BOTTOM_CENTER);
    }

    private void animateBubbleSort(int[] arr) {
        SequentialTransition seq = new SequentialTransition();

        int n = arr.length;
        boolean swapped;
        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                final int indexA = j;
                final int indexB = j + 1;

                seq.getChildren().add(colorBarsTransition(indexA, indexB, Color.ORANGE));

                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swapped = true;

                    seq.getChildren().add(createSwapTransition(indexA, indexB));

                    // Swap barContainers references in array
                    VBox tempVbox = barContainers[indexA];
                    barContainers[indexA] = barContainers[indexB];
                    barContainers[indexB] = tempVbox;

                    seq.getChildren().add(new PauseTransition(STEP_DURATION));
                } else {
                    seq.getChildren().add(new PauseTransition(STEP_DURATION));
                }

                seq.getChildren().add(colorBarsTransition(indexA, indexB, Color.CORNFLOWERBLUE));

                final int stepNum = i * (n - 1) + j + 1;
                seq.getChildren().add(new PauseTransition(Duration.millis(50)));
                seq.getChildren().add(new RunnableTransition(() ->
                        steps.add("Step " + stepNum + ": " + Arrays.toString(arr))
                ));
            }
            if (!swapped) break;
        }
        seq.getChildren().add(new RunnableTransition(() ->
                steps.add("Sorted: " + Arrays.toString(arr))
        ));

        seq.play();
    }

    private void animateSelectionSort(int[] arr) {
        SequentialTransition seq = new SequentialTransition();
        int n = arr.length;

        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;

            seq.getChildren().add(colorBarTransition(minIndex, Color.RED));

            for (int j = i + 1; j < n; j++) {
                final int indexJ = j;

                seq.getChildren().add(colorBarTransition(indexJ, Color.ORANGE));

                seq.getChildren().add(new PauseTransition(STEP_DURATION));

                if (arr[j] < arr[minIndex]) {
                    seq.getChildren().add(colorBarTransition(minIndex, Color.CORNFLOWERBLUE));
                    minIndex = j;
                    seq.getChildren().add(colorBarTransition(minIndex, Color.RED));
                } else {
                    seq.getChildren().add(colorBarTransition(j, Color.CORNFLOWERBLUE));
                }
            }

            if (minIndex != i) {
                int temp = arr[minIndex];
                arr[minIndex] = arr[i];
                arr[i] = temp;

                seq.getChildren().add(createSwapTransition(i, minIndex));

                VBox tempVbox = barContainers[i];
                barContainers[i] = barContainers[minIndex];
                barContainers[minIndex] = tempVbox;
            }

            seq.getChildren().add(colorBarTransition(i, Color.GREEN));
            if (minIndex != i) {
                seq.getChildren().add(colorBarTransition(minIndex, Color.CORNFLOWERBLUE));
            }

            final int stepNum = i + 1;
            seq.getChildren().add(new RunnableTransition(() ->
                    steps.add("Step " + stepNum + ": " + Arrays.toString(arr))
            ));
        }

        seq.getChildren().add(new RunnableTransition(() ->
                steps.add("Sorted: " + Arrays.toString(arr))
        ));

        seq.play();
    }

    private void animateQuickSort(int[] arr) {
        SequentialTransition seq = new SequentialTransition();

        quickSortHelper(arr, 0, arr.length - 1, seq);

        seq.getChildren().add(new RunnableTransition(() ->
                steps.add("Sorted: " + Arrays.toString(arr))
        ));
        seq.play();
    }

    private void quickSortHelper(int[] arr, int low, int high, SequentialTransition seq) {
        if (low < high) {
            int pi = partition(arr, low, high, seq);

            // Add small delay between recursive calls
            seq.getChildren().add(new PauseTransition(Duration.millis(100)));

            quickSortHelper(arr, low, pi - 1, seq);

            // Add small delay between recursive calls
            seq.getChildren().add(new PauseTransition(Duration.millis(100)));

            quickSortHelper(arr, pi + 1, high, seq);
        }
    }

    private int partition(int[] arr, int low, int high, SequentialTransition seq) {
        int pivot = arr[high];
        seq.getChildren().add(colorBarTransition(high, Color.PURPLE));

        int i = low - 1;

        for (int j = low; j < high; j++) {
            final int indexJ = j;
            seq.getChildren().add(colorBarTransition(j, Color.ORANGE));
            seq.getChildren().add(new PauseTransition(STEP_DURATION));

            if (arr[j] <= pivot) {
                i++;

                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;

                seq.getChildren().add(createSwapTransition(i, j));

                VBox tempVbox = barContainers[i];
                barContainers[i] = barContainers[j];
                barContainers[j] = tempVbox;
            }

            seq.getChildren().add(colorBarTransition(j, Color.CORNFLOWERBLUE));
        }

        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;

        seq.getChildren().add(createSwapTransition(i + 1, high));

        VBox tempVbox = barContainers[i + 1];
        barContainers[i + 1] = barContainers[high];
        barContainers[high] = tempVbox;

        seq.getChildren().add(colorBarTransition(high, Color.CORNFLOWERBLUE));
        seq.getChildren().add(colorBarTransition(i + 1, Color.GREEN));

        final int partitionIndex = i + 1;
        seq.getChildren().add(new RunnableTransition(() ->
                steps.add("Partitioned at index " + partitionIndex + ": " + Arrays.toString(arr))
        ));

        return partitionIndex;
    }

    private Animation createSwapTransition(int i, int j) {
        // Early return if indices are the same
        if (i == j) {
            return new PauseTransition(Duration.ZERO);
        }

        VBox vboxA = barContainers[i];
        VBox vboxB = barContainers[j];

        double distance = (j - i) * (vboxA.getWidth() + arrayPane.getSpacing());

        // Create smooth translate transitions
        TranslateTransition ttA = new TranslateTransition(SWAP_DURATION, vboxA);
        ttA.setByX(distance);
        ttA.setInterpolator(SMOOTH_INTERPOLATOR);

        TranslateTransition ttB = new TranslateTransition(SWAP_DURATION, vboxB);
        ttB.setByX(-distance);
        ttB.setInterpolator(SMOOTH_INTERPOLATOR);

        // Add slight vertical movement for more natural effect
        TranslateTransition ttAUp = new TranslateTransition(Duration.millis(SWAP_DURATION.toMillis()/2), vboxA);
        ttAUp.setByY(-20);
        ttAUp.setAutoReverse(true);
        ttAUp.setCycleCount(2);
        ttAUp.setInterpolator(SMOOTH_INTERPOLATOR);

        TranslateTransition ttBUp = new TranslateTransition(Duration.millis(SWAP_DURATION.toMillis()/2), vboxB);
        ttBUp.setByY(-20);
        ttBUp.setAutoReverse(true);
        ttBUp.setCycleCount(2);
        ttBUp.setInterpolator(SMOOTH_INTERPOLATOR);

        ParallelTransition pt = new ParallelTransition(ttA, ttB, ttAUp, ttBUp);

        pt.setOnFinished(e -> {
            // Reset translations
            vboxA.setTranslateX(0);
            vboxB.setTranslateX(0);
            vboxA.setTranslateY(0);
            vboxB.setTranslateY(0);

            // Update the barContainers array first
            VBox tempVbox = barContainers[i];
            barContainers[i] = barContainers[j];
            barContainers[j] = tempVbox;

            // Get current positions in the HBox
            ObservableList<Node> children = arrayPane.getChildren();
            int indexA = children.indexOf(vboxA);
            int indexB = children.indexOf(vboxB);

            // Only proceed if both elements are found and need to be swapped
            if (indexA >= 0 && indexB >= 0 && indexA != indexB) {
                // Remove both elements
                children.remove(vboxA);
                children.remove(vboxB);

                // Reinsert in swapped positions
                if (indexA < indexB) {
                    children.add(indexA, vboxB);
                    children.add(indexB, vboxA);
                } else {
                    children.add(indexB, vboxA);
                    children.add(indexA, vboxB);
                }
            }
        });

        return pt;
    }

    private Animation colorBarTransition(int index, Color color) {
        VBox vbox = barContainers[index];
        Rectangle rect = (Rectangle) vbox.getChildren().get(0);

        FillTransition ft = new FillTransition(STEP_DURATION, rect, (Color) rect.getFill(), color);
        ft.setInterpolator(SMOOTH_INTERPOLATOR);
        return ft;
    }

    private Animation colorBarsTransition(int indexA, int indexB, Color color) {
        return new ParallelTransition(
                colorBarTransition(indexA, color),
                colorBarTransition(indexB, color)
        );
    }

    @Override
    protected void updateDisplay() {

    }



    // Helper class to run code during SequentialTransition
    private static class RunnableTransition extends Transition {

        private final Runnable action;

        public RunnableTransition(Runnable action) {
            this.action = action;
            setCycleDuration(Duration.ZERO);
        }

        @Override
        protected void interpolate(double frac) {
            action.run();
        }
    }
}
