package bgu.spl.net.impl.stomp.frames;

import bgu.spl.net.impl.stomp.StompMessagingProtocolImpl;
import bgu.spl.net.impl.stomp.User;
import bgu.spl.net.impl.stomp.UsersControl;
import bgu.spl.net.srv.Connections;

public class BookAddMsg implements Message {
    //------------------- start edit 7/1 ------------------------
    private String destination_topic;
    private String book_name;
    private String orig_msg_from_client;
    //------------------- end edit 7/1 --------------------------

    public BookAddMsg(String destination_topic, String book_name, String orig_msg_from_client) {
        //------------------- start edit 7/1 ------------------------
        this.destination_topic=destination_topic;
        this.book_name=book_name;
        this.orig_msg_from_client=orig_msg_from_client;
        //------------------- end edit 7/1 --------------------------
    }

    @Override
    public void process(int connectionID, Connections connections) {
        //------------------- start edit 7/1 ------------------------
        User curr_user = UsersControl.getInstance().getUserByConnectionId(connectionID);
        if(!curr_user.getConnected_successfully()){
            connections.send(connectionID, new ErrorMsg("","Didn't logged in",
                    orig_msg_from_client,"The user isn't logged in yet."));
        }
        else {
            //Integer userTopicSubNumber = UsersControl.getInstance().getUserByConnectionId(connectionID).get_SubNum_by_TopicName(destination_topic);
            String userName = curr_user.getName();
            //if (userTopicSubNumber != null) {
                connections.send(destination_topic, new AcknowledgeMsg(
                        "MESSAGE\n" +
                                "subscription:" + "$" + "\n" +               // 00 is general number that
                                "Message-id:" + StompMessagingProtocolImpl.getNewMessageId() + "\n" +
                                "destination:" + destination_topic + "\n\n" +
                                userName + " has added the book " + book_name + "\n" +
                                "\u0000"));
            //}
        }
        //------------------- end edit 7/1 --------------------------
    }

    @Override
    public String getMessageData() {
        return null;
    }
}
