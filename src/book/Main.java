package book;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
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
//			System.out.println("카테고리 개수 : "+link.size());
//			for(int i=0;i<link.size();i++)
//				System.out.println(i+" : "+link.get(i));
			for(int i=0;i<1;i++) {//560부터 음반 및 잡것 //599수집하다 에러 oracle.net.ns.NetException: Listener refused the connection with the following error: ORA-12519, TNS:no appropriate service handler found
				//System.out.println("1 : "+link.get(i));
				//System.out.println("2 : "+link.get(i).attr("href"));
				//System.out.println("================================================================");
				try {
					for(int p=1;;p++) {
						Set<String> set = new HashSet<String>();//페이지마다 태그 쭉 모아서 저장하기~!
						Document doc1= Jsoup.connect("https://www.aladin.co.kr"+link.get(i).attr("href")+"&BrowseTarget=List&page="+p).get();
						//System.out.println("현재 찾는 주소 : "+"https://www.aladin.co.kr"+link.get(i).attr("href")+"&BrowseTarget=List&page="+p);
						//System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
						Elements detailLinks = doc1.select(".ss_book_list a[class=bo3]");
						if(detailLinks.size()==0)	break;
						for(int j=0;j<detailLinks.size();j++) {
							Document doc2= Jsoup.connect(detailLinks.get(j).attr("href")).get();
							BookVO vo=new BookVO();
							String isbn=doc2.select(".conts_info_list1 li:last-child").text();
							Pattern pat = Pattern.compile("\\d{13}");
							Matcher m = pat.matcher(isbn);
							if(m.find())	isbn=m.group();
							//System.out.println("도서번호 : "+isbn);
							vo.setIsbn(Long.parseLong(isbn));
							String cate1=doc2.select("#ulCategory li:nth-child(1) a:nth-child(1)").text();
							//System.out.println("카테고리 : "+cate1);
							vo.setCategory(cate1);
							String cate2=doc2.select("#ulCategory li:nth-child(1) a:nth-child(2)").text();
							//System.out.println("부카테고리 : "+cate2);
							vo.setSubCategory(cate2);
							String etcInfo=doc2.select(".conts_info_list1").text();
							etcInfo=etcInfo.substring(0,etcInfo.lastIndexOf(" ISBN"));
							//System.out.println("기타 : "+ etcInfo);
							vo.setEtcInfo(etcInfo);
							String title=doc2.select(".Ere_bo_title").text();
							System.out.println("제목 : "+title);
							vo.setTitle(title);
							String subTitle=doc2.select(".Ere_sub1_title").text();
							//System.out.println("부제목 : "+ subTitle);
							vo.setSubtitle(subTitle);
							//작가(가변 : 지은이 엮은이 옮긴이 등), 출판사, 출판일(가변 : 뒤에 원작이 있는경우 원제가 붙음) 묶음
							String tmp=doc2.select("li[class=Ere_sub2_title]").text().trim();
							pat = Pattern.compile("(\\d{4})-(\\d{2})-(\\d{2})");
							m = pat.matcher(tmp);
							String tmpDate="";
							if(m.find())	tmpDate=m.group();
							//System.out.println("출판일 : "+tmpDate);
							vo.setPublicationDay(tmpDate);
							///////////////////////////////////////////////////////////
							tmp=tmp.substring(0,tmp.lastIndexOf(tmpDate));
							String publisher=tmp.substring(tmp.lastIndexOf(')')+1);
							//System.out.println("출판사 : "+publisher);
							vo.setPublisher(publisher);
							///////////////////////////////////////////////////////////
							String writer=tmp.substring(0,tmp.lastIndexOf(publisher));
							//System.out.println("작가 : "+writer);
							vo.setWriter(writer);
							///////////////////////////////////////////////////////////
							Elements tmpPrice=doc2.select(".Ritem");
							String price=tmpPrice.get(0).text();
							String discount=tmpPrice.get(1).text();
							price=price.substring(0,price.indexOf("원")+1);
							discount=discount.substring(0,discount.indexOf("원")+1);
							//System.out.println("정가 : "+price);
							//System.out.println("할인가 : "+discount);
							vo.setPrice(price);
							vo.setDiscount(discount);
							///////////////////////////////////////////////////////////
							///////////////////////////////////////////////////////////
							Document yes0=Jsoup.connect("http://www.yes24.com/SearchCorner/Search?domain=BOOK&query="+isbn).get();
							String link1=yes0.select(".img_bdr").attr("href");
							//System.out.println(link1);
							//yes에서 poster
							String poster=yes0.select(".img_bdr img").attr("src");
							vo.setPoster(poster);
							//System.out.println("포스터 링크 : "+poster);
							///////////////////////////////////////////////////////////
							Document yes1=Jsoup.connect("http://www.yes24.com"+link1).get();
							///////////////////////////////////////////////////////////
							Elements tg=yes1.select(".tag");
							StringBuffer sb0=new StringBuffer();
							for(int l=0;l<tg.size();l++) {
								sb0.append(tg.get(l).text());
								//set을 카테고리마다 만들고, 태그 저장 후 하나의 카테고리 탐색 끝나면 태그테이블에 넣기
								set.add(tg.get(l).text().trim());
							}
							//System.out.println("태그들 : "+sb0);
							vo.setTags(sb0.toString());
							///////////////////////////////////////////////////////////
							String text=yes1.select("#infoset_introduce .infoWrap_txtInner").text();
							//System.out.println("책소개 : "+text);
							vo.setInfoText(text);
							///////////////////////////////////////////////////////////	
							String contTable=yes1.select("#infoset_toc .infoWrap_txt").text();
							//System.out.println("목차 : "+contTable);
							vo.setContentsTable(contTable);
							///////////////////////////////////////////////////////////
							String imgs=yes1.select("#infoset_chYes img").attr("src");
							//System.out.println("이미지링크 : "+imgs);
							vo.setImgs(imgs);
							//데이터 수집 시작할때만 주석 풀고, 수집 후 다시 주석하기
							dao.dataInsert(vo);
						}
						//데이터 수집 시작할때만 주석 풀고, 수집 후 다시 주석하기
						System.out.println("카테고리 : "+i+" 페이지 : "+p);
						dao.tagInsert(set);		
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

