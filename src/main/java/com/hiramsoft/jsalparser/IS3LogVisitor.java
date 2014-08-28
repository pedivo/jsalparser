package com.hiramsoft.jsalparser;

/**
 * Created by ihiram on 8/28/14.
 */
public interface IS3LogVisitor {

	void accept(S3LogEntry entry);
}
