package readpoem;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
/**
 * 
 * 从baidu上搜索古诗，并打印出来
 * https://hanyu.baidu.com/s?wd=古诗名+古诗&from=poem
 * e.g. 
 * https://hanyu.baidu.com/s?wd=%E5%90%B4%E5%85%B4%E6%9D%82%E8%AF%97+%E5%8F%A4%E8%AF%97&from=poem
 */
public class ReadPoem {

	public static ArrayList<String> toArrayByFileReader(String name) {
		// 使用ArrayList来存储每行读取到的字符串
		ArrayList<String> arrayList = new ArrayList<>();
		try {
			FileReader fr = new FileReader(name);
			BufferedReader bf = new BufferedReader(fr);
			String str;
			// 按行读取字符串
			while ((str = bf.readLine()) != null) {
				arrayList.add(str.trim());
			}
			bf.close();
			fr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return arrayList;
	}

	public static String urlEncodeURL(String url) {
        try {
            String result = URLEncoder.encode(url, "UTF-8");
            result = result.replaceAll("%3A", ":").replaceAll("%2F", "/").replaceAll("\\+", "%20");//+实际上是 空格 url encode而来
            return result;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
	public static ArrayList<String> gsArrNotFind=new ArrayList<String>();
	public static void outputGS() {
		// TODO Auto-generated method stub
//		ArrayList<String> gsArr= toArrayByFileReader("readpoem/poemTest.txt");

		ArrayList<String> gsArr= toArrayByFileReader("readpoem/poem.txt");
		System.out.println(gsArr);
		int num =1;
		int	tryTime=1;
		for(int i=0;i<gsArr.size();i++) {
			String str= gsArr.get(i);
			String urlString = "https://hanyu.baidu.com/s?wd="+urlEncodeURL(str)+"+%E5%8F%A4%E8%AF%97&from=poem";
//			System.out.println(urlString);
			try {
				Document document = Jsoup.connect(urlString).get();
				// poem-detail-header
				Element header = document.getElementById("poem-detail-header");
				if(header!=null) {
					Elements author= header.getElementsByClass("poem-detail-header-info");
					if(author.get(0).wholeText().contains("作者")) {
						System.out.println(num+". "+header.getElementsByTag("h1").text());
						System.out.println(author.get(0).wholeText().replace(" ","")
								.replace("译文对照","").replace("\n","").replace("【作者】","").replace("【朝代】","(").trim()+")");
						num++;
					}else {
						if(tryTime<10) {
							tryTime++;
							i--;
						}else {
							tryTime=1;
							gsArrNotFind.add(str);
						}
						continue;
					}
				}else {
					if(tryTime<10) {
						tryTime++;
						i--;
					}else {
						tryTime=1;
						gsArrNotFind.add(str);
					}
					continue;
				}
				Elements bodys=document.getElementsByAttributeValue("id", "body_p");
				if(bodys.size()==0) {
					bodys=header.getElementsByClass("poem-detail-main-text");
				}
				for (Element body : bodys) {
					System.out.print(body.text());
				}
				System.out.println(); 
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.err.println("未搜索到的古诗："+gsArrNotFind);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		outputGS();
	}

}
