package cn.itcast.startservicefromanotherapp;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class Book implements Parcelable {

    private String bookName;
    private String lendUser;
    private int daysToReturn;
    private boolean lendState;
    private boolean sellState;
    private int bookPrice;
    private int userBalance;

    public Book(String bookName, String lendUser, int daysToReturn, boolean lendState, boolean sellState, int bookPrice, int userBalance) {
        this.bookName = bookName;
        this.lendUser = lendUser;
        this.daysToReturn = daysToReturn;
        this.lendState = lendState;
        this.sellState = sellState;
        this.bookPrice = bookPrice;
        this.userBalance = userBalance;
    }

    public boolean getSellState() {
        return sellState;
    }

    public void setSellState(boolean sellState) {
        this.sellState = sellState;
    }

    public Book(Parcel in) {
        bookName = in.readString();
        lendUser = in.readString();
        daysToReturn = in.readInt();
        lendState = in.readByte() != 0;
        bookPrice = in.readInt();
        userBalance = in.readInt();
        sellState = in.readByte()!=0;

    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    //书名
    public String getBookName(){
        return bookName;
    }
    public void setBookName(String name){
        this.bookName=name;
    }

    public int getBookPrice(){
        return bookPrice;
    }
    public void setBookPrice(int price){
        this.bookPrice=price;
    }

    public int getUserBalance() {
        return userBalance;
    }
    public void setUserBalance(int userBalance) {
        this.userBalance = userBalance;
    }

/*    public void buyBook(){
        if(this.userBalance>=this.bookPrice){
            userBalance-=bookPrice;
        }
        else{
            Log.d("Book","余额不足");
        }
    }*/
    //借还
    public boolean getLendState(){
        return lendState;
    }
    public void setLendState(boolean state){
        if(state){
            this.daysToReturn=30;
        }else{
            this.daysToReturn=0;
        }
        this.lendState=state;

    }
    public void setLendUser(String name){
        this.lendUser = name;
    }
    public String getLendUser(){
        return lendUser;
    }
    public int getDaysToReturn(){
        return daysToReturn;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(bookName);
        dest.writeString(lendUser);
        dest.writeInt(daysToReturn);
        dest.writeByte((byte) (lendState ? 1 : 0));
        dest.writeInt(bookPrice);
        dest.writeInt(userBalance);
        dest.writeByte((byte)(sellState?1:0));
    }
}
