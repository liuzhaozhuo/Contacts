package cn.gdcjxy.contacts.bean;


public class Contact {
    private Long id;
    private String name;
    private String tel;

    public Contact(String name, String tel) {
        this.name = name;
        this.tel = tel;
    }

    public Contact(Long id, String name, String tel) {
        this.id = id;
        this.name = name;
        this.tel = tel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", tel='" + tel + '\'' +
                '}';
    }
}
