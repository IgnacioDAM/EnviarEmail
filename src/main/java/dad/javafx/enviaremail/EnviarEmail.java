package dad.javafx.enviaremail;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class EnviarEmail extends Application {

	private Controller controller;

	@Override
	public void start(Stage primaryStage) throws Exception {
		controller = new Controller();

		Scene scene = new Scene(controller.getVista(), 640, 500);
		primaryStage.setTitle("Enviar email");
		primaryStage.setScene(scene);
		primaryStage.getIcons().add(new Image(EnviarEmail.class.getResourceAsStream("/email-send-icon-32x32.png")));
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
