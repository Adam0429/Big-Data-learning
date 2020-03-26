import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class q1 {

    public static class MyMapper
            extends Mapper<Object, Text, Text, FloatWritable>{

        private Text topic = new Text();
        public void map(Object key, Text value, Context context
        ) throws IOException, InterruptedException {
            String string = value.toString();
            String []str = string.split(",");
            if(!str[3].equals("passenger_count")){
                topic.set("avg passengers:");
                context.write(topic,new FloatWritable(Float.parseFloat(str[3])));
            }
        }
    }

    public static class MyReducer
            extends Reducer<Text,FloatWritable,Text,FloatWritable> {
        private FloatWritable result = new FloatWritable();

        public void reduce(Text key, Iterable<FloatWritable> values,
                           Context context
        ) throws IOException, InterruptedException {
            float sum = 0;
            float n = 0;
            for (FloatWritable val : values) {
                sum += val.get();
                n += 1;
            }
            result.set(sum/n);
            context.write(key, result);
        }
    }

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "average passenger");
        job.setJarByClass(q1.class);
        job.setMapperClass(MyMapper.class);
        job.setCombinerClass(MyReducer.class);
        job.setReducerClass(MyReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(FloatWritable.class);
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}



//hadoop com.sun.tools.javac.Main q1.java
//jar cvfe q1.jar q1 ./*class
//hadoop jar q1.jar /A1_dataset /output_q1