package org.apache.hupa.vaadin.mobile;

import org.apache.hupa.vaadin.ui.MainDisplay;

import com.vaadin.addon.touchkit.extensions.TouchKitIcon;
import com.vaadin.addon.touchkit.ui.NavigationManager;
import com.vaadin.addon.touchkit.ui.TabBarView;
import com.vaadin.addon.touchkit.ui.Toolbar;
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.ui.AbstractTextField;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Tree;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class MobileMainView extends NavigationManager implements MainDisplay {

    private NavigationManager navigationManager = new NavigationManager();
    private AbstractTextField iSearch;
    private Button bMark;
    private Button bDelete;
    private Button bSource;
    private Button bForward;
    private Button bReplyAll;
    private Button bReply;
    private Button bCompose;
    private Button bReload;
    private Button bLogout;
    private Label lUser;


    public MobileMainView() {
        bMark = new Button("Mark");
        bDelete = new Button("Delete");
        bSource = new Button("Source");
        bForward = new Button("Fw");
        bReplyAll = new Button("Re All");
        bReply = new Button("Re");
        bCompose = new Button("New");
        bReload = new Button("Upd");
        bLogout = new Button("Logout");
        iSearch = new TextField();
        lUser = new Label();
        
        setCaption("tarari");
        
        VerticalLayout vMain = new VerticalLayout();
        vMain.setSizeFull();
        setSizeFull();
        
        Toolbar bar = new Toolbar();
        
        bar.addComponent(bCompose);
        bar.addComponent(bReply);
        bar.addComponent(bReplyAll);
        bar.addComponent(bForward);
        bar.addComponent(bDelete);
        
        TouchKitIcon.camera.addTo(bCompose);
        TouchKitIcon.camera.addTo(bReply);
        TouchKitIcon.camera.addTo(bReplyAll);
        TouchKitIcon.camera.addTo(bForward);
        TouchKitIcon.camera.addTo(bDelete);
        
        setCurrentComponent(new CssLayout(vMain, bar));
        
    }
    @Override
    public void show() {
        UI.getCurrent().setContent(this);        
    }
    @Override
    public void hide() {
    }    
    @Override
    public Button getbLogout() {
        return bLogout;
    }
    @Override
    public Button getbMark() {
        return bMark;
    }
    @Override
    public Button getbDelete() {
        return bDelete;
    }
    @Override
    public Button getbSource() {
        return bSource;
    }
    @Override
    public Button getbForward() {
        return bForward;
    }
    @Override
    public Button getbReplyAll() {
        return bReplyAll;
    }
    @Override
    public Button getbReply() {
        return bReply;
    }
    @Override
    public Button getbCompose() {
        return bCompose;
    }
    @Override
    public Button getbReload() {
        return bReload;
    }
    @Override
    public AbstractTextField getiSearch() {
        return iSearch;
    }
    @Override
    public Label getlUser() {
        return lUser;
    }}
