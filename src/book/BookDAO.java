package book;
import java.sql.*;
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
			if(conn!=null)	conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//책정보 수집
	public void dataInsert(BookVO vo) {
		try {
			getConnection();
			String sql="INSERT INTO book_info(isbn,category,subCategory,poster,title,subtitle,writer,publisher,publicationDay,price,discount,etcInfo,infoText,imgs,contentsTable,tags) "
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
			ps.setString(9, vo.getPublicationDay());
			ps.setString(10, vo.getPrice());
			ps.setString(11, vo.getDiscount());
			ps.setString(12, vo.getEtcInfo());
			ps.setString(13, vo.getInfoText());
			ps.setString(14, vo.getImgs());
			ps.setString(15, vo.getContentsTable());
			ps.setString(16, vo.getTags());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			disConnection();
		}
	}
	public void tagInsert(Set<String> set) {
		Iterator<String> it = set.iterator();
		try {
			getConnection();
			while(it.hasNext()) {
				String tmp=it.next();
				String sql="SELECT COUNT(*) FROM tag WHERE name=?";
				ps=conn.prepareStatement(sql);
				ps.setString(1, tmp);
				ResultSet rs=ps.executeQuery();
				rs.next();
				int n=rs.getInt(1);
				if(n==0) {
					sql="INSERT INTO tag(name) VALUES(?)";
					ps=conn.prepareStatement(sql);
					ps.setString(1, tmp);
					ps.executeUpdate();
				}
				rs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			disConnection();
		}
	}
	public boolean isIsbn(long isbn) {
		boolean b=true;
		try {
			getConnection();
			String sql="SELECT COUNT(*) FROM book_info WHERE isbn="+isbn;
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			rs.next();
			int n=rs.getInt(1);
			if(n==0)	b=false;	
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			disConnection();
		}
		return b;
	}
	public List<Long> getIsbn() {
		List<Long> list = new ArrayList<>();
		try {
			getConnection();
			System.out.println("DB연결...");
			String sql="SELECT isbn FROM book_info";
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			int i=0;
			while(rs.next()) {
				list.add(rs.getLong(1));
				i++;
				if(i%2000==0)	System.out.println(i+"까지 읽어옴");
			}
			System.out.println("isbn "+i+"개 가져오기 완료");
			rs.close();
		}catch(Exception e){}
		finally {
			disConnection();
		}
		return list;
	}
	public void scoreInsert(long isbn,double d) {
		try {
			getConnection();
			String sql="UPDATE book_info SET score=? WHERE isbn=?";
			ps=conn.prepareStatement(sql);
			ps.setDouble(1, d);
			ps.setLong(2, isbn);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			disConnection();
		}
		
	}
}
