package yahoofinance.quotes.query1v7.ext;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import yahoofinance.Utils;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes2.CrumbManager;
import yahoofinance.quotes.query1v7.QuotesRequest;
import yahoofinance.util.RedirectableRequest;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class QuotesRequestExt<T> extends QuotesRequest<T> {

    private static final Logger log = LoggerFactory.getLogger(QuotesRequest.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public QuotesRequestExt(String symbols) {
        super(symbols);
    }

    @Override
    public List<T> getResult() throws IOException {
        List<T> result = new ArrayList();
        Map<String, String> params = new LinkedHashMap();
        params.put("symbols", this.symbols);
        params.put("crumb", CrumbManager.getCrumb());
        String url = YahooFinance.QUOTES_QUERY1V7_BASE_URL + "?" + Utils.getURLParameters(params);
        log.info("Sending request: " + url);
        URL request = new URL(url);
        RedirectableRequest redirectableRequest = new RedirectableRequest(request, 5);
        redirectableRequest.setConnectTimeout(YahooFinance.CONNECTION_TIMEOUT);
        redirectableRequest.setReadTimeout(YahooFinance.CONNECTION_TIMEOUT);
        URLConnection connection = redirectableRequest.openConnection();
        InputStreamReader is = new InputStreamReader(connection.getInputStream());
        JsonNode node = objectMapper.readTree(is);
        if (node.has("quoteResponse") && node.get("quoteResponse").has("result")) {
            node = node.get("quoteResponse").get("result");

            for(int i = 0; i < node.size(); ++i) {
                result.add(this.parseJson(node.get(i)));
            }

            return result;
        } else {
            throw new IOException("Invalid response");
        }
    }
}
