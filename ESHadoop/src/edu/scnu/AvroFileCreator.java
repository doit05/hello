package edu.scnu;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import org.apache.avro.file.DataFileWriter;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumWriter;

public class AvroFileCreator {

	public static void main(String[] args) throws IOException {
		File infile=new File("test.docx");
		File outAvroFile=new File("Avro.bin");
		FileInputStream in = new FileInputStream(infile);
		ByteBuffer bytes=ByteBuffer.allocate((int) infile.length());
		FileChannel fic=in.getChannel();
		DatumWriter<ArticleFile> datumwriter=new SpecificDatumWriter<ArticleFile>(ArticleFile.class);
		DataFileWriter<ArticleFile> dataFileWriter = new DataFileWriter<ArticleFile>(datumwriter);
		dataFileWriter.create(ArticleFile.getClassSchema(),outAvroFile);
		ArticleFile record=new ArticleFile();
		record.setTitle("testtitle");
		fic.read(bytes);
		bytes.rewind();
		record.setContentBytes(bytes);
		for(int i=0;i<100;i++)
			dataFileWriter.append(record);
		dataFileWriter.flush();
		fic.close();
		in.close();
		dataFileWriter.close();
	}

}
