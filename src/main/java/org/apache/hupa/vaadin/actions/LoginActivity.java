package org.apache.hupa.vaadin.actions;

import org.apache.hupa.shared.domain.Settings;
import org.apache.hupa.vaadin.hupa.HupaConnector;
import org.apache.hupa.vaadin.ui.HupaLoginScreen;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;


@SuppressWarnings("serial")
public class LoginActivity {
	
	private HupaLoginScreen display;
	private HupaConnector hupa;
	private FoldersActivity folders;
	
	public LoginActivity(HupaConnector hupaConnector, HupaLoginScreen hupaLoginScreen, FoldersActivity foldersActivity) {
		hupa = hupaConnector;
		display = hupaLoginScreen;
		folders = foldersActivity;
		bind();
	}
	
	public void goTo() {
		UI.getCurrent().setContent(display);
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
