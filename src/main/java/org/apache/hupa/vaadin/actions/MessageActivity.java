package org.apache.hupa.vaadin.actions;

import java.io.Serializable;

import org.apache.hupa.shared.SConsts;
import org.apache.hupa.shared.domain.ImapFolder;
import org.apache.hupa.shared.domain.Message;
import org.apache.hupa.shared.domain.MessageAttachment;
import org.apache.hupa.shared.domain.MessageDetails;
import org.apache.hupa.vaadin.hupa.HupaConnector;
import org.apache.hupa.vaadin.ui.HupaMainScreen;

import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.ExternalResource;
import com.vaadin.shared.ui.BorderStyle;
import com.vaadin.ui.Link;
import com.vaadin.ui.Table;

@SuppressWarnings("serial")
public class MessageActivity implements Serializable {
    
    private HupaMainScreen display;
    private HupaConnector hupa;
    private IndexedContainer container;

    private ImapFolder folder;
    private Message message;
    private MessageDetails details;
    
    public MessageActivity(HupaConnector hupaConnector, HupaMainScreen hupaMainScreen) {
        hupa = hupaConnector;
        display = hupaMainScreen;
        bind();
    }

    @SuppressWarnings("unchecked")
    public void goTo(ImapFolder f, Message m) {
        folder = f;
        message = m;
        
        details = hupa.loadMessage(folder, message);
        display.getTextMsg().setReadOnly(false);
        display.getTextMsg().setValue(details.getText());
        display.getTextMsg().setReadOnly(true);
        
        Table t = display.getTableAttachments();
        boolean hasAttach = details.getMessageAttachments().size() > 0;
        t.setVisible(hasAttach);
        if (hasAttach) {
               container = new IndexedContainer();
            container.addContainerProperty("attachments", Link.class, null);
            for (MessageAttachment a : details.getMessageAttachments()) {
                Item i = container.addItem(a.getName());
                ExternalResource resource = new ExternalResource(
                        getDownloadAttachUrl(a.getName(), folder.getFullName(),message.getUid()));
                Link link = new Link(a.getName(), resource, "_blank", 800, 400, BorderStyle.NONE);
                i.getItemProperty("attachments").setValue(link);
            }
        }
        t.setContainerDataSource(container);
    }
    

    public ImapFolder getFolder() {
        return folder;
    }

    public Message getMessage() {
        return message;
    }    
    
    public MessageDetails getDetails() {
        return details;
    }

    private void bind() {
    }
    
    private static String getDownloadAttachUrl(String name, String folder, long uid) {
        return SConsts.SERVLET_DOWNLOAD + "?" + SConsts.PARAM_NAME + "="
                + name + "&" + SConsts.PARAM_FOLDER + "=" + folder + "&" + SConsts.PARAM_UID
                + "=" + uid + "&" + SConsts.PARAM_MODE + "=inline";
    }
    
    protected static String getSourceAttachUrl(String folder, long uid) {
        return SConsts.SERVLET_SOURCE + "?" + SConsts.PARAM_UID + "="
                + uid + "&" + SConsts.PARAM_FOLDER + "=" + folder;
    }
}
