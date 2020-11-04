package crawler;

import java.io.IOException;
import java.util.Scanner;

/**
 * @author
 * Nguyễn Tuấn Quốc - 17021326
 * Nguyễn Phương Hằng - 17021241
 * Lưu Hoài Linh - 17021284
 */

public class MainClass {

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("----------");
            System.out.println("Website input:");
            String url = scanner.nextLine();
            CrawlerWebsite crawler = new CrawlerWebsite(url);
            crawler.GetDeathLinks();
        }
    }
}
