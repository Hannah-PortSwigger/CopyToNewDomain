import burp.api.montoya.BurpExtension;
import burp.api.montoya.MontoyaApi;

public class Extension implements BurpExtension
{

    private static final String EXTENSION_NAME = "Copy to new domain";

    @Override
    public void initialize(MontoyaApi montoyaApi)
    {
        montoyaApi.extension().setName(EXTENSION_NAME);

        montoyaApi.userInterface().registerContextMenuItemsProvider(new MyContextMenuItemsProvider(montoyaApi.siteMap(), montoyaApi.userInterface().swingUtils().suiteFrame()));
    }
}
