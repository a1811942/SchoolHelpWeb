<template>
  <div class="common-layout">
    <el-container>
      <el-header width="100%">
        <el-card class="box-card1" shadow="hover">
          <div class="bold">
            <router-link to="/home/moments">
              <el-button :icon="Search" size="large" circle
                ><el-icon><Back /></el-icon
              ></el-button>
            </router-link>
            {{ moments.name }}
          </div>
        </el-card>
      </el-header>

      <el-container>
        <el-aside width="1000px">
          <el-card class="box-card" shadow="never">
            <el-container>
              <el-aside width="50px">
                <el-avatar
                  :size="50"
                  :src="
                    'http://localhost:8080/demo/UpdateAndDown/down?name=' +
                    moments.avatar
                  "
                />
                <el-dropdown :hide-on-click="false">
                  <span class="el-dropdown-link">
                    Dropdown List<el-icon class="el-icon--right"
                      ><arrow-down
                    /></el-icon>
                  </span>
                  <template #dropdown>
                    <el-dropdown-menu>
                      <el-dropdown-item>Action 1</el-dropdown-item>
                      <el-dropdown-item>Action 2</el-dropdown-item>
                      <el-dropdown-item>Action 3</el-dropdown-item>
                      <el-dropdown-item disabled>Action 4</el-dropdown-item>
                      <el-dropdown-item divided>Action 5</el-dropdown-item>
                      <el-dropdown-item divided>Action 6</el-dropdown-item>
                    </el-dropdown-menu>
                  </template>
                </el-dropdown>
              </el-aside>
              <el-container>
                <el-header height="10px"
                  ><div class="name">
                    {{ moments.name }}
                  </div>
                </el-header>
                <el-main>
                  <div class="content">
                    <!-- <img src="../images/pjecue.jpg"/> -->
                    {{ moments.content }}
                    <!-- 显示图片 -->
                    <div class="demo-image__placeholder">
                      <div class="block" v-for="image in url" :key="image">
                        <el-image src="require('../images/pjecue.jpg')" />
                      </div>
                    </div>

                    <div class="demo-image__preview">
                      <el-image
                        v-for="image in photo"
                        :key="image"
                        style="width: 30%; height: 30%"
                        :src="
                          'http://localhost:8080/demo/UpdateAndDown/down?name=' +
                          image
                        "
                        :preview-src-list="srcList"
                        :initial-index="1"
                        preview="1"
                        fit="cover"
                      />
                    </div>
                  </div>
                  <br />
                  {{ moments.date }}
                </el-main>
                <el-footer>
                  <el-button
                    text
                    circle
                    @click.stop="dialogTableVisible = true"
                  >
                    <el-icon size="large"><ChatRound /></el-icon
                  ></el-button>
                  &#12288;
                  <el-button text circle>
                    <el-icon size="large"><Pointer /></el-icon>
                  </el-button>
                </el-footer>
              </el-container>
            </el-container>
          </el-card>
          <!-- 第二个卡片-------------------------------- -->
          <el-card shadow="never" class="box-card">
            <el-input
              :rows="2"
              type="textarea"
              placeholder="评论"
              class="textarea"
              clearable
              v-model="comment"
            />

            <br />
            <br />
            <el-row justify="end">
              <el-button type="primary" round @click="saveComment"
                >评论</el-button
              >
            </el-row>
          </el-card>
          <!-- 之后的卡片 查看评论 -->

          <el-card class="box-card" shadow="hover">
            <div v-for="o in getComment.getComment" :key="o">
              <el-container>
                <el-aside width="50px">
                  <el-avatar :size="50" :src="circleUrl"
                /></el-aside>
                <el-container>
                  <el-header height="10px">
                    {{ o.name }} {{ o.date }}</el-header
                  >
                  <el-main>
                    {{ o.comment }}
                  </el-main>
                </el-container>
              </el-container>
              <br />
            </div>
          </el-card>
        </el-aside>
        <el-main>Main</el-main>
      </el-container>
    </el-container>
  </div>

  <!-- 外面评论对话框 -->
  <el-dialog v-model="dialogTableVisible" title="评论">
    <el-input
      :rows="2"
      type="textarea"
      placeholder="评论"
      class="textarea"
      clearable
      v-model="comment"
    />

    <br />
    <br />
    <el-row justify="end">
      <el-button type="primary" round @click="saveComment">评论</el-button>
    </el-row>
  </el-dialog>
</template>
<script setup lang="ts">
import { computed, onBeforeMount, onMounted, reactive, ref } from "vue";
import { ElMessage, ElMessageBox } from "element-plus";
import type { Action } from "element-plus";
import axios from "axios";
import { useRoute, useRouter } from "vue-router";

const dialogTableVisible = ref(false);
const dialogTableVisible1 = ref(false);
const router = useRouter();
const route = useRoute();
console.log(route.query);
var momentsId = route.query.momentsId;
const url = ref([]);
const srcList = ref("");

//返回到动态页面
const back = () => {
  router.push({
    path: "/home/moments",
  });
};
//查询的评论信息
const getComment = reactive({
  getComment: [],
  name: "",
  comment: "",
  date: "",
  photo: "",
});
//根据动态的id查询所有评论
const getCommentAndStudentByMomentId = () => {
  axios
    .post("/demo/comment/getCommentAndStudentByMomentId", momentsId, {
      headers: {
        token: sessionStorage.getItem("token"),
      },
    })
    .then((res) => {
      getComment.getComment = res.data.result;
      getComment.name = res.data.result.name;
      getComment.getComment = res.data.result;

      console.log("res.data.result", res.data.result);
      console.log("res.data.result.date", res.data.result.date);
      getMomentsAndStudentById;
    })
    .catch((error) => {
      ElMessage.error("查看评论失败");
    });
};

//填写的评论
const comment = ref("");
//评论
const saveComment = () => {
  axios
    .post(
      "/demo/comment/saveComment",
      {
        comment: comment.value,
        studentId: sessionStorage.getItem("studentId"),
        studentName: sessionStorage.getItem("studentName"),
        momentsId: momentsId,
      },
      {
        headers: {
          token: sessionStorage.getItem("token"),
        },
      }
    )
    .then((res) => {
      comment.value = "";
      getCommentAndStudentByMomentId();
    })
    .catch((error) => {
      ElMessage.error("评论失败");
    });
};
//动态显示的信息
const moments = reactive({
  photoName: [],
  content: "",
  name: "",
  date: "",
  avatar: "",
});

// onBeforeMount(()=>{
//   downPhoto();
// });
onMounted(() => {
  console.log("url", url.value);

  getMomentsAndStudentById();
  getCommentAndStudentByMomentId();
  getPhotoName();
  // downPhoto();
  console.log("momentId", route.query);
});
//根据动态的id查询详细信息
const getMomentsAndStudentById = () => {
  axios
    .post("/demo/moments/getMomentsAndStudentById", route.query.momentsId, {
      headers: {
        token: sessionStorage.getItem("token"),
      },
    })
    .then((res) => {
      moments.content = res.data.result.content;
      moments.name = res.data.result.name;
      moments.date = res.data.result.date;
      moments.avatar = res.data.result.avatar;
    })
    .catch((error) => {
      ElMessage.error("根据id查询照片名称失败");
    });
};

//照片名称
const photo = ref([]);
//根据momentId查询照片名称
const getPhotoName = () => {
  axios
    .post("/demo/photo/getPhotoByMomentsById", route.query.momentsId, {
      headers: {
        token: sessionStorage.getItem("token"),
      },
    })
    .then((res) => {
      photo.value = res.data.result;
      console.log("photo.value", photo.value);

      console.log("a=========", a);
      console.log("a.length=========", a.length);
      // downPhoto();
    })
    .catch((error) => {
      ElMessage.error("根据id查询动态详细信息失败");
    });
};

//回调函数值（url）
let tmp;
//中间数组
let a = [];
let vv = 0; // 判断什么时候将中间数组a的值赋给url
function recoll(aa) {
  a.push(aa); //有错误 但是用起来好像没什么影响
  vv = vv + 1;
  if (vv == photo.value.length) {
    //
    url.value = a;
  }
  console.log("url===", url.value);
}
//下载图片
const downPhoto = () => {
  for (var i = 0; i < photo.value.length; i++) {
    axios
      .post("/demo/UpdateAndDown/down", photo.value[i], {
        responseType: "blob",
        headers: { token: sessionStorage.getItem("token") },
      })
      .then((res) => {
        console.log(" res", res.data);
        console.log(" photo.value.length", photo.value.length);
        let blob = new Blob([res.data]); // 返回的文件流数据
        console.log(
          "window.URL.createObjectURL(blob)====",
          window.URL.createObjectURL(blob)
        );

        tmp = window.URL.createObjectURL(blob); //转化为路径
        //回调函数（push时不可以在.then中进行，因为.then时异步的，需要使用回调函数）
        recoll(tmp);
        console.log("a", a);
      })
      .catch((error) => {
        ElMessage.error("下载图片失败");
      });

    console.log("a=", a);
  }
};
</script>
<style scoped>
.demo-image__preview {
  white-space: nowrap;
}
.demo-image__error .image-slot {
  font-size: 30px;
}
.demo-image__error .image-slot .el-icon {
  font-size: 30px;
}
.demo-image__error .el-image {
  width: 100%;
  height: 200px;
}
.text {
  font-size: 14px;
}

.item {
  padding: 18px 0;
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
}
/* .block {
  top: 50px;
} */
.textarea {
  border: 0;
  outline: none;
}
.bold {
  font-weight: bold;
  font-size: x-large;
}
.content {
  font-weight: 540;
  font-size: x-large;
}
.name {
  font-weight: 600;
  font-size: large;
}

.demo-image__placeholder .block {
  padding: 30px 0;
  text-align: center;
  border-right: solid 1px var(--el-border-color);
  display: inline-block;
  width: 30%;
  box-sizing: border-box;
  vertical-align: top;
}
.demo-image__placeholder .demonstration {
  display: block;
  color: var(--el-text-color-secondary);
  font-size: 14px;
  margin-bottom: 20px;
}
.demo-image__placeholder .el-image {
  padding: 0 5px;
  max-width: 300px;
  max-height: 200px;
}

.demo-image__placeholder.image-slot {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 100%;
  background: var(--el-fill-color-light);
  color: var(--el-text-color-secondary);
  font-size: 14px;
}
.demo-image__placeholder .dot {
  animation: dot 2s infinite steps(3, start);
  overflow: hidden;
}
</style>