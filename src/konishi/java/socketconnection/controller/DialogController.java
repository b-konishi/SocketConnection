package konishi.java.socketconnection.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import konishi.java.socketconnection.base.ControllerBase;
import konishi.java.socketconnection.model.ReceiveModel;

public class DialogController extends ControllerBase {

	@FXML RadioButton client1_button;
	@FXML RadioButton client2_button;
	@FXML RadioButton client3_button;
	@FXML RadioButton client4_button;
	
	@FXML TextField address_port_text;
	
	@FXML Label error_text;
	
	@FXML Button ok_button;
	@FXML Button cancel_button;
	
	public void initialize() {
		ReceiveModel.selectedClientDialog = 1;
		if (ReceiveModel.errorDialog)
			error_text.setText("Please connect again.");
	}
	
	@Override
	public void handleMouseAction(MouseEvent event) throws Exception {
	}

	@Override
	public void handleButtonAction(ActionEvent event) throws Exception {
		
		switch (getId(event.toString())) {
		case "client1_button":
			ReceiveModel.selectedClientDialog = 1;
			stackTrace();
			break;
		case "client2_button":
			ReceiveModel.selectedClientDialog = 2;
			break;
		case "client3_button":
			ReceiveModel.selectedClientDialog = 3;
			break;
		case "client4_button":
			ReceiveModel.selectedClientDialog = 4;
			break;
			
		case "ok_button":
			ReceiveModel.addressPortDialog = address_port_text.getText();
			((Node)event.getSource()).getScene().getWindow().hide();
			break;
		case "cancel_button":
			System.exit(0);
			break;
		}
		
	}
	
}
