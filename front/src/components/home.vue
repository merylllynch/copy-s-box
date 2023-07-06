<template>
  <div>
    <div class="col-10 offset-1">
    <table class="table caption-top table-hover table-striped">
  <caption>
    <h1>学生成绩管理系统</h1>
    <div>
      <el-button-group>
      <el-button type="success" @click="getStudents">获取学生信息</el-button>
     <el-button type="primary" @click="dialogVisible = true">添加学生</el-button>      
     <el-input v-model="research.name"
    placeholder="请输入姓名"
    prefix-icon="el-icon-search" style="width: 150px;left: 350px;">
  </el-input>
  <el-select v-model="value" clearable placeholder="请选择班级" style="width: 120px;left: 355px;">
    <el-option
      v-for="item in options1"
      :key="item.value"
      :label="item.label"
      :value="item.value">
    </el-option>
  </el-select>
  <el-button icon="el-icon-search"  circle  style="left: 700px;" @click="Search()"></el-button>
     <el-button type="text" @click="open" style="left: 770px;">注销</el-button>
     
  <el-dialog
  :visible.sync="dialogVisible"
  width="30%"
  :before-close="handleClose">
  <div class="offset-3">请输入学生信息</div>
  <span slot="footer" class="dialog-footer">
    
    <el-button type="primary" @click="addStudent">确 定</el-button>
    <el-button @click="dialogVisible = false">取 消</el-button>
  </span>

  <div class="offset-1"><span>学生学号</span><input v-model="newStudent.number"  oninput ="value=value.replace(/[^\d]/g,'')"/></div>
  <div class="offset-1"><span>学生姓名</span><input v-model="newStudent.name" placeholder="请输入中文" onkeyup="value=value.replace(/[^\u4E00-\u9FA5]/g,'')"/></div>
  <div class="offset-1"><span>学生性别</span>
    <!-- <input v-model="newStudent.sex"/> -->
    <el-select v-model="newStudent.sex"  placeholder="请选择性别" style="width: 168px;height: 40px;">
    <el-option
      v-for="item in options2"
      :key="item.value"
      :label="item.label"
      :value="item.value">
    </el-option>
  </el-select>
  </div>
  <div class="offset-1">
    <span>学生班级</span>
      <!-- <input v-model="newStudent.classroom"/> -->
    <el-select v-model="newStudent.classroom"  placeholder="请选择班级" style="width: 168px;height: 40px;">
    <el-option
      v-for="item in options1"
      :key="item.value"
      :label="item.label"
      :value="item.value">
    </el-option>
  </el-select>
  </div>
  <div class="offset-1"><span>语文成绩</span><input oninput ="value=value.replace(/[^0-9.]|^0/g,'')" v-model.number="newStudent.chi"/></div>
  <div class="offset-1"><span>数学成绩</span><input oninput ="value=value.replace(/[^0-9.]|^0/g,'')" v-model.number="newStudent.math"/></div>
  <div class="offset-1"><span>英语成绩</span><input oninput ="value=value.replace(/[^0-9.]|^0/g,'')" v-model.number="newStudent.eng"/></div>
  <div class="offset-1"><span>综合成绩</span><input oninput ="value=value.replace(/[^0-9.]|^0/g,'')" v-model.number="newStudent.sys"/></div>
</el-dialog>
    </el-button-group>
    
    </div>
    
    
  
  </caption>
  <thead class="table-dark" style="height: 41px ;">
    <tr>
      <th scope="col">学号</th>
      <th scope="col">姓名</th>
      <th scope="col">性别</th>
      <th scope="col">班级</th>
      <th scope="col">语文成绩</th>
      <th scope="col">数学成绩</th>
      <th scope="col">英语成绩</th>
      <th scope="col">综合成绩</th>
      <th scope="col" style="height: 41px ;" sortable><span>总成绩</span>
        <el-button-group style="height: 20px ;width: 12px;">         
          <el-button type="text" icon="el-icon-caret-top"  size="medium" style="height: 10px;width: 20px;top: -4px;left: 5px;padding: 0;" @click="Asc()"></el-button>
          <el-button type="text" icon="el-icon-caret-bottom"  size="medium"  style="height: 8px ;width: 20px;top: -4px;left: 7.5px;padding: 0px 20px 10px 0px;" @click="Desc()"></el-button>         
        </el-button-group>
      </th>
      
      <th scope="col">操作</th>
    </tr>

  </thead>
  <tbody>
    <StudentName v-for="stu in students_for_page" :key="stu.id" :student="stu"></StudentName>
  </tbody>
</table>
<div class="text-center">
   <el-button-group>
    <el-button type="primary" icon="el-icon-arrow-left" @click="last_page">上一页</el-button>
    <el-button type="primary" @click="next_page">下一页<i class="el-icon-arrow-right el-icon--right"></i></el-button>
   </el-button-group>
   
   </div>
   
   
   </div>
  </div>
</template>

<script>
import axios from 'axios'
import StudentName from './StudentName'

export default {
  name: 'home',
  components: {
    StudentName
  },
  data(){
    return {
      options1: [{
          value: '一班',
          label: '一班'
        }, {
          value: '二班',
          label: '二班'
        }, {
          value: '三班',
          label: '三班'
        }, {
          value: '四班',
          label: '四班'
        }, {
          value: '五班',
          label: '五班'
        }],
        options2: [{
          value: '男',
          label: '男'
        }, {
          value: '女',
          label: '女'
        }],
        value: '',
      students:[],
      page:1,
      dialogVisible: false,
      research:{
        name:"",
        classroom:"",
        order:"",
      },
      newStudent:{
        number:"",
        name:"",
        sex:"",
        classroom:"",
        chi:"",
        math:"",
        eng:"",
        sys:"",
        sum:"",
      }
    }
  },
  methods:{
    Desc(){
      this.research.order=1;
      axios({
        url:'http://localhost:8080/search',
        method:'POST',
        data:this.research
      }).then(res=>{
        console.log(res.data);
        this.students=res.data;
      })
    },
    Asc(){
      this.research.order=2;
      axios({
        url:'http://localhost:8080/search',
        method:'POST',
        data:this.research
      }).then(res=>{
        console.log(res.data);
        this.students=res.data;
      })
    },
    Search(){
      this.research.classroom=this.value;
      this.research.order=null;
      axios({
        url:'http://localhost:8080/search',
        method:'POST',
        data:this.research
      }).then(res=>{
        console.log(res.data);
        this.students=res.data;
      })
    },
    open() {
        this.$confirm('此操作将退出登录, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
          center: true
        }).then(() => {
          this.$message({
            type: 'success',
            message: '退出成功!',           
          });
          sessionStorage.removeItem("isLogined");
          this.$router.push({path: "/login"});
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消'
          });          
        });
      },
    getStudents(){
      axios(
        {
          url: "http://localhost:8080/students",
          method: 'GET',
          
        }
      ).then(res=>{
        console.log(res.data);
        this.students=res.data;
      })

    },
    handleClose(done) {
        this.$confirm('确认关闭？')
          .then(() => {
            done();
          })
          .catch(()=> {});
      },
    addStudent(){
      console.log(this.newStudent);
      if(this.newStudent.name==""||this.newStudent.number==""||this.newStudent.sex==""
      ||this.newStudent.classroom==""||this.newStudent.chi==""||this.newStudent.eng==""
      ||this.newStudent.math==""||this.newStudent.sys==""){
        this.$message.warning("请填写完整!!!");
      }else{
        this.newStudent.sum=this.newStudent.chi+this.newStudent.eng+this.newStudent.math+this.newStudent.sys;
        axios({
          url:'http://localhost:8080/add',
          method:'POST',
          data:this.newStudent
        })
        this.dialogVisible = false
        location.reload();
      }
      
    },
    next_page(){
      console.log(this.students.length);
      if((this.students.length-1)/5>=this.page){
        this.page += 1;
      }
      
    },
    last_page(){
      if(this.page!=1){
        this.page -=1;
      }      
    }
  },
  //分页
  computed:{
    students_for_page(){
      return this.students.slice(this.page*5-5,this.page*5);
    }
  }
}
</script>

<style>

</style>