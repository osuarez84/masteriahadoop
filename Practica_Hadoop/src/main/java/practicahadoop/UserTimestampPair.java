package practicahadoop;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;

/* COMPOSITE KEY (user, timestamp) */
public class UserTimestampPair implements Writable,
		WritableComparable<UserTimestampPair> {

	private Text user;
	private LongWritable timestamp;

	/* Constructors */
	public UserTimestampPair() {
		this.user = new Text();
		this.timestamp = new LongWritable();
	}

	public UserTimestampPair(Text user, LongWritable timestamp) {
		this.user = user;
		this.timestamp = timestamp;
	}

	/* Methods */
	@Override
	public void readFields(DataInput in) throws IOException {
		user.readFields(in);
		timestamp.readFields(in);
	}

	@Override
	public void write(DataOutput out) throws IOException {
		user.write(out);
		timestamp.write(out);
	}

	@Override
	public int compareTo(UserTimestampPair pair) {
		// controls the order of the (key,value) composite key
		int compareValue = this.user.compareTo(pair.getUser());
		if (compareValue == 0) { // son iguales
			compareValue = this.timestamp.compareTo(pair.getTimestamp());
		}

		// sort descending
		return -1 * compareValue;
	}

	public Text getUser() {
		return user;
	}

	public void setUser(Text user) {
		this.user = user;
	}

	public LongWritable getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LongWritable timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((timestamp == null) ? 0 : timestamp.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
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
		UserTimestampPair other = (UserTimestampPair) obj;
		if (timestamp == null) {
			if (other.timestamp != null)
				return false;
		} else if (!timestamp.equals(other.timestamp))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UserTimestampPair [user=" + user + ", timestamp=" + timestamp
				+ "]";
	}
}
