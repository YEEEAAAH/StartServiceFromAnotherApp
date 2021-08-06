package cn.itcast.anotherapp;

import androidx.appcompat.app.AppCompatActivity;
import cn.itcast.startservicefromanotherapp.Book;
import cn.itcast.startservicefromanotherapp.IBookServiceRemoteBinder;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    private Intent serviceIntent;
    private IBookServiceRemoteBinder iBookServiceRemoteBinder = null;
    public boolean mBound = false;
    private ArrayList<Book> mBooks;

    EditText book_name;
    EditText user_name;

    private final ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e(TAG, "MAIN service connected");
            iBookServiceRemoteBinder = IBookServiceRemoteBinder.Stub.asInterface(service);
            try{
                mBooks = (ArrayList<Book>) iBookServiceRemoteBinder.getBooks();
                for(int i = 0;i<mBooks.size();i++){
                    Log.d(TAG, "onServiceConnected: "+mBooks.get(i).toString());
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }

            /*            try {
                *//*mBooks = (ArrayList<Book>) iBookServiceRemoteBinder.addBook(new Book("name1", "Person1", 30, true, 25, 700));
               iBookServiceRemoteBinder.addBook(new Book("name2","Person2",18,true,78,10));
               iBookServiceRemoteBinder.addBook(new Book("name3",null,0,false,40,0));*//*
*//*               for (int i =0;i<mbooks.size();i++)
                Log.e(TAG, "onServiceConnected: "+mbooks.get(i).getBookName());*//*

            } catch (RemoteException e) {
                e.printStackTrace();
            }*/
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e(getLocalClassName(), "service disconnected");
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        serviceIntent = new Intent();
        serviceIntent.setComponent(new ComponentName("cn.itcast.startservicefromanotherapp", "cn.itcast.startservicefromanotherapp.BookService"));
        book_name =(EditText)findViewById(R.id.ipt_book_name);
        user_name = (EditText)findViewById(R.id.ipt_user_name);

//        et_input = (EditText) findViewById(R.id.et_input);

        findViewById(R.id.btn_start_service).setOnClickListener(this);
        findViewById(R.id.btn_stop_service).setOnClickListener(this);
        findViewById(R.id.btn_query_price).setOnClickListener(this);
        findViewById(R.id.btn_buy).setOnClickListener(this);
        findViewById(R.id.query_book).setOnClickListener(this);
        findViewById(R.id.return_book).setOnClickListener(this);
        findViewById(R.id.query_data).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start_service:
                bindService(serviceIntent, mServiceConnection, Context.BIND_AUTO_CREATE);
                break;
            case R.id.btn_stop_service:
                unbindService(mServiceConnection);
                break;
            case R.id.btn_query_price:
                String bookName1 = book_name.getText().toString();
                TextView query_price = (TextView)findViewById(R.id.query_price);
                try {
                    Log.d(TAG,"btn_query_price");
                    if(bookName1!=null){
                        int bookp =iBookServiceRemoteBinder.queryPrice(bookName1);
                        query_price.setText("价格: "+bookp);
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
/*            case R.id.query_data:
                //mBooks = (ArrayList<Book>) iBookServiceRemoteBinder.getBooks();
                Log.d(TAG,"query"+mBooks.size());
                for(int i = 0;i<mBooks.size();i++){
                    Log.d(TAG, "query: "+mBooks.get(i).toString());
                }*/
            case R.id.btn_buy:
                String bookName2 = book_name.getText().toString();
                try {
                    if(iBookServiceRemoteBinder.buyBook(bookName2)!=null){
                        Toast.makeText(this,"您购买了 "+bookName2,Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(this,"本书已售罄",Toast.LENGTH_LONG).show();
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            case R.id.query_book:
                    try {
                        if(iBookServiceRemoteBinder.bookToReturn(user_name.getText().toString())==null){
                            Toast.makeText(this,"无待还书籍",Toast.LENGTH_LONG).show();
                        }
                        else{
                            TextView bookToReturn =(TextView)findViewById(R.id.book_to_return);
                            bookToReturn.setText("待还:"+iBookServiceRemoteBinder.bookToReturn(user_name.getText().toString()));
                        }

                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
            case R.id.return_book:
                try {
                    if (iBookServiceRemoteBinder.bookToReturn(user_name.getText().toString())!=null) {
                        if(iBookServiceRemoteBinder.returnBook(user_name.getText().toString())!=null){
                            Toast.makeText(this,"您已归还书籍",Toast.LENGTH_LONG).show();
                        }
                        else
                            Toast.makeText(this,"无待还书籍",Toast.LENGTH_LONG).show();
                    }
                }
                 catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;

//                queryPrice(mBooks,bookName,book_name);


            default:
                break;


        }
    }

/*    private void queryPrice(ArrayList<Book> books,String bookName,EditText editText) {
        for(int i=0;i<books.size();i++){
            if(bookName.equals(books.get(i).getBookName())){
                editText.setText("价格: "+books.get(i).getBookPrice());
            }
        }
    }*/




/*    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {

        Log.d(TAG,"Bind Service");
        binder = IBookServiceRemoteBinder.Stub.asInterface(service);

    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        Log.d(TAG,"unBind Service");
    }*/
}