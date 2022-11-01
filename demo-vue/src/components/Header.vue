<template>
  <div >
  <el-row class="tac">
    <el-col :span="5" >
      <h5 class="mb-2">Default colors</h5>
      <el-menu
        default-active="2"
        class="el-menu-vertical-demo"
        @open="handleOpen"
        @close="handleClose"
      >
        <!-- <el-sub-menu index="1">
          <template #title>
            <el-icon><location /></el-icon>
            <span>Navigator One</span>
          </template>
          <el-menu-item-group title="Group One">
            <el-menu-item index="1-1">item one</el-menu-item>
            <el-menu-item index="1-2">item two</el-menu-item>
          </el-menu-item-group>
          <el-menu-item-group title="Group Two">
            <el-menu-item index="1-3">item three</el-menu-item>
          </el-menu-item-group>
          <el-sub-menu index="1-4">
            <template #title>item four</template>
            <el-menu-item index="1-4-1">item one</el-menu-item>
          </el-sub-menu>
        </el-sub-menu> -->


        <el-menu-item index="commission" @click="skip('/home/commission')">
          <el-icon size="large"><House  /></el-icon>
          <span class="font" >委托</span>
        </el-menu-item>

        <el-menu-item index="moments" @click="skip('/home/moments')">
          <el-icon size="large"><document /></el-icon>
          <span class="font" >论坛</span>
        </el-menu-item>


        <el-menu-item index="list" @click="skip('/home/list')">
          <el-icon ><setting /></el-icon>
          <span class="font" >看一看</span>
        </el-menu-item>

        <el-menu-item index="person" @click="skip('/home/person')">
          <el-icon><UserFilled /></el-icon>
          <span class="font" >个人中心</span>
        </el-menu-item>
        <el-menu-item index="list">
         {{studentName}}
          <!-- <el-avatar :size="50" :src="state.circleUrl" /> -->
        </el-menu-item>
      </el-menu>
    
    </el-col>
    
  </el-row>
</div>
</template>

<script lang="ts" setup>
import {
  Document,
  Menu as IconMenu,
  Location,
  Setting,
} from '@element-plus/icons-vue'
import { indexOf } from 'lodash';
import { reactive, ref } from 'vue'
import router from '../router';
const studentName=sessionStorage.getItem("studentName")
const state = reactive({
  circleUrl:
    'blob:http://localhost:3000/8f1733d7-e5c0-451d-a104-efef58d2f171',
  squareUrl:
    'https://cube.elemecdn.com/9/c2/f0ee8a3c7c9638a54940382568c9dpng.png',
  sizeList: ['small', '', 'large'] as const,
})
const handleOpen = (key: string, keyPath: string[]) => {
  console.log("studentName",studentName)
  console.log(key, keyPath)
  // router.push({
  //         path: keyPath[0],
  //       });
  
}
const handleClose = (key: string, keyPath: string[]) => {
  console.log(key, "cyujgghxc")
}
const skip = (Path) => {
  router.push({
          path: Path,
        });
  
}
</script>
<style >
  .font {
    font-size: x-large;
  }
</style>
