/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studentmanagementsystem;

/**
 *
 * @author WINDOWS 10
 * SUBSCRIBE OUR YOUTUBE CHANNEL -> https://www.youtube.com/channel/UCPgcmw0LXToDn49akUEJBkQ
 * THANKS FOR YOUR SUPPORT : ) 
 */
public class courseData {
    
    private String id;
    private String description;
    private String etc;
    
    public courseData(String id, String description, String etc){
        this.id = id;
        this.description = description;
        this.etc = etc;
    }

    public String getId() {
        return id;
    }

    public String getEtc() {
        return etc;
    }
    public String getCourse(){
        return id;
    }
    public String getDescription(){
        return description;
    }
    public String getDegree(){
        return etc;
    }
    
}
