package book;

import java.util.Date;

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
			//isbn,category,subCategory,poster,title,subtitle,writer,publisher,etcInfo,price,discount,text,imgs,contentsTable,tags,publicationDay
			
			Document doc = Jsoup.connect("https://www.aladin.co.kr/home/welcome.aspx").get();
			Elements link = doc.select(".browse_sub a");
			System.out.println("카테고리 개수 : "+link.size());
			
			for(int i=0;i<link.size();i++) {
				//System.out.println("1 : "+link.get(i));
				//System.out.println("2 : "+link.get(i).attr("href"));
				for(int p=1;;p++) {
					Document doc1= Jsoup.connect("https://www.aladin.co.kr"+link.attr("href")+"&BrowseTarget=List&page="+p).get();
					Elements detailLinks = doc1.select(".ss_book_list a[class=bo3]");
					if(detailLinks.size()==0)	break;
					for(int j=0;j<detailLinks.size();j++) {
						//System.out.println("3 : "+detailLinks.get(j).attr("href"));
						Document doc2= Jsoup.connect(detailLinks.get(j).attr("href")).get();
						
						Element isbn=null;
						//카테고리 다받아오고 처음꺼의 첫번째는 카테고리에 넣고, 두번째는 서브카테고리에 넣기 (구분자 > )
						Elements categories=null;
						String cate1="";
						String cate2="";
						Element poster=null;//찾아보기 포스터 어느부분에서 구하는지??
						Element title=null;
						Element subTitle=null;
						//지은이, 출판사, 출판일 묶여있음 ([class=Ere_sub2_title])
						Elements etc=null;
						String date="";
						Date publicationDay=null;//출판일 -> 스트링을 데이트로 바꾸기
						String writer="";
						String publisher="";
						Element etcInfo=null;
						Element price=null;
						Element discount=null;
						//태그 여러개 구분자는 무엇으로 할지 생각하고 저장해야됨
						Elements tags=null;
						//이미지파일링크들 어떤 구분자로 나누어서 가져올지 생각 (공백??)
						Elements imgs=null;
						//text,contentsTable
					}
					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

