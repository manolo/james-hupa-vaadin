package org.apache.hupa.vaadin.actions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.hupa.shared.data.MessageImpl.IMAPFlag;
import org.apache.hupa.shared.domain.FetchMessagesResult;
import org.apache.hupa.shared.domain.ImapFolder;
import org.apache.hupa.shared.domain.Message;
import org.apache.hupa.vaadin.hupa.HupaConnector;
import org.apache.hupa.vaadin.ui.MainDisplay;
import org.apache.hupa.vaadin.ui.MessageListDisplay;

import com.vaadin.data.Item;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;

@SuppressWarnings("serial")
public class MessageListActivity implements Serializable {

    private MessageListDisplay display;
    private MainDisplay mainDisplay;
    private HupaConnector hupa;
    private MessageActivity activity;
    private IndexedContainer container;
    private ImapFolder folder;
    private Message message;

    public MessageListActivity(HupaConnector hupaConnector, MessageListDisplay listDisplay, MainDisplay hupaMainScreen, MessageActivity messageActivity) {
        hupa = hupaConnector;
        display = listDisplay;
        mainDisplay = hupaMainScreen;
        activity = messageActivity;
        bind();
    }

    public void goTo(ImapFolder folder) {
        this.folder = folder;
        reload();
    }

    public void reload() {
        FetchMessagesResult msgs = hupa.fetchMessages(folder, mainDisplay.getiSearch().getValue().trim());
        Table sample = display.getTableMsgs();
        sample.setContainerDataSource(fillDataSource(msgs));
        onSelectMessages();
    }

    public ImapFolder getFolder() {
        return folder;
    }

    public List<Message> getSelected() {
        @SuppressWarnings("unchecked")
        Set<Message> ids = (Set<Message>)display.getTableMsgs().getValue();
        return new ArrayList<Message>(ids);
    }

    @SuppressWarnings("unchecked")
    private IndexedContainer fillDataSource(FetchMessagesResult msgs) {
        container = new IndexedContainer();
        container.addContainerProperty("from", String.class, null);
        container.addContainerProperty("subject", Label.class, null);
        container.addContainerProperty("date", Date.class, null);
        container.addContainerProperty("att", Resource.class, null);
        for (Message m: msgs.getMessages()) {
            Item i = container.addItem(m);
            i.getItemProperty("from").setValue(m.getFrom());
            i.getItemProperty("date").setValue(m.getReceivedDate());
            Label l = m.getFlags().contains(IMAPFlag.SEEN) ?
                    new Label("<b>" + m.getSubject() + "</b>", ContentMode.HTML) :
                    new Label(m.getSubject());
            i.getItemProperty("subject").setValue(l);

            if (m.hasAttachment()) {
                i.getItemProperty("att").setValue(new ThemeResource("../hupa/img/clip.png"));
            }
        }
        return container;
    }

    private void bind() {
        Table t = display.getTableMsgs();
        t.setSizeFull();
        t.setSelectable(true);
        t.setMultiSelect(true);
        t.setImmediate(true);

        t.addValueChangeListener(new ValueChangeListener() {
            public void valueChange(final ValueChangeEvent event) {
                onSelectMessages();
            }
        });
    }

    @SuppressWarnings("unchecked")
    private void onSelectMessages() {
        List<Message> ids = getSelected();
        boolean isMultiple = false;
        boolean isSingle = false;
        switch (ids.size()) {
            case 0:
                break;
            case 1:
                message = ids.iterator().next();
                activity.goTo(folder, message);
                container.getItem(message).getItemProperty("subject").setValue(new Label(message.getSubject()));
                isSingle = true;
                break;
            default:
                isMultiple = true;
        }
        mainDisplay.getbReply().setEnabled(isSingle);
        mainDisplay.getbReplyAll().setEnabled(isSingle);
        mainDisplay.getbForward().setEnabled(isSingle);
        mainDisplay.getbSource().setEnabled(isSingle);
        mainDisplay.getbDelete().setEnabled(isSingle || isMultiple);
        mainDisplay.getbMark().setEnabled(isSingle || isMultiple);
    }
}
