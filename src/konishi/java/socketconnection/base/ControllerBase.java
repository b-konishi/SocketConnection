package konishi.java.socketconnection.base;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import konishi.java.socketconnection.json.MapCoordinates;
import konishi.java.socketconnection.type.JsonType;

/**
 * Client側・Server側双方に共通する処理をまとめた基底クラスです。
 * 
 * @version 1.0.0
 * @author konishi
 */
abstract public class ControllerBase extends TotalBase {
	
	@FXML public AnchorPane root_map;
	
	protected Thread update = null;
	protected Rectangle r = null;
	protected int mapFrag = 0;
	
	@FXML abstract public void handleMouseAction(MouseEvent event) throws Exception;
	@FXML abstract public void handleButtonAction(ActionEvent event) throws Exception;
	
	/**
	 * MapのベースとなるAnchorPaneを渡します。<br>
	 * これにより、Server側とClient側のMapの同期化が可能となります。
	 * @param _root_map MapのAnchorPane
	 */
	public void setRootMap(AnchorPane _root_map) {
		if (_root_map != null)
			root_map = _root_map;
	}
	
	/**
	 * JSON形式で、ArrayListに格納します。
	 * @return JSON形式のリスト
	 */
	public ArrayList<JsonType> manageMapCoordinates() {
		MapCoordinates map = new MapCoordinates(mapFrag, r.getX(),r.getY());
		ArrayList<JsonType> list = new ArrayList<JsonType>();
		list.add(map);
		return list;
	}
	
	/**
	 * 座標変換を行います。
	 * @param list JSON形式のリスト
	 */
	public void convertCoordinates(ArrayList<JsonType> list) {
		if (list == null)	return;
		
		MapCoordinates map = (MapCoordinates) list.get(0);
		System.out.println("convertCoordinate");
		System.out.println(map.itemX + "  " + map.itemY);
		drawFigure(map.itemID, map.itemX, map.itemY);
	}
	
	/**
	 * 図形を描画します。
	 * @param id アイテムのID
	 * @param x X座標
	 * @param y Y座標
	 */
	public void drawFigure(int id, double x, double y) {	
		r = new Rectangle(10, 10);
		r.setX(x - r.getWidth()/2);
		r.setY(y - r.getHeight()/2);
		switch (id) {
		case 1:
			r.setFill(Color.RED);
			break;
		case 2:
			r.setFill(Color.BLACK);
			break;
		case 3:
			r.setFill(Color.LIGHTGREEN);
			break;
		}
		root_map.getChildren().add(r);
	}
	
	/**
	 * javafx.event.ActionEventからtoStringされた文字列のIDのみを取得します。<br>
	 * 内部での取得方式: "=id"に続き、","で終わる間の文字列を取得
	 * @param eventString ActionEventのtoStringされた文字列
	 * @return ID名
	 */
	public String getId(String eventString) {
		String idName = "";
		String regex = "id=(.*),";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(eventString);
		if(m.find()) idName = m.group(1);
		
		return idName;
	}
	
	/**
	 * 2進数を10進数へ変換します。<br>
	 * ビットフラグに使用します。
	 * @param bin 2進数フラグ
	 * @return 10進数変換値
	 */
	public int toDecimal(int bin) {
		return Integer.parseInt(String.valueOf(bin), 2);
	}
}
