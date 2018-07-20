package practicahadoop;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

/* GROUPING_COMPARATOR */
/* El Custom comparator se encarga de hacer el sorting
 tal que los datos se agrupen por la natural key una vez
 que estos lleguen al reducer */
public class UserTimestampGroupingComparator extends WritableComparator {

	// Constructor
	public UserTimestampGroupingComparator() {
		super(UserTimestampPair.class, true);
	}

	// Compara las natural key (user)
	@SuppressWarnings("rawtypes")
	@Override
	public int compare(WritableComparable a, WritableComparable b) {
		
		UserTimestampPair pair = (UserTimestampPair) a;
		UserTimestampPair pair2 = (UserTimestampPair) b;

		return pair.getUser().compareTo(pair2.getUser());

	}

}
