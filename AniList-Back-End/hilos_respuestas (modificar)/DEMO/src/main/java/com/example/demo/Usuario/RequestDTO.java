package com.example.demo.Usuario;

public class RequestDTO {
    private String nickname;
    private  String image_path;
    public RequestDTO(String nickname,String image_path){
        this.nickname=nickname;
        this.image_path=image_path;
    }
    public RequestDTO(){}

    public void setNickname(String nickname){
        this.nickname=nickname;
    }
    public String getNickname(){
        return this.nickname;
    }
    public void setImage_path(String image_path){
        this.image_path=image_path;
    }
    public String getImage_path(){
        return this.image_path;
    }


}
