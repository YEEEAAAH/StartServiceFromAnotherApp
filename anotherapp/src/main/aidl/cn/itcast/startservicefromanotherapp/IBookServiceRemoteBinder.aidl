// IBookServiceRemoteBinder.aidl
package cn.itcast.startservicefromanotherapp;
import cn.itcast.startservicefromanotherapp.Book;

// Declare any non-default types here with import statements

interface IBookServiceRemoteBinder {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    List<Book> getBooks();
    List<Book> addBook(in Book book);
    //查询价格
    int queryPrice(String name);
    //结账
    List<Book> buyBook(String name);
    //查询待还书籍
    List<String> bookToReturn(String userName);
    //还书
    List<Book> returnBook(String userName);



}