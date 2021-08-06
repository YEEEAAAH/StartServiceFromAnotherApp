package cn.itcast.startservicefromanotherapp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

public class BookService extends Service {

    private static final String TAG = "BookService";


    private ArrayList<Book> mBooks;

    public BookService() {
    }
    private IBinder mBinder = new IBookServiceRemoteBinder.Stub() {
        @Override
        public List<Book> getBooks() throws RemoteException {
            return mBooks;
        }

        @Override
        public List<Book> addBook(Book book) throws RemoteException {
            mBooks.add(book);
            return mBooks;

        }

        @Override
        public int queryPrice( String name) throws RemoteException {
            Log.e(TAG, "queryPrice: "+name );
            for(int i = 0; i < mBooks.size();i++){
                if(name.equals(mBooks.get(i).getBookName())){
                    Log.d(TAG, "queryPrice: "+mBooks.get(i).toString());
                    return mBooks.get(i).getBookPrice();
                }

            }
            return 0;
        }

        @Override
        public List<Book> buyBook( String name) throws RemoteException {
            for(int i = 0; i < mBooks.size();i++){
                if(name.equals(mBooks.get(i).getBookName()))
                    if(mBooks.get(i).getSellState())
                        return null;
                     else{
                        mBooks.get(i).setSellState(true);
                        return mBooks;
                    }
            }
            return null;
        }

        @Override
        public List<String> bookToReturn(String userName) throws RemoteException {
            List<String> list = new ArrayList<>();

            for(int i =0;i<mBooks.size();i++){
                if(userName.equals(mBooks.get(i).getLendUser()))
                    list.add(mBooks.get(i).getBookName());
            }
            return list;
        }

        @Override
        public List<Book> returnBook(String userName) throws RemoteException {
            for(int i = 0;i<mBooks.size();i++){
                if (userName.equals(mBooks.get(i).getLendUser())){
                    mBooks.get(i).setLendState(false);
                    return mBooks;
                }
            }
            return null;

        }


    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: Service bind");
        return mBinder;
    }

    @Override
    public void onCreate() {
        mBooks = new ArrayList<>();
        super.onCreate();
        Log.d(TAG,"onCreate: Service start");
        mBooks.add(new Book("bookName1","user1",30,true,false,20,20));
        mBooks.add(new Book("bookName2","user2",30,true,false,300,1));
        mBooks.add(new Book("bookName3",null,30,false,false,0,1));
        mBooks.add(new Book("bookName4","user4",30,true,false,300,1));
        mBooks.add(new Book("bookName5","user5",30,true,false,300,1));

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy: Service destroy");
    }
}
