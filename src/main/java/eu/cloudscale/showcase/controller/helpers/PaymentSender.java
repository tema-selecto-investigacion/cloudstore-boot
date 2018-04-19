package eu.cloudscale.showcase.controller.helpers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.jms.*;

@Component
public class PaymentSender {

    private static final Logger LOG = LoggerFactory.getLogger(PaymentSender.class);

    private String paymentDestinationQueue;
    private String paymentStatusQueue;
    private Connection connection;

    @Autowired
    public PaymentSender(Connection connection,
                         @Value("${cloudstore.jms.payment}") String paymentDestinationQueue,
                         @Value("${cloudstore.jms.payment-status}") String paymentStatusQueue) {
        this.connection = connection;
        this.paymentDestinationQueue = paymentDestinationQueue;
        this.paymentStatusQueue = paymentStatusQueue;
        try {
            this.connection.start();
        } catch (JMSException e) {
            LOG.error("Error when connecting to Queue ", e);
        }
    }

    public String sendPaymentDetails(String distribution, String attr1, String attr2, String attr3) throws JMSException{
        LOG.debug("Inside send payment details");

        Session session = this.connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue paymentQueue = session.createQueue(this.paymentDestinationQueue);
        Queue paymentStatusQueue = session.createQueue(this.paymentStatusQueue);

        MapMessage msg = session.createMapMessage();
        msg.setString("distribution", distribution);
        msg.setString("attr1", attr1);
        msg.setString("attr2", attr2);
        msg.setString("attr3", attr3);

//        TextMessage msg = session.createTextMessage(payment);
        msg.setJMSReplyTo(paymentStatusQueue);
        MessageProducer sender = session.createProducer(paymentQueue);
        sender.send(msg);
        LOG.debug("Message sent");

        String filter = "JMSCorrelationID = '"+ msg.getJMSMessageID() +"'";
        MessageConsumer receiver = session.createConsumer(paymentStatusQueue, filter);
        TextMessage resp = (TextMessage) receiver.receive();
        LOG.debug("Confirmation = " + resp.getText());
        
//        this.connection.close();

        return resp.getText();
    }

    


}
