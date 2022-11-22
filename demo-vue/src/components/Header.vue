<template>
  <div>
    <el-row class="tac">
      <el-col :span="5">
        <div class="aa">校园帮帮网</div>
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
            <el-icon size="large"><House /></el-icon>
            <span class="font">委托</span>
          </el-menu-item>

          <el-menu-item index="moments" @click="skip('/home/moments')">
            <el-icon size="large"><document /></el-icon>
            <span class="font">论坛</span>
          </el-menu-item>

          <el-menu-item index="list" @click="skip('/home/list')">
            <el-icon><setting /></el-icon>
            <span class="font">看一看</span>
          </el-menu-item>

          <el-menu-item index="person" @click="skip('/home/person')">
            <el-icon><UserFilled /></el-icon>
            <span class="font">个人中心</span>
          </el-menu-item>

          <el-menu-item index="setting" @click="skip('/home/setting')">
            <el-icon><setting /></el-icon>
            <span class="font">设置</span>
          </el-menu-item>

          <el-popover
            placement="bottom"
            :width="250"
            trigger="click"
          >
            <template #reference>
              <el-menu-item>
                <div class="aa">
                  <el-avatar
                    :size="50"
                    :src="
                      'http://localhost:8080/demo/UpdateAndDown/down?name=' +
                      avatar
                    "
                  />
                  {{ studentName }}
                </div>
                <!-- <el-avatar :size="50" :src="state.circleUrl" /> -->
              </el-menu-item>
            </template>
            <div @click="centerDialogVisible=true" style=" cursor: pointer;font-size: large;">
               退出登录
            </div>
           
          </el-popover>
        </el-menu>
      </el-col>
    </el-row>
    <el-dialog
    v-model="centerDialogVisible"
    title="注销用户？"
    width="30%"
    show-close="false"
    class="button"
  
  >
    <span >是否注销用户。如果您只想切换用户，可以通过切换用户来实现</span>
    <template #footer>
      <span class="dialog-footer">
        
        <el-button type="primary" @click="loginOut" style="width:450px; ">
          登出
        </el-button>
        <br/>
        <el-button @click="centerDialogVisible=false" style="width:450px; ">取消</el-button>
      </span>
    </template>
  </el-dialog>
  </div>
</template>

<script lang="ts" setup>
import {
  Document,
  Menu as IconMenu,
  Location,
  Setting,
} from "@element-plus/icons-vue";
import axios from "axios";
import { ElMessage } from "element-plus";
import { indexOf } from "lodash";
import { onMounted, reactive, ref } from "vue";
import router from "../router";
const studentName = sessionStorage.getItem("studentName");
const avatar = sessionStorage.getItem("avatar");
const centerDialogVisible = ref(false)
onMounted(() => {});
//退出登录
const loginOut=()=>{
  axios
    .delete(
      "/demo/student/loginOut",
      {
        headers: { token: sessionStorage.getItem("token") },
      }
    )
    .then((res) => {
      if(res.data.msgId==="A001"){
         router.push({
          path: "/login",
        });
        centerDialogVisible.value = false
      }
      if(res.data.msgId==="A002"){
        ElMessage.error("退出登录失败");
        centerDialogVisible.value = false
      }
    })
    .catch((error) => {
      ElMessage.error("退出登录失败");
    });
}
const state = reactive({
  circleUrl: "blob:http://localhost:3000/8f1733d7-e5c0-451d-a104-efef58d2f171",
  squareUrl:
    "https://cube.elemecdn.com/9/c2/f0ee8a3c7c9638a54940382568c9dpng.png",
  sizeList: ["small", "", "large"] as const,
});
const handleOpen = (key: string, keyPath: string[]) => {
  console.log("studentName", studentName);
  console.log(key, keyPath);
  // router.push({
  //         path: keyPath[0],
  //       });
}; 
const handleClose = (key: string, keyPath: string[]) => {
  console.log(key, "cyujgghxc");
};
const skip = (Path) => {
  router.push({
    path: Path,
  });
};
</script>
<style >
.font {
  font-size: x-large;
}
.aa {
  left: 10px;
  font-size: x-large;
  cursor: pointer;
}
.bb{
  z-index: 1000;
}
.button{
width:500px;
 text-align: center;
 font: x-larger;
}
</style>
