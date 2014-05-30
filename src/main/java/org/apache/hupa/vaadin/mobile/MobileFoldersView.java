package org.apache.hupa.vaadin.mobile;

import org.apache.hupa.vaadin.ui.FoldersDisplay;

import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Tree;
import com.vaadin.ui.UI;

@SuppressWarnings("serial")
public class MobileFoldersView extends NavigationView implements FoldersDisplay {

    Tree treeFolders;
    
    public MobileFoldersView() {
        setCaption("Folders");
        VerticalComponentGroup vFolders = new VerticalComponentGroup();
        treeFolders = new Tree();
        treeFolders.setImmediate(false);
        treeFolders.setWidth("100.0%");
        treeFolders.setHeight("100.0%");
        vFolders.addComponent(treeFolders);
        setContent(new CssLayout(vFolders));
    }

    @Override
    public Tree getTreeFolders() {
        return treeFolders;
    }

    @Override
    public void show() {
//        UI.getCurrent().setContent(this);
    }  
}
