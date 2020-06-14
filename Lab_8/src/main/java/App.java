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
        System.out.print("Введите кол-во потоков: ");
        int threads = scn.nextInt();
        scn.close();

        URLPool pool = new URLPool();
        pool.addUrlData(new URLData(url, 0));

        Thread[] threadsList = new Thread[threads];

        for(int i = 0; i < threads; i++){
            CrawlerTask task = new CrawlerTask(pool, iteration, "Thread-" + (i + 1));
            threadsList[i] = new Thread(task);
            threadsList[i].start();
        }

        for(Thread thread: threadsList)
            thread.join();

        pool.fileOutput("test.txt");
    }
}
