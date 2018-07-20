package practicahadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.hadoop.util.ToolRunner;


public class TweetJob {
	

  public static void main (String[] args)throws Exception {
	
	Configuration conf = new Configuration();
    conf.set("mapred.textoutputformat.separator", ",");
    
    /* Configuramos el job MapReduce */
    Job job = new Job(conf);
    job.setJarByClass(TweetJob.class);
    job.setJobName("SecondarySortDriver");
    
    /* Parseamos los argumentos */
    String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
    if (otherArgs.length != 2) {
       System.err.println("Usage: SecondarySortDriver <input> <output>");
       System.exit(1);
    } 

    /* Configuramos mapper, reducer y el job */
    job.setJarByClass(TweetJob.class);
    job.setJarByClass(TweetMapper.class);
    job.setJarByClass(TweetReducer.class);
    
   // set mapper and reducer
    job.setMapperClass(TweetMapper.class);
    job.setReducerClass(TweetReducer.class);
    
    // definimos la salida del mapper key-value
    job.setMapOutputKeyClass(UserTimestampPair.class);
    job.setMapOutputValueClass(TweetValue.class);
          
    // definimos la salida dle reducer key-value
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(Text.class);

    // Los siguientes settings se utilizan para realizar
    // el secondary sorting.
    job.setPartitionerClass(UserTimestampPartitioner.class);
    job.setGroupingComparatorClass(UserTimestampGroupingComparator.class);
    
    /* Configuramos los formatos de entrada y salida */
    job.setInputFormatClass(TextInputFormat.class);
    job.setOutputFormatClass(TextOutputFormat.class);

    /* Configuramos los paths para los ficheros de entrada/salida */
    FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
    FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));

    /* Esperamos a que se complete el job */
    job.waitForCompletion(true);


  }


}
