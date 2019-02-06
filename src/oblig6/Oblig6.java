/**
 * Et program som tegner ut et binært søketre.
 * Bruker kan legge til og slette noder, samt legge til tilfeldige noder.
 * Det balanseres også ved bruk av AVL.
 */
package oblig6;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.stage.Stage;

/**
 *
 * @author Frode Siem 151332
 */
public class Oblig6 extends Application {

    private static final int WIDTH = 1200;
    private static final int HEIGHT = 900;
    private final static int H_DIFF = 50;
    private final static int RANDOM_NUM_MULTIPLIER = 100;
    private final static int OVAL_SIZE = 25;

    // Offsets for å plassere tekst mitt i sirkel
    private final static int TEXT_X_OFFSET = 7;
    private final static int TEXT_Y_OFFSET = 18;

    // Offsets for å plassere linjer passende og fint.
    private final static int LEFT_LINE_START_HEIGHT_OFFSET = 15;
    private final static int LEFT_LINE_END_WIDTH_OFFSET = 25;
    private final static int LEFT_LINE_END_HEIGHT_OFFSET = 60;

    private final static int RIGHT_LINE_START_WIDTH_OFFSET = 25;
    private final static int RIGHT_LINE_START_HEIGHT_OFFSET = 15;
    private final static int RIGHT_LINE_END_HEIGHT_OFFSET = 60;

    BinarySearchTree bst;

    Canvas c = new Canvas(WIDTH, HEIGHT);
    GraphicsContext gc = c.getGraphicsContext2D();

    @Override
    public void start(Stage primaryStage) {

        BorderPane b = new BorderPane();
        FlowPane f = new FlowPane();
        Group root = new Group();

        Button nyBtn = new Button("NY");
        TextField nyInput = new TextField();

        Button slettBtn = new Button("SLETT");
        TextField slettInput = new TextField();

        Button randomBtn = new Button("Legg inn tilfeldig tall");

        Button exitBtn = new Button("EXIT");

        TextField errorMsg = new TextField();

        f.getChildren().addAll(nyBtn, nyInput, slettBtn, slettInput, randomBtn, exitBtn, errorMsg);

        bst = new BinarySearchTree<>();

        nyBtn.setOnAction(e -> {
            String tom = nyInput.getText();

            if (gyldig(tom)) {
                int item = Integer.parseInt(nyInput.getText());
                bst.insert(item);
                errorMsg.setText("La til node " + item);
                gc.clearRect(0, 0, WIDTH, HEIGHT);
                drawTree(bst.root, 0, WIDTH, 0);
            } else {
                errorMsg.setText("Ugyldig ny input");
                throw new IllegalArgumentException("Kan ikke legge til null");
            }
        });

        slettBtn.setOnAction(e -> {
            String input = slettInput.getText();

            if (gyldig(input)) {
                int item = Integer.parseInt(slettInput.getText());

                if (bst.find(item) == null) {
                    errorMsg.setText("Fant ikke node");
                    throw new IllegalArgumentException("Fant ikke ønsket node");
                } else {
                    bst.remove(item);
                    gc.clearRect(0, 0, WIDTH, HEIGHT);
                    errorMsg.setText("Slettet node " + item);
                    drawTree(bst.root, 0, WIDTH, 0);
                }
            } else {
                errorMsg.setText("Ugyldig slett input");
                throw new IllegalArgumentException("Kan ikke legge til null");
            }

        });

        randomBtn.setOnAction(e -> {
            int item = (int) (Math.random() * RANDOM_NUM_MULTIPLIER);
            bst.insert(item);
            errorMsg.setText("La til tilfeldig node");
            gc.clearRect(0, 0, WIDTH, HEIGHT);        
            drawTree(bst.root, 0, WIDTH, 0);

        });

        exitBtn.setOnAction(e -> {
            Platform.exit();
        });

        root.getChildren().add(c);

        b.setTop(f);
        b.setCenter(root);

        Scene scene = new Scene(b, WIDTH, HEIGHT);

        primaryStage.setTitle("Binary Search Tree");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
/***
 * 
 * @param node
 * @param venstre venstre kant på canvas
 * @param høyre høyre kant på canvas
 * @param høyde høyde til neste node
 */
    public void drawTree(BinaryNode node, int venstre, int høyre, int høyde) {

        gc.strokeOval((venstre + høyre) / 2, høyde, OVAL_SIZE, OVAL_SIZE);
        gc.strokeText(node.element.toString(), (venstre + høyre) / 2 + TEXT_X_OFFSET, høyde + TEXT_Y_OFFSET);
        gc.setStroke(Color.BLACK);

        System.out.println(node.height);

        if (node.left != null) {
            gc.strokeLine((venstre + høyre) / 2, høyde + LEFT_LINE_START_HEIGHT_OFFSET, (venstre + ((venstre + høyre) / 2)) / 2 + LEFT_LINE_END_WIDTH_OFFSET, høyde + LEFT_LINE_END_HEIGHT_OFFSET);
            drawTree(node.left, venstre, (venstre + høyre) / 2, høyde + H_DIFF);
        }

        if (node.right != null) {
            gc.strokeLine((venstre + høyre) / 2 + RIGHT_LINE_START_WIDTH_OFFSET, høyde + RIGHT_LINE_START_HEIGHT_OFFSET, (høyre + ((venstre + høyre) / 2)) / 2, høyde + RIGHT_LINE_END_HEIGHT_OFFSET);
            drawTree(node.right, (venstre + høyre) / 2, høyre, høyde + H_DIFF);
        }
    }

    // sjekker om det er noe i inputfeltet.
    public boolean gyldig(String s) {
        return !(s.equals(""));
    }
}
