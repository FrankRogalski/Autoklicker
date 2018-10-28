package main;

import java.awt.Robot;
import java.awt.event.InputEvent;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class AutoClicker extends Application {
	private Canvas can;
	private GraphicsContext gc;
	private Timeline tl_draw;

	private boolean toggle = false;
	private Robot bot;
	private final int mask = InputEvent.BUTTON1_DOWN_MASK;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void init() throws Exception {
		tl_draw = new Timeline(new KeyFrame(Duration.millis(1000.0 / 60.0), e -> {
			draw();
		}));
		tl_draw.setCycleCount(Timeline.INDEFINITE);
	}

	@Override
	public void start(Stage stage) throws Exception {
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root, 700, 400);

		can = new Canvas(scene.getWidth(), scene.getHeight());
		gc = can.getGraphicsContext2D();
		stage.setTitle("Autoklicker");

		root.setCenter(can);
		// root.setStyle("-fx-background-color: #000000");

		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent key) {
				if (key.getCode() == KeyCode.SPACE) {
					if (toggle) {
						tl_draw.pause();
					} else {
						tl_draw.play();
					}
					toggle = !toggle;
					show();
				}
			}
		});

		stage.setResizable(false);

		stage.setScene(scene);
		stage.show();

		bot = new Robot();
		show();
	}

	private void show() {
		gc.setFill(toggle ? Color.GREEN : Color.RED);
		gc.fillRect(0, 0, can.getWidth(), can.getHeight());
		gc.setFill(Color.BLACK);
		gc.fillText(toggle ? "an" : "aus", can.getWidth() / 2, can.getHeight() / 2);
	}

	private void draw() {
		bot.mousePress(mask);
		bot.mouseRelease(mask);
	}
}