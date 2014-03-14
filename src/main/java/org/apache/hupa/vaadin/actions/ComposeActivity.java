package org.apache.hupa.vaadin.actions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.hupa.shared.Util;
import org.apache.hupa.shared.data.SendForwardMessageActionImpl;
import org.apache.hupa.shared.data.SendMessageActionImpl;
import org.apache.hupa.shared.data.SendReplyMessageActionImpl;
import org.apache.hupa.shared.data.SmtpMessageImpl;
import org.apache.hupa.shared.domain.GenericResult;
import org.apache.hupa.shared.domain.ImapFolder;
import org.apache.hupa.shared.domain.Message;
import org.apache.hupa.shared.domain.MessageDetails;
import org.apache.hupa.shared.domain.SendForwardMessageAction;
import org.apache.hupa.shared.domain.SendMessageAction;
import org.apache.hupa.shared.domain.SendReplyMessageAction;
import org.apache.hupa.shared.domain.SmtpMessage;
import org.apache.hupa.shared.rpc.ContactsResult.Contact;
import org.apache.hupa.vaadin.hupa.HupaConnector;
import org.apache.hupa.vaadin.ui.HupaComposeScreen;
import org.vaadin.tokenfield.TokenField;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

@SuppressWarnings("serial")
public class ComposeActivity implements Serializable {
    
    public static enum Action {COMPOSE, REPLY, REPLY_ALL, FORWARD}
    private HupaComposeScreen display;
    private HupaConnector hupa;
    private ImapFolder folder;
    private MessageDetails details;
    private Action action;
    private BeanItemContainer<Contact> contactsContainer = new BeanItemContainer<Contact>(Contact.class);
    private Window window;
    
    private Message message;
    
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

    private void bind() {
        display.getbSend().addClickListener(new ClickListener() {
            public void buttonClick(ClickEvent event) {
                send();
            }
        });
        display.getbCancel().addClickListener(new ClickListener() {
            public void buttonClick(ClickEvent event) {
                UI.getCurrent().removeWindow(window);
            }
        });
    }

    public void goTo(ImapFolder f, Message m, MessageDetails d, Action a) {
        folder = f;
        message = m;
        details = d;
        action = a;
        
        UI.getCurrent().addWindow(window);
        fillContactLists();
        
        if (hupa.getUser() != null)
            display.gettFrom().setValue(hupa.getUser().getName());
        
        String subject = message != null && message.getSubject() != null ? message.getSubject().trim() : "";
        
        switch (a) {
            case COMPOSE:
                display.gettSubject().setValue("");
                display.gettBody().setValue("");
                return;
            case REPLY:
                if (!subject.toLowerCase().startsWith("re:")) {
                    subject = "Re: " + subject;
                }
                if (message.getReplyto() != null && !message.getFrom().contains(message.getReplyto())) {
                    display.getTfTo().addToken(new Contact(message.getReplyto()));
                } else {
                    display.getTfTo().addToken(new Contact(message.getFrom()));
                }
                break;
            case REPLY_ALL:
                if (!subject.toLowerCase().startsWith("re:")) {
                    subject = "Re: " + subject;
                }                
                ArrayList<String> list = new ArrayList<String>();
                if (message.getReplyto() != null && !message.getFrom().contains(message.getReplyto()))
                    list.add(message.getReplyto());
                if (message.getTo() != null)
                    list.addAll(message.getTo());
                if (message.getCc() != null)
                    list.addAll(message.getCc());
                list = removeEmailFromList(list, hupa.getUser().getName());
                for (String email : list) {
                    display.getTfCc().addToken(new Contact(email));
                }
                display.getTfTo().addToken(new Contact(message.getFrom()));                
                break;
            case FORWARD:
                if (!subject.toLowerCase().startsWith("fwd:")) {
                    subject = "Fwd: " + subject;
                }
                break;
        }
        
        display.gettSubject().setValue(subject);
        display.gettBody().setValue(wrapMessage());
    }
    
    private void send() {
        SmtpMessage smtpMessage = parseMessage();
        GenericResult r;
        if (action == Action.COMPOSE) {
            SendMessageAction sendAction = new SendMessageActionImpl(smtpMessage);
            r = hupa.sendMessage(sendAction);
        } else if (action == Action.FORWARD) {
            SendForwardMessageAction sendAction = new SendForwardMessageActionImpl();
            sendAction.setReferences(details.getReferences());
            sendAction.setMessage(smtpMessage);
            sendAction.setFolder(folder);
            sendAction.setUid(message.getUid());
            r = hupa.sendForwardMessage(sendAction);
        } else {
            SendReplyMessageAction sendAction = new SendReplyMessageActionImpl();
            sendAction.setReferences(details.getReferences());
            sendAction.setMessage(smtpMessage);
            sendAction.setFolder(folder);
            sendAction.setUid(message.getUid());
            r = hupa.sendReplyMessage(sendAction);
        }
        if (r.isSuccess()) {
            Notification.show("Message sent", Type.TRAY_NOTIFICATION);
            display.getbCancel().click();
        } else {
            Notification.show("Error sending message", r.getMessage(), Type.ERROR_MESSAGE);
        }
    }

    private void fillContactLists() {
        contactsContainer.removeAllItems();
        for (Contact c : hupa.getUserStorage().getContacts()) {
            contactsContainer.addBean(c);
        }        
        display.getTfTo().setContainerDataSource(contactsContainer);
        display.getTfCc().setContainerDataSource(contactsContainer);
        display.getTfBcc().setContainerDataSource(contactsContainer);
    }

    private String generateHeader() {
        String ret = "<br>";
        if (message != null) {
            if (action == Action.FORWARD) {
                ret += "--------- Forwarded message --------- <br>";
                ret += "From: " + message.getFrom().replaceAll("<", "&lt;").replaceAll(">", "&gt;") + "<br>";
                ret += "Date: " + message.getReceivedDate() + "<br>";
                ret += "Subject: " + message.getSubject() + "<br>";
                ArrayList<String> to = new ArrayList<String>();
                to.addAll(message.getTo());
                to.addAll(message.getCc());
                ret += "To: " + Util.listToString(to).replaceAll("<", "&lt;").replaceAll(">", "&gt;") + "<br>";
            } else if (action == Action.REPLY || action == Action.REPLY_ALL) {
                ret += "On " + message.getReceivedDate();
                ret += ", " + message.getFrom().replaceAll("<", "&lt;").replaceAll(">", "&gt;");
                ret += ". wrote:<br>";
            }
        }
        return ret + "<br>";
    }

    private SmtpMessage parseMessage() {
        SmtpMessage message = new SmtpMessageImpl();

        message.setFrom(hupa.getUser().getName());
        message.setSubject(display.gettSubject().getValue());
        message.setText(display.gettBody().getValue());
        
        message.setTo(tokenFieldValueToList(display.getTfTo()));
        message.setCc(tokenFieldValueToList(display.getTfCc()));
        message.setBcc(tokenFieldValueToList(display.getTfBcc()));
        return message;
    }
    
    private ArrayList<String> removeEmailFromList(List<String> list, String email) {
        ArrayList<String> ret = new ArrayList<String>();
        String regex = ".*<?\\s*" + email.trim() + "\\s*>?\\s*";
        for (String e : list) {
            if (!e.matches(regex)) {
                ret.add(e);
            }
        }
        return ret;
    }
    
    private List<String> tokenFieldValueToList(TokenField f) {
        List<String> list = new ArrayList<String>();
        Set<?> contacts = (Set<?>)f.getValue();
        if (contacts != null) {
            for (Object c : contacts) {
                if (c instanceof Contact) {
                    list.add(((Contact)c).toIsoAddress());    
                } else {
                    list.add(c.toString());
                }
            }
        }
        return list;
    }
    
    public String wrapMessage() {
        String ret = "";
        if (message != null) {
            ret += generateHeader();
        }
        if (details != null && details.getText() != null && details.getText().length() > 0) {
            ret += "<blockquote style='border-left: 1px solid rgb(204, 204, 204); margin: 0pt 0pt 0pt 0.8ex; padding-left: 1ex;'>";
            ret += details.getText();
            ret += "</blockquote>";
        }
        return ret;
    }    
}
