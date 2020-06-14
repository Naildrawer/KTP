import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;

public class URLPool {

    private LinkedList<URLData> open_list = new LinkedList<>();
    private LinkedList<URLData> close_list = new LinkedList<>();

    public synchronized void addUrlData(URLData data){
        open_list.add(data);
    }

    public LinkedList<URLData> getOpen_list() {
        return open_list;
    }

    public synchronized URLData getLastURLData(){
        URLData last = open_list.removeLast();
        close_list.add(last);
        return last;
    }

    public boolean isEmptyOpen(){
        return open_list.isEmpty();
    }

    public void fileOutput(String path){
        try(FileOutputStream fos=new FileOutputStream(path)){
            String line = "";

            for(URLData data : close_list)
                line += data.getUrl() + " " + data.getIteration() + "\n";

            byte[] buffer = line.getBytes();
            fos.write(buffer, 0, buffer.length);
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    public void consoleOutput(){
        for(URLData data : close_list)
            System.out.println(data.getUrl() + " " + data.getIteration());

    }
}
