package helper;

import java.util.Map;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;

import clojure.lang.Obj;

public class AviatorExpHelper {

	static {
//		AviatorEvaluator.addFunction(function);
	}
//	private 
	
	public static String excute(String express, Map<String, Object> env) {
		Expression expression = AviatorEvaluator.compile(express, true);
		return expression.execute(env).toString();
	}
	
	public static void main(String[] args) {
		Expression expression = AviatorEvaluator.compile("1==1", true);
		System.out.println(expression.execute(null));
	}
	
}
