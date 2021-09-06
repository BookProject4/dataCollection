package book;

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
			// 국내
			// 1 유아 : https://www.aladin.co.kr/shop/wbrowse.aspx?CID=13789
			// 2 >> 주제별 책읽기 : https://www.aladin.co.kr/shop/wbrowse.aspx?BrowseTarget=List&SortOrder=2&ViewType=Detail&ViewRowsCount=25&PublishMonth=0&PublishDay=84&UsedShop=0&DiscountPercent=20&BranchType=1&Stockstatus=1&CID=70125
			
			// 외국
			// 1 소설/시/희곡 : https://www.aladin.co.kr/shop/wbrowse.aspx?CID=90842
			// 2 >> 문학 : https://www.aladin.co.kr/shop/wbrowse.aspx?ViewType=Simple&ViewRowsCount=24&PublishMonth=0&PublishDay=84&UsedShop=&DiscountPercent=20&BranchType=1&Stockstatus=1&CID=97454
			
			// 이북
			// 1 유아 : https://www.aladin.co.kr/shop/wbrowse.aspx?CID=38424
			// 2 >> 0~3세 https://www.aladin.co.kr/shop/wbrowse.aspx?BrowseTarget=List&ViewRowsCount=25&ViewType=Detail&PublishMonth=0&SortOrder=2&Stockstatus=1&PublishDay=84&CustReviewRankStart=&CustReviewRankEnd=&CustReviewCountStart=&CustReviewCountEnd=&PriceFilterMin=&PriceFilterMax=&SearchOption=&NotFreeSampleEbook=&IsRentable=&EbookSerial=&UsedShop=0&DiscountPercent=20&BranchType=1&CID=39420
			
			String[] cid= {"13789","90842","38424"};//158585 -> 이북 끝
			String[] cate= {"국내도서","해외도서","eBook"};
			Document doc = Jsoup.connect("https://www.aladin.co.kr/shop/wbrowse.aspx?+").get();
			Elements link = doc.select("a[href*=www.aladin.co.kr/shop/wbrowse.aspx?][class=gr03]");
			System.out.println("카테고리 내 상세페이지 개수 : "+link.size());
			
			int j=0;
			for(int i=0;i<link.size();i++) {
				System.out.println(link.get(i));
				if(j==3)	break;
				String CID="";
				
				
				BookVO vo=new BookVO();
				//isbn,category,subCategory,poster,title,subtitle,writer,publisher,etcInfo,price,discount,text,imgs,contentsTable,tags,publicationDay
				vo.setCategory(cate[j]);
				
				
				if(CID.equals("90842"))	j++;
				if(CID.equals("38424"))	j++;
				if(CID.equals("158585"))  j++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

