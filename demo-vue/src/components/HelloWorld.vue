<template>

  <el-input
    v-model="username"
    placeholder="用户名"
    class="w-50 m-2"
    size="large"
  />
  <el-input
    v-model="password"
    placeholder="密码"
    class="w-50 m-2"
    size="large"
    @keyup.enter="submit"
  />

  <el-button @click="submit">Default</el-button>
</template>

<script lang="ts" setup>
import axios from "axios";
import { ElMessage } from "element-plus";
import { ref } from "vue";
const username = ref("");
const password = ref("");
const submit = () => {
  axios
    .post("/login", { username: username.value, password: password.value })
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
      }
    })
    .catch((error) => {
      ElMessage.error("查询失败");
    });
};
</script>