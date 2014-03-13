package org.apache.hupa.vaadin.actions;

import org.apache.hupa.shared.domain.ImapFolder;
import org.apache.hupa.shared.domain.MessageDetails;
import org.apache.hupa.shared.rpc.ContactsResult.Contact;
import org.apache.hupa.vaadin.hupa.HupaConnector;
import org.apache.hupa.vaadin.ui.HupaComposeScreen;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

@SuppressWarnings("serial")
public class ComposeActivity {
	
	private HupaComposeScreen display;
	private HupaConnector hupa;
	ImapFolder folder;
	MessageDetails details;
	BeanItemContainer<Contact> contactsContainer = new BeanItemContainer<Contact>(Contact.class);
	Window composeWindow;
	
	public ComposeActivity(HupaConnector hupaConnector, HupaComposeScreen hupaComposeScreen) {
		hupa = hupaConnector;
		display = hupaComposeScreen;
		composeWindow = new Window();
        composeWindow.setModal(true);
        composeWindow.setWidth("900px");
        composeWindow.setHeight("700px");
        composeWindow.setResizable(true);
		composeWindow.setContent(display);
		composeWindow.center();
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
		UI.getCurrent().addWindow(composeWindow);
	}

	private void bind() {
    }
}
