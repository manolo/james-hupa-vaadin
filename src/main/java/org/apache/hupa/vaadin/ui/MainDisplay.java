package org.apache.hupa.vaadin.ui;

import com.vaadin.ui.AbstractTextField;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;

public interface MainDisplay {

    Button getbLogout();

    Button getbMark();

    Button getbDelete();

    Button getbSource();

    Button getbForward();

    Button getbReplyAll();

    Button getbReply();

    Button getbCompose();

    Button getbReload();

    Label getlUser();

    AbstractTextField getiSearch();
    
    void show();
    
    void hide();
}