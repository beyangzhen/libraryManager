package com.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import com.actionForm.BookForm;
import com.actionForm.BorrowForm;
import com.actionForm.ReaderForm;
import com.core.ConnDB;

public class BorrowDAO {
    ConnDB conn = new ConnDB();
    public int insert() {
        String sql = "INSERT INTO tb_borrow (bookid) vlaues(1) ";
        int ret = conn.executeUpdate(sql);
        return ret;
    }
    // ͼ�����
	public int insertBorrow(ReaderForm readerForm, BookForm bookForm,
			String operator) {
		String sql1 = "select t.days from tb_bookinfo b left join tb_booktype t on"
				+ " b.typeid=t.id where b.id=" + bookForm.getId() + ""; // ��ȡ�ɽ�������SQL���
		ResultSet rs = conn.executeQuery(sql1); // ִ��SQL���
		int days = 0;
		try {
			if (rs.next()) {
				days = rs.getInt(1); // ��ȡ�ɽ�����
			}
		} catch (SQLException ex) {
		}
		// ����黹ʱ��
		Calendar calendar = Calendar.getInstance(); // ��ȡϵͳ����
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		java.sql.Date date = java.sql.Date.valueOf(format.format(calendar
				.getTime()));// ��������
		calendar.add(calendar.DAY_OF_YEAR, days);// ���Ͽɽ�����
		java.sql.Date backTime = java.sql.Date.valueOf(format.format(calendar
				.getTime())); // �黹����

		String sql = "Insert into tb_borrow (readerid,bookid,borrowTime,backTime,operator) values("
				+ readerForm.getId()
				+ ","
				+ bookForm.getId()
				+ ",'"
				+ date
				+ "','" + backTime + "','" + operator + "')";
		System.out.println("���ͼ�������Ϣ��SQL��" + sql);
		int falg = conn.executeUpdate(sql); // ִ�и������
		conn.close();// �ر����ݿ�����
		return falg;
	}
      // ͼ��̽�
      public int renew(int id){
          String sql0="SELECT bookid FROM tb_borrow WHERE id="+id+"";	//���ݽ���ID��ѯͼ��ID��SQL���
          ResultSet rs1=conn.executeQuery(sql0);	//ִ�в�ѯ���
          int flag=0;
          try {
            if (rs1.next()) {
            	//��ȡ�ɽ�����
                String sql1 = "select t.days from tb_bookinfo b left join" +
                		" tb_booktype t on b.typeid=t.id where b.id=" 
                		+rs1.getInt(1) + "";	//��ȡ�ɽ�������SQL���
                ResultSet rs = conn.executeQuery(sql1);	//ִ�в�ѯ���
                int days = 0;
                try {
                    if (rs.next()) {
                        days = rs.getInt(1);	//��ȡ�ɽ�����
                    }
                } catch (SQLException ex) {
                }
                //����黹ʱ��
                Calendar calendar=Calendar.getInstance(); //��ȡϵͳ����
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");//�������ڸ�ʽ
                java.sql.Date date=java.sql.Date.valueOf(
                		format.format(calendar.getTime()));//��������
                calendar.add(calendar.DAY_OF_YEAR, days);	//���Ͽɽ�����
                java.sql.Date backTime= java.sql.Date.valueOf(format.format(calendar.getTime()));	//�黹����
                String sql = "UPDATE tb_borrow SET backtime='" + backTime +
                             "' where id=" + id + "";	//���¹黹ʱ���������
                flag = conn.executeUpdate(sql);//ִ�и������
            }
          } catch (Exception ex1) {}
          conn.close();//�ر����ݿ�����
          return flag;
      }
      // ͼ��黹
      public int back(int id,String operator){
    	  //���ݽ���ID��ȡ����ID��ͼ��ID
          String sql0="SELECT readerid,bookid FROM tb_borrow WHERE id="+id+"";
          ResultSet rs1=conn.executeQuery(sql0);	//ִ�в�ѯ���
          int flag=0;
        try {
            if (rs1.next()) {
            	 Calendar calendar=Calendar.getInstance(); //��ȡϵͳ����
                 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                 java.sql.Date date=java.sql.Date.valueOf(format.format(calendar.getTime()));//��������
                int readerid=rs1.getInt(1);	//��ȡ����ID
                int bookid=rs1.getInt(2);//��ȡͼ��ID
                String sql1="INSERT INTO tb_giveback (readerid,bookid,backTime" +
                		",operator) VALUES("+readerid+","+bookid+",'"
                		+date+"','"+operator+"')";	//����黹��Ϣ
                int ret=conn.executeUpdate(sql1);	//ִ�и������
                if(ret==1){
                    String sql2 = "UPDATE tb_borrow SET ifback=1 where id=" + id +
                                 "";	//��������Ϣ���Ϊ�ѹ黹
                    flag = conn.executeUpdate(sql2);	//ִ�и������
                }else{
                    flag=0;
                }
            }
        } catch (Exception ex1) {
        }
          conn.close();//�ر����ݿ�����
          return flag;
      }
      // ��ѯͼ�������Ϣ
      public Collection<BorrowForm> borrowinfo(String str){
	      String sql="select borr.*,book.bookname,book.price,pub.pubname," +
	      		"bs.name bookcasename,r.barcode from (select * from tb_borrow " +
	      		"where ifback=0) as borr left join tb_bookinfo book on borr.bookid" +
	      		"=book.id join tb_publishing pub on book.isbn=pub.isbn join" +
	      		" tb_bookcase bs on book.bookcase=bs.id join tb_reader r on" +
	      		" borr.readerid=r.id where r.barcode='"+str+"'";
	      ResultSet rs=conn.executeQuery(sql);//ִ�в�ѯ���
	      Collection<BorrowForm> coll=new ArrayList<BorrowForm>();
	      BorrowForm form=null;
	      try {
	          while (rs.next()) {
	              form = new BorrowForm();
	              form.setId(Integer.valueOf(rs.getInt(1)));//��ȡID��
	              form.setBorrowTime(rs.getString(4));//��ȡ����ʱ��
	              form.setBackTime(rs.getString(5));//��ȡ�黹ʱ��
	              form.setBookName(rs.getString(8));//��ȡͼ������
	              form.setPrice(Float.valueOf(rs.getFloat(9)));//��ȡ����
	              form.setPubName(rs.getString(10));//��ȡ������
	              form.setBookcaseName(rs.getString(11));	//��ȡ�������
	              coll.add(form);//��ӽ�����Ϣ��Collection������
	          }
	      } catch (SQLException ex) {
	          System.out.println("������Ϣ��"+ex.getMessage());//����쳣��Ϣ
	      }
	      conn.close();//�ر����ݿ�����
	      return coll;
      }
      // ��������
    public Collection bremind(){
	    Calendar calendar=Calendar.getInstance(); //��ȡϵͳ����
	    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	    java.sql.Date date=java.sql.Date.valueOf(format.format(calendar.getTime()));//��������
	    String sql="select borr.borrowTime,borr.backTime,book.barcode,book.bookname,r.name readername,r.barcode readerbarcode from tb_borrow borr join tb_bookinfo book on book.id=borr.bookid join tb_reader r on r.id=borr.readerid where borr.backTime <='"+date+"' and borr.ifback=0";
	    ResultSet rs=conn.executeQuery(sql);
	    Collection coll=new ArrayList();
	    BorrowForm form=null;
	    try {
	        while (rs.next()) {
	            form = new BorrowForm();
	            form.setBorrowTime(rs.getString(1));
	            form.setBackTime(rs.getString(2));
	            form.setBookBarcode(rs.getString(3));
	            form.setBookName(rs.getString(4));
	            form.setReaderName(rs.getString(5));
	            form.setReaderBarcode(rs.getString(6));
	            coll.add(form);
	            System.out.println("ͼ�������룺"+rs.getString(3));
	        }
	    } catch (SQLException ex) {
	        System.out.println(ex.getMessage());
	    }
	    conn.close();
	    return coll;
    }	
	// ͼ����Ĳ�ѯ
	public Collection borrowQuery(String strif){
	    String sql="";
	    if(strif!="all" && strif!=null && strif!=""){
	        sql="select * from (select borr.borrowTime,borr.backTime,book.barcode,book.bookname,r.name readername,r.barcode readerbarcode,borr.ifback from tb_borrow borr join tb_bookinfo book on book.id=borr.bookid join tb_reader r on r.id=borr.readerid) as borr where borr."+strif+"";
	    }else{
	        sql="select * from (select borr.borrowTime,borr.backTime,book.barcode,book.bookname,r.name readername,r.barcode readerbarcode,borr.ifback from tb_borrow borr join tb_bookinfo book on book.id=borr.bookid join tb_reader r on r.id=borr.readerid) as borr";
	    }
	    ResultSet rs=conn.executeQuery(sql);
		System.out.println("ͼ����Ĳ�ѯ��SQL��"+sql);
		Collection coll=new ArrayList();
		BorrowForm form=null;
		try {
		    while (rs.next()) {
		        form = new BorrowForm();
		        form.setBorrowTime(rs.getString(1));
		        form.setBackTime(rs.getString(2));
		        form.setBookBarcode(rs.getString(3));
		        form.setBookName(rs.getString(4));
		        form.setReaderName(rs.getString(5));
		        form.setReaderBarcode(rs.getString(6));
		        form.setIfBack(rs.getInt(7));
		        coll.add(form);
		    }
		} catch (SQLException ex) {
		    System.out.println(ex.getMessage());
		}
		conn.close();
		return coll;
	}
    // ͼ���������
    public Collection<BorrowForm> bookBorrowSort() {
       String sql = "select * from (SELECT bookid,count(bookid) as degree FROM" +
       		" tb_borrow group by bookid) as borr join (select b.*,c.name as bookcaseName" +
       		",p.pubname,t.typename from tb_bookinfo b left join tb_bookcase" +
       		" c on b.bookcase=c.id join tb_publishing p on b.ISBN=p.ISBN join " +
       		"tb_booktype t on b.typeid=t.id where b.del=0)" +
       		" as book on borr.bookid=book.id order by borr.degree desc limit 10 ";
        Collection<BorrowForm> coll = new ArrayList<BorrowForm>();	//������ʵ����Collection����
        BorrowForm form = null;	//����BorrowForm����
        ResultSet rs = conn.executeQuery(sql);	//ִ�в�ѯ���
        try {
            while (rs.next()) {
                form = new BorrowForm();	//ʵ����BorrowForm����
                form.setBookId(rs.getInt(1));		//��ȡͼ��ID
                form.setDegree(rs.getInt(2));		//��ȡ���Ĵ���
                form.setBookBarcode(rs.getString(3));	//��ȡͼ��������
                form.setBookName(rs.getString(4));	//��ȡͼ������
                form.setAuthor(rs.getString(6));	//��ȡ����
                form.setPrice(Float.valueOf(rs.getString(9)));	//��ȡ����
                form.setBookcaseName(rs.getString(16));	//��ȡ�������
                form.setPubName(rs.getString(17));	//��ȡ������
                form.setBookType(rs.getString(18));	//��ȡͼ������
                coll.add(form);	//���浽Collection������
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());	//����쳣��Ϣ
        }
        conn.close();	//�ر����ݿ�����
        return coll;
    }
}
