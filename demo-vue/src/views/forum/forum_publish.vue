<template>
  <div class="common-layout">
    <el-container>
      <el-header width="100%">
        <el-input v-model="input" placeholder="Please input" clearable style="with:50px"/>
        <el-button type="danger" round @click="publish">发文章</el-button>
      </el-header>
      <el-container>
        <el-aside width="1000px">
          <el-input
            v-model="textarea1"
            placeholder="Please input"
            show-word-limit
            type="textarea"
            :rows="30"
            class="text"
          />
        </el-aside>
        <el-main>Main</el-main>
      </el-container>
    </el-container>
  </div>
</template>
    <script setup lang="ts">
import { Search } from "@element-plus/icons-vue";
import { ref } from "@vue/reactivity";
const textarea1 = ref("");
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
.box-card {
  width: 99%;
  cursor: pointer;
}
</style>