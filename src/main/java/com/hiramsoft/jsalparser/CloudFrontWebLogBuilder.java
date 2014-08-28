package com.hiramsoft.jsalparser;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ihiram on 8/27/14.
 *
 * Because of the header line, we have to process each line according to the simple schema defined.
 * One of the fastest ways I know to write this is by using a builder pattern.
 *
 * This class is used only by the ANTLR grammar.
 */
class CloudFrontWebLogBuilder {

	public CloudFrontWebLogBuilder(){
		this.headers = new ArrayList<String>();
		this.builders = new ArrayList<IValueBuilder>();
		this.position = 0;
	}

	private ArrayList<String> headers;
	private ArrayList<IValueBuilder> builders;
	private int position;
	private CloudFrontWebLogEntry entry;

	public void acceptVersion(String version){
		// No-op since there is only one version
	}

	public void reset(){
		this.headers.clear();
		this.builders.clear();
		this.position = 0;
		this.entry = null;
	}

	public void acceptHeader(String header){
		this.headers.add(header);
		if(VALUE_BUILDERS.containsKey(header))
		{
			this.builders.add(VALUE_BUILDERS.get(header));
		}
		else
		{
			this.builders.add(ExtraBuilder);
		}
	}

	public void acceptValue(String value){
		if(this.entry == null)
		{
			this.entry = new CloudFrontWebLogEntry();
			this.position = 0;
		}
		if(this.position < this.builders.size())
		{
			this.builders.get(this.position).accept(value, this.entry);
		}
		else
		{
			ExtraBuilder.accept(value, this.entry);
		}
		this.position++;
	}

	public void skipValue(){
		this.position++;
	}

	public CloudFrontWebLogEntry buildEntry(){

		if(entry.dateHolder == null && entry.timeHolder == null)
		{
			// do nothing since no date or time information was provided
		}
		else if (entry.dateHolder != null && entry.timeHolder == null)
		{
			// we have only a date and assume midnight (in UTC time)
			String dateTimeString = entry.dateHolder + " 00:00:00" + " +0000";
			DateTime dt = dateAndTimeFormatter.parseDateTime(dateTimeString);
			entry.setDateTime(dt);
		}
		else if (entry.dateHolder != null && entry.timeHolder != null)
		{
			// we have both a date and a time (in UTC time)
			String dateTimeString = entry.dateHolder + " " + entry.timeHolder + " +0000";
			DateTime dt = dateAndTimeFormatter.parseDateTime(dateTimeString);
			entry.setDateTime(dt);
		}
		else if (entry.dateHolder == null && entry.timeHolder != null)
		{
			// do nothing, we need a date at a minimum
		}

		CloudFrontWebLogEntry retEntry = this.entry;
		this.entry = null;
		this.position = 0;

		return retEntry;
	}

	private static final DateTimeFormatter dateAndTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss Z").withZoneUTC();

	private static final Map<String, IValueBuilder> VALUE_BUILDERS = new HashMap<String, IValueBuilder>();

	private static final IValueBuilder ExtraBuilder = new IValueBuilder() {
		@Override
		public void accept(String value, CloudFrontWebLogEntry entry) {
			entry.getExtras().add(value);
		}
	};

	static {
		VALUE_BUILDERS.put("date", new IValueBuilder() {
			@Override
			public void accept(String value, CloudFrontWebLogEntry entry) {
				entry.dateHolder = value;
			}
		});
		VALUE_BUILDERS.put("time", new IValueBuilder() {
			@Override
			public void accept(String value, CloudFrontWebLogEntry entry) {
				entry.timeHolder = value;
			}
		});
		VALUE_BUILDERS.put("x-edge-location", new IValueBuilder() {
			@Override
			public void accept(String value, CloudFrontWebLogEntry entry) {
				entry.setxEdgeLocation(value);
			}
		});
		VALUE_BUILDERS.put("sc-bytes", new IValueBuilder() {
			@Override
			public void accept(String value, CloudFrontWebLogEntry entry) {
				entry.setServerToClientBytes(Long.parseLong(value));
			}
		});
		VALUE_BUILDERS.put("c-ip", new IValueBuilder() {
			@Override
			public void accept(String value, CloudFrontWebLogEntry entry) {
				entry.setClientIpAddress(value);
			}
		});
		VALUE_BUILDERS.put("cs-method", new IValueBuilder() {
			@Override
			public void accept(String value, CloudFrontWebLogEntry entry) {
				entry.setClientToServerMethod(value);
			}
		});
		VALUE_BUILDERS.put("cs(Host)", new IValueBuilder() {
			@Override
			public void accept(String value, CloudFrontWebLogEntry entry) {
				entry.setClientToServerHost(value);
			}
		});
		VALUE_BUILDERS.put("cs-uri-stem", new IValueBuilder() {
			@Override
			public void accept(String value, CloudFrontWebLogEntry entry) {
				entry.setClientToServerUriStem(value);
			}
		});
		VALUE_BUILDERS.put("cs-uri-query", new IValueBuilder() {
			@Override
			public void accept(String value, CloudFrontWebLogEntry entry) {
				entry.setClientToServerQueryString(value);
			}
		});
		VALUE_BUILDERS.put("sc-status", new IValueBuilder() {
			@Override
			public void accept(String value, CloudFrontWebLogEntry entry) {
				entry.setServerToClientStatus(Integer.parseInt(value));
			}
		});
		VALUE_BUILDERS.put("cs(Referer)", new IValueBuilder() {
			@Override
			public void accept(String value, CloudFrontWebLogEntry entry) {
				entry.setClientToServerReferrer(value);
			}
		});
		VALUE_BUILDERS.put("cs(User-Agent)", new IValueBuilder() {
			@Override
			public void accept(String value, CloudFrontWebLogEntry entry) {
				entry.setClientToServerUserAgent(value);
			}
		});
		VALUE_BUILDERS.put("cs(Cookie)", new IValueBuilder() {
			@Override
			public void accept(String value, CloudFrontWebLogEntry entry) {
				entry.setClientToServerCookies(value);
			}
		});
		VALUE_BUILDERS.put("x-edge-result-type", new IValueBuilder() {
			@Override
			public void accept(String value, CloudFrontWebLogEntry entry) {
				entry.setxEdgeResultType(value);
			}
		});
		VALUE_BUILDERS.put("x-edge-request-id", new IValueBuilder() {
			@Override
			public void accept(String value, CloudFrontWebLogEntry entry) {
				entry.setxEdgeRequestId(value);
			}
		});
		VALUE_BUILDERS.put("x-host-header", new IValueBuilder() {
			@Override
			public void accept(String value, CloudFrontWebLogEntry entry) {
				entry.setxHostHeader(value);
			}
		});
		VALUE_BUILDERS.put("cs-protocol", new IValueBuilder() {
			@Override
			public void accept(String value, CloudFrontWebLogEntry entry) {
				entry.setClientToServerIsHttps(value.equals("https"));
			}
		});
		VALUE_BUILDERS.put("cs-bytes", new IValueBuilder() {
			@Override
			public void accept(String value, CloudFrontWebLogEntry entry) {
				entry.setClientToServerBytes(Long.parseLong(value));
			}
		});
		VALUE_BUILDERS.put("time-taken", new IValueBuilder() {
			@Override
			public void accept(String value, CloudFrontWebLogEntry entry) {
				// Every other time unit is in millis, so this one should be, too.
				Double partialSeconds = Double.parseDouble(value);
				entry.setTimeTakenMilliSeconds((long) (1000 * partialSeconds));
			}
		});
	}
}
