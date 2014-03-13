package org.apache.hupa.vaadin.actions;

import java.util.Date;
import java.util.Set;

import org.apache.hupa.shared.domain.FetchMessagesResult;
import org.apache.hupa.shared.domain.ImapFolder;
import org.apache.hupa.shared.domain.Message;
import org.apache.hupa.vaadin.hupa.HupaConnector;
import org.apache.hupa.vaadin.ui.HupaMainScreen;

import com.vaadin.data.Item;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Table;

@SuppressWarnings("serial")
public class MessageListActivity {
	
	private HupaMainScreen display;
	private HupaConnector hupa;
	private MessageActivity activity;
	private IndexedContainer container;
	private ImapFolder folder;
	
	public MessageListActivity(HupaConnector hupaConnector, HupaMainScreen hupaMainScreen, MessageActivity messageActivity) {
		hupa = hupaConnector;
		display = hupaMainScreen;
		activity = messageActivity;
		bind();
	}

	public void goTo(ImapFolder folder) {
		this.folder = folder;
		FetchMessagesResult msgs = hupa.fetchMessages(folder);
		Table sample = display.getTableMsgs();
		sample.setContainerDataSource(fillDataSource(msgs));
	}
	
    @SuppressWarnings("unchecked")
	private IndexedContainer fillDataSource(FetchMessagesResult msgs) {
    	container = new IndexedContainer();
        container.addContainerProperty("from", String.class, null);
        container.addContainerProperty("subject", String.class, null);
        container.addContainerProperty("date", Date.class, null);
        container.addContainerProperty("att", Resource.class, null);
        for (Message m: msgs.getMessages()) {
        	Item i = container.addItem(m);
        	i.getItemProperty("from").setValue(m.getFrom());
        	i.getItemProperty("subject").setValue(m.getSubject());
        	i.getItemProperty("date").setValue(m.getReceivedDate());
        	if (m.hasAttachment()) {
        		i.getItemProperty("att").setValue(new ThemeResource("../mytheme/img/clip.png"));
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
            @Override
            public void valueChange(final ValueChangeEvent event) {
            	@SuppressWarnings("unchecked")
				Set<Object> ids = (Set<Object>)event.getProperty().getValue();
            	if (ids.size() == 1) {
            		activity.goTo(folder, (Message)ids.iterator().next());
            	}
            }
        });
    }
}
