package org.apache.hupa.vaadin.actions;

import java.io.Serializable;

import org.apache.hupa.shared.domain.ImapFolder;
import org.apache.hupa.shared.domain.MessageDetails;
import org.apache.hupa.shared.rpc.ContactsResult.Contact;
import org.apache.hupa.vaadin.hupa.HupaConnector;
import org.apache.hupa.vaadin.ui.HupaComposeScreen;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

@SuppressWarnings("serial")
public class ComposeActivity implements Serializable {
	
	private HupaComposeScreen display;
	private HupaConnector hupa;
	ImapFolder folder;
	MessageDetails details;
	BeanItemContainer<Contact> contactsContainer = new BeanItemContainer<Contact>(Contact.class);
	Window window;
	
	public ComposeActivity(HupaConnector hupaConnector, HupaComposeScreen hupaComposeScreen) {
		hupa = hupaConnector;
		display = hupaComposeScreen;
		window = new Window();
        window.setModal(true);
        window.setWidth("900px");
        window.setHeight("700px");
        window.setResizable(true);
		window.setContent(display);
		window.center();
		bind();
	}

	public void goTo(ImapFolder f, MessageDetails m) {
		folder = f;
		details = m;
		
		contactsContainer.removeAllItems();
		for (Contact c : hupa.getUserStorage().getContacts()) {
			contactsContainer.addBean(c);
		}		

		display.getTfTo().setContainerDataSource(contactsContainer);
		display.getTfCc().setContainerDataSource(contactsContainer);
		display.getTfBcc().setContainerDataSource(contactsContainer);
		UI.getCurrent().addWindow(window);
	}

	private void bind() {
    }
}
