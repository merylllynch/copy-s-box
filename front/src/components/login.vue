<template>
  <div id="login-container">
    <div style="text-align: center;height: 50px;width: 400px;">
      登录
    </div>
    <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
      <el-form-item label="账号" prop="username">
        <el-input type="text" v-model="ruleForm.username" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input type="password" v-model="ruleForm.password" autocomplete="off" show-password></el-input>
      </el-form-item>
<!--      <el-form-item label="年龄" prop="age">-->
<!--        <el-input v-model.number="ruleForm.age"></el-input>-->
<!--      </el-form-item>-->
      <el-form-item>
        <el-button type="primary" @click="submitForm('ruleForm')">登录</el-button>
        <el-button @click="resetForm('ruleForm')">重置</el-button>
        <el-button @click="dialogFormVisibleregister = true">注册</el-button>     
        <!-- <el-dialog
  :visible.sync="dialogFormVisibleregister"
  width="30%" > -->
  <el-dialog title="请注册" :visible.sync="dialogFormVisibleregister" width="30%" center>
  <!-- <div class="offset-3">请注册</div> -->
  <span slot="footer" class="dialog-footer">
    
    <el-button type="primary" @click="register">确 定</el-button>
    <el-button @click="dialogFormVisibleregister = false">取 消</el-button>
  </span>

    <el-form-item label="用户名" :label-width="formLabelWidth">
      <el-input v-model="user_for_register.username" autocomplete="off" size="medium"></el-input>
    </el-form-item>
    <el-form-item label="密码" :label-width="formLabelWidth" >
      <el-input v-model="user_for_register.password" autocomplete="off" type="password" size="medium"></el-input>
    </el-form-item>
    <el-form-item label="确认密码" :label-width="formLabelWidth" >
      <el-input v-model="user_for_register.confirmPassword" autocomplete="off" type="password" size="medium"></el-input>
    </el-form-item>
</el-dialog>
      </el-form-item>

      
    </el-form>
    
  </div>
  
</template>

<script>
  import axios from 'axios'
  export default {
    name: "login",
  data() {
    registeroutcome:'';
    var validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入账户'));
      } else {
        if (this.ruleForm.pass !== '') {
          this.$refs.ruleForm.validateField('pass');
        }
        callback();
      }
    };
    var validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'));
      } else {
        callback();
      }
    };
    return {
      user_for_register:{
        username: '',
        password: '',
        confirmPassword:'',
      },
      dialogFormVisibleregister:false,
      ruleForm: {
        username: '',
        password: '',
      },
      rules: {
        username: [
          { validator: validatePass, trigger: 'blur' }
        ],
        password: [
          { validator: validatePass2, trigger: 'blur' }
        ],
        // age: [
        //   { validator: checkAge, trigger: 'blur' }
        // ]
      },
      
    };
  },
  methods: {
    register(){
      if(this.user_for_register.password!=this.user_for_register.confirmPassword){
        this.$message.error("密码与确认密码内容不一致!!!");
      }else{
        axios({
        url:"http://localhost:8080/register",
        method:"POST",
        data:this.user_for_register
      }).then(res=>{
        console.log(res.data);
        this.registeroutcome=res.data;
      })
      if(this.registeroutcome==1){
        this.dialogFormVisibleregister=false;
        this.$message.success("注册成功!!!");
      }else{
        this.$message.error("注册失败!!!存在相同用户名");
      }      
      }
      
    },
    //登录按钮事件
    submitForm(formName) {
      axios({
        url:'http://localhost:8080/login',
        data:this.ruleForm,
        method:"POST"
      }).then(res =>{
        console.log(res.data);
        //如果数据库中有该用户则跳转至home页面
        if(res.data == "1"){
          //在会话存储空间中存入isLogined为1
          sessionStorage.setItem("isLogined","1");
          this.$message.success("登录成功!!!");
          this.$router.push({path: "/home"});
        }
        else{
          this.$message.error("用户名或密码错误");
        }
      })
      
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    }
  }
  }
</script>

<style scoped>
  body{
    margin: 0;
  }
  #login-container{
    width: 400px;
    height: 290px;
    background: #e5e9f2;
    position: absolute;
    left: 50%;
    top: 50%;
    margin-left: -220px;
    margin-top: -170px;
    border-radius: 5px;
    padding-top: 40px;
    padding-right: 40px;
  }
</style>
