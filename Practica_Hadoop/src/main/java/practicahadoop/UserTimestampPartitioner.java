package practicahadoop;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Partitioner;


/* El partitioner se encarga de enviar todos los datos con la misma
key (la natural key, no incluida la composite key) al mismo reducer */

public class UserTimestampPartitioner extends Partitioner<UserTimestampPair, IntWritable> {

  @Override
  public int getPartition (UserTimestampPair key, IntWritable value, int numberOfPartitions) {
    
    // aseguramos que el numero de particiones son no negativas
    return Math.abs(key.getUser().hashCode() % numberOfPartitions);
  }

}