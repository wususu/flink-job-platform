package model;

public enum AttrType {
	REAL("REAL", 1),
	COMPLEX("COMPLEX", 2);
	
	public String val;
	public int code;
	
	private AttrType(String val, int code) {
		this.val = val;
		this.code = code;
	}
	
	public static AttrType get(int code) {
		if (code == 1) {
			return REAL;
		}
		if (code == 2) {
			return COMPLEX;
		}
		return null;
	}
}
