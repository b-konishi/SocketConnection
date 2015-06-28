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

/**
 * クライアント側の初期設定を行うダイアログです。<br>
 * このダイアログでOKボタンが押され、通信が成功したとき、クライアントメイン画面に遷移します。<br>
 * @author konishi
 * @see ClientController
 *
 */
public class DialogController extends ControllerBase {

	@FXML RadioButton client1_button, client2_button, client3_button, client4_button;
	@FXML TextField address_port_text;
	@FXML Label error_text;
	@FXML Button ok_button, cancel_button;
	
	/**
	 * ダイアログ内での初期処理を行います。
	 */
	public void initialize() {
		ReceiveModel.myMachineNumber = "1";
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
			ReceiveModel.myMachineNumber = "1";
			stackTrace();
			break;
		case "client2_button":
			ReceiveModel.myMachineNumber = "2";
			break;
		case "client3_button":
			ReceiveModel.myMachineNumber = "3";
			break;
		case "client4_button":
			ReceiveModel.myMachineNumber = "4";
			break;
			
		case "ok_button":
			ReceiveModel.addressPortDialog = (address_port_text.getText().isEmpty()) ? address_port_text.getPromptText() : address_port_text.getText();
			((Node)event.getSource()).getScene().getWindow().hide();
			break;
		case "cancel_button":
			System.exit(0);
			break;
		}
		
	}
	
}
