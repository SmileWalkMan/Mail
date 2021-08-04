import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;
//macOS中运行需要添加权限才行： 系统设置偏好-》安全性和隐私-》辅助功能
//windows还没有尝试过
public class ScreenClick {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Point point = MouseInfo.getPointerInfo().getLocation();
			System.out.println(point.x);
			System.out.println(point.y);
			
			Robot rob = new Robot();
//			rob.createScreenCapture(screenRect)
			rob.delay(3000);
			rob.mouseMove(point.x,point.y);
//			rob.mouseMove(300, 300);
			rob.mousePress(InputEvent.BUTTON3_MASK);
			rob.delay(3000);
			rob.mouseRelease(InputEvent.BUTTON3_MASK);
		} catch (AWTException exception) {
			exception.printStackTrace();
		}

	}

}
