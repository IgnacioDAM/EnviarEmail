package dad.javafx.enviaremail;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.util.converter.NumberStringConverter;

public class Controller implements Initializable {

	// Vista
	@FXML
	private BorderPane vista;

	@FXML
	private TextField nombreText;

	@FXML
	private TextField puertoText;

	@FXML
	private TextField emailRemText;

	@FXML
	private PasswordField contraseñaField;

	@FXML
	private TextField emailDestText;

	@FXML
	private TextField asuntoText;

	@FXML
	private CheckBox conexionCheck;

	@FXML
	private TextArea mensajeArea;

	@FXML
	private Button enviarButton;

	@FXML
	private Button vaciarButton;

	@FXML
	private Button cerrarButton;

	// Model
	private Model model = new Model();

	public Controller() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/VistaEnviarEmail.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Bindings.bindBidirectional(model.nombreProperty(), nombreText.textProperty());
		Bindings.bindBidirectional(puertoText.textProperty(), model.puertoProperty(), new NumberStringConverter());
		Bindings.bindBidirectional(model.sslProperty(), conexionCheck.selectedProperty());
		Bindings.bindBidirectional(model.remitenteProperty(), emailRemText.textProperty());
		Bindings.bindBidirectional(model.passwdProperty(), contraseñaField.textProperty());
		Bindings.bindBidirectional(model.destinatarioProperty(), emailDestText.textProperty());
		Bindings.bindBidirectional(model.asuntoProperty(), asuntoText.textProperty());
		Bindings.bindBidirectional(model.mensajeProperty(), mensajeArea.textProperty());
	}

	@FXML
	public void onEnviarAction(ActionEvent e) {
		Email email = new SimpleEmail();

		try {
			email.setHostName(model.getNombre());
			email.setSmtpPort(model.getPuerto());
			email.setAuthenticator(new DefaultAuthenticator(model.getRemitente(), model.getPasswd()));
			email.setSSLOnConnect(model.isSsl());
			email.setFrom(model.getRemitente());
			email.setSubject(model.getAsunto());
			email.setMsg(model.getMensaje());
			email.addTo(model.getDestinatario());
			email.send();

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Mensaje enviado");
			alert.setHeaderText("Mensaje enviado con éxito a '" + model.getDestinatario() + "'");

			alert.showAndWait();
		} catch (EmailException e1) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("No se pudo enviar el email.");
			alert.setContentText(e1.getLocalizedMessage());

			alert.showAndWait();

			e1.printStackTrace();
		}
	}

	@FXML
	public void onVaciarAction(ActionEvent e) {
		model.setNombre("");
		model.setPuerto(0);
		model.setSsl(false);
		model.setRemitente("");
		model.setPasswd("");
		model.setDestinatario("");
		model.setAsunto("");
		model.setMensaje("");
	}

	@FXML
	public void onCerrarAction(ActionEvent e) {
		Platform.exit();
	}

	public BorderPane getVista() {
		return vista;
	}

	public void setPrincipalPane(BorderPane vista) {
		this.vista = vista;
	}

}
