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
						/*
						 * isbn >  .conts_info_list1 li:last-child
category > #ulCategory a:nth-child(1)
subCategory > #ulCategory a:nth-child(2)
						 */
						
						BookVO vo=new BookVO();
						
						String isbn=doc2.select(".conts_info_list1 li:last-child").text();
						//System.out.println(isbn);
						isbn=isbn.substring(isbn.lastIndexOf(":")+2);
						//System.out.println(isbn);
						vo.setIsbn(Long.parseLong(isbn));
						
						String cate1=doc2.select("#ulCategory a:nth-child(1)").text();
						cate1=cate1.substring(0,cate1.indexOf(" "));
						//System.out.println(cate1);
						vo.setCategory(cate1);
						
						String cate2=doc2.select("#ulCategory a:nth-child(2)").text();
						cate2=cate2.substring(0,cate2.indexOf(" "));
						//System.out.println(cate2);
						vo.setSubCategory(cate2);
						
						String etcInfo=doc2.select(".conts_info_list1").text();
						etcInfo=etcInfo.substring(0,etcInfo.lastIndexOf(" ISBN"));
						//System.out.println(etcInfo);
						vo.setEtcInfo(etcInfo);
						
//						String poster=null;//찾아보기 포스터 어느부분에서 구하는지??
//						String title=null;
//						String subTitle=null;
//						//지은이, 출판사, 출판일 묶여있음 ([class=Ere_sub2_title])
//						Elements etc=null;
//						String date="";
//						Date publicationDay=null;//출판일 -> 스트링을 데이트로 바꾸기
//						String writer="";
//						String publisher="";
//						Element price=null;//가격!!
//						Element discount=null;//할인가!!
//						//태그 여러개 구분자는 무엇으로 할지 생각하고 저장해야됨
//						Elements tags=null; //태그들!!
//						//이미지파일링크들 어떤 구분자로 나누어서 가져올지 생각 (공백??)
//						Elements imgs=null; //책 이미지들!!
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

