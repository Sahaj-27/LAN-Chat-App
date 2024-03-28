package lanChatApp;

import groupChat.GroupChat;
import javax.swing.SwingWorker;

public class HandleGroupChat extends SwingWorker<Void, Void> {

    @Override
    protected Void doInBackground() throws Exception {
        new GroupChat().showOptionsCreateServerJoinServer();
        return null;
    }
    
}