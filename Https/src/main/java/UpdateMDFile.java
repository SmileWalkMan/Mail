import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.PrintStream;
//Add <br> at the end of the github .md files
public class UpdateMDFile {

	public static void changeMD4All(File dir) throws Exception {
		if(dir.isDirectory()) {
			File[] listFiles = dir.listFiles();
			for (int i = 0; i < listFiles.length; i++) {
				File f = listFiles[i];
				changeMD4All(f);
			}
		}else {
			changeMD(dir);
		}
	}

	public static void changeMD(File f) throws Exception {

		if(!f.getName().endsWith(".md")) return;
		System.out.println("Start Updating: "+f.getAbsolutePath());
		FileInputStream is = new FileInputStream(f);
		BufferedReader br = new BufferedReader(new InputStreamReader(is));

		StringBuilder sb = new StringBuilder();
		String line = null;

		while ((line = br.readLine()) != null) {
			line = line.trim();
			if (line.lastIndexOf("<br>") < 0) {
				line += "<br>\r\n";
			}
			sb.append(line);
		}
		br.close();
		String content = sb.toString();
		PrintStream stream = null;
		try {
			stream = new PrintStream(f);// 写入的文件path
			stream.print(content);// 写入的字符串
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("End Updating: "+f.getAbsolutePath());
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File dir = new File(".");
		try {
			changeMD4All(dir);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
