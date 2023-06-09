package Model;

public class Product {
    private  String productid ;
    private  String productimage ;
    private String name;
    private String desc;
    private String price;
    private String sto;
    private String cate;
    private String curr;

    private  String publisher ;

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getProductimage() {
        return productimage;
    }

    public void setProductimage(String productimage) {
        this.productimage = productimage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSto() {
        return sto;
    }

    public void setSto(String sto) {
        this.sto = sto;
    }

    public String getCate() {
        return cate;
    }

    public void setCate(String cate) {
        this.cate = cate;
    }

    public String getCurr() {
        return curr;
    }

    public void setCurr(String curr) {
        this.curr = curr;
    }


    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Product(String productid, String productimage, String name, String desc, String price, String sto, String cate, String curr, String publisher) {
        this.productid = productid;
        this.productimage = productimage;
        this.name = name;
        this.desc = desc;
        this.price = price;
        this.sto = sto;
        this.cate = cate;
        this.curr = curr;
        this.publisher = publisher;
    }

    public Product() {
    }



}
