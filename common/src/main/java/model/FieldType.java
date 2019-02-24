package model;

public enum FieldType {

	STRING("STRING", String.class),
	INTEGER("INTEGER", Integer.class),
	DOUBEL("DOUBLE", Double.class);
	
	public String val;
	public Class<?> clazz;
	
	FieldType(String val, Class<?> clazz) {
		this.val = val;
		this.clazz = clazz;
	}
	
	public static FieldType get(String typeName) {
		for(FieldType type: FieldType.values()) {
			if (type.val.equals(typeName)) {
				return type;
			}
		}
		throw new IllegalArgumentException("FieldType not found");
	}

}
