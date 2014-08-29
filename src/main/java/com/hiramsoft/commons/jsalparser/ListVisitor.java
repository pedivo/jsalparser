package com.hiramsoft.commons.jsalparser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ihiram on 8/28/14.
 */
class ListVisitor implements IS3LogVisitor, ICloudFrontLogVisitor {

	public ListVisitor(){
		this.s3 = new ArrayList<S3LogEntry>();
		this.cf = new ArrayList<CloudFrontWebLogEntry>();
	}

	private List<S3LogEntry> s3;
	private List<CloudFrontWebLogEntry> cf;

	@Override
	public void accept(CloudFrontWebLogEntry entry) {
		cf.add(entry);
	}

	@Override
	public void accept(S3LogEntry entry) {
		s3.add(entry);
	}

	public List<S3LogEntry> getS3(){
		return this.s3;
	}

	public List<CloudFrontWebLogEntry> getCf(){
		return this.cf;
	}
}
