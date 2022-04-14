import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class URLLink {
    public URLLink(){
        try{
            GetURL();
        }catch (Exception e){
            System.out.println("Error");
        }
    }

    public void GetURL() throws Exception{
        Document doc= Jsoup.connect("https://www.youtube.com/results?search_query=떡볶이+레시피").get();
        Elements contents= doc.select("#columns");
        System.out.println(contents);
    }
}
