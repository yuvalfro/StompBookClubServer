package bgu.spl.net.impl.stomp.frames;

import bgu.spl.net.impl.stomp.StompMessagingProtocolImpl;
import bgu.spl.net.impl.stomp.UsersControl;
import bgu.spl.net.srv.Connections;

public class BookBorrowAsk implements Message {
    //------------------- start edit 7/1 ------------------------
    private String destination_topic;
    private String bookname;
    private String orig_msg_from_client;
    //------------------- end edit 7/1 --------------------------

    public BookBorrowAsk(String destination_topic, String bookname, String orig_msg_from_client){
        //------------------- start edit 7/1 ------------------------
        this.destination_topic=destination_topic;
        this.bookname=bookname;
        this.orig_msg_from_client=orig_msg_from_client;
        //------------------- end edit 7/1 --------------------------
    }

    @Override
    public void process(int connectionID, Connections connections) {
        //------------------- start edit 7/1 ------------------------
        Integer userTopicSubNumber = UsersControl.getInstance().getUserByConnectionId(connectionID).get_SubNum_by_TopicName(destination_topic);
        String userName = UsersControl.getInstance().getUserByConnectionId(connectionID).getName();
        connections.send(destination_topic, new AcknowledgeMsg(
                "MESSAGE\n" +
                        "subscription:" + userTopicSubNumber + "\n" +
                        "Message-id:" + StompMessagingProtocolImpl.getNewMessageId() + "\n" +
                        "destination:" + destination_topic + "\n\n" +

                        userName+" wish to borrow "+bookname+"\n"+

                        "^@"));                     // sending a message: borrow query
        //------------------- end edit 7/1 --------------------------
    }

    @Override
    public String getMessageData() {
        return null;
    }
}