package com.hiramsoft.commons.jsalparser;

import com.hiramsoft.commons.jsalparser.CloudFrontLogGrammarLexer;
import com.hiramsoft.commons.jsalparser.CloudFrontLogGrammarParser;
import org.antlr.v4.runtime.ANTLRInputStream;
import com.hiramsoft.commons.jsalparser.S3TypedLogGrammarLexer;
import org.antlr.v4.runtime.CommonTokenStream;
import com.hiramsoft.commons.jsalparser.S3TypedLogGrammarParser;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by ihiram on 8/27/14.
 */
public class JSalParser {

	////////
	// S3
	///////

	public static List<S3LogEntry> parseS3Log(String content){
		ANTLRInputStream inputStream = new ANTLRInputStream(content);

		return parseS3Log(inputStream);
	}

	public static List<S3LogEntry> parseS3Log(InputStream content) throws IOException{
		ANTLRInputStream inputStream = new ANTLRInputStream(content);

		return parseS3Log(inputStream);
	}

	private static List<S3LogEntry> parseS3Log(ANTLRInputStream content){
		ListVisitor listVisitor = new ListVisitor();
		parseS3Log(content, listVisitor);
		return listVisitor.getS3();
	}

	////////
	// S3 Visitor-based methods
	///////

	public static void parseS3Log(InputStream content, IS3LogVisitor visitor) throws IOException{
		ANTLRInputStream inputStream = new ANTLRInputStream(content);
		parseS3Log(inputStream, visitor);
	}

	private static void parseS3Log(ANTLRInputStream content, IS3LogVisitor visitor){
		S3TypedLogGrammarLexer lexer = new S3TypedLogGrammarLexer(content);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		S3TypedLogGrammarParser parser = new S3TypedLogGrammarParser(tokens);

		parser.file(visitor);
	}


	///////
	// CloudFront
	//////

	public static List<CloudFrontWebLogEntry> parseCloudFrontLog(String content){
		ANTLRInputStream inputStream = new ANTLRInputStream(content);

		return parseCloudFrontLog(inputStream);
	}

	public static List<CloudFrontWebLogEntry> parseCloudFrontLog(InputStream content) throws IOException{
		ANTLRInputStream inputStream = new ANTLRInputStream(content);

		return parseCloudFrontLog(inputStream);
	}

	private static List<CloudFrontWebLogEntry> parseCloudFrontLog(ANTLRInputStream content){

		ListVisitor listVisitor = new ListVisitor();
		parseCloudFrontLog(content, listVisitor);
		return listVisitor.getCf();
	}

	//////////
	// CloudFront Visitor-based methods
	//////////

	public static void parseCloudFrontLog(InputStream content, ICloudFrontLogVisitor visitor) throws IOException
	{
		ANTLRInputStream inputStream = new ANTLRInputStream(content);

		parseCloudFrontLog(inputStream, visitor);
	}

	private static void parseCloudFrontLog(ANTLRInputStream content, ICloudFrontLogVisitor visitor)
	{
		CloudFrontLogGrammarLexer lexer = new CloudFrontLogGrammarLexer(content);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		CloudFrontLogGrammarParser parser = new CloudFrontLogGrammarParser(tokens);

		parser.file(visitor);
	}
}
