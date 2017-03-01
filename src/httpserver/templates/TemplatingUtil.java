package httpserver.templates;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.naming.NameNotFoundException;

public class TemplatingUtil {

	public static String replaceAll(String template, HashMap<String, Object> environment)
			throws NameNotFoundException {
		Pattern pattern = Pattern.compile("%(\\w+)%");
		Matcher matcher = pattern.matcher(template);
		String varName, value;

		while (matcher.find()) {
			varName = matcher.group(1);
			value = environment.get(varName).toString();
			if (value == null)
				throw new NameNotFoundException("Variable name not found in environment");
			template = template.replace("%" + varName + "%", value);
		}
		return template;
	}

}
