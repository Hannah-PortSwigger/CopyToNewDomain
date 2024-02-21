import burp.api.montoya.core.ToolType;
import burp.api.montoya.sitemap.SiteMap;
import burp.api.montoya.ui.contextmenu.ContextMenuEvent;
import burp.api.montoya.ui.contextmenu.ContextMenuItemsProvider;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MyContextMenuItemsProvider implements ContextMenuItemsProvider
{
    private final JMenuItem replacementMenu;

    public MyContextMenuItemsProvider(SiteMap siteMap, Frame suiteFrame)
    {
        CustomDialog customDialog = new CustomDialog();

        replacementMenu = new JMenuItem("Copy to new domain");
        replacementMenu.addActionListener(l -> customDialog.showReplacementDialog(siteMap, suiteFrame));
    }

    @Override
    public List<Component> provideMenuItems(ContextMenuEvent contextMenuEvent)
    {
        List<Component> menuList = new ArrayList<>();

        if (contextMenuEvent.isFromTool(ToolType.TARGET))
        {
            menuList.add(replacementMenu);
        }

        return menuList;
    }
}
