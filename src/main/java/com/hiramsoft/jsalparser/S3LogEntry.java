package com.hiramsoft.jsalparser;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;

/**
 * Created by ihiram on 8/27/14.
 */
public class S3LogEntry {
	// http://docs.aws.amazon.com/AmazonS3/latest/dev/LogFormat.html

	// [27/Aug/2014:20:19:42 +0000]
	private static final DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MMM/yyyy:HH:mm:ss Z").withZoneUTC();

	public S3LogEntry(){
		this.extras = new ArrayList<String>();
	}

	private String bucketOwner;
	private String bucket;
	private DateTime time;
	private String remoteIpAddress;
	private String requester;
	private String requestId;
	private String operation;
	private String key;
	private String requestUri;
	private int httpStatus;
	private String errorCode;
	private long bytesSent;
	private long objectSize;
	private long totalTime;
	private long turnAroundTime;
	private String referrer;
	private String userAgent;
	private String versionId;

	public ArrayList<String> getExtras() {
		return extras;
	}

	private ArrayList<String> extras;

	public String getBucketOwner() {
		return bucketOwner;
	}

	public void setBucketOwner(String bucketOwner) {
		this.bucketOwner = bucketOwner;
	}

	public String getBucket() {
		return bucket;
	}

	public void setBucket(String bucket) {
		this.bucket = bucket;
	}

	public DateTime getTime() {
		return time;
	}

	public void setTime(DateTime time) {
		this.time = time;
	}

	// convenience so we don't have to put the format into the
	public void parseTime(String input){
		this.setTime(
			formatter.parseDateTime(input)
		);
	}

	public String getRemoteIpAddress() {
		return remoteIpAddress;
	}

	public void setRemoteIpAddress(String remoteIpAddress) {
		this.remoteIpAddress = remoteIpAddress;
	}

	public String getRequester() {
		return requester;
	}

	public void setRequester(String requester) {
		this.requester = requester;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getRequestUri() {
		return requestUri;
	}

	public void setRequestUri(String requestUri) {
		this.requestUri = requestUri;
	}

	public int getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(int httpStatus) {
		this.httpStatus = httpStatus;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public long getBytesSent() {
		return bytesSent;
	}

	public void setBytesSent(long bytesSent) {
		this.bytesSent = bytesSent;
	}

	public long getObjectSize() {
		return objectSize;
	}

	public void setObjectSize(long objectSize) {
		this.objectSize = objectSize;
	}

	public long getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(long totalTime) {
		this.totalTime = totalTime;
	}

	public long getTurnAroundTime() {
		return turnAroundTime;
	}

	public void setTurnAroundTime(long turnAroundTime) {
		this.turnAroundTime = turnAroundTime;
	}

	public String getReferrer() {
		return referrer;
	}

	public void setReferrer(String referrer) {
		this.referrer = referrer;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getVersionId() {
		return versionId;
	}

	public void setVersionId(String versionId) {
		this.versionId = versionId;
	}

}
