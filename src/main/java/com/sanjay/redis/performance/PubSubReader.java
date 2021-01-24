package com.sanjay.redis.performance;


import com.google.cloud.pubsub.v1.MessageReceiver;
import com.google.cloud.pubsub.v1.Subscriber;
import com.google.pubsub.v1.ProjectSubscriptionName;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class PubSubReader {
    public static void main(String... args) {
        String projectId = "spring-index-302215";
        String subscriptionId = "test1";
        String topicId = "subs1";

        receiveMessagesWithDeliveryAttemptsExample(
                projectId, subscriptionId);
    }

    public static void receiveMessagesWithDeliveryAttemptsExample(
            String projectId, String subscriptionId) {

        ProjectSubscriptionName subscriptionName =
                ProjectSubscriptionName.of(projectId, subscriptionId);

        // Instantiate an asynchronous message receiver.
        MessageReceiver receiver =
                (message, consumer) -> {
                    // Handle incoming message, then ack the received message.
                    System.out.println("Id: " + message.getMessageId());
                    System.out.println("Data: " + message.getData().toStringUtf8());
                    System.out.println("Delivery Attempt: " + Subscriber.getDeliveryAttempt(message));
                    consumer.ack();
                };

        Subscriber subscriber = null;
        try {
            subscriber = Subscriber.newBuilder(subscriptionName, receiver).build();
            // Start the subscriber.
            subscriber.startAsync().awaitRunning();
            System.out.printf("Listening for messages on %s:\n", subscriptionName.toString());
            // Allow the subscriber to run for 30s unless an unrecoverable error occurs.
            subscriber.awaitTerminated(30, TimeUnit.SECONDS);
        } catch (TimeoutException timeoutException) {
            // Shut down the subscriber after 30s. Stop receiving messages.
            subscriber.stopAsync();
        }
    }
}