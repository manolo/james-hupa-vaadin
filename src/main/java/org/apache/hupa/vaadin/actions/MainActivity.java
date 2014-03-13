package org.apache.hupa.vaadin.actions;

import org.apache.hupa.shared.data.ImapFolderImpl;
import org.apache.hupa.vaadin.hupa.HupaConnector;
import org.apache.hupa.vaadin.ui.HupaMainScreen;
import org.hibernate.validator.util.privilegedactions.GetAnnotationParameter;

import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.UI;

@SuppressWarnings("serial")
public class MainActivity {
	
	private HupaMainScreen display;
	private HupaConnector hupa;
	private IndexedContainer container;
	private FoldersActivity folders;
	private MessageListActivity msgList;
	private MessageActivity message;
	private ComposeActivity compose;
	
	public MainActivity(HupaConnector hupaConnector, HupaMainScreen hupaMainScreen,
			FoldersActivity foldersActivity, MessageListActivity messageListActivity,
			MessageActivity messageActivity, ComposeActivity composeActivity) {
		
		hupa = hupaConnector;
		display = hupaMainScreen;
		folders = foldersActivity;
		msgList = messageListActivity;
		message = messageActivity;
		compose = composeActivity;
		bind();
	}

	public void goTo() {
		UI.getCurrent().setContent(display);
		msgList.goTo(new ImapFolderImpl("INBOX"));
	}

	private void bind() {
		display.getbCompose().addClickListener(new ClickListener() {
			public void buttonClick(ClickEvent event) {
				compose.goTo(null, null);
			}
		});
		display.getbLogout().addClickListener(new ClickListener() {
			public void buttonClick(ClickEvent event) {
				hupa.doLogout();
				UI.getCurrent().getSession().close();
				UI.getCurrent().getPage().setLocation("");
			}
		});
    }
	
}
