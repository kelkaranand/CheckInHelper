import java.util.HashMap;


public class Encrypter {

	static HashMap<Character,Character> encryptKey = new HashMap<Character,Character>();
	static HashMap<Character,Character> decryptKey = new HashMap<Character,Character>();
	
	public static void initialize()
	{
		
		//Add key value pairs to encrypt key
		encryptKey.put('a', 'd');
		encryptKey.put('b', 'q');
		encryptKey.put('c', 'e');
		encryptKey.put('d', 'r');
		encryptKey.put('e', 'f');
		encryptKey.put('f', 's');
		encryptKey.put('g', 'g');
		encryptKey.put('h', 't');
		encryptKey.put('i', 'h');
		encryptKey.put('j', 'u');
		encryptKey.put('k', 'i');
		encryptKey.put('l', 'v');
		encryptKey.put('m', 'j');
		encryptKey.put('n', 'w');
		encryptKey.put('o', 'k');
		encryptKey.put('p', 'x');
		encryptKey.put('q', 'l');
		encryptKey.put('r', 'y');
		encryptKey.put('s', 'm');
		encryptKey.put('t', 'z');
		encryptKey.put('u', 'n');
		encryptKey.put('v', 'c');
		encryptKey.put('w', 'o');
		encryptKey.put('x', 'b');
		encryptKey.put('y', 'p');
		encryptKey.put('z', 'a');
		
		encryptKey.put('A', 'D');
		encryptKey.put('B', 'Q');
		encryptKey.put('C', 'E');
		encryptKey.put('D', 'R');
		encryptKey.put('E', 'F');
		encryptKey.put('F', 'S');
		encryptKey.put('G', 'G');
		encryptKey.put('H', 'T');
		encryptKey.put('I', 'H');
		encryptKey.put('J', 'U');
		encryptKey.put('K', 'I');
		encryptKey.put('L', 'V');
		encryptKey.put('M', 'J');
		encryptKey.put('N', 'W');
		encryptKey.put('O', 'K');
		encryptKey.put('P', 'X');
		encryptKey.put('Q', 'L');
		encryptKey.put('R', 'Y');
		encryptKey.put('S', 'M');
		encryptKey.put('T', 'Z');
		encryptKey.put('U', 'N');
		encryptKey.put('V', 'C');
		encryptKey.put('W', 'O');
		encryptKey.put('X', 'B');
		encryptKey.put('Y', 'P');
		encryptKey.put('Z', 'A');
		
		
		
		//Add key value pairs to decrypt key
		decryptKey.put('d', 'a');
		decryptKey.put('q','b');
		decryptKey.put('e','c');
		decryptKey.put('r','d');
		decryptKey.put('f','e');
		decryptKey.put('s','f');
		decryptKey.put('g','g');
		decryptKey.put('t','h');
		decryptKey.put('h','i');
		decryptKey.put('u','j');
		decryptKey.put('i','k');
		decryptKey.put('v','l');
		decryptKey.put('j','m');
		decryptKey.put('w','n');
		decryptKey.put('k','o');
		decryptKey.put('x','p');
		decryptKey.put('l','q');
		decryptKey.put('y','r');
		decryptKey.put('m','s');
		decryptKey.put('z','t');
		decryptKey.put('n','u');
		decryptKey.put('c','v');
		decryptKey.put('o','w');
		decryptKey.put('b','x');
		decryptKey.put('p','y');
		decryptKey.put('a','z');
				
		decryptKey.put('D','A');
		decryptKey.put('Q','B');
		decryptKey.put('E','C');
		decryptKey.put('R','D');
		decryptKey.put('F','E');
		decryptKey.put('S','F');
		decryptKey.put('G', 'G');
		decryptKey.put('T','H');
		decryptKey.put('H','I');
		decryptKey.put('U','J');
		decryptKey.put('I','K');
		decryptKey.put('V','L');
		decryptKey.put('J','M');
		decryptKey.put('W','N');
		decryptKey.put('K','O');
		decryptKey.put('X','P');
		decryptKey.put('L','Q');
		decryptKey.put('Y','R');
		decryptKey.put('M','S');
		decryptKey.put('Z','T');
		decryptKey.put('N','U');
		decryptKey.put('C','V');
		decryptKey.put('O','W');
		decryptKey.put('B','X');
		decryptKey.put('P','Y');
		decryptKey.put('A','Z');
	}
	
	public static String encrypt(String a)
	{
		String temp="";
		for(int i=0;i<a.length();i++)
		{
			if(encryptKey.containsKey((Character)a.charAt(i)))
			{
				temp=temp+encryptKey.get((Character)a.charAt(i));
			}
			else
			{
				temp=temp+a.charAt(i);
			}
		}
		return temp;
	}
	
	public static String decrypt(String a)
	{
		String temp="";
		for(int i=0;i<a.length();i++)
		{
			if(decryptKey.containsKey((Character)a.charAt(i)))
			{
				temp=temp+decryptKey.get((Character)a.charAt(i));
			}
			else
			{
				temp=temp+a.charAt(i);
			}
		}
		return temp;
	}
	
	
	
}
