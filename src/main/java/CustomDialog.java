import burp.api.montoya.http.message.HttpRequestResponse;
import burp.api.montoya.http.message.requests.HttpRequest;
import burp.api.montoya.sitemap.SiteMap;
import burp.api.montoya.sitemap.SiteMapFilter;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static burp.api.montoya.http.HttpService.httpService;
import static burp.api.montoya.http.message.requests.HttpRequest.httpRequest;

public class CustomDialog
{
    private final JTextField baseUrlToReplaceField;
    private final JTextField replacementBaseUrlField;
    private final Object[] message;
    private final ExecutorService executorService;

    public CustomDialog()
    {
        baseUrlToReplaceField = new JTextField("https://...");
        replacementBaseUrlField = new JTextField("https://...");
        message = new Object[]{
                "Base URL to replace:", baseUrlToReplaceField,
                "New base URL:", replacementBaseUrlField
        };


        executorService = Executors.newSingleThreadExecutor();
    }

    void showReplacementDialog(SiteMap siteMap, Frame suiteFrame)
    {

        int option = JOptionPane.showConfirmDialog(suiteFrame, message, "Copy to new URL", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION)
        {
            executorService.submit(() -> {
                List<HttpRequestResponse> siteMapList = siteMap.requestResponses(SiteMapFilter.prefixFilter(baseUrlToReplaceField.getText()));

                for (HttpRequestResponse requestResponse : siteMapList)
                {
                    siteMap.add(HttpRequestResponse.httpRequestResponse(updateWithNewBase(requestResponse.request(), replacementBaseUrlField.getText()), null));
                }
            });
        }
    }

    private HttpRequest updateWithNewBase(HttpRequest request, String baseUrl)
    {
        String hostValue = baseUrl.replaceAll("http(s)?://|www\\.|/.*", "");

        return httpRequest(httpService(baseUrl), request.toString())
                .withUpdatedHeader("Host", hostValue);
    }
}
