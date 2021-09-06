package book;
import java.sql.*;
import java.sql.Date;
import java.util.*;

public class BookDAO {
	private Connection conn;
	private PreparedStatement ps;
	private final String URL="jdbc:oracle:thin:@211.238.142.186:1521:XE";
	
	public BookDAO() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void getConnection() {
		try {
			conn=DriverManager.getConnection(URL,"hr","happy");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void disConnection() {
		try {
			if(ps!=null)	ps.close();
			if(conn!=null)	ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//수집 -> isbn 빼고 나머지 다 스트링으로 가져옴... 수정해야됨
	public void dataInsert(BookVO vo) {
		try {
			getConnection();
			String sql="INSERT INTO book_info(isbn,category,subCategory,poster,title,subtitle,writer,publisher,etcInfo,price,discount,text,imgs,contentsTable,tags,publicationDay) "
					+ "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			ps=conn.prepareStatement(sql);
			ps.setLong(1, vo.getIsbn());
			ps.setString(2, vo.getCategory());
			ps.setString(3, vo.getSubCategory());
			ps.setString(4, vo.getPoster());
			ps.setString(5, vo.getTitle());
			ps.setString(6, vo.getSubtitle());
			ps.setString(7, vo.getWriter());
			ps.setString(8, vo.getPublisher());
			ps.setString(9, vo.getEtcInfo());
			ps.setString(10, vo.getPrice());
			ps.setString(11, vo.getDiscount());
			ps.setString(12, vo.getText());//Strings 사이에 구분자 넣기 // 수집할때 수정 여기서 말고
			ps.setString(13, vo.getImgs());//Strings 사이에 구분자 넣기 // 수집할때 수정 여기서 말고
			ps.setString(14, vo.getContentsTable());
			ps.setString(15, vo.getTags());//Strings 사이에 구분자 넣기 // 수집할때 수정 여기서 말고
			ps.setDate(16, (Date) vo.getPublicationDay());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			disConnection();
		}
	}
}
