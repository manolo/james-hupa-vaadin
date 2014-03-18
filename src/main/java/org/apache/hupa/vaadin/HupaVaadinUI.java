package org.apache.hupa.vaadin;

import javax.servlet.annotation.WebServlet;

import org.apache.hupa.server.servlet.DownloadAttachmentServlet;
import org.apache.hupa.server.servlet.MessageSourceServlet;
import org.apache.hupa.shared.SConsts;
import org.apache.hupa.vaadin.actions.ComposeActivity;
import org.apache.hupa.vaadin.actions.FoldersActivity;
import org.apache.hupa.vaadin.actions.LoginActivity;
import org.apache.hupa.vaadin.actions.MainActivity;
import org.apache.hupa.vaadin.actions.MessageActivity;
import org.apache.hupa.vaadin.actions.MessageListActivity;
import org.apache.hupa.vaadin.hupa.HupaConnector;
import org.apache.hupa.vaadin.ui.HupaComposeScreen;
import org.apache.hupa.vaadin.ui.HupaLoginScreen;
import org.apache.hupa.vaadin.ui.HupaMainScreen;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

@Theme("hupa")
@SuppressWarnings("serial")
public class HupaVaadinUI extends UI
{
    private static HupaConnector hupa = HupaConnector.create();
    
    @WebServlet(value = "/" + SConsts.SERVLET_DOWNLOAD)
    public static class AttachServlet extends DownloadAttachmentServlet {
        public AttachServlet() {
            super(hupa.getCache(), hupa.getLogger());
        }
    }
    
    @WebServlet(value = "/" + SConsts.SERVLET_SOURCE)
    public static class ViewSourceServlet extends MessageSourceServlet {
        public ViewSourceServlet() {
            super(hupa.getCache(), hupa.getLogger());
        }
    }
    
    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = HupaVaadinUI.class, widgetset = "org.apache.hupa.vaadin.HupaWidgetSet")
    public static class Servlet extends VaadinServlet {
    }

    @Override
    protected void init(VaadinRequest request) {
        
        HupaLoginScreen loginScreen = new HupaLoginScreen();
        HupaMainScreen mainScreen = new HupaMainScreen();
        HupaComposeScreen composeScreen = new HupaComposeScreen();
        
        MessageActivity messageActivity = new MessageActivity(hupa, mainScreen);
        MessageListActivity messageListActivity = new MessageListActivity(hupa, mainScreen, messageActivity);
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
