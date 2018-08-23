public class Top20Mapper extends Mapper<LongWritable, Text, NullWritable, Text> {
// create the Tree Map with MySalaryComparator
public static TreeMap<sala, Text> ToRecordMap = new TreeMap<Salary , Text>(new MySalaryComp1());
                    
public void map(LongWritable key, Text value, Context context)throws IOException, InterruptedException {
                     String line=value.toString();
                     String[] tokens=line.split("\t");
                     //split the data and fetch salary
                     int salary=Integer.parseInt(tokens[3]);
                //insert salary object as  key and entire row as value
                     //tree map sort the records based on salary
               ToRecordMap.put(new Salary (salary), new Text(value));
 
// If we have more than ten records, remove the one with the lowest salary
// As this tree map is sorted in descending order, the employee with
// the lowest salary is the last key.
 
Iterator<Entry<Salary , Text>> iter = ToRecordMap.entrySet().iterator();
     Entry<Salary , Text> entry = null;
                
                 while(ToRecordMap.size()>10){
                   entry = iter.next();      
   iter.remove();         
                 }
                     }
protected void cleanup(Context context) throws IOException, InterruptedException {
 
// Output our ten records to the reducers with a null key
 
                     for (Text t:ToRecordMap.values()) {
                     context.write(NullWritable.get(), t);
                     }
                     }                 
}