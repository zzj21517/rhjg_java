package com.lxh.websocket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author 。
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ChatUserDTO {

    /**
     * 用户id
     */
    private String userId;

    /**
     * 用户名
     */
    private String userName;


    /**
     * 用户图片
     */
    private String userImg;


    public String convertUser(String user){

        return "";
    }
}