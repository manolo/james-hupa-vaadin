package org.apache.hupa.vaadin;

import org.apache.hupa.vaadin.actions.ComposeActivity;
import org.apache.hupa.vaadin.actions.FoldersActivity;
import org.apache.hupa.vaadin.actions.LoginActivity;
import org.apache.hupa.vaadin.actions.LoginActivity.LoginDisplay;
import org.apache.hupa.vaadin.actions.MainActivity;
import org.apache.hupa.vaadin.actions.MessageActivity;
import org.apache.hupa.vaadin.actions.MessageListActivity;
import org.apache.hupa.vaadin.hupa.HupaConnector;
import org.apache.hupa.vaadin.ui.HupaComposeScreen;
import org.apache.hupa.vaadin.ui.HupaLoginScreen;
import org.apache.hupa.vaadin.ui.HupaMainScreen;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

@Theme("hupa")
@SuppressWarnings("serial")
@Widgetset("org.apache.hupa.vaadin.gwt.HupaDesktopWidgetSet")
public class HupaDesktopUI extends UI
{
    private static HupaConnector hupa = HupaConnector.create();

    @Override
    protected void init(VaadinRequest request) {
        LoginDisplay loginScreen = new HupaLoginScreen();
        HupaMainScreen mainScreen = new HupaMainScreen();
        HupaComposeScreen composeScreen = new HupaComposeScreen();

        MessageActivity messageActivity = new MessageActivity(hupa, mainScreen);
        MessageListActivity messageListActivity = new MessageListActivity(hupa, mainScreen, mainScreen, messageActivity);
        ComposeActivity composeActivity = new ComposeActivity(hupa, composeScreen);
        FoldersActivity foldersActivity = new FoldersActivity(hupa, mainScreen, messageListActivity);
        MainActivity mainActivity = new MainActivity(hupa, mainScreen, foldersActivity, messageListActivity, messageActivity, composeActivity);
        LoginActivity loginActivity = new LoginActivity(hupa, loginScreen, mainActivity);

        exportJsFunctions();

        loginActivity.goTo();
    }

    private void exportJsFunctions() {
        getPage().getJavaScript().execute("openLink = function(o){open(o, '_blank', '')}");
        getPage().getJavaScript().execute("mailTo = function(o){alert('TODO: not handling mail addresses via JS yet (' + o + ')')}");
    }
}
