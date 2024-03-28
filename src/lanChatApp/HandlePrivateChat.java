package lanChatApp;

import javax.swing.SwingWorker;
import privateChat.PrivateChat;

public class HandlePrivateChat extends SwingWorker<Void, Void> {

    @Override
    protected Void doInBackground() throws Exception {
        new PrivateChat().showOptionsCreateServerJoinServer();
        return null;
    }
    
}
