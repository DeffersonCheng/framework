package com.wdl.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternUtil {
	public static boolean matchUrl(String regex, String str) {
		regex=regex.replace("*","([\\w\\W]*)");
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}
}
