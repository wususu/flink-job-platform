package conf;

public class MapperHelper {

	public static<T> T getMapper(Class<T> clazz) {
		return BeanUtils.getService(clazz);
	}
	
}
