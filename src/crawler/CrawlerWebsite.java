package crawler;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

import org.jsoup.Jsoup;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author
 * Nguyễn Tuấn Quốc - 17021326
 * Nguyễn Phương Hằng - 17021241
 * Lưu Hoài Linh - 17021284
 */

public class CrawlerWebsite {

    ArrayList<String> listLinks = new ArrayList<>();
    String url;
    String domain;
    Document document;

    public CrawlerWebsite(String url) {
        this.url = validateUrl(url);
    }

    // lấy tất cả chap trên web và in ra
    public void GetDeathLinks() throws IOException {
        try {
            URI uri;
            uri = new URI(url);
            domain = uri.getScheme() + "://" + uri.getHost();
            domain = domain.startsWith("www.") ? domain.substring(4) : domain;
        } catch (URISyntaxException e1) {
            e1.printStackTrace();
        }
        try{
            url = validateUrl(Jsoup.connect(url).followRedirects(true).execute().url().toExternalForm());
        }
        catch (Exception e){}
        try {
            document = Jsoup.connect(url).get();
            System.out.println("----------");
            getAllDeathLinksInPage(document);
            System.out.println("----------");
            getAllDeathImagesInPage(url, document);
        }
        catch (Exception e) {
            System.out.println("Url is invalid");
        }
    }

    // lấy tất cả links
    void getAllDeathLinksInPage(Document document) throws IOException {
        boolean checkIfAllLinksLive = false;
        Map<String, Integer> map = new HashMap<String, Integer>();

        Elements a_tags = document.select("a[href]");
        for (Element a_tag : a_tags) {
            String link = a_tag.attr("abs:href");
            if (link != "" && !map.containsKey(link)) {
                map.put(link, 1);
                if (link.startsWith("/")) link = domain + link;
                if (!(link.startsWith("http://") || link.startsWith("https://"))) link = url + "/" + link;
                try {
                    Response res = Jsoup.connect(link).followRedirects(false).execute();
                } catch (Exception e) {
                    if (!checkIfAllLinksLive) {
                        System.out.println("Death links:");
                    }
                    System.out.println("--Death: " + link);
                    checkIfAllLinksLive = true;
                }
            }
        }

        if (!checkIfAllLinksLive) {
            System.out.println("All links are live");
        }
    }

    void getAllDeathImagesInPage(String url, Document document) throws IOException {
        boolean checkIfAllImagesLive = false;
        Map<String,Integer> map = new HashMap<String,Integer>();
        
        Elements image_tags = document.select("img");
        for (Element image_tag : image_tags) {
            String link = image_tag.attr("src");
            if (link != "" && !map.containsKey(link)) {
                map.put(link, 1);
                if (link.startsWith("/")) link = domain + link;
                if (!(link.startsWith("http://") || link.startsWith("https://"))) link = url + "/" + link;
                try{
                    String contentType = Jsoup.connect(link).ignoreContentType(true).followRedirects(false).execute().header("Content-Type");
                    if (!contentType.contains("image")) {
                        if (!checkIfAllImagesLive) System.out.println("Death images:");
                        System.out.println("--Death: " + link);
                        checkIfAllImagesLive = true;
                    }
                }
                catch (Exception e) {
                    if (!checkIfAllImagesLive) System.out.println("Death images:");
                    System.out.println("--Death: " + link);
                    checkIfAllImagesLive = true;
                }
            }
        }

        if (!checkIfAllImagesLive) {
            System.out.println("All images are live");
        }
    }

    public static String validateUrl(String url) {

        String resUrl = url;

        if (!(url.startsWith("http://") || url.startsWith("https://"))) resUrl = "http://" + url;

        if (url.endsWith("/")) {
            resUrl = url.substring(0, url.length() - 1);
        }

        if (resUrl.contains("#")) {
            resUrl = resUrl.substring(0, resUrl.indexOf("#"));
        }

        return resUrl;
    }
}
