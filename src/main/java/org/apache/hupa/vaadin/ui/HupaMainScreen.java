package org.apache.hupa.vaadin.ui;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.VerticalSplitPanel;

@SuppressWarnings("serial")
public class HupaMainScreen extends CustomComponent {
	

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */
	
	@AutoGenerated
	private AbsoluteLayout mainLayout;
	@AutoGenerated
	private VerticalLayout vMain;
	@AutoGenerated
	private HorizontalSplitPanel hSplitFolders;
	@AutoGenerated
	private VerticalSplitPanel vSplitMessages;
	@AutoGenerated
	private HorizontalLayout hMessage;
	@AutoGenerated
	private Table tableAttachments;
	@AutoGenerated
	private RichTextArea textMsg;
	@AutoGenerated
	private Table tableMsgs;
	@AutoGenerated
	private Tree treeFolders;
	@AutoGenerated
	private HorizontalLayout hButtons;
	@AutoGenerated
	private TextField iSearch;
	@AutoGenerated
	private Label lSearch;
	@AutoGenerated
	private Button bMark;
	@AutoGenerated
	private Button bDelete;
	@AutoGenerated
	private Button bSource;
	@AutoGenerated
	private Button bForward;
	@AutoGenerated
	private Button bReplyAll;
	@AutoGenerated
	private Button bReply;
	@AutoGenerated
	private Button bCompose;
	@AutoGenerated
	private Button bReload;
	@AutoGenerated
	private HorizontalLayout hHead;
	@AutoGenerated
	private Button bLogout;
	@AutoGenerated
	private Label lHupa;
	public HupaMainScreen() {
		buildMainLayout();
		setCompositionRoot(mainLayout);
		mainLayout.setSizeFull();
		hSplitFolders.setSplitPosition(300, Unit.PIXELS);
		vSplitMessages.setSplitPosition(200, Unit.PIXELS);
		textMsg.setReadOnly(true);
		tableAttachments.setVisible(false);
	}
	
	public Button getbLogout() {
		return bLogout;
	}
	public Tree getTreeFolders() {
		return treeFolders;
	}

	public Button getbMark() {
		return bMark;
	}

	public Button getbDelete() {
		return bDelete;
	}

	public Button getbSource() {
		return bSource;
	}

	public Button getbForward() {
		return bForward;
	}

	public Button getbReplyAll() {
		return bReplyAll;
	}

	public Button getbReply() {
		return bReply;
	}

	public Button getbCompose() {
		return bCompose;
	}

	public Button getbReload() {
		return bReload;
	}
	
	public Table getTableMsgs() {
		return tableMsgs;
	}

	public RichTextArea getTextMsg() {
		return textMsg;
	}
	
	public Table getTableAttachments() {
		return tableAttachments;
	}	
	
	@AutoGenerated
	private AbsoluteLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new AbsoluteLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("100%");
		mainLayout.setHeight("100%");
		
		// top-level component properties
		setWidth("100.0%");
		setHeight("100.0%");
		
		// vMain
		vMain = buildVMain();
		mainLayout.addComponent(vMain,
				"top:0.0px;right:7.0px;bottom:0.0px;left:0.0px;");
		
		return mainLayout;
	}

	@AutoGenerated
	private VerticalLayout buildVMain() {
		// common part: create layout
		vMain = new VerticalLayout();
		vMain.setImmediate(false);
		vMain.setWidth("100.0%");
		vMain.setHeight("100.0%");
		vMain.setMargin(true);
		vMain.setSpacing(true);
		
		// hHead
		hHead = buildHHead();
		vMain.addComponent(hHead);
		
		// hButtons
		hButtons = buildHButtons();
		vMain.addComponent(hButtons);
		
		// hSplitFolders
		hSplitFolders = buildHSplitFolders();
		vMain.addComponent(hSplitFolders);
		vMain.setExpandRatio(hSplitFolders, 1.0f);
		
		return vMain;
	}

	@AutoGenerated
	private HorizontalLayout buildHHead() {
		// common part: create layout
		hHead = new HorizontalLayout();
		hHead.setImmediate(false);
		hHead.setWidth("100.0%");
		hHead.setHeight("20px");
		hHead.setMargin(false);
		
		// lHupa
		lHupa = new Label();
		lHupa.setStyleName("v-window-header");
		lHupa.setImmediate(false);
		lHupa.setWidth("-1px");
		lHupa.setHeight("100.0%");
		lHupa.setValue("}> Apache Hupa");
		hHead.addComponent(lHupa);
		hHead.setExpandRatio(lHupa, 1.0f);
		
		// bLogout
		bLogout = new Button();
		bLogout.setCaption("Logout");
		bLogout.setImmediate(true);
		bLogout.setWidth("-1px");
		bLogout.setHeight("-1px");
		hHead.addComponent(bLogout);
		hHead.setComponentAlignment(bLogout, new Alignment(10));
		
		return hHead;
	}

	@AutoGenerated
	private HorizontalLayout buildHButtons() {
		// common part: create layout
		hButtons = new HorizontalLayout();
		hButtons.setImmediate(false);
		hButtons.setWidth("100.0%");
		hButtons.setHeight("-1px");
		hButtons.setMargin(false);
		
		// bReload
		bReload = new Button();
		bReload.setCaption("Reload");
		bReload.setImmediate(true);
		bReload.setWidth("-1px");
		bReload.setHeight("-1px");
		hButtons.addComponent(bReload);
		
		// bCompose
		bCompose = new Button();
		bCompose.setCaption("Compose");
		bCompose.setImmediate(true);
		bCompose.setWidth("-1px");
		bCompose.setHeight("-1px");
		hButtons.addComponent(bCompose);
		
		// bReply
		bReply = new Button();
		bReply.setCaption("Reply");
		bReply.setImmediate(true);
		bReply.setWidth("-1px");
		bReply.setHeight("-1px");
		hButtons.addComponent(bReply);
		
		// bReplyAll
		bReplyAll = new Button();
		bReplyAll.setCaption("Reply All");
		bReplyAll.setImmediate(true);
		bReplyAll.setWidth("-1px");
		bReplyAll.setHeight("-1px");
		hButtons.addComponent(bReplyAll);
		
		// bForward
		bForward = new Button();
		bForward.setCaption("Forward");
		bForward.setImmediate(true);
		bForward.setWidth("-1px");
		bForward.setHeight("-1px");
		hButtons.addComponent(bForward);
		
		// bSource
		bSource = new Button();
		bSource.setCaption("Show Raw");
		bSource.setImmediate(true);
		bSource.setWidth("-1px");
		bSource.setHeight("-1px");
		hButtons.addComponent(bSource);
		
		// bDelete
		bDelete = new Button();
		bDelete.setCaption("Delete");
		bDelete.setImmediate(true);
		bDelete.setWidth("-1px");
		bDelete.setHeight("-1px");
		hButtons.addComponent(bDelete);
		
		// bMark
		bMark = new Button();
		bMark.setCaption("Mark");
		bMark.setImmediate(true);
		bMark.setWidth("-1px");
		bMark.setHeight("-1px");
		hButtons.addComponent(bMark);
		
		// lSearch
		lSearch = new Label();
		lSearch.setImmediate(false);
		lSearch.setWidth("-1px");
		lSearch.setHeight("-1px");
		lSearch.setValue("Search:");
		hButtons.addComponent(lSearch);
		hButtons.setExpandRatio(lSearch, 1.0f);
		hButtons.setComponentAlignment(lSearch, new Alignment(34));
		
		// iSearch
		iSearch = new TextField();
		iSearch.setStyleName("search");
		iSearch.setImmediate(false);
		iSearch.setWidth("200px");
		iSearch.setHeight("-1px");
		hButtons.addComponent(iSearch);
		hButtons.setComponentAlignment(iSearch, new Alignment(33));
		
		return hButtons;
	}

	@AutoGenerated
	private HorizontalSplitPanel buildHSplitFolders() {
		// common part: create layout
		hSplitFolders = new HorizontalSplitPanel();
		hSplitFolders.setImmediate(false);
		hSplitFolders.setWidth("100.0%");
		hSplitFolders.setHeight("100.0%");
		
		// treeFolders
		treeFolders = new Tree();
		treeFolders.setCaption("IMAP Folders");
		treeFolders.setImmediate(false);
		treeFolders.setWidth("-1px");
		treeFolders.setHeight("100.0%");
		hSplitFolders.addComponent(treeFolders);
		
		// vSplitMessages
		vSplitMessages = buildVSplitMessages();
		hSplitFolders.addComponent(vSplitMessages);
		
		return hSplitFolders;
	}

	@AutoGenerated
	private VerticalSplitPanel buildVSplitMessages() {
		// common part: create layout
		vSplitMessages = new VerticalSplitPanel();
		vSplitMessages.setImmediate(false);
		vSplitMessages.setWidth("100.0%");
		vSplitMessages.setHeight("100.0%");
		
		// tableMsgs
		tableMsgs = new Table();
		tableMsgs.setImmediate(false);
		tableMsgs.setWidth("-1px");
		tableMsgs.setHeight("-1px");
		vSplitMessages.addComponent(tableMsgs);
		
		// hMessage
		hMessage = buildHMessage();
		vSplitMessages.addComponent(hMessage);
		
		return vSplitMessages;
	}

	@AutoGenerated
	private HorizontalLayout buildHMessage() {
		// common part: create layout
		hMessage = new HorizontalLayout();
		hMessage.setImmediate(false);
		hMessage.setWidth("100.0%");
		hMessage.setHeight("100.0%");
		hMessage.setMargin(false);
		
		// textMsg
		textMsg = new RichTextArea();
		textMsg.setStyleName("message");
		textMsg.setImmediate(false);
		textMsg.setWidth("100.0%");
		textMsg.setHeight("100.0%");
		hMessage.addComponent(textMsg);
		hMessage.setExpandRatio(textMsg, 1.0f);
		
		// tableAttachments
		tableAttachments = new Table();
		tableAttachments.setImmediate(false);
		tableAttachments.setWidth("200px");
		tableAttachments.setHeight("100.0%");
		hMessage.addComponent(tableAttachments);
		
		return hMessage;
	}

}
