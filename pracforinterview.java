import java.util.Scanner;
public class pracforinterview
{
	public static boolean unique(String s)
	{
		for(int i = 0 ; i < s.length() ; i++)
		{
			char curr = s.charAt(i);
			for(int j = i+1; j < s.length() ; j++)
			{
				if(s.charAt(j) == curr)
					return false;
			}
		}
		return true;
	}
	
	public static void main (String [] args)
	{
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter input");
		String input = sc.next();
		String str = new String(input);
		boolean isitunique = unique(str);
		System.out.println(isitunique);
	}
}