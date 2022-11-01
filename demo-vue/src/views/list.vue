<template>
    <div class="common-layout">
      <el-container>
        <el-header width="100%">
          <el-card class="box-card1" shadow="hover">
            <el-container>
              <el-aside width="950px">
                <div class="top-card">
                  <el-form :inline="true"> 委托 </el-form>
                </div>
              </el-aside>
              <el-container>
                <el-header>
                  <el-input
                    :suffix-icon="Search"
                    placeholder="搜索委托"
                    size="large"
                    class="headerInput"
                  />
                </el-header>
                </el-container>
                </el-container>
                </el-card>
                </el-header>
                <el-container>
          <el-aside width="1000px">
            <div v-for="i in Forum" :key="i" >
            <el-card class="card " >
              <div class="card-font">{{i.title}} </div>
                <div>{{i.forumContent}}</div>
                <div class="card-date">{{i.name}}{{i.date}}</div>
                
              </el-card>
          
          </div>
          </el-aside>
          <el-main>Main</el-main>
        </el-container>
      </el-container>
            
    </div>
  </template>
    <script setup lang="ts">
  import { Search } from "@element-plus/icons-vue";
import axios from "axios";
import { ElMessage } from "element-plus";
import { onMounted, ref } from "vue";
const Forum=ref()
onMounted(() => {
  getALlForumAndStudent();

});
const getALlForumAndStudent=()=>{
  axios
    .post("/demo/forum/getALlForumAndStudent", {}, {
      headers: {
        token: sessionStorage.getItem("token"),
      },
    })
    .then((res) => {
      Forum.value=res.data.result
    })
    .catch((error) => {
      ElMessage.error("查询论坛失败");
    });
}
  </script>
    <style>
  .top-card {
    font-weight: bold;
    font-size: x-large;
  }
  .headerInput {
    width: 400px;
    border-right: 0ch;
  }
  .box-card1 {
    width: 84%;
    height: 70px;
    position: fixed;
    left: 250px;
    right: 0;
    z-index: 5;
  }
  .card {
    height: 300px;
    
  }
  .card-font{
    font-size: xx-large;
    cursor: pointer;
  }
  .card-date{
    float: inline-end;
    font-size: large;
    text-align:right;
    bottom: 0;
  }
  </style>