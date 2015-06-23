package konishi.java.socketconnection.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.imageio.ImageIO;

import konishi.java.socketconnection.base.ControllerBase;
import konishi.java.socketconnection.main.TransmitClient;
import konishi.java.socketconnection.model.ReceiveModel;
import konishi.java.socketconnection.model.StoreData;

/**
 * クライアント側のコントローラーです。
 * 
 * @version 1.0.0
 * @author konishi
 * @see ServerController
 */
public class ClientController extends ControllerBase {
	
	@FXML public AnchorPane root_map;
	
	@FXML public Button connection_button;
	@FXML public Button disconnection_button;

	@FXML public Label machine_number;

	@FXML public ImageView camera_image;
	
	@FXML public Button front_camera_button;
	@FXML public Button back_camera_button;
	@FXML public Button arm_camera_button;
	
	@FXML public ImageView robot_map_button;
	@FXML public ImageView doll_map_button;
	@FXML public ImageView rubble1_map_button;
	@FXML public ImageView rubble2_map_button;
	@FXML public ImageView dust_map_button;
	
	@FXML public Button l2_controller_button;
	@FXML public Button l1_controller_button;
	@FXML public Button r2_controller_button;
	@FXML public Button r1_controller_button;
	@FXML public Button right_controller_button;
	@FXML public Button left_controller_button;
	@FXML public Button up_controller_button;
	@FXML public Button down_controller_button;
	@FXML public Button circle_controller_button;
	@FXML public Button cross_controller_button;
	@FXML public Button triangle_controller_button;
	@FXML public Button rectangle_controller_button;
	
	@FXML public Rectangle grid00_rect;
	@FXML public Rectangle grid01_rect;
	@FXML public Rectangle grid02_rect;
	@FXML public Rectangle grid10_rect;
	@FXML public Rectangle grid11_rect;
	@FXML public Rectangle grid12_rect;
	@FXML public Rectangle grid20_rect;
	@FXML public Rectangle grid21_rect;
	@FXML public Rectangle grid22_rect;
	
	@FXML public Button grid_send_button;
	
	@FXML public TextField weight_text;
	@FXML public TextField color_text;
	@FXML public TextField frequency_text;
	@FXML public TextField message_text;
	@FXML public Button weight_button;
	@FXML public Button color_button;
	@FXML public Button frequency_button;
	@FXML public Button submit_button;
	
	@FXML public Button shutdown_button;
	
	@FXML public Label ip_adress;
	@FXML public Label emergency_stop_signal;
	
	@FXML public TextField input_server_adress;
	
	private static final int CLIENT_MAP_ITEM_SIZE = StoreData.DEFAULT_MAP_ITEM_SIZE / StoreData.MAP_MAGNIFICATION;
	
	private TransmitClient client = null;
	
	public void initialize() {
		StoreData.LOCAL = StoreData.CLIENT;
		StoreData.DEFAULT_MAP_ITEM_SIZE = StoreData.CLIENT_ICON_SIZE;
		
		ReceiveModel.defaultGridColor = grid00_rect.getFill();
		
		try {
			do {
				// 確認ダイアログの表示
				FXMLLoader loader = new FXMLLoader(getClass().getResource("dialog.fxml"));
				loader.load();
				Parent root = loader.getRoot();
				Scene scene = new Scene(root);
				Stage confirmDialog = new Stage(StageStyle.UTILITY);
				confirmDialog.setScene(scene);
				// サイズ変更不可
				confirmDialog.setResizable(false);
				confirmDialog.setTitle("Select an Option");
				// ダイアログが閉じるまでブロックされる
				confirmDialog.showAndWait();
	
	            stackTrace(ReceiveModel.myMachineNumber + " " + ReceiveModel.addressPortDialog);
	            machine_number.setText(ReceiveModel.myMachineNumber + "号機");
	            if (ReceiveModel.addressPortDialog.contains(":")) {
	            	try {
						ArrayList<String> text = stringSeparator(ReceiveModel.addressPortDialog, ":");
						StoreData.HOST_NAME = text.get(0);
						StoreData.PORT = Integer.parseInt(text.get(1));
	            	} catch(Exception e) {}
	            }
			} while (!firstConnection());

        } catch (Exception e) {
        	System.exit(0);
        }
		
		
		
		ImageView[] images = {robot_map_button, doll_map_button, rubble1_map_button, rubble2_map_button, dust_map_button};
		for (int i = 0; i < images.length; i++) {
			ReceiveModel.imageViews[i] = images[i];
		}
		
		AnimationTimer timer = new AnimationTimer() {	
			@Override
			public void handle(long now) {
				if (!ReceiveModel.emergencyStopSignal) {
					mapPainter(CLIENT_MAP_ITEM_SIZE, CLIENT_MAP_ITEM_SIZE);
				} else {
					emergencyDisposal();
				}
			}
		};
		timer.start();
	}
	
	/**
	 * 初期化処理を記述するメソッドです。
	 */
	public boolean firstConnection() throws Exception {
		try {
			emergency_stop_signal.setText("");
			connection_button.setText(StoreData.IN_CONNECTION);
			emergency_stop_signal.setText(StoreData.SERVER_NOMAL_CONNECTION);
			
			client = new TransmitClient();
			setRootMap(root_map);
			ip_adress.setText(client.getPort());
		} catch (Exception e) {
			emergency_stop_signal.setText(StoreData.SERVER_NOT_CONNECTED);
			connection_button.setText(StoreData.CONNECTED);
			ReceiveModel.errorDialog = true;
			return false;
		}
		return true;
	}
	
	/**
	 * 緊急時の処理をおこないます。
	 * 緊急時とは、サーバーの通信が途絶えた時、緊急時ストップシグナルフラグが立ち、
	 * 処理をおこなわれます。
	 */
	public void emergencyDisposal() {
		stackTrace();
		emergency_stop_signal.setText(StoreData.SERVER_STOPPED);
		connection_button.setText(StoreData.CONNECTED);
		client.write("CLOSE");
		ReceiveModel.emergencyStopSignal = false;
		try {
			client.closeTransport();
		} catch (IOException e) {}
	}
	
	/**
	 * マウスクリック時の動作を定義します。
	 * @param event マウスクリック時に受け取るイベント
	 */
	@FXML public void handleMouseAction(MouseEvent event) throws Exception {
		switch (getId(event.toString())) { 
		case "robot_map_button":
			iconOpacity(robot_map_button, mapFrag);
			if (mapFrag != 1) {
				mapFrag = 0;
			}
			mapFrag ^= 1;
			break;
		case "doll_map_button":
			iconOpacity(doll_map_button, mapFrag);
			if (mapFrag != 2) {
				mapFrag = 0;
			}
			mapFrag ^= 2;
			break;
		case "rubble1_map_button":
			iconOpacity(rubble1_map_button, mapFrag);
			if (mapFrag != 3) {
				mapFrag = 0;
			}
			mapFrag ^= 3;
			break;
		case "rubble2_map_button":
			iconOpacity(rubble2_map_button, mapFrag);
			if (mapFrag != 4) {
				mapFrag = 0;
			}
			mapFrag ^= 4;
			break;
		case "dust_map_button":
			iconOpacity(dust_map_button, mapFrag);
			if (mapFrag != 5) {
				mapFrag = 0;
			}
			mapFrag ^= 5;
			break;
			
		case "root_map":
			if (mapFrag != 0) {
				int x = (int)event.getX() + swing;
				int y = (int)event.getY() + swing;
				
				if (x < StoreData.DEFAULT_MAP_ITEM_SIZE/2 || x > StoreData.CLIENT_MAP_SIZE || y < StoreData.DEFAULT_MAP_ITEM_SIZE/2 || y > StoreData.CLIENT_MAP_SIZE-50)
					break;
				
				ReceiveModel.data = stringMapEventAgent(StoreData.CLIENT_PORT, mapFrag, x, y);
				stackTrace(ReceiveModel.data);
				try {
					client.write(ReceiveModel.data);
				} catch (NullPointerException e) {}
				ReceiveModel.isUpdated = true;
			}
			break;
			
		case "grid00_rect":
			setGridColor(grid00_rect);
			break;
		case "grid01_rect":
			setGridColor(grid01_rect);
			break;
		case "grid02_rect":
			setGridColor(grid02_rect);
			break;
		case "grid10_rect":
			setGridColor(grid10_rect);
			break;
		case "grid11_rect":
			setGridColor(grid11_rect);
			break;
		case "grid12_rect":
			setGridColor(grid12_rect);
			break;
		case "grid20_rect":
			setGridColor(grid20_rect);
			break;
		case "grid21_rect":
			setGridColor(grid21_rect);
			break;
		case "grid22_rect":
			setGridColor(grid22_rect);
			break;
		
		default :
			
		}
	}
	
	public void setGridColor(Rectangle r) {
		if (r.getFill() != Color.BLACK)
			r.setFill(Color.BLACK);
		else
			r.setFill(ReceiveModel.defaultGridColor);
	}

	@FXML public void handleKeyAction(KeyEvent event) {
		if (KeyCode.ENTER.equals(event.getCode())) {
			ArrayList<String> text = stringSeparator(input_server_adress.getText(), ":");
			
			StoreData.HOST_NAME = text.get(0);
			StoreData.PORT = Integer.parseInt(text.get(1));
			
		}
	}
	public String getGridData(Rectangle... r) {
		StringBuilder sb = new StringBuilder();
		for (int i=0; i < r.length; i++) {
			sb.append((r[i].getFill().equals(ReceiveModel.defaultGridColor)) ? "0" : "1");
		}
		return new String(sb);
	}
	
	/**
	 * ボタンアクションを読み取ります。
	 * @param event イベントアクション
	 * @throws Exception エラー
	 */
	@FXML public void handleButtonAction(ActionEvent event) throws Exception {	
		switch (getId(event.toString())) { 
		case "connection_button":
			firstConnection();
			break;
		case "disconnection_button":
			connection_button.setText(StoreData.CONNECTED);
			client.closeTransport();
			break;
			
		case "front_camera_button":
			FileChooser fc = new FileChooser();
			try {
				File importFile = fc.showOpenDialog(null);
				BufferedImage readImage = ImageIO.read(importFile);
				WritableImage im = SwingFXUtils.toFXImage(readImage, null);
				camera_image.setImage(im);
				if (importFile != null) {
					ReceiveModel.image = importFile;
					ReceiveModel.isSendedImage = true;
				}
			} catch (Exception e) {}
			break;
		case "back_camera_button":
			
			break;
		case "arm_camera_button":
			break;
		
		case "l2_controller_button":
			break;
		case "l1_controller_button":
			break;
		case "r2_controller_button":
			break;
		case "r1_controller_button":
			break;
		case "right_controller_button":
			break;
		case "left_controller_button":
			break;
		case "up_controller_button":
			break;
		case "down_controller_button":
			break;
		case "circle_controller_button":
			break;
		case "cross_controller_button":
			break;
		case "triangle_controller_button":
			break;
		case "rectangle_controller_button":
			break;

		case "grid_send_button":
			ReceiveModel.gridData = getGridData(grid00_rect, grid01_rect, grid02_rect,
										grid10_rect, grid11_rect, grid12_rect,
										grid20_rect, grid21_rect, grid22_rect);
			ReceiveModel.isSendedGrid = true;
			break;
		
		case "weight_button":
			client.write("MESSAGE:"+ReceiveModel.myMachineNumber+":"+"WEIGHT:"+weight_text.getText());
			break;
		case "color_button":
			client.write("MESSAGE:"+ReceiveModel.myMachineNumber+":"+"COLOR:"+color_text.getText());
			break;
		case "frequency_button":
			client.write("MESSAGE:"+ReceiveModel.myMachineNumber+":"+"FREQUENCY:"+frequency_text.getText());
			break;
		case "submit_button":
			client.write("MESSAGE:"+ReceiveModel.myMachineNumber+":"+"MESSAGE:"+message_text.getText());
			break;
		
		case "shutdown_button":
			try {
				client.closeTransport();
			} finally {
				System.exit(0);
			}
			break;
		
		}
	}
}
