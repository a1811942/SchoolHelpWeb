<template>
  <div>
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
          <el-card class="box-card">
            <el-container>
              <el-header height="500px"
                ><div class="fromName">{{ toStudentName }}</div>

                <div class="student" v-for="i in message.fromMessage" :key="i">
                  :{{ studentName}} <br />
                  {{ i }} <br /><br />
                </div>
                <br /><br />

                <br />

                <div class="toStudent" v-for="i in message.toMessage" :key="i">
                  {{ toStudentName }}:<br />
                  {{ i}}
                </div>

              </el-header>
              <el-main>
                <el-input
                  v-model="toMessage"
                  maxlength="100"
                  placeholder="Please input"
                  show-word-limit
                  type="textarea"
                  :rows="6"
                  class="text"
                />
                <el-button @click="sendMessage">发送信息</el-button>
              </el-main>
            </el-container>
          </el-card>
        </el-aside>
        <el-main>Main</el-main>
      </el-container>
    </el-container>
  </div>
</template>
 <script setup lang="ts">
import axios from "axios";
import { ElMessage } from "element-plus";
import { computed, onMounted, reactive, ref } from "vue";
import { useRoute, useRouter } from "vue-router";
const router = useRouter();
const route = useRoute();
//发送的信息
const message=ref({
   toMessage:[],
 fromMessage:[]
})
const  fromMessage=ref("")
const  toMessage=ref("")

const toStudentName = ref("");
var ws = new WebSocket("ws://localhost:8080/demo/chat");
ws.onmessage = (evt) => {
  var dataStr = evt;
};
const toStudentId = ref(route.query.toStudentId);
var studentName = sessionStorage.getItem("studentName");
onMounted(() => {
  getStudentById();
  getMessageByKey();
  // ws.onopen = () => {
  //   console.log("链接成功");
  // };
  //服务器每次返回信息都会执行一次onmessage方法
});
const str = ref("");
//发送消息
const sendMessage = () => {
  var json = { toStudentName: toStudentName.value, message: toMessage.value };
  ws.send(JSON.stringify(json));
  str.value = toMessage.value;
  console.log("toME", toMessage.value);
  console.log("str", str.value);
  saveMessage();
};
//接收
ws.onmessage = (evt) => {
  var res = JSON.parse(evt.data);
  fromMessage.value = res.message;
  console.log("fromME", fromMessage.value);
};

ws.onclose = (evt) => {
  console.log("接收消息", evt);
};
const saveMessage = () => {
  axios
    .post(
      "/demo/redis/saveMessage",
      {
        studentId: sessionStorage.getItem("studentId"),
        toStudentId: toStudentId.value,
        message: str.value,
      },
      {
        headers: {
          token: sessionStorage.getItem("token"),
        },
      }
    )
    .then((res) => {
      getMessageByKey();
    })
    .catch((error) => {
      ElMessage.error("缓存聊天记录失败");
    });
};
const getMessageByKey = () => {
  axios
    .post(
      "/demo/redis/getMessageByKey",
      {
        studentId: sessionStorage.getItem("studentId"),
        toStudentId: toStudentId.value,
      },
      {
        headers: {
          token: sessionStorage.getItem("token"),
        },
      }
    )
    .then((res) => {
      message.value.fromMessage=res.data.result.myMessage
      message.value.toMessage=res.data.result.yourMessage
      console.log("res.data.result",res.data.result)
    })
    .catch((error) => {
      ElMessage.error("查询聊天记录失败");
    });
};
const getStudentById = () => {
  axios
    .post("/demo/commission/getStudentById", toStudentId.value, {
      headers: {
        token: sessionStorage.getItem("token"),
      },
    })
    .then((res) => {
      toStudentName.value = res.data.result.name;
    })
    .catch((error) => {
      ElMessage.error("接单失败");
    });
};
</script>
 <style scoped>
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
  height: 831px;
  bottom: 0px;
  padding-bottom: 0px;
}
.text {
  width: 900px;
  left: 0px;
  right: 0px;
}
.fromName {
  text-align: center;
  font-size: x-large;
}
.student {
  text-align: right;
}
.toStudent {
  text-align: left;
}
</style>