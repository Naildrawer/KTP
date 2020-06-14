import java.net.*;
import java.util.LinkedList;
import java.util.Scanner;

public class App {

    public static void main(String[] args) throws Exception, MalformedURLException {
        Scanner scn = new Scanner(System.in);
        System.out.print("Введите адрес сайта: ");
        String url = scn.nextLine();
        System.out.print("Введите глубину поиска: ");
        int iteration = scn.nextInt();
        scn.close();

        URLsList list = new URLsList();
        list.addUrlData(new URLData(url, 0));

        for(int i = 0; i < iteration; i++) {
            LinkedList<URLData> searchList = list.getOpen_list();
            list.closingUrl();

            Search search = new Search(list, searchList);
            search.run();
        }

        list.fileOutput("test.txt");
    }
}
