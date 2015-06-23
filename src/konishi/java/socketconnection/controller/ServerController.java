package konishi.java.socketconnection.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javafx.animation.AnimationTimer;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import javax.imageio.ImageIO;

import konishi.java.socketconnection.base.ControllerBase;
import konishi.java.socketconnection.main.TransmitServer;
import konishi.java.socketconnection.model.ReceiveModel;
import konishi.java.socketconnection.model.StoreData;

/**
 * サーバー側のコントローラーです。
 * 
 * @version 1.0.0
 * @author konishi
 * @see ClientController
 */
public class ServerController extends ControllerBase {
	
	@FXML public AnchorPane root_map;
	
	@FXML public Button connection_button;
	@FXML public Button disconnection_button;
	
	@FXML public Tab receive_image_tab1;
	@FXML public Tab receive_image_tab2;
	@FXML public Tab receive_image_tab3;
	@FXML public Tab receive_image_tab4;
	
	@FXML public ImageView receive_image1;
	@FXML public ImageView receive_image2;
	@FXML public ImageView receive_image3;
	@FXML public ImageView receive_image4;
	
	@FXML public Button front_camera1_button;
	@FXML public Button back_camera1_button;
	@FXML public Button arm_camera1_button;
	
	@FXML public Button front_camera2_button;
	@FXML public Button back_camera2_button;
	@FXML public Button arm_camera2_button;
	
	@FXML public Button front_camera3_button;
	@FXML public Button back_camera3_button;
	@FXML public Button arm_camera3_button;
	
	@FXML public Button front_camera4_button;
	@FXML public Button back_camera4_button;
	@FXML public Button arm_camera4_button;
	
	@FXML public Rectangle grid00_rect1;
	@FXML public Rectangle grid01_rect1;
	@FXML public Rectangle grid02_rect1;
	@FXML public Rectangle grid10_rect1;
	@FXML public Rectangle grid11_rect1;
	@FXML public Rectangle grid12_rect1;
	@FXML public Rectangle grid20_rect1;
	@FXML public Rectangle grid21_rect1;
	@FXML public Rectangle grid22_rect1;
	
	@FXML public Rectangle grid00_rect2;
	@FXML public Rectangle grid01_rect2;
	@FXML public Rectangle grid02_rect2;
	@FXML public Rectangle grid10_rect2;
	@FXML public Rectangle grid11_rect2;
	@FXML public Rectangle grid12_rect2;
	@FXML public Rectangle grid20_rect2;
	@FXML public Rectangle grid21_rect2;
	@FXML public Rectangle grid22_rect2;
	
	@FXML public Rectangle grid00_rect3;
	@FXML public Rectangle grid01_rect3;
	@FXML public Rectangle grid02_rect3;
	@FXML public Rectangle grid10_rect3;
	@FXML public Rectangle grid11_rect3;
	@FXML public Rectangle grid12_rect3;
	@FXML public Rectangle grid20_rect3;
	@FXML public Rectangle grid21_rect3;
	@FXML public Rectangle grid22_rect3;
	
	@FXML public Rectangle grid00_rect4;
	@FXML public Rectangle grid01_rect4;
	@FXML public Rectangle grid02_rect4;
	@FXML public Rectangle grid10_rect4;
	@FXML public Rectangle grid11_rect4;
	@FXML public Rectangle grid12_rect4;
	@FXML public Rectangle grid20_rect4;
	@FXML public Rectangle grid21_rect4;
	@FXML public Rectangle grid22_rect4;
	
	@FXML public Label weight1_label;
	@FXML public Label weight2_label;
	@FXML public Label weight3_label;
	@FXML public Label weight4_label;
	@FXML public Label color1_label;
	@FXML public Label color2_label;
	@FXML public Label color3_label;
	@FXML public Label color4_label;
	@FXML public Label frequency1_label;
	@FXML public Label frequency2_label;
	@FXML public Label frequency3_label;
	@FXML public Label frequency4_label;
	@FXML public Label message1_label;
	@FXML public Label message2_label;
	@FXML public Label message3_label;
	@FXML public Label message4_label;
	
	
	@FXML public Button submit1_button;
	@FXML public Button submit2_button;
	@FXML public Button submit3_button;
	@FXML public Button submit4_button;
	
	@FXML public ImageView robot_map_button;
	@FXML public ImageView doll_map_button;
	@FXML public ImageView rubble1_map_button;
	@FXML public ImageView rubble2_map_button;
	@FXML public ImageView dust_map_button;
	
	@FXML public Button shutdown_button;
	
	@FXML public Label ip_adress;
	@FXML public Label ip_adress1;
	@FXML public Label ip_adress2;
	@FXML public Label ip_adress3;
	@FXML public Label ip_adress4;
	
//	private static final String MAP_FILE = StoreData.SERVER_MAP_FILE;
	
	private TransmitServer server = null;
	private AnimationTimer timer = null;
				
	public void initialize() {
		StoreData.LOCAL = StoreData.SERVER;
		StoreData.DEFAULT_MAP_ITEM_SIZE = StoreData.SERVER_ICON_SIZE;
		
		Rectangle[][] grid = {{grid00_rect1, grid01_rect1, grid02_rect1, grid10_rect1, grid11_rect1, grid12_rect1, grid20_rect1, grid21_rect1, grid22_rect1},
				{grid00_rect2, grid01_rect2, grid02_rect2, grid10_rect2, grid11_rect2, grid12_rect2, grid20_rect2, grid21_rect2, grid22_rect2},
				{grid00_rect3, grid01_rect3, grid02_rect3, grid10_rect3, grid11_rect3, grid12_rect3, grid20_rect3, grid21_rect3, grid22_rect3},
				{grid00_rect4, grid01_rect4, grid02_rect4, grid10_rect4, grid11_rect4, grid12_rect4, grid20_rect4, grid21_rect4, grid22_rect4}};
		
		ImageView[] images = {robot_map_button, doll_map_button, rubble1_map_button, rubble2_map_button, dust_map_button};
		ImageView[] image = {receive_image1, receive_image2, receive_image3, receive_image4};
		Tab[] tab = {receive_image_tab1, receive_image_tab2, receive_image_tab3, receive_image_tab4};
		
		Label[] weight = {weight1_label, weight2_label, weight3_label, weight4_label};
		Label[] color = {color1_label, color2_label, color3_label, color4_label};
		Label[] frequency = {frequency1_label, frequency2_label, frequency3_label, frequency4_label};
		Label[] message = {message1_label, message2_label, message3_label, message4_label};
		
		for (int i = 0; i < images.length; i++) {
			ReceiveModel.imageViews[i] = images[i];
		}
		
		timer = new AnimationTimer() {	
			@Override
			public void handle(long now) {
				portPrinter();
				mapPainter(StoreData.DEFAULT_MAP_ITEM_SIZE, StoreData.DEFAULT_MAP_ITEM_SIZE);
				
				if (ReceiveModel.isSendedImage) {
					try {
						BufferedImage readImage = ImageIO.read(ReceiveModel.image);
						WritableImage im = SwingFXUtils.toFXImage(readImage, null);
						
						if (!ReceiveModel.imageMachineNumber.contains(ReceiveModel.myMachineNumber)) {
							image[ReceiveModel.imageMachineNumber.size()].setImage(im);
							tab[ReceiveModel.imageMachineNumber.size()].setText("受信画像(From " + ReceiveModel.myMachineNumber + "号機)");
							ReceiveModel.imageMachineNumber.add(ReceiveModel.myMachineNumber);
						} else {
							int num;
							image[(num = ReceiveModel.imageMachineNumber.indexOf(ReceiveModel.myMachineNumber))].setImage(im);
							tab[num].setText("受信画像(From " + ReceiveModel.myMachineNumber + "号機)");
						}
					} catch (IOException e) {
						errorStackTrace(e);
					}
					ReceiveModel.isSendedImage = false;
				}
				
				if (ReceiveModel.isSendedGrid) {
					for (int i = 0; i < 9; i++) {
						if (ReceiveModel.gridData.charAt(i) == '1') {
							grid[Integer.parseInt(ReceiveModel.myMachineNumber)-1][i].setFill(Color.BLACK);
						}
					}
					ReceiveModel.isSendedGrid = false;
				}
				
				if (ReceiveModel.weightFlag) {
					weight[Integer.parseInt(ReceiveModel.myMachineNumber)-1].setText(ReceiveModel.weightData);
					ReceiveModel.weightFlag = false;
				} else if (ReceiveModel.colorFlag) {
					color[Integer.parseInt(ReceiveModel.myMachineNumber)-1].setText(ReceiveModel.colorData);
					ReceiveModel.colorFlag = false;
				} else if (ReceiveModel.frequencyFlag) {
					frequency[Integer.parseInt(ReceiveModel.myMachineNumber)-1].setText(ReceiveModel.frequencyData);
					ReceiveModel.frequencyFlag = false;
				} else if (ReceiveModel.messageFlag) {
					message[Integer.parseInt(ReceiveModel.myMachineNumber)-1].setText(ReceiveModel.messageData);
					ReceiveModel.messageFlag = false;
				}
			}
		};
		timer.start();
	}
	
	/**
	 * connectボタンを押した際の初期処理です。
	 * #initializeとは違うことに注意してください。
	 * @throws Exception
	 */
	public void firstConnection() throws Exception {
		stackTrace(StoreData.PORT);
		server = new TransmitServer();
		stackTrace();
		setRootMap(root_map);
		
		ip_adress.setText(server.getPort());
	}
	
	/**
	 * サーバー画面のポート表示を管理します。
	 * クライアントが切断された時、自動的に対象から外します。
	 */
	public void portPrinter() {
		Label[] clientAdress = {ip_adress1, ip_adress2, ip_adress3, ip_adress4};
		
		while (clientValue > ReceiveModel.clientValue) {
			clientAdress[--clientValue].setText(StoreData.UNSET);
		}
		clientValue = ReceiveModel.clientValue;
		for (int i = 0; i < clientValue; i++) {
			if (clientAdress[i].getText().equals(StoreData.UNSET)) {
				clientAdress[i].setText(ReceiveModel.clientAdress.get(i));
			}
		}
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
			swing *= -1;
			if (mapFrag != 0 && ReceiveModel.clientValue >= 0) {
				int x = (int)event.getX() + swing;
				int y = (int)event.getY() + swing;
				
				if (x < StoreData.DEFAULT_MAP_ITEM_SIZE/2 || x > StoreData.SERVER_MAP_SIZE || y < StoreData.DEFAULT_MAP_ITEM_SIZE/2 || y > StoreData.SERVER_MAP_SIZE-100)
					break;
				
				ReceiveModel.data = stringMapEventAgent("0000", mapFrag, x, y);
				ReceiveModel.isUpdated = true;
			}
			break;
		}
	}
	
	/**
	 * ボタンアクションを読み取ります。
	 * @param event イベントアクション
	 * @throws Exception エラー
	 */
	@FXML public void handleButtonAction(ActionEvent event) throws Exception {
		switch (getId(event.toString())) {
		case "connection_button":
			connection_button.setText(StoreData.IN_CONNECTION);
			firstConnection();
			clientValue = 0;
			break;
		case "disconnection_button":
			connection_button.setText(StoreData.CONNECTED);
			ReceiveModel.emergencyStopSignal = true;
			stackTrace(clientValue);
			if (ReceiveModel.clientValue == 0)
				server.closeTransport();
			break;
			
		case "front_camera1_button":
			break;
		case "back_camera1_button":
			break;
		case "arm_camera1_button":
			break;
			
		case "front_camera2_button":
			break;
		case "back_camera2_button":
			break;
		case "arm_camera2_button":
			break;
			
		case "front_camera3_button":
			break;
		case "back_camera3_button":
			break;
		case "arm_camera3_button":
			break;
			
		case "front_camera4_button":
			break;
		case "back_camera4_button":
			break;
		case "arm_camera4_button":
			break;
			
		case "submit1_button":
			break;
		case "submit2_button":
			break;
		case "submit3_button":
			break;
		case "submit4_button":
			break;
			
		case "shutdown_button":
			if (ReceiveModel.clientValue == 0)
				System.exit(0);
			ReceiveModel.shutdownSignal = true;
			break;
		}
	}
}
