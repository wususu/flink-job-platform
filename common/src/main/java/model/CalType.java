package model;

public enum CalType {

	
	MAX("MAX",3),MIN("MIN",4),ADD("ADD",2),APPEND("APPEND",1),NEW("NEW",5),AVIEXP("AVIEXP",6);
	public String val;
	public int code;

	private CalType(String val, int code) {
		this.val = val;
		this.code = code;
	}
	
	public static CalType get(int code) {
		for(CalType type: CalType.values()) {
			if (type.code == code) {
				return type;
			}
		}
		throw new IllegalArgumentException("CalType not found");
	}
	
}
