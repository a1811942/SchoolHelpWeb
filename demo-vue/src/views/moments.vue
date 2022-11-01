<template>
  <div class="page-box">
  <div class="common-layout">
    <el-container>
      <el-header width="100%">
        <el-card class="box-card1" shadow="hover">
          <el-container>
            <el-aside width="950px">
              <div class="top-card">
                <el-form :inline="true"> 动态 </el-form>
              </div>
            </el-aside>
            <el-container>
              <el-header>
                <el-input
                  :suffix-icon="Search"
                  placeholder="搜索动态"
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
          <!-- 发布动态 -->
          <el-card class="releaseMoments2">
            <el-input class="releaseMoments1" type="textarea" :rows="4">
            </el-input>
            <!-- 上传照片 -->
            <el-upload
              action="http://localhost:8080/demo/UpdateAndDown/upload"
              list-type="picture-card"
              :on-preview="handlePictureCardPreview"
              :on-remove="handleRemove"
              :headers="headerObj"
            >
              <el-icon><Plus /></el-icon>
            </el-upload>

            <el-dialog v-model="dialogVisible">
              <img w-full :src="dialogImageUrl" alt="Preview Image" />
            </el-dialog>

            <el-row justify="end">
              <el-button type="primary" round @click="saveComment"
                >发布</el-button
              >
            </el-row>
          </el-card>

          <div v-for="o in moments.moments" :key="o">
            <el-card
              class="box-card"
              shadow="hover"
              @click="ViewInformation(o.id)"
            >
              <el-container>
                <el-aside width="50px">
                  <el-avatar :size="50" :src="circleUrl"
                /></el-aside>
                <el-container>
                  <el-header height="10px">
                    <div class="name">
                      {{ o.name }}
                    </div>
                    {{ o.date }}
                  </el-header>
                  <el-main>
                    <br />
                    <br />
                    <div class="comment">
                      {{ o.content }}
                    <img src="../images/pjecue.jpg"/>
                      <!-- 显示图片 -->
                      <div class="demo-image__placeholder">
                        <el-image src="" />
                      </div>
                    </div>
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
          </div>
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
</div>
</template>
<script setup lang="ts">
import { computed, onMounted, reactive, ref } from "vue";
import {
  ElMessage,
  ElMessageBox,
  UploadFile,
  UploadProps,
  UploadUserFile,
} from "element-plus";
import type { Action } from "element-plus";
import axios from "axios";
import { Search } from "@element-plus/icons-vue";
import { useRouter } from "vue-router";
import { query } from "express";
const dialogTableVisible = ref(false);
const dialogTableVisible1 = ref(false);
const router = useRouter();
const url = ref([]);
const srcList = ref("");
// 上传照片

const dialogImageUrl = ref("");
const dialogVisible = ref(false);
const headerObj = ref({ token: sessionStorage.getItem("token") });
const handleRemove: UploadProps["onRemove"] = (uploadFile, uploadFiles) => {
  console.log(uploadFile, uploadFiles);
  console.log("uploadFile==", uploadFile.name);
  console.log("删除==========", uploadFiles);
  deletePhoto(uploadFile.name);
};

const handlePictureCardPreview: UploadProps["onPreview"] = (uploadFile) => {
  dialogImageUrl.value = uploadFile.url!;
  dialogVisible.value = true;
};
const beforeAvatarUpload: UploadProps["beforeUpload"] = (rawFile) => {
  if (rawFile.type !== "image/jpeg") {
    ElMessage.error("Avatar picture must be JPG format!");
    return false;
  } else if (rawFile.size / 1024 / 1024 > 5) {
    ElMessage.error("Avatar picture size can not exceed 2MB!");
    return false;
  }
  return true;
};

//下载图片
const downPhoto = () => {
  for (var i = 0; i < moments.moments.length; i++) {
    axios
      .post("/demo/UpdateAndDown/down", moments.moments[i].photo_name, {
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
//回调函数值（url）
let tmp;
//中间数组
let a = [];
let vv = 0; // 判断什么时候将中间数组a的值赋给url
function recoll(aa) {
  a.push(aa); //有错误 但是用起来好像没什么影响
  vv = vv + 1;
  if (vv == moments.moments.length) {
    //
    url.value = a;
  }
  console.log("url===", url.value);
}

//查询详细的动态信息
const ViewInformation = (id) => {
  router.push({
    path: "information",
    query: { momentsId: id },
  });
};
//评论
const comment = ref("");
const saveComment = () => {
  axios
    .post(
      "/demo/comment/saveComment",
      {
        comment: comment.value,
        studentId: sessionStorage.getItem("studentId"),
        studentName: sessionStorage.getItem("studentName"),
      },
      {
        headers: {
          token: sessionStorage.getItem("token"),
        },
      }
    )
    .then((res) => {
      comment.value = "";
    })
    .catch((error) => {
      ElMessage.error("评论失败");
    });
};
//朋友圈
const moments = reactive({
  moments: [],
  content: "",
  name: "",
  date: "",
});
onMounted(() => {
  getMomentsAndStudent();
});
//查询用户发布的朋友圈信息
const getMomentsAndStudent = () => {
  axios
    .post(
      "/demo/moments/getMomentsAndStudent",
      {},
      {
        headers: {
          token: sessionStorage.getItem("token"),
        },
      }
    )
    .then((res) => {
      moments.content = res.data.result.content;
      moments.name = res.data.result.name;
      moments.date = res.data.result.date;
      moments.moments = res.data.result;
      console.log(" moments.moments", moments.moments.name);
      console.log(" moments.content", moments.content);
    })
    .catch((error) => {
      ElMessage.error("查询失败");
    });
};
const deletePhoto = (name) => {
  axios
    .post(
      "/demo/UpdateAndDown/detelePhoto",
      name,
      {
        headers: {
          token: sessionStorage.getItem("token"),
        },
      }
    )
    .then((res) => {

    })
    .catch((error) => {
      ElMessage.error("删除");
    });
};

</script>
<style scoped>
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
  cursor: pointer;
}
.block {
  top: 50px;
}
.textarea {
  border: 0;
  outline: none;
}
.bold {
  font-weight: bold;
  white-space: nowrap;
}
.top-card {
  font-weight: bold;
  font-size: x-large;
}
.name {
  font-weight: bold;
  font-size: larger;
}
.comment {
  font-weight: 530;
  font-size: larger;
}
.headerInput {
  width: 400px;
  border-right: 0ch;
}
.releaseMoments1 {
  border: 0;
  width: 99%;
}
.releaseMoments2 {
  width: 99%;
}
.page-box {
  background-color: #0c0a0a;
  height: 100%

}
</style>