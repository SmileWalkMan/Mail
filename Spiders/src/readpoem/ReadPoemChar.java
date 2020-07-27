package readpoem;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
/**
 * 
 * 从baidu上搜索古诗，并打印出来
 * https://hanyu.baidu.com/s?wd=古诗名+古诗&from=poem
 * e.g. 
 * https://hanyu.baidu.com/s?wd=%E5%90%B4%E5%85%B4%E6%9D%82%E8%AF%97+%E5%8F%A4%E8%AF%97&from=poem
 */
public class ReadPoemChar {

	public static void toArrayByFileReader(String name) {
		// 使用ArrayList来存储每行读取到的字符串
		Set<String> strSet= new HashSet<String>();
		try {
			FileReader fr = new FileReader(name);
			BufferedReader bf = new BufferedReader(fr);
			String str = bf.readLine();
			// 按行读取字符串
			String[] charArr=str.trim().split("");
			for(String c :charArr) {
				strSet.add(c);
			}
			System.out.println(strSet);
			bf.close();
			fr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		toArrayByFileReader("readpoem/char.txt");
	}

}
