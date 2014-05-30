package org.apache.hupa.vaadin;


import org.apache.hupa.vaadin.actions.ComposeActivity;
import org.apache.hupa.vaadin.actions.FoldersActivity;
import org.apache.hupa.vaadin.actions.LoginActivity;
import org.apache.hupa.vaadin.actions.LoginActivity.LoginDisplay;
import org.apache.hupa.vaadin.actions.MainActivity;
import org.apache.hupa.vaadin.actions.MessageActivity;
import org.apache.hupa.vaadin.actions.MessageListActivity;
import org.apache.hupa.vaadin.hupa.HupaConnector;
import org.apache.hupa.vaadin.mobile.MobileFoldersView;
import org.apache.hupa.vaadin.mobile.MobileLoginView;
import org.apache.hupa.vaadin.mobile.MobileMainView;
import org.apache.hupa.vaadin.mobile.MobileMessageListView;
import org.apache.hupa.vaadin.mobile.MobileMessageView;
import org.apache.hupa.vaadin.ui.FoldersDisplay;
import org.apache.hupa.vaadin.ui.HupaComposeScreen;
import org.apache.hupa.vaadin.ui.MessageDisplay;
import org.apache.hupa.vaadin.ui.MessageListDisplay;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

/**
 * The UI's "main" class
 */
@SuppressWarnings("serial")
@Widgetset("org.apache.hupa.vaadin.gwt.HupaMobileWidgetSet")
@Theme("touchkit")
public class HupaTouchkitUI extends UI {
    
    private static HupaConnector hupa = HupaConnector.create();

    @Override
    protected void init(VaadinRequest request) {
        
        LoginDisplay loginScreen = new MobileLoginView();
        MobileMainView mainScreen = new MobileMainView();
        FoldersDisplay foldersScreen = new MobileFoldersView();
        MessageListDisplay messageListDisplay = new MobileMessageListView();
        MessageDisplay messageDisplay = new MobileMessageView();
        HupaComposeScreen composeScreen = new HupaComposeScreen();
        
        MessageActivity messageActivity = new MessageActivity(hupa, messageDisplay);
        MessageListActivity messageListActivity = new MessageListActivity(hupa, messageListDisplay, mainScreen, messageActivity);
        ComposeActivity composeActivity = new ComposeActivity(hupa, composeScreen);
        FoldersActivity foldersActivity = new FoldersActivity(hupa, foldersScreen, messageListActivity);
        MainActivity mainActivity = new MainActivity(hupa, mainScreen, foldersActivity, messageListActivity, messageActivity, composeActivity);
        LoginActivity loginActivity = new LoginActivity(hupa, loginScreen, mainActivity);
        
        loginActivity.goTo();
//
//        
//        
//        if(true) return;
//        final TabBarView tabBarView = new TabBarView();
//        final NavigationManager navigationManager = new NavigationManager();
//        navigationManager.setCaption("Tab 1");
//        navigationManager.setCurrentComponent(new MenuView());
//        Tab tab; 
//        tab = tabBarView.addTab(navigationManager);
//        tabBarView.setWidth("100%");
//        tabBarView.setHeight("100%");
//        TouchKitIcon.book.addTo(tab);
//        tab = tabBarView.addTab(new Label("Tab 2"), "Tab 2");
//        TouchKitIcon.ambulance.addTo(tab);
//        tab = tabBarView.addTab(new Label("Tab 3"), "Tab 3");
//        TouchKitIcon.download.addTo(tab);
//        setContent(tabBarView);
    }
}
