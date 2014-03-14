package org.apache.hupa.vaadin.actions;

import java.io.Serializable;

import org.apache.hupa.shared.domain.Settings;
import org.apache.hupa.vaadin.hupa.HupaConnector;
import org.apache.hupa.vaadin.ui.HupaLoginScreen;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;


@SuppressWarnings("serial")
public class LoginActivity implements Serializable {
    
    private HupaLoginScreen display;
    private HupaConnector hupa;
    private MainActivity folders;
    private Window window;
    
    public LoginActivity(HupaConnector hupaConnector, HupaLoginScreen hupaLoginScreen, MainActivity mainActivity) {
        hupa = hupaConnector;
        display = hupaLoginScreen;
        folders = mainActivity;
        window = new Window("}> Hupa Login");
        window.setModal(false);
        window.setWidth("400px");
        window.setHeight("300px");
        window.setResizable(false);
        window.setContent(display);
        window.setClosable(false);
        window.setDraggable(false);
        window.center();        
        bind();
    }
    
    public void goTo() {
        UI.getCurrent().setContent(new VerticalLayout());
        UI.getCurrent().addWindow(window);
    }
    
    private void bind() {
        display.getbLogin().addClickListener(new ClickListener() {
            public void buttonClick(ClickEvent event) {
                if (hupa.doLogin(display.getfUser().getValue(),
                        display.getfPassword().getValue(),
                        display.gettImapServer().getValue(),
                        display.gettImapPort().getValue(),
                        display.getcImapSecure().getValue(),
                        display.gettSmtpServer().getValue(),
                        display.gettSmtpPort().getValue(),
                        display.getcSmtpSecure().getValue())) {
                    
                    UI.getCurrent().removeWindow(window);            
                    folders.goTo();
                } else {
                    Notification.show("Login incorrect:",
                              "check your password and server settings.",
                              Notification.Type.WARNING_MESSAGE);
                }
            }
        });
        
        display.getfUser().setImmediate(true);
        display.getfUser().addValueChangeListener(new ValueChangeListener() {
            public void valueChange(ValueChangeEvent event) {
                Settings s = hupa.getSettings(display.getfUser().getValue());
                display.gettImapServer().setValue(checkNull(s.getImapServer()));
                display.gettImapPort().setValue(checkNull(s.getImapPort()));
                display.gettSmtpServer().setValue(checkNull(s.getSmtpServer()));
                display.gettSmtpPort().setValue(checkNull(s.getSmtpPort()));
                display.getcImapSecure().setValue(s.getImapSecure());
                display.getcSmtpSecure().setValue(s.getImapSecure());
            }
            private String checkNull(Object o) {
                return o == null || (o instanceof Integer && ((Integer)o).equals(0)) ? "" : String.valueOf(o);
            }
        });
    }

}
