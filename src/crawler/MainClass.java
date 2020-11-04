package crawler;

import java.io.IOException;
import java.util.Scanner;

public class MainClass {

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String url = "";
        while (true) {
            menu();
            url = scanner.nextLine();
            CrawlerWebsite crawler = new CrawlerWebsite(url);
            crawler.GetDeathLinks();
        }
    }

    static void menu() {
        System.out.println("----------");
        System.out.println("Website input:");
    }

}
