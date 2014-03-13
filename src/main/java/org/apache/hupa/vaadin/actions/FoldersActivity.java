package org.apache.hupa.vaadin.actions;

import java.util.List;

import org.apache.hupa.shared.domain.ImapFolder;
import org.apache.hupa.vaadin.hupa.HupaConnector;
import org.apache.hupa.vaadin.ui.HupaMainScreen;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.ui.UI;


@SuppressWarnings("serial")
public class FoldersActivity {
	
	private HupaMainScreen display;
	private HupaConnector hupa;
	private MessageListActivity activity;
	
	public FoldersActivity(HupaConnector hupaConnector, HupaMainScreen hupaMainScreen, MessageListActivity messageListActivity) {
		hupa = hupaConnector;
		display = hupaMainScreen;
		activity = messageListActivity;
		bind();
	}

	public void goTo() {
		UI.getCurrent().setContent(display);
		List<ImapFolder> folders = hupa.fetchFolders();
		
		HierarchicalContainer container = new HierarchicalContainer();
		fillContainer(container, folders, null);
		display.getTreeFolders().setContainerDataSource(container);
	}
	
	private void fillContainer(HierarchicalContainer container, List<ImapFolder> list, ImapFolder current) {
		for (ImapFolder f : list) {
			container.addItem(f);
			if (current != null) {
				container.setParent(f, current);
			}
			if (f.getHasChildren() && f.getChildren().size() > 0) {
				fillContainer(container, f.getChildren(), f);
			} else {
				container.setChildrenAllowed(f, false);
			}
		}
	}
	
    private void bind() {
    	display.getTreeFolders().setImmediate(true);
    	display.getTreeFolders().addValueChangeListener(new ValueChangeListener() {
            public void valueChange(final ValueChangeEvent event) {
            	ImapFolder f = (ImapFolder)event.getProperty().getValue();
            	activity.goTo(f);
            }
        });
    }
}
