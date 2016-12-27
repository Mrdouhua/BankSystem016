
package cn.edu.cqupt.core;
import java.util.UUID;
public class UUIDFactory {
	public static String generateUUID(){
		UUID gUUid =UUID.randomUUID();
		String uudi = gUUid.toString();
		return uudi.replaceAll("-", "");
	}
}
