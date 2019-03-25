package model;

public class AttrDto {

	private Integer source;
	private Integer calType;
	private Integer attrType;
	private String attrId;
	private String field;
	private String dimensionKey;
	private String dimensionType;
	private String aviExp;
	public Integer getSource() {
		return source;
	}
	public void setSource(Integer source) {
		this.source = source;
	}
	public Integer getCalType() {
		return calType;
	}
	public void setCalType(Integer calType) {
		this.calType = calType;
	}
	public Integer getAttrType() {
		return attrType;
	}
	public void setAttrType(Integer attrType) {
		this.attrType = attrType;
	}
	public String getAttrId() {
		return attrId;
	}
	public void setAttrId(String attrId) {
		this.attrId = attrId;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getDimensionKey() {
		return dimensionKey;
	}
	public void setDimensionKey(String dimensionKey) {
		this.dimensionKey = dimensionKey;
	}
	public String getDimensionType() {
		return dimensionType;
	}
	public void setDimensionType(String dimensionType) {
		this.dimensionType = dimensionType;
	}
	public String getAviExp() {
		return aviExp;
	}
	public void setAviExp(String aviExp) {
		this.aviExp = aviExp;
	}
	
	public boolean isValid() {
		if (source == null || source < 1) {
			return false;
		}
		if (calType == null || attrType ==null) {
			return false;
		}
		if (attrId == null || attrId.trim().equals("")) {
			return false;
		}
		if (dimensionKey == null || dimensionKey.trim().equals("")) {
			return false;
		}
		if (dimensionType == null || dimensionType.trim().equals("")) {
			return false;
		}
		return true;
	}
	
}
