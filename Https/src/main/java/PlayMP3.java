import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

import javazoom.jl.player.Player;

public class PlayMP3 {

	public static Player player = null;
	public static void play(String filename)
    {
        try{
        	try {
    			//声明一个File对象
    			File mp3 = new File(filename);
    			//创建一个输入流
    			FileInputStream fileInputStream = new FileInputStream(mp3);
    			//创建一个缓冲流
    			BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
    			//创建播放器对象，把文件的缓冲流传入进去
    			player = new Player(bufferedInputStream);
    			//调用播放方法进行播放
    			player.play();
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
        }catch(Exception e){
         System.out.println(e); 
        }
    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		play("win.mp3");
		
	}

}
