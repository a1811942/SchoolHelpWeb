<template>
  <div>
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
            <el-input
              v-model="MomentContent.content"
              class="releaseMoments1"
              type="textarea"
              :rows="4"
            >
            </el-input>
            <!-- 上传照片 -->
            <el-upload
              action="http://localhost:8080/demo/UpdateAndDown/upload"
              list-type="picture-card"
              ref="uploadRef"
              :data="policyData"
              :on-preview="handlePictureCardPreview"
              :on-remove="handleRemove"
              :before-upload="beforeAvatarUpload"
              :headers="headerObj"
              :on-success="uploadSuccess"
            >
              <el-icon><Plus /></el-icon>
            </el-upload>

            <el-dialog v-model="dialogVisible">
              <img w-full :src="dialogImageUrl" alt="Preview Image" />
            </el-dialog>

            <el-row justify="end">
              <el-button type="primary" round @click="saveMoments"
                >发布</el-button
              ><el-button @click="beforeUpload">上传</el-button>
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
                  <el-avatar
                    :size="50"
                    :src="
                      'http://localhost:8080/demo/UpdateAndDown/down?name=' +
                      o.avatar
                    "
                  />
                </el-aside>
                <el-container>
                  <el-header height="10px">
                    <div class="name">{{ o.name }}--{{ o.school }}</div>
                    {{ o.date }}
                    <el-dropdown
                      :hide-on-click="false"
                      style="float: right"
                      v-if="o.student_id === nowStudentId"
                    >
                      <el-icon size="large"><More /></el-icon>

                      <template #dropdown>
                        <el-dropdown-menu>
                          <el-dropdown-item @click="deleteMoments(o.id)"
                            >删除</el-dropdown-item
                          >
                          <el-dropdown-item>修改</el-dropdown-item>
                        </el-dropdown-menu>
                      </template>
                    </el-dropdown>
                  </el-header>
                  <el-main>
                    <br />
                    <br />
                    <div class="comment">
                      {{ o.content }}
                      <!-- <div v-for="i in o.photoName" :key="i" >
                      <img :src="'http://localhost:3000/src/images/'+i" class="image"   /></div> -->
                      <!-- 显示图片 -->
                      <div class="demo-image__preview">
                        <el-image
                          v-for="image in o.photoName"
                          :key="image"
                          style="width: 200px; height: 200px"
                          :src="
                            'http://localhost:8080/demo/UpdateAndDown/down?name=' +
                            image
                          "
                          :preview-src-list="srcList"
                          :initial-index="1"
                          :headers="headerObj"
                          preview="1"
                          fit="cover"
                        />
                      </div>
                    </div>
                  </el-main>
                  <el-footer>
                    <div v-if="like.momentsId !== o.id">
                      <el-button
                        text
                        circle
                        @click.stop="dialogTableVisible = true"
                      >
                        <el-icon size="large"><ChatRound /></el-icon>
                      </el-button>
                      &#12288;

                      <el-button text circle @click.stop="likePoint(o.id)">
                        <el-icon size="large" color="red"><Pointer /></el-icon
                        >{{ o.likeCount }}
                      </el-button>
                    </div>

                    <div v-if="like.temp === '1' && like.momentsId === o.id">
                      <el-button
                        text
                        circle
                        @click.stop="dialogTableVisible = true"
                      >
                        <el-icon size="large"><ChatRound /></el-icon>
                      </el-button>
                      &#12288;

                      <el-button text circle @click.stop="likePoint(o.id)">
                        <el-icon size="large" color="red"><Pointer /></el-icon
                        >{{ like.likeCount }}
                      </el-button>
                    </div>
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
</template>
<script setup lang="ts">
import { computed, onMounted, reactive, ref } from "vue";
import {
  ElMessage,
  ElMessageBox,
  UploadFile,
  UploadInstance,
  UploadProps,
  UploadUserFile,
} from "element-plus";
import type { Action } from "element-plus";
import axios from "axios";
import { School, Search } from "@element-plus/icons-vue";
import { useRouter } from "vue-router";
import { policy } from "../upload/policy";
import Cookies from "js-cookie";
const dialogTableVisible = ref(false);
const dialogTableVisible1 = ref(false);
const router = useRouter();
const url = ref([]);
const srcList = ref("");
const headerObj = ref({ token: sessionStorage.getItem("token") });
// 上传照片
const dialogImageUrl = ref("");
const dialogVisible = ref(false);
const nowStudentId = sessionStorage.getItem("studentId");
//动态内容
const MomentContent = ref({
  content: "",
  momentsId: "",
});
//重新命名后照片的名称
var photoName = [];
//上传时照片的名称
var beforePhotoName = [];
//朋友圈
const moments = reactive({
  moments: [],
  content: "",
  photoName: [],
  name: "",
  date: "",
});
//点赞
const like = ref({
  //点赞数量
  likeCount: "",
  //是否点赞 1点赞 0未点赞
  isLike: "",
  //点赞的动态id
  momentsId: "",
  //标记变量
  temp: "",
});
let params = new FormData();
const policyData = ref({
  policy: "",
  signature: "",
  OSSAccessKeyId: "",
  key: "",
  dir: "",
  host: "",
});
const uploadRef = ref<UploadInstance>();
onMounted(() => {
  //  Cookies.set("token",sessionStorage.getItem("token"))

  getMomentsAndStudent();
});
//删除动态
const deleteMoments = (momentsId) => {
  ElMessageBox.confirm("这将永久删除此条动态，是否继续?", "警告", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning",
  })
    .then(() => {
      axios
        .post("/demo/moments/deleteMoments", momentsId, {
          headers: { token: sessionStorage.getItem("token") },
        })
        .then((res) => {
          ElMessage({
            type: "success",
            message: "删除成功",
          });
          getMomentsAndStudent();
        })
        .catch((error) => {
          ElMessage.error("点赞失败");
        });
    })
    .catch(() => {
      ElMessage({
        type: "info",
        message: "已取消",
      });
    });
};
//点赞
const likePoint = (momentsId) => {
  axios
    .post(
      "/demo/like/doLike",
      { momentsId, studentId },
      {
        headers: { token: sessionStorage.getItem("token") },
      }
    )
    .then((res) => {
      like.value.isLike = res.data.result.result;
      like.value.momentsId = res.data.result.momentsId;
      like.value.temp = "1";
      getLikeCount(momentsId);
    })
    .catch((error) => {
      ElMessage.error("点赞失败");
    });
};
//查询点赞个数
const getLikeCount = (momentsId) => {
  axios
    .post(
      "/demo/like/getLikeCount",
      { momentsId, nowStudentId },
      {
        headers: { token: sessionStorage.getItem("token") },
      }
    )
    .then((res) => {
      like.value.likeCount = res.data.result;
    })
    .catch((error) => {
      ElMessage.error("查询点赞失败");
    });
};
//发布动态
const saveMoments = () => {
  // saveMomentsPhoto();
  saveMomentsContent();
};
const clearFiles = () => {
  //调用el-upload外部函数
  // 在合适的地方调用，这是一个例子
};
const handleRemove: UploadProps["onRemove"] = (uploadFile, uploadFiles) => {
  //此处删除照片采用的数组中照片名称相对应，应该有bug，当上传失败时，数组中push为空，于改名后的照片名称对应不上
  for (var i = 0; i < beforePhotoName.length; i++) {
    if (beforePhotoName[i] == uploadFile.name) {
      photoName.splice(i, 1);
    }
    console.log("是否有", photoName[i] == uploadFile.name);
    console.log("photoName[i]", photoName[i]);
    console.log("uploadFile.name", uploadFile.name);
  }
  console.log(uploadFile, uploadFiles);
  // deletePhoto(uploadFile.name);
};

const handlePictureCardPreview: UploadProps["onPreview"] = (uploadFile) => {
  dialogImageUrl.value = uploadFile.url!;
  dialogVisible.value = true;
};
const uploadSuccess = (response, file, fileList) => {
  photoName.push(response.result);
};
const beforeAvatarUpload: UploadProps["beforeUpload"] = (rawFile) => {
  beforePhotoName.push(rawFile.name);

  if (rawFile.type !== "image/jpeg") {
    ElMessage.error("Avatar picture must be JPG format!");
    return false;
  } else if (rawFile.size / 1024 / 1024 > 5) {
    ElMessage.error("Avatar picture size can not exceed 2MB!");
    return false;
  }

  // return new Promise((resolve, reject) => {
  //   policy()
  //     .then((res) => {
  //       policyData.value.policy = res.data.result.policy;
  //       policyData.value.key =
  //         res.data.result.dir + "/" + guid() + rawFile.name;
  //       policyData.value.signature = res.data.result.signature;
  //       policyData.value.OSSAccessKeyId = res.data.result.accessId;
  //       policyData.value.dir = res.data.result.dir;
  //       policyData.value.host = res.data.result.host;
  //       resolve(true);
  //     })
  //     .catch((err) => {
  //       console.log("错误++++++++++++++2");
  //       reject(false);
  //     });
  // });
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

//查询所有用户用户发布的朋友圈信息
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
      moments.photoName = res.data.result.photo;
      moments.moments = res.data.result;
    })
    .catch((error) => {
      ElMessage.error("查询失败");
    });
};
const deletePhoto = (name) => {
  axios
    .post("/demo/UpdateAndDown/detelePhoto", name, {
      headers: {
        token: sessionStorage.getItem("token"),
      },
    })
    .then((res) => {})
    .catch((error) => {
      ElMessage.error("删除");
    });
};
//保存发布动态的照片信息
const saveMomentsPhoto = () => {
  axios
    .put(
      "/demo/photo/savePhoto",
      { photoName: photoName, momentsId: MomentContent.value.momentsId },

      {
        headers: { token: sessionStorage.getItem("token") },
      }
    )
    .then((res) => {
      MomentContent.value.momentsId = res.data.result;
      uploadRef.value!.clearFiles();

      getMomentsAndStudent();
    })
    .catch((error) => {
      ElMessage.error("发布失败");
    });
};
//保存发布动态的文字内容
const saveMomentsContent = () => {
  axios
    .post(
      "/demo/moments/saveMoments",

      {
        content: MomentContent.value.content,
        studentId: sessionStorage.getItem("studentId"),
      },
      {
        headers: { token: sessionStorage.getItem("token") },
      }
    )
    .then((res) => {
      MomentContent.value.momentsId = res.data.result;
      console.log(
        " MomentContent.value.momentsId",
        MomentContent.value.momentsId
      );
      saveMomentsPhoto();
      MomentContent.value.content = "";
      photoName = [];
      beforePhotoName = [];
    })
    .catch((error) => {
      ElMessage.error("发布失败");
    });
};
//生成uuid
function guid() {
  return "xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx".replace(/[xy]/g, function (c) {
    var r = (Math.random() * 16) | 0,
      v = c == "x" ? r : (r & 0x3) | 0x8;
    return v.toString(16);
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
.image {
  width: 30%;
  height: 30;
}
.page-box {
  background-color: brown;
  height: 100vw;
}
.like {
  color: red;
}
.update {
  float: right;
}
</style>