package konishi.java.socketconnection.serialize;

import java.awt.image.BufferedImage;
import java.io.Serializable;

/**
 * BufferedImageクラスをラップし、シリアライズするクラスです。<br>
 * Socketを用いて画像送信を行う場合に、ObjectOutputStream#writeObjectにこのクラスのインスタンスを与えて通信します。<br>
 * シリアライズする理由は、ObjectOutputStreamは直列化されたオブジェクトしか受け取らないためです。<br>
 * ImageクラスからBufferedImageクラスへの変換は複雑であり、Imageクラスを送信するのは効率的ではありません。<br>
 * @author konishi
 *
 */
public class BufferedImageSerializer implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int width, height;
	private int[] pixels;
	
	/**
	 * 受け取ったBufferedImageクラスのインスタンスの情報を収集します。
	 * @param image 送信したいBufferedImageクラスインスタンス
	 */
	public BufferedImageSerializer(BufferedImage image) {
		width = image.getWidth();
		height = image.getHeight();
		pixels = new int[width * height];
		image.getRGB(0, 0, width, height, pixels, 0, width);
	}
	
	/**
	 * BufferedImageクラスのインスタンスを受け取ります。
	 * 画像受信元にて使用します。
	 * @return 送信されたBufferedImageインスタンス
	 */
	public BufferedImage getImage() {
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		image.setRGB(0, 0, width, height, pixels, 0, width);
		return image;
	}
	
}
