<template>
    <tr>     
      <td v-show="!is_edit">{{ student.number }}</td>
      <td v-show="!is_edit">{{ student.name }}</td>    
      <td v-show="!is_edit">{{ student.sex }}</td>
      <td v-show="!is_edit">{{ student.classroom }}</td>
      <td v-show="!is_edit">{{ student.chi }}</td>
      <td v-show="!is_edit">{{ student.math }}</td>
      <td v-show="!is_edit">{{ student.eng }}</td>
      <td v-show="!is_edit">{{ student.sys }}</td> 
      <td v-show="!is_edit">{{ student.sum }}</td> 
      <td v-show="!is_edit">
        <el-button-group style="width: 70px;">
          <el-button type="primary" icon="el-icon-edit" circle @click="modify" size="small"></el-button>
        <el-button type="danger" icon="el-icon-delete" circle @click="deleteStudent" size="small"></el-button>
        </el-button-group>
        
      </td>

      <td v-show="is_edit"><el-input  class="w-50;h-50" type="text" v-model="student.number" oninput ="value=value.replace(/[^\d]/g,'')"/></td>
      <td v-show="is_edit"><el-input  class="w-50;h-50" type="text" v-model="student.name" /></td>    
      <td v-show="is_edit"><el-input  class="w-50;h-50" type="text" v-model="student.sex" /></td>
      <td v-show="is_edit"><el-input  class="w-50;h-50" type="text" v-model="student.classroom" /></td>
      <td v-show="is_edit"><el-input  class="w-50;h-50" type="text" v-model.number="student.chi" oninput ="value=value.replace(/[^\d]/g,'')"/></td>
      <td v-show="is_edit"><el-input  class="w-50;h-50" type="text" v-model.number="student.math" oninput ="value=value.replace(/[^\d]/g,'')"/></td>
      <td v-show="is_edit"><el-input  class="w-50;h-50" type="text" v-model.number="student.eng" oninput ="value=value.replace(/[^\d]/g,'')"/></td>
      <td v-show="is_edit"><el-input  class="w-50;h-50" type="text" v-model.number="student.sys" oninput ="value=value.replace(/[^\d]/g,'')"/></td> 
      <td v-show="is_edit">
        <el-input  class="w-50;h-50" type="text" v-model.number="student.sum" :disabled="true"/>
      </td> 
      <td v-show="is_edit" >
        <el-button-group >
          <el-button type="success" icon="el-icon-check" circle @click="save" size="mini" style="top: 5px;"></el-button>
        <!-- <el-button type="danger" icon="el-icon-delete" circle size="small"></el-button> -->
        </el-button-group>
        
      </td>
    </tr>
    
</template>

<script>
import axios from 'axios';

export default {
    props:["student"],
  
    data(){
        return {
            is_edit:false,
            
        }
    },
    methods:{
        modify(){
            this.is_edit=true;
        },
        save(){
          this.student.sum=this.student.chi+this.student.eng+this.student.math+this.student.sys;
          axios({
            url:"http://localhost:8080/update",
            method:"POST",
            data:this.student
          })
          this.is_edit=false;
        },
        deleteStudent(){
          axios({
            url:"http://localhost:8080/delete",
            method:"POST",
            data:this.student
          }).then(res=>{
            console.log(res.data);            
          })
          location.reload();
        }
    }
}
</script>

<style>

</style>