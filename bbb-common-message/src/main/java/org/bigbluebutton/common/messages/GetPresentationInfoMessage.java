package org.bigbluebutton.common.messages;

import java.util.HashMap;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class GetPresentationInfoMessage implements IPublishedMessage {
	public static final String GET_PRESENTATION_INFO = "get_presentation_info";
	public static final String VERSION = "0.0.1";

	public final String meetingId;
	public final String requesterId;
	public final String replyTo;

	public GetPresentationInfoMessage(String meetingId, String requesterId, String replyTo) {
		this.meetingId = meetingId;
		this.requesterId = requesterId;
		this.replyTo = replyTo;
	}

	public String toJson() {
		HashMap<String, Object> payload = new HashMap<String, Object>();
		payload.put(Constants.MEETING_ID, meetingId);
		payload.put(Constants.REQUESTER_ID, requesterId);
		payload.put(Constants.REPLY_TO, replyTo);

		java.util.HashMap<String, Object> header = MessageBuilder.buildHeader(GET_PRESENTATION_INFO, VERSION, null);

		return MessageBuilder.buildJson(header, payload);
	}

	public static GetPresentationInfoMessage fromJson(String message) {
		JsonParser parser = new JsonParser();
		JsonObject obj = (JsonObject) parser.parse(message);

		if (obj.has("header") && obj.has("payload")) {
			JsonObject header = (JsonObject) obj.get("header");
			JsonObject payload = (JsonObject) obj.get("payload");

			if (header.has("name")) {
				String messageName = header.get("name").getAsString();
				if (GET_PRESENTATION_INFO.equals(messageName)) {
					if (payload.has(Constants.MEETING_ID) 
							&& payload.has(Constants.REQUESTER_ID)
							&& payload.has(Constants.REPLY_TO)) {
						String meetingId = payload.get(Constants.MEETING_ID).getAsString();
						String requesterId = payload.get(Constants.REQUESTER_ID).getAsString();
						String replyTo = payload.get(Constants.REPLY_TO).getAsString();

						return new GetPresentationInfoMessage(meetingId, requesterId, replyTo);
					}
				} 
			}
		}
		return null;
	}
}
