<template>

<div class="common-layout">
    <el-container>
      <el-aside width="1200px"></el-aside>
      <el-main> 
        <el-card class="box-card">
            <div style="text-align:center">大学生专属平台</div>
            
            <el-input v-model="username" placeholder="请输入用户名" style="width: 220px text-align:center" size="large" /><br></br><br></br>
            <el-input v-model="password" type="password" placeholder="请输入密码" style="width: 220px text-align:center" size="large" /><br></br><br></br>
            <el-button @click="login" style="width: 110px">登录</el-button>
            <el-button @click="register" style="width: 110px">注册</el-button>
        </el-card>
    </el-main>
    </el-container>
  </div>




  </template>
  <script lang="ts" setup>
import { useRouter } from "vue-router";
import axios from "axios";
import { ElMessage } from "element-plus";
import { ref, registerRuntimeCompiler } from "vue";
const username = ref("");
const password = ref("");
const router = useRouter();
var ws = new WebSocket("ws://localhost:8080/demo/chat");
const login = () => {
  axios
    .post("/demo/student/login", { username: username.value, password: password.value })
    .then((res) => {
      if (res.data.msgId == "A001") {
        ElMessage.error("用户名不存在");
      } else if (res.data.msgId == "A002") {
        ElMessage.error("密码错误");
      } else {
        ElMessage.success({
          message: "登录成功",
          type: "success",
        });
        sessionStorage.setItem("token", res.data.result.token);
        sessionStorage.setItem("username", res.data.result.username);
        sessionStorage.setItem("studentId", res.data.result.studentId);
        sessionStorage.setItem("studentName", res.data.result.studentName);
        sessionStorage.setItem("avatar", res.data.result.avatar);
        console.log("token", sessionStorage.getItem("token"));
        console.log("avatar", sessionStorage.getItem("avatar"));
        console.log("studentId", sessionStorage.getItem("studentId"));
        ws.onopen = () => {
    console.log("链接成功");
  };
        router.push({
          path: "/home",
        });
      }
    })
    .catch((error) => {
      ElMessage.error("登陆失败");
    });
};
const register=()=>{
  router.push({
          path: "/register",
        });
}
</script>
  <style scoped>
.text {
  font-size: 14px;
}

.item {
  padding: 18px 0;
}

.box-card {
  width: 300px;
}
</style>