package com.fabrikam.dronedelivery.deliveryscheduler.scheduler.StorageQueue;

import com.fabrikam.dronedelivery.deliveryscheduler.scheduler.SchedulerSettings;
import com.microsoft.azure.storage.queue.CloudQueue;
import com.microsoft.azure.storage.queue.CloudQueueClient;
import com.microsoft.azure.storage.queue.CloudQueueMessage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class StorageQueueTest {

    private CloudQueueClient storageClient ;

    private String someStringContent;

//    This test will work only if environment variables are set
//    STORAGE_QUEUE_CONNECTION_STRING and STORAGE_QUEUE_NAME

    @Before
    public void setup() {

        SchedulerSettings.storageQueueConnectionString = System.getenv("STORAGE_QUEUE_CONNECTION_STRING");
        SchedulerSettings.storageQueueName = System.getenv("STORAGE_QUEUE_NAME");
        storageClient = StorageQueueClientFactory.get();
        someStringContent = "This is test message to queue";
    }

    @Test
    public void it_should_add_message_to_queue()  {

        //Arrange
        CloudQueueMessage queueMessage = new CloudQueueMessage(someStringContent);

        CloudQueue queueReference = null;
        try {

            queueReference = storageClient.getQueueReference(SchedulerSettings.storageQueueName);
            queueReference.createIfNotExists();


            //Act
            queueReference.addMessage(queueMessage);

            CloudQueueMessage peekMessage = queueReference.peekMessage();


            //Assert
            Assert.assertTrue(peekMessage.getMessageContentAsString().equalsIgnoreCase(someStringContent));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}
