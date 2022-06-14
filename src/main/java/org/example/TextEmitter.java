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

        int id = 122928933;
        String contestant = "Company Inc.";
        String cnpjContestant = "11.111.111/0001-11";
        String contestee = "Bad Company Inc.";
        String cnpjContestee = "11.111.111/0001-12";


        try {
            String payload = String.format(":exclamation: A CERC foi solicitada para realizar mediação " +
                    "na seguinte contestação:\n" +
                    "Contestacão *%s*.\n" +
                    "Contestante *%s* CNPJ *%s*\n" +
                    "Contestado *%s* CNPJ *%s*\n", Integer.toString(id), contestant, cnpjContestant,
                    contestee, cnpjContestee);
            ChatPostMessageResponse response = slack.methods(token).chatPostMessage(req -> req
                    .channel("C03KH0F7U3V") //id for #API
                    .text(payload));
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
