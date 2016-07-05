package edu.scnu;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.elasticsearch.hadoop.mr.EsOutputFormat;
import org.apache.avro.mapred.AvroKey;
import org.apache.avro.mapreduce.*;

public class MRRunner implements Tool {

	static class TestMapper extends Mapper<AvroKey<ArticleFile>, NullWritable, NullWritable, MapWritable> {
		protected void map(AvroKey<ArticleFile> key, NullWritable value, Context context)
				throws IOException, InterruptedException {
			if (key.datum().getLastModTime() > key.datum().getPrevModTime()) {
				try {
					//Configuration conf = new Configuration();
					MessageDigest md5 = MessageDigest.getInstance("MD5");
					BigInteger md5digest = new BigInteger(1, md5.digest(key.datum().getContentBytes().array()));
//					String url = "http://" + conf.get("es.nodes") + "/" + conf.get("es.resource")
//							+ "/_search/exists/?q=md5:" + md5digest.toString();
//					HttpClient client = new DefaultHttpClient();
//					HttpGet request = new HttpGet(url);
//					HttpResponse response = client.execute(request);
//					try {
//						JSONObject json = new JSONObject(EntityUtils.toString(response.getEntity()));
//						if (!json.getBoolean("exists")) {
							Tika tika = new Tika();

							MapWritable doc = new MapWritable();

							// System.err.println(key.datum().getTitle());

							doc.put(new Text("articleId"), new Text(md5digest.toString(16)));
							doc.put(new Text("title"), new Text(key.datum().getTitle().toString()));
							InputStream inContent = new ByteArrayInputStream(key.datum().getContentBytes().array());
							try {
								doc.put(new Text("content"), new Text(tika.parseToString(inContent)));
							} catch (TikaException e) {
								//e.printStackTrace();
							}
							context.write(NullWritable.get(), doc);
//						}
//					} catch (JSONException e) {
//					}
				} catch (NoSuchAlgorithmException e) {
				}
			}
		}
	}

	Configuration conf = null;

	@Override
	public Configuration getConf() {
		if (this.conf == null) {
			this.conf = new Configuration();
			this.conf.setBoolean("mapred.map.tasks.speculative.execution", false);
			this.conf.setBoolean("mapred.reduce.tasks.speculative.execution", false);
			if (this.conf.get("es.nodes") == null || this.conf.get("es.nodes") == "")
				this.conf.set("es.nodes", "192.168.75.146:9200");
			if (this.conf.get("es.resource") == null || this.conf.get("es.resource") == "")
				this.conf.set("es.resource", "index/article");
			this.conf.set("es.mapping.id", "articleId");
		}
		return this.conf;
	}

	@Override
	public void setConf(Configuration conf) {
		this.conf.addResource(conf);
	}

	@Override
	public int run(String[] arg0) throws Exception {
		Job startjob = Job.getInstance(getConf());
		startjob.setOutputFormatClass(EsOutputFormat.class);
		startjob.setInputFormatClass(AvroKeyInputFormat.class);
		AvroJob.setInputKeySchema(startjob, ArticleFile.getClassSchema());
		startjob.setMapOutputKeyClass(NullWritable.class);
		startjob.setMapOutputValueClass(MapWritable.class);
		startjob.setMapperClass(TestMapper.class);
		FileInputFormat.setInputPaths(startjob, new Path("BigAvro.bin"));
		startjob.waitForCompletion(true);
		return 0;
	}

	public static void main(String[] args) throws Exception {
		ToolRunner.run(new MRRunner(), args);
	}

}
