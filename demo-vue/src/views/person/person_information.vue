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
          <el-aside width="1000px">阿松大</el-aside>
          <el-main>Main</el-main>
        </el-container>
      </el-container>
            
    </div>
  </template>
    <script setup lang="ts">
  import { Search } from "@element-plus/icons-vue";
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