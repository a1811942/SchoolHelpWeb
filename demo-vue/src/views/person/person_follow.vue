<template>
  <div class="page-box">
    <el-container>
      <el-header width="100%" height="160px"
      >
        <el-container>
      <el-header height="70px">
        <el-card class="box-card1" shadow="never">
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
        <el-aside width="500px">
          
          <el-card class="box-card2" shadow="hover"></el-card>
          <el-card class="box-card3" shadow="hover"></el-card>
        </el-aside>
        <el-main>   
        </el-main>
      </el-container>
    </el-container>
      </el-header>
      <el-container>
        <el-aside width="1000px">
          


          <div v-for="i in 10" :key="i">
            <el-container>
              <el-aside width="60px">
                <el-avatar
                  :size="60"
                  :src="'http://localhost:8080/demo/UpdateAndDown/down?name='"
                />
              </el-aside>
              <el-container>
                <el-header height="10px">
                  <el-button style="float: right" color="#000000"
                    >取消关注</el-button
                  >
                  <div style="font-weight: bold">刘士鑫</div>
                </el-header>
                <el-main>
                  a811911149
                  <br />
                  We're the lead NASA center for the @Space_Station ,
                  @NASA_Orion spacecraft, and @NASA_Gateway . We're home to
                  Mission Control and the @NASA_Astronauts corps.
                </el-main>
              </el-container>
            </el-container>
          </div>
        </el-aside>
        <el-main>Main</el-main>
      </el-container>
    </el-container>
  </div>
</template>
      <script lang="ts">
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
.box-card2 {
  width: 500px;
  height: 70px;
  position: fixed;
  left: 250px;
  right: 0;
  z-index: 4;
}
.box-card3 {
  width: 500px;
  height: 70px;
  position: fixed;
  left: 750px;
  right: 0;
  z-index: 4;
}
.box-card {
  width: 99%;
  cursor: pointer;
}
.page-box {
  background-color: #ffffff;
  height: 100%;
}
</style>