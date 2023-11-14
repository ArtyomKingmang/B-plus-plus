import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Queue;

import com.kingmang.bpp.Char;
import com.kingmang.bpp.Context;
import com.kingmang.bpp.parser.Parser;
import com.kingmang.bpp.parser.Reader;
import com.kingmang.bpp.Token;


public class Test {

	public static void main(String[] args) throws IOException {
		StringBuilder script = new StringBuilder();
		String path = "C:\\Users\\crowb\\OneDrive\\Рабочий стол\\B++\\";
		String fileName = path + "main.bpp";
		String input = new String(Files.readAllBytes(Paths.get(fileName)));
		script.append(input);

		ArrayList<Char> characters = new ArrayList<Char>();
		for (int i = 0; i < script.length(); i ++) {
			characters.add(new Char(script.charAt(i)));
		}
		script.setLength(0);

		Reader reader = new Reader();
		Queue<Token> tokens = reader.read(characters);

		Parser parser = new Parser();
		Context context = parser.parse(tokens);

		context.run();
	}

}
