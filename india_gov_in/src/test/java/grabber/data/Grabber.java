package grabber.data;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Grabber {
	 static ArrayList<String> nameofCouncilMinister=new ArrayList<String>();
	 static ArrayList<String> nameofMinistryTheyHold=new ArrayList<String>();
	
	static Logger log;
	static {
		log = LogManager.getLogger(Grabber.class);
	}
	static public LinkedHashMap< String, LinkedHashMap<String,String>> ministerDetails= new LinkedHashMap< String, LinkedHashMap<String,String>>();
	static public LinkedHashMap<String,String> details =new LinkedHashMap<String,String>();
public static void main(String[] args) throws IOException {

	Document doc = Jsoup.connect("https://www.india.gov.in/my-government/whos-who/council-ministers").get();
			                      //.proxy("gappsproxy.cognizant.com", 6050)
			                     // .get();
	
	
	log.info(doc.title());
	Elements nameofMinisters = doc.select("span.field-content");
	nameOfCouncilMinister(nameofMinisters);
	Elements departmentName = doc.select("div.item-list");
	MinistryofMinisters(departmentName);
	Elements image=doc.select("table>tbody>tr>td>div>div>img");
	//downloadImage(image);
	Elements contacts=doc.select("table>tbody>tr>td>a");
	ministerContacts(contacts);
	
	nameofMinistryTheyHold.set(0, nameofMinistryTheyHold.get(0).replace("Ministry", ", Ministry"));
	nameofMinistryTheyHold.set(0,nameofMinistryTheyHold.get(0).replace("Department", ", Department"));
	
	
	for (int i=0;i<nameofMinistryTheyHold.size();i++) {
	details.put(nameofCouncilMinister.get(i), nameofMinistryTheyHold.get(i));
	}
	System.out.println(details);

}
	
private static void ministerContacts(Elements contacts) {
	// TODO Auto-generated method stub
	LinkedHashMap<String,String> minContact=new LinkedHashMap<String,String>(); 
	for(int i=0;i<contacts.size();i++)
	{
		minContact.put(contacts.get(i).ownText(), contacts.get(i).getElementsByAttribute("href").toString());
	}
	System.out.println(minContact);
	
}

private static void downloadImage(Elements image) throws IOException {
	for(int i=0;i<image.size();i++) 
	{
	String url=image.get(i).attr("src");
	URL in = new URL(url);
    FileUtils.copyURLToFile(in, new File("D:\\Image\\"+i+"_"+nameofCouncilMinister.get(i).replace(" ","_")+".jpg"));
    ;
	}
	
	
}

private static void MinistryofMinisters(Elements departmentName) {
     

	
	nameofMinistryTheyHold.addAll(departmentName.eachText());
	nameofMinistryTheyHold.remove("Birth Certificate Driving Licence Pan card");
		
for (int i=0;i<nameofMinistryTheyHold.size();i++) {
	if(nameofMinistryTheyHold.get(i).equals("")) {
		nameofMinistryTheyHold.remove(i);
}
	
}
for (String nameofMinistry: nameofMinistryTheyHold)
System.out.println(nameofMinistry);
System.out.println(nameofMinistryTheyHold.size());	
}

private static void nameOfCouncilMinister(Elements nameofMinisters) {
	// TODO Auto-generated method stub
	
	
	for (int i=0;i<nameofMinisters.size();i++) {
		
		nameofCouncilMinister.add(nameofMinisters.get(i).ownText());
		
		
	
	}
for (int i=0;i<nameofCouncilMinister.size();i++) {
	if(nameofCouncilMinister.get(i).equals("")) {
		nameofCouncilMinister.remove(i);
	}
}
for (String nameofCouncilMin: nameofCouncilMinister)
System.out.println(nameofCouncilMin);
System.out.println(nameofCouncilMinister.size());	
}
}

	


