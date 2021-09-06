package book;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main {
	public static void main(String[] args) {
		Main C=new Main();
		C.getData();
	}
	
	public void getData() {
		BookDAO dao = new BookDAO();
		try {
			Document doc0 = Jsoup.connect("https://www.aladin.co.kr/home/welcome.aspx").get();
			Elements link = doc0.select(".browse_sub a");
			System.out.println("카테고리 개수 : "+link.size());
			
			for(int i=122;i<link.size();i++) {
				//System.out.println("1 : "+link.get(i));
				//System.out.println("2 : "+link.get(i).attr("href"));
				//System.out.println("================================================================");
				for(int p=1;;p++) {
					Document doc1= Jsoup.connect("https://www.aladin.co.kr"+link.get(i).attr("href")+"&BrowseTarget=List&page="+p).get();
					System.out.println("현재 찾는 주소 : "+"https://www.aladin.co.kr"+link.get(i).attr("href")+"&BrowseTarget=List&page="+p);
					System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
					Elements detailLinks = doc1.select(".ss_book_list a[class=bo3]");
					if(detailLinks.size()==0)	break;
					for(int j=0;j<detailLinks.size();j++) {
						Document doc2= Jsoup.connect(detailLinks.get(j).attr("href")).get();
						
						BookVO vo=new BookVO();
						
						String isbn=doc2.select(".conts_info_list1 li:last-child").text();
						Pattern pat = Pattern.compile("\\d{13}");
						Matcher m = pat.matcher(isbn);
						if(m.find())	isbn=m.group();
						System.out.println("도서번호 : "+isbn);
						vo.setIsbn(Long.parseLong(isbn));
						
						String cate1=doc2.select("#ulCategory li:nth-child(1) a:nth-child(1)").text();
						System.out.println("카테고리 : "+cate1);
						vo.setCategory(cate1);
						
						String cate2=doc2.select("#ulCategory li:nth-child(1) a:nth-child(2)").text();
						System.out.println("부카테고리 : "+cate2);
						vo.setSubCategory(cate2);
						
						String etcInfo=doc2.select(".conts_info_list1").text();
						etcInfo=etcInfo.substring(0,etcInfo.lastIndexOf(" ISBN"));
						System.out.println("기타 : "+ etcInfo);
						vo.setEtcInfo(etcInfo);
						
						String title=doc2.select(".Ere_bo_title").text();
						System.out.println("제목 : "+title);
						vo.setTitle(title);
						
						String subTitle=doc2.select(".Ere_sub1_title").text();
						System.out.println("부제목 : "+ subTitle);
						
						//작가, 출판사, 출판일 묶음
						String tmp=doc2.select("li[class=Ere_sub2_title]").text().trim();
						
						pat = Pattern.compile("(\\d{4})-(\\d{2})-(\\d{2})");
						m = pat.matcher(tmp);
						String tmpDate="";
						if(m.find())	tmpDate=m.group();
						System.out.println("출판일 변환 전 : "+tmpDate);
						SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
						Date date=sdf.parse(tmpDate);
						System.out.println("출판일 변환 후 오라클 Date형으로 넣기 : "+date);
						vo.setPublicationDay(date);
						
						tmp=tmp.substring(0,tmp.lastIndexOf(tmpDate));
						String publisher=tmp.substring(tmp.lastIndexOf(')')+1);
						System.out.println("출판사 : "+publisher);
						vo.setPublisher(publisher);
						
						String writer=tmp.substring(0,tmp.lastIndexOf(publisher));
						System.out.println("작가 : "+writer);
						vo.setWriter(writer);
						
						
						
						
						System.out.println("================================================================");
						
//						String poster=null;//찾아보기 포스터 어느부분에서 구하는지??
//						Element price=null;//가격!!
//						Element discount=null;//할인가!!
//						Elements tags=null; //태그들!!
//						Elements imgs=null; //이미지파일링크들 어떤 구분자로 나누어서 가져올지 생각 (공백??)
//						Elements text=doc2.select(""); // 책소개!!
//						Elements contentsTable // 목차!
					}
					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

