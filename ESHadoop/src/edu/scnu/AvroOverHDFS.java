package edu.scnu;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import org.apache.avro.file.DataFileWriter;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.mapred.FsInput;
import org.apache.avro.specific.SpecificDatumWriter;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.util.Time;

public class AvroOverHDFS {

	public static void upload2HDFS4InputSteams(ArrayList<String> titles, ArrayList<InputStream> in_array) throws IOException {

		// 打开本地文档
		// File infile = new File("/root/test.docx");
		// File infile = new File(infilepath);
		// InputStream in;
		// FileChannel fic;
		// in = new FileInputStream(infile);
		// fic = in.getChannel();

		if (titles.size()!= in_array.size() || titles.isEmpty() || in_array.isEmpty())
			return;

		// HDFS打开文件,创建写入器
		Configuration conf = new Configuration();
		String out_path = "BigAvro.bin";
		Path hdfs_path = new Path(out_path);
		FileSystem fs = FileSystem.get(conf);
		OutputStream out;

		DatumWriter<ArticleFile> datumwriter = new SpecificDatumWriter<ArticleFile>(ArticleFile.class);
		DataFileWriter<ArticleFile> dataFileWriterOrigin = new DataFileWriter<ArticleFile>(datumwriter);
		DataFileWriter<ArticleFile> dataFileWriter;

		if (fs.exists(hdfs_path)) {
			out = fs.append(hdfs_path);
			dataFileWriter = dataFileWriterOrigin.appendTo(new FsInput(hdfs_path, conf), out);
		} else {
			out = fs.create(hdfs_path);
			dataFileWriter = dataFileWriterOrigin.create(ArticleFile.getClassSchema(), out);
		}

		// 构建记录
		ArticleFile record = new ArticleFile();
		for (int i = 0; i < in_array.size(); i++) {
			record.setTitle(titles.get(i));
			ByteBuffer bytes = ByteBuffer.allocate((int) in_array.get(i).available());
			try {
				in_array.get(i).read(bytes.array());
			} catch (IOException e) {
				for (int j = i; j < in_array.size(); j++) {
					in_array.get(j).close();
				}
				out.close();
				dataFileWriter.close();
				dataFileWriterOrigin.close();
				throw e;
			}
			bytes.rewind();
			record.setContentBytes(bytes);
			try {
				dataFileWriter.append(record);
				dataFileWriter.flush();

			} catch (IOException e) {
				for (int j = i + 1; j < in_array.size(); j++) {
					in_array.get(j).close();
				}
				out.close();
				dataFileWriter.close();
				dataFileWriterOrigin.close();
				throw e;
			} finally {
				in_array.get(i).close();
			}
		}

		dataFileWriter.close();
		dataFileWriterOrigin.close();
		out.close();
	}

	public static void upload2HDFS4Files(ArrayList<File> in_array) throws IOException {

		// 打开本地文档
		// File infile = new File("/root/test.docx");
		// File infile = new File(infilepath);
		// InputStream in;
		// FileChannel fic;
		// in = new FileInputStream(infile);
		// fic = in.getChannel();

		if (in_array.isEmpty())
			return;

		// HDFS打开文件,创建写入器
		Configuration conf = new Configuration();
		String out_path = "BigAvro.bin";
		long last_mod_time=0;
		Path hdfs_path = new Path(out_path);
		FileSystem fs = FileSystem.get(conf);
		OutputStream out;

		DatumWriter<ArticleFile> datumwriter = new SpecificDatumWriter<ArticleFile>(ArticleFile.class);
		DataFileWriter<ArticleFile> dataFileWriterOrigin = new DataFileWriter<ArticleFile>(datumwriter);
		DataFileWriter<ArticleFile> dataFileWriter;

		if (fs.exists(hdfs_path)) {
			out = fs.append(hdfs_path);
			last_mod_time=fs.getFileStatus(hdfs_path).getModificationTime();
			dataFileWriter = dataFileWriterOrigin.appendTo(new FsInput(hdfs_path, conf), out);
			
		} else {
			out = fs.create(hdfs_path);
			last_mod_time=Time.now();
			dataFileWriter = dataFileWriterOrigin.create(ArticleFile.getClassSchema(), out);
		}

		// 构建记录
		ArticleFile record = new ArticleFile();
		for (int i = 0; i < in_array.size(); i++) {
			if(in_array.get(i).isDirectory()||!in_array.get(i).exists())
				continue;
			record.setTitle(in_array.get(i).getName());
			record.setPrevModTime(last_mod_time);
			record.setLastModTime(Time.now());
			record.setCrc32("");
			record.setMd5("");
			InputStream in=new FileInputStream(in_array.get(i));
			ByteBuffer bytes = ByteBuffer.allocate((int) in.available());
			try {
				in.read(bytes.array());
			} catch (IOException e) {
				in.close();
				out.close();
				dataFileWriter.close();
				dataFileWriterOrigin.close();
				throw e;
			}
			bytes.rewind();
			record.setContentBytes(bytes);
			try {
				dataFileWriter.append(record);
				dataFileWriter.flush();

			} catch (IOException e) {
				out.close();
				dataFileWriter.close();
				dataFileWriterOrigin.close();
				throw e;
			} finally {
				in.close();
			}
		}

		dataFileWriter.close();
		dataFileWriterOrigin.close();
		out.close();
	}	
	
	public static void main(String[] args) throws Exception {
		ArrayList<File> infiles=new ArrayList<File>();
		if (args.length == 0) {
			infiles.add(new File("/root/test.docx"));
		}
		else
		{
			for(int i=0;i<args.length;i++)
			{
				infiles.add(new File(args[i]));
			}
		}
		AvroOverHDFS.upload2HDFS4Files(infiles);
	}
}
