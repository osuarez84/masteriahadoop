package practicahadoop;
import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import com.google.gson.Gson;

public class TweetMapper extends Mapper<LongWritable, Text, UserTimestampPair, TweetValue> {

	private Gson gson = new Gson();

	/* Extraemos los datos de los tweets del JSON */
	@Override
	public void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		
		/* leemos la lina del JSON y parseamos los datos */
		Tweet tweet = gson.fromJson(value.toString(), Tweet.class);

		/* creamos la composite key */
		UserTimestampPair pair = new UserTimestampPair(new Text(tweet.getUser()), new LongWritable(tweet.getTimestamp()));
		
		/* emitimos la composite key - natural value */
		context.write(pair,
				new TweetValue(new LongWritable(tweet.getTimestamp()),
						new LongWritable(tweet.getRetweet_count())));

	}
}
