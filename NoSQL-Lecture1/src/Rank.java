import java.util.Arrays;

public class Rank {
	public static String[] reRank(String[] input) {
		int r=0,g=0,b=0;
		for(int i=0;i<input.length;i++) {
			if(input[i].equals("r")) {
				r++;
			}
			if(input[i].equals("g")) {
				g++;
			}
			if(input[i].equals("b")) {
				b++;
			}
		}
		String[] string=new String[input.length];
		for(int j=0;j<r;j++) {
			string[j]="r";
		}
		for(int j=r;j<g+r;j++) {
			string[j]="g";
		}
		for(int j=g+r;j<g+r+b;j++) {
			string[j]="b";
		}
		return string;
	}
	
	public static String[] reRankTwoPointers(String[] input){
		if(input == null || input.length ==0) {
			return new String[0];
		}
		int i=0;
		int left=0;
		int right=input.length-1;
		while(i<=right) {
			if(input[i].equals("r")) {
				String t1=input [i];
				input[i]=input[left];
				input[left]=t1;
				i++;
				left++;
			}
			else if(input[i].equals("g")) {
				i++;
			}
			else if(input[i].equals("b")) {
				String t2=input[i];
				input[i]=input[right];
				input[right]=t2;
				right--;
			}
		}
		return input;
	}
	
	public static void main(String[] args) {
		String[] input=new String[]{"r","r","b","g","b","r","g"};
		String[] string=reRankTwoPointers(input);
		for(int i=0;i<input.length;i++) {
			System.out.print(string[i]);
		}
//		System.out.println(reRank(input));
//		Arrays.asList(reRank(input)).forEach(e->);
	}
}
