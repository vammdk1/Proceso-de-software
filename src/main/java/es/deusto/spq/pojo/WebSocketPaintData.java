package es.deusto.spq.pojo;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class WebSocketPaintData extends WebSocketData {
	int x;
	int y;
	Mode mode;
	
	enum Mode {
		Paint,
		Erase,
	}
	
	protected WebSocketPaintData(int x, int y, Mode mode) {
		super("Paint");
		this.x = x;
		this.y = y;
		this.mode = mode;
	}

	@Override
	public String encode() {
		String modeString = "";
		if (mode == Mode.Paint) {
			modeString = "0";
		} else if (mode == Mode.Erase) {
			modeString = "1";
		}
		
		if (!modeString.equals("")) {
		return "Paint\n"
				+ x + "\n"
				+ y + "\n" +
				modeString;
		} 
		return null;
	}

	public static WebSocketPaintData decodeData(String data) {
		int xLineEnd = data.indexOf("\n");
		int yLineEnd = data.indexOf("\n", data.indexOf("\n") + 1);
		
		int x = Integer.parseInt(data.substring(0, xLineEnd));
		int y = Integer.parseInt(data.substring(xLineEnd + 1, yLineEnd));
		int modeInt= Integer.parseInt(data.substring(yLineEnd + 1, data.length()));
		Mode mode;
		
		if (modeInt == 0) {
			mode = Mode.Paint;
		} else if (modeInt == 1) {
			mode = Mode.Erase;
		} else {
			mode = null;
		}
		
		return new WebSocketPaintData(x, y, mode);
		
	}
	
}
