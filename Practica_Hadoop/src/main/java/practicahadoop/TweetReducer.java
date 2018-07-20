package practicahadoop;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import com.google.common.collect.Iterables;


public class TweetReducer extends
		Reducer<UserTimestampPair, TweetValue, Text, Text> {

	/* Recibimos los datos ordenados por timestamp de mas actual
	 * a mas antiguo y computamos los calculos que se piden. */
	@Override
	protected void reduce(UserTimestampPair key, Iterable<TweetValue> values,
			Context context) throws IOException, InterruptedException {

		double popularityMomentum = 0;
		double volumeMomentum = 0;
		
		int totalUserTweets = 0;
		
		/* Guardamos los primeros 20 tweets para los calculos */
		List<TweetValue> cachedValues = new ArrayList<>();
		for (TweetValue tv: values) {
			if (totalUserTweets < 20) {
				cachedValues.add(new TweetValue(new LongWritable(tv.getTimestamp().get()), new LongWritable(tv.getRetweetCount().get())));
	
			}
			
			totalUserTweets++;
		}
				
		/* Guardamos el numero de tweets que el usuario tiene para procesar */
		int tweetsToProcess = cachedValues.size();
		
		/* Si el usuario tiene mas de un tweet calculamos... */
		if (tweetsToProcess != 1) {
			
			int count  = 0;
			
			int first = 0; // t_0
			int middle = (int) Math.floor(tweetsToProcess/2.0); // t_N/2
			int last = tweetsToProcess - 1; // t_N
			
			/* inicializamos las variables de los calculos */
			double t_0 = 0, t_N_2 = 0, t_N = 0;
			long totalRts = 0, numRts = 0;
			
			/* iteramos sobre cada par key-value */
			for (TweetValue value: cachedValues) {
				
				/* guardamos los tweets que nos interesan
				 * para el volume momentum */
				if(count == first) {
					t_0 = (double)value.getTimestamp().get();
				} else if (count == middle) {
					t_N_2 = (double)value.getTimestamp().get();
				} else if (count == last) {
					t_N = (double)value.getTimestamp().get();
				}
				
				/* computamos el total de retweets para el popularity momentum*/
				totalRts += value.getRetweetCount().get();
				
				/* computamos el calculo para el popularity momentum */
				if(count >= middle) {
					numRts += value.getRetweetCount().get();
				}
				count++;

			}
			

		/* hacemos los calculos con los datos obtenidos */
		try {
				// gestionamos una division por 0
			if (t_N - t_0 != 0) {
				volumeMomentum = Math.floor((((t_N - t_0) - (t_N - t_N_2))/(t_N - t_0)) * 1000)/1000;
			} else {
				volumeMomentum = 0.0;
			}
			
			// gestionamos una division por 0
			if(totalRts != 0){
				popularityMomentum = Math.floor(((double)numRts / totalRts) * 1000)/1000;
			} else {
				popularityMomentum = 0.0;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/* emitimos como salida los valores calculados */
	context.write(new Text(key.getUser()), new Text(totalUserTweets + ","
			+ popularityMomentum + "," + volumeMomentum));
			
		
		
		
//		List<TweetValue> cachedValues = new ArrayList<>();
//		for(TweetValue tv : values) {
//			cachedValues.add(new TweetValue(new LongWritable(tv.getTimestamp().get()), new LongWritable(tv.getRetweetCount().get())));
//		}
//		int totalUserTweets = cachedValues.size();
//		
//		int tweetsToProcess = 0;
//		if (totalUserTweets > 20) {
//			tweetsToProcess = 20;
//		} else {
//			tweetsToProcess = totalUserTweets;
//		}
//
//		if (tweetsToProcess != 1) {
//			int count  = 0;
//			int first = 0; // t_N
//			int middle = (int) Math.floor(tweetsToProcess/2.0); // t_N/2
//			int last = tweetsToProcess - 1; // t_0
//			
//			double t_0 = 0, t_N_2 = 0, t_N = 0;
//			long totalRts = 0, numRts = 0;
//			
//			for(TweetValue value : cachedValues) {
//				if(count == tweetsToProcess) {
//					break;
//				}
//				
//				if(count == first) {
//					t_0 = (double)value.getTimestamp().get();
//				} else if (count == middle) {
//					t_N_2 = (double)value.getTimestamp().get();
//				} else if (count == last) {
//					t_N = (double)value.getTimestamp().get();
//				}
//				
//				totalRts += value.getRetweetCount().get();
//				if(count >= middle) {
//					numRts += value.getRetweetCount().get();
//				}
//				
//				count++;
//			}
//
//		try {
//				// gestionamos una division por 0
//				if (t_N - t_0 != 0) {
//					volumeMomentum = Math.floor((((t_N - t_0) - (t_N - t_N_2))/(t_N - t_0)) * 1000)/1000;
//				} else {
//					volumeMomentum = 0.0;
//				}
//				
//				// gestionamos una division por 0
//				if(totalRts != 0){
//					popularityMomentum = Math.floor(((double)numRts / totalRts) * 1000)/1000;
//				} else {
//					popularityMomentum = 0.0;
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//
//		context.write(new Text(key.getUser()), new Text(totalUserTweets + ","
//				+ popularityMomentum + "," + volumeMomentum));
	}

}
