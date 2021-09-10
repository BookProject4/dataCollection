
package book;

import java.util.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class GetScore {
	public static void main(String[] args) {
		GetScore gs = new GetScore();
		gs.getData();
	}

	public void getData() {
		BookDAO dao = new BookDAO();
		List<Long> list = dao.getIsbn();
		try {
			for (Long l : list) {
				try {
					Document yes = Jsoup.connect("http://www.yes24.com/SearchCorner/Search?domain=BOOK&query=" + l)
							.get();
					Elements sc = yes.select(".gd_rating .yes_b");
					if (sc.size() == 0) {
						System.out.println(l + " -> 점수없음(건너뜀)");
						continue;
					}
					String s = sc.get(0).text();
					System.out.println(l+" : "+ s + " -> 수집!");
					//dao.scoreInsert(l, Double.parseDouble(s));
				} catch (Exception e) {}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}