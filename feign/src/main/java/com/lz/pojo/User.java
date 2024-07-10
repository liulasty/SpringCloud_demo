package com.lz.pojo;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/07/09/上午10:15
 * @Description:
 */



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

/**
 * 用户
 *
 * @author lz
 * @date 2024/07/10 15:46:43
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User implements Serializable {

    private Integer id;
    

    private String name;
    

    private Integer age;
    

    private String sex;
    
    

    
    @Override
    public String toString() {
        return "user{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                '}';
    }
}