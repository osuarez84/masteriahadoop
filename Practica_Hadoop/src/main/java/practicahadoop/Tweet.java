package practicahadoop;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/* Clase para extrater los datos de cada linea de tweet */

public class Tweet {
	String CREATED_AT_FORMAT = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
	
	// Variables para guardar los datos
	String created_at;
	long retweet_count;
	User user;
	
	public class User {
		String screen_name;
	}

	
	/* Getters */
	public String getCreated_at() {
		return created_at;
	}
	
	public long getTimestamp() {
		SimpleDateFormat sdf = new SimpleDateFormat(CREATED_AT_FORMAT);
		try {
			return sdf.parse(created_at).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
			return 0L;
		}
	}

	public long getRetweet_count() {
		return retweet_count;
	}

	public String getUser() {
		return user.screen_name;
	}

	@Override
	public String toString() {
		return "Tweet [created_at=" + created_at + ", retweet_count="
				+ retweet_count + ", user=" + user.screen_name + "]";
	}
}
