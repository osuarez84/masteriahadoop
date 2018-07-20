package practicahadoop;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Writable;


public class TweetValue implements Writable {
	private LongWritable timestamp;
	private LongWritable retweetCount;

	public TweetValue() {	
		this.timestamp = new LongWritable();
		this.retweetCount = new LongWritable();
	}
	
	public TweetValue(LongWritable timestamp, LongWritable retweetCount) {
		this.timestamp = timestamp;
		this.retweetCount = retweetCount;
	}

	@Override
	public void write(DataOutput out) throws IOException {
		timestamp.write(out);
		retweetCount.write(out);
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		timestamp.readFields(in);
		retweetCount.readFields(in);
	}

	public LongWritable getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LongWritable timestamp) {
		this.timestamp = timestamp;
	}

	public LongWritable getRetweetCount() {
		return retweetCount;
	}

	public void setRetweetCount(LongWritable retweetCount) {
		this.retweetCount = retweetCount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((retweetCount == null) ? 0 : retweetCount.hashCode());
		result = prime * result
				+ ((timestamp == null) ? 0 : timestamp.hashCode());
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
		TweetValue other = (TweetValue) obj;
		if (retweetCount == null) {
			if (other.retweetCount != null)
				return false;
		} else if (!retweetCount.equals(other.retweetCount))
			return false;
		if (timestamp == null) {
			if (other.timestamp != null)
				return false;
		} else if (!timestamp.equals(other.timestamp))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TweetValue [timestamp=" + timestamp + ", retweetCount="
				+ retweetCount + "]";
	}
}
