package org.apache.hupa.vaadin.actions;

import java.io.Serializable;

import org.apache.hupa.shared.domain.Settings;
import org.apache.hupa.vaadin.hupa.HupaConnector;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutListener;
import com.vaadin.ui.AbstractTextField;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Notification;

@SuppressWarnings("serial")
public class LoginActivity implements Serializable {

    public interface LoginDisplay {
        void show();
        void hide();
        Button getbLogin();
        AbstractTextField getfPassword();
        AbstractTextField getfUser();
        CheckBox getcSmtpSecure();
        AbstractTextField gettSmtpPort();
        AbstractTextField gettSmtpServer();
        CheckBox getcImapSecure();
        AbstractTextField gettImapPort();
        AbstractTextField gettImapServer();
    }

    private LoginDisplay display;
    private HupaConnector hupa;
    private MainActivity main;

    public LoginActivity(HupaConnector hupaConnector, LoginDisplay hupaLoginScreen, MainActivity mainActivity) {
        hupa = hupaConnector;
        display = hupaLoginScreen;
        main = mainActivity;
        bind();
    }

    public void goTo() {
        display.show();
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

                    display.hide();
                    main.goTo();
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
        display.getfPassword().addShortcutListener(
            new ShortcutListener("Search", ShortcutAction.KeyCode.ENTER, null) {
                public void handleAction(Object sender, Object target) {
                    display.getbLogin().click();
                }
        });
    }

}
