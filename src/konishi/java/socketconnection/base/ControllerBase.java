package konishi.java.socketconnection.base;

import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import konishi.java.socketconnection.model.ReceiveModel;
import konishi.java.socketconnection.model.StoreData;

/**
 * Client側・Server側双方に共通する処理をまとめた基底クラスです。
 * 
 * @version 1.0.0
 * @author konishi
 */
abstract public class ControllerBase extends TotalBase {
	
	@FXML public AnchorPane root_map;
	
	protected Thread update = null;
	protected int mapFrag = 0;
	
	protected String[] imageName = {"rescue.gif", "doll.gif", "rubble1.png", "rubble2.png"};
	protected ImageView image = null;
	
	protected ArrayList<Integer> coordinate = new ArrayList<>();
	
	protected int clientValue = 0;
	
	protected int swing = 1;
	
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
	
	public String stringMapEventAgent(String kind, int map, int x, int y) {
		return (kind + ":" + map + ":" + x + ":" + y);
	}
	
	/**
	 * マップにアイコンを描画します。<br>
	 * ReceiveModel.dataには、そのデータが自身のアプリケーションで行われたアクションであるか<br>
	 * リモートで送信されてきた情報であるかを判別するためのデータも含まれているため、<br>
	 * それを分別しています。
	 * @param w アイコンの横幅
	 * @param h	アイコンの縦幅
	 */
	public void mapPainter(int w, int h) {
		if (ReceiveModel.isUpdated) {
			ArrayList<String> list = stringSeparator(ReceiveModel.data, ":");
			
			String kind = list.get(0);
			list.remove(0);
			coordinate = parseIntList(list);
			drawFigure(kind, coordinate.get(0), coordinate.get(1), coordinate.get(2), w, h);
			ReceiveModel.isUpdated = false;
		}
	}

	/**
	 * 図形を描画します。
	 * @param id アイテムのID
	 * @param x X座標
	 * @param y Y座標
	 */
	public void drawFigure(String kind, int id, double _x, double _y, int w, int h) {
		
		if(id != 5) {
			image = new ImageView("image/"+imageName[id-1]);
				if (!StoreData.LOCAL.equals(StoreData.SERVER)) {
					image.setFitWidth(StoreData.CLIENT_ICON_SIZE);
					image.setFitHeight(StoreData.CLIENT_ICON_SIZE);
				} else {
					image.setFitWidth(StoreData.SERVER_ICON_SIZE);
					image.setFitHeight(StoreData.SERVER_ICON_SIZE);
				}
		}
		
		int x = (int)_x;
		int y = (int)_y;
				
		final int MAG = StoreData.MAP_MAGNIFICATION;
		
		if (StoreData.LOCAL.equals(StoreData.SERVER)) {
			if (!kind.equals("0000")) {
				x *= MAG;
				y *= MAG;
			}
		} else {
			if (kind.equals("0000")) {
				x /= MAG;
				y /= MAG;
			} else if (("_"+StoreData.CLIENT_PORT).equals(kind)) {
				return;
			}
		}
		
		stackTrace(id + " " + x + " " + y);
		
		final int HALF_AREA = StoreData.DEFAULT_MAP_ITEM_SIZE /2;
		ArrayList<int[]> item = ReceiveModel.mapItem;
		
		int[] pointer = {id, x, y};
		if (id != 5)
			item.add(pointer);
		
		switch (id) {
		case 1:
		case 2:
		case 3:
		case 4:
			image.setX(x - image.getFitWidth()/2);
			image.setY(y - image.getFitHeight()/2);
			root_map.getChildren().add(image);
			break;
		case 5:
			boolean xJudge, yJudge;
			for (int i = 0; i < item.size(); i++) {
				xJudge = ((item.get(i)[1] + HALF_AREA) > x) && ((item.get(i)[1] - HALF_AREA) < x);
				yJudge = ((item.get(i)[2] + HALF_AREA) > y) && ((item.get(i)[2] - HALF_AREA) < y);
				
				if (xJudge && yJudge) {
					item.remove(i);
					try {
						stackTrace("erase");
						root_map.getChildren().remove(i+1);
					} catch (IndexOutOfBoundsException e) {
						e.printStackTrace();
					}
					break;
				}
			}
			break;
		}
		ReceiveModel.mapItem = item;
	}
	
	/**
	 * iconが現在選択中であるかないかをiconの明暗によって区別します。
	 * @param image クリックしたicon
	 * @param flag 現在のmapFrag
	 */
	protected void iconOpacity(ImageView image, int flag) {
		boolean reverse = false;
		ImageView[] imageViews = new ImageView[ReceiveModel.imageViews.length];
		for (int i = 0; i < imageViews.length; i++) {
			imageViews[i] = ReceiveModel.imageViews[i];
		}
		for (int i = 0; i < imageViews.length; i++) {
			imageViews[i].setOpacity(1);
			if (imageViews[i] == image && i+1 == flag) {
				reverse = true;
			}
		}
		if (!reverse)
			image.setOpacity(0.5);
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
		
		StringTokenizer st = new StringTokenizer(idName, ",");
		if (st.hasMoreTokens())
			idName = st.nextToken();
		if (idName.endsWith("]"))
			idName = idName.replace("]", "");
		
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
	
	/**
	 * JSON形式で、ArrayListに格納します。
	 * @return JSON形式のリスト
	 */
//	public ArrayList mapCoordinateManager() {
//		MapCoordinates map = new MapCoordinates(mapFrag, (int)r.getX(), (int)r.getY());
//		ArrayList list = new ArrayList();
//		list.add(map);
//		return list;
//	}
	
	/**
	 * 座標変換を行います。
	 * @param list JSON形式のリスト
	 */
//	public void convertCoordinates(ArrayList list) {
//		if (list == null)	return;
//		
//		MapCoordinates map = (MapCoordinates) list.get(list.size()-1);
////		System.out.println("convertCoordinate");
////		System.out.println(map.itemX + "  " + map.itemY);
//		drawFigure(map.itemID, map.itemX, map.itemY);
//	}
	
	/**
	 * 乱数生成機です。
	 * 前回と同じ値を出力しません。
	 * @return 乱数
	 */
//	protected int notSameNumberGenerator() {
//		int clientValue = 0;
//		int diffSwing, beforeDiffSwing = 100;
//		
//		diffSwing = (new Random().nextInt(3))-1;
//		if (diffSwing == beforeDiffSwing) {
//			diffSwing = notSameNumberGenerator();
//		}
//		beforeDiffSwing = diffSwing;
//		return diffSwing;
//	}
	
}
