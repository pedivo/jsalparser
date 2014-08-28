package com.hiramsoft.jsalparser;

/**
 * Created by ihiram on 8/28/14.
 */
public interface ICloudFrontLogVisitor {
	void accept(CloudFrontWebLogEntry entry);
}
