import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

public class Formula {

	public static String ChangeToChineseFormula(String EnglishFormula) {
		EnglishFormula=EnglishFormula.replace("’", "\'");
		EnglishFormula=EnglishFormula.replace(" ", "");
		EnglishFormula=EnglishFormula.replace("U\'2", "右右");
		EnglishFormula=EnglishFormula.replace("U2", "左左");
		EnglishFormula=EnglishFormula.replace("U\'", "右");
		EnglishFormula=EnglishFormula.replace("U", "左");
		
		EnglishFormula=EnglishFormula.replace("R\'2", "下下");
		EnglishFormula=EnglishFormula.replace("R2", "上上");
		EnglishFormula=EnglishFormula.replace("R\'", "下");
		EnglishFormula=EnglishFormula.replace("R", "上");
		
		EnglishFormula=EnglishFormula.replace("F\'2", "提提");
		EnglishFormula=EnglishFormula.replace("F2", "压压");
		EnglishFormula=EnglishFormula.replace("F\'", "提");
		EnglishFormula=EnglishFormula.replace("F", "压");
		
		EnglishFormula=EnglishFormula.replace("D\'2", "挡挡");
		EnglishFormula=EnglishFormula.replace("D2", "打打");
		EnglishFormula=EnglishFormula.replace("D\'", "挡");
		EnglishFormula=EnglishFormula.replace("D", "打");
		
		EnglishFormula=EnglishFormula.replace("B\'2", "挑挑");
		EnglishFormula=EnglishFormula.replace("B2", "拨拨");
		EnglishFormula=EnglishFormula.replace("B\'", "挑");
		EnglishFormula=EnglishFormula.replace("B", "拨");
		
		EnglishFormula=EnglishFormula.replace("u\'2", "双右双右");
		EnglishFormula=EnglishFormula.replace("u2", "双左双左");
		EnglishFormula=EnglishFormula.replace("u\'", "双右");
		EnglishFormula=EnglishFormula.replace("u", "双左");
		
		EnglishFormula=EnglishFormula.replace("r\'2", "双下双下");
		EnglishFormula=EnglishFormula.replace("r2", "双上双上");
		EnglishFormula=EnglishFormula.replace("r\'", "双下");
		EnglishFormula=EnglishFormula.replace("r", "双上");
		
		EnglishFormula=EnglishFormula.replace("f\'2", "双提双提");
		EnglishFormula=EnglishFormula.replace("f2", "双压双压");
		EnglishFormula=EnglishFormula.replace("f\'", "双提");
		EnglishFormula=EnglishFormula.replace("f", "双压");
		
		EnglishFormula=EnglishFormula.replace("d\'2", "双挡双挡");
		EnglishFormula=EnglishFormula.replace("d2", "双打双打");
		EnglishFormula=EnglishFormula.replace("d\'", "双挡");
		EnglishFormula=EnglishFormula.replace("d", "双打");
		
		EnglishFormula=EnglishFormula.replace("b\'2", "双挑双挑");
		EnglishFormula=EnglishFormula.replace("b2", "双拨双拨");
		EnglishFormula=EnglishFormula.replace("b\'", "双挑");
		EnglishFormula=EnglishFormula.replace("b", "双拨");
		
		EnglishFormula=EnglishFormula.replace("x\'2", "覆覆");
		EnglishFormula=EnglishFormula.replace("x2", "翻翻");
		EnglishFormula=EnglishFormula.replace("x\'", "覆");
		EnglishFormula=EnglishFormula.replace("x", "翻");
		
		EnglishFormula=EnglishFormula.replace("y\'2", "正正");
		EnglishFormula=EnglishFormula.replace("y2", "侧侧");
		EnglishFormula=EnglishFormula.replace("y\'", "正");
		EnglishFormula=EnglishFormula.replace("y", "侧");
		
		EnglishFormula=EnglishFormula.replace("z\'2", "站站");
		EnglishFormula=EnglishFormula.replace("z2", "倒倒");
		EnglishFormula=EnglishFormula.replace("z\'", "站");
		EnglishFormula=EnglishFormula.replace("z", "倒");
		
		return EnglishFormula;
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		FileInputStream inputStream;
		try {
			inputStream = new FileInputStream("cfop_formula.txt");
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			
			String str = null;
			while((str = bufferedReader.readLine()) != null)
			{
				if(str.trim().equals(""))continue;
				System.out.println(ChangeToChineseFormula(str));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
