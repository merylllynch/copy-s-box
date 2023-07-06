package org.example;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import org.example.mapper.StudentMapper;
import org.example.mapper.UserMapper;
import org.example.pojo.Student;
import org.example.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@SuppressWarnings("all")
@CrossOrigin(origins = {"*", "null"})

public class Controller {
    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private UserMapper userMapper;
    private Gson gson=new Gson();
    //获取学生信息
    @GetMapping("/students")
    public String getStudents(){

        QueryWrapper<Student> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("number");
        List<Student> students = studentMapper.selectList(wrapper);
        return gson.toJson(students);
    }
    @PostMapping("/add")
    public  void addStudent(@RequestBody Student student){
        studentMapper.insert(student);
    }
    @PostMapping("delete")
    public String removeStudent(@RequestBody Student student){
        studentMapper.deleteById(student);
        List<Student> students = studentMapper.selectList(null);
        for(Student s:students){
            System.out.println(s);
        };
        return gson.toJson(students);

    }
    @PostMapping("update")
    public void updateStudent(@RequestBody Student student) {
        studentMapper.updateById(student);
    }
    @PostMapping("login")
    public String loginStudent(@RequestBody User user) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.setEntity(user);
        User user_selected = userMapper.selectOne(userQueryWrapper);
        if (user_selected==null){
            return "0";
        }
        return "1";
    }
    @PostMapping("register")
    public  String registeruser(@RequestBody User user){
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.setEntity(user);
        User user_selected = userMapper.selectOne(userQueryWrapper);
        //如果user表中已存在相同用户名，则添加失败返回0。
        if (user_selected!=null && user.getUsername().equals(user_selected.getUsername())){
            return "0";
        }
        userMapper.insert(user);
        return "1";
    }

    //按照理综进行排序
//    @GetMapping("rank")
//    public String rank(){
//        QueryWrapper<Student> wrapper = new QueryWrapper<>();
//        wrapper.orderByDesc("sys");
//        List<Student> students = studentMapper.selectList(wrapper);
//        return gson.toJson(students);
//    }

    //模糊查询
    @PostMapping("search")
    public String search(@RequestBody HashMap<String,String> map){
        String name = map.get("name");
        //判断姓名是否为空
        QueryWrapper<Student> wrapper = new QueryWrapper<>();
        if (name!=null&&!StringUtils.isEmpty(name)){
            wrapper.like("name",name);
        }
        //判断搜索条件是否等于空
        if (map.get("classroom")!=null&&!StringUtils.isEmpty(map.get("classroom"))){
            wrapper.like("classroom",map.get("classroom"));
        }
        //判断是否需要排序
        if (map.get("order")!=null&&!StringUtils.isEmpty(map.get("order"))){
            int order=Integer.valueOf(map.get("order"));
            if (order==1){
                wrapper.orderByDesc("sum");
            }else {
                wrapper.orderByAsc("sum");
            }
        }
        return gson.toJson(studentMapper.selectList(wrapper));
    }

}

