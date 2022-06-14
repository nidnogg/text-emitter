package org.example;

import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import com.slack.api.model.Message;

import java.io.IOException;

public class TextEmitter {
    public static void main(String[] args) {
        Slack slack = Slack.getInstance();
        // Load an env variable
        // If the token is a bot token, it starts with `xoxb-` while if it's a user token, it starts with `xoxp-`
        String token = System.getenv("SLACK_TOKEN");
        System.out.println("current token " + token);

        try {
            ChatPostMessageResponse response = slack.methods(token).chatPostMessage(req -> req
                    .channel("#api")
                    .text(":wave: hello world"));
            if (response.isOk()) {
                Message postedMessage = response.getMessage();
                System.out.println(postedMessage);
            } else {
                String errorCode = response.getError(); // e.g., "invalid_auth", "channel_not_found"
                System.out.println("Error: " + errorCode);
            }
        } catch (SlackApiException requestFailure) {
            System.out.println(requestFailure);
            // Slack API responded with unsuccessful status code (= not 20x)
        } catch (IOException connectivityIssue) {
            System.out.println(connectivityIssue);
            // Throwing this exception indicates your app or Slack servers had a connectivity issue.
        }
    }
}
