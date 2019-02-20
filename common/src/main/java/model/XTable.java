package model;

public class XTable{
	private String dbName;
	private String tblName;
	
	public XTable(String dbName, String tblName) {
		this.dbName = dbName;
		this.tblName = tblName;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getTblName() {
		return tblName;
	}

	public void setTblName(String tblName) {
		this.tblName = tblName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dbName == null) ? 0 : dbName.hashCode());
		result = prime * result + ((tblName == null) ? 0 : tblName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		XTable other = (XTable) obj;
		if (dbName == null) {
			if (other.dbName != null)
				return false;
		} else if (!dbName.equals(other.dbName))
			return false;
		if (tblName == null) {
			if (other.tblName != null)
				return false;
		} else if (!tblName.equals(other.tblName))
			return false;
		return true;
	}

}