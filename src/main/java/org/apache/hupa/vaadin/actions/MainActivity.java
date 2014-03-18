package org.apache.hupa.vaadin.actions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.hupa.shared.data.DeleteMessageByUidActionImpl;
import org.apache.hupa.shared.domain.DeleteMessageByUidAction;
import org.apache.hupa.shared.domain.GenericResult;
import org.apache.hupa.shared.domain.Message;
import org.apache.hupa.vaadin.actions.ComposeActivity.Action;
import org.apache.hupa.vaadin.hupa.HupaConnector;
import org.apache.hupa.vaadin.ui.HupaMainScreen;
import org.vaadin.dialogs.ConfirmDialog;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutListener;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;

@SuppressWarnings("serial")
public class MainActivity implements Serializable {
    
    private HupaMainScreen display;
    private HupaConnector hupa;
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
        display.getlUser().setValue(hupa.getUser().getName());
        folders.goTo();
    }

    private void bind() {
        display.getbLogout().addClickListener(new ClickListener() {
            public void buttonClick(ClickEvent event) {
                hupa.doLogout();
                UI.getCurrent().getSession().close();
                UI.getCurrent().getPage().setLocation("");
            }
        });

        display.getiSearch().setImmediate(true);
        display.getiSearch().addShortcutListener(
            new ShortcutListener("Search", ShortcutAction.KeyCode.ENTER, null) {
                public void handleAction(Object sender, Object target) {
                    msgList.reload();
                }
        });
        display.getiSearch().addValueChangeListener(new ValueChangeListener() {
            public void valueChange(ValueChangeEvent event) {
                msgList.reload();
            }
        });
        
        display.getbReload().addClickListener(new ClickListener() {
            public void buttonClick(ClickEvent event) {
                msgList.reload();
            }
        });
        display.getbCompose().addClickListener(new ClickListener() {
            public void buttonClick(ClickEvent event) {
                compose.goTo(message.getFolder(), message.getMessage(), message.getDetails(), Action.COMPOSE);
            }
        });
        display.getbReply().addClickListener(new ClickListener() {
            public void buttonClick(ClickEvent event) {
                compose.goTo(message.getFolder(), message.getMessage(), message.getDetails(), Action.REPLY);
            }
        });
        display.getbReplyAll().addClickListener(new ClickListener() {
            public void buttonClick(ClickEvent event) {
                compose.goTo(message.getFolder(), message.getMessage(), message.getDetails(), Action.REPLY_ALL);
            }
        });
        display.getbForward().addClickListener(new ClickListener() {
            public void buttonClick(ClickEvent event) {
                compose.goTo(message.getFolder(), message.getMessage(), message.getDetails(), Action.FORWARD);
            }
        });
        display.getbSource().addClickListener(new ClickListener() {
            public void buttonClick(ClickEvent event) {
                Notification.show("View source: unimplemented yet.");
            }
        });
        display.getbDelete().addClickListener(new ClickListener() {
            public void buttonClick(ClickEvent event) {
                final List<Long> uids = new ArrayList<Long>();
                for (Message m : msgList.getSelected()) {
                    uids.add(m.getUid());
                }
                if (uids.size() > 0) {
                    ConfirmDialog.show(UI.getCurrent(), "Do you want to delete " + uids.size() + " messages?", new ConfirmDialog.Listener() {
                        public void onClose(ConfirmDialog dlg) {
                            if (dlg.isConfirmed()) {
                                DeleteMessageByUidAction action = new DeleteMessageByUidActionImpl();
                                action.setFolder(msgList.getFolder());
                                action.setMessageUids(uids);
                                GenericResult r = hupa.deleteMessages(action);
                                if (r.isSuccess()) {
                                    Notification.show(uids.size() + " messages where removed", Type.TRAY_NOTIFICATION);
                                } else {
                                    Notification.show("Error removing messages", r.getMessage(), Type.ERROR_MESSAGE);
                                }                      
                                msgList.reload();
                            }
                        }
                    });
                    
                }
            }
        });
        display.getbMark().addClickListener(new ClickListener() {
            public void buttonClick(ClickEvent event) {
                Notification.show("Mark messages: unimplemented yet. " + msgList.getSelected());
            }
        });
    }
}
